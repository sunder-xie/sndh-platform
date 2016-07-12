package com.nhry.model.bill;

import com.nhry.model.basic.BaseQueryModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gongjk on 2016/7/12.
 */
public class EmpBranchBillSearch  extends BaseQueryModel implements Serializable{

    private String branchNo;
    private Date startDate;
    private Date endDate;
    private String empNo;
    private Date dispDate;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Date getDispDate() {
        return dispDate;
    }

    public void setDispDate(Date dispDate) {
        this.dispDate = dispDate;
    }
}
