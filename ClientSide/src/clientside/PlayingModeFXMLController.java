/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientside;

import clientHandler.Game;
import clientHandler.ClientHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author sarahouf
 */
public class PlayingModeFXMLController implements Initializable {
    
    @FXML
    private Button easyBtn;
    @FXML
    private Button mediumBtn;
    @FXML
    private Button hardBtn;
    
    @FXML
    private void easyBtnHandler(ActionEvent event){
        Game.setMode(0);
        ClientHandler.changeScene("Game");
    }
    
    @FXML
    private void mediumBtnHandler(ActionEvent event){
        //Game.setMode(0);
    }
    
    @FXML
    private void hardBtnHandler(ActionEvent event){
        Game.setMode(2);
        ClientHandler.changeScene("Game");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
