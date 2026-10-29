package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the appointments table, including the rules that need the database:
 * the length of a visit comes from its service, and a pet or staff member cannot have two visits at the same time.
 */
public class AppointmentDAO
{
    // All appointments with the pet, service and staff names, newest first.
    public List<Appointment> findAll() throws CustomException
    {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.*, p.name AS pet_name, p.customer_id, s.name AS service_name, t.name AS staff_name "
                   + "FROM appointments a "
                   + "JOIN pets p ON p.id = a.pet_id "
                   + "JOIN services s ON s.id = a.service_id "
                   + "LEFT JOIN staff t ON t.id = a.staff_id "      // LEFT JOIN because staff is optional
                   + "ORDER BY a.start_at DESC, a.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Appointment a = new Appointment();
                a.setId(rs.getInt("id"));
                a.setPetId(rs.getInt("pet_id"));
                a.setServiceId(rs.getInt("service_id"));

                // staff_id can be empty (Unassigned), so we keep null in that case
                int staffId = rs.getInt("staff_id");
                if (rs.wasNull())
                {
                    a.setStaffId(null);
                }
                else
                {
                    a.setStaffId(staffId);
                }

                a.setStart(rs.getTimestamp("start_at").toLocalDateTime());
                a.setDuration(rs.getInt("duration_minutes"));
                a.setStatus(rs.getString("status"));
                a.setNotes(rs.getString("notes"));
                a.setPetName(rs.getString("pet_name"));
                a.setServiceName(rs.getString("service_name"));
                a.setStaffName(rs.getString("staff_name"));
                a.setCustomerId(rs.getInt("customer_id"));
                appointments.add(a);
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return appointments;
    }

    public void insert(Appointment a) throws CustomException
    {
        String sql = "INSERT INTO appointments (pet_id, service_id, staff_id, start_at, duration_minutes, status, notes) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection())
        {
            // the visit is as long as its service
            int minutes = getServiceMinutes(con, a.getServiceId());
            checkTime(con, a, minutes, 0);

            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, a.getPetId());
                ps.setInt(2, a.getServiceId());
                setStaff(ps, 3, a.getStaffId());
                ps.setTimestamp(4, Timestamp.valueOf(a.getStart()));
                ps.setInt(5, minutes);
                ps.setString(6, a.getStatus());
                ps.setString(7, a.getNotes());
                ps.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void update(Appointment a) throws CustomException
    {
        String sql = "UPDATE appointments SET pet_id = ?, service_id = ?, staff_id = ?, start_at = ?, "
                   + "duration_minutes = ?, status = ?, notes = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection())
        {
            int minutes = getServiceMinutes(con, a.getServiceId());

            // If the service was not changed, keep the length already saved.
            // This way an old visit keeps its length even if the service was edited later.
            String find = "SELECT service_id, duration_minutes FROM appointments WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(find))
            {
                ps.setInt(1, a.getId());

                try (ResultSet rs = ps.executeQuery())
                {
                    if (!rs.next())
                    {
                        throw new CustomException("Appointment no longer exists. Refresh the list.");
                    }
                    if (rs.getInt("service_id") == a.getServiceId())
                    {
                        minutes = rs.getInt("duration_minutes");
                    }
                }
            }

            checkBillCustomer(con, a);
            checkTime(con, a, minutes, a.getId());

            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, a.getPetId());
                ps.setInt(2, a.getServiceId());
                setStaff(ps, 3, a.getStaffId());
                ps.setTimestamp(4, Timestamp.valueOf(a.getStart()));
                ps.setInt(5, minutes);
                ps.setString(6, a.getStatus());
                ps.setString(7, a.getNotes());
                ps.setInt(8, a.getId());
                ps.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void delete(int id) throws CustomException
    {
        String sql = "DELETE FROM appointments WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Appointment no longer exists. Refresh the list.");
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    // The length (in minutes) of a service.
    private int getServiceMinutes(Connection con, int serviceId) throws SQLException, CustomException
    {
        String sql = "SELECT duration_minutes FROM services WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, serviceId);

            try (ResultSet rs = ps.executeQuery())
            {
                if (!rs.next())
                {
                    throw new CustomException("Service no longer exists. Refresh the list.");
                }
                return rs.getInt("duration_minutes");
            }
        }
    }

    // If this appointment already has a bill, the pet must still belong to the billed customer.
    private void checkBillCustomer(Connection con, Appointment a) throws SQLException, CustomException
    {
        String sql = "SELECT b.id FROM bills b JOIN pets p ON p.id = ? "
                   + "WHERE b.appointment_id = ? AND b.customer_id <> p.customer_id LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, a.getPetId());
            ps.setInt(2, a.getId());

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    throw new CustomException("The linked bill belongs to a different customer. Choose a pet of the billed customer.");
                }
            }
        }
    }

    // Checks the length, then checks that the pet and the staff member are free at this time.
    // ignoreId is the appointment being edited (0 for a new one), so it does not clash with itself.
    private void checkTime(Connection con, Appointment a, int minutes, int ignoreId) throws SQLException, CustomException
    {
        if (minutes < 1 || minutes > 1440)
        {
            throw new CustomException("Service duration must be 1 to 1440 minutes.");
        }

        // cancelled and no-show appointments do not block anyone
        if (a.getStatus().equals("Cancelled") || a.getStatus().equals("No-show"))
        {
            return;
        }

        // Two visits overlap when each one starts before the other one ends.
        String sql = "SELECT id FROM appointments "
                   + "WHERE id <> ? AND status NOT IN ('Cancelled', 'No-show') "
                   + "AND (pet_id = ? OR staff_id = ?) "
                   + "AND start_at < ? "                                       // starts before the new visit ends
                   + "AND TIMESTAMPADD(MINUTE, duration_minutes, start_at) > ? LIMIT 1";   // ends after the new visit starts

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, ignoreId);
            ps.setInt(2, a.getPetId());
            setStaff(ps, 3, a.getStaffId());
            ps.setTimestamp(4, Timestamp.valueOf(a.getStart().plusMinutes(minutes)));
            ps.setTimestamp(5, Timestamp.valueOf(a.getStart()));

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    throw new CustomException("The pet or staff member already has an overlapping appointment.");
                }
            }
        }
    }

    // Saves the staff id, or an empty value (NULL) when no staff member is chosen.
    private void setStaff(PreparedStatement ps, int position, Integer staffId) throws SQLException
    {
        if (staffId == null)
        {
            ps.setNull(position, Types.INTEGER);
        }
        else
        {
            ps.setInt(position, staffId);
        }
    }
}
