package com.nhry.model.milktrans;

import com.nhry.model.basic.BaseQueryModel;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/7/20.
 */
public class InSideSalOrderSearchModel extends BaseQueryModel implements Serializable{
    private String branchNo;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}
