package com.nhry.service.stud.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.stud.dao.TMdSchoolMaraRuleBaseMapper;
import com.nhry.data.stud.dao.TMdSchoolMaraRuleMapper;
import com.nhry.data.stud.domain.TMdSchoolMaraRule;
import com.nhry.data.stud.domain.TMdSchoolMaraRuleBase;
import com.nhry.model.stud.SchoolMaraRuleModel;
import com.nhry.service.stud.dao.SchoolMaraRuleService;

/**
*
*@author dai
*/
public class SchoolMaraRuleServiceImpl implements SchoolMaraRuleService {
	

	@Autowired
	private TMdSchoolMaraRuleMapper tMdSchoolMaraRuleMapper;
	
	
	@Autowired
	private TMdSchoolMaraRuleBaseMapper tMdSchoolMaraRuleBaseMapper;
	
	@Autowired
	private UserSessionService userSessionService;
	
	
	@Override
	public List<TMdSchoolMaraRule> findSchoolMaraRule(SchoolMaraRuleModel mdel) {
		TSysUser currentUser = userSessionService.getCurrentUser();
		if(null == mdel || null == mdel.getSchoolCode()){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "学校代码必传");
		}
		
		if(StringUtils.isBlank(currentUser.getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前用户未关联销售组织");
		}
		
		mdel.setSalesOrg(currentUser.getSalesOrg());
		return tMdSchoolMaraRuleMapper.findSchoolMaraRule(mdel);
	}

	@Override
	public int intsertinfo(SchoolMaraRuleModel mdel) {
		TSysUser currentUser = userSessionService.getCurrentUser();
		TMdSchoolMaraRuleBase ruleBase = mdel.getRuleBase();
		List<TMdSchoolMaraRule> tMdSchoolMaraRuleList = mdel.getSchoolMaraList();
		if(null == mdel || null == mdel.getSchoolCode()){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "学校代码必传");
		}
		
		if(StringUtils.isBlank(currentUser.getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前用户未关联销售组织");
		}
		if(null == ruleBase && null == tMdSchoolMaraRuleList){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "全局损耗基数和学校损耗基数不能全为空");
		}
		mdel.setSalesOrg(currentUser.getSalesOrg());
		
	    if(ruleBase !=null){
	    	//删除当前损耗基数
	    	tMdSchoolMaraRuleBaseMapper.deleteBySalesOrg(currentUser.getSalesOrg());
	    	//添加损耗基数
	    	Date date = new Date();
	    	ruleBase.setMid(UUID.randomUUID().toString().replace("-", ""));
	    	ruleBase.setCreateAt(date);
	    	ruleBase.setCreateBy(currentUser.getLoginName());
	    	ruleBase.setCreateByTxt(currentUser.getDisplayName());
	    	ruleBase.setLastModified(date);
	    	ruleBase.setLastModifiedBy(currentUser.getLoginName());
	    	ruleBase.setLastModifiedByTxt(currentUser.getDisplayName());
	    	ruleBase.setSalesOrg(currentUser.getSalesOrg());
	    	tMdSchoolMaraRuleBaseMapper.intsertinfo(ruleBase);
	    }
		
		int result=0;
		
		//删除当前奶品损耗基数
		tMdSchoolMaraRuleMapper.deleteByModel(mdel);
		//保存当前奶品损耗基数
		for (TMdSchoolMaraRule tMdSchoolMaraRule : tMdSchoolMaraRuleList) {
			Date date = new Date();
			tMdSchoolMaraRule.setMid(UUID.randomUUID().toString().replace("-", ""));
			tMdSchoolMaraRule.setCreateAt(date);
			tMdSchoolMaraRule.setCreateBy(currentUser.getLoginName());
			tMdSchoolMaraRule.setCreateByTxt(currentUser.getDisplayName());
			tMdSchoolMaraRule.setLastModified(date);
			tMdSchoolMaraRule.setLastModifiedBy(currentUser.getLoginName());
			tMdSchoolMaraRule.setLastModifiedByTxt(currentUser.getDisplayName());
			tMdSchoolMaraRule.setSalesOrg(currentUser.getSalesOrg());
			tMdSchoolMaraRule.setSchoolCode(mdel.getSchoolCode());;
			result+=tMdSchoolMaraRuleMapper.intsertinfo(tMdSchoolMaraRule);
		}
		return result;
	}

//	@Override
//	public int deleteByModel(SchoolMaraRuleModel mdel) {
//		return tMdSchoolMaraRuleMapper.deleteByModel(mdel);
//	}
	
	@Override
	public TMdSchoolMaraRuleBase findMaraRuleBaseByModel(SchoolMaraRuleModel mdel) {
		TSysUser currentUser = userSessionService.getCurrentUser();
		if(null == mdel || null == mdel.getSchoolCode()){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "学校代码必传");
		}
		
		if(StringUtils.isBlank(currentUser.getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前用户未关联销售组织");
		}
		mdel.setSalesOrg(currentUser.getSalesOrg());
		return tMdSchoolMaraRuleBaseMapper.findMaraRuleBaseByModel(mdel);
	}

}
