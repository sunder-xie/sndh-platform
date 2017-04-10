package com.nhry.model.stud;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoxijun
 */
public class SchoolClassModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String salesOrg;
	private String schoolCode;
	private List<String> classCodes;
	
	public List<String> getClassCodes() {
		return classCodes;
	}
	public void setClassCodes(List<String> classCodes) {
		this.classCodes = classCodes;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	
	
	
	
	
	
}
