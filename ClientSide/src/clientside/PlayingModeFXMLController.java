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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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
    private Button backbtn;
    @FXML
    private ListView<String> playerList;
    @FXML
    private ListView<String> statusList;
    @FXML
    private ListView<String> scoreList;
    @FXML
    private Label userName;
    @FXML
    private Label userScore;
    
    @FXML
    private void easyBtnHandler(ActionEvent event){
        Game.setMode(0);
        ClientHandler.getPlayer().updateStatus("busy");
        ClientHandler.changeScene("Game");
    }
    
    @FXML
    private void mediumBtnHandler(ActionEvent event){
        //Game.setMode(0);
    }
    
    @FXML
    private void hardBtnHandler(ActionEvent event){
        Game.setMode(2);
        ClientHandler.getPlayer().updateStatus("busy");
        ClientHandler.changeScene("Game");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientHandler.setPlaymodeCtrl(this);
        updateTable(ClientHandler.getNameList(),ClientHandler.getScoreList(),ClientHandler.getStatusList());
        userName.setText(ClientHandler.getPlayer().getUsername());
        userScore.setText(String.valueOf(ClientHandler.getPlayer().getScore())+" points");
    }    

    @FXML
    private void backHandler(MouseEvent event) {
        ClientHandler.changeScene("Newgame");
    }
    
    public void updateTable(ObservableList<String> name , ObservableList<String> score , ObservableList<String> status){
        playerList.setItems(name);
        statusList.setItems(status);
        scoreList.setItems(score);
    }
}
