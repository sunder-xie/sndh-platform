package com.nhry.service.stock.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;
import com.nhry.model.stock.StockModel;

/**
 * Created by cbz on 7/19/2016.
 */
public interface TSsmStockService {
    /**
     * 库存保存
     * */
    int save(TSsmStock ssmStock);
    TSsmStock getStock(TSsmStockKey key);
    PageInfo findStock(StockModel mode);
}
