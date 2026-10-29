package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the customers table.
 */
public class CustomerDAO
{
    // All customers, newest first.
    public List<Customer> findAll() throws CustomException
    {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, name, phone, email, address FROM customers ORDER BY id DESC";

        // the connection, statement and result close by themselves at the end of this block
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            // read the rows one by one
            while (rs.next())
            {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customers.add(customer);
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return customers;
    }

    public void insert(Customer customer) throws CustomException
    {
        String sql = "INSERT INTO customers (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection())
        {
            checkDuplicate(con, customer);

            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getPhone());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                ps.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void update(Customer customer) throws CustomException
    {
        String sql = "UPDATE customers SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection())
        {
            checkDuplicate(con, customer);

            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getPhone());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                ps.setInt(5, customer.getId());

                // no row changed means somebody deleted this customer
                if (ps.executeUpdate() == 0)
                {
                    throw new CustomException("Customer no longer exists. Refresh the list.");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void delete(int id) throws CustomException
    {
        String sql = "DELETE FROM customers WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Customer no longer exists. Refresh the list.");
            }
        }
        catch (SQLException ex)
        {
            // error 1451 means pets or bills still belong to this customer
            if (ex.getErrorCode() == 1451)
            {
                throw new CustomException("Cannot delete this customer because other records are linked to them.");
            }
            throw new CustomException(ex);
        }
    }

    // Two different customers may not have the same name and phone number.
    private void checkDuplicate(Connection con, Customer customer) throws SQLException, CustomException
    {
        String sql = "SELECT id FROM customers WHERE name = ? AND phone = ? AND id <> ? LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setInt(3, customer.getId());

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    throw new CustomException("A customer with this name and phone already exists.");
                }
            }
        }
    }
}
