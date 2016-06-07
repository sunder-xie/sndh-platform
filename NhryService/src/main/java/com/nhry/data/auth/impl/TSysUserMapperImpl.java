package com.nhry.data.auth.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.dao.TSysUserMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.model.auth.UserQueryModel;

public class TSysUserMapperImpl implements TSysUserMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public PageInfo findUser(UserQueryModel um) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("findUser", um, Integer.parseInt(um.getPageNum()),Integer.parseInt(um.getPageSize()));
	}

	@Override
	public TSysUser login(TSysUser user) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("login", user);
	}

	@Override
	public int addUser(TSysUser user) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addUser", user);
	}

	@Override
	public TSysUser findUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findUserByLoginName", loginName);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int updateUser(TSysUser record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateUser", record);
	}

	@Override
	public int updateUserPw(TSysUser record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateUserPw", record);
	}

	@Override
	public int deleteUserByLoginName(TSysUser user) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("deleteUserByLoginName", user);
	}
}
