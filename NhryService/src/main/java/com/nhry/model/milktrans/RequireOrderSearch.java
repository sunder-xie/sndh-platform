package com.nhry.model.milktrans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/24.
 */
public class RequireOrderSearch  implements Serializable {
    private String branchNo;
    private Date requireDate;

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
