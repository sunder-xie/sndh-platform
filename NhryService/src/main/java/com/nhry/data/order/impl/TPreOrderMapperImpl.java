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

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.BranchBillSearch;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.order.ManHandOrderSearchModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.ReturnOrderModel;
import com.nhry.model.order.UpdateManHandOrderModel;

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
	public int updateOrderStatus(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("updateOrderStatus", record);
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
	public int deleteByPrimaryKey(String orderNo)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int insertSelective(TPreOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
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
