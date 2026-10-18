/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TANISHA SANJANA
 */
public class Service {
    private final int id;
    private final String name;
    private final String category;
    private final int duration;

    public Service(int id, String name, String category, int duration) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getDuration() {
        return duration;
    }

    public static List<Service> getAll() throws SQLException {

        List<Service> services = new ArrayList<>();

        String sql = "SELECT id, name, category, duration_minutes "
                + "FROM services ORDER BY id DESC";

        Connection con = DBConnection.getConnection();

        try (PreparedStatement statement = con.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                services.add(new Service(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("category"),
                        result.getInt("duration_minutes")
                ));
            }

        } catch (SQLException e) {
            throw e;

        } finally {
            con.close();
        }

        return services;
    }

    public void add() throws SQLException {

        String sql = "INSERT INTO services "
                + "(name, category, duration_minutes) VALUES (?, ?, ?)";

        Connection con = DBConnection.getConnection();

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            fillStatement(statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw e;

        } finally {
            con.close();
        }
    }

    public void update() throws SQLException {

        String sql = "UPDATE services SET name = ?, category = ?, "
                + "duration_minutes = ? WHERE id = ?";

        Connection con = DBConnection.getConnection();

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            fillStatement(statement);
            statement.setInt(4, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException(
                        "Service no longer exists. Click Refresh."
                );
            }

        } catch (SQLException e) {
            throw e;

        } finally {
            con.close();
        }
    }

    public static void delete(int id) throws SQLException {

        String sql = "DELETE FROM services WHERE id = ?";

        Connection con = DBConnection.getConnection();

        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException(
                        "Service no longer exists. Click Refresh."
                );
            }

        } catch (SQLException e) {
            throw e;

        } finally {
            con.close();
        }
    }

    private void fillStatement(PreparedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setString(2, category);
        statement.setInt(3, duration);
    }   
}
