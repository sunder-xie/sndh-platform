package com.nhry.service.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.milk.RouteOrderSearchModel;

public interface DeliverMilkService {
	PageInfo searchRouteOrders(RouteOrderSearchModel smodel);
	
	int searchRouteDetails(RouteOrderSearchModel smodel);
}
