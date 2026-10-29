package controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Customer;
import model.CustomerDAO;
import model.CustomException;
import model.Pet;
import model.PetDAO;
import view.ViewPets;

/**
 * Controller for the Pets page: add, update, delete and show pets.
 */
public class PetController
{
    private final ViewPets view;
    private final PetDAO petDAO = new PetDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    private List<Pet> shownPets = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();   // the customers in the owner list
    private int selectedId = 0;                              // 0 means no pet is selected

    public PetController(ViewPets view)
    {
        this.view = view;

        view.getAddButton().addActionListener(e -> addPet());
        view.getEditButton().addActionListener(e -> updatePet());
        view.getDeleteButton().addActionListener(e -> deletePet());
        view.getRefreshButton().addActionListener(e -> showAll());

        // The view already has its own Clear action. Remove it, so only ours runs (it shows "Auto ID").
        for (ActionListener listener : view.getClearButton().getActionListeners())
        {
            view.getClearButton().removeActionListener(listener);
        }
        view.getClearButton().addActionListener(e -> clear());

        view.getRecordsTable().getSelectionModel().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting())
            {
                onRowSelected();
            }
        });

        showAll();
    }

    private void addPet()
    {
        if (selectedId != 0)
        {
            JOptionPane.showMessageDialog(view, "Click Clear form before adding a new record.");
            return;
        }
        try
        {
            Pet pet = readForm();
            petDAO.insert(pet);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        clear();
        JOptionPane.showMessageDialog(view, "Record saved.");
        showAll();
    }

    private void updatePet()
    {
        if (selectedId == 0)
        {
            JOptionPane.showMessageDialog(view, "Select a record to update.");
            return;
        }
        try
        {
            Pet pet = readForm();
            pet.setId(selectedId);
            petDAO.update(pet);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        clear();
        JOptionPane.showMessageDialog(view, "Record saved.");
        showAll();
    }

    private void deletePet()
    {
        if (selectedId == 0)
        {
            JOptionPane.showMessageDialog(view, "Select a record to delete.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(view, "Delete the selected record?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION)
        {
            return;
        }

        try
        {
            petDAO.delete(selectedId);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        clear();
        JOptionPane.showMessageDialog(view, "Record deleted.");
        showAll();
    }

    // Loads the pets and the owner list from the database.
    private void showAll()
    {
        try
        {
            List<Pet> pets = petDAO.findAll();
            customers = customerDAO.findAll();
            shownPets = pets;

            // fill the owner list (the first item is only a hint)
            view.getCustomerComboBox().removeAllItems();
            view.getCustomerComboBox().addItem("Select customer...");
            for (Customer c : customers)
            {
                view.getCustomerComboBox().addItem(c.getId() + " - " + c.getName());
            }

            clear();
            view.getSearchField().setText("");
            DefaultTableModel model = (DefaultTableModel) view.getRecordsTable().getModel();
            model.setRowCount(0);
            view.getRecordsTable().setRowSorter(new TableRowSorter<>(model));

            for (Pet p : shownPets)
            {
                model.addRow(new Object[]
                {
                    p.getId(),
                    p.getName(),
                    p.getCustomerName(),
                    p.getSpecies(),
                    p.getBreed(),
                    p.getAge(),
                    p.getGender()
                });
            }
            view.setStatus(shownPets.size() + " record(s) loaded.");
        }
        catch (CustomException ex)
        {
            view.setStatus("Refresh failed. Check the database and try again.");
            showError(ex.getMessage());
        }
    }

    private void onRowSelected()
    {
        int row = view.getRecordsTable().getSelectedRow();

        if (row < 0)
        {
            selectedId = 0;
            view.getIdField().setText("Auto ID");
            return;
        }

        Pet p = shownPets.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId = p.getId();
        view.getIdField().setText(String.valueOf(p.getId()));

        // choose the owner in the list (position + 1 because of the hint item)
        view.getCustomerComboBox().setSelectedIndex(0);
        for (int i = 0; i < customers.size(); i++)
        {
            if (customers.get(i).getId() == p.getCustomerId())
            {
                view.getCustomerComboBox().setSelectedIndex(i + 1);
            }
        }

        view.getNameField().setText(p.getName());
        view.getSpeciesComboBox().setSelectedItem(p.getSpecies());
        view.getBreedField().setText(p.getBreed());
        view.getAgeField().setText(String.valueOf(p.getAge()));
        view.getGenderComboBox().setSelectedItem(p.getGender());
    }

    private void clear()
    {
        selectedId = 0;
        view.clearForm();
        view.getIdField().setText("Auto ID");
    }

    // Reads the form and checks it. Returns a Pet (without an id).
    private Pet readForm() throws CustomException
    {
        // the owner
        if (view.getCustomerComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a customer.");
        }
        Customer customer = customers.get(view.getCustomerComboBox().getSelectedIndex() - 1);

        // the name
        String name = view.getNameField().getText().trim();
        if (name.isEmpty())
        {
            throw new CustomException("Pet name is required.");
        }
        if (name.length() > 100)
        {
            throw new CustomException("Pet name must have at most 100 characters.");
        }

        // the species
        if (view.getSpeciesComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a species.");
        }
        String species = (String) view.getSpeciesComboBox().getSelectedItem();

        // the breed is optional
        String breed = view.getBreedField().getText().trim();
        if (breed.length() > 100)
        {
            throw new CustomException("Breed must have at most 100 characters.");
        }

        // the age
        String ageMessage = "Age must be a whole number from 0 to 200.";
        int age;
        try
        {
            age = Integer.parseInt(view.getAgeField().getText().trim());
        }
        catch (NumberFormatException ex)
        {
            throw new CustomException(ageMessage);
        }
        if (age < 0 || age > 200)
        {
            throw new CustomException(ageMessage);
        }

        Pet pet = new Pet();
        pet.setCustomerId(customer.getId());
        pet.setName(name);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setAge(age);
        pet.setGender((String) view.getGenderComboBox().getSelectedItem());
        return pet;
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(view, message, "Pawzenix", JOptionPane.ERROR_MESSAGE);
    }
}
