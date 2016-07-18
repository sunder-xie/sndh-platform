package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.domain.TSsmSalOrderItems;

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
}
