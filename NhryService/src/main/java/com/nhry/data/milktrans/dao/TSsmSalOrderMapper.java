package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmSalOrder;
import com.nhry.model.order.OrderPointModel;
import com.nhry.model.milktrans.SalOrderModel;

import java.util.List;
import java.util.Map;

/**
 * Created by gongjk on 2016/7/16.
 */
public interface TSsmSalOrderMapper {
    int addsalOrder(TSsmSalOrder order);

    int uptVouCherNoByOrderNo(Map map);

    List<TSsmSalOrder> selectSalOrderByDateAndNo(SalOrderModel model);

    List<TSsmSalOrder> selectSalOrderByDateAndBranchOrDealerNo(SalOrderModel sModel);

    int delSalOrderByOrderNo(String orderNo);


}
