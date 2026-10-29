package model;

import java.time.LocalDateTime;

/**
 * One appointment (a row of the appointments table).
 */
public class Appointment
{
    private int id;
    private int petId;
    private int serviceId;
    private Integer staffId;   // null means Unassigned
    private LocalDateTime start;   // date and time the visit starts
    private int duration;   // minutes
    private String status;
    private String notes;
    private String petName;   // names below are only used to show in the table
    private String serviceName;
    private String staffName;
    private int customerId;   // the pet's owner

    public Appointment()
    {
    }

    public Appointment(int id, int petId, int serviceId, Integer staffId, LocalDateTime start, int duration, String status, String notes, String petName, String serviceName, String staffName, int customerId)
    {
        this.id = id;
        this.petId = petId;
        this.serviceId = serviceId;
        this.staffId = staffId;
        this.start = start;
        this.duration = duration;
        this.status = status;
        this.notes = notes;
        this.petName = petName;
        this.serviceName = serviceName;
        this.staffName = staffName;
        this.customerId = customerId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getPetId()
    {
        return petId;
    }

    public void setPetId(int petId)
    {
        this.petId = petId;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
    }

    public Integer getStaffId()
    {
        return staffId;
    }

    public void setStaffId(Integer staffId)
    {
        this.staffId = staffId;
    }

    public LocalDateTime getStart()
    {
        return start;
    }

    public void setStart(LocalDateTime start)
    {
        this.start = start;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getPetName()
    {
        return petName;
    }

    public void setPetName(String petName)
    {
        this.petName = petName;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getStaffName()
    {
        return staffName;
    }

    public void setStaffName(String staffName)
    {
        this.staffName = staffName;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }
}
