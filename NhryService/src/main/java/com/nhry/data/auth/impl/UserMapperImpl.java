package com.nhry.data.auth.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.dao.UserMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.datasource.DynamicSqlSessionTemplate;

public class UserMapperImpl implements UserMapper {

	private DynamicSqlSessionTemplate sqlSessionTemplate;

	@Override
	public <T> PageInfo<T> selectByUserName(String uname, int pageNum,
			int pageSize) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("selectByUserName", uname,pageNum, pageSize);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int addUser(TSysUser user) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("adduser1", user);
	}

	@Override
	public <T> PageInfo<T> selectByPage(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("all", pageNum, pageSize);
	}

	@Override
	public TSysUser login(TSysUser user) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("login",user);
	}

	@Override
	public int deleteByPrimaryKey(String loginName) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("deleteByPrimaryKey", loginName);
	}

	@Override
	public TSysUser selectByPrimaryKey(String loginName) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectByPrimaryKey",loginName);
	}

	@Override
	public int updateByPrimaryKey(TSysUser record) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateByPrimaryKey", "updateByPrimaryKey");
	}
}
