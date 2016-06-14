/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagebrowser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Daniel
 */
public class Images {
    
    
    private Documents docs = new Documents();
    public void createIndex() throws IOException
    {
        LinkedList<String> images = openPictures(Paths.get(System.getProperty("user.dir") + "/src/Images/New").toFile());
        LinkedList<String> index = new LinkedList<String>();
        if (images != null)
        {            
            for (String s : images)
            {
                Path p = Paths.get(s);
                int[][][] arrayRGB = new int[4][4][4];               
                Image image = new Image("file:"+s);                
                PixelReader px = image.getPixelReader();
                for(int i = 0; i < image.getWidth(); i++)
                {
                    for(int j = 0; j < image.getHeight(); j++)
                    {
                        Color color = px.getColor(i, j);
                        int red = (int) (color.getRed()*(arrayRGB.length-1));
                        int blue = (int)(color.getBlue()*(arrayRGB.length-1));
                        int green = (int)(color.getGreen()*(arrayRGB.length-1));
                        arrayRGB[red][green][blue]++;
                    }
                }
                
                File source = new File(s.toString());
                
                File destination = new File(Paths.get(System.getProperty("user.dir") + "/src/Images/Added/"+p.getFileName().toString()).toString());
                if (!destination.exists()) 
                {
                    source.renameTo(destination);
                }
                index.add(destination.getName()+":"+Arrays.toString(matrixToArray(arrayRGB))+"$");
            }
            System.out.println(index.toString());
            docs.writeFile("index.idx", index.toString());
            System.out.println("Index is done");
        }
        else
        {
            System.out.println("Lista nula");
        }
        
    }
    
    public int[] matrixToArray(int[][][] matrix)
    {
        int[] array = new int[matrix.length*matrix.length*matrix.length];
        int z = 0;
        
        for(int i = 0; i< matrix.length;i++)
        {
            for(int j = 0;j< matrix.length; j++)
            {
                for(int k = 0; k < matrix.length; k++)
                {
                    array[z] = matrix[i][j][k];
                    
                    z++;
                }
            }
        }
        
        return array;
    }
    
    public LinkedList<String> openPictures(File path) throws IOException
    {
        return docs.getAllImages(path);        
    }
}
