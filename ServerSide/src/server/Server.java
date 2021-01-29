/*
    Server class is the main class that handles the server's logic
 */

package server;

import java.net.ServerSocket;
import java.net.Socket;
import server.utils.ServerUtils;
import handler.*;      
import java.io.IOException;


/*
 *
 * @author Hossam Khalil
 */

public class Server {
    
    private ServerSocket serverSocket;
    private boolean isServerUp = false;
    private UpdatesHandler updateHandler;
    
    private ClientAcceptListener clientAcceptListener;
 
    //Client accept listener inner class
    private class ClientAcceptListener extends Thread {

        private Socket clientSocket;
        private volatile boolean isStayAlive = true;
        
        //constructor 
        ClientAcceptListener()
        {
            this.start();
        }
        
        //Listen to connection request
        @Override
        public void run() {
            while (isStayAlive){
                try {
                    clientSocket = serverSocket.accept();

                    //New client is accepted
                    ServerUtils.appendLog("[ClientAcceptListener class]: Client has been accepted. ");
                    
                    //init thread to receive the client
                    new AuthenHandler(clientSocket);
                    
                } catch (IOException ex) {
                    ServerUtils.appendLog("[ClientAcceptListener class]: Connection dropped (client not accepted). ");
                }
            }
        }
        
        public void close()
        {
            isStayAlive = false;
            this.stop();
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
            
            //reset all players to offline
            DBOperations.setAllOffline();
            
            //start update thread
            updateHandler = new UpdatesHandler();
            
            ServerUtils.appendLog("[Server class]: Server is up and running on port:" + ServerUtils.PORT_NUMBER); 

        } catch (IOException ex) {
            ServerUtils.appendLog("[Error]: Server failed to start on port:" + ServerUtils.PORT_NUMBER); 
        }
    }
    
    //Stop the server
    public void stop()
    {
        //check if the server has been up 
        if (isServerUp)
        {
            try {
                //close the server port
                serverSocket.close();

            } catch (IOException ex) {
                ServerUtils.appendLog("[Error]: Server failed to close the port on closing."); 
            }

            //close the listener thread
            clientAcceptListener.close();

            //stop the update handler thread
            updateHandler.close();

            //stop all the online handlers
            PlayerHandler.stopAllHandlers();
            
            //stop all the authen handlers
            AuthenHandler.stopAllHandlers();
            
            //reset all players to offline
            DBOperations.setAllOffline();

            //reset server is up flag
            isServerUp = false;
        }

        ServerUtils.appendLog("[Server class]: Server stopped successfully.");
    }
}