/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;


import java.util.Vector;

import playerinfo.Player;
import server.DBOperations;
import server.utils.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
/**
 *
 * @author Hossam
 */
public class UpdatesHandler extends Thread{
    
    private Vector <Player> playerVect;
    private Vector <PlayerHandler> handlersVect;
    
    public UpdatesHandler()
    {
        this.start();
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            //Check if the db has changed from the last iteration

            if(DBOperations.isDBChanged())
            {
                
                //update players list
                playerVect = DBOperations.getAllPlayers();

                //update handlers
                handlersVect = PlayerHandler.getOnlinePlayerHandlers();

                //Notify all players
                //notifyNewPlayerList(playerVect);
            }
           // if not skip this iteration

            try {
                sleep(500);
            } catch (InterruptedException ex) {

            }
        }
    }
    public Vector <Player> getCurrentPlayerList()
    {
        return playerVect;
    }
    
    private void notifyNewPlayerList(Vector <Player> playerList)
    {
        //construct json array
        JSONArray jsonArray = JSONHandeling.playerListToJSONArray(playerList);
        JSONObject json = new JSONObject();

        json.put("type", Requests.UPDATE_LIST);
        json.put("list", jsonArray);
        

        //Broadcast the json array
        for(PlayerHandler handler: handlersVect)
        {
            try {
                handler.getOutputStream().writeUTF(json.toString());
            } catch (IOException ex) {
                
                //Client has dropped remove this client
                handler.close();
            }
        }
    }
    
    public void close()
    {
        this.stop();
    }
}
