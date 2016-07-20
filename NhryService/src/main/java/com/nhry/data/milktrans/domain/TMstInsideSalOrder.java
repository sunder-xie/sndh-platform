package com.nhry.data.milktrans.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/30.
 */
public class TMstInsideSalOrder implements Serializable {
    private String insOrderNo;
    private Date orderDate;
    private String dispOrderNo;
    private String salEmpNo;
    private String empName;
    private String branchNo;
    private String branchName;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getInsOrderNo() {
        return insOrderNo;
    }

    public void setInsOrderNo(String insOrderNo) {
        this.insOrderNo = insOrderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getDispOrderNo() {
        return dispOrderNo;
    }

    public void setDispOrderNo(String dispOrderNo) {
        this.dispOrderNo = dispOrderNo;
    }

    public String getSalEmpNo() {
        return salEmpNo;
    }

    public void setSalEmpNo(String salEmpNo) {
        this.salEmpNo = salEmpNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}
