/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.*;
import view.ViewStaff;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TANISHA SANJANA
 */
public class StaffController {
    private final ViewStaff view;
    private List<Staff> records=new ArrayList<>();
    private int selectedId;


    public StaffController(ViewStaff view) {
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

    private Staff readInput() {

        return new Staff(selectedId,
                Input.text(view.getNameField().getText(),"Staff name",100,true),
                Input.choice(view.getRoleComboBox(),"a role"),
                Input.phone(view.getPhoneField().getText()),Input.email(view.getEmailField().getText()));
    }

    private void save(boolean update) {
        if (update && selectedId==0) {
            JOptionPane.showMessageDialog(view,"Select a record to update."); return;
        }
        if (!update && selectedId!=0) {
            JOptionPane.showMessageDialog(view,"Click Clear form before adding a new record."); return;
        }
        try {
            Staff item=readInput();
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
        try { Staff.delete(selectedId); }
        catch (SQLException e) { Input.error(view,e); return; }
        clear();
        JOptionPane.showMessageDialog(view,"Record deleted.");
        load();
    }

    private void load() {
        try {
            List<Staff> loaded=Staff.getAll();

            clear();
            records=loaded;
            DefaultTableModel table=Input.emptyTable(view.getRecordsTable(),view.getSearchField());
            for (Staff item:records) table.addRow(new Object[]{item.getId(),item.getName(),item.getRole(),item.getPhone(),item.getEmail()});
            view.setStatus(records.size()+" record(s) loaded.");
        } catch (SQLException e) { view.setStatus("Refresh failed. Check the database and try again."); Input.error(view,e); }
    }

    private void select() {
        int row=view.getRecordsTable().getSelectedRow();
        if (row<0) { selectedId=0; view.getIdField().setText("Auto ID"); return; }
        Staff item=records.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId=item.getId(); view.getIdField().setText(String.valueOf(selectedId));

        view.getNameField().setText(item.getName());
        view.getRoleComboBox().setSelectedItem(item.getRole());
        view.getPhoneField().setText(item.getPhone());
        view.getEmailField().setText(item.getEmail());
    }

    private void clear() {
        selectedId=0; view.clearForm(); view.getIdField().setText("Auto ID");
    }
}
