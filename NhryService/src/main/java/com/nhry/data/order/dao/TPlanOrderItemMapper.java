package com.nhry.data.order.dao;

import com.nhry.data.order.domain.TPlanOrderItem;

public interface TPlanOrderItemMapper {
    int deleteByPrimaryKey(String itemNo);

    int insert(TPlanOrderItem record);

    int insertSelective(TPlanOrderItem record);

    TPlanOrderItem selectByPrimaryKey(String itemNo);

    int updateByPrimaryKeySelective(TPlanOrderItem record);

    int updateByPrimaryKey(TPlanOrderItem record);
}