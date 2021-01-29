/*
    This is the class that constructs the componentrs of the server GUi
 */

package server.serverfx;

import database.DBConfig;
import static database.DatabaseDriver.DB_CON_STATUS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import database.playerinfo.Player;
import database.DBMethods;
import server.Server;
import server.utils.ServerUtils;

/**
 *
 * @author Ghada Ragab
 */

public class FXMLDocumentBase extends AnchorPane {

    protected final ToggleButton controlBtn;
    protected final ListView logsView;
    protected final Label logsLabel;
    protected final PieChart clientChart;
    protected final ListView dbView;
    protected final ProgressIndicator loadIndicator;
    protected final Label loadLabel;
    protected final Pagination pagination;
    protected final Label dbLabel;
    protected final TableView tableView;
    protected final TableColumn tableColumn;
    protected final TableColumn tableColumn0;
    protected final TableColumn tableColumn1;
    protected final Label playerLabel;
    protected final Label label;
    protected final WebView webView;

    // vars
    ObservableList<PieChart.Data> _clientChart ;
    ObservableList<String> _dbView ;
    ObservableList<String> _logsView ;
    ObservableList<Player> _tableView ;

    private Boolean controlFlag ;
    private Boolean visableFlag ;
    Server serverInGUI ;
    private Stage window ;
    
    public Server getServerInGUI() {
        return serverInGUI;
    }
    
    // end vars
    
    public FXMLDocumentBase() {
        
        // inits
        this.controlFlag = false ;
        this.visableFlag = false ;
        this.serverInGUI = new Server() ;
        
        _dbView = FXCollections.observableArrayList() ;
        _logsView = FXCollections.observableArrayList() ;
        _tableView = FXCollections.observableArrayList();

        controlBtn = new ToggleButton();
        logsView = new ListView();
        logsLabel = new Label();
        clientChart = new PieChart();
        dbView = new ListView();
        loadIndicator = new ProgressIndicator();
        loadLabel = new Label();
        pagination = new Pagination();
        dbLabel = new Label();
        tableView = new TableView();
        tableColumn = new TableColumn();
        tableColumn0 = new TableColumn();
        tableColumn1 = new TableColumn();
        playerLabel = new Label();
        label = new Label();
        webView = new WebView();

        setId("AnchorPane");
        setPrefHeight(902.0);
        setPrefWidth(1190.0);
        setStyle("-fx-background-color: #FFFFFF;");

        controlBtn.setLayoutX(540.0);
        controlBtn.setLayoutY(640.0);
        controlBtn.setMnemonicParsing(false);
        controlBtn.setPrefHeight(111.0);
        controlBtn.setPrefWidth(228.0);
        controlBtn.setStyle("-fx-background-color: #800000; -fx-border-color: #000000;");
        controlBtn.setText("Stopped");
        controlBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        controlBtn.setFont(new Font("Arial Bold", 40.0));

        logsView.setLayoutX(12.0);
        logsView.setLayoutY(8.0);
        logsView.setPrefHeight(450.0);
        logsView.setPrefWidth(442.0);
        logsView.setStyle("-fx-border-color: #00004d; -fx-background-color: #b3ffff;");

        logsLabel.setLayoutX(12.0);
        logsLabel.setLayoutY(458.0);
        logsLabel.setPrefHeight(52.0);
        logsLabel.setPrefWidth(442.0);
        logsLabel.setStyle("-fx-background-color: #00004d; -fx-border-color: #00004d;");
        logsLabel.setText("Logs");      
        logsLabel.setTextFill(javafx.scene.paint.Color.valueOf("#FFFFFF"));
        logsLabel.setFont(new Font(28.0));
        logsLabel.setAlignment(javafx.geometry.Pos.CENTER);
        
        clientChart.setLayoutX(461.0);
        clientChart.setLayoutY(100.0);
        clientChart.setPrefHeight(380.0);
        clientChart.setPrefWidth(374.0);
        clientChart.setStyle("-fx-background-color: #FFFFFF;");
        clientChart.setTitle("Clients Status");
        clientChart.setTitleSide(javafx.geometry.Side.BOTTOM);
        
        
        dbView.setLayoutX(12.0);
        dbView.setLayoutY(550.0);
        dbView.setPrefHeight(260.0);
        dbView.setPrefWidth(442.0);
        dbView.setStyle("-fx-border-color: #00004d; -fx-background-color: #b3ffff;");

        loadIndicator.setLayoutX(724.0);
        loadIndicator.setLayoutY(40.0);
        loadIndicator.setProgress(0.0);

        loadLabel.setAlignment(javafx.geometry.Pos.CENTER);
        loadLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        loadLabel.setLayoutX(550.0);
        loadLabel.setLayoutY(35.0);
        loadLabel.setPrefHeight(41.0);
        loadLabel.setPrefWidth(170.0);
        loadLabel.setText("Current Load");
        loadLabel.setTextFill(javafx.scene.paint.Color.valueOf("#2323a9"));
        loadLabel.setFont(new Font(28.0));

        pagination.setLayoutX(543.0);
        pagination.setLayoutY(844.0);
        pagination.setMaxPageIndicatorCount(2);
        pagination.setPageCount(2);
        pagination.setPrefHeight(62.0);
        pagination.setPrefWidth(187.0);
        pagination.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #FFFFFF;");

        dbLabel.setLayoutX(12.0);
        dbLabel.setLayoutY(768.0);
        dbLabel.setPrefHeight(52.0);
        dbLabel.setPrefWidth(442.0);
        dbLabel.setStyle("-fx-background-color: #00004d; -fx-border-color: #00004d;");
        dbLabel.setText("DataBase");
        dbLabel.setTextFill(javafx.scene.paint.Color.valueOf("#FFFFFF"));
        dbLabel.setFont(new Font(28.0));
        dbLabel.setAlignment(javafx.geometry.Pos.CENTER);
        
        tableView.setLayoutX(843.0);
        tableView.setLayoutY(10.0);
        tableView.setPrefHeight(752.0);
        tableView.setPrefWidth(333.0);
        tableView.setStyle("-fx-border-color: #00004d; -fx-background-color: #b3ffff;");

        tableColumn.setPrefWidth(153.0);
        tableColumn.setStyle("-fx-text-fill: #3002ff;");
        tableColumn.setText("PlayerName");

        tableColumn0.setPrefWidth(106.0);
        tableColumn0.setStyle("-fx-text-fill: #3002ff;");
        tableColumn0.setText("Status");

        tableColumn1.setPrefWidth(72.0);
        tableColumn1.setStyle("-fx-text-fill: #3002ff;");
        tableColumn1.setText("Score");

        playerLabel.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        playerLabel.setLayoutX(843.0);
        playerLabel.setLayoutY(764.0);
        playerLabel.setPrefHeight(52.0);
        playerLabel.setPrefWidth(333.0);
        playerLabel.setStyle("-fx-background-color: #00004d; -fx-border-color: #00004d;");
        playerLabel.setText("Players List");
        playerLabel.setTextFill(javafx.scene.paint.Color.valueOf("#FFFFFF"));
        playerLabel.setFont(new Font(28.0));
        playerLabel.setAlignment(javafx.geometry.Pos.CENTER);
        
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(467.0);
        label.setLayoutY(607.0);
        label.setPrefHeight(84.0);
        label.setPrefWidth(361.0);
        label.setStyle("-fx-background-color: #00ffff;");
        label.setTextFill(javafx.scene.paint.Color.RED);

        webView.setLayoutX(8.0);
        webView.setLayoutY(4.0);
        webView.setPrefHeight(828.0);
        webView.setPrefWidth(1173.0);
        webView.setVisible(false);

        getChildren().add(controlBtn);
        getChildren().add(logsView);
        getChildren().add(logsLabel);
        getChildren().add(clientChart);
        getChildren().add(dbView);
        getChildren().add(loadIndicator);
        getChildren().add(loadLabel);
        getChildren().add(pagination);
        getChildren().add(dbLabel);
        tableView.getColumns().add(tableColumn);
        tableView.getColumns().add(tableColumn0);
        tableView.getColumns().add(tableColumn1);
        getChildren().add(tableView);
        getChildren().add(playerLabel);
        //getChildren().add(label);
        getChildren().add(webView);
        
        ServerUtils.clearLog(controlFlag);
        
        controlBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if(!controlFlag){
                    controlFlag = true ;
                    ServerUtils.clearLog(controlFlag);
                    serverInGUI.start();
                    controlBtn.setStyle("-fx-background-color: #006622; -fx-border-color: #000000;");
                    controlBtn.setText("Running");
                    controlBtn.setTextFill(javafx.scene.paint.Color.WHITE);
                    controlBtn.setFont(new Font("Arial Bold", 40.0));                                       
                }else{
                    
                    controlFlag = false ;
                    ServerUtils.clearLog(controlFlag);
                    serverInGUI.stop();
                    
                    //create new object
                    serverInGUI = new Server();
                    
                    controlBtn.setStyle("-fx-background-color: #800000; -fx-border-color: #000000;");
                    controlBtn.setText("Stopped");
                    controlBtn.setTextFill(javafx.scene.paint.Color.WHITE);
                    controlBtn.setFont(new Font("Arial Bold", 40.0));                   
                }
                window = (Stage)((Node)event.getSource()).getScene().getWindow();
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
    
    
    protected  void logViewHandler() {
        
        this.refreshLoadIndicator();
        _logsView.clear();
        File logsFile =  new File(ServerUtils.logsFilePath); 
        FileReader fr;
        BufferedReader br ;
        StringBuffer sb;
        String line;
        BufferedWriter out = null ;
        Date d = new Date() ;
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
                    }                    
                    this.logsView.setDisable(false);
                }catch(FileNotFoundException ef){
                    try {
                        System.err.println("FileNotFoundException");
                        if (logsFile.createNewFile()) {
                            out = new BufferedWriter(new FileWriter(logsFile, true));
                            out.write("[ "+d.toString()+" ] :  Creating new logFile\n");
                            fr=new FileReader(logsFile);   //reads the file
                            br=new BufferedReader(fr);  //creates a buffering character input stream
                            sb=new StringBuffer();    //constructs a string buffer with no characters
                            while((line=br.readLine())!=null)
                            {
                                _logsView.add(line);
                            }
                            fr.close();
                            out.close();
                        }

                        this.logsView.setDisable(false);
                        } catch (IOException ex) {
                            this.logsView.setDisable(true);
                        }
                }catch (IOException ioe){
                        this.logsView.setDisable(true);   
                }
        this.logsView.setItems(_logsView);
    }

    public  void clientChartHandler(){

        int online = 0 ;
        int offline = 0 ;
        int busy = 0 ;
        if(DBMethods.getAllRecords(Player.statusType.offline.toString()) != null )
            offline = DBMethods.getAllRecords(Player.statusType.offline.toString()).size();
        
        if(DBMethods.getAllRecords(Player.statusType.online.toString()) != null )
            online = DBMethods.getAllRecords(Player.statusType.online.toString()).size();
        
        if(DBMethods.getAllRecords(Player.statusType.busy.toString()) != null )
            busy = DBMethods.getAllRecords(Player.statusType.busy.toString()).size();
        
        _clientChart = FXCollections.observableArrayList(
            new PieChart.Data("Offline",offline),//Player.getAll("offline").size()),
            new PieChart.Data("Online",online),          
            new PieChart.Data("Busy",busy)//Player.getAll("busy").size())    
        );
        clientChart.setData(_clientChart);
    }

    protected  void dbViewHandler(){
        
        this.refreshLoadIndicator();
        _dbView.clear();
        _dbView.add("Database Scheme          :    "+ DBConfig.DB_SCHEME);
        _dbView.add("Database Name             :    "+ DBConfig.DB_NAME); 
        _dbView.add("Database Port                :    "+ DBConfig.DB_PORT);
        _dbView.add("Database URL                :    "+ DBConfig.DB_URL); 
        _dbView.add("Database User               :    "+ DBConfig.DB_USER);
        _dbView.add("Database InConnection :    "+ DB_CON_STATUS); 
        this.dbView.setItems(_dbView);
    }

    protected  void tableViewHandler(){
        
        _tableView.clear();
        if(DBMethods.getAllOrderedDesc("score") != null )
            _tableView.addAll(DBMethods.getAllOrderedDesc("score"));
        else
            _tableView.clear();
        
        tableView.setItems(_tableView);
        tableColumn.setCellValueFactory( new PropertyValueFactory<>("username") );
        tableColumn0.setCellValueFactory( new PropertyValueFactory<>("status") );
        tableColumn1.setCellValueFactory( new PropertyValueFactory<>("score") );
    }


    private void refreshLoadIndicator(){

        double all = 0 ;
        double online = 0 ;
        double offline = 0 ;
        double none = .001 ;
        int busy = 0 ;
        if(DBMethods.getAllRecords(Player.statusType.online.toString()) != null )
            online = DBMethods.getAllRecords(Player.statusType.online.toString()).size();
        if(DBMethods.getAllRecords(Player.statusType.offline.toString()) != null )
            offline = DBMethods.getAllRecords(Player.statusType.offline.toString()).size();
        if(DBMethods.getAllRecords(Player.statusType.busy.toString()) != null )
            busy = DBMethods.getAllRecords(Player.statusType.busy.toString()).size();
        if(DBMethods.getAllRecords(Player.statusType.none.toString()) != null )
            busy = DBMethods.getAllRecords(Player.statusType.none.toString()).size();
        if( offline == 0 && online+busy >0 )
            all = 1;
        else
            all = (double)(online+busy) / (none+online+offline+busy)  ;
        loadIndicator.setProgress(all);
    }
}
