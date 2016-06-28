/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagebrowser;

import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author Daniel
 */
public class Img
{
    private Vector<Integer> array1, array2, array3;
    private int[] arrayR, arrayB, arrayG;
    private String name = "";
    
    public Img()
    {
        array1 = new Vector<Integer>();
        array2 = new Vector<Integer>();
        array3 = new Vector<Integer>();
        
    }
    
    public void newArrayR(int n)
    {
        arrayR = new int[n];
        for(int i = 0;i <  arrayR.length;i++)
        {
            arrayR[i] = 0;
        }
    }
    public void newArrayB(int n)
    {
        arrayB = new int[n];
        for(int i = 0;i <  arrayB.length;i++)
        {
            arrayB[i] = 0;
        }
    }
    public void newArrayG(int n)
    {
        arrayG = new int[n];
        for(int i = 0;i <  arrayG.length;i++)
        {
            arrayG[i] = 0;
        }
    }
    
    
    public void setName(String n)
    {
        name = n;
    }
    public void addArray1(int i)
    {
        arrayR[i]++;
    }
    public void addArray2(int i)
    {
        arrayB[i]++;
    }
    public void addArray3(int i)
    {
        arrayG[i]++;   
    }
    
    public void setArray1(int i, int n)
    {       
        arrayR[i] = n;
    }
    public void setArray2(int i, int n)
    {      
        arrayB[i] = n;
    }
    public void setArray3(int i, int n)
    {      
        arrayG[i] = n;
    }
    public Vector<Integer> getArray1()
    {   
        Vector<Integer> retorno = new Vector<Integer>();
        for(int i : arrayR)
            retorno.add(i);
        return retorno;
    }
    public Vector<Integer> getArray2()
    {
        Vector<Integer> retorno = new Vector<Integer>();
        for(int i : arrayB)
            retorno.add(i);
        return retorno;
    }
    public Vector<Integer> getArray3()
    {
        Vector<Integer> retorno = new Vector<Integer>();
        for(int i : arrayG)
            retorno.add(i);
        return retorno;
    }
    public int[] vectorToArray1()
    {
        return arrayR;
    }
    public int[] vectorToArray2()
    {        
        return arrayB;
    }public int[] vectorToArray3()
    {
        return arrayG;
    }
    
    public String getName()
    {
        return name;
    }
    public String array1ToString()
    {
        return getArray1().toString();       
    }
    public String array2ToString()
    {
        return getArray2().toString();       
    }
    public String array3ToString()
    {
        return getArray3().toString();       
    }
}
