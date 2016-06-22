package com.nhry.data.order.dao;

import java.util.List;

import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;

public interface TOrderDaliyPlanItemMapper {
	 int deleteFromDateToDate(TOrderDaliyPlanItem record);

    int insert(TOrderDaliyPlanItem record);

    int insertSelective(TOrderDaliyPlanItem record);

    TOrderDaliyPlanItem selectByPrimaryKey(TOrderDaliyPlanItemKey key);

    int updateByPrimaryKeySelective(TOrderDaliyPlanItem record);

    int updateByPrimaryKey(TOrderDaliyPlanItem record);
    
    int updateDaliyPlanItemStatus(TOrderDaliyPlanItem record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByEntryNo(String itemNo);
    
    int updateDaliyPlanItem(TOrderDaliyPlanItem record);
    
    TOrderDaliyPlanItem selectDaliyPlansByEntryNoAndNo(TOrderDaliyPlanItemKey record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderNo);
}