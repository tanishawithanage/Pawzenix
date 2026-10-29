package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.CustomException;
import model.Service;
import model.ServiceDAO;
import view.ViewServices;

/**
 * Controller for the Services page: add, update, delete and show services.
 */
public class ServiceController
{
    private final ViewServices view;
    private final ServiceDAO serviceDAO = new ServiceDAO();

    private List<Service> shownServices = new ArrayList<>();
    private int selectedId = 0;     // 0 means no service is selected

    public ServiceController(ViewServices view)
    {
        this.view = view;

        view.getAddButton().addActionListener(e -> addService());
        view.getEditButton().addActionListener(e -> updateService());
        view.getDeleteButton().addActionListener(e -> deleteService());
        view.getClearButton().addActionListener(e -> clear());
        view.getRefreshButton().addActionListener(e -> showAll());

        view.getRecordsTable().getSelectionModel().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting())
            {
                onRowSelected();
            }
        });

        showAll();
    }

    private void addService()
    {
        try
        {
            Service service = readForm();
            serviceDAO.insert(service);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Service added successfully.");
        clear();
        showAll();
    }

    private void updateService()
    {
        try
        {
            checkSelected();
            Service service = readForm();
            service.setId(selectedId);
            serviceDAO.update(service);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Service updated successfully.");
        clear();
        showAll();
    }

    private void deleteService()
    {
        try
        {
            checkSelected();

            int answer = JOptionPane.showConfirmDialog(view, "Delete this service?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (answer != JOptionPane.YES_OPTION)
            {
                return;
            }
            serviceDAO.delete(selectedId);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(view, "Service deleted successfully.");
        clear();
        showAll();
    }

    private void showAll()
    {
        try
        {
            shownServices = serviceDAO.findAll();

            view.getSearchField().setText("");
            clear();

            DefaultTableModel model = (DefaultTableModel) view.getRecordsTable().getModel();
            model.setRowCount(0);
            view.getRecordsTable().setRowSorter(new TableRowSorter<>(model));

            for (Service s : shownServices)
            {
                model.addRow(new Object[]
                {
                    s.getId(),
                    s.getName(),
                    s.getCategory(),
                    s.getDuration()
                });
            }
            view.setStatus(shownServices.size() + " service(s).");
        }
        catch (CustomException ex)
        {
            view.setStatus("Could not load services. Click Refresh.");
            showError("Error loading services: " + ex.getMessage() + "\nClick Refresh after fixing the connection.");
        }
    }

    private void onRowSelected()
    {
        int row = view.getRecordsTable().getSelectedRow();

        if (row == -1)
        {
            selectedId = 0;
            view.getIdField().setText("Auto ID");
            return;
        }

        Service s = shownServices.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId = s.getId();
        view.getIdField().setText(String.valueOf(s.getId()));
        view.getNameField().setText(s.getName());
        view.getCategoryComboBox().setSelectedItem(s.getCategory());
        view.getDurationField().setText(String.valueOf(s.getDuration()));
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
            throw new CustomException("Select a service from the table first.");
        }
    }

    // Reads the form and checks it. Returns a Service (without an id).
    private Service readForm() throws CustomException
    {
        String name = view.getNameField().getText().trim();
        String minutes = view.getDurationField().getText().trim();

        if (name.isEmpty())
        {
            throw new CustomException("Enter the service name.");
        }
        if (name.length() > 100)
        {
            throw new CustomException("Service name must not exceed 100 characters.");
        }

        // the first item of the category list is only a hint, so it cannot be chosen
        if (view.getCategoryComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a category.");
        }
        String category = view.getCategoryComboBox().getSelectedItem().toString();

        int duration;
        try
        {
            duration = Integer.parseInt(minutes);
        }
        catch (NumberFormatException ex)
        {
            throw new CustomException("Enter minutes as a whole number, such as 30.");
        }
        if (duration <= 0)
        {
            throw new CustomException("Minutes must be greater than zero.");
        }

        Service service = new Service();
        service.setName(name);
        service.setCategory(category);
        service.setDuration(duration);
        return service;
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(view, message, "Pawzenix", JOptionPane.ERROR_MESSAGE);
    }
}
