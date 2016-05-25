package com.nhry.service.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.UserMapper;
import com.nhry.domain.SysUser;
import com.nhry.domain.TSysUser;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.service.BaseService;
import com.nhry.service.dao.UserService;
import com.nhry.utils.json.JackJson;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class UserServiceImpl extends BaseService implements UserService {
	private UserMapper userMapper;

	@Override
	public PageInfo selectByUserName(String uname, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return userMapper.selectByUserName(uname, pageNum, pageSize);
	}

	@Override
	public PageInfo selectByPage(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return userMapper.selectByPage(pageNum, pageSize);
	}

	public int addUser(JSONObject user) {
		TSysUser _user = JackJson.fromJsonToObject(user.toString(), TSysUser.class);
		if (_user == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "用户信息格式异常");
		}
		return userMapper.addUser(_user);
	}

	@Override
	public TSysUser login(TSysUser user) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getPwd())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"用户名、密码不能为空!");
		}

		TSysUser _user = userMapper.login(user);
		if(user == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"系统不存在该用户,请检查你的用户名、密码！");
		}
		return _user;
	}
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
}
