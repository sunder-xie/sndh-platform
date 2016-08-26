package com.nhry.service.pi.impl;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
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
import com.nhry.data.stock.dao.TSsmSalFactoryPriceMapper;
import com.nhry.data.stock.domain.*;
import com.nhry.model.milktrans.ReqGoodsOrderItemSearch;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.milktrans.SalOrderModel;
import com.nhry.model.stock.StockModel;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.service.pi.pojo.SalesOrderHeader;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.client.PISuccessMessage;
import com.nhry.webService.client.PISuccessTMessage;
import com.nhry.webService.client.businessData.model.Delivery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

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

    private UserSessionService userSessionService;

    private TSsmSalFactoryPriceMapper ssmSalFactoryPriceMapper;

    public void setSsmSalFactoryPriceMapper(TSsmSalFactoryPriceMapper ssmSalFactoryPriceMapper) {
        this.ssmSalFactoryPriceMapper = ssmSalFactoryPriceMapper;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

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
    public PISuccessMessage generateRequireOrder(TSsmReqGoodsOrder ssmReqGoodsOrder) {
        TMdBranchEx branchEx = branchExMapper.getBranchEx(ssmReqGoodsOrder.getBranchNo());
        TMdBranch branch = branchMapper.getBranchByNo(ssmReqGoodsOrder.getBranchNo());
        ReqGoodsOrderItemSearch key = new ReqGoodsOrderItemSearch();
        key.setOrderNo(ssmReqGoodsOrder.getOrderNo());
        key.setMatnr(ssmReqGoodsOrder.getBranchNo());
        List<Map<String, String>> items = tSsmReqGoodsOrderItemMapper.findItemsForPI(key);
        if (branchEx == null) {
            throw new ServiceException(MessageCode.SERVER_ERROR, "奶站的扩展信息不存在！");
        } else {
            if (StringUtils.isEmpty(branchEx.getDocType()) || StringUtils.isEmpty(branchEx.getPurchGroup()) || StringUtils.isEmpty(branchEx.getPurchOrg()) || StringUtils.isEmpty(branchEx.getReslo())) {
                throw new ServiceException(MessageCode.SERVER_ERROR, "奶站的扩展信息不完善！");
            }
        }
        if (StringUtils.isEmpty(branch.getLgort()) && "01".equals(branch.getBranchGroup())) {
            throw new ServiceException(MessageCode.SERVER_ERROR, "奶站的库存地点为空！");
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
        List<Map<String, String>> items = tSsmSalOrderItemMapper.findItemsForPI(ssmSalOrder.getOrderNo());
        TMdBranch branch = branchMapper.getBranchByNo(ssmSalOrder.getBranchNo());
        TMdBranchEx branchEx = branchExMapper.getBranchEx(ssmSalOrder.getBranchNo());
        String lgort = branch.getLgort();
        boolean isZy = false;
        if ("02".equals(branch.getBranchGroup())) {
            lgort = branchEx.getReslo();
        }else{
            isZy = true;
        }
        orderHeader.setLgort(lgort);
        String werks = branchEx.getSupplPlnt();
        orderHeader.setWerks(werks);
        String auartType = PIPropertitesUtil.getValue("PI.AUART.ZOR");
        String saleOrgTX = PIPropertitesUtil.getValue("PI.SALEORG_TX");
        String freeType = ssmSalOrder.getFreeFlag();
        if ("N".equals(freeType)) {
            if (saleOrgTX.equals(ssmSalOrder.getSalesOrg())) {
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZOR1");
            } else {
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZOR");
            }
        } else {
            NHSysCodeItem key = new NHSysCodeItem();
            key.setTypeCode("1016");
            key.setItemCode(vkorg);
            NHSysCodeItem codeItem = sysCodeItemMapper.findCodeItenByCode(key);
            if (codeItem != null) {
                orderHeader.setAugru(codeItem.getAttr1());
                orderHeader.setKostl(codeItem.getAttr2());
                orderHeader.setZz001(codeItem.getAttr3());
            }
            if (saleOrgTX.equals(ssmSalOrder.getSalesOrg())) {
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZFD1");
            } else {
                auartType = PIPropertitesUtil.getValue("PI.AUART.ZFD");
            }
        }
        orderHeader.setAuartType(auartType);
        orderHeader.setBSTKD(ssmSalOrder.getOrderNo());
        orderHeader.setLFDAT(ssmSalOrder.getRequiredDate());
        PISuccessMessage message = BusinessDataConnection.SalesOrderCreate(items, orderHeader);
//        if(message.isSuccess()) {
//            String orderNo = message.getData();
//            if (isZy) {
//                savePriceAndGiOrder(orderNo, ssmSalOrder.getBranchNo(),false,true);
//            }
//        }
        return message;
    }

    @Override
    public String generateDelivery(String orderNo, String branchNo, boolean isDeli){
        if (StringUtils.isEmpty(orderNo)) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "调拨单或销售订单凭证没有生成！");
        } else {
            TSsmGiOrder order = ssmGiOrderMapper.findGiOrderByReqOrderNo(orderNo);
            if(order != null){
                throw new ServiceException(MessageCode.LOGIC_ERROR, "交货单已生成,请直接查询");
            }
            savePriceAndGiOrder(orderNo, branchNo, isDeli,false);
        }
        return "1";
    }

    private void savePriceAndGiOrder(String orderNo, String branchNo, boolean isDeli,boolean isZy) {
        PISuccessTMessage<List<Delivery>> message1 = BusinessDataConnection.DeliveryQuery(orderNo, isDeli, isZy);
        if(message1.isSuccess()) {
            List<Delivery> deliveries = message1.getData();
            if (deliveries.size() > 0) {
                TSsmGiOrder ssmGiOrder = null;
                for (Delivery d : deliveries) {
                    //防止一个调拨单生成多个交货单
                    if(!isZy) {
                        ssmGiOrder = ssmGiOrderMapper.selectGiOrderByNo(d.getVBELN());
                        if (ssmGiOrder == null) {
                            ssmGiOrder = new TSsmGiOrder();
                            ssmGiOrder.setBranchNo(branchNo);
                            ssmGiOrder.setOrderNo(d.getVBELN());
                            ssmGiOrder.setStatus("10");
                            ssmGiOrder.setSyncAt(new Date());
                            ssmGiOrder.setOrderDate(d.getLFDAT());
                            ssmGiOrder.setMemoTxt(d.getBSTKD());
                            ssmGiOrder.setReqOrderNo(d.getVBELV());
                            ssmGiOrderMapper.insertGiOrder(ssmGiOrder);
                        }
                        TSsmGiOrderItemKey key = new TSsmGiOrderItemKey();
                        key.setOrderDate(d.getLFDAT());
                        key.setItemNo(d.getPOSNR());
                        key.setOrderNo(d.getVBELN());
                        TSsmGiOrderItem ssmGiOrderItem = ssmGiOrderItemMapper.selectGiOrderItemByNo(key);
                        if (ssmGiOrderItem == null) {
                            ssmGiOrderItem = new TSsmGiOrderItem();
                            ssmGiOrderItem.setOrderNo(d.getVBELN());
                            ssmGiOrderItem.setMatnr(d.getMATNR());
                            ssmGiOrderItem.setUnit(d.getMEINS());
                            ssmGiOrderItem.setItemNo(d.getPOSNR());
                            ssmGiOrderItem.setOrderDate(d.getLFDAT());
                            ssmGiOrderItem.setItemType(d.getPSTYV());
                            ssmGiOrderItem.setQty(d.getLFIMG());
                            ssmGiOrderItem.setFactoryPrice(d.getCmpre());
                            ssmGiOrderItemMapper.insertGiOrderItem(ssmGiOrderItem);
                        } else {
                            ssmGiOrderItem.setMatnr(d.getMATNR());
                            ssmGiOrderItem.setUnit(d.getMEINS());
                            ssmGiOrderItem.setItemType(d.getPSTYV());
                            ssmGiOrderItem.setQty(d.getLFIMG());
                            ssmGiOrderItem.setFactoryPrice(d.getCmpre());
                            ssmGiOrderItemMapper.updateGiOrderItem(ssmGiOrderItem);
                        }
                    }
                    if(!isDeli){
                        TSysUser user = userSessionService.getCurrentUser();
                        TSsmSalFactoryPrice price = new TSsmSalFactoryPrice();
                        price.setCreateAt(new Date());
                        price.setCreateBy(user.getLoginName());
                        price.setBranchNo(branchNo);
                        price.setMatnr(d.getMATNR());
                        price.setOrderDate(d.getLFDAT());
                        price.setSalesOrg(user.getSalesOrg());
                        price.setFactoryPrice(d.getCmpre());
                        ssmSalFactoryPriceMapper.insertFactoryPrice(price);
                    }
                }
            }
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR,message1.getMessage());
        }
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

    public String execDelivery(StockModel model) {
        String message = "";
        TMdBranch branch = branchMapper.getBranchByNo(model.getBranchNo());
        Date curDate = new Date();
//        String salesOrg = branch.getSalesOrg();
//        如果是华西或者天音 则requiredDate日期为今天  否则requiredDate为明天
//        if("4181".equals(salesOrg) || "4390".equals(salesOrg)){
//            curDate = new Date();
//        }else{
//            curDate = DateUtil.getTomorrow(new Date());
//        }
        if(model.getCurDate() != null){
            curDate = model.getCurDate();
        }
        if ("01".equals(branch.getBranchGroup())) {
            RequireOrderSearch search = new RequireOrderSearch();
            search.setRequiredDate(curDate);
            search.setBranchNo(model.getBranchNo());
            List<TSsmReqGoodsOrder> order1 = tSsmReqGoodsOrderMapper.searchRequireOrderByRequireDate(search);
            if (order1.size()==0) {
                throw new ServiceException(MessageCode.SERVER_ERROR, "要货单没有生成！");
            } else {
                message = generateDelivery(order1.get(0).getVoucherNo(), model.getBranchNo(), true);
            }
        } else {
            SalOrderModel model1 = new SalOrderModel();
            model1.setBranchNo(model.getBranchNo());
            model1.setOrderDate(curDate);
            List<TSsmSalOrder> orders = ssmSalOrderMapper.selectSalOrderByDateAndNo(model1);
            if (orders.size() != 0) {
                for (TSsmSalOrder order : orders) {
                    message = generateDelivery(order.getVoucherNo(), model.getBranchNo(), false);
                }
            } else {
                throw new ServiceException(MessageCode.SERVER_ERROR, "销售订单没有生成！");
            }
        }
        return message;
    }

    @Override
    public String saveFactoryPrice() {
        List<TSsmSalOrder> orders = ssmSalOrderMapper.findGidOrderByNotWBSTK();
        if(orders.size() >0){
            for(TSsmSalOrder order : orders){
                PISuccessTMessage<List<Delivery>> message1 = BusinessDataConnection.DeliveryQuery(order.getVoucherNo(), false, true);
                if(message1.isSuccess()) {
                    List<Delivery> deliveries = message1.getData();
                    if (deliveries.size() > 0) {
                        for (Delivery d : deliveries) {
                            TSysUser user = userSessionService.getCurrentUser();
                            TSsmSalFactoryPriceKey key = new TSsmSalFactoryPriceKey();
                            key.setOrderDate(d.getLFDAT());
                            key.setMatnr(d.getMATNR());
                            key.setBranchNo(order.getBranchNo());
                            TSsmSalFactoryPrice price =ssmSalFactoryPriceMapper.selectFactoryPrice(key);
                            if(price == null) {
                                price = new TSsmSalFactoryPrice();
                                price.setCreateAt(new Date());
                                price.setCreateBy(user.getLoginName());
                                price.setBranchNo(order.getBranchNo());
                                price.setMatnr(d.getMATNR());
                                price.setOrderDate(d.getLFDAT());
                                price.setSalesOrg(user.getSalesOrg());
                                price.setFactoryPrice(d.getCmpre());
                                ssmSalFactoryPriceMapper.insertFactoryPrice(price);
                            }
                        }
                        ssmSalOrderMapper.updateWBSTK(order.getOrderNo());
                    }
                }
            }
        }
        return "1";
    }


    @Override
    public String execSalesOrder(Date date, TMdBranch branch) {
//        RequireOrderSearch search = new RequireOrderSearch();
//        search.setRequiredDate(date);
//        search.setBranchNo(branch.getBranchNo());
//        TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);

//        generateSalesOrder(order,branch.getDealerNo(),branch.getBranchNo(),branch.getSalesOrg(),"");
        return "1";
    }


}
