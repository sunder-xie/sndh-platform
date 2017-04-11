package com.nhry.data.stud.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.model.stud.OrderStudQueryModel;

public interface TMstOrderStudMapper {

    int insertOrder(TMstOrderStud mstOrderStud);

    TMstOrderStud selectByOrderId(String orderId);

    int updateByOrder(TMstOrderStud mstOrderStud);
    
    PageInfo<TMstOrderStud> findOrderPage(OrderStudQueryModel queryModel);

	TMstOrderStud selectOrderBySchoolCodeAndDateWithOrderStatus10(TMstOrderStud mstOrderStud);
}