package com.nhry.data.milk.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milk.dao.TMstRefuseResendItemMapper;
import com.nhry.data.milktrans.domain.TMstRefuseResendItem;

/**
 * Created by gongjk on 2016/10/19.
 */
public class TMstRefuseResendItemMapperImpl implements TMstRefuseResendItemMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    @Override
    public int addResendItem(TMstRefuseResendItem resendItem) {
        return sqlSessionTemplate.insert("addResendItem",resendItem);
    }
}
