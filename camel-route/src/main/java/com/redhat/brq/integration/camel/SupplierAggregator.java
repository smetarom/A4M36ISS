package com.redhat.brq.integration.camel;

import com.redhat.brq.integration.examination.supplier.a.ItemReply;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.math.BigDecimal;

/**
 * @author Roman Smetana
 */
public class SupplierAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        ItemReply newBody = (ItemReply) newExchange.getIn().getBody();
        if (oldExchange == null) {
            if (!newBody.isAvailable()) {
                newExchange.getIn().setBody(null);
            }
            return newExchange;
        }

        ItemReply oldBody = (ItemReply) oldExchange.getIn().getBody();

        if (oldBody.isAvailable() && newBody.isAvailable()) {
            if (oldBody.getPrice().compareTo(newBody.getPrice()) > 0) {
                newExchange.getIn().setBody(createItemReply(true, newBody.getPrice()));
            } else {
                newExchange.getIn().setBody(createItemReply(true, oldBody.getPrice()));
            }
            return newExchange;
        } else if (newBody.isAvailable()) {
            newExchange.getIn().setBody(createItemReply(true, newBody.getPrice()));
            return newExchange;
        }

        newExchange.getIn().setBody(null);
        return newExchange;
    }

    private ItemReply createItemReply(boolean available, BigDecimal price) {
        ItemReply itemReply = new ItemReply();
        itemReply.setAvailable(available);
        itemReply.setPrice(price);
        return itemReply;
    }
}
