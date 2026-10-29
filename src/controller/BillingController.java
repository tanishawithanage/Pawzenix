package controller;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Appointment;
import model.AppointmentDAO;
import model.Bill;
import model.BillDAO;
import model.BillItem;
import model.Customer;
import model.CustomerDAO;
import model.CustomException;
import model.Service;
import model.ServiceDAO;
import view.ViewBilling;

/**
 * Controller for the Billing page.
 * A bill has a header (customer, date, payment) and a list of items (service, quantity, price).
 * The items are kept in the "items" list while the person edits them, and are saved together with the bill.
 */
public class BillingController
{
    private final ViewBilling view;
    private final BillDAO billDAO = new BillDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    private List<Bill> shownBills = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();          // all appointments
    private final List<Appointment> customerAppointments = new ArrayList<>();   // only the chosen customer's appointments
    private final List<BillItem> items = new ArrayList<>();              // the items of the bill on the screen
    private int selectedId = 0;     // 0 means no bill is selected

    // true while the program itself is changing the lists, so our listeners ignore those changes
    private boolean changing = false;

    public BillingController(ViewBilling view)
    {
        this.view = view;

        // the total is calculated from the items, so the person cannot type it
        view.getTotalAmountField().setEditable(false);

        view.getAddButton().addActionListener(e -> addBill());
        view.getEditButton().addActionListener(e -> updateBill());
        view.getDeleteButton().addActionListener(e -> deleteBill());
        view.getRefreshButton().addActionListener(e -> showAll());
        view.getAddItemButton().addActionListener(e -> addItem());
        view.getUpdateItemButton().addActionListener(e -> updateItem());
        view.getRemoveItemButton().addActionListener(e -> removeItem());

        // The view already has its own Clear action. Remove it, so only ours runs (it shows "Auto ID").
        for (ActionListener listener : view.getClearButton().getActionListeners())
        {
            view.getClearButton().removeActionListener(listener);
        }
        view.getClearButton().addActionListener(e -> clear());

        // when another customer is chosen, show only that customer's appointments
        view.getCustomerComboBox().addActionListener(e ->
        {
            if (!changing)
            {
                showCustomerAppointments();
            }
        });

        // click a bill in the table: show it in the form
        view.getRecordsTable().getSelectionModel().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting() && !changing)
            {
                onBillSelected();
            }
        });

        // click an item in the items list: show it in the item boxes
        view.getItemsList().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting() && !changing)
            {
                onItemSelected();
            }
        });

        showAll();
    }

    // ---------------------------------------------------------------- bills

    private void addBill()
    {
        if (selectedId != 0)
        {
            JOptionPane.showMessageDialog(view, "Click Clear form before adding a new bill.");
            return;
        }
        try
        {
            Bill bill = readForm();
            billDAO.insert(bill, items);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        clear();
        JOptionPane.showMessageDialog(view, "Bill saved.");
        showAll();
    }

    private void updateBill()
    {
        if (selectedId == 0)
        {
            JOptionPane.showMessageDialog(view, "Select a bill to update.");
            return;
        }
        try
        {
            Bill bill = readForm();
            bill.setId(selectedId);
            billDAO.update(bill, items);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        clear();
        JOptionPane.showMessageDialog(view, "Bill saved.");
        showAll();
    }

    private void deleteBill()
    {
        if (selectedId == 0)
        {
            JOptionPane.showMessageDialog(view, "Select a bill to delete.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(view, "Delete this bill and all its service items?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION)
        {
            return;
        }

        try
        {
            billDAO.delete(selectedId);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        clear();
        JOptionPane.showMessageDialog(view, "Bill deleted.");
        showAll();
    }

    // Loads the bills and the drop-down lists from the database.
    private void showAll()
    {
        try
        {
            List<Bill> bills = billDAO.findAll();
            List<Customer> newCustomers = customerDAO.findAll();
            List<Service> newServices = serviceDAO.findAll();
            List<Appointment> newAppointments = appointmentDAO.findAll();

            clear();
            changing = true;
            shownBills = bills;
            customers = newCustomers;
            services = newServices;
            appointments = newAppointments;

            // the first item of each list is only a hint
            view.getCustomerComboBox().removeAllItems();
            view.getCustomerComboBox().addItem("Select customer...");
            for (Customer c : customers)
            {
                view.getCustomerComboBox().addItem(c.getId() + " - " + c.getName());
            }

            view.getServiceComboBox().removeAllItems();
            view.getServiceComboBox().addItem("Select service...");
            for (Service s : services)
            {
                view.getServiceComboBox().addItem(s.getId() + " - " + s.getName());
            }

            view.getSearchField().setText("");
            DefaultTableModel model = (DefaultTableModel) view.getRecordsTable().getModel();
            model.setRowCount(0);
            view.getRecordsTable().setRowSorter(new TableRowSorter<>(model));

            for (Bill b : shownBills)
            {
                Object appointmentText = "None";
                if (b.getAppointmentId() != null)
                {
                    appointmentText = b.getAppointmentId();
                }

                model.addRow(new Object[]
                {
                    b.getId(),
                    b.getCustomerName(),
                    appointmentText,
                    b.getDate(),
                    b.getTotal(),
                    b.getPaymentMethod(),
                    b.getPaymentStatus()
                });
            }

            showCustomerAppointments();
            view.setStatus(shownBills.size() + " bill(s) loaded. Add item edits to the list, then save the bill.");
        }
        catch (CustomException ex)
        {
            view.setStatus("Refresh failed. Try again after checking the database.");
            showError(ex.getMessage());
        }
        finally
        {
            changing = false;
        }
    }

    private void onBillSelected()
    {
        int row = view.getRecordsTable().getSelectedRow();

        if (row < 0)
        {
            selectedId = 0;
            view.getIdField().setText("Auto ID");
            return;
        }

        Bill bill = shownBills.get(view.getRecordsTable().convertRowIndexToModel(row));

        try
        {
            List<BillItem> billItems = billDAO.findItems(bill.getId());

            changing = true;
            selectedId = bill.getId();
            view.getIdField().setText(String.valueOf(bill.getId()));

            // choose the customer, then show that customer's appointments
            view.getCustomerComboBox().setSelectedIndex(0);
            for (int i = 0; i < customers.size(); i++)
            {
                if (customers.get(i).getId() == bill.getCustomerId())
                {
                    view.getCustomerComboBox().setSelectedIndex(i + 1);
                }
            }
            showCustomerAppointments();

            // choose the appointment of the bill (position + 1 because of "No appointment")
            for (int i = 0; i < customerAppointments.size(); i++)
            {
                if (bill.getAppointmentId() != null && customerAppointments.get(i).getId() == bill.getAppointmentId())
                {
                    view.getAppointmentComboBox().setSelectedIndex(i + 1);
                }
            }

            view.getBillDateField().setText(bill.getDate().toString());
            view.getPaymentMethodComboBox().setSelectedItem(bill.getPaymentMethod());
            view.getPaymentStatusComboBox().setSelectedItem(bill.getPaymentStatus());

            clearItemInputs();
            items.clear();
            items.addAll(billItems);
            showItems();
        }
        catch (CustomException ex)
        {
            clear();
            showError(ex.getMessage());
        }
        finally
        {
            changing = false;
        }
    }

    private void clear()
    {
        changing = true;
        try
        {
            selectedId = 0;
            items.clear();
            view.clearForm();
            view.getIdField().setText("Auto ID");
            view.getBillDateField().setText(LocalDate.now().toString());
            showCustomerAppointments();
        }
        finally
        {
            changing = false;
        }
    }

    // Shows only the appointments of the chosen customer in the appointment list.
    private void showCustomerAppointments()
    {
        customerAppointments.clear();
        view.getAppointmentComboBox().removeAllItems();
        view.getAppointmentComboBox().addItem("No appointment");

        int index = view.getCustomerComboBox().getSelectedIndex();
        if (index <= 0)
        {
            return;
        }

        int customerId = customers.get(index - 1).getId();
        for (Appointment a : appointments)
        {
            if (a.getCustomerId() == customerId)
            {
                customerAppointments.add(a);
                view.getAppointmentComboBox().addItem(a.getId() + " - " + a.getPetName() + " - " + a.getStart());
            }
        }
    }

    // Reads the bill header from the form and checks it. Returns a Bill (without an id).
    private Bill readForm() throws CustomException
    {
        // the customer and the payment method must be chosen
        if (view.getCustomerComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a customer.");
        }
        Customer customer = customers.get(view.getCustomerComboBox().getSelectedIndex() - 1);

        if (view.getPaymentMethodComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a payment method.");
        }
        String method = (String) view.getPaymentMethodComboBox().getSelectedItem();

        if (items.isEmpty())
        {
            throw new CustomException("Add at least one service item to the bill.");
        }

        // the appointment is optional (the first item is "No appointment")
        Integer appointmentId = null;
        int appointmentIndex = view.getAppointmentComboBox().getSelectedIndex();
        if (appointmentIndex > 0)
        {
            appointmentId = customerAppointments.get(appointmentIndex - 1).getId();
        }

        // the bill date, like 2026-10-25
        String dateMessage = "Enter a valid date as YYYY-MM-DD (year 1000 to 9999).";
        LocalDate date;
        try
        {
            date = LocalDate.parse(view.getBillDateField().getText().trim());
        }
        catch (DateTimeParseException ex)
        {
            throw new CustomException(dateMessage);
        }
        if (date.getYear() < 1000 || date.getYear() > 9999)
        {
            throw new CustomException(dateMessage);
        }

        // the total is the sum of all the items
        BigDecimal total = getItemsTotal();
        if (total.compareTo(new BigDecimal("9999999999.99")) > 0)
        {
            throw new CustomException("Bill total is too large.");
        }

        Bill bill = new Bill();
        bill.setCustomerId(customer.getId());
        bill.setAppointmentId(appointmentId);
        bill.setDate(date);
        bill.setTotal(total);
        bill.setPaymentMethod(method);
        bill.setPaymentStatus((String) view.getPaymentStatusComboBox().getSelectedItem());
        return bill;
    }

    // ---------------------------------------------------------------- items of the bill

    private void addItem()
    {
        try
        {
            items.add(readItem());
            showItems();
            clearItemInputs();
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
        }
    }

    private void updateItem()
    {
        int selected = view.getItemsList().getSelectedIndex();
        if (selected < 0)
        {
            JOptionPane.showMessageDialog(view, "Select a service item to update.");
            return;
        }

        try
        {
            BillItem item = readItem();

            // if the service is the same, keep the name that was saved with the item
            if (items.get(selected).getServiceId() == item.getServiceId())
            {
                item.setServiceName(items.get(selected).getServiceName());
            }
            items.set(selected, item);
            showItems();
            clearItemInputs();
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
        }
    }

    private void removeItem()
    {
        int selected = view.getItemsList().getSelectedIndex();
        if (selected < 0)
        {
            JOptionPane.showMessageDialog(view, "Select a service item to remove.");
            return;
        }
        items.remove(selected);
        showItems();
    }

    // Shows the items in the list and the total in the Total box.
    private void showItems()
    {
        changing = true;
        try
        {
            DefaultListModel<String> list = new DefaultListModel<>();
            for (BillItem item : items)
            {
                list.addElement(item.toString());
            }
            view.getItemsList().setModel(list);
            view.getTotalAmountField().setText(getItemsTotal().toPlainString());
        }
        finally
        {
            changing = false;
        }
    }

    private void onItemSelected()
    {
        int selected = view.getItemsList().getSelectedIndex();
        if (selected < 0)
        {
            return;
        }

        BillItem item = items.get(selected);
        view.getServiceComboBox().setSelectedIndex(0);
        for (int i = 0; i < services.size(); i++)
        {
            if (services.get(i).getId() == item.getServiceId())
            {
                view.getServiceComboBox().setSelectedIndex(i + 1);
            }
        }
        view.getQuantityField().setText(String.valueOf(item.getQuantity()));
        view.getUnitPriceField().setText(item.getUnitPrice().toPlainString());
    }

    // Reads the item boxes (service, quantity, unit price) and checks them.
    private BillItem readItem() throws CustomException
    {
        if (view.getServiceComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a service.");
        }
        Service service = services.get(view.getServiceComboBox().getSelectedIndex() - 1);

        // the quantity: a whole number from 1 to 10000
        String quantityMessage = "Quantity must be a whole number from 1 to 10000.";
        int quantity;
        try
        {
            quantity = Integer.parseInt(view.getQuantityField().getText().trim());
        }
        catch (NumberFormatException ex)
        {
            throw new CustomException(quantityMessage);
        }
        if (quantity < 1 || quantity > 10000)
        {
            throw new CustomException(quantityMessage);
        }

        // the unit price: digits with at most two decimals, more than zero
        String priceMessage = "Unit price must be positive, with at most two decimal places.";
        String priceText = view.getUnitPriceField().getText().trim();
        if (!priceText.matches("[0-9]+(\\.[0-9]{1,2})?"))
        {
            throw new CustomException(priceMessage);
        }
        BigDecimal price = new BigDecimal(priceText).setScale(2);
        if (price.signum() <= 0 || price.compareTo(new BigDecimal("99999999.99")) > 0)
        {
            throw new CustomException(priceMessage);
        }

        BillItem item = new BillItem();
        item.setServiceId(service.getId());
        item.setServiceName(service.getName());
        item.setQuantity(quantity);
        item.setUnitPrice(price);
        return item;
    }

    private void clearItemInputs()
    {
        view.getServiceComboBox().setSelectedIndex(0);
        view.getQuantityField().setText("");
        view.getUnitPriceField().setText("");
    }

    // The sum of price x quantity of all items.
    private BigDecimal getItemsTotal()
    {
        BigDecimal total = BigDecimal.ZERO.setScale(2);
        for (BillItem item : items)
        {
            total = total.add(item.getTotal());
        }
        return total;
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(view, message, "Pawzenix", JOptionPane.ERROR_MESSAGE);
    }
}
