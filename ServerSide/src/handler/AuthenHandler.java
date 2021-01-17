/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

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


public class AuthenHandler extends Thread {
    
    Socket socket;
    UserRequestInfo userReqInfo;
    
    DataInputStream inputStream;
    DataOutputStream outputStream;
    
    boolean isRequestValid = false;
    
    public AuthenHandler(Socket socket)
    {
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
                        PlayerInfo result = signIn(userReqInfo.getUname(),userReqInfo.getPassword());
                    }
                    
                    else//Sign up request
                    {
                        boolean result = signUp(userReqInfo.getUname(),userReqInfo.getPassword());
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
            userReqInfo.setUname((String)json.get("uname"));
           
            
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    
    private PlayerInfo signIn(String uname, String password)
    {
        PlayerInfo result = new PlayerInfo();
        System.out.println("sign in function");   
        return result;
    }
    
    private boolean signUp (String uname, String password)
    {
        
        boolean result = false;
        System.out.println("sign up function");
        return result;
    }
    
    public void close()
    {
        try {
            socket.close();
            this.stop();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
