package com.nhry.service.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.PromotionOrderModel;
import com.nhry.model.order.TPromotionModel;

import java.math.BigDecimal;
import java.util.List;

public interface PromotionService {
	
	PageInfo selectPromotionsrsByPage(OrderSearchModel smodel);
	
	List<TPromotion> getPromotionByMatnr(String code);
	
	TPromotion selectPromotionByPromNo(String code);
	
	void calculateEntryPromotion(TPlanOrderItem entry);
	
	void calculateEntryPromotionForStop(TPlanOrderItem entry);

	void createDaliyPlanByPromotion(TPreOrder order, List<TPlanOrderItem> entriesList,List<TOrderDaliyPlanItem> daliyPlans);

	List<TPromotion> selectAllPromotionByOrder(PromotionOrderModel model);

	List<TPromotion> selectPromotionByOneMatnr(TPromotion promotion);

	List<TPromotion> selectPromotionByOrder(TPromotion promotion);

	List<TPromotion> selectPromotionByYear(TPromotion promotion);

	void calculateOrderEntryPromotion(TPlanOrderItem entry, BigDecimal entryAmount,TPreOrder order);

	void calculateOrderPromotion(TPreOrder order);

	List<TPromotionModel> selectProCreatOrder(OrderCreateModel record);
}
