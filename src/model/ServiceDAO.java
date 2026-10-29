package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the services table.
 */
public class ServiceDAO
{
    // All services, newest first.
    public List<Service> findAll() throws CustomException
    {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT id, name, category, duration_minutes FROM services ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setCategory(rs.getString("category"));
                service.setDuration(rs.getInt("duration_minutes"));
                services.add(service);
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return services;
    }

    public void insert(Service service) throws CustomException
    {
        String sql = "INSERT INTO services (name, category, duration_minutes) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, service.getName());
            ps.setString(2, service.getCategory());
            ps.setInt(3, service.getDuration());
            ps.executeUpdate();
        }
        catch (SQLException ex)
        {
            throw makeError(ex);
        }
    }

    public void update(Service service) throws CustomException
    {
        String sql = "UPDATE services SET name = ?, category = ?, duration_minutes = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, service.getName());
            ps.setString(2, service.getCategory());
            ps.setInt(3, service.getDuration());
            ps.setInt(4, service.getId());

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Service no longer exists. Click Refresh.");
            }
        }
        catch (SQLException ex)
        {
            throw makeError(ex);
        }
    }

    public void delete(int id) throws CustomException
    {
        String sql = "DELETE FROM services WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Service no longer exists. Click Refresh.");
            }
        }
        catch (SQLException ex)
        {
            throw makeError(ex);
        }
    }

    // Messages that are specific to services.
    private CustomException makeError(SQLException ex)
    {
        if (ex.getErrorCode() == 1062)
        {
            // the service name column is UNIQUE
            return new CustomException("A service with this name already exists.");
        }
        else if (ex.getErrorCode() == 1451)
        {
            // appointments or bill items still use this service
            return new CustomException("Cannot delete this service because other records are linked to it.");
        }
        return new CustomException(ex);
    }
}
