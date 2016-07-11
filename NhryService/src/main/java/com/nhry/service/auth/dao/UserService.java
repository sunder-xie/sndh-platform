package com.nhry.service.auth.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.auth.UserQueryModel;

public interface UserService {
	public PageInfo findUser(UserQueryModel um);
	
	/**
	 * 添加、更新用户
	 * @param user
	 * @return
	 */
	public int addUser(TSysUser user);
	
	/**
	 * 用户登录方法
	 * @param user
	 * @return
	 */
	public TSysUser login(TSysUser user);
	
	public TSysUser findUserByLoginName(String loginName);
	
	public int updateUser(TSysUser record);
	
	public int deleteUserByLoginName(String uname);
}
