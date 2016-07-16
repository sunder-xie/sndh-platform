package com.nhry.service.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmReqGoodsOrderItem;
import com.nhry.model.milktrans.ReqGoodsOrderItemSearch;
import com.nhry.model.milktrans.RequireOrderModel;
import com.nhry.model.milktrans.UpdateNewRequiredModel;
import com.nhry.model.milktrans.UpdateRequiredModel;

import java.util.Date;

/**
 * Created by gongjk on 2016/6/24.
 */
public interface RequireOrderService {

    RequireOrderModel creatRequireOrder();
    RequireOrderModel searchRequireOrder(Date requiredDate);

    int uptNewRequireOrderItem(UpdateNewRequiredModel uModel);

    int addRequireOrderItem(TSsmReqGoodsOrderItem item);

    int delRequireOrderItem(ReqGoodsOrderItemSearch item);

    int uptRequireOrder(UpdateRequiredModel uModel);

    int sendRequireOrderToERP();

    int creatPromoSalOrderOfDealerBranch(Date today);

    int creatNoPromoSalOrderOfDealerBranch(Date requiredDate);

    int creatNoPromoSalOrderOfSelftBranch(Date requiredDate);

    int creatPromoSalOrderOfSelftBranch(Date requiredDate);
}
