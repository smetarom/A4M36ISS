A4M36ISS
========

Zatim zkopirovany vychozi stav ukazkoveho repozitare

This project serves as a context for the A4M36ISS course examination projects. It provides a standalone Docker file which simulates multiple information integrations that should be integrated in the workflow defined by an [assignment](https://developer.jboss.org/wiki/SystemIntegrationWithJBoss#jive_content_id_Zvren_prce).
The Docker image is published at [Docker Hub](https://hub.docker.com/r/jpechane/course-sys-int-systems/).

## How to run
``docker run -it -p 9092:9092 -p 8080:8080 -p 8443:8443 jpechane/course-sys-int-systems``

## How to build
``mvn clean install``
``docker build -t jpechane/course-sys-int-systems docker``

## Service endpoints exposed
### Inventory
#### Query
* H2 Database table `INVENTORYTABLE`
 * JDBC URL `jdbc:h2:tcp://localhost/~/exam`
 * Credentials `sa/sa`
 * Driver version `1.3.173`
* Use command `java -jar h2-1.3.173.jar` (or any other JDBC viewer) to run web GUI and analyze the structure and the content of the table

### Accounting
#### Input
* Endpoint exposed at `https://localhost:8443/accounting/rest/accounting/invoice/issue`, using `POST` method
* Server certificate located at `docker/keystore.jks`
* Example of JSON message
```
{
	"id": 1,
	"address": {
		"firstName": "Jiri",
		"lastName": "Novak",
		"street": "Purkynova",
		"city": "Brno",
		"zipCode": "602 00"
	},
	"items": [
		{
			"articleId": 10,
			"count": 30,
			"unitPrice": 3
		}
	]
}
```

#### Output
* Example of JSON message
```
{
   "invoiceId": 1000001,
   "order":    {
      "id": 1,
      "items": [      {
         "articleId": 10,
         "count": 30,
         "unitPrice": 3
      }],
      "address":       {
         "firstName": "Jiri",
         "lastName": "Novak",
         "street": "Purkynova",
         "city": "Brno",
         "zipCode": "602 00"
      }
   },
   "status": "ISSUED"
}
```

#### Behaviour
Accounting either issues invoices (status `ISSUED`) and assigns invoice id as order id incremented by one million.
If the total order price is either zero or less or over 1000 then it rejects invoce (status `INVALID`) and sets invoice id to `-1`.

### Supplier A
#### Contract
* http://localhost:8080/supplier-a/SupplierAService?wsdl
* Server certificate located at `docker/keystore.jks`
* Secured by basic authentication with credentials `webuser/JBoss.123`
#### Loaded data

|SKU    | Price | Amount |
|-------|-------|--------|
|fedora | 10    | 10     |
|rhel   | 1000  | 5      |
|ubuntu | 2     | 50     |

#### Behaviour
If SKU does not exist then a fault is thrown. If amount queried is greater than available then the `false` is returned.

### Supplier B
#### Contract
* http://localhost:8080/supplier-b/SupplierBService?wsdl
* Server certificate located at `docker/keystore.jks`
* Secured by https client authentication, use `docker/keystore.jks`
#### Loaded data

|SKU    | Price | Amount |
|-------|-------|--------|
|fedora | 10    | 20     |
|rhel   | 100   | 200    |
|ubuntu | 300   | 28     |

#### Behaviour
If SKU does not exist then a fault is thrown. If amount queried is greater than available then the `false` is returned.
