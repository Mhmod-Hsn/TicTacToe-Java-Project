/*
    The class that connects with the database
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
 * @author Ahmed Mamdouh Abdel-Wahab
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
            DB_CON_STATUS = false ;
        }
    }
    public void endStatConnection() {
        try {
            //resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
        }
    }
    
    public void endPStatConnection() {
        try {
            //resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
        }
    }
    
     public void endResultSet() {
        try {
            resultSet.close();
        } catch (SQLException ex) {
        }
     }
}
