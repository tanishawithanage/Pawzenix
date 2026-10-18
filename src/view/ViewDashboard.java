package view;

public class ViewDashboard extends javax.swing.JPanel {

    public ViewDashboard() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        headingPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        headerActions = new javax.swing.JPanel();
        refreshButton = new javax.swing.JButton();
        pageScroll = new javax.swing.JScrollPane();
        dashboardContent = new javax.swing.JPanel();
        statsPanel = new javax.swing.JPanel();
        visitsCard = new javax.swing.JPanel();
        visitsTitle = new javax.swing.JLabel();
        visitsAmount = new javax.swing.JLabel();
        petsCard = new javax.swing.JPanel();
        petsTitle = new javax.swing.JLabel();
        petsAmount = new javax.swing.JLabel();
        collectedCard = new javax.swing.JPanel();
        collectedTitle = new javax.swing.JLabel();
        collectedAmount = new javax.swing.JLabel();
        outstandingCard = new javax.swing.JPanel();
        outstandingTitle = new javax.swing.JLabel();
        outstandingAmount = new javax.swing.JLabel();
        middlePanel = new javax.swing.JPanel();
        scheduleCard = new javax.swing.JPanel();
        scheduleHeader = new javax.swing.JPanel();
        scheduleTitle = new javax.swing.JLabel();
        bookButton = new javax.swing.JButton();
        scheduleScroll = new javax.swing.JScrollPane();
        scheduleTable = new javax.swing.JTable();
        lowerPanel = new javax.swing.JPanel();
        chartCard = new javax.swing.JPanel();
        chartTitle = new javax.swing.JLabel();
        chartPanel = new javax.swing.JPanel();
        chartPlaceholder = new javax.swing.JLabel();
        followCard = new javax.swing.JPanel();
        followupTitle = new javax.swing.JLabel();
        followupsPanel = new javax.swing.JPanel();
        followupPlaceholder = new javax.swing.JLabel();
        updatedLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 247, 245));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 20, 30));
        setPreferredSize(new java.awt.Dimension(1160, 820));
        setLayout(new java.awt.BorderLayout(0, 0));

        headerPanel.setBackground(new java.awt.Color(244, 247, 245));
        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 22, 0));
        headerPanel.setLayout(new java.awt.BorderLayout(0, 0));

        headingPanel.setBackground(new java.awt.Color(244, 247, 245));
        headingPanel.setLayout(new java.awt.GridLayout(2, 1, 0, 8));

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(24, 61, 53));
        titleLabel.setText("A little care. A happier day.");
        headingPanel.add(titleLabel);

        subtitleLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(97, 119, 110));
        subtitleLabel.setText("Pawzenix overview");
        headingPanel.add(subtitleLabel);

        headerPanel.add(headingPanel, java.awt.BorderLayout.CENTER);

        headerActions.setBackground(new java.awt.Color(244, 247, 245));
        headerActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        refreshButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(24, 117, 101));
        refreshButton.setText("Refresh");
        refreshButton.setFocusPainted(false);
        refreshButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh-teal.png"))); // NOI18N
        refreshButton.setIconTextGap(8);
        headerActions.add(refreshButton);

        headerPanel.add(headerActions, java.awt.BorderLayout.EAST);

        add(headerPanel, java.awt.BorderLayout.NORTH);

        pageScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        dashboardContent.setBackground(new java.awt.Color(244, 247, 245));
        dashboardContent.setPreferredSize(new java.awt.Dimension(1000, 750));
        dashboardContent.setLayout(new java.awt.BorderLayout(0, 22));

        statsPanel.setBackground(new java.awt.Color(244, 247, 245));
        statsPanel.setPreferredSize(new java.awt.Dimension(0, 122));
        statsPanel.setLayout(new java.awt.GridLayout(1, 4, 16, 0));

        visitsCard.setBackground(new java.awt.Color(255, 255, 255));
        visitsCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 18, 20, 18));
        visitsCard.setLayout(new java.awt.BorderLayout(0, 12));

        visitsTitle.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        visitsTitle.setForeground(new java.awt.Color(97, 119, 110));
        visitsTitle.setText("Visits today");
        visitsTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/appointments-teal.png"))); // NOI18N
        visitsTitle.setIconTextGap(8);
        visitsCard.add(visitsTitle, java.awt.BorderLayout.NORTH);

        visitsAmount.setFont(new java.awt.Font("SansSerif", 1, 32)); // NOI18N
        visitsAmount.setForeground(new java.awt.Color(24, 117, 101));
        visitsAmount.setText("—");
        visitsCard.add(visitsAmount, java.awt.BorderLayout.CENTER);

        statsPanel.add(visitsCard);

        petsCard.setBackground(new java.awt.Color(255, 255, 255));
        petsCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 18, 20, 18));
        petsCard.setLayout(new java.awt.BorderLayout(0, 12));

        petsTitle.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        petsTitle.setForeground(new java.awt.Color(97, 119, 110));
        petsTitle.setText("Registered pets");
        petsTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pets-purple.png"))); // NOI18N
        petsTitle.setIconTextGap(8);
        petsCard.add(petsTitle, java.awt.BorderLayout.NORTH);

        petsAmount.setFont(new java.awt.Font("SansSerif", 1, 32)); // NOI18N
        petsAmount.setForeground(new java.awt.Color(117, 100, 171));
        petsAmount.setText("—");
        petsCard.add(petsAmount, java.awt.BorderLayout.CENTER);

        statsPanel.add(petsCard);

        collectedCard.setBackground(new java.awt.Color(255, 255, 255));
        collectedCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 18, 20, 18));
        collectedCard.setLayout(new java.awt.BorderLayout(0, 12));

        collectedTitle.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        collectedTitle.setForeground(new java.awt.Color(97, 119, 110));
        collectedTitle.setText("Collected today");
        collectedTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/billing-teal.png"))); // NOI18N
        collectedTitle.setIconTextGap(8);
        collectedCard.add(collectedTitle, java.awt.BorderLayout.NORTH);

        collectedAmount.setFont(new java.awt.Font("SansSerif", 1, 23)); // NOI18N
        collectedAmount.setForeground(new java.awt.Color(24, 117, 101));
        collectedAmount.setText("—");
        collectedCard.add(collectedAmount, java.awt.BorderLayout.CENTER);

        statsPanel.add(collectedCard);

        outstandingCard.setBackground(new java.awt.Color(255, 255, 255));
        outstandingCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 18, 20, 18));
        outstandingCard.setLayout(new java.awt.BorderLayout(0, 12));

        outstandingTitle.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        outstandingTitle.setForeground(new java.awt.Color(97, 119, 110));
        outstandingTitle.setText("Outstanding");
        outstandingTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reports-purple.png"))); // NOI18N
        outstandingTitle.setIconTextGap(8);
        outstandingCard.add(outstandingTitle, java.awt.BorderLayout.NORTH);

        outstandingAmount.setFont(new java.awt.Font("SansSerif", 1, 23)); // NOI18N
        outstandingAmount.setForeground(new java.awt.Color(155, 99, 14));
        outstandingAmount.setText("—");
        outstandingCard.add(outstandingAmount, java.awt.BorderLayout.CENTER);

        statsPanel.add(outstandingCard);

        dashboardContent.add(statsPanel, java.awt.BorderLayout.NORTH);

        middlePanel.setBackground(new java.awt.Color(244, 247, 245));
        middlePanel.setLayout(new java.awt.BorderLayout(0, 20));

        scheduleCard.setBackground(new java.awt.Color(255, 255, 255));
        scheduleCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 22, 22, 22));
        scheduleCard.setLayout(new java.awt.BorderLayout(0, 14));

        scheduleHeader.setBackground(new java.awt.Color(255, 255, 255));
        scheduleHeader.setLayout(new java.awt.BorderLayout(0, 0));

        scheduleTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        scheduleTitle.setForeground(new java.awt.Color(24, 61, 53));
        scheduleTitle.setText("Today's appointments");
        scheduleHeader.add(scheduleTitle, java.awt.BorderLayout.CENTER);

        bookButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        bookButton.setBackground(new java.awt.Color(24, 117, 101));
        bookButton.setForeground(new java.awt.Color(255, 255, 255));
        bookButton.setText("Book a visit");
        bookButton.setFocusPainted(false);
        bookButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        bookButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus-white.png"))); // NOI18N
        bookButton.setIconTextGap(8);
        scheduleHeader.add(bookButton, java.awt.BorderLayout.EAST);

        scheduleCard.add(scheduleHeader, java.awt.BorderLayout.NORTH);

        scheduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Time", "Pet", "Owner", "Service", "Care provider", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scheduleTable.setRowHeight(47);
        scheduleTable.setFillsViewportHeight(true);
        scheduleScroll.setViewportView(scheduleTable);

        scheduleCard.add(scheduleScroll, java.awt.BorderLayout.CENTER);

        middlePanel.add(scheduleCard, java.awt.BorderLayout.CENTER);

        lowerPanel.setBackground(new java.awt.Color(244, 247, 245));
        lowerPanel.setPreferredSize(new java.awt.Dimension(0, 210));
        lowerPanel.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        chartCard.setBackground(new java.awt.Color(255, 255, 255));
        chartCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        chartCard.setLayout(new java.awt.BorderLayout(0, 8));

        chartTitle.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        chartTitle.setForeground(new java.awt.Color(24, 61, 53));
        chartTitle.setText("Visits over the last 7 days");
        chartCard.add(chartTitle, java.awt.BorderLayout.NORTH);

        chartPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartPanel.setLayout(new java.awt.BorderLayout(0, 0));

        chartPlaceholder.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        chartPlaceholder.setForeground(new java.awt.Color(97, 119, 110));
        chartPlaceholder.setText("Weekly visits chart");
        chartPanel.add(chartPlaceholder, java.awt.BorderLayout.CENTER);

        chartCard.add(chartPanel, java.awt.BorderLayout.CENTER);

        lowerPanel.add(chartCard);

        followCard.setBackground(new java.awt.Color(255, 255, 255));
        followCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        followCard.setLayout(new java.awt.BorderLayout(0, 12));

        followupTitle.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        followupTitle.setForeground(new java.awt.Color(24, 61, 53));
        followupTitle.setText("Follow-ups in the next 7 days");
        followCard.add(followupTitle, java.awt.BorderLayout.NORTH);

        followupsPanel.setBackground(new java.awt.Color(255, 255, 255));
        followupsPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 6));

        followupPlaceholder.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        followupPlaceholder.setForeground(new java.awt.Color(97, 119, 110));
        followupPlaceholder.setText("Follow-up reminders");
        followupsPanel.add(followupPlaceholder);

        followCard.add(followupsPanel, java.awt.BorderLayout.CENTER);

        lowerPanel.add(followCard);

        middlePanel.add(lowerPanel, java.awt.BorderLayout.SOUTH);

        dashboardContent.add(middlePanel, java.awt.BorderLayout.CENTER);

        updatedLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        updatedLabel.setForeground(new java.awt.Color(97, 119, 110));
        updatedLabel.setText("Dashboard summary");
        dashboardContent.add(updatedLabel, java.awt.BorderLayout.SOUTH);

        pageScroll.setViewportView(dashboardContent);

        add(pageScroll, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bookButton;
    private javax.swing.JPanel chartCard;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JLabel chartPlaceholder;
    private javax.swing.JLabel chartTitle;
    private javax.swing.JLabel collectedAmount;
    private javax.swing.JPanel collectedCard;
    private javax.swing.JLabel collectedTitle;
    private javax.swing.JPanel dashboardContent;
    private javax.swing.JPanel followCard;
    private javax.swing.JLabel followupPlaceholder;
    private javax.swing.JLabel followupTitle;
    private javax.swing.JPanel followupsPanel;
    private javax.swing.JPanel headerActions;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel headingPanel;
    private javax.swing.JPanel lowerPanel;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JLabel outstandingAmount;
    private javax.swing.JPanel outstandingCard;
    private javax.swing.JLabel outstandingTitle;
    private javax.swing.JScrollPane pageScroll;
    private javax.swing.JLabel petsAmount;
    private javax.swing.JPanel petsCard;
    private javax.swing.JLabel petsTitle;
    private javax.swing.JButton refreshButton;
    private javax.swing.JPanel scheduleCard;
    private javax.swing.JPanel scheduleHeader;
    private javax.swing.JScrollPane scheduleScroll;
    private javax.swing.JTable scheduleTable;
    private javax.swing.JLabel scheduleTitle;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel updatedLabel;
    private javax.swing.JLabel visitsAmount;
    private javax.swing.JPanel visitsCard;
    private javax.swing.JLabel visitsTitle;
    // End of variables declaration//GEN-END:variables
}
