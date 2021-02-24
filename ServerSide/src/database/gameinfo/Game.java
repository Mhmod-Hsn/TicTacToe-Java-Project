/*
*   The class that represents a game
*/

package database.gameinfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import database.playerinfo.PlayerModel;

/**
 *
 * @author Ghada Ragab
 */

public class Game {

    // Assigning a value to each enum
    public  static  enum cellType { X("X"), O("O"), EMPTY(" ");  

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
    
    public  static  enum orderType {ASC,DESC}
    
    private static orderType order ;

    public static orderType getOrder() {
        return Game.order ;
    }
    public static void setOrder(orderType _order) {
        Game.order = _order;
    }
    
    
    private Long gameId ;
    private String created_at ; 
    private cellType[] board = new cellType[9] ;
    private cellType turn ;
    private Long pid1 ;
    private Long pid2 ; 
    private String xPlayerUsername ;
    private String oPlayerUsername ;

    public void setXPlayerUsername(String xPlayerUsername) {
        this.xPlayerUsername = xPlayerUsername;
    }

    public void setOPlayerUsername(String oPlayerUserName) {
        this.oPlayerUsername = oPlayerUserName;
    }

    public String getXPlayerUsername() {
        return xPlayerUsername;
    }

    public String getOPlayerUsername() {
        return oPlayerUsername;
    }

    public Game(Long gameId, String _xPlayerUserName, String _oPlayerUserName) {
        this.gameId = gameId;
        
        this.xPlayerUsername = _xPlayerUserName ;
        this.oPlayerUsername = _oPlayerUserName;
        this.pid1 = PlayerModel.selectIdWhereUsr(_xPlayerUserName);
        this.pid2 = PlayerModel.selectIdWhereUsr(_oPlayerUserName);
    }
    
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
    
    public void setgameId(Long gameId) {
        this.gameId = gameId;
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

    public Long getGameId() {
        return gameId;
    }

    public String getSaveDate() {
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

    public Game(Long gid, String created_at, String _turn, Long pid1, Long pid2) {
    
        this.gameId = gid;
        this.created_at = created_at;
        this.turn = setCellTypeStr(_turn); 
        this.pid1 = pid1;
        this.pid2 = pid2;
        this.xPlayerUsername = PlayerModel.selectUserWhereId(pid1);
        this.oPlayerUsername = PlayerModel.selectUserWhereId(pid2);
    }
    
    public cellType setCellTypeStr(String val){
        
        if(val.equalsIgnoreCase("X"))
            return Game.cellType.X ;
        else if (val.equalsIgnoreCase("O"))
            return Game.cellType.O;
        else
            return Game.cellType.EMPTY; 
    }
    
    public String[] getBoardStr() {
        
        String[] _board = new String[9];
        
        for (int i= 0; i<board.length ;i++){
           _board[i]= this.board[i].toString();
        }
        
        return _board;
    }

    public void setBoard(String _cell0 ,String _cell1,String _cell2,String _cell3,String _cell4,String _cell5,String _cell6,String _cell7,String _cell8){
       
        this.board[0] = setCellTypeStr(_cell0) ;
        this.board[1] = setCellTypeStr(_cell1) ;
        this.board[2] = setCellTypeStr(_cell2) ;
        this.board[3] = setCellTypeStr(_cell3) ;
        this.board[4] = setCellTypeStr(_cell4) ;
        this.board[5] = setCellTypeStr(_cell5) ;
        this.board[6] = setCellTypeStr(_cell6) ;
        this.board[7] = setCellTypeStr(_cell7) ;
        this.board[8] = setCellTypeStr(_cell8) ;
        
    }
    
    // static methods 
    public static Game createGame(ResultSet _rs){
        Game g ;
        try {
          g = new Game(_rs.getLong("gid"),_rs.getString("created_at"), _rs.getString("turn"),_rs.getLong("player1") ,_rs.getLong("player2"));
          g.setBoard( _rs.getString("cell0"), _rs.getString("cell1"), _rs.getString("cell2"), _rs.getString("cell3"), _rs.getString("cell4"), _rs.getString("cell5"), _rs.getString("cell6"),_rs.getString("cell7"), _rs.getString("cell8") );

        } catch (SQLException ex) {

            return null ;
        }
        return g;
    }

}
