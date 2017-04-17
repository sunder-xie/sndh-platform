package com.nhry.data.stud.dao;

import java.util.List;
import java.util.Map;

import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.data.stud.domain.TMstOrderStudItem;

public interface TMstOrderStudItemMapper {
	
    int insertSdutOrderItem(TMstOrderStudItem orderStudItem);

    TMstOrderStudItem selectByMid(String mid);

    List<TMstOrderStudItem> findOrderItemByOrderId(String orderId);
    
    List<TMstOrderStudItem> findOrderItemByMap(Map<String, Object> parameterMap);
    
    List<TMstOrderStudItem> findOrderItemByMapWithBatch(Map<String, Object> parameterMap);

	int deleteOrderAndItem(Map<String, Object> delMap);

	int deleteOrderWithBatch(Map<String, Object> delMap);

	String findSumBySelective(TMstOrderStud obj);

	String findLossSumBySelective(TMstOrderStud obj);
	
}