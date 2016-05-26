package com.nhry.common.model;

import java.io.Serializable;
import java.util.Date;

public class AccessKey implements Serializable {
	// accesskey
	private String ack;
	// 用户名
	private String uname;
	// 第一次访问时间
	private Date visitFirstTime;
	// 最后一次访问时间
	private Date visitEndTime;
	// 访问IP
	private String accessIp;
	// 访问次数
	private int visitCount;
	
	private String lastUri; 

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getVisitFirstTime() {
		return visitFirstTime;
	}

	public void setVisitFirstTime(Date visitFirstTime) {
		this.visitFirstTime = visitFirstTime;
	}

	public Date getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public String getLastUri() {
		return lastUri;
	}

	public void setLastUri(String lastUri) {
		this.lastUri = lastUri;
	}
}
