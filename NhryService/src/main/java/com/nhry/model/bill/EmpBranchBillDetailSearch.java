package com.nhry.model.bill;

import com.nhry.model.basic.BaseQueryModel;

import java.util.Date;

/**
 * Created by gongjk on 2016/7/11.
 */
public class EmpBranchBillDetailSearch extends BaseQueryModel {
    private String empNo;
    private Date dispDate;
    private String salesOrg;

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

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg;
    }
}
