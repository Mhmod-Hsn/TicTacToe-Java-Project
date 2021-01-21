/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;


import playerinfo.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;

import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.DBOperations;
import server.utils.Errors;
import server.utils.Requests;
/**
 *
 * @author Hossam
 */

public class PlayerHandler extends Thread{
    
    private Socket playerSocket;
    private Player player;
    
    private Player playerRequest;
    
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    
    private JSONObject json;
    private JSONParser parser;
        
    private static Vector <PlayerHandler> onlinePlayerHandlers = new Vector <>();
    
    class GameEstablishHandler extends Thread{

        PlayerHandler senderPlayerHandler;
        PlayerHandler receiverPlayerHandler;
        
        String errorMsg;
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
                receiverJson = senderPlayerHandler.playerToJson(senderPlayerHandler.getPlayerInfo());
                receiverJson.put("type", Requests.RECEIVE_INVITATION);

                //Send invitation to player 2
                receiverPlayerHandler.getOutputStream().writeUTF(receiverJson.toString());

                //wait for response on the same request from player 2
                do {
                    receiverJson = parseStrToJson(receiverPlayerHandler.getInputStream().readUTF());
                } while (!receiverJson.get("type").equals(Requests.RECEIVE_INVITATION));

                //Construct response for player 1 (in case of valid delivery)
                senderJson = receiverPlayerHandler.playerToJson(receiverPlayerHandler.getPlayerInfo());
                
                senderJson.put("type", Requests.SEND_INVITATION);
                senderJson.put("responseStatus", true);
                senderJson.put("errorMsg", "");
                senderJson.put("invitationStatus", (boolean) receiverJson.get("invitationStatus"));

                senderPlayerHandler.getOutputStream().writeUTF(senderJson.toString());

                //establish game if the invitation is accepted
                if ((boolean) receiverJson.get("invitationStatus")) {
                    // Start Game 
                }
                
            } catch (IOException ex) {
                //Conection Failed
                System.out.println("Connection failed to close game from gamee stablish handler");
                Logger.getLogger(GameEstablishHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(GameEstablishHandler.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }

        public void close()
        {
            this.stop();
            System.out.println("GameEstablishHander closed");
        }
    }
    
    public PlayerHandler ()
    {
        
    }
    
    public PlayerHandler(Socket socket, Player playerInfo)
    {
        this.playerSocket = socket;
        this.player = playerInfo;
                
        json = new JSONObject();
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
            ex.printStackTrace();
            close();
        }
    }
    
    @Override
    public void run()
    {
        //Listen to the requests of the players
        while (true)
        {

        }
    }
    
    //Getters
    public Player getPlayerInfo(){
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
    public void resetHandlers()
    {
        onlinePlayerHandlers.clear();
    }
    

    //Methods used for json object handeling
    
    //convert player object to json object
    public JSONObject playerToJson(Player player)
    {
       JSONObject json = new JSONObject();
       
       json.put("username", player.getUsername());
       json.put("score", player.getScore());
       json.put("Status", player.getStatus());
       json.put("avatar", player.getAvatar());
       
       return json;
    }
    //convert string to json object
    private JSONObject parseStrToJson(String jsonStr) throws ParseException
    {
        JSONParser parser = new JSONParser();
        
        JSONObject json = (JSONObject)parser.parse(jsonStr);
        
        return json;
    }
    
    
    //Players Requests handeling
    
    private boolean handlePlayerRequest(JSONObject json)
    {

        switch ((String)json.get("type"))
        {
            //Signout request
            case Requests.SIGN_OUT:
                signOut();
                break;
                
            case Requests.SEND_INVITATION:
               // playerInvite(this.player.getUsername());
                break;
            
            case Requests.UPDATE_SCORE:
                
                updatePlayerScore((long)json.get("score"));
                break;
                
            default:
                return false;
        }

        return true;
    }
    //invite player request
    private boolean playerInvite()
    {
        return true;
    }
    //update player score request
    private boolean updatePlayerScore(long newScore)
    {
        return (DBOperations.updatePlayerScore(player.getUsername(), newScore));
    }
    //signout request
    private void signOut()
    {
        //signout successfully
        this.close();
    }

}
