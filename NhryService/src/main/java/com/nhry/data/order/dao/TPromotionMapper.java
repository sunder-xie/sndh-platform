package com.nhry.data.order.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.order.OrderSearchModel;

public interface TPromotionMapper {
    List<TPromotion> selectPromotionByMatnr(TPromotion record);

    TPromotion selectPromotionByPromNo(TPromotion record);
    
    PageInfo selectPromotionsrsByPage(OrderSearchModel smodel);
}