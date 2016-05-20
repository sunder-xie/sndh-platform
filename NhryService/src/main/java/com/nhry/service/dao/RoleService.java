package com.nhry.service.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.Role;
import com.nhry.domain.User;

public interface RoleService {
	public PageInfo selectByRoleName(String roleName,int pageNum,int pageSize);
	
	public PageInfo selectByPage(int pageNum,int pageSize);
	
	public Role selectOneRole(String name);
	
	public int addRole(Role role);
	public int deleteRole(String id);
	public int updateRole(Role role);
}
