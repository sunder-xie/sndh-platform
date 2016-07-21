package com.nhry.data.stock.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;
import com.nhry.model.stock.StockModel;

public interface TSsmStockMapper {
    int deleteStock(TSsmStockKey key);

    int insertStock(TSsmStock record);

    TSsmStock getStock(TSsmStockKey key);

    int updateStock(TSsmStock record);

    PageInfo findStock(StockModel model);

    PageInfo findStockinsidesal(StockModel model);
}