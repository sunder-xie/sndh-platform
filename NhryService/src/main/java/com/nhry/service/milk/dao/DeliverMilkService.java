package com.nhry.service.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milk.RouteOrderModel;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milk.RouteUpdateModel;
import com.nhry.model.milktrans.UnDeliverProductSearch;

public interface DeliverMilkService {
	PageInfo searchRouteOrders(RouteOrderSearchModel smodel);
	
	RouteOrderModel searchRouteDetails(String orderNo);
	
	int updateRouteOrder(RouteUpdateModel record);
	
	int updateRouteOrderItems(RouteDetailUpdateModel record);
	
	int createDayRouteOder();

	PageInfo searchUndeliverProduct(UnDeliverProductSearch uSearch);

	int createInsideSalOrder(String dispOrderNo);
}
