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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
    
    /** Send a new request to the server
     * @param jsonMsg
     */
    public static void sendRequest(JSONObject jsonMsg)
    {
        try {
            ps.writeUTF(jsonMsg.toJSONString());
        } catch (IOException ex) {
        } 
    }
    
    /* Receive requests from the server and send them to the handler */
    public static class recieveRespone implements Runnable {
        boolean running = true;
        String response;
        @Override
        public void run(){
            while (running) {
                try {
                    response = ds.readUTF();
                    if (response != null) {
                        handleResponse(response);
                    }
                } catch (IOException ex) {
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
        }
    }

    /** Change displayed scene to the given FXML.
     * @param newScene
     */
    @FXML
    public static void changeScene(String newScene)
    {   
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
    public static ObservableList<String> getNameList (){
        return name;
    }  
    public static ObservableList<String> getStatusList(){
        return status;
    }  
    public static ObservableList<String> getScoreList(){
        return score;
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
            Platform.runLater(() -> {loginctrl.getLabel().setText(warning);});
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
        
        for (int i = 0 ; i < list.size(); i++) {
            try {
                JSONplayer  = (JSONObject) parser.parse(list.get(i).toString());
                name.add(JSONplayer.get("username").toString());
                score.add(JSONplayer.get("score").toString());
                status.add(JSONplayer.get("status").toString());
            } catch (ParseException ex) {
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
        JSONObject inviteRequest = new JSONObject();
        inviteRequest.put("type", "invitePlayer");
        inviteRequest.put("username", invitedPlayerUsername);
        inviteRequest.put("newGame", true);
        ClientHandler.sendRequest(inviteRequest);
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
            }
        }
        Platform.runLater(() -> {loadgamectrl.displayGames(games);});
    }
}
