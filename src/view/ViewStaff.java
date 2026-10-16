package view;

public class ViewStaff extends javax.swing.JPanel {

    public ViewStaff() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        headingPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        recordsCard = new javax.swing.JPanel();
        toolsPanel = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        toolbar = new javax.swing.JPanel();
        refreshButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        recordsScroll = new javax.swing.JScrollPane();
        recordsTable = new javax.swing.JTable();
        noticeLabel = new javax.swing.JLabel();

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
        titleLabel.setText("Care team");
        headingPanel.add(titleLabel);

        subtitleLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(97, 119, 110));
        subtitleLabel.setText("The people behind every happy visit.");
        headingPanel.add(subtitleLabel);

        headerPanel.add(headingPanel, java.awt.BorderLayout.CENTER);

        add(headerPanel, java.awt.BorderLayout.NORTH);

        recordsCard.setBackground(new java.awt.Color(255, 255, 255));
        recordsCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 22, 22, 22));
        recordsCard.setLayout(new java.awt.BorderLayout(0, 18));

        toolsPanel.setBackground(new java.awt.Color(255, 255, 255));
        toolsPanel.setLayout(new java.awt.BorderLayout(16, 0));

        searchField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        searchField.setPreferredSize(new java.awt.Dimension(230, 40));
        searchField.setToolTipText("Search by name or related details");
        toolsPanel.add(searchField, java.awt.BorderLayout.CENTER);

        toolbar.setBackground(new java.awt.Color(255, 255, 255));
        toolbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));

        refreshButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(24, 117, 101));
        refreshButton.setText("Refresh");
        refreshButton.setFocusPainted(false);
        refreshButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/refresh-teal.png"))); // NOI18N
        refreshButton.setIconTextGap(8);
        toolbar.add(refreshButton);

        editButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        editButton.setForeground(new java.awt.Color(24, 117, 101));
        editButton.setText("Edit");
        editButton.setFocusPainted(false);
        editButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/edit-teal.png"))); // NOI18N
        editButton.setIconTextGap(8);
        toolbar.add(editButton);

        deleteButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(211, 94, 78));
        deleteButton.setText("Delete");
        deleteButton.setFocusPainted(false);
        deleteButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/trash-coral.png"))); // NOI18N
        deleteButton.setIconTextGap(8);
        toolbar.add(deleteButton);

        addButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        addButton.setBackground(new java.awt.Color(24, 117, 101));
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setText("Add new");
        addButton.setFocusPainted(false);
        addButton.setMargin(new java.awt.Insets(10, 12, 10, 12));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/pawzenix/icons/plus-white.png"))); // NOI18N
        addButton.setIconTextGap(8);
        toolbar.add(addButton);

        toolsPanel.add(toolbar, java.awt.BorderLayout.EAST);

        recordsCard.add(toolsPanel, java.awt.BorderLayout.NORTH);

        recordsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Team member", "Role", "Mobile number", "Availability"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recordsTable.setRowHeight(47);
        recordsTable.setFillsViewportHeight(true);
        recordsScroll.setViewportView(recordsTable);

        recordsCard.add(recordsScroll, java.awt.BorderLayout.CENTER);

        noticeLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        noticeLabel.setForeground(new java.awt.Color(97, 119, 110));
        noticeLabel.setText("No records yet.");
        recordsCard.add(noticeLabel, java.awt.BorderLayout.SOUTH);

        add(recordsCard, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel headingPanel;
    private javax.swing.JLabel noticeLabel;
    private javax.swing.JPanel recordsCard;
    private javax.swing.JScrollPane recordsScroll;
    private javax.swing.JTable recordsTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel toolbar;
    private javax.swing.JPanel toolsPanel;
    // End of variables declaration//GEN-END:variables
}
