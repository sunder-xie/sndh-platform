package com.nhry.service.stock.impl;

import com.nhry.data.stock.dao.TSsmStockMapper;
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
    public int insert() {
        return 0;
    }

    @Override
    public int update() {
        return 0;
    }
}
