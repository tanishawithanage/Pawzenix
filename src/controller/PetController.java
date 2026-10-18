/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.*;
import view.ViewPets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TANISHA SANJANA
 */
public class PetController {
    private final ViewPets view;
    private List<Pet> records=new ArrayList<>();
    private int selectedId;
    private List<Customer> customers=new ArrayList<>();

    public PetController(ViewPets view) {
        this.view=view;
        view.getAddButton().addActionListener(e->save(false));
        view.getEditButton().addActionListener(e->save(true));
        view.getDeleteButton().addActionListener(e->delete());
        view.getRefreshButton().addActionListener(e->load());
        Input.clearAction(view.getClearButton(),this::clear);
        view.getRecordsTable().getSelectionModel().addListSelectionListener(e->{
            if (!e.getValueIsAdjusting()) select();
        });
        load();
    }

    private Pet readInput() {

        Input.choice(view.getCustomerComboBox(),"a customer");
        Customer customer=customers.get(view.getCustomerComboBox().getSelectedIndex()-1);
        return new Pet(selectedId,customer.getId(),
                Input.text(view.getNameField().getText(),"Pet name",100,true),
                Input.choice(view.getSpeciesComboBox(),"a species"),
                Input.text(view.getBreedField().getText(),"Breed",100,false),
                Input.number(view.getAgeField().getText(),"Age",0,200),
                (String)view.getGenderComboBox().getSelectedItem(),customer.getName());
    }

    private void save(boolean update) {
        if (update && selectedId==0) {
            JOptionPane.showMessageDialog(view,"Select a record to update."); return;
        }
        if (!update && selectedId!=0) {
            JOptionPane.showMessageDialog(view,"Click Clear form before adding a new record."); return;
        }
        try {
            Pet item=readInput();
            if (update) item.update(); else item.add();
        } catch (SQLException | IllegalArgumentException e) {
            Input.error(view,e); return;
        }
        clear();
        JOptionPane.showMessageDialog(view,"Record saved.");
        load();
    }

    private void delete() {
        if (selectedId==0) { JOptionPane.showMessageDialog(view,"Select a record to delete."); return; }
        if (!Input.confirm(view,"Delete the selected record?")) return;
        try { Pet.delete(selectedId); }
        catch (SQLException e) { Input.error(view,e); return; }
        clear();
        JOptionPane.showMessageDialog(view,"Record deleted.");
        load();
    }

    private void load() {
        try {
            List<Pet> loaded=Pet.getAll();

            List<Customer> newCustomers=Customer.getAll();
            customers=newCustomers;
            view.getCustomerComboBox().removeAllItems();
            view.getCustomerComboBox().addItem("Select customer...");
            for (Customer c:customers) view.getCustomerComboBox().addItem(c.getId()+" - "+c.getName());
            clear();
            records=loaded;
            DefaultTableModel table=Input.emptyTable(view.getRecordsTable(),view.getSearchField());
            for (Pet item:records) table.addRow(new Object[]{item.getId(),item.getName(),item.getCustomerName(),item.getSpecies(),item.getBreed(),item.getAge(),item.getGender()});
            view.setStatus(records.size()+" record(s) loaded.");
        } catch (SQLException e) { view.setStatus("Refresh failed. Check the database and try again."); Input.error(view,e); }
    }

    private void select() {
        int row=view.getRecordsTable().getSelectedRow();
        if (row<0) { selectedId=0; view.getIdField().setText("Auto ID"); return; }
        Pet item=records.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId=item.getId(); view.getIdField().setText(String.valueOf(selectedId));

        view.getCustomerComboBox().setSelectedIndex(0);
        for (int i=0;i<customers.size();i++) if (customers.get(i).getId()==item.getCustomerId()) view.getCustomerComboBox().setSelectedIndex(i+1);
        view.getNameField().setText(item.getName());
        view.getSpeciesComboBox().setSelectedItem(item.getSpecies());
        view.getBreedField().setText(item.getBreed());
        view.getAgeField().setText(String.valueOf(item.getAge()));
        view.getGenderComboBox().setSelectedItem(item.getGender());
    }

    private void clear() {
        selectedId=0; view.clearForm(); view.getIdField().setText("Auto ID");
    }
}
