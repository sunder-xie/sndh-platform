package com.nhry.data.order.dao;

import com.nhry.data.order.domain.TMstRequireOrder;
import com.nhry.model.order.RequireOrderModel;

import java.util.List;

/**
 * Created by gongjk on 2016/6/22.
 */
public interface TRequireOrderMapper {

    public int insertRequireOrder(TMstRequireOrder order);

    List<TMstRequireOrder> searchRequireOrder(RequireOrderModel rModel);
    TMstRequireOrder selectRequireOrderItemByitem(TMstRequireOrder tMstRequireOrder);

    int uptRequireOrder(TMstRequireOrder tMstRequireOrder);
}
