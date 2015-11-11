package com.redhat.brq.integration.camel;

import com.redhat.brq.integration.camel.model.Order;
import org.apache.camel.Exchange;
import org.restlet.data.MediaType;

/**
 * @author Roman Smetana
 */
public class PrepareAccountingProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("PrepareAccountingProcessor" + exchange.getIn().getBody());
        Order o = new Order();
        //exchange.getIn().setBody("{}");
    }
}
