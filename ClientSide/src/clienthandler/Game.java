/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientHandler;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sarahouf
 */
public class Game {
    private char[][] board;
    private int nextMove; //0 => player X, 1 => player O
    private static int movesCount;
    private final char playerX = 'X';
    private final char playerO = 'O';
    private int winner;
    private static int mode;
    
    public static class CellPosition{
        public int row = -1;
        public int col = -1;
        
        CellPosition(){}
        
        CellPosition(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
  //  private char value;

    public Game() {
        this.board = new char[][]{{' ', ' ', ' '},{' ', ' ', ' '},{' ', ' ', ' '}};
        movesCount = 0;
    }
    
    public char[][] getBoard(){
        return board;
    }
    
    public static int getMode(){
        return mode;
    }
    
    public static void setMode(int mode){
        Game.mode = mode;
    }
    
    public static void setMovesCount(int moves){
        movesCount = 0;
    }
    
    public void setNextMove(int nextMove){
        this.nextMove = nextMove;
    }
    
    public int getNextMove(){
        return nextMove;
    }
    
//    public void setValue(char value){
//        this.value = value;
//    }
    
    public void setCell1(char value){
        board[0][0] = value;
        movesCount++;
    }
    
    public void setCell2(char value){
        board[0][1] = value;
        movesCount++;
    }
    
    public void setCell3(char value){
        board[0][2] = value;
        movesCount++;
    }
    
    public void setCell4(char value){
        board[1][0] = value;
        movesCount++;
    }
    
    public void setCell5(char value){
        board[1][1] = value;
        movesCount++;
    }
    
    public void setCell6(char value){
        board[1][2] = value;
        movesCount++;
    }
    
    public void setCell7(char value){
        board[2][0] = value;
        movesCount++;
    }
    
    public void setCell8(char value){
        board[2][1] = value;
        movesCount++;
    }
    
    public void setCell9(char value){
        board[2][2] = value;
        movesCount++;
    }
    
    public boolean checkRows(){
        boolean result = false;
        for(int i = 0; i < 3; i++){
            if(board[i][0] != ' '){
                if(board[i][0] == board[i][1] && board[i][0] == board[i][2]){
                    result = true;
                    if(board[i][0] == playerX){
                        winner = 0;
                    }
                    else{
                        winner = 1;
                    }
                    //System.out.println("Row complete");
                }
            }
        }
        return result;
    }
    
    public boolean checkCols(){
        boolean result = false;
        for(int i = 0; i < 3; i++){
            if(board[0][i] != ' '){
                if(board[0][i] == board[1][i] && board[0][i] == board[2][i]){
                    result = true;
                    if(board[0][i] == playerX){
                        winner = 0;
                    }
                    else{
                        winner = 1;
                    }
                    //System.out.println("col complete");
                }
            }
        }
        
        return result;
    }
    
    public boolean checkDiagonals(){
        boolean result =false;
        if(board[0][0] != ' '){
            if(board[0][0] == board[1][1] && board[0][0] == board[2][2]){
                result = true;
                if(board[0][0] == playerX){
                    winner = 0;
                }
                else{
                    winner = 1;
                }
                //System.out.println("diag complete");
            }
        }
        if(board[0][2] != ' '){
            if(board[0][2] == board[1][1] && board[0][2] == board[2][0]){
                result = true;
                if(board[0][2] == playerX){
                    winner = 0;
                }
                else{
                    winner = 1;
                }
                //System.out.println("diag complete");
            }
        }
        
        return result;
    }
    
    public boolean checkNumberOfMoves(){
        boolean result = false;
        if(movesCount == 9){
            result = true;
        }
        
        return result;
    }
    
    public int checkWin(){
        boolean resultRows;
        boolean resultCols;
        boolean resultDiagonals;
        boolean resultMoves = false;
        int win = 0; //0 game still going, 1 player won, 2 number of moves reached(draw)
        resultRows = checkRows();
        resultCols = checkCols();
        resultDiagonals = checkDiagonals();
        
        if(resultCols || resultRows || resultDiagonals){
            win = 1;
//            System.out.println(resultCols);
//            System.out.println(resultRows);
//            System.out.println(resultDiagonals);
        }
        else{
            resultMoves = checkNumberOfMoves();
        }
        
        if(resultMoves == true){
            win = 2;
        }
        
        return win;
    }
    
    public void printBoard()
    {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print("" + board[i][j] + "   ");
            }
            System.out.println("");
        }
    }
    
    public void clearBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = ' ';
            }
        }
    }
    
    public int getNumberOfMoves(){
        int count = 0;
 
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == ' '){
                    count++;
                }
            }
        }
        
        return count;
    }
    
    public boolean checkMovesOnBoard(){
        boolean move = false;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == ' '){
                    move = true;
                    break;
                }
            }
        }
        
        return move;
    }
    
    public ArrayList getAvailableMoves(){
        ArrayList<CellPosition> availableMoves = new ArrayList();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == ' '){
                    CellPosition c = new CellPosition(i, j);
                    availableMoves.add(c);
                }
            }
        }
        
        return availableMoves;
    }
    
    public int evaluateMove(char[][] board){
        boolean resultRows;
        boolean resultCols;
        boolean resultDiagonals;
        
        int score = 0;
        resultRows = checkRows();
        
        if(resultRows){
            if(winner == 1){//if player O (computer) wins score is +10
                score = 10;
            }
            else{
                score = -10;//if player X (human) wins score is -10
            }
            return score;
        }
        
        resultCols = checkCols();
        if(resultCols){
            if(winner == 1){//if player O (computer) wins score is +10
                score = 10;
            }
            else{
                score = -10;//if player X (human) wins score is -10
            }
            return score;
        }
        resultDiagonals = checkDiagonals();
        if(resultDiagonals){
            if(winner == 1){//if player O (computer) wins score is +10
                score = 10;
            }
            else{
                score = -10;//if player X (human) wins score is -10
            }
            return score;
        }
        
        
        
        return score;
    }
    
    public int minimax(char[][] board, int depth, boolean isMaximizerPlayer){
        int score = evaluateMove(board);
        
        if(score == 10){
            return (score * (getNumberOfMoves() + 1)) - depth;
        }
        else if(score == -10){
            return (score  * (getNumberOfMoves() + 1)) + depth;
        }
        
        boolean checkLeftMoves = checkMovesOnBoard();
        if(!checkLeftMoves){
            return 0;
        }
        
        int bestValue;
        
        if(isMaximizerPlayer){
            bestValue = Integer.MIN_VALUE;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(board[i][j] == ' '){
                        board[i][j] = playerO;
                        //we want to maximize so get the max value each time
                        bestValue = Math.max(bestValue, minimax(board, depth + 1, !isMaximizerPlayer));
                        board[i][j] = ' ';
                    }
                }
            }            
        }
        else{
            bestValue = Integer.MAX_VALUE;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(board[i][j] == ' '){
                        board[i][j] = playerX;
                        //we want to maximize so get the max value each time
                        bestValue = Math.min(bestValue, minimax(board, depth + 1, !isMaximizerPlayer));
                        board[i][j] = ' ';
                    }
                }
            }
        }
        return bestValue;
    }
    
    public CellPosition getBestMove(){
        int bestValue = Integer.MIN_VALUE;
        CellPosition bestCell = new CellPosition();
        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == ' '){
                    board[i][j] = playerO;
                    int newValue = minimax(board, 0, false);
                    board[i][j] = ' ';
                    
                    if(newValue > bestValue){
                        bestValue = newValue;
                        bestCell.row = i;
                        bestCell.col = j;
                    }
                }
            }
        }
        return bestCell;
    }
    
    public CellPosition getRandomCell(){
        ArrayList<CellPosition> availableMoves = getAvailableMoves();
        int length = availableMoves.size();
        Random random = new Random();
        
        int rand = random.nextInt(length);
        
        return availableMoves.get(rand);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Game g = new Game();
        g.setCell6('X');
        
        
    }
}