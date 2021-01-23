/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;



import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.util.Vector;

import playerinfo.Player;
import server.DBOperations;
import server.utils.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hossam
 */

public class PlayerHandler extends Thread{
 
    private JSONObject lastRequest;
    public Socket playerSocket;
    private Player player;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    
    private String errorMsg;
    
    private JSONObject jsonObj;
    private JSONParser parser;
        
    private static Vector <PlayerHandler> onlinePlayerHandlers = new Vector <>();
    
    class GameEstablishHandler extends Thread{

        PlayerHandler senderPlayerHandler;
        PlayerHandler receiverPlayerHandler;
  
        JSONObject senderJson, receiverJson;

        public GameEstablishHandler(PlayerHandler senderhHandler, PlayerHandler receiverHandler)
        {
            this.senderPlayerHandler = senderhHandler;
            this.receiverPlayerHandler = receiverHandler;
                       
            //Start the thread
            this.start();   
        }

        @Override
        public void run()
        {
            try {
                
                receiverJson = new JSONObject();
                senderJson = new JSONObject();

                //Construct invitation json object
                receiverJson = JSONHandeling.playerToJson(senderPlayerHandler.getPlayerInfo());
                receiverJson = JSONHandeling.addToJsonObject(receiverJson,"type", Requests.RECEIVE_INVITATION);

                //Send invitation to player 2
                receiverPlayerHandler.getOutputStream().writeUTF(receiverJson.toString());
                
                System.out.println("sent to player2: "+ receiverJson.toString());

   
                //wait for response on the same request from player 2
                while(true) {

                    receiverJson = receiverPlayerHandler.getlastRequest();
                    
                    if (receiverJson == null)
                    {
                        continue;
                    }
                    if (receiverJson.get("type").equals(Requests.RECEIVE_INVITATION))
                    {
                        break;
                    }
                }
    
                System.out.println("received from player2: "+ receiverJson.toString());
               
                //Construct response for player 1 (in case of valid delivery)
                senderJson = JSONHandeling.playerToJson(receiverPlayerHandler.getPlayerInfo());
                
                senderJson = JSONHandeling.constructJsonResponse(senderJson ,Requests.SEND_INVITATION);
                
                senderJson = JSONHandeling.addToJsonObject(senderJson,"invitationStatus"
                        ,(boolean) receiverJson.get("invitationStatus"));

                //send the response to the sender player
                senderPlayerHandler.getOutputStream().writeUTF(senderJson.toString());
                
                System.out.println("sent to player1: "+ senderJson.toString());
                
                //establish game if the invitation is accepted
                if ((boolean) receiverJson.get("invitationStatus")) {
                    // Start Game 
                }
                
                //close this thrad 
                close();
                
            } catch (IOException ex) {
                //Conection Failed
                System.out.println("[GameEstablishHandler] Player connection dropped");
                close();
            }
//            } catch (ParseException ex) {
//               System.out.println("[GameEstablishHandler] Parse Exception");
//               ex.printStackTrace();
//            }   
        }

        public void close()
        {
            System.out.println("GameEstablishHander closed");
            this.stop(); 
        }
    }
    
    public PlayerHandler(Socket socket, Player playerInfo)
    {
        this.playerSocket = socket;
        this.player = playerInfo;
                
        jsonObj = new JSONObject();
        parser = new JSONParser();
        
        try {
            //Create the input and output channels
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            //Add to the vector of handlers
            onlinePlayerHandlers.add(this);    
            
            //Start the thread to accept requests
            this.start();
            
        } catch (IOException ex) {
            System.out.println("Client dropped in PlayerHandler");
            close();
        }
    }
    
    @Override
    public void run()
    {
        //Listen to the requests of the players
        while (true)
        {
            try {
                //handle user requests
                jsonObj = playerRequestHandler(inputStream.readUTF());

                if (jsonObj != null)
                {
                    //send response to the user               
                    outputStream.writeUTF(jsonObj.toString());
                }  
                //Connection Drop
            } catch (IOException ex) { 
                System.out.println("[PlayerHandler]: player connection dropped"); 
                close();
                
            } catch (ParseException ex) {
                System.out.println("[PlayerHandler]: Parse Exception"); 
                System.out.println(jsonObj);
            } 
        }
    }
    
    //Getters
    
    public JSONObject getlastRequest(){
        return lastRequest;
    }

    public Player getPlayerInfo() {
        return player;
    }
    public DataOutputStream getOutputStream() {
        return outputStream;
    }
    public DataInputStream getInputStream() {
        return inputStream;
    }
    
    public static Vector<PlayerHandler> getOnlinePlayerHandlers() {
        return onlinePlayerHandlers;
    }
    
    
    //Methods used by server to manage this handler
    
    //close the current thread and logout the current player
    public void close()
    {
        try {
            
            //Signout this player
            DBOperations.logout(player.getUsername());
            
            //remove this current player from online vector
            onlinePlayerHandlers.remove(this);
            
            //Close the connection
            playerSocket.close();
            
            //close this thread
            this.stop();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("[PlayerHandler] Player socket can't be closed.");
        }
    }
    
    //empty the online list
    public static void resetHandlers()
    {
        onlinePlayerHandlers.clear();
    }
    

    //Players Requests handeling
    private JSONObject playerRequestHandler(String jsonStr) throws ParseException
    {
        
        lastRequest = JSONHandeling.parseStringToJson(jsonStr);
        JSONObject responseJsonObj = new JSONObject();
        
        //find out which request
        String requestType = (String)lastRequest.get("type");
        
        //is the request proccessed successfully 
        boolean isSuccess = false;
        
        switch (requestType)
        {
            
            case Requests.RECEIVE_INVITATION:
                //forward the request
                return null;

            //Signout request
            case Requests.SIGN_OUT:
                //signout from the account
                System.out.println("Player loggedout");
                close();
                isSuccess = true;
                break;
                
            //Send invitation request
            case Requests.SEND_INVITATION:
                //send invitation to another player
                isSuccess = playerInvite(lastRequest.get("username").toString());
                break;
            
            //update status request
            case Requests.UPDATE_STATUS:
                //update the player status
                isSuccess = updatePlayerStatus(lastRequest.get("status").toString());
                break;
            
            //update score request   
            case Requests.UPDATE_SCORE:       
                //update the player score
                isSuccess =  updatePlayerScore((long)lastRequest.get("score"));
                break;
                
            //Unknown request
            default:
                requestType =  Requests.UNKNOWN;
                errorMsg = Errors.INVALID;
                isSuccess = false;
                break;
        }
        
        if (isSuccess)
        {
            responseJsonObj = JSONHandeling.constructJsonResponse(responseJsonObj, requestType);
            System.out.println("Player Request [ "+requestType+"] is a success");
        }
        else 
        {
            responseJsonObj = JSONHandeling.errorToJson(requestType, errorMsg); 
            System.out.println("Player Request [ "+requestType+"] is a faliure");
            System.out.println("Player Request "+lastRequest+" is a faliure");
        }

        return responseJsonObj;
    }
    
    //invite player request
    private boolean playerInvite(String username)
    {

        PlayerHandler playerToInvite = isPlayerHandlerExists(username);
        errorMsg = "";

        //player wasn't found
        if (playerToInvite == null)
        {
            errorMsg = Errors.NOT_EXIST;
            return false;
        }
        //check if player is not online
        if (! playerToInvite.getPlayerInfo().getStatus().toString().equals("online"))
        {
            errorMsg = Errors.BUSY;
            return false;
        }

        //start game Game Establish thread
        new GameEstablishHandler (this, playerToInvite);

        return true;

    }
     
    //update player score request
    private boolean updatePlayerScore(long newScore)
    {
        errorMsg = "";
        
        if (! DBOperations.updatePlayerScore(player.getUsername(), newScore))
        {
            errorMsg = Errors.NOT_EXIST;
            return false;
        }
        //update current player
        this.player.setScore(newScore);

        return true;
    }
    
    //update player status
    private boolean updatePlayerStatus(String newStatus)
    {   
        errorMsg = "";
        //fix the (ingame) bug
        if (newStatus.equals("ingame"))
        {
            newStatus = Player.statusType.busy.toString();
        }
        
        //check allowed status
        if ( ! (newStatus.equals(Player.statusType.busy.toString()) ||
            newStatus.equals(Player.statusType.online.toString())))
        {
            errorMsg = Errors.INVALID;
            return false;
        }
        if (! DBOperations.updatePlayerStatus(player.getUsername(),newStatus))
        {
            errorMsg = Errors.NOT_EXIST;
            return false;
        }
        
        //update current status
        if (newStatus.equals(Player.statusType.busy.toString()))
        {
            this.player.setStatus(Player.statusType.busy);
        }
        else 
        {
            this.player.setStatus(Player.statusType.online);
        }
        return true;
    }
    
    //Check if a player is online
    private PlayerHandler isPlayerHandlerExists (String  username)
    {
        for (PlayerHandler handler : onlinePlayerHandlers)
        {
            if (handler.getPlayerInfo().getUsername().equals(username))
            {
                return handler;
            }
        }
        //player was not found
        return null;
    }
}
