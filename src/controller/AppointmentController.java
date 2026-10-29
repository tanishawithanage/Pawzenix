package controller;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Appointment;
import model.AppointmentDAO;
import model.CustomException;
import model.Pet;
import model.PetDAO;
import model.Service;
import model.ServiceDAO;
import model.Staff;
import model.StaffDAO;
import view.ViewAppointments;

/**
 * Controller for the Appointments page: add, update, delete and show appointments.
 */
public class AppointmentController
{
    private final ViewAppointments view;
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final PetDAO petDAO = new PetDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final StaffDAO staffDAO = new StaffDAO();

    private List<Appointment> shownAppointments = new ArrayList<>();
    // the lists behind the three drop-down lists
    private List<Pet> pets = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    private List<Staff> staffList = new ArrayList<>();
    private int selectedId = 0;     // 0 means no appointment is selected

    public AppointmentController(ViewAppointments view)
    {
        this.view = view;

        view.getAddButton().addActionListener(e -> addAppointment());
        view.getEditButton().addActionListener(e -> updateAppointment());
        view.getDeleteButton().addActionListener(e -> deleteAppointment());
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

    private void addAppointment()
    {
        if (selectedId != 0)
        {
            JOptionPane.showMessageDialog(view, "Click Clear form before adding a new record.");
            return;
        }
        try
        {
            Appointment appointment = readForm();
            appointmentDAO.insert(appointment);
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

    private void updateAppointment()
    {
        if (selectedId == 0)
        {
            JOptionPane.showMessageDialog(view, "Select a record to update.");
            return;
        }
        try
        {
            Appointment appointment = readForm();
            appointment.setId(selectedId);
            appointmentDAO.update(appointment);
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

    private void deleteAppointment()
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
            appointmentDAO.delete(selectedId);
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

    // Loads the appointments and the three drop-down lists from the database.
    private void showAll()
    {
        try
        {
            List<Appointment> appointments = appointmentDAO.findAll();
            pets = petDAO.findAll();
            services = serviceDAO.findAll();
            staffList = staffDAO.findAll();
            shownAppointments = appointments;

            // the first item of each list is only a hint
            view.getPetComboBox().removeAllItems();
            view.getPetComboBox().addItem("Select pet...");
            for (Pet p : pets)
            {
                view.getPetComboBox().addItem(p.getId() + " - " + p.getName() + " (" + p.getCustomerName() + ")");
            }

            view.getServiceComboBox().removeAllItems();
            view.getServiceComboBox().addItem("Select service...");
            for (Service s : services)
            {
                view.getServiceComboBox().addItem(s.getId() + " - " + s.getName());
            }

            view.getStaffComboBox().removeAllItems();
            view.getStaffComboBox().addItem("Unassigned");
            for (Staff s : staffList)
            {
                view.getStaffComboBox().addItem(s.getId() + " - " + s.getName());
            }

            clear();
            view.getSearchField().setText("");
            DefaultTableModel model = (DefaultTableModel) view.getRecordsTable().getModel();
            model.setRowCount(0);
            view.getRecordsTable().setRowSorter(new TableRowSorter<>(model));

            for (Appointment a : shownAppointments)
            {
                String staffName = a.getStaffName();
                if (staffName == null)
                {
                    staffName = "Unassigned";
                }

                model.addRow(new Object[]
                {
                    a.getId(),
                    a.getPetName(),
                    a.getServiceName(),
                    staffName,
                    a.getStart().toLocalDate(),
                    a.getStart().toLocalTime(),
                    a.getStatus(),
                    a.getNotes()
                });
            }
            view.setStatus(shownAppointments.size() + " record(s) loaded.");
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

        Appointment a = shownAppointments.get(view.getRecordsTable().convertRowIndexToModel(row));
        selectedId = a.getId();
        view.getIdField().setText(String.valueOf(a.getId()));

        // choose the right item in each list (position + 1 because of the hint item)
        view.getPetComboBox().setSelectedIndex(0);
        for (int i = 0; i < pets.size(); i++)
        {
            if (pets.get(i).getId() == a.getPetId())
            {
                view.getPetComboBox().setSelectedIndex(i + 1);
            }
        }

        view.getServiceComboBox().setSelectedIndex(0);
        for (int i = 0; i < services.size(); i++)
        {
            if (services.get(i).getId() == a.getServiceId())
            {
                view.getServiceComboBox().setSelectedIndex(i + 1);
            }
        }

        view.getStaffComboBox().setSelectedIndex(0);
        for (int i = 0; i < staffList.size(); i++)
        {
            if (a.getStaffId() != null && staffList.get(i).getId() == a.getStaffId())
            {
                view.getStaffComboBox().setSelectedIndex(i + 1);
            }
        }

        view.getDateField().setText(a.getStart().toLocalDate().toString());
        view.getTimeField().setText(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm")));
        view.getStatusComboBox().setSelectedItem(a.getStatus());
        view.getNotesArea().setText(a.getNotes());
    }

    private void clear()
    {
        selectedId = 0;
        view.clearForm();
        view.getIdField().setText("Auto ID");
    }

    // Reads the form and checks it. Returns an Appointment (without an id).
    private Appointment readForm() throws CustomException
    {
        // the pet and the service must be chosen
        if (view.getPetComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a pet.");
        }
        if (view.getServiceComboBox().getSelectedIndex() <= 0)
        {
            throw new CustomException("Select a service.");
        }
        Pet pet = pets.get(view.getPetComboBox().getSelectedIndex() - 1);
        Service service = services.get(view.getServiceComboBox().getSelectedIndex() - 1);

        // the staff member is optional (the first item is "Unassigned")
        Integer staffId = null;
        int staffIndex = view.getStaffComboBox().getSelectedIndex();
        if (staffIndex > 0)
        {
            staffId = staffList.get(staffIndex - 1).getId();
        }

        // the date, like 2026-10-25
        String dateMessage = "Enter a valid date as YYYY-MM-DD (year 1000 to 9999).";
        LocalDate date;
        try
        {
            date = LocalDate.parse(view.getDateField().getText().trim());
        }
        catch (DateTimeParseException ex)
        {
            throw new CustomException(dateMessage);
        }
        if (date.getYear() < 1000 || date.getYear() > 9999)
        {
            throw new CustomException(dateMessage);
        }

        // the time in 24-hour format, like 09:30
        LocalTime time;
        try
        {
            time = LocalTime.parse(view.getTimeField().getText().trim(), DateTimeFormatter.ofPattern("HH:mm"));
        }
        catch (DateTimeParseException ex)
        {
            throw new CustomException("Enter a valid 24-hour time as HH:mm, for example 09:30.");
        }

        String notes = view.getNotesArea().getText().trim();
        if (notes.length() > 1000)
        {
            throw new CustomException("Notes must have at most 1000 characters.");
        }

        Appointment appointment = new Appointment();
        appointment.setPetId(pet.getId());
        appointment.setServiceId(service.getId());
        appointment.setStaffId(staffId);
        appointment.setStart(date.atTime(time));
        appointment.setDuration(service.getDuration());     // a visit is as long as its service
        appointment.setStatus((String) view.getStatusComboBox().getSelectedItem());
        appointment.setNotes(notes);
        return appointment;
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(view, message, "Pawzenix", JOptionPane.ERROR_MESSAGE);
    }
}
