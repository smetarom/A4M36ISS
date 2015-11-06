package com.redhat.brq.integration.camel;

import org.apache.camel.Exchange;

/**
 * @author Roman Smetana
 */
public class PrepareAccountingProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("{}");
    }
}
