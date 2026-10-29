package controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import model.CustomException;
import model.DashboardDAO;
import view.ViewDashboard;

/**
 * Controller for the Overview page: the four numbers, today's appointments,
 * the visits of the last 7 days and the upcoming appointments.
 */
public class DashboardController
{
    private final ViewDashboard view;
    private final DashboardDAO dashboardDAO = new DashboardDAO();

    public DashboardController(ViewDashboard view)
    {
        this.view = view;

        view.getFollowupTitle().setText("Upcoming appointments (next 7 days, up to 10)");
        view.getRefreshButton().addActionListener(e -> refresh());

        refresh();
    }

    // Reads everything from the database and shows it on the page.
    private void refresh()
    {
        try
        {
            LocalDate today = LocalDate.now();

            showNumbers(today);
            showTodaySchedule(today);
            showLastWeek(today);
            showUpcoming();

            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            view.getUpdatedLabel().setText("Updated " + time);
        }
        catch (CustomException ex)
        {
            view.getUpdatedLabel().setText("Refresh failed. Previously shown values may be out of date.");
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Pawzenix", JOptionPane.ERROR_MESSAGE);
        }
    }

    // The four cards at the top.
    private void showNumbers(LocalDate today) throws CustomException
    {
        BigDecimal collected = dashboardDAO.getCollected(today);
        BigDecimal outstanding = dashboardDAO.getOutstanding();

        view.getVisitsAmount().setText(String.valueOf(dashboardDAO.countVisits(today)));
        view.getPetsAmount().setText(String.valueOf(dashboardDAO.countPets()));
        view.getCollectedAmount().setText("LKR " + collected.toPlainString());
        view.getOutstandingAmount().setText("LKR " + outstanding.toPlainString());
    }

    // The table of today's appointments.
    private void showTodaySchedule(LocalDate today) throws CustomException
    {
        List<Object[]> rows = dashboardDAO.getTodaySchedule(today);

        // a table the person can only read, not edit
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Time", "Pet", "Owner", "Service", "Care provider", "Status"}, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (Object[] row : rows)
        {
            model.addRow(row);
        }
        view.getScheduleTable().setModel(model);
    }

    // The bar chart: one bar for each of the last 7 days.
    private void showLastWeek(LocalDate today) throws CustomException
    {
        int[] counts = dashboardDAO.getVisitsLastWeek(today);

        // the longest bar is the day with the most visits
        int most = 1;
        for (int count : counts)
        {
            if (count > most)
            {
                most = count;
            }
        }

        JPanel chart = view.getChartPanel();
        chart.removeAll();
        chart.setLayout(new GridLayout(7, 1, 0, 4));

        for (int i = 0; i < 7; i++)
        {
            String day = today.minusDays(6 - i).format(DateTimeFormatter.ofPattern("MM-dd"));

            JProgressBar bar = new JProgressBar(0, most);
            bar.setValue(counts[i]);
            bar.setStringPainted(true);
            bar.setString(String.valueOf(counts[i]));

            JPanel row = new JPanel(new BorderLayout(8, 0));
            row.add(new JLabel(day), BorderLayout.WEST);
            row.add(bar, BorderLayout.CENTER);
            chart.add(row);
        }
        chart.revalidate();
        chart.repaint();
    }

    // The list of upcoming appointments.
    private void showUpcoming() throws CustomException
    {
        List<String> lines = dashboardDAO.getUpcoming(LocalDateTime.now());

        String text = "No upcoming appointments.";
        if (!lines.isEmpty())
        {
            text = String.join("\n", lines);
        }

        JTextArea area = new JTextArea(text);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JPanel panel = view.getFollowupsPanel();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}
