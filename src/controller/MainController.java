package controller;

import model.User;
import view.*;
import java.awt.BorderLayout;
import java.awt.Window;
import java.util.Arrays;
import java.util.function.Supplier;
import javax.swing.*;

/**
 *
 * @author TANISHA SANJANA
 */

public class MainController {
    private final ViewMain view;
    private final User user;

    public MainController(ViewMain view,User user) {
        this.view=view; this.user=user;
        view.getContentPanel().setLayout(new BorderLayout());
        bind(view.getDashboardButton(),ViewDashboard::new);
        bind(view.getCustomersButton(),ViewCustomers::new);
        bind(view.getPetsButton(),ViewPets::new);
        bind(view.getAppointmentsButton(),ViewAppointments::new);
        bind(view.getBillingButton(),ViewBilling::new);
        bind(view.getServicesButton(),ViewServices::new);
        bind(view.getStaffButton(),ViewStaff::new);
        bind(view.getReportsButton(),ViewReports::new);
        view.getUserLabel().setText(user==null?"Development mode":user.getUsername());
        view.getRoleLabel().setText(user==null?"Login not enabled":"Signed in");
        view.getPasswordButton().setEnabled(user!=null);
        view.getPasswordButton().addActionListener(e->changePassword());
        view.getLogoutButton().addActionListener(e->logout());
        show(new ViewDashboard());
    }

    private void bind(JButton button,Supplier<JPanel> page) {
        for (java.awt.event.ActionListener listener:button.getActionListeners()) button.removeActionListener(listener);
        button.addActionListener(e->show(page.get()));
    }

    private void show(JPanel page) {
        if (page instanceof ViewDashboard) {
            ((ViewDashboard)page).getBookButton().addActionListener(e->show(new ViewAppointments()));
        }
        view.getContentPanel().removeAll(); view.getContentPanel().add(page,BorderLayout.CENTER);
        view.getContentPanel().revalidate(); view.getContentPanel().repaint();
    }

    private void logout() {
        if (!Input.confirm(view,"Sign out? Unsaved form changes will be lost.")) return;
        Window window=SwingUtilities.getWindowAncestor(view);
        if (window!=null) window.dispose();
        ViewLogin login=new ViewLogin(); login.setLocationRelativeTo(null); login.setVisible(true);
    }

    private void changePassword() {
        if (user==null) return;
        JPasswordField current=new JPasswordField(),replacement=new JPasswordField(),confirm=new JPasswordField();
        Object[] fields={"Current password",current,"New password (8 to 128 characters)",replacement,"Confirm new password",confirm};
        if (JOptionPane.showConfirmDialog(view,fields,"Change password",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        char[] old=current.getPassword(),next=replacement.getPassword(),again=confirm.getPassword();
        current.setText(""); replacement.setText(""); confirm.setText("");
        try {
            if (!Arrays.equals(next,again)) throw new IllegalArgumentException("New passwords do not match.");
            user.changePassword(old,next); JOptionPane.showMessageDialog(view,"Password changed.");
        } catch (Exception e) { Input.error(view,e); }
        finally { Arrays.fill(old,'\0'); Arrays.fill(next,'\0'); Arrays.fill(again,'\0'); }
    }
}
