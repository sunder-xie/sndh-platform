package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TMstRequireOrder;
import com.nhry.model.milktrans.RequireOrderSearch;

import java.util.List;

/**
 * Created by gongjk on 2016/6/22.
 */
public interface TRequireOrderMapper {

    public int insertRequireOrder(TMstRequireOrder order);

    List<TMstRequireOrder> searchRequireOrder(RequireOrderSearch rModel);
    TMstRequireOrder selectRequireOrderItemByitem(TMstRequireOrder tMstRequireOrder);

    int uptRequireOrder(TMstRequireOrder tMstRequireOrder);
    int delRequireOrder(TMstRequireOrder tMstRequireOrder);
}
