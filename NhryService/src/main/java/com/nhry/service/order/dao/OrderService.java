package com.nhry.service.order.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TMstRequireOrder;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.*;

public interface OrderService {
	
	PageInfo searchOrders(OrderSearchModel smodel);
	
	int createOrder(OrderCreateModel record);
	
	int editOrderForLong(OrderEditModel record);
	
	int editOrderForShort(DaliyPlanEditModel record);
	
	int stopOrderForTime(OrderSearchModel record);
	
	int backOrder(OrderSearchModel record);
	
	int continueOrder(OrderSearchModel record);
	
	int modifyOrderStatus(TPreOrder record);
	
	OrderCreateModel selectOrderByCode(String orderCode);

	PageInfo searchReturnOrders(ManHandOrderSearchModel manHandModel);

	TPreOrder manHandOrderDetail(String orderCode);

	int uptManHandOrder(UpdateManHandOrderModel uptManHandModel);

	int returnOrder(ReturnOrderModel returnOrderModel);

	int canOrderUnsubscribe(String orderNo);

	RequireOrderModel creatRequireOrder(RequireOrderModel rModel);

	int insertRequireOrder(TMstRequireOrder order);

	RequireOrderModel searchRequireOrder(RequireOrderModel rModel);

	int uptRequireOrder(RequireOrderModel rModel);
	
	List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderCode);
}
