/*
    The configuration interface that is used to configure the database connection
    parameters
 */
package database;

/**
 *
 * @author Ahmed Mamdouh Abdel-Wahab
 */

public interface DBConfig {
    
    static final String DB_SERVER_PORT = "8080";

    static final String DB_NAME = "tictactoedb" ;
    static final String DB_SCHEME = "mysql" ;
    static final String DB_PORT = "3306" ;
    static final String DB_IP = "localhost" ;
    static final String DB_USER = "game";
    static final String DB_PASSWD = "123";
    
    static final String DB_SERVER_URL = "http://"+DB_IP+":"+DB_SERVER_PORT+"/phpmyadmin/db_structure.php?server=1&db="+DB_NAME;
    
    static final String DB_URL = "jdbc:"+DB_SCHEME+"://"+DB_IP+":"+DB_PORT+"/"+DB_NAME;
    static final String DB_DRV = "com.mysql.cj.jdbc.Driver";

}
