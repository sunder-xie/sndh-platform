package com.nhry.model.basic;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PriceQueryModel extends BaseQueryModel implements Serializable {
	private String salesOrg;
	private String priceGroup;;
	private String status;
	private String startDate;
	private String endDate;
	public String getPriceGroup() {
		return priceGroup;
	}
	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
}
