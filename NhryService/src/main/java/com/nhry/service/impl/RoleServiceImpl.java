package com.nhry.service.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.RoleMapper;
import com.nhry.domain.Role;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.service.BaseService;
import com.nhry.service.dao.RoleService;

public class RoleServiceImpl extends BaseService implements RoleService {
	private RoleMapper roleMapper;
	
	@Override
	public PageInfo selectByRoleName(String roleName,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		if(roleName == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR);
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
	public Role selectOneRole(String id)
	{
		// TODO Auto-generated method stub
		Role role = roleMapper.selectOneRole(id);
		System.out.println(role.getId()+role.getRoleName());
		return role;
	}

	@Override
	public int addRole(Role role)
	{
		// TODO Auto-generated method stub
		return roleMapper.addRole(role);
	}

	@Override
	public int deleteRole(String id)
	{
		// TODO Auto-generated method stub
		int success = roleMapper.deleteRole(id);
		System.out.println("[========="+success);
//		if(success<=0){
//			throw new ServiceException(MessageCode.LOGIC_ERROR,"数据不存在!");
//		}
		
		return success;
	}

	@Override
	public int updateRole(Role role)
	{
		// TODO Auto-generated method stub
		int success = roleMapper.updateRole(role);
		
		if(success<=0){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"更新失败!");
		}
		
		return success;
	}
}
