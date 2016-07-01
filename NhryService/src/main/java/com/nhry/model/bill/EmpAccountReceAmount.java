package com.nhry.model.bill;

import java.math.BigDecimal;

/**
 * Created by gongjk on 2016/7/1.
 */
public class EmpAccountReceAmount {
    private String orderNo;
    private String empName;
    private String custName;
    private String address;
    private String mp;
    private BigDecimal amountRece;
    private BigDecimal amountReal;
    private BigDecimal amountGap;
    private String paymentType;
    private String paymentDate;
    private String branchName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public BigDecimal getAmountRece() {
        return amountRece;
    }

    public void setAmountRece(BigDecimal amountRece) {
        this.amountRece = amountRece;
    }

    public BigDecimal getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(BigDecimal amountReal) {
        this.amountReal = amountReal;
    }

    public BigDecimal getAmountGap() {
        return amountGap;
    }

    public void setAmountGap(BigDecimal amountGap) {
        this.amountGap = amountGap;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
