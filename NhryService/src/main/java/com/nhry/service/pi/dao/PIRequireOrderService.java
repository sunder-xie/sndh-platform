package com.nhry.service.pi.dao;

import com.nhry.data.basic.domain.TSsmReqGoodsOrder;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderItem;

import java.util.Date;

/**
 * Created by cbz on 7/4/2016.
 */
public interface PIRequireOrderService {
    void generateRequireOrder(TSsmReqGoodsOrder ssmReqGoodsOrder);
}
