/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Service;
import view.ViewServices;
/**
 *
 * @author TANISHA SANJANA
 */
public class ServiceController {
    

    private final ViewServices view;
    private int selectedId = 0;

    public ServiceController(ViewServices view) {
        this.view = view;

        view.getAddButton().addActionListener(e -> addService());
        view.getEditButton().addActionListener(e -> updateService());
        view.getDeleteButton().addActionListener(e -> deleteService());
        view.getClearButton().addActionListener(e -> clearFields());
        view.getRefreshButton().addActionListener(e -> loadServices());

        view.getRecordsTable().getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        selectService();
                    }
                });

        loadServices();
    }

    private Service readInput(int id) {

        String name = view.getNameField().getText().trim();
        String minutes = view.getDurationField().getText().trim();

        if (name.isEmpty()) {
            throw new IllegalArgumentException(
                    "Enter the service name."
            );
        }

        if (name.length() > 100) {
            throw new IllegalArgumentException(
                    "Service name must not exceed 100 characters."
            );
        }

        if (view.getCategoryComboBox().getSelectedIndex() <= 0) {
            throw new IllegalArgumentException(
                    "Select a category."
            );
        }

        String category = view.getCategoryComboBox()
                .getSelectedItem().toString();

        int duration;

        try {
            duration = Integer.parseInt(minutes);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Enter minutes as a whole number, such as 30."
            );
        }

        if (duration <= 0) {
            throw new IllegalArgumentException(
                    "Minutes must be greater than zero."
            );
        }

        return new Service(id, name, category, duration);
    }

    private void addService() {
        try {
            Service service = readInput(0);
            service.add();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    view, "Error: " + e.getMessage()
            );
            return;

        } catch (SQLException e) {
            showDatabaseError(e);
            return;
        }

        JOptionPane.showMessageDialog(
                view, "Service added successfully."
        );

        clearFields();
        loadServices();
    }

    private void updateService() {
        try {
            requireSelection();

            Service service = readInput(selectedId);
            service.update();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    view, "Error: " + e.getMessage()
            );
            return;

        } catch (SQLException e) {
            showDatabaseError(e);
            return;
        }

        JOptionPane.showMessageDialog(
                view, "Service updated successfully."
        );

        clearFields();
        loadServices();
    }

    private void deleteService() {
        try {
            requireSelection();

            int answer = JOptionPane.showConfirmDialog(
                    view,
                    "Delete this service?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }

            Service.delete(selectedId);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    view, "Error: " + e.getMessage()
            );
            return;

        } catch (SQLException e) {
            showDatabaseError(e);
            return;
        }

        JOptionPane.showMessageDialog(
                view, "Service deleted successfully."
        );

        clearFields();
        loadServices();
    }

    private void loadServices() {
        try {
            List<Service> services = Service.getAll();

            view.getSearchField().setText("");

            view.getRecordsTable().setRowSorter(
                    new TableRowSorter<>(
                            view.getRecordsTable().getModel()
                    )
            );

            clearFields();

            DefaultTableModel table = (DefaultTableModel)
                    view.getRecordsTable().getModel();

            table.setRowCount(0);

            for (Service service : services) {
                table.addRow(new Object[]{
                    service.getId(),
                    service.getName(),
                    service.getCategory(),
                    service.getDuration()
                });
            }

            view.setStatus(services.size() + " service(s).");

        } catch (SQLException e) {
            view.setStatus("Could not load services. Click Refresh.");

            JOptionPane.showMessageDialog(
                    view,
                    "Error loading services: " + e.getMessage()
                    + "\nClick Refresh after fixing the connection."
            );
        }
    }

    private void selectService() {

        int selectedRow = view.getRecordsTable().getSelectedRow();

        if (selectedRow == -1) {
            selectedId = 0;
            view.getIdField().setText("Auto ID");
            return;
        }

        int row = view.getRecordsTable()
                .convertRowIndexToModel(selectedRow);

        DefaultTableModel table = (DefaultTableModel)
                view.getRecordsTable().getModel();

        selectedId = ((Number) table.getValueAt(row, 0)).intValue();

        view.getIdField().setText(String.valueOf(selectedId));
        view.getNameField().setText(table.getValueAt(row, 1).toString());

        view.getCategoryComboBox().setSelectedItem(
                table.getValueAt(row, 2).toString()
        );

        view.getDurationField().setText(
                table.getValueAt(row, 3).toString()
        );
    }

    private void requireSelection() {
        if (selectedId == 0) {
            throw new IllegalArgumentException(
                    "Select a service from the table first."
            );
        }
    }

    private void clearFields() {
        selectedId = 0;
        view.clearForm();
    }

    private void showDatabaseError(SQLException e) {

        if (e.getErrorCode() == 1062) {
            JOptionPane.showMessageDialog(
                    view, "Error: A service with this name already exists."
            );

        } else if (e.getErrorCode() == 1451) {
            JOptionPane.showMessageDialog(
                    view,
                    "Error: Cannot delete this service because "
                    + "other records are linked to it."
            );

        } else {
            JOptionPane.showMessageDialog(
                    view, "Error: " + e.getMessage()
            );
        }
    }
}