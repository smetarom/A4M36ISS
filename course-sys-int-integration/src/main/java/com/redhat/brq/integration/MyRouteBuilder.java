package com.redhat.brq.integration;

import com.redhat.brq.integration.exception.InvalidOrderException;
import com.redhat.brq.integration.model.Order;
import com.redhat.brq.integration.service.OrderRepository;
import com.redhat.brq.integration.service.OrderStatusProvider;
import com.redhat.brq.integration.service.OrderValidator;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    @Override
    public void configure() {
        
        restConfiguration()
            .component("restlet") // use camel-restlet component as rest api provider
            .bindingMode(RestBindingMode.json) // binding json to/from POJO (DTO)
            .host("localhost")
            .port(8080)

            .dataFormatProperty("prettyPrint", "true")
            .dataFormatProperty("include", "NON_NULL") //
            .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES");

        // Po přijetí objednávky ve formátu JSON proveďte konverzi na Java objekt
        rest("/orders").consumes("application/json").produces("application/json")
            .post().type(Order.class).to("direct:new-order")
            .get("/{orderId}").outType(Order.class).to("direct:find-order");

        // Po přijetí objednávky přes SOAP proveďte konverzi na Java objekt
        
        
        // Proveďte validaci údajů v objednávce
        from("direct:new-order").id("new-order")
            .bean(OrderRepository.class, "create")
            .log("Received new order: ${body.id}")
            .setHeader("Location", simple("/orders/${body.id}"))
            .setProperty("orderId", simple("${body.id}"))          
                
            .onException(InvalidOrderException.class).handled(true)
                .log("The order is not valid: ${body}.")
                .bean(OrderStatusProvider.class, "orderInvalid")
            .end()
                
            .bean(OrderValidator.class, "validate")
            .to("direct:issue.order");

        
        // výsledek na GET - nevím zda v tomto projektu musí být
        from("direct:find-order").id("find-order")
            .setProperty("orderId", simple("${header.orderId}"))
            .bean(OrderRepository.class, "get")
            .choice()
                .when(body().isNull()).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
            .end();
        
        
        // Proveďte dotaz na sklad, zdali je výrobek k dispozici
        from("direct:issue-order").id("direct:issue.order")
            .log("Issuing new order: ${body}")
            ;
        
        // Pokud ne, pak se souběžně dotažte obou externích dodavatelů na výrobek a jeho cenu

        // Vyberte dodavatele s nižší cenou, pokud je zboží u něj dostupné

        // Pokud je nová cena vyšší než původní cena objednávky pak

        // Pokračujte ve zpracovávání pro VIP zákazníky

        // Stornujte objednávku v případě ostatních

        // Vystavte fakturu v účetnictví

        // Nastartujte XA transakci av jejím rámci

        // Odešlete zprávu s požadavkem na expedici

        // Obsah zprávy se nadefinujte vlastní

        // Změňte stav skladu, pokud byly výrobky k dispozici lokálně

        // Vytvořte a odešlete odpoveď
        
    }

}
