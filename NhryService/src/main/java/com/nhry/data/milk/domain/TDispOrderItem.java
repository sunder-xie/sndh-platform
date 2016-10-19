package com.nhry.data.milk.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TDispOrderItem extends TDispOrderItemKey {
    private String matnr;
    
    private String confirmMatnr;

    private BigDecimal confirmQty;

    private BigDecimal qty;

    private String unit;

    private BigDecimal price;

    private BigDecimal amt;

	private BigDecimal remainAmt;

    private BigDecimal confirmAmt;

    private String status;

    private String pickupOrderNo;

    private String pickupOrderItemNo;

    private String reason;

    private Date redispDate;

    private BigDecimal wholesalePrice;

    private BigDecimal wholesalePrice1;

    private String addressNo;
    
    private String orgItemNo;
    
    private String orgOrderNo;
    
    private String reachTimeType;
    
    private String dispEmpNo;
    
    private String addressTxt;
    
    private String matnrTxt;
    
    private String custName;
    
    private String custTel;
    
    private Integer retQtyB;
    
    private Integer retQtyS;
    
    private Integer retQtyM;
    
    private String replaceReason;
    
    private String giftFlag;

	private Integer totalQty;

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getRemainAmt() {
		return remainAmt;
	}

	public void setRemainAmt(BigDecimal remainAmt) {
		this.remainAmt = remainAmt;
	}

	public String getGiftFlag()
	{
		return giftFlag;
	}

	public void setGiftFlag(String giftFlag)
	{
		this.giftFlag = giftFlag;
	}

	public String getReplaceReason()
	{
		return replaceReason;
	}

	public void setReplaceReason(String replaceReason)
	{
		this.replaceReason = replaceReason;
	}

	public Integer getRetQtyB()
	{
		return retQtyB;
	}

	public void setRetQtyB(Integer retQtyB)
	{
		this.retQtyB = retQtyB;
	}

	public Integer getRetQtyS()
	{
		return retQtyS;
	}

	public void setRetQtyS(Integer retQtyS)
	{
		this.retQtyS = retQtyS;
	}

	public Integer getRetQtyM()
	{
		return retQtyM;
	}

	public void setRetQtyM(Integer retQtyM)
	{
		this.retQtyM = retQtyM;
	}

	public String getCustTel()
	{
		return custTel;
	}

	public void setCustTel(String custTel)
	{
		this.custTel = custTel;
	}

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public String getAddressTxt()
	{
		return addressTxt;
	}

	public void setAddressTxt(String addressTxt)
	{
		this.addressTxt = addressTxt;
	}

	public String getMatnrTxt()
	{
		return matnrTxt;
	}

	public void setMatnrTxt(String matnrTxt)
	{
		this.matnrTxt = matnrTxt;
	}
    
    public String getDispEmpNo()
	{
		return dispEmpNo;
	}

	public void setDispEmpNo(String dispEmpNo)
	{
		this.dispEmpNo = dispEmpNo;
	}

	public String getReachTimeType()
	{
		return reachTimeType;
	}

	public void setReachTimeType(String reachTimeType)
	{
		this.reachTimeType = reachTimeType;
	}

	public String getOrgItemNo()
	{
		return orgItemNo;
	}

	public void setOrgItemNo(String orgItemNo)
	{
		this.orgItemNo = orgItemNo == null ? null : orgItemNo.trim();
	}

	public String getOrgOrderNo()
	{
		return orgOrderNo;
	}

	public void setOrgOrderNo(String orgOrderNo)
	{
		this.orgOrderNo = orgOrderNo == null ? null : orgOrderNo.trim();
	}

	public String getAddressNo()
	{
		return addressNo;
	}

	public void setAddressNo(String addressNo)
	{
		this.addressNo = addressNo == null ? null : addressNo.trim();
	}

	public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr == null ? null : matnr.trim();
    }

    public String getConfirmMatnr()
	 {
		return confirmMatnr;
	 }

	public void setConfirmMatnr(String confirmMatnr)
	{
		this.confirmMatnr = confirmMatnr == null ? null : confirmMatnr.trim();
	}

	public BigDecimal getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(BigDecimal confirmQty) {
        this.confirmQty = confirmQty;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPickupOrderNo() {
        return pickupOrderNo;
    }

    public void setPickupOrderNo(String pickupOrderNo) {
        this.pickupOrderNo = pickupOrderNo == null ? null : pickupOrderNo.trim();
    }

    public String getPickupOrderItemNo() {
        return pickupOrderItemNo;
    }

    public void setPickupOrderItemNo(String pickupOrderItemNo) {
        this.pickupOrderItemNo = pickupOrderItemNo == null ? null : pickupOrderItemNo.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getRedispDate() {
        return redispDate;
    }

    public void setRedispDate(Date redispDate) {
        this.redispDate = redispDate;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getWholesalePrice1() {
        return wholesalePrice1;
    }

    public void setWholesalePrice1(BigDecimal wholesalePrice1) {
        this.wholesalePrice1 = wholesalePrice1;
    }
}