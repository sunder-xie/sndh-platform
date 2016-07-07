package com.nhry.service.pi.dao;

import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.webService.client.PISuccessMessage;

/**
 * Created by cbz on 7/6/2016.
 */
public interface PISalesOrderService {
    /**
     *
     * @param ssmReqGoodsOrder
     * @param kunnr 客户编号
     * @param kunwe 送达方
     * @param vkorg 销售组织
     * @return
     */
    PISuccessMessage generateSalesOrder(TSsmReqGoodsOrder ssmReqGoodsOrder,String kunnr,String kunwe,String vkorg);
}
