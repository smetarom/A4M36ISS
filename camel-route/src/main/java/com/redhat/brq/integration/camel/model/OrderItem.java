package com.redhat.brq.integration.camel.model;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";", crlf = "UNIX")
public class OrderItem {

    @DataField(pos = 1)
    private String sku;

    @DataField(pos = 2)
    private int id;

    @DataField(pos = 3)
    private int count;

    private double unitPrice;

    private int price;

    public OrderItem() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OrderItem(String sku, int id, int count, double unitPrice, int price) {
        this.sku = sku;
        this.id = id;
        this.count = count;
        this.unitPrice = unitPrice;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "sku='" + sku + '\'' +
                ", id=" + id +
                ", count=" + count +
                ", unitPrice=" + unitPrice +
                ", price=" + price +
                '}';
    }
}
