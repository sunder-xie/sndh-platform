package com.nhry.model.stock;

import com.nhry.model.basic.BaseQueryModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by cbz on 7/16/2016.
 */
public class StockModel extends BaseQueryModel implements Serializable {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String branchNo;
    String empNo;

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }


}
