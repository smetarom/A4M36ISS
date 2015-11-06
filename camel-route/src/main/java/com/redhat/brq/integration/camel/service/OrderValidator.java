package com.redhat.brq.integration.camel.service;

import com.redhat.brq.integration.camel.exception.ValidationException;
import com.redhat.brq.integration.camel.model.Order;

/**
 * @author Roman Smetana
 */
public class OrderValidator {

    public void validate(Order order) throws ValidationException{
        System.out.println("Validate: " + order);
//        throw new ValidationException();
    }
}
