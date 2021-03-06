package com.redhat.brq.integration.examination.supplier.a;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 *
 */
@WebService(name = "SupplierA", targetNamespace = "urn:com.redhat.sysint.exam:supplier-a")
public interface SupplierA {


	/**
	 *
	 * @param itemRequest
	 * @return
	 *     returns exam.sysint.redhat.com.supplier_a.ItemReply
	 * @throws UnkownItemException
	 */
	@WebMethod
	@WebResult(name = "ItemReply", targetNamespace = "")
	@RequestWrapper(localName = "available", targetNamespace = "urn:com.redhat.sysint.exam:supplier-a", className = "exam.sysint.redhat.com.supplier_a.Available")
	@ResponseWrapper(localName = "availableResponse", targetNamespace = "urn:com.redhat.sysint.exam:supplier-a", className = "exam.sysint.redhat.com.supplier_a.AvailableResponse")
	public ItemReply available(
			@WebParam(name = "ItemRequest", targetNamespace = "")
			ItemRequest itemRequest)
			throws UnkownItemException
	;

}