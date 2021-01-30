package clientHandler;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;

public class Player {
    
    private int id;
    private String username;
    private String password;
    private String status;
    private int score;
    private static boolean invited = false;
    private String opponent;
    
    public void Player(){}
    
    public void Player(int id, String username, String password, String status, int score){
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.score = score;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public void setInvited(boolean invited){
        Player.invited = invited;
    }
    
    public void setOpponent(String opponent){
        this.opponent = opponent;
    }
    
    public String getOpponent(){
        return opponent;
    }
    
    public boolean getInvited(){
        return invited;
    }
    
    public int getId(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getStatus(){
        return status;
    }
    
    public int getScore(){
        return score;
    }
    
    public boolean checkUsername(String username){
        boolean check = true;
        if(username.equals("")){
            check = false;
        }
        else if(username.length() > 20){
            check = false;
        }
        else{
            check = Pattern.matches("^[a-zA-Z1-9-_]*$", username);
        }
        
        return check;
    }
    
    public boolean checkPassword(String password){
        boolean check = true;
        if(password.equals("")){
            check = false;
        }
        else if(password.length() < 6 || password.length() > 20){
            check = false;
        }
        
        return check;
    }
    
    public void updateStatus(String status){
        JSONObject newstatus = new JSONObject();
        newstatus.put("type", "updateStatus");
        newstatus.put("status", status);
        ClientHandler.sendRequest(newstatus);
        this.status = status;
    }
    
    public void updateScore(String newScore){
        JSONObject newscore = new JSONObject();
        newscore.put("type", "updateScore");
        newscore.put("score", newScore);
        ClientHandler.sendRequest(newscore);
    }
}
