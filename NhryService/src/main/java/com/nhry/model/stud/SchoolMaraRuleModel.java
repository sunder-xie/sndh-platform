package com.nhry.model.stud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nhry.data.stud.domain.TMdSchoolMaraRule;

/**
*
*@author dai
*/
public class SchoolMaraRuleModel  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String salesOrg;
	//学校
	private String schoolCode;
	//班级
	private List<TMdSchoolMaraRule> schoolMaraList;

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

	public List<TMdSchoolMaraRule> getSchoolMaraList() {
		return schoolMaraList;
	}

	public void setSchoolMaraList(List<TMdSchoolMaraRule> schoolMaraList) {
		this.schoolMaraList = schoolMaraList;
	}



	
	
	
}
