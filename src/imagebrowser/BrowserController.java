/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagebrowser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
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
    private ListView galleryList;

    private Documents docs;
    //Methods  
    private Stage fileSearcher;// = new Stage(); 

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Images index = new Images();
        try
        {
            index.createIndex();
        }
        catch (IOException ex)
        {
            Logger.getLogger(BrowserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        queryBtn.setTooltip(new Tooltip("Imagen de referencia"));
        threshold.setTooltip(new Tooltip("Similitud de las imágenes"));
        docs = new Documents();
        LinkedList<String> list = newImages();
        loadImages(list);
    }

    private LinkedList<String> newImages()
    {
        try
        {
            LinkedList<String> list = docs.getAllImages(Paths.get(System.getProperty("user.dir") + "/src/Images/Added").toFile());
            if (list != null)
            {
                for (String s : list)
                {
                    System.out.println(s);
                }
                return list;
            }
        }
        catch (IOException ex)
        {
            System.out.println("Error");
            Logger.getLogger(BrowserController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    private void loadImages(LinkedList<String> list)
    {
        if (list != null)
        {            
            for (String s : list)
            {
                System.out.println(s);
                Image image = new Image("file:"+s);
                ImageView view = new ImageView(image);
                HBox box = new HBox(view);
                galleryList.getItems().add(box);                               
            }
        }
        else
        {
            System.out.println("Lista nula");
        }
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
    public void btnBuscarPressed(ActionEvent event)
    {
        System.out.println(queryTxt.getText());
        Image image = new Image("file:"+queryTxt.getText());
        Images img = new Images();
        Img arrayGRB = img.colorHistogram(image);       
        try
        {
            String ss = docs.openFile("index.txt");
            Hashtable<String, Img> tabla = docs.docToImg(ss);
            double d = 0;
            Hashtable<String, Double> resultados = new Hashtable<String, Double>();
            double resultadoComparacion = 0;
            for(String key : tabla.keySet())
            {                                
                Img imagen = tabla.get(key);
                d = img.compareVectors(arrayGRB.vectorToArray1(), imagen.vectorToArray1());
                d += img.compareVectors(arrayGRB.vectorToArray2(), imagen.vectorToArray2()); 
                d += img.compareVectors(arrayGRB.vectorToArray3(), imagen.vectorToArray3()); 
                resultadoComparacion = d/3;
                resultados.put(key, resultadoComparacion);               
            }
             System.out.println(resultados.toString());
             showImages(resultados);
            //System.out.println(tabla.toString());
            
        }
        catch (IOException ex)
        {
            Logger.getLogger(BrowserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showImages(Hashtable<String, Double> resultados)
    {
        galleryList.getItems().clear();
        TreeMap<Double, String> show = new TreeMap<Double, String>();
          for(String key : resultados.keySet())
          {
              if((double)resultados.get(key) >= threshold.getValue())
              {
                show.put((double)resultados.get(key)*-1, key);
              }
          }
          for(double d : show.keySet())
          {
            String key = show.get(d);
            Image image = new Image("file:D:\\Danielito\\Documents\\ImageBrowser\\src\\Images\\Added\\"+key);
            ImageView view = new ImageView(image);
            HBox box = new HBox(view);
            galleryList.getItems().add(box);
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
            for (File file : db.getFiles())
            {
                filePath = file.getAbsolutePath();
                queryTxt.setText(filePath.toString());
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    public void textChanged(InputMethodEvent e)
    {
        
        if (!queryTxt.getText().isEmpty())
        {
            threshold.setDisable(false);
            searchBtn.setDisable(false);
        }
        
    }    
   
    
  
}
