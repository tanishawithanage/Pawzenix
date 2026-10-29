package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the pets table.
 */
public class PetDAO
{
    // All pets with the owner's name, newest first.
    public List<Pet> findAll() throws CustomException
    {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS customer_name "
                   + "FROM pets p JOIN customers c ON c.id = p.customer_id "
                   + "ORDER BY p.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setCustomerId(rs.getInt("customer_id"));
                pet.setName(rs.getString("name"));
                pet.setSpecies(rs.getString("species"));
                pet.setBreed(rs.getString("breed"));
                pet.setAge(rs.getInt("age_years"));
                pet.setGender(rs.getString("gender"));
                pet.setCustomerName(rs.getString("customer_name"));
                pets.add(pet);
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
        return pets;
    }

    public void insert(Pet pet) throws CustomException
    {
        String sql = "INSERT INTO pets (customer_id, name, species, breed, age_years, gender) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, pet.getCustomerId());
            ps.setString(2, pet.getName());
            ps.setString(3, pet.getSpecies());
            ps.setString(4, pet.getBreed());
            ps.setInt(5, pet.getAge());
            ps.setString(6, pet.getGender());
            ps.executeUpdate();
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void update(Pet pet) throws CustomException
    {
        String sql = "UPDATE pets SET customer_id = ?, name = ?, species = ?, breed = ?, "
                   + "age_years = ?, gender = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection())
        {
            checkOwnerChange(con, pet);

            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, pet.getCustomerId());
                ps.setString(2, pet.getName());
                ps.setString(3, pet.getSpecies());
                ps.setString(4, pet.getBreed());
                ps.setInt(5, pet.getAge());
                ps.setString(6, pet.getGender());
                ps.setInt(7, pet.getId());

                if (ps.executeUpdate() == 0)
                {
                    throw new CustomException("Pet no longer exists. Refresh the list.");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    public void delete(int id) throws CustomException
    {
        String sql = "DELETE FROM pets WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0)
            {
                throw new CustomException("Pet no longer exists. Refresh the list.");
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex);
        }
    }

    // The owner cannot be changed if a bill already belongs to the current owner for one of this pet's appointments.
    private void checkOwnerChange(Connection con, Pet pet) throws SQLException, CustomException
    {
        String sql = "SELECT b.id FROM bills b JOIN appointments a ON a.id = b.appointment_id "
                   + "WHERE a.pet_id = ? AND b.customer_id <> ? LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, pet.getId());
            ps.setInt(2, pet.getCustomerId());

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    throw new CustomException("This pet has a bill linked to its current owner. The owner cannot be changed.");
                }
            }
        }
    }
}
