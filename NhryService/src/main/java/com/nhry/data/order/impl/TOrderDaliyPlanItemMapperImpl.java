package com.nhry.data.order.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.OrderItemFactoryPrice;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.ReturnOrderModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TOrderDaliyPlanItemMapperImpl implements TOrderDaliyPlanItemMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int deleteFromDateToDate(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("deleteFromDateToDate", record); 
	}

	@Override
	public int selectMaxDaliyPlansNoByOrderNo(String orderNo)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectMaxDaliyPlansNoByOrderNo", orderNo); 
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
	public int updateDaliyPlanItemRemainAmt(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateDaliyPlanItemRemainAmt", record);
	}
	
	@Override
	public int updateDaliyPlanItem(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateDaliyPlanItem", record);
	}
	
	@Override
	public List<TOrderDaliyPlanItem> selectDaliyPlansByEntryNo(String itemNo)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectDaliyPlansByEntryNo", itemNo);
	}
	
	@Override
	public List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderNo)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectDaliyPlansByOrderNo", orderNo);
	}
	
	@Override
	public List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNoAsc(String orderNo)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("selectDaliyPlansByOrderNoAsc", orderNo);
	}
	
	@Override
	public TOrderDaliyPlanItem selectDaliyPlansByEntryNoAndNo(TOrderDaliyPlanItemKey record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectDaliyPlansByEntryNoAndNo", record);
	}
	
	@Override
	public int updateDaliyPlansToStop(TPreOrder record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		record.setStopDateStartStr(format.format(record.getStopDateStart()));
//		if(record.getStopDateEnd()!=null){
//			record.setStopDateEndStr(format.format(record.getStopDateEnd()));
//		}
		return sqlSessionTemplate.update("updateDaliyPlansToStop", record);
	}
	
	@Override
	public int updateDaliyPlansToBack(TPreOrder record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		record.setStopDateStartStr(format.format(record.getBackDate()));
		return sqlSessionTemplate.update("updateDaliyPlansToStop", record);
	}

	@Override
	public List<TOrderDaliyPlanItem> selectDaliyPlansByBranchAndDay(RequireOrderSearch rModel) {
		return sqlSessionTemplate.selectList("selectDaliyPlansByBranchAndDay", rModel);
	}

	@Override
	public List<String> getDailOrderPromOfDealerBranch(RequireOrderSearch rModel) {
		return sqlSessionTemplate.selectList("getDailOrderPromOfDealerBranch", rModel);
	}

	@Override
	public List<TOrderDaliyPlanItem> selectProDayPlanOfDealerBranch(RequireOrderSearch rModel) {
		return sqlSessionTemplate.selectList("selectProDayPlanOfDealerBranch", rModel);
	}

	@Override
	public List<TOrderDaliyPlanItem> selectNoProDayPlanOfDealerBranch(RequireOrderSearch rModel) {
		return sqlSessionTemplate.selectList("selectNoProDayPlanOfDealerBranch", rModel);
	}

	@Override
	public List<String> getDailOrderPromOfSelfBranch(RequireOrderSearch rModel) {
		return sqlSessionTemplate.selectList("getDailOrderPromOfSelfBranch", rModel);
	}

	@Override
	public List<TOrderDaliyPlanItem> selectNoProDayPlanOfSelfBranch(RequireOrderSearch rModel) {
		return sqlSessionTemplate.selectList("selectNoProDayPlanOfSelfBranch", rModel);
	}

	@Override
	public List<TOrderDaliyPlanItem> selectProDayPlanOfSelfBranch(RequireOrderSearch rModel) {
		return sqlSessionTemplate.selectList("selectProDayPlanOfSelfBranch", rModel);
	}

	@Override
	public List<TOrderDaliyPlanItem> getProductItemsByOrderNo(String orderCode,String salesOrg) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("orderNo",orderCode);
		map.put("salesOrg",salesOrg);
		return sqlSessionTemplate.selectList("getProductItemsByOrderNo", map);
	}


	/**
	 * 根据订单号和日期获取当前日期的日订单状态
	 * @param orderNo
	 * @param date
	 * @return 日订单状态
	 */
	@Override
	public String getDayOrderStat(String orderNo, Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ReturnOrderModel returnOrderModel = new ReturnOrderModel();
		returnOrderModel.setOrderNo(orderNo);
		returnOrderModel.setRetReason(format.format(date));
		List<TOrderDaliyPlanItem> items = sqlSessionTemplate.selectList("getDayOrderStat", returnOrderModel);
		for(TOrderDaliyPlanItem it : items){
			if("20".equals(it.getStatus())){
				return it.getStatus();
			}
		}
		return "10";
	}



	@Override
	public BigDecimal selectDaliyPlansRemainAmt(TOrderDaliyPlanItemKey record)
	{
		return sqlSessionTemplate.selectOne("selectDaliyPlansRemainAmt", record);
	}

	@Override
	public List<TOrderDaliyPlanItem> selectbyDispLineNo(String empNo , String date ,String reachTimeType)
	{
		TOrderDaliyPlanItemKey key = new TOrderDaliyPlanItemKey();
		key.setPlanItemNo(date);
		key.setItemNo(empNo);
		key.setOrderNo(reachTimeType);
		return sqlSessionTemplate.selectList("selectDaliyPlansByDispNo", key);
	}

	@Override
	public int updateByPrimaryKeySelective(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateFromDateToDate(TOrderDaliyPlanItem record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateFromDateToDate", record);
	}
	
	@Override
	public PageInfo selectDaliyOrdersByPages(OrderSearchModel smodel)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchDaliyOrdersByPage",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));	
	}

	@Override
	public List<TOrderDaliyPlanItem> searchDaliyOrdersByOrderNoAndFinalStop(OrderSearchModel smodel)
	{
		return sqlSessionTemplate.selectList("searchDaliyOrdersByOrderNoAndFinalStop", smodel);
	}

	@Override
	public List<TOrderDaliyPlanItem> searchDaliyPlansByStatus(String orderNo, String status1,String status2,String status3)
	{
		TOrderDaliyPlanItem smodel = new TOrderDaliyPlanItem();
		smodel.setOrderNo(orderNo);
		smodel.setStatus(status1);
		smodel.setCreateBy(status2);
		smodel.setCreateByTxt(status3);
		return sqlSessionTemplate.selectList("searchDaliyPlansByStatus", smodel);
	}

	/* (non-Javadoc) 
	* @title: deletePlansByAmt
	* @description: 
	* @param orderNo
	* @return 
	* @see com.nhry.data.order.dao.TOrderDaliyPlanItemMapper#deletePlansByAmt(java.lang.String) 
	*/
	@Override
	public int deletePlansByAmt(String orderNo)
	{
		return sqlSessionTemplate.delete("deletePlansByAmt",orderNo);
	}
	
}
