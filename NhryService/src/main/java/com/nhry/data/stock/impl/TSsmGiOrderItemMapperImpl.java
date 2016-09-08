package com.nhry.data.stock.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stock.dao.TSsmGiOrderItemMapper;
import com.nhry.data.stock.domain.TSsmGiOrderItem;
import com.nhry.data.stock.domain.TSsmGiOrderItemKey;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/7/2016.
 */
public class TSsmGiOrderItemMapperImpl implements TSsmGiOrderItemMapper{

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int deleteGiOrderItemByNo(TSsmGiOrderItemKey key) {
        return sqlSessionTemplate.delete("deleteGiOrderItemByNo",key);
    }

    @Override
    public int insertGiOrderItem(TSsmGiOrderItem record) {
        return sqlSessionTemplate.insert("insertGiOrderItem",record);
    }

    @Override
    public int insertGiOrderItemSelective(TSsmGiOrderItem record) {
        return sqlSessionTemplate.insert("insertGiOrderItemSelective",record);
    }

    @Override
    public TSsmGiOrderItem selectGiOrderItemByNo(TSsmGiOrderItemKey key) {
        return sqlSessionTemplate.selectOne("selectGiOrderItemByNo",key);
    }

    @Override
    public int updateGiOrderItemSelective(TSsmGiOrderItem record) {
        return sqlSessionTemplate.update("updateGiOrderItemSelective",record);
    }

    @Override
    public int updateGiOrderItem(TSsmGiOrderItem record) {
        return sqlSessionTemplate.update("updateGiOrderItemByNo",record);
    }

    @Override
    public List<TSsmGiOrderItem> findGiOrderItem(TSsmGiOrderItemKey key) {
        return sqlSessionTemplate.selectList("findGiOrderItem",key);
    }

    @Override
    public BigDecimal selectProximalFactoryPrice(String matnr, String branchNo) {
          Map<String,String> map = new HashMap<String,String>();
            map.put("branchNo",branchNo);
            map.put("matnr",matnr);
        return sqlSessionTemplate.selectOne("selectProximalFactoryPrice",map);
    }
}
