/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author Ahmed Mamdouh
 */
public class DatabaseDriver {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private String queryStr;
    private ResultSet resultSet; // used for store data from database    
    public static Boolean DB_CON_STATUS = false ;

    public DatabaseDriver(){
        //startConnection();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public void setQuerystr(String querystr) {
        this.queryStr = querystr;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public String getQuerystr() {
        return queryStr;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void startConnection() {
        try {
            Class.forName(DBConfig.DB_DRV);
            connection = DriverManager.getConnection(DBConfig.DB_URL,DBConfig.DB_USER,DBConfig.DB_PASSWD);
            DB_CON_STATUS = true ;
        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(DatabaseDriver.class.getName()).log(Level.SEVERE, null, ex);
            DB_CON_STATUS = false ;
            //System.out.println("Cann't connenct to Database : "+ DBConfig.DB_NAME );
        }
    }
    public void endStatConnection() {
        try {
            //resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseDriver.class.getName()).log(Level.SEVERE, null, ex);
              //System.out.println("Cann't End DB :"+DBConfig.DB_NAME+" connenction .. ");
        }
    }
    
    public void endPStatConnection() {
        try {
            //resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
             //System.out.println("Cann't End DB :"+DBConfig.DB_NAME+" connenction .. ");
        }
    }
    
     public void endResultSet() {
        try {
            resultSet.close();
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseDriver.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Cann't End ResultSet Connection of DB :"+DBConfig.DB_NAME);
        }
     }
}
