/*
    This class is used to produce a thread per player to handle the player's
    requests [outside the game].
 */

package handler;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.io.IOException;

import database.gameinfo.Game;
import database.playerinfo.Player;

import server.DBOperations;
import server.utils.Errors;
import server.utils.JSONHandeling;
import server.utils.Requests;
import server.utils.ServerUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;



/**
 *
 * @author Hossam Khalil
 */

public class PlayerHandler extends Thread{
 
    public Socket playerSocket;
    private Player player;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    
    private String errorMsg;
    private volatile JSONObject forwardedRequest;
    private JSONObject jsonObj;
    
    private volatile boolean isStayAlive = true;
        
    
    private static Vector <PlayerHandler> onlinePlayerHandlers = new Vector <>();
    
    class GameEstablishHandler extends Thread{

        private PlayerHandler senderPlayerHandler;
        private PlayerHandler receiverPlayerHandler;
        private boolean isOldGame = false;
        private Game oldGame;
        
        private volatile JSONObject receiverJson; 
        private JSONObject senderJson ;
        
        private volatile boolean isReceived = false;

        public GameEstablishHandler(PlayerHandler senderhHandler, PlayerHandler receiverHandler , Long oldGameId)
        {

            //store the handlers
            this.senderPlayerHandler = senderhHandler;
            this.receiverPlayerHandler = receiverHandler;
            
            
            //raise flag if the game is old
            if (oldGameId != null)
            {
                isOldGame = true;
                
                //load the game
                oldGame = DBOperations.getGame(oldGameId);

                //invalid id (consider it as a new game)
                if (oldGame == null)
                {
                    isOldGame = false;
                }
            }
            
            //clear last read
            senderPlayerHandler.getForwardedRequest();
            receiverPlayerHandler.getForwardedRequest();
            
            //update players status to busy
            senderPlayerHandler.updatePlayerStatus("busy");
            receiverPlayerHandler.updatePlayerStatus("busy");
              
            //Start the thread
            this.start();   
        }

        @Override
        public void run()
        {
            try {
                
                receiverJson = new JSONObject();
                senderJson = new JSONObject();

                //Construct invitation json object
                receiverJson = JSONHandeling.playerToJSON(senderPlayerHandler.getPlayerInfo());
                receiverJson = JSONHandeling.addToJSONObject(receiverJson,"type", Requests.RECEIVE_INVITATION);
                receiverJson = JSONHandeling.addToJSONObject(receiverJson,"newGame", !isOldGame);
                
                //load old game info if found
                if(isOldGame)
                {
                    receiverJson = JSONHandeling.addToJSONObject(receiverJson,"gameId", oldGame.getGameId());
                    receiverJson = JSONHandeling.addToJSONObject(receiverJson,"date", oldGame.getSaveDate());
                }
                
                //Send invitation to player 2
                receiverPlayerHandler.getOutputStream().writeUTF(receiverJson.toString());
                
                //wait for response on the same request from player 2
                while(! isReceived) {
                    System.out.println("");
                    receiverJson = receiverPlayerHandler.getForwardedRequest();

                    if (receiverJson == null);

                    else if (receiverJson.get("type").equals(Requests.RECEIVE_INVITATION))
                    {
                        isReceived = true;
                    }
                }
                
                //Construct response for player 1 (in case of valid delivery)
                senderJson = JSONHandeling.playerToJSON(receiverPlayerHandler.getPlayerInfo());
                
                senderJson = JSONHandeling.constructJSONResponse(senderJson ,Requests.INVITATION_RESPONSE);
                
                senderJson = JSONHandeling.addToJSONObject(senderJson,"invitationStatus"
                        ,receiverJson.get("invitationStatus"));

                senderJson = JSONHandeling.addToJSONObject(senderJson,"newGame", !isOldGame);
                
                //send the response to the sender player
                senderPlayerHandler.getOutputStream().writeUTF(senderJson.toString());
                

                
                //establish game if the invitation is accepted
                if (receiverJson.get("invitationStatus").equals("true")) {

                    // Start Game
                    ServerUtils.appendLog("[GameEstablishHandler class]: Game Invitation Accepted [ "+
                            senderPlayerHandler.getPlayerInfo().getUsername()+"] vs [ "+
                            receiverPlayerHandler.getPlayerInfo().getUsername()+" ]");

                    new GameHandler(senderPlayerHandler,receiverPlayerHandler,oldGame);
                }
                else 
                {
                    //update players status back to online
                    senderPlayerHandler.updatePlayerStatus("online");
                    receiverPlayerHandler.updatePlayerStatus("online");
                    
                    ServerUtils.appendLog("[GameEstablishHandler class]: Game Invitation Rejection [ "+
                            senderPlayerHandler.getPlayerInfo().getUsername()+"] vs [ "+
                            receiverPlayerHandler.getPlayerInfo().getUsername()+" ]");
                }

                
                //close this thrad 
                close();
                
            } catch (IOException ex) {
                
                //Conection Failed
                ServerUtils.appendLog("[GameEstablishHandler class]: Player connection dropped (connection failed)");
                close();
            }
        }

        public void close()
        {
            this.stop(); 
        }
    }
    
    
    public PlayerHandler(Socket socket, Player playerInfo)
    {
        this.playerSocket = socket;
        this.player = playerInfo;
                
        jsonObj = new JSONObject();

        
        try {
            
            //Create the input and output channels
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            //Add to the vector of handlers
            onlinePlayerHandlers.add(this);    
            
            //Start the thread to accept requests
            this.start();
            
        } catch (IOException ex) {
            
            ServerUtils.appendLog("[PlayerHandler class]: Player has dropped connection");
            close();
        }
    }
    
    @Override
    public void run()
    {
        //Listen to the requests of the players
        while (isStayAlive)
        {
            try {
                //handle user requests
                jsonObj = playerRequestHandler(inputStream.readUTF());

                if (jsonObj != null)
                {
                    //send response to the user               
                    outputStream.writeUTF(jsonObj.toString());
                }  
                //Connection Drop
            } catch (IOException ex) { 
                ServerUtils.appendLog("[PlayerHandler class]: Player has dropped connections"); 
                close();
                
            } catch (ParseException ex) {
                ServerUtils.appendLog("[Error]: In PlayerHander Invalid received data [Parse Exception]: "); 
                ServerUtils.appendLog(jsonObj.toString());
            } 
        }
    }
    
    //Getters
    public JSONObject getForwardedRequest(){
        
        JSONObject result = forwardedRequest;
        forwardedRequest = null;
        return result;
    }
    
    public Player getPlayerInfo() {
        return player;
    }
    
    public DataOutputStream getOutputStream() {
        return outputStream;
    }
    
    public DataInputStream getInputStream() {
        return inputStream;
    }
    
    public long getPlayerScore()
    {
        return player.getScore();
    }
    
    public static Vector<PlayerHandler> getOnlinePlayerHandlers() {
        return onlinePlayerHandlers;
    }
    
    
    //Methods used by server to manage this handler
    
    //close the current thread and logout the current player
    public void close()
    {
        try {
            
            //Signout this player
            DBOperations.logout(player.getUsername());
            
            //remove this current player from online vector
            onlinePlayerHandlers.remove(this);
            
            //Close the connection
            playerSocket.close();
            

        } catch (IOException ex) {
            ServerUtils.appendLog("[Error]: In PlayerHandler Player socket failed to be closed.");
        }
        
        //set flag to false to break the loop
        isStayAlive = false;
        
        //close this thread
        this.stop();

    }
    
    //empty the online list
    public static void stopAllHandlers()
    {
        int numberOfOnlinePlayers = onlinePlayerHandlers.size();
        
        //stop all running hundlers
        for (int indx = 0 ; indx < numberOfOnlinePlayers; indx++)
        {
            onlinePlayerHandlers.get(0).close();
        }
    }
    
    //Players Requests handeling
    private JSONObject playerRequestHandler(String jsonStr) throws ParseException
    {       
        //System.out.println("in player handler: "+jsonStr);
        jsonObj = JSONHandeling.parseStringToJSON(jsonStr);
        
        JSONObject responseJsonObj = new JSONObject();
        
        //find out which request
        String requestType = (String)jsonObj.get("type");

        //is the request proccessed successfully 
        boolean isSuccess = false;
        
        switch (requestType)
        {

            //Signout request
            case Requests.SIGN_OUT:
                //signout from the account
                ServerUtils.appendLog("[PlayerHandler class]: Player [ "+this.player.getUsername()+" ] has loggedout");
                close();
                isSuccess = true;
                break;
                
            //Send invitation request
            case Requests.SEND_INVITATION:
                //send invitation to another player
                isSuccess = playerInvite(jsonObj);
                break;
            
            //update status request
            case Requests.UPDATE_STATUS:
                //update the player status
                isSuccess = updatePlayerStatus(jsonObj.get("status").toString());
                break;
            
            //update score request   
            case Requests.UPDATE_SCORE:       
                //update the player score
                isSuccess =  updatePlayerScore((long)jsonObj.get("score"));
                break;
                
            //get saved games request
            case Requests.GET_SAVED_GAMES:
                //get list of available games
                responseJsonObj = getSavedGames();
                isSuccess = true;
                break;
            
            //requests to be forwarded to other handlers
            default:
                //forward the request
                forwardedRequest = jsonObj;
                return null; 
                
        }
        
        if (isSuccess)
        {
            responseJsonObj = JSONHandeling.constructJSONResponse(responseJsonObj, requestType);
            ServerUtils.appendLog("[PlayerHandler class]: Player Request [ "+requestType+"] has succeeded");
        }
        else 
        {
            responseJsonObj = JSONHandeling.errorToJSON(requestType, errorMsg); 
            ServerUtils.appendLog("[PlayerHandler class]: Player Request [ "+requestType+"] has failed");
            ServerUtils.appendLog("[PlayerHandler class]: The failed request: "+jsonObj);
        }

        return responseJsonObj;
    }
    
    //invite player request
    private boolean playerInvite(JSONObject reqeustObj)
    {

        PlayerHandler playerToInvite = 
                isPlayerHandlerExists(reqeustObj.get("username").toString());
        
        errorMsg = "";

        //player wasn't found
        if (playerToInvite == null)
        {
            errorMsg = Errors.NOT_EXIST;
            return false;
        }
        //check if player is not online
        if (! playerToInvite.getPlayerInfo().getStatus().toString().equals("online"))
        {
            errorMsg = Errors.BUSY;
            return false;
        }
        
        //Check if it's a new or old game
        Long oldGameId;
        
        if (reqeustObj.get("newGame").equals(true))
        {
           oldGameId = null; 
        }
        else //load the game
        {
           oldGameId = (Long)reqeustObj.get("gameId");
        }
        
        //start Game Establish thread
        new GameEstablishHandler (this, playerToInvite, oldGameId);

        return true;

    }
     
    //update player score request
    public boolean updatePlayerScore(long newScore)
    {
        errorMsg = "";
        
        if (! DBOperations.updatePlayerScore(player.getUsername(), newScore))
        {
            errorMsg = Errors.NOT_EXIST;
            return false;
        }
        //update current player
        this.player.setScore(newScore);

        return true;
    }
    
    //update player status
    public boolean updatePlayerStatus(String newStatus)
    {   
        errorMsg = "";
        //fix the (ingame) bug
        if (newStatus.equals("ingame"))
        {
            newStatus = Player.statusType.busy.toString();
        }
        
        //check allowed status
        if ( ! (newStatus.equals(Player.statusType.busy.toString()) ||
            newStatus.equals(Player.statusType.online.toString())))
        {
            errorMsg = Errors.INVALID;
            return false;
        }
        if (! DBOperations.updatePlayerStatus(player.getUsername(),newStatus))
        {
            errorMsg = Errors.NOT_EXIST;
            return false;
        }
        
        //update current status
        if (newStatus.equals(Player.statusType.busy.toString()))
        {
            this.player.setStatus(Player.statusType.busy);
        }
        else 
        {
            this.player.setStatus(Player.statusType.online);
        }
        return true;
    }
    
    //Check if a player is online
    private PlayerHandler isPlayerHandlerExists (String  username)
    {
        for (PlayerHandler handler : onlinePlayerHandlers)
        {
            if (handler.getPlayerInfo().getUsername().equals(username))
            {
                return handler;
            }
        }
        //player was not found
        return null;
    }

    private JSONObject getSavedGames()
    {
        //get game list
        Vector <Game> gameList = DBOperations.getGameList(this.getPlayerInfo().getUsername());

        //if the list is empty
        if (gameList == null)
        {
            gameList = new Vector();
        }
        
        //create json object
        JSONObject responseJSON = new JSONObject();

        //convert it the result to a json object
        JSONHandeling.addToJSONObject(responseJSON, "gamesList", 
                JSONHandeling.gameListToJSONArray(gameList, this.getPlayerInfo().getUsername()));
        
        return responseJSON;
    }
}