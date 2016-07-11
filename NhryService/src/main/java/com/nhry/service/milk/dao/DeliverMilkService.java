package com.nhry.service.milk.dao;

import java.math.BigDecimal;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.model.milk.RouteDetailUpdateListModel;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milk.RouteOrderModel;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milk.RouteUpdateModel;

public interface DeliverMilkService {
	PageInfo searchRouteOrders(RouteOrderSearchModel smodel);
	
	PageInfo searchRouteOrderDetail(RouteOrderSearchModel smodel);
	
	List searchRouteOrderDetailAll(String code);
	
	List searchRouteChangeOrder(String code);
	
	RouteOrderModel searchRouteDetails(String orderNo);
	
	int updateRouteOrder(RouteUpdateModel record);
	
	int updateRouteOrderAllItems(RouteDetailUpdateListModel record);
	
	int updateRouteOrderItems(RouteDetailUpdateModel record);
	public int createInsideSalOrder(String dispOrderNo);
	
	int updateDaliyPlanByRouteOrder(String orderCode);
	
	int createDayRouteOder();
	
	int createRouteChanges();
	
	int updatePreOrderCurAmt(String orderNo , BigDecimal amt);
}
