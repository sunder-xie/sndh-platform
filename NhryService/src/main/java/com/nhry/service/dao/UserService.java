package com.nhry.service.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.User;

public interface UserService {
	public PageInfo selectByUserName(String uname,int pageNum,int pageSize);
}
