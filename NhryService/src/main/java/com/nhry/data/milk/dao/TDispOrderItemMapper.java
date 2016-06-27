package com.nhry.data.milk.dao;

import java.util.List;

import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;

public interface TDispOrderItemMapper {
    int deleteByPrimaryKey(TDispOrderItemKey key);

    int insert(TDispOrderItem record);

    List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record);

    TDispOrderItem selectByPrimaryKey(TDispOrderItemKey key);

    int updateByPrimaryKeySelective(TDispOrderItem record);

    int updateByPrimaryKey(TDispOrderItem record);
}