package com.nhry.data.milktrans.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TRecBotDetailMapper;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.model.milktrans.ReturnboxSerarch;

import java.util.List;
import java.util.Map;

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
    public TRecBotDetail selectBotDetailByDetLsh(Map<String, String> map) {
        return sqlSessionTemplate.selectOne("selectBotDetailByDetLshAndSpec",map);
    }

    @Override
    public TRecBotDetail selectRetByDispOrderNo(String dispOrderNo) {
        return sqlSessionTemplate.selectOne("selectRetByDispOrderNo",dispOrderNo);
    }

    @Override
    public int uptRecBotDetail(TRecBotDetail entry) {
        return sqlSessionTemplate.update("uptRecBotDetail",entry);
    }

    @Override
    public PageInfo searchRetBoxPage(ReturnboxSerarch rSearch) {
        return sqlSessionTemplate.selectListByPages("searchRetBoxPage", rSearch, Integer.parseInt(rSearch.getPageNum()), Integer.parseInt(rSearch.getPageSize()));

    }
}
