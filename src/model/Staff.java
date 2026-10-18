/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TANISHA SANJANA
 */
public class Staff {
    private final int id;
    private final String name;
    private final String role;
    private final String phone;
    private final String email;

    public Staff(int id, String name, String role, String phone, String email) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public static List<Staff> getAll() throws SQLException {
        List<Staff> rows = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM staff ORDER BY id DESC");
             ResultSet r = s.executeQuery()) {
            while (r.next()) rows.add(new Staff(r.getInt("id"), r.getString("name"), r.getString("role"), r.getString("phone"), r.getString("email")));
        } catch (SQLException e) {
            throw e;
        }
        return rows;
    }

    public void add() throws SQLException { save(false); }
    public void update() throws SQLException { save(true); }

    private void save(boolean update) throws SQLException {
        String sql = update ? "UPDATE staff SET name = ?, role = ?, phone = ?, email = ? WHERE id = ?" : "INSERT INTO staff (name, role, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, name);
            s.setString(2, role);
            s.setString(3, phone);
            s.setString(4, email);
            if (update) s.setInt(5, id);
            if (s.executeUpdate() == 0) throw new SQLException("Record no longer exists. Refresh the list.", "02000");
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void delete(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement s = c.prepareStatement("DELETE FROM staff WHERE id = ?")) {
            s.setInt(1, id);
            if (s.executeUpdate() == 0) throw new SQLException("Record no longer exists. Refresh the list.", "02000");
        } catch (SQLException e) {
            throw e;
        }
    } 
}
