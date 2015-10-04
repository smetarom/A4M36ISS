package com.redhat.brq.integration;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    @Override
    public void configure() {

        // here is a sample which processes the input files
        // (leaving them in place - see the 'noop' flag)
        // then performs content based routing on the message using XPath
        from("file:src/data?noop=true")
            .choice()
                .when(xpath("/person/city = 'London'"))
                    .log("UK message")
                    .to("file:target/messages/uk")
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others");
		
        
        // Po přijetí objednávky ve formátu JSON nebo SOAP proveďte konverzi na Java objekt
		
        // Proveďte validaci údajů v objednávce

        // Proveďte dotaz na sklad, zdali je výrobek k dispozici

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
