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
 * Controller for the Reports page.
 *
 * How a Jasper report is produced (the same 4 steps for both reports):
 *   1. COMPILE  the design file (src/reports/*.jrxml) into a JasperReport object
 *   2. LOAD     the rows from MySQL (model.ReportData) and wrap them in a data source
 *   3. FILL     the report with the rows + parameters (dates) -> a JasperPrint (the finished pages)
 *   4. SHOW     the JasperPrint in Jasper's viewer window (JRViewer)
 */
public class ReportController {

    /** Small helper type so one method can run either report: "give me the rows between two dates". */
    private interface RowSource {
        List<Map<String, ?>> load(LocalDate from, LocalDate to) throws SQLException;
    }

    private final ViewReports view;
    // A design is compiled only once, then reused (compiling takes a moment).
    private final Map<String, JasperReport> compiled = new HashMap<>();

    public ReportController(ViewReports view) {
        this.view = view;

        // Suggest "this month so far" so the person can just press Preview.
        LocalDate today = LocalDate.now();
        view.getFromField().setText(today.withDayOfMonth(1).toString());
        view.getToField().setText(today.toString());

        view.getActivityPreview().addActionListener(e ->
                run("appointment_activity", "Appointment activity", ReportData::appointments));
        view.getRevenuePreview().addActionListener(e ->
                run("invoice_revenue", "Invoice revenue & balances", ReportData::revenue));
    }

    /** Runs one report from start to finish. Any problem is shown in an error dialog. */
    private void run(String design, String title, RowSource source) {
        try {
            LocalDate from = readDate(view.getFromField().getText(), "From");
            LocalDate to = readDate(view.getToField().getText(), "To");
            if (from.isAfter(to)) {
                throw new IllegalArgumentException("The From date must not be after the To date.");
            }
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            List<Map<String, ?>> rows = source.load(from, to);          // step 2
            JasperPrint print = fill(design, rows, from, to);          // steps 1 + 3
            show(print, title);                                         // step 4
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

    /** Compiles (once) and fills the design with the rows. The parameters appear in the report header. */
    private JasperPrint fill(String design, List<Map<String, ?>> rows, LocalDate from, LocalDate to)
            throws JRException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("FROM_DATE", from.toString());
        parameters.put("TO_DATE", to.toString());
        parameters.put("GENERATED_AT",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return JasperFillManager.fillReport(loadDesign(design), parameters, new JRMapCollectionDataSource(rows));
    }

    /** Reads /reports/<design>.jrxml from the project and compiles it. */
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

    /** Opens the finished report in a preview window (it has zoom, page navigation, print and save buttons). */
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
