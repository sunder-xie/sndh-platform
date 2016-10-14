package com.nhry.service.stock.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;
import com.nhry.model.stock.StockModel;

import java.math.BigDecimal;

/**
 * Created by cbz on 7/19/2016.
 */
public interface TSsmStockService {
    /**
     * 库存保存
     * */
    int save(TSsmStock ssmStock);
    TSsmStock getStock(TSsmStockKey key);

    /**
     * 返回库存信息
     * @param mode
     * @return
     */
    PageInfo findStock(StockModel mode);
    PageInfo findStockinsidesal(StockModel mode);
    BigDecimal findStockTotal(StockModel mode);
    int genarateStock(StockModel mode);

    /**
     * 库存运算
     * @param branchNo
     * @param matnr
     * @param qty
     * @param salesOrg
     * @return
     */
    int updateStock(String branchNo, String matnr, BigDecimal qty, String salesOrg);



}
