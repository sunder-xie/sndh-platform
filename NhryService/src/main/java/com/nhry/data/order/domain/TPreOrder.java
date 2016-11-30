package com.nhry.data.order.domain;

import com.nhry.utils.SysContant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(indexName = SysContant.INDEX_NAME, type = "t_preorder")
public class TPreOrder {
	@Id
    private String orderNo;

    private String orderType;

    private String hadBranchFlag;

    private Date orderDate;

    private Date endDate;

    private String paymentmethod;

    private String preorderSource;

    private String onlineSourceType;

    private String onlineorderNo;

    private String branchNo;

    private String branchTel;
    
    private String branchName;

	 private String customerTel;

    private String milkmemberNo;
    
    private String milkmemberName;

    private BigDecimal acctAmt;

    private BigDecimal factAmt;

    private String memberNo;

    private String retReason;

    private Date retDate;

    private String paymentStat;

    private String milkboxStat;

    private BigDecimal initAmt;

    private BigDecimal onlineInitAmt;
    
    private String initAmtStr;

    private BigDecimal curAmt;

    private String dispLineNo;

    private String empNo;

    private String empName;

    private String empTel;

    private String adressNo;

    private String createrNo;

    private String createrBy;

    private String preorderStat;

    private String memoTxt;

    private String payMethod;

    private String solicitNo;

    private Date solicitDate;
    
    private String solicitDateStr;

    private String solicitorNo;

    private String solicitBy;
    
    private Date stopDateStart;

	 private Date stopDateEnd;
	 
	 private String stopDateStartStr;

	 private String stopDateEndStr;
    
    private String stopReason;
    
    private String deliveryType;
    
    private String backReason;

    private Date backDate;
    
    private String sign;
    
    private String salesOrgName;

    private String salesOrg;

    private String dealerNo;

    private  String dealerName;
    private String deleteReason;
    
    private String payMan;
    
    private String resumeFlag;
    
    private Date payDate;

    private String payDateStr;
    
    private String isIntegration;
    
    private Integer yGrowth;
    
    private Integer yFresh;

    private String receiptNo;
    
    private String isPaid;
    
    private String validDate;
    private String isValid;
    String branchEmpNo;
    String branchMp;
    String branchEmpName;
    String addressTxt;
    private String promotion;
    private String promItemNo;
    private BigDecimal discountAmt;

    public String getPromItemNo() {
        return promItemNo;
    }

    public void setPromItemNo(String promItemNo) {
        this.promItemNo = promItemNo;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public BigDecimal getOnlineInitAmt() {
        return onlineInitAmt;
    }

    public void setOnlineInitAmt(BigDecimal onlineInitAmt) {
        this.onlineInitAmt = onlineInitAmt;
    }

    public String getPayDateStr() {
        return payDateStr;
    }

    public void setPayDateStr(String payDateStr) {
        this.payDateStr = payDateStr;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getHadBranchFlag() {
        return hadBranchFlag;
    }

    public void setHadBranchFlag(String hadBranchFlag) {
        this.hadBranchFlag = hadBranchFlag;
    }

    public String getOnlineSourceType() {
        return onlineSourceType;
    }

    public void setOnlineSourceType(String onlineSourceType) {
        this.onlineSourceType = onlineSourceType;
    }

    public String getValidDate()
	{
		return validDate;
	}

	public void setValidDate(String validDate)
	{
		this.validDate = validDate;
	}

	public String getIsPaid()
	{
		return isPaid;
	}

	public void setIsPaid(String isPaid)
	{
		this.isPaid = isPaid;
	}

	public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
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

	public String getIsIntegration()
	{
		return isIntegration;
	}

	public void setIsIntegration(String isIntegration)
	{
		this.isIntegration = isIntegration;
	}

	public BigDecimal getFactAmt() {
        return factAmt;
    }

    public void setFactAmt(BigDecimal factAmt) {
        this.factAmt = factAmt;
    }

    public BigDecimal getAcctAmt() {
        return acctAmt;
    }

    public void setAcctAmt(BigDecimal acctAmt) {
        this.acctAmt = acctAmt;
    }

    public String getResumeFlag()
	{
		return resumeFlag;
	}

	public void setResumeFlag(String resumeFlag)
	{
		this.resumeFlag = resumeFlag;
	}

	public String getSalesOrgName() {
        return salesOrgName;
    }

    public void setSalesOrgName(String salesOrgName) {
        this.salesOrgName = salesOrgName;
    }

    public String getBranchEmpNo() {
        return branchEmpNo;
    }

    public void setBranchEmpNo(String branchEmpNo) {
        this.branchEmpNo = branchEmpNo;
    }

    public String getBranchMp() {
        return branchMp;
    }

    public void setBranchMp(String branchMp) {
        this.branchMp = branchMp;
    }

    public String getBranchEmpName() {
        return branchEmpName;
    }

    public void setBranchEmpName(String branchEmpName) {
        this.branchEmpName = branchEmpName;
    }

    public String getAddressTxt() {
        return addressTxt;
    }

    public void setAddressTxt(String addressTxt) {
        this.addressTxt = addressTxt;
    }

	public String getPayMan()
	{
		return payMan;
	}

	public void setPayMan(String payMan)
	{
		this.payMan = payMan;
	}

	public Date getPayDate()
	{
		return payDate;
	}

	public void setPayDate(Date payDate)
	{
		this.payDate = payDate;
	}

	public String getDeleteReason()
	{
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason)
	{
		this.deleteReason = deleteReason;
	}

	public String getDealerNo()
	{
		return dealerNo;
	}

	public void setDealerNo(String dealerNo)
	{
		this.dealerNo = dealerNo;
	}

	public String getSalesOrg()
	{
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg)
	{
		this.salesOrg = salesOrg;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public String getSolicitDateStr()
	{
		return solicitDateStr;
	}

	public void setSolicitDateStr(String solicitDateStr)
	{
		this.solicitDateStr = solicitDateStr;
	}

	public String getBackReason()
	{
		return backReason;
	}

	public void setBackReason(String backReason)
	{
		this.backReason = backReason  == null ? null : backReason.trim();
	}

	public Date getBackDate()
	{
		return backDate;
	}

	public void setBackDate(Date backDate)
	{
		this.backDate = backDate;
	}

	public String getMilkmemberName()
	{
		return milkmemberName;
	}

	public void setMilkmemberName(String milkmemberName)
	{
		this.milkmemberName = milkmemberName;
	}

	public String getInitAmtStr()
	{
		return initAmtStr;
	}

	public void setInitAmtStr(String initAmtStr)
	{
		this.initAmtStr = initAmtStr;
	}

	public String getStopDateStartStr()
	{
		return stopDateStartStr;
	}

	public void setStopDateStartStr(String stopDateStartStr)
	{
		this.stopDateStartStr = stopDateStartStr;
	}

	public String getStopDateEndStr()
	{
		return stopDateEndStr;
	}

	public void setStopDateEndStr(String stopDateEndStr)
	{
		this.stopDateEndStr = stopDateEndStr;
	}

	public String getDeliveryType()
	{
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType)
	{
		this.deliveryType = deliveryType;
	}

	 public String getBranchName()
    {
   	 return branchName;
    }
    
    public void setBranchName(String branchName)
    {
   	 this.branchName = branchName  == null ? null : branchName.trim();
    }
    
    public Date getStopDateStart()
    {
   	 return stopDateStart;
    }
    
    public void setStopDateStart(Date stopDateStart)
    {
   	 this.stopDateStart = stopDateStart;
    }
    
    public Date getStopDateEnd()
    {
   	 return stopDateEnd;
    }
    
    public void setStopDateEnd(Date stopDateEnd)
    {
   	 this.stopDateEnd = stopDateEnd;
    }
    
    public String getStopReason()
    {
   	 return stopReason;
    }
    
    public void setStopReason(String stopReason)
    {
   	 this.stopReason = stopReason  == null ? null : stopReason.trim();
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpTel() {
        return empTel;
    }

    public void setEmpTel(String empTel) {
        this.empTel = empTel;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel  == null ? null : customerTel.trim();
    }

    public String getBranchTel() {
        return branchTel;
    }

    public void setBranchTel(String branchTel) {
        this.branchTel = branchTel == null ? null : branchTel.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRetReason() {
        return retReason;
    }

    public void setRetReason(String retReason) {
        this.retReason = retReason  == null ? null : retReason.trim();
    }

    public Date getRetDate() {
        return retDate;
    }

    public void setRetDate(Date retDate) {
        this.retDate = retDate;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod == null ? null : paymentmethod.trim();
    }

    public String getPreorderSource() {
        return preorderSource;
    }

    public void setPreorderSource(String preorderSource) {
        this.preorderSource = preorderSource == null ? null : preorderSource.trim();
    }

    public String getOnlineorderNo() {
        return onlineorderNo;
    }

    public void setOnlineorderNo(String onlineorderNo) {
        this.onlineorderNo = onlineorderNo == null ? null : onlineorderNo.trim();
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo == null ? null : branchNo.trim();
    }

    public String getMilkmemberNo() {
        return milkmemberNo;
    }

    public void setMilkmemberNo(String milkmemberNo) {
        this.milkmemberNo = milkmemberNo == null ? null : milkmemberNo.trim();
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo == null ? null : memberNo.trim();
    }

    public String getPaymentStat() {
        return paymentStat;
    }

    public void setPaymentStat(String paymentStat) {
        this.paymentStat = paymentStat == null ? null : paymentStat.trim();
    }

    public String getMilkboxStat() {
        return milkboxStat;
    }

    public void setMilkboxStat(String milkboxStat) {
        this.milkboxStat = milkboxStat == null ? null : milkboxStat.trim();
    }

    public BigDecimal getInitAmt() {
        return initAmt;
    }

    public void setInitAmt(BigDecimal initAmt) {
        this.initAmt = initAmt;
    }

    public BigDecimal getCurAmt() {
        return curAmt;
    }

    public void setCurAmt(BigDecimal curAmt) {
        this.curAmt = curAmt;
    }

    public String getDispLineNo() {
        return dispLineNo;
    }

    public void setDispLineNo(String dispLineNo) {
        this.dispLineNo = dispLineNo == null ? null : dispLineNo.trim();
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo == null ? null : empNo.trim();
    }

    public String getAdressNo() {
        return adressNo;
    }

    public void setAdressNo(String adressNo) {
        this.adressNo = adressNo == null ? null : adressNo.trim();
    }

    public String getCreaterNo() {
        return createrNo;
    }

    public void setCreaterNo(String createrNo) {
        this.createrNo = createrNo == null ? null : createrNo.trim();
    }

    public String getCreaterBy() {
        return createrBy;
    }

    public void setCreaterBy(String createrBy) {
        this.createrBy = createrBy == null ? null : createrBy.trim();
    }

    public String getPreorderStat() {
        return preorderStat;
    }

    public void setPreorderStat(String preorderStat) {
        this.preorderStat = preorderStat == null ? null : preorderStat.trim();
    }

    public String getMemoTxt() {
        return memoTxt;
    }

    public void setMemoTxt(String memoTxt) {
        this.memoTxt = memoTxt == null ? null : memoTxt.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public String getSolicitNo() {
        return solicitNo;
    }

    public void setSolicitNo(String solicitNo) {
        this.solicitNo = solicitNo == null ? null : solicitNo.trim();
    }

    public Date getSolicitDate() {
        return solicitDate;
    }

    public void setSolicitDate(Date solicitDate) {
        this.solicitDate = solicitDate;
    }

    public String getSolicitorNo() {
        return solicitorNo;
    }

    public void setSolicitorNo(String solicitorNo) {
        this.solicitorNo = solicitorNo == null ? null : solicitorNo.trim();
    }

    public String getSolicitBy() {
        return solicitBy;
    }

    public void setSolicitBy(String solicitBy) {
        this.solicitBy = solicitBy == null ? null : solicitBy.trim();
    }
}