/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import gameinfo.Game;
import gameinfo.Game.cellType;
import gameinfo.GameModel;
import java.util.Vector;
import playerinfo.Player;

/**
 *
 * @author ahmed
 */
public class TestPlayerModel {
    public static void main(String[] args) {
       //Player p = new Player() ;
      //Player.add("ahmedd", "456","ah@test","none" ,20,null) ;
       //Player.update(2,"test", "4556","testname@test","ingame" ,200,null) ;
       //Player.delete(2)
       //Long l = Player.getScore("ahmed");
       Vector<Player> pList = Player.getAll() ;
       if(Player.check("user1", "123"))
           System.out.println("exist");
       Player user = Player.get("user1", "123") ;
       System.out.println(user.getStatus().toString());
       //Vector<Player> pList = p.selectAllWhereStatus("offline") ;
       for(int i=0 ;i<pList.size();i++ ){
           System.out.println(pList.get(i).getUsername());
       }
       
       // game 
       cellType[] _board = new cellType[9] ;
       for (int i= 0; i<_board.length ;i++)
           _board[i]= cellType.EMPTY ;
       
       //Game.add(Game.cellType.X, _board , (long)2,(long)3);
       //Game.update((long)1, cellType.O, _board, (long)1, (long)2);
       Game.update(cellType.EMPTY, _board, Player.getPid("user1") , Player.getPid("user2"));
       System.out.println(Player.getPid("user1"));
       
    }

    
}
