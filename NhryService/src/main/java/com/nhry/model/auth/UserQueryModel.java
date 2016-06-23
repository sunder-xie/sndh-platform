package com.nhry.model.auth;

import com.nhry.model.basic.BaseQueryModel;

import java.io.Serializable;
import java.util.Date;

public class UserQueryModel extends BaseQueryModel implements Serializable {
  private String uname;
  private String groupId;
  private Date startDate;
  private Date endDate;
public String getUname() {
	return uname;
}
public void setUname(String uname) {
	this.uname = uname;
}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
  
}
