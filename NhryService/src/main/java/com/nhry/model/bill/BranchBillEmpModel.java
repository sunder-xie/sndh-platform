package com.nhry.model.bill;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gongjk on 2016/6/29.
 */
public class BranchBillEmpModel {
    private String empNo;
    private String empName;
    private BigDecimal salePrice;
    private BigDecimal billPirce;
    private List<BranchBillEmpItemModel> entries;

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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getBillPirce() {
        return billPirce;
    }

    public void setBillPirce(BigDecimal billPirce) {
        this.billPirce = billPirce;
    }

    public List<BranchBillEmpItemModel> getEntries() {
        return entries;
    }

    public void setEntries(List<BranchBillEmpItemModel> entries) {
        this.entries = entries;
    }
}
