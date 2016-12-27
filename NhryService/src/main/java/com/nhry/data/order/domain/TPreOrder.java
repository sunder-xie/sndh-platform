package com.nhry.data.order.domain;

import com.nhry.utils.SysContant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(indexName = SysContant.INDEX_NAME, type = "t_preorder")
public class TPreOrder {
	@Id
    private String orderNo;                 //订单编号

    private String orderType;               //订单类型

    private String hadBranchFlag;

    private Date orderDate;             //订单创建日期

    private Date endDate;               //订单截止日期

    private String paymentmethod;       //订单付款方式  10.后付款 20.预付款

    private String preorderSource;      //订单来源   10 电商、20 摆台（征订）、30 奶站、40 牛奶钱包、 50 手机app 60 电话 、70 机构订奶

    private String onlineSourceType;    //订单来源的二级 比如10 电商下面有 淘宝、京东,如果是机构订奶 存的是机构编码

    private String onlineorderNo;       //电商那边的订单号

    private String branchNo;            //奶站编号

    private String branchTel;           //奶站电话
    
    private String branchName;          //奶站名称

	 private String customerTel;          //订户电话

    private String milkmemberNo;           //会员编号
    
    private String milkmemberName;         //订户编号

    private BigDecimal acctAmt;            //订户余额

    private BigDecimal factAmt;

    private String memberNo;

    private String retReason;           //退回原因

    private Date retDate;               //退回日期

    private String paymentStat;         //支付状态 10 未支付、20 已支付（电商、牛奶钱包、机构、年卡 创建时均是已支付状态）

    private String milkboxStat;        //奶箱状态 10 已安装 、20 未安装、30 不需要安装

    private BigDecimal initAmt;        // 订单总金额（不包括折扣 ）= 每天的日订单（不包括停订的）金额之和

    private BigDecimal onlineInitAmt;  //电商订单总金额
    
    private String initAmtStr;

    private BigDecimal curAmt;          //剩余金额 （订单总金额 -  完结金额）

    private String dispLineNo;

    private String empNo;               //送奶员编号

    private String empName;             //送奶员名称

    private String empTel;              //送奶员电话

    private String adressNo;            //订单地址编号

    private String createrNo;           //创建人编号

    private String createrBy;           //创建人名称

    private String preorderStat;        //订单状态 已生效10、未生效20、无效30,作废40,

    private String memoTxt;             //备注

    private String payMethod;           //支付方式

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
    private String promType;
    private String promSubType;

    public String getPromSubType() {
        return promSubType;
    }

    public void setPromSubType(String promSubType) {
        this.promSubType = promSubType;
    }

    public String getPromType() {
        return promType;
    }

    public void setPromType(String promType) {
        this.promType = promType;
    }

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