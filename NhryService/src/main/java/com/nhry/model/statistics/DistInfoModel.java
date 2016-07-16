package com.nhry.model.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cbz on 7/16/2016.
 */
public class DistInfoModel {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date beginDate;
    Date endDate;
    String pageNum;
    String pageSize;

    public Date getBeginDate() {
        try {
            if(beginDate != null)
            return format.parse(format.format(beginDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        try {
            if(endDate != null)
            return format.parse(format.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
