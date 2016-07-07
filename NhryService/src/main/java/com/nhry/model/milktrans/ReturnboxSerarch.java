package com.nhry.model.milktrans;

import com.nhry.model.basic.BaseQueryModel;

import java.util.Date;

/**
 * Created by gongjk on 2016/7/6.
 */
public class ReturnboxSerarch extends BaseQueryModel{
    private String branchNo;
    private String empNo;
    private Date startDate;
    private Date endDate;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
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
}
