/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import org.json.simple.JSONObject;
import server.utils.JSONHandeling;
import server.utils.Requests;

/**
 *
 * @author Hossam
 */

public class GameHandler extends Thread {
    
    private static final int WIN_POINTS = 20;
    private static final int LOSS_POINTS = 15;
    
    private final PlayerHandler xPlayerHandler;
    private final PlayerHandler oPlayerHandler;
    
    private volatile JSONObject xPlayerRequestObj;
    private volatile JSONObject oPlayerRequestObj;
        
    private JSONObject responseObj;
    private final DataOutputStream xPlayerOutput;
    private final DataOutputStream oPlayerOutput;
    
    private volatile boolean isGameEnded = false;
    
    //define the moves
    private static final char X_MOVE = 'X';
    private static final char O_MOVE = 'O';
    private static final char EMPTY_CELL = ' ';
    
    
    //create 2d array to carry the state of the game
    private char[][] gameBoard = {{EMPTY_CELL,EMPTY_CELL,EMPTY_CELL},
                                  {EMPTY_CELL,EMPTY_CELL,EMPTY_CELL},
                                  {EMPTY_CELL,EMPTY_CELL,EMPTY_CELL}};
            

    public GameHandler(PlayerHandler xPlayerHandler, PlayerHandler oPlayerHandler)
    {
        //clear last reads
        //listen from both players
        xPlayerHandler.getForwardedRequest();
        oPlayerHandler.getForwardedRequest();
        
        //define the handlers
        this.xPlayerHandler = xPlayerHandler;
        this.oPlayerHandler = oPlayerHandler;
        
        //define output streams
        this.xPlayerOutput = xPlayerHandler.getOutputStream();
        this.oPlayerOutput = oPlayerHandler.getOutputStream();
        
        
        //create a json object
        responseObj = new JSONObject();
        
        //construct the init json to boradcast
        responseObj = JSONHandeling.constructJsonResponse(responseObj, Requests.GAME_STARTED);
        
        //boradcast that the game has started
        broadcast(responseObj);
        
        //Start the thread
        this.start();   
    }
    
    @Override
    public void run ()
    {
        //running until the game ends
        while (!isGameEnded)
        {
            //listen from both players
            xPlayerRequestObj = xPlayerHandler.getForwardedRequest();
            oPlayerRequestObj = oPlayerHandler.getForwardedRequest();
            
            //received request from x player 
            if ( xPlayerRequestObj != null)
            {
                handlePlayerRequest(xPlayerRequestObj, true);
            }
            
            //received request from o player
            if (oPlayerRequestObj != null)
            {
                handlePlayerRequest(oPlayerRequestObj , false);
            }
        }
        
        //game ended
        
//        //construct the ending json to boradcast
//        responseObj = JSONHandeling.constructJsonResponse(responseObj, Requests.GAME_ENDED);
//        
//        //boradcast that the game has ended
//        broadcast(responseObj);
        
        //end this thread
        this.close();
    }
    
    
    private boolean broadcast(JSONObject json)
    {
        try {
            this.xPlayerOutput.writeUTF(json.toString());
            this.oPlayerOutput.writeUTF(json.toString());
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    private char[] convertToOneDimension(char [][]arr){
        
            char []oneDimensionArr = new char[9];
            int index=0;
            
            for (int i = 0; i < 3; i++) {
                
                for (int j = 0; j < 3; j++) {
                    oneDimensionArr[index] = arr[i][j];
                    index++;
                }
            }
            return oneDimensionArr;
        }
    
    private char [][] convertToTwoDimension(char [] arr){
        
        char[][] twoDimensionArr = new char[3][3];
        int index=0;
        
        for (int i = 0; i < 3; i++) {
            
            for (int j = 0; j < 3; j++) {
                twoDimensionArr[i][j] = arr[index];
                index++;
            }
        }
        return twoDimensionArr;
        
    }

    private boolean handlePlayerRequest(JSONObject playerRequest, boolean isXPlayer )
    {
        //find out which request
        String requestType = (String)playerRequest.get("type");
        System.out.println("[GameHandler class]: (handling player reques>>) in game: "+playerRequest.toString());
        boolean isSucess = false;
        char move;
        
        DataOutputStream OtherPlayerOutput; 
        
        //find which player to load the info
        if (isXPlayer)
        {
            move = X_MOVE;
            OtherPlayerOutput = oPlayerOutput;
        }
        else 
        {
            move = O_MOVE;
            OtherPlayerOutput = xPlayerOutput;
        }
        
        switch (requestType)
        {
            //game move request
            case Requests.GAME_MOVE:
                
                //save the move
                recordMove((Long)playerRequest.get("row"),(Long)playerRequest.get("col") ,move);
                //forward to the other player
                isSucess = sendResponseToPlayer(playerRequest, OtherPlayerOutput);
                break; 
                
            //chat message request
            case Requests.CHAT_MSG:
                //forward to the other player
                isSucess = sendResponseToPlayer(playerRequest, OtherPlayerOutput);
                break;
                
            //game ended request
            case Requests.GAME_ENDED:
                
                //get winner player
                gameEndingRoutine(move);
                //update the is game ended flag
                isGameEnded = true;
                isSucess = true;
                break;
        }
        return isSucess;
    }
    
    private void recordMove (Long row , Long col , char move)
    {
        this.gameBoard[row.intValue()][col.intValue()] = move;
    }
    
    private boolean sendResponseToPlayer (JSONObject response, DataOutputStream output)
    {
        try {
            output.writeUTF(response.toString());
            return true;
            
        //player has dropped connection
        } catch (IOException ex) {
            return false;
        }     
    }

    private void gameEndingRoutine(char winnerMove)
    {
        PlayerHandler winnerPlayer;
        PlayerHandler otherPlayer;
        
        // x player wins
        if (winnerMove == X_MOVE)
        {
            winnerPlayer = xPlayerHandler;
            otherPlayer = oPlayerHandler;
        }
        // o player wins
        else 
        {
            winnerPlayer = oPlayerHandler;
            otherPlayer = xPlayerHandler;
        }
        //update players points

        winnerPlayer.updatePlayerScore(winnerPlayer.getPlayerScore() + WIN_POINTS);
        
        long newOtherScore = otherPlayer.getPlayerScore() - LOSS_POINTS;
        if (newOtherScore < 0)
        {
            otherPlayer.updatePlayerScore(0);
        }
        else 
        {
            otherPlayer.updatePlayerScore(newOtherScore);
        }

        //send new scores
        JSONObject endJsonObj = new JSONObject();
        
        endJsonObj = JSONHandeling.constructJsonResponse(endJsonObj, Requests.GAME_ENDED);
        endJsonObj = JSONHandeling.addToJsonObject(endJsonObj, "score", winnerPlayer.getPlayerScore());
        sendResponseToPlayer(endJsonObj,winnerPlayer.getOutputStream());
        
        endJsonObj = new JSONObject();
        
        endJsonObj = JSONHandeling.constructJsonResponse(endJsonObj, Requests.GAME_ENDED);
        endJsonObj = JSONHandeling.addToJsonObject(endJsonObj, "score", otherPlayer.getPlayerScore());
        sendResponseToPlayer(endJsonObj,otherPlayer.getOutputStream());
        
        //update players status
        this.xPlayerHandler.updatePlayerStatus("online");
        this.oPlayerHandler.updatePlayerStatus("online");
    }

    private void close()
    {
        //close this thread
        this.stop();
    }
}