package com.nhry.data.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdPriceMapper;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.model.basic.PriceQueryModel;

public class TMdPriceMapperImpl implements TMdPriceMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int addNewPriceGroup(TMdPrice record)
	{
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addNewPriceGroup", record);
	}

	@Override
	public int disablePriceGroup(TMdPrice record)
	{
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("disablePriceGroup", record);
	}

	@Override
	public int updatePriceGroup(TMdPrice record)
	{
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updatePriceGroup", record);
	}

	@Override
	public PageInfo searchPriceGroups(PriceQueryModel smodel)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchPriceGroups",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}

	@Override
	public TMdPrice selectPriceGroupByCode(Integer id)
	{
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectPriceGroupByCode", id);
	}

}
