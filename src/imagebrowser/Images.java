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
                Image image = new Image("file:"+s);                
                Img img = colorHistogram(image);                                
                File source = new File(s.toString());                
                File destination = new File(Paths.get(System.getProperty("user.dir") + "/src/Images/Added/"+p.getFileName().toString()).toString());
                if (!destination.exists()) 
                {
                    source.renameTo(destination);
                }
                index.add(destination.getName()+":"+img.array1ToString()+";"+img.array2ToString()+";"+img.array3ToString()+"$");
            }
            //System.out.println(index.toString());
            docs.writeFile("index.txt", index.toString());
            System.out.println("Index is done");
        }
        else
        {
            System.out.println("Lista nula");
        }
        
    }
    
    public Img colorHistogram(Image image)
    {
        int length1 = 10;
        int length2 = 10;
        int length3 = 10;
        Img img = new Img();
        img.newArrayR(length1);
        img.newArrayB(length2);
        img.newArrayG(length3);
        PixelReader px = image.getPixelReader();
        for(int i = 0; i < image.getWidth(); i++)
        {
            for(int j = 0; j < image.getHeight(); j++)
            {
                Color color = px.getColor(i, j);
                int red = (int) Math.round((color.getRed())*(length1-1));
                int blue = (int)Math.round(color.getBlue()*(length2-1));
                int green = (int)Math.round(color.getGreen()*(length3-1));
                img.addArray1(red);
                img.addArray2(blue);
                img.addArray3(green);
            }
        }
        return img;        
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
    
    public double compareVectors(int[] arrayA, int[] arrayB)
    {
        double d = 0;
        if(arrayA.length != arrayB.length)
            return -1;
        long sumatoriaA, sumatoriaB, sumatoriaAB, sumatoriaA2, sumatoriaB2;
        sumatoriaA = sumatoriaB = sumatoriaAB = sumatoriaA2 = sumatoriaB2 = 0;        
        final int N = arrayA.length;
        
        for(int i = 0; i < arrayA.length; i++)
        {
            long A = arrayA[i];
            long B = arrayB[i];
            long AB = A * B;
            long A2 = (long) Math.pow(A, 2);
            long B2 = (long) Math.pow(B, 2);
            sumatoriaA += A;
            sumatoriaB += B;            
            sumatoriaAB += AB;
            sumatoriaA2 += A2;
            sumatoriaB2 += B2;
        }
        d = ((N*sumatoriaAB)   - ((sumatoriaA * sumatoriaB)/N));
        double raiz = Math.sqrt(((N*sumatoriaA2) - (Math.pow(sumatoriaA, 2)/N))*((N*sumatoriaB2) - (Math.pow(sumatoriaB, 2)/N)));
        printArray(arrayA);
        printArray(arrayB);
        return (d/raiz);
    }
    public void printArray(int[] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            System.out.print(array[i]);
            System.out.print(" ");
        }
        System.out.println("");
    }
}
