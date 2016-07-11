/**
 * T024.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:09:26 BST)
 */
package com.nhry.webService.client.masterData.functions;


/**
 *  T024 bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class T024 implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = T024
       Namespace URI = urn:sap-com:document:sap:rfc:functions
       Namespace Prefix = ns1
     */

    /**
     * field for MANDT
     */
    protected com.nhry.webService.client.masterData.functions.MANDT_type7 localMANDT;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localMANDTTracker = false;

    /**
     * field for EKGRP
     */
    protected com.nhry.webService.client.masterData.functions.EKGRP_type1 localEKGRP;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEKGRPTracker = false;

    /**
     * field for EKNAM
     */
    protected com.nhry.webService.client.masterData.functions.EKNAM_type1 localEKNAM;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEKNAMTracker = false;

    /**
     * field for EKTEL
     */
    protected com.nhry.webService.client.masterData.functions.EKTEL_type1 localEKTEL;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEKTELTracker = false;

    /**
     * field for LDEST
     */
    protected com.nhry.webService.client.masterData.functions.LDEST_type1 localLDEST;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localLDESTTracker = false;

    /**
     * field for TELFX
     */
    protected com.nhry.webService.client.masterData.functions.TELFX_type1 localTELFX;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTELFXTracker = false;

    /**
     * field for TEL_NUMBER
     */
    protected com.nhry.webService.client.masterData.functions.TEL_NUMBER_type1 localTEL_NUMBER;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTEL_NUMBERTracker = false;

    /**
     * field for TEL_EXTENS
     */
    protected com.nhry.webService.client.masterData.functions.TEL_EXTENS_type1 localTEL_EXTENS;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTEL_EXTENSTracker = false;

    /**
     * field for SMTP_ADDR
     */
    protected com.nhry.webService.client.masterData.functions.SMTP_ADDR_type1 localSMTP_ADDR;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSMTP_ADDRTracker = false;

    public boolean isMANDTSpecified() {
        return localMANDTTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.MANDT_type7
     */
    public com.nhry.webService.client.masterData.functions.MANDT_type7 getMANDT() {
        return localMANDT;
    }

    /**
     * Auto generated setter method
     * @param param MANDT
     */
    public void setMANDT(
        com.nhry.webService.client.masterData.functions.MANDT_type7 param) {
        localMANDTTracker = param != null;

        this.localMANDT = param;
    }

    public boolean isEKGRPSpecified() {
        return localEKGRPTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.EKGRP_type1
     */
    public com.nhry.webService.client.masterData.functions.EKGRP_type1 getEKGRP() {
        return localEKGRP;
    }

    /**
     * Auto generated setter method
     * @param param EKGRP
     */
    public void setEKGRP(
        com.nhry.webService.client.masterData.functions.EKGRP_type1 param) {
        localEKGRPTracker = param != null;

        this.localEKGRP = param;
    }

    public boolean isEKNAMSpecified() {
        return localEKNAMTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.EKNAM_type1
     */
    public com.nhry.webService.client.masterData.functions.EKNAM_type1 getEKNAM() {
        return localEKNAM;
    }

    /**
     * Auto generated setter method
     * @param param EKNAM
     */
    public void setEKNAM(
        com.nhry.webService.client.masterData.functions.EKNAM_type1 param) {
        localEKNAMTracker = param != null;

        this.localEKNAM = param;
    }

    public boolean isEKTELSpecified() {
        return localEKTELTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.EKTEL_type1
     */
    public com.nhry.webService.client.masterData.functions.EKTEL_type1 getEKTEL() {
        return localEKTEL;
    }

    /**
     * Auto generated setter method
     * @param param EKTEL
     */
    public void setEKTEL(
        com.nhry.webService.client.masterData.functions.EKTEL_type1 param) {
        localEKTELTracker = param != null;

        this.localEKTEL = param;
    }

    public boolean isLDESTSpecified() {
        return localLDESTTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.LDEST_type1
     */
    public com.nhry.webService.client.masterData.functions.LDEST_type1 getLDEST() {
        return localLDEST;
    }

    /**
     * Auto generated setter method
     * @param param LDEST
     */
    public void setLDEST(
        com.nhry.webService.client.masterData.functions.LDEST_type1 param) {
        localLDESTTracker = param != null;

        this.localLDEST = param;
    }

    public boolean isTELFXSpecified() {
        return localTELFXTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.TELFX_type1
     */
    public com.nhry.webService.client.masterData.functions.TELFX_type1 getTELFX() {
        return localTELFX;
    }

    /**
     * Auto generated setter method
     * @param param TELFX
     */
    public void setTELFX(
        com.nhry.webService.client.masterData.functions.TELFX_type1 param) {
        localTELFXTracker = param != null;

        this.localTELFX = param;
    }

    public boolean isTEL_NUMBERSpecified() {
        return localTEL_NUMBERTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.TEL_NUMBER_type1
     */
    public com.nhry.webService.client.masterData.functions.TEL_NUMBER_type1 getTEL_NUMBER() {
        return localTEL_NUMBER;
    }

    /**
     * Auto generated setter method
     * @param param TEL_NUMBER
     */
    public void setTEL_NUMBER(
        com.nhry.webService.client.masterData.functions.TEL_NUMBER_type1 param) {
        localTEL_NUMBERTracker = param != null;

        this.localTEL_NUMBER = param;
    }

    public boolean isTEL_EXTENSSpecified() {
        return localTEL_EXTENSTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.TEL_EXTENS_type1
     */
    public com.nhry.webService.client.masterData.functions.TEL_EXTENS_type1 getTEL_EXTENS() {
        return localTEL_EXTENS;
    }

    /**
     * Auto generated setter method
     * @param param TEL_EXTENS
     */
    public void setTEL_EXTENS(
        com.nhry.webService.client.masterData.functions.TEL_EXTENS_type1 param) {
        localTEL_EXTENSTracker = param != null;

        this.localTEL_EXTENS = param;
    }

    public boolean isSMTP_ADDRSpecified() {
        return localSMTP_ADDRTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.SMTP_ADDR_type1
     */
    public com.nhry.webService.client.masterData.functions.SMTP_ADDR_type1 getSMTP_ADDR() {
        return localSMTP_ADDR;
    }

    /**
     * Auto generated setter method
     * @param param SMTP_ADDR
     */
    public void setSMTP_ADDR(
        com.nhry.webService.client.masterData.functions.SMTP_ADDR_type1 param) {
        localSMTP_ADDRTracker = param != null;

        this.localSMTP_ADDR = param;
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
                this, parentQName));
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
        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();
        writeStartElement(prefix, namespace, parentQName.getLocalPart(),
            xmlWriter);

        if (serializeType) {
            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "urn:sap-com:document:sap:rfc:functions");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":T024", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "T024", xmlWriter);
            }
        }

        if (localMANDTTracker) {
            if (localMANDT == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "MANDT cannot be null!!");
            }

            localMANDT.serialize(new javax.xml.namespace.QName("", "MANDT"),
                xmlWriter);
        }

        if (localEKGRPTracker) {
            if (localEKGRP == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "EKGRP cannot be null!!");
            }

            localEKGRP.serialize(new javax.xml.namespace.QName("", "EKGRP"),
                xmlWriter);
        }

        if (localEKNAMTracker) {
            if (localEKNAM == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "EKNAM cannot be null!!");
            }

            localEKNAM.serialize(new javax.xml.namespace.QName("", "EKNAM"),
                xmlWriter);
        }

        if (localEKTELTracker) {
            if (localEKTEL == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "EKTEL cannot be null!!");
            }

            localEKTEL.serialize(new javax.xml.namespace.QName("", "EKTEL"),
                xmlWriter);
        }

        if (localLDESTTracker) {
            if (localLDEST == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "LDEST cannot be null!!");
            }

            localLDEST.serialize(new javax.xml.namespace.QName("", "LDEST"),
                xmlWriter);
        }

        if (localTELFXTracker) {
            if (localTELFX == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "TELFX cannot be null!!");
            }

            localTELFX.serialize(new javax.xml.namespace.QName("", "TELFX"),
                xmlWriter);
        }

        if (localTEL_NUMBERTracker) {
            if (localTEL_NUMBER == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "TEL_NUMBER cannot be null!!");
            }

            localTEL_NUMBER.serialize(new javax.xml.namespace.QName("",
                    "TEL_NUMBER"), xmlWriter);
        }

        if (localTEL_EXTENSTracker) {
            if (localTEL_EXTENS == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "TEL_EXTENS cannot be null!!");
            }

            localTEL_EXTENS.serialize(new javax.xml.namespace.QName("",
                    "TEL_EXTENS"), xmlWriter);
        }

        if (localSMTP_ADDRTracker) {
            if (localSMTP_ADDR == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "SMTP_ADDR cannot be null!!");
            }

            localSMTP_ADDR.serialize(new javax.xml.namespace.QName("",
                    "SMTP_ADDR"), xmlWriter);
        }

        xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
            return "ns1";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Utility method to write an element start tag.
     */
    private void writeStartElement(java.lang.String prefix,
        java.lang.String namespace, java.lang.String localPart,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

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
    private void writeAttribute(java.lang.String prefix,
        java.lang.String namespace, java.lang.String attName,
        java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

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
    private void writeAttribute(java.lang.String namespace,
        java.lang.String attName, java.lang.String attValue,
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
    private void writeQNameAttribute(java.lang.String namespace,
        java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String attributeNamespace = qname.getNamespaceURI();
        java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

        if (attributePrefix == null) {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }

        java.lang.String attributeValue;

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
        java.lang.String namespaceURI = qname.getNamespaceURI();

        if (namespaceURI != null) {
            java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

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
            java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
            java.lang.String namespaceURI = null;
            java.lang.String prefix = null;

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
    private java.lang.String registerPrefix(
        javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null) {
            prefix = generatePrefix(namespace);

            javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

            while (true) {
                java.lang.String uri = nsContext.getNamespaceURI(prefix);

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
        public static T024 parse(javax.xml.stream.XMLStreamReader reader)
            throws java.lang.Exception {
            T024 object = new T024();

            int event;
            javax.xml.namespace.QName currentQName = null;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                currentQName = reader.getName();

                if (reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                    java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");

                    if (fullTypeName != null) {
                        java.lang.String nsPrefix = null;

                        if (fullTypeName.indexOf(":") > -1) {
                            nsPrefix = fullTypeName.substring(0,
                                    fullTypeName.indexOf(":"));
                        }

                        nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                        java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                    ":") + 1);

                        if (!"T024".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (T024) com.nhry.webService.client.masterData.functions.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "MANDT").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "MANDT").equals(
                            reader.getName())) {
                    object.setMANDT(com.nhry.webService.client.masterData.functions.MANDT_type7.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "EKGRP").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "EKGRP").equals(
                            reader.getName())) {
                    object.setEKGRP(com.nhry.webService.client.masterData.functions.EKGRP_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "EKNAM").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "EKNAM").equals(
                            reader.getName())) {
                    object.setEKNAM(com.nhry.webService.client.masterData.functions.EKNAM_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "EKTEL").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "EKTEL").equals(
                            reader.getName())) {
                    object.setEKTEL(com.nhry.webService.client.masterData.functions.EKTEL_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "LDEST").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "LDEST").equals(
                            reader.getName())) {
                    object.setLDEST(com.nhry.webService.client.masterData.functions.LDEST_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "TELFX").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "TELFX").equals(
                            reader.getName())) {
                    object.setTELFX(com.nhry.webService.client.masterData.functions.TELFX_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "TEL_NUMBER").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "TEL_NUMBER").equals(
                            reader.getName())) {
                    object.setTEL_NUMBER(com.nhry.webService.client.masterData.functions.TEL_NUMBER_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "TEL_EXTENS").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "TEL_EXTENS").equals(
                            reader.getName())) {
                    object.setTEL_EXTENS(com.nhry.webService.client.masterData.functions.TEL_EXTENS_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "SMTP_ADDR").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "SMTP_ADDR").equals(
                            reader.getName())) {
                    object.setSMTP_ADDR(com.nhry.webService.client.masterData.functions.SMTP_ADDR_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement()) {
                    // 2 - A start element we are not expecting indicates a trailing invalid property
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
