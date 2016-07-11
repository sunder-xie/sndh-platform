package com.nhry.data.order.dao;

import com.nhry.data.order.domain.TPlanOrderItem;

import java.util.List;
import java.util.Map;

public interface TPlanOrderItemMapper {
    int deleteByEntryItemNo(String itemNo);

    int insert(TPlanOrderItem record);

    int insertSelective(TPlanOrderItem record);

    TPlanOrderItem selectByPrimaryKey(String itemNo);

    int updateByPrimaryKeySelective(TPlanOrderItem record);

    int updateByPrimaryKey(TPlanOrderItem record);
    
    TPlanOrderItem selectEntryByEntryNo(String code);
    
    List<TPlanOrderItem> selectByOrderCode(String orderCode);
    
    int updateEntryByItemNo(TPlanOrderItem record);

    List<TPlanOrderItem> selectEntriesByOrderNo(Map<String, String> map);


}