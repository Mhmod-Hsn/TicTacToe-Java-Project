/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientside;

import clientHandler.ClientHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ayaha
 */
public class InviteFXMLController implements Initializable {

    @FXML
    private Button invite;
    @FXML
    private Button back;
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
    private ComboBox<String> inviteBox;
    @FXML
    private AnchorPane waitingSubscene;
    @FXML
    private Label waitingLbl;
    @FXML
    private Button okBtn;
    
    private ObservableList<String> OnlinePlayers;
    

    public InviteFXMLController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int index=0;
        ClientHandler.setInviteCtrl(this);
        waitingSubscene.setVisible(false);
        okBtn.setDisable(true);
        
        inviteBox.setPromptText("Select a player");
        userName.setText(ClientHandler.getPlayer().getUsername());
        userScore.setText(String.valueOf(ClientHandler.getPlayer().getScore())+" points");
        updateTable(ClientHandler.getNameList() , ClientHandler.getScoreList() , ClientHandler.getStatusList());
    }    

    @FXML
    private void inviteHandler(MouseEvent event) {

        if(inviteBox.getValue() != null && !inviteBox.getValue().equals("")){
            
            ClientHandler.invitePlayerRequest(inviteBox.getValue());
            waitingLbl.setText("Please wait for opponent response.");
            waitingSubscene.setVisible(true);
        }
    }
    
    @FXML
    private void okBtnHandler(ActionEvent event){
        if(ClientHandler.getGameAccepted()){
            ClientHandler.changeScene("Multigame");
        }
        else{
            waitingSubscene.setVisible(false);
        }
    }

    @FXML
    private void backHandler(MouseEvent event) {
        ClientHandler.changeScene("Newgame");
    }
    
    public void updateTable(ObservableList<String> name , ObservableList<String> score , ObservableList<String> status){
        int index=0;
        playerList.setItems(name);
        statusList.setItems(status);
        scoreList.setItems(score);
        OnlinePlayers = FXCollections.observableArrayList ();
        for (String player : name){
            if( "online".equals(status.get(index)) && !player.equals(userName.getText())){
                OnlinePlayers.add(player);
            }
            index++;
        }
        inviteBox.setItems(OnlinePlayers);
    }
    
    public Label getWaitingLbl(){
        return this.waitingLbl;
    }
    
    public Button getOkBtn(){
        return this.okBtn;
    }
    
    public void updateScore(String newScore){
        userScore.setText(newScore);
    }
}
