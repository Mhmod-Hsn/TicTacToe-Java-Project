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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    @FXML
    private AnchorPane savingSubscene;
    @FXML
    private Label savingLbl;
    @FXML
    private Button homeBtn;
    @FXML
    private AnchorPane waitingSubscene;
    @FXML
    private Label waitingLbl;
    @FXML
    private Button okBtn;
    @FXML
    private TextArea chatBox;
    @FXML
    private TextArea msgBox;
    
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
                
                if(player1Label.getText().equals(ClientHandler.getPlayer().getUsername()))
                {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        System.out.println("Not sleep.");
                    }
                    
                    ClientHandler.gameEndedRequest(player1Label.getText());   
                }

                winnerLabel.setText(player1Label.getText() + " won!");
                //TODO
                setSceneVisibility(true);
            
            }
            else{
                
                if(player2Label.getText().equals(ClientHandler.getPlayer().getUsername()))
                {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        System.out.println("Not sleep.");
                    }
                    
                    ClientHandler.gameEndedRequest(player2Label.getText());   
                }
                winnerLabel.setText(player2Label.getText() + " won!");
                //TODO
                setSceneVisibility(true);
            }
            
        }
        else if(win == 2){
            
            if(player1Value == 'X'){
                
                if(player1Label.getText().equals(ClientHandler.getPlayer().getUsername())){
                    
                    try {
                            Thread.sleep(500);
                        }catch (InterruptedException ex) {
                            System.out.println("Not sleep.");
                        }
                    ClientHandler.gameEndedRequest(player1Label.getText());   
                }
             }

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
        ClientHandler.setReplay(false);
        ClientHandler.getPlayer().updateStatus("online");
        ClientHandler.changeScene("Start");//should be the scene for starting a game
    }
    
    @FXML
    private void saveBtnHandler(ActionEvent event){
        ClientHandler.saveGameRequest(game.getNextMove());
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
            player1Label.setText(ClientHandler.getPlayer().getUsername());
            player2Label.setText(ClientHandler.getPlayer().getOpponent());
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
                        checkWinOrDraw();
                        break;
                        
                    case 1:
                        cell2.setText(String.valueOf(player2Value));
                        game.setCell2(player2Value);
                        player2Handle();
                        checkWinOrDraw();
                        break;
                        
                    case 2:
                        cell3.setText(String.valueOf(player2Value));
                        game.setCell3(player2Value);
                        player2Handle();
                        checkWinOrDraw();
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
                        checkWinOrDraw();
                        break;
                    case 1:
                        cell5.setText(String.valueOf(player2Value));
                        game.setCell5(player2Value);
                        player2Handle();
                        checkWinOrDraw();
                        break;
                    case 2:
                        cell6.setText(String.valueOf(player2Value));
                        game.setCell6(player2Value);
                        player2Handle();
                        checkWinOrDraw();
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
                        checkWinOrDraw();
                        break;
                    case 1:
                        cell8.setText(String.valueOf(player2Value));
                        game.setCell8(player2Value);
                        player2Handle();
                        checkWinOrDraw();
                        break;
                    case 2:
                        cell9.setText(String.valueOf(player2Value));
                        game.setCell9(player2Value);
                        player2Handle();
                        checkWinOrDraw();
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

        ClientHandler.setInGameScene(true);
        setSceneVisibility(false);
        ClientHandler.setMultigameCtrl(this);
        savingSubscene.setVisible(false);
        homeBtn.setDisable(true);
        waitingSubscene.setVisible(false);
        okBtn.setDisable(true);
        chatBox.appendText(String.format("\n"));
        
        //should get mode from the previous scene
        //mode = Game.getMode(); 
        game = new Game();
        game.setNextMove(0);
        
        setPlayerData();
        
        System.out.println("play: " + play);
        
        finish = false;
    }   
    @FXML
    private void sendHandler(MouseEvent event) {
        
        if(!msgBox.getText().equals(""))
        {
            ClientHandler.sendChatRequest(msgBox.getText());
            chatBox.appendText("[You]: "+msgBox.getText()+String.format("\n"));
            msgBox.setText("");
        }
    }

    @FXML
    private void playAgainHandler(ActionEvent event) {
        ClientHandler.invitePlayerRequest(ClientHandler.getPlayer().getOpponent());
        ClientHandler.setReplay(true);
        waitingSubscene.setVisible(true);
    }

    @FXML
    private void exitHandler(ActionEvent event) {
//        ClientHandler.getPlayer().updateStatus("online");
        ClientHandler.setReplay(false);
        ClientHandler.changeScene("Start");
    }
    
    @FXML
    private void homeBtnHandler(ActionEvent event){
        ClientHandler.changeScene("Start");
    }
    
    @FXML
    private void okBtnHandler(ActionEvent event){
        if(ClientHandler.getGameAccepted()){
            game.clearBoard();
            game.setNextMove(0);
            Game.setMovesCount(0);
            setPlayerData();
            finish = false;
            clearCells();
            setSceneVisibility(false);
            waitingSubscene.setVisible(false);
            okBtn.setDisable(true);
            ClientHandler.setReplay(false);
        }
        else{
            ClientHandler.changeScene("Start");
        }
    }
    
    public Label getWaitingLbl(){
        return this.waitingLbl;
    }
    
    public Label getSavingLbl(){
        return this.savingLbl;
    }
    
    public Button getOkBtn(){
        return this.okBtn;
    }
    
    public Button getHomtBtn(){
        return this.homeBtn;
    }
    
    public AnchorPane getSavingSubscene(){
        return this.savingSubscene;
    }
   
    public void displayOpponentMsg(String msg){
        chatBox.appendText("["+ClientHandler.getPlayer().getOpponent()+"]: "+msg+String.format("\n"));
    }
    
}
