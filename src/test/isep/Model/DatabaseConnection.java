package test.isep.Model;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

public class DatabaseConnection {

    private String            url    = "jdbc:mysql://localhost/projetweb";
    private String            user   = "root";
    private String            passwd = "";

    private static Connection connect;

    static Logger log = Logger.getLogger(DatabaseConnection.class);

    private DatabaseConnection() 
    {
        try 
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        } 
        catch ( ClassNotFoundException e ) 
        {
            e.getMessage();
        }
        try 
        {
            connect = (Connection) DriverManager.getConnection( url, user, passwd );
            log.info( "Connection to the DB is a success!" );
        } 
        catch ( SQLException e ) 
        {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() 
    {
        if ( connect == null ) 
        {
            new DatabaseConnection();
        }
        return connect;
    }
}
