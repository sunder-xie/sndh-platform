package com.nhry.data.milk.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milktrans.UnDeliverProductSearch;
import com.nhry.service.milk.pojo.TDispOrderChangeItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	public List<TRecBotDetail> createRecBotByDispOrder(String dispOrderNo) {
		return sqlSessionTemplate.selectList("createRecBotByDispOrder",dispOrderNo);
	}

	@Override
	public PageInfo searchUndeliverProduct(UnDeliverProductSearch uSearch) {
		return sqlSessionTemplate.selectListByPages("searchUndeliverProduct",uSearch, Integer.parseInt(uSearch.getPageNum()), Integer.parseInt(uSearch.getPageSize()));
	}

	@Override
	public List<TDispOrderItem> selectItemsByOrderNo(String dispOrderNo) {
		return sqlSessionTemplate.selectList("selectItemsByOrderNo",dispOrderNo);
	}


	@Override
	public List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectDispItemsByKey", record);
	}
	
	@Override
	public List<TDispOrderItem> selectItemsByOrgOrderAndItemNo(String orderNo, String itemNo, Date date)
	{
		TDispOrderItemKey record = new TDispOrderItemKey();
		record.setOrderNo(orderNo);
		record.setItemNo(itemNo);
		record.setOrderDate(date);
		return sqlSessionTemplate.selectList("selectItemsByOrgOrderAndItemNo", record);
	}
	
	@Override
	public List<TDispOrderItem> selectItemsByConfirmed()
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectItemsByConfirmed");
	}

	@Override
	public List<TDispOrderChangeItem> selectDispItemsChange(String yestoday,String today,String orderNo)
	{
		TDispOrderItem key = new TDispOrderItem();
		key.setOrderNo(yestoday);
		key.setItemNo(today);
		key.setAddressNo(orderNo);
		return sqlSessionTemplate.selectList("selectDispItemsChange", key);
	}

	@Override
	public int updateByPrimaryKeySelective(TDispOrderItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDispOrderItem(RouteDetailUpdateModel record,TPlanOrderItem entry,Map<String,String>productMap)
	{
		//用原订单行的价格
		BigDecimal orgPrice = entry.getSalesPrice();
		TDispOrderItem key = new TDispOrderItem();
		key.setOrderNo(record.getOrderNo());
		key.setItemNo(record.getItemNo());
		key.setConfirmQty(new BigDecimal(record.getConfirmQty()));
		key.setReason(record.getReason());//当换货时，有换货原因
		if("10".equals(key.getReason()))key.setReplaceReason(record.getReplaceReason());
		key.setStatus("30");//30 回执确认
		key.setConfirmMatnr(record.getProductCode());
		key.setConfirmAmt(key.getConfirmQty().multiply(orgPrice));
		//回瓶规格
		if(!record.getMatnr().equals(record.getProductCode())){
			if(productMap.containsKey(record.getProductCode())){
				if(productMap.get(record.getProductCode()).equals("10")){
					key.setRetQtyS(key.getConfirmQty().intValue());
				}
				else if(productMap.get(record.getProductCode()).equals("20")){
					key.setRetQtyM(key.getConfirmQty().intValue());
				}
				else if(productMap.get(record.getProductCode()).equals("30")){
					key.setRetQtyB(key.getConfirmQty().intValue());
				}
				else{
					key.setRetQtyS(0);
					key.setRetQtyM(0);
					key.setRetQtyB(0);
				}
			}else{
				key.setRetQtyS(0);
				key.setRetQtyM(0);
				key.setRetQtyB(0);
			}
		}
//		key.setLastModified(new Date());
//		key.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
//		key.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
		return sqlSessionTemplate.update("updateDispOrderItem", key);
	}
	
	@Override
	public PageInfo selectRouteDetailsByPage(RouteOrderSearchModel smodel)
	{
		return sqlSessionTemplate.selectListByPages("selectRouteDetailsByPage",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}

	@Override
	public List<TDispOrderItem> selectNotDeliveryItemsByKeys(String code)
	{
		return sqlSessionTemplate.selectList("selectNotDeliveryItemsByKeys", code);
	}
	
	@Override
	public TDispOrderItem selectDispOrderItemByKey(TDispOrderItemKey code)
	{
		return sqlSessionTemplate.selectOne("selectDispOrderItemByKey", code);
	}

	@Override
	public List selectRouteDetails(String routeCode)
	{
		return sqlSessionTemplate.selectList("selectRouteDetailsAll", routeCode);
	}

}
