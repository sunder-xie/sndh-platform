package com.nhry.service.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.order.*;
import com.nhry.service.order.pojo.OrderRemainData;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
	List editOrderForLongForViewPlans(OrderEditModel record);
	
	TPreOrder selectLatestOrder(String vipNo);
	
	List<TOrderDaliyPlanItem> searchDaliyPlansByStatus(String orderNo, String status1,String status2,String status3);
	
	PageInfo searchNeedResumeOrders(OrderSearchModel smodel);

	PageInfo searchReNeedOrdersByMp(OrderSearchModel smodel);

	PageInfo searchOrderByMp(OrderSearchModel smodel);
	
	PageInfo searchOrders(OrderSearchModel smodel);
	
	PageInfo searchDaliyOrders(OrderSearchModel smodel);
	
	OrderSearchModel calculateContinueOrder(OrderSearchModel record);

	String createOrder(OrderCreateModel record);

	String createOrders(List<OrderCreateModel> record);

	int editOrderForLong(OrderEditModel record);

	int editOrderForShort(DaliyPlanEditModel record);

	int batchStopOrderForTime(OrderSearchModel record);
	
	int stopOrderForTime(OrderSearchModel record);
	
	int stopOrderInTime(OrderSearchModel record);

	int backOrder(OrderSearchModel record);

	int batchContinueOrder(OrderSearchModel record);
	
	int continueOrderAuto(String orderNo,String memoTxt);
	
	int continueOrder(OrderSearchModel record);
	
	int batchContinueOrdeAfterStop(OrderSearchModel record);
	
	int continueOrdeAfterStop(OrderSearchModel record);

	int modifyOrderStatus(TPreOrder record);

	OrderCreateModel selectOrderByCode(String orderCode);

	PageInfo searchReturnOrders(ManHandOrderSearchModel manHandModel);

	TPreOrder manHandOrderDetail(String orderCode);

	int uptManHandOrder(UpdateManHandOrderModel uptManHandModel);

	int returnOrder(UpdateManHandOrderModel uptManHandModel);

	int orderConfirm(UpdateManHandOrderModel uptManHandModel);

	int batchorderConfirm(UpdateManHandOrderModel uptManHandModel);

	int canOrderUnsubscribe(String orderNo);

	List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderCode);
	
	void resumeDaliyPlanForRouteOrder(BigDecimal confirmQty,TDispOrderItem entry,TPlanOrderItem orgEntry,Date dispDate);
	
	List<TOrderDaliyPlanItem> createDaliyPlan(TPreOrder order ,List<TPlanOrderItem> entries);
	
	OrderRemainData searchOrderRemainData(String phone);

	CollectOrderModel queryCollectByOrderNo(String orderCode);

	TPlanOrderItem calculateAmtAndEndDateForFront(TPlanOrderItem item);
	
	TPlanOrderItem calculateTotalQtyForFront(TPlanOrderItem item);
	
	int updateOrderAndEntriesDispStartDate(String orderNo,List<TPlanOrderItem> entries);
	
   int selectRequiredOrderNum();
   
   int selectStopOrderNum();
   
   int createDaliyPlansForIniOrders(String str);
   
   int replaceOrdersDispmember(OrderSearchModel record);
   
   List<TOrderDaliyPlanItem> viewDaliyPlans(OrderCreateModel record);
   
   int recoverStopDaliyDaliyPlan(TOrderDaliyPlanItem item); 
   
   void returnOrderRemainAmtToAcct(String orderNo,Date dispDate);
   
   void setOrderToFinish(String orderNo,Date dispDate);
   
   void reEditDaliyPlansByRouteDetail(RouteDetailUpdateModel newItem , TDispOrderItem orgItem , Date dispDate);

	int selectUnfinishOrderNum(String vipCustNo);

	int searchReturnOrdersNum();

	BigDecimal calPreOrderTotalFactoryPrice(String orderNo);

	void selectOrdersAndSendMessage();

	PageInfo searchPendingConfirmUnOnline(OrderSearchModel smodel);
	PageInfo searchPendingConfirmOnline(OrderSearchModel smodel);

	int orderConfirmUnOnline(UpdateManHandOrderModel uptManHandModel);
	int batchOrderConfirmUnOnline(UpdateManHandOrderModel uptManHandModel);
	int advanceBackOrder(OrderSearchModel record);
	int updateBackState();

	int uptOrderlong(OrderEditModel record);

	List uptOrderlongForViewPlans(OrderEditModel record);
	int backUnBranchOrder(UpdateManHandOrderModel smodel);

	int continueOrdeAfterStop2(OrderSearchModel record);

	int uptDispDateProm(TOrderDaliyPlanItem plan);
}
