package controller;

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.User;
import view.ViewAppointments;
import view.ViewBilling;
import view.ViewCustomers;
import view.ViewDashboard;
import view.ViewLogin;
import view.ViewMain;
import view.ViewPets;
import view.ViewReports;
import view.ViewServices;
import view.ViewStaff;

/**
 * Controller for the main window: the menu on the left opens the pages, and Sign out goes back to the login.
 */
public class MainController
{
    private final ViewMain view;

    public MainController(ViewMain view, User user)
    {
        this.view = view;
        view.getContentPanel().setLayout(new BorderLayout());

        // every menu button opens a new page (a new page reads fresh data from the database)
        view.getDashboardButton().addActionListener(e -> showDashboard());
        view.getCustomersButton().addActionListener(e -> showPage(new ViewCustomers()));
        view.getPetsButton().addActionListener(e -> showPage(new ViewPets()));
        view.getAppointmentsButton().addActionListener(e -> showPage(new ViewAppointments()));
        view.getBillingButton().addActionListener(e -> showPage(new ViewBilling()));
        view.getServicesButton().addActionListener(e -> showPage(new ViewServices()));
        view.getStaffButton().addActionListener(e -> showPage(new ViewStaff()));
        view.getReportsButton().addActionListener(e -> showPage(new ViewReports()));
        view.getLogoutButton().addActionListener(e -> logout());

        // show who is signed in
        if (user == null)
        {
            view.getUserLabel().setText("Development mode");
            view.getRoleLabel().setText("Login not enabled");
        }
        else
        {
            view.getUserLabel().setText(user.getUsername());
            view.getRoleLabel().setText("Signed in");
        }

        // the password is fixed in LoginController, so there is nothing to change
        view.getPasswordButton().setVisible(false);

        showDashboard();
    }

    // The Overview page. Its "Book a visit" button opens the Appointments page.
    private void showDashboard()
    {
        ViewDashboard dashboard = new ViewDashboard();
        dashboard.getBookButton().addActionListener(e -> showPage(new ViewAppointments()));
        showPage(dashboard);
    }

    // Puts a page in the middle of the window.
    private void showPage(JPanel page)
    {
        view.getContentPanel().removeAll();
        view.getContentPanel().add(page, BorderLayout.CENTER);
        view.getContentPanel().revalidate();
        view.getContentPanel().repaint();
    }

    private void logout()
    {
        int answer = JOptionPane.showConfirmDialog(view, "Sign out? Unsaved form changes will be lost.",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION)
        {
            return;
        }

        // close the main window and open the login window again
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null)
        {
            window.dispose();
        }
        ViewLogin login = new ViewLogin();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }
}
