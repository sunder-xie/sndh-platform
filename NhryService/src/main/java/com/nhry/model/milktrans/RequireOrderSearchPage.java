package com.nhry.model.milktrans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/24.
 */
public class RequireOrderSearchPage implements Serializable {
    private String dealerId;
    private Date requiredDate;
    private String pageNum = "1";
    private String pageSize = "10";

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}

