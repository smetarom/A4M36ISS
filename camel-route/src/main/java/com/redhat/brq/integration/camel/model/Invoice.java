package com.redhat.brq.integration.camel.model;

/**
 * @author Roman Smetana
 */
public class Invoice {
    private Long invoiceId;
    private InvoiceOrder order;
    private String status;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public InvoiceOrder getOrder() {
        return order;
    }

    public void setOrder(InvoiceOrder order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
