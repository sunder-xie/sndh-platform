package com.nhry.service.stud.dao;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.model.stud.OrderStudQueryModel;

public interface OrderStudService {

    int createOrder(TMstOrderStud mstOrderStud) throws Exception;

    TMstOrderStud selectByOrderId(String orderId);

    int updateByOrder(TMstOrderStud mstOrderStud);
    
    PageInfo<TMstOrderStud> findOrderPage(OrderStudQueryModel queryModel);

	Map<String, Object> findOrderInfoBySchoolCodeAndDate(TMstOrderStud mstOrderStud);
}