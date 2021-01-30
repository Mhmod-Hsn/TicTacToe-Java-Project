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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author ayaha
 */
public class LoadgameFXMLController implements Initializable {

    @FXML
    private ListView<String> playerList;
    @FXML
    private ListView<String> statusList;
    @FXML
    private ListView<String> scoreList;
    @FXML
    private Button backbtn;
    @FXML
    private Label userName;
    @FXML
    private Label userScore;
    @FXML
    private ComboBox<String> gamesComboBox;
    @FXML
    private Button invitePlayer;
    @FXML
    private AnchorPane waitingSubscene;
    @FXML
    private Label waitingLbl;
    @FXML
    private Button proceedBtn;
    @FXML
    private ImageView loadingImg;
    @FXML
    private Label rejectionLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientHandler.setLoadgameCtrl(this);
        waitingSubscene.setVisible(false);
        rejectionLabel.setVisible(false);
        proceedBtn.setDisable(true);
        updateTable(ClientHandler.getNameList(),ClientHandler.getScoreList(),ClientHandler.getStatusList());
        userName.setText(ClientHandler.getPlayer().getUsername());
        userScore.setText(String.valueOf(ClientHandler.getPlayer().getScore())+" points");
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
    
    public void updateScore(String newScore){
        userScore.setText(newScore);
    }
    
    public void displayGames(ObservableList<String> games){
        gamesComboBox.setItems(games);
    }

    @FXML
    private void invitePlayerHandler(MouseEvent event) {
        JSONObject chosenGame;
        if(gamesComboBox.getValue()!=null && !gamesComboBox.getValue().equals("")){
            int gameIndex=Integer.parseInt(gamesComboBox.getValue().split("\\.")[0])-1;
            chosenGame=(JSONObject)ClientHandler.getGames().get(gameIndex);
            Long gameID = (Long)chosenGame.get("gameId");
            ClientHandler.loadGameRequest(chosenGame.get("player").toString(),gameID);
            waitingSubscene.setVisible(true);
        }
    }
    
    public Label getRejectionLabel(){
        return  rejectionLabel;
    }
    
    public void requestRejectionHandler(){
        waitingLbl.setVisible(false);
        loadingImg.setVisible(false);
        rejectionLabel.setVisible(true);
        proceedBtn.setDisable(false);
    }

    @FXML
    private void proceedHandler(MouseEvent event) {
        waitingSubscene.setVisible(false);
        rejectionLabel.setVisible(false);
        proceedBtn.setDisable(true);
        waitingLbl.setVisible(true);
        loadingImg.setVisible(true);
        
    }
}
