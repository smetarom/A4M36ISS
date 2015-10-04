package com.redhat.brq.integration.exercise.accounting;

import java.io.Serializable;

public class OrderItem implements Serializable {
	private static final long serialVersionUID = -8796388121191014358L;
	private long articleId;
	private int count;
	private double unitPrice;

	public long getArticleId() {
		return articleId;
	}
	
	public void setArticleId(long articleId) {
		this.articleId = articleId;
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
	
	public double getPrice() {
		return count * getUnitPrice();
	}

	@Override
	public String toString() {
		return "OrderItem [articleId=" + articleId + ", count=" + count
				+ ", unitPrice=" + unitPrice + "]";
	}
}
