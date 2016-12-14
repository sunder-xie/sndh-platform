package com.nhry.data.order.dao;

import com.nhry.data.order.domain.TMstYearCardCompOrder;

/**
 * Created by gongjk on 2016/12/14.
 */
public interface TYearCardCompOrderMapper {
    //插入  年卡折扣补偿报表
    int addYearCardCompOrder(TMstYearCardCompOrder yOrder);
}
