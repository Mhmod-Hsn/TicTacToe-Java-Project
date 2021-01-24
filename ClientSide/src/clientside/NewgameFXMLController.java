/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientside;

import clientHandler.ClientHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ayaha
 */
public class NewgameFXMLController implements Initializable {

    @FXML
    private ListView<String> playerList;
    @FXML
    private ListView<String> statusList;
    @FXML
    private ListView<String> scoreList;
    @FXML
    private Button signlebtn;
    @FXML
    private Button multibtn;
    @FXML
    private Button backbtn;
    @FXML
    private Label userName;
    @FXML
    private Label userScore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientHandler.setNewgameCtrl(this);
        updateTable(ClientHandler.getNameList(),ClientHandler.getScoreList(),ClientHandler.getStatusList());
        userName.setText(ClientHandler.getPlayer().getUsername());
        userScore.setText(String.valueOf(ClientHandler.getPlayer().getScore())+" points");
    }    

    @FXML
    private void singleHandler(MouseEvent event) {
        ClientHandler.changeScene("PlayingMode");
    }

    @FXML
    private void multiHandler(MouseEvent event) {
        ClientHandler.changeScene("Invite");
    }

    @FXML
    private void backHandler(MouseEvent event) {
        ClientHandler.changeScene("Start");
    }

    public void updateTable(ObservableList<String> name , ObservableList<String> score , ObservableList<String> status){
        playerList.setItems(name);
        statusList.setItems(status);
        scoreList.setItems(score);
    }
}
