package com.nhry.model.auth;

import java.io.Serializable;
import java.util.Date;

import com.nhry.model.basic.BaseQueryModel;

public class UserQueryModel extends BaseQueryModel implements Serializable {
  private String uname;
  private String userGroupName;
  private Date startDate;
  private Date endDate;
public String getUname() {
	return uname;
}
public void setUname(String uname) {
	this.uname = uname;
}
public String getUserGroupName() {
	return userGroupName;
}
public void setUserGroupName(String userGroupName) {
	this.userGroupName = userGroupName;
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
