/*
 * Welcome Pane to welcome user again to the game once he logins.
 */
package clientside;

import clientHandler.ClientHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class WelcomeFXMLController implements Initializable {
    @FXML
    private ImageView WelcomeImg;
    @FXML
    private Label username;
    @FXML
    private Button startPlaying;

    // StartPlaying button handler switch the scene to StartPane
    @FXML
    private void startPlayingHandler(MouseEvent event) {
        ClientHandler.changeScene("Start");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setText(ClientHandler.getPlayer().getUsername());
    }    
}
