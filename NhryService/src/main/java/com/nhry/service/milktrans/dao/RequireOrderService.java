package com.nhry.service.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmReqGoodsOrderItem;
import com.nhry.data.milktrans.domain.TSsmSalOrder;
import com.nhry.data.milktrans.domain.TSsmSalOrderItems;
import com.nhry.model.milktrans.*;

import java.util.Date;
import java.util.List;

/**
 * Created by gongjk on 2016/6/24.
 */
public interface RequireOrderService {

    RequireOrderModel creatRequireOrder();
    RequireOrderModel searchRequireOrder(Date orderDate);

    int uptNewRequireOrderItem(UpdateNewRequiredModel uModel);

    int addRequireOrderItem(TSsmReqGoodsOrderItem item);

    int delRequireOrderItem(ReqGoodsOrderItemSearch item);

    int uptRequireOrder(UpdateRequiredModel uModel);

    String sendRequireOrderToERP();

    int creatPromoSalOrderOfDealerBranch(Date today);

    int creatNoPromoSalOrderOfDealerBranch(Date requiredDate);

    int creatNoPromoSalOrderOfSelftBranch(Date requiredDate);

    int creatPromoSalOrderOfSelftBranch(Date requiredDate);

   // List<TSsmSalOrder> creaSalOrderOfSelftBranch();

    List<TSsmSalOrder> creaSalOrderOfSelftBranchByDate(SalOrderDaySearch search);

    List<TSsmSalOrder> creaSalOrderOfDealerBranch();

    List<TSsmSalOrder> getSaleOrderByQueryDate(SalOrderModel sModel);

    List<TSsmSalOrderItems> getSaleOrderDetailByOrderNo(String orderNo);

    RequireOrderModel creatRequireOrderByDate(ReqGoodsOrderSearch eSearch);


}
