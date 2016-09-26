package com.nhry.data.bill.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.bill.dao.CustomerBillMapper;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.bill.domain.TMstRecvOffset;
import com.nhry.data.bill.domain.TMstRefund;
import com.nhry.model.bill.CollectOrderBillModel;
import com.nhry.model.bill.CollectOrderSearchModel;

import java.util.List;


/**
 * Created by gongjk on 2016/6/23.
 */
public class CustomerBillMapperImpl implements CustomerBillMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }



    @Override
    public TMstRecvBill getRecBillByOrderNo(String orderNo) {
        return sqlSessionTemplate.selectOne("getCustomerOrderByCode",orderNo);
    }

    @Override
    public int insertCustomerPayment(TMstRecvBill customerBill) {
        return sqlSessionTemplate.insert("customerPayment",customerBill);

    }

    @Override
    public int updateCustomerBillrPayment(TMstRecvBill customerBill) {
        return sqlSessionTemplate.update("updateCustomerBillrPayment",customerBill);
    }

    @Override
    public CollectOrderBillModel queryCollectByOrderNo(String orderCode,String paymentmethod) {
        CollectOrderSearchModel model = new CollectOrderSearchModel();
        model.setOrderNo(orderCode);
        model.setPaymentmehod(paymentmethod);
        if("20".equals(paymentmethod)){
            return sqlSessionTemplate.selectOne("queryCollectByBeforeOrderNo",model);
        }else{
            return  sqlSessionTemplate.selectOne("queryCollectByAfterOrderNo",model);
        }

    }

    @Override
    public TMstRecvBill getRecBillByReceoptNo(String receiptNo) {
        return sqlSessionTemplate.selectOne("getRecBillByReceoptNo",receiptNo);
    }

    @Override
    public int addOffset(TMstRecvOffset offset) {
        return sqlSessionTemplate.insert("addOffset",offset);
    }

    @Override
    public int addRefund(TMstRefund refund) {
        return sqlSessionTemplate.insert("addRefund",refund);
    }

    @Override
    public List<CollectOrderBillModel> selectAfterCollectByOrders(String paymentmethod, List<String> advancePayOrders) {
        CollectOrderSearchModel model = new CollectOrderSearchModel();
        model.setOrders(advancePayOrders);
        model.setPaymentmehod(paymentmethod);
        return sqlSessionTemplate.selectList("queryCollectByAfterOrders",model);
    }

    @Override
    public List<CollectOrderBillModel> selectBeforeCollectByOrders(String paymentmethod, List<String> advancePayOrders) {
        CollectOrderSearchModel model = new CollectOrderSearchModel();
        model.setOrders(advancePayOrders);
        model.setPaymentmehod(paymentmethod);
        return sqlSessionTemplate.selectList("queryCollectByBeforeOrders",model);
    }

    @Override
    public int delReceipt(String receiptNo) {
        return sqlSessionTemplate.delete("delReceipt",receiptNo);
    }

}
