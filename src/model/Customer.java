package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final int id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;

    public Customer(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email == null ? "" : email;
        this.address = address == null ? "" : address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    public static List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, name, phone, email, address "
                + "FROM customers ORDER BY id DESC";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                customers.add(new Customer(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("phone"),
                        result.getString("email"),
                        result.getString("address")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            connection.close();
        }
        return customers;
    }

    public void add() throws SQLException {
        String sql = "INSERT INTO customers (name, phone, email, address) "
                + "VALUES (?, ?, ?, ?)";

        Connection connection = DBConnection.getConnection();

        try {
            checkDuplicate(connection);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                fillStatement(statement);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            connection.close();
        }
    }

    public void update() throws SQLException {
        String sql = "UPDATE customers SET name = ?, phone = ?, email = ?, "
                + "address = ? WHERE id = ?";

        Connection connection = DBConnection.getConnection();

        try {
            checkDuplicate(connection);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                fillStatement(statement);
                statement.setInt(5, id);
                if (statement.executeUpdate() == 0) {
                    throw new SQLException("Customer no longer exists. Refresh the list.", "02000");
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            connection.close();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Customer no longer exists. Refresh the list.", "02000");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            connection.close();
        }
    }

    private void fillStatement(PreparedStatement statement) throws SQLException {
        statement.setString(1, name);
        statement.setString(2, phone);
        statement.setString(3, email);
        statement.setString(4, address);
    }

    private void checkDuplicate(Connection connection) throws SQLException {
        String sql = "SELECT id FROM customers "
                + "WHERE name = ? AND phone = ? AND id <> ? LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setInt(3, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    throw new SQLException(
                            "A customer with this name and phone already exists.", "23000", 1062);
                }
            }
        }
    }
}
