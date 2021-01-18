/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author ahmed
 */
public interface DBConfig {
    static final String DB_URL = "jdbc:mysql://localhost:3306/tictactoedb";
    //   static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_DRV = "com.mysql.cj.jdbc.Driver";
    static final String DB_USER = "game";
    static final String DB_PASSWD = "123";    
}
