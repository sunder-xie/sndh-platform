package com.nhry.model.basic;

import java.io.Serializable;

public class ProductQueryModel extends BaseQueryModel implements Serializable {
	private String firstCatory;
	private String secCatory;
	private String status;
	private String matnrTxt;
	private String salesOrg;
	private String dealerNo;
	private String branchNo;
	
	public String getFirstCatory() {
		return firstCatory;
	}
	public void setFirstCatory(String firstCatory) {
		this.firstCatory = firstCatory;
	}
	public String getSecCatory() {
		return secCatory;
	}
	public void setSecCatory(String secCatory) {
		this.secCatory = secCatory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMatnrTxt() {
		return matnrTxt;
	}
	public void setMatnrTxt(String matnrTxt) {
		this.matnrTxt = matnrTxt;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getDealerNo() {
		return dealerNo;
	}
	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
}
