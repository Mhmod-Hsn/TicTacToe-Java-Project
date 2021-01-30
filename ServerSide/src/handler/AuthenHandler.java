/*
    This class is used to produce a thread per user to handle the user's
    authentication routine [signin, signup]
 */

package handler;


import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import database.playerinfo.Player;
import java.util.Vector;

import server.utils.Errors;
import server.utils.JSONHandeling;
import server.utils.Requests;
import server.utils.ServerUtils;
import server.DBOperations;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;



/**
 *
 * @author Hossam Khalil
 */

public class AuthenHandler extends Thread {
    
    private Socket socket;
    private String errorMsg;
    private JSONObject jsonObj;
    private Player curPlayer;
    
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    
    private boolean isRequestValid = false;
    private volatile boolean isStayAlive = true;
    
    public static Vector <AuthenHandler> connectedClientsList = new Vector();
            
    public AuthenHandler(Socket socket)
    {
        this.socket = socket;
        try {
            //Create the input and output channels
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
           
            //Add to the vector of handlers
            connectedClientsList.add(this);   
            
            //Start the thread
            this.start();
            
        } catch (IOException ex) {
            //Client socket has dropped (close this thread)
            ServerUtils.appendLog("[AuthenHandler class]: Client dropped connection");
            this.close();
        }
    }
    
    @Override
    public void run()
    {
        while (isStayAlive)
        {
            try {
                //handle user requests
                jsonObj = userRequestHandler(inputStream.readUTF());

                //send response to the user               
                outputStream.writeUTF(jsonObj.toString());
                
                //Connection Drop
            } catch (IOException ex) {
                ServerUtils.appendLog("[AuthenHandler class]: Client connection dropped");
                close();
                
            } catch (ParseException ex) {
               //invalid data received (skip this iteration)
               ServerUtils.appendLog("[Error]: In AuthenHandler invalid received data Parse Exception");
               ServerUtils.appendLog(jsonObj.toString());
            } 
        }
    }
    
    
    private JSONObject userRequestHandler(String jsonStr) throws ParseException, IOException
    {
        
        jsonObj =  JSONHandeling.parseStringToJSON(jsonStr);
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
                    responseJsonObj = JSONHandeling.playerToJSON(playerInfo);

                    //add the json response construction
                    responseJsonObj = JSONHandeling.constructJSONResponse(responseJsonObj, requestType);

                    ServerUtils.appendLog("[AuthenHandler class]: A Client has just signed in");
                    
                    //send response to the user               
                    outputStream.writeUTF(responseJsonObj.toString());
                    
                    signInRoutine(playerInfo);
                }
                
                //already signed in or doesn't exist
                else
                {
                    responseJsonObj = JSONHandeling.errorToJSON(requestType, errorMsg); 
                    ServerUtils.appendLog("[AuthenHandler class]:" + requestType+" rejected: error msg [" + errorMsg+ " ]");
                }
                break;


            //close request
            case (Requests.CLOSE):
                
                //Terminate this thread
                ServerUtils.appendLog("[AuthenHandler class]: Client closed connection");
                this.close(); 
                break;

            //unknown request
            default:
                ServerUtils.appendLog("[AuthenHandler class]: Unknown incoming request");
                responseJsonObj = JSONHandeling.errorToJSON(Requests.UNKNOWN, Errors.INVALID); 
                break;
        } 
            //output the result
            return responseJsonObj;
    }
    
    private Player signIn(String uname, String password)
    {
        if ( DBOperations.checkPass(password,DBOperations.getUserPass(uname)) ) 
        {
            System.out.println("true"+password+"  "+ DBOperations.getUserPass(uname) );
            if (DBOperations.isLoggedIn(uname, password)) //checkPass("123", str)
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
            System.out.println("false"+password+"  "+ DBOperations.getUserPass(uname) );
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
    
    public static void stopAllHandlers()
    {
        int numberOfOnlinePlayers = connectedClientsList.size();
        
        //stop all running hundlers
        for (int indx = 0 ; indx < numberOfOnlinePlayers; indx++)
        {
            connectedClientsList.get(0).close();
        }
    }
    
    public void close()
    {

       try {            
            //Close the connection
            socket.close();
    
        } catch (IOException ex) {
            ServerUtils.appendLog("[Error]: AuthenHandler class user socket can't be closed.");
        }
       
       connectedClientsList.remove(this);
       
        //set variable to break the loop incase thread didn't close
        isStayAlive = false;
        
        //close this thread
        this.stop();
    }
   
}
