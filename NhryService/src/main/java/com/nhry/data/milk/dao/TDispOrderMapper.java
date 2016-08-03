package com.nhry.data.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderKey;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;

import java.math.BigDecimal;
import java.util.List;

public interface TDispOrderMapper {
    List<TDispOrder> selectTodayDispOrderByBranchNo(String branchNo);

    int insert(TDispOrder record);

    PageInfo searchRoutePlansByPage(RouteOrderSearchModel smodel);

    TDispOrder selectByPrimaryKey(TDispOrderKey key);

    TDispOrder selectYestodayDispOrderByEmp(TDispOrder record);

    int updateDispOrderStatus(String orderCode,String status);
    BigDecimal creatRecBot(CreateEmpReturnboxModel cModel);

    TDispOrder getDispOrderByNo(String dispOrderNo);
    
    int updateDispOrderEmp(TDispOrder order);
}