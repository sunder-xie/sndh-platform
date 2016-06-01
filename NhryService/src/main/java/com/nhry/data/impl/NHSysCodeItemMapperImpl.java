package com.nhry.data.impl;

import java.util.List;

import com.nhry.data.dao.NHSysCodeItemMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.NHSysCodeItem;
import com.nhry.domain.NHSysCodeItemKey;

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
