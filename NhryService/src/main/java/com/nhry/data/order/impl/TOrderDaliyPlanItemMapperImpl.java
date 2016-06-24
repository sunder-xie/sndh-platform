package com.nhry.data.order.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.model.milktrans.RequireOrderModel;
import com.nhry.model.order.ReturnOrderModel;

import java.util.Date;
import java.util.List;

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
	public int updateDaliyPlanItemStatus(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateDaliyPlanItemStatus", record);
	}
	
	@Override
	public List<TOrderDaliyPlanItem> selectDaliyPlansByEntryNo(String itemNo)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectDaliyPlansByEntryNo", itemNo);
	}

	/**
	 * 根据订单号和日期获取当前日期的日订单状态
	 * @param orderNo
	 * @param date
	 * @return 日订单状态
	 */
	@Override
	public String getDayOrderStat(String orderNo, Date date) {
		ReturnOrderModel returnOrderModel = new ReturnOrderModel();
		returnOrderModel.setOrderNo(orderNo);
		returnOrderModel.setRetDate(date);
		TOrderDaliyPlanItem item = sqlSessionTemplate.selectOne("getDayOrderStat", returnOrderModel);
		return item.getStatus();
	}

	@Override
	public List<TOrderDaliyPlanItem> selectDaliyPlansByBranchAndDay(RequireOrderModel rModel) {
		return sqlSessionTemplate.selectList("selectDaliyPlansByBranchAndDay", rModel);
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
