package com.nhry.service.pi.impl;

import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.service.pi.dao.PIOrderService;
import com.nhry.service.pi.pojo.Order;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by cbz on 7/20/2016.
 */
@WebService
public class PIOrderServiceImpl implements PIOrderService {
    @Override
    public Order findOrder(String ORDER_NO, TPreOrder order, List<TPlanOrderItem> planItems, List<TOrderDaliyPlanItem> daliyPlanItems) {
        return null;
    }
}
