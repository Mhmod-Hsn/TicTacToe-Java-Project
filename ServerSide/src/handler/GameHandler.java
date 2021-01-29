/*
    This class is used to produce a thread per game to handle the player's
    requests [inside the game].
 */

package handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import server.utils.Errors;
import server.utils.JSONHandeling;
import server.utils.Requests;
import server.DBOperations;

import database.gameinfo.Game;
import org.json.simple.JSONObject;


/**
 *
 * @author Hossam Khalil
 */


public class GameHandler extends Thread {
    
    private static final int WIN_POINTS = 20;
    private static final int LOSS_POINTS = 15;
    
    private final PlayerHandler xPlayerHandler;
    private final PlayerHandler oPlayerHandler;
    
    //old game
    private final Game oldGame;
    
    private boolean isOldGame = false;
            
    private volatile JSONObject xPlayerRequestObj;
    private volatile JSONObject oPlayerRequestObj;
        
    private JSONObject responseObj;
    private final DataOutputStream xPlayerOutput;
    private final DataOutputStream oPlayerOutput;
    
    private volatile boolean isGameEnded = false;
    
    //define the moves
    private static final Game.cellType X_MOVE = Game.cellType.X;
    private static final Game.cellType O_MOVE = Game.cellType.O;
    private static final Game.cellType EMPTY_CELL = Game.cellType.EMPTY;
    

    //create 2d array to carry the state of the game
    private Game.cellType[][] gameBoard = {{EMPTY_CELL,EMPTY_CELL,EMPTY_CELL},
                                  {EMPTY_CELL,EMPTY_CELL,EMPTY_CELL},
                                  {EMPTY_CELL,EMPTY_CELL,EMPTY_CELL}};
            

    public GameHandler(PlayerHandler xPlayerHandler, PlayerHandler oPlayerHandler, Game oldGame)
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
        
        this.oldGame = oldGame;
        
        //check and old game if existed
        if (oldGame != null)
        {
            //load the board
            isOldGame = true;
            gameBoard = convertToTwoDimension(oldGame.getBoard());
        }
        
        //create a json object
        responseObj = new JSONObject();
        
        //Start the thread
        this.start();   
    }
    
    
    @Override
    public void run ()
    {  
        //construct the init json to boradcast
        responseObj = constructGameStartJSON();

        //boradcast that the game init json
        broadcast(responseObj);
        
        //running until the game ends
        while (!isGameEnded)
        {
            
            //System.out.println("");
            
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

        //end this thread
        this.close();
    }
    
    
    private void broadcast(JSONObject json)
    {
        //send to player 1
        
        try {
            this.xPlayerOutput.writeUTF(json.toString());
            
        } catch (IOException ex) {
            //the player has dropped connection (consider him as a player quit
            //and end the game
            
            quitGameRoutine(Game.cellType.X);
            isGameEnded = true;
            
        }
        //send to player 2
        try {
            this.oPlayerOutput.writeUTF(json.toString());
        } catch (IOException ex) {
            
            //the player has dropped connection (consider him as a player quit
            //and end the game
            quitGameRoutine(Game.cellType.O);
            isGameEnded = true;
        }
    }

    private boolean handlePlayerRequest(JSONObject playerRequest, boolean isXPlayer )
    {
        //find out which request
        String requestType = (String)playerRequest.get("type");
        
        boolean isSucess = false;
        Game.cellType move;
        
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
              
            //save game request
            case Requests.GAME_SAVE:

                //save the game
                if (saveGame((String)playerRequest.get("nextMove")))
                {
                    //construct the ending json to boradcast
                    responseObj = JSONHandeling.constructJSONResponse(responseObj, requestType);
                          
                    //boradcast that the game has ended
                    broadcast(responseObj);
                    
                    isSucess = true;
                    
                    //end the game
                    isGameEnded = true;
                }
                //failed to save
                else
                {
                    //construct the ending json to boradcast
                    responseObj = JSONHandeling.errorToJSON(requestType, Errors.INVALID);
                          
                    //boradcast that the game has ended
                    broadcast(responseObj);
                    
                    isSucess = false;
                }
                
                break;
                
            //game ended request
            case Requests.GAME_ENDED:
                
                //check if the game is not a draw
                if (playerRequest.get("isDraw").toString().equals("false"))
                {
                    //get winner player
                    updateEndGamePlayersScores(move, false);
                }
                
                else
                {
                    updateEndGamePlayersScores(move, true);
                }
                
                //update the is game ended flag
                isGameEnded = true;
                
                isSucess = true;
                break;  

            //player quit game request
            case Requests.GAME_QUIT:
                
                quitGameRoutine(move);

                //update the is game ended flag
                isGameEnded = true;
                isSucess = true;
                
                break;  
        }
        return isSucess;
    }
    
    private void recordMove (Long row , Long col , Game.cellType move)
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

    private void updateEndGamePlayersScores (Game.cellType winnerMove, boolean isDraw)
    {
        PlayerHandler winnerPlayer;
        PlayerHandler otherPlayer;

        //update score
        if (!isDraw)
        {
            // x player wins
            if (winnerMove.equals(X_MOVE))
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

        }
        //don't update score
        else 
        {
            winnerPlayer = xPlayerHandler;
            otherPlayer = oPlayerHandler;
        }
        
        //send new scores
        JSONObject endJsonObj = new JSONObject();
        
        endJsonObj = JSONHandeling.constructJSONResponse(endJsonObj, Requests.GAME_ENDED);
        endJsonObj = JSONHandeling.addToJSONObject(endJsonObj, "score", winnerPlayer.getPlayerScore());
        sendResponseToPlayer(endJsonObj,winnerPlayer.getOutputStream());
        
        endJsonObj = new JSONObject();
        
        endJsonObj = JSONHandeling.constructJSONResponse(endJsonObj, Requests.GAME_ENDED);
        endJsonObj = JSONHandeling.addToJSONObject(endJsonObj, "score", otherPlayer.getPlayerScore());
        sendResponseToPlayer(endJsonObj,otherPlayer.getOutputStream());
    }
    
    private boolean saveGame (String move)
    {
        if (isOldGame) {
            DBOperations.deleteGame(oldGame.getGameId());
        }
        Game.cellType nextMove;
        
        //convert the move into the cell types
        nextMove = move.equals(Game.cellType.X.toString())
                 ?Game.cellType.X:Game.cellType.O;
        
        //Save the game
        return DBOperations.addGame(convertToOneDimension(this.gameBoard), 
                this.xPlayerHandler.getPlayerInfo().getUsername(), 
                this.oPlayerHandler.getPlayerInfo().getUsername(),nextMove);
    }

    private JSONObject constructGameStartJSON()
    {
        //to carry the response type and data
        String responseType;
        JSONObject responseObj;
        
        //construct data for old game
        if (isOldGame)
        {
            responseType = Requests.GAME_LOAD;
            
            //load game data
            responseObj = JSONHandeling.gameToJSON(oldGame);
        }
        else
        {
            responseType = Requests.GAME_STARTED;
            //empty data
            responseObj = new JSONObject();
        }
        
        //return the constructed the response
        return JSONHandeling.constructJSONResponse(responseObj, responseType);
    }

    private void quitGameRoutine(Game.cellType quitMove)
    {
        DataOutputStream winnerPlayerStream;
        //get the winner move
        Game.cellType winnerMove;

        if (quitMove.equals(Game.cellType.O))
        {
            winnerMove = Game.cellType.X;
            winnerPlayerStream = this.xPlayerOutput;
        }
        else     
        {
            winnerMove = Game.cellType.O;
            winnerPlayerStream = this.oPlayerOutput;
        }
        
        //update players score
        updateEndGamePlayersScores(winnerMove, false);
        
        //notify other player that game has ended
        
        //construct response 
        JSONObject quitResponse = new JSONObject();
        
        quitResponse = JSONHandeling.constructJSONResponse(quitResponse,
        Requests.GAME_QUIT);
        
        sendResponseToPlayer(quitResponse,winnerPlayerStream);
    }
    
    private Game.cellType[] convertToOneDimension( Game.cellType [][]arr){

            Game.cellType []oneDimensionArr = new Game.cellType[9];
            int index=0;

            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < 3; j++) {
                    oneDimensionArr[index] = arr[i][j];
                    index++;
                }
            }
            return oneDimensionArr;
    }
    
    private Game.cellType [][] convertToTwoDimension(Game.cellType [] arr){

        Game.cellType[][] twoDimensionArr = new Game.cellType[3][3];
        int index=0;

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                twoDimensionArr[i][j] = arr[index];
                index++;
            }
        }
        return twoDimensionArr;

    }
    
    private void close()
    {
        
        //update players status
        this.xPlayerHandler.updatePlayerStatus("online");
        this.oPlayerHandler.updatePlayerStatus("online");
        
        //delete game if saved
        if(isOldGame)
        {
            DBOperations.deleteGame(oldGame.getGameId());
        }
        
        //close this thread
        this.stop();
    }
}