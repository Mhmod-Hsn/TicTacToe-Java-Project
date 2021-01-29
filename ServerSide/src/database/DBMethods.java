/*
    Abstract class that is used to send / reterive data from database
 */

package database;

import database.gameinfo.*;
import database.playerinfo.*;

import java.util.Vector;
import database.playerinfo.Player.orderType;

/**
 *
 * @author Ahmed Mamdouh Mostafa
 */

public abstract class DBMethods {
    
    // public static methods 
    public static boolean addPlayer(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar){
        return PlayerModel.insertRecord(_username, _passwd, _email, _status, _score, _avatar) ;
    }

    // update
    public static boolean update(long _pid ,String _newUsername , String _newPasswd , String _newEmail  , String _newStatus ,long _newScore , String _newAvatar){
        return PlayerModel.updateIdRecord(_pid, _newUsername, _newPasswd, _newEmail, _newStatus, _newScore, _newAvatar);
    }
    public static boolean updateWithCheck(String _username , String _passwd , String _newEmail  , String _newStatus ,long _newScore , String _newAvatar){
        return PlayerModel.updateUsrPassRecord(_username, _passwd, _newEmail, _newStatus, _newScore, _newAvatar);
    }
    public static boolean update(String _username , String _newPasswd , String _newEmail  , String _newStatus ,long _newScore , String _newAvatar){
        return PlayerModel.updateUsrRecord(_username, _newPasswd, _newEmail, _newStatus, _newScore, _newAvatar);
    }
    public static boolean updateScore(String _username , long _newScore ){
        return PlayerModel.updateUsrFieldScore(_username, _newScore);
    }
    public static boolean updateStatus(String _username , String _newStatus ){
        return PlayerModel.updateUsrFieldStatus(_username, _newStatus) ;
    }
    
    //update all status with no check (String _status)
    public static boolean updateStatus( String _newStatus ){
        return PlayerModel.updateFieldStatus(_newStatus) ;
    }
    public static boolean updateStatusWithCheck(String _username , String _passwd , String _newStatus ){
        return PlayerModel.updateUsrPassFieldStatus(_username, _passwd, _newStatus) ;
    }
    public static boolean updateScoreWithCheck(String _username , String _passwd ,long _newScore ){
        return PlayerModel.updateUsrPassFieldScore(_username, _passwd, _newScore);
    }
    
    // delete
    public static boolean delete(long _pid){
        return PlayerModel.deleteIdRecord(_pid);
    }
    public static boolean delete(String _username){
        return PlayerModel.deleteUsrRecord(_username) ;
    }
    public static boolean deleteMail(String _email){
        return PlayerModel.deleteMailRecord(_email) ;
    }
    public static boolean deleteMail(String _email, String _passwd){
        return PlayerModel.deleteMailPassRecord(_email, _passwd) ;
    }
    
    public static boolean delete(String _username, String _passwd){
        return PlayerModel.deleteUsrPassRecord(_username, _passwd) ;
    }
   
    //get    
    public static Long getScore(String _username ){
        return PlayerModel.selectScoreWhereUsr(_username);
    }
    public static Long getScore(String _username ,String _passwd ){
        return PlayerModel.selectScoreWhereUsrPass(_username, _passwd);
    }
    public static String getStatus(String _username ){
        return PlayerModel.selectStatusWhereUsr(_username) ;
    } 
    public static String getStatus(String _username ,String _passwd ){
        return PlayerModel.selectStatusWhereUsrPass(_username, _passwd);
    } 
    public static String getMail(String _username ){
        return PlayerModel.selectMailWhereUsr(_username);
    } 
    public static String getPass(String _username ){
        return PlayerModel.selectPassWhereUsr(_username);
    } 
    public static boolean isRecordExists(String _username ,String _passwd ){
        return PlayerModel.selectWhereUsrPass(_username, _passwd);
    }
    public static Player getPlayer(String _username ,String _passwd ){
        return PlayerModel.selectPlayerWhereUsrPass(_username, _passwd);
    }
    public static Vector<Player> getAllRecords(String _status ){
        return PlayerModel.selectAllWhereStatus(_status);
    }
    public static Vector<Player> getAllRecords(){   
        return PlayerModel.selectAllPlayers() ;
    }
    
    public static Vector<Player> getAllOrdered(String colName , orderType _order ){
        if (_order == orderType.ASC)
            return PlayerModel.selectAllPlayersOrderByASC(colName);
        else
            return PlayerModel.selectAllPlayersOrderByDESC(colName);             
     }    
    public static Vector<Player> getAllOrderedDesc(String colName ){
        return PlayerModel.selectAllPlayersOrderByDESC(colName);     
    }
    public static Vector<Player> getAllOrderedAsc(String colName ){
        return PlayerModel.selectAllPlayersOrderByASC(colName);
    }
    
    /****************Game Related********************/
    
    public static boolean addNewGame(Game.cellType _turn , Game.cellType[] _board , String _player1  , String _player2 ){
        return GameModel.insertRecord(_turn, _board, _player1, _player2 );
    }
    
    public static boolean deleteGame(long _gid )
    {
        return GameModel.deleteIdRecord(_gid);
    }
    
    public static Game getGame (long _gid) 
    {
        return GameModel.selectGameWhereId (_gid ) ; 
    }
    
    public static Vector<Game> getGameList(String player)
    {
       return GameModel.selectAllWhereP1OrderedDescDate(player) ;
    }
}
