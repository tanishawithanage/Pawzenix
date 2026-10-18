package view;

/**
 * Pawzenix input form. Layout metadata is stored in the matching .form file.
 * Database/controller code was not included in the uploaded project.
 * Use the public component getters to connect your existing controller.
 */
public class ViewAppointments extends javax.swing.JPanel {
    public ViewAppointments() {
        initComponents();
        configureTable();
    }

    // Controller hooks stay OUTSIDE GUI Builder guarded sections.
    public javax.swing.JTextField getIdField() { return idField; }
    public javax.swing.JComboBox<String> getPetComboBox() { return petComboBox; }
    public javax.swing.JComboBox<String> getServiceComboBox() { return serviceComboBox; }
    public javax.swing.JComboBox<String> getStaffComboBox() { return staffComboBox; }
    public javax.swing.JComboBox<String> getStatusComboBox() { return statusComboBox; }
    public javax.swing.JTextField getDateField() { return dateField; }
    public javax.swing.JTextField getTimeField() { return timeField; }
    public javax.swing.JTextArea getNotesArea() { return notesArea; }
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
        if (petComboBox.getItemCount() > 0) petComboBox.setSelectedIndex(0);
        if (serviceComboBox.getItemCount() > 0) serviceComboBox.setSelectedIndex(0);
        if (staffComboBox.getItemCount() > 0) staffComboBox.setSelectedIndex(0);
        if (statusComboBox.getItemCount() > 0) statusComboBox.setSelectedIndex(0);
        dateField.setText("");
        timeField.setText("");
        notesArea.setText("");
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
        petComboBoxPanel = new javax.swing.JPanel();
        petComboBoxLabel = new javax.swing.JLabel();
        petComboBox = new javax.swing.JComboBox<>();
        serviceComboBoxPanel = new javax.swing.JPanel();
        serviceComboBoxLabel = new javax.swing.JLabel();
        serviceComboBox = new javax.swing.JComboBox<>();
        staffComboBoxPanel = new javax.swing.JPanel();
        staffComboBoxLabel = new javax.swing.JLabel();
        staffComboBox = new javax.swing.JComboBox<>();
        statusComboBoxPanel = new javax.swing.JPanel();
        statusComboBoxLabel = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        dateFieldPanel = new javax.swing.JPanel();
        dateFieldLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        timeFieldPanel = new javax.swing.JPanel();
        timeFieldLabel = new javax.swing.JLabel();
        timeField = new javax.swing.JTextField();
        notesAreaPanel = new javax.swing.JPanel();
        notesAreaLabel = new javax.swing.JLabel();
        notesAreaScroll = new javax.swing.JScrollPane();
        notesArea = new javax.swing.JTextArea();
        emptyFieldPanel0 = new javax.swing.JPanel();
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
        titleLabel.setText("Appointments");
        headingPanel.add(titleLabel, java.awt.BorderLayout.NORTH);

        subtitleLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(97, 119, 110));
        subtitleLabel.setText("Plan the right care, at the right time.");
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
        sectionLabel.setText("Booking details");
        cardHeadingPanel.add(sectionLabel, java.awt.BorderLayout.CENTER);

        requiredLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        requiredLabel.setForeground(new java.awt.Color(97, 119, 110));
        requiredLabel.setText("* Required");
        cardHeadingPanel.add(requiredLabel, java.awt.BorderLayout.EAST);

        inputCard.add(cardHeadingPanel, java.awt.BorderLayout.NORTH);

        inputGrid.setBackground(new java.awt.Color(255, 255, 255));
        inputGrid.setLayout(new java.awt.GridLayout(3, 3, 18, 12));

        idFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        idFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        idFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        idFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        idFieldLabel.setText("Appointment ID");
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

        petComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        petComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        petComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        petComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        petComboBoxLabel.setText("Pet *");
        petComboBoxLabel.setLabelFor(petComboBox);
        petComboBoxPanel.add(petComboBoxLabel, java.awt.BorderLayout.NORTH);

        petComboBox.setForeground(new java.awt.Color(24, 61, 53));
        petComboBox.setBackground(new java.awt.Color(250, 252, 250));
        petComboBox.setToolTipText("Show pet name and owner when loading records");
        petComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select pet…" }));
        petComboBox.setPreferredSize(new java.awt.Dimension(200, 34));
        petComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        petComboBoxPanel.add(petComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(petComboBoxPanel);

        serviceComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        serviceComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        serviceComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        serviceComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        serviceComboBoxLabel.setText("Service *");
        serviceComboBoxLabel.setLabelFor(serviceComboBox);
        serviceComboBoxPanel.add(serviceComboBoxLabel, java.awt.BorderLayout.NORTH);

        serviceComboBox.setForeground(new java.awt.Color(24, 61, 53));
        serviceComboBox.setBackground(new java.awt.Color(250, 252, 250));
        serviceComboBox.setToolTipText("Select an existing service");
        serviceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select service…" }));
        serviceComboBox.setPreferredSize(new java.awt.Dimension(200, 34));
        serviceComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        serviceComboBoxPanel.add(serviceComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(serviceComboBoxPanel);

        staffComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        staffComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        staffComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        staffComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        staffComboBoxLabel.setText("Assigned staff");
        staffComboBoxLabel.setLabelFor(staffComboBox);
        staffComboBoxPanel.add(staffComboBoxLabel, java.awt.BorderLayout.NORTH);

        staffComboBox.setForeground(new java.awt.Color(24, 61, 53));
        staffComboBox.setBackground(new java.awt.Color(250, 252, 250));
        staffComboBox.setToolTipText("Optional staff member");
        staffComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unassigned" }));
        staffComboBox.setPreferredSize(new java.awt.Dimension(200, 34));
        staffComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        staffComboBoxPanel.add(staffComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(staffComboBoxPanel);

        statusComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        statusComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        statusComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        statusComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        statusComboBoxLabel.setText("Appointment status *");
        statusComboBoxLabel.setLabelFor(statusComboBox);
        statusComboBoxPanel.add(statusComboBoxLabel, java.awt.BorderLayout.NORTH);

        statusComboBox.setForeground(new java.awt.Color(24, 61, 53));
        statusComboBox.setBackground(new java.awt.Color(250, 252, 250));
        statusComboBox.setToolTipText("Appointment status");
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Booked", "Confirmed", "Completed", "Cancelled", "No-show" }));
        statusComboBox.setPreferredSize(new java.awt.Dimension(200, 34));
        statusComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        statusComboBoxPanel.add(statusComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(statusComboBoxPanel);

        dateFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        dateFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        dateFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        dateFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        dateFieldLabel.setText("Date (YYYY-MM-DD) *");
        dateFieldLabel.setLabelFor(dateField);
        dateFieldPanel.add(dateFieldLabel, java.awt.BorderLayout.NORTH);

        dateField.setForeground(new java.awt.Color(24, 61, 53));
        dateField.setBackground(new java.awt.Color(250, 252, 250));
        dateField.setToolTipText("For example 2026-10-20");
        dateField.setColumns(16);
        dateField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        dateField.setPreferredSize(new java.awt.Dimension(200, 34));
        dateField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        dateFieldPanel.add(dateField, java.awt.BorderLayout.CENTER);

        inputGrid.add(dateFieldPanel);

        timeFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        timeFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        timeFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        timeFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        timeFieldLabel.setText("Time (HH:mm) *");
        timeFieldLabel.setLabelFor(timeField);
        timeFieldPanel.add(timeFieldLabel, java.awt.BorderLayout.NORTH);

        timeField.setForeground(new java.awt.Color(24, 61, 53));
        timeField.setBackground(new java.awt.Color(250, 252, 250));
        timeField.setToolTipText("24-hour time, for example 14:30");
        timeField.setColumns(16);
        timeField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        timeField.setPreferredSize(new java.awt.Dimension(200, 34));
        timeField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        timeFieldPanel.add(timeField, java.awt.BorderLayout.CENTER);

        inputGrid.add(timeFieldPanel);

        notesAreaPanel.setBackground(new java.awt.Color(255, 255, 255));
        notesAreaPanel.setLayout(new java.awt.BorderLayout(0, 7));

        notesAreaLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        notesAreaLabel.setForeground(new java.awt.Color(24, 61, 53));
        notesAreaLabel.setText("Booking notes");
        notesAreaLabel.setLabelFor(notesArea);
        notesAreaPanel.add(notesAreaLabel, java.awt.BorderLayout.NORTH);

        notesArea.setForeground(new java.awt.Color(24, 61, 53));
        notesArea.setBackground(new java.awt.Color(250, 252, 250));
        notesArea.setColumns(24);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setRows(1);
        notesArea.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        notesArea.setMargin(new java.awt.Insets(6, 8, 6, 8));
        notesAreaScroll.setViewportView(notesArea);

        notesAreaPanel.add(notesAreaScroll, java.awt.BorderLayout.CENTER);

        inputGrid.add(notesAreaPanel);

        emptyFieldPanel0.setBackground(new java.awt.Color(255, 255, 255));
        emptyFieldPanel0.setLayout(new java.awt.BorderLayout(0, 0));
        inputGrid.add(emptyFieldPanel0);

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
                "ID", "Pet", "Service", "Staff", "Date", "Time", "Status", "Notes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField dateField;
    private javax.swing.JLabel dateFieldLabel;
    private javax.swing.JPanel dateFieldPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel editorPanel;
    private javax.swing.JPanel emptyFieldPanel0;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel headingPanel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idFieldLabel;
    private javax.swing.JPanel idFieldPanel;
    private javax.swing.JPanel inputCard;
    private javax.swing.JPanel inputGrid;
    private javax.swing.JTextArea notesArea;
    private javax.swing.JLabel notesAreaLabel;
    private javax.swing.JPanel notesAreaPanel;
    private javax.swing.JScrollPane notesAreaScroll;
    private javax.swing.JLabel noticeLabel;
    private javax.swing.JComboBox<String> petComboBox;
    private javax.swing.JLabel petComboBoxLabel;
    private javax.swing.JPanel petComboBoxPanel;
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
    private javax.swing.JComboBox<String> serviceComboBox;
    private javax.swing.JLabel serviceComboBoxLabel;
    private javax.swing.JPanel serviceComboBoxPanel;
    private javax.swing.JComboBox<String> staffComboBox;
    private javax.swing.JLabel staffComboBoxLabel;
    private javax.swing.JPanel staffComboBoxPanel;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JLabel statusComboBoxLabel;
    private javax.swing.JPanel statusComboBoxPanel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JTextField timeField;
    private javax.swing.JLabel timeFieldLabel;
    private javax.swing.JPanel timeFieldPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel toolbar;
    private javax.swing.JPanel toolsPanel;
    // End of variables declaration//GEN-END:variables
}
