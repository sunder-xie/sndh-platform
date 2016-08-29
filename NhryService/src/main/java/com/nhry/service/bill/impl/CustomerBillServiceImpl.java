package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.domain.TVipAcct;
import com.nhry.data.bill.dao.CustomerBillMapper;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.bill.domain.TMstRecvOffset;
import com.nhry.data.bill.domain.TMstRefund;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.*;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.bill.dao.CustomerBillService;
import com.nhry.service.external.dao.EcService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.service.order.dao.PromotionService;
import com.nhry.service.pi.dao.PIVipInfoDataService;
import com.nhry.service.pi.pojo.MemberActivities;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.YearLastMonthUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;

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
    private OrderService orderService;
    private PromotionService promotionService;
    private TVipCustInfoService tVipCustInfoService;
    private TSysUserRoleMapper urMapper;
 	 private TaskExecutor taskExecutor;
 	 private EcService messLogService;
    private PIVipInfoDataService piVipInfoDataService;
    private TDispOrderItemMapper tDispOrderItemMapper;
    private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;


    public void settOrderDaliyPlanItemMapper(TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper) {
        this.tOrderDaliyPlanItemMapper = tOrderDaliyPlanItemMapper;
    }

    public void settDispOrderItemMapper(TDispOrderItemMapper tDispOrderItemMapper) {
        this.tDispOrderItemMapper = tDispOrderItemMapper;
    }

    public void setPiVipInfoDataService(PIVipInfoDataService piVipInfoDataService) {
        this.piVipInfoDataService = piVipInfoDataService;
    }

    public void setMessLogService(EcService messLogService)
 	{
 		this.messLogService = messLogService;
 	}

 	public void setTaskExecutor(TaskExecutor taskExecutor)
 	{
 		this.taskExecutor = taskExecutor;
 	}

    @Override
    public PageInfo searchCustomerOrder(CustBillQueryModel cModel) {
        TSysUser user = userSessionService.getCurrentUser();
        List<String> rids = urMapper.getUserRidsByLoginName(user.getLoginName());
        cModel.setSalesOrg(user.getSalesOrg());
        cModel.setBranchNo(user.getBranchNo());
        cModel.setDealerNo(user.getDealerId());

        // TODO Auto-generated method stub
        if(StringUtils.isEmpty(cModel.getPageNum()) || StringUtils.isEmpty(cModel.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return tPreOrderMapper.searchCustomerOrder(cModel);
    }

    @Override
    public TMstRecvBill getRecBillByOrderNo(String orderNo) {
        TMstRecvBill result = customerBillMapper.getRecBillByOrderNo(orderNo);

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

            TMstRecvBill customerBill = customerBillMapper.getRecBillByOrderNo(orderNo);
            if(customerBill!= null && "20".equals(customerBill.getStatus())){
                errorContent = "该订单已收款";
                throw new ServiceException(MessageCode.LOGIC_ERROR,errorContent);
            }

            TSysUser user = userSessionService.getCurrentUser();
            Date date = new Date();


            //录入收款信息
                BigDecimal accAmt = customerBill.getAccAmt();    //已收的订户余额
                BigDecimal amt = new BigDecimal(cModel.getAmt());//收款金额
                int com = accAmt.add(amt).compareTo(order.getInitAmt()); // 收款金额 加上已收的余额 与 订单金额比较
                //如果收款金额 加上已收的余额 大于订单金额
                if(com== 1){
                    //记录收款金额为 订单余额减去 已收的订户余额
                    customerBill.setAmt(new BigDecimal(cModel.getAmt()));
                    //并将多出来的金额 放入订户余额中
                    TVipAcct eac = tVipCustInfoService.findVipAcctByCustNo(order.getMilkmemberNo());
                    TVipAcct ac = new TVipAcct();
                    BigDecimal acLeftAmt = new BigDecimal("0.00");
                    if(eac!=null){
                        acLeftAmt = eac.getAcctAmt();
                    }
                    ac.setVipCustNo(order.getMilkmemberNo());
                    ac.setAcctAmt(accAmt.add(amt).subtract(order.getInitAmt()));
                    tVipCustInfoService.addVipAcct(ac);
                }else if(com == 0){
                    //如果收款金额 加上已收的余额 等于 订单金额
                    customerBill.setAmt(new BigDecimal(cModel.getAmt()));
                }else{
                    //如果收款金额 加上已收的余额 小于 订单金额
                    throw new ServiceException(MessageCode.LOGIC_ERROR,"输入的金额和已支付的账户余额不足以支付此订单!");
                }
                //备注
                if(StringUtils.isNoneBlank(cModel.getRemark())){
                    customerBill.setRemark(cModel.getRemark());
                }
                customerBill.setPaymentType(cModel.getPaymentType());
                customerBill.setPaymentYearMonth(YearLastMonthUtil.getYearThisMonth());
                customerBill.setRecvEmp(cModel.getEmpNo());
                customerBill.setReceiptDate(date);
                customerBill.setLastModified(date);
                customerBill.setLastModifiedBy(user.getLoginName());
                customerBill.setLastModifiedByTxt(user.getDisplayName());
                customerBill.setStatus("20");
                updateBill =  customerBillMapper.updateCustomerBillrPayment(customerBill);

                //更新订单状态为已收款
                updateOrderStatus = tPreOrderMapper.updateOrderPayMentStatus(orderNo);
                //预付款的,更新订单行起始日期
                if("20".equals(order.getPaymentmethod()) && cModel.getEntries()!=null && cModel.getEntries().size() > 0){
               	 orderService.updateOrderAndEntriesDispStartDate(order.getOrderNo(),cModel.getEntries());
                }
                
                
                //预付款的，付款后生成日计划
                if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getMilkboxStat()) ){
               	 OrderCreateModel omodel = orderService.selectOrderByCode(orderNo);
               	 List<TOrderDaliyPlanItem> list = orderService.createDaliyPlan(omodel.getOrder(),omodel.getEntries());
               	 promotionService.createDaliyPlanByPromotion(omodel.getOrder(),omodel.getEntries(),list);
                }

                //会员积分
                if("Y".equals(order.getIsIntegration())){
                    taskExecutor.execute(new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            this.setName("updateVip");
                            Map<String,String> planOrderMap = new HashMap<String,String>();
                            planOrderMap.put("salesOrg",user.getSalesOrg());
                            planOrderMap.put("orderNo",orderNo);
                            List<MemberActivities> items;
                            if("20".equals(order.getPaymentmethod())){
                                items   = tPlanOrderItemMapper.selectBeforePayActivitiesByOrderNo(planOrderMap);
                            }else{
                                items  = tPlanOrderItemMapper.selectAfterPayActivitiesByOrderNo(planOrderMap);
                            }
                            if(items.size()>0){
                                BigDecimal totalprice = new BigDecimal(0);
                                for (MemberActivities item : items){
                                    item.setProcess("X");
                                    Calendar calendar = new GregorianCalendar();
                                    calendar.setTime(date);
                                    Date firstDay = calendar.getTime();
                                    item.setActivitydate(firstDay);
                                    if(StringUtils.isNotBlank(item.getCardid())){
                                        item.setCardid("");
                                    }
                                    piVipInfoDataService.createMemberActivities(item);

                                }
                            }

                        }
                    });
                }


                if("10".equals(order.getPaymentmethod())){
                    BigDecimal factAmt = tPreOrderMapper.calculateOrderFactoryAmt(orderNo);
                    int  updateFactAmt = tPreOrderMapper.updateOrderFacAmt(factAmt  == null ? new BigDecimal(0) : factAmt,orderNo);
                }


               //发送EC,更新订单状态
       			TPreOrder sendOrder = new TPreOrder();
       			sendOrder.setOrderNo(order.getOrderNo());
       			sendOrder.setPreorderStat("101");
       			sendOrder.setPaymentmethod(cModel.getPaymentType());
       			taskExecutor.execute(new Thread(){
       				@Override
       				public void run() {
       					super.run();
       					this.setName("updateOrderStatus");
       					messLogService.sendOrderStatus(sendOrder);

       					if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getMilkboxStat())){
       						sendOrder.setEmpNo(order.getEmpNo());
       						sendOrder.setPreorderStat("200");
       						messLogService.sendOrderStatus(sendOrder);
       					}

       				}
       			 });
                
                return updateBill+updateOrderStatus;
    }

    @Override
    public CustomerBillOrder getCustomerOrderDetailByCode(String orderNo) {
        TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orderNo);
        TSysUser user = userSessionService.getCurrentUser();

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

    //根据订单号 创建 收款单
    @Override
    public TMstRecvBill createRecBillByOrderNo(String orderNo) {
        String errorContent ="";
        TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
        if(order == null){
            errorContent = "该订单不存在！";
            throw new ServiceException(MessageCode.LOGIC_ERROR,errorContent);
        }
        TMstRecvBill customerBill = customerBillMapper.getRecBillByOrderNo(orderNo);
        if(customerBill != null ){
            return customerBill;
        }
        TSysUser user = userSessionService.getCurrentUser();
        customerBill  = new TMstRecvBill();
        customerBill.setRecvEmp(order.getEmpNo());
        customerBill.setOrderNo(orderNo);
       // customerBill.setAmt(order.getInitAmt());
        customerBill.setStatus("10");
        customerBill.setReceiptNo(PrimaryKeyUtils.generateUuidKey());
        customerBill.setVipCustNo(order.getMilkmemberNo());
        customerBill.setCreateAt(new Date());
        customerBill.setCreateBy(user.getLoginName());
        customerBill.setCreateByTxt(user.getDisplayName());
        customerBill.setEndTime(order.getEndDate());
        TVipAcct ac = new TVipAcct();
        BigDecimal acLeftAmt = new BigDecimal("0.00");
        TVipAcct eac = tVipCustInfoService.findVipAcctByCustNo(order.getMilkmemberNo());
        if(eac!=null){
            acLeftAmt = eac.getAcctAmt();
        }
        //余额大于0  扣除余额
        ac.setVipCustNo(order.getMilkmemberNo());
        customerBill.setCustAccAmt(acLeftAmt);
        //如果余额大于订单金额  则
        if(acLeftAmt.compareTo(order.getInitAmt()) == 1){
            customerBill.setAccAmt(order.getInitAmt());
            customerBill.setSuppAmt(BigDecimal.ZERO);
            ac.setAcctAmt(order.getInitAmt().multiply(new BigDecimal(-1)));
        }else{
            ac.setAcctAmt(acLeftAmt.multiply(new BigDecimal(-1)));
            customerBill.setAccAmt(acLeftAmt);
            customerBill.setSuppAmt(order.getInitAmt().subtract(acLeftAmt));
        }
          tVipCustInfoService.addVipAcct(ac);
          customerBillMapper.insertCustomerPayment(customerBill);
        return customerBill;
    }

    @Override
    public List<String> searchCustomerOrderForExp(CustBillQueryModel cModel) {
        TSysUser user = userSessionService.getCurrentUser();

        cModel.setSalesOrg(user.getSalesOrg());
        if(StringUtils.isBlank(cModel.getBranchNo())){
            cModel.setBranchNo(user.getBranchNo());
        }
        if(StringUtils.isBlank(cModel.getDealerNo())) {
            cModel.setDealerNo(user.getDealerId());
        }
        return tPreOrderMapper.searchCustomerOrderForExp(cModel);
    }

    @Override
    public BigDecimal custBatchCollect(CustBatchBillQueryModel model) {
        TSysUser user = userSessionService.getCurrentUser();
        model.setSalesOrg(user.getSalesOrg());
        model.setBranchNo(user.getBranchNo());
        model.setDealerNo(user.getDealerId());
        List<TPreOrder> orderList = tPreOrderMapper.searchCustomerOrderByEmpNo(model);
        BigDecimal totalPayment = new BigDecimal(0);
        if(orderList !=null && orderList.size()>0){
            for(TPreOrder order : orderList){
                //判断该订单 对应的收款单是否创建 如果没有先创建
                TMstRecvBill  bill = this.createRecBillByOrderNo(order.getOrderNo());
                CustomerPayMentModel cmodel = new CustomerPayMentModel();
                cmodel.setAmt(order.getInitAmt().subtract(bill.getAccAmt()).toString());
                cmodel.setEmpNo(order.getEmpNo());
                cmodel.setOrderNo(bill.getOrderNo());
                cmodel.setPaymentType("10");
                this.customerPayment(cmodel);
                totalPayment =  totalPayment.add(order.getInitAmt());
            }
        }

        return totalPayment;
    }
    @Override
    public BigDecimal custBatchCollectBySelect(OrderSearchModel oModel) {
        if(oModel.getOrders() == null || !(oModel.getOrders().size()>0)){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"没有选择的订单");
        }
        List<TPreOrder> ordersList = tPreOrderMapper.selectCustBatchCollect(oModel);
        BigDecimal total = new BigDecimal(0);
        if(ordersList !=null && ordersList.size()>0){
            for(TPreOrder order : ordersList){
                //判断该订单 对应的收款单是否创建 如果没有先创建
                TMstRecvBill  bill = this.createRecBillByOrderNo(order.getOrderNo());
                CustomerPayMentModel cmodel = new CustomerPayMentModel();
                cmodel.setAmt(order.getInitAmt().subtract(bill.getAccAmt()).toString());
                cmodel.setEmpNo(order.getEmpNo());
                cmodel.setOrderNo(bill.getOrderNo());
                cmodel.setPaymentType("10");
                this.customerPayment(cmodel);
                total =  total.add(order.getInitAmt());
            }
        }
        return total;
    }

    @Override
    public int customerOffset(String receiptNo) {
        TMstRecvBill  bill = customerBillMapper.getRecBillByReceoptNo(receiptNo);
        if("10".equals(bill.getStatus())){
            throw  new ServiceException(MessageCode.LOGIC_ERROR,"该订单还未收款！！！");
        }else if("Y".equals(bill.getHadOffset())){
            throw  new ServiceException(MessageCode.LOGIC_ERROR,"该订单已经冲销过了！！！");
        } else{
            int orderNum = tDispOrderItemMapper.selectDispOrderNumByPreOrderNo(bill.getOrderNo());
            if(orderNum > 0 ){
                throw  new ServiceException(MessageCode.LOGIC_ERROR,"该订单已生成了路单，不能冲销！！！");
            }

            TPreOrder preOrder = tPreOrderMapper.selectByPrimaryKey(bill.getOrderNo());
            //扣除余额
            if(preOrder.getInitAmt().compareTo(bill.getAmt())!=0){
                //返回积分
                TVipAcct eac = tVipCustInfoService.findVipAcctByCustNo(preOrder.getMilkmemberNo());
                TVipAcct ac = new TVipAcct();
                BigDecimal acLeftAmt = new BigDecimal("0.00");
                if(eac!=null){
                    acLeftAmt = eac.getAcctAmt();
                }
                ac.setVipCustNo(preOrder.getMilkmemberNo());
                if(preOrder.getInitAmt().compareTo(bill.getAmt())==-1){
                    ac.setAcctAmt(preOrder.getInitAmt().subtract(bill.getAmt()));
                }else{
                    ac.setAcctAmt(preOrder.getInitAmt().subtract(bill.getAmt()));
                }

                tVipCustInfoService.addVipAcct(ac);
            }
            //判断是否需要 返还积分
            if("Y".equals(preOrder.getIsIntegration())){
                taskExecutor.execute(new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        this.setName("minusVipPoint");
                        BigDecimal gRate = BigDecimal.ONE.multiply(new BigDecimal(preOrder.getyGrowth()==null?0:preOrder.getyGrowth()));//成长
                        BigDecimal fRate = BigDecimal.ONE.multiply(new BigDecimal(preOrder.getyFresh()==null?0:preOrder.getyFresh()));//鲜峰
                        MemberActivities item = new MemberActivities();
                        Date date = new Date();
                        item.setActivitydate(date);
                        item.setSalesorg(preOrder.getSalesOrg());
                        item.setCategory("YRETURN");
                        item.setProcesstype("YSUB_RETURN");
                        item.setOrderid(preOrder.getOrderNo());
                        item.setMembershipguid(preOrder.getMemberNo());
                        item.setPointtype("YGROWTH");
                        item.setPoints(gRate);
                        //第1遍传成长
                        piVipInfoDataService.createMemberActivities(item);
                        //第2遍传先锋
                        item.setPointtype("YFRESH");
                        item.setPoints(fRate);
                        piVipInfoDataService.createMemberActivities(item);
                    }
                });
            }
            //将 收款单置为 已冲销
            TMstRecvBill newBill = new TMstRecvBill();
            newBill.setReceiptNo(bill.getReceiptNo());
            newBill.setHadOffset("Y");
            customerBillMapper.updateCustomerBillrPayment(newBill);
            //删除 日订单
            tOrderDaliyPlanItemMapper.deletePlansByOrder(bill.getOrderNo());
            //生成对应的冲销单
            TSysUser user = userSessionService.getCurrentUser();
            TMstRecvOffset offset  = new TMstRecvOffset();
            offset.setOrderNo(bill.getOrderNo());
            offset.setVipCustNo(bill.getVipCustNo());
            offset.setCreateAt(new Date());
            offset.setReceiptNo(bill.getReceiptNo());
            offset.setOffsetNo(PrimaryKeyUtils.generateUuidKey());
            offset.setOffsetDate(new Date());
            offset.setAmt(bill.getAmt());
            offset.setAccAmt(bill.getAccAmt());
            customerBillMapper.addOffset(offset);
            TPreOrder order = new TPreOrder();
            order.setOrderNo(bill.getOrderNo());
            order.setPaymentStat("10");
            tPreOrderMapper.updateOrderStatus(order);
        }

        return 1;
    }

    @Override
    public BigDecimal calculateTotalBeforBatch(CustBatchBillQueryModel cModel) {
        return tPreOrderMapper.calculateTotalBeforBatch(cModel);
    }

    @Override
    public int custRefund(CustomerRefundModel cModel) {
        TSysUser user = userSessionService.getCurrentUser();
        //返回积分
        TVipAcct eac = tVipCustInfoService.findVipAcctByCustNo(cModel.getVipCustNo());
        TVipAcct ac = new TVipAcct();
        BigDecimal acLeftAmt = new BigDecimal("0.00");
        if(eac!=null){
            acLeftAmt = eac.getAcctAmt();
        }
        ac.setVipCustNo(cModel.getVipCustNo());
        ac.setAcctAmt(cModel.getRefundAmount());
        int custInfo = tVipCustInfoService.addVipAcct(ac);
        TMstRefund refund = new TMstRefund();
        refund.setRefundNo(PrimaryKeyUtils.generateUpperUuidKey());
        refund.setAmt(cModel.getRefundAmount());
        refund.setCreateAt(new Date());
        refund.setVipCustNo(cModel.getVipCustNo());
        refund.setCreateBy(user.getLoginName());
        refund.setCreateByTxt(user.getDisplayName());
        if(StringUtils.isNotBlank(cModel.getRemark())){
            refund.setRemark(cModel.getRemark());
        }
        int refundInfo =  customerBillMapper.addRefund(refund);
        return refundInfo + custInfo;
    }

    @Override
    public List<CollectOrderBillModel> BatchPrintForExp(CustBillQueryModel cModel) {
        List<CollectOrderBillModel> result = new ArrayList<CollectOrderBillModel>();
        List<String> advancePayOrders = tPreOrderMapper.selectAdvanceOrderNos(cModel);
        if(advancePayOrders!=null && advancePayOrders.size()>0){
            List<CollectOrderBillModel> before = customerBillMapper.selectBeforeCollectByOrders("20",advancePayOrders);
            if(before!=null && before.size()>0){
                result.addAll(before);
            }
        }

        List<String> afterPayOrders = tPreOrderMapper.selectAfterOrderNos(cModel);
        if(afterPayOrders!=null && afterPayOrders.size()>0) {
            List<CollectOrderBillModel> after = customerBillMapper.selectAfterCollectByOrders("10", afterPayOrders);
            if (after != null && after.size() > 0) {
                result.addAll(after);
            }
        }
        return result;
    }


    @Override
    public CollectOrderBillModel queryCollectByOrderNo(String orderCode) {
        TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderCode);
        return customerBillMapper.queryCollectByOrderNo(orderCode,order.getPaymentmethod());
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
    public void setOrderService(OrderService orderService) {
       this.orderService = orderService;
    }
    
    public void setPromotionService(PromotionService promotionService) {
       this.promotionService = promotionService;
    }
    public void settVipCustInfoService(TVipCustInfoService tVipCustInfoService) {
       this.tVipCustInfoService = tVipCustInfoService;
    }

    public void setUrMapper(TSysUserRoleMapper urMapper) {
        this.urMapper = urMapper;
    }
}
