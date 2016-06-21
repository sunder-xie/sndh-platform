package com.nhry.service;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;

public class BaseService {
    
	protected UserSessionService userSessionService;

	public void setUserSessionService(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}
}
