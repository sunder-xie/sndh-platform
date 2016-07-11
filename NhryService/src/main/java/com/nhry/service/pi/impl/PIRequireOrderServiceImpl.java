package com.nhry.service.pi.impl;

import com.nhry.data.basic.dao.TMdBranchExMapper;
import com.nhry.data.basic.domain.TMdBranchEx;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.data.stock.dao.TSsmGiOrderItemMapper;
import com.nhry.data.stock.dao.TSsmGiOrderMapper;
import com.nhry.data.stock.domain.TSsmGiOrder;
import com.nhry.data.stock.domain.TSsmGiOrderItem;
import com.nhry.data.stock.domain.TSsmGiOrderItemKey;
import com.nhry.model.milktrans.ReqGoodsOrderItemSearch;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.webService.client.PISuccessMessage;
import com.nhry.webService.client.businessData.model.Delivery;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 要货单
 * Created by cbz on 7/4/2016.
 */
public class PIRequireOrderServiceImpl implements PIRequireOrderService {

    private TMdBranchExMapper branchExMapper;

    private TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper;

    private TSsmGiOrderMapper ssmGiOrderMapper;

    private TSsmGiOrderItemMapper ssmGiOrderItemMapper;

    private TSsmReqGoodsOrderMapper tSsmReqGoodsOrderMapper;

    public void setSsmGiOrderItemMapper(TSsmGiOrderItemMapper ssmGiOrderItemMapper) {
        this.ssmGiOrderItemMapper = ssmGiOrderItemMapper;
    }

    public void settSsmReqGoodsOrderMapper(TSsmReqGoodsOrderMapper tSsmReqGoodsOrderMapper) {
        this.tSsmReqGoodsOrderMapper = tSsmReqGoodsOrderMapper;
    }

    public void setSsmGiOrderMapper(TSsmGiOrderMapper ssmGiOrderMapper) {
        this.ssmGiOrderMapper = ssmGiOrderMapper;
    }

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

    @Override
    public PISuccessMessage generateSalesOrder(TSsmReqGoodsOrder ssmReqGoodsOrder, String kunnr, String kunwe, String vkorg) {
        ReqGoodsOrderItemSearch key = new ReqGoodsOrderItemSearch();
        key.setOrderNo(ssmReqGoodsOrder.getOrderNo());
        key.setMatnr(ssmReqGoodsOrder.getBranchNo());
        List<Map<String, String>> items = tSsmReqGoodsOrderItemMapper.findItemsForPI(key);
        return BusinessDataConnection.SalesOrderCreate(kunnr,kunwe,vkorg,ssmReqGoodsOrder.getOrderNo(),ssmReqGoodsOrder.getRequiredDate(),items);
    }

    @Override
    public String getDelivery(String orderNo, boolean isDeli){
        try{
          List<Delivery> deliveries = BusinessDataConnection.DeliveryQuery(orderNo,isDeli);
            if(deliveries.isEmpty()){
                TSsmGiOrder ssmGiOrder = null;
                Delivery delivery = deliveries.get(0);
                ssmGiOrder = ssmGiOrderMapper.selectGiOrderByNo(delivery.getBSTKD());
                TSsmReqGoodsOrder reqGoodsOrder = tSsmReqGoodsOrderMapper.getRequireOrderByVoucherNo(delivery.getBSTKD());
                if (ssmGiOrder == null) {
                    ssmGiOrder = new TSsmGiOrder();
                    ssmGiOrder.setBranchNo(reqGoodsOrder.getBranchNo());
                    ssmGiOrder.setOrderNo(delivery.getBSTKD());
                    ssmGiOrder.setStatus("10");
                    ssmGiOrder.setSyncAt(new Date());
                    ssmGiOrderMapper.insertGiOrder(ssmGiOrder);
                } else {
                    ssmGiOrder.setBranchNo(reqGoodsOrder.getBranchNo());
                    ssmGiOrder.setOrderNo(delivery.getBSTKD());
                    ssmGiOrder.setSyncAt(new Date());
                    ssmGiOrderMapper.updateGiOrder(ssmGiOrder);
                }
                for(Delivery d : deliveries) {
                    TSsmGiOrderItemKey key = new TSsmGiOrderItemKey();
                    key.setOrderDate(d.getLFDAT());
                    key.setItemNo(d.getVBELN());
                    key.setOrderNo(d.getBSTKD());
                    TSsmGiOrderItem ssmGiOrderItem = ssmGiOrderItemMapper.selectGiOrderItemByNo(key);
                    if(ssmGiOrderItem == null) {
                        ssmGiOrderItem = new TSsmGiOrderItem();
                        ssmGiOrderItem.setOrderNo(d.getBSTKD());
//                    ssmGiOrderItem.setConfirmQty();
                        ssmGiOrderItem.setMatnr(d.getMATNR());
                        ssmGiOrderItem.setUnit(d.getMEINS());
                        ssmGiOrderItem.setItemNo(d.getVBELN());
                        ssmGiOrderItem.setOrderDate(d.getLFDAT());
                        ssmGiOrderItem.setItemType(d.getPSTYV());
                        ssmGiOrderItem.setQty(d.getLFIMG());
                        ssmGiOrderItem.setFactoryPrice(d.getCmpre());
                        ssmGiOrderItemMapper.insertGiOrderItem(ssmGiOrderItem);
                    }else{
                        ssmGiOrderItem.setMatnr(d.getMATNR());
                        ssmGiOrderItem.setUnit(d.getMEINS());
                        ssmGiOrderItem.setItemType(d.getPSTYV());
                        ssmGiOrderItem.setQty(d.getLFIMG());
                        ssmGiOrderItem.setFactoryPrice(d.getCmpre());
                        ssmGiOrderItemMapper.updateGiOrderItem(ssmGiOrderItem);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "1";
    }


}
