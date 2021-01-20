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
    public static final String SIGN_OUT = "signout";

    //Game playing related
    public static final String PLAY_INVITATION = "player invite";
    public static final String UPDATE_SCORE = "update score";
    
    //Unkown requests
    public static final String UNKNOWN = "unknown";
    
}

