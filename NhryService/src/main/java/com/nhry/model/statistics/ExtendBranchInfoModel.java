package com.nhry.model.statistics;

import com.nhry.utils.date.Date;

/**
 * Created by huaguan on 2016/8/4.
 */
public class ExtendBranchInfoModel extends BranchInfoModel {
    String empNo;
    Date orderDateStart;
    Date orderDateEnd;

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Date getOrderDateStart() {
        return orderDateStart;
    }

    public void setOrderDateStart(Date orderDateStart) {
        this.orderDateStart = orderDateStart;
    }

    public Date getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(Date orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }
}
