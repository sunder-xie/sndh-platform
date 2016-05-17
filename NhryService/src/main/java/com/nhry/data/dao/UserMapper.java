package com.nhry.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.User;

public interface UserMapper {
	public <T> PageInfo<T> selectByUserName(@Param("uname")String uname,int pageNum,int pageSize);
	
	public List<User> all();
	
	public int addUser(User user);
}
