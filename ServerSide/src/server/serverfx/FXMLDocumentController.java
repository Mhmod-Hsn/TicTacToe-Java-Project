/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.serverfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.Server;

/**
 *
 * @author ahmed
 */
public class FXMLDocumentController implements Initializable {
    
//    private Label label;
    @FXML
    private ToggleButton controlBtn;
    @FXML
    private ListView<?> logsView;
    @FXML
    private Label logsLabel;
    @FXML
    private PieChart clientChart;
    @FXML
    private ListView<?> dbView;
    @FXML
    private Label dbLabel;
    @FXML
    private Pagination pagination;
    @FXML
    private ProgressIndicator loadIndicator;
    @FXML
    private Label loadLabel;
    ObservableList<PieChart.Data> _clientChart ;
    ObservableList<String> _dbView ;
    ObservableList<String> _logsView ;
    private Boolean controlFlag ;
    private Server serverInGUI ;
    private Stage window ;
    private Thread th ;
    private void handleButtonAction(ActionEvent event) {
        
                
    }
    private void pieChartClk(){
     //pieChart

                
                    _clientChart = FXCollections.observableArrayList(
                        new PieChart.Data("Online",10),//Player.getAll("online").size()),
                        new PieChart.Data("Offline",10),//Player.getAll("offline").size()),
                        new PieChart.Data("Busy",10)//Player.getAll("busy").size())    
                    );
                    this.clientChart.setData(_clientChart);
                    //this.clientChart.setDisable(false);
                

                    
        //end chart
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            //System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh");
            
    }    

    @FXML
    private void handleButtonAction(MouseEvent event) {
        //System.out.println("You clicked me!");
                    _clientChart = FXCollections.observableArrayList(
                        new PieChart.Data("Online",10),//Player.getAll("online").size()),
                        new PieChart.Data("Offline",10),//Player.getAll("offline").size()),
                        new PieChart.Data("Busy",10)//Player.getAll("busy").size())    
                    );
                    this.clientChart.setData(_clientChart);
                    //this.clientChart.setDisable(false);
    }
    
}
