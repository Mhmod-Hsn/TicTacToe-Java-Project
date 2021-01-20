/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author ahmed
 */
public class Game {
    public  static  enum cellType { X("X"), O("O"), EMPTY(" ");  // Assigning a value to each enum
        private final String code;
        cellType(String code){
            this.code = code;
        }
        // Overriding toString() method to return "" instead of "EMPTY"
        @Override
        public String toString(){
            return this.code;
        }
    }
    public  static  enum orderType {ASC,DESC}   // modification 17/1 ingame -> busy
    private static orderType order ;

    public static orderType getOrder() {
        return Game.order ;
    }
    public static void setOrder(orderType _order) {
        Game.order = _order;
    }
    
    
    private Long gid ;
    private String created_at ;   // may be date who knows !!
    private cellType[] board = new cellType[9] ;
    private cellType turn ;
    private Long pid1 ;
    private Long pid2 ; 
    
    public void setBoard(cellType _cell0 ,cellType _cell1,cellType _cell2,cellType _cell3,cellType _cell4,cellType _cell5,cellType _cell6,cellType _cell7,cellType _cell8){
        this.board[0] = _cell0 ;
        this.board[1] = _cell1 ;
        this.board[2] = _cell2 ;
        this.board[3] = _cell3 ;
        this.board[4] = _cell4 ;
        this.board[5] = _cell5 ;
        this.board[6] = _cell6 ;
        this.board[7] = _cell7 ;
        this.board[8] = _cell8 ;
        
    }
    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setBoard(cellType[] board) {
        this.board = board;
    }

    public void setTurn(cellType turn) {
        this.turn = turn;
    }

    public void setPid1(Long pid1) {
        this.pid1 = pid1;
    }

    public void setPid2(Long pid2) {
        this.pid2 = pid2;
    }

    public Long getGid() {
        return gid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public cellType[] getBoard() {
        return board;
    }

    public cellType getTurn() {
        return turn;
    }

    public Long getPid1() {
        return pid1;
    }

    public Long getPid2() {
        return pid2;
    }

    public Game(Long gid, String created_at, cellType turn, Long pid1, Long pid2) {
        this.gid = gid;
        this.created_at = created_at;
        this.turn = turn;
        this.pid1 = pid1;
        this.pid2 = pid2;
    }
    // static methods 
    public static Game createGame(ResultSet _rs){
        Game g ;
        try {
          g = new Game(_rs.getLong("gid"),_rs.getString("created_at"), Game.cellType.valueOf( _rs.getString("turn")),_rs.getLong("player1") ,_rs.getLong("player2"));
            g.setBoard(Game.cellType.valueOf( _rs.getString("cell0")),Game.cellType.valueOf( _rs.getString("cell1")),Game.cellType.valueOf( _rs.getString("cell2")),Game.cellType.valueOf( _rs.getString("cell3")),Game.cellType.valueOf( _rs.getString("cell4")), Game.cellType.valueOf( _rs.getString("cell5")),Game.cellType.valueOf( _rs.getString("cell6")),Game.cellType.valueOf( _rs.getString("cell7")),Game.cellType.valueOf( _rs.getString("cell8")));
          System.out.println("creating Game ok ");
        } catch (SQLException ex) {
            System.out.println("error creating Game");
            return null ;
        }
        return g;
    }
    
    public static boolean add(cellType _turn , cellType[] _board , Long _player1  , Long _player2 ){
        return GameModel.insertRecord(_turn, _board, _player1, _player2 ) ;
    }
    public static boolean update(Long _gid , cellType _turn , cellType[] _board , Long _player1  , Long _player2 ){
        return GameModel.updateIdRecord(_gid ,_turn, _board, _player1, _player2 ) ;
    }
    public static boolean update(cellType _turn , cellType[] _board , Long _player1  , Long _player2 ){
        return GameModel.updateBoardWhereP1P2(_turn, _board, _player1, _player2);
    }
    public static boolean update(cellType _turn , cellType[] _board , Long _player1  , Long _player2 , String _created_at ){
        return GameModel.updateBoardWhereP1P2Date(_turn, _board, _player1, _player2 , _created_at);
    }
    
    public static boolean deleteId(long _gid ){
        return GameModel.deleteIdRecord(_gid);
    }
    public static boolean deleteP1(long _player1 ){
        return GameModel.deleteP1Record(_player1) ;
    }
    public static boolean deleteP2(long _player2 ){
        return GameModel.deleteP2Record(_player2);
    }
    public static boolean delete(long _player1, long _player2 ){
        return GameModel.deleteP1P2Record(_player1, _player2);
    }
    public static boolean delete(long _player1, long _player2 ,String _created_at){
        return GameModel.deleteP1P2Record(_player1, _player2, _created_at);
    }
    
    public static Game get(long _gid){
       return GameModel.selectGameWhereId (_gid ) ; 
    }
    public static Game get(long _pid1 , long _pid2 , String _created_at){
       return GameModel.selectGameWhereP1P2Date(_pid1, _pid2, _created_at); 
    }
    public static Vector<Game> get(long _pid1 , long _pid2){
       return GameModel.selectAllWhereP1P2(_pid1, _pid2) ;
    }
    public static Vector<Game> getAll(){
       return GameModel.selectAllGames() ;
    }
   public static Vector<Game> getAllOrdered(String colName , orderType _order ){
        if (_order == orderType.ASC)
            return GameModel.selectAllGamesOrderByASC(colName);
        else
            return GameModel.selectAllGamesOrderByDESC(colName);             
     }    
    public static Vector<Game> getAllOrderedDesc(String colName ){
        return GameModel.selectAllGamesOrderByDESC(colName);     
    }
    public static Vector<Game> getAllOrderedAsc(String colName ){
        return GameModel.selectAllGamesOrderByASC(colName);
    }
}



// ----------------- Game ProtoTypes ----------------------------------------
//  static boolean insertRecord(cellType _turn , cellType[] _board , Long _player1  , Long _player2 )
//  static boolean updateIdRecord(Long _gid ,cellType _turn , cellType[] _board , Long _player1  , Long _player2 )
//  static boolean updateBoardWhereP1P2(_turn, _board, _player1, _player2)
//  static boolean updateBoardWhereP1P2Date( cellType _turn , cellType[] _board , Long _player1  , Long _player2 ,String _created_at )

//  static boolean deleteIdRecord(long _gid){
//  static boolean deleteP1Record(long _player1){
//  static boolean deleteP2Record(long _player2){
//  static boolean deleteP1P2Record(long _player1 ,long _player2){
//  static boolean deleteP1P2Record(long _player1 ,long _player2, String  _created_at)


//  static Game selectGameWhereId (Long _gid )   
//  static Game selectGameWhereP1P2Date (Long _player1 , Long _player2 ,String _created_at)
//  static Vector<Game> selectAllWhereP1P2(Long _player1 , Long _player2 )
//  static Vector<Game> selectAllGames(){   
//  static Vector<Game> selectAllPlayersOrderByDESC(String colName )
//  static Vector<Game> selectAllPlayersOrderByASC(String colName )
