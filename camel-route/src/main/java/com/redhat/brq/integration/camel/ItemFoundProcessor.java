package com.redhat.brq.integration.camel;

import com.redhat.brq.integration.examination.supplier.a.ItemReply;
import org.apache.camel.Exchange;

import java.math.BigDecimal;

/**
 * @author Roman Smetana
 */
public class ItemFoundProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        ItemReply itemReply = new ItemReply();
        itemReply.setAvailable(true);
        itemReply.setPrice(exchange.getIn().getHeader("requestedPrice", BigDecimal.class));
    }
}
