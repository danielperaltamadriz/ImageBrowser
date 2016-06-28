/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagebrowser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;
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
    
    public String openFile(String path) throws IOException
    {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        return content;
    }
    
    public Hashtable<String, Vector<Integer>> docToList(String file)
    {
        Hashtable<String, Vector<Integer>> retorno = new Hashtable<String, Vector<Integer>>();
        file = file.replace(" ", "");
        String[] imagenes = file.split("\n");
        for(String img : imagenes)
        {
            String[] nombre = img.split(":");
            String[] valoresStr = nombre[1].split(",");            
            Vector<Integer> valoresInt = new Vector<Integer>();
            for(String s : valoresStr)
            {
                try
                {
                    int i = Integer.parseInt(s);               
                    valoresInt.add(i);
                }
                catch(NumberFormatException e)
                {
                    valoresInt.add(0);
                }
            }
            retorno.put(nombre[0], valoresInt);
        }
        
        return retorno;
        
    }
    public Hashtable<String, Img> docToImg(String file)
    {
       
        Hashtable<String, Img> retorno = new Hashtable<String, Img>();
        file = file.replace(" ","");
        file = file.replaceAll("\r", "");
        String[] imagenes = file.split("\n");
        for(String img : imagenes)             
        {
            Img features = new Img();
            String[] nombre = img.split(":");
            String[] valoresStr = nombre[1].split(";");            
            String[] valores = valoresStr[0].split(","); 
            
            features.newArrayR(valores.length);
            for(int i = 0; i <valores.length; i++)                        
            {
                System.out.println(valores[i]);
                int j = Integer.parseInt(valores[i]);
                features.setArray1(i, j);
            }              
            valores = valoresStr[1].split(",");  
            features.newArrayB(valores.length);
            for(int i = 0; i <valores.length; i++)                        
            {
                System.out.println(valores[i]);
                int j = Integer.parseInt(valores[i]);
                features.setArray2(i, j);
            }              
            valores = valoresStr[2].split(",");  
            features.newArrayG(valores.length);
            for(int i = 0; i <valores.length; i++)                        
            {
                System.out.println(valores[i]);
                int j = Integer.parseInt(valores[i]);
                features.setArray3(i, j);
            }  
        
            retorno.put(nombre[0], features);
        }
        
        return retorno;
        
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
