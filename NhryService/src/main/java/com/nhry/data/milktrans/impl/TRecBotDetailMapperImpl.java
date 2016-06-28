package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TRecBotDetailMapper;
import com.nhry.data.milktrans.domain.TRecBotDetail;

import java.util.List;

/**
 * Created by gongjk on 2016/6/28.
 */
public class TRecBotDetailMapperImpl implements TRecBotDetailMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    @Override
    public int addRecBotItem(TRecBotDetail bot) {
        return sqlSessionTemplate.insert("addRecBotItem",bot);
    }

    @Override
    public List<TRecBotDetail> selectBotDetailByRetLsh(String retLsh) {
        return sqlSessionTemplate.selectList("selectBotDetailByRetLsh",retLsh);
    }

    @Override
    public int uptRecBotDetail(TRecBotDetail entry) {
        return sqlSessionTemplate.update("uptRecBotDetail",entry);
    }
}
