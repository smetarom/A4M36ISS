package com.redhat.brq.integration.camel;

import com.redhat.brq.integration.camel.exception.MoreExpensiveException;
import com.redhat.brq.integration.camel.exception.ValidationException;
import com.redhat.brq.integration.camel.model.Invoice;
import com.redhat.brq.integration.camel.model.Order;
import com.redhat.brq.integration.camel.service.OrderValidator;
import com.redhat.brq.integration.examination.supplier.a.ItemReply;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import java.math.BigDecimal;

public class OrderProcessRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("restlet") // use camel-restlet component as rest api provider
                .bindingMode(RestBindingMode.json) // binding json to/from POJO (DTO)
                .port(7080)

                .dataFormatProperty("prettyPrint", "true")
                .dataFormatProperty("include", "NON_NULL") //
                .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES");

        JaxbDataFormat jaxb = new JaxbDataFormat(true);
        jaxb.setContextPath("com.redhat.brq.integration.camel.model");

        rest("/orders").consumes("application/json").produces("application/json")
                .post().type(Order.class).to("direct:new-order");

        from("direct:new-order").id("new-order")
                .onException(ValidationException.class).handled(true)
                .log(LoggingLevel.ERROR, "Invalid order discarded.")
                .end()
                .onException(MoreExpensiveException.class).handled(true)
                .log(LoggingLevel.ERROR, "Item was more expensive, canceling the order.")
                .end()
                .setHeader("orderAddress", simple("${body.address}"))
                .setHeader("orderId", simple("${body.id}"))
                .bean(OrderValidator.class, "validate")
                .log("Issuing new order: ${body}")
                .split(simple("${body.items}"), new OrderAggregationStrategy())
                    .to("direct:query-storage")
                .end()
                .to("seda:aggregate");

        from("direct:query-storage").id("storage")
                .log("Searching for: ${body}")
                .setHeader("itemId", simple("${body.id}"))
                .setHeader("requestedPrice", simple("${body.price}"))
                .setHeader("requestedCount", simple("${body.count}"))
                .setBody(constant("select * from ITEM where id = :?itemId"))
                .to("jdbc:dataSource?useHeadersAsParameters=true")
                .to("direct:process-item");

        from("direct:process-item").id("process-item")
//                .log("From database: ${body}")
                .choice()
                .when(header("CamelJdbcRowCount").isGreaterThan(0))
                .log("Item with id ${header.itemId} found.")
                .process(new ItemFoundProcessor())
                .otherwise()
                .log("Item with id ${header.itemId} not found.")
//                        .to("seda:query-supplier-a")
//                        .to("seda:query-supplier-b")
                .multicast(new SupplierAggregator()).stopOnException()
                .to("direct:query-supplier-a", "direct:query-supplier-b")
                .parallelProcessing().timeout(1000).end()
                .to("direct:aggregated-suppliers")
                .end();

//        CxfEndpoint cxfEndpoint = new CxfEndpoint();
//        cxfEndpoint.setCamelContext(getContext());
//        cxfEndpoint.setAddress("http://localhost:8080/supplier-a/SupplierAService");
//        cxfEndpoint.setDefaultOperationName("available");
//        cxfEndpoint.setWsdlURL("http://localhost:8080/supplier-a/SupplierAService?wsdl");
//        cxfEndpoint.setServiceName(new QName("urn:com.redhat.sysint.exam:supplier-a", "SupplierAService"));
//        cxfEndpoint.setPortName(new QName("urn:com.redhat.sysint.exam:supplier-a", "SupplierAPort"));
//        cxfEndpoint.setDataFormat(DataFormat.PAYLOAD);
//        cxfEndpoint.setUsername("webuser");
//        cxfEndpoint.setPassword("JBoss.123");
//        cxfEndpoint.setProperties(new HashMap<>());

        ItemReply itemReplyA = new ItemReply();
        itemReplyA.setAvailable(true);
        itemReplyA.setPrice(BigDecimal.valueOf(3.5));

        ItemReply itemReplyB = new ItemReply();
        itemReplyB.setAvailable(true);
        itemReplyB.setPrice(BigDecimal.valueOf(14.35));

        from("direct:query-supplier-a").id("query-supplier-a")
//            .process(new StorageAQueryProcessor())
                .log("Supplier A")
                .log("${body}")
                .log("${header.requestedPrice}")
                .setBody(constant(itemReplyA));
//        .to(cxfEndpoint)
//            .log("${body}");

        from("direct:query-supplier-b").id("query-supplier-b")
                .log("Suppier B")
                .setBody(constant(itemReplyB));

        from("direct:aggregated-suppliers").id("aggregated-suppliers")
                .log("${body}")
                .choice()
                .when(body().isNull())
                .log("Item ${header.itemId} is not available.")
                .when(simple("${body.price} > ${header.requestedPrice}"))
                .setBody(constant(null))
                .log("Item ${header.itemId} is more expensive.")
                .otherwise()
                .log("Item ${header.itemId} was ordered from supplier.");

//        getContext().getComponent("")

        from("seda:aggregate").id("aggregate")
                .log("aggregate")
//                .process(new PrepareAccountingProcessor())

//                .removeHeaders("*")
//                .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.POST))
//                .to("https4://147.251.253.26:8443/accounting/rest/accounting/invoice/issue")
//                .convertBodyTo(String.class)

                .setHeader("replies", body())
                .process(new FakeAccountingAnswer())
                .unmarshal().json(JsonLibrary.Jackson, Invoice.class)
                .convertBodyTo(Invoice.class)
                .log("Received Accounting response: ${body.status}")
                .log("Received Accounting response: ${header.replies}")
                .log("Address: ${header.orderAddress}")
                .choice()
                .to("direct:send-items");

        from("direct:send-items").id("send-items")
                .to("activemq:queue:ORDERS")
                .setBody(header("replies"))
                .split(body())
                .choice()
                .when(simple("${body.inStore} == true"))
                    .setHeader("itemId", simple("${body.id}"))
                    .setHeader("count", simple("${body.count}"))
                    .log("${header.count}")
                    .setBody(constant("update ITEM set COUNT = COUNT - :?count where id = :?itemId"))
                .to("jdbc:dataSource?useHeadersAsParameters=true")
                .end();
    }

    private static class PersistItemsProcessor implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
        }
    }

    private static class FakeAccountingAnswer implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            exchange.getIn().setBody("{\n" +
                    "   \"invoiceId\": 1000001,\n" +
                    "   \"order\":    {\n" +
                    "      \"id\": 1,\n" +
                    "      \"items\": [      {\n" +
                    "         \"articleId\": 10,\n" +
                    "         \"count\": 30,\n" +
                    "         \"unitPrice\": 3\n" +
                    "      }],\n" +
                    "      \"address\":       {\n" +
                    "         \"firstName\": \"Jiri\",\n" +
                    "         \"lastName\": \"Novak\",\n" +
                    "         \"street\": \"Purkynova\",\n" +
                    "         \"city\": \"Brno\",\n" +
                    "         \"zipCode\": \"602 00\"\n" +
                    "      }\n" +
                    "   },\n" +
                    "   \"status\": \"ISSUED\"\n" +
                    "}");
        }
    }
}
