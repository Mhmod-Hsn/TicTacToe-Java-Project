/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appinfo;

import database.playerinfo.Player;
import database.playerinfo.PlayerModel;

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
    
    PlayerModel model = new PlayerModel();
    
    public Player login(String username, String password){
        Player p = model.selectPlayerWhereUsrPass(username, password);
        
        if (p != null) {
            model.updateUsrFieldStatus(username, String.valueOf(Player.statusType.online));
            p.setStatus(Player.statusType.online);
        }
        return p;
    }

    public boolean isUserExists(String username, String password){
        return model.selectWhereUsrPass(username, password);
    }
    
    public boolean isLoggedIn(String username, String password){
        String status = model.selectStatusWhereUsr(username);
        if ( status.equals("online") || status.equals("ingame")) {
            return true;
        }
        return false;
    }
    
    
      
    public Player register(String username, String password){
        Player newPlayer = new Player(username, password, null);
        newPlayer.setScore((long)0);
        newPlayer.setStatus(Player.statusType.online);
        if (model.insertRecord(newPlayer.getUsername() ,newPlayer.getPasswd(),null, String.valueOf(newPlayer.getStatus()), newPlayer.getScore(), null)){
           return newPlayer;
        }
        return null;  
    }
    
}
// sgin //obj boolean    alluser status