package controller;

import model.Dashboard;
import view.ViewDashboard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author TANISHA SANJANA
 */

public class DashboardController {
    private final ViewDashboard view;
    private boolean loading;

    public DashboardController(ViewDashboard view) {
        this.view=view;
        view.getFollowupTitle().setText("Upcoming appointments (next 7 days, up to 10)");
        view.getRefreshButton().addActionListener(e->load());
        load();
    }

    private void load() {
        if (loading) return;
        loading=true; view.getRefreshButton().setEnabled(false); view.getUpdatedLabel().setText("Loading...");
        new SwingWorker<Dashboard,Void>() {
            @Override protected Dashboard doInBackground() throws Exception { return Dashboard.load(); }
            @Override protected void done() {
                try {
                    Dashboard data=get();
                    view.getVisitsAmount().setText(String.valueOf(data.getVisits()));
                    view.getPetsAmount().setText(String.valueOf(data.getPets()));
                    view.getCollectedAmount().setText("LKR "+data.getCollected().toPlainString());
                    view.getOutstandingAmount().setText("LKR "+data.getOutstanding().toPlainString());
                    DefaultTableModel table=new DefaultTableModel(new String[]{"Time","Pet","Owner","Service","Care provider","Status"},0) {
                        @Override public boolean isCellEditable(int row,int column) { return false; }
                    };
                    for (Object[] row:data.getSchedule()) table.addRow(row);
                    view.getScheduleTable().setModel(table);
                    JPanel chart=view.getChartPanel(); chart.removeAll(); chart.setLayout(new GridLayout(7,1,0,4));
                    int[] counts=data.getWeekly(); int maximum=1;
                    for (int count:counts) maximum=Math.max(maximum,count);
                    for (int i=0;i<7;i++) {
                        JPanel row=new JPanel(new BorderLayout(8,0));
                        row.add(new JLabel(data.getToday().minusDays(6-i).format(DateTimeFormatter.ofPattern("MM-dd"))),BorderLayout.WEST);
                        JProgressBar bar=new JProgressBar(0,maximum); bar.setValue(counts[i]); bar.setStringPainted(true); bar.setString(String.valueOf(counts[i]));
                        row.add(bar,BorderLayout.CENTER); chart.add(row);
                    }
                    JPanel upcoming=view.getFollowupsPanel(); upcoming.removeAll(); upcoming.setLayout(new BorderLayout());
                    JTextArea text=new JTextArea(data.getUpcoming().isEmpty()?"No upcoming appointments.":String.join("\n",data.getUpcoming()));
                    text.setEditable(false); text.setLineWrap(true); text.setWrapStyleWord(true);
                    upcoming.add(new JScrollPane(text),BorderLayout.CENTER);
                    chart.revalidate(); chart.repaint(); upcoming.revalidate(); upcoming.repaint();
                    view.getUpdatedLabel().setText("Updated "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                } catch (Exception e) {
                    view.getUpdatedLabel().setText("Refresh failed. Previously shown values may be out of date.");
                    Throwable cause=e.getCause(); Input.error(view,cause instanceof Exception?(Exception)cause:e);
                } finally { loading=false; view.getRefreshButton().setEnabled(true); }
            }
        }.execute();
    }
}
