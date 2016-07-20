package com.nhry.data.stock.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stock.dao.TSsmStockMapper;
import com.nhry.data.stock.domain.TSsmStock;
import com.nhry.data.stock.domain.TSsmStockKey;
import com.nhry.model.stock.StockModel;

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
    public PageInfo findStock(StockModel model) {
        return sqlSessionTemplate.selectListByPages("findStock",model,Integer.valueOf(model.getPageNum()),Integer.valueOf(model.getPageSize()));
    }
}
