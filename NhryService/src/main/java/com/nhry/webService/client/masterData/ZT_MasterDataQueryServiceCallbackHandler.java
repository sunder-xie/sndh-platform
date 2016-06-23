/**
 * ZT_MasterDataQueryServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:08:57 BST)
 */
package com.nhry.webService.client.masterData;


/**
 *  ZT_MasterDataQueryServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ZT_MasterDataQueryServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ZT_MasterDataQueryServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ZT_MasterDataQueryServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for customerQuery method
     * override this method for handling normal response from customerQuery operation
     */
    public void receiveResultcustomerQuery(
        ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from customerQuery operation
     */
    public void receiveErrorcustomerQuery(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for matWHWQuery method
     * override this method for handling normal response from matWHWQuery operation
     */
    public void receiveResultmatWHWQuery(
        ZT_MasterDataQueryServiceStub.ZMM_POS_24DATAResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from matWHWQuery operation
     */
    public void receiveErrormatWHWQuery(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for mATQUERY method
     * override this method for handling normal response from mATQUERY operation
     */
    public void receiveResultmATQUERY(
        ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from mATQUERY operation
     */
    public void receiveErrormATQUERY(Exception e) {
    }
}
