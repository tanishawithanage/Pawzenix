package controller;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Customer;
import view.ViewCustomers;

/**
 *
 * @author TANISHA SANJANA
 */
public class CustomerController {

    private final ViewCustomers view;
    private int selectedId = 0;

    public CustomerController(ViewCustomers view) {
        this.view = view;
        view.getAddButton().addActionListener(e -> addCustomer());
        view.getEditButton().addActionListener(e -> updateCustomer());
        view.getDeleteButton().addActionListener(e -> deleteCustomer());
        view.getRefreshButton().addActionListener(e -> loadCustomers());
        view.getClearButton().addActionListener(e -> clearFields());
        view.getRecordsTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectCustomer();
            }
        });
        loadCustomers();
    }

    private Customer readInput(int id) throws InvalidCustomerException {
        String name = view.getNameField().getText().trim();
        String phone = view.getPhoneField().getText().trim().replaceAll("[\\s()-]", "");
        String email = view.getEmailField().getText().trim();
        String address = view.getAddressField().getText().trim();

        if (name.isEmpty()) {
            throw new InvalidCustomerException("Enter the customer's name.");
        }
        if (name.length() > 100) {
            throw new InvalidCustomerException("Name must be 100 characters or fewer.");
        }

        if (phone.startsWith("+94")) {
            phone = "0" + phone.substring(3);
        }
        if (!phone.matches("07\\d{8}")) {
            throw new InvalidCustomerException("Enter a Sri Lankan mobile number, e.g. 0771234567 or +94771234567.");
        }
        if (email.length() > 100 || (!email.isEmpty()
                && !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))) {
            throw new InvalidCustomerException("Enter a valid email address, up to 100 characters, or leave it blank.");
        }
        if (address.length() > 255) {
            throw new InvalidCustomerException("Address must be 255 characters or fewer.");
        }
        return new Customer(id, name, phone, email, address);
    }

    private void addCustomer() {
        try {
            Customer customer = readInput(0);
            customer.add();
        } catch (InvalidCustomerException | SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Customer added successfully.");
        clearFields();
        loadCustomers();
    }

    private void updateCustomer() {
        try {
            requireSelection();
            Customer customer = readInput(selectedId);
            customer.update();
        } catch (InvalidCustomerException | SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Customer updated successfully.");
        clearFields();
        loadCustomers();
    }

    private void deleteCustomer() {
        try {
            requireSelection();
            int answer = JOptionPane.showConfirmDialog(view,
                    "Delete this customer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
            Customer.delete(selectedId);
        } catch (InvalidCustomerException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            return;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                JOptionPane.showMessageDialog(view,
                        "Cannot delete this customer because other records are linked to them.");
            } else {
                JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
            }
            return;
        }
        JOptionPane.showMessageDialog(view, "Customer deleted successfully.");
        clearFields();
        loadCustomers();
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = Customer.getAll();
            view.getSearchField().setText("");
            view.getRecordsTable().setRowSorter(
                    new TableRowSorter<>(view.getRecordsTable().getModel()));
            clearFields();
            DefaultTableModel table = (DefaultTableModel) view.getRecordsTable().getModel();
            table.setRowCount(0);
            for (Customer customer : customers) {
                table.addRow(new Object[]{
                    customer.getId(), customer.getName(), customer.getPhone(),
                    customer.getEmail(), customer.getAddress()
                });
            }
            view.setStatus(customers.size() + " customer(s).");
        } catch (SQLException e) {
            view.setStatus("Could not load customers. Click Refresh.");
            JOptionPane.showMessageDialog(view,
                    "Error loading customers: " + e.getMessage()
                    + "\nClick Refresh after fixing the connection.");
        }
    }

    private void selectCustomer() {
        int selectedRow = view.getRecordsTable().getSelectedRow();
        if (selectedRow == -1) {
            selectedId = 0;
            view.getIdField().setText("Auto ID");
            return;
        }
        int row = view.getRecordsTable().convertRowIndexToModel(selectedRow);
        DefaultTableModel table = (DefaultTableModel) view.getRecordsTable().getModel();
        selectedId = ((Number) table.getValueAt(row, 0)).intValue();
        view.getIdField().setText(String.valueOf(selectedId));
        view.getNameField().setText(cell(table, row, 1));
        view.getPhoneField().setText(cell(table, row, 2));
        view.getEmailField().setText(cell(table, row, 3));
        view.getAddressField().setText(cell(table, row, 4));
    }

    private void clearFields() {
        selectedId = 0;
        view.clearForm();
    }

    private void requireSelection() throws InvalidCustomerException {
        if (selectedId <= 0) {
            throw new InvalidCustomerException("Select a customer from the table first.");
        }
    }

    private String cell(DefaultTableModel table, int row, int column) {
        Object value = table.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }
}
