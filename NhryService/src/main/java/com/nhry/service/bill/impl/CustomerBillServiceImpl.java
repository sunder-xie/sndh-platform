package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.bill.dao.CustomerBillMapper;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.bill.CustomerBillOrder;
import com.nhry.model.bill.CustomerPayMentModel;
import com.nhry.service.bill.dao.CustomerBillService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gongjk on 2016/6/23.
 */
public class CustomerBillServiceImpl implements CustomerBillService {

    private CustomerBillMapper customerBillMapper;
    private TPreOrderMapper tPreOrderMapper;
    private TPlanOrderItemMapper tPlanOrderItemMapper;
    private UserSessionService userSessionService;

    @Override
    public PageInfo searchCustomerOrder(CustBillQueryModel cModel) {
        // TODO Auto-generated method stub
        if(StringUtils.isEmpty(cModel.getPageNum()) || StringUtils.isEmpty(cModel.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return tPreOrderMapper.searchCustomerOrder(cModel);
    }

    @Override
    public TMstRecvBill getCustomerOrderByCode(String orderNo) {
        return customerBillMapper.getCustomerOrderByCode(orderNo);
    }

    @Override
    public int customerPayment(CustomerPayMentModel cModel) {
        String errorContent ="";
        try{
            int updateBill = 0;
            int updateOrderStatus = 0;
            String orderNo = cModel.getOrderNo();
            TMstRecvBill bill = customerBillMapper.getCustomerOrderByCode(orderNo);

            if(bill!= null && "20".equals(bill.getStatus())){
                errorContent = "该订单已收款";
                throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单已收款！");
            }else {
                TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
                Calendar calendar =Calendar.getInstance();
                Date date = new Date();
                calendar.setTime(date);

                TMstRecvBill customerBill = new TMstRecvBill();
                customerBill.setOrderNo(orderNo);
                customerBill.setAmt(Integer.valueOf(cModel.getAmt()));
                customerBill.setReceiptDate(date);
                customerBill.setStatus("20");
                customerBill.setRecvEmp(order.getEmpNo());
                customerBill.setPaymentType(cModel.getPaymentType());
                String payMentYM = String.valueOf(calendar.get(Calendar.YEAR)+String.valueOf(calendar.get(Calendar.MONTH)+1));
                TSysUser user = userSessionService.getCurrentUser();
                customerBill.setVipCustNo(order.getMemberNo());
                customerBill.setPaymentYearMonth(payMentYM);
                customerBill.setLastModified(date);
                customerBill.setLastModifiedBy(user.getLoginName());
                customerBill.setCreateByTxt(user.getDisplayName());

                if(bill!=null && bill.getStatus()=="10"){
                    updateBill =  customerBillMapper.updateCustomerBillrPayment(customerBill);
                }else{
                    if(StringUtils.isBlank(customerBill.getReceiptNo())){
                        String receiptNo  = String.valueOf(new Date().getTime()).substring(0,10);
                        customerBill.setReceiptNo(receiptNo);
                    }
                    customerBill.setCreateAt(date);
                    customerBill.setCreateBy(user.getLoginName());
                    customerBill.setCreateByTxt(user.getDisplayName());
                    updateBill =  customerBillMapper.customerPayment(customerBill);
                }
                updateOrderStatus = tPreOrderMapper.updateOrderPayMentStatus(orderNo);
                return updateBill+updateOrderStatus;
            }

        }catch (Exception e){
            if("".equals(errorContent)){
                throw new ServiceException(MessageCode.LOGIC_ERROR,"收款失败！");
            }else{
                throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单已收款！");
            }

        }


    }

    @Override
    public CustomerBillOrder getCustomerOrderDetailByCode(String orderNo) {
        TMstRecvBill bill = customerBillMapper.getCustomerOrderByCode(orderNo);
        List<TPlanOrderItem> entry = new  ArrayList<TPlanOrderItem>();
        if(tPlanOrderItemMapper.selectByOrderCode(orderNo) !=null){
            entry.addAll(tPlanOrderItemMapper.selectByOrderCode(orderNo));
        }

        CustomerBillOrder  order = new CustomerBillOrder();
        order.setBill(bill);
        order.setEntrys(entry);
        return order;
    }

    public void setCustomerBillMapper(CustomerBillMapper customerBillMapper) {
        this.customerBillMapper = customerBillMapper;
    }

    public void settPreOrderMapper(TPreOrderMapper tPreOrderMapper) {
        this.tPreOrderMapper = tPreOrderMapper;
    }

    public void settPlanOrderItemMapper(TPlanOrderItemMapper tPlanOrderItemMapper) {
        this.tPlanOrderItemMapper = tPlanOrderItemMapper;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }
}
