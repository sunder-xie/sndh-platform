package com.nhry.data.order.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.order.dao.TYearCardCompOrderMapper;
import com.nhry.data.order.domain.TMstYearCardCompOrder;

/**
 * Created by gongjk on 2016/12/14.
 */
public class TYearCardCompOrderMapperImpl implements TYearCardCompOrderMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int addYearCardCompOrder(TMstYearCardCompOrder yOrder) {
        return sqlSessionTemplate.insert("addYearCardCompOrder",yOrder);
    }
}
