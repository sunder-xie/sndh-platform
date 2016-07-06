package com.nhry.service.pi.dao;

import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.webService.client.PISuccessMessage;


import java.util.Date;

/**
 * 要货单
 * Created by cbz on 7/4/2016.
 */
public interface PIRequireOrderService {
    PISuccessMessage generateRequireOrder(TSsmReqGoodsOrder ssmReqGoodsOrder);
}
