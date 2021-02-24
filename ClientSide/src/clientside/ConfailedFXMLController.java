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

        if(ClientHandler.connectToServer())
        {

            Thread readerThread = new Thread(new ClientHandler.recieveRespone());
            readerThread.start();
            ClientHandler.changeScene("Login");
        }
    }
    
}
