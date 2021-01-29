package clientside;

import clientHandler.Player;
import java.io.IOException;
import javafx.scene.text.*;
import java.lang.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.regex.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class LoginFXMLDocumentBase extends BorderPane {

    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final ColumnConstraints columnConstraints2;
    protected final ColumnConstraints columnConstraints3;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final RowConstraints rowConstraints2;
    protected final RowConstraints rowConstraints3;
    protected final Button btnSignup;
    protected final Button btnLogin;
    protected final TextField txtPassword;
    protected final TextField txtUsername;
    protected final Label passLbl;
    protected final Label userLbl;
    protected final Label warningLbl;
    Player player;
    FXMLLoader loader;

    public LoginFXMLDocumentBase() {

        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        columnConstraints2 = new ColumnConstraints();
        columnConstraints3 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        rowConstraints2 = new RowConstraints();
        rowConstraints3 = new RowConstraints();
        btnSignup = new Button();
        btnLogin = new Button();
        txtPassword = new TextField();
        txtUsername = new TextField();
        passLbl = new Label();
        userLbl = new Label();
        warningLbl = new Label();
        setStyle("-fx-background-color: white;");

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(gridPane, javafx.geometry.Pos.CENTER);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(100.0);

        columnConstraints2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints2.setMinWidth(10.0);
        columnConstraints2.setPrefWidth(100.0);

        columnConstraints3.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints3.setMinWidth(10.0);
        columnConstraints3.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(30.0);
        rowConstraints2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints3.setMinHeight(10.0);
        rowConstraints3.setPrefHeight(30.0);
        rowConstraints3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        GridPane.setColumnIndex(btnSignup, 3);
        GridPane.setHalignment(btnSignup, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(btnSignup, 3);
        GridPane.setValignment(btnSignup, javafx.geometry.VPos.CENTER);
        btnSignup.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        btnSignup.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        btnSignup.setMnemonicParsing(false);
        btnSignup.setPrefHeight(41.0);
        btnSignup.setPrefWidth(116.0);
        btnSignup.setText("Sign Up");
        btnSignup.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        GridPane.setColumnIndex(btnLogin, 1);
        GridPane.setHalignment(btnLogin, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(btnLogin, 3);
        GridPane.setValignment(btnLogin, javafx.geometry.VPos.CENTER);
        btnLogin.setMnemonicParsing(false);
        btnLogin.setPrefHeight(41.0);
        btnLogin.setPrefWidth(116.0);
        btnLogin.setText("Log In");

        GridPane.setColumnIndex(txtPassword, 2);
        GridPane.setColumnSpan(txtPassword, 2);
        GridPane.setRowIndex(txtPassword, 2);
        txtPassword.setPrefHeight(30.0);
        txtPassword.setPrefWidth(200.0);

        GridPane.setColumnIndex(txtUsername, 2);
        GridPane.setColumnSpan(txtUsername, 2);
        GridPane.setRowIndex(txtUsername, 1);
        txtUsername.setPrefHeight(30.0);
        txtUsername.setPrefWidth(0.0);

        GridPane.setColumnIndex(passLbl, 1);
        GridPane.setRowIndex(passLbl, 2);
        passLbl.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        passLbl.setPrefHeight(15.0);
        passLbl.setPrefWidth(203.0);
        passLbl.setText("Password");
        passLbl.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        passLbl.setFont(new Font(20.0));

        GridPane.setColumnIndex(userLbl, 1);
        GridPane.setRowIndex(userLbl, 1);
        userLbl.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        userLbl.setPrefHeight(15.0);
        userLbl.setPrefWidth(203.0);
        userLbl.setText("Username");
        userLbl.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        userLbl.setFont(new Font(20.0));

        GridPane.setColumnIndex(warningLbl, 1);
        GridPane.setColumnSpan(warningLbl, 4);
        GridPane.setRowIndex(warningLbl, 4);
        warningLbl.setAlignment(javafx.geometry.Pos.BASELINE_CENTER);
        warningLbl.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        warningLbl.setPrefHeight(15.0);
        warningLbl.setPrefWidth(362.0);
        warningLbl.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        warningLbl.setTextFill(javafx.scene.paint.Color.valueOf("#f21616"));
        setCenter(gridPane);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getColumnConstraints().add(columnConstraints1);
        gridPane.getColumnConstraints().add(columnConstraints2);
        gridPane.getColumnConstraints().add(columnConstraints3);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getRowConstraints().add(rowConstraints2);
        gridPane.getRowConstraints().add(rowConstraints3);
        gridPane.getChildren().add(btnSignup);
        gridPane.getChildren().add(btnLogin);
        gridPane.getChildren().add(txtPassword);
        gridPane.getChildren().add(txtUsername);
        gridPane.getChildren().add(passLbl);
        gridPane.getChildren().add(userLbl);
        gridPane.getChildren().add(warningLbl);
        
        player = new Player();
        loader = new FXMLLoader();

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String username = txtUsername.getText();
                String password = txtPassword.getText();                
                loader.setLocation(LoginFXMLController.class.getResource("/clientside/WelcomeFXML.fxml"));
                
                boolean checkUname = player.checkUsername(username);
                boolean checkPass = player.checkPassword(password);
                ////System.out.println(checkUname);
                if(!checkUname){
                    warningLbl.setText("Invalid username format");
                }
                else if(!checkPass){
                    warningLbl.setText("Invalid password format, should be between 6 and 20 characters");
                }
                else{
                    warningLbl.setText("");
                    //TODO
                    //send data to server to check for existence before changing
                    //to the next scene
                    try {
                        Parent welcomeParent = loader.load();
                        Scene welcomeScene = new Scene(welcomeParent);
                        WelcomeFXMLController ctrl = loader.getController();
                        //ctrl.setUsername(username);
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(welcomeScene);
                        window.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }                
            }
        });
        
        btnSignup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String name=txtUsername.getText();
                String password=txtPassword.getText();
                
                loader.setLocation(LoginFXMLController.class.getResource("/clientside/WelcomeFXML.fxml"));
                
                boolean checkUname = player.checkUsername(name);
                boolean checkPass = player.checkPassword(password);

                if(!checkUname){
                    warningLbl.setText("Invalid username format");
                }
                else if(!checkPass){
                    warningLbl.setText("Invalid password format, should be between 6 and 20 characters");
                }
                else
                { 
                    warningLbl.setText("");
                    //TODO
                    //send data to server to check for existence before changing
                    //to the next scene
                    try {
                        Parent welcomeParent = loader.load();
                        Scene welcomeScene = new Scene(welcomeParent);
                        WelcomeFXMLController ctrl = loader.getController();
                        //ctrl.setUsername(name);
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(welcomeScene);
                        window.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
    }
}
