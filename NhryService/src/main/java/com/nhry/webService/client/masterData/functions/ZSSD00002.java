/**
 * ZSSD00002.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:09:26 BST)
 */
package com.nhry.webService.client.masterData.functions;


/**
 *  ZSSD00002 bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class ZSSD00002 implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = ZSSD00002
       Namespace URI = urn:sap-com:document:sap:rfc:functions
       Namespace Prefix = ns1
     */

    /**
     * field for KUNNR
     */
    protected KUNNR_type1 localKUNNR;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localKUNNRTracker = false;

    /**
     * field for NAME1
     */
    protected NAME1_type1 localNAME1;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNAME1Tracker = false;

    /**
     * field for NAME2
     */
    protected NAME2_type1 localNAME2;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNAME2Tracker = false;

    /**
     * field for ORT01
     */
    protected ORT01_type1 localORT01;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localORT01Tracker = false;

    /**
     * field for STRAS
     */
    protected STRAS_type1 localSTRAS;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSTRASTracker = false;

    /**
     * field for TELF1
     */
    protected TELF1_type1 localTELF1;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTELF1Tracker = false;

    /**
     * field for BUKRS
     */
    protected BUKRS_type1 localBUKRS;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localBUKRSTracker = false;

    /**
     * field for REGIO
     */
    protected REGIO_type1 localREGIO;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localREGIOTracker = false;

    /**
     * field for NAME3
     */
    protected NAME3_type1 localNAME3;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNAME3Tracker = false;

    /**
     * field for NAMEV
     */
    protected NAMEV_type1 localNAMEV;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNAMEVTracker = false;

    /**
     * field for LOEVM
     */
    protected com.nhry.webService.client.masterData.functions.LOEVM_type1 localLOEVM;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localLOEVMTracker = false;

    public boolean isKUNNRSpecified() {
        return localKUNNRTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.KUNNR_type1
     */
    public KUNNR_type1 getKUNNR() {
        return localKUNNR;
    }

    /**
     * Auto generated setter method
     * @param param KUNNR
     */
    public void setKUNNR(
        KUNNR_type1 param) {
        localKUNNRTracker = param != null;

        this.localKUNNR = param;
    }

    public boolean isNAME1Specified() {
        return localNAME1Tracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.NAME1_type1
     */
    public NAME1_type1 getNAME1() {
        return localNAME1;
    }

    /**
     * Auto generated setter method
     * @param param NAME1
     */
    public void setNAME1(
        NAME1_type1 param) {
        localNAME1Tracker = param != null;

        this.localNAME1 = param;
    }

    public boolean isNAME2Specified() {
        return localNAME2Tracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.NAME2_type1
     */
    public NAME2_type1 getNAME2() {
        return localNAME2;
    }

    /**
     * Auto generated setter method
     * @param param NAME2
     */
    public void setNAME2(
        NAME2_type1 param) {
        localNAME2Tracker = param != null;

        this.localNAME2 = param;
    }

    public boolean isORT01Specified() {
        return localORT01Tracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.ORT01_type1
     */
    public ORT01_type1 getORT01() {
        return localORT01;
    }

    /**
     * Auto generated setter method
     * @param param ORT01
     */
    public void setORT01(
        ORT01_type1 param) {
        localORT01Tracker = param != null;

        this.localORT01 = param;
    }

    public boolean isSTRASSpecified() {
        return localSTRASTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.STRAS_type1
     */
    public STRAS_type1 getSTRAS() {
        return localSTRAS;
    }

    /**
     * Auto generated setter method
     * @param param STRAS
     */
    public void setSTRAS(
        STRAS_type1 param) {
        localSTRASTracker = param != null;

        this.localSTRAS = param;
    }

    public boolean isTELF1Specified() {
        return localTELF1Tracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.TELF1_type1
     */
    public TELF1_type1 getTELF1() {
        return localTELF1;
    }

    /**
     * Auto generated setter method
     * @param param TELF1
     */
    public void setTELF1(
        TELF1_type1 param) {
        localTELF1Tracker = param != null;

        this.localTELF1 = param;
    }

    public boolean isBUKRSSpecified() {
        return localBUKRSTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.BUKRS_type1
     */
    public BUKRS_type1 getBUKRS() {
        return localBUKRS;
    }

    /**
     * Auto generated setter method
     * @param param BUKRS
     */
    public void setBUKRS(
        BUKRS_type1 param) {
        localBUKRSTracker = param != null;

        this.localBUKRS = param;
    }

    public boolean isREGIOSpecified() {
        return localREGIOTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.REGIO_type1
     */
    public REGIO_type1 getREGIO() {
        return localREGIO;
    }

    /**
     * Auto generated setter method
     * @param param REGIO
     */
    public void setREGIO(
        REGIO_type1 param) {
        localREGIOTracker = param != null;

        this.localREGIO = param;
    }

    public boolean isNAME3Specified() {
        return localNAME3Tracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.NAME3_type1
     */
    public NAME3_type1 getNAME3() {
        return localNAME3;
    }

    /**
     * Auto generated setter method
     * @param param NAME3
     */
    public void setNAME3(
        NAME3_type1 param) {
        localNAME3Tracker = param != null;

        this.localNAME3 = param;
    }

    public boolean isNAMEVSpecified() {
        return localNAMEVTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.NAMEV_type1
     */
    public NAMEV_type1 getNAMEV() {
        return localNAMEV;
    }

    /**
     * Auto generated setter method
     * @param param NAMEV
     */
    public void setNAMEV(
        NAMEV_type1 param) {
        localNAMEVTracker = param != null;

        this.localNAMEV = param;
    }

    public boolean isLOEVMSpecified() {
        return localLOEVMTracker;
    }

    /**
     * Auto generated getter method
     * @return com.nhry.webService.client.masterData.functions.LOEVM_type1
     */
    public com.nhry.webService.client.masterData.functions.LOEVM_type1 getLOEVM() {
        return localLOEVM;
    }

    /**
     * Auto generated setter method
     * @param param LOEVM
     */
    public void setLOEVM(
        com.nhry.webService.client.masterData.functions.LOEVM_type1 param) {
        localLOEVMTracker = param != null;

        this.localLOEVM = param;
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
                    namespacePrefix + ":ZSSD00002", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "ZSSD00002", xmlWriter);
            }
        }

        if (localKUNNRTracker) {
            if (localKUNNR == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "KUNNR cannot be null!!");
            }

            localKUNNR.serialize(new javax.xml.namespace.QName("", "KUNNR"),
                xmlWriter);
        }

        if (localNAME1Tracker) {
            if (localNAME1 == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NAME1 cannot be null!!");
            }

            localNAME1.serialize(new javax.xml.namespace.QName("", "NAME1"),
                xmlWriter);
        }

        if (localNAME2Tracker) {
            if (localNAME2 == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NAME2 cannot be null!!");
            }

            localNAME2.serialize(new javax.xml.namespace.QName("", "NAME2"),
                xmlWriter);
        }

        if (localORT01Tracker) {
            if (localORT01 == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ORT01 cannot be null!!");
            }

            localORT01.serialize(new javax.xml.namespace.QName("", "ORT01"),
                xmlWriter);
        }

        if (localSTRASTracker) {
            if (localSTRAS == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "STRAS cannot be null!!");
            }

            localSTRAS.serialize(new javax.xml.namespace.QName("", "STRAS"),
                xmlWriter);
        }

        if (localTELF1Tracker) {
            if (localTELF1 == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "TELF1 cannot be null!!");
            }

            localTELF1.serialize(new javax.xml.namespace.QName("", "TELF1"),
                xmlWriter);
        }

        if (localBUKRSTracker) {
            if (localBUKRS == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "BUKRS cannot be null!!");
            }

            localBUKRS.serialize(new javax.xml.namespace.QName("", "BUKRS"),
                xmlWriter);
        }

        if (localREGIOTracker) {
            if (localREGIO == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "REGIO cannot be null!!");
            }

            localREGIO.serialize(new javax.xml.namespace.QName("", "REGIO"),
                xmlWriter);
        }

        if (localNAME3Tracker) {
            if (localNAME3 == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NAME3 cannot be null!!");
            }

            localNAME3.serialize(new javax.xml.namespace.QName("", "NAME3"),
                xmlWriter);
        }

        if (localNAMEVTracker) {
            if (localNAMEV == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NAMEV cannot be null!!");
            }

            localNAMEV.serialize(new javax.xml.namespace.QName("", "NAMEV"),
                xmlWriter);
        }

        if (localLOEVMTracker) {
            if (localLOEVM == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "LOEVM cannot be null!!");
            }

            localLOEVM.serialize(new javax.xml.namespace.QName("", "LOEVM"),
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
        public static ZSSD00002 parse(javax.xml.stream.XMLStreamReader reader)
            throws Exception {
            ZSSD00002 object = new ZSSD00002();

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

                        if (!"ZSSD00002".equals(type)) {
                            //find namespace for the prefix
                            String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (ZSSD00002) ExtensionMapper.getTypeObject(nsUri,
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
                        new javax.xml.namespace.QName("", "KUNNR").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "KUNNR").equals(
                            reader.getName())) {
                    object.setKUNNR(KUNNR_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "NAME1").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "NAME1").equals(
                            reader.getName())) {
                    object.setNAME1(NAME1_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "NAME2").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "NAME2").equals(
                            reader.getName())) {
                    object.setNAME2(NAME2_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "ORT01").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "ORT01").equals(
                            reader.getName())) {
                    object.setORT01(ORT01_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "STRAS").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "STRAS").equals(
                            reader.getName())) {
                    object.setSTRAS(STRAS_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "TELF1").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "TELF1").equals(
                            reader.getName())) {
                    object.setTELF1(TELF1_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "BUKRS").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "BUKRS").equals(
                            reader.getName())) {
                    object.setBUKRS(BUKRS_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "REGIO").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "REGIO").equals(
                            reader.getName())) {
                    object.setREGIO(REGIO_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "NAME3").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "NAME3").equals(
                            reader.getName())) {
                    object.setNAME3(NAME3_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "NAMEV").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "NAMEV").equals(
                            reader.getName())) {
                    object.setNAMEV(NAMEV_type1.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if ((reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "LOEVM").equals(
                            reader.getName())) ||
                        new javax.xml.namespace.QName("", "LOEVM").equals(
                            reader.getName())) {
                    object.setLOEVM(com.nhry.webService.client.masterData.functions.LOEVM_type1.Factory.parse(
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
                throw new Exception(e);
            }

            return object;
        }
    } //end of factory class
}
