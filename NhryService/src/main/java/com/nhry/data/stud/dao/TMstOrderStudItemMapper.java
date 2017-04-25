package com.nhry.data.stud.dao;

import java.util.List;
import java.util.Map;

import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.data.stud.domain.TMstOrderStudItem;

public interface TMstOrderStudItemMapper {
	
    int insertSdutOrderItem(TMstOrderStudItem orderStudItem);

    TMstOrderStudItem selectByMid(String mid);

    List<TMstOrderStudItem> findOrderItemByOrderId(Map<String, Object> parameterMap);
    
    List<TMstOrderStudItem> findOrderItemByMap(Map<String, Object> parameterMap);
    
    List<TMstOrderStudItem> findOrderItemByMapWithBatch(Map<String, Object> parameterMap);

	int deleteOrderAndItem(Map<String, Object> delMap);

	int deleteOrderWithBatch(Map<String, Object> delMap);

	String findSumBySelective(TMstOrderStud obj);

	String findLossSumBySelective(TMstOrderStud obj);

	List<TMstOrderStud> findClassOrderItemByOrderStud(TMstOrderStud item);

	int insertSdutOrderItemUnpack(TMstOrderStudItem item);

	int deleteOrderItemByOrderIdUnpack(String orderId);

	List<TMstOrderStudItem> findOrderItemByMapUnpack(Map<String, Object> selectMap);

	String findSumBySelectiveUnpack(TMstOrderStud obj);

	String findLossSumBySelectiveUnpack(TMstOrderStud obj);
	
}