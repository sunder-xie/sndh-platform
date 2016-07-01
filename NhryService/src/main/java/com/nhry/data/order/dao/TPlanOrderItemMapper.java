package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.model.bill.EmpDispDetiallInfoSearch;

import java.util.List;

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

    PageInfo empDispDetialInfo(EmpDispDetiallInfoSearch eSearch);
}