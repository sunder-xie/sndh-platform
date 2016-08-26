package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.CustBatchBillQueryModel;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.order.*;
import com.nhry.service.order.pojo.OrderRemainData;

import java.math.BigDecimal;
import java.util.List;

public interface TPreOrderMapper {
	 int updateOrderResumed(String orderNo);
	
    int deleteByPrimaryKey(String orderNo);

    int insert(TPreOrder record);
    
    int updateOrderEmpNo(TPreOrder record);
    
    int replaceOrdersDispmember(OrderSearchModel smodel);

    int updateOrderInitAmtAndCurAmt(TPreOrder record);

    TPreOrder selectByPrimaryKey(String orderNo);

    int updateOrderCurAmtAndInitAmt(TPreOrder record);
    
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

    BigDecimal calculateOrderFactoryAmt(String orderNo);

    int updateOrderFacAmt(BigDecimal factAmt, String orderNo);

    int selectRequiredOrderNum(OrderSearchModel smodel);
    
    int selectStopOrderNum(OrderSearchModel smodel);
    
    PageInfo selectNeedResumeOrders(OrderSearchModel smodel);
    
    List<TPreOrder> selectIniOrders();

    List<String> searchCustomerOrderForExp(CustBillQueryModel cModel);

    List<TPreOrder> searchCustomerOrderByEmpNo(CustBatchBillQueryModel model);

    int uptYfrechAndYGrowthByOrderNo(OrderPointModel model);

    List<TPreOrder> selectCustBatchCollect(OrderSearchModel oModel);

    BigDecimal calculateAfterOrderFactoryAmt(String orderNo);
}