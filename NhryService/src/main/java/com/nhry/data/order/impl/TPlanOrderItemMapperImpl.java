package com.nhry.data.order.impl;

import java.util.List;

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
	public List<TPlanOrderItem> selectByOrderCode(String orderCode)
	{
		return sqlSessionTemplate.selectList("selectEntriesByOrderCode", orderCode);
	}
	
	@Override
	public int insert(TPlanOrderItem record)
	{
		return sqlSessionTemplate.insert("insertNewOrderEntry", record);
	}
	
	@Override
	public int updateEntryByItemNo(TPlanOrderItem record)
	{
		return sqlSessionTemplate.update("updateEntryByOrderCode", record);
	}
	
	@Override
	public TPlanOrderItem selectEntryByEntryNo(String code)
	{
		return sqlSessionTemplate.selectOne("selectEntryByEntryNo", code);
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
