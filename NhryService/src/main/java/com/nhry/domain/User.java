package com.nhry.domain;

import java.io.Serializable;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(description = "user")
public class User implements Serializable {
  private int id;
  private String userName;
  private String email;
  private String comments;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
  
}
