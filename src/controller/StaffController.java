package controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.CustomException;
import model.Staff;
import model.StaffDAO;
import view.ViewStaff;

/**
 * Controller for the Care team page: add, update, delete and show staff members.
 */
public class StaffController
{
    private final ViewStaff view;
    private final StaffDAO staffDAO = new StaffDAO();

    private List<Staff> shownStaff = new ArrayList<>();
    private int selectedId = 0;     // 0 means no staff member is selected

    public StaffController(ViewStaff view)
    {
        this.view = view;

        view.getAddButton().addActionListener(e -> addStaff());
        view.getEditButton().addActionListener(e -> updateStaff());
        view.getDeleteButton().addActionListener(e -> deleteStaff());
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

    private void addStaff()
    {
        if (selectedId != 0)
        {
            JOptionPane.showMessageDialog(view, "Click Clear form before adding a new record.");
            return;
        }
        try
        {
            Staff staff = readForm();
            staffDAO.insert(staff);
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

    private void updateStaff()
    {
        if (selectedId == 0)
        {
            JOptionPane.showMessageDialog(view, "Select a record to update.");
            return;
        }
        try
        {
            Staff staff = readForm();
            staff.setId(selectedId);
            staffDAO.update(staff);
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

    private void deleteStaff()
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
            staffDAO.delete(selectedId);
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

    private void showAll()
    {
        try
        {
            shownStaff = staffDAO.findAll();

            clear();
            view.getSearchField().setText("");
            DefaultTableModel model = (DefaultTableModel) view.getRecordsTable().getModel();
            model.setRowCount(0);
            view.getRecordsTable().setRowSorter(new TableRowSorter<>(model));

            for (Staff s : shownStaff)
            {
                model.addRow(new Object[]
                {
                    s.getId(),
                    s.getName(),
                    s.getRole(),
                    s.getPhone(),
                    s.getEmail()
                });
            }
            view.setStatus(shownStaff.size() + " record(s) loaded.");
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

        Staff s = shownStaff.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId = s.getId();
        view.getIdField().setText(String.valueOf(s.getId()));
        view.getNameField().setText(s.getName());
        view.getRoleComboBox().setSelectedItem(s.getRole());
        view.getPhoneField().setText(s.getPhone());
        view.getEmailField().setText(s.getEmail());
    }

    private void clear()
    {
        selectedId = 0;
        view.clearForm();
        view.getIdField().setText("Auto ID");
    }

    // Reads the form and checks it. Returns a Staff (without an id).
    private Staff readForm() throws CustomException
    {
        // the name
        String name = view.getNameField().getText().trim();
        if (name.isEmpty())
        {
            throw new CustomException("Staff name is required.");
        }
        if (name.length() > 100)
        {
            throw new CustomException("Staff name must have at most 100 characters.");
        }

        // the role
        if (view.getRoleComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a role.");
        }
        String role = (String) view.getRoleComboBox().getSelectedItem();

        // the phone is optional: 10 digits starting with 0 (+94 is changed to 0)
        String phone = view.getPhoneField().getText().trim();
        if (phone.length() > 30)
        {
            throw new CustomException("Phone must have at most 30 characters.");
        }
        phone = phone.replaceAll("[\\s()\\-]", "");
        if (phone.startsWith("+94"))
        {
            phone = "0" + phone.substring(3);
        }
        if (!phone.isEmpty() && !phone.matches("0[0-9]{9}"))
        {
            throw new CustomException("Enter a 10-digit Sri Lankan phone number, or leave it blank.");
        }

        // the email is optional too
        String email = view.getEmailField().getText().trim();
        if (email.length() > 100)
        {
            throw new CustomException("Email must have at most 100 characters.");
        }
        if (!email.isEmpty() && !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))
        {
            throw new CustomException("Enter a valid email address, or leave it blank.");
        }

        Staff staff = new Staff();
        staff.setName(name);
        staff.setRole(role);
        staff.setPhone(phone);
        staff.setEmail(email);
        return staff;
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(view, message, "Pawzenix", JOptionPane.ERROR_MESSAGE);
    }
}
