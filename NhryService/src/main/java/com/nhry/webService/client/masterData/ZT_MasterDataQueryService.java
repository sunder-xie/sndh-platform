/**
 * ZT_MasterDataQueryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:08:57 BST)
 */
package com.nhry.webService.client.masterData;


import com.nhry.webService.client.masterData.functions.*;

/*
 *  ZT_MasterDataQueryService java interface
 */
public interface ZT_MasterDataQueryService {
    /**
     * Auto generated method signature
     *
     * @param zSD_CUSTOMER_DATA_SYN_RFC0
     */
    public ZSD_CUSTOMER_DATA_SYN_RFCResponse customerQuery(
            ZSD_CUSTOMER_DATA_SYN_RFC zSD_CUSTOMER_DATA_SYN_RFC0)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zSD_CUSTOMER_DATA_SYN_RFC0
     */
    public void startcustomerQuery(
            ZSD_CUSTOMER_DATA_SYN_RFC zSD_CUSTOMER_DATA_SYN_RFC0,
            final ZT_MasterDataQueryServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param zMM_POS_24DATA2
     */
    public ZMM_POS_24DATAResponse matWHWQuery(
            ZMM_POS_24DATA zMM_POS_24DATA2)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zMM_POS_24DATA2
     */
    public void startmatWHWQuery(
            ZMM_POS_24DATA zMM_POS_24DATA2,
            final ZT_MasterDataQueryServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param zSD_T005_DATA4
     */
    public ZSD_T005_DATAResponse codeQuery(
            ZSD_T005_DATA zSD_T005_DATA4)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zSD_T005_DATA4
     */
    public void startcodeQuery(
            ZSD_T005_DATA zSD_T005_DATA4,
            final ZT_MasterDataQueryServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param zSD_MATERAIL_DATA_RFC6
     */
    public ZSD_MATERAIL_DATA_RFCResponse mATQUERY(
            ZSD_MATERAIL_DATA_RFC zSD_MATERAIL_DATA_RFC6)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zSD_MATERAIL_DATA_RFC6
     */
    public void startmATQUERY(
            ZSD_MATERAIL_DATA_RFC zSD_MATERAIL_DATA_RFC6,
            final ZT_MasterDataQueryServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param zSD_SALES_ORGANIZATION_RFC8
     */
    public ZSD_SALES_ORGANIZATION_RFCResponse salesQuery(
            ZSD_SALES_ORGANIZATION_RFC zSD_SALES_ORGANIZATION_RFC8)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @param zSD_SALES_ORGANIZATION_RFC8
     */
    public void startsalesQuery(
            ZSD_SALES_ORGANIZATION_RFC zSD_SALES_ORGANIZATION_RFC8,
            final ZT_MasterDataQueryServiceCallbackHandler callback)
        throws java.rmi.RemoteException;

    //
}
