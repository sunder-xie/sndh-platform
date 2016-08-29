package com.nhry.data.bill.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.bill.dao.CustomerBillMapper;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.bill.domain.TMstRecvOffset;
import com.nhry.data.bill.domain.TMstRefund;
import com.nhry.model.bill.CollectOrderBillModel;


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
    public CollectOrderBillModel queryCollectByOrderNo(String orderCode) {
        return sqlSessionTemplate.selectOne("queryCollectByOrderNo",orderCode);
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

}
