package com.redhat.brq.integration.examination.common;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
	@Id @Column(name = "id")
	private String sku;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "count")
	private int amount;
	
	public Item() {
		super();
	}
	
	public Item(final String sku, final int price, final int amount) {
		super();
		this.sku = sku;
		this.price = new BigDecimal(price);
		this.amount = amount;
	}
	
	public Item(final int price, final int amount) {
		this(null, price, amount);
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(final BigDecimal price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(final int amount) {
		this.amount = amount;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}	

	@Override
	public String toString() {
		return "Item [sku=" + sku + ", price=" + price + ", amount=" + amount
				+ "]";
	}
}