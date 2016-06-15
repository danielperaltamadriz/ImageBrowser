/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagebrowser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class Documents
{

    public void openDocument(String path)
    {

    }

    public void newImages()
    {
        System.out.println("Abriendo carpeta con documentos");
        try
        {
            Files.walk(Paths.get(System.getProperty("user.dir") + "/Images/New")).forEach(filePath -> 
                    {
                        if (Files.isRegularFile(filePath))
                        {
                            System.out.println("Leyendo documento");
                            System.out.println(filePath.toString());
                        }
            });
        }
        catch (IOException ex)
        {
            Logger.getLogger(Documents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(String name, String datos)
    {	
	try
        {
        File file =new File(name);

    	/* This logic is to create the file if the
    	 * file is not already present
    	 */
    	if(!file.exists()){
            try
            {
                System.out.println("Se creó índice");
                file.createNewFile();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Documents.class.getName()).log(Level.SEVERE, null, ex);
            }
    	}

    	//Here true is to append the content to file
        String data[] = datos.split("\\$, ");
        
    	FileWriter fw = new FileWriter(file,true);
    	//BufferedWriter writer give better performance
    	BufferedWriter bw = new BufferedWriter(fw);
        for(String s : data)
        {
            s = s.replace("[", "");
            s = s.replace("]", "");
            s = s.replace("$", "");
            bw.write(s);
            bw.newLine();
        }
    	//Closing BufferedWriter Stream
    	bw.close();

	System.out.println("Data successfully appended at the end of file");

      }catch(IOException ioe){
         System.out.println("Exception occurred:");
    	 ioe.printStackTrace();
       } 
    }
    
    public File openFile(String path) throws IOException
    {
        File file = new File(path);
        if(!file.exists() && !file.isDirectory())
        {
            file.createNewFile();
        }
        return file;
    }
        

    public static LinkedList<String> getAllImages(File directory) throws IOException
    {
        LinkedList<String> resultList = new LinkedList<String>();
        File[] f = directory.listFiles();
        for (File file : f)
        {
            if (file != null && file.getName().toLowerCase().endsWith(".jpg"))
            {
                resultList.add(file.getPath());
            }
            else if (file != null && file.getName().toLowerCase().endsWith(".png"))
            {
                resultList.add(file.getPath());
            }
        }

        if (resultList.size() > 0)
        {
            return resultList;
        }
        else
        {
            return null;
        }
    }

}
