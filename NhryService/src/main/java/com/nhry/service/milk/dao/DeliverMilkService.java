package com.nhry.service.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.model.milk.*;
import com.nhry.model.milktrans.CreateInSalOrderModel;
import com.nhry.model.milktrans.InSideSalOrderDetailSearchModel;
import com.nhry.model.milktrans.InSideSalOrderSearchModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DeliverMilkService {
	PageInfo searchRouteOrders(RouteOrderSearchModel smodel);
	
	PageInfo searchRouteOrderDetail(RouteOrderSearchModel smodel);
	
	List searchRouteOrderDetailAll(String code);
	
	List searchRouteChangeOrder(String code);
	
	RouteOrderModel searchRouteDetails(String orderNo);
	
	int updateRouteOrder(RouteUpdateModel record);
	
	int updateRouteOrderAllItems(RouteDetailUpdateListModel record);
	
	int updateRouteOrderItems(RouteDetailUpdateModel record,Map<String,String> productMap);
	public int createInsideSalOrder(String dispOrderNo);
	
	int updateDaliyPlanByRouteOrder(String orderCode);
	
	int createDayRouteOder();
	
	int createRouteChanges();
	
	int updatePreOrderCurAmt(String orderNo , BigDecimal amt);
	
	int createDispOrderdayliy();

	PageInfo getInsideSalOrder(InSideSalOrderSearchModel sModel);

	PageInfo getInsideSalOrderDetail(InSideSalOrderDetailSearchModel sModel);

	int createInsideSalOrderByStock(CreateInSalOrderModel cModel);
	
	int reEditRouteDetail(TDispOrderItem item);

	int updateInSalOrderAndStockByUpdateDiapOrder(TDispOrderItem newItem , TDispOrderItem orgItem);
}
