package com.redhat.brq.integration.exercise.accounting;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;


@Path("accounting")
public class AccountingService {
	private static final Logger LOG = Logger.getLogger(AccountingService.class);

	@POST
	@Path("invoice/issue")
	@Produces("application/json")
	@Consumes("application/json")
	public InvoiceIssueReply issueInvoice(Order order) {
		LOG.info("Got request " + order);
		InvoiceIssueReply reply;
		if (order.getTotalPrice() > 0 && order.getTotalPrice() < 1000) {
			reply = new InvoiceIssueReply(1_000_000 + order.getId(), order, "ISSUED");
		}
		else {
			reply = new InvoiceIssueReply(-1, order, "INVALID");
		}
		LOG.info("Sending reply " + reply);
		return reply;
	}
	
}
