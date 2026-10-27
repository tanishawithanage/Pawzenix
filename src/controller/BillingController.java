package controller;

import model.*;
import view.ViewBilling;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TANISHA SANJANA
 */

public class BillingController {
    private final ViewBilling view;
    private List<Bill> records=new ArrayList<>();
    private List<Customer> customers=new ArrayList<>();
    private List<Service> services=new ArrayList<>();
    private List<Appointment> appointments=new ArrayList<>();
    private final List<Appointment> visibleAppointments=new ArrayList<>();
    private final List<BillItem> items=new ArrayList<>();
    private int selectedId;
    private boolean changing;

    public BillingController(ViewBilling view) {
        this.view=view;
        view.getTotalAmountField().setEditable(false);
        view.getAddButton().addActionListener(e->save(false));
        view.getEditButton().addActionListener(e->save(true));
        view.getDeleteButton().addActionListener(e->delete());
        view.getRefreshButton().addActionListener(e->load());
        view.getAddItemButton().addActionListener(e->saveItem(false));
        view.getUpdateItemButton().addActionListener(e->saveItem(true));
        view.getRemoveItemButton().addActionListener(e->removeItem());
        Input.clearAction(view.getClearButton(),this::clear);
        view.getCustomerComboBox().addActionListener(e->{if (!changing) filterAppointments();});
        view.getRecordsTable().getSelectionModel().addListSelectionListener(e->{if (!e.getValueIsAdjusting() && !changing) select();});
        view.getItemsList().addListSelectionListener(e->{if (!e.getValueIsAdjusting() && !changing) selectItem();});
        load();
    }

    private void filterAppointments() {
        visibleAppointments.clear();
        view.getAppointmentComboBox().removeAllItems();
        view.getAppointmentComboBox().addItem("No appointment");
        int index=view.getCustomerComboBox().getSelectedIndex();
        if (index<=0) return;
        int customerId=customers.get(index-1).getId();
        for (Appointment a:appointments) if (a.getCustomerId()==customerId) {
            visibleAppointments.add(a);
            view.getAppointmentComboBox().addItem(a.getId()+" - "+a.getPetName()+" - "+a.getStart());
        }
    }

    private void saveItem(boolean update) {
        int selected=view.getItemsList().getSelectedIndex();
        if (update && selected<0) { JOptionPane.showMessageDialog(view,"Select a service item to update."); return; }
        try {
            Input.choice(view.getServiceComboBox(),"a service");
            Service service=services.get(view.getServiceComboBox().getSelectedIndex()-1);
            String name=service.getName();
            if (update && items.get(selected).getServiceId()==service.getId()) name=items.get(selected).getServiceName();
            BillItem item=new BillItem(service.getId(),name,
                    Input.number(view.getQuantityField().getText(),"Quantity",1,10000),
                    Input.money(view.getUnitPriceField().getText()));
            if (update) items.set(selected,item); else items.add(item);
            showItems();
            view.getServiceComboBox().setSelectedIndex(0);
            view.getQuantityField().setText(""); view.getUnitPriceField().setText("");
        } catch (IllegalArgumentException e) { Input.error(view,e); }
    }

    private void removeItem() {
        int index=view.getItemsList().getSelectedIndex();
        if (index<0) { JOptionPane.showMessageDialog(view,"Select a service item to remove."); return; }
        items.remove(index); showItems();
    }

    private void showItems() {
        changing=true;
        try {
            DefaultListModel<String> list=new DefaultListModel<>();
            BigDecimal total=BigDecimal.ZERO.setScale(2);
            for (BillItem item:items) { list.addElement(item.toString()); total=total.add(item.getTotal()); }
            view.getItemsList().setModel(list); view.getTotalAmountField().setText(total.toPlainString());
        } finally { changing=false; }
    }

    private void selectItem() {
        int index=view.getItemsList().getSelectedIndex();
        if (index<0) return;
        BillItem item=items.get(index);
        view.getServiceComboBox().setSelectedIndex(0);
        for (int i=0;i<services.size();i++) if (services.get(i).getId()==item.getServiceId()) view.getServiceComboBox().setSelectedIndex(i+1);
        view.getQuantityField().setText(String.valueOf(item.getQuantity()));
        view.getUnitPriceField().setText(item.getUnitPrice().toPlainString());
    }

    private Bill readInput() {
        Input.choice(view.getCustomerComboBox(),"a customer");
        Customer customer=customers.get(view.getCustomerComboBox().getSelectedIndex()-1);
        String method=Input.choice(view.getPaymentMethodComboBox(),"a payment method");
        if (items.isEmpty()) throw new IllegalArgumentException("Add at least one service item to the bill.");
        int appointmentIndex=view.getAppointmentComboBox().getSelectedIndex();
        Integer appointmentId=appointmentIndex>0?visibleAppointments.get(appointmentIndex-1).getId():null;
        return new Bill(selectedId,customer.getId(),appointmentId,Input.date(view.getBillDateField().getText()),
                new BigDecimal(view.getTotalAmountField().getText()),method,
                (String)view.getPaymentStatusComboBox().getSelectedItem(),customer.getName());
    }

    private void save(boolean update) {
        if (update && selectedId==0) { JOptionPane.showMessageDialog(view,"Select a bill to update."); return; }
        if (!update && selectedId!=0) { JOptionPane.showMessageDialog(view,"Click Clear form before adding a new bill."); return; }
        try {
            Bill bill=readInput();
            if (update) bill.update(items); else bill.add(items);
        } catch (SQLException | IllegalArgumentException e) { Input.error(view,e); return; }
        clear(); JOptionPane.showMessageDialog(view,"Bill saved."); load();
    }

    private void delete() {
        if (selectedId==0) { JOptionPane.showMessageDialog(view,"Select a bill to delete."); return; }
        if (!Input.confirm(view,"Delete this bill and all its service items?")) return;
        try { Bill.delete(selectedId); } catch (SQLException e) { Input.error(view,e); return; }
        clear(); JOptionPane.showMessageDialog(view,"Bill deleted."); load();
    }

    private void load() {
        try {
            List<Bill> newRecords=Bill.getAll();
            List<Customer> newCustomers=Customer.getAll();
            List<Service> newServices=Service.getAll();
            List<Appointment> newAppointments=Appointment.getAll();
            clear(); changing=true;
            records=newRecords; customers=newCustomers; services=newServices; appointments=newAppointments;
            view.getCustomerComboBox().removeAllItems(); view.getCustomerComboBox().addItem("Select customer...");
            for (Customer c:customers) view.getCustomerComboBox().addItem(c.getId()+" - "+c.getName());
            view.getServiceComboBox().removeAllItems(); view.getServiceComboBox().addItem("Select service...");
            for (Service s:services) view.getServiceComboBox().addItem(s.getId()+" - "+s.getName());
            DefaultTableModel table=Input.emptyTable(view.getRecordsTable(),view.getSearchField());
            for (Bill bill:records) table.addRow(new Object[]{bill.getId(),bill.getCustomerName(),
                    bill.getAppointmentId()==null?"None":bill.getAppointmentId(),bill.getDate(),bill.getTotal(),bill.getPaymentMethod(),bill.getPaymentStatus()});
            filterAppointments();
            view.setStatus(records.size()+" bill(s) loaded. Add item edits to the list, then save the bill.");
        } catch (SQLException e) { view.setStatus("Refresh failed. Try again after checking the database."); Input.error(view,e); }
        finally { changing=false; }
    }

    private void select() {
        int row=view.getRecordsTable().getSelectedRow();
        if (row<0) { selectedId=0; view.getIdField().setText("Auto ID"); return; }
        Bill bill=records.get(view.getRecordsTable().convertRowIndexToModel(row));
        try {
            List<BillItem> loaded=Bill.getItems(bill.getId());
            changing=true;
            selectedId=bill.getId(); view.getIdField().setText(String.valueOf(selectedId));
            view.getCustomerComboBox().setSelectedIndex(0);
            for (int i=0;i<customers.size();i++) if (customers.get(i).getId()==bill.getCustomerId()) view.getCustomerComboBox().setSelectedIndex(i+1);
            filterAppointments();
            for (int i=0;i<visibleAppointments.size();i++) if (bill.getAppointmentId()!=null && visibleAppointments.get(i).getId()==bill.getAppointmentId()) view.getAppointmentComboBox().setSelectedIndex(i+1);
            view.getBillDateField().setText(bill.getDate().toString());
            view.getPaymentMethodComboBox().setSelectedItem(bill.getPaymentMethod());
            view.getPaymentStatusComboBox().setSelectedItem(bill.getPaymentStatus());
            view.getServiceComboBox().setSelectedIndex(0); view.getQuantityField().setText(""); view.getUnitPriceField().setText("");
            items.clear(); items.addAll(loaded); showItems();
        } catch (SQLException e) { clear(); Input.error(view,e); }
        finally { changing=false; }
    }

    private void clear() {
        changing=true;
        try {
            selectedId=0; items.clear(); view.clearForm(); view.getIdField().setText("Auto ID");
            view.getBillDateField().setText(LocalDate.now().toString());
            filterAppointments();
        } finally { changing=false; }
    }
}
