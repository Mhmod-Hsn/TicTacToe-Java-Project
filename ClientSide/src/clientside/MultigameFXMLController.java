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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;

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
    public TextArea msgBox;
    
    Game game;
    char player1Value;
    char player2Value;
    boolean play;
    MouseEvent event;
    int mode;//0 easy, 1 medium, 2 hard
    boolean finish;
    boolean isDraw;

    
    private void player1Handle(){
        toggleNextMove();
        togglePlay();
    }
    
    private void player2Handle(){
       toggleNextMove();
       togglePlay();
    }
    
    private void checkWinOrDraw(){

        int win = game.checkWin();

        if(win == 1){
            finish = true;
            
            if(player1Value == 'X'){
                
                if(player1Label.getText().equals(ClientHandler.getPlayer().getUsername()))
                {
                    try {
                        Thread.sleep(300);
                           
                        ClientHandler.gameEndedRequest(player1Label.getText(), isDraw, "");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MultigameFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                winnerLabel.setText(player1Label.getText() + " won!");
                setSceneVisibility(true);
            
            }
            else{
                
                if(player2Label.getText().equals(ClientHandler.getPlayer().getUsername()))
                {
                    try {
                        Thread.sleep(300);
                           
                        ClientHandler.gameEndedRequest(player2Label.getText(), isDraw, "");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MultigameFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                winnerLabel.setText(player2Label.getText() + " won!");
                setSceneVisibility(true);
            }
            
        }
        else if(win == 2){
            
            if(player1Value == 'X'){
                
                if(player1Label.getText().equals(ClientHandler.getPlayer().getUsername())){
                    try {
                        isDraw = true;
                        Thread.sleep(300);   
                        ClientHandler.gameEndedRequest(player1Label.getText(), isDraw, "");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MultigameFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
             }

            finish = true;

            winnerLabel.setText("It's a draw!");
            setSceneVisibility(true);

        }
        game.printBoard();
    }
    
    @FXML
    private void cell1Handler(MouseEvent event){
        if(play && ("".equals(cell1.getText()) || " ".equals(cell1.getText()))){
            cell1.setText(String.valueOf(player1Value));
            game.setCell1(player1Value);
            ClientHandler.sendMoveRequest(0, 0);
            checkWinOrDraw();
            player1Handle();
        }
    }
    
    @FXML
    private void cell2Handler(MouseEvent event){
        if(play && ("".equals(cell2.getText()) || " ".equals(cell2.getText()))){
            cell2.setText(String.valueOf(player1Value));
            game.setCell2(player1Value);
            ClientHandler.sendMoveRequest(0, 1);
            checkWinOrDraw();
            player1Handle();
        }
    }
    
    @FXML
    private void cell3Handler(MouseEvent event){
        if(play && ("".equals(cell3.getText()) || " ".equals(cell3.getText()))){
            cell3.setText(String.valueOf(player1Value));
            game.setCell3(player1Value);
            ClientHandler.sendMoveRequest(0, 2);
            checkWinOrDraw();
            player1Handle();
        }
    }
    
    @FXML
    private void cell4Handler(MouseEvent event){
        if(play && ("".equals(cell4.getText()) || " ".equals(cell4.getText()))){
            cell4.setText(String.valueOf(player1Value));
            game.setCell4(player1Value);
            ClientHandler.sendMoveRequest(1, 0);
            checkWinOrDraw();
            player1Handle();
        }
    }
    
    @FXML
    private void cell5Handler(MouseEvent event){
        if(play && ("".equals(cell5.getText()) || " ".equals(cell5.getText()))){
            cell5.setText(String.valueOf(player1Value));
            game.setCell5(player1Value);
            ClientHandler.sendMoveRequest(1, 1);
            checkWinOrDraw();
            player1Handle();
        }
    }
    
    @FXML
    private void cell6Handler(MouseEvent event){
        if(play && ("".equals(cell6.getText()) || " ".equals(cell6.getText()))){
            cell6.setText(String.valueOf(player1Value));
            game.setCell6(player1Value);
            ClientHandler.sendMoveRequest(1, 2);
            checkWinOrDraw();
            player1Handle();
        }
    }
    
    @FXML
    private void cell7Handler(MouseEvent event){
        if(play && ("".equals(cell7.getText()) || " ".equals(cell7.getText()))){
            cell7.setText(String.valueOf(player1Value));
            game.setCell7(player1Value);
            ClientHandler.sendMoveRequest(2, 0);
            checkWinOrDraw();
            player1Handle();
        }
    }
    
    @FXML
    private void cell8Handler(MouseEvent event){
        if(play && ("".equals(cell8.getText()) || " ".equals(cell8.getText()))){
            cell8.setText(String.valueOf(player1Value));
            game.setCell8(player1Value);
            ClientHandler.sendMoveRequest(2, 1);
            checkWinOrDraw();
            player1Handle();
        }

    }
    
    @FXML
    private void cell9Handler(MouseEvent event){
        if(play && ("".equals(cell9.getText()) || " ".equals(cell9.getText()))){
            cell9.setText(String.valueOf(player1Value));
            game.setCell9(player1Value);
            ClientHandler.sendMoveRequest(2, 2);
            checkWinOrDraw();
            player1Handle();
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

    private void exitBtnHandler(ActionEvent event){        
        Platform.exit();
    }
    
    private void backBtnHandler(ActionEvent event){
        setSceneVisibility(false);
    }
    
    @FXML
    private void quitBtnHandler(ActionEvent event){
        ClientHandler.setReplay(false);
        
        JSONObject gameQuit = new JSONObject();
        
        gameQuit.put("type", "gameQuit");
        gameQuit.put("responseStatus", "true");

        ClientHandler.sendRequest(gameQuit);

        ClientHandler.changeScene("Start");//should be the scene for starting a game
    }
    
    @FXML
    private void saveBtnHandler(ActionEvent event){
        String nextMove = " ";
        if(game.getNextMove() == 0){
            nextMove = "X";
        }
        else if(game.getNextMove() == 1){
            nextMove = "O";
        }
        ClientHandler.saveGameRequest(nextMove);
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

    }
    
    private void toggleNextMove(){
        if(game.getNextMove() == 0){
            game.setNextMove(1);
        }
        else{
            game.setNextMove(0);
        }
    }
    
    
    public void secondPlayerMove(){
        if(!finish){
            
            //get cell move of the second player
            Game.CellPosition resultCell = Game.getMoveOfNextPlayer();
            
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
    
    private void setGameLoaded(){
        if(ClientHandler.getNextPlayer().equals(ClientHandler.getPlayer().getUsername())){
            if(ClientHandler.getNextMove() == 'X'){
                player1Value = 'X';                
                player2Value = 'O';  
                game.setNextMove(0);
            }
            else{
                player1Value = 'O';
                player2Value = 'X';
                game.setNextMove(1);
            }
            player1Label.setText(ClientHandler.getPlayer().getUsername());
            player2Label.setText(ClientHandler.getPlayer().getOpponent());
            play = true;
        }
        else{
            if(ClientHandler.getNextMove() == 'X'){
                player1Value = 'O';                
                player2Value = 'X';  
                game.setNextMove(0);
            }
            else{
                player1Value = 'X';
                player2Value = 'O';
                game.setNextMove(1);
            }
            player1Label.setText(ClientHandler.getPlayer().getUsername());
            player2Label.setText(ClientHandler.getPlayer().getOpponent());
            play = false;
        }

        game.setBoard(ClientHandler.getBoard());
        game.printBoard();
    }
    
    private void setLoadedBoard(){
        int numOfMoves = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(game.getBoard()[i][j] == 'X' || game.getBoard()[i][j] == 'O'){
                    numOfMoves++;
                }
                
                if(i == 0){
                    switch (j) {
                        case 0:
                            cell1.setText(String.valueOf(game.getBoard()[i][j]));                            
                            break;

                        case 1:
                            cell2.setText(String.valueOf(game.getBoard()[i][j]));                            
                            break;

                        case 2:
                            cell3.setText(String.valueOf(game.getBoard()[i][j]));
                            break;

                        default:
                            break;
                    }
                }
                else if(i == 1){
                    switch (j) {
                        case 0:
                            cell4.setText(String.valueOf(game.getBoard()[i][j]));
                            break;
                        case 1:
                            cell5.setText(String.valueOf(game.getBoard()[i][j]));
                            break;
                        case 2:
                            cell6.setText(String.valueOf(game.getBoard()[i][j]));
                            break;
                        default:
                            break;
                    }
                }
                else if(i == 2){
                    switch (j) {
                        case 0:
                            cell7.setText(String.valueOf(game.getBoard()[i][j]));
                            break;
                        case 1:
                            cell8.setText(String.valueOf(game.getBoard()[i][j]));
                            break;
                        case 2:
                            cell9.setText(String.valueOf(game.getBoard()[i][j]));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        game.setMovesCount(numOfMoves);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clearCells();
        setSceneVisibility(false);
        ClientHandler.setMultigameCtrl(this);
        savingSubscene.setVisible(false);
        homeBtn.setDisable(true);
        waitingSubscene.setVisible(false);
        okBtn.setDisable(true);
        chatBox.appendText(String.format("\n"));
        
        game = new Game();
        
        if(ClientHandler.getIsLoaded()){
            
            setGameLoaded();
            setLoadedBoard();
        }
        else{
            game.setNextMove(0);
            setPlayerData();
        }
        
        finish = false;
        isDraw = false;
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
        
        clearCells();
        game.clearBoard();
        
        if(ClientHandler.getGameAccepted()){
            
            
            game.setNextMove(0);
            game.setMovesCount(0);
            setPlayerData();
            finish = false;
            isDraw = false;
            setSceneVisibility(false);
            waitingSubscene.setVisible(false);
            okBtn.setDisable(true);
            ClientHandler.setReplay(false);
            ClientHandler.setIsLoaded(false);
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
    
    public AnchorPane getWaitingSubscene(){
        
        return this.waitingSubscene;
    }
    
    public void displayOpponentMsg(String msg){
        
        chatBox.appendText("["+ClientHandler.getPlayer().getOpponent()+"]: "
        +msg+String.format("\n"));
    }

    @FXML
    private void msgBoxHandler(KeyEvent event) {
         if(event.getCode().equals(KeyCode.ENTER)) {
            if(!msgBox.getText().equals(""))
              {
                  ClientHandler.sendChatRequest(msgBox.getText());
                  chatBox.appendText("[You]: "+msgBox.getText()+String.format("\n"));
                  msgBox.setText("");
              }
          }
    }
    
    
}
