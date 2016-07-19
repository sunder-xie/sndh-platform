package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.auth.domain.TSysUserRole;
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
import com.nhry.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by gongjk on 2016/6/23.
 */
public class CustomerBillServiceImpl implements CustomerBillService {

    private CustomerBillMapper customerBillMapper;
    private TPreOrderMapper tPreOrderMapper;
    private TPlanOrderItemMapper tPlanOrderItemMapper;
    private UserSessionService userSessionService;
    private TSysUserRoleMapper urMapper;

    @Override
    public PageInfo searchCustomerOrder(CustBillQueryModel cModel) {
        TSysUser user = userSessionService.getCurrentUser();
        TSysUserRole userRole =urMapper.getUserRoleByLoginName(user.getLoginName());
        cModel.setSalesOrg(user.getSalesOrg());
        if("10004".equals(userRole.getId())){
            cModel.setBranchNo(user.getBranchNo());
        }else if("10005".equals(userRole.getId())){
            cModel.setDealerNo(user.getDealerId());
        }
        // TODO Auto-generated method stub
        if(StringUtils.isEmpty(cModel.getPageNum()) || StringUtils.isEmpty(cModel.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return tPreOrderMapper.searchCustomerOrder(cModel);
    }

    @Override
    public TMstRecvBill getRecBillByOrderNo(String orderNo) {
        TMstRecvBill result = customerBillMapper.getRecBillByOrderNo(orderNo);
        if(result == null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单还未收款！！！请先收款");
        }
        return result;
    }

    @Override
    public int customerPayment(CustomerPayMentModel cModel) {
        String errorContent ="";
            int updateBill = 0;
            int updateOrderStatus = 0;
            String orderNo = cModel.getOrderNo();
            TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);

            if(order == null){
                errorContent = "该订单不存在！";
                throw new ServiceException(MessageCode.LOGIC_ERROR,errorContent);
            }

            TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orderNo);
            if(bill!= null && "20".equals(bill.getStatus())){
                errorContent = "该订单已收款";
                throw new ServiceException(MessageCode.LOGIC_ERROR,errorContent);
            }else {

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
                String month = calendar.get(Calendar.MONTH)+1 < 10 ? "0"+(calendar.get(Calendar.MONTH)+1) : String.valueOf(calendar.get(Calendar.MONTH)+1);
                String payMentYM = String.valueOf(calendar.get(Calendar.YEAR))+month;
                TSysUser user = userSessionService.getCurrentUser();
                //备注
                if(StringUtils.isNoneBlank(cModel.getRemark())){
                    customerBill.setRemark(cModel.getRemark());
                }

                customerBill.setEndTime(order.getEndDate());
                customerBill.setVipCustNo(order.getMilkmemberNo());
                customerBill.setPaymentYearMonth(payMentYM);
                customerBill.setLastModified(date);
                customerBill.setLastModifiedBy(user.getLoginName());
                customerBill.setCreateByTxt(user.getDisplayName());

                if(bill!=null && bill.getStatus()=="10"){
                    updateBill =  customerBillMapper.updateCustomerBillrPayment(customerBill);
                }else{
                    if(StringUtils.isBlank(customerBill.getReceiptNo())){
                        //收款流水号
                        customerBill.setReceiptNo(SerialUtil.creatSeria());
                    }
                    customerBill.setCreateAt(date);
                    customerBill.setCreateBy(user.getLoginName());
                    customerBill.setCreateByTxt(user.getDisplayName());
                    updateBill =  customerBillMapper.customerPayment(customerBill);
                }
                //更新订单状态为已收款
                updateOrderStatus = tPreOrderMapper.updateOrderPayMentStatus(orderNo);
                return updateBill+updateOrderStatus;
            }
    }

    @Override
    public CustomerBillOrder getCustomerOrderDetailByCode(String orderNo) {
        TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orderNo);
        TSysUser user = userSessionService.getCurrentUser();
        if(bill == null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单还未收款！！请先收款");
        }
        int totalNum = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        List<TPlanOrderItem> entries = new  ArrayList<TPlanOrderItem>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("orderNo",orderNo);
        map.put("salesOrg",user.getSalesOrg());
        List<TPlanOrderItem> items = tPlanOrderItemMapper.selectEntriesByOrderNo(map);
        if( items !=null && items.size()>0){
            for(TPlanOrderItem item : items){
                totalNum = totalNum+item.getQty();
                totalPrice = totalPrice.add(item.getSalesPrice().multiply(new BigDecimal(item.getQty())));
                entries.add(item);
            }
        }
        bill.setTotalNum(totalNum);
        bill.setTotalPrice(totalPrice);
        CustomerBillOrder  order = new CustomerBillOrder();
        order.setBill(bill);
        order.setEntries(entries);
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

    public void setUrMapper(TSysUserRoleMapper urMapper) {
        this.urMapper = urMapper;
    }
}
