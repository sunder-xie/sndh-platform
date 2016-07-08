package com.nhry.model.order;

import com.nhry.model.basic.BaseQueryModel;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "MilkboxCreateModel", description = "奶箱新建对象")
public class MilkboxCreateModel implements Serializable {
	@ApiModelProperty(value="status",notes="状态")
	private String code;
	private String status;
	private String paymentStatus;
	private String setDate;
	private String empNo;
	private String addressNo;
	private String branchNo;
	public String getEmpNo()
	{
		return empNo;
	}
	public void setEmpNo(String empNo)
	{
		this.empNo = empNo;
	}
	public String getAddressNo()
	{
		return addressNo;
	}
	public void setAddressNo(String addressNo)
	{
		this.addressNo = addressNo;
	}
	public String getBranchNo()
	{
		return branchNo;
	}
	public void setBranchNo(String branchNo)
	{
		this.branchNo = branchNo;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getPaymentStatus()
	{
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus)
	{
		this.paymentStatus = paymentStatus;
	}
	public String getSetDate()
	{
		return setDate;
	}
	public void setSetDate(String setDate)
	{
		this.setDate = setDate;
	}
	
	
}