package com.redhat.brq.integration.service;

import java.util.Map;
import java.util.TreeMap;

import org.apache.camel.ExchangeProperty;

import com.redhat.brq.integration.model.Order;

public final class OrderRepository {

    private static final Map<Long, Order> ORDERS = new TreeMap<>();
    private static long sequence;

    public static void create(Order order) {
        order.setId(++sequence);
        ORDERS.put(order.getId(), order);
    }

    // map long id to property.orderId of camel exchange
    public static Order get(@ExchangeProperty("orderId") long id) {
        return ORDERS.get(id);
    }

    public static void clear() {
        ORDERS.clear();
        sequence = 0;
    }

    private OrderRepository() {
    }
}
