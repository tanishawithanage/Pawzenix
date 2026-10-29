package controller;

import java.awt.Dialog;
import java.awt.Window;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.CustomException;
import model.ReportDAO;
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
 * Making a Jasper report takes 4 steps:
 *   1. COMPILE  the design file (src/reports/*.jrxml)
 *   2. LOAD     the rows from the database (ReportDAO)
 *   3. FILL     the design with the rows and the dates
 *   4. SHOW     the finished report in a preview window
 */
public class ReportController
{
    private final ViewReports view;
    private final ReportDAO reportDAO = new ReportDAO();

    public ReportController(ViewReports view)
    {
        this.view = view;

        // suggest "this month so far"
        LocalDate today = LocalDate.now();
        view.getFromField().setText(today.withDayOfMonth(1).toString());
        view.getToField().setText(today.toString());

        view.getActivityPreview().addActionListener(e -> showAppointmentReport());
        view.getRevenuePreview().addActionListener(e -> showRevenueReport());
    }

    private void showAppointmentReport()
    {
        try
        {
            LocalDate from = readDate(view.getFromField().getText(), "From");
            LocalDate to = readDate(view.getToField().getText(), "To");
            checkOrder(from, to);

            List<Map<String, ?>> rows = reportDAO.getAppointments(from, to);
            showReport("appointment_activity", "Appointment activity", rows, from, to);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
        }
    }

    private void showRevenueReport()
    {
        try
        {
            LocalDate from = readDate(view.getFromField().getText(), "From");
            LocalDate to = readDate(view.getToField().getText(), "To");
            checkOrder(from, to);

            List<Map<String, ?>> rows = reportDAO.getRevenue(from, to);
            showReport("invoice_revenue", "Invoice revenue & balances", rows, from, to);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
        }
    }

    // Steps 1, 3 and 4: compile the design, fill it with the rows and show it.
    private void showReport(String design, String title, List<Map<String, ?>> rows, LocalDate from, LocalDate to)
    {
        try (InputStream file = ReportController.class.getResourceAsStream("/reports/" + design + ".jrxml"))
        {
            if (file == null)
            {
                throw new CustomException("Report design not found: /reports/" + design + ".jrxml\n"
                        + "Check that the reports folder is inside the project's source packages.");
            }

            // 1. compile the design
            JasperReport report = JasperCompileManager.compileReport(file);

            // 3. fill it: the parameters are printed in the header of the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("FROM_DATE", from.toString());
            parameters.put("TO_DATE", to.toString());
            parameters.put("GENERATED_AT", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRMapCollectionDataSource(rows));

            // 4. show it in a window (it has zoom, page buttons, print and save)
            Window owner = SwingUtilities.getWindowAncestor(view);
            JDialog dialog = new JDialog(owner, "Pawzenix - " + title, Dialog.ModalityType.MODELESS);
            dialog.setContentPane(new JRViewer(print));
            dialog.setSize(1000, 740);
            dialog.setLocationRelativeTo(view);
            dialog.setVisible(true);
        }
        catch (CustomException ex)
        {
            showError(ex.getMessage());
        }
        catch (JRException | IOException ex)
        {
            showError(ex.getMessage());
        }
    }

    // Reads a date like 2026-10-25 from a text box.
    private LocalDate readDate(String text, String label) throws CustomException
    {
        String message = label + " date: Enter a valid date as YYYY-MM-DD (year 1000 to 9999).";
        LocalDate date;
        try
        {
            date = LocalDate.parse(text.trim());
        }
        catch (DateTimeParseException ex)
        {
            throw new CustomException(message);
        }
        if (date.getYear() < 1000 || date.getYear() > 9999)
        {
            throw new CustomException(message);
        }
        return date;
    }

    private void checkOrder(LocalDate from, LocalDate to) throws CustomException
    {
        if (from.isAfter(to))
        {
            throw new CustomException("The From date must not be after the To date.");
        }
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(view, message, "Pawzenix", JOptionPane.ERROR_MESSAGE);
    }
}
