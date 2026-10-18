/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
/**
 *
 * @author TANISHA SANJANA
 */
public class Customer {

    private int id;
    private String name;
    private String phone;

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    
    // CREATE - Add customer
    public void addCustomer() throws SQLException {

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO customers (name, phone) VALUES (?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.executeUpdate();
        } finally {
            con.close();
        }
    }

    // UPDATE - Update customer
    public void updateCustomer() throws SQLException {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE customers SET name=?, phone=? WHERE id=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setInt(3, id);
            pst.executeUpdate();
        } finally {
            con.close();
        }
    }

    // DELETE - Delete customer
    public void deleteCustomer() throws SQLException {

        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM customers WHERE id=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } finally {
            con.close();
        }
    }
}
    

