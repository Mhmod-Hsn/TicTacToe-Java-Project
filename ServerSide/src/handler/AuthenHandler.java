/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;


import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import server.DBOperations;
import playerinfo.Player;
import server.utils.*;

import org.json.simple.JSONObject;

import java.io.IOException;
import org.json.simple.parser.ParseException;

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
                System.out.println("[AuthenHandler] Client connection dropped");
                close();
            } catch (ParseException ex) {
               System.out.println("[AuthenHandler] Parse Exception");
               System.out.println(jsonObj);
            } 
        }
    }
    
    
    private JSONObject userRequestHandler(String jsonStr) throws ParseException, IOException
    {
        jsonObj =  JSONHandeling.parseStringToJson(jsonStr);
        JSONObject responseJsonObj = new JSONObject();
        
        Player playerInfo;

        //findout which request
        String requestType = (String)jsonObj.get("type");
        
        switch (requestType)
        {
            //sign in or sign up request
            case (Requests.SIGN_IN): case(Requests.SIGN_UP):
                
                if (requestType.equals(Requests.SIGN_IN))
                {
                    playerInfo = signIn((String)jsonObj.get("username"),(String)jsonObj.get("password"));
                }
                else 
                {
                    playerInfo = signUp((String)jsonObj.get("username"),(String)jsonObj.get("password"));
                }

                //valid sign in
                if (playerInfo != null) 
                {
                    //construct the player info
                    responseJsonObj = JSONHandeling.playerToJson(playerInfo);

                    //add the json response construction
                    responseJsonObj = JSONHandeling.constructJsonResponse(responseJsonObj, requestType);

                    System.out.println("Client has signed in");
                    
                    //send response to the user               
                    outputStream.writeUTF(responseJsonObj.toString());
                    
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
                System.out.println("Unknown request");
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
        //stop this thread
        this.stop();
    }
    
    public void close()
    {

       try {
            System.out.println("Authentication handler closed");
            
            //Close the connection
            socket.close();

            //close this thread
            this.stop();

            
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("[AuthenHandler] user socket can't be closed.");
        }
    }
}
