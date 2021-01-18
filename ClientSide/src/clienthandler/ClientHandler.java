/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientHandler;

import clientside.ClientSide;
import clientside.FXMLDocumentController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    private static DataOutputStream dOut;
    private static Stage window;
    private static Player player;
    private static DataOutputStream ps;
    private static FXMLDocumentController loginctrl;
     
    public ClientHandler(){  
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
//            ps = new PrintStream(clientSocket.getOutputStream());
            
        } catch (IOException ex) {
            res = false;
        }
        return res;
    }
    
    /** Close the connection with the server.
     */
    public static void closeCon(){
        try{
            dOut.close();
            ds.close();
            clientSocket.close();
        }
        catch(IOException ex){
            System.out.println("No connection exists to close.");
        }
    }
    
    /** Send a new request to the server
     ** @param fields. : a map object containing the request attributes.
     */
    public static void sendRequest(Map<String, String> fields)
    {
        System.out.println(fields);
        JSONObject jsonMsg = new JSONObject();
        jsonMsg.putAll(fields);
        try {
            ps.writeUTF(jsonMsg.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Sent a new request.");
    }
    
    /* Receive requests from the server and send them to the handler */
    public static class recieveRespone implements Runnable {
        boolean running = true;
        String response;
        @Override
        public void run(){
            System.out.println("Recieve Response Thread has Started.");
            while (running) {
                try {
                    response = ds.readUTF();
                    if (response != null) {
                        handleResponse(response);
                    }
                } catch (IOException ex) {
                    System.out.println("Connection is down.");
                    running=false;
                    changeScene("ConfailedFXML");
                }
            }
        }
    }
    
    /** Handle responses according to its type.
     * @param response : received response from the server.
     */
    public static void handleResponse(String response)
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
            }
        } catch (ParseException ex) {
            System.out.println("Exception in handle response.");
        }
    }

    /** Change displayed scene to the given FXML.
     * @param fxml.
     */
    @FXML
    public static void changeScene(String fxml)
    {   
        System.out.println("Changing scene to:"+fxml);
        Platform.runLater(() -> {   
            try {
                Parent root = FXMLLoader.load(ClientSide.class.getResource(fxml+".fxml"));
                Scene scene = new Scene(root);
                window.setScene(scene);
                window.setResizable(false);
                window.show();
            } catch (IOException ex) {
                System.out.println("Exception in change scene function.");
            }
        });
    }
    
    /** Set the window to the application stage to be able to access and change it.
     * @param stage
     */
    public static void setWindow(Stage stage) {
        window = stage;
    }
    public static Stage getWindow() {
        return window;
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
    
    /** Set the login scene controller to be able to access and change it.
     * @param ctrl 
     */
    public static void setLoginCtrl(FXMLDocumentController ctrl) {
        loginctrl=ctrl;
    }

    /** Login response handler.
     * @param response : response of login requests including signup and signin.
     */
    public static void Login(JSONObject response)
    {
        String request = response.get("type").toString();
        String resStatus = response.get("responseStatus").toString();
        if(resStatus.equals("true")){
            System.out.println(response);
//            player.setId( Integer.parseInt(response.get("id").toString()));
            player.setScore(Integer.parseInt(response.get("score").toString()));
            player.setUsername(response.get("username").toString());
            player.setStatus(response.get("status").toString());
            changeScene("WelcomeFXML");
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
            System.out.println("Error :"+warning);
            Platform.runLater(() -> {loginctrl.getLabel().setText(warning);});
        }
    }
}