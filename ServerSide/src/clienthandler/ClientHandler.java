/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientHandler;

import clientside.ClientSide;
import clientside.InviteFXMLController;
import clientside.LoginFXMLController;
import clientside.LoadgameFXMLController;
import clientside.NewgameFXMLController;
import clientside.PlayingModeFXMLController;
import clientside.StartFXMLController;
import clientside.InvitationFXMLController;
import clientside.MultigameFXMLController;
import clientHandler.Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarahouf
 */
public class ClientHandler {
    
    private static Socket clientSocket;
    private static DataInputStream ds;
    private static Stage window;
    private static Player player;
    private static String currentScene;
    private static DataOutputStream ps;
    private static LoginFXMLController loginctrl;
    private static StartFXMLController startctrl;
    private static NewgameFXMLController newgamectrl;
    private static LoadgameFXMLController loadgamectrl;
    private static InviteFXMLController Invitectrl;
    private static PlayingModeFXMLController Playmodectrl;
    private static InvitationFXMLController invitationCtrl;
    private static MultigameFXMLController multigameCtrl;
    private static ObservableList<String> name = FXCollections.observableArrayList ();
    private static ObservableList<String> status = FXCollections.observableArrayList ();
    private static ObservableList<String> score= FXCollections.observableArrayList ();
    private static ObservableList<String> games= FXCollections.observableArrayList ();
    private static String invitingUsername;
    private static boolean gameAccepted = false;
    private static boolean replay = false;
    private static JSONArray gamesFullInfo;
    private static char[][] gameBoard = new char[3][3];
    private static char nextMove;
    private static boolean isLoaded = false;
    private static String nextPlayer;
    private static boolean clientDropped = false;
    private static boolean isConnected;
    
    private ClientHandler(){  
    }
    
    /** Connect to the server
     ** @return  status of connection.
     */
    public static boolean connectToServer(){
        boolean res = true;
        try {
            clientSocket = new Socket("127.0.0.1", 7777);
            ds = new DataInputStream(clientSocket.getInputStream());
            ps = new DataOutputStream(clientSocket.getOutputStream());
            //ps = new PrintStream(clientSocket.getOutputStream());
            
        } catch (IOException ex) {
            res = false;
        }
        return res;
    }
    
    /** Close the connection with the server.
     */
    public static void closeCon(){
        try{
            ps.close();
            ds.close();
            clientSocket.close();
        }
        catch(IOException ex){
            //System.out.println("No connection exists to close.");
        }
    }
    
    /** Send a new request to the server
     * @param jsonMsg
     */
    public static void sendRequest(JSONObject jsonMsg)
    {
        try {
            ps.writeUTF(jsonMsg.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        //System.out.println("Sent a new request:"+ jsonMsg.toString());
        
    }
    
    /* Receive requests from the server and send them to the handler */
    public static class recieveRespone implements Runnable {
        boolean running = true;
        String response;
        @Override
        public void run(){
            //System.out.println("Recieve Response Thread has Started.");
            while (running) {
                try {
                    response = ds.readUTF();
                    if (response != null) {
                        handleResponse(response);
                    }
                } catch (IOException ex) {
                    //System.out.println("Connection is down.");
                    running=false;
                    changeScene("Confailed");
                }
            }
        }
    }
    
    /** Handle responses according to its type.
     * @param response : received response from the server.
     */
    private static void handleResponse(String response)
    {
        //System.out.println("Received a new response: "+response);
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonMsg = (JSONObject) parser.parse(response);
            switch (jsonMsg.get("type").toString()) {
                case "signin":
                    Login(jsonMsg);
                    break;
                case "signup":
                    Login(jsonMsg);
                    break;
                case "updateStatus":
                    updateStatus(jsonMsg);
                    break;
                case "updateList":
                    updateList(jsonMsg);
                    break;
                case "invitePlayer":
                    invitePlayerResponse(jsonMsg);
                    break;
                case "invitation":
                    invitationRequest(jsonMsg);
                    break;
                case "sendMove":
                    getMoveReponse(jsonMsg);
                    break;
                case "gameStarted":
                    GameStartedResponse(jsonMsg);
                    break;
                case "sendChat":
                    sendChatResponse(jsonMsg);
                    break;
                case "gameEnded":
                    gameEndedResponse(jsonMsg);
                    break;   
                case "getGames":
                    displayGamesList(jsonMsg);
                    break;                    
                case "loadGame":
                    loadGameResponse(jsonMsg);
                    break;
                case "invitationResponse":
                    getInvitationResponse(jsonMsg);
                    break;
                    
                case "saveGame":
                    saveGameResponse(jsonMsg);
                    break;
                    
                case "gameQuit":
                    gameQuitResponse(jsonMsg);
                    break;
                
            }
        } catch (ParseException ex) {
            //System.out.println("Exception in handle response.");
        }
    }

    /** Change displayed scene to the given FXML.
     * @param newScene
     */
    @FXML
    public static void changeScene(String newScene)
    {   
        //System.out.println("Changing scene to: "+newScene);
        setCurrentScene(newScene);
        Platform.runLater(() -> {   
            try {
                Parent root = FXMLLoader.load(ClientSide.class.getResource(newScene+"FXML.fxml"));
                Scene scene = new Scene(root);
                window.setScene(scene);
                window.setResizable(false);
                window.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                //System.out.println("Exception in change scene function.");
            }
        });
    }
    /********************** getters and setters ******************/
    /** Set the window to the application stage to be able to access and change it.
     * @param stage
     */
    public static void setWindow(Stage stage) {
        window = stage;
    }
    public static Stage getWindow() {
        return window;
    }
    public static void setCurrentScene(String scene) {
        currentScene = scene;
    }
    public static String getCurrentScene() {
        return currentScene;
    }
    /** Set the player to the current user player.
     * @param p
     */
    public static void setPlayer(Player p) {
        player = p;
    }
    public static Player getPlayer() {
        return player;
    }
    
    /** Set scene controllers to be able to access and change them.
     * @param ctrl 
     */
    public static void setLoginCtrl(LoginFXMLController ctrl) {
        loginctrl=ctrl;
    }
    public static void setStartCtrl(StartFXMLController ctrl) {
        startctrl=ctrl;
    }
    public static void setNewgameCtrl(NewgameFXMLController ctrl) {
        newgamectrl=ctrl;
    }
    public static void setLoadgameCtrl(LoadgameFXMLController ctrl){
       loadgamectrl=ctrl;
    }
    public static void setInviteCtrl(InviteFXMLController ctrl){
       Invitectrl=ctrl;
    }
    public static void setPlaymodeCtrl(PlayingModeFXMLController ctrl){
       Playmodectrl=ctrl;
    }
    public static void setInvitationCtrl(InvitationFXMLController ctrl){
        invitationCtrl = ctrl;
    }
    public static void setMultigameCtrl(MultigameFXMLController ctrl){
        multigameCtrl = ctrl;
    }
    public static boolean getGameAccepted(){
        return gameAccepted;
    }
    public static void setReplay(boolean replay){
       ClientHandler.replay = replay;
    }
    public static boolean getReplay(){
        return replay;
    }
    public static JSONArray getGames (){
        return gamesFullInfo;
    }  
    public static ObservableList<String> getNameList (){
        return name;
    }  
    public static ObservableList<String> getStatusList(){
        return status;
    }  
    public static ObservableList<String> getScoreList(){
        return score;
    }
    public static char[][] getBoard(){
        return gameBoard;
    }
    public static void setIsLoaded(boolean isloaded){
        isLoaded = isloaded;
    }
    public static boolean getIsLoaded(){
        return isLoaded;
    }
    public static char getNextMove(){
        return nextMove;
    }
    public static String getNextPlayer(){
        return nextPlayer;
    }
    public static boolean getClientDropped(){
        return clientDropped;
    }
    
    /*************************** Login Response Handler ******************************/
    /** Login response handler.
     * @param response : response of login requests including signup and signin.
     */
    private static void Login(JSONObject response)
    {
        String request = response.get("type").toString();
        String resStatus = response.get("responseStatus").toString();
        if(resStatus.equals("true")){
            //System.out.println(response);
            player.setScore(Integer.parseInt(response.get("score").toString()));
            player.setUsername(response.get("username").toString());
            player.setStatus(response.get("status").toString());
            changeScene("Welcome");
        }
        else{
            String error = response.get("errorMsg").toString();
            String warning;
            
            if ( request.equals("signin") && error.equals("signedin"))
                warning="You are already signed in.";
            else if ( request.equals("signin") && error.equals("fail"))
                warning="Wrong user name or password";
            else if ( request.equals("signup") && error.equals("fail"))
                warning="Username already exists.";
            else
                warning="unexpected";
            //System.out.println("Error :"+warning);
            Platform.runLater(() -> {loginctrl.getLabel().setText(warning);});
        }
    }
    
    /*************************** update Status Response Handler *****************************/
    private static void updateStatus(JSONObject response){
        String resStatus = response.get("responseStatus").toString();
        if(resStatus.equals("true")){
            //System.out.println(response);
            //changeScene("Game");
        }
        else{
            //System.out.println("Fail update status");
        }
    }
    
    /********************* update Players list Response Handler *********************/
    private static void updateList(JSONObject response){
        JSONObject JSONplayer;
        JSONParser parser = new JSONParser();
        JSONArray list =(JSONArray) response.get("list");

        name = FXCollections.observableArrayList ();
        status = FXCollections.observableArrayList ();
        score= FXCollections.observableArrayList ();
        
        //System.out.println(" list size : "+list.size());
        for (int i = 0 ; i < list.size(); i++) {
            try {
                JSONplayer  = (JSONObject) parser.parse(list.get(i).toString());
                name.add(JSONplayer.get("username").toString());
                score.add(JSONplayer.get("score").toString());
                status.add(JSONplayer.get("status").toString());
            } catch (ParseException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        switch (currentScene) {
        case "Start":
            Platform.runLater(()->{startctrl.updateTable(name, score, status);});
            break;
        case "Newgame":
            Platform.runLater(()->{newgamectrl.updateTable(name, score, status);});
            break;
        case "Loadgame":
            Platform.runLater(()->{loadgamectrl.updateTable(name, score, status);});
            break;
        case "Invite":
            Platform.runLater(()->{Invitectrl.updateTable(name, score, status);});
            break;
        case "PlayMode":
            Platform.runLater(()->{Playmodectrl.updateTable(name, score, status);});
            break;
            
            
        default:
            break;
        }
    }
    
    /********************* Invite another player request and response *********************/
    public static void invitePlayerRequest(String invitedPlayerUsername){
        //System.out.println("inside invite request");
        JSONObject inviteRequest = new JSONObject();
        inviteRequest.put("type", "invitePlayer");
        inviteRequest.put("username", invitedPlayerUsername);
        inviteRequest.put("newGame", true);
        ClientHandler.sendRequest(inviteRequest);
    }
    
    private static void invitePlayerResponse(JSONObject response){
        String resStatus = response.get("responseStatus").toString();

        if(resStatus.equals("false")){
            if(currentScene.equals("Multigame")){
                gameAccepted = false;
                Platform.runLater(() -> {multigameCtrl.getWaitingLbl().setText(player.getOpponent() + " is not available."); 
                multigameCtrl.getOkBtn().setDisable(false);});
            }
            else if(currentScene.equals("Loadgame")){
                Platform.runLater(() -> {
                loadgamectrl.getRejectionLabel().setText("Player is not available.");
                loadgamectrl.requestRejectionHandler();});
            }
        }
    }
  
    private static void getInvitationResponse(JSONObject response){
        
        String resStatus = response.get("responseStatus").toString();
        String inviteStatus = response.get("invitationStatus").toString();
        String username = response.get("username").toString();
        String newGame = response.get("newGame").toString();
        
        if(resStatus.equals("true")){
            if(newGame.equals("true"))
                newGameInviteHandler(username , inviteStatus);
            else if (newGame.equals("false"))
                replayGameInviteHandler(username,inviteStatus);
        }
    }
    
    /********************* New game invitation response handler *********************/
    private static void newGameInviteHandler(String username , String inviteStatus){
        if(inviteStatus.equals("true")){
            //System.out.println("invitation to a new game accepted.");
            player.setInvited(false);
            player.setOpponent(username);
            gameAccepted = true;
            if(replay){
                Platform.runLater(() -> {multigameCtrl.getWaitingLbl().setText(username  + " accepted your invitation, Waiting for game to start.");});
            }
            else{
                Platform.runLater(() -> {Invitectrl.getWaitingLbl().setText(username + " accepted your invitation, Waiting for game to start.");});
            }                
        }
        else{
            //System.out.println("invitation to a new game declined.");
            gameAccepted = false;
            if(replay){
                Platform.runLater(() -> {multigameCtrl.getWaitingLbl().setText(username + " declined your invitation."); multigameCtrl.getOkBtn().setDisable(false);});
            }
            else{
                Platform.runLater(() -> {Invitectrl.getWaitingLbl().setText(username + " declined your invitation."); Invitectrl.getOkBtn().setDisable(false);});
            }
        }
    }
    
    /********************* Replay an old game invitation response handler *********************/
    private static void replayGameInviteHandler(String username,String inviteStatus){
        if(inviteStatus.equals("false")){
            //System.out.println("invitation to an old game declined.");
            Platform.runLater(() -> { loadgamectrl.getRejectionLabel().setText("Player rejected your request");
            loadgamectrl.requestRejectionHandler();});
        }
        else if (inviteStatus.equals("true")){
            player.setOpponent(username);
            //System.out.println("invitation to an old game accpeted, waiting game from server.");
        }
    }
    
    /********************* Invitation from another player request handler *********************/
    private static void invitationRequest(JSONObject request){
        String username = request.get("username").toString();
        String newGame = request.get("newGame").toString();
        invitingUsername = username;        
        //System.out.println("invitation recieved from "+ username); 
        ClientHandler.changeScene("Invitation");
        Platform.runLater(()->{
            if(newGame.equals("false")){
                String date = request.get("date").toString();
                invitationCtrl.getInvitationLabel().setText("Player "+username+" is inviting you to replay an old game.\n Date: ["+date+"]\n Do you accept?");
            }
            else{
                invitationCtrl.getInvitationLabel().setText("Player "+username+" is inviting you to a new game.\n Do you accept?");
            }
        });
    }
    /************** respond to an invitation from another player handler **************/
    public static void invitationResponse(String response){
        JSONObject inviteResponse = new JSONObject();
        inviteResponse.put("type", "invitation");
        inviteResponse.put("invitationStatus", response);
        ClientHandler.sendRequest(inviteResponse);
        if(response == "true"){
            player.setOpponent(invitingUsername);
        }
    }
    
    /************************** Start game handler **************************/
    private static void GameStartedResponse(JSONObject response){
        String resStatus = response.get("responseStatus").toString();
        if(resStatus.equals("true")){
            
            if(replay){
                if(player.getInvited()){
                    Platform.runLater(() -> {invitationCtrl.getWaitingLbl().setText("Game established, Start Playing!");
                    invitationCtrl.getStartBtn().setDisable(false);});
                    changeScene("Multigame");

                }
                else{
                    Platform.runLater(() -> {multigameCtrl.getWaitingLbl().setText("Game established, Start Playing!"); 
                    multigameCtrl.getOkBtn().setDisable(false);});
                }
            }
            else{
                if(player.getInvited()){
                    Platform.runLater(() -> {invitationCtrl.getWaitingLbl().setText("Game established, Start Playing!"); 
                    invitationCtrl.getStartBtn().setDisable(false);});
                }
                else{
                    Platform.runLater(() -> {Invitectrl.getWaitingLbl().setText("Game established, Start Playing!"); 
                    Invitectrl.getOkBtn().setDisable(false);});

                }
                changeScene("Multigame");

            }
        }
        else{
            //System.out.println("game failed to start");
        }
    }
    
    /************************** Send a Game Ended request handler **************************/
    public static void gameEndedRequest(String winner, boolean isDraw, String errorMsg){
        JSONObject gameEnded = new JSONObject();
        gameEnded.put("type", "gameEnded");
        gameEnded.put("responseStatus", "true");
        gameEnded.put("username", winner);
        gameEnded.put("isDraw", isDraw);
        gameEnded.put("errorMsg", errorMsg);
        ClientHandler.sendRequest(gameEnded);
        
        isLoaded = false;
    }
    /****************** Quit response from server *****************************/
    private static void gameQuitResponse(JSONObject response){
        
        String resStatus = response.get("responseStatus").toString();
        if(resStatus.equals("true")){
            gameAccepted = false;
            Platform.runLater(() -> {multigameCtrl.getWaitingSubscene().setVisible(true);
            multigameCtrl.getOkBtn().setDisable(false);
            
            multigameCtrl.getWaitingLbl().setText("Client has dropped from the game");});
        }
    }
    
    /****************** Update Score response from server *********************/
    private static void gameEndedResponse(JSONObject response){
        String errormsg = response.get("errorMsg").toString();
        if(errormsg.equals("")){
            String newScore = response.get("score").toString();
            //System.out.println("Updated Your score to : "+newScore);       
            player.setScore(Integer.parseInt(newScore));
        }
        else if(errormsg.equals("clientDropped")){
            gameAccepted = false;
            Platform.runLater(() -> {multigameCtrl.getWaitingSubscene().setVisible(true);
            multigameCtrl.getWaitingLbl().setText("Client has dropped from the game");});
        }
        
        isLoaded = false;
        
    }
    
    /************** Send Move request and Response **************/
    public static void sendMoveRequest(int row, int col){
        //System.out.println("inside send move request");
        JSONObject sendMoveRequest = new JSONObject();
        sendMoveRequest.put("type", "sendMove");
        sendMoveRequest.put("row", (Integer)row);
        sendMoveRequest.put("col", (Integer)col);
        ClientHandler.sendRequest(sendMoveRequest);
    }
    private static void getMoveReponse(JSONObject response){
        int row = Integer.parseInt(response.get("row").toString());
        int col = Integer.parseInt(response.get("col").toString());
        
        Game.CellPosition move = new Game.CellPosition(row, col);
        //System.out.println(move.row);
        //System.out.println(move.col);
        Game.setMoveOfNextPlayer(move);
        
        Platform.runLater(()->{multigameCtrl.secondPlayerMove();});
    }
    
    /************** Save Game request and Response **************/
    public static void saveGameRequest(String nextMove){
        JSONObject saveGame = new JSONObject();
        saveGame.put("type", "saveGame");
        saveGame.put("nextMove", nextMove);
        ClientHandler.sendRequest(saveGame);
        Platform.runLater(() -> {multigameCtrl.getSavingSubscene().setVisible(true);});
    } 
    private static void saveGameResponse(JSONObject response){
        String resStatus = response.get("responseStatus").toString();
        if(resStatus.equals("true")){
            Platform.runLater(() -> {multigameCtrl.getSavingSubscene().setVisible(true);
            multigameCtrl.getSavingLbl().setText("Game saved successfully."); 
            multigameCtrl.getHomtBtn().setDisable(false);});
        }
        else{
//            Platform.runLater(() -> {multigameCtrl.getSavingSubscene().setVisible(true);});
            //System.out.println("failed game save");
        }
    }
    
    /************** Send Chat request and Response **************/
    public static void sendChatRequest(String msg){
        JSONObject sendChat = new JSONObject();
        sendChat.put("type", "sendChat");
        sendChat.put("msg", msg);
        ClientHandler.sendRequest(sendChat);
    }
    private static void sendChatResponse(JSONObject response){
        multigameCtrl.displayOpponentMsg(response.get("msg").toString());
    }
    
    
    /************** Load Game request and Response **************/
    public static void loadGameRequest(String invitedPlayerUsername , Long gameId){
        JSONObject loadGameReq = new JSONObject();
        loadGameReq.put("type", "invitePlayer");
        loadGameReq.put("username", invitedPlayerUsername);
        loadGameReq.put("newGame", false);
        loadGameReq.put("gameId", gameId);
        sendRequest(loadGameReq);
        //System.out.println("Sent a load game request.");
    }
    private static void loadGameResponse(JSONObject response){
        String resStatus = response.get("responseStatus").toString();
        JSONParser parser = new JSONParser();
        if(resStatus.equals("true")){
            JSONArray board = (JSONArray)response.get("gameboard");
            nextMove = response.get("nextMove").toString().charAt(0);
            
            String xPlayer = response.get("xPlayer").toString();
            String oPlayer = response.get("oPlayer").toString();
            char[] board1D = new char[9];
            if(nextMove == 'X'){
                nextPlayer = xPlayer;
            }
            else if(nextMove == 'O'){
                nextPlayer = oPlayer;
            }
            for(int i = 0; i < board.size(); i++){
                board1D[i] = board.get(i).toString().charAt(0);
            }
            gameBoard = Game.convertToTwoDimension(board1D);
            isLoaded = true;
            if(player.getInvited()){
                Platform.runLater(() -> {changeScene("Multigame");});
            }
            else{
                Platform.runLater(() -> {changeScene("Multigame");});
            }
        }
    }
    
    /************** Display list of games in load games scene **************/
    private static void displayGamesList(JSONObject response){
        
        games = FXCollections.observableArrayList ();
        JSONParser parser = new JSONParser();
        gamesFullInfo =(JSONArray) response.get("gamesList");
        JSONObject game = new JSONObject();
        
        for (int i = 0 ; i < gamesFullInfo.size(); i++) {
            
            try {
                game=(JSONObject) parser.parse(gamesFullInfo.get(i).toString());
                games.add((i+1)+". ["+game.get("date").toString()+"] with "+game.get("player").toString());
            } catch (ParseException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Platform.runLater(() -> {loadgamectrl.displayGames(games);});
    }
}
