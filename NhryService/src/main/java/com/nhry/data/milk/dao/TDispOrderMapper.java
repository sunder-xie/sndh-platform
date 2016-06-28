package com.nhry.data.milk.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderKey;
import com.nhry.model.milk.RouteOrderSearchModel;

public interface TDispOrderMapper {
    int deleteByPrimaryKey(TDispOrderKey key);

    int insert(TDispOrder record);

    PageInfo selectMilkboxsByPage(RouteOrderSearchModel smodel);

    TDispOrder selectByPrimaryKey(TDispOrderKey key);

    int updateByPrimaryKeySelective(TDispOrder record);

    int updateDispOrderStatus(String orderCode,String status);
}