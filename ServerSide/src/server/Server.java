/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;



import java.net.ServerSocket;
import java.net.Socket;

import server.utils.ServerUtils;
import handler.*;      

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author Hossam
 */
public class Server {
    
    private ServerSocket serverSocket;
    private boolean isServerUp;
    private UpdatesHandler updateHandler;
    
    private ClientAcceptListener clientAcceptListener;
 
    //Client accept listener inner class
    private class ClientAcceptListener extends Thread {

        private Socket clientSocket;
        
        //constructor 
        ClientAcceptListener()
        {
            this.start();
        }
        
        //Listen to connection request
        @Override
        public void run() {
            while (true){
                try {
                    clientSocket = serverSocket.accept();

                    //New client is accepted
                    System.out.println("Client is accepted. ");
                    
                    //init thread to receive the client
                    new AuthenHandler(clientSocket);
                    
                } catch (IOException ex) {
                    System.out.println("Connection dropped client not accepted. ");
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
            
            //reset the online handlers
             PlayerHandler.resetHandlers();
             
            //reset all players to offline
            DBOperations.setAllOffline();
            
            //start update thread
            updateHandler = new UpdatesHandler();
            
            System.out.println("Server is up on port:" + ServerUtils.PORT_NUMBER); 

        } catch (IOException ex) {
            System.out.print("Exception in server start");
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
        
        //stop the update handler thread
        updateHandler.close();
        
        //reset server is up flag
        isServerUp = false;
        
        
        System.out.println("Server is stopped.");
        
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String [] args)
    {
        new Server().start();
    }
}