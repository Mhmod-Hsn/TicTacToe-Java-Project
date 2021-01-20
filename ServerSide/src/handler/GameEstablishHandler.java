/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import playerinfo.Player;
import server.DBOperations;
import server.utils.Errors;
import server.utils.Requests;

/**
 *
 * @author amamd
 */
public class GameEstablishHandler extends Thread{
    
    PlayerHandler senderPlayerHandler;
    PlayerHandler receiverPlayerHandler;
    UserRequestInfo userReqInfo;
    DBOperations dbOperation;
    String errorMsg;
    JSONObject senderJson, receiverJson;
    JSONParser parser;
    Player curPlayer;
    
    
   
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
            parser = new JSONParser();
            
            //Create the sender input and output channels
            receiverJson = senderPlayerHandler.playerToJson(senderPlayerHandler.getPlayerInfo());
            receiverJson.put("type", Requests.RECEIVE_INVITATION);
            
            
            //Send invitation to player 2
            receiverPlayerHandler.getOutputStream().writeUTF(receiverJson.toString());
            
            do {
                receiverJson = (JSONObject) parser.parse(receiverPlayerHandler.getInputStream().readUTF());
            } while (!receiverJson.get("type").equals(Requests.RECEIVE_INVITATION));
            
            
            senderJson = receiverPlayerHandler.playerToJson(receiverPlayerHandler.getPlayerInfo());
            senderJson.put("type", Requests.SEND_INVITATION);
            senderJson.put("responseStatus", true);
            senderJson.put("errorMsg", "");
            senderJson.put("invitationStatus", (boolean) receiverJson.get("invitationStatus"));
            
            senderPlayerHandler.getOutputStream().writeUTF(senderJson.toString());
 
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
        System.out.println("Authentication handler closed");
    }
}
 
