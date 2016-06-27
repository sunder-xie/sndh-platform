package com.nhry.data.milk.impl;

import java.util.List;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;

public class TDispOrderItemMapperImpl implements TDispOrderItemMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public int deleteByPrimaryKey(TDispOrderItemKey key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TDispOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectItemsByKeys", record);
	}

	@Override
	public TDispOrderItem selectByPrimaryKey(TDispOrderItemKey key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TDispOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TDispOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
