/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import server.DBOperations;
import playerinfo.Player;
import java.io.IOException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.Socket;

import server.utils.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hossam
 */

public class AuthenHandler extends Thread {
    
    Socket socket;
    String errorMsg;
    JSONObject jsonObj;
    Player curPlayer;
    
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
                //handle user requests
                jsonObj = userRequestHandler(inputStream.readUTF());

                //send response to the user               
                outputStream.writeUTF(jsonObj.toString());
                
                //Connection Drop
            } catch (IOException ex) { 
                System.out.println("Dropped client");
                
                try {
                    //close this socket and end this thread
                    socket.close();
                } catch (IOException ex1) {
                    System.out.println("Couldn't close the socket.");
                    }
                close();
            } catch (ParseException ex) {
                Logger.getLogger(AuthenHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    
    private JSONObject userRequestHandler(String jsonStr) throws ParseException, IOException
    {
        

        JSONObject responseJsonObj = JSONHandeling.parseStringToJson(jsonStr);
        Player playerInfo;

        //findout which request
        String requestType = (String)responseJsonObj.get("type");
        
        switch (requestType)
        {
            //sign in pr sign up request
            case (Requests.SIGN_IN): case(Requests.SIGN_UP):
                
                if (requestType.equals(Requests.SIGN_IN))
                {
                    playerInfo = signIn((String)responseJsonObj.get("username"),(String)responseJsonObj.get("password"));
                }
                else 
                {
                    playerInfo = signUp((String)responseJsonObj.get("username"),(String)responseJsonObj.get("password"));
                }

                //valid sign in
                if (playerInfo != null) 
                {
                    //construct the player info
                    responseJsonObj = JSONHandeling.playerToJson(playerInfo);

                    //add the json response construction
                    responseJsonObj = JSONHandeling.constructJsonResponse(responseJsonObj, requestType);

                    System.out.println("Client has signed in");

                    signInRoutine(playerInfo);
                }
                
                //already signed in or doesn't exist
                else
                {
                    responseJsonObj = JSONHandeling.errorToJson(requestType, errorMsg); 
                    System.out.println(requestType+" rejected: error msg [" + errorMsg+ " ]");
                }
                break;


            //close request
            case (Requests.CLOSE):
                
                //Terminate this thread
                System.out.println("Client closed");
                this.close(); 
                break;

            //unknown request
            default:
                System.out.println("Unkown request");
                responseJsonObj = JSONHandeling.errorToJson(Requests.UNKNOWN, Errors.INVALID); 
                break;
        } 
            //output the result
            return responseJsonObj;
    }
    
    private Player signIn(String uname, String password)
    {
        if (DBOperations.isUserExists(uname, password)) 
        {
            if (DBOperations.isLoggedIn(uname, password)) 
            {
                errorMsg = Errors.SIGNNED_IN;
            }
            else
            {
               return DBOperations.login(uname, password);
            }
        }
        else
        {
            errorMsg = Errors.NOT_EXIST;
        }
        return null;
    }
    
    private Player signUp (String uname, String password)
    {
        curPlayer = DBOperations.register(uname, password);
        
        if (curPlayer == null)
        {
            errorMsg = Errors.EXISTS;
        }
        return curPlayer;
    }
    
   private void signInRoutine (Player playerInfo) throws IOException
    {
        // init the player handler 
        new PlayerHandler(this.socket, playerInfo);
        
        this.close();
    }
    
   
    public void close()
    {
        System.out.println("Authentication handler closed");
        this.stop();
    }
}
