package controller;

import java.util.Arrays;
import javax.swing.JFrame;
import model.User;
import view.ViewLogin;
import view.ViewMain;

/**
 * Controller for the sign-in window.
 * The login is fixed inside the program (no database lookup):
 *     username: admin      password: 123
 */
public class LoginController
{
    // The only account that can sign in. Change these two lines to change the login.
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";

    private final ViewLogin view;

    public LoginController(ViewLogin view)
    {
        this.view = view;

        view.getSignInButton().addActionListener(e -> signIn());
        view.getPasswordField().addActionListener(e -> signIn());      // the Enter key in the password box
        view.getRootPane().setDefaultButton(view.getSignInButton());
        view.getErrorLabel().setText(" ");
    }

    private void signIn()
    {
        String username = view.getUsernameField().getText().trim();
        char[] password = view.getPasswordField().getPassword();

        if (username.isEmpty() || password.length == 0)
        {
            view.getErrorLabel().setText("Enter your username and password.");
        }
        else if (username.equals(USERNAME) && new String(password).equals(PASSWORD))
        {
            openMainWindow();
        }
        else
        {
            view.getErrorLabel().setText("Incorrect username or password.");
            view.getPasswordField().setText("");
        }

        // do not keep the typed password in memory
        Arrays.fill(password, '\0');
    }

    // Closes the sign-in window and opens the main window.
    private void openMainWindow()
    {
        JFrame frame = new JFrame("Pawzenix");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ViewMain(new User(0, USERNAME)));
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.dispose();
    }
}
