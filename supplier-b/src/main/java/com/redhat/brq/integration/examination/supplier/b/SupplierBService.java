package com.redhat.brq.integration.examination.supplier.b;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.redhat.brq.integration.examination.common.Item;
import com.redhat.brq.integration.examination.supplier.Supplier;

@WebService(name = "SupplierB", serviceName = "SupplierBService", targetNamespace = "urn:com.redhat.sysint.exam:supplier-b")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.BARE)
public class SupplierBService extends Supplier {
	public SupplierBService() {
		inventory.put("fedora", new Item(10, 20));
		inventory.put("rhel", new Item(100, 200));
		inventory.put("ubuntu", new Item(300, 28));
	}	
}
