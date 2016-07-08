package com.nhry.data.stock.dao;

import com.nhry.data.stock.domain.TSsmGiOrderItem;
import com.nhry.data.stock.domain.TSsmGiOrderItemKey;

public interface TSsmGiOrderItemMapper {
    int deleteGiOrderItemByNo(TSsmGiOrderItemKey key);

    int insertGiOrderItem(TSsmGiOrderItem record);

    int insertGiOrderItemSelective(TSsmGiOrderItem record);

    TSsmGiOrderItem selectGiOrderItemByNo(TSsmGiOrderItemKey key);

    int updateGiOrderItemSelective(TSsmGiOrderItem record);

    int updateGiOrderItem(TSsmGiOrderItem record);
}