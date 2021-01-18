package clientside;

import clientHandler.ClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientSide extends Application {
    
    boolean connected = false;
    Parent root;
    
    @Override
    public void init()
    {
        connected = ClientHandler.connectToServer();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        if (connected){
            System.out.println("Connected To server.");
            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Thread readerThread = new Thread(new ClientHandler.recieveRespone());
            readerThread.start();
        }
        else{
            System.out.println("Connection Failed.");
            root = FXMLLoader.load(getClass().getResource("confailedFXML.fxml"));
        }
        //Parent root = new LoginFXMLDocumentBase();
        Scene scene = new Scene(root);
        ClientHandler.setWindow(stage);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
