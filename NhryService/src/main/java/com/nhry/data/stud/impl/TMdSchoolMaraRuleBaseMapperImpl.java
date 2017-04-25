package com.nhry.data.stud.impl;

import java.util.HashMap;
import java.util.Map;

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
	public int deleteBySalesOrg(String salesOrg) {
		return sqlSessionTemplate.insert("deleteBySalesOrg",salesOrg);
	}

}
