package com.nhry.service.auth.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.TSysUser;

public interface UserService {
	public PageInfo selectByUserName(String uname,int pageNum,int pageSize);

	public PageInfo selectByPage(int pageNum,int pageSize);

	public int addUser(JSONObject user);
	
	public TSysUser login(TSysUser user);
}
