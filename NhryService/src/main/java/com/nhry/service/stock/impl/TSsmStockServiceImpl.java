package com.nhry.service.stock.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stock.dao.TSsmStockMapper;
import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;
import com.nhry.model.stock.StockModel;
import com.nhry.service.stock.dao.TSsmStockService;

/**
 * Created by cbz on 7/19/2016.
 */
public class TSsmStockServiceImpl implements TSsmStockService {

    private TSsmStockMapper ssmStockMapper;

    public void setSsmStockMapper(TSsmStockMapper ssmStockMapper) {
        this.ssmStockMapper = ssmStockMapper;
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
}
