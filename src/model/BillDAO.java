package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the bills and bill_items tables.
 * A bill and its items are always saved together in one transaction:
 * either everything is saved or nothing is.
 */
public class BillDAO
{
    // All bills with the customer's name, newest first.
    public List<Bill> findAll() throws CustomException
    {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.*, c.name AS customer_name "
                   + "FROM bills b JOIN customers c ON c.id = b.customer_id "
                   + "ORDER BY b.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setCustomerId(rs.getInt("customer_id"));

                // appointment_id can be empty, so we keep null in that case
                int appointmentId = rs.getInt("appointment_id");
                if (rs.wasNull())
                {
                    bill.setAppointmentId(null);
                }
                else
                {
                    bill.setAppointmentId(appointmentId);
                }

                bill.setDate(rs.getDate("bill_date").toLocalDate());
                bill.setTotal(rs.getBigDecimal("total"));
                bill.setPaymentMethod(rs.getString("payment_method"));
                bill.setPaymentStatus(rs.getString("payment_status"));
                bill.setCustomerName(rs.getString("customer_name"));
                bills.add(bill);
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return bills;
    }

    // The lines of one bill.
    public List<BillItem> findItems(int billId) throws CustomException
    {
        List<BillItem> items = new ArrayList<>();
        String sql = "SELECT * FROM bill_items WHERE bill_id = ? ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, billId);

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    BillItem item = new BillItem();
                    item.setServiceId(rs.getInt("service_id"));
                    item.setServiceName(rs.getString("service_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    items.add(item);
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return items;
    }

    public void insert(Bill bill, List<BillItem> items) throws CustomException
    {
        String sql = "INSERT INTO bills (customer_id, appointment_id, bill_date, total, "
                   + "payment_method, payment_status, paid_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection())
        {
            // start a transaction: nothing is final until commit()
            con.setAutoCommit(false);

            try
            {
                checkAppointment(con, bill);

                // a bill that is saved as Paid gets the payment time now
                Timestamp paidAt = null;
                if (bill.getPaymentStatus().equals("Paid"))
                {
                    paidAt = new Timestamp(System.currentTimeMillis());
                }

                int billId;
                try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
                {
                    setBillValues(ps, bill, paidAt);
                    ps.executeUpdate();

                    // read the id that MySQL gave to the new bill
                    try (ResultSet keys = ps.getGeneratedKeys())
                    {
                        keys.next();
                        billId = keys.getInt(1);
                    }
                }

                insertItems(con, billId, items);
                con.commit();
            }
            catch (SQLException | CustomException ex)
            {
                // something failed, so undo everything done since the transaction started
                con.rollback();
                throw ex;
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void update(Bill bill, List<BillItem> items) throws CustomException
    {
        String sql = "UPDATE bills SET customer_id = ?, appointment_id = ?, bill_date = ?, total = ?, "
                   + "payment_method = ?, payment_status = ?, paid_at = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection())
        {
            con.setAutoCommit(false);

            try
            {
                checkAppointment(con, bill);

                // read the payment time that is already saved
                Timestamp paidAt = null;
                String find = "SELECT paid_at FROM bills WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(find))
                {
                    ps.setInt(1, bill.getId());

                    try (ResultSet rs = ps.executeQuery())
                    {
                        if (!rs.next())
                        {
                            throw new CustomException("Bill no longer exists. Refresh the list.");
                        }
                        paidAt = rs.getTimestamp("paid_at");
                    }
                }

                if (bill.getPaymentStatus().equals("Paid"))
                {
                    // keep the old payment time, or set it now if the bill was not paid before
                    if (paidAt == null)
                    {
                        paidAt = new Timestamp(System.currentTimeMillis());
                    }
                }
                else
                {
                    // an Unpaid bill has no payment time
                    paidAt = null;
                }

                try (PreparedStatement ps = con.prepareStatement(sql))
                {
                    setBillValues(ps, bill, paidAt);
                    ps.setInt(8, bill.getId());
                    ps.executeUpdate();
                }

                // replace the old items with the current ones
                try (PreparedStatement ps = con.prepareStatement("DELETE FROM bill_items WHERE bill_id = ?"))
                {
                    ps.setInt(1, bill.getId());
                    ps.executeUpdate();
                }
                insertItems(con, bill.getId(), items);

                con.commit();
            }
            catch (SQLException | CustomException ex)
            {
                con.rollback();
                throw ex;
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    // Deleting a bill also deletes its items (ON DELETE CASCADE in the database).
    public void delete(int id) throws CustomException
    {
        String sql = "DELETE FROM bills WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Bill no longer exists. Refresh the list.");
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    // If the bill is linked to an appointment, that appointment must belong to the same customer.
    private void checkAppointment(Connection con, Bill bill) throws SQLException, CustomException
    {
        if (bill.getAppointmentId() == null)
        {
            return;
        }

        String sql = "SELECT p.customer_id FROM appointments a JOIN pets p ON p.id = a.pet_id WHERE a.id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, bill.getAppointmentId());

            try (ResultSet rs = ps.executeQuery())
            {
                if (!rs.next() || rs.getInt("customer_id") != bill.getCustomerId())
                {
                    throw new CustomException("The selected appointment must belong to this customer. Refresh and select again.");
                }
            }
        }
    }

    // Fills the 7 values that insert and update have in common.
    private void setBillValues(PreparedStatement ps, Bill bill, Timestamp paidAt) throws SQLException
    {
        ps.setInt(1, bill.getCustomerId());

        if (bill.getAppointmentId() == null)
        {
            ps.setNull(2, Types.INTEGER);
        }
        else
        {
            ps.setInt(2, bill.getAppointmentId());
        }

        ps.setDate(3, Date.valueOf(bill.getDate()));
        ps.setBigDecimal(4, bill.getTotal());
        ps.setString(5, bill.getPaymentMethod());
        ps.setString(6, bill.getPaymentStatus());
        ps.setTimestamp(7, paidAt);
    }

    // Saves every line of the bill.
    private void insertItems(Connection con, int billId, List<BillItem> items) throws SQLException
    {
        String sql = "INSERT INTO bill_items (bill_id, service_id, service_name, quantity, unit_price) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            for (BillItem item : items)
            {
                ps.setInt(1, billId);
                ps.setInt(2, item.getServiceId());
                ps.setString(3, item.getServiceName());
                ps.setInt(4, item.getQuantity());
                ps.setBigDecimal(5, item.getUnitPrice());
                ps.executeUpdate();
            }
        }
    }
}
