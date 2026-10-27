package view;

/**
 *
 * @author TANISHA SANJANA
 */
public class ViewLogin extends javax.swing.JFrame {

    public ViewLogin() {
        initComponents();
        if (!java.beans.Beans.isDesignTime()) {
            new controller.LoginController(this);
        }
    }

    // Controller hooks stay OUTSIDE GUI Builder guarded sections.
    public javax.swing.JTextField getUsernameField() { return usernameField; }
    public javax.swing.JPasswordField getPasswordField() { return passwordField; }
    public javax.swing.JButton getSignInButton() { return signInButton; }
    public javax.swing.JLabel getErrorLabel() { return errorLabel; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        welcomePanel = new javax.swing.JPanel();
        brandLabel = new javax.swing.JLabel();
        welcomeCopy = new javax.swing.JPanel();
        pawLabel = new javax.swing.JLabel();
        welcomeTitle = new javax.swing.JLabel();
        welcomeSubtitle = new javax.swing.JLabel();
        brandFooter = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        loginFields = new javax.swing.JPanel();
        eyebrowLabel = new javax.swing.JLabel();
        welcomeBackLabel = new javax.swing.JLabel();
        loginSubtitle = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        errorLabel = new javax.swing.JLabel();
        signInButton = new javax.swing.JButton();
        loginFooter = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pawzenix • Sign in");
        setBackground(new java.awt.Color(244, 247, 245));
        setMinimumSize(new java.awt.Dimension(980, 660));
        setPreferredSize(new java.awt.Dimension(1120, 740));
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        welcomePanel.setBackground(new java.awt.Color(20, 62, 53));
        welcomePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(42, 44, 36, 35));
        welcomePanel.setLayout(new java.awt.BorderLayout(0, 20));

        brandLabel.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        brandLabel.setForeground(new java.awt.Color(255, 255, 255));
        brandLabel.setText("pawzenix");
        welcomePanel.add(brandLabel, java.awt.BorderLayout.NORTH);

        welcomeCopy.setBackground(new java.awt.Color(20, 62, 53));
        welcomeCopy.setLayout(new java.awt.GridBagLayout());

        pawLabel.setFont(new java.awt.Font("SansSerif", 1, 120)); // NOI18N
        pawLabel.setForeground(new java.awt.Color(185, 221, 200));
        pawLabel.setPreferredSize(new java.awt.Dimension(150, 150));
        pawLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/paw-sage.png"))); // NOI18N
        pawLabel.setIconTextGap(8);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
        welcomeCopy.add(pawLabel, gridBagConstraints);

        welcomeTitle.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        welcomeTitle.setForeground(new java.awt.Color(255, 255, 255));
        welcomeTitle.setText("<html>A calmer day for you.<br>A happier day for them.</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        welcomeCopy.add(welcomeTitle, gridBagConstraints);

        welcomeSubtitle.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        welcomeSubtitle.setForeground(new java.awt.Color(180, 207, 193));
        welcomeSubtitle.setText("<html>Appointments, care and payments<br>in one place.</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        welcomeCopy.add(welcomeSubtitle, gridBagConstraints);

        welcomePanel.add(welcomeCopy, java.awt.BorderLayout.CENTER);

        brandFooter.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        brandFooter.setForeground(new java.awt.Color(180, 207, 193));
        brandFooter.setText("BUILT AROUND BETTER CARE");
        welcomePanel.add(brandFooter, java.awt.BorderLayout.SOUTH);

        getContentPane().add(welcomePanel);

        loginPanel.setBackground(new java.awt.Color(255, 255, 255));
        loginPanel.setLayout(new java.awt.GridBagLayout());

        loginFields.setBackground(new java.awt.Color(255, 255, 255));
        loginFields.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 35, 25, 35));
        loginFields.setLayout(new java.awt.GridBagLayout());

        eyebrowLabel.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        eyebrowLabel.setForeground(new java.awt.Color(24, 117, 101));
        eyebrowLabel.setText("YOUR CARE CENTRE, CONNECTED");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(eyebrowLabel, gridBagConstraints);

        welcomeBackLabel.setFont(new java.awt.Font("SansSerif", 1, 32)); // NOI18N
        welcomeBackLabel.setForeground(new java.awt.Color(24, 61, 53));
        welcomeBackLabel.setText("Welcome back");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(welcomeBackLabel, gridBagConstraints);

        loginSubtitle.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        loginSubtitle.setForeground(new java.awt.Color(97, 119, 110));
        loginSubtitle.setText("Good care starts with a calm, organised day.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 24, 0);
        loginFields.add(loginSubtitle, gridBagConstraints);

        usernameLabel.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(24, 61, 53));
        usernameLabel.setText("Username");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(usernameLabel, gridBagConstraints);

        usernameField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        usernameField.setPreferredSize(new java.awt.Dimension(230, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(usernameField, gridBagConstraints);

        passwordLabel.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(24, 61, 53));
        passwordLabel.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(passwordLabel, gridBagConstraints);

        passwordField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        passwordField.setPreferredSize(new java.awt.Dimension(320, 44));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(passwordField, gridBagConstraints);

        errorLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(211, 94, 78));
        errorLabel.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(errorLabel, gridBagConstraints);

        signInButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        signInButton.setBackground(new java.awt.Color(24, 117, 101));
        signInButton.setForeground(new java.awt.Color(255, 255, 255));
        signInButton.setText("Sign in to Pawzenix");
        signInButton.setFocusPainted(false);
        signInButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        signInButton.setPreferredSize(new java.awt.Dimension(320, 46));
        signInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/check-white.png"))); // NOI18N
        signInButton.setIconTextGap(8);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 24, 0);
        loginFields.add(signInButton, gridBagConstraints);

        loginFooter.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        loginFooter.setForeground(new java.awt.Color(97, 119, 110));
        loginFooter.setText("Pet care & veterinary centre management");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        loginFields.add(loginFooter, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        loginPanel.add(loginFields, gridBagConstraints);

        getContentPane().add(loginPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel brandFooter;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel eyebrowLabel;
    private javax.swing.JPanel loginFields;
    private javax.swing.JLabel loginFooter;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel loginSubtitle;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel pawLabel;
    private javax.swing.JButton signInButton;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel welcomeBackLabel;
    private javax.swing.JPanel welcomeCopy;
    private javax.swing.JPanel welcomePanel;
    private javax.swing.JLabel welcomeSubtitle;
    private javax.swing.JLabel welcomeTitle;
    // End of variables declaration//GEN-END:variables
}
