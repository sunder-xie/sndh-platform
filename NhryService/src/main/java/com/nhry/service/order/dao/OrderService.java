package com.nhry.service.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.order.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {

	PageInfo searchOrders(OrderSearchModel smodel);

	int createOrder(OrderCreateModel record);

	int editOrderForLong(OrderEditModel record);

	int editOrderForShort(DaliyPlanEditModel record);

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
}
