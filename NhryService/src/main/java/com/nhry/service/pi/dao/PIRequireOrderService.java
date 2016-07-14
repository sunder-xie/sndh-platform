package com.nhry.service.pi.dao;

import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.webService.client.PISuccessMessage;

import java.util.Date;

/**
 * 要货单
 * Created by cbz on 7/4/2016.
 */
public interface PIRequireOrderService {
    /**
     * 要货单
     * @param ssmReqGoodsOrder 要货单主表
     * @return
     */
    PISuccessMessage generateRequireOrder(TSsmReqGoodsOrder ssmReqGoodsOrder);

    /**
     * 销售订单创建
     * @param ssmReqGoodsOrder
     * @param kunnr 客户编号
     * @param kunwe 送达方
     * @param vkorg 销售组织
     * @param activityId 促销活动号
     * @return
     */
    PISuccessMessage generateSalesOrder(TSsmReqGoodsOrder ssmReqGoodsOrder, String kunnr, String kunwe, String vkorg, String activityId) ;

    /**
     * 交货单
      * @param orderNo 销售凭证
     * @param isDeli 是否是调货单
     * @return
     */
    String getDelivery(String orderNo,boolean isDeli);

    String execRequieOrder(Date date, String branchNo);

    String execSalesOrder(Date date, TMdBranch branch);

}
