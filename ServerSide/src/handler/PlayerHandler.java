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

import database.playerinfo.Player;
import server.DBOperations;
import server.utils.*;

import org.json.simple.JSONObject;

import org.json.simple.parser.ParseException;
import java.io.IOException;


/**
 *
 * @author Hossam
 */

public class PlayerHandler extends Thread{
 
    public Socket playerSocket;
    private Player player;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    
    private String errorMsg;
    private volatile JSONObject forwardedRequest;
    private JSONObject jsonObj;
        
    
    private static Vector <PlayerHandler> onlinePlayerHandlers = new Vector <>();
    
    class GameEstablishHandler extends Thread{

        private PlayerHandler senderPlayerHandler;
        private PlayerHandler receiverPlayerHandler;
  
        private volatile JSONObject receiverJson; 
        private JSONObject senderJson ;
        
        private volatile boolean isReceived = false;

        public GameEstablishHandler(PlayerHandler senderhHandler, PlayerHandler receiverHandler)
        {

            //store the handlers
            this.senderPlayerHandler = senderhHandler;
            this.receiverPlayerHandler = receiverHandler;
            
            //clear last read
            senderPlayerHandler.getForwardedRequest();
            receiverPlayerHandler.getForwardedRequest();
            
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
                
   
                //wait for response on the same request from player 2
                while(! isReceived) {

                    receiverJson = receiverPlayerHandler.getForwardedRequest();

                    if (receiverJson == null);
                    
                    else if (receiverJson.get("type").equals(Requests.RECEIVE_INVITATION))
                    {
                        isReceived = true;
                    }
                }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("[GameEstablishHandler class]: A new request was received from player2: "+ receiverJson.toString());
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////           
                //Construct response for player 1 (in case of valid delivery)
                senderJson = JSONHandeling.playerToJson(receiverPlayerHandler.getPlayerInfo());
                
                senderJson = JSONHandeling.constructJsonResponse(senderJson ,Requests.SEND_INVITATION);
                
                senderJson = JSONHandeling.addToJsonObject(senderJson,"invitationStatus"
                        ,receiverJson.get("invitationStatus"));

                //send the response to the sender player
                senderPlayerHandler.getOutputStream().writeUTF(senderJson.toString());
                
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                System.out.println("[GameEstablishHandler class]: A new request was sent to player1: "+ senderJson.toString());
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                //establish game if the invitation is accepted
                if (receiverJson.get("invitationStatus").equals("true")) {
                    
                    //update players status to busy
                    senderPlayerHandler.updatePlayerStatus("busy");
                    receiverPlayerHandler.updatePlayerStatus("busy");
                    
                    // Start Game 
                    new GameHandler(senderPlayerHandler,receiverPlayerHandler);
                }
                
                //close this thrad 
                close();
                
            } catch (IOException ex) {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                
                //Conection Failed
                System.out.println("[GameEstablishHandler class]: Player connection dropped (connection failed)");
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                
                close();
            }
        }

        public void close()
        {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            System.out.println("[GameEstablishHandler class]: thread is closed");
            this.stop(); 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        }
    }
    
    public PlayerHandler(Socket socket, Player playerInfo)
    {
        this.playerSocket = socket;
        this.player = playerInfo;
                
        jsonObj = new JSONObject();

        
        try {
            //Create the input and output channels
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            //Add to the vector of handlers
            onlinePlayerHandlers.add(this);    
            
            //Start the thread to accept requests
            this.start();
            
        } catch (IOException ex) {
            
            System.out.println("[PlayerHandler class]: Client has dropped");
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
                System.out.println("[PlayerHandler class]: Player connection dropped"); 
                close();
                
            } catch (ParseException ex) {
                System.out.println("[PlayerHandler class]: Parse Exception in the main thread"); 
                System.out.println(jsonObj);
            } 
        }
    }
    
    //Getters
    public JSONObject getForwardedRequest(){
        JSONObject result = forwardedRequest;
        forwardedRequest = null;
        return result;
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
    public long getPlayerScore()
    {
        return player.getScore();
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
            System.out.println("[PlayerHandler class]: Player socket couldn't be closed.");
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
        jsonObj = JSONHandeling.parseStringToJson(jsonStr);
        
        JSONObject responseJsonObj = new JSONObject();
        System.out.println("[PlayerHandler class]: Parsing player request=> "+jsonObj.toString());
        //find out which request
        String requestType = (String)jsonObj.get("type");

        //is the request proccessed successfully 
        boolean isSuccess = false;
        
        switch (requestType)
        {

            //Signout request
            case Requests.SIGN_OUT:
                //signout from the account
                System.out.println("[PlayerHandler class]: Player has loggedout");
                close();
                isSuccess = true;
                break;
                
            //Send invitation request
            case Requests.SEND_INVITATION:
                //send invitation to another player
                isSuccess = playerInvite(jsonObj.get("username").toString());
                return null;
            
            //update status request
            case Requests.UPDATE_STATUS:
                //update the player status
                isSuccess = updatePlayerStatus(jsonObj.get("status").toString());
                break;
            
            //update score request   
            case Requests.UPDATE_SCORE:       
                //update the player score
                isSuccess =  updatePlayerScore((long)jsonObj.get("score"));
                break;
                
            //requests to be forwarded to other handlers
            case Requests.RECEIVE_INVITATION: case Requests.GAME_ENDED:
            case Requests.GAME_MOVE: case Requests.CHAT_MSG: 
            case Requests.GAME_STARTED:
                
                //forward the request
                forwardedRequest = jsonObj;
                return null; 
                
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
            System.out.println("[PlayerHandler class]: Player Request [ "+requestType+"] has succeeded");
        }
        else 
        {
            responseJsonObj = JSONHandeling.errorToJson(requestType, errorMsg); 
            System.out.println("[PlayerHandler class]: Player Request [ "+requestType+"] has failed");
            System.out.println("[PlayerHandler class]: The failed request: "+jsonObj);
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
    public boolean updatePlayerScore(long newScore)
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
    public boolean updatePlayerStatus(String newStatus)
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
