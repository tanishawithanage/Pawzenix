/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Customer;
import java.sql.SQLException;

/**
 *
 * @author TANISHA SANJANA
 */
public class CustomerController {
 
    public void addCustomer(String name, String phone)
            throws SQLException {

        if (name.trim().isEmpty() || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Please fill all fields!");
        }

        Customer c = new Customer(0, name, phone);
        c.addCustomer();
    }

    public void updateCustomer(int id, String name, String phone)
            throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID!");
        }

        Customer c = new Customer(id, name, phone);
        c.updateCustomer();
    }

    public void deleteCustomer(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID!");
        }

        Customer c = new Customer(id, "", "");
        c.deleteCustomer();
    }
}
