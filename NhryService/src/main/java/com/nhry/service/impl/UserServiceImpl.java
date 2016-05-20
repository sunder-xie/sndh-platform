package com.nhry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.UserMapper;
import com.nhry.domain.User;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.service.BaseService;
import com.nhry.service.dao.UserService;

public class UserServiceImpl extends BaseService implements UserService {
	private UserMapper userMapper;
	
	@Override
	public PageInfo selectByUserName(String uname,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		if(uname != null){
			throw new ServiceException(MessageCode.LOGIC_ERROR);
		}
		return userMapper.selectByUserName(uname,pageNum,pageSize);
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
//	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public int greeUser(User user) {
		// TODO Auto-generated method stub
		for(int t=1;t<=10;t++){
			User u = new User();
//			if(t  >= 9){
//				if(t%0==0){
//					
//				}
//			}
			u.setId(t);
			u.setUserName("张三"+t);
			u.setComments("备注"+t);
			userMapper.addUser(u);
		}
		return 0;
	}
}
