package com.nhry.data.bill.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.bill.dao.CustomerBillMapper;
import com.nhry.data.bill.domain.TMstRecvBill;

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
    public int customerPayment(TMstRecvBill customerBill) {
        return sqlSessionTemplate.insert("customerPayment",customerBill);

    }

    @Override
    public int updateCustomerBillrPayment(TMstRecvBill customerBill) {
        return sqlSessionTemplate.update("updateCustomerBillrPayment",customerBill);
    }

}
