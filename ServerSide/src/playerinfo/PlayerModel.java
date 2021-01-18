/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerinfo;

import database.DatabaseDriver;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed
 */
public interface PlayerModel {
    static final DatabaseDriver db = new DatabaseDriver() ;

//    public PlayerModel() {
//        //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/persondb","root","");  //here sonoo is database name, root is username and password   create conn
//   //    db.setStatement(db.getConnection().createStatement()) ;
//        //stmt=con.createStatement();                         // create state 
//   //     db.setQuerystr("querystr");
//   //     db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));
//        //rs=stmt.executeQuery("select * from persontb");  // exe str query 
//        
//    }
    
    static boolean insertRecord(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                
                //int checkNew=stmt.executeUpdate("insert into persontb values( "+idInfo.getText()+","+ "'"+pFnameInfo.getText()+"'"+","+ "'"+pMnameInfo.getText()+"'"+","+ "'"+pLnameInfo.getText()+"'"+","+ "'"+pEmailInfo.getText()+"'"+","+ "'"+pPhoneInfo.getText()+"' )");  
                //INSERT INTO `players`(`pid`, `username`, `passwd`, `email`, `status`, `score`, `avatar`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6],[value-7])
                int checkNew=db.getStatement().executeUpdate("INSERT INTO players ( username, passwd, email, status, score, avatar) VALUES( '"+_username+"', '"+_passwd+"', '"+_email+"', '"+_status+"', "+_score+", '"+_avatar+"' )"); 
                db.endStatConnection();
                if(checkNew >= 1){
                    System.out.println("new ok ");                   
                    return true ;
                    /*TestDB2.this.rs=stmt.executeQuery("select * from persontb");
                    refreshPersonList(rs);                            
                    curIndex = pList.size()-1 ;
                    //displayPerson(pList.get(curIndex)) ; 
                    newFlag = false ;
                    newBtn.setText("Saved");*/
                }
                else{
                    System.out.println("new error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error new");
                return false ;
            }
    } 
    // update DML  update record [ id or user or user,pass]  and Field score or status [ user or user,pass ]
    static boolean updateIdRecord(long _pid ,String _username , String _passwd , String _email  , String _status ,long _score , String _avatar){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                //UPDATE `players` SET `pid`=[value-1],`username`=[value-2],`passwd`=[value-3],`email`=[value-4],`status`=[value-5],`score`=[value-6],`avatar`=[value-7] WHERE 1

                int checkUpdate=db.getStatement().executeUpdate("UPDATE players SET username= '"+_username+"' , passwd= '"+_passwd+"' , email= '"+_email+"' , status='"+_status+"' , score= "+_score+" ,avatar= '"+_avatar+"' WHERE pid = "+_pid); 
                db.endStatConnection();
                if(checkUpdate >= 1){
                    System.out.println("update ok ");                
                    return true ;
                }
                else{
                    System.out.println("update error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    } 
    
    static boolean updateUsrRecord(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                //UPDATE `players` SET `pid`=[value-1],`username`=[value-2],`passwd`=[value-3],`email`=[value-4],`status`=[value-5],`score`=[value-6],`avatar`=[value-7] WHERE 1

                int checkUpdate=db.getStatement().executeUpdate("UPDATE players SET passwd= '"+_passwd+"' , email= '"+_email+"' , status='"+_status+"' , score= "+_score+" ,avatar= '"+_avatar+"' WHERE username = '"+_username+"'"); 
                db.endStatConnection();
                if(checkUpdate >= 1){
                    System.out.println("update ok ");                
                    return true ;
                }
                else{
                    System.out.println("update error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    } 
    
    static boolean updateUsrFieldStatus(String _username , String _status ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                //UPDATE `players` SET `pid`=[value-1],`username`=[value-2],`passwd`=[value-3],`email`=[value-4],`status`=[value-5],`score`=[value-6],`avatar`=[value-7] WHERE 1

                int checkUpdate=db.getStatement().executeUpdate("UPDATE players SET status='"+_status+"' WHERE username = '"+_username+"'"); 
                db.endStatConnection();
                if(checkUpdate >= 1){
                    System.out.println("update ok ");                
                    return true ;
                }
                else{
                    System.out.println("update error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    } 
    
    static boolean updateUsrFieldScore(String _username , long _score ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                //UPDATE `players` SET `pid`=[value-1],`username`=[value-2],`passwd`=[value-3],`email`=[value-4],`status`=[value-5],`score`=[value-6],`avatar`=[value-7] WHERE 1

                int checkUpdate=db.getStatement().executeUpdate("UPDATE players SET score="+_score+" WHERE username = '"+_username+"'"); 
                db.endStatConnection();
                if(checkUpdate >= 1){
                    System.out.println("update ok ");                
                    return true ;
                }
                else{
                    System.out.println("update error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    } 
    
    static boolean updateUsrPassRecord(String _username , String _passwd , String _email  , String _status ,long _score , String _avatar){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                //UPDATE `players` SET `pid`=[value-1],`username`=[value-2],`passwd`=[value-3],`email`=[value-4],`status`=[value-5],`score`=[value-6],`avatar`=[value-7] WHERE 1

                int checkUpdate=db.getStatement().executeUpdate("UPDATE players SET email= '"+_email+"' , status='"+_status+"' , score= "+_score+" ,avatar= '"+_avatar+"' WHERE username = '"+_username+"' and passwd= '"+_passwd+"'"); 
                db.endStatConnection();
                if(checkUpdate >= 1){
                    System.out.println("update ok ");                
                    return true ;
                }
                else{
                    System.out.println("update error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    }
    static boolean updateUsrPassFieldStatus(String _username , String _passwd , String _status ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                //UPDATE `players` SET `pid`=[value-1],`username`=[value-2],`passwd`=[value-3],`email`=[value-4],`status`=[value-5],`score`=[value-6],`avatar`=[value-7] WHERE 1

                int checkUpdate=db.getStatement().executeUpdate("UPDATE players SET status='"+_status+"' WHERE username = '"+_username+"' and passwd= '"+_passwd+"'"); 
                db.endStatConnection();
                if(checkUpdate >= 1){
                    System.out.println("update ok ");                
                    return true ;
                }
                else{
                    System.out.println("update error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    }
    static boolean updateUsrPassFieldScore(String _username , String _passwd ,long _score ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                //UPDATE `players` SET `pid`=[value-1],`username`=[value-2],`passwd`=[value-3],`email`=[value-4],`status`=[value-5],`score`=[value-6],`avatar`=[value-7] WHERE 1

                int checkUpdate=db.getStatement().executeUpdate("UPDATE players SET score='"+_score+"' WHERE username = '"+_username+"' and passwd= '"+_passwd+"'"); 
                db.endStatConnection();
                if(checkUpdate >= 1){
                    System.out.println("update ok ");                
                    return true ;
                }
                else{
                    System.out.println("update error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    }
    // delete DML  with id or username or mail or user,pass or mail,pass
    static boolean deleteIdRecord(long _pid){
         try {
                //TestDB2.this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/persondb","root",""); 
                db.startConnection();
                //stmt=con.createStatement(); 
                db.setStatement(db.getConnection().createStatement()) ;
                
                //int checkNew=stmt.executeUpdate("insert into persontb values( "+idInfo.getText()+","+ "'"+pFnameInfo.getText()+"'"+","+ "'"+pMnameInfo.getText()+"'"+","+ "'"+pLnameInfo.getText()+"'"+","+ "'"+pEmailInfo.getText()+"'"+","+ "'"+pPhoneInfo.getText()+"' )");  
                //INSERT INTO `players`(`pid`, `username`, `passwd`, `email`, `status`, `score`, `avatar`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6],[value-7])
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM players WHERE pid="+_pid); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                    /*TestDB2.this.rs=stmt.executeQuery("select * from persontb");
                    refreshPersonList(rs);                            
                    curIndex = pList.size()-1 ;
                    //displayPerson(pList.get(curIndex)) ; 
                    newFlag = false ;
                    newBtn.setText("Saved");*/
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    } 
    
    static boolean deleteUsrRecord(String _username){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                // (`pid`, `username`, `passwd`, `email`, `status`, `score`, `avatar`)
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM players WHERE username= '"+_username+"'"); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    } 
    
    static boolean deleteMailRecord(String _email){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                // (`pid`, `username`, `passwd`, `email`, `status`, `score`, `avatar`)
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM players WHERE email= '"+_email+"'"); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    } 
    
    static boolean deleteMailPassRecord(String _email, String _passwd){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                // (`pid`, `username`, `passwd`, `email`, `status`, `score`, `avatar`)
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM players WHERE email= '"+_email+"' and passwd= '"+_passwd+"'"); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    } 
    static boolean deleteUsrPassRecord(String _username, String _passwd){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                // (`pid`, `username`, `passwd`, `email`, `status`, `score`, `avatar`)
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM players WHERE email= '"+_username+"' and passwd= '"+_passwd+"'"); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    } 
    
    
    // DML Select
    static Long selectScoreWhereUsr(String _username ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select score from players where username= '"+_username+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    Long tmpScore = db.getResultSet().getLong("score") ;
                    System.out.println(tmpScore);
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpScore ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    }
    
    static String selectStatusWhereUsr(String _username ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select status from players where username= '"+_username+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    String tmpStatus = db.getResultSet().getString("status") ;
                    System.out.println(tmpStatus);
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpStatus ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    }
    static String selectPassWhereUsr(String _username ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select passwd from players where username= '"+_username+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    String tmpPasswd = db.getResultSet().getString("passwd") ;
                    System.out.println(tmpPasswd);
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpPasswd ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    }
    // check 
     static boolean selectWhereUsrPass(String _username ,String _passwd ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from players where username= '"+_username+"' and passwd = '"+_passwd+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return false ;
                }
                else{
                    System.out.println("true select");
                    db.endResultSet();
                    db.endStatConnection();
                    return true ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return false ;
            }
    }
    
     static playerinfo.Player selectPlayerWhereUsrPass(String _username ,String _passwd ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from players where username= '"+_username+"' and passwd = '"+_passwd+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    playerinfo.Player p = playerinfo.Player.createPlayer(db.getResultSet()) ;
                    System.out.println("true select");
                    db.endResultSet();
                    db.endStatConnection();
                    return p ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    } 
     
    static String selectMailWhereUsr(String _username ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select email from players where username= '"+_username+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    String tmpEmail = db.getResultSet().getString("email") ;
                    System.out.println(tmpEmail);
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpEmail ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    }
    
     static Long selectScoreWhereUsrPass(String _username ,String _passwd ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select score from players where username= '"+_username+"' and passwd='"+_passwd+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    Long tmpScore = db.getResultSet().getLong("score") ;
                    System.out.println(tmpScore);
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpScore ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    }
     
      static String selectStatusWhereUsrPass(String _username ,String _passwd ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select status from players where username= '"+_username+"' and passwd='"+_passwd+"'");
  
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    String tmpStatus = db.getResultSet().getString("status") ;
                    System.out.println(tmpStatus);
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpStatus ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
     // not tested to be continued ..... abd change above avatar with correct DT
      
      
    static Vector<playerinfo.Player> selectAllWhereStatus(String _status ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from players where status= '"+_status+"'");
  
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  
                
                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    
                    Vector<playerinfo.Player>  tmpUsrs =  new Vector<playerinfo.Player>(); 
                    tmpUsrs.add(playerinfo.Player.createPlayer(db.getResultSet()));
                    while(db.getResultSet().next()){
                        tmpUsrs.add(playerinfo.Player.createPlayer(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpUsrs ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
      // check 
     static Vector<playerinfo.Player> selectAllPlayers(){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from players ");
  
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  
                
                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().isBeforeFirst()== false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    //db.getResultSet().first() ;
                    Vector<playerinfo.Player>  tmpUsrs = new Vector<playerinfo.Player>(); 
                    while(db.getResultSet().next()){
                        tmpUsrs.add(playerinfo.Player.createPlayer(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpUsrs ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
     
     static Vector<playerinfo.Player> selectAllPlayersOrderByDESC(String colName ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from players ORDER BY "+colName+" DESC ");
  
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  
                
                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().isBeforeFirst()== false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    //db.getResultSet().first() ;
                    Vector<playerinfo.Player>  tmpUsrs = new Vector<playerinfo.Player>(); 
                    while(db.getResultSet().next()){
                        tmpUsrs.add(playerinfo.Player.createPlayer(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpUsrs ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
     
     static Vector<playerinfo.Player> selectAllPlayersOrderByASC(String colName ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from players ORDER BY "+colName+" ASC ");
  
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  
                
                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().isBeforeFirst()== false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    //db.getResultSet().first() ;
                    Vector<playerinfo.Player>  tmpUsrs = new Vector<playerinfo.Player>(); 
                    while(db.getResultSet().next()){
                        tmpUsrs.add(playerinfo.Player.createPlayer(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpUsrs ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
}