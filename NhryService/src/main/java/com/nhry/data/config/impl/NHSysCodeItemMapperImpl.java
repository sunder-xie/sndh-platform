package com.nhry.data.config.impl;

import java.util.List;

import com.nhry.data.config.dao.NHSysCodeItemMapper;
import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.data.config.domain.NHSysCodeItemKey;
import com.nhry.datasource.DynamicSqlSessionTemplate;

public class NHSysCodeItemMapperImpl implements NHSysCodeItemMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public int deleteByPrimaryKey(NHSysCodeItemKey key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(NHSysCodeItem record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NHSysCodeItem selectByPrimaryKey(NHSysCodeItemKey key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(NHSysCodeItem record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<NHSysCodeItem> getCodeItemsByTypeCode(String typecode) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("getCodeItemsByTypeCode", typecode);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
