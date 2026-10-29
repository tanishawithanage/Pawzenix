package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the staff table.
 */
public class StaffDAO
{
    // All staff members, newest first.
    public List<Staff> findAll() throws CustomException
    {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT id, name, role, phone, email FROM staff ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Staff staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setRole(rs.getString("role"));
                staff.setPhone(rs.getString("phone"));
                staff.setEmail(rs.getString("email"));
                staffList.add(staff);
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return staffList;
    }

    public void insert(Staff staff) throws CustomException
    {
        String sql = "INSERT INTO staff (name, role, phone, email) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getRole());
            ps.setString(3, staff.getPhone());
            ps.setString(4, staff.getEmail());
            ps.executeUpdate();
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void update(Staff staff) throws CustomException
    {
        String sql = "UPDATE staff SET name = ?, role = ?, phone = ?, email = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getRole());
            ps.setString(3, staff.getPhone());
            ps.setString(4, staff.getEmail());
            ps.setInt(5, staff.getId());

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Record no longer exists. Refresh the list.");
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void delete(int id) throws CustomException
    {
        String sql = "DELETE FROM staff WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Record no longer exists. Refresh the list.");
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }
}
