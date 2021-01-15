/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import handler.*;

/**
 *
 * @author Hossam
 */
public class Server {
    
    private ServerSocket serverSocket;
    private boolean isServerUp;
    private ClientAcceptListener clientAcceptListener;
    
    //Client accept listener inner class
    private class ClientAcceptListener extends Thread {

        private Socket clientSocket;
        
        //Listen to connection request
        @Override
        public void run() {
            while (true){
                try {
                    clientSocket = serverSocket.accept();
                    //init thread to receive the client
                    new AuthenHandler(clientSocket);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    //Start the server
    public void start()
    {
        try {
        
        //Create the server socket
        serverSocket = new ServerSocket(ServerUtils.PORT_NUMBER);
            
        //Create the clients connection request accept thread
        clientAcceptListener = new ClientAcceptListener();
            
        //set server is up flag
        isServerUp = true;
        
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //Stop the server
    public void stop()
    {
        try {
        
        //close the server port
        serverSocket.close();
        
        //close the listener thread
        clientAcceptListener.stop();
        
        //reset server is up flag
        isServerUp = false;
        
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}