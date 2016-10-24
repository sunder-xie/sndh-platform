package com.nhry.data.stock.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stock.dao.TSsmStockMapper;
import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;
import com.nhry.model.stock.StockModel;

import java.math.BigDecimal;

/**
 * Created by cbz on 7/19/2016.
 */
public class TSsmStockMapperImpl implements TSsmStockMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int deleteStock(TSsmStockKey key) {
        return sqlSessionTemplate.delete("deleteStock",key);
    }

    @Override
    public int insertStock(TSsmStock record) {
        return sqlSessionTemplate.insert("insertStock",record);
    }

    @Override
    public TSsmStock getStock(TSsmStockKey key) {
        return sqlSessionTemplate.selectOne("getStock",key);
    }

    @Override
    public int updateStock(TSsmStock record) {
        return sqlSessionTemplate.update("updateStock",record);
    }

    @Override
    public int updateStockToZero(String BranchNo){
        return sqlSessionTemplate.update("updateStockToZero",BranchNo);

    }

    @Override
    public PageInfo findStockinsidesal(StockModel model) {
        return sqlSessionTemplate.selectListByPages("findStockinsidesal",model,Integer.valueOf(model.getPageNum()),Integer.valueOf(model.getPageSize()));
    }

    @Override
    public PageInfo findStock(StockModel model) {
        return sqlSessionTemplate.selectListByPages("findStock",model,Integer.valueOf(model.getPageNum()),Integer.valueOf(model.getPageSize()));
    }

    @Override
    public BigDecimal findStockTotal(StockModel model) {
        return sqlSessionTemplate.selectOne("findStockTotal",model);
    }
}
