package com.nhry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.UserMapper;
import com.nhry.domain.User;
import com.nhry.service.BaseService;
import com.nhry.service.dao.UserService;

public class UserServiceImpl extends BaseService implements UserService {
	private UserMapper userMapper;
	
	@Override
	public PageInfo selectByUserName(String uname,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		return userMapper.selectByUserName(uname,pageNum,pageSize);
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public PageInfo selectByPage(int pageNum, int pageSize)
	{
		// TODO Auto-generated method stub
		return userMapper.selectByPage(pageNum, pageSize);
	}
}
