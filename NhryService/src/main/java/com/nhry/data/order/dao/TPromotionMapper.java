package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.model.order.TPromotionModel;

import java.util.List;

public interface TPromotionMapper {
    List<TPromotion> selectPromotionByMatnr(TPromotion record);

    TPromotion selectPromotionByPromNo(TPromotion record);
    
    PageInfo selectPromotionsrsByPage(OrderSearchModel smodel);

    List<TPromotion> selectPromationByOneMatnr(TPromotion record);

    List<TPromotion> selectPromationByOrder(TPromotion record);

    List<TPromotion> selectPromotionByYear(TPromotion record);

    TPromotion selectPromotionByPromNoAndItemNo(TPromotion record);
    //获取符合订单行的促销，满赠，单品满减
    List<TPromotionModel> selectPromotionByEntryAndAmt(TPromotion pro);
    //获取符合订单的促销，整单满减，年卡
    List<TPromotionModel> selectPromotionsByOrder(TPromotion pro);

    //获取销售组织下  年卡，半年卡，季卡信息
    List<TPromotionModel> selYearCardPromBySalesOrg(TPromotion prom);
}