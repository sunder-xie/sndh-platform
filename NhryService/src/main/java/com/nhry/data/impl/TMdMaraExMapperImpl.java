package com.nhry.data.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.TMdMaraExMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.TMdMaraEx;

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

}
