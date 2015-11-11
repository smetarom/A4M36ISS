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
        System.out.println("ItemFoundPprocessor: " + exchange.getIn().getHeaders());
        itemReply.setPrice(exchange.getIn().getHeader("PRICE", BigDecimal.class));

    }
}
