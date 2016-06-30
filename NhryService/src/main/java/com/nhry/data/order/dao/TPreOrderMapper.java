package com.nhry.data.order.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.order.ManHandOrderSearchModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.ReturnOrderModel;
import com.nhry.model.order.UpdateManHandOrderModel;

public interface TPreOrderMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(TPreOrder record);

    int insertSelective(TPreOrder record);

    TPreOrder selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(TPreOrder record);

    int updateByPrimaryKey(TPreOrder record);

	 PageInfo selectOrdersByPages(OrderSearchModel smodel);
	 
	 int updateOrderStatus(TPreOrder record);

    PageInfo searchReturnOrders(ManHandOrderSearchModel manHandModel);

    TPreOrder manHandOrderDetail(String orderCode);

    int uptManHandOrder(UpdateManHandOrderModel uptManHandModel);

    int returnOrder(ReturnOrderModel returnOrderModel);

    int orderUnsubscribe(String orderNo);
    
    int updateOrderEndDate(TPreOrder record);
    
    List<TPreOrder> selectDispNoByGroup();

    PageInfo searchCustomerOrder(CustBillQueryModel cModel);

    int updateOrderPayMentStatus(String orderNo);

}