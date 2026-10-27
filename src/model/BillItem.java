package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BillItem {
    private final int serviceId;
    private final String serviceName;
    private final int quantity;
    private final BigDecimal unitPrice;

    public BillItem(int serviceId, String serviceName, int quantity, BigDecimal unitPrice) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    public BigDecimal getTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.UNNECESSARY);
    }
    @Override public String toString() {
        return serviceName + " | " + quantity + " x " + unitPrice.toPlainString() + " = LKR " + getTotal().toPlainString();
    }
}
