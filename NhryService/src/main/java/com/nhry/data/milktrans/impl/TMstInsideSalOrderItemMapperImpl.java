package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TMstInsideSalOrderItemMapper;
import com.nhry.data.milktrans.domain.TMstInsideSalOrderItem;

/**
 * Created by gongjk on 2016/6/30.
 */
public class TMstInsideSalOrderItemMapperImpl implements TMstInsideSalOrderItemMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    @Override
    public int delInSalOrderItemByOrderNo(String insOrderNo) {
        return sqlSessionTemplate.delete("delInSalOrderItemByOrderNo",insOrderNo);
    }

    @Override
    public int insertOrderItem(TMstInsideSalOrderItem item) {
        return sqlSessionTemplate.insert("insertOrderItem",item);
    }
}
