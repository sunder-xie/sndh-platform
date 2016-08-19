package com.nhry.data.bill.dao;

import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.model.bill.CollectOrderBillModel;

/**
 * Created by gongjk on 2016/6/23.
 */
public interface CustomerBillMapper {

    public TMstRecvBill getRecBillByOrderNo(String orderNo);

    public int insertCustomerPayment(TMstRecvBill customerBill);

    public int updateCustomerBillrPayment(TMstRecvBill customerBill);

    CollectOrderBillModel queryCollectByOrderNo(String orderCode);
}
