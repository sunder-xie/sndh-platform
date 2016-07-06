package com.nhry.model.milktrans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/24.
 */
public class RequireOrderSearch  implements Serializable {
    private String branchNo;
    private Date requireDate;
    private int preDays;
    private String salesOrg;

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg;
    }

    public int getPreDays() {
        return preDays;
    }

    public void setPreDays(int preDays) {
        this.preDays = preDays;
    }

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
}
