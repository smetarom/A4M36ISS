package com.redhat.brq.integration.camel.model;

/**
 * @author Roman Smetana
 */
public class InvoiceOrderItem {
    private Long articleId;
    private Long count;
    private Integer unitPrice;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }
}
