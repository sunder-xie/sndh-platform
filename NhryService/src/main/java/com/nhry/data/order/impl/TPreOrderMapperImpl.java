package com.nhry.data.order.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.CustBatchBillQueryModel;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.order.*;
import com.nhry.service.order.pojo.OrderRemainData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TPreOrderMapperImpl implements TPreOrderMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public int updateOrderResumed(String orderNo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateOrderResumed",orderNo);
	}
	
	@Override
	public PageInfo selectOrdersByPages(OrderSearchModel smodel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchOrders",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}
	
	@Override
	public int updateOrderStatus(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateOrderStatus", record);
	}
	
	@Override
	public int updateOrderEmpNo(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateOrderEmpNo", record);
	}

	@Override
	public PageInfo searchReturnOrders(ManHandOrderSearchModel manHandModel) {
		return sqlSessionTemplate.selectListByPages("searchReturnOrders",manHandModel, Integer.parseInt(manHandModel.getPageNum()), Integer.parseInt(manHandModel.getPageSize()));
	}

	@Override
	public TPreOrder manHandOrderDetail(String orderNo) {
		return sqlSessionTemplate.selectOne("manHandOrderDetail", orderNo);
	}


	@Override
	public int uptManHandOrder(UpdateManHandOrderModel uptManHandModel) {
		return sqlSessionTemplate.update("uptManHandOrder", uptManHandModel);
	}

	@Override
	public int returnOrder(ReturnOrderModel returnOrderModel) {
		return sqlSessionTemplate.update("returnOrder", returnOrderModel);
	}

	@Override
	public int orderUnsubscribe(String orderNo) {
		return sqlSessionTemplate.update("orderUnsubscribe", orderNo);
	}

	@Override
	public PageInfo searchCustomerOrder(CustBillQueryModel cModel) {
		return sqlSessionTemplate.selectListByPages("searchCustomerOrder",cModel, Integer.parseInt(cModel.getPageNum()), Integer.parseInt(cModel.getPageSize()));

	}

	@Override
	public int updateOrderPayMentStatus(String orderNo) {
		return sqlSessionTemplate.update("updateOrderPayMentStatus", orderNo);

	}

	@Override
	public int insert(TPreOrder record)
	{
		return sqlSessionTemplate.insert("insertNewOrder", record);
	}
	
	@Override
	public TPreOrder selectByPrimaryKey(String orderNo)
	{
		return sqlSessionTemplate.selectOne("selectByOrderCode", orderNo);
	}
	
	@Override
	public int updateOrderEndDate(TPreOrder record)
	{
		return sqlSessionTemplate.update("updateOrderEndDate", record);
	}
	
	@Override
	public List<TPreOrder> selectDispNoByGroup(String branchNo)
	{
		return sqlSessionTemplate.selectList("selectDispNoByGroup",branchNo);
	}
	
	@Override
	public int deleteByPrimaryKey(String orderNo)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateOrderInitAmtAndCurAmt(TPreOrder record)
	{
		return sqlSessionTemplate.update("updateOrderInitAmtAndCurAmt",record);
	}
	
	@Override
	public int updateOrderCurAmt(TPreOrder record)
	{
		return sqlSessionTemplate.update("updateOrderCurAmt", record);
	}
	
	@Override
	public int updateOrderCurAmtAndInitAmt(TPreOrder record)
	{
		return sqlSessionTemplate.update("updateOrderCurAmtAndInitAmt", record);
	}
	
	@Override
	public int updateByPrimaryKey(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderRemainData searchOrderRemainData(String memberNo)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectOrderRemainAndAmt", memberNo);
	}
	
	@Override
	public List<TPreOrder> selectByMilkmemberNo(String memberNo)
	{
		return sqlSessionTemplate.selectList("selectByMilkmemberNo", memberNo);
	}

	@Override
	public List<TPreOrder> selectNodeletedByMilkmemberNo(TPreOrder order)
	{
		return sqlSessionTemplate.selectList("selectNodeletedByMilkmemberNo", order);
	}

	@Override
	public int updateOrderSolicitor(TPreOrder order)
	{
		return sqlSessionTemplate.update("updateOrderSolicitor", order);
	}

	@Override
	public BigDecimal calculateOrderFactoryAmt(String orderNo) {
		return sqlSessionTemplate.selectOne("calculateOrderFactoryAmt",orderNo);
	}


	@Override
	public int updateOrderFacAmt(BigDecimal factAmt, String orderNo) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("factAmt",factAmt);
		map.put("orderNo",orderNo);
		return sqlSessionTemplate.update("updateOrderFacAmt",map);
	}

	public int selectRequiredOrderNum(OrderSearchModel smodel)
	{
		return sqlSessionTemplate.selectOne("selectRequiredOrderNum",smodel);
	}

	@Override
	public int selectStopOrderNum(OrderSearchModel smodel)
	{
		return sqlSessionTemplate.selectOne("selectStopOrderNum",smodel);
	}
	
	@Override
	public PageInfo selectNeedResumeOrders(OrderSearchModel smodel) {

		return sqlSessionTemplate.selectListByPages("selectNeedResumeOrders",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}

	/* (non-Javadoc) 
	* @title: selectIniOrders
	* @description: 查找所有期初订单
	* @return 
	* @see com.nhry.data.order.dao.TPreOrderMapper#selectIniOrders() 
	*/
	@Override
	public List<TPreOrder> selectIniOrders()
	{
		return sqlSessionTemplate.selectList("selectIniOrders");
	}

	@Override
	public List<String> searchCustomerOrderForExp(CustBillQueryModel cModel) {
		return sqlSessionTemplate.selectList("searchCustomerOrderForExp",cModel);
	}

	@Override
	public List<TPreOrder> searchCustomerOrderByEmpNo(CustBatchBillQueryModel model) {
		return sqlSessionTemplate.selectList("searchCustomerOrderByEmpNo",model);
	}

	@Override
	public int uptYfrechAndYGrowthByOrderNo(OrderPointModel model) {
		return sqlSessionTemplate.update("uptYfrechAndYGrowthByOrderNo",model);
	}

	@Override
	public List<TPreOrder> selectCustBatchCollect(OrderSearchModel smodel) {
		return sqlSessionTemplate.selectList("selectCustBatchCollect",smodel);
	}

	@Override
	public BigDecimal calculateAfterOrderFactoryAmt(String orderNo) {
		return sqlSessionTemplate.selectOne("calculateAfterOrderFactoryAmt", orderNo);
	}

	/* (non-Javadoc) 
	* @title: replaceOrdersDispmember
	* @description: 替换所有a送奶员的订单为b送奶员
	* @param smodel
	* @return 
	* @see com.nhry.data.order.dao.TPreOrderMapper#replaceOrdersDispmember(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int replaceOrdersDispmember(OrderSearchModel smodel)
	{
		return sqlSessionTemplate.update("replaceOrdersDispmember", smodel);
	}
}
