package com.nhry.model.order;

import com.nhry.model.basic.BaseQueryModel;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "MilkboxSearchModel", description = "奶箱查询对象")
public class MilkboxSearchModel extends BaseQueryModel implements Serializable {
	@ApiModelProperty(value="status",notes="状态")
	private String status;
	private String emp;
	private String setDateStart;
	private String setDateEnd;
	public String getEmp()
	{
		return emp;
	}
	public void setEmp(String emp)
	{
		this.emp = emp;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getSetDateStart()
	{
		return setDateStart;
	}
	public void setSetDateStart(String setDateStart)
	{
		this.setDateStart = setDateStart;
	}
	public String getSetDateEnd()
	{
		return setDateEnd;
	}
	public void setSetDateEnd(String setDateEnd)
	{
		this.setDateEnd = setDateEnd;
	}
	
	
}
