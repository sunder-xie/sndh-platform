package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TSsmReqGoodsOrderItem;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderItemKey;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderKey;

import java.util.List;
import java.util.Map;

public interface TSsmReqGoodsOrderItemMapper {
    int deleteSRGOItem(TSsmReqGoodsOrderItemKey key);

    int insertSRGOItem(TSsmReqGoodsOrderItem record);

    int insertSRGOItemSelective(TSsmReqGoodsOrderItem record);

    TSsmReqGoodsOrderItem getSRGOItem(TSsmReqGoodsOrderItemKey key);

    int updateSRGOItemSelective(TSsmReqGoodsOrderItem record);

    int updateSRGOItem(TSsmReqGoodsOrderItem record);

    List<Map<String,String>> findItemsForPI (TSsmReqGoodsOrderKey key);
}