package com.nhry.service.order.dao;

import java.util.List;

import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.data.order.domain.TPromotion;

public interface PromotionService {
	
	List<TPromotion> getPromotionByMatnr(String code);
	
	TPromotion selectPromotionByPromNo(String code);
	
	void calculateEntryPromotion(TPlanOrderItem entry);
	
	void calculateEntryPromotionForStop(TPlanOrderItem entry);

	void createDaliyPlanByPromotion(TPreOrder order, List<TPlanOrderItem> entriesList,List<TOrderDaliyPlanItem> daliyPlans);
}
