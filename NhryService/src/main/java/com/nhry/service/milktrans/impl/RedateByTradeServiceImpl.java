package com.nhry.service.milktrans.impl;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.milktrans.dao.TSsmRedateByTradeMapper;
import com.nhry.data.milktrans.domain.TSsmRedateByTrade;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.service.milktrans.dao.RedateByTradeService;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cbz on 12/27/2016.
 */
public class RedateByTradeServiceImpl implements RedateByTradeService {
    private TSsmRedateByTradeMapper tSsmRedateByTradeMapper;
    private TPlanOrderItemMapper tPlanOrderItemMapper;

    public void settSsmRedateByTradeMapper(TSsmRedateByTradeMapper tSsmRedateByTradeMapper) {
        this.tSsmRedateByTradeMapper = tSsmRedateByTradeMapper;
    }

    public void settPlanOrderItemMapper(TPlanOrderItemMapper tPlanOrderItemMapper) {
        this.tPlanOrderItemMapper = tPlanOrderItemMapper;
    }

    @Override
    public void insert(String orderNo, String branchNo, String salesOrg, String promNo, String matnr, BigDecimal redate) {
        TSsmRedateByTrade ssmRedateByTrade = new TSsmRedateByTrade();
        ssmRedateByTrade.setOrderNo(orderNo);
        ssmRedateByTrade.setBranchNo(branchNo);
        ssmRedateByTrade.setSalesOrg(salesOrg);
        ssmRedateByTrade.setPromNo(promNo);
        ssmRedateByTrade.setCreateDate(new Date());
        ssmRedateByTrade.setMatnr(matnr);
        ssmRedateByTrade.setRedate(redate);
        ssmRedateByTrade.setOrderDate(new Date());
        ssmRedateByTrade.setDhNo(orderNo);
        tSsmRedateByTradeMapper.insertRedateByTrade(ssmRedateByTrade);
    }

    @Override
    public List<TSsmRedateByTrade> findNoSendRedateByTrade() {
        return tSsmRedateByTradeMapper.findNoSendRedateByTrade();
    }

    @Override
    public void update(TSsmRedateByTrade ssmRedateByTrade) {
        tSsmRedateByTradeMapper.updateRedateByTrade(ssmRedateByTrade);
    }

    @Override
    public void saveRedate(TPreOrder order) {
        List<TPlanOrderItem> itemList = tPlanOrderItemMapper.selectByOrderCode(order.getOrderNo());
        String matnr = "";
        for(TPlanOrderItem item : itemList){
            matnr = item.getMatnr();
        }
        if(StringUtils.isEmpty(matnr)){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"未定位到订单行项目物料！");
        }
        insert(order.getOrderNo(),order.getBranchNo(),order.getSalesOrg(),order.getPromotion(),matnr,order.getDiscountAmt());
    }


}
