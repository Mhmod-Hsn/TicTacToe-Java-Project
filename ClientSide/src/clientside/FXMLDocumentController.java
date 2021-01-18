/*
 * Login Pane which is the first pane to appear after starting the application.
 */
package clientside;

import clientHandler.ClientHandler;
import clientHandler.Player;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btnSignup;
    @FXML
    private Button btnLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label passLbl;
    @FXML
    private Label userLbl;
    @FXML
    private Label warningLbl;
    
    Player player;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player = new Player();
        ClientHandler.setLoginCtrl(this);
    }    

    @FXML
    private void loginHandler(MouseEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();                
                
        boolean checkUname = player.checkUsername(username);
        boolean checkPass = player.checkPassword(password);

        if(!checkUname){
            warningLbl.setText("Invalid username format");
        }
        else if(!checkPass){
            warningLbl.setText("Invalid password format, should be between 6 and 20 characters");
        }
        else{
            warningLbl.setText("");
                    
            player.setUsername(username);
            ClientHandler.setPlayer(player); 
                    
            //Generate a new login request to the server.
            Map<String, String> map = new HashMap<>();
            map.put("type", "signin");
            map.put("username", username);
            map.put("password", password);
            ClientHandler.sendRequest(map);
        }                
    }

    @FXML
    private void signupHandler(MouseEvent event) {
        
        String username=txtUsername.getText();
        String password=txtPassword.getText();
                
        boolean checkUname = player.checkUsername(username);
        boolean checkPass = player.checkPassword(password);                

        if(!checkUname){
            warningLbl.setText("Invalid username format");
        }
        else if(!checkPass){
            warningLbl.setText("Invalid password format, should be between 6 and 20 characters");
        }
        else { 
            warningLbl.setText("");
            player.setUsername(username);
            ClientHandler.setPlayer(player);
                    
            //Generate a new sign up request to the server.
            Map<String, String> map = new HashMap<>();
            map.put("type", "signup");
            map.put("username", username);
            map.put("password", password);
            ClientHandler.sendRequest(map);
        }
    }   
    
    //getter for warning label
    public Label getLabel() {
    return this.warningLbl;
    }
}
