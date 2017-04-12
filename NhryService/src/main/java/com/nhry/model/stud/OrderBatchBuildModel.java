package com.nhry.model.stud;

/**
 * @author zhaoxijun
 * @date 2017年4月12日
 */
public class OrderBatchBuildModel {
	private String orderStartDateStr;
	private String orderEndDateStr;
	private String uncloudSchoolCodes;
	private String week;
	public String getOrderStartDateStr() {
		return orderStartDateStr;
	}
	public void setOrderStartDateStr(String orderStartDateStr) {
		this.orderStartDateStr = orderStartDateStr;
	}
	public String getOrderEndDateStr() {
		return orderEndDateStr;
	}
	public void setOrderEndDateStr(String orderEndDateStr) {
		this.orderEndDateStr = orderEndDateStr;
	}
	public String getUncloudSchoolCodes() {
		return uncloudSchoolCodes;
	}
	public void setUncloudSchoolCodes(String uncloudSchoolCodes) {
		this.uncloudSchoolCodes = uncloudSchoolCodes;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	
}
