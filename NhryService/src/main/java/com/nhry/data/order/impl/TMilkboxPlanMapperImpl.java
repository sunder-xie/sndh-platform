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

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TMilkboxPlanMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TMilkboxPlan;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.ManHandOrderSearchModel;
import com.nhry.model.order.MilkboxSearchModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.ReturnOrderModel;
import com.nhry.model.order.UpdateManHandOrderModel;

public class TMilkboxPlanMapperImpl implements TMilkboxPlanMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public PageInfo selectMilkboxsByPage(MilkboxSearchModel smodel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("selectMilkboxsByPage",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}
	
	@Override
	public TMilkboxPlan selectByPrimaryKey(String planNo)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectMilkboxPlanByPlanNo", planNo);
	}
	
	@Override
	public int deleteByPrimaryKey(String planNo)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int insert(TMilkboxPlan record)
	{
		// TODO Auto-generated method stub 
		return sqlSessionTemplate.insert("addNewMilkboxPlan", record);
	}
	
	@Override
	public int insertSelective(TMilkboxPlan record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateByPrimaryKeySelective(TMilkboxPlan record)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateMilkboxPlan(TMilkboxPlan record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateMilkboxPlan", record);
	}

	@Override
	public int updateMilkboxPlans(Map<String, Object> params)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateMilkboxPlans", params);
	}

	@Override
	public int updateMilkboxPlanPrinted(String code)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateMilkboxPlanPrinted", code);
	}
	
	
}
