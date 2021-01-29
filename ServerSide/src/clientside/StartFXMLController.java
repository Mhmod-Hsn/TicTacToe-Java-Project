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
    private Label userName;
    @FXML
    private Label userScore;
    @FXML
    private Button aboutBtn;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientHandler.setStartCtrl(this);
        updateTable(ClientHandler.getNameList(),ClientHandler.getScoreList(),ClientHandler.getStatusList());
        userName.setText(ClientHandler.getPlayer().getUsername());
        userScore.setText(String.valueOf(ClientHandler.getPlayer().getScore())+" points");
    }    

    @FXML
    private void newgameHandler(MouseEvent event) {
        ClientHandler.changeScene("Newgame");
    }

    @FXML
    private void loadgameHandler(MouseEvent event) {
        JSONObject getGamesRequest = new JSONObject();
        getGamesRequest.put("type","getGames");
        ClientHandler.sendRequest(getGamesRequest);
        ClientHandler.changeScene("Loadgame");
    }

    @FXML
    private void exitHandler(MouseEvent event) {
         ClientHandler.getWindow().close();
    }
    
    public void updateTable(ObservableList<String> name , ObservableList<String> score , ObservableList<String> status){
        playerList.setItems(name);
        scoreList.setItems(score);
        statusList.setItems(status);
    }

    public void updateScore(String newScore){
        userScore.setText(newScore);
    }
    
    private void updateHandler(MouseEvent event) {
        JSONObject updateReq = new JSONObject();
        updateReq.put("type", "updateList");
        ClientHandler.sendRequest(updateReq);
    }

    @FXML
    private void aboutHandler(MouseEvent event) {
        ClientHandler.changeScene("About");
    }
}