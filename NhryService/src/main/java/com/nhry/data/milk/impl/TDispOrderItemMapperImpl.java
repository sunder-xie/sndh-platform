package com.nhry.data.milk.impl;

import java.math.BigDecimal;
import java.util.List;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.service.milk.pojo.TDispOrderChangeItem;

public class TDispOrderItemMapperImpl implements TDispOrderItemMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
//	private UserSessionService userSessionService;
//	public void setUserSessionService(UserSessionService userSessionService) {
//		this.userSessionService = userSessionService;
//	}
	
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
	public int batchinsert(List<TDispOrderItem> records)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("batchAddNewDispOrderItems", records);
	}

	@Override
	public List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectDispItemsByKey", record);
	}
	
	@Override
	public List<TDispOrderItem> selectItemsByConfirmed()
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectItemsByConfirmed");
	}

	@Override
	public List<TDispOrderChangeItem> selectDispItemsChange(String yestoday,String today)
	{
		TDispOrderItem key = new TDispOrderItem();
		key.setOrderNo(yestoday);
		key.setItemNo(today);
		return sqlSessionTemplate.selectList("selectDispItemsChange", key);
	}

	@Override
	public int updateByPrimaryKeySelective(TDispOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDispOrderItem(RouteDetailUpdateModel record,TPlanOrderItem entry)
	{
		//用原订单行的价格
		BigDecimal orgPrice = entry.getSalesPrice();
		TDispOrderItem key = new TDispOrderItem();
		key.setOrderNo(record.getOrderNo());
		key.setItemNo(record.getItemNo());
		key.setConfirmQty(new BigDecimal(record.getQty()));
		key.setReason(record.getReason());
		key.setStatus(record.getStatus());
		key.setConfirmMatnr(record.getProductCode());
		key.setConfirmAmt(key.getConfirmQty().multiply(orgPrice));
//		key.setLastModified(new Date());
//		key.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
//		key.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
		return sqlSessionTemplate.update("updateDispOrderItem", key);
	}

}
