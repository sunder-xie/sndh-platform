package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.domain.TSsmSalOrderItems;
import java.util.Map;

import java.util.List;

/**
 * Created by gongjk on 2016/7/16.
 */
public class TSsmSalOrderItemMapperImpl implements TSsmSalOrderItemMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    @Override
    public int addSalOrderItem(TSsmSalOrderItems salOrderItems) {
        return sqlSessionTemplate.insert("addSalOrderItem",salOrderItems);
    }

    @Override
    public List<Map<String, String>> findItemsForPI(String orderNo) {
        return sqlSessionTemplate.selectList("findItemsForPI",orderNo);
    }
}
