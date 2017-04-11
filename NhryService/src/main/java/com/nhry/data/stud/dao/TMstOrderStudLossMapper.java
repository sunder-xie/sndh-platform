package com.nhry.data.stud.dao;

import java.util.List;

import com.nhry.data.stud.domain.TMstOrderStudLoss;

public interface TMstOrderStudLossMapper {

    int insertOrderStudLoss(TMstOrderStudLoss orderStudLoss);
    
    List<TMstOrderStudLoss> findLossByOrderId(String orderId);
}