/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import server.utils.JSONHandeling;
import server.utils.Requests;

/**
 *
 * @author Hossam
 */

public class GameHandler {
    
    private PlayerHandler xPlayerHandler;
    private PlayerHandler oPlayerHandler;
    
    private JSONObject jsonObj;
    private DataOutputStream xPlayerOutput;
    private DataOutputStream oPlayerOutput;
    
    public GameHandler(PlayerHandler xPlayerHandler, PlayerHandler oPlayerHandler)
    {
        //define the handlers
        this.xPlayerHandler = xPlayerHandler;
        this.oPlayerHandler = oPlayerHandler;
        
        //define output streams
        this.xPlayerOutput = xPlayerHandler.getOutputStream();
        this.oPlayerOutput = oPlayerHandler.getOutputStream();

        //create a json object
        jsonObj = new JSONObject();
        
        //construct the init json to boradcast
        jsonObj = JSONHandeling.constructJsonResponse(jsonObj, Requests.GAME_STARTED);
        
        
        //construct the jason
        //boradcast that the game has started
        broadcast(jsonObj);
        
        //Start the thread
        //this.start();   
    }
    
    private boolean broadcast(JSONObject json)
    {
        try {
            this.xPlayerOutput.writeUTF(json.toString());
            this.oPlayerOutput.writeUTF(json.toString());
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

}