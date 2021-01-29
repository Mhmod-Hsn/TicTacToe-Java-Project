/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientside;

import clientHandler.ClientHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sarahouf
 */
public class InvitationFXMLController implements Initializable {

    @FXML
    private Label invitationLbl;
    @FXML
    private Button acceptBtn;
    @FXML
    private Button rejectBtn;
    @FXML
    private AnchorPane waitingSubscene;
    @FXML
    private Label waitingLbl;
    @FXML
    private Button startGameBtn;
    
    @FXML
    private void acceptBtnHandler(ActionEvent event){
        ClientHandler.invitationResponse("true");
        ClientHandler.getPlayer().setInvited(true);
        waitingSubscene.setVisible(true);
//        ClientHandler.getPlayer().updateStatus("ingame");
//        ClientHandler.changeScene("Multigame");
    }
    
    @FXML
    private void startGameBtnHandler(ActionEvent event){
        ClientHandler.changeScene("Multigame");
    }
    
    @FXML
    private void rejectBtnHandler(ActionEvent event){
        ClientHandler.invitationResponse("false");
        ClientHandler.changeScene("Start");
    }
    @FXML
    public Label getInvitationLabel(){
        //System.out.println("returned invitation labeel");
        return this.invitationLbl;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ClientHandler.setInvitationCtrl(this);
        waitingSubscene.setVisible(false);
        startGameBtn.setDisable(true);
    } 
    
    public Label getWaitingLbl(){
        return this.waitingLbl;
    }
    
    public Button getStartBtn(){
        return this.startGameBtn;
    }

}
