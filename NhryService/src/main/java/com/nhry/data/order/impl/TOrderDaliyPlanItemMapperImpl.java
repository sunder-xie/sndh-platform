package com.nhry.data.order.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPlanOrderItem;

public class TOrderDaliyPlanItemMapperImpl implements TOrderDaliyPlanItemMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int deleteByPrimaryKey(TOrderDaliyPlanItemKey key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("insertNewOrderDaliyPlanEntry", record);
	}

	@Override
	public int insertSelective(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TOrderDaliyPlanItem selectByPrimaryKey(TOrderDaliyPlanItemKey key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
