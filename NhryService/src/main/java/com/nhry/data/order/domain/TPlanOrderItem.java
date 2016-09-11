package com.nhry.data.order.domain;

import java.math.BigDecimal;
import java.util.Date;


public class TPlanOrderItem {
    private String itemNo;

    private String orderNo;

    private Date orderDate;

    private String refItemNo;

    private String newRowFlag;
    
    private String newFlag;

    private String matnr;

    private String matnrTxt;
    
    private String unit;

    private Integer qty;

    private BigDecimal salesPrice;

    private String ruleType;

    private Integer dispDays;

    private Integer gapDays;

    private String ruleTxt;

    private String reachTime;
    
    private String reachTimeType;

    private Date startDispDate;
    
    private Date startDate;

    private Date endDispDate;
    
    private String startDispDateStr;

    private String endDispDateStr;

    private String promotion;

    private Integer promDays;

    private String status;

    private Integer buyQty;

    private Integer giftQty;

    private Date createAt;

    private String createBy;

    private String createByTxt;

    private Date lastModified;

    private String lastModifiedBy;

    private String lastModifiedByTxt;
    
    private String giftMatnr;
    
    private String giftUnit;
    
    private Integer dispTotal;
    
    private BigDecimal entryTotal;
    
    private Integer yGrowth;
    
    private Integer yFresh;
    
    private String isDeletedFlag;
    
    private String deletePlansFlag;

	public String getIsDeletedFlag()
	{
		return isDeletedFlag;
	}

	public void setIsDeletedFlag(String isDeletedFlag)
	{
		this.isDeletedFlag = isDeletedFlag;
	}

	public String getDeletePlansFlag()
	{
		return deletePlansFlag;
	}

	public void setDeletePlansFlag(String deletePlansFlag)
	{
		this.deletePlansFlag = deletePlansFlag;
	}

	public String getNewFlag()
	{
		return newFlag;
	}

	public void setNewFlag(String newFlag)
	{
		this.newFlag = newFlag;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Integer getyGrowth()
	{
		return yGrowth;
	}

	public void setyGrowth(Integer yGrowth)
	{
		this.yGrowth = yGrowth;
	}

	public Integer getyFresh()
	{
		return yFresh;
	}

	public void setyFresh(Integer yFresh)
	{
		this.yFresh = yFresh;
	}

	public BigDecimal getEntryTotal()
	{
		return entryTotal;
	}

	public void setEntryTotal(BigDecimal entryTotal)
	{
		this.entryTotal = entryTotal;
	}

	public Integer getDispTotal()
	{
		return dispTotal;
	}

	public void setDispTotal(Integer dispTotal)
	{
		this.dispTotal = dispTotal;
	}

	public String getGiftUnit()
	{
		return giftUnit;
	}

	public void setGiftUnit(String giftUnit)
	{
		this.giftUnit = giftUnit;
	}

	public String getGiftMatnr()
	{
		return giftMatnr;
	}

	public void setGiftMatnr(String giftMatnr)
	{
		this.giftMatnr = giftMatnr;
	}

	public String getMatnrTxt()
	{
		return matnrTxt;
	}

	public void setMatnrTxt(String matnrTxt)
	{
		this.matnrTxt = matnrTxt;
	}

	public String getStartDispDateStr() {
       return startDispDateStr;
    }

    public void setStartDispDateStr(String startDispDateStr) {
       this.startDispDateStr = startDispDateStr == null ? null : startDispDateStr.trim();
    }
    
    public String getEndDispDateStr() {
       return endDispDateStr;
    }

    public void setEndDispDateStr(String endDispDateStr) {
       this.endDispDateStr = endDispDateStr == null ? null : endDispDateStr.trim();
    }
    
    public String getReachTime() {
       return reachTime;
    }

    public void setReachTime(String reachTime) {
       this.reachTime = reachTime == null ? null : reachTime.trim();
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRefItemNo() {
        return refItemNo;
    }

    public void setRefItemNo(String refItemNo) {
        this.refItemNo = refItemNo == null ? null : refItemNo.trim();
    }

    public String getNewRowFlag() {
        return newRowFlag;
    }

    public void setNewRowFlag(String newRowFlag) {
        this.newRowFlag = newRowFlag == null ? null : newRowFlag.trim();
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr == null ? null : matnr.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType == null ? null : ruleType.trim();
    }

    public Integer getDispDays() {
        return dispDays;
    }

    public void setDispDays(Integer dispDays) {
        this.dispDays = dispDays;
    }

    public Integer getGapDays() {
        return gapDays;
    }

    public void setGapDays(Integer gapDays) {
        this.gapDays = gapDays;
    }

    public String getRuleTxt() {
        return ruleTxt;
    }

    public void setRuleTxt(String ruleTxt) {
        this.ruleTxt = ruleTxt == null ? null : ruleTxt.trim();
    }

    public String getReachTimeType() {
        return reachTimeType;
    }

    public void setReachTimeType(String reachTimeType) {
        this.reachTimeType = reachTimeType == null ? null : reachTimeType.trim();
    }
    
    public Date getStartDispDate() {
        return startDispDate;
    }

    public void setStartDispDate(Date startDispDate) {
        this.startDispDate = startDispDate;
    }
    public Date getEndDispDate() {
        return endDispDate;
    }

    public void setEndDispDate(Date endDispDate) {
        this.endDispDate = endDispDate;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Integer getPromDays() {
        return promDays;
    }

    public void setPromDays(Integer promDays) {
        this.promDays = promDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(Integer buyQty) {
        this.buyQty = buyQty;
    }

    public Integer getGiftQty() {
        return giftQty;
    }

    public void setGiftQty(Integer giftQty) {
        this.giftQty = giftQty;
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