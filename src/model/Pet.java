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
public class Pet {
    private final int id;
    private final int customerId;
    private final String name;
    private final String species;
    private final String breed;
    private final int age;
    private final String gender;
    private final String customerName;

    public Pet(int id, int customerId, String name, String species, String breed, int age, String gender, String customerName) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.customerName = customerName;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public String getBreed() { return breed; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getCustomerName() { return customerName; }

    public static List<Pet> getAll() throws SQLException {
        List<Pet> rows = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS customer_name FROM pets p JOIN customers c ON c.id=p.customer_id ORDER BY p.id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement s = c.prepareStatement(sql); ResultSet r = s.executeQuery()) {
            while (r.next()) rows.add(new Pet(r.getInt("id"), r.getInt("customer_id"),
                    r.getString("name"), r.getString("species"), r.getString("breed"),
                    r.getInt("age_years"), r.getString("gender"), r.getString("customer_name")));
        }
        return rows;
    }

    public void add() throws SQLException { save(false); }
    public void update() throws SQLException { save(true); }

    private void save(boolean update) throws SQLException {
        String sql = update
                ? "UPDATE pets SET customer_id=?, name=?, species=?, breed=?, age_years=?, gender=? WHERE id=?"
                : "INSERT INTO pets (customer_id,name,species,breed,age_years,gender) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement lock = c.prepareStatement("SELECT id FROM app_lock WHERE id=1 FOR UPDATE")) {
                    lock.executeQuery().close();
                }
                if (update) {
                    try (PreparedStatement s = c.prepareStatement("SELECT b.id FROM bills b JOIN appointments a ON a.id=b.appointment_id WHERE a.pet_id=? AND b.customer_id<>? LIMIT 1")) {
                        s.setInt(1, id); s.setInt(2, customerId);
                        try (ResultSet r = s.executeQuery()) {
                            if (r.next()) throw new SQLException("This pet has a bill linked to its current owner. The owner cannot be changed.", "45000");
                        }
                    }
                }
                try (PreparedStatement s = c.prepareStatement(sql)) {
                    s.setInt(1, customerId); s.setString(2, name); s.setString(3, species);
                    s.setString(4, breed); s.setInt(5, age); s.setString(6, gender);
                    if (update) s.setInt(7, id);
                    if (s.executeUpdate()==0) throw new SQLException("Pet no longer exists. Refresh the list.", "02000");
                }
                c.commit();
            } catch (SQLException | RuntimeException e) {
                try { c.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        }
    }

    public static void delete(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement s = c.prepareStatement("DELETE FROM pets WHERE id=?")) {
            s.setInt(1, id);
            if (s.executeUpdate()==0) throw new SQLException("Pet no longer exists. Refresh the list.", "02000");
        }
    }
}
