/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TANISHA SANJANA
 */
public class Appointment {
    private final int id;
    private final int petId;
    private final int serviceId;
    private final Integer staffId;
    private final LocalDateTime start;
    private final int duration;
    private final String status;
    private final String notes;
    private final String petName;
    private final String serviceName;
    private final String staffName;
    private final int customerId;

    public Appointment(int id, int petId, int serviceId, Integer staffId, LocalDateTime start, int duration, String status, String notes, String petName, String serviceName, String staffName, int customerId) {
        this.id = id;
        this.petId = petId;
        this.serviceId = serviceId;
        this.staffId = staffId;
        this.start = start;
        this.duration = duration;
        this.status = status;
        this.notes = notes;
        this.petName = petName;
        this.serviceName = serviceName;
        this.staffName = staffName;
        this.customerId = customerId;
    }

    public int getId() { return id; }
    public int getPetId() { return petId; }
    public int getServiceId() { return serviceId; }
    public Integer getStaffId() { return staffId; }
    public LocalDateTime getStart() { return start; }
    public int getDuration() { return duration; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public String getPetName() { return petName; }
    public String getServiceName() { return serviceName; }
    public String getStaffName() { return staffName; }
    public int getCustomerId() { return customerId; }

    public static List<Appointment> getAll() throws SQLException {
        List<Appointment> rows = new ArrayList<>();
        String sql = "SELECT a.*, p.name pet_name, p.customer_id, s.name service_name, t.name staff_name "
                + "FROM appointments a JOIN pets p ON p.id=a.pet_id JOIN services s ON s.id=a.service_id "
                + "LEFT JOIN staff t ON t.id=a.staff_id ORDER BY a.start_at DESC, a.id DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement s = c.prepareStatement(sql); ResultSet r = s.executeQuery()) {
            while (r.next()) {
                int staff = r.getInt("staff_id");
                Integer staffId = r.wasNull() ? null : staff;
                rows.add(new Appointment(r.getInt("id"),r.getInt("pet_id"),r.getInt("service_id"),staffId,
                        r.getTimestamp("start_at").toLocalDateTime(),r.getInt("duration_minutes"),r.getString("status"),
                        r.getString("notes"),r.getString("pet_name"),r.getString("service_name"),
                        r.getString("staff_name"),r.getInt("customer_id")));
            }
        }
        return rows;
    }

    public void add() throws SQLException { save(false); }
    public void update() throws SQLException { save(true); }

    private void save(boolean update) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement lock = c.prepareStatement("SELECT id FROM app_lock WHERE id=1 FOR UPDATE")) {
                    lock.executeQuery().close();
                }
                int minutes;
                try (PreparedStatement s = c.prepareStatement("SELECT duration_minutes FROM services WHERE id=?")) {
                    s.setInt(1, serviceId);
                    try (ResultSet r = s.executeQuery()) {
                        if (!r.next()) throw new SQLException("Service no longer exists. Refresh the list.", "45000");
                        minutes = r.getInt(1);
                    }
                }
                if (update) {
                    try (PreparedStatement s = c.prepareStatement("SELECT service_id,duration_minutes FROM appointments WHERE id=?")) {
                        s.setInt(1,id);
                        try (ResultSet r = s.executeQuery()) {
                            if (!r.next()) throw new SQLException("Appointment no longer exists. Refresh the list.", "02000");
                            if (r.getInt(1)==serviceId) minutes=r.getInt(2);
                        }
                    }
                    try (PreparedStatement s = c.prepareStatement("SELECT b.id FROM bills b JOIN pets p ON p.id=? WHERE b.appointment_id=? AND b.customer_id<>p.customer_id LIMIT 1")) {
                        s.setInt(1,petId); s.setInt(2,id);
                        try (ResultSet r = s.executeQuery()) {
                            if (r.next()) throw new SQLException("The linked bill belongs to a different customer. Choose a pet of the billed customer.", "45000");
                        }
                    }
                }
                if (minutes<1 || minutes>1440) throw new SQLException("Service duration must be 1 to 1440 minutes.", "45000");
                if (!status.equals("Cancelled") && !status.equals("No-show")) {
                    String overlap = "SELECT id FROM appointments WHERE id<>? AND status NOT IN ('Cancelled','No-show') "
                            + "AND (pet_id=? OR staff_id=?) AND start_at<? "
                            + "AND TIMESTAMPADD(MINUTE,duration_minutes,start_at)>? LIMIT 1";
                    try (PreparedStatement s = c.prepareStatement(overlap)) {
                        s.setInt(1,update?id:0); s.setInt(2,petId);
                        if (staffId==null) s.setNull(3,Types.INTEGER); else s.setInt(3,staffId);
                        s.setTimestamp(4,Timestamp.valueOf(start.plusMinutes(minutes)));
                        s.setTimestamp(5,Timestamp.valueOf(start));
                        try (ResultSet r = s.executeQuery()) {
                            if (r.next()) throw new SQLException("The pet or staff member already has an overlapping appointment.", "45000");
                        }
                    }
                }
                String sql = update
                        ? "UPDATE appointments SET pet_id=?, service_id=?, staff_id=?, start_at=?, duration_minutes=?, status=?, notes=? WHERE id=?"
                        : "INSERT INTO appointments (pet_id,service_id,staff_id,start_at,duration_minutes,status,notes) VALUES (?,?,?,?,?,?,?)";
                try (PreparedStatement s = c.prepareStatement(sql)) {
                    s.setInt(1,petId); s.setInt(2,serviceId);
                    if (staffId==null) s.setNull(3,Types.INTEGER); else s.setInt(3,staffId);
                    s.setTimestamp(4,Timestamp.valueOf(start)); s.setInt(5,minutes);
                    s.setString(6,status); s.setString(7,notes);
                    if (update) s.setInt(8,id);
                    if (s.executeUpdate()==0) throw new SQLException("Appointment no longer exists. Refresh the list.", "02000");
                }
                c.commit();
            } catch (SQLException | RuntimeException e) {
                try { c.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        }
    }

    public static void delete(int id) throws SQLException {
        try (Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement("DELETE FROM appointments WHERE id=?")) {
            s.setInt(1,id);
            if (s.executeUpdate()==0) throw new SQLException("Appointment no longer exists. Refresh the list.", "02000");
        }
    }
}
