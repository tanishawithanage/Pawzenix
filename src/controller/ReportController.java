package controller;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Window;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import model.ReportData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import view.ViewReports;

/**
 *
 * @author TANISHA SANJANA
 */
public class ReportController {

    private interface RowSource {
        List<Map<String, ?>> load(LocalDate from, LocalDate to) throws SQLException;
    }

    private final ViewReports view;
    private final Map<String, JasperReport> compiled = new HashMap<>();

    public ReportController(ViewReports view) {
        this.view = view;

        LocalDate today = LocalDate.now();
        view.getFromField().setText(today.withDayOfMonth(1).toString());
        view.getToField().setText(today.toString());

        view.getActivityPreview().addActionListener(e ->
                run("appointment_activity", "Appointment activity", ReportData::appointments));
        view.getRevenuePreview().addActionListener(e ->
                run("invoice_revenue", "Invoice revenue & balances", ReportData::revenue));
    }

    private void run(String design, String title, RowSource source) {
        try {
            LocalDate from = readDate(view.getFromField().getText(), "From");
            LocalDate to = readDate(view.getToField().getText(), "To");
            if (from.isAfter(to)) {
                throw new IllegalArgumentException("The From date must not be after the To date.");
            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            List<Map<String, ?>> rows = source.load(from, to);          
            JasperPrint print = fill(design, rows, from, to);          
            show(print, title);                                         
        } catch (IllegalArgumentException | SQLException | JRException e) {
            Input.error(view, e);
        } finally {
            view.setCursor(Cursor.getDefaultCursor());
        }
    }

    private LocalDate readDate(String text, String label) {
        try {
            return Input.date(text);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(label + " date: " + e.getMessage());
        }
    }

    private JasperPrint fill(String design, List<Map<String, ?>> rows, LocalDate from, LocalDate to)
            throws JRException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("FROM_DATE", from.toString());
        parameters.put("TO_DATE", to.toString());
        parameters.put("GENERATED_AT",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return JasperFillManager.fillReport(loadDesign(design), parameters, new JRMapCollectionDataSource(rows));
    }

    private JasperReport loadDesign(String design) throws JRException {
        JasperReport report = compiled.get(design);
        if (report == null) {
            String path = "/reports/" + design + ".jrxml";
            try (InputStream in = ReportController.class.getResourceAsStream(path)) {
                if (in == null) {
                    throw new JRException("Report design not found: " + path
                            + "\nCheck that the reports folder is inside the project's source packages.");
                }
                report = JasperCompileManager.compileReport(in);
            } catch (IOException e) {
                throw new JRException(e);
            }
            compiled.put(design, report);
        }
        return report;
    }

    private void show(JasperPrint print, String title) throws JRException {
        JRViewer viewer = new JRViewer(print);
        Window owner = SwingUtilities.getWindowAncestor(view);
        JDialog dialog = new JDialog(owner, "Pawzenix - " + title, Dialog.ModalityType.MODELESS);
        dialog.setContentPane(viewer);
        dialog.setSize(1000, 740);
        dialog.setLocationRelativeTo(view);
        dialog.setVisible(true);
    }
}
