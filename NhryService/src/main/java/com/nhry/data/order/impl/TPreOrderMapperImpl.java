/**
 * @project:NhryService
 * @package:com.nhry.data.order.impl
 * @copyright: Copyright[2016-2999] [Markor Investment Group Co. LTD]. All Rights Reserved.
 * @filename: TPreOrderMapperImpl.java
 * @description:<描述>
 * @author: Himari
 * @date: 20 Jun 2016-10:17:22 am
 * @version: 1.0
 * @since: JDK 1.8
 */
package com.nhry.data.order.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderSearchModel;

public class TPreOrderMapperImpl implements TPreOrderMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public PageInfo selectOrdersByPages(OrderSearchModel smodel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchOrders",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}
	
	@Override
	public int deleteByPrimaryKey(String orderNo)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int insert(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("insertNewOrder", record);
	}
	
	@Override
	public int insertSelective(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public TPreOrder selectByPrimaryKey(String orderNo)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int updateByPrimaryKeySelective(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateByPrimaryKey(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}