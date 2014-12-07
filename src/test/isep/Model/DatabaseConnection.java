package test.isep.Model;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DatabaseConnection {

    private String            url    = "jdbc:mysql://localhost/projetweb";
    private String            user   = "root";
    private String            passwd = "";

    private static Connection connect;

    private DatabaseConnection() {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {

            e.getMessage();
        }
        try {
            connect = (Connection) DriverManager.getConnection( url, user, passwd );
            System.out.println( "Connection to the DB is a success! powered by Log4J" );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if ( connect == null ) {
            new DatabaseConnection();
        }
        return connect;
    }
}
