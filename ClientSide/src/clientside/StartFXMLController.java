package clientside;

import clientHandler.ClientHandler;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONObject;

public class StartFXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private ListView<String> playerList;
    @FXML
    private ListView<String> statusList;
    @FXML
    private ListView<String> scoreList;
    @FXML
    private Button newbtn;
    @FXML
    private Button loadbtn;
    @FXML
    private Button exitbtn;
    @FXML
    private Button update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientHandler.setStartCtrl(this);
    }    

    @FXML
    private void newgameHandler(MouseEvent event) {
        ClientHandler.changeScene("Newgame");
    }

    @FXML
    private void loadgameHandler(MouseEvent event) {
        ClientHandler.changeScene("Loadgame");
    }

    @FXML
    private void exitHandler(MouseEvent event) {
    }
    
    public void updateTable(ObservableList<String> name , ObservableList<String> score , ObservableList<String> status){
        playerList.setItems(name);
        statusList.setItems(status);
        scoreList.setItems(score);
    }

    @FXML
    private void updateHandler(MouseEvent event) {
        JSONObject updateReq = new JSONObject();
        updateReq.put("type", "updateList");
        ClientHandler.sendRequest(updateReq);
    }
}