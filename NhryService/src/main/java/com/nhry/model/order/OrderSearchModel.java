package com.nhry.model.order;

import com.nhry.model.basic.BaseQueryModel;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "OrderSearchModel", description = "订单信息列表查询对象")
public class OrderSearchModel extends BaseQueryModel implements Serializable {
	@ApiModelProperty(value="orderNo",notes="订单编号")
	private String orderNo;
	private String branchNo;
	private String empNo;
	private String product;
	private String content;//订户电话或者姓名
	private String goAmt;//订单续订的续费
	private String address;
	private String milkmemberNo;
	private String orderDate;
	private String paymentStat;
	private String milkboxStat;
	private String preorderStat;
	private String orderDateStart;
	private String orderDateEnd;
	private String companyName;
	private String reason;
	private String status;
	private String orderReturnDateStart;
	private String orderReturnDateEnd;
	private String salesOrg;
	private String dealerNo;
	private Integer goDays;
	
	public Integer getGoDays()
	{
		return goDays;
	}
	public void setGoDays(Integer goDays)
	{
		this.goDays = goDays;
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
	public String getGoAmt()
	{
		return goAmt;
	}
	public void setGoAmt(String goAmt)
	{
		this.goAmt = goAmt;
	}
	public String getProduct()
	{
		return product;
	}
	public void setProduct(String product)
	{
		this.product = product;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getOrderReturnDateStart()
	{
		return orderReturnDateStart;
	}
	public void setOrderReturnDateStart(String orderReturnDateStart)
	{
		this.orderReturnDateStart = orderReturnDateStart;
	}
	public String getOrderReturnDateEnd()
	{
		return orderReturnDateEnd;
	}
	public void setOrderReturnDateEnd(String orderReturnDateEnd)
	{
		this.orderReturnDateEnd = orderReturnDateEnd;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	public String getCompanyName()
	{
		return companyName;
	}
	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getOrderDateStart()
	{
		return orderDateStart;
	}
	public void setOrderDateStart(String orderDateStart)
	{
		this.orderDateStart = orderDateStart;
	}
	public String getOrderDateEnd()
	{
		return orderDateEnd;
	}
	public void setOrderDateEnd(String orderDateEnd)
	{
		this.orderDateEnd = orderDateEnd;
	}
	public String getOrderNo()
	{
		return orderNo;
	}
	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}
	public String getBranchNo()
	{
		return branchNo;
	}
	public void setBranchNo(String branchNo)
	{
		this.branchNo = branchNo;
	}
	public String getEmpNo()
	{
		return empNo;
	}
	public void setEmpNo(String empNo)
	{
		this.empNo = empNo;
	}
	public String getMilkmemberNo()
	{
		return milkmemberNo;
	}
	public void setMilkmemberNo(String milkmemberNo)
	{
		this.milkmemberNo = milkmemberNo;
	}
	public String getOrderDate()
	{
		return orderDate;
	}
	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}
	public String getPaymentStat()
	{
		return paymentStat;
	}
	public void setPaymentStat(String paymentStat)
	{
		this.paymentStat = paymentStat;
	}
	public String getMilkboxStat()
	{
		return milkboxStat;
	}
	public void setMilkboxStat(String milkboxStat)
	{
		this.milkboxStat = milkboxStat;
	}
	public String getPreorderStat()
	{
		return preorderStat;
	}
	public void setPreorderStat(String preorderStat)
	{
		this.preorderStat = preorderStat;
	}
	
}
