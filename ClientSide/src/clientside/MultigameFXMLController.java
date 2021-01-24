/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientside;

import clientHandler.ClientHandler;
import clientHandler.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sarahouf
 */
public class MultigameFXMLController implements Initializable {
    
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
    char player1Value;
    char player2Value;
    boolean play;
    MouseEvent event;
    int mode;//0 easy, 1 medium, 2 hard
    boolean finish;
    
    private void player1Handle(){
//        game.setNextMove(1);
        //play = false;
        toggleNextMove();
        togglePlay();
//        secondPlayerMove();
//        value= 'O';
    }
    
    private void player2Handle(){
       toggleNextMove();
       togglePlay();
    }
    
    private void checkWinOrDraw(){
        int win = game.checkWin();
        if(win == 1){
            finish = true;
            System.out.println("Player " + player1Value + " won!");
            if(player1Value == 'X'){
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
            System.out.println("Its a draw!");
            winnerLabel.setText("It's a draw!");
            setSceneVisibility(true);
        }
        game.printBoard();
    }
    
    @FXML
    private void cell1Handler(MouseEvent event){
        if(play && "".equals(cell1.getText())){
            cell1.setText(String.valueOf(player1Value));
            game.setCell1(player1Value);
            ClientHandler.sendMoveRequest(0, 0);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell1.setText(String.valueOf(value));
//            game.setCell1(value);
//            checkWinOrDraw();
//            playerOHandle();     
//        }
    }
    
    @FXML
    private void cell2Handler(MouseEvent event){
        if(play && "".equals(cell2.getText())){
            cell2.setText(String.valueOf(player1Value));
            game.setCell2(player1Value);
            ClientHandler.sendMoveRequest(0, 1);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell2.setText(String.valueOf(value));
//            game.setCell2(value);
//            checkWinOrDraw();
//            playerOHandle();       
//        }
    }
    
    @FXML
    private void cell3Handler(MouseEvent event){
        if(play && "".equals(cell3.getText())){
            cell3.setText(String.valueOf(player1Value));
            game.setCell3(player1Value);
            ClientHandler.sendMoveRequest(0, 2);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell3.setText(String.valueOf(value));
//            game.setCell3(value);
//            checkWinOrDraw();
//            playerOHandle();    
//        }
    }
    
    @FXML
    private void cell4Handler(MouseEvent event){
        if(play && "".equals(cell4.getText())){
            cell4.setText(String.valueOf(player1Value));
            game.setCell4(player1Value);
            ClientHandler.sendMoveRequest(1, 0);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell4.setText(String.valueOf(value));
//            game.setCell4(value);
//            checkWinOrDraw();
//            playerOHandle();                
//        }
    }
    
    @FXML
    private void cell5Handler(MouseEvent event){
        if(play && "".equals(cell5.getText())){
            cell5.setText(String.valueOf(player1Value));
            game.setCell5(player1Value);
            ClientHandler.sendMoveRequest(1, 1);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell5.setText(String.valueOf(value));
//            game.setCell5(value);
//            checkWinOrDraw();
//            playerOHandle();       
//        }
    }
    
    @FXML
    private void cell6Handler(MouseEvent event){
        if(play && "".equals(cell6.getText())){
            cell6.setText(String.valueOf(player1Value));
            game.setCell6(player1Value);
            ClientHandler.sendMoveRequest(1, 2);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell6.setText(String.valueOf(value));
//            game.setCell6(value);
//            checkWinOrDraw();
//            playerOHandle();
//                
//        }
    }
    
    @FXML
    private void cell7Handler(MouseEvent event){
        if(play && "".equals(cell7.getText())){
            cell7.setText(String.valueOf(player1Value));
            game.setCell7(player1Value);
            ClientHandler.sendMoveRequest(2, 0);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell7.setText(String.valueOf(value));
//            game.setCell7(value);
//            checkWinOrDraw();
//            playerOHandle();
//                
//        }
    }
    
    @FXML
    private void cell8Handler(MouseEvent event){
        if(play && "".equals(cell8.getText())){
            cell8.setText(String.valueOf(player1Value));
            game.setCell8(player1Value);
            ClientHandler.sendMoveRequest(2, 1);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell8.setText(String.valueOf(value));
//            game.setCell8(value);
//            checkWinOrDraw();
//            playerOHandle();                
//        }

    }
    
    @FXML
    private void cell9Handler(MouseEvent event){
        if(play && "".equals(cell9.getText())){
            cell9.setText(String.valueOf(player1Value));
            game.setCell9(player1Value);
            ClientHandler.sendMoveRequest(2, 2);
            checkWinOrDraw();
            player1Handle();
        }
//        else if(!play){
//            cell9.setText(String.valueOf(value));
//            game.setCell9(value);
//            checkWinOrDraw();
//            playerOHandle();
//                
//        }
    }
    
    private void setSceneVisibility(Boolean visible){
        if(visible){
            resultAnchor.setVisible(true);
        }
        else{
            resultAnchor.setVisible(false);
        }
    }
    
//    private void retryBtnHandler(ActionEvent event){
//        game.clearBoard();
//        game.setNextMove(0);
//        Game.setMovesCount(0);
//        player1Value = 'X';
//        play = true;
//        finish = false;
//        clearCells();
//        setSceneVisibility(false);
//    }
    
    private void exitBtnHandler(ActionEvent event){
        //TODO
        //ClientHandler.getPlayer().updateStatus("offline"); needed or not???
        Platform.exit();
    }
    
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
    
    private void togglePlay(){
        if(play == true){
            play = false;
        }
        else{
            play = true;
        }
        
        System.out.println("play: " + play);
    }
    
    private void toggleNextMove(){
        if(game.getNextMove() == 0){
            game.setNextMove(1);
        }
        else{
            game.setNextMove(0);
        }
    }
    
    private void setPlayerData(){
        if(ClientHandler.getPlayer().getInvited()){
            play = false;
            player1Value = 'O';
            player2Value = 'X';
            player1Label.setText(ClientHandler.getPlayer().getOpponent());
            player2Label.setText(ClientHandler.getPlayer().getUsername());
        }
        else{
            play = true;
            player1Value = 'X';
            player2Value = 'O';
            player1Label.setText(ClientHandler.getPlayer().getUsername());
            player2Label.setText(ClientHandler.getPlayer().getOpponent());
        }
    }
    
    public void secondPlayerMove(){
        if(!finish){
            
            //get cell move of the second player
            Game.CellPosition resultCell = Game.getMoveOfNextPlayer();
            System.out.println(resultCell.row);
            System.out.println(resultCell.col);
            if(resultCell.row == 0){
                switch (resultCell.col) {
                    case 0:
                        cell1.setText(String.valueOf(player2Value));
                        game.setCell1(player2Value);
                        player2Handle();
                        break;
                    case 1:
                        cell2.setText(String.valueOf(player2Value));
                        game.setCell2(player2Value);
                        player2Handle();
                        break;
                    case 2:
                        cell3.setText(String.valueOf(player2Value));
                        game.setCell3(player2Value);
                        player2Handle();
                        break;
                    default:
                        break;
                }
            }
            else if(resultCell.row == 1){
                switch (resultCell.col) {
                    case 0:
                        cell4.setText(String.valueOf(player2Value));
                        game.setCell4(player2Value);
                        player2Handle();
                        break;
                    case 1:
                        cell5.setText(String.valueOf(player2Value));
                        game.setCell5(player2Value);
                        player2Handle();
                        break;
                    case 2:
                        cell6.setText(String.valueOf(player2Value));
                        game.setCell6(player2Value);
                        player2Handle();
                        break;
                    default:
                        break;
                }
            }
            else if(resultCell.row == 2){
                switch (resultCell.col) {
                    case 0:
                        cell7.setText(String.valueOf(player2Value));
                        game.setCell7(player2Value);
                        player2Handle();
                        break;
                    case 1:
                        cell8.setText(String.valueOf(player2Value));
                        game.setCell8(player2Value);
                        player2Handle();
                        break;
                    case 2:
                        cell9.setText(String.valueOf(player2Value));
                        game.setCell9(player2Value);
                        player2Handle();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setSceneVisibility(false);
        ClientHandler.setMultigameCtrl(this);
        
        mode = Game.getMode(); //should get mode from the previous scene
        game = new Game();
        game.setNextMove(0);
        
        setPlayerData();
        
        System.out.println("play: " + play);
        
        finish = false;
    }   
    @FXML
    private void sendHandler(MouseEvent event) {
    }

    @FXML
    private void playAgainHandler(ActionEvent event) {
        game.clearBoard();
        game.setNextMove(0);
        Game.setMovesCount(0);
        setPlayerData();
        finish = false;
        clearCells();
        setSceneVisibility(false);
    }

    @FXML
    private void exitHandler(ActionEvent event) {
        ClientHandler.getPlayer().updateStatus("online");
        ClientHandler.changeScene("Start");
    }
    
}
