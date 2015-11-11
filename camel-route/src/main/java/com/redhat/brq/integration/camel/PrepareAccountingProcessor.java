package com.redhat.brq.integration.camel;

import org.apache.camel.Exchange;
import org.restlet.data.MediaType;

/**
 * @author Roman Smetana
 */
public class PrepareAccountingProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("{\"sku\": \"ubuntu\",\"id\": 1,\"address\": {\"firstName\": \"Jiri\",\"lastName\": \"Novak\",\"street\": \"Purkynova\",\"city\": \"Brno\",\"zipCode\": \"602 00\"},\"items\": [{\"articleId\": 10,\"count\": 30,\"unitPrice\": 3}]}");
    }
}
