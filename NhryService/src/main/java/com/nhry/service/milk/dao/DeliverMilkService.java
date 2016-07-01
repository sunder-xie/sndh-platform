package com.nhry.service.milk.dao;

import java.math.BigDecimal;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milk.RouteOrderModel;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milk.RouteUpdateModel;

public interface DeliverMilkService {
	PageInfo searchRouteOrders(RouteOrderSearchModel smodel);
	
	PageInfo searchRouteOrderDetail(RouteOrderSearchModel smodel);
	
	RouteOrderModel searchRouteDetails(String orderNo);
	
	int updateRouteOrder(RouteUpdateModel record);
	
	int updateRouteOrderItems(RouteDetailUpdateModel record);
	
	int updateDaliyPlanByRouteOrder(String orderCode);
	
	int createDayRouteOder();
	
	int createRouteChanges();
	
	int updatePreOrderCurAmt(String orderNo , BigDecimal amt);
}
