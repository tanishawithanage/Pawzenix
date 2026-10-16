package view;

public class ViewMain extends javax.swing.JPanel {

    public ViewMain() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidebarPanel = new javax.swing.JPanel();
        brandPanel = new javax.swing.JPanel();
        brandLabel = new javax.swing.JLabel();
        taglineLabel = new javax.swing.JLabel();
        workspaceLabel = new javax.swing.JLabel();
        navigationArea = new javax.swing.JPanel();
        navigationPanel = new javax.swing.JPanel();
        dashboardButton = new javax.swing.JButton();
        customersButton = new javax.swing.JButton();
        petsButton = new javax.swing.JButton();
        appointmentsButton = new javax.swing.JButton();
        treatmentsButton = new javax.swing.JButton();
        billingButton = new javax.swing.JButton();
        servicesButton = new javax.swing.JButton();
        staffButton = new javax.swing.JButton();
        reportsButton = new javax.swing.JButton();
        accountPanel = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        passwordButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(244, 247, 245));
        setPreferredSize(new java.awt.Dimension(1440, 860));
        setLayout(new java.awt.BorderLayout(0, 0));

        sidebarPanel.setBackground(new java.awt.Color(20, 62, 53));
        sidebarPanel.setPreferredSize(new java.awt.Dimension(240, 0));
        sidebarPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 18, 20, 18));
        sidebarPanel.setLayout(new java.awt.BorderLayout(0, 14));

        brandPanel.setBackground(new java.awt.Color(20, 62, 53));
        brandPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 15, 0));
        brandPanel.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        brandLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        brandLabel.setForeground(new java.awt.Color(255, 255, 255));
        brandLabel.setText("pawzenix");
        brandPanel.add(brandLabel);

        taglineLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        taglineLabel.setForeground(new java.awt.Color(169, 198, 183));
        taglineLabel.setText("Pet care, thoughtfully.");
        brandPanel.add(taglineLabel);

        workspaceLabel.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        workspaceLabel.setForeground(new java.awt.Color(145, 176, 158));
        workspaceLabel.setText("WORKSPACE");
        brandPanel.add(workspaceLabel);

        sidebarPanel.add(brandPanel, java.awt.BorderLayout.NORTH);

        navigationArea.setBackground(new java.awt.Color(20, 62, 53));
        navigationArea.setLayout(new java.awt.BorderLayout(0, 0));

        navigationPanel.setBackground(new java.awt.Color(20, 62, 53));
        navigationPanel.setLayout(new java.awt.GridLayout(9, 1, 0, 3));

        dashboardButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        dashboardButton.setBackground(new java.awt.Color(20, 62, 53));
        dashboardButton.setForeground(new java.awt.Color(213, 227, 219));
        dashboardButton.setText("Overview");
        dashboardButton.setFocusPainted(false);
        dashboardButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        dashboardButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dashboardButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        dashboardButton.setPreferredSize(new java.awt.Dimension(204, 42));
        dashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/dashboard-sage.png"))); // NOI18N
        dashboardButton.setIconTextGap(8);
        navigationPanel.add(dashboardButton);

        customersButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        customersButton.setBackground(new java.awt.Color(20, 62, 53));
        customersButton.setForeground(new java.awt.Color(213, 227, 219));
        customersButton.setText("Customers");
        customersButton.setFocusPainted(false);
        customersButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        customersButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        customersButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        customersButton.setPreferredSize(new java.awt.Dimension(204, 42));
        customersButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/customers-sage.png"))); // NOI18N
        customersButton.setIconTextGap(8);
        navigationPanel.add(customersButton);

        petsButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        petsButton.setBackground(new java.awt.Color(20, 62, 53));
        petsButton.setForeground(new java.awt.Color(213, 227, 219));
        petsButton.setText("Pets");
        petsButton.setFocusPainted(false);
        petsButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        petsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        petsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        petsButton.setPreferredSize(new java.awt.Dimension(204, 42));
        petsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/pets-sage.png"))); // NOI18N
        petsButton.setIconTextGap(8);
        navigationPanel.add(petsButton);

        appointmentsButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        appointmentsButton.setBackground(new java.awt.Color(20, 62, 53));
        appointmentsButton.setForeground(new java.awt.Color(213, 227, 219));
        appointmentsButton.setText("Appointments");
        appointmentsButton.setFocusPainted(false);
        appointmentsButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        appointmentsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        appointmentsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        appointmentsButton.setPreferredSize(new java.awt.Dimension(204, 42));
        appointmentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/appointments-sage.png"))); // NOI18N
        appointmentsButton.setIconTextGap(8);
        navigationPanel.add(appointmentsButton);

        treatmentsButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        treatmentsButton.setBackground(new java.awt.Color(20, 62, 53));
        treatmentsButton.setForeground(new java.awt.Color(213, 227, 219));
        treatmentsButton.setText("Treatments & care");
        treatmentsButton.setFocusPainted(false);
        treatmentsButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        treatmentsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        treatmentsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        treatmentsButton.setPreferredSize(new java.awt.Dimension(204, 42));
        treatmentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/treatments-sage.png"))); // NOI18N
        treatmentsButton.setIconTextGap(8);
        navigationPanel.add(treatmentsButton);

        billingButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        billingButton.setBackground(new java.awt.Color(20, 62, 53));
        billingButton.setForeground(new java.awt.Color(213, 227, 219));
        billingButton.setText("Billing & payments");
        billingButton.setFocusPainted(false);
        billingButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        billingButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        billingButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        billingButton.setPreferredSize(new java.awt.Dimension(204, 42));
        billingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/billing-sage.png"))); // NOI18N
        billingButton.setIconTextGap(8);
        navigationPanel.add(billingButton);

        servicesButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        servicesButton.setBackground(new java.awt.Color(20, 62, 53));
        servicesButton.setForeground(new java.awt.Color(213, 227, 219));
        servicesButton.setText("Services");
        servicesButton.setFocusPainted(false);
        servicesButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        servicesButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        servicesButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        servicesButton.setPreferredSize(new java.awt.Dimension(204, 42));
        servicesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/services-sage.png"))); // NOI18N
        servicesButton.setIconTextGap(8);
        navigationPanel.add(servicesButton);

        staffButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        staffButton.setBackground(new java.awt.Color(20, 62, 53));
        staffButton.setForeground(new java.awt.Color(213, 227, 219));
        staffButton.setText("Care team");
        staffButton.setFocusPainted(false);
        staffButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        staffButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        staffButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        staffButton.setPreferredSize(new java.awt.Dimension(204, 42));
        staffButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/staff-sage.png"))); // NOI18N
        staffButton.setIconTextGap(8);
        navigationPanel.add(staffButton);

        reportsButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        reportsButton.setBackground(new java.awt.Color(20, 62, 53));
        reportsButton.setForeground(new java.awt.Color(213, 227, 219));
        reportsButton.setText("Reports");
        reportsButton.setFocusPainted(false);
        reportsButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        reportsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        reportsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 8));
        reportsButton.setPreferredSize(new java.awt.Dimension(204, 42));
        reportsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/reports-sage.png"))); // NOI18N
        reportsButton.setIconTextGap(8);
        navigationPanel.add(reportsButton);

        navigationArea.add(navigationPanel, java.awt.BorderLayout.NORTH);

        sidebarPanel.add(navigationArea, java.awt.BorderLayout.CENTER);

        accountPanel.setBackground(new java.awt.Color(20, 62, 53));
        accountPanel.setLayout(new java.awt.GridLayout(4, 1, 0, 7));

        userLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        userLabel.setForeground(new java.awt.Color(255, 255, 255));
        userLabel.setText("Administrator");
        accountPanel.add(userLabel);

        roleLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        roleLabel.setForeground(new java.awt.Color(170, 197, 181));
        roleLabel.setText("Administrator");
        accountPanel.add(roleLabel);

        passwordButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        passwordButton.setForeground(new java.awt.Color(97, 119, 110));
        passwordButton.setText("Change password");
        passwordButton.setFocusPainted(false);
        passwordButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        passwordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/settings-teal.png"))); // NOI18N
        passwordButton.setIconTextGap(8);
        accountPanel.add(passwordButton);

        logoutButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(97, 119, 110));
        logoutButton.setText("Sign out");
        logoutButton.setFocusPainted(false);
        logoutButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/logout-teal.png"))); // NOI18N
        logoutButton.setIconTextGap(8);
        accountPanel.add(logoutButton);

        sidebarPanel.add(accountPanel, java.awt.BorderLayout.SOUTH);

        add(sidebarPanel, java.awt.BorderLayout.WEST);

        contentPanel.setBackground(new java.awt.Color(244, 247, 245));
        contentPanel.setLayout(new java.awt.CardLayout());
        add(contentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel accountPanel;
    private javax.swing.JButton appointmentsButton;
    private javax.swing.JButton billingButton;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JPanel brandPanel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton customersButton;
    private javax.swing.JButton dashboardButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel navigationArea;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JButton passwordButton;
    private javax.swing.JButton petsButton;
    private javax.swing.JButton reportsButton;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JButton servicesButton;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JButton staffButton;
    private javax.swing.JLabel taglineLabel;
    private javax.swing.JButton treatmentsButton;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel workspaceLabel;
    // End of variables declaration//GEN-END:variables
}
