package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The SQL for the two Jasper reports.
 * Every row is a Map: the key is the field name used in the .jrxml file, the value is the data.
 */
public class ReportDAO
{
    // Report 1 (appointment_activity.jrxml): every visit between the two dates.
    public List<Map<String, ?>> getAppointments(LocalDate from, LocalDate to) throws CustomException
    {
        List<Map<String, ?>> rows = new ArrayList<>();
        String sql = "SELECT a.id, a.start_at, p.name AS pet, c.name AS customer, s.name AS service, "
                   + "COALESCE(t.name, 'Unassigned') AS staff, a.status "
                   + "FROM appointments a "
                   + "JOIN pets p ON p.id = a.pet_id "
                   + "JOIN customers c ON c.id = p.customer_id "
                   + "JOIN services s ON s.id = a.service_id "
                   + "LEFT JOIN staff t ON t.id = a.staff_id "
                   + "WHERE a.start_at >= ? AND a.start_at < ? "
                   + "ORDER BY a.start_at, a.id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            // the end date is included: the visits run until the start of the next day
            ps.setTimestamp(1, Timestamp.valueOf(from.atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(to.plusDays(1).atStartOfDay()));

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", rs.getInt("id"));
                    row.put("date", rs.getTimestamp("start_at").toLocalDateTime().toString().replace('T', ' '));
                    row.put("pet", rs.getString("pet"));
                    row.put("customer", rs.getString("customer"));
                    row.put("service", rs.getString("service"));
                    row.put("staff", rs.getString("staff"));
                    row.put("status", rs.getString("status"));
                    rows.add(row);
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return rows;
    }

    // Report 2 (invoice_revenue.jrxml): every bill line between the two dates.
    public List<Map<String, ?>> getRevenue(LocalDate from, LocalDate to) throws CustomException
    {
        List<Map<String, ?>> rows = new ArrayList<>();
        String sql = "SELECT b.id, b.bill_date, c.name AS customer, i.service_name AS service, "
                   + "i.quantity, i.unit_price, (i.quantity * i.unit_price) AS line_total, b.payment_status "
                   + "FROM bills b "
                   + "JOIN customers c ON c.id = b.customer_id "
                   + "JOIN bill_items i ON i.bill_id = b.id "
                   + "WHERE b.bill_date BETWEEN ? AND ? "
                   + "ORDER BY b.bill_date, b.id, i.id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", rs.getInt("id"));
                    row.put("date", rs.getDate("bill_date").toString());
                    row.put("customer", rs.getString("customer"));
                    row.put("service", rs.getString("service"));
                    row.put("quantity", rs.getInt("quantity"));
                    row.put("unit_price", rs.getBigDecimal("unit_price"));
                    row.put("line_total", rs.getBigDecimal("line_total"));
                    row.put("status", rs.getString("payment_status"));
                    rows.add(row);
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return rows;
    }
}
