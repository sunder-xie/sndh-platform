package com.nhry.model.bill;

import java.util.List;

/**
 * Created by gongjk on 2016/8/29.
 */
public class CollectOrderSearchModel {
    private String paymentmehod ;
    private List<String> orders;

    public String getPaymentmehod() {
        return paymentmehod;
    }

    public void setPaymentmehod(String paymentmehod) {
        this.paymentmehod = paymentmehod;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
