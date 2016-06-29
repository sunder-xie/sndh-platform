package com.nhry.data.milk.dao;

import java.util.List;

import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.model.milk.RouteDetailUpdateModel;

public interface TDispOrderItemMapper {
    int deleteByPrimaryKey(TDispOrderItemKey key);

    int insert(TDispOrderItem record);

    List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record);

    TDispOrderItem selectByPrimaryKey(TDispOrderItemKey key);

    int updateByPrimaryKeySelective(TDispOrderItem record);

    int updateDispOrderItem(RouteDetailUpdateModel record);
    
    int batchinsert(List<TDispOrderItem> records);
}