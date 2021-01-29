/*
*   The class that represents a player
*/

package database.playerinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Mamdouh Abdel-Wahab
 */


public class Player {
    
    public  static  enum statusType {offline,online,busy,none} 
    public  static  enum orderType {ASC,DESC}  
    private static orderType order ;

    public static orderType getOrder() {
        return Player.order ;
    }

    public static void setOrder(orderType _order) {
        Player.order = _order;
    }
    
    
    private Long pid ;
    private Long score ;
    private String username ;
    private String passwd ;
    private String email ; 
    private statusType status ;
    InputStream avatar ;
    

    public Long getPid() {
        return pid;
    }

    public Long getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getEmail() {
        return email;
    }

    public statusType getStatus() {
        return status;
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public void setPid(Long _pid) {
        this.pid = _pid;
    }

    public void setScore(Long _score) {
        this.score = _score;
    }

    public void setUsername(String _username) {
        this.username = _username;
    }

    public void setPasswd(String _passwd) {
        this.passwd = _passwd;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public void setStatus(statusType _status) {
        this.status = _status;
    }

    public void setAvatar(InputStream avatar) {  
        this.avatar = avatar;
    }
    public void setAvatar(File _avatar) {  
        try {
            this.avatar = new FileInputStream( _avatar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Player(Long score, String username, String passwd, String email, statusType status, FileInputStream avatar) {
        this.score = score;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
        this.status = status;
        this.avatar = avatar;
    }
    
    public Player(String username, String passwd, String email) {
        this.username = username;
        this.passwd = passwd;
        this.email = email;
    }
    
    public Player(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }
    
    public Player(Long pid, String username, String passwd, String email) {
        this.pid = pid;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
    }
    
   
    // public static methods 
    // add 
    public static Player createPlayer(ResultSet _rs) {
        Player p ;
        try {
          p = new Player(_rs.getLong("pid"),_rs.getString("username"),_rs.getString("passwd"),_rs.getString("email"));
          p.setStatus(Player.statusType.valueOf(_rs.getString("status")));
          p.setAvatar((InputStream)null);
          p.setScore(_rs.getLong("score"));

        } catch (SQLException ex) {
 
            return null ;
        }
        return p;
    }
    
}