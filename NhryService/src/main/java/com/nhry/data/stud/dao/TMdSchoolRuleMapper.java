package com.nhry.data.stud.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stud.domain.TMdSchoolRule;
import com.nhry.model.stud.SchoolQueryModel;
import com.nhry.model.stud.SchoolRuleQueryModel;

public interface TMdSchoolRuleMapper {
	/**
	 * 获取该销售组织的订奶信息
	 * @param model
	 * @return
	 */
	PageInfo<TMdSchoolRule> serchSchoolRuleList(SchoolRuleQueryModel model);
   
	
	/**
	 * 更新学校政策
	 */
	int uptSchoolRule(TMdSchoolRule tMdSchoolRule);
}