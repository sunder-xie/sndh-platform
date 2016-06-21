package com.nhry.data.order.dao;

import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;

public interface TOrderDaliyPlanItemMapper {
    int deleteByPrimaryKey(TOrderDaliyPlanItemKey key);

    int insert(TOrderDaliyPlanItem record);

    int insertSelective(TOrderDaliyPlanItem record);

    TOrderDaliyPlanItem selectByPrimaryKey(TOrderDaliyPlanItemKey key);

    int updateByPrimaryKeySelective(TOrderDaliyPlanItem record);

    int updateByPrimaryKey(TOrderDaliyPlanItem record);
}