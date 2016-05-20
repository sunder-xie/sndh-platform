package com.nhry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.RoleMapper;
import com.nhry.data.dao.UserMapper;
import com.nhry.domain.Role;
import com.nhry.domain.User;
import com.nhry.exception.ExceptionCode;
import com.nhry.exception.ServiceException;
import com.nhry.service.BaseService;
import com.nhry.service.dao.RoleService;
import com.nhry.service.dao.UserService;

public class RoleServiceImpl extends BaseService implements RoleService {
	private RoleMapper roleMapper;
	
	@Override
	public PageInfo selectByRoleName(String roleName,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		if(roleName == null){
			throw new ServiceException(ExceptionCode.LOGIC_ERROR);
		}
		return roleMapper.selectByRoleName(roleName,pageNum,pageSize);
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	public PageInfo selectByPage(int pageNum, int pageSize)
	{
		// TODO Auto-generated method stub
		return roleMapper.selectByPage(pageNum, pageSize);
	}
 
	@Override
	public Role selectOneRole(String name)
	{
		// TODO Auto-generated method stub
		Role role = roleMapper.selectOneRole(name);
		System.out.println(role.getId()+role.getRoleName());
		return role;
	}
}
