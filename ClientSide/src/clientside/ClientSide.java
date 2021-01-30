package clientside;

import clientHandler.ClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class ClientSide extends Application {
    
    boolean connected = false;
    Thread readerThread;
    Parent root;
    
    @Override
    public void init()
    {
        connected = ClientHandler.connectToServer();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        if (connected){

            root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
            
            readerThread = new Thread(new ClientHandler.recieveRespone());
            readerThread.start();
        }
        else{

            root = FXMLLoader.load(getClass().getResource("ConfailedFXML.fxml"));
        }

        Scene scene = new Scene(root);
        ClientHandler.setWindow(stage);
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop(){
        
        if(connected){
            JSONObject msg = new JSONObject();
            if(ClientHandler.getCurrentScene().equals("Multigame")){
                msg.put("type","gameQuit");
                msg.put("errorMsg", "clientDropped");
                ClientHandler.sendRequest(msg);
            }
            msg = new JSONObject();
            msg.put("type","signout");
            ClientHandler.sendRequest(msg);
            
            ClientHandler.closeCon();
            readerThread.stop();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
