package com.nhry.data.order.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TMilkboxPlanMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.dao.TPromotionMapper;
import com.nhry.data.order.domain.TMilkboxPlan;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.order.ManHandOrderSearchModel;
import com.nhry.model.order.MilkboxSearchModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.ReturnOrderModel;
import com.nhry.model.order.UpdateManHandOrderModel;

public class TPromotionMapperImpl implements TPromotionMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public List<TPromotion> selectPromotionByMatnr(TPromotion record)
	{
		return sqlSessionTemplate.selectList("selectPromotionByMatnr", record);
	}

	@Override
	public TPromotion selectPromotionByPromNo(TPromotion record)
	{
		return sqlSessionTemplate.selectOne("selectPromotionByPromNo", record);
	}
	
	
	
}