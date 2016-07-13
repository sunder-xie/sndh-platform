package com.nhry.data.milktrans.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.model.milktrans.ReturnboxSerarch;

import java.util.List;
import java.util.Map;

/**
 * Created by gongjk on 2016/6/28.
 */
public interface TRecBotDetailMapper {
    int addRecBotItem(TRecBotDetail bot);

    int uptRecBotDetail(TRecBotDetail entry);

    PageInfo searchRetBoxPage(ReturnboxSerarch rSearch);

    TRecBotDetail selectBotDetailByDetLsh(Map<String, String> map);

    List<TRecBotDetail> selectRetByDispOrderNo(String dispOrderNo);
}
