/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.playerinfo.PlayerModel;

/**
 *
 * @author ahmed
 */
public class TestPlayerModel {
    public static void main(String[] args) {
       PlayerModel p = new PlayerModel() ;
      // p.insertRecord("ahmedd", "456","ah@test","none" ,20,null) ;
       //p.updateIdRecord(2,"test", "4556","testname@test","ingame" ,200,null) ;
       //p.deleteIdRecord(2) ;
       Long l = p.selectScoreWhereUsr("ahmed");
       
    }

    
}
