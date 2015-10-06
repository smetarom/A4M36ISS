package com.redhat.brq.integration.model;

public class InvoiceStatus {

    private String resolution;
    private String description;

    public InvoiceStatus(String resolution, String description) {
        this.resolution = resolution;
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "InvoiceStatus [resolution=" + resolution + ", description=" + description + "]";
    }
}
