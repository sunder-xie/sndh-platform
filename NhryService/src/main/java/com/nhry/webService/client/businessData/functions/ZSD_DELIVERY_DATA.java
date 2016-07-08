/**
 * ZSD_DELIVERY_DATA.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:09:26 BST)
 */
package com.nhry.webService.client.businessData.functions;


/**
 *  ZSD_DELIVERY_DATA bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class ZSD_DELIVERY_DATA implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions",
            "ZSD_DELIVERY_DATA", "ns1");

    /**
     * field for I_DELIVERY
     */
    protected I_DELIVERY_type1 localI_DELIVERY;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localI_DELIVERYTracker = false;

    /**
     * field for ET_DATA
     */
    protected ET_DATA_type1 localET_DATA;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localET_DATATracker = false;

    /**
     * field for IT_DATUM
     */
    protected IT_DATUM_type1 localIT_DATUM;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIT_DATUMTracker = false;

    /**
     * field for IT_KUNNR
     */
    protected IT_KUNNR_type1 localIT_KUNNR;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIT_KUNNRTracker = false;

    /**
     * field for IT_KUNNR2
     */
    protected IT_KUNNR2_type1 localIT_KUNNR2;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIT_KUNNR2Tracker = false;

    /**
     * field for IT_SO
     */
    protected IT_SO_type1 localIT_SO;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIT_SOTracker = false;

    public boolean isI_DELIVERYSpecified() {
        return localI_DELIVERYTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.businessData.functions.I_DELIVERY_type1
     */
    public I_DELIVERY_type1 getI_DELIVERY() {
        return localI_DELIVERY;
    }

    /**
     * Auto generated setter method
     * @param param I_DELIVERY
     */
    public void setI_DELIVERY(
        I_DELIVERY_type1 param) {
        localI_DELIVERYTracker = param != null;

        this.localI_DELIVERY = param;
    }

    public boolean isET_DATASpecified() {
        return localET_DATATracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.businessData.functions.ET_DATA_type1
     */
    public ET_DATA_type1 getET_DATA() {
        return localET_DATA;
    }

    /**
     * Auto generated setter method
     * @param param ET_DATA
     */
    public void setET_DATA(
        ET_DATA_type1 param) {
        localET_DATATracker = param != null;

        this.localET_DATA = param;
    }

    public boolean isIT_DATUMSpecified() {
        return localIT_DATUMTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.businessData.functions.IT_DATUM_type1
     */
    public IT_DATUM_type1 getIT_DATUM() {
        return localIT_DATUM;
    }

    /**
     * Auto generated setter method
     * @param param IT_DATUM
     */
    public void setIT_DATUM(
        IT_DATUM_type1 param) {
        localIT_DATUMTracker = param != null;

        this.localIT_DATUM = param;
    }

    public boolean isIT_KUNNRSpecified() {
        return localIT_KUNNRTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.businessData.functions.IT_KUNNR_type1
     */
    public IT_KUNNR_type1 getIT_KUNNR() {
        return localIT_KUNNR;
    }

    /**
     * Auto generated setter method
     * @param param IT_KUNNR
     */
    public void setIT_KUNNR(
        IT_KUNNR_type1 param) {
        localIT_KUNNRTracker = param != null;

        this.localIT_KUNNR = param;
    }

    public boolean isIT_KUNNR2Specified() {
        return localIT_KUNNR2Tracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.businessData.functions.IT_KUNNR2_type1
     */
    public IT_KUNNR2_type1 getIT_KUNNR2() {
        return localIT_KUNNR2;
    }

    /**
     * Auto generated setter method
     * @param param IT_KUNNR2
     */
    public void setIT_KUNNR2(
        IT_KUNNR2_type1 param) {
        localIT_KUNNR2Tracker = param != null;

        this.localIT_KUNNR2 = param;
    }

    public boolean isIT_SOSpecified() {
        return localIT_SOTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.businessData.functions.IT_SO_type1
     */
    public IT_SO_type1 getIT_SO() {
        return localIT_SO;
    }

    /**
     * Auto generated setter method
     * @param param IT_SO
     */
    public void setIT_SO(IT_SO_type1 param) {
        localIT_SOTracker = param != null;

        this.localIT_SO = param;
    }

    /**
     *
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(
        final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {
        return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                this, MY_QNAME));
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        serialize(parentQName, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        String prefix = null;
        String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();
        writeStartElement(prefix, namespace, parentQName.getLocalPart(),
            xmlWriter);

        if (serializeType) {
            String namespacePrefix = registerPrefix(xmlWriter,
                    "urn:sap-com:document:sap:rfc:functions");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":ZSD_DELIVERY_DATA", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "ZSD_DELIVERY_DATA", xmlWriter);
            }
        }

        if (localI_DELIVERYTracker) {
            if (localI_DELIVERY == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "I_DELIVERY cannot be null!!");
            }

            localI_DELIVERY.serialize(new javax.xml.namespace.QName("",
                    "I_DELIVERY"), xmlWriter);
        }

        if (localET_DATATracker) {
            if (localET_DATA == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ET_DATA cannot be null!!");
            }

            localET_DATA.serialize(new javax.xml.namespace.QName("", "ET_DATA"),
                xmlWriter);
        }

        if (localIT_DATUMTracker) {
            if (localIT_DATUM == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "IT_DATUM cannot be null!!");
            }

            localIT_DATUM.serialize(new javax.xml.namespace.QName("", "IT_DATUM"),
                xmlWriter);
        }

        if (localIT_KUNNRTracker) {
            if (localIT_KUNNR == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "IT_KUNNR cannot be null!!");
            }

            localIT_KUNNR.serialize(new javax.xml.namespace.QName("", "IT_KUNNR"),
                xmlWriter);
        }

        if (localIT_KUNNR2Tracker) {
            if (localIT_KUNNR2 == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "IT_KUNNR2 cannot be null!!");
            }

            localIT_KUNNR2.serialize(new javax.xml.namespace.QName("",
                    "IT_KUNNR2"), xmlWriter);
        }

        if (localIT_SOTracker) {
            if (localIT_SO == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "IT_SO cannot be null!!");
            }

            localIT_SO.serialize(new javax.xml.namespace.QName("", "IT_SO"),
                xmlWriter);
        }

        xmlWriter.writeEndElement();
    }

    private static String generatePrefix(String namespace) {
        if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
            return "ns1";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Utility method to write an element start tag.
     */
    private void writeStartElement(String prefix,
        String namespace, String localPart,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        String writerPrefix = xmlWriter.getPrefix(namespace);

        if (writerPrefix != null) {
            xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
        } else {
            if (namespace.length() == 0) {
                prefix = "";
            } else if (prefix == null) {
                prefix = generatePrefix(namespace);
            }

            xmlWriter.writeStartElement(prefix, localPart, namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }
    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(String prefix,
        String namespace, String attName,
        String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        String writerPrefix = xmlWriter.getPrefix(namespace);

        if (writerPrefix != null) {
            xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
        } else {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
            xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(String namespace,
        String attName, String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attValue);
        } else {
            xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(String namespace,
        String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        String attributeNamespace = qname.getNamespaceURI();
        String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

        if (attributePrefix == null) {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }

        String attributeValue;

        if (attributePrefix.trim().length() > 0) {
            attributeValue = attributePrefix + ":" + qname.getLocalPart();
        } else {
            attributeValue = qname.getLocalPart();
        }

        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attributeValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                attributeValue);
        }
    }

    /**
     *  method to handle Qnames
     */
    private void writeQName(javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        String namespaceURI = qname.getNamespaceURI();

        if (namespaceURI != null) {
            String prefix = xmlWriter.getPrefix(namespaceURI);

            if (prefix == null) {
                prefix = generatePrefix(namespaceURI);
                xmlWriter.writeNamespace(prefix, namespaceURI);
                xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
                xmlWriter.writeCharacters(prefix + ":" +
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            } else {
                // i.e this is the default namespace
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    qname));
        }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (qnames != null) {
            // we have to store this data until last moment since it is not possible to write any
            // namespace data after writing the charactor data
            StringBuffer stringToWrite = new StringBuffer();
            String namespaceURI = null;
            String prefix = null;

            for (int i = 0; i < qnames.length; i++) {
                if (i > 0) {
                    stringToWrite.append(" ");
                }

                namespaceURI = qnames[i].getNamespaceURI();

                if (namespaceURI != null) {
                    prefix = xmlWriter.getPrefix(namespaceURI);

                    if ((prefix == null) || (prefix.length() == 0)) {
                        prefix = generatePrefix(namespaceURI);
                        xmlWriter.writeNamespace(prefix, namespaceURI);
                        xmlWriter.setPrefix(prefix, namespaceURI);
                    }

                    if (prefix.trim().length() > 0) {
                        stringToWrite.append(prefix).append(":")
                                     .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                } else {
                    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qnames[i]));
                }
            }

            xmlWriter.writeCharacters(stringToWrite.toString());
        }
    }

    /**
     * Register a namespace prefix
     */
    private String registerPrefix(
        javax.xml.stream.XMLStreamWriter xmlWriter, String namespace)
        throws javax.xml.stream.XMLStreamException {
        String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null) {
            prefix = generatePrefix(namespace);

            javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

            while (true) {
                String uri = nsContext.getNamespaceURI(prefix);

                if ((uri == null) || (uri.length() == 0)) {
                    break;
                }

                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    /**
     *  Factory class that keeps the parse method
     */
    public static class Factory {
        private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

        /**
         * static method to create the object
         * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
         *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
         * Postcondition: If this object is an element, the reader is positioned at its end element
         *                If this object is a complex type, the reader is positioned at the end element of its outer element
         */
        public static ZSD_DELIVERY_DATA parse(
            javax.xml.stream.XMLStreamReader reader) throws Exception {
            ZSD_DELIVERY_DATA object = new ZSD_DELIVERY_DATA();

            int event;
            javax.xml.namespace.QName currentQName = null;
            String nillableValue = null;
            String prefix = "";
            String namespaceuri = "";

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                currentQName = reader.getName();

                if (reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                    String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");

                    if (fullTypeName != null) {
                        String nsPrefix = null;

                        if (fullTypeName.indexOf(":") > -1) {
                            nsPrefix = fullTypeName.substring(0,
                                    fullTypeName.indexOf(":"));
                        }

                        nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                        String type = fullTypeName.substring(fullTypeName.indexOf(
                                    ":") + 1);

                        if (!"ZSD_DELIVERY_DATA".equals(type)) {
                            //find namespace for the prefix
                            String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (ZSD_DELIVERY_DATA) ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                while (!reader.isEndElement()) {
                    if (reader.isStartElement()) {
                        if ((reader.isStartElement() &&
                                new javax.xml.namespace.QName("", "I_DELIVERY").equals(
                                    reader.getName())) ||
                                new javax.xml.namespace.QName("", "I_DELIVERY").equals(
                                    reader.getName())) {
                            object.setI_DELIVERY(I_DELIVERY_type1.Factory.parse(
                                    reader));

                            reader.next();
                        } // End of if for expected property start element

                        else
                         if ((reader.isStartElement() &&
                                new javax.xml.namespace.QName("", "ET_DATA").equals(
                                    reader.getName())) ||
                                new javax.xml.namespace.QName("", "ET_DATA").equals(
                                    reader.getName())) {
                            object.setET_DATA(ET_DATA_type1.Factory.parse(
                                    reader));

                            reader.next();
                        } // End of if for expected property start element

                        else
                         if ((reader.isStartElement() &&
                                new javax.xml.namespace.QName("", "IT_DATUM").equals(
                                    reader.getName())) ||
                                new javax.xml.namespace.QName("", "IT_DATUM").equals(
                                    reader.getName())) {
                            object.setIT_DATUM(IT_DATUM_type1.Factory.parse(
                                    reader));

                            reader.next();
                        } // End of if for expected property start element

                        else
                         if ((reader.isStartElement() &&
                                new javax.xml.namespace.QName("", "IT_KUNNR").equals(
                                    reader.getName())) ||
                                new javax.xml.namespace.QName("", "IT_KUNNR").equals(
                                    reader.getName())) {
                            object.setIT_KUNNR(IT_KUNNR_type1.Factory.parse(
                                    reader));

                            reader.next();
                        } // End of if for expected property start element

                        else
                         if ((reader.isStartElement() &&
                                new javax.xml.namespace.QName("", "IT_KUNNR2").equals(
                                    reader.getName())) ||
                                new javax.xml.namespace.QName("", "IT_KUNNR2").equals(
                                    reader.getName())) {
                            object.setIT_KUNNR2(IT_KUNNR2_type1.Factory.parse(
                                    reader));

                            reader.next();
                        } // End of if for expected property start element

                        else
                         if ((reader.isStartElement() &&
                                new javax.xml.namespace.QName("", "IT_SO").equals(
                                    reader.getName())) ||
                                new javax.xml.namespace.QName("", "IT_SO").equals(
                                    reader.getName())) {
                            object.setIT_SO(IT_SO_type1.Factory.parse(
                                    reader));

                            reader.next();
                        } // End of if for expected property start element

                        else {
                            // 3 - A start element we are not expecting indicates an invalid parameter was passed
                            throw new org.apache.axis2.databinding.ADBException(
                                "Unexpected subelement " + reader.getName());
                        }
                    } else {
                        reader.next();
                    }
                } // end of while loop
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }
    } //end of factory class
}