package com.nhry.service.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milk.RouteOrderModel;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milk.RouteUpdateModel;

public interface DeliverMilkService {
	PageInfo searchRouteOrders(RouteOrderSearchModel smodel);
	
	RouteOrderModel searchRouteDetails(String orderNo);
	
	int updateRouteOrder(RouteUpdateModel record);
	
	int updateRouteOrderItems(RouteDetailUpdateModel record);
	public int createInsideSalOrder(String dispOrderNo);
	
	int createDayRouteOder();
	
	int createRouteChanges();
	
	int updatePreOrderCurAmt();
}
