/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.io.Serializable;

/**
 *
 * @author Hossam
 */
public class UserInfo implements Serializable{
    
    private String uname;
    private String password;
    private boolean isSignIn;

    
    public UserInfo(String uname, String password, boolean isSignIn) {
        this.password = password;
        this.uname = uname;
        this.isSignIn = isSignIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(boolean isSignIn) {
        this.isSignIn = isSignIn;
    }
}