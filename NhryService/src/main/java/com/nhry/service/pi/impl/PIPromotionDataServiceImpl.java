package com.nhry.service.pi.impl;

import com.nhry.data.promotion.dao.*;
import com.nhry.data.promotion.domain.Promotion;
import com.nhry.service.pi.dao.PIPromotionDataService;
import com.nhry.service.pi.pojo.PIMessage;
import com.nhry.service.pi.pojo.PIPromotion;

import javax.jws.WebService;

/**
 * Created by cbz on 8/8/2016.
 */
@WebService
public class PIPromotionDataServiceImpl implements PIPromotionDataService {
    PromotionMapper promotionMapper;
    PromotionScopeItemMapper promotionScopeItemMapper;
    PromotionOrigItemMapper promotionOrigItemMapper;
    PromotionGiftItemMapper promotionGiftItemMapper;
    PromotionConItemMapper promotionConItemMapper;
    @Override
    public PIMessage getPromotionData(PIPromotion piPromotion) {
        PIMessage message = new PIMessage();
        Promotion promotion = piPromotion.getPromotion();

        return message;
    }
}
