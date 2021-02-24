/*
    This is an abstract class that is responsbile for interacing with database.
 */

package server;

import database.gameinfo.Game;
import java.util.Vector;
import database.DBMethods;
import database.playerinfo.Player;


/**
 *
 * @author Ahmed Mamdouh Mostafa
 */

public abstract class DBOperations {
    
    //indicates if the db is updated from the last check
    private static boolean isDBChanged = false;
    
    
    /*******Updates control methods***********/
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
    

    /*******User related methods***********/
    
    public static Player login(String username, String password){
        Player p = DBMethods.getPlayer(username, password);
        
        if (p != null) {
            DBMethods.updateStatus(username, (Player.statusType.online).toString());
            p.setStatus(Player.statusType.online);
            
            //assign the db changed flag 
            isDBChanged = true;
        }
        return p;
    }

    public static boolean isUserExists(String username, String password){
        return DBMethods.isRecordExists(username, password);
    }
    
    public static boolean isLoggedIn(String username, String password){
        String status = DBMethods.getStatus(username);
        
        if ( status.equals("online") || status.equals("busy")) 
        {
            return true;
        }
        
        return false;
    }
    
    public static Player register(String username, String password){
        
        Player newPlayer = new Player(username, password);
        
        newPlayer.setScore((long)0);
        
        newPlayer.setStatus(Player.statusType.online);
        
        boolean isCreated = DBMethods.addPlayer(newPlayer.getUsername() ,newPlayer.getPasswd(),null, newPlayer.getStatus().toString(), newPlayer.getScore(), null);

        if (isCreated)
        {
           //assign the db changed flag 
           isDBChanged = true;
           return newPlayer;
        }
        
        return null;  
    }
    
    
    /*******Players related methods***********/
    public static boolean logout (String username)
    {
        //assign the db changed flag 
        isDBChanged = true;
        return DBMethods.updateStatus(username, (Player.statusType.offline).toString());
    }
    
    public static boolean updatePlayerScore (String username, long newScore)
    {
        if (DBMethods.updateScore(username, newScore)) {
            
            isDBChanged = true;
            return true;
        }
        return false;
    }
    
    public static boolean updatePlayerStatus (String username, String newScore)
    {
        if (DBMethods.updateStatus(username, newScore)) {
            isDBChanged = true;
            return true;
        }
            return false;
    }
    
    public static Vector <Player> getAllPlayers()
    {
        return DBMethods.getAllOrderedDesc("score");
    }
    
    public static boolean setAllOffline()
    {
        //assign the db changed flag 
        isDBChanged = true;
        return DBMethods.updateStatus(Player.statusType.offline.toString());
    }
    
    public static Vector <Player> getOnlinePlayers()
    {
        Vector<Player> onlinePlayers = DBMethods.getAllRecords(Player.statusType.online.toString());
             
        onlinePlayers.addAll(getBusyPlayers());
        
        return onlinePlayers;
    }
    
    public static Vector <Player> getBusyPlayers()
    {
        return DBMethods.getAllRecords(Player.statusType.busy.toString());
    }
    
    public static Vector <Player> getOfflinePlayers()
    {
        return DBMethods.getAllRecords(Player.statusType.offline.toString());
    }
    
    
    /*******Game related methods***********/
    public static boolean addGame(Game.cellType []gameBoard, String player1 ,String player2,  Game.cellType nextMove)  
    {
        //**ADD GAME ***/
        return DBMethods.addNewGame(nextMove,gameBoard,player1,player2);
    }

    public static boolean deleteGame (long gameId)
    {
        return DBMethods.deleteGame(gameId);
    }
    
    public static Vector<Game>  getGameList (String username)
    {
        return DBMethods.getGameList(username);
    }
    
    public static Game  getGame (Long gameId)
    {
        return DBMethods.getGame(gameId);
    } 
}