package com.nhry.data.stud.dao;

import java.util.List;
import java.util.Map;

import com.nhry.data.stud.domain.TMstOrderStudLoss;

public interface TMstOrderStudLossMapper {

    int insertOrderStudLoss(TMstOrderStudLoss orderStudLoss);
    
    List<TMstOrderStudLoss> findLossByOrderId(Map<String, Object> selectMap);
}