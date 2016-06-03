package com.nhry.data.auth.domain;

import java.io.Serializable;

public class Role implements Serializable {
  private int id;
  private String roleName;
  private String shortName;
  private String comments;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getComments() {
	return comments;
}
public String getRoleName()
{
	return roleName;
}
public void setRoleName(String roleName)
{
	this.roleName = roleName;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getShortName()
{
	return shortName;
}
public void setShortName(String shortName)
{
	this.shortName = shortName;
}
}
