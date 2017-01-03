package com.nhry.service.milktrans.impl;

import com.nhry.data.milktrans.dao.TSsmRedateByTradeMapper;
import com.nhry.data.milktrans.domain.TSsmRedateByTrade;
import com.nhry.service.milktrans.dao.RedateByTradeService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cbz on 12/27/2016.
 */
public class RedateByTradeServiceImpl implements RedateByTradeService {
    private TSsmRedateByTradeMapper tSsmRedateByTradeMapper;

    public void settSsmRedateByTradeMapper(TSsmRedateByTradeMapper tSsmRedateByTradeMapper) {
        this.tSsmRedateByTradeMapper = tSsmRedateByTradeMapper;
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


}
