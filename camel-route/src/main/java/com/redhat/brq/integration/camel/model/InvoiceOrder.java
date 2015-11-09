package com.redhat.brq.integration.camel.model;

import java.util.List;

/**
 * @author Roman Smetana
 */
public class InvoiceOrder {
    private Long id;
    private List<InvoiceOrderItem> items;
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InvoiceOrderItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceOrderItem> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
