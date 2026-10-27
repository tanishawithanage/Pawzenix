package view;

/**
 *
 * @author TANISHA SANJANA
 */
public class ViewServices extends javax.swing.JPanel {
    public ViewServices() {
        initComponents();
        configureTable();
        if (!java.beans.Beans.isDesignTime()) {
            new controller.ServiceController(this);
        }
    }

    // Controller hooks stay OUTSIDE GUI Builder guarded sections.
    public javax.swing.JTextField getIdField() { return idField; }
    public javax.swing.JTextField getNameField() { return nameField; }
    public javax.swing.JComboBox<String> getCategoryComboBox() { return categoryComboBox; }
    public javax.swing.JTextField getDurationField() { return durationField; }
    public javax.swing.JButton getAddButton() { return addButton; }
    public javax.swing.JButton getEditButton() { return editButton; }
    public javax.swing.JButton getDeleteButton() { return deleteButton; }
    public javax.swing.JButton getClearButton() { return clearButton; }
    public javax.swing.JButton getSearchButton() { return searchButton; }
    public javax.swing.JButton getRefreshButton() { return refreshButton; }

    /** Clear the editor locally. This method does not delete a database record. */
    public void clearForm() {
        recordsTable.clearSelection();
        idField.setText("New");
        nameField.setText("");
        if (categoryComboBox.getItemCount() > 0) categoryComboBox.setSelectedIndex(0);
        durationField.setText("");
    }

    public void setStatus(String message) { noticeLabel.setText(message); }

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearForm();
    }//GEN-LAST:event_clearButtonActionPerformed


    /** Theme only: safe to keep outside the generated layout. */
    private void configureTable() {
        recordsTable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
        recordsTable.getTableHeader().setBackground(new java.awt.Color(0xE8F1EC));
        recordsTable.getTableHeader().setForeground(new java.awt.Color(0x183D35));
        recordsTable.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 34));
        recordsTable.getTableHeader().setReorderingAllowed(false);
        recordsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        recordsScroll.getViewport().setBackground(java.awt.Color.WHITE);
        recordsScroll.setColumnHeaderView(recordsTable.getTableHeader());
        // Do not set a fixed preferred size on the JTable: the viewport fills the panel.
        recordsTable.setRowSorter(new javax.swing.table.TableRowSorter<javax.swing.table.TableModel>(recordsTable.getModel()));
        recordsTable.getColumnModel().getColumn(0).setPreferredWidth(55);
    }

    public javax.swing.JTable getRecordsTable() { return recordsTable; }
    public javax.swing.JTextField getSearchField() { return searchField; }

    /** Search filters rows already loaded in the table; it does not query MySQL. */
    private void applySearch() {
        javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter =
            new javax.swing.table.TableRowSorter<javax.swing.table.TableModel>(recordsTable.getModel());
        String query = searchField.getText().trim();
        if (!query.isEmpty()) {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?iu)" + java.util.regex.Pattern.quote(query)));
        }
        recordsTable.setRowSorter(sorter);
        recordsTable.clearSelection();
        setStatus(recordsTable.getRowCount() + " matching record(s). Select a row to update or delete.");
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        applySearch();
    }//GEN-LAST:event_searchButtonActionPerformed

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        headingPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        brandTagLabel = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        editorPanel = new javax.swing.JPanel();
        inputCard = new javax.swing.JPanel();
        cardHeadingPanel = new javax.swing.JPanel();
        sectionLabel = new javax.swing.JLabel();
        requiredLabel = new javax.swing.JLabel();
        inputGrid = new javax.swing.JPanel();
        idFieldPanel = new javax.swing.JPanel();
        idFieldLabel = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        nameFieldPanel = new javax.swing.JPanel();
        nameFieldLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        categoryComboBoxPanel = new javax.swing.JPanel();
        categoryComboBoxLabel = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox<>();
        durationFieldPanel = new javax.swing.JPanel();
        durationFieldLabel = new javax.swing.JLabel();
        durationField = new javax.swing.JTextField();
        emptyFieldPanel0 = new javax.swing.JPanel();
        emptyFieldPanel1 = new javax.swing.JPanel();
        toolbar = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        recordsCard = new javax.swing.JPanel();
        toolsPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchActionsPanel = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        recordsScroll = new javax.swing.JScrollPane();
        recordsTable = new javax.swing.JTable();
        noticeLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 247, 245));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 20, 24));
        setMinimumSize(new java.awt.Dimension(880, 720));
        setPreferredSize(new java.awt.Dimension(1060, 800));
        setLayout(new java.awt.BorderLayout(0, 0));

        headerPanel.setBackground(new java.awt.Color(244, 247, 245));
        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 18, 0));
        headerPanel.setLayout(new java.awt.BorderLayout(12, 0));

        headingPanel.setBackground(new java.awt.Color(244, 247, 245));
        headingPanel.setLayout(new java.awt.BorderLayout(0, 6));

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 27)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(24, 61, 53));
        titleLabel.setText("Services");
        headingPanel.add(titleLabel, java.awt.BorderLayout.NORTH);

        subtitleLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(97, 119, 110));
        subtitleLabel.setText("A simple menu of veterinary and pet care services.");
        headingPanel.add(subtitleLabel, java.awt.BorderLayout.CENTER);

        headerPanel.add(headingPanel, java.awt.BorderLayout.CENTER);

        brandTagLabel.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        brandTagLabel.setForeground(new java.awt.Color(24, 117, 101));
        brandTagLabel.setText("PAWZENIX  /  PET CARE");
        headerPanel.add(brandTagLabel, java.awt.BorderLayout.EAST);

        add(headerPanel, java.awt.BorderLayout.NORTH);

        bodyPanel.setBackground(new java.awt.Color(244, 247, 245));
        bodyPanel.setLayout(new java.awt.BorderLayout(0, 0));

        editorPanel.setBackground(new java.awt.Color(244, 247, 245));
        editorPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 16, 0));
        editorPanel.setLayout(new java.awt.BorderLayout(0, 12));

        inputCard.setBackground(new java.awt.Color(255, 255, 255));
        inputCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 18, 18, 18));
        inputCard.setLayout(new java.awt.BorderLayout(0, 14));

        cardHeadingPanel.setBackground(new java.awt.Color(255, 255, 255));
        cardHeadingPanel.setLayout(new java.awt.BorderLayout(8, 0));

        sectionLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        sectionLabel.setForeground(new java.awt.Color(24, 61, 53));
        sectionLabel.setText("Service details");
        cardHeadingPanel.add(sectionLabel, java.awt.BorderLayout.CENTER);

        requiredLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        requiredLabel.setForeground(new java.awt.Color(97, 119, 110));
        requiredLabel.setText("* Required");
        cardHeadingPanel.add(requiredLabel, java.awt.BorderLayout.EAST);

        inputCard.add(cardHeadingPanel, java.awt.BorderLayout.NORTH);

        inputGrid.setBackground(new java.awt.Color(255, 255, 255));
        inputGrid.setLayout(new java.awt.GridLayout(2, 3, 18, 12));

        idFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        idFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        idFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        idFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        idFieldLabel.setText("Service ID");
        idFieldLabel.setLabelFor(idField);
        idFieldPanel.add(idFieldLabel, java.awt.BorderLayout.NORTH);

        idField.setForeground(new java.awt.Color(24, 61, 53));
        idField.setToolTipText("New for a new record; set by the controller when selecting a saved row");
        idField.setText("New");
        idField.setColumns(16);
        idField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        idField.setEditable(false);
        idField.setBackground(new java.awt.Color(232, 241, 236));
        idField.setPreferredSize(new java.awt.Dimension(200, 34));
        idField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        idFieldPanel.add(idField, java.awt.BorderLayout.CENTER);

        inputGrid.add(idFieldPanel);

        nameFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        nameFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        nameFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        nameFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        nameFieldLabel.setText("Service name *");
        nameFieldLabel.setLabelFor(nameField);
        nameFieldPanel.add(nameFieldLabel, java.awt.BorderLayout.NORTH);

        nameField.setForeground(new java.awt.Color(24, 61, 53));
        nameField.setBackground(new java.awt.Color(250, 252, 250));
        nameField.setToolTipText("Service name — up to 100 characters");
        nameField.setColumns(16);
        nameField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        nameField.setPreferredSize(new java.awt.Dimension(200, 34));
        nameField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        nameFieldPanel.add(nameField, java.awt.BorderLayout.CENTER);

        inputGrid.add(nameFieldPanel);

        categoryComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        categoryComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        categoryComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        categoryComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        categoryComboBoxLabel.setText("Category *");
        categoryComboBoxLabel.setLabelFor(categoryComboBox);
        categoryComboBoxPanel.add(categoryComboBoxLabel, java.awt.BorderLayout.NORTH);

        categoryComboBox.setForeground(new java.awt.Color(24, 61, 53));
        categoryComboBox.setBackground(new java.awt.Color(250, 252, 250));
        categoryComboBox.setToolTipText("Choose a service category");
        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select category…", "Veterinary", "Vaccination", "Grooming", "Boarding", "Other" }));
        categoryComboBox.setPreferredSize(new java.awt.Dimension(200, 34));
        categoryComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        categoryComboBoxPanel.add(categoryComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(categoryComboBoxPanel);

        durationFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        durationFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        durationFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        durationFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        durationFieldLabel.setText("Minutes to complete *");
        durationFieldLabel.setLabelFor(durationField);
        durationFieldPanel.add(durationFieldLabel, java.awt.BorderLayout.NORTH);

        durationField.setForeground(new java.awt.Color(24, 61, 53));
        durationField.setBackground(new java.awt.Color(250, 252, 250));
        durationField.setToolTipText("Positive whole number, for example 30");
        durationField.setColumns(16);
        durationField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        durationField.setPreferredSize(new java.awt.Dimension(200, 34));
        durationField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        durationFieldPanel.add(durationField, java.awt.BorderLayout.CENTER);

        inputGrid.add(durationFieldPanel);

        emptyFieldPanel0.setBackground(new java.awt.Color(255, 255, 255));
        emptyFieldPanel0.setLayout(new java.awt.BorderLayout(0, 0));
        inputGrid.add(emptyFieldPanel0);

        emptyFieldPanel1.setBackground(new java.awt.Color(255, 255, 255));
        emptyFieldPanel1.setLayout(new java.awt.BorderLayout(0, 0));
        inputGrid.add(emptyFieldPanel1);

        inputCard.add(inputGrid, java.awt.BorderLayout.CENTER);

        editorPanel.add(inputCard, java.awt.BorderLayout.CENTER);

        toolbar.setBackground(new java.awt.Color(244, 247, 245));
        toolbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));

        addButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        addButton.setText("Add");
        addButton.setBackground(new java.awt.Color(24, 117, 101));
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setFocusPainted(false);
        addButton.setMargin(new java.awt.Insets(9, 12, 9, 12));
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus-white.png"))); // NOI18N
        addButton.setIconTextGap(8);
        addButton.setPreferredSize(new java.awt.Dimension(166, 40));
        toolbar.add(addButton);

        editButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        editButton.setText("Update");
        editButton.setForeground(new java.awt.Color(24, 117, 101));
        editButton.setFocusPainted(false);
        editButton.setMargin(new java.awt.Insets(9, 12, 9, 12));
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit-teal.png"))); // NOI18N
        editButton.setIconTextGap(8);
        editButton.setPreferredSize(new java.awt.Dimension(166, 40));
        toolbar.add(editButton);

        deleteButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.setBackground(new java.awt.Color(251, 237, 234));
        deleteButton.setForeground(new java.awt.Color(211, 94, 78));
        deleteButton.setFocusPainted(false);
        deleteButton.setMargin(new java.awt.Insets(9, 12, 9, 12));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash-coral.png"))); // NOI18N
        deleteButton.setIconTextGap(8);
        deleteButton.setPreferredSize(new java.awt.Dimension(166, 40));
        toolbar.add(deleteButton);

        clearButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        clearButton.setText("Clear form");
        clearButton.setForeground(new java.awt.Color(24, 117, 101));
        clearButton.setFocusPainted(false);
        clearButton.setMargin(new java.awt.Insets(9, 12, 9, 12));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh-teal.png"))); // NOI18N
        clearButton.setIconTextGap(8);
        clearButton.setPreferredSize(new java.awt.Dimension(166, 40));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        toolbar.add(clearButton);

        editorPanel.add(toolbar, java.awt.BorderLayout.SOUTH);

        bodyPanel.add(editorPanel, java.awt.BorderLayout.NORTH);

        recordsCard.setBackground(new java.awt.Color(255, 255, 255));
        recordsCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 18, 14, 18));
        recordsCard.setLayout(new java.awt.BorderLayout(0, 12));

        toolsPanel.setBackground(new java.awt.Color(255, 255, 255));
        toolsPanel.setLayout(new java.awt.BorderLayout(12, 0));

        searchLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(24, 61, 53));
        searchLabel.setText("Search records");
        searchLabel.setLabelFor(searchField);
        toolsPanel.add(searchLabel, java.awt.BorderLayout.WEST);

        searchField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        searchField.setColumns(22);
        searchField.setPreferredSize(new java.awt.Dimension(240, 36));
        searchField.setToolTipText("Search the displayed records; leave blank to show all rows");
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        toolsPanel.add(searchField, java.awt.BorderLayout.CENTER);

        searchActionsPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchActionsPanel.setLayout(new java.awt.GridLayout(1, 2, 8, 0));

        searchButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        searchButton.setText("Search");
        searchButton.setBackground(new java.awt.Color(24, 117, 101));
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new java.awt.Dimension(108, 36));
        searchButton.setMargin(new java.awt.Insets(5, 8, 5, 8));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        searchActionsPanel.add(searchButton);

        refreshButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        refreshButton.setText("Refresh");
        refreshButton.setForeground(new java.awt.Color(24, 117, 101));
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new java.awt.Dimension(108, 36));
        refreshButton.setMargin(new java.awt.Insets(5, 8, 5, 8));
        searchActionsPanel.add(refreshButton);

        toolsPanel.add(searchActionsPanel, java.awt.BorderLayout.EAST);

        recordsCard.add(toolsPanel, java.awt.BorderLayout.NORTH);

        recordsScroll.setPreferredSize(new java.awt.Dimension(900, 250));

        recordsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Service name", "Category", "Minutes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recordsTable.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        recordsTable.setRowHeight(36);
        recordsTable.setFillsViewportHeight(true);
        recordsTable.setGridColor(new java.awt.Color(231, 238, 234));
        recordsTable.setSelectionBackground(new java.awt.Color(217, 238, 229));
        recordsTable.setSelectionForeground(new java.awt.Color(24, 61, 53));
        recordsTable.setForeground(new java.awt.Color(24, 61, 53));
        recordsScroll.setViewportView(recordsTable);

        recordsCard.add(recordsScroll, java.awt.BorderLayout.CENTER);

        noticeLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        noticeLabel.setForeground(new java.awt.Color(97, 119, 110));
        noticeLabel.setText("No records loaded. Select a row to update or delete.");
        recordsCard.add(noticeLabel, java.awt.BorderLayout.SOUTH);

        bodyPanel.add(recordsCard, java.awt.BorderLayout.CENTER);

        add(bodyPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel brandTagLabel;
    private javax.swing.JPanel cardHeadingPanel;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JLabel categoryComboBoxLabel;
    private javax.swing.JPanel categoryComboBoxPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField durationField;
    private javax.swing.JLabel durationFieldLabel;
    private javax.swing.JPanel durationFieldPanel;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel editorPanel;
    private javax.swing.JPanel emptyFieldPanel0;
    private javax.swing.JPanel emptyFieldPanel1;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel headingPanel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idFieldLabel;
    private javax.swing.JPanel idFieldPanel;
    private javax.swing.JPanel inputCard;
    private javax.swing.JPanel inputGrid;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameFieldLabel;
    private javax.swing.JPanel nameFieldPanel;
    private javax.swing.JLabel noticeLabel;
    private javax.swing.JPanel recordsCard;
    private javax.swing.JScrollPane recordsScroll;
    private javax.swing.JTable recordsTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel requiredLabel;
    private javax.swing.JPanel searchActionsPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JLabel sectionLabel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel toolbar;
    private javax.swing.JPanel toolsPanel;
    // End of variables declaration//GEN-END:variables
}
