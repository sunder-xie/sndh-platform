package com.nhry.data.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.UserMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.User;

public class UserMapperImpl implements UserMapper {
	
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public <T> PageInfo<T> selectByUserName(String uname,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("selectByUserName", uname,pageNum,pageSize);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public List<User> all() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("all");
	}

	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
	  return sqlSessionTemplate.insert("adduser1", user);
	}
}
