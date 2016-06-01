package com.nhry.data.impl;

import com.nhry.data.dao.NHSysParameterMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.NHSysParameter;

public class NHSysParameterMapperImpl implements NHSysParameterMapper {
	
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int deleteSysParamByCode(String paramCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("deleteSysParamByCode","paramCode");
	}

	@Override
	public int insert(NHSysParameter record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("insert", record);
	}

	@Override
	public NHSysParameter selectSysParamByCode(String paramCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectSysParamByCode", paramCode);
	}

	@Override
	public int uptSysParamByCode(NHSysParameter record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptSysParamByCode", record);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}
