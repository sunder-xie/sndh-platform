package com.nhry.model.stud;

/**
 * @author zhaoxijun
 * @date 2017年4月12日
 */
public class OrderBatchBuildModel {
	private String orderGetDateStr;
	private String uncloudSchoolCodes;
	private String week;
	
	private String orderDateStr;
	private String schoolCode;
	
	public String getOrderGetDateStr() {
		return orderGetDateStr;
	}
	public void setOrderGetDateStr(String orderGetDateStr) {
		this.orderGetDateStr = orderGetDateStr;
	}
	public String getOrderDateStr() {
		return orderDateStr;
	}
	public void setOrderDateStr(String orderDateStr) {
		this.orderDateStr = orderDateStr;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
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
