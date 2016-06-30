package com.nhry.model.basic;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ProductQueryModel extends BaseQueryModel implements Serializable {
	private String firstCatory;
	private String secCatory;
	private String status;
	private String matnrTxt;
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
	
}
