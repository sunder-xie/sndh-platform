package com.nhry.model.basic;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(value = "PriceSearchModel", description = "价格组列表查询对象")
public class PriceQueryModel extends BaseQueryModel implements Serializable
{
	@ApiModelProperty(value = "agencyType", notes = "经销商类型")
	private String agencyType;
	private String status;

	public String getAgencyType()
	{
		return agencyType;
	}

	public void setAgencyType(String agencyType)
	{
		this.agencyType = agencyType;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
