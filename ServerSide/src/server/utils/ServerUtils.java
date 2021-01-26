/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 *
 * @author Hossam
 */

public abstract class ServerUtils {
    public static final int PORT_NUMBER = 7777;
    
    public static final String logsFilePath = "src\\server\\logs\\logs.txt";    
    
    public static Boolean appendLog(String logStr){
        File logsFile =  new File(logsFilePath);
        BufferedWriter out = null ;
        Date d = new Date();
        try { 
            if(logsFile != null ){
                out = new BufferedWriter(new FileWriter(logsFile, true));                  
                out.write("[ "+d.toString()+" ] :  "+logStr+"\n"); 
                //System.out.println("Data Successfully appended into file");                 
            }else{
//                File not found case
                if (logsFile.createNewFile()) {
                    out = new BufferedWriter(new FileWriter(logsFile, true));                  
                    out.write("[ "+d.toString()+" ] :  "+logStr+"\n"); 
                    //System.out.println("Data Successfully appended into file after creation"); 
                }
            }
        }
        catch (IOException io) {
            //System.out.println("Data not appended into file");  // make new file
        } 
        finally { 
            try { 
                out.close(); 
                return true;
            } 
            catch (IOException io) {
                return false; 
            } 
        }
        
    }
    
       
}
        
