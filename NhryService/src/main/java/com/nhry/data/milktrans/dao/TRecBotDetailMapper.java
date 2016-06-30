package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TRecBotDetail;

import java.util.List;

/**
 * Created by gongjk on 2016/6/28.
 */
public interface TRecBotDetailMapper {
    int addRecBotItem(TRecBotDetail bot);

    List<TRecBotDetail> selectBotDetailByRetLsh(String retLsh);

    int uptRecBotDetail(TRecBotDetail entry);
}
