package com.nhry.service.stud.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.stud.dao.TMdSchoolRuleMapper;
import com.nhry.data.stud.domain.TMdSchoolRule;
import com.nhry.model.stud.SchoolRuleQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.stud.dao.SchoolRuleService;

/**
*
*@author dai
*/
public class SchoolRuleServiceImpl  implements SchoolRuleService {
	
	@Autowired
	private TMdSchoolRuleMapper tMdSchoolRuleMapper;
	
	@Autowired
	private UserSessionService userSessionService;
	
	
	@Override
	public PageInfo<TMdSchoolRule> findSchoolRulePage(SchoolRuleQueryModel model) {
		TSysUser sysuser = userSessionService.getCurrentUser();
		if(StringUtils.isEmpty(sysuser.getSalesOrg())){
			return null;
		}
		model.setSalesOrg(sysuser.getSalesOrg());
		return tMdSchoolRuleMapper.serchSchoolRuleList(model);
	}
	

	@Override
	public int uptSchoolRule(TMdSchoolRule tMdSchoolRule) {
		TSysUser sysuser = this.userSessionService.getCurrentUser();
		if(StringUtils.isEmpty(sysuser.getSalesOrg())){
			return 0;
		}
		return tMdSchoolRuleMapper.uptSchoolRule(tMdSchoolRule);
	}


}
