/*
    This is the main GUI application class for the server.
 */

package server.serverfx;

import database.DBMethods;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Ahmed Mamdouh Abdel-Wahab
 */
public class ServerSide extends Application {
     
    public static FXMLDocumentBase rootOrigin;
    public static final int refreshRate = 3 ;
    public static boolean DBStatusFlag = false ;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        if( DBMethods.checkDBConnection()){            
            DBStatusFlag = true ;
        }else{
            DBStatusFlag= false ;
        }
        
        Parent root  = new FXMLDocumentBase();
        
        rootOrigin = (FXMLDocumentBase) root ;
        
       
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), (ActionEvent actionEvent) -> {
            if( DBStatusFlag ){
                rootOrigin.clientChartHandler();
                rootOrigin.logViewHandler();
                rootOrigin.tableViewHandler();
            }

            rootOrigin.dbViewHandler();

        }), new KeyFrame(Duration.seconds(refreshRate)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Server Panel");
        stage.resizableProperty().setValue(false);
        stage.show();
    }
    
    @Override
    public void stop(){

        //stop the server object
        rootOrigin.serverInGUI.stop();
        
        System.exit(0);   // exit plateform.exit()
    }

    public static void main(String[] args) {
        launch(args);
    }

}
