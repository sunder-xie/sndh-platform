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
    /**
     * 根据订单号 查询收款信息（不包含冲销过的收款单）
     * @param  orderNo
     * @return
     */
    public TMstRecvBill getRecBillByOrderNo(String orderNo);

    public int insertCustomerPayment(TMstRecvBill customerBill);

    public int updateCustomerBillrPayment(TMstRecvBill customerBill);

    CollectOrderBillModel queryCollectByOrderNo(String orderCode,String paymentmethod);

    TMstRecvBill getRecBillByReceoptNo(String receiptNo);

    int addOffset(TMstRecvOffset offset);

    int  addRefund(TMstRefund refund);

    List<CollectOrderBillModel> selectHasItemsCollectByOrders(String paymentmethod, List<String> advancePayOrders);

    List<CollectOrderBillModel> selectNoItemsCollectByOrders(String paymentmethod, List<String> advancePayOrders);

    int delReceipt(String receiptNo);
}
