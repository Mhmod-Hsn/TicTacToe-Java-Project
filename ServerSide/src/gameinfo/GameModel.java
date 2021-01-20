/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinfo;

import database.DatabaseDriver;
import gameinfo.Game.cellType;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed
 */
public interface  GameModel {
    static final DatabaseDriver db = new DatabaseDriver() ;
 // ( `gid` SERIAL NOT NULL , `created_at` INT NOT NULL DEFAULT CURRENT_TIMESTAMP , `turn` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell0` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell1` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell2` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell3` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell4` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell5` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `player1` BIGINT UNSIGNED NOT NULL , `player2` BIGINT UNSIGNED NOT NULL , PRIMARY KEY (`gid`), INDEX (`player1`), INDEX (`player2`)) ENGINE = InnoDB;
  //  ( `gid` SERIAL NOT NULL , `created_at` INT NOT NULL DEFAULT CURRENT_TIMESTAMP , `turn` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell0` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell1` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell2` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell3` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell4` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `cell5` ENUM('X','O','none') NOT NULL DEFAULT 'none' , `player1` BIGINT UNSIGNED NOT NULL , `player2` BIGINT UNSIGNED NOT NULL 
    static boolean insertRecord(cellType _turn , cellType[] _board , Long _player1  , Long _player2 ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                if(_board.length != 9){
                     System.out.println("new array error");
                    db.endStatConnection();
                    return false ;
                }
                int checkNew=db.getStatement().executeUpdate("INSERT INTO games ( turn, cell0, cell1, cell2 , cell3 , cell4 ,cell5 ,cell6,cell7,cell8, player1 , player2 ) VALUES( '"+_turn+"', '"+_board[0]+"', '"+_board[1]+"', '"+_board[2]+"', '"+_board[3]+"', '"+_board[4]+"', '"+_board[5]+"', '"+_board[6]+"', '"+_board[7]+"', '"+_board[8]+"', "+_player1+", "+_player2+" )"); 
                db.endStatConnection();
                if(checkNew >= 1){
                    System.out.println("new ok ");                   
                    return true ;
                }
                else{
                    System.out.println("new error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                //Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error new");
                return false ;
            }
    } 
    // update DML  update record [ id or user or user,pass]  and Field score or status [ user or user,pass ]
   static boolean updateIdRecord(Long _gid ,cellType _turn , cellType[] _board , Long _player1  , Long _player2 ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                if(_board.length != 9){
                    System.out.println("update array error");
                    db.endStatConnection();
                    return false ;
                }
                int checkUpdate=db.getStatement().executeUpdate("UPDATE games SET turn= '"+_turn+"' , cell0= '"+_board[0]+"' , cell1= '"+_board[1]+"' , cell2= '"+_board[2]+"' , cell3= '"+_board[3]+"' , cell4= '"+_board[4]+"' , cell5= '"+_board[5]+"' , cell6= '"+_board[6]+"' , cell7= '"+_board[7]+"' , cell8= '"+_board[8]+"' , player1= "+_player1+" , player2= "+_player2+" WHERE gid = "+_gid); 
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
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    }
   
    
    static boolean updateBoardWhereP1P2( cellType _turn , cellType[] _board , Long _player1  , Long _player2 ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                if(_board.length != 9){
                    System.out.println("update array error");
                    db.endStatConnection();
                    return false ;
                }
                int checkUpdate=db.getStatement().executeUpdate("UPDATE games SET turn= '"+_turn+"' , cell0= '"+_board[0]+"' , cell1= '"+_board[1]+"' , cell2= '"+_board[2]+"' , cell3= '"+_board[3]+"' , cell4= '"+_board[4]+"' , cell5= '"+_board[5]+"' , cell6= '"+_board[6]+"' , cell7= '"+_board[7]+"' , cell8= '"+_board[8]+"'  WHERE player1="+_player1+" and player2= "+_player2); 
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
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    } 
     static boolean updateBoardWhereP1P2Date( cellType _turn , cellType[] _board , Long _player1  , Long _player2 ,String _created_at ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ; 
                if(_board.length != 9){
                    System.out.println("update array error");
                    db.endStatConnection();
                    return false ;
                }
                int checkUpdate=db.getStatement().executeUpdate("UPDATE games SET turn= '"+_turn+"' , cell0= '"+_board[0]+"' , cell1= '"+_board[1]+"' , cell2= '"+_board[2]+"' , cell3= '"+_board[3]+"' , cell4= '"+_board[4]+"' , cell5= '"+_board[5]+"' , cell6= '"+_board[6]+"' , cell7= '"+_board[7]+"' , cell8= '"+_board[8]+"'  WHERE player1="+_player1+" and player2= "+_player2+" and created_at= '"+_created_at+"'"); 
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
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error update");
                return false ;
            }
    } 
    
 
    // delete DML  with id or username or mail or user,pass or mail,pass
    static boolean deleteIdRecord(long _gid){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM games WHERE gid="+_gid); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    } 
    static boolean deleteP1Record(long _player1){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM games WHERE player1="+_player1); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    }
     static boolean deleteP2Record(long _player2){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM games WHERE player2="+_player2); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    }
     static boolean deleteP1P2Record(long _player1 ,long _player2){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM games WHERE player2="+_player2+" and player1="+_player1); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    }
     
       static boolean deleteP1P2Record(long _player1 ,long _player2, String  _created_at){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                
                int checkDelete=db.getStatement().executeUpdate("DELETE FROM games WHERE player2="+_player2+" and player1="+_player1+" and created_at= '"+_created_at+"'"); 
                db.endStatConnection();  // for statment with no resultset
                if(checkDelete >= 1){
                    System.out.println("del ok ");                   
                    return true ;
                }
                else{
                    System.out.println("del error");
                    return false ;
                }
                //db.endStatConnection();
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error del");
                return false ;
            }
    }
   static Game selectGameWhereId (Long _gid ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from games where gid= "+_gid);
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    Game g = Game.createGame(db.getResultSet()) ;
                    System.out.println("true select");
                    db.endResultSet();
                    db.endStatConnection();
                    return g ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    } 
    
    static Game selectGameWhereP1P2Date (Long _player1 , Long _player2 ,String _created_at){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from games where player1= "+_player1+" and player2="+_player2+" and created_at= '"+_created_at+"'");
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  

                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    Game g = Game.createGame(db.getResultSet()) ;
                    System.out.println("true select");
                    db.endResultSet();
                    db.endStatConnection();
                    return g ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
    }
  
   
    
      
     
  static Vector<Game> selectAllWhereP1P2(Long _player1 , Long _player2 ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from games where player1= "+_player1+" and player2="+_player2);
  
                db.setResultSet(db.getStatement().executeQuery(db.getQuerystr()));  
                
                //boolean checkFirst = TestDB2.this.rs.first() ;
                if(db.getResultSet().next() == false){
                    db.endResultSet();
                    db.endStatConnection();
                    System.err.println("false select");
                    return null ;
                }
                else{
                    
                    Vector<Game>  tmpGames =  new Vector<Game>(); 
                    tmpGames.add(Game.createGame(db.getResultSet()));
                    while(db.getResultSet().next()){
                        tmpGames.add(Game.createGame(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpGames ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
      // check 
     static Vector<Game> selectAllGames(){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from games ");
  
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
                    Vector<Game>  tmpGames = new Vector<Game>(); 
                    while(db.getResultSet().next()){
                        tmpGames.add(Game.createGame(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpGames ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
     
     static Vector<Game> selectAllGamesOrderByDESC(String colName ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from games ORDER BY "+colName+" DESC ");
  
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
                    Vector<Game>  tmpGames = new Vector<Game>(); 
                    while(db.getResultSet().next()){
                        tmpGames.add(Game.createGame(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpGames ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
     static Vector<Game> selectAllGamesOrderByASC(String colName ){
         try {
                db.startConnection();
                db.setStatement(db.getConnection().createStatement()) ;
                db.setQuerystr("select * from games ORDER BY "+colName+" ASC ");
  
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
                    Vector<Game>  tmpGames = new Vector<Game>(); 
                    while(db.getResultSet().next()){
                        tmpGames.add(Game.createGame(db.getResultSet()));
                    }
                    System.out.println("true Array");
                    db.endResultSet();
                    db.endStatConnection();
                    return tmpGames ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("error select");
                return null ;
            }
      }
}
