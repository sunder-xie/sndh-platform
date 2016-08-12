package com.nhry.data.promotion.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.promotion.dao.PromotionScopeItemMapper;
import com.nhry.data.promotion.domain.PromotionScopeItem;

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
    public PromotionScopeItem selectScopeItem(PromotionScopeItem key) {
        return sqlSessionTemplate.selectOne("selectScopeItem",key);
    }

}
