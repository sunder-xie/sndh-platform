package com.nhry.model.basic;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "EmpSearchModel", description = "网点员工信息查询对象")
public class BranchQueryModel extends BaseQueryModel implements Serializable {
	@ApiModelProperty(value="branchLevel",notes="奶站等级")

	private String salesOrg;
	private String branchLevel;
	private String salesCha;

	public String getSalesCha() {
		return salesCha;
	}

	public void setSalesCha(String salesCha) {
		this.salesCha = salesCha;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
	}
}
