package com.nhry.data.auth.domain;

import java.io.Serializable;
import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;

public class SysUser implements Serializable {
  private String loginName;;
  private String id;
  private String pwd;
  private String status;
  private Date createAt;
  private String createBy;
  private String createByTxt;
  private Date lastModified;
  private String lastModifiedBy;
  private String lastModifiedByTxt;
public String getLoginName() {
	return loginName;
}
public void setLoginName(String loginName) {
	this.loginName = loginName;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPwd() {
	return pwd;
}
public void setPwd(String pwd) {
	this.pwd = pwd;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Date getCreateAt() {
	return createAt;
}
public void setCreateAt(Date createAt) {
	this.createAt = createAt;
}
public String getCreateBy() {
	return createBy;
}
public void setCreateBy(String createBy) {
	this.createBy = createBy;
}
public String getCreateByTxt() {
	return createByTxt;
}
public void setCreateByTxt(String createByTxt) {
	this.createByTxt = createByTxt;
}
public Date getLastModified() {
	return lastModified;
}
public void setLastModified(Date lastModified) {
	this.lastModified = lastModified;
}
public String getLastModifiedBy() {
	return lastModifiedBy;
}
public void setLastModifiedBy(String lastModifiedBy) {
	this.lastModifiedBy = lastModifiedBy;
}
public String getLastModifiedByTxt() {
	return lastModifiedByTxt;
}
public void setLastModifiedByTxt(String lastModifiedByTxt) {
	this.lastModifiedByTxt = lastModifiedByTxt;
}
  
}
