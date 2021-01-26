package server.serverfx;


import database.DBConfig;
import static database.DatabaseDriver.DB_CON_STATUS;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import database.playerinfo.*;
import server.Server;
import server.utils.ServerUtils;

public class FXMLDocumentBase extends AnchorPane {

    protected final ToggleButton controlBtn;
    protected final ListView logsView;
    protected final Label logsLabel;
    protected final PieChart clientChart;
    protected final ListView dbView;
    protected final ProgressIndicator loadIndicator;
    protected final Label loadLabel;
    protected final Hyperlink hyperlink;
    protected final WebView webView;
    protected final Pagination pagination;
// vars
    ObservableList<PieChart.Data> _clientChart ;
    ObservableList<String> _dbView ;
    ObservableList<String> _logsView ;
    private Boolean controlFlag ;
    private Boolean visableFlag ;
    private Server serverInGUI ;
    private Stage window ;
// end vars
    public FXMLDocumentBase() {
// inits
        this.controlFlag = false ;
        this.visableFlag = false ;
        this.serverInGUI = new Server() ;
        _dbView = FXCollections.observableArrayList() ;
        _logsView = FXCollections.observableArrayList() ;
// end inits
        controlBtn = new ToggleButton();
        logsView = new ListView();
        logsLabel = new Label();
        clientChart = new PieChart();
        dbView = new ListView();
        loadIndicator = new ProgressIndicator();
        loadLabel = new Label();
        hyperlink = new Hyperlink();
        webView = new WebView();
        pagination = new Pagination();

        setId("AnchorPane");
        setPrefHeight(902.0);
        setPrefWidth(1190.0);

        controlBtn.setLayoutX(45.0);
        controlBtn.setLayoutY(770.0);
        controlBtn.setMnemonicParsing(false);
        controlBtn.setPrefHeight(74.0);
        controlBtn.setPrefWidth(184.0);
        controlBtn.setText("Turn On");
        controlBtn.setTextFill(javafx.scene.paint.Color.valueOf("#4f00ffdb"));
        controlBtn.setFont(new Font("Arial Bold", 34.0));

        logsView.setLayoutX(37.0);
        logsView.setLayoutY(37.0);
        logsView.setOnMouseClicked(this::logViewHandle);
        logsView.setOnMouseDragged(this::logViewHandle);
        logsView.setOnMouseEntered(this::logViewHandle);
        logsView.setOnMouseExited(this::logViewHandle);
        logsView.setOnMouseMoved(this::logViewHandle);
        logsView.setOnMousePressed(this::logViewHandle);
        logsView.setOnMouseReleased(this::logViewHandle);
        logsView.setPrefHeight(655.0);
        logsView.setPrefWidth(656.0);

        logsLabel.setLayoutX(38.0);
        logsLabel.setLayoutY(691.0);
        logsLabel.setPrefHeight(52.0);
        logsLabel.setPrefWidth(250.0);
        logsLabel.setText("Server Logs");
        logsLabel.setTextFill(javafx.scene.paint.Color.valueOf("#2412e3"));
        logsLabel.setFont(new Font(18.0));

        clientChart.setLayoutX(702.0);
        clientChart.setLayoutY(37.0);
        clientChart.setOnMouseClicked(this::clientChartHandle);
        clientChart.setOnMouseDragged(this::clientChartHandle);
        clientChart.setOnMouseEntered(this::clientChartHandle);
        clientChart.setOnMouseExited(this::clientChartHandle);
        clientChart.setOnMouseMoved(this::clientChartHandle);
        clientChart.setOnMousePressed(this::clientChartHandle);
        clientChart.setOnMouseReleased(this::clientChartHandle);
        clientChart.setPrefHeight(388.0);
        clientChart.setPrefWidth(474.0);
        clientChart.setTitle("Clients Status");
        clientChart.setTitleSide(javafx.geometry.Side.BOTTOM);

        dbView.setLayoutX(705.0);
        dbView.setLayoutY(447.0);
        dbView.setOnMouseClicked(this::dbViewHandle);
        dbView.setOnMouseDragged(this::dbViewHandle);
        dbView.setOnMouseEntered(this::dbViewHandle);
        dbView.setOnMouseExited(this::dbViewHandle);
        dbView.setOnMouseMoved(this::dbViewHandle);
        dbView.setOnMousePressed(this::dbViewHandle);
        dbView.setOnMouseReleased(this::dbViewHandle);
        dbView.setPrefHeight(244.0);
        dbView.setPrefWidth(474.0);

        loadIndicator.setLayoutX(1125.0);
        loadIndicator.setLayoutY(787.0);
//        loadIndicator.setOnMouseDragEntered(this::loadIndicatorHandle);
//        loadIndicator.setOnMouseDragExited(this::loadIndicatorHandle);
//        loadIndicator.setOnMouseDragOver(this::loadIndicatorHandle);
//        loadIndicator.setOnMouseDragReleased(this::loadIndicatorHandle);
        loadIndicator.setProgress(0.0);

        loadLabel.setAlignment(javafx.geometry.Pos.CENTER);
        loadLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        loadLabel.setLayoutX(1003.0);
        loadLabel.setLayoutY(782.0);
        loadLabel.setPrefHeight(41.0);
        loadLabel.setPrefWidth(123.0);
        loadLabel.setText("Current Load");
        loadLabel.setTextFill(javafx.scene.paint.Color.valueOf("#8902f5"));
        loadLabel.setFont(new Font(19.0));

        hyperlink.setAlignment(javafx.geometry.Pos.CENTER);
        hyperlink.setLayoutX(870.0);
        hyperlink.setLayoutY(691.0);
        hyperlink.setPrefHeight(52.0);
        hyperlink.setPrefWidth(139.0);
        hyperlink.setText("Database");
        hyperlink.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        hyperlink.setTextFill(javafx.scene.paint.Color.valueOf("#f80303"));
        hyperlink.setFont(new Font(22.0));

        webView.setLayoutX(14.0);
        webView.setLayoutY(14.0);
        webView.setPrefHeight(831.0);
        webView.setPrefWidth(1164.0);
        webView.setVisible(false);

        pagination.setCurrentPageIndex(0);
        pagination.setLayoutX(521.0);
        pagination.setLayoutY(850.0);
        pagination.setMaxPageIndicatorCount(2);
        pagination.setPageCount(2);
        pagination.setPrefHeight(52.0);
        pagination.setPrefWidth(200.0);

        getChildren().add(controlBtn);
        getChildren().add(logsView);
        getChildren().add(logsLabel);
        getChildren().add(clientChart);
        getChildren().add(dbView);
        getChildren().add(loadIndicator);
        getChildren().add(loadLabel);
        getChildren().add(hyperlink);
        getChildren().add(webView);
        getChildren().add(pagination);
        
        
        controlBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(! controlFlag){
                    controlFlag = true ;
                    controlBtn.setText("Turn Off");
                    controlBtn.setStyle("-fx-text-fill: #ff0000 ");
                    serverInGUI.start();
//                    clientChart.setVisible(true);
                }else{
                    controlFlag = false ;
                    controlBtn.setText("Turn On");
                    controlBtn.setStyle("-fx-text-fill: #0000ff ");
                    serverInGUI.stop();
//                    clientChart.setVisible(false);
                }
//                controlBtn.setDisable(true);
                window = (Stage)((Node)event.getSource()).getScene().getWindow();
//                window.setTitle("Server Mannger");
                window.show();
            }
            
        });
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if(! visableFlag){                
                WebEngine engine = webView.getEngine();
                engine.load(DBConfig.DB_SERVER_URL);
                webView.setVisible(true);
                visableFlag = true ;
            }
            else{
                webView.setVisible(false);
                visableFlag = false ;
            }
        });
        

    }

    protected  void logViewHandle(javafx.scene.input.MouseEvent mouseEvent){
        this.refreshLoadIndicator();
        _logsView.clear();
        File logsFile =  new File(ServerUtils.logsFilePath); 
        FileReader fr;
        BufferedReader br ;
        StringBuffer sb;
        String line;                          
                try{
                    if(logsFile != null){
                        fr=new FileReader(logsFile);   //reads the file  
                        br=new BufferedReader(fr);  //creates a buffering character input stream  
                        sb=new StringBuffer();    //constructs a string buffer with no characters  
                        while((line=br.readLine())!=null)  
                        {  
                                _logsView.add(line);
                        } 
                        fr.close();
                    }else{
                        if (logsFile.createNewFile()) {
                            fr=new FileReader(logsFile);   //reads the file  
                            br=new BufferedReader(fr);  //creates a buffering character input stream  
                            sb=new StringBuffer();    //constructs a string buffer with no characters  
                            while((line=br.readLine())!=null)  
                            {  
                                    _logsView.add(line);
                            }
                            fr.close();
                        }
                    }
                    
                    this.logsView.setDisable(false);
                }catch(FileNotFoundException ef){
                        //System.err.println("FileNotFoundException");
                        this.logsView.setDisable(true);

                }catch (IOException ioe){
                        //System.err.println("IOException");
                        this.logsView.setDisable(true);
                }
        this.logsView.setItems(_logsView);
    }







    protected  void clientChartHandle(javafx.scene.input.MouseEvent mouseEvent){
        this.refreshLoadIndicator();
        int online = 0 ;
        int offline = 0 ;
        int busy = 0 ;
        if(DbMethods.getAllRecords(Player.statusType.online.toString()) != null )
            online = DbMethods.getAllRecords(Player.statusType.online.toString()).size();
        if(DbMethods.getAllRecords(Player.statusType.offline.toString()) != null )
            offline = DbMethods.getAllRecords(Player.statusType.offline.toString()).size();
        if(DbMethods.getAllRecords(Player.statusType.busy.toString()) != null )
            busy = DbMethods.getAllRecords(Player.statusType.busy.toString()).size();
        
        _clientChart = FXCollections.observableArrayList(
            new PieChart.Data("Online",online),
            new PieChart.Data("Offline",offline),//Player.getAll("offline").size()),
            new PieChart.Data("Busy",busy)//Player.getAll("busy").size())    
        );
        this.clientChart.setData(_clientChart);
    }







    protected  void dbViewHandle(javafx.scene.input.MouseEvent mouseEvent){
        this.refreshLoadIndicator();
        _dbView.clear();
        _dbView.add("Database Scheme       :    "+ DBConfig.DB_SCHEME);
        _dbView.add("Database Name         :    "+ DBConfig.DB_NAME); 
        _dbView.add("Database Port         :    "+ DBConfig.DB_PORT);
        _dbView.add("Database URL          :    "+ DBConfig.DB_URL); 
        _dbView.add("Database User         :    "+ DBConfig.DB_USER);
        _dbView.add("Database InConnection :    "+ DB_CON_STATUS); 
        this.dbView.setItems(_dbView);
    }







//    protected  void loadIndicatorHandle(javafx.scene.input.MouseDragEvent mouseDragEvent){
//        
//    }
    private void refreshLoadIndicator(){
        //System.out.println("ahmed");
        double all = 0 ;
        int online = 0 ;
        int offline = 0 ;
        int busy = 0 ;
        if(DbMethods.getAllRecords(Player.statusType.online.toString()) != null )
            online = DbMethods.getAllRecords(Player.statusType.online.toString()).size();
        if(DbMethods.getAllRecords(Player.statusType.offline.toString()) != null )
            offline = DbMethods.getAllRecords(Player.statusType.offline.toString()).size();
        if(DbMethods.getAllRecords(Player.statusType.busy.toString()) != null )
            busy = DbMethods.getAllRecords(Player.statusType.busy.toString()).size();
        if(offline == 0)
            all = 0;
        else
            all = (double)(online+(2*busy)) / (online+offline+(2*busy)) ;
        //System.out.println(online);
        //System.out.println(offline);
        //System.out.println(busy);
        //System.out.println(all);
        loadIndicator.setProgress(all);
    }



}
