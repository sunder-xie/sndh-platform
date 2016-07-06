package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TSsmReqGoodsOrder;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderKey;

public interface TSsmReqGoodsOrderMapper {
    int deleteSRGO(TSsmReqGoodsOrderKey key);

    int insertSRGO(TSsmReqGoodsOrder record);

    int insertSRGOSelective(TSsmReqGoodsOrder record);

    TSsmReqGoodsOrder getSRGO(TSsmReqGoodsOrderKey key);

    int updateSRGOSelective(TSsmReqGoodsOrder record);

    int updateSRGO(TSsmReqGoodsOrder record);
}