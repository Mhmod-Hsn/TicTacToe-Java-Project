/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;


import database.playerinfo.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;

import java.net.Socket;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Hossam
 */

public class PlayerHandler extends Thread{
    
    private Socket playerSocket;
    private Player player;
    
    private Player playerRequest;
    
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    
    private JSONObject json;
    private JSONParser parser;
    
    public static Vector<Player> onlinePlayersVect;
    
    public PlayerHandler(Socket socket, Player playerInfo)
    {
        this.playerSocket = socket;
        this.player = playerInfo;
        
        json = new JSONObject();
        parser = new JSONParser();
        
        try {
            //Create the input and output channels
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            
            //return the info to the list of players (logged in client)
            outputStream.writeObject(player);
            
            //add the player to the online players vector
            onlinePlayersVect.add(player);
            
            //set the player status into signned in 
            
            
            //Start the thread to accept requests
            this.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            close();
        }
    }
    
    @Override
    public void run()
    {
        //Listen to the requests of the users
        while (true)
        {
            try {
//                playerRequest = (player)inputStream.readObject();
                
                /*
                if () //Sign in request
                {
                }
                else if ()//Sign up request
                {
                }
                else //Null (invalid request)
                {   
                    close();
                }
                */
                //Connection Drop
            } catch (IOException | ClassNotFoundException ex) { 
                //close this socket and end this thread
                ex.printStackTrace();
                close();
            } 
        }
    }
    
    public void close()
    {
        try {
            //remove this current player from online vector
            onlinePlayersVect.remove(this.player);
            
            //Close the connection
            playerSocket.close();
            this.stop();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}