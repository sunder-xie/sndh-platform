package com.nhry.service.bill.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.model.bill.*;

import java.util.List;

/**
 * Created by gongjk on 2016/6/23.
 */
public interface CustomerBillService {
   public PageInfo searchCustomerOrder(CustBillQueryModel cModel);

   public TMstRecvBill getRecBillByOrderNo(String orderNo);

   public int  customerPayment(CustomerPayMentModel cModel);

   public CustomerBillOrder getCustomerOrderDetailByCode(String orderNo);

   public TMstRecvBill createRecBillByOrderNo(String orderNo);

   List<String> searchCustomerOrderForExp(CustBillQueryModel cModel);

   int custBatchCollect(CustBatchBillQueryModel cModel);

   CollectOrderBillModel queryCollectByOrderNo(String orderCode);

   public int  custPayment(CustomerPayMentModel cModel);
}
