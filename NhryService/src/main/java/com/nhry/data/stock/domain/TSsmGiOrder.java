package com.nhry.data.stock.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TSsmGiOrder {
    private String orderNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    private String branchNo;

    private String memoTxt;

    private Date syncAt;

    private String status;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    public Date getOrderDate(){
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo == null ? null : branchNo.trim();
    }

    public String getMemoTxt() {
        return memoTxt;
    }

    public void setMemoTxt(String memoTxt) {
        this.memoTxt = memoTxt == null ? null : memoTxt.trim();
    }

    public Date getSyncAt() {
        return syncAt;
    }

    public void setSyncAt(Date syncAt) {
        this.syncAt = syncAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}