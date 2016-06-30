package com.nhry.data.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;
import com.nhry.model.milktrans.UnDeliverProductSearch;

import java.util.List;

public interface TDispOrderItemMapper {
    int deleteByPrimaryKey(TDispOrderItemKey key);

    int insert(TDispOrderItem record);

    List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record);

    TDispOrderItem selectByPrimaryKey(TDispOrderItemKey key);

    int updateByPrimaryKeySelective(TDispOrderItem record);

    int updateDispOrderItem(RouteDetailUpdateModel record);
    
    int batchinsert(List<TDispOrderItem> records);

    List<TRecBotDetail> selectItemsByReturnBox(CreateEmpReturnboxModel cModel);


    PageInfo searchUndeliverProduct(UnDeliverProductSearch uSearch);

    List<TDispOrderItem> selectItemsByOrderNo(String dispOrderNo);
}