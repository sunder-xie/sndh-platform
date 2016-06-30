package com.nhry.data.milktrans.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TRecBotMapper;
import com.nhry.data.milktrans.domain.TRecBot;
import com.nhry.model.milktrans.BoxSearch;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;

/**
 * Created by gongjk on 2016/6/27.
 */
public class TRecBotMapperImpl implements TRecBotMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    @Override
    public PageInfo searchBoxPage(BoxSearch bsearch) {
        return sqlSessionTemplate.selectListByPages("searchBoxPage",bsearch, Integer.parseInt(bsearch.getPageNum()), Integer.parseInt(bsearch.getPageSize()));
    }

    @Override
    public int addTrecBot(TRecBot tRecBot) {
        return sqlSessionTemplate.insert("addTrecBot",tRecBot);
    }

    @Override
    public TRecBot searchBoxByEmpAndBranch(CreateEmpReturnboxModel cModel) {
        return sqlSessionTemplate.selectOne("searchBoxByEmpAndBranch",cModel);
    }

    @Override
    public int uptTrecBot(TRecBot bot) {
        return sqlSessionTemplate.update("uptTrecBot",bot);
    }

}
