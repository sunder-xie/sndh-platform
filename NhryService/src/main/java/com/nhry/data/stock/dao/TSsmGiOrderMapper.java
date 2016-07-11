package com.nhry.data.stock.dao;

import com.nhry.data.stock.domain.TSsmGiOrder;

public interface TSsmGiOrderMapper {
    int deleteGiOrderByNo(String orderNo);

    int insertGiOrder(TSsmGiOrder record);

    int insertGiOrderSelective(TSsmGiOrder record);

    TSsmGiOrder selectGiOrderByNo(String orderNo);

    int updateGiOrderSelective(TSsmGiOrder record);

    int updateGiOrder(TSsmGiOrder record);
}