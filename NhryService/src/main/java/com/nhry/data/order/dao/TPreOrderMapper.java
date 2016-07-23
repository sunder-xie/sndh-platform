package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.order.ManHandOrderSearchModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.ReturnOrderModel;
import com.nhry.model.order.UpdateManHandOrderModel;
import com.nhry.service.order.pojo.OrderRemainData;

import java.util.List;

public interface TPreOrderMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(TPreOrder record);

    int updateOrderInitAmtAndCurAmt(TPreOrder record);

    TPreOrder selectByPrimaryKey(String orderNo);

    int updateOrderCurAmt(TPreOrder record);

    int updateByPrimaryKey(TPreOrder record);

	 PageInfo selectOrdersByPages(OrderSearchModel smodel);
	 
	 int updateOrderStatus(TPreOrder record);

    PageInfo searchReturnOrders(ManHandOrderSearchModel manHandModel);

    TPreOrder manHandOrderDetail(String orderCode);

    int uptManHandOrder(UpdateManHandOrderModel uptManHandModel);

    int returnOrder(ReturnOrderModel returnOrderModel);

    int orderUnsubscribe(String orderNo);
    
    int updateOrderEndDate(TPreOrder record);
    
    List<TPreOrder> selectDispNoByGroup(String branchNo);

    PageInfo searchCustomerOrder(CustBillQueryModel cModel);

    int updateOrderPayMentStatus(String orderNo);
    
    OrderRemainData searchOrderRemainData(String memberNo);
    
    List<TPreOrder> selectByMilkmemberNo(String memberNo);
    
    List<TPreOrder> selectNodeletedByMilkmemberNo(TPreOrder order);
    
    int updateOrderSolicitor(TPreOrder order);
    

}