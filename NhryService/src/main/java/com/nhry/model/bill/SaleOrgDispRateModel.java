package com.nhry.model.bill;

import java.util.List;

/**
 * Created by gongjk on 2016/7/4.
 */
public class SaleOrgDispRateModel {
    private String saleOrg;
    private String salaryMet;
    List<DispNumEntry> dispNumEntries;

    public String getSaleOrg() {
        return saleOrg;
    }

    public void setSaleOrg(String saleOrg) {
        this.saleOrg = saleOrg;
    }

    public String getSalaryMet() {
        return salaryMet;
    }

    public void setSalaryMet(String salaryMet) {
        this.salaryMet = salaryMet;
    }

    public List<DispNumEntry> getDispNumEntries() {
        return dispNumEntries;
    }

    public void setDispNumEntries(List<DispNumEntry> dispNumEntries) {
        this.dispNumEntries = dispNumEntries;
    }
}
