package com.nhry.data.order.dao;

import java.util.List;

import com.nhry.data.order.domain.TPlanOrderItem;

public interface TPlanOrderItemMapper {
    int deleteByPrimaryKey(String itemNo);

    int insert(TPlanOrderItem record);

    int insertSelective(TPlanOrderItem record);

    TPlanOrderItem selectByPrimaryKey(String itemNo);

    int updateByPrimaryKeySelective(TPlanOrderItem record);

    int updateByPrimaryKey(TPlanOrderItem record);
    
    TPlanOrderItem selectEntryByEntryNo(String code);
    
    List<TPlanOrderItem> selectByOrderCode(String orderCode);
    
    int updateEntryByItemNo(TPlanOrderItem record);
}