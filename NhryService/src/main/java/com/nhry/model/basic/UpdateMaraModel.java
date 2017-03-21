package com.nhry.model.basic;

import java.io.Serializable;
import java.util.ArrayList;

import com.nhry.model.sys.ResponseModel;
import com.wordnik.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;

public class UpdateMaraModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String salesOrg;
	private String sort;
	private String hide;
	private String matnr;
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getHide() {
		return hide;
	}
	public void setHide(String hide) {
		this.hide = hide;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	
	
}
