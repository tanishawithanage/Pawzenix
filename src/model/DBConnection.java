/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TANISHA SANJANA
 */
public class DBConnection {
      public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/pawzenix","root","");
        } 
        
        catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL driver class was not found. Check the Connector/J JAR.",
                    e
            );
        }
    }
}
      
