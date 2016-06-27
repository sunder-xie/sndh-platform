package com.nhry.data.milk.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderKey;
import com.nhry.model.milk.RouteOrderSearchModel;

public class TDispOrderMapperImpl implements TDispOrderMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public PageInfo selectMilkboxsByPage(RouteOrderSearchModel smodel)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchRoutePlansByPage",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}
	
	@Override
	public int deleteByPrimaryKey(TDispOrderKey key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TDispOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TDispOrder selectByPrimaryKey(TDispOrderKey key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TDispOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TDispOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
