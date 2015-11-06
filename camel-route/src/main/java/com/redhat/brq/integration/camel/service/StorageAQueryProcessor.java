package com.redhat.brq.integration.camel.service;

import com.redhat.brq.integration.examination.supplier.a.ItemReply;
import com.redhat.brq.integration.examination.supplier.a.ItemRequest;
import com.redhat.brq.integration.examination.supplier.a.SupplierA;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * @author Roman Smetana
 */
public class StorageAQueryProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        URL url = new URL("http://localhost:8080/supplier-a/SupplierAService?wsdl");

        QName qname = new QName("urn:com.redhat.sysint.exam:supplier-a", "SupplierAService");

        Service service = Service.create(url, qname);

        SupplierA supplier = service.getPort(SupplierA.class);

        BindingProvider bp = (BindingProvider) supplier;
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "webuser");
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "JBoss.123");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setAmount(1);
        itemRequest.setSku("rhel");

        ItemReply available = supplier.available(itemRequest);

        exchange.getIn().setBody(available);

        Thread.sleep(5000);
    }
}
