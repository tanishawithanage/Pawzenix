/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TANISHA SANJANA
 */
public class Dashboard {
    private int visits,pets;
    private BigDecimal collected,outstanding;
    private LocalDate today;
    private final List<Object[]> schedule=new ArrayList<>();
    private final List<String> upcoming=new ArrayList<>();
    private final int[] weekly=new int[7];

    public int getVisits() { return visits; }
    public int getPets() { return pets; }
    public BigDecimal getCollected() { return collected; }
    public BigDecimal getOutstanding() { return outstanding; }
    public LocalDate getToday() { return today; }
    public List<Object[]> getSchedule() { return schedule; }
    public List<String> getUpcoming() { return upcoming; }
    public int[] getWeekly() { return weekly.clone(); }

    public static Dashboard load() throws SQLException {
        Dashboard data=new Dashboard();
        try (Connection c=DBConnection.getConnection()) {
            c.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            c.setAutoCommit(false);
            try {
                try (PreparedStatement s=c.prepareStatement("SELECT CURRENT_DATE"); ResultSet r=s.executeQuery()) {
                    r.next(); data.today=r.getDate(1).toLocalDate();
                }
                String totals="SELECT (SELECT COUNT(*) FROM appointments WHERE DATE(start_at)=CURRENT_DATE AND status NOT IN ('Cancelled','No-show')) visits, "
                        + "(SELECT COUNT(*) FROM pets) pets, "
                        + "(SELECT COALESCE(SUM(total),0) FROM bills WHERE payment_status='Paid' AND DATE(paid_at)=CURRENT_DATE) collected, "
                        + "(SELECT COALESCE(SUM(total),0) FROM bills WHERE payment_status='Unpaid') outstanding";
                try (PreparedStatement s=c.prepareStatement(totals); ResultSet r=s.executeQuery()) {
                    r.next(); data.visits=r.getInt("visits"); data.pets=r.getInt("pets");
                    data.collected=r.getBigDecimal("collected"); data.outstanding=r.getBigDecimal("outstanding");
                }
                String schedule="SELECT a.start_at,p.name pet,c.name owner,s.name service,t.name staff,a.status "
                        + "FROM appointments a JOIN pets p ON p.id=a.pet_id JOIN customers c ON c.id=p.customer_id "
                        + "JOIN services s ON s.id=a.service_id LEFT JOIN staff t ON t.id=a.staff_id "
                        + "WHERE DATE(a.start_at)=CURRENT_DATE ORDER BY a.start_at,a.id";
                try (PreparedStatement s=c.prepareStatement(schedule); ResultSet r=s.executeQuery()) {
                    while (r.next()) data.schedule.add(new Object[]{r.getTimestamp(1).toLocalDateTime().toLocalTime(),r.getString(2),r.getString(3),r.getString(4),r.getString(5)==null?"Unassigned":r.getString(5),r.getString(6)});
                }
                try (PreparedStatement s=c.prepareStatement("SELECT DATE(start_at),COUNT(*) FROM appointments WHERE start_at>=? AND start_at<? AND status NOT IN ('Cancelled','No-show') GROUP BY DATE(start_at)")) {
                    s.setTimestamp(1,Timestamp.valueOf(data.today.minusDays(6).atStartOfDay()));
                    s.setTimestamp(2,Timestamp.valueOf(data.today.plusDays(1).atStartOfDay()));
                    try (ResultSet r=s.executeQuery()) {
                        while (r.next()) {
                            int day=(int)java.time.temporal.ChronoUnit.DAYS.between(data.today.minusDays(6),r.getDate(1).toLocalDate());
                            data.weekly[day]=r.getInt(2);
                        }
                    }
                }
                String upcoming="SELECT a.start_at,p.name,s.name FROM appointments a JOIN pets p ON p.id=a.pet_id "
                        + "JOIN services s ON s.id=a.service_id WHERE a.start_at>=CURRENT_TIMESTAMP "
                        + "AND a.start_at<TIMESTAMPADD(DAY,7,CURRENT_TIMESTAMP) AND a.status IN ('Booked','Confirmed') ORDER BY a.start_at LIMIT 10";
                try (PreparedStatement s=c.prepareStatement(upcoming); ResultSet r=s.executeQuery()) {
                    while (r.next()) data.upcoming.add(r.getTimestamp(1).toLocalDateTime().toString().replace('T',' ')+" - "+r.getString(2)+" - "+r.getString(3));
                }
                c.commit();
            } catch (SQLException | RuntimeException e) {
                try { c.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        }
        return data;
    }   
}
