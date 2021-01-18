/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import appinfo.AppDbOperation;
import database.playerinfo.Player;
import java.io.IOException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.Socket;

import server.utils.Errors;
import server.utils.Requests;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Hossam
 */

public class AuthenHandler extends Thread {
    
    Socket socket;
    UserRequestInfo userReqInfo;
    AppDbOperation dbOperation;
    String errorMsg;
    JSONObject jsonObj;
    Player curPlayer;
    
    DataInputStream inputStream;
    DataOutputStream outputStream;
    
    boolean isRequestValid = false;
    
    public AuthenHandler(Socket socket)
    {
        this.dbOperation = new AppDbOperation();
        this.socket = socket;
        try {
            //Create the input and output channels
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            
            
            //create object of the request
            userReqInfo = new UserRequestInfo();
            
            
            //Start the thread
            this.start();
            
        } catch (IOException ex) {
            System.out.println("AuthenticationHandler input and output constructor catch");
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        
        while (true)
        {
            try {
                
                isRequestValid = parseToUserReq(inputStream.readUTF());
                
                if (isRequestValid) //valid signin/signup request
                {
                    if (userReqInfo.isSignIn()) //Sign in request
                    {
                        Player result = signIn(userReqInfo.getUname(),userReqInfo.getPassword());
                        
                        if (userRequestResponse(result, Requests.SIGN_IN)) {
                            //new PlayerHandler(socket, result);
                            System.out.println("Client has signed up");
                            this.close();
                        }else
                            System.out.println("Sign in rejected " + errorMsg);
                        
                    }
                    
                    else//Sign up request
                    {
                        Player result = signUp(userReqInfo.getUname(),userReqInfo.getPassword());
                        
                        if (userRequestResponse(result, Requests.SIGN_UP)) {
                            //new PlayerHandler(socket, result);
                            System.out.println("Client has signed up");
                            this.close();
                        }else
                            System.out.println("Sign up rejected " + errorMsg);
                        
                    }
                    
                }
                else //invalid request  
                {   
                    //respond with error message (invalid) to the type (unknown)
                    outputStream.writeUTF(errorToJson(Requests.UNKNOWN,Errors.INVALID).toString());
                }
                
                //Connection Drop
            } catch (IOException ex) { 
                System.out.println("Dropped client");
                //close this socket and end this thread
                close();
                
            } 
        }
    }
    
    private JSONObject errorToJson(String type, String errorMsg)
    {
        //Construct the json object
        JSONObject json = new JSONObject();
        
        //add the relevent fields
        json.put("type",type);
        json.put("responseStatus",false);
        json.put("errorMsg",errorMsg);
        
        return json;
    }
    
    private boolean parseToUserReq(String jsonStr)
    {
        JSONParser parser = new JSONParser();
        
        try {
            JSONObject json = (JSONObject)parser.parse(jsonStr);
            
            //Signin request
            if (Requests.SIGN_IN.equals((String)json.get("type")))
            {
                userReqInfo.setIsSignIn((true));
            }
            //signup request
            else if (Requests.SIGN_UP.equals((String)json.get("type")))
            {
                userReqInfo.setIsSignIn((false));
            }
            else //Unknown request
            {
                return false;
            }
            
            userReqInfo.setPassword((String)json.get("password"));
            userReqInfo.setUname((String)json.get("username"));
            
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    
    private JSONObject playerToJson(Player player)
    {   
        JSONObject json = new JSONObject();
        json.put("id", player.getPid());
        json.put("username", player.getUsername());
        json.put("status", player.getStatus().toString());
        json.put("score", player.getScore());
        json.put("avatar", player.getAvatar());
        
        return json;
    }
    
    private boolean userRequestResponse(Player player, String requestType) throws IOException{
        if (player != null) {
            jsonObj =  playerToJson(player);
            jsonObj.put("type", requestType);
            jsonObj.put("responseStatus", true);
            outputStream.writeUTF(jsonObj.toString());
            
            return true;
        }
        else{
            outputStream.writeUTF(errorToJson(requestType, errorMsg).toString());
            
            return false;
        }

    }
     

    private Player signIn(String uname, String password)
    {
        if (dbOperation.isUserExists(uname, password)) {
            if (dbOperation.isLoggedIn(uname, password)) {
                errorMsg = Errors.SIGNNED_IN;
                System.out.println("already logged in");
            }else{
               return dbOperation.login(uname, password);
            }
        }else{
            errorMsg = Errors.NOT_EXIST;
            System.out.println("Not Exist");
        }
        return null;
    }
    
    private Player signUp (String uname, String password)
    {
        curPlayer = dbOperation.register(uname, password);
        if (curPlayer == null)
            errorMsg = Errors.EXISTS;
        return curPlayer;
    }
    
 
    
    public void close()
    {
        try {
            socket.close();
            this.stop();
            System.out.println("Authentication handler closed");

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Authentication handler exception");
        }
    }
}



class UserRequestInfo {
    
    private String uname;
    private String password;
    private boolean isSignIn;
    

    
    public UserRequestInfo(String uname, String password, boolean isSignIn) {
        this.password = password;
        this.uname = uname;
        this.isSignIn = isSignIn;
    }

    public UserRequestInfo() {
        this.password = null;
        this.uname = null;
        this.isSignIn = false;
    }
        
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(boolean isSignIn) {
        this.isSignIn = isSignIn;
    }
}
