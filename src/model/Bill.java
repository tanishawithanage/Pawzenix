package model;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bill {
    private final int id;
    private final int customerId;
    private final Integer appointmentId;
    private final LocalDate date;
    private final BigDecimal total;
    private final String paymentMethod;
    private final String paymentStatus;
    private final String customerName;

    public Bill(int id, int customerId, Integer appointmentId, LocalDate date, BigDecimal total, String paymentMethod, String paymentStatus, String customerName) {
        this.id = id;
        this.customerId = customerId;
        this.appointmentId = appointmentId;
        this.date = date;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.customerName = customerName;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public Integer getAppointmentId() { return appointmentId; }
    public LocalDate getDate() { return date; }
    public BigDecimal getTotal() { return total; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getCustomerName() { return customerName; }

    public static List<Bill> getAll() throws SQLException {
        List<Bill> rows=new ArrayList<>();
        String sql="SELECT b.*,c.name customer_name FROM bills b JOIN customers c ON c.id=b.customer_id ORDER BY b.id DESC";
        try (Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement(sql); ResultSet r=s.executeQuery()) {
            while (r.next()) {
                int appointment=r.getInt("appointment_id");
                Integer appointmentId=r.wasNull()?null:appointment;
                rows.add(new Bill(r.getInt("id"),r.getInt("customer_id"),appointmentId,r.getDate("bill_date").toLocalDate(),
                        r.getBigDecimal("total"),r.getString("payment_method"),r.getString("payment_status"),r.getString("customer_name")));
            }
        }
        return rows;
    }

    public static List<BillItem> getItems(int billId) throws SQLException {
        List<BillItem> rows=new ArrayList<>();
        try (Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement("SELECT * FROM bill_items WHERE bill_id=? ORDER BY id")) {
            s.setInt(1,billId);
            try (ResultSet r=s.executeQuery()) {
                while (r.next()) rows.add(new BillItem(r.getInt("service_id"),r.getString("service_name"),r.getInt("quantity"),r.getBigDecimal("unit_price")));
            }
        }
        return rows;
    }

    public void add(List<BillItem> items) throws SQLException { save(items,false); }
    public void update(List<BillItem> items) throws SQLException { save(items,true); }

    private void save(List<BillItem> items, boolean update) throws SQLException {
        if (items.isEmpty()) throw new IllegalArgumentException("Add at least one service item.");
        BigDecimal sum=BigDecimal.ZERO.setScale(2);
        for (BillItem item:items) {
            if (item.getQuantity()<1 || item.getQuantity()>10000 || item.getUnitPrice().signum()<=0
                    || item.getUnitPrice().scale()>2 || item.getUnitPrice().compareTo(new BigDecimal("99999999.99"))>0)
                throw new IllegalArgumentException("Each item needs a valid quantity and a positive price with up to two decimal places.");
            sum=sum.add(item.getTotal());
        }
        if (sum.compareTo(new BigDecimal("9999999999.99"))>0) throw new IllegalArgumentException("Bill total is too large.");
        try (Connection c=DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement lock=c.prepareStatement("SELECT id FROM app_lock WHERE id=1 FOR UPDATE")) {
                    lock.executeQuery().close();
                }
                if (appointmentId!=null) {
                    try (PreparedStatement s=c.prepareStatement("SELECT p.customer_id FROM appointments a JOIN pets p ON p.id=a.pet_id WHERE a.id=?")) {
                        s.setInt(1,appointmentId);
                        try (ResultSet r=s.executeQuery()) {
                            if (!r.next() || r.getInt(1)!=customerId) throw new SQLException("The selected appointment must belong to this customer. Refresh and select again.","45000");
                        }
                    }
                }
                Timestamp paidAt=null;
                if (update) {
                    try (PreparedStatement s=c.prepareStatement("SELECT paid_at FROM bills WHERE id=? FOR UPDATE")) {
                        s.setInt(1,id);
                        try (ResultSet r=s.executeQuery()) {
                            if (!r.next()) throw new SQLException("Bill no longer exists. Refresh the list.","02000");
                            paidAt=r.getTimestamp(1);
                        }
                    }
                }
                if (paymentStatus.equals("Paid")) {
                    if (paidAt==null) {
                        try (PreparedStatement s=c.prepareStatement("SELECT CURRENT_TIMESTAMP"); ResultSet r=s.executeQuery()) {
                            r.next(); paidAt=r.getTimestamp(1);
                        }
                    }
                } else paidAt=null;
                String sql=update
                        ? "UPDATE bills SET customer_id=?,appointment_id=?,bill_date=?,total=?,payment_method=?,payment_status=?,paid_at=? WHERE id=?"
                        : "INSERT INTO bills (customer_id,appointment_id,bill_date,total,payment_method,payment_status,paid_at) VALUES (?,?,?,?,?,?,?)";
                int billId=id;
                try (PreparedStatement s=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                    s.setInt(1,customerId);
                    if (appointmentId==null) s.setNull(2,Types.INTEGER); else s.setInt(2,appointmentId);
                    s.setDate(3,Date.valueOf(date)); s.setBigDecimal(4,sum);
                    s.setString(5,paymentMethod); s.setString(6,paymentStatus); s.setTimestamp(7,paidAt);
                    if (update) s.setInt(8,id);
                    s.executeUpdate();
                    if (!update) {
                        try (ResultSet keys=s.getGeneratedKeys()) {
                            if (!keys.next()) throw new SQLException("Could not retrieve the new bill ID.");
                            billId=keys.getInt(1);
                        }
                    }
                }
                if (update) {
                    try (PreparedStatement s=c.prepareStatement("DELETE FROM bill_items WHERE bill_id=?")) {
                        s.setInt(1,billId); s.executeUpdate();
                    }
                }
                try (PreparedStatement s=c.prepareStatement("INSERT INTO bill_items (bill_id,service_id,service_name,quantity,unit_price) VALUES (?,?,?,?,?)")) {
                    for (BillItem item:items) {
                        s.setInt(1,billId); s.setInt(2,item.getServiceId()); s.setString(3,item.getServiceName());
                        s.setInt(4,item.getQuantity()); s.setBigDecimal(5,item.getUnitPrice()); s.addBatch();
                    }
                    s.executeBatch();
                }
                c.commit();
            } catch (SQLException | RuntimeException e) {
                try { c.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        }
    }

    public static void delete(int id) throws SQLException {
        try (Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement("DELETE FROM bills WHERE id=?")) {
            s.setInt(1,id);
            if (s.executeUpdate()==0) throw new SQLException("Bill no longer exists. Refresh the list.","02000");
        }
    }
}
