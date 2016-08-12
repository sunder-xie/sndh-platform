package com.nhry.data.promotion.dao;

import com.nhry.data.promotion.domain.PromotionScopeItem;

public interface PromotionScopeItemMapper {

    int insertScopeItem(PromotionScopeItem record);

    PromotionScopeItem selectScopeItem(PromotionScopeItem key);
}