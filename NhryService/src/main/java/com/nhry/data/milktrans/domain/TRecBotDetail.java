package com.nhry.data.milktrans.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/27.
 */
public class TRecBotDetail implements Serializable {
    private String detLsh;      //明细流水号
    private String retLsh;      //回瓶流水号
    private String spec;        //规格
    private int receiveNum;     //应收数量
    private int realNum;        //实收数量
    private String createBy;
    private String createByTxt;
    private Date lastModified;
    private String lastModifiedBy;
    private String lastModifiedByTxt;

    public String getDetLsh() {
        return detLsh;
    }

    public void setDetLsh(String detLsh) {
        this.detLsh = detLsh;
    }

    public String getRetLsh() {
        return retLsh;
    }

    public void setRetLsh(String retLsh) {
        this.retLsh = retLsh;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    public int getRealNum() {
        return realNum;
    }

    public void setRealNum(int realNum) {
        this.realNum = realNum;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateByTxt() {
        return createByTxt;
    }

    public void setCreateByTxt(String createByTxt) {
        this.createByTxt = createByTxt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedByTxt() {
        return lastModifiedByTxt;
    }

    public void setLastModifiedByTxt(String lastModifiedByTxt) {
        this.lastModifiedByTxt = lastModifiedByTxt;
    }
}
