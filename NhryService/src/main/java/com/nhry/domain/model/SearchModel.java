package com.nhry.domain.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SearchModel implements Serializable {
  private String branchNo;
  private String delFlag;
  private int pageSize;
  private int pageNum;

public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public int getPageNum() {
	return pageNum;
}
public void setPageNum(int pageNum) {
	this.pageNum = pageNum;
}

public String getDelFlag() {
	return delFlag;
}
public void setDelFlag(String delFlag) {
	this.delFlag = delFlag;
}
public String getBranchNo() {
	return branchNo;
}
public void setBranchNo(String branchNo) {
	this.branchNo = branchNo;
}
}
