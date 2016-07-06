package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TMdDealer;

import java.util.List;

public interface TMdDealerMapper {
    int deleteDealerByNo(String dealerNo);

    int insertDealer(TMdDealer record);

//    int insertSelective(TMdDealer record);

    TMdDealer selectDealerByNo(String dealerNo);

//    int updateByPrimaryKeySelective(TMdDealer record);

    int updateDealer(TMdDealer record);

    List<TMdDealer> selectDealerBySalesOrg(String salesOrg);
}