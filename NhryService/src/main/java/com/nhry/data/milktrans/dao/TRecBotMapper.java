package com.nhry.data.milktrans.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milktrans.domain.TRecBot;
import com.nhry.model.milktrans.BoxSearch;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;
import java.util.Date;

import java.util.List;

/**
 * Created by gongjk on 2016/6/27.
 */
public interface TRecBotMapper {
    public PageInfo searchBoxPage(BoxSearch bsearch);

    int addTrecBot(TRecBot tRecBot);

    TRecBot searchBoxByEmpAndBranch(CreateEmpReturnboxModel cModel);

    int uptTrecBot(TRecBot bot);

    List<TRecBot> searchBoxByDate(Date today);

    TRecBot getReturnBoxByNo(String retLsh);
}
