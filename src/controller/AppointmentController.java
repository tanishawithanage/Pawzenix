/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.*;
import view.ViewAppointments;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TANISHA SANJANA
 */
public class AppointmentController {
  private final ViewAppointments view;
    private List<Appointment> records=new ArrayList<>();
    private int selectedId;
    private List<Pet> pets=new ArrayList<>();
    private List<Service> services=new ArrayList<>();
    private List<Staff> staff=new ArrayList<>();

    public AppointmentController(ViewAppointments view) {
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

    private Appointment readInput() {

        Input.choice(view.getPetComboBox(),"a pet");
        Input.choice(view.getServiceComboBox(),"a service");
        Pet pet=pets.get(view.getPetComboBox().getSelectedIndex()-1);
        Service service=services.get(view.getServiceComboBox().getSelectedIndex()-1);
        int index=view.getStaffComboBox().getSelectedIndex();
        Staff member=index>0?staff.get(index-1):null;
        return new Appointment(selectedId,pet.getId(),service.getId(),member==null?null:member.getId(),
                Input.date(view.getDateField().getText()).atTime(Input.time(view.getTimeField().getText())),
                service.getDuration(),(String)view.getStatusComboBox().getSelectedItem(),
                Input.text(view.getNotesArea().getText(),"Notes",1000,false),
                pet.getName(),service.getName(),member==null?null:member.getName(),pet.getCustomerId());
    }

    private void save(boolean update) {
        if (update && selectedId==0) {
            JOptionPane.showMessageDialog(view,"Select a record to update."); return;
        }
        if (!update && selectedId!=0) {
            JOptionPane.showMessageDialog(view,"Click Clear form before adding a new record."); return;
        }
        try {
            Appointment item=readInput();
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
        try { Appointment.delete(selectedId); }
        catch (SQLException e) { Input.error(view,e); return; }
        clear();
        JOptionPane.showMessageDialog(view,"Record deleted.");
        load();
    }

    private void load() {
        try {
            List<Appointment> loaded=Appointment.getAll();

            List<Pet> newPets=Pet.getAll();
            List<Service> newServices=Service.getAll();
            List<Staff> newStaff=Staff.getAll();
            pets=newPets; services=newServices; staff=newStaff;
            view.getPetComboBox().removeAllItems(); view.getPetComboBox().addItem("Select pet...");
            for (Pet p:pets) view.getPetComboBox().addItem(p.getId()+" - "+p.getName()+" ("+p.getCustomerName()+")");
            view.getServiceComboBox().removeAllItems(); view.getServiceComboBox().addItem("Select service...");
            for (Service s:services) view.getServiceComboBox().addItem(s.getId()+" - "+s.getName());
            view.getStaffComboBox().removeAllItems(); view.getStaffComboBox().addItem("Unassigned");
            for (Staff s:staff) view.getStaffComboBox().addItem(s.getId()+" - "+s.getName());
            clear();
            records=loaded;
            DefaultTableModel table=Input.emptyTable(view.getRecordsTable(),view.getSearchField());
            for (Appointment item:records) table.addRow(new Object[]{item.getId(),item.getPetName(),item.getServiceName(),item.getStaffName()==null?"Unassigned":item.getStaffName(),item.getStart().toLocalDate(),item.getStart().toLocalTime(),item.getStatus(),item.getNotes()});
            view.setStatus(records.size()+" record(s) loaded.");
        } catch (SQLException e) { view.setStatus("Refresh failed. Check the database and try again."); Input.error(view,e); }
    }

    private void select() {
        int row=view.getRecordsTable().getSelectedRow();
        if (row<0) { selectedId=0; view.getIdField().setText("Auto ID"); return; }
        Appointment item=records.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId=item.getId(); view.getIdField().setText(String.valueOf(selectedId));

        view.getPetComboBox().setSelectedIndex(0);
        view.getServiceComboBox().setSelectedIndex(0);
        view.getStaffComboBox().setSelectedIndex(0);
        for (int i=0;i<pets.size();i++) if (pets.get(i).getId()==item.getPetId()) view.getPetComboBox().setSelectedIndex(i+1);
        for (int i=0;i<services.size();i++) if (services.get(i).getId()==item.getServiceId()) view.getServiceComboBox().setSelectedIndex(i+1);
        for (int i=0;i<staff.size();i++) if (item.getStaffId()!=null && staff.get(i).getId()==item.getStaffId()) view.getStaffComboBox().setSelectedIndex(i+1);
        view.getDateField().setText(item.getStart().toLocalDate().toString());
        view.getTimeField().setText(item.getStart().toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        view.getStatusComboBox().setSelectedItem(item.getStatus());
        view.getNotesArea().setText(item.getNotes());
    }

    private void clear() {
        selectedId=0; view.clearForm(); view.getIdField().setText("Auto ID");
    }  
}
