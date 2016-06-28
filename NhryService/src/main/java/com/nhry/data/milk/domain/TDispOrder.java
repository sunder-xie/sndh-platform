package com.nhry.data.milk.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TDispOrder extends TDispOrderKey {
    private Date dispDate;

    private String branchNo;

    private String dispLineNo;

    private String mstCustNo;

    private BigDecimal amt;

    private BigDecimal confirmAmt;

    private String reachTimeType;

    private String paymentType;

    private String status;

    private String memoTxt;

    private Integer dispOrder;

    private String dispEmpNo;

    private String hzOrderNo;

    private String dayAcctFlag;

    private Date createAt;

    private String createBy;

    private String createByTxt;

    private Date lastModified;

    private String lastModifiedBy;

    private String lastModifiedByTxt;

    private String paymentOrderNo;

    private String subscribeOrderNo;
    
    private Integer totalQty;
    
	public Integer getTotalQty()
	{
		return totalQty;
	}

	public void setTotalQty(Integer totalQty)
	{
		this.totalQty = totalQty;
	}

	public Date getDispDate() {
        return dispDate;
    }

    public void setDispDate(Date dispDate) {
        this.dispDate = dispDate;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo == null ? null : branchNo.trim();
    }

    public String getDispLineNo() {
        return dispLineNo;
    }

    public void setDispLineNo(String dispLineNo) {
        this.dispLineNo = dispLineNo == null ? null : dispLineNo.trim();
    }

    public String getMstCustNo() {
        return mstCustNo;
    }

    public void setMstCustNo(String mstCustNo) {
        this.mstCustNo = mstCustNo == null ? null : mstCustNo.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getConfirmAmt() {
        return confirmAmt;
    }

    public void setConfirmAmt(BigDecimal confirmAmt) {
        this.confirmAmt = confirmAmt;
    }

    public String getReachTimeType() {
        return reachTimeType;
    }

    public void setReachTimeType(String reachTimeType) {
        this.reachTimeType = reachTimeType == null ? null : reachTimeType.trim();
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMemoTxt() {
        return memoTxt;
    }

    public void setMemoTxt(String memoTxt) {
        this.memoTxt = memoTxt == null ? null : memoTxt.trim();
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getDispEmpNo() {
        return dispEmpNo;
    }

    public void setDispEmpNo(String dispEmpNo) {
        this.dispEmpNo = dispEmpNo == null ? null : dispEmpNo.trim();
    }

    public String getHzOrderNo() {
        return hzOrderNo;
    }

    public void setHzOrderNo(String hzOrderNo) {
        this.hzOrderNo = hzOrderNo == null ? null : hzOrderNo.trim();
    }

    public String getDayAcctFlag() {
        return dayAcctFlag;
    }

    public void setDayAcctFlag(String dayAcctFlag) {
        this.dayAcctFlag = dayAcctFlag == null ? null : dayAcctFlag.trim();
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

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo == null ? null : paymentOrderNo.trim();
    }

    public String getSubscribeOrderNo() {
        return subscribeOrderNo;
    }

    public void setSubscribeOrderNo(String subscribeOrderNo) {
        this.subscribeOrderNo = subscribeOrderNo == null ? null : subscribeOrderNo.trim();
    }
}