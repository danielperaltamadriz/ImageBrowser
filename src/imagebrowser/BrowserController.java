/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagebrowser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class BrowserController implements Initializable 
{   
    //Variables:
    @FXML
    private Button queryBtn, searchBtn;                
    @FXML
    private TextArea queryTxt;
    @FXML
    private AnchorPane anchorMain;
    @FXML
    private AnchorPane anchorBrowser;
    @FXML
    private Slider threshold;     
    @FXML
    private VBox vBoxGallery;      
    
        
    //Methods  
    private Stage fileSearcher;// = new Stage(); 
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        queryBtn.setTooltip(new Tooltip("Imagen de referencia"));
        threshold.setTooltip(new Tooltip("Similitud de las imágenes"));
       // docs = new Documents();               
    }    
    
    
    private void newImages()
    {
        Documents docs = new Documents();
    }
    
    @FXML
    public void handle(ActionEvent event) 
    {        
        fileSearcher = new Stage();
        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        fileSearcher.setScene(scene);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG archivos (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("JPG archivos (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().addAll(extFilter, extFilter2);
        fileChooser.setTitle("Elija una imagen");
        File file = fileChooser.showOpenDialog(fileSearcher);
        if (file != null) 
        {
            queryTxt.setText(file.getPath().toString());
        }          
    }
     @FXML
        public void queryTxtSetOnDragOver(DragEvent event) 
        {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) 
            {
                event.acceptTransferModes(TransferMode.COPY);
            } 
            else 
            {
                event.consume();
            }
        }
               
    @FXML
    public void queryTxtSetOnDragDropped(DragEvent event) 
    {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) 
        {
            success = true;
            String filePath = null;
            for (File file:db.getFiles()) 
            {
                filePath = file.getAbsolutePath();
                queryTxt.setText(filePath.toString());
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
    

       
       
    
}
