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
        //Listen to the requests of the users
        while (true)
        {/*
            try {
                playerRequest = inputStream.readUTF();
                

                if () //Sign in request
                {
                }
                else if ()//Sign up request
                {
                }
                else //Null (invalid request)
                {   
                    close();
                }

                //Connection Drop
            } catch (IOException | ClassNotFoundException ex) { 
                //close this socket and end this thread
                ex.printStackTrace();
                close();
            } */
        }
    }
    
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
    
    public void resetHandlers()
    {
        onlinePlayerHandlers.clear();
    }
    
    public JSONObject playerToJson(Player player)
    {
       JSONObject json = new JSONObject();
       
       json.put("username", player.getUsername());
       json.put("score", player.getScore());
       json.put("Status", player.getStatus());
       json.put("avatar", player.getAvatar());
       
       return json;
    }
    
    private JSONObject parseStrToJson(String jsonStr) throws ParseException
    {
        JSONParser parser = new JSONParser();
        
        JSONObject json = (JSONObject)parser.parse(jsonStr);
        
        return json;
    }
    
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

    private boolean playerInvite()
    {
        return true;
    }
    
    private boolean updatePlayerScore(long newScore)
    {
        return (DBOperations.updatePlayerScore(player.getUsername(), newScore));
    }
    
    private void signOut()
    {
        //signout successfully
        this.close();
    }
    
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
}



///////////////////////////
/* 
    private void notifyNewPlayerList(Vector <Player> playerList)
    {
        //construct json array
        JSONArray jsonArray = playerListToJSONArray(playerList);
        
        //Broadcast the json array
        for(PlayerHandler handler: onlinePlayerHandlers)
        {
            try {
                handler.outputStream.writeUTF(jsonArray.toJSONString());
            } catch (IOException ex) {
                
                //Client has dropped remove this client
                handler.close();

            }
        }
    }
    
    private JSONArray playerListToJSONArray(Vector <Player> playerList)
    {
        //Construct json array
        jsonPlayersList = new JSONArray();
    
        for (Player playerInfo : playerList)
        {
            jsonPlayersList.add(playerToJson(playerInfo));   
        }
        
        return jsonPlayersList;
    }
    */