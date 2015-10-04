package com.redhat.brq.integration.examination.supplier;

import java.math.BigDecimal;

public class ItemReply {
	private boolean available;
	private BigDecimal price;
	
	public ItemReply(boolean available, BigDecimal price) {
		super();
		this.available = available;
		this.price = price;
	}	

	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(final boolean available) {
		this.available = available;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(final BigDecimal price) {
		this.price = price;
	}	
}
