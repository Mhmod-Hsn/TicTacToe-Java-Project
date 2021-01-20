/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.io.IOException;
import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import playerinfo.Player;
import server.AppDbOperation;

/**
 *
 * @author Hossam
 */
public class UpdatesHandler extends Thread{
    
    private static final AppDbOperation DB_OBJ = new AppDbOperation();
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

            if(AppDbOperation.isDBChanged())
            {
                
                //update players list
                playerVect = DB_OBJ.getAllPlayers();

                //update handlers
                handlersVect = PlayerHandler.getOnlinePlayerHandlers();

                //Notify all players
                notifyNewPlayerList(playerVect);
            }
           // if not skip this iteration

            try {
                sleep(1000);
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
        JSONArray jsonArray = playerListToJSONArray(playerList);
        
        //Broadcast the json array
        for(PlayerHandler handler: handlersVect)
        {
            try {
                handler.getOutputStream().writeUTF(jsonArray.toJSONString());
            } catch (IOException ex) {
                
                //Client has dropped remove this client
                handler.close();
            }
        }
    }
    
    private JSONArray playerListToJSONArray(Vector <Player> playerList)
    {
        //Construct json array
        JSONArray jsonPlayersList = new JSONArray();
    
        for (Player playerInfo : playerList)
        {
            jsonPlayersList.add(playerToJson(playerInfo));   
        }
        
        return jsonPlayersList;
    }
    
    private JSONObject playerToJson(Player player)
    {
       JSONObject json = new JSONObject();
       
       json.put("username", player.getUsername());
       json.put("score", player.getScore());
       json.put("status", player.getStatus());
       json.put("avatar", player.getAvatar());
       
       return json;
    }
    
    public void close()
    {
        this.stop();
    }
}
