package com.nhry.data.bill.dao;

import com.nhry.data.bill.domain.TMstRecvBill;

/**
 * Created by gongjk on 2016/6/23.
 */
public interface CustomerBillMapper {

    public TMstRecvBill getCustomerOrderByCode(String orderNo);

    public int customerPayment(TMstRecvBill customerBill);

    public int updateCustomerBillrPayment(TMstRecvBill customerBill);
}
