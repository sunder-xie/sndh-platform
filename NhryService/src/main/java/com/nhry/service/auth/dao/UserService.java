package com.nhry.service.auth.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.auth.UserQueryModel;
import com.nhry.model.auth.UserQueryModel2;
import com.nhry.model.auth.UserQueryModel3;

import java.util.List;

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
	
	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	public TSysUser getCurrentLoginUser();
	
	public TSysUser findUserByLoginName(String loginName);
	
	public int updateUser(TSysUser record);
	
	public int deleteUserByLoginName(String uname);

	List<TSysUser> findUserByRoleId(UserQueryModel2 um);

	PageInfo findUserPageByRoleId(UserQueryModel um);

	/**
	 * 查询未分配角色的用户
	 * @param model
	 * @return
	 */
	List<TSysUser> findNotRoleUser(UserQueryModel3 model);

	/**
	 * 查询未分配角色的用户分页
	 * @param model
	 * @return
	 */
	PageInfo<TSysUser> findNotRoleUserPage(UserQueryModel3 model);
}
