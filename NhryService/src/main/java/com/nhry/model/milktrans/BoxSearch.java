package com.nhry.model.milktrans;

import com.nhry.model.basic.BaseQueryModel;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/6/27.
 */
public class BoxSearch extends BaseQueryModel implements Serializable{
    private String status;

    private String empNo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
}
