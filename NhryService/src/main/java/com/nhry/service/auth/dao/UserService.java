package com.nhry.service.auth.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.auth.UserQueryModel;

public interface UserService {
	public PageInfo findUser(UserQueryModel um);

	public int addUser(TSysUser user);
	
	public TSysUser login(TSysUser user);
	
	public TSysUser findUserByLoginName(String loginName);
	
	public int updateUser(TSysUser record);
	
	public int deleteUserByLoginName(String uname);
}
