package com.nhry.data.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.RoleMapper;
import com.nhry.data.dao.UserMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.Role;

public class RoleMapperImpl implements RoleMapper {

	private DynamicSqlSessionTemplate sqlSessionTemplate;

	@Override
	public <T> PageInfo<T> selectByRoleName(String roleName, int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("selectByRoleName",roleName, pageNum, pageSize);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public List<Role> all() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("allRoles");
	}

	@Override
	public int addRole(Role role) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("addRole", role);
	}

	@Override
	public <T> PageInfo<T> selectByPage(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("allRoles", pageNum,
				pageSize);
	}

	@Override
	public Role selectOneRole(String name) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectOneRole", name);
	}

	@Override
	public int deleteRole(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("deleteRole", id);
	}

	@Override
	public int updateRole(Role role) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateRole", role);
	}
}
