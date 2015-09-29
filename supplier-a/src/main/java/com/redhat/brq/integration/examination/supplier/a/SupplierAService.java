package com.redhat.brq.integration.examination.supplier.a;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.redhat.brq.integration.examination.common.Item;
import com.redhat.brq.integration.examination.supplier.Supplier;

@WebService(name = "SupplierA", serviceName = "SupplierAService", targetNamespace = "urn:com.redhat.sysint.exam:supplier-a")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class SupplierAService extends Supplier {
	public SupplierAService() {
		inventory.put("fedora", new Item(10, 10));
		inventory.put("rhel", new Item(1000, 5));
		inventory.put("ubuntu", new Item(2, 50));
	}	
}
