package com.nhry.service.stock.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.stock.dao.TSsmStockMapper;
import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;
import com.nhry.model.stock.StockModel;
import com.nhry.service.stock.dao.TSsmGiOrderItemService;
import com.nhry.service.stock.dao.TSsmGiOrderService;
import com.nhry.service.stock.dao.TSsmStockService;

import java.math.BigDecimal;

/**
 * Created by cbz on 7/19/2016.
 */
public class TSsmStockServiceImpl implements TSsmStockService {

    private TSsmReqGoodsOrderMapper ssmReqGoodsOrderMapper;

    private TSsmStockMapper ssmStockMapper;

    private TSsmGiOrderService giOrderService;

    private TSsmGiOrderItemService giOrderItemService;

    public void setSsmStockMapper(TSsmStockMapper ssmStockMapper) {
        this.ssmStockMapper = ssmStockMapper;
    }

    public void setSsmReqGoodsOrderMapper(TSsmReqGoodsOrderMapper ssmReqGoodsOrderMapper) {
        this.ssmReqGoodsOrderMapper = ssmReqGoodsOrderMapper;
    }

    public void setGiOrderService(TSsmGiOrderService giOrderService) {
        this.giOrderService = giOrderService;
    }

    public void setGiOrderItemService(TSsmGiOrderItemService giOrderItemService) {
        this.giOrderItemService = giOrderItemService;
    }

    @Override
    public int save(TSsmStock ssmStock) {
        TSsmStockKey key = new TSsmStockKey();
        key.setBranchNo(ssmStock.getBranchNo());
        key.setMatnr(ssmStock.getMatnr());
        TSsmStock ssmStock1 = getStock(key);
        if(ssmStock1!=null){
            ssmStock1.setQty(ssmStock.getQty());
            ssmStock1.setUnit(ssmStock.getUnit());
        }else{
            return ssmStockMapper.insertStock(ssmStock);
        }
        return ssmStockMapper.updateStock(ssmStock1);
    }

    @Override
    public TSsmStock getStock(TSsmStockKey key) {
        return ssmStockMapper.getStock(key);
    }

    @Override
    public PageInfo findStock(StockModel mode) {
        return ssmStockMapper.findStock(mode);
    }

    @Override
    public BigDecimal findStockTotal(StockModel mode) {
        return ssmStockMapper.findStockTotal(mode);
    }

    @Override
    public PageInfo findStockinsidesal(StockModel mode) {
        return ssmStockMapper.findStockinsidesal(mode);
    }
    @Override
    public int genarateStock(StockModel mode){

       return 1;
    }

    @Override
    public int updateStock(String branchNo, String matnr, BigDecimal qty, String salesOrg) {
        TSsmStockKey key = new TSsmStockKey();
        key.setBranchNo(branchNo);
        key.setMatnr(matnr);
        TSsmStock ssmStock1 = getStock(key);
        if(ssmStock1!=null && qty!= null){
            if(ssmStock1.getQty() != null){
                ssmStock1.setQty(ssmStock1.getQty().subtract(qty));
            }else{
                ssmStock1.setQty(new BigDecimal("0").subtract(qty));
            }
            return ssmStockMapper.updateStock(ssmStock1);
        }else{
            ssmStock1 = new TSsmStock();
            ssmStock1.setBranchNo(branchNo);
            ssmStock1.setMatnr(matnr);
            ssmStock1.setSalesOrg(salesOrg);
            ssmStock1.setQty(new BigDecimal("0").subtract(qty));
            return ssmStockMapper.insertStock(ssmStock1);
        }
    }


}
