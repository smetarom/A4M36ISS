package com.redhat.brq.integration.camel.model;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

// TASK-3
// Mark the class as csv record, use ';' as separator and crlf set to 'UNIX'
@CsvRecord(separator = ";", crlf = "UNIX")
public class OrderItem {

    // TASK-3
    // Mark this attribute as CSV field, do not forget to set correct position (pos)
    @DataField(pos = 1)
    private String id;

    // TASK-3
    // Mark this attribute as CSV field, do not forget to set correct position (pos)
    @DataField(pos = 2)
    private int count;

    private double unitPrice;

    private int price;

    public OrderItem() {
    }

    public OrderItem(String id, int count, double unitPrice) {
        this.id = id;
        this.count = count;
        this.unitPrice = unitPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return count * getUnitPrice();
    }

    @Override
    public String toString() {
        return "OrderItem [id=" + id + ", count=" + count + ", price=" + price + "]";
    }
}
