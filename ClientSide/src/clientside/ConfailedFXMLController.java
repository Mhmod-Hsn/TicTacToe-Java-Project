package clientside;

import clientHandler.ClientHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ConfailedFXMLController implements Initializable {

    @FXML
    private Button tryagain;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void tryagainHandler(MouseEvent event) {
        System.out.println("Trying to connect again.");
        if(ClientHandler.connectToServer())
        {
            System.out.println("Connected successfully.");
            Thread readerThread = new Thread(new ClientHandler.recieveRespone());
            readerThread.start();
            ClientHandler.changeScene("FXMLDocument");
        }
        else{
            System.out.println("Failed to Connect.");
        }
    }
    
}
