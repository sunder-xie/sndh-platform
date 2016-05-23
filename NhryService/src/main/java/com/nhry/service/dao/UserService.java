package com.nhry.service.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.User;

import java.util.List;

public interface UserService {
	public PageInfo selectByUserName(String uname,int pageNum,int pageSize);

	public PageInfo selectByPage(int pageNum,int pageSize);

	public int greeUser(User user);

	List<User> all();
}
