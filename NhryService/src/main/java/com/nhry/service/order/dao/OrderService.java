package com.nhry.service.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.*;
import com.nhry.service.order.pojo.OrderRemainData;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
	List<TOrderDaliyPlanItem> searchDaliyPlansByStatus(String orderNo, String status1,String status2,String status3);
	
	PageInfo searchOrders(OrderSearchModel smodel);
	
	PageInfo searchDaliyOrders(OrderSearchModel smodel);

	String createOrder(OrderCreateModel record);

	int editOrderForLong(OrderEditModel record);

	int editOrderForShort(DaliyPlanEditModel record);

	int batchStopOrderForTime(OrderSearchModel record);
	
	int stopOrderForTime(OrderSearchModel record);

	int backOrder(OrderSearchModel record);

	int continueOrder(OrderSearchModel record);
	
	int continueOrdeAfterStop(OrderSearchModel record);

	int modifyOrderStatus(TPreOrder record);

	OrderCreateModel selectOrderByCode(String orderCode);

	PageInfo searchReturnOrders(ManHandOrderSearchModel manHandModel);

	TPreOrder manHandOrderDetail(String orderCode);

	int uptManHandOrder(UpdateManHandOrderModel uptManHandModel);

	int returnOrder(ReturnOrderModel returnOrderModel);

	int canOrderUnsubscribe(String orderNo);

	List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderCode);
	
	void resumeDaliyPlanForRouteOrder(BigDecimal confirmQty,TDispOrderItem entry,TPlanOrderItem orgEntry,Date dispDate);
	
	List<TOrderDaliyPlanItem> createDaliyPlan(TPreOrder order ,List<TPlanOrderItem> entries);
	
	OrderRemainData searchOrderRemainData(String phone);

	CollectOrderModel queryCollectByOrderNo(String orderCode);

	TPlanOrderItem calculateAmtAndEndDateForFront(TPlanOrderItem item);
}
