package com.nhry.data.basic.impl;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.domain.TMdMaraEx;

public class TMdMaraExMapperImpl implements TMdMaraExMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public int uptProductExByCode(TMdMaraEx record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptProductExByCode", record);
	}

	@Override
	public int addMaraEx(TMdMaraEx record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addMaraEx", record);
	}

	@Override
	public TMdMaraEx findProductExByCode(Map<String,String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findProductExByCode", attrs);
	}
}
