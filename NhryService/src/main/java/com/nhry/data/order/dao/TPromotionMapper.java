package com.nhry.data.order.dao;

import java.util.List;

import com.nhry.data.order.domain.TPromotion;

public interface TPromotionMapper {
    List<TPromotion> selectPromotionByMatnr(TPromotion record);

    TPromotion selectPromotionByPromNo(TPromotion record);
}