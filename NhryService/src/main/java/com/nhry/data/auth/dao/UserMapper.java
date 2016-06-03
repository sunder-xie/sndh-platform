package com.nhry.data.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.SysUser;
import com.nhry.data.auth.domain.TSysUser;

public interface UserMapper {
	public <T> PageInfo<T> selectByUserName(String uname,int pageNum,int pageSize);
	
	public <T> PageInfo<T> selectByPage(int pageNum,int pageSize);
	
	public TSysUser login(TSysUser user);
	
	public int addUser(TSysUser user);
	
	int deleteByPrimaryKey(String loginName);

    TSysUser selectByPrimaryKey(String loginName);

    int updateByPrimaryKey(TSysUser record);
}
