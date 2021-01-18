/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerinfo;

import java.util.Vector;

import playerinfo.Player.orderType;

/**
 *
 * @author Hossam
 */

public abstract class DbMethods {
    
    // public static methods 
    public static boolean add(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar){
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
    public static boolean check(String _username ,String _passwd ){
        return PlayerModel.selectWhereUsrPass(_username, _passwd);
    }
    public static Player get(String _username ,String _passwd ){
        return PlayerModel.selectPlayerWhereUsrPass(_username, _passwd);
    }
    public static Vector<Player> getAll(String _status ){
        return PlayerModel.selectAllWhereStatus(_status) ;
    }
    public static Vector<Player> getAll(){   
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
    
}


//-------------------------------  Player Model ProtoTypes -------------------------------
//    boolean insertRecord(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar);
//    public boolean updateIdRecord(long _pid ,String _username , String _passwd , String _email  , String _status ,long _score , String _avatar);
//    public boolean updateUsrRecord(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar);
//    public boolean updateUsrFieldStatus(String _username , String _status );
//    public boolean updateUsrFieldScore(String _username , long _score );
//    public boolean updateUsrPassRecord(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar);
//    public boolean updateUsrPassFieldStatus(String _username , String _passwd , String _status );
//    public boolean updateUsrPassFieldScore(String _username , String _passwd ,long _score );
//    public boolean deleteIdRecord(long _pid);
//    public boolean deleteUsrRecord(String _username);
//    public boolean deleteMailRecord(String _email);
//    public boolean deleteMailPassRecord(String _email, String _passwd);
//    public boolean deleteUsrPassRecord(String _username, String _passwd);
//     //DML Queries
//    public Long selectScoreWhereUsr(String _username );
//    public String selectStatusWhereUsr(String _username );
//    public String selectPassWhereUsr(String _username );
//    public boolean selectWhereUsrPass(String _username ,String _passwd );
//    public playerinfo.Player selectPlayerWhereUsrPass(String _username ,String _passwd );
//    public String selectMailWhereUsr(String _username );
//    public Long selectScoreWhereUsrPass(String _username ,String _passwd );
//    public String selectStatusWhereUsrPass(String _username ,String _passwd );
//    public Vector<playerinfo.Player> selectAllWhereStatus(String _status );
//    public Vector<playerinfo.Player> selectAllPlayers();
//    public Vector<playerinfo.Player> selectAllPlayersOrderByDESC(String colName );
//    public Vector<playerinfo.Player> selectAllPlayersOrderByASC(String colName );