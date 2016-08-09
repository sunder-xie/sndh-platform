package com.nhry.data.promotion.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Promotion {
    private String promNo;

    private String promDes;

    private String promType;

    private String salesOrg;

    private String salesDist;

    private String customerType;

    private String promStat;

    private Date planStartTime;

    private Date planStopTime;

    private Date buyStartTime;

    private Date buyStopTime;

    private BigDecimal buyAmt;

    private BigDecimal giftAmt;

    private Date createAt;

    private String createBy;

    private String createByTxt;

    private Date lastModified;

    private String lastModifiedBy;

    private String lastModifiedByTxt;

    public String getPromNo() {
        return promNo;
    }

    public void setPromNo(String promNo) {
        this.promNo = promNo == null ? null : promNo.trim();
    }

    public String getPromDes() {
        return promDes;
    }

    public void setPromDes(String promDes) {
        this.promDes = promDes == null ? null : promDes.trim();
    }

    public String getPromType() {
        return promType;
    }

    public void setPromType(String promType) {
        this.promType = promType == null ? null : promType.trim();
    }

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg == null ? null : salesOrg.trim();
    }

    public String getSalesDist() {
        return salesDist;
    }

    public void setSalesDist(String salesDist) {
        this.salesDist = salesDist == null ? null : salesDist.trim();
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType == null ? null : customerType.trim();
    }

    public String getPromStat() {
        return promStat;
    }

    public void setPromStat(String promStat) {
        this.promStat = promStat == null ? null : promStat.trim();
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanStopTime() {
        return planStopTime;
    }

    public void setPlanStopTime(Date planStopTime) {
        this.planStopTime = planStopTime;
    }

    public Date getBuyStartTime() {
        return buyStartTime;
    }

    public void setBuyStartTime(Date buyStartTime) {
        this.buyStartTime = buyStartTime;
    }

    public Date getBuyStopTime() {
        return buyStopTime;
    }

    public void setBuyStopTime(Date buyStopTime) {
        this.buyStopTime = buyStopTime;
    }

    public BigDecimal getBuyAmt() {
        return buyAmt;
    }

    public void setBuyAmt(BigDecimal buyAmt) {
        this.buyAmt = buyAmt;
    }

    public BigDecimal getGiftAmt() {
        return giftAmt;
    }

    public void setGiftAmt(BigDecimal giftAmt) {
        this.giftAmt = giftAmt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateByTxt() {
        return createByTxt;
    }

    public void setCreateByTxt(String createByTxt) {
        this.createByTxt = createByTxt == null ? null : createByTxt.trim();
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
        this.lastModifiedBy = lastModifiedBy == null ? null : lastModifiedBy.trim();
    }

    public String getLastModifiedByTxt() {
        return lastModifiedByTxt;
    }

    public void setLastModifiedByTxt(String lastModifiedByTxt) {
        this.lastModifiedByTxt = lastModifiedByTxt == null ? null : lastModifiedByTxt.trim();
    }
}