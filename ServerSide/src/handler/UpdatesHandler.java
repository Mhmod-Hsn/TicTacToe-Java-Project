/*
    The thread that is responsible for updating the live list of players
    and broadcast the changes to all players + the server monitoring.
 */

package handler;


import java.util.Vector;

import database.playerinfo.Player;
import server.DBOperations;
import server.utils.JSONHandeling;
import server.utils.Requests;
import server.utils.ServerUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 *
 * @author Hossam Khalil
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
                
                if (playerVect == null)
                {
                    playerVect = new Vector();
                }
                
                //update handlers
                handlersVect = PlayerHandler.getOnlinePlayerHandlers();

                //Notify all players
                notifyNewPlayerList(playerVect);
            }
            
           // if not skip this iteration
            try {
                sleep(300);
            } catch (InterruptedException ex) {
                ServerUtils.appendLog("[Error]: Update list thread failed to sleep.");
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
