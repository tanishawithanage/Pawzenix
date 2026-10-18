/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author TANISHA SANJANA
 */
class Input {
    private Input() { }

    static String text(String value, String label, int max, boolean required) {
        value=value.trim();
        if (required && value.isEmpty()) throw new IllegalArgumentException(label+" is required.");
        if (value.length()>max) throw new IllegalArgumentException(label+" must have at most "+max+" characters.");
        return value;
    }

    static String choice(JComboBox<String> box, String label) {
        if (box.getSelectedIndex()<=0) throw new IllegalArgumentException("Select "+label+".");
        return (String)box.getSelectedItem();
    }

    static int number(String value, String label, int min, int max) {
        try {
            int number=Integer.parseInt(value.trim());
            if (number<min || number>max) throw new NumberFormatException();
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(label+" must be a whole number from "+min+" to "+max+".");
        }
    }

    static BigDecimal money(String value) {
        try {
            if (!value.trim().matches("[0-9]+(\\.[0-9]{1,2})?")) throw new NumberFormatException();
            BigDecimal amount=new BigDecimal(value.trim()).setScale(2);
            if (amount.signum()<=0 || amount.compareTo(new BigDecimal("99999999.99"))>0) throw new NumberFormatException();
            return amount;
        } catch (NumberFormatException | ArithmeticException e) {
            throw new IllegalArgumentException("Unit price must be positive, with at most two decimal places.");
        }
    }

    static LocalDate date(String text) {
        try {
            LocalDate date=LocalDate.parse(text.trim(),DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
            if (date.getYear()<1000 || date.getYear()>9999) throw new IllegalArgumentException();
            return date;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Enter a valid date as YYYY-MM-DD (year 1000 to 9999).");
        }
    }

    static LocalTime time(String text) {
        try {
            return LocalTime.parse(text.trim(),DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Enter a valid 24-hour time as HH:mm, for example 09:30.");
        }
    }

    static String phone(String value) {
        String phone=text(value,"Phone",30,false).replaceAll("[\\s()\\-]","");
        if (phone.startsWith("+94")) phone="0"+phone.substring(3);
        if (!phone.isEmpty() && !phone.matches("0[0-9]{9}"))
            throw new IllegalArgumentException("Enter a 10-digit Sri Lankan phone number, or leave it blank.");
        return phone;
    }

    static String email(String value) {
        String email=text(value,"Email",100,false);
        if (!email.isEmpty() && !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))
            throw new IllegalArgumentException("Enter a valid email address, or leave it blank.");
        return email;
    }

    static void error(Component view, Exception e) {
        String message=e.getMessage();
        if (e instanceof SQLException) {
            SQLException sql=(SQLException)e;
            if (sql.getErrorCode()==1062) message="This record already exists (service name or linked appointment).";
            else if (sql.getErrorCode()==1451) message="This record is used by another record and cannot be deleted.";
            else if (sql.getErrorCode()==1452) message="A selected record no longer exists. Refresh and select it again.";
            else if (sql.getSQLState()!=null && sql.getSQLState().startsWith("08")) message="Cannot connect to MySQL. Check XAMPP and DBConnection.\n"+sql.getMessage();
        }
        JOptionPane.showMessageDialog(view,message,"Pawzenix",JOptionPane.ERROR_MESSAGE);
    }

    static boolean confirm(Component view, String text) {
        return JOptionPane.showConfirmDialog(view,text,"Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;
    }

    static DefaultTableModel emptyTable(JTable table, JTextField search) {
        table.clearSelection(); search.setText("");
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        model.setRowCount(0);
        table.setRowSorter(new TableRowSorter<>(model));
        return model;
    }

    static void clearAction(JButton button, Runnable clear) {
        for (java.awt.event.ActionListener listener:button.getActionListeners()) button.removeActionListener(listener);
        button.addActionListener(e->clear.run());
    }    
}
