package com.nhry.data.promotion.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.promotion.dao.PromotionScopeItemMapper;
import com.nhry.data.promotion.domain.PromotionScopeItem;
import com.nhry.data.promotion.domain.PromotionScopeItemKey;

/**
 * Created by cbz on 8/8/2016.
 */
public class PromotionScopeItemMapperImpl implements PromotionScopeItemMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int insertScopeItem(PromotionScopeItem record) {
        return sqlSessionTemplate.insert("insertScopeItem",record);
    }

    @Override
    public PromotionScopeItem selectScopeItem(PromotionScopeItemKey key) {
        return sqlSessionTemplate.selectOne("selectScopeItem",key);
    }

    @Override
    public int updateScopeItemSelective(PromotionScopeItem record) {
        return sqlSessionTemplate.update("updateScopeItemSelective",record);
    }

    @Override
    public int updateScopeItem(PromotionScopeItem record) {
        return sqlSessionTemplate.update("updateScopeItem",record);
    }
}
