package com.redhat.brq.integration.model;

public class Invoice {

    private long invoiceId;
    private Order order;
    private InvoiceStatus status;

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Invoice() {
    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceId=" + invoiceId + ", order=" + order + ", status=" + status + '}';
    }

}
