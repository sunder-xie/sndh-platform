package com.nhry.data.promotion.dao;

import com.nhry.data.promotion.domain.PromotionScopeItem;
import com.nhry.data.promotion.domain.PromotionScopeItemKey;

public interface PromotionScopeItemMapper {

    int insertScopeItem(PromotionScopeItem record);

    PromotionScopeItem selectScopeItem(PromotionScopeItemKey key);

    int updateScopeItemSelective(PromotionScopeItem record);

    int updateScopeItem(PromotionScopeItem record);
}