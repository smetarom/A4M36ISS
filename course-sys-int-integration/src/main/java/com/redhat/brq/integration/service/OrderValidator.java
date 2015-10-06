package com.redhat.brq.integration.service;

import com.redhat.brq.integration.exception.InvalidOrderException;
import com.redhat.brq.integration.model.Address;
import com.redhat.brq.integration.model.Order;
import com.redhat.brq.integration.model.OrderItem;
import java.util.List;

public final class OrderValidator {

    public static void validate(Order order) throws InvalidOrderException {
        final Address address = order.getAddress();
        if (address == null
                || address.getCity() == null
                || address.getFirstName() == null
                || address.getLastName() == null
                || address.getStreet() == null
                || address.getZipCode() == null) {
            throw new InvalidOrderException();
        }
        final List<OrderItem> items = order.getItems();
        if (items == null || items.isEmpty()) {
            throw new InvalidOrderException();
        } else {
            for (OrderItem item : items) {
                if (item.getArticleId() < 0
                        || item.getCount() < 0
                        || item.getPrice() < 0
                        || item.getUnitPrice() < 0) {
                    throw new InvalidOrderException();
                }
            }
        }
    }

    private OrderValidator() {
    }
}
