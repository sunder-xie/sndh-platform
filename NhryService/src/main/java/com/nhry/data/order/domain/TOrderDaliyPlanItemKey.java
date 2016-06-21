package com.nhry.data.order.domain;

import java.util.Date;

public class TOrderDaliyPlanItemKey {
    private String orderNo;

    private Date orderDate;

    private String planItemNo;

    private String itemNo;

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

    public String getPlanItemNo() {
        return planItemNo;
    }

    public void setPlanItemNo(String planItemNo) {
        this.planItemNo = planItemNo == null ? null : planItemNo.trim();
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }
}