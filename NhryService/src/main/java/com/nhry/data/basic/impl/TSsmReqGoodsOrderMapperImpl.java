package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.basic.domain.TSsmReqGoodsOrder;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderKey;

/**
 * Created by cbz on 7/5/2016.
 */
public class TSsmReqGoodsOrderMapperImpl implements TSsmReqGoodsOrderMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int deleteSRGO(TSsmReqGoodsOrderKey key) {
        return sqlSessionTemplate.delete("deleteSRGO",key);
    }

    @Override
    public int insertSRGO(TSsmReqGoodsOrder record) {
        return sqlSessionTemplate.insert("insertSRGO",record);
    }

    @Override
    public int insertSRGOSelective(TSsmReqGoodsOrder record) {
        return sqlSessionTemplate.insert("insertSRGOSelective",record);
    }

    @Override
    public TSsmReqGoodsOrder getSRGO(TSsmReqGoodsOrderKey key) {
        return sqlSessionTemplate.selectOne("getSRGO",key);
    }

    @Override
    public int updateSRGOSelective(TSsmReqGoodsOrder record) {
        return sqlSessionTemplate.update("updateSRGOSelective",record);
    }

    @Override
    public int updateSRGO(TSsmReqGoodsOrder record) {
        return sqlSessionTemplate.update("updateGRGO",record);
    }
}
