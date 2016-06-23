package com.nhry.model.order;

import com.nhry.data.order.domain.OrderRequireItem;
import com.wordnik.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by gongjk on 2016/6/21.
 */
@ApiModel(value = "ReturnOrderModel", description = "要货计划对象")
public class RequireOrderModel implements Serializable {

    private String branchNo;
    private Date requireDate;
    List<OrderRequireItem> entries;
    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public Date getRequireDate() {
        return requireDate;
    }

    public void setRequireDate(Date requireDate) {
        this.requireDate = requireDate;
    }

    public List<OrderRequireItem> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderRequireItem> entries) {
        this.entries = entries;
    }
}