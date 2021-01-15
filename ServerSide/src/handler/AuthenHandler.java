/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Hossam
 */

public class AuthenHandler extends Thread {
    
    Socket socket;
    UserInfo info;
    
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    
    public AuthenHandler(Socket socket)
    {
        this.socket = socket;
        try {
            //Create the input and output channels
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            //Start the thread
            this.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            try {
                info = (UserInfo)inputStream.readObject();
                
                if (info.isSignIn()) //Sign in request
                {
                    PlayerInfo result = signIn(info.getUname(),info.getPassword());
                }
                else if (! info.isSignIn())//Sign up request
                {
                    boolean result = signUp(info.getUname(),info.getPassword());
                }
                else //Null (invalid request)
                {   
                    close();
                }
                
                //Connection Drop
            } catch (IOException | ClassNotFoundException ex) { 
                //close this socket and end this thread
                ex.printStackTrace();
                close();
                
            } 
        }
    }
    
    private PlayerInfo signIn(String uname, String password)
    {
        PlayerInfo result = new PlayerInfo();
        return result;
    }
    
    private boolean signUp (String uname, String password)
    {
        boolean result = false;
        
        return result;
    }
    
    public void close()
    {
        try {
            socket.close();
            this.stop();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
