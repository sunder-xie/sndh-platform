package com.nhry.service.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.bill.CustomerBillOrder;
import com.nhry.model.bill.CustomerPayMentModel;

/**
 * Created by gongjk on 2016/6/23.
 */
public interface CustomerBillService {
   public PageInfo searchCustomerOrder(CustBillQueryModel cModel);

   public TMstRecvBill getCustomerOrderByCode(String orderNo);

   public int  customerPayment(CustomerPayMentModel cModel);

   public CustomerBillOrder getCustomerOrderDetailByCode(String orderNo);

}
