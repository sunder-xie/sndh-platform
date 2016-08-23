package com.nhry.service.pi.impl;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMdBranchExMapper;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdBranchEx;
import com.nhry.data.config.dao.NHSysCodeItemMapper;
import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderMapper;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.data.milktrans.domain.TSsmSalOrder;
import com.nhry.data.stock.dao.TSsmGiOrderItemMapper;
import com.nhry.data.stock.dao.TSsmGiOrderMapper;
import com.nhry.data.stock.domain.TSsmGiOrder;
import com.nhry.data.stock.domain.TSsmGiOrderItem;
import com.nhry.data.stock.domain.TSsmGiOrderItemKey;
import com.nhry.model.milktrans.ReqGoodsOrderItemSearch;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.milktrans.SalOrderModel;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.service.pi.pojo.SalesOrderHeader;
import com.nhry.utils.DateUtil;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.client.PISuccessMessage;
import com.nhry.webService.client.businessData.model.Delivery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 要货单
 * Created by cbz on 7/4/2016.
 */
public class PIRequireOrderServiceImpl implements PIRequireOrderService {

    private static final Logger logger = Logger.getLogger(PIRequireOrderServiceImpl.class);

    private TMdBranchExMapper branchExMapper;

    private TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper;

    private TSsmGiOrderMapper ssmGiOrderMapper;

    private TSsmGiOrderItemMapper ssmGiOrderItemMapper;

    private TSsmReqGoodsOrderMapper tSsmReqGoodsOrderMapper;

    private TSsmSalOrderItemMapper tSsmSalOrderItemMapper;

    private TSsmSalOrderMapper ssmSalOrderMapper;

    private TMdBranchMapper branchMapper;

    private NHSysCodeItemMapper sysCodeItemMapper;

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

    public void setSysCodeItemMapper(NHSysCodeItemMapper sysCodeItemMapper) {
        this.sysCodeItemMapper = sysCodeItemMapper;
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
        SalesOrderHeader orderHeader = new SalesOrderHeader();
        orderHeader.setKUNNR(kunnr);
        orderHeader.setKUNWE(kunwe);
        orderHeader.setVKORG(vkorg);
        orderHeader.setActivityId(activityId);
        List<Map<String,String>> items = tSsmSalOrderItemMapper.findItemsForPI(ssmSalOrder.getOrderNo());
        TMdBranch branch = branchMapper.getBranchByNo(ssmSalOrder.getBranchNo());
        TMdBranchEx branchEx = branchExMapper.getBranchEx(ssmSalOrder.getBranchNo());
        String lgort = branch.getLgort();
        if("02".equals(branch.getBranchGroup())){
            lgort = branchEx.getReslo();
        }
        orderHeader.setLgort(lgort);
        String werks = branchEx.getSupplPlnt();
        orderHeader.setWerks(werks);
        String auartType = PIPropertitesUtil.getValue("PI.AUART.ZOR");
        String saleOrgTX = PIPropertitesUtil.getValue("PI.SALEORG_TX");
        String freeType = ssmSalOrder.getFreeFlag();
        if("N".equals(freeType)){
            if(saleOrgTX.equals(ssmSalOrder.getSalesOrg())){
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZOR1");
            }else{
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZOR");
            }
        }else{
            NHSysCodeItem key = new NHSysCodeItem();
            key.setTypeCode("1016");
            key.setItemCode(vkorg);
            NHSysCodeItem codeItem = sysCodeItemMapper.findCodeItenByCode(key);
            if(codeItem!=null){
                orderHeader.setAugru(codeItem.getAttr1());
                orderHeader.setKostl(codeItem.getAttr2());
                orderHeader.setZz001(codeItem.getAttr3());
            }
            if(saleOrgTX.equals(ssmSalOrder.getSalesOrg())){
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZFD1");
            }else{
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZFD");
            }
        }
        orderHeader.setAuartType(auartType);
        orderHeader.setBSTKD(ssmSalOrder.getOrderNo());
        orderHeader.setLFDAT(ssmSalOrder.getRequiredDate());
        return BusinessDataConnection.SalesOrderCreate(items, orderHeader);
    }

    @Override
    public String generateDelivery(String orderNo,String branchNo,boolean isDeli){
        String message = "";
        if(StringUtils.isEmpty(orderNo)){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"调拨单或销售订单凭证没有生成！");
        }
        try{
          List<Delivery> deliveries = BusinessDataConnection.DeliveryQuery(orderNo,isDeli);
            if(deliveries.size()>0){
                TSsmGiOrder ssmGiOrder = null;
//                Delivery delivery = deliveries.get(0);
//                ssmGiOrder = ssmGiOrderMapper.selectGiOrderByNo(delivery.getVBELN());
//                if (ssmGiOrder == null) {
//                    ssmGiOrder = new TSsmGiOrder();
//                    ssmGiOrder.setBranchNo(branchNo);
//                    ssmGiOrder.setOrderNo(delivery.getVBELN());
//                    ssmGiOrder.setStatus("10");
//                    ssmGiOrder.setSyncAt(new Date());
//                    ssmGiOrder.setOrderDate(delivery.getLFDAT());
//                    ssmGiOrder.setMemoTxt(delivery.getBSTKD());
//                    ssmGiOrderMapper.insertGiOrder(ssmGiOrder);
//                } else {
//                    ssmGiOrder.setBranchNo(branchNo);
//                    ssmGiOrder.setOrderNo(delivery.getVBELN());
//                    ssmGiOrder.setMemoTxt(delivery.getBSTKD());
//                    ssmGiOrder.setSyncAt(new Date());
//                    ssmGiOrder.setOrderDate(delivery.getLFDAT());
//                    ssmGiOrderMapper.updateGiOrder(ssmGiOrder);
//                }
                for(Delivery d : deliveries) {
                    //防止一个调拨单生成多个交货单
                    ssmGiOrder = ssmGiOrderMapper.selectGiOrderByNo(d.getVBELN());
                    if (ssmGiOrder == null) {
                        ssmGiOrder = new TSsmGiOrder();
                        ssmGiOrder.setBranchNo(branchNo);
                        ssmGiOrder.setOrderNo(d.getVBELN());
                        ssmGiOrder.setStatus("10");
                        ssmGiOrder.setSyncAt(new Date());
                        ssmGiOrder.setOrderDate(d.getLFDAT());
                        ssmGiOrder.setMemoTxt(d.getBSTKD());
                        ssmGiOrderMapper.insertGiOrder(ssmGiOrder);
                    }
//                    else {
//                        ssmGiOrder.setBranchNo(branchNo);
//                        ssmGiOrder.setOrderNo(d.getVBELN());
//                        ssmGiOrder.setMemoTxt(d.getBSTKD());
//                        ssmGiOrder.setSyncAt(new Date());
//                        ssmGiOrder.setOrderDate(d.getLFDAT());
//                        ssmGiOrderMapper.updateGiOrder(ssmGiOrder);
//                    }
                    TSsmGiOrderItemKey key = new TSsmGiOrderItemKey();
                    key.setOrderDate(d.getLFDAT());
                    key.setItemNo(d.getPOSNR());
                    key.setOrderNo(d.getVBELN());
                    TSsmGiOrderItem ssmGiOrderItem = ssmGiOrderItemMapper.selectGiOrderItemByNo(key);
                    BigDecimal sum = new BigDecimal(0);
                    if(ssmGiOrderItem == null) {
                        ssmGiOrderItem = new TSsmGiOrderItem();
                        ssmGiOrderItem.setOrderNo(d.getVBELN());
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
            }else {
                message = "要货单"+orderNo+"单据在ERP中未过账！";
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("要货单"+orderNo +"获取交货单异常！原因："+ e.getMessage());
            throw new ServiceException(MessageCode.SERVER_ERROR, "要货单"+orderNo +"获取交货单异常！原因："+ e.getMessage());
        }
        return message;
    }

    @Override
    public String execRequieOrder(Date date, String branchNo) {
        RequireOrderSearch search = new RequireOrderSearch();
        search.setRequiredDate(date);
        search.setOrderDate(date);
        search.setBranchNo(branchNo);
        TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);
        generateRequireOrder(order);
        return "1";
    }

    public String execDelivery(String branchNo){
        String message = "";
        TMdBranch branch = branchMapper.getBranchByNo(branchNo);
        Date curDate = new Date();
//        String salesOrg = branch.getSalesOrg();
        //如果是华西或者天音 则requiredDate日期为今天  否则requiredDate为明天
//        if("4181".equals(salesOrg) || "4390".equals(salesOrg)){
//            curDate = new Date();
//        }else{
//            curDate = DateUtil.getTomorrow(new Date());
//        }
        if("01".equals(branch.getBranchGroup())){
            RequireOrderSearch search = new RequireOrderSearch();
            search.setOrderDate(curDate);
            search.setBranchNo(branchNo);
            TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);
            if(order == null){
                throw new ServiceException(MessageCode.SERVER_ERROR,"要货单没有生成！");
            }else {
                message = generateDelivery(order.getVoucherNo(), branchNo, true);
            }
        }else{
            SalOrderModel model = new SalOrderModel();
            model.setBranchNo(branchNo);
            model.setOrderDate(curDate);
            List<TSsmSalOrder> orders = ssmSalOrderMapper.selectSalOrderByDateAndNo(model);
            if(orders != null){
                for(TSsmSalOrder order : orders){
                  message =  generateDelivery(order.getVoucherNo(), branchNo, false);
                }
            }else{
                throw new ServiceException(MessageCode.SERVER_ERROR,"销售订单没有生成！");
            }
        }
        return message;
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
