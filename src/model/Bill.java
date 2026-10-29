package model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * One bill (a row of the bills table). Its lines are BillItem objects.
 */
public class Bill
{
    private int id;
    private int customerId;
    private Integer appointmentId;   // null means the bill has no appointment
    private LocalDate date;
    private BigDecimal total;
    private String paymentMethod;
    private String paymentStatus;
    private String customerName;   // only used to show in the table

    public Bill()
    {
    }

    public Bill(int id, int customerId, Integer appointmentId, LocalDate date, BigDecimal total, String paymentMethod, String paymentStatus, String customerName)
    {
        this.id = id;
        this.customerId = customerId;
        this.appointmentId = appointmentId;
        this.date = date;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.customerName = customerName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public Integer getAppointmentId()
    {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId)
    {
        this.appointmentId = appointmentId;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
}
