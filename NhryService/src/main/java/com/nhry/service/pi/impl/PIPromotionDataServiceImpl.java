package com.nhry.service.pi.impl;

import com.nhry.data.promotion.domain.*;
import com.nhry.service.pi.dao.PIPromotionDataService;
import com.nhry.service.pi.pojo.PIMessage;
import com.nhry.service.pi.pojo.PIPromotion;
import com.nhry.service.promotion.dao.PromotionDataService;

import javax.jws.WebService;
import java.util.List;
/**
 * Created by cbz on 8/8/2016.
 */
@WebService
public class PIPromotionDataServiceImpl implements PIPromotionDataService {
    PromotionDataService promotionDataService;

    public void setPromotionDataService(PromotionDataService promotionDataService) {
        this.promotionDataService = promotionDataService;
    }

    @Override
    public PIMessage getPromotionData(PIPromotion piPromotion) {

        PIMessage message = new PIMessage();
        try {
            Promotion promotion = piPromotion.getPromotion();
            List<PromotionConItem> conItems = piPromotion.getPromotionConItems();
            List<PromotionGiftItem> giftItems = piPromotion.getPromotionGiftItems();
            List<PromotionScopeItem> scopeItems = piPromotion.getPromotionScopeItems();
            List<PromotionOrigItem> origItems = piPromotion.getPromotionOrigItems();
            promotionDataService.savePromotion(promotion);

            if (conItems != null) {
                for (PromotionConItem conItem : conItems) {
                    promotionDataService.savePromotionConItem(conItem);
                }
            }

            if (origItems != null) {
                for (PromotionOrigItem origItem : origItems) {
                    promotionDataService.savePromotionOrigItem(origItem);
                }
            }

            if (scopeItems != null) {
                for (PromotionScopeItem scopeItem : scopeItems) {
                    promotionDataService.savePromotionScopeItem(scopeItem);
                }
            }
            if (giftItems != null) {
                for (PromotionGiftItem giftItem : giftItems) {
                    promotionDataService.savePromotionGiftItem(giftItem);
                }
            }
            message.setSuccess(true);
            message.setMessage("OK");
        }catch (Exception e){
            message.setMessage("fail");
            message.setSuccess(false);
        }
        return message;
    }
}
