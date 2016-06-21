package com.nhry.data.order.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.domain.TPlanOrderItem;

public class TPlanOrderItemMapperImpl implements TPlanOrderItemMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public int deleteByPrimaryKey(String itemNo)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TPlanOrderItem record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("insertNewOrderEntry", record);
	}
	
	@Override
	public int insertSelective(TPlanOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public TPlanOrderItem selectByPrimaryKey(String itemNo)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int updateByPrimaryKeySelective(TPlanOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateByPrimaryKey(TPlanOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
