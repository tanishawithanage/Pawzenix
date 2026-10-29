package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Customer;
import model.CustomerDAO;
import model.CustomException;
import view.ViewCustomers;

/**
 * Controller for the Customers page: add, update, delete and show customers.
 */
public class CustomerController
{
    private final ViewCustomers view;
    private final CustomerDAO customerDAO = new CustomerDAO();

    // the customers that are in the table (same order as the table rows)
    private List<Customer> shownCustomers = new ArrayList<>();
    private int selectedId = 0;     // 0 means no customer is selected

    public CustomerController(ViewCustomers view)
    {
        this.view = view;

        view.getAddButton().addActionListener(e -> addCustomer());
        view.getEditButton().addActionListener(e -> updateCustomer());
        view.getDeleteButton().addActionListener(e -> deleteCustomer());
        view.getRefreshButton().addActionListener(e -> showAll());
        view.getClearButton().addActionListener(e -> clear());

        // fill the form when a row of the table is clicked
        view.getRecordsTable().getSelectionModel().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting())
            {
                onRowSelected();
            }
        });

        showAll();
    }

    private void addCustomer()
    {
        try
        {
            Customer customer = readForm();
            customerDAO.insert(customer);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Customer added successfully.");
        clear();
        showAll();
    }

    private void updateCustomer()
    {
        try
        {
            checkSelected();
            Customer customer = readForm();
            customer.setId(selectedId);
            customerDAO.update(customer);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Customer updated successfully.");
        clear();
        showAll();
    }

    private void deleteCustomer()
    {
        try
        {
            checkSelected();

            int answer = JOptionPane.showConfirmDialog(view, "Delete this customer?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (answer != JOptionPane.YES_OPTION)
            {
                return;
            }
            customerDAO.delete(selectedId);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Customer deleted successfully.");
        clear();
        showAll();
    }

    // Loads all customers from the database into the table.
    private void showAll()
    {
        try
        {
            shownCustomers = customerDAO.findAll();

            view.getSearchField().setText("");
            clear();

            DefaultTableModel model = (DefaultTableModel) view.getRecordsTable().getModel();
            model.setRowCount(0);
            view.getRecordsTable().setRowSorter(new TableRowSorter<>(model));

            for (Customer c : shownCustomers)
            {
                model.addRow(new Object[]
                {
                    c.getId(),
                    c.getName(),
                    c.getPhone(),
                    c.getEmail(),
                    c.getAddress()
                });
            }
            view.setStatus(shownCustomers.size() + " customer(s).");
        }
        catch (CustomException ex)
        {
            view.setStatus("Could not load customers. Click Refresh.");
            showError("Error loading customers: " + ex.getMessage() + "\nClick Refresh after fixing the connection.");
        }
    }

    private void onRowSelected()
    {
        int row = view.getRecordsTable().getSelectedRow();

        // no row is selected
        if (row == -1)
        {
            selectedId = 0;
            view.getIdField().setText("Auto ID");
            return;
        }

        // the table can be sorted, so ask which row of the list this is
        Customer c = shownCustomers.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId = c.getId();
        view.getIdField().setText(String.valueOf(c.getId()));
        view.getNameField().setText(c.getName());
        view.getPhoneField().setText(c.getPhone());
        view.getEmailField().setText(c.getEmail());
        view.getAddressField().setText(c.getAddress());
    }

    private void clear()
    {
        selectedId = 0;
        view.clearForm();
    }

    private void checkSelected() throws CustomException
    {
        if (selectedId == 0)
        {
            throw new CustomException("Select a customer from the table first.");
        }
    }

    // Reads the form and checks it. Returns a Customer (without an id).
    private Customer readForm() throws CustomException
    {
        String name = view.getNameField().getText().trim();
        String phone = view.getPhoneField().getText().trim().replaceAll("[\\s()-]", "");
        String email = view.getEmailField().getText().trim();
        String address = view.getAddressField().getText().trim();

        if (name.isEmpty())
        {
            throw new CustomException("Enter the customer's name.");
        }
        if (name.length() > 100)
        {
            throw new CustomException("Name must be 100 characters or fewer.");
        }

        // +94771234567 is the same number as 0771234567
        if (phone.startsWith("+94"))
        {
            phone = "0" + phone.substring(3);
        }
        // a Sri Lankan mobile number: 07 followed by 8 digits
        if (!phone.matches("07\\d{8}"))
        {
            throw new CustomException("Enter a Sri Lankan mobile number, e.g. 0771234567 or +94771234567.");
        }

        // the email is optional, but if it is typed it must look like name@site.com
        if (email.length() > 100 || (!email.isEmpty() && !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")))
        {
            throw new CustomException("Enter a valid email address, up to 100 characters, or leave it blank.");
        }
        if (address.length() > 255)
        {
            throw new CustomException("Address must be 255 characters or fewer.");
        }

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        return customer;
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(view, message, "Pawzenix", JOptionPane.ERROR_MESSAGE);
    }
}
