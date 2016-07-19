package com.nhry.data.bill.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/27.
 */
public class BranchBillOfCust   implements Serializable {
    private String branchNo;
    private String branchName;
    private String orderNo;//订单号
    private String preorderSource;//订单来源
    private String paymentType;//付款选择（预付款/后付款）
    private String vipCustNo;//订户号
    private String vipName;//订户名称
    private String vipTel;//订户电话
    private String vipMp;//订户手机
    private String address;//订户地址
    private Integer realCollection;//实收款
    private Integer settlementPrice; //结算价
    private String paymentDate;//付款日期
    private String empNo;//送奶工 工号
    private String empName;//送奶工名称
    private Date receiptDate;

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getVipMp() {
        return vipMp;
    }

    public void setVipMp(String vipMp) {
        this.vipMp = vipMp;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPreorderSource() {
        return preorderSource;
    }

    public void setPreorderSource(String preorderSource) {
        this.preorderSource = preorderSource;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getVipCustNo() {
        return vipCustNo;
    }

    public void setVipCustNo(String vipCustNo) {
        this.vipCustNo = vipCustNo;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipTel() {
        return vipTel;
    }

    public void setVipTel(String vipTel) {
        this.vipTel = vipTel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRealCollection() {
        return realCollection;
    }

    public void setRealCollection(Integer realCollection) {
        this.realCollection = realCollection;
    }

    public Integer getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(Integer settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}

