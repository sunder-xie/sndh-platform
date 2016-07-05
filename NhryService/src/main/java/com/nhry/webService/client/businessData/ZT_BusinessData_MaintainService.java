/**
 * ZT_BusinessData_MaintainService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:08:57 BST)
 */
package com.nhry.webService.client.businessData;


import com.nhry.webService.client.businessData.functions.*;

/*
 *  ZT_BusinessData_MaintainService java interface
 */
public interface ZT_BusinessData_MaintainService {
    /**
     * Auto generated method signature
     *
     * @param zSD_DELIVERY_DATA0
     */
    public ZSD_DELIVERY_DATAResponse deliveryQuery(
            ZSD_DELIVERY_DATA zSD_DELIVERY_DATA0)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zSD_DELIVERY_DATA0
     */
    public void startdeliveryQuery(
            ZSD_DELIVERY_DATA zSD_DELIVERY_DATA0,
            final ZT_BusinessData_MaintainServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param zSD_SALESORDER_DATA_RFC_22
     */
    public ZSD_SALESORDER_DATA_RFC_2Response salesOrderCreate(
            ZSD_SALESORDER_DATA_RFC_2 zSD_SALESORDER_DATA_RFC_22)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zSD_SALESORDER_DATA_RFC_22
     */
    public void startsalesOrderCreate(
            ZSD_SALESORDER_DATA_RFC_2 zSD_SALESORDER_DATA_RFC_22,
            final ZT_BusinessData_MaintainServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param zSD_REQUISITION_CREATE_RFC4
     */
    public ZSD_REQUISITION_CREATE_RFCResponse requisitionCreate(
            ZSD_REQUISITION_CREATE_RFC zSD_REQUISITION_CREATE_RFC4)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zSD_REQUISITION_CREATE_RFC4
     */
    public void startrequisitionCreate(
            ZSD_REQUISITION_CREATE_RFC zSD_REQUISITION_CREATE_RFC4,
            final ZT_BusinessData_MaintainServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    //
}
