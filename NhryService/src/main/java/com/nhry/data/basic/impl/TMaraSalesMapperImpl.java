package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMaraSalesMapper;
import com.nhry.data.basic.domain.TMaraSalesKey;

/**
 * Created by cbz on 6/24/2016.
 */
public class TMaraSalesMapperImpl implements TMaraSalesMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int deleteMaraSales(TMaraSalesKey key) {
        return sqlSessionTemplate.delete("deleteByPrimaryKey",key);
    }

    @Override
    public int insertMaraSales(TMaraSalesKey record) {
        return sqlSessionTemplate.insert("insertMaraSales",record);
    }

    @Override
    public int isMaraSales(TMaraSalesKey record) {
        return sqlSessionTemplate.selectOne("isMaraSales",record);

    }
}
