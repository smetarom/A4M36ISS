package com.redhat.brq.integration.camel;

import com.redhat.brq.integration.examination.supplier.a.ItemReply;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Smetana
 */
public class OrderAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        List<ItemReply> newBody = null;
        Object body = newExchange.getIn().getBody();
        System.out.println("Aggregation: " + oldExchange + ", " + newExchange);
        if (body instanceof ItemReply) {
            newBody = new ArrayList<>();
            newBody.add((ItemReply) body);
        } else {

            newBody = (List<ItemReply>) body;
        }
        if (oldExchange == null) {
            List<ItemReply> itemReplies = new ArrayList<>();
            itemReplies.addAll(newBody);
            newExchange.getIn().setBody(itemReplies);
            return newExchange;
        }
        List<ItemReply> oldBody = (List<ItemReply>) oldExchange.getIn().getBody();
        if (newBody == null) {
            oldBody.add(null);
        } else {
            oldBody.addAll(newBody);
        }
        newExchange.getIn().setBody(oldBody);
        return newExchange;
    }
}
