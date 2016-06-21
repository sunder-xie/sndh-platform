package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderSearchModel;

public interface TPreOrderMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(TPreOrder record);

    int insertSelective(TPreOrder record);

    TPreOrder selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(TPreOrder record);

    int updateByPrimaryKey(TPreOrder record);

	 PageInfo selectOrdersByPages(OrderSearchModel smodel);
}