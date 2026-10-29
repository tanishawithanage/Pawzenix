package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * The SQL for the Overview (dashboard) page. Each method answers one question.
 * Cancelled and No-show appointments are never counted as visits.
 */
public class DashboardDAO
{
    // How many visits are planned today.
    public int countVisits(LocalDate today) throws CustomException
    {
        String sql = "SELECT COUNT(*) FROM appointments "
                   + "WHERE DATE(start_at) = ? AND status NOT IN ('Cancelled', 'No-show')";
        return getNumber(sql, today).intValue();
    }

    // How many pets are registered.
    public int countPets() throws CustomException
    {
        return getNumber("SELECT COUNT(*) FROM pets", null).intValue();
    }

    // Money received today (bills marked Paid today).
    public BigDecimal getCollected(LocalDate today) throws CustomException
    {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM bills "
                   + "WHERE payment_status = 'Paid' AND DATE(paid_at) = ?";
        return getNumber(sql, today);
    }

    // Money still to be received (all Unpaid bills).
    public BigDecimal getOutstanding() throws CustomException
    {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM bills WHERE payment_status = 'Unpaid'";
        return getNumber(sql, null);
    }

    // Today's appointments, one row for each: time, pet, owner, service, care provider, status.
    public List<Object[]> getTodaySchedule(LocalDate today) throws CustomException
    {
        List<Object[]> rows = new ArrayList<>();
        String sql = "SELECT a.start_at, p.name AS pet, c.name AS owner, s.name AS service, t.name AS staff, a.status "
                   + "FROM appointments a "
                   + "JOIN pets p ON p.id = a.pet_id "
                   + "JOIN customers c ON c.id = p.customer_id "
                   + "JOIN services s ON s.id = a.service_id "
                   + "LEFT JOIN staff t ON t.id = a.staff_id "
                   + "WHERE DATE(a.start_at) = ? ORDER BY a.start_at, a.id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setDate(1, Date.valueOf(today));

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    String staff = rs.getString("staff");
                    if (staff == null)
                    {
                        staff = "Unassigned";
                    }

                    rows.add(new Object[]
                    {
                        rs.getTimestamp("start_at").toLocalDateTime().toLocalTime(),
                        rs.getString("pet"),
                        rs.getString("owner"),
                        rs.getString("service"),
                        staff,
                        rs.getString("status")
                    });
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return rows;
    }

    // Visits for each of the last 7 days. Position 0 is six days ago, position 6 is today.
    public int[] getVisitsLastWeek(LocalDate today) throws CustomException
    {
        int[] counts = new int[7];
        LocalDate firstDay = today.minusDays(6);
        String sql = "SELECT DATE(start_at) AS day, COUNT(*) AS total FROM appointments "
                   + "WHERE start_at >= ? AND start_at < ? AND status NOT IN ('Cancelled', 'No-show') "
                   + "GROUP BY DATE(start_at)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setTimestamp(1, Timestamp.valueOf(firstDay.atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(today.plusDays(1).atStartOfDay()));

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    // how many days after the first day is this row?
                    int position = (int) ChronoUnit.DAYS.between(firstDay, rs.getDate("day").toLocalDate());
                    counts[position] = rs.getInt("total");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return counts;
    }

    // Up to 10 Booked or Confirmed appointments in the next 7 days, as ready-to-show text lines.
    public List<String> getUpcoming(LocalDateTime now) throws CustomException
    {
        List<String> lines = new ArrayList<>();
        String sql = "SELECT a.start_at, p.name AS pet, s.name AS service "
                   + "FROM appointments a "
                   + "JOIN pets p ON p.id = a.pet_id "
                   + "JOIN services s ON s.id = a.service_id "
                   + "WHERE a.start_at >= ? AND a.start_at < ? AND a.status IN ('Booked', 'Confirmed') "
                   + "ORDER BY a.start_at LIMIT 10";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setTimestamp(1, Timestamp.valueOf(now));
            ps.setTimestamp(2, Timestamp.valueOf(now.plusDays(7)));

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    String time = rs.getTimestamp("start_at").toLocalDateTime().toString().replace('T', ' ');
                    lines.add(time + " - " + rs.getString("pet") + " - " + rs.getString("service"));
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return lines;
    }

    // Runs a query that returns one number (a count or a sum). day is the optional date for the ? in the query.
    private BigDecimal getNumber(String sql, LocalDate day) throws CustomException
    {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            if (day != null)
            {
                ps.setDate(1, Date.valueOf(day));
            }

            try (ResultSet rs = ps.executeQuery())
            {
                rs.next();
                return rs.getBigDecimal(1);
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }
}
