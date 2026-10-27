package controller;

import model.User;
import view.ViewLogin;
import view.ViewMain;
import javax.swing.*;
import java.util.Arrays;

/**
 *
 * @author TANISHA SANJANA
 */

public class LoginController {
    private final ViewLogin view;
    private boolean signingIn;

    public LoginController(ViewLogin view) {
        this.view=view;
        view.getSignInButton().addActionListener(e->signIn());
        view.getPasswordField().addActionListener(e->signIn());
        view.getRootPane().setDefaultButton(view.getSignInButton());
        view.getErrorLabel().setText(" ");
    }

    private void signIn() {
        if (signingIn) return;
        String username=view.getUsernameField().getText().trim();
        char[] password=view.getPasswordField().getPassword();
        if (username.isEmpty() || password.length==0) {
            Arrays.fill(password,'\0'); view.getErrorLabel().setText("Enter your username and password."); return;
        }
        signingIn=true; view.getSignInButton().setEnabled(false); view.getErrorLabel().setText("Signing in...");
        new SwingWorker<User,Void>() {
            @Override protected User doInBackground() throws Exception {
                try { return User.authenticate(username,password); }
                finally { Arrays.fill(password,'\0'); }
            }
            @Override protected void done() {
                try {
                    User user=get();
                    if (user==null) { view.getErrorLabel().setText("Incorrect username or password."); return; }
                    JFrame frame=new JFrame("Pawzenix");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setContentPane(new ViewMain(user));
                    frame.setSize(1280,800); frame.setLocationRelativeTo(null); frame.setVisible(true);
                    view.dispose();
                } catch (Exception e) {
                    view.getErrorLabel().setText("Sign-in failed. Check the database connection.");
                    Throwable cause=e.getCause();
                    Input.error(view,cause instanceof Exception?(Exception)cause:e);
                } finally {
                    signingIn=false; view.getSignInButton().setEnabled(true); view.getPasswordField().setText("");
                }
            }
        }.execute();
    }
}
