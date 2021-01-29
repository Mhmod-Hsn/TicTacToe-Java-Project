/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientside;

import clientHandler.ClientHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import clientHandler.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sarahouf
 */
public class GameFXMLController implements Initializable {
    
    @FXML
    private Label cell1;
    @FXML
    private Label cell2;
    @FXML
    private Label cell3;
    @FXML
    private Label cell4;
    @FXML
    private Label cell5;
    @FXML
    private Label cell6;
    @FXML
    private Label cell7;
    @FXML
    private Label cell8;
    @FXML
    private Label cell9;
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private AnchorPane resultAnchor;
    @FXML
    private SubScene resultSubscene;
    @FXML
    private Label winnerLabel;
    @FXML
    private Button quitBtn;
    @FXML
    private Button saveBtn;
    Game game;
    char value;
    boolean play;
    MouseEvent event;
    int mode;//0 easy, 1 medium, 2 hard
    boolean finish;
    
    private void playerXHandle(){
        game.setNextMove(1);
        play = false;
        value= 'O';
    }
    
    private void playerOHandle(){
        game.setNextMove(0);
        play = true;
        value= 'X';
    }
    
    private void checkWinOrDraw(){
        int win = game.checkWin();
        if(win == 1){
            finish = true;
            //System.out.println("Player " + value + " won!");
            if(value == 'X'){
                winnerLabel.setText(player1Label.getText() + " won!");
                //TODO
                //update score here if needed
                setSceneVisibility(true);
            }
            else{
                winnerLabel.setText(player2Label.getText() + " won!");
                //TODO
                //update score here if needed
                setSceneVisibility(true);
            }
            
        }
        else if(win == 2){
            finish = true;
            //System.out.println("Its a draw!");
            winnerLabel.setText("It's a draw!");
            setSceneVisibility(true);
        }
        game.printBoard();
    }
    
    @FXML
    private void cell1Handler(MouseEvent event){
        if(!finish && play && "".equals(cell1.getText())){
            cell1.setText(String.valueOf(value));
            game.setCell1(value);            
            play = false;            
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell1.setText(String.valueOf(value));
            game.setCell1(value);
            checkWinOrDraw();
            playerOHandle();     
        }
    }
    
    @FXML
    private void cell2Handler(MouseEvent event){
        if(!finish && play && "".equals(cell2.getText())){
            cell2.setText(String.valueOf(value));
            game.setCell2(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell2.setText(String.valueOf(value));
            game.setCell2(value);
            checkWinOrDraw();
            playerOHandle();       
        }
    }
    
    @FXML
    private void cell3Handler(MouseEvent event){
        if(!finish && play && "".equals(cell3.getText())){
            cell3.setText(String.valueOf(value));
            game.setCell3(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell3.setText(String.valueOf(value));
            game.setCell3(value);
            checkWinOrDraw();
            playerOHandle();    
        }
    }
    
    @FXML
    private void cell4Handler(MouseEvent event){
        if(!finish && play && "".equals(cell4.getText())){
            cell4.setText(String.valueOf(value));
            game.setCell4(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish && !play){
            cell4.setText(String.valueOf(value));
            game.setCell4(value);
            checkWinOrDraw();
            playerOHandle();                
        }
    }
    
    @FXML
    private void cell5Handler(MouseEvent event){
        if(!finish && play && "".equals(cell5.getText())){
            cell5.setText(String.valueOf(value));
            game.setCell5(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell5.setText(String.valueOf(value));
            game.setCell5(value);
            checkWinOrDraw();
            playerOHandle();       
        }
    }
    
    @FXML
    private void cell6Handler(MouseEvent event){
        if(!finish && play && "".equals(cell6.getText())){
            cell6.setText(String.valueOf(value));
            game.setCell6(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell6.setText(String.valueOf(value));
            game.setCell6(value);
            checkWinOrDraw();
            playerOHandle();
                
        }
    }
    
    @FXML
    private void cell7Handler(MouseEvent event){
        if(!finish && play && "".equals(cell7.getText())){
            cell7.setText(String.valueOf(value));
            game.setCell7(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell7.setText(String.valueOf(value));
            game.setCell7(value);
            checkWinOrDraw();
            playerOHandle();
                
        }
    }
    
    @FXML
    private void cell8Handler(MouseEvent event){
        if(!finish && play && "".equals(cell8.getText())){
            cell8.setText(String.valueOf(value));
            game.setCell8(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell8.setText(String.valueOf(value));
            game.setCell8(value);
            checkWinOrDraw();
            playerOHandle();                
        }

    }
    
    @FXML
    private void cell9Handler(MouseEvent event){
        if(!finish && play && "".equals(cell9.getText())){
            cell9.setText(String.valueOf(value));
            game.setCell9(value);
            checkWinOrDraw();
            playerXHandle();
            computerPlay();
        }
        else if(!finish &&!play){
            cell9.setText(String.valueOf(value));
            game.setCell9(value);
            checkWinOrDraw();
            playerOHandle();
                
        }
    }
    
    @FXML
    private void computerPlay(){
        if(!finish){
            Game.CellPosition resultCell = null;
            if(mode == 0){
                resultCell = game.getRandomCell();
            }
            else if(mode == 2){
                resultCell = game.getBestMove();
            }

            if(resultCell.row == 0){
                switch (resultCell.col) {
                    case 0:
                        cell1Handler(event);
                        break;
                    case 1:
                        cell2Handler(event);
                        break;
                    case 2:
                        cell3Handler(event);
                        break;
                    default:
                        break;
                }
            }
            else if(resultCell.row == 1){
                switch (resultCell.col) {
                    case 0:
                        cell4Handler(event);
                        break;
                    case 1:
                        cell5Handler(event);
                        break;
                    case 2:
                        cell6Handler(event);
                        break;
                    default:
                        break;
                }
            }
            else if(resultCell.row == 2){
                switch (resultCell.col) {
                    case 0:
                        cell7Handler(event);
                        break;
                    case 1:
                        cell8Handler(event);
                        break;
                    case 2:
                        cell9Handler(event);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    private void setSceneVisibility(Boolean visible){
        if(visible){
            resultAnchor.setVisible(true);
        }
        else{
            resultAnchor.setVisible(false);
        }
    }
    @FXML
    private void retryBtnHandler(ActionEvent event){
        game.clearBoard();
        game.setNextMove(0);
        Game.setMovesCount(0);
        value = 'X';
        play = true;
        finish = false;
        clearCells();
        setSceneVisibility(false);
    }
    @FXML
    private void exitBtnHandler(ActionEvent event){
        //TODO
        //ClientHandler.getPlayer().updateStatus("offline"); needed or not???
        Platform.exit();
    }
    @FXML
    private void backBtnHandler(ActionEvent event){
        setSceneVisibility(false);
    }
    
    @FXML
    private void quitBtnHandler(ActionEvent event){
        ClientHandler.getPlayer().updateStatus("online");
        ClientHandler.changeScene("Start");//should be the scene for starting a game
    }
    
    @FXML
    private void saveBtnHandler(ActionEvent event){
        
    }
    @FXML
    private void clearCells(){
        cell1.setText("");
        cell2.setText("");
        cell3.setText("");
        cell4.setText("");
        cell5.setText("");
        cell6.setText("");
        cell7.setText("");
        cell8.setText("");
        cell9.setText("");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSceneVisibility(false);
        player1Label.setText(ClientHandler.getPlayer().getUsername());
        player2Label.setText("Computer");
        
        mode = Game.getMode(); //should get mode from the previous scene
        game = new Game();
        game.setNextMove(0);
        value = 'X';
        play = true;
        finish = false;
        ////System.out.println(game.getBoard().length);
      //  th.start();
    }    

    @FXML
    private void sendHandler(MouseEvent event) {
    }

    @FXML
    private void playAgainHandler(ActionEvent event) {
    }

    @FXML
    private void exitHandler(ActionEvent event) {
    }
    
}

