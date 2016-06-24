package com.nhry.service.milktrans.dao;

import com.nhry.data.milktrans.domain.TMstRequireOrder;
import com.nhry.model.milktrans.RequireOrderModel;
import com.nhry.model.milktrans.RequireOrderSearch;

/**
 * Created by gongjk on 2016/6/24.
 */
public interface RequireOrderService {
    RequireOrderModel creatRequireOrder(RequireOrderModel rModel);

    int insertRequireOrder(TMstRequireOrder order);

    RequireOrderModel searchRequireOrder(RequireOrderSearch rModel);

    int uptRequireOrder(RequireOrderModel rModel);
}
