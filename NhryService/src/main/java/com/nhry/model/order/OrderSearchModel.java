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
	private String milkmemberNo;
	private String orderDate;
	private String paymentStat;
	private String milkboxStat;
	private String preorderStat;
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
