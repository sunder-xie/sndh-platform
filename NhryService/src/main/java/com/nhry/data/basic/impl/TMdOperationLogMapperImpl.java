package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdOperationLogMapper;
import com.nhry.data.basic.domain.TMdOperationLog;

/**
 * Created by gongjk on 2016/10/24.
 */
public class TMdOperationLogMapperImpl implements TMdOperationLogMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    @Override
    public int save(TMdOperationLog log) {
        return sqlSessionTemplate.insert("save",log);
    }
}
