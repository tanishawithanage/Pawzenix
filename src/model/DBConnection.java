package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gives the DAO classes a connection to the MySQL database.
 * If your MySQL has a password, change PASSWORD here.
 */
public class DBConnection
{
    private static final String URL = "jdbc:mysql://localhost:3306/pawzenix";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException
    {
        try
        {
            // loads the MySQL driver (the Connector/J jar must be added to the project libraries)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (ClassNotFoundException ex)
        {
            throw new SQLException("MySQL driver class was not found. Check the Connector/J JAR.", ex);
        }
    }
}
