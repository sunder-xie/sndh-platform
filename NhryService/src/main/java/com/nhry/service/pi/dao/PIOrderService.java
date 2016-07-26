package com.nhry.service.pi.dao;

import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.service.pi.pojo.Order;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * Created by cbz on 7/20/2016.
 */
@WebService
public interface PIOrderService {
   public Order findOrder(@WebParam(name = "ORDER_NO") String ORDER_NO,@WebParam(name = "T_PREORDER") TPreOrder T_PREORDER,@WebParam(name = "T_MST_PLAN_ORDER_ITEM") List<TPlanOrderItem> planItems,@WebParam(name = "T_MST_ORDER_DALIY_PLAN_ITEM") List<TOrderDaliyPlanItem> daliyPlanItems);
}
