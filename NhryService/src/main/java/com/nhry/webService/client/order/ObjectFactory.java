
package com.nhry.webservice.client.order;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.nhry.webservice.client.order package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTreeCodeItemsByTypeCode_QNAME = new QName("http://dao.server.webservice.nhry.com/", "getTreeCodeItemsByTypeCode");
    private final static QName _GetTreeCodeItemsByTypeCodeResponse_QNAME = new QName("http://dao.server.webservice.nhry.com/", "getTreeCodeItemsByTypeCodeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.nhry.webservice.client.order
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTreeCodeItemsByTypeCode }
     * 
     */
    public GetTreeCodeItemsByTypeCode createGetTreeCodeItemsByTypeCode() {
        return new GetTreeCodeItemsByTypeCode();
    }

    /**
     * Create an instance of {@link GetTreeCodeItemsByTypeCodeResponse }
     * 
     */
    public GetTreeCodeItemsByTypeCodeResponse createGetTreeCodeItemsByTypeCodeResponse() {
        return new GetTreeCodeItemsByTypeCodeResponse();
    }

    /**
     * Create an instance of {@link NhSysCodeItem }
     * 
     */
    public NhSysCodeItem createNhSysCodeItem() {
        return new NhSysCodeItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTreeCodeItemsByTypeCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dao.server.webservice.nhry.com/", name = "getTreeCodeItemsByTypeCode")
    public JAXBElement<GetTreeCodeItemsByTypeCode> createGetTreeCodeItemsByTypeCode(GetTreeCodeItemsByTypeCode value) {
        return new JAXBElement<GetTreeCodeItemsByTypeCode>(_GetTreeCodeItemsByTypeCode_QNAME, GetTreeCodeItemsByTypeCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTreeCodeItemsByTypeCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dao.server.webservice.nhry.com/", name = "getTreeCodeItemsByTypeCodeResponse")
    public JAXBElement<GetTreeCodeItemsByTypeCodeResponse> createGetTreeCodeItemsByTypeCodeResponse(GetTreeCodeItemsByTypeCodeResponse value) {
        return new JAXBElement<GetTreeCodeItemsByTypeCodeResponse>(_GetTreeCodeItemsByTypeCodeResponse_QNAME, GetTreeCodeItemsByTypeCodeResponse.class, null, value);
    }

}
