package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.model.milktrans.RequireOrderSearch;

/**
 * Created by gongjk on 2016/6/22.
 */
public interface TSsmReqGoodsOrderMapper {

    public int insertRequireOrder(TSsmReqGoodsOrder order);
    TSsmReqGoodsOrder searchRequireOrder(RequireOrderSearch rModel);

    TSsmReqGoodsOrder getRequireOrderByNo(String orderNo);

    //修改要货计划状态 或 修改记录
    int uptRequireGoodsModifyInfo(TSsmReqGoodsOrder orderModel);


}
