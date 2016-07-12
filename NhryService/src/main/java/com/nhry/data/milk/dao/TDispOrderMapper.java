package com.nhry.data.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderKey;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;

import java.math.BigDecimal;

public interface TDispOrderMapper {
    int deleteByPrimaryKey(TDispOrderKey key);

    int insert(TDispOrder record);

    PageInfo searchRoutePlansByPage(RouteOrderSearchModel smodel);

    TDispOrder selectByPrimaryKey(TDispOrderKey key);

    TDispOrder selectYestodayDispOrderByEmp(TDispOrder record);

    int updateDispOrderStatus(String orderCode,String status);
    BigDecimal creatRecBot(CreateEmpReturnboxModel cModel);

    TDispOrder getDispOrderByNo(String dispOrderNo);
}