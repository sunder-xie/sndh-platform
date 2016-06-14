package com.nhry.data.config.impl;

import com.nhry.data.config.dao.NHSysCodeTypeMapper;
import com.nhry.data.config.domain.NHSysCodeType;
import com.nhry.datasource.DynamicSqlSessionTemplate;

public class NHSysCodeTypeMapperImpl implements NHSysCodeTypeMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public int delSysCodeTypeByCode(String typeCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("delSysCodeTypeByCode", typeCode);
	}

	@Override
	public int addSysCodeType(NHSysCodeType record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addSysCodeType", record);
	}

	@Override
	public NHSysCodeType findCodeTypeByCode(String typeCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findCodeTypeByCode", typeCode);
	}

	@Override
	public int updateSysCodeType(NHSysCodeType record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateSysCodeType", record);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}
