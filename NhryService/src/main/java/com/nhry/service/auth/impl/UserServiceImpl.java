package com.nhry.service.auth.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysResourceMapper;
import com.nhry.data.auth.dao.TSysRoleMapper;
import com.nhry.data.auth.dao.TSysUserMapper;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.auth.UserQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.auth.dao.ResourceService;
import com.nhry.service.auth.dao.RoleService;
import com.nhry.service.auth.dao.UserService;
import com.nhry.utils.date.Date;

import org.apache.commons.lang3.StringUtils;

public class UserServiceImpl extends BaseService implements UserService {
	private TSysUserMapper userMapper;
	private TSysRoleMapper roleMapper;
	private ResourceService resService;

	@Override
	public PageInfo findUser(UserQueryModel um){
		// TODO Auto-generated method stub
		return userMapper.findUser(um);
	}

	public int addUser(TSysUser user) {
		if(StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getDisplayName())){
			 throw new ServiceException(MessageCode.LOGIC_ERROR, "loginName、displayName属性值不能为空!");
		}
		TSysUser u = this.userMapper.findUserByLoginName(user.getLoginName());
		if(u == null){
			user.setCreateOn(new Date());
			user.setLastModified(new Date());
			return userMapper.addUser(user);
		}else{
			user.setLastModified(new Date());
			return this.userMapper.updateUser(user);
		}
	}

	@Override
	public TSysUser login(TSysUser user) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(user.getLoginName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"用户名不能为空!");
		}

		TSysUser _user = userMapper.login(user);
		if(_user == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"系统不存在该用户,请检查你的用户名、密码！");
		}
//		_user.setResources(resService.findRecoureByUserId(_user.getLoginName()));
//		_user.setRoles(roleMapper.getUserRoles(_user.getLoginName()));
		return _user;
	}

	public void setUserMapper(TSysUserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public TSysUser findUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return userMapper.findUserByLoginName(loginName);
	}

	@Override
	public int updateUser(TSysUser record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getLoginName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "用户名登录名不能为空！");
		}
		record.setLastModified(new Date());
		return userMapper.updateUser(record);
	}

	@Override
	public int deleteUserByLoginName(String uname) {
		// TODO Auto-generated method stub
		TSysUser user = this.findUserByLoginName(uname);
		if(user == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该用户名对应的用户信息不存在！");
		}
		user.setLastModified(new Date());
		return this.userMapper.deleteUserByLoginName(user);
	}

	public void setRoleMapper(TSysRoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public void setResService(ResourceService resService) {
		this.resService = resService;
	}
}
