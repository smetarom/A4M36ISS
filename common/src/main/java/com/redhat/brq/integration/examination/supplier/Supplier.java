package com.redhat.brq.integration.examination.supplier;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.apache.log4j.Logger;

import com.redhat.brq.integration.examination.common.Item;

public class Supplier {
	static final Logger LOG = Logger.getLogger(Supplier.class);
	

	protected Map<String, Item> inventory = new HashMap<>();

	public Supplier() {
		super();
	}

	@WebMethod(operationName = "available")
	@WebResult(name = "ItemReply")
	public ItemReply isAvailable(@WebParam(name = "ItemRequest") final ItemRequest request) throws UnkownItemException {
		LOG.info("Got request " + request);
		final Item item = inventory.get(request.getSku());
		if (item == null) {
			LOG.error("Unknown SKU");
			throw new UnkownItemException(request.getSku());
		}
		ItemReply reply = new ItemReply(request.getAmount() <= item.getAmount(), item.getPrice());
		LOG.info("Sending reply " + reply);
		return reply;
	}

}