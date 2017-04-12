package com.nhry.data.stud.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stud.dao.TMdSchoolMaraRuleBaseMapper;
import com.nhry.data.stud.domain.TMdSchoolMaraRuleBase;
import com.nhry.model.stud.SchoolMaraRuleModel;

/**
*
*@author dai
*/
public class TMdSchoolMaraRuleBaseMapperImpl  implements TMdSchoolMaraRuleBaseMapper{

	@Autowired
	private DynamicSqlSessionTemplate sqlSessionTemplate;

	@Override
	public TMdSchoolMaraRuleBase findMaraRuleBaseByModel(SchoolMaraRuleModel mdel) {
		return sqlSessionTemplate.selectOne("findMaraRuleBaseByModel", mdel);
	}

	@Override
	public int intsertinfo(TMdSchoolMaraRuleBase tMdSchoolMaraRuleBase) {
		return sqlSessionTemplate.insert("saveInfo",tMdSchoolMaraRuleBase);
	}
	
	
	@Override
	public int deleteByMid(String mid) {
		return sqlSessionTemplate.insert("deleteByMid",mid);
	}
	/*
	@Override
	public int uptInfo(TMdSchoolMaraRuleBase tMdSchoolMaraRuleBase) {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<TMdSchoolMaraRule> findSchoolMaraRule(SchoolMaraRuleModel mdel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("findSchoolMaraRule",mdel);
	}

	@Override
	public int intsertinfo(TMdSchoolMaraRule tMdSchoolMaraRule) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("intsertinfo",tMdSchoolMaraRule);
	}
	
	@Override
	public int deleteByModel(SchoolMaraRuleModel mdel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("deleteByModel",mdel);
	}*/
	
}
