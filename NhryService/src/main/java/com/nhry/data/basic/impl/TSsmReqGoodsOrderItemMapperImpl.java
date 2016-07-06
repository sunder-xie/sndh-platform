package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderItem;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderItemKey;
import com.nhry.data.basic.domain.TSsmReqGoodsOrderKey;

import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/5/2016.
 */
public class TSsmReqGoodsOrderItemMapperImpl implements com.nhry.data.basic.dao.TSsmReqGoodsOrderItemMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int deleteSRGOItem(TSsmReqGoodsOrderItemKey key) {
        return sqlSessionTemplate.delete("deleteSRGOItem",key);
    }

    @Override
    public int insertSRGOItem(TSsmReqGoodsOrderItem record) {
        return sqlSessionTemplate.insert("insertSRGOItem",record);
    }

    @Override
    public int insertSRGOItemSelective(TSsmReqGoodsOrderItem record) {
        return sqlSessionTemplate.insert("insertSRGOItemSelective",record);
    }

    @Override
    public TSsmReqGoodsOrderItem getSRGOItem(TSsmReqGoodsOrderItemKey key) {
        return sqlSessionTemplate.selectOne("getSRGOItem",key);
    }

    @Override
    public int updateSRGOItemSelective(TSsmReqGoodsOrderItem record) {
        return sqlSessionTemplate.update("updateSRGOItemSelective",record);
    }

    @Override
    public int updateSRGOItem(TSsmReqGoodsOrderItem record) {
        return sqlSessionTemplate.update("updateSRGOItem",record);
    }

    @Override
    public List<Map<String, String>> findItemsForPI(TSsmReqGoodsOrderKey key) {
        return sqlSessionTemplate.selectList("findItemsForPI",key);
    }
}
