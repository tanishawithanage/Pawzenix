package model;

import java.math.BigDecimal;

/**
 * One line of a bill (a row of the bill_items table).
 */
public class BillItem
{
    private int serviceId;
    private String serviceName;     // the name is saved with the item, so old bills keep their original wording
    private int quantity;
    private BigDecimal unitPrice;

    public BillItem()
    {
    }

    public BillItem(int serviceId, String serviceName, int quantity, BigDecimal unitPrice)
    {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    // price x quantity, with two decimal places
    public BigDecimal getTotal()
    {
        return unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2);
    }

    // this text is what the items list on the Billing page shows
    @Override
    public String toString()
    {
        return serviceName + " | " + quantity + " x " + unitPrice.toPlainString()
                + " = LKR " + getTotal().toPlainString();
    }
}
