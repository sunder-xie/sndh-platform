package com.nhry.service.pi.impl;

import com.nhry.data.basic.dao.TMdBranchExMapper;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.basic.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.basic.domain.*;
import com.nhry.service.pi.dao.PIRequireOrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/4/2016.
 */
public class PIRequireOrderServiceImpl implements PIRequireOrderService {

    private TSsmReqGoodsOrderMapper ssmReqGoodsOrderMapper;

    private TMdBranchExMapper branchExMapper;

    private TSsmReqGoodsOrderItemMapper ssmReqGoodsOrderItemMapper;

    public void setSsmReqGoodsOrderMapper(TSsmReqGoodsOrderMapper ssmReqGoodsOrderMapper) {
        this.ssmReqGoodsOrderMapper = ssmReqGoodsOrderMapper;
    }

    public void setBranchExMapper(TMdBranchExMapper branchExMapper) {
        this.branchExMapper = branchExMapper;
    }

    public void setSsmReqGoodsOrderItemMapper(TSsmReqGoodsOrderItemMapper ssmReqGoodsOrderItemMapper) {
        this.ssmReqGoodsOrderItemMapper = ssmReqGoodsOrderItemMapper;
    }

    @Override
    public void generateRequireOrder(TSsmReqGoodsOrder ssmReqGoodsOrder) {
        try {
            TMdBranchEx branchEx = branchExMapper.getBranchEx(ssmReqGoodsOrder.getBranchNo());
            TSsmReqGoodsOrderKey key = new TSsmReqGoodsOrderKey();
            key.setOrderNo(ssmReqGoodsOrder.getOrderNo());
            Date date = ssmReqGoodsOrder.getOrderDate();
            key.setOrderDate(date);
            List<Map<String, String>> items = ssmReqGoodsOrderItemMapper.findItemsForPI(key);
            String vNo = BusinessDataConnection.RequisitionCreate(branchEx, date, items);
            ssmReqGoodsOrder.setVoucherNo(vNo);
            ssmReqGoodsOrder.setStatus("1");
            ssmReqGoodsOrderMapper.updateSRGOSelective(ssmReqGoodsOrder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
