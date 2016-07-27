package com.nhry.model.milktrans;


import java.io.Serializable;
import java.util.Date;

public class SalOrderModel implements Serializable {
    private String branchNo;
    private String dealerNo;
    private Date orderDate;

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}