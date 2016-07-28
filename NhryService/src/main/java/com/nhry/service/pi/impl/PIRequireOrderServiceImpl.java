package com.nhry.service.pi.impl;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMdBranchExMapper;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdBranchEx;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderMapper;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.data.milktrans.domain.TSsmSalOrder;
import com.nhry.data.stock.dao.TSsmGiOrderItemMapper;
import com.nhry.data.stock.dao.TSsmGiOrderMapper;
import com.nhry.data.stock.dao.TSsmStockMapper;
import com.nhry.data.stock.domain.*;
import com.nhry.model.basic.MessageModel;
import com.nhry.model.milktrans.ReqGoodsOrderItemSearch;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.milktrans.SalOrderModel;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.webService.client.PISuccessMessage;
import com.nhry.webService.client.businessData.model.Delivery;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
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

    private TSsmSalOrderItemMapper tSsmSalOrderItemMapper;

    private TSsmSalOrderMapper ssmSalOrderMapper;

    private TMdBranchMapper branchMapper;

    private TSsmStockMapper ssmStockMapper;

    public void setBranchMapper(TMdBranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }

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

    public void settSsmSalOrderItemMapper(TSsmSalOrderItemMapper tSsmSalOrderItemMapper) {
        this.tSsmSalOrderItemMapper = tSsmSalOrderItemMapper;
    }

    public void setSsmSalOrderMapper(TSsmSalOrderMapper ssmSalOrderMapper) {
        this.ssmSalOrderMapper = ssmSalOrderMapper;
    }

    @Override
    public PISuccessMessage generateRequireOrder(TSsmReqGoodsOrder ssmReqGoodsOrder)  {
            TMdBranchEx branchEx = branchExMapper.getBranchEx(ssmReqGoodsOrder.getBranchNo());
            TMdBranch branch = branchMapper.getBranchByNo(ssmReqGoodsOrder.getBranchNo());
            ReqGoodsOrderItemSearch key = new ReqGoodsOrderItemSearch();
            key.setOrderNo(ssmReqGoodsOrder.getOrderNo());
            key.setMatnr(ssmReqGoodsOrder.getBranchNo());
            List<Map<String, String>> items = tSsmReqGoodsOrderItemMapper.findItemsForPI(key);
            if(branchEx == null){
                throw new ServiceException(MessageCode.SERVER_ERROR,"奶站的扩展信息不存在！");
            }else{
                if(StringUtils.isEmpty(branchEx.getDocType()) || StringUtils.isEmpty(branchEx.getPurchGroup()) || StringUtils.isEmpty(branchEx.getPurchOrg()) || StringUtils.isEmpty(branchEx.getReslo())){
                    throw new ServiceException(MessageCode.SERVER_ERROR,"奶站的扩展信息不完善！");
                }
            }
            if(StringUtils.isEmpty(branch.getLgort()) && "01".equals(branch.getBranchGroup())){
                throw new ServiceException(MessageCode.SERVER_ERROR,"奶站的库存地点为空！");
            }
            return BusinessDataConnection.RequisitionCreate(branchEx, ssmReqGoodsOrder.getRequiredDate(), items, branch.getLgort());
    }

    @Override
    public PISuccessMessage generateSalesOrder(TSsmSalOrder ssmSalOrder, String kunnr, String kunwe, String vkorg, String activityId) {
        List<Map<String,String>> items = tSsmSalOrderItemMapper.findItemsForPI(ssmSalOrder.getOrderNo());
        TMdBranch branch = branchMapper.getBranchByNo(ssmSalOrder.getBranchNo());
        TMdBranchEx branchEx = branchExMapper.getBranchEx(ssmSalOrder.getBranchNo());
        String lgort = branch.getLgort();
        if("02".equals(branch.getBranchGroup())){
            lgort = branchEx.getReslo();
        }
        String werks = branchEx.getSupplPlnt();

        return BusinessDataConnection.SalesOrderCreate(kunnr,kunwe,vkorg, ssmSalOrder.getOrderNo(), ssmSalOrder.getRequiredDate(),items,activityId, lgort, werks);
    }

    @Override
    public String generateDelivery(String orderNo,String branchNo,boolean isDeli){
        if(StringUtils.isEmpty(orderNo)){
            throw new ServiceException(MessageCode.SERVER_ERROR,"销售订单凭证没有生成！");
        }
        try{
          List<Delivery> deliveries = BusinessDataConnection.DeliveryQuery(orderNo,isDeli);
            if(deliveries.size()>0){
                TSsmGiOrder ssmGiOrder = null;
                Delivery delivery = deliveries.get(0);
                ssmGiOrder = ssmGiOrderMapper.selectGiOrderByNo(delivery.getBSTKD());
                if (ssmGiOrder == null) {
                    ssmGiOrder = new TSsmGiOrder();
                    ssmGiOrder.setBranchNo(branchNo);
                    ssmGiOrder.setOrderNo(delivery.getBSTKD());
                    ssmGiOrder.setStatus("10");
                    ssmGiOrder.setSyncAt(new Date());
                    ssmGiOrder.setOrderDate(delivery.getLFDAT());
                    ssmGiOrderMapper.insertGiOrder(ssmGiOrder);
                } else {
                    ssmGiOrder.setBranchNo(branchNo);
                    ssmGiOrder.setOrderNo(delivery.getBSTKD());
                    ssmGiOrder.setSyncAt(new Date());
                    ssmGiOrder.setOrderDate(delivery.getLFDAT());
                    ssmGiOrderMapper.updateGiOrder(ssmGiOrder);
                }
                for(Delivery d : deliveries) {
                    TSsmGiOrderItemKey key = new TSsmGiOrderItemKey();
                    key.setOrderDate(d.getLFDAT());
                    key.setItemNo(d.getPOSNR());
                    key.setOrderNo(d.getBSTKD());
                    TSsmGiOrderItem ssmGiOrderItem = ssmGiOrderItemMapper.selectGiOrderItemByNo(key);
                    BigDecimal sum = new BigDecimal(0);
                    if(ssmGiOrderItem == null) {
                        ssmGiOrderItem = new TSsmGiOrderItem();
                        ssmGiOrderItem.setOrderNo(d.getBSTKD());
                        ssmGiOrderItem.setMatnr(d.getMATNR());
                        ssmGiOrderItem.setUnit(d.getMEINS());
                        ssmGiOrderItem.setItemNo(d.getPOSNR());
                        ssmGiOrderItem.setOrderDate(d.getLFDAT());
                        ssmGiOrderItem.setItemType(d.getPSTYV());
                        ssmGiOrderItem.setQty(d.getLFIMG());
                        ssmGiOrderItem.setFactoryPrice(d.getCmpre());
                        sum = d.getLFIMG();
                        ssmGiOrderItemMapper.insertGiOrderItem(ssmGiOrderItem);
                    }else{
                        ssmGiOrderItem.setMatnr(d.getMATNR());
                        ssmGiOrderItem.setUnit(d.getMEINS());
                        ssmGiOrderItem.setItemType(d.getPSTYV());
                        sum = d.getLFIMG().subtract(ssmGiOrderItem.getQty());
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

    @Override
    public String execRequieOrder(Date date, String branchNo) {
        RequireOrderSearch search = new RequireOrderSearch();
        search.setRequiredDate(date);
        search.setBranchNo(branchNo);
        TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);
        generateRequireOrder(order);
        return "1";
    }

    public String execDelivery(String branchNo){
        TMdBranch branch = branchMapper.getBranchByNo(branchNo);
        if("01".equals(branch.getBranchGroup())){
            RequireOrderSearch search = new RequireOrderSearch();
            search.setRequiredDate(new Date());
            search.setBranchNo(branchNo);
            TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);
            if(order == null){
                throw new ServiceException(MessageCode.SERVER_ERROR,"要货单没有生成！");
            }else {
                generateDelivery(order.getVoucherNo(), branchNo, true);
            }
        }else{
            SalOrderModel model = new SalOrderModel();
            model.setBranchNo(branchNo);
            model.setOrderDate(new Date());
            List<TSsmSalOrder> orders = ssmSalOrderMapper.selectSalOrderByDateAndNo(model);
            if(orders != null){
                for(TSsmSalOrder order : orders){
                    generateDelivery(order.getVoucherNo(), branchNo, false);
                }
            }else{
                throw new ServiceException(MessageCode.SERVER_ERROR,"销售订单没有生成！");
            }
        }
        return "1";
    }


    @Override
    public String execSalesOrder(Date date, TMdBranch branch){
//        RequireOrderSearch search = new RequireOrderSearch();
//        search.setRequiredDate(date);
//        search.setBranchNo(branch.getBranchNo());
//        TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);

//        generateSalesOrder(order,branch.getDealerNo(),branch.getBranchNo(),branch.getSalesOrg(),"");
        return "1";
    }


}
