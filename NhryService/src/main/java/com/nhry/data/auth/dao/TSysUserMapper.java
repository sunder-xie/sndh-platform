package com.nhry.data.auth.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.auth.UserQueryModel;

import java.util.List;
import java.util.Map;

public interface TSysUserMapper {
	public PageInfo findUser(UserQueryModel um);

	public TSysUser login(TSysUser user);

	public int addUser(TSysUser user);

	TSysUser findUserByLoginName(String loginName);

	int updateUser(TSysUser record);
	
	public int updateUserPw(TSysUser record);
	
	public int deleteUserByLoginName(TSysUser user);

	List<TSysUser> findUserByRoleId(UserQueryModel um);

	PageInfo findUserPageByRoleId(UserQueryModel um);
	
	/**
	 * 根据组织和角色id，查找用户列表
	 * @param attrs
	 * @return
	 */
	List<TSysUser> getUsersByRidandOrgs(Map<String,String> attrs);
	
	/**
	 * 根据组织和角色查询登录名列表(不在给定经销商范围内的)
	 * @param attrs
	 * @return
	 */
	List<TSysUser> getloginNamesByOrgsandRid(Map<String,String> attrs);
	
	/**
	 * 根据组织和角色查询登录名列表
	 * @param attrs
	 * @return
	 */
	List<TSysUser> getloginNamesByOrgsandRid2(Map<String,String> attrs);
}