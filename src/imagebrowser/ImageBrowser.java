/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagebrowser;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Daniel
 */
public class ImageBrowser extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException 
    {
        primaryStage.initStyle(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("Browser.fxml"));        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Buscador de imágenes");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
       
    
    public static void main(String[] args) {
                
        launch(args);
    }
    
}
