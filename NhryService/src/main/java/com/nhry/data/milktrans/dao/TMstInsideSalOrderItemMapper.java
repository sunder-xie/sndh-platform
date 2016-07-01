package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TMstInsideSalOrderItem;

/**
 * Created by gongjk on 2016/6/30.
 */
public interface TMstInsideSalOrderItemMapper {
    int delInSalOrderItemByOrderNo(String insOrderNo);

    int insertOrderItem(TMstInsideSalOrderItem item);
}
