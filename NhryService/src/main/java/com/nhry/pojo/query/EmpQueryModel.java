package com.nhry.pojo.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EmpSearchModel", description = "网点员工信息查询对象")
public class EmpQueryModel extends BaseQueryModel implements Serializable {
	@ApiModelProperty(value="branchNo",notes="网点编号")
	private String branchNo;
	private String delFlag;
	
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
