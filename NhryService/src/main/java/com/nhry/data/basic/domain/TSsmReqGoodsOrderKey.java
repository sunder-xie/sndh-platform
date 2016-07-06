package com.nhry.data.basic.domain;

import java.util.Date;

public class TSsmReqGoodsOrderKey {
    private String orderNo;

    private Date orderDate;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}