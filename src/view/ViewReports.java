package view;

/**
 *
 * @author TANISHA SANJANA
 */
public class ViewReports extends javax.swing.JPanel {

    public ViewReports() {
        initComponents();
        // PDF download is not used: Jasper's preview window already has its own Save and Print buttons.
        activityExport.setVisible(false);
        revenueExport.setVisible(false);
        if (!java.beans.Beans.isDesignTime()) {
            new controller.ReportController(this);
        }
    }

    // Controller hooks stay OUTSIDE GUI Builder guarded sections.
    public javax.swing.JTextField getFromField() { return fromField; }
    public javax.swing.JTextField getToField() { return toField; }
    public javax.swing.JButton getActivityPreview() { return activityPreview; }
    public javax.swing.JButton getRevenuePreview() { return revenuePreview; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        headingPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        filterPanel = new javax.swing.JPanel();
        dateRangeLabel = new javax.swing.JLabel();
        fromLabel = new javax.swing.JLabel();
        fromField = new javax.swing.JTextField();
        toLabel = new javax.swing.JLabel();
        toField = new javax.swing.JTextField();
        datesHint = new javax.swing.JLabel();
        reportBody = new javax.swing.JPanel();
        reportCards = new javax.swing.JPanel();
        activityCard = new javax.swing.JPanel();
        activityTitle = new javax.swing.JLabel();
        activityCopy = new javax.swing.JPanel();
        activityDescription = new javax.swing.JLabel();
        activityHint = new javax.swing.JLabel();
        activityActions = new javax.swing.JPanel();
        activityPreview = new javax.swing.JButton();
        activityExport = new javax.swing.JButton();
        revenueCard = new javax.swing.JPanel();
        revenueTitle = new javax.swing.JLabel();
        revenueCopy = new javax.swing.JPanel();
        revenueDescription = new javax.swing.JLabel();
        revenueHint = new javax.swing.JLabel();
        revenueActions = new javax.swing.JPanel();
        revenuePreview = new javax.swing.JButton();
        revenueExport = new javax.swing.JButton();
        statusPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 247, 245));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 20, 30));
        setPreferredSize(new java.awt.Dimension(1160, 820));
        setLayout(new java.awt.BorderLayout());

        headerPanel.setBackground(new java.awt.Color(244, 247, 245));
        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 22, 0));
        headerPanel.setLayout(new java.awt.BorderLayout());

        headingPanel.setBackground(new java.awt.Color(244, 247, 245));
        headingPanel.setLayout(new java.awt.GridLayout(2, 1, 0, 8));

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(24, 61, 53));
        titleLabel.setText("Reports");
        headingPanel.add(titleLabel);

        subtitleLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        subtitleLabel.setForeground(new java.awt.Color(97, 119, 110));
        subtitleLabel.setText("Clear records. Better decisions.");
        headingPanel.add(subtitleLabel);

        headerPanel.add(headingPanel, java.awt.BorderLayout.CENTER);

        add(headerPanel, java.awt.BorderLayout.NORTH);

        bodyPanel.setBackground(new java.awt.Color(244, 247, 245));
        bodyPanel.setLayout(new java.awt.BorderLayout(0, 22));

        filterPanel.setBackground(new java.awt.Color(255, 255, 255));
        filterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 22, 22, 22));
        filterPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 14, 0));

        dateRangeLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        dateRangeLabel.setForeground(new java.awt.Color(24, 61, 53));
        dateRangeLabel.setText("Date range");
        filterPanel.add(dateRangeLabel);

        fromLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        fromLabel.setForeground(new java.awt.Color(24, 61, 53));
        fromLabel.setText("From");
        filterPanel.add(fromLabel);

        fromField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        fromField.setPreferredSize(new java.awt.Dimension(145, 38));
        filterPanel.add(fromField);

        toLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        toLabel.setForeground(new java.awt.Color(24, 61, 53));
        toLabel.setText("To");
        filterPanel.add(toLabel);

        toField.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        toField.setPreferredSize(new java.awt.Dimension(145, 38));
        filterPanel.add(toField);

        datesHint.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        datesHint.setForeground(new java.awt.Color(97, 119, 110));
        datesHint.setText("YYYY-MM-DD • Inclusive dates");
        filterPanel.add(datesHint);

        bodyPanel.add(filterPanel, java.awt.BorderLayout.NORTH);

        reportBody.setBackground(new java.awt.Color(244, 247, 245));
        reportBody.setLayout(new java.awt.BorderLayout());

        reportCards.setBackground(new java.awt.Color(244, 247, 245));
        reportCards.setPreferredSize(new java.awt.Dimension(0, 420));
        reportCards.setLayout(new java.awt.GridLayout(2, 1, 0, 20));

        activityCard.setBackground(new java.awt.Color(255, 255, 255));
        activityCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 22, 22, 22));
        activityCard.setLayout(new java.awt.BorderLayout(0, 16));

        activityTitle.setFont(new java.awt.Font("SansSerif", 1, 21)); // NOI18N
        activityTitle.setForeground(new java.awt.Color(24, 117, 101));
        activityTitle.setText("Appointment activity");
        activityTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/appointments-teal.png"))); // NOI18N
        activityTitle.setIconTextGap(8);
        activityCard.add(activityTitle, java.awt.BorderLayout.NORTH);

        activityCopy.setBackground(new java.awt.Color(255, 255, 255));
        activityCopy.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        activityDescription.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        activityDescription.setForeground(new java.awt.Color(24, 61, 53));
        activityDescription.setText("Plan staffing and review completed, cancelled and missed visits.");
        activityCopy.add(activityDescription);

        activityHint.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        activityHint.setForeground(new java.awt.Color(97, 119, 110));
        activityHint.setText("Filters by visit date. Includes pets, owners, services and care providers.");
        activityCopy.add(activityHint);

        activityCard.add(activityCopy, java.awt.BorderLayout.CENTER);

        activityActions.setBackground(new java.awt.Color(255, 255, 255));
        activityActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));

        activityPreview.setBackground(new java.awt.Color(24, 117, 101));
        activityPreview.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        activityPreview.setForeground(new java.awt.Color(255, 255, 255));
        activityPreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reports-white.png"))); // NOI18N
        activityPreview.setText("Preview report");
        activityPreview.setFocusPainted(false);
        activityPreview.setIconTextGap(8);
        activityPreview.setMargin(new java.awt.Insets(10, 12, 10, 12));
        activityPreview.setPreferredSize(new java.awt.Dimension(170, 40));
        activityActions.add(activityPreview);

        activityExport.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        activityExport.setForeground(new java.awt.Color(24, 117, 101));
        activityExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/export-teal.png"))); // NOI18N
        activityExport.setText("Export PDF");
        activityExport.setFocusPainted(false);
        activityExport.setIconTextGap(8);
        activityExport.setMargin(new java.awt.Insets(10, 12, 10, 12));
        activityExport.setPreferredSize(new java.awt.Dimension(155, 40));
        activityActions.add(activityExport);

        activityCard.add(activityActions, java.awt.BorderLayout.SOUTH);

        reportCards.add(activityCard);

        revenueCard.setBackground(new java.awt.Color(255, 255, 255));
        revenueCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 22, 22, 22));
        revenueCard.setLayout(new java.awt.BorderLayout(0, 16));

        revenueTitle.setFont(new java.awt.Font("SansSerif", 1, 21)); // NOI18N
        revenueTitle.setForeground(new java.awt.Color(117, 100, 171));
        revenueTitle.setText("Invoice revenue & balances");
        revenueTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/billing-purple.png"))); // NOI18N
        revenueTitle.setIconTextGap(8);
        revenueCard.add(revenueTitle, java.awt.BorderLayout.NORTH);

        revenueCopy.setBackground(new java.awt.Color(255, 255, 255));
        revenueCopy.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        revenueDescription.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        revenueDescription.setForeground(new java.awt.Color(24, 61, 53));
        revenueDescription.setText("Review billed totals, discounts, collections and outstanding balances.");
        revenueCopy.add(revenueDescription);

        revenueHint.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        revenueHint.setForeground(new java.awt.Color(97, 119, 110));
        revenueHint.setText("Filters by invoice date. Includes payments to date for those invoices.");
        revenueCopy.add(revenueHint);

        revenueCard.add(revenueCopy, java.awt.BorderLayout.CENTER);

        revenueActions.setBackground(new java.awt.Color(255, 255, 255));
        revenueActions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));

        revenuePreview.setBackground(new java.awt.Color(117, 100, 171));
        revenuePreview.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        revenuePreview.setForeground(new java.awt.Color(255, 255, 255));
        revenuePreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reports-white.png"))); // NOI18N
        revenuePreview.setText("Preview report");
        revenuePreview.setFocusPainted(false);
        revenuePreview.setIconTextGap(8);
        revenuePreview.setMargin(new java.awt.Insets(10, 12, 10, 12));
        revenuePreview.setPreferredSize(new java.awt.Dimension(170, 40));
        revenueActions.add(revenuePreview);

        revenueExport.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        revenueExport.setForeground(new java.awt.Color(117, 100, 171));
        revenueExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/export-purple.png"))); // NOI18N
        revenueExport.setText("Export PDF");
        revenueExport.setFocusPainted(false);
        revenueExport.setIconTextGap(8);
        revenueExport.setMargin(new java.awt.Insets(10, 12, 10, 12));
        revenueExport.setPreferredSize(new java.awt.Dimension(155, 40));
        revenueActions.add(revenueExport);

        revenueCard.add(revenueActions, java.awt.BorderLayout.SOUTH);

        reportCards.add(revenueCard);

        reportBody.add(reportCards, java.awt.BorderLayout.NORTH);

        statusPanel.setBackground(new java.awt.Color(244, 247, 245));
        statusPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 0, 0, 0));
        statusPanel.setLayout(new java.awt.BorderLayout());

        statusLabel.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(97, 119, 110));
        statusLabel.setText("Choose dates, then preview or export a report.");
        statusPanel.add(statusLabel, java.awt.BorderLayout.NORTH);

        reportBody.add(statusPanel, java.awt.BorderLayout.CENTER);

        bodyPanel.add(reportBody, java.awt.BorderLayout.CENTER);

        add(bodyPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel activityActions;
    private javax.swing.JPanel activityCard;
    private javax.swing.JPanel activityCopy;
    private javax.swing.JLabel activityDescription;
    private javax.swing.JButton activityExport;
    private javax.swing.JLabel activityHint;
    private javax.swing.JButton activityPreview;
    private javax.swing.JLabel activityTitle;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel dateRangeLabel;
    private javax.swing.JLabel datesHint;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JTextField fromField;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel headingPanel;
    private javax.swing.JPanel reportBody;
    private javax.swing.JPanel reportCards;
    private javax.swing.JPanel revenueActions;
    private javax.swing.JPanel revenueCard;
    private javax.swing.JPanel revenueCopy;
    private javax.swing.JLabel revenueDescription;
    private javax.swing.JButton revenueExport;
    private javax.swing.JLabel revenueHint;
    private javax.swing.JButton revenuePreview;
    private javax.swing.JLabel revenueTitle;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField toField;
    private javax.swing.JLabel toLabel;
    // End of variables declaration//GEN-END:variables
}
