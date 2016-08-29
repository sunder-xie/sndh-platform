package com.nhry.data.bill.dao;

import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.bill.domain.TMstRecvOffset;
import com.nhry.data.bill.domain.TMstRefund;
import com.nhry.model.bill.CollectOrderBillModel;

import java.util.List;

/**
 * Created by gongjk on 2016/6/23.
 */
public interface CustomerBillMapper {

    public TMstRecvBill getRecBillByOrderNo(String orderNo);

    public int insertCustomerPayment(TMstRecvBill customerBill);

    public int updateCustomerBillrPayment(TMstRecvBill customerBill);

    CollectOrderBillModel queryCollectByOrderNo(String orderCode,String paymentmethod);

    TMstRecvBill getRecBillByReceoptNo(String receiptNo);

    int addOffset(TMstRecvOffset offset);

    int  addRefund(TMstRefund refund);

    List<CollectOrderBillModel> selectAfterCollectByOrders(String paymentmethod, List<String> advancePayOrders);

    List<CollectOrderBillModel> selectBeforeCollectByOrders(String paymentmethod, List<String> advancePayOrders);
}
