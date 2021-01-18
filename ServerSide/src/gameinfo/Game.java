/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinfo;

import java.util.Date;

/**
 *
 * @author ahmed
 */
public class Game {
    public  static  enum cellType {X,O,none}   // modification 17/1 ingame -> busy
    public  static  enum orderType {ASC,DESC}   // modification 17/1 ingame -> busy
    private static orderType order ;

    public static orderType getOrder() {
        return Game.order ;
    }
    public static void setOrder(orderType _order) {
        Game.order = _order;
    }
    
    
    private Long gid ;
    private String created_at ;
    private cellType[] board = new cellType[6] ;
    private cellType turn ;
    private Long pid1 ;
    private Long pid2 ; 
    
    
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
    
    
}
