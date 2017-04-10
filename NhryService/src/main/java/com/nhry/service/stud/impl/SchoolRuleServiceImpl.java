package com.nhry.service.stud.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
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
public class SchoolRuleServiceImpl extends BaseService implements SchoolRuleService {
	
	@Autowired
	TMdSchoolRuleMapper tMdSchoolRuleMapper;
	
	
	@Override
	public PageInfo<TMdSchoolRule> findSchoolRulePage(SchoolRuleQueryModel model) {
		TSysUser sysuser = this.userSessionService.getCurrentUser();
		if(StringUtils.isEmpty(sysuser.getBranchNo())){
			return null;
		}
		model.setSalesOrg(sysuser.getSalesOrg());
		return tMdSchoolRuleMapper.serchSchoolRuleList(model);
	}

	@Override
	public int uptSchoolRule(TMdSchoolRule tMdSchoolRule) {
		TSysUser sysuser = this.userSessionService.getCurrentUser();
		if(StringUtils.isEmpty(sysuser.getBranchNo())){
			return 0;
		}
		return tMdSchoolRuleMapper.uptSchoolRule(tMdSchoolRule);
	}


}
