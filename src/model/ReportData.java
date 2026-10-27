package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportData {
    public static List<Map<String,?>> appointments(LocalDate from,LocalDate to) throws SQLException {
        String sql="SELECT a.id,a.start_at,p.name pet,c.name customer,s.name service,COALESCE(t.name,'Unassigned') staff,a.status "
                + "FROM appointments a JOIN pets p ON p.id=a.pet_id JOIN customers c ON c.id=p.customer_id "
                + "JOIN services s ON s.id=a.service_id LEFT JOIN staff t ON t.id=a.staff_id "
                + "WHERE a.start_at>=? AND a.start_at<? ORDER BY a.start_at,a.id";
        List<Map<String,?>> rows=new ArrayList<>();
        try (Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement(sql)) {
            s.setTimestamp(1,Timestamp.valueOf(from.atStartOfDay()));
            s.setTimestamp(2,Timestamp.valueOf(to.plusDays(1).atStartOfDay()));
            try (ResultSet r=s.executeQuery()) {
                while (r.next()) {
                    Map<String,Object> row=new LinkedHashMap<>();
                    row.put("id",r.getInt("id")); row.put("date",r.getTimestamp("start_at").toLocalDateTime().toString().replace('T',' '));
                    for (String field:new String[]{"pet","customer","service","staff","status"}) row.put(field,r.getString(field));
                    rows.add(row);
                }
            }
        }
        return rows;
    }

    public static List<Map<String,?>> revenue(LocalDate from,LocalDate to) throws SQLException {
        String sql="SELECT b.id,b.bill_date,c.name customer,i.service_name service,i.quantity,i.unit_price," 
                + "(i.quantity*i.unit_price) line_total,b.payment_status "
                + "FROM bills b JOIN customers c ON c.id=b.customer_id JOIN bill_items i ON i.bill_id=b.id "
                + "JOIN services s ON s.id=i.service_id WHERE b.bill_date BETWEEN ? AND ? ORDER BY b.bill_date,b.id,i.id";
        List<Map<String,?>> rows=new ArrayList<>();
        try (Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement(sql)) {
            s.setDate(1,Date.valueOf(from)); s.setDate(2,Date.valueOf(to));
            try (ResultSet r=s.executeQuery()) {
                while (r.next()) {
                    Map<String,Object> row=new LinkedHashMap<>();
                    row.put("id",r.getInt("id")); row.put("date",r.getDate("bill_date").toString());
                    row.put("customer",r.getString("customer")); row.put("service",r.getString("service"));
                    row.put("quantity",r.getInt("quantity")); row.put("unit_price",r.getBigDecimal("unit_price"));
                    row.put("line_total",r.getBigDecimal("line_total")); row.put("status",r.getString("payment_status"));
                    rows.add(row);
                }
            }
        }
        return rows;
    }
}
