package com.nhry.service.stud.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nhry.common.auth.UserSessionService;
import com.nhry.data.stud.dao.TMdMaraStudMapper;
import com.nhry.data.stud.domain.TMdMaraStud;
import com.nhry.service.stud.dao.MaraStudService;

/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
public class MaraStudServiceImpl implements MaraStudService {
	
	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private TMdMaraStudMapper maraStudMapper;
	
	@Override
	public List<TMdMaraStud> findAllListBySalesOrg(String salesOrg) {
		return maraStudMapper.findAllListBySalesOrg(salesOrg);
	}

	@Override
	public List<TMdMaraStud> findAllListBySalesOrg() {
		return this.findAllListBySalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
	}

}
