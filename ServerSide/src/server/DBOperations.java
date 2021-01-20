/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Vector;
import playerinfo.DbMethods;
import playerinfo.Player;


/**
 *
 * @author ahmed mamoduh 2
 */

public abstract class DBOperations {
    //indicates if the db is updated from the last check
    private static boolean isDBChanged = false;
    
            
    public static boolean isDBChanged ()
    {
        //save the value of the flag
        boolean result = isDBChanged;
        
        //reset the flag because of this check
        resetIsDBChanged();
        
        return result;
    }
    
    public static void resetIsDBChanged ()
    {
        isDBChanged = false;
    }
    
    public static Player login(String username, String password){
        Player p = DbMethods.getPlayer(username, password);
        
        if (p != null) {
            DbMethods.updateStatus(username, (Player.statusType.online).toString());
            p.setStatus(Player.statusType.online);
            
            //assign the db changed flag 
            isDBChanged = true;
        }
        return p;
    }

    public static boolean isUserExists(String username, String password){
        return DbMethods.isRecordExists(username, password);
    }
    
    public static boolean isLoggedIn(String username, String password){
        String status = DbMethods.getStatus(username);
        
        if ( status.equals("online") || status.equals("busy")) 
        {
            return true;
        }
        
        return false;
    }
    
    public static boolean logout (String username)
    {
        //assign the db changed flag 
        isDBChanged = true;
        return DbMethods.updateStatus(username, (Player.statusType.offline).toString());
    }
    
    public static boolean updatePlayerScore (String username, long newScore)
    {
        return (DbMethods.updateScore(username, newScore));
    }
    public static Player register(String username, String password){
        
        Player newPlayer = new Player(username, password);
        
        newPlayer.setScore((long)0);
        
        newPlayer.setStatus(Player.statusType.online);
        
        boolean isCreated = DbMethods.addPlayer(newPlayer.getUsername() ,newPlayer.getPasswd(),null, newPlayer.getStatus().toString(), newPlayer.getScore(), null);

        if (isCreated)
        {
           //assign the db changed flag 
           isDBChanged = true;
           return newPlayer;
        }
        
        return null;  
    }

    public static Vector <Player> getAllPlayers()
    {
        return DbMethods.getAllOrderedDesc("score");
    }
    
    public static boolean setAllOffline()
    {
        //assign the db changed flag 
        isDBChanged = true;
        return DbMethods.updateStatus(Player.statusType.offline.toString());
    }
    
    public static Vector <Player> getOnlinePlayers()
    {
        Vector<Player> onlinePlayers = DbMethods.getAllRecords(Player.statusType.online.toString());
             
        onlinePlayers.addAll(getBusyPlayers());
        
        return onlinePlayers;
    }
    
    public static Vector <Player> getBusyPlayers()
    {
        return DbMethods.getAllRecords(Player.statusType.busy.toString());
    }
    
    public static Vector <Player> getOfflinePlayers()
    {
        return DbMethods.getAllRecords(Player.statusType.offline.toString());
    }
    
    
    
    
}