package com.nhry.service.pi.impl;

import com.nhry.data.basic.dao.TMdBranchExMapper;
import com.nhry.data.basic.domain.TMdBranchEx;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.model.milktrans.ReqGoodsOrderItemSearch;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.webService.client.PISuccessMessage;

import java.util.List;
import java.util.Map;

/**
 * 要货单
 * Created by cbz on 7/4/2016.
 */
public class PIRequireOrderServiceImpl implements PIRequireOrderService {

    private TMdBranchExMapper branchExMapper;

    private TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper;

    public void setBranchExMapper(TMdBranchExMapper branchExMapper) {
        this.branchExMapper = branchExMapper;
    }

    public void settSsmReqGoodsOrderItemMapper(TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper) {
        this.tSsmReqGoodsOrderItemMapper = tSsmReqGoodsOrderItemMapper;
    }

    @Override
    public PISuccessMessage generateRequireOrder(TSsmReqGoodsOrder ssmReqGoodsOrder) {
            TMdBranchEx branchEx = branchExMapper.getBranchEx(ssmReqGoodsOrder.getBranchNo());
            ReqGoodsOrderItemSearch key = new ReqGoodsOrderItemSearch();
            key.setOrderNo(ssmReqGoodsOrder.getOrderNo());
            key.setMatnr(ssmReqGoodsOrder.getBranchNo());
            List<Map<String, String>> items = tSsmReqGoodsOrderItemMapper.findItemsForPI(key);
            return BusinessDataConnection.RequisitionCreate(branchEx, ssmReqGoodsOrder.getRequiredDate(), items);
    }




}
