/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appinfo;

import playerinfo.DbMethods;
import playerinfo.Player;


/**
 *
 * @author ahmed
 */
public class AppDbOperation {
//    vector user
//    vector game 
//    socket 
//    db
//    sign in
//    sign up
//    getalluser
//    getallgame
    

    
    public Player login(String username, String password){
        Player p = DbMethods.selectPlayerWhereUsrPass(username, password);
        
        if (p != null) {
            DbMethods.updateUsrFieldStatus(username, String.valueOf(Player.statusType.online));
            p.setStatus(Player.statusType.online);
        }
        return p;
    }

    public boolean isUserExists(String username, String password){
        return DbMethods.selectWhereUsrPass(username, password);
    }
    
    public boolean isLoggedIn(String username, String password){
        String status = DbMethods.selectStatusWhereUsr(username);
        if ( status.equals("online") || status.equals("ingame")) {
            return true;
        }
        return false;
    }
    
    
      
    public Player register(String username, String password){
        Player newPlayer = new Player(username, password, null);
        newPlayer.setScore((long)0);
        newPlayer.setStatus(Player.statusType.online);
        if (PlayerModel.insertRecord(newPlayer.getUsername() ,newPlayer.getPasswd(),null, String.valueOf(newPlayer.getStatus()), newPlayer.getScore(), null)){
           return newPlayer;
        }
        return null;  
    }
    
}
// sgin //obj boolean    alluser status