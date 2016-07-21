package com.nhry.service.basic.pojo;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/6/3.
 */
public class AreaSearchModel implements Serializable {
    private String status;
    private String content;
    private String branchNo;
    private String salesOrg;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

   
}
