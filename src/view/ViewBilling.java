package view;

/**
 *
 * @author TANISHA SANJANA
 */

public class ViewBilling extends javax.swing.JPanel {
    public ViewBilling() {
        initComponents();
        configureTable();
    }

    // Controller hooks stay OUTSIDE GUI Builder guarded sections.
    public javax.swing.JTextField getIdField() { return idField; }
    public javax.swing.JComboBox<String> getCustomerComboBox() { return customerComboBox; }
    public javax.swing.JComboBox<String> getAppointmentComboBox() { return appointmentComboBox; }
    public javax.swing.JTextField getBillDateField() { return billDateField; }
    public javax.swing.JComboBox<String> getPaymentMethodComboBox() { return paymentMethodComboBox; }
    public javax.swing.JComboBox<String> getPaymentStatusComboBox() { return paymentStatusComboBox; }
    public javax.swing.JTextField getTotalAmountField() { return totalAmountField; }
    public javax.swing.JComboBox<String> getServiceComboBox() { return serviceComboBox; }
    public javax.swing.JTextField getQuantityField() { return quantityField; }
    public javax.swing.JTextField getUnitPriceField() { return unitPriceField; }
    public javax.swing.JButton getAddItemButton() { return addItemButton; }
    public javax.swing.JButton getUpdateItemButton() { return updateItemButton; }
    public javax.swing.JButton getRemoveItemButton() { return removeItemButton; }
    public javax.swing.JList<String> getItemsList() { return itemsList; }
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
        if (customerComboBox.getItemCount() > 0) customerComboBox.setSelectedIndex(0);
        if (appointmentComboBox.getItemCount() > 0) appointmentComboBox.setSelectedIndex(0);
        billDateField.setText("");
        if (paymentMethodComboBox.getItemCount() > 0) paymentMethodComboBox.setSelectedIndex(0);
        if (paymentStatusComboBox.getItemCount() > 0) paymentStatusComboBox.setSelectedIndex(0);
        totalAmountField.setText("0.00");
        if (serviceComboBox.getItemCount() > 0) serviceComboBox.setSelectedIndex(0);
        quantityField.setText("");
        unitPriceField.setText("");
        itemsList.setModel(new javax.swing.DefaultListModel<String>());
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
        inputStackPanel = new javax.swing.JPanel();
        inputCard = new javax.swing.JPanel();
        cardHeadingPanel = new javax.swing.JPanel();
        sectionLabel = new javax.swing.JLabel();
        requiredLabel = new javax.swing.JLabel();
        inputGrid = new javax.swing.JPanel();
        idFieldPanel = new javax.swing.JPanel();
        idFieldLabel = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        customerComboBoxPanel = new javax.swing.JPanel();
        customerComboBoxLabel = new javax.swing.JLabel();
        customerComboBox = new javax.swing.JComboBox<>();
        appointmentComboBoxPanel = new javax.swing.JPanel();
        appointmentComboBoxLabel = new javax.swing.JLabel();
        appointmentComboBox = new javax.swing.JComboBox<>();
        billDateFieldPanel = new javax.swing.JPanel();
        billDateFieldLabel = new javax.swing.JLabel();
        billDateField = new javax.swing.JTextField();
        paymentMethodComboBoxPanel = new javax.swing.JPanel();
        paymentMethodComboBoxLabel = new javax.swing.JLabel();
        paymentMethodComboBox = new javax.swing.JComboBox<>();
        paymentStatusComboBoxPanel = new javax.swing.JPanel();
        paymentStatusComboBoxLabel = new javax.swing.JLabel();
        paymentStatusComboBox = new javax.swing.JComboBox<>();
        totalAmountFieldPanel = new javax.swing.JPanel();
        totalAmountFieldLabel = new javax.swing.JLabel();
        totalAmountField = new javax.swing.JTextField();
        emptyFieldPanel0 = new javax.swing.JPanel();
        itemsCard = new javax.swing.JPanel();
        itemsTitleLabel = new javax.swing.JLabel();
        lineBodyPanel = new javax.swing.JPanel();
        lineEditorPanel = new javax.swing.JPanel();
        lineFieldsPanel = new javax.swing.JPanel();
        serviceComboBoxPanel = new javax.swing.JPanel();
        serviceComboBoxLabel = new javax.swing.JLabel();
        serviceComboBox = new javax.swing.JComboBox<>();
        lineAmountsPanel = new javax.swing.JPanel();
        quantityFieldPanel = new javax.swing.JPanel();
        quantityFieldLabel = new javax.swing.JLabel();
        quantityField = new javax.swing.JTextField();
        unitPriceFieldPanel = new javax.swing.JPanel();
        unitPriceFieldLabel = new javax.swing.JLabel();
        unitPriceField = new javax.swing.JTextField();
        lineButtonsPanel = new javax.swing.JPanel();
        addItemButton = new javax.swing.JButton();
        updateItemButton = new javax.swing.JButton();
        removeItemButton = new javax.swing.JButton();
        itemsScroll = new javax.swing.JScrollPane();
        itemsList = new javax.swing.JList<>();
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
        titleLabel.setText("Billing & payments");
        headingPanel.add(titleLabel, java.awt.BorderLayout.NORTH);

        subtitleLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(97, 119, 110));
        subtitleLabel.setText("Bring service charges and payment details together.");
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

        inputStackPanel.setBackground(new java.awt.Color(244, 247, 245));
        inputStackPanel.setLayout(new java.awt.GridLayout(1, 2, 16, 0));

        inputCard.setBackground(new java.awt.Color(255, 255, 255));
        inputCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 18, 14, 18));
        inputCard.setLayout(new java.awt.BorderLayout(0, 14));

        cardHeadingPanel.setBackground(new java.awt.Color(255, 255, 255));
        cardHeadingPanel.setLayout(new java.awt.BorderLayout(8, 0));

        sectionLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        sectionLabel.setForeground(new java.awt.Color(24, 61, 53));
        sectionLabel.setText("Bill details");
        cardHeadingPanel.add(sectionLabel, java.awt.BorderLayout.CENTER);

        requiredLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        requiredLabel.setForeground(new java.awt.Color(97, 119, 110));
        requiredLabel.setText("* Required");
        cardHeadingPanel.add(requiredLabel, java.awt.BorderLayout.EAST);

        inputCard.add(cardHeadingPanel, java.awt.BorderLayout.NORTH);

        inputGrid.setBackground(new java.awt.Color(255, 255, 255));
        inputGrid.setLayout(new java.awt.GridLayout(4, 2, 18, 8));

        idFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        idFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        idFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        idFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        idFieldLabel.setText("Bill ID");
        idFieldLabel.setLabelFor(idField);
        idFieldPanel.add(idFieldLabel, java.awt.BorderLayout.NORTH);

        idField.setForeground(new java.awt.Color(24, 61, 53));
        idField.setToolTipText("New for a new record; set by the controller when selecting a saved row");
        idField.setText("New");
        idField.setColumns(16);
        idField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        idField.setEditable(false);
        idField.setBackground(new java.awt.Color(232, 241, 236));
        idField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        idField.setPreferredSize(new java.awt.Dimension(180, 32));
        idFieldPanel.add(idField, java.awt.BorderLayout.CENTER);

        inputGrid.add(idFieldPanel);

        customerComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        customerComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        customerComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        customerComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        customerComboBoxLabel.setText("Customer *");
        customerComboBoxLabel.setLabelFor(customerComboBox);
        customerComboBoxPanel.add(customerComboBoxLabel, java.awt.BorderLayout.NORTH);

        customerComboBox.setForeground(new java.awt.Color(24, 61, 53));
        customerComboBox.setBackground(new java.awt.Color(250, 252, 250));
        customerComboBox.setToolTipText("The appointment must belong to this customer");
        customerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select customer…" }));
        customerComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        customerComboBox.setPreferredSize(new java.awt.Dimension(180, 32));
        customerComboBoxPanel.add(customerComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(customerComboBoxPanel);

        appointmentComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        appointmentComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        appointmentComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        appointmentComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        appointmentComboBoxLabel.setText("Linked appointment");
        appointmentComboBoxLabel.setLabelFor(appointmentComboBox);
        appointmentComboBoxPanel.add(appointmentComboBoxLabel, java.awt.BorderLayout.NORTH);

        appointmentComboBox.setForeground(new java.awt.Color(24, 61, 53));
        appointmentComboBox.setBackground(new java.awt.Color(250, 252, 250));
        appointmentComboBox.setToolTipText("Optional appointment belonging to the selected customer");
        appointmentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No appointment" }));
        appointmentComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        appointmentComboBox.setPreferredSize(new java.awt.Dimension(180, 32));
        appointmentComboBoxPanel.add(appointmentComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(appointmentComboBoxPanel);

        billDateFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        billDateFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        billDateFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        billDateFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        billDateFieldLabel.setText("Bill date (YYYY-MM-DD) *");
        billDateFieldLabel.setLabelFor(billDateField);
        billDateFieldPanel.add(billDateFieldLabel, java.awt.BorderLayout.NORTH);

        billDateField.setForeground(new java.awt.Color(24, 61, 53));
        billDateField.setBackground(new java.awt.Color(250, 252, 250));
        billDateField.setToolTipText("For example 2026-10-20");
        billDateField.setColumns(16);
        billDateField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        billDateField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        billDateField.setPreferredSize(new java.awt.Dimension(180, 32));
        billDateFieldPanel.add(billDateField, java.awt.BorderLayout.CENTER);

        inputGrid.add(billDateFieldPanel);

        paymentMethodComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        paymentMethodComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        paymentMethodComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        paymentMethodComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        paymentMethodComboBoxLabel.setText("Payment method *");
        paymentMethodComboBoxLabel.setLabelFor(paymentMethodComboBox);
        paymentMethodComboBoxPanel.add(paymentMethodComboBoxLabel, java.awt.BorderLayout.NORTH);

        paymentMethodComboBox.setForeground(new java.awt.Color(24, 61, 53));
        paymentMethodComboBox.setBackground(new java.awt.Color(250, 252, 250));
        paymentMethodComboBox.setToolTipText("Method used for payment");
        paymentMethodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select method…", "Cash", "Card", "Bank transfer" }));
        paymentMethodComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        paymentMethodComboBox.setPreferredSize(new java.awt.Dimension(180, 32));
        paymentMethodComboBoxPanel.add(paymentMethodComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(paymentMethodComboBoxPanel);

        paymentStatusComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        paymentStatusComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        paymentStatusComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        paymentStatusComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        paymentStatusComboBoxLabel.setText("Payment status *");
        paymentStatusComboBoxLabel.setLabelFor(paymentStatusComboBox);
        paymentStatusComboBoxPanel.add(paymentStatusComboBoxLabel, java.awt.BorderLayout.NORTH);

        paymentStatusComboBox.setForeground(new java.awt.Color(24, 61, 53));
        paymentStatusComboBox.setBackground(new java.awt.Color(250, 252, 250));
        paymentStatusComboBox.setToolTipText("Stored payment status");
        paymentStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unpaid", "Paid" }));
        paymentStatusComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        paymentStatusComboBox.setPreferredSize(new java.awt.Dimension(180, 32));
        paymentStatusComboBoxPanel.add(paymentStatusComboBox, java.awt.BorderLayout.CENTER);

        inputGrid.add(paymentStatusComboBoxPanel);

        totalAmountFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        totalAmountFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        totalAmountFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        totalAmountFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        totalAmountFieldLabel.setText("Total amount (LKR)");
        totalAmountFieldLabel.setLabelFor(totalAmountField);
        totalAmountFieldPanel.add(totalAmountFieldLabel, java.awt.BorderLayout.NORTH);

        totalAmountField.setForeground(new java.awt.Color(24, 61, 53));
        totalAmountField.setToolTipText("Calculated from quantity × unit price for all items");
        totalAmountField.setText("0.00");
        totalAmountField.setColumns(16);
        totalAmountField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        totalAmountField.setEditable(false);
        totalAmountField.setBackground(new java.awt.Color(232, 241, 236));
        totalAmountField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        totalAmountField.setPreferredSize(new java.awt.Dimension(180, 32));
        totalAmountFieldPanel.add(totalAmountField, java.awt.BorderLayout.CENTER);

        inputGrid.add(totalAmountFieldPanel);

        emptyFieldPanel0.setBackground(new java.awt.Color(255, 255, 255));
        emptyFieldPanel0.setLayout(new java.awt.BorderLayout(0, 0));
        inputGrid.add(emptyFieldPanel0);

        inputCard.add(inputGrid, java.awt.BorderLayout.CENTER);

        inputStackPanel.add(inputCard);

        itemsCard.setBackground(new java.awt.Color(255, 255, 255));
        itemsCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 18, 14, 18));
        itemsCard.setLayout(new java.awt.BorderLayout(0, 14));

        itemsTitleLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        itemsTitleLabel.setForeground(new java.awt.Color(24, 61, 53));
        itemsTitleLabel.setText("Service items");
        itemsCard.add(itemsTitleLabel, java.awt.BorderLayout.NORTH);

        lineBodyPanel.setBackground(new java.awt.Color(255, 255, 255));
        lineBodyPanel.setLayout(new java.awt.BorderLayout(0, 10));

        lineEditorPanel.setBackground(new java.awt.Color(255, 255, 255));
        lineEditorPanel.setLayout(new java.awt.BorderLayout(0, 10));

        lineFieldsPanel.setBackground(new java.awt.Color(255, 255, 255));
        lineFieldsPanel.setLayout(new java.awt.BorderLayout(0, 10));

        serviceComboBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        serviceComboBoxPanel.setLayout(new java.awt.BorderLayout(0, 7));

        serviceComboBoxLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        serviceComboBoxLabel.setForeground(new java.awt.Color(24, 61, 53));
        serviceComboBoxLabel.setText("Service *");
        serviceComboBoxLabel.setLabelFor(serviceComboBox);
        serviceComboBoxPanel.add(serviceComboBoxLabel, java.awt.BorderLayout.NORTH);

        serviceComboBox.setForeground(new java.awt.Color(24, 61, 53));
        serviceComboBox.setBackground(new java.awt.Color(250, 252, 250));
        serviceComboBox.setToolTipText("Select a service to include in this bill");
        serviceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select service…" }));
        serviceComboBox.setPreferredSize(new java.awt.Dimension(200, 34));
        serviceComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        serviceComboBoxPanel.add(serviceComboBox, java.awt.BorderLayout.CENTER);

        lineFieldsPanel.add(serviceComboBoxPanel, java.awt.BorderLayout.NORTH);

        lineAmountsPanel.setBackground(new java.awt.Color(255, 255, 255));
        lineAmountsPanel.setLayout(new java.awt.GridLayout(1, 2, 12, 0));

        quantityFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        quantityFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        quantityFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        quantityFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        quantityFieldLabel.setText("Quantity *");
        quantityFieldLabel.setLabelFor(quantityField);
        quantityFieldPanel.add(quantityFieldLabel, java.awt.BorderLayout.NORTH);

        quantityField.setForeground(new java.awt.Color(24, 61, 53));
        quantityField.setBackground(new java.awt.Color(250, 252, 250));
        quantityField.setToolTipText("Positive whole number");
        quantityField.setColumns(16);
        quantityField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        quantityField.setPreferredSize(new java.awt.Dimension(200, 34));
        quantityField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        quantityFieldPanel.add(quantityField, java.awt.BorderLayout.CENTER);

        lineAmountsPanel.add(quantityFieldPanel);

        unitPriceFieldPanel.setBackground(new java.awt.Color(255, 255, 255));
        unitPriceFieldPanel.setLayout(new java.awt.BorderLayout(0, 7));

        unitPriceFieldLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        unitPriceFieldLabel.setForeground(new java.awt.Color(24, 61, 53));
        unitPriceFieldLabel.setText("Unit price (LKR) *");
        unitPriceFieldLabel.setLabelFor(unitPriceField);
        unitPriceFieldPanel.add(unitPriceFieldLabel, java.awt.BorderLayout.NORTH);

        unitPriceField.setForeground(new java.awt.Color(24, 61, 53));
        unitPriceField.setBackground(new java.awt.Color(250, 252, 250));
        unitPriceField.setToolTipText("Amount per unit, up to two decimal places");
        unitPriceField.setColumns(16);
        unitPriceField.setMargin(new java.awt.Insets(6, 8, 6, 8));
        unitPriceField.setPreferredSize(new java.awt.Dimension(200, 34));
        unitPriceField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        unitPriceFieldPanel.add(unitPriceField, java.awt.BorderLayout.CENTER);

        lineAmountsPanel.add(unitPriceFieldPanel);

        lineFieldsPanel.add(lineAmountsPanel, java.awt.BorderLayout.CENTER);

        lineEditorPanel.add(lineFieldsPanel, java.awt.BorderLayout.NORTH);

        lineButtonsPanel.setBackground(new java.awt.Color(255, 255, 255));
        lineButtonsPanel.setLayout(new java.awt.GridLayout(1, 3, 8, 0));

        addItemButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        addItemButton.setText("Add item");
        addItemButton.setForeground(new java.awt.Color(24, 117, 101));
        addItemButton.setFocusPainted(false);
        addItemButton.setPreferredSize(new java.awt.Dimension(108, 36));
        addItemButton.setMargin(new java.awt.Insets(5, 8, 5, 8));
        addItemButton.setToolTipText("Add this service item to the bill");
        lineButtonsPanel.add(addItemButton);

        updateItemButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        updateItemButton.setText("Update");
        updateItemButton.setForeground(new java.awt.Color(24, 117, 101));
        updateItemButton.setFocusPainted(false);
        updateItemButton.setPreferredSize(new java.awt.Dimension(108, 36));
        updateItemButton.setMargin(new java.awt.Insets(5, 8, 5, 8));
        updateItemButton.setToolTipText("Update the selected bill item");
        lineButtonsPanel.add(updateItemButton);

        removeItemButton.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        removeItemButton.setText("Remove");
        removeItemButton.setBackground(new java.awt.Color(251, 237, 234));
        removeItemButton.setForeground(new java.awt.Color(211, 94, 78));
        removeItemButton.setFocusPainted(false);
        removeItemButton.setPreferredSize(new java.awt.Dimension(108, 36));
        removeItemButton.setMargin(new java.awt.Insets(5, 8, 5, 8));
        removeItemButton.setToolTipText("Remove the selected bill item");
        lineButtonsPanel.add(removeItemButton);

        lineEditorPanel.add(lineButtonsPanel, java.awt.BorderLayout.CENTER);

        lineBodyPanel.add(lineEditorPanel, java.awt.BorderLayout.NORTH);

        itemsScroll.setPreferredSize(new java.awt.Dimension(300, 60));

        itemsList.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        itemsList.setFixedCellHeight(26);
        itemsList.setVisibleRowCount(2);
        itemsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        itemsList.setToolTipText("Current bill items: service, quantity, unit price and line total");
        itemsScroll.setViewportView(itemsList);

        lineBodyPanel.add(itemsScroll, java.awt.BorderLayout.CENTER);

        itemsCard.add(lineBodyPanel, java.awt.BorderLayout.CENTER);

        inputStackPanel.add(itemsCard);

        editorPanel.add(inputStackPanel, java.awt.BorderLayout.CENTER);

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
                "Bill ID", "Customer", "Appointment", "Bill date", "Total (LKR)", "Payment method", "Payment status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
    private javax.swing.JButton addItemButton;
    private javax.swing.JComboBox<String> appointmentComboBox;
    private javax.swing.JLabel appointmentComboBoxLabel;
    private javax.swing.JPanel appointmentComboBoxPanel;
    private javax.swing.JTextField billDateField;
    private javax.swing.JLabel billDateFieldLabel;
    private javax.swing.JPanel billDateFieldPanel;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel brandTagLabel;
    private javax.swing.JPanel cardHeadingPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox<String> customerComboBox;
    private javax.swing.JLabel customerComboBoxLabel;
    private javax.swing.JPanel customerComboBoxPanel;
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
    private javax.swing.JPanel inputStackPanel;
    private javax.swing.JPanel itemsCard;
    private javax.swing.JList<String> itemsList;
    private javax.swing.JScrollPane itemsScroll;
    private javax.swing.JLabel itemsTitleLabel;
    private javax.swing.JPanel lineAmountsPanel;
    private javax.swing.JPanel lineBodyPanel;
    private javax.swing.JPanel lineButtonsPanel;
    private javax.swing.JPanel lineEditorPanel;
    private javax.swing.JPanel lineFieldsPanel;
    private javax.swing.JLabel noticeLabel;
    private javax.swing.JComboBox<String> paymentMethodComboBox;
    private javax.swing.JLabel paymentMethodComboBoxLabel;
    private javax.swing.JPanel paymentMethodComboBoxPanel;
    private javax.swing.JComboBox<String> paymentStatusComboBox;
    private javax.swing.JLabel paymentStatusComboBoxLabel;
    private javax.swing.JPanel paymentStatusComboBoxPanel;
    private javax.swing.JTextField quantityField;
    private javax.swing.JLabel quantityFieldLabel;
    private javax.swing.JPanel quantityFieldPanel;
    private javax.swing.JPanel recordsCard;
    private javax.swing.JScrollPane recordsScroll;
    private javax.swing.JTable recordsTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton removeItemButton;
    private javax.swing.JLabel requiredLabel;
    private javax.swing.JPanel searchActionsPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JLabel sectionLabel;
    private javax.swing.JComboBox<String> serviceComboBox;
    private javax.swing.JLabel serviceComboBoxLabel;
    private javax.swing.JPanel serviceComboBoxPanel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel toolbar;
    private javax.swing.JPanel toolsPanel;
    private javax.swing.JTextField totalAmountField;
    private javax.swing.JLabel totalAmountFieldLabel;
    private javax.swing.JPanel totalAmountFieldPanel;
    private javax.swing.JTextField unitPriceField;
    private javax.swing.JLabel unitPriceFieldLabel;
    private javax.swing.JPanel unitPriceFieldPanel;
    private javax.swing.JButton updateItemButton;
    // End of variables declaration//GEN-END:variables
}
