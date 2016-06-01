package com.nhry.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EmpSearchModel", description = "网点员工信息查询对象")
public class EmpSearchModel implements Serializable {
	@ApiModelProperty(value="branchNo",notes="网点编号")
	private String branchNo;
	private String delFlag;
	private String pageSize;
	private String pageNum;
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
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	
}
