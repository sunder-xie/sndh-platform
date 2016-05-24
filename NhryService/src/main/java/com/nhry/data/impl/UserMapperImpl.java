package com.nhry.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.UserMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.SysUser;
import com.nhry.domain.TSysUser;

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
	public TSysUser login(String uname, String pwd) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("uname", uname);
		attrs.put("pwd", pwd);
		return sqlSessionTemplate.selectOne("login",attrs);
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
