package com.redhat.brq.integration.examination.supplier;

public class UnkownItemException extends Exception {

	private static final long serialVersionUID = 5928298379623080674L;

	public UnkownItemException(final String sku) {
		super("Unknown item with SKU = " + sku);
	}
}
