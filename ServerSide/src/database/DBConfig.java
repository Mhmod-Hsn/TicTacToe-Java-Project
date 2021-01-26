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
    static final String DB_SERVER_URL = "http://localhost:8080/phpmyadmin/db_structure.php?server=1&db=tictactoedb";
//    com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/persondatabase", "root", ""
    static final String DB_NAME = "tictactoedb" ;
    static final String DB_SCHEME = "mysql" ;
    static final String DB_PORT = "3306" ;
    static final String DB_IP = "localhost" ;

    static final String DB_URL = "jdbc:"+DB_SCHEME+"://"+DB_IP+":"+DB_PORT+"/"+DB_NAME;


    //static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "root";
    static final String DB_PASSWD = "";
    
//"com.mysql.cj.jdbc.Driver"
}
