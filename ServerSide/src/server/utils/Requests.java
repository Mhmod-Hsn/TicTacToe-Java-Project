/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.utils;

/**
 *
 * @author Hossam
 */
public class Requests {
    
    //Authentication related
    public static final String SIGN_IN = "signin";
    public static final String SIGN_UP = "signup";
    public static final String CLOSE = "close";
    public static final String SIGN_OUT = "signout";

    //Game related
    public static final String SEND_INVITATION = "invitePlayer";
    public static final String RECEIVE_INVITATION = "invitation";
    public static final String UPDATE_SCORE = "updateScore";
    
    //Unkown requests
    public static final String UNKNOWN = "unknown";
    
    //List
    public static final String UPDATE_LIST = "updateList";
    
}

