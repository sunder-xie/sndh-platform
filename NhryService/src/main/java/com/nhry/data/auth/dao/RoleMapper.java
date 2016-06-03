package com.nhry.data.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.Role;

public interface RoleMapper {
	public <T> PageInfo<T> selectByRoleName(@Param("roleName")String roleName,int pageNum,int pageSize);
	
	public <T> PageInfo<T> selectByPage(int pageNum,int pageSize);
	
	public List<Role> all();
	
	public int addRole(Role role);
	
	public Role selectOneRole(String name);
	
	public int deleteRole(String id);
	
	public int updateRole(Role role);
}
