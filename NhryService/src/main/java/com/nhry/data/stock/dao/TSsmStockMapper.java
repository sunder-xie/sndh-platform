package com.nhry.data.stock.dao;

import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;

public interface TSsmStockMapper {
    int deleteStock(TSsmStockKey key);

    int insertStock(TSsmStock record);

    TSsmStock getStock(TSsmStockKey key);

    int updateStock(TSsmStock record);
}