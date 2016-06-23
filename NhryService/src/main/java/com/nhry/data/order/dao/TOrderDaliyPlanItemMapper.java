package com.nhry.data.order.dao;

import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.model.order.RequireOrderModel;

import java.util.Date;
import java.util.List;

public interface TOrderDaliyPlanItemMapper {
    int deleteByPrimaryKey(TOrderDaliyPlanItemKey key);

    int insert(TOrderDaliyPlanItem record);

    int insertSelective(TOrderDaliyPlanItem record);

    TOrderDaliyPlanItem selectByPrimaryKey(TOrderDaliyPlanItemKey key);

    int updateByPrimaryKeySelective(TOrderDaliyPlanItem record);

    int updateByPrimaryKey(TOrderDaliyPlanItem record);
    
    int updateDaliyPlanItemStatus(TOrderDaliyPlanItem record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByEntryNo(String itemNo);

    String getDayOrderStat(String orderNo, Date date);

    List<TOrderDaliyPlanItem> selectDaliyPlansByBranchAndDay(RequireOrderModel rModel);
}