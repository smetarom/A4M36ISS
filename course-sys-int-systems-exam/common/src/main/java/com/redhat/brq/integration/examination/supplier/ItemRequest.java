package com.redhat.brq.integration.examination.supplier;

public class ItemRequest {
	private String sku;
	private int amount;
	
	public String getSku() {
		return sku;
	}
	public void setSku(final String sku) {
		this.sku = sku;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(final int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "ItemRequest [sku=" + sku + ", amount=" + amount + "]";
	}
}
