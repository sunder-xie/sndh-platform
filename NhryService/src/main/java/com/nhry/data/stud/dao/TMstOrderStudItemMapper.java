package com.nhry.data.stud.dao;

import java.util.List;
import java.util.Map;

import com.nhry.data.stud.domain.TMstOrderStudItem;

public interface TMstOrderStudItemMapper {
	
    int insertOrderItem(TMstOrderStudItem orderStudItem);

    TMstOrderStudItem selectByMid(String mid);

    List<TMstOrderStudItem> findOrderItemByOrderId(String orderId);
    
    List<TMstOrderStudItem> findOrderItemByMap(Map<String, Object> parameterMap);
}