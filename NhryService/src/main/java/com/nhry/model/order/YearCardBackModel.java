package com.nhry.model.order;


import java.math.BigDecimal;

/**
 * Created by gongjk on 2016/12/13.
 */
public class YearCardBackModel {
    private BigDecimal backAmt;
    private String orderNo;

    public BigDecimal getBackAmt() {
        return backAmt;
    }

    public void setBackAmt(BigDecimal backAmt) {
        this.backAmt = backAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
