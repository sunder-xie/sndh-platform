package com.nhry.service.order.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TVipAcct;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.data.bill.dao.CustomerBillMapper;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.order.*;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.PriceService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.external.dao.EcService;
import com.nhry.service.order.dao.MilkBoxService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.service.order.dao.PromotionService;
import com.nhry.service.order.pojo.OrderRemainData;
import com.nhry.utils.CodeGeneratorUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderServiceImpl extends BaseService implements OrderService {

	private TPreOrderMapper tPreOrderMapper;
	private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
	private TPlanOrderItemMapper tPlanOrderItemMapper;
	private TVipCustInfoService tVipCustInfoService;
	private MilkBoxService milkBoxService;
	private PriceService priceService;
	private PromotionService promotionService;
	private TDispOrderItemMapper tDispOrderItemMapper;
	private TMdBranchMapper branchMapper;
	private TaskExecutor taskExecutor;
	private EcService messLogService;
	private CustomerBillMapper customerBillMapper;
	
	public void setCustomerBillMapper(CustomerBillMapper customerBillMapper) {
		this.customerBillMapper = customerBillMapper;
	}

	public void setMessLogService(EcService messLogService)
	{
		this.messLogService = messLogService;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor)
	{
		this.taskExecutor = taskExecutor;
	}

	public void setBranchMapper(TMdBranchMapper branchMapper) {
		this.branchMapper = branchMapper;
	}

	public void settDispOrderItemMapper(TDispOrderItemMapper tDispOrderItemMapper)
	{
		this.tDispOrderItemMapper = tDispOrderItemMapper;
	}

	public void setPromotionService(PromotionService promotionService)
	{
		this.promotionService = promotionService;
	}
	
	public void setPriceService(PriceService priceService)
	{
		this.priceService = priceService;
	}

	public void settVipCustInfoService(TVipCustInfoService tVipCustInfoService)
	{
		this.tVipCustInfoService = tVipCustInfoService;
	}

	public void settOrderDaliyPlanItemMapper(TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper)
	{
		this.tOrderDaliyPlanItemMapper = tOrderDaliyPlanItemMapper;
	}

	public void settPlanOrderItemMapper(TPlanOrderItemMapper tPlanOrderItemMapper)
	{
		this.tPlanOrderItemMapper = tPlanOrderItemMapper;
	}

	public void settPreOrderMapper(TPreOrderMapper tPreOrderMapper)
	{
		this.tPreOrderMapper = tPreOrderMapper;
	}
	
	public void setMilkBoxService(MilkBoxService milkBoxService)
	{
		this.milkBoxService = milkBoxService;
	}

	//登陆页面时，待确认的订单
	@Override
	public int selectRequiredOrderNum()
	{
		OrderSearchModel smodel = new OrderSearchModel();
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());
		return tPreOrderMapper.selectRequiredOrderNum(smodel);
	}

	//登陆页面时，还有5天就要到期的订单
	@Override
	public int selectStopOrderNum()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		OrderSearchModel smodel = new OrderSearchModel();
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());
		smodel.setOrderDateStart(format.format(afterDate(new Date(),1)));
		smodel.setOrderDateEnd(format.format(afterDate(new Date(),5)));
		return tPreOrderMapper.selectStopOrderNum(smodel);
	}

	/* (non-Javadoc) 
	* @title: searchNeedResumeOrders
	* @description: 5天内需要续订的订单列表
	* @param smodel
	* @return 
	* @see com.nhry.service.order.dao.OrderService#searchNeedResumeOrders(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public PageInfo searchNeedResumeOrders(OrderSearchModel smodel)
	{
		if(StringUtils.isBlank(smodel.getOrderDateStart()) || StringUtils.isBlank(smodel.getOrderDateEnd())){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			smodel.setOrderDateStart(format.format(afterDate(today,1)));
			smodel.setOrderDateEnd(format.format(afterDate(today,5)));
		}
		
		//权限
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());
		
		return tPreOrderMapper.selectNeedResumeOrders(smodel);
	}
	
	/* (non-Javadoc)
        * @title: selectOrderByCode
        * @description: 根据订单号查询订单
        * @param orderCode
        * @return
        * @see com.nhry.service.order.dao.OrderService#selectOrderByCode(java.lang.String)
        */
	@Override
	public OrderCreateModel selectOrderByCode(String orderCode)
	{
		OrderCreateModel orderModel = new OrderCreateModel();
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderCode);
		if(order==null)throw new ServiceException(MessageCode.LOGIC_ERROR,"订单不存在！");
		
		ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(orderCode);
		orderModel.setEntries(entries);
		orderModel.setOrder(order);
//		//帐户
		orderModel.setAccount(tVipCustInfoService.findVipAcctByCustNo(orderModel.getOrder().getMilkmemberNo()));
//		//地址信息
		orderModel.setAddress(tVipCustInfoService.findAddressDetailById(orderModel.getOrder().getAdressNo()));
		
		return orderModel;
	}

	/* (non-Javadoc)
    * @title: selectOrderByCode
    * @description: 根据订单号查询该订单所有日计划
    * @param orderCode
    * @return
    * @see com.nhry.service.order.dao.OrderService#selectOrderByCode(java.lang.String)
    */
   @Override
   public List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderCode)
   {  
   	return tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orderCode);
   }
   
	/* (non-Javadoc) 
	* @title: searchDaliyPlansByStatus
	* @description: 根据订单号，日单状态查询该订单所有日计划
	* @param orderNo
	* @param status
	* @return 
	* @see com.nhry.service.order.dao.OrderService#searchDaliyPlansByStatus(java.lang.String, java.lang.String) 
	*/
	@Override
	public List<TOrderDaliyPlanItem> searchDaliyPlansByStatus(String orderNo, String status1,String status2,String status3)
	{
		return tOrderDaliyPlanItemMapper.searchDaliyPlansByStatus(orderNo, status1, status2, status3);
	}

	/* (non-Javadoc)
	* @title: 退回单列表
	* @description:
	* @return
	* @see com.nhry.service.order.dao.OrderService#searchReturnOrders()
	*/
	@Override
	public PageInfo searchReturnOrders(ManHandOrderSearchModel manHandModel) {
		if(StringUtils.isEmpty(manHandModel.getPageNum()) || StringUtils.isEmpty(manHandModel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		manHandModel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		manHandModel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		return tPreOrderMapper.searchReturnOrders(manHandModel);
	}

	/* (non-Javadoc)
	* @title: 人工分单详情
	* @description:
	* @return
	* @see com.nhry.service.order.dao.OrderService#manHandOrderDetail()
	*/
	@Override
	public TPreOrder manHandOrderDetail(String orderCode) {
		return tPreOrderMapper.manHandOrderDetail(orderCode);
	}

	/* (non-Javadoc)
	* @title: 人工分单
	* @description:
	* @return
	* @see com.nhry.service.order.dao.OrderService#uptManHandOrder()
	*/
	@Override
	public int uptManHandOrder(UpdateManHandOrderModel uptManHandModel) {
		if(org.apache.commons.lang.StringUtils.isBlank(uptManHandModel.getBranchNo()) || org.apache.commons.lang.StringUtils.isBlank(uptManHandModel.getOrderNo())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站号和订单号不能为空！");
		}
		//如果更换奶站，可能会更换商品价格，并把用户挂到该奶站下
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(uptManHandModel.getOrderNo());
//		if(!uptManHandModel.getBranchNo().equals(order.getBranchNo())){
			
		//订户挂奶站,订单的奶站和销售组织变更
		String salesOrg = tVipCustInfoService.uptCustBranchNo(order.getMilkmemberNo(),uptManHandModel.getBranchNo());
		uptManHandModel.setSalesOrg(salesOrg);
		
		//换价格
		ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(uptManHandModel.getOrderNo());
		for(TPlanOrderItem entry : orgEntries){
			float price = priceService.getMaraPrice(uptManHandModel.getBranchNo(), entry.getMatnr(), order.getDeliveryType());
			if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"替换的奶站,产品["+entry.getMatnr()+"]价格小于0，或可能没有该产品，请检查或退订此订单!");
			entry.setSalesPrice(new BigDecimal(String.valueOf(price)));
			tPlanOrderItemMapper.updateEntryByItemNo(entry);
		}
			
//		}
		
		tPreOrderMapper.uptManHandOrder(uptManHandModel);
		
		//当是电商的订单时，更新EC对应订单的奶站
		if("10".equals(order.getPreorderSource())){
			order.setBranchNo(uptManHandModel.getBranchNo());
			//发送EC
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("updateOrderBranchNo");
					messLogService.sendOrderBranch(order);
				}
			});
		}
		
		return 1;
	}

	/* (non-Javadoc)
	* @title: 退回
	* @description:
	* @return
	* @see com.nhry.service.order.dao.OrderService#returnOrder()
	*/
	@Override
	public int returnOrder(ReturnOrderModel r) {
		if( StringUtils.isBlank(r.getRetReason()) || StringUtils.isBlank(r.getOrderNo())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "信息不完整！");
		}
		
		//需要把订户挂到一个没用的奶站上
		//TODO
		
		r.setRetDate(new Date());
		r.setRetReason(r.getRetReason().trim());
		return tPreOrderMapper.returnOrder(r);
	}
	
	/* (non-Javadoc) 
	* @title: batchStopOrderForTime
	* @description: 批量停订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#batchStopOrderForTime(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int batchStopOrderForTime(OrderSearchModel record)
	{
		if(StringUtils.isBlank(record.getOrderDateEnd())){
			if(StringUtils.isNotBlank(record.getOrderNo())){
				List<String> orderList = Arrays.asList(record.getOrderNo().split(","));
				orderList.stream().forEach((e)->{
					record.setOrderNo(e);
					stopOrderForTime(record);
				});
			}
		}else{
			if(StringUtils.isNotBlank(record.getOrderNo())){
				List<String> orderList = Arrays.asList(record.getOrderNo().split(","));
				orderList.stream().forEach((e)->{
					record.setOrderNo(e);
					stopOrderInTime(record);
				});
			}
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: stopOrderForTime
	* @description: 订单停订一段时间
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#stopOrderForTime(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int stopOrderForTime(OrderSearchModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		
		if(order!= null){
			if("20".equals(order.getSign())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"该订单已经停订，不能修改停订!");
			}
//			String status = null;
//			try
//			{
//				status = tOrderDaliyPlanItemMapper.getDayOrderStat(record.getOrderNo(), new Date());
//			}
//			catch (Exception e)
//			{
//				//没有当天已确认的日单
//			}
//			if("20".equals(status)){
//				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"订单日订单已经确认，不能修改订单!");
//			}
			if(tOrderDaliyPlanItemMapper.searchDaliyOrdersByOrderNoAndFinalStop(record).size()>0){
				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"长期停订前订单日订单已经停订，请往后修改日期或修改日计划!");
			}
			//停订逻辑
			try
			{
				Date sdate = format.parse(record.getOrderDateStart());
				order.setStopDateStart(sdate);
				if(StringUtils.isNotBlank(record.getOrderDateEnd())){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"接口访问错误!不能有结束日期!");
				}else{
					order.setStopDateEnd(null);
				}
			}
			catch (ParseException e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式不正确!");
			}
			order.setStopReason(record.getReason());
			String state = order.getPaymentmethod();
			if("20".equals(state)){//先付款
				//用掉的钱，将存入订户账户
//				if("20".equals(order.getPaymentStat())){//已经付过钱的退余额
//					BigDecimal remain = order.getCurAmt();
//					TVipAcct ac = new TVipAcct();
//					ac.setAcctAmt(remain);
//					ac.setVipCustNo(order.getMilkmemberNo());
//					tVipCustInfoService.addVipAcct(ac);
//				}
				
				tOrderDaliyPlanItemMapper.updateDaliyPlansToStop(order);

			}else{//后付款
				//用掉的钱，将存入订户账户
//				BigDecimal remain = order.getCurAmt().subtract(order.getInitAmt());
//				TVipAcct ac = new TVipAcct();
//				ac.setAcctAmt(remain);
//				ac.setVipCustNo(order.getMilkmemberNo());
//				tVipCustInfoService.addVipAcct(ac);
				
				tOrderDaliyPlanItemMapper.updateDaliyPlansToStop(order);
				
			}
			//更新订单标示为停订,综合可能有很多订单
			if(order.getStopDateEnd()==null){
   			order.setSign("20");//停订中
			}
			tPreOrderMapper.updateOrderStatus(order);
			
			//发送EC
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("stopOrderForTime");
					record.setContent("Y");
					messLogService.sendOrderStopRe(record);
				}
			});
			
			//订户状态更改???
			List<TPreOrder> list = tPreOrderMapper.selectByMilkmemberNo(order.getMilkmemberNo());
			if(list==null||list.size()<=0){
				tVipCustInfoService.discontinue(order.getMilkmemberNo(), "30",null,null);
			}
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"当前订单不存在");
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: stopOrderInTime
	* @description: 区间内停订订单,就是停订+复订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#stopOrderInTime(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int stopOrderInTime(OrderSearchModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		
		if(order!= null){
//			String status = null;
//			try
//			{
//				status = tOrderDaliyPlanItemMapper.getDayOrderStat(record.getOrderNo(), new Date());
//			}
//			catch (Exception e)
//			{
//				//没有当天已确认的日单
//			}
//			if("20".equals(status)){
//				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"订单日订单已经确认，不能修改订单!");
//			}
			if(tOrderDaliyPlanItemMapper.searchDaliyOrdersByOrderNoAndFinalStop(record).size()>0){
				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"停订区间内有日订单已经停订，请往后修改日期或修改日计划!");
			}
			//停订逻辑
			try
			{
				Date sdate = format.parse(record.getOrderDateStart());
				order.setStopDateStart(sdate);
				Date edate = format.parse(record.getOrderDateEnd());
				order.setStopDateEnd(edate);
			}
			catch (ParseException e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式不正确!");
			}
			tOrderDaliyPlanItemMapper.updateDaliyPlansToStop(order);

			//以下为复订的逻辑
			BigDecimal orderAmt = order.getInitAmt();//订单金额
			ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrderNo());
			String startDateStr = record.getOrderDateEnd();
			Date startDate = order.getStopDateEnd();//续订开始日期
			//后付款的
			if(!"20".equals(order.getPaymentmethod())){
				//把后期路单置回原样,重新计算金额
				TOrderDaliyPlanItem uptKey= new TOrderDaliyPlanItem(); 
				uptKey.setOrderNo(order.getOrderNo());
				uptKey.setDispDateStr(startDateStr);
				tOrderDaliyPlanItemMapper.updateFromDateToDate(uptKey);
				
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(record.getOrderNo());

				for(TOrderDaliyPlanItem p :daliyPlans){
					if("30".equals(p.getStatus()) && !p.getDispDate().before(order.getStopDateStart())){
						BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
						orderAmt = orderAmt.subtract(planAmt);
					}
				}
				
				order.setCurAmt(orderAmt.subtract(order.getInitAmt().subtract(order.getCurAmt())));
				order.setInitAmt(orderAmt);
				
				for(TOrderDaliyPlanItem p :daliyPlans){
					if("10".equals(p.getStatus())){
						BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
						orderAmt = orderAmt.subtract(planAmt);
						p.setRemainAmt(orderAmt);
						tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
					}else{
						p.setRemainAmt(orderAmt);
						tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
					}
				}
				
			}else{
					ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(record.getOrderNo());
					
					//删除从复订时间开始的日计划
	   			TOrderDaliyPlanItem deleteKey= new TOrderDaliyPlanItem(); 
	   			deleteKey.setOrderNo(order.getOrderNo());
	   			deleteKey.setDispDateStr(startDateStr);
	   			tOrderDaliyPlanItemMapper.deleteFromDateToDate(deleteKey);
	   			
	   			Map<String,Integer> qtyMap = new HashMap<String,Integer>();
					for(TOrderDaliyPlanItem p: daliyPlans){
						if(p.getGiftQty()!=null)continue;
						String itemNo = p.getItemNo();
						if(!"30".equals(p.getStatus()) && p.getDispDate().before(startDate) && p.getGiftQty()==null){
							BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
							orderAmt = orderAmt.subtract(planAmt);
						}
						if(!p.getDispDate().before(order.getStopDateStart()) && "30".equals(p.getStatus())){
							if(qtyMap.containsKey(itemNo)){
								qtyMap.replace(itemNo, 1 + qtyMap.get(itemNo));
							}else{
								qtyMap.put(itemNo, 1);
							}
						}
					}
					
	   			List<TOrderDaliyPlanItem> list = createDaliyPlanForResumeOrder(order , orgEntries , orderAmt , startDate , qtyMap);
	   			
	   			//回改订单行项目,更新最后配送日期
	   			for( TPlanOrderItem entry :orgEntries){
	   				for(TOrderDaliyPlanItem p:list){
	   					if(entry.getItemNo().equals(p.getItemNo())){
	   						entry.setEndDispDate(p.getDispDate());
	   						promotionService.calculateEntryPromotionForStop(entry);
	   						//保存修改后的该行
	   						tPlanOrderItemMapper.updateEntryByItemNo(entry);
	   						break;
	   					}
	   				}
	   			}
	   			
	   			//如果有赠品，生成赠品的日计划
	   			promotionService.createDaliyPlanByPromotion(order,orgEntries,list);
	   			
	   			//更新订单
	   			order.setEndDate(calculateFinalDate(orgEntries));//订单截止日期修改
			}

			//更新截止日期
			tPreOrderMapper.updateOrderEndDate(order);
			
			//发送EC
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("stopOrderInTime");
					record.setContent("N");
					messLogService.sendOrderStopRe(record);
				}
			});
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"当前订单不存在");
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: stopOrderForTime
	* @description: 订单退订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#stopOrderForTime(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int backOrder(OrderSearchModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		
		if(order!= null){
//			if(tDispOrderItemMapper.selectCountOfTodayByOrgOrder(order.getOrderNo(),format.format(new Date()))>0)throw new ServiceException(MessageCode.LOGIC_ERROR,"此订单，今日有确认的路单!请等路单确认后再操作!");
			
			order.setBackDate(afterDate(new Date(),1));
			order.setBackReason(record.getReason());
			order.setMemoTxt(record.getMemoTxt());
			
			String state = order.getPaymentmethod();
			
			BigDecimal leftAmt = order.getCurAmt();
			if("20".equals(state)){//先付款
				tOrderDaliyPlanItemMapper.updateDaliyPlansToBack(order);
				
				//此为多余的钱，如果是预付款，将存入订户账户
				if(order.getInitAmt()!=null && "20".equals(order.getPaymentStat())){//已经收款的
					TVipAcct ac = new TVipAcct();
				   ac.setVipCustNo(order.getMilkmemberNo());
				   ac.setAcctAmt(order.getCurAmt());
					tVipCustInfoService.addVipAcct(ac);
				}else if("10".equals(order.getPaymentStat())){
					//此处看是否打印过收款单，里面有没有用帐户余额支付的金额，退回
					TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
	            if(bill!= null){
	            	TVipAcct ac = new TVipAcct();
					   ac.setVipCustNo(order.getMilkmemberNo());
					   ac.setAcctAmt(bill.getAccAmt());//TODO 取哪个金额?
						tVipCustInfoService.addVipAcct(ac);
	            }
				}
				
				//用掉的钱
				BigDecimal remain = order.getInitAmt().subtract(order.getCurAmt());
				order.setInitAmt(remain);
				order.setCurAmt(new BigDecimal("0.00"));
				
			}else{//后付款
				tOrderDaliyPlanItemMapper.updateDaliyPlansToBack(order);
				
				//用掉的钱
				BigDecimal remain = order.getInitAmt().subtract(order.getCurAmt());
				order.setInitAmt(remain);
				order.setCurAmt(new BigDecimal("0.00"));
			}
			
			//更新订单状态为退订
			order.setEndDate(new Date());
			order.setPreorderStat("30");//失效的订单
			order.setSign("30");//标示退订
			tPreOrderMapper.updateOrderEndDate(order);
			
			//订户状态更改???
			List<TPreOrder> list = tPreOrderMapper.selectByMilkmemberNo(order.getMilkmemberNo());
			if(list==null||list.size()<=0){
				tVipCustInfoService.discontinue(order.getMilkmemberNo(), "40",null,null);
			}
			
			//发送EC,更新订单状态
			TPreOrder sendOrder = new TPreOrder();
			sendOrder.setOrderNo(order.getOrderNo());
			sendOrder.setPreorderStat("300");
			sendOrder.setCurAmt("20".equals(state)?leftAmt:order.getCurAmt());
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("updateOrderStatus");
					messLogService.sendOrderStatus(sendOrder);
				}
			});
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前订单不存在");
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: batchContinueOrder
	* @description: 批量续订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#batchContinueOrder(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int batchContinueOrder(OrderSearchModel record)
	{
		if(StringUtils.isNotBlank(record.getOrderNo())){
			List<String> orderList = Arrays.asList(record.getOrderNo().split(","));
			orderList.stream().forEach((e)->{
				if(!"true".equals(record.getContent())){
					record.setOrderNo(e);
					continueOrder(record);
				}else{
					continueOrderAuto(e);
				}
			});
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: continueOrder
	* @description: 订单自动续订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#continueOrder(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int continueOrderAuto(String orderNo)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
		
		if("Y".equals(order.getResumeFlag())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, orderNo+" [订单已经被续订过!]");
		}
		tPreOrderMapper.updateOrderResumed(orderNo);//该订单已经被续订
		
		ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(orderNo);
//		Date orgFirstDate = null; 
//		Map<String,Integer> entryDateMap = new HashMap<String,Integer>();
//		for(TPlanOrderItem e :entries){
//			if(orgFirstDate==null){
//				orgFirstDate = e.getStartDispDate();
//			}else{
//				orgFirstDate = orgFirstDate.before(e.getStartDispDate())?orgFirstDate:e.getStartDispDate();
//			}
//		}
//		for(TPlanOrderItem e :entries){
//			entryDateMap.put(e.getItemNo(), daysOfTwo(orgFirstDate, e.getStartDispDate()));
//		}
		
		if(order!= null){
			if("20".equals(order.getMilkboxStat()))throw new ServiceException(MessageCode.LOGIC_ERROR, orderNo+"原订单还没有装箱，不能续订!");
			if("20".equals(order.getPaymentmethod())&&"10".equals(order.getPaymentStat()))throw new ServiceException(MessageCode.LOGIC_ERROR, orderNo+"原订单为预付款订单，没有付款，不能续订!");
			//基本参考原单
			Date sdate = afterDate(order.getEndDate(),1);
			//新的订单号
			Date date = new Date();
			order.setOrderNo(CodeGeneratorUtil.getCode());
			order.setOrderDate(date);
         order.setPaymentStat("10");//默认未付款
			//生成每个订单行
			List<TPlanOrderItem> entriesList = new ArrayList<TPlanOrderItem>();
			int index = 0;
			BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
			for(TPlanOrderItem entry: entries){
				entry.setOrderNo(order.getOrderNo());
				//设置配送开始时间
				int days = daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate());
				calculateEntryStartDate(entry); 
				Date edate = afterDate(entry.getStartDispDate(), days);
				entry.setEndDispDate(edate);
				
				entry.setItemNo(order.getOrderNo() + String.valueOf(index));//行项目编号
				entry.setRefItemNo(String.valueOf(index));//参考行项目编号
				entry.setOrderDate(date);//订单日期
				entry.setCreateAt(date);//创建日期
				entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
				entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
				BigDecimal entryTotal = calculateEntryAmount(entry);
				
				if("20".equals(order.getPaymentmethod()) && StringUtils.isNotBlank(entry.getPromotion())){
					TPromotion promotion = promotionService.selectPromotionByPromNo(entry.getPromotion());
					if(promotion!=null){
						if(promotion.getPlanStartTime().after(date) || promotion.getPlanStopTime().before(date) ){
							throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单的促销活动已经结束!");
						}
					}
					entry.setGiftMatnr("");
					promotionService.calculateEntryPromotion(entry);
				}
				
				orderAmt = orderAmt.add(entryTotal);
				entriesList.add(entry);

				index++;
			}
			
			//保存订单，订单行
			order.setCurAmt(orderAmt);//订单价格
			order.setInitAmt(orderAmt);
			order.setEndDate(calculateFinalDate(entriesList));//订单截止日期
			tPreOrderMapper.insert(order);
			
			for(TPlanOrderItem entry: entriesList){
				tPlanOrderItemMapper.insert(entry);
			}
			
			//订户状态更改
			tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",null,new com.nhry.utils.date.Date());
			
			//生成每日计划
			List<TOrderDaliyPlanItem> list = createDaliyPlan(order,entriesList);
			
			//创建订单发送EC，发送系统消息(以线程方式),只有奶站的发，摆台的确认时发，电商不发
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("sendOrderToEc");
					messLogService.sendOrderInfo(order, entriesList);
				}
			});
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,orderNo+"原订单不存在");
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: calculateContinueOrder
	* @description: 订单续订截止日期
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#calContinueOrder(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public OrderSearchModel calculateContinueOrder(OrderSearchModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		
		Date startDate = null;
		if(StringUtils.isBlank(record.getOrderDateStart())){
			startDate = afterDate(order.getEndDate(),1);
			record.setOrderDateStart(format.format(startDate));
		}else{
			try
			{
				//续订的起始日期
				startDate = format.parse(record.getOrderDateStart());
			}
			catch (ParseException e)
			{
				record.setContent(null);
				return record;
			}
		}
		
		int goDays = 0;//续订多少天
		if(record.getGoDays()==null){
			Date firstDate = null;
			Date lastDate = null;
			ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrderNo());
			for(TPlanOrderItem e : entries){
				if(firstDate == null){
					firstDate = e.getStartDispDate();
				}else{
					firstDate = e.getStartDispDate().before(firstDate)? e.getStartDispDate():firstDate;
				}
				if(lastDate == null){
					lastDate = e.getEndDispDate();
				}else{
					lastDate = e.getEndDispDate().after(lastDate)? e.getEndDispDate():lastDate;
				}
			}
			goDays = daysOfTwo(firstDate,lastDate);
			record.setGoDays(goDays);
		}else{
			goDays = record.getGoDays();
		}
		
		record.setOrderDateEnd(format.format(afterDate(startDate,goDays)));
		record.setContent(null);

		return record;
	}
	
	/* (non-Javadoc) 
	* @title: continueOrder
	* @description: 订单续订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#continueOrder(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int continueOrder(OrderSearchModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		
		//在批量续订时，预付款的订单自动续订
		if("true".equals(record.getContent()) || ("batch".equals(record.getStatus()) && "20".equals(order.getPaymentmethod())) ){
			continueOrderAuto(order.getOrderNo());
			return 1;
		}
		
		if("Y".equals(order.getResumeFlag())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, order.getOrderNo()+" [订单已经被续订过!]");
		}
		tPreOrderMapper.updateOrderResumed(order.getOrderNo());//该订单已经被续订
		
		ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrderNo());
		Date orgFirstDate = null; 
		Map<String,Integer> entryDateMap = new HashMap<String,Integer>();
		for(TPlanOrderItem e :entries){
			if(orgFirstDate==null){
				orgFirstDate = e.getStartDispDate();
			}else{
				orgFirstDate = orgFirstDate.before(e.getStartDispDate())?orgFirstDate:e.getStartDispDate();
			}
		}
		for(TPlanOrderItem e :entries){
			entryDateMap.put(e.getItemNo(), daysOfTwo(orgFirstDate, e.getStartDispDate()));
		}
		
		if(order!= null){
			if("20".equals(order.getMilkboxStat()))throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"原订单还没有装箱，不能续订!");
			if("20".equals(order.getPaymentmethod())&&"10".equals(order.getPaymentStat()))throw new ServiceException(MessageCode.LOGIC_ERROR, "原订单为预付款订单，没有付款，不能续订!");
			Date sdate = null;
			Date edate = null;
			try
			{
				sdate = format.parse(record.getOrderDateStart());
				edate = format.parse(record.getOrderDateEnd());
			}
			catch (ParseException e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式不正确!");
			}
			
			if(edate.before(sdate))throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"续订截止日期不能小于原订单截止日期!");
			if(sdate.before(order.getEndDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"续订日期不能小于原订单截止日期!"+order.getEndDate());
//			String state = order.getPaymentmethod();
			
			//基本参考原单
//			int goDays = record.getGoDays();
			//新的订单号
			Date date = new Date();
			order.setOrderNo(CodeGeneratorUtil.getCode());
			//
			order.setOrderDate(date);
//         if("20".equals(state)){
         order.setPaymentStat("10");//默认未付款
//         }
			//生成每个订单行
			List<TPlanOrderItem> entriesList = new ArrayList<TPlanOrderItem>();
			int index = 0;
			BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
			for(TPlanOrderItem entry: entries){
				entry.setOrderNo(order.getOrderNo());
				//设置配送开始时间
				Date startDate = afterDate(sdate,entryDateMap.get(entry.getItemNo()));
//				Date edate = afterDate(startDate,daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate()));
//				Date edate2 = afterDate(startDate,goDays);
				if(edate.before(startDate)){
					continue;//如果需要续订天数不足某一行，这行不需要续订
				}
			   entry.setEndDispDate(edate);
				entry.setStartDispDate(startDate);
				
				entry.setItemNo(order.getOrderNo() + String.valueOf(index));//行项目编号
				entry.setRefItemNo(String.valueOf(index));//参考行项目编号
				entry.setOrderDate(date);//订单日期
				entry.setCreateAt(date);//创建日期
				entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
				entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
				BigDecimal entryTotal = calculateEntryAmount(entry);
				
				//促销清空
				entry.setPromotion("");
				entry.setBuyQty(0);
				entry.setGiftQty(0);
				entry.setGiftMatnr("");
				entry.setGiftUnit("");
//				entry.setDispTotal(0);
				
				orderAmt = orderAmt.add(entryTotal);
				entriesList.add(entry);

				index++;
			}
			//保存订单，订单行
			order.setCurAmt(orderAmt);//订单价格
			order.setInitAmt(orderAmt);
			order.setEndDate(calculateFinalDate(entriesList));//订单截止日期
			tPreOrderMapper.insert(order);
			
			for(TPlanOrderItem entry: entriesList){
				tPlanOrderItemMapper.insert(entry);
			}
			
			//续费的金额存入帐户
//			if(StringUtils.isNotBlank(record.getGoAmt())){
//				BigDecimal remain = new BigDecimal(record.getGoAmt()).subtract(orderAmt);
//				TVipAcct account = new TVipAcct();
//				account.setBranchNo(order.getBranchNo());
//				account.setVipCustNo(order.getMilkmemberNo());
//				account.setAcctAmt(new BigDecimal(record.getGoAmt()));
//				tVipCustInfoService.addVipAcct(account);
//			}
			
			//订户状态更改
			tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",null,new com.nhry.utils.date.Date());
			
			//生成每日计划
			createDaliyPlan(order,entriesList);
			
			//创建订单发送EC，发送系统消息(以线程方式),只有奶站的发，摆台的确认时发，电商不发
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("sendOrderToEc");
					messLogService.sendOrderInfo(order, entriesList);
				}
			});
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"原订单不存在");
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: batchContinueOrdeAfterStop
	* @description: 订单批量复订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#batchContinueOrdeAfterStop(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int batchContinueOrdeAfterStop(OrderSearchModel record)
	{
		if(StringUtils.isNotBlank(record.getOrderNo())){
			List<String> orderList = Arrays.asList(record.getOrderNo().split(","));
			orderList.stream().forEach((e)->{
				record.setOrderNo(e);
				continueOrdeAfterStop(record);
			});
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: continueOrdeAfterStop
	* @description: 订单停订后续订（复订）
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#continueOrdeAfterStop(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int continueOrdeAfterStop(OrderSearchModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		
		if("10".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"在订的订单，不能修改复订!");
		}
		
		BigDecimal orderAmt = order.getInitAmt();//订单金额
		ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrderNo());
		String startDateStr = record.getOrderDateStart();//续订开始日期
		Date startDate = null;
		try
		{
			startDate = format.parse(startDateStr);
			order.setStopDateEnd(startDate);
		}
		catch (ParseException e)
		{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式有误!");
		}
		Date today = new Date();
		if(!startDate.after(order.getStopDateStart())){
			//不修改，把路单置回原样
			TOrderDaliyPlanItem uptKey= new TOrderDaliyPlanItem(); 
			uptKey.setOrderNo(order.getOrderNo());
			uptKey.setDispDateStr(startDateStr);
			tOrderDaliyPlanItemMapper.updateFromDateToDate(uptKey);
			
			order.setSign("10");//标示在订
			tPreOrderMapper.updateOrderEndDate(order);
			
			//订户状态更改
			tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",null,null);
			
			//发送EC
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("resumeOrder");
					record.setOrderDateStart(startDateStr);
					record.setContent("Y");
					messLogService.sendOrderStopRe(record);
				}
			});
			
			return 1;
		}
		//后付款的
		if(!"20".equals(order.getPaymentmethod())){
			//把后期路单置回原样,重新计算金额
			TOrderDaliyPlanItem uptKey= new TOrderDaliyPlanItem(); 
			uptKey.setOrderNo(order.getOrderNo());
			uptKey.setDispDateStr(startDateStr);
			tOrderDaliyPlanItemMapper.updateFromDateToDate(uptKey);
			
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(record.getOrderNo());

			for(TOrderDaliyPlanItem p :daliyPlans){
				if("30".equals(p.getStatus()) && !p.getDispDate().before(order.getStopDateStart())){
					BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
					orderAmt = orderAmt.subtract(planAmt);
				}
			}
			
			order.setCurAmt(orderAmt.subtract(order.getInitAmt().subtract(order.getCurAmt())));
			order.setInitAmt(orderAmt);
			
			for(TOrderDaliyPlanItem p :daliyPlans){
				if("10".equals(p.getStatus())){
					BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
					orderAmt = orderAmt.subtract(planAmt);
					p.setRemainAmt(orderAmt);
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
				}else{
					p.setRemainAmt(orderAmt);
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
				}
			}
			
		}else{
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(record.getOrderNo());
				
				//删除从复订时间开始的日计划
   			TOrderDaliyPlanItem deleteKey= new TOrderDaliyPlanItem(); 
   			deleteKey.setOrderNo(order.getOrderNo());
   			deleteKey.setDispDateStr(startDateStr);
   			tOrderDaliyPlanItemMapper.deleteFromDateToDate(deleteKey);
   			
   			Map<String,Integer> qtyMap = new HashMap<String,Integer>();
				for(TOrderDaliyPlanItem p: daliyPlans){
					if(p.getGiftQty()!=null)continue;
					String itemNo = p.getItemNo();
					if(!"30".equals(p.getStatus()) && p.getDispDate().before(startDate) && p.getGiftQty()==null){
						BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
						orderAmt = orderAmt.subtract(planAmt);
					}
					if(!p.getDispDate().before(order.getStopDateStart()) && "30".equals(p.getStatus())){
						if(qtyMap.containsKey(itemNo)){
							qtyMap.replace(itemNo, 1 + qtyMap.get(itemNo));
						}else{
							qtyMap.put(itemNo, 1);
						}
					}
				}
				
   			List<TOrderDaliyPlanItem> list = createDaliyPlanForResumeOrder(order , orgEntries , orderAmt , startDate , qtyMap);
   			
   			//回改订单行项目,更新最后配送日期
   			for( TPlanOrderItem entry :orgEntries){
   				for(TOrderDaliyPlanItem p:list){
   					if(entry.getItemNo().equals(p.getItemNo())){
   						entry.setEndDispDate(p.getDispDate());
   						promotionService.calculateEntryPromotionForStop(entry);
   						//保存修改后的该行
   						tPlanOrderItemMapper.updateEntryByItemNo(entry);
   						break;
   					}
   				}
   			}
   			
   			//如果有赠品，生成赠品的日计划
   			promotionService.createDaliyPlanByPromotion(order,orgEntries,list);
   			
   			//更新订单
   			order.setEndDate(calculateFinalDate(orgEntries));//订单截止日期修改
		}

		order.setSign("10");//标示在订
		tPreOrderMapper.updateOrderEndDate(order);
		
		//订户状态更改
		tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",null,null);
		
		//发送EC
		taskExecutor.execute(new Thread(){
			@Override
			public void run() {
				super.run();
				this.setName("resumeOrder");
				record.setOrderDateStart(startDateStr);
				record.setContent("Y");
				messLogService.sendOrderStopRe(record);
			}
		});
		
		return 1;
	}

	/**
	 * 判断当前订单能否退订
	 * @param orderNo
	 * @return
	 */
	@Override
	public int canOrderUnsubscribe(String orderNo) {
		OrderCreateModel orderModel = this.selectOrderByCode(orderNo);
		if(orderModel!= null){
			TPreOrder order = orderModel.getOrder();
			String paymentStat = order.getPaymentStat();

			//判断当前日订单状态：如果为"已创建"  可直接修改
			String dayOrderStat = tOrderDaliyPlanItemMapper.getDayOrderStat(orderNo,new Date());
			if("10".equals(dayOrderStat)){
				return 1;
			}else if("20".equals(dayOrderStat)){
				//当前订单状态为："已确认",不能修改
				return 2;
			}else{
				//当前日订单状态为已退订
				return 3;
			}
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前订单不存在");
		}

	}



	/* (non-Javadoc)
	* @title: 查询订单列表
	* @description:
	* @return
	* @see com.nhry.service.order.dao.OrderService#searchOrders()
	*/
	@Override
	public PageInfo searchOrders(OrderSearchModel smodel)
	{
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());
		return tPreOrderMapper.selectOrdersByPages(smodel);
	}

	/* (non-Javadoc) 
	* @title: searchDaliyOrders
	* @description: 分页查询日计划
	* @param smodel
	* @return 
	* @see com.nhry.service.order.dao.OrderService#searchDaliyOrders(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public PageInfo searchDaliyOrders(OrderSearchModel smodel)
	{	
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(smodel.getOrderNo());
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		PageInfo pages = tOrderDaliyPlanItemMapper.selectDaliyOrdersByPages(smodel);
		//后付款显示负数金额
		if("10".equals(orgOrder.getPaymentmethod())){
			for(Object e : pages.getList()){
				TOrderDaliyPlanItem p = ((TOrderDaliyPlanItem)e);
				p.setRemainAmt(p.getRemainAmt().subtract(orgOrder.getInitAmt()));
			}
		}
	
		return pages;
	}
	
	/* (non-Javadoc)
	* @title: 生成订单
	* @description: 生成订单与订单行项目
	* @return
	* @see com.nhry.service.order.dao.OrderService#createOrder()
	*/
	@Override
	public String createOrder(OrderCreateModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = record.getOrder();
		List<TPlanOrderItem> entriesList = new ArrayList<TPlanOrderItem>();
		//信息交验
		validateOrderInfo(record);
		//暂时生成订单号
		Date date = new Date();
		order.setOrderNo(CodeGeneratorUtil.getCode());
		//其他订单信息
		order.setOrderDate(date);//订单创建日期

		order.setCreaterBy(userSessionService.getCurrentUser().getLoginName());//创建人
		order.setCreaterNo(userSessionService.getCurrentUser().getGroupId());//创建人编号
		if(StringUtils.isBlank(order.getOrderType())){
			order.setOrderType("20");//订单类型 页面都是线下
		}
		if(StringUtils.isBlank(order.getPreorderSource())){
			order.setPreorderSource("30");//订单来源  页面中来源都是30（奶站）
		}
//		order.setMilkmemberNo(milkmemberNo);//喝奶人编号
//		order.setEmpNo(empNo);//送奶员编号
//		order.setInitAmt(initAmt);//页面输入的初始订单金额
		order.setPaymentmethod(order.getPaymentStat());//10 后款 20 先款 30 殿付款
		order.setPaymentStat("10");//付款状态,生成时未付款
		order.setMilkboxStat(StringUtils.isBlank(order.getMilkboxStat()) == true ? "20": order.getMilkboxStat());//奶箱状态
		if(StringUtils.isBlank(order.getPreorderStat())){
			order.setPreorderStat("10");//订单状态,初始确认
		}
		order.setSign("10");//在订状态
		//根据传的奶站获取经销商和销售组织
		if(StringUtils.isNotBlank(order.getBranchNo())){
			TMdBranch branch = branchMapper.selectBranchByNo(order.getBranchNo());
			if(branch==null)throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站不存在!");
			order.setDealerNo(branch.getDealerNo());//进销商
			order.setSalesOrg(branch.getSalesOrg());
		}
		//order.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());//销售组织


		//征订日期
		if(StringUtils.isNotBlank(order.getSolicitDateStr())){
			try
			{
				order.setSolicitDate(format.parse(order.getSolicitDateStr()));
			}
			catch (Exception e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式有误!");
			}
		}

		//如果地址信息不为空，为订户创建新的地址
		if(record.getAddress() != null && "1".equals(record.getAddress().getAddressMode())){
			if("30".equals(order.getPreorderSource()) ){
				record.getAddress().setVipCustNo(order.getMilkmemberNo());
				record.getAddress().setRecvName(order.getMilkmemberName());
				order.setAdressNo(tVipCustInfoService.addAddressForCust(record.getAddress(),null,null).split(",")[1]);
			}else{
				Map<String,String> map = new HashMap<String,String>();
				map.put("activityNo",order.getSolicitNo());
				map.put("vipType",order.getDeliveryType());
				map.put("vipSrc",order.getPreorderSource());
				String addressAndMilkmember = tVipCustInfoService.addAddressForCust(record.getAddress(),order.getBranchNo(),map);
				order.setMilkmemberNo(addressAndMilkmember.split(",")[0]);
				order.setAdressNo(addressAndMilkmember.split(",")[1]);
			}
		}

		//生成每个订单行
		int index = 0;
		BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
		for(TPlanOrderItem entry: record.getEntries()){
			entry.setOrderNo(order.getOrderNo());
			entry.setItemNo(order.getOrderNo() + String.valueOf(index));//行项目编号
			entry.setRefItemNo(String.valueOf(index));//参考行项目编号
			entry.setOrderDate(date);//订单日期
			entry.setCreateAt(date);//创建日期
			entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
			entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
			try
			{
				entry.setStartDispDate(format.parse(entry.getStartDispDateStr()));
				if("20".equals(order.getPaymentmethod())){
					resolveEntryEndDispDate(entry);
				}else{
					entry.setEndDispDate(format.parse(entry.getEndDispDateStr()));
				}
			}
			catch (ServiceException e1){
				throw e1;
			}
			catch (Exception e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式有误");
			}
			orderAmt = orderAmt.add(calculateEntryAmount(entry));
			
			//促销判断
			if(StringUtils.isNotBlank(entry.getPromotion())&&"10".equals(order.getPaymentmethod()))throw new ServiceException(MessageCode.LOGIC_ERROR,"后付款的订单不能参加促销!");
			promotionService.calculateEntryPromotion(entry);
			
			entriesList.add(entry);

			index++;
		}

		//订单价格
		order.setCurAmt(orderAmt);
		
		//此为多余的钱，如果是预付款，将存入订户账户???
//		if(order.getInitAmt()!=null){
//			if("20".equals(order.getPaymentmethod()) && order.getInitAmt().subtract(orderAmt).floatValue()<0)throw new ServiceException(MessageCode.LOGIC_ERROR,"付款方式为预付款!您支付的金额不足!");
//			BigDecimal remain = order.getInitAmt().subtract(order.getCurAmt());
//			if(record.getAccount() != null && "20".equals(order.getPaymentmethod())){
//				if(StringUtils.isBlank(record.getAccount().getBranchNo()))record.getAccount().setBranchNo(order.getBranchNo());
//				if(StringUtils.isBlank(record.getAccount().getVipCustNo()))record.getAccount().setVipCustNo(order.getMilkmemberNo());
//				record.getAccount().setAcctAmt(remain);
//				tVipCustInfoService.addVipAcct(record.getAccount());
//			}
//		}
		
		order.setEndDate(calculateFinalDate(entriesList));//订单截止日期
		order.setInitAmt(orderAmt);
		
		//保存订单和行项目
		tPreOrderMapper.insert(record.getOrder());
		entriesList.forEach(entry->{
			tPlanOrderItemMapper.insert(entry);
		});
		
		//非奶站订单直接返回
		if(!"30".equals(order.getPreorderSource())){
			return order.getOrderNo();
		}
		
		//订户状态更改
		tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",new com.nhry.utils.date.Date(),new com.nhry.utils.date.Date());
			
		//生成装箱工单
		if("20".equals(order.getMilkboxStat())){
			MilkboxCreateModel model = new MilkboxCreateModel();
			model.setCode(order.getOrderNo());
			milkBoxService.addNewMilkboxPlan(model);
		}
		
		//生成每日计划
		List<TOrderDaliyPlanItem> list = createDaliyPlan(order,record.getEntries());
		
		//如果有赠品，生成赠品的日计划
		promotionService.createDaliyPlanByPromotion(order,entriesList,list);
		
		//创建订单发送EC，发送系统消息(以线程方式),只有奶站的发，摆台的确认时发，电商不发
		taskExecutor.execute(new Thread(){
			@Override
			public void run() {
				super.run();
				this.setName("sendOrderToEc");
				messLogService.sendOrderInfo(order, entriesList);
				
				if("10".equals(order.getPaymentmethod()) && !"20".equals(order.getMilkboxStat())){
					TPreOrder sendOrder = new TPreOrder();
					sendOrder.setOrderNo(order.getOrderNo());
					sendOrder.setPreorderStat("200");
					messLogService.sendOrderStatus(sendOrder);
				}
			}
		});
		
		return order.getOrderNo();
	}

	//根据订单行生成每日计划
	@Override
	public List<TOrderDaliyPlanItem> createDaliyPlan(TPreOrder order ,List<TPlanOrderItem> entries){

		//预付款的要付款+装箱才生成日计划
		if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getPaymentStat())){
			return null;
		}
		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
		if("20".equals(order.getMilkboxStat())){
			return null;
		}

		List<TOrderDaliyPlanItem> daliyPlans = new ArrayList<TOrderDaliyPlanItem>();
		BigDecimal curAmt = order.getCurAmt();

		//计算每个行项目总共需要送多少天
		Map<TPlanOrderItem,Integer> entryMap = new HashMap<TPlanOrderItem,Integer>();
		int maxEntryDay = 365;
		Date firstDeliveryDate = null;
		for(TPlanOrderItem entry: entries){
			if(firstDeliveryDate==null){
				firstDeliveryDate = entry.getStartDispDate();
			}else{
				firstDeliveryDate = firstDeliveryDate.before(entry.getStartDispDate())?firstDeliveryDate:entry.getStartDispDate();
			}
			int entryDays = (daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate())) + 1;
			entryMap.put(entry,entryDays);
//			maxEntryDay = maxEntryDay > entryDays ? maxEntryDay : entryDays;
		}

		//根据最大配送天数的行
		int afterDays = 0;//经过的天数
		//行号唯一，需要判断以前最大的行号
		int daliyEntryNo = 0;//日计划行号
		try{
			daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(order.getOrderNo()) + 1;
		}catch(Exception e){
			//如果找不到最大值
		}
		
		outer:for(int i = 0; i < maxEntryDay; i++){
			for (TPlanOrderItem entry : entryMap.keySet()) {
				int days = entryMap.get(entry);
				if(days - 1 >= 0){
					//判断是按周期送还是按星期送
					Date today = afterDate(firstDeliveryDate,afterDays);
					
					if(entry.getStartDispDate().after(today))continue;
					
					entryMap.replace(entry, days-1);//剩余天数减1天
					
					if("10".equals(entry.getRuleType())){
						int gapDays = entry.getGapDays() + 1;//间隔天数
						if(daysOfTwo(entry.getStartDispDate(),today)%gapDays != 0){
							if(entry.getRuleTxt()!=null){
								List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
								if(deliverDays.size() > 0){//判断周6，7是否配送
									String weekday = getWeek(today);
									if(!deliverDays.contains(weekday)){
										continue;
									}
								}
							}else{
								continue;
							}
						}
					}
					else if("20".equals(entry.getRuleType())){
						String weekday = getWeek(today);
						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
						if(!deliverDays.contains(weekday)){
							continue;//如果选择的星期几不送，则跳过今天生成日计划
						}
					}

					//生成该订单行的每日计划
					TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
					plan.setOrderNo(entry.getOrderNo());//订单编号
					plan.setOrderDate(entry.getOrderDate());//订单日期
					plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
					plan.setItemNo(entry.getItemNo());//预订单日计划行
					plan.setDispDate(today);//配送日期
//					plan.setReachTime(entry.getReachTime());//送达时段
					plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
					plan.setMatnr(entry.getMatnr());//产品编号
					plan.setUnit(entry.getUnit());//配送单位
					plan.setQty(entry.getQty());//产品数量
					plan.setPrice(entry.getSalesPrice());//产品价格
					plan.setPromotionFlag(entry.getPromotion());//促销号
					//日计划行金额和
					BigDecimal qty = new BigDecimal(entry.getQty().toString());
					plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
					curAmt = curAmt.subtract(plan.getAmt());
					
					//当订单余额小于0时停止
					if(curAmt.floatValue() < 0)break outer;
					
					plan.setRemainAmt(curAmt);//订单余额
					plan.setStatus("10");//状态
					plan.setCreateAt(new Date());//创建时间
					plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
					plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
					
					tOrderDaliyPlanItemMapper.insert(plan);
					daliyEntryNo++;
					
					daliyPlans.add(0,plan);
				}else{
					continue;
				}
			}
			afterDays++;
		}

		return daliyPlans;
	}

	/* (non-Javadoc)
	 * @title: modifyOrderStatus
	 * @description: 修改订单状态   订单，付款，奶箱状态
	 * @param state
	 * @param status
	 * @return
	 * @see com.nhry.service.order.dao.OrderService#modifyOrderStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public int modifyOrderStatus(TPreOrder record)
	{
		//未收款10、已收款20、垫付款30 paymentstat
		//已装箱10、未装箱20、无需装箱30 milkboxstat
		//已生效10、未生效20、无效30,作废40 preorderstat
		//先款10、后付款20  paymentmethod
		//在订10、停订20、退订30 sign
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		if(order == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单号不存在!");
		}
//		if(record.getMilkmemberNo()!=null){
//			//更改订户
//			tPreOrderMapper.updateOrderStatus(record);
//			return 1;
//		}
		if(StringUtils.isNotBlank(record.getEmpNo())){
			//更改送奶工
			tPreOrderMapper.updateOrderEmpNo(record);
			return 1;
		}
		if(StringUtils.isBlank(record.getPaymentStat()) && StringUtils.isBlank(record.getMilkboxStat())
				&& StringUtils.isBlank(record.getPreorderStat()) && StringUtils.isBlank(record.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"更新信息与状态为空!");
		}
		if("10".equals(record.getPreorderStat())){
			if(StringUtils.isBlank(order.getMilkmemberNo()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单没有订户，请选择订户或新建订户!");
			if(StringUtils.isBlank(order.getEmpNo()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单没有送奶员，请选择送奶员!");
			//订户状态更改
			tPreOrderMapper.updateOrderStatus(record);
			tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",new com.nhry.utils.date.Date(),new com.nhry.utils.date.Date());
			
			//电商或者摆台的订单确认后，走线下逻辑,生成装箱工单
			ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(order.getOrderNo());
			if("20".equals(order.getMilkboxStat())){
				MilkboxCreateModel model = new MilkboxCreateModel();
				model.setCode(order.getOrderNo());
				milkBoxService.addNewMilkboxPlan(model);
				
   			//生成每日计划
   			List<TOrderDaliyPlanItem> list = createDaliyPlan(order,orgEntries);
   			//如果有赠品，生成赠品的日计划
   			promotionService.createDaliyPlanByPromotion(order,orgEntries,list);
			}
			
			//创建订单发送EC，发送系统消息(以线程方式),只有奶站的发，摆台的确认时发，电商不发
			if("20".equals(order.getPreorderSource())){
				taskExecutor.execute(new Thread(){
					@Override
					public void run() {
						super.run();
						this.setName("sendOrderToEc");
						messLogService.sendOrderInfo(order, orgEntries);
					}
				});
			}
			
			return 1;
		}
		//作废
		if("40".equals(record.getPreorderStat())){
			//当非奶站订单时，作废待确认订单时
			List<TPreOrder> list = tPreOrderMapper.selectNodeletedByMilkmemberNo(order);
			if(list == null || list.size() <= 0 ){
				tVipCustInfoService.deleteCustByCustNo(order.getMilkmemberNo());
			}
			tPreOrderMapper.updateOrderStatus(record);
			
			return 1;
		}


		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: editOrderForLong
	* @description: 长期修改订单
	* @param record
	* @return
	* @see com.nhry.service.order.dao.OrderService#editOrder(com.nhry.model.order.OrderCreateModel)
	*/
	@Override
	public int editOrderForLong(OrderEditModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(record.getOrder().getOrderNo());
		ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrder().getOrderNo());
		ArrayList<TPlanOrderItem> curEntries = record.getEntries();
		ArrayList<TPlanOrderItem> modifiedEntries = new ArrayList<TPlanOrderItem>();
		
		String state = orgOrder.getPaymentmethod();
		if("10".equals(state)){
			//后付款
			//修改订单根据行项目编号来确定行是否修改，换商品或改数量
			BigDecimal orderUsedAmt = orgOrder.getInitAmt().subtract(orgOrder.getCurAmt());
			BigDecimal orderAmt = new BigDecimal("0.00").add(orderUsedAmt);
			for(TPlanOrderItem orgEntry : orgEntries){
				boolean delFlag = true;
				boolean modiFlag = false;
				for(TPlanOrderItem curEntry : curEntries){
					if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
						delFlag = false;
						if(!orgEntry.getMatnr().equals(curEntry.getMatnr())){//换商品
							modiFlag = true;
							orgEntry.setMatnr(curEntry.getMatnr());
							orgEntry.setSalesPrice(curEntry.getSalesPrice());
							orgEntry.setUnit(curEntry.getUnit());
						}
						if(orgEntry.getQty() != curEntry.getQty()){//改数量
							modiFlag = true;
							orgEntry.setQty(curEntry.getQty());
						}
						if(!orgEntry.getRuleType().equals(curEntry.getRuleType())){//周期变更
							modiFlag = true;
							orgEntry.setRuleType(curEntry.getRuleType());
							orgEntry.setGapDays(curEntry.getGapDays());
							orgEntry.setRuleTxt(curEntry.getRuleTxt());
						}else{
							//相同时判断是周期送还是星期送
							if("10".equals(orgEntry.getRuleType()) && (!curEntry.getGapDays().equals(orgEntry.getGapDays()) ) ){
								modiFlag = true;
								orgEntry.setGapDays(curEntry.getGapDays());
								orgEntry.setRuleTxt(curEntry.getRuleTxt());
							}else if("20".equals(orgEntry.getRuleType()) && !curEntry.getRuleTxt().equals(orgEntry.getRuleTxt()) ){
								modiFlag = true;
								orgEntry.setRuleTxt(curEntry.getRuleTxt());
							}
						}
						if(!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())){//送奶时段变更
							modiFlag = true;
							orgEntry.setReachTimeType(curEntry.getReachTimeType());
						}
						//比较配送日期是否修改
						String startstr = format.format(orgEntry.getStartDispDate());
						String endstr = format.format(orgEntry.getEndDispDate());
						if(!startstr.equals(format.format(curEntry.getStartDispDate())) || !endstr.equals(format.format(curEntry.getEndDispDate())) ){
							modiFlag = true;
							orgEntry.setStartDispDate(curEntry.getStartDispDate());
							orgEntry.setEndDispDate(curEntry.getEndDispDate());
						}
						
						orderAmt = orderAmt.add(calculateEntryAmount(orgEntry));//订单总金额需要重新计算
						if(!modiFlag){
							break;
						}
						if(StringUtils.isNotBlank(orgEntry.getPromotion()))throw new ServiceException(MessageCode.LOGIC_ERROR,"促销商品行不能更改!");
						
						//保存修改后的该行
						tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
						//删除不需要的日单
						TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
						newPlan.setOrderNo(orgOrder.getOrderNo());
						newPlan.setItemNo(orgEntry.getItemNo());
						newPlan.setStatus("10");
						newPlan.setDispDateStr(startstr);
						tOrderDaliyPlanItemMapper.deleteFromDateToDate(newPlan);
						//行修改完毕
						modifiedEntries.add(orgEntry);
						break;
					}
				}
				if(delFlag){
					orgEntries.remove(orgEntry);
					//此行删除了，删除所有剩余的日单
					orgEntry.setStatus("30");//30表示删除的行
					tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
					TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
					newPlan.setOrderNo(orgOrder.getOrderNo());
					newPlan.setItemNo(orgEntry.getItemNo());
					newPlan.setStatus("10");
					newPlan.setDispDateStr(format.format(orgEntry.getStartDispDate()));//关于从什么地方删除日单??
					tOrderDaliyPlanItemMapper.deleteFromDateToDate(newPlan);
				}
				
			}
			//更新订单
   		orgOrder.setInitAmt(orderAmt);
   		orgOrder.setCurAmt(orderAmt.subtract(orderUsedAmt));
   		
			//生成新的每日订单
			createDaliyPlan(orgOrder , modifiedEntries);
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
			calculateDaliyPlanRemainAmt(orgOrder,daliyPlans);
			
			//订单截止日期修改
			orgOrder.setEndDate(calculateFinalDate(orgEntries));
			tPreOrderMapper.updateOrderEndDate(orgOrder);
			
		}else{
			//先付款,订单总金额不变,配送到金额为0为止
			//修改订单根据行项目编号来确定行是否修改，换商品或改数量
			BigDecimal orderLeftAmt = orgOrder.getCurAmt();
			for(TPlanOrderItem orgEntry : orgEntries){
				boolean delFlag = true;
				boolean modiFlag = false;
				for(TPlanOrderItem curEntry : curEntries){
					if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
						delFlag = false;
						if(!orgEntry.getMatnr().equals(curEntry.getMatnr())){//换商品
							modiFlag = true;
							orgEntry.setMatnr(curEntry.getMatnr());
							orgEntry.setSalesPrice(curEntry.getSalesPrice());
							orgEntry.setUnit(curEntry.getUnit());
						}
						if(orgEntry.getQty() != curEntry.getQty()){//改数量
							modiFlag = true;
							orgEntry.setQty(curEntry.getQty());
						}
						if(!orgEntry.getRuleType().equals(curEntry.getRuleType())){//周期变更
							modiFlag = true;
							orgEntry.setRuleType(curEntry.getRuleType());
							orgEntry.setGapDays(curEntry.getGapDays());
							orgEntry.setRuleTxt(curEntry.getRuleTxt());
						}else{
							//相同时判断是周期送还是星期送
							if("10".equals(orgEntry.getRuleType()) && (!curEntry.getGapDays().equals(orgEntry.getGapDays()) ) ){
								modiFlag = true;
								orgEntry.setGapDays(curEntry.getGapDays());
								orgEntry.setRuleTxt(curEntry.getRuleTxt());
							}else if("20".equals(orgEntry.getRuleType()) && !curEntry.getRuleTxt().equals(orgEntry.getRuleTxt()) ){
								modiFlag = true;
								orgEntry.setRuleTxt(curEntry.getRuleTxt());
							}
						}
						if(!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())){//送奶时段变更
							modiFlag = true;
							orgEntry.setReachTimeType(curEntry.getReachTimeType());
						}
						//比较配送日期是否修改
						String startstr = format.format(orgEntry.getStartDispDate());
						String endstr = format.format(orgEntry.getEndDispDate());
						if(!startstr.equals(format.format(curEntry.getStartDispDate())) || !endstr.equals(format.format(curEntry.getEndDispDate()))){
							modiFlag = true;
							orgEntry.setStartDispDate(curEntry.getStartDispDate());
							orgEntry.setEndDispDate(curEntry.getEndDispDate());
						}
						
						if(!modiFlag){
							break;
						}
						if(StringUtils.isNotBlank(orgEntry.getPromotion()))throw new ServiceException(MessageCode.LOGIC_ERROR,"促销商品行不能更改!");
						
						//保存修改后的该行
//						tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
						//删除不需要的日单
						TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
						newPlan.setOrderNo(orgOrder.getOrderNo());
						newPlan.setItemNo(orgEntry.getItemNo());
						newPlan.setStatus("10");
						newPlan.setDispDateStr(startstr);
						tOrderDaliyPlanItemMapper.deleteFromDateToDate(newPlan);
						//行修改完毕
						modifiedEntries.add(orgEntry);
						break;
					}
				}
				if(delFlag){
					//此行删除了，删除所有剩余的日单
					orgEntries.remove(orgEntry);
					orgEntry.setStatus("30");//30表示删除的行
					tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
					TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
					newPlan.setOrderNo(orgOrder.getOrderNo());
					newPlan.setItemNo(orgEntry.getItemNo());
					newPlan.setStatus("10");
					newPlan.setDispDateStr(format.format(orgEntry.getStartDispDate()));//关于从什么地方删除日单??
					tOrderDaliyPlanItemMapper.deleteFromDateToDate(newPlan);
				}
				
			}
			
			//生成新的每日订单
   		createDaliyPlanForLongEdit(orgOrder , modifiedEntries ,orgEntries);
   		
   		//保存修改后的该行
   		orgEntries.stream().forEach((e)->{tPlanOrderItemMapper.updateEntryByItemNo(e);});
			
			//订单截止日期修改
			orgOrder.setEndDate(calculateFinalDate(orgEntries));
			tPreOrderMapper.updateOrderEndDate(orgOrder);
			
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: editOrderForShort
	* @description: 短期修改订单（改日计划）
	* @param record 前端传回修改过的,停的
	* @return 
	* @see com.nhry.service.order.dao.OrderService#editOrderForShort(com.nhry.model.order.OrderEditModel) 
	*/
	@Override
	public int editOrderForShort(DaliyPlanEditModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(record.getOrderCode());
		ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrderCode());
		ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(record.getOrderCode());
		Map<String,TOrderDaliyPlanItem> daliyPlanMap = new HashMap<String,TOrderDaliyPlanItem>();
		for(TOrderDaliyPlanItem plan : daliyPlans){
			daliyPlanMap.put(plan.getItemNo()+"/"+plan.getPlanItemNo(), plan);
		}
		Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> stopPlans = new HashMap<TOrderDaliyPlanItem,TOrderDaliyPlanItem>();
		Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> changeProductPlans = new HashMap<TOrderDaliyPlanItem,TOrderDaliyPlanItem>();
		Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> changeQtyPlans = new HashMap<TOrderDaliyPlanItem,TOrderDaliyPlanItem>();
		//长期变更(10)需对订单进行统一更改，关联更改日订单  和 短期变更(20)短期变更对订户日订单进行修改
		//后付款的,日订单不往后延
		if("10".equals(orgOrder.getPaymentmethod()) ){
			for(TOrderDaliyPlanItem plan : record.getEntries()){
//				plan.getMatnr();//校验商品
				BigDecimal cj = new BigDecimal("0.00");//差价
				TOrderDaliyPlanItem orgPlan = daliyPlanMap.get(plan.getItemNo()+"/"+plan.getPlanItemNo());
				if(tDispOrderItemMapper.selectItemsByOrgOrderAndItemNo(orgPlan.getOrderNo(), orgPlan.getItemNo(), orgPlan.getDispDate()).size()>0){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该日计划已经生成路单，不可以修改!");
				}
				if("30".equals(orgPlan.getStatus())){//如果已停订，跳过
					continue;
				}
				if("30".equals(plan.getStatus())){
					orgPlan.setStatus(plan.getStatus());
					cj = cj.subtract(orgPlan.getAmt());
				}
				//送达时段
				if(StringUtils.isNotBlank(plan.getReachTimeType())){
					orgPlan.setReachTimeType(plan.getReachTimeType());
				}
				if(!"30".equals(plan.getStatus())){
   				if(!orgPlan.getMatnr().equals(plan.getMatnr())){
   					float price = priceService.getMaraPrice(orgOrder.getBranchNo(), plan.getMatnr(), orgOrder.getDeliveryType());
   					if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"替换的产品价格小于0，请维护价格组!");
   					cj = orgPlan.getAmt().subtract(new BigDecimal(String.valueOf(price)).multiply(new BigDecimal(orgPlan.getQty().toString())));
   					orgPlan.setPrice(new BigDecimal(String.valueOf(price)));
   				}
   				orgPlan.setMatnr(plan.getMatnr());
   				orgPlan.setQty(plan.getQty());
   				orgPlan.setUnit(plan.getUnit());
   				orgPlan.setAmt(orgPlan.getPrice().multiply(new BigDecimal(orgPlan.getQty().toString())));
				}
				orgPlan.setLastModified(new Date());
				orgPlan.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
				orgPlan.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
//				tOrderDaliyPlanItemMapper.updateDaliyPlanItem(orgPlan);
				
				//重新计算日计划的剩余金额,更新订单金额
				orgOrder.setInitAmt(orgOrder.getInitAmt().add(cj));
				orgOrder.setCurAmt(orgOrder.getCurAmt().add(cj));
				tPreOrderMapper.updateOrderInitAmtAndCurAmt(orgOrder);
				
				calculateDaliyPlanRemainAmt(orgOrder,daliyPlans);
			}
			
		//先付款的	,日订单往后延
		}else if("20".equals(orgOrder.getPaymentmethod()) ){
			for(TOrderDaliyPlanItem plan : record.getEntries()){
//				plan.getMatnr();//校验商品
				TOrderDaliyPlanItem orgPlan = daliyPlanMap.get(plan.getItemNo()+"/"+plan.getPlanItemNo());
				if(tDispOrderItemMapper.selectItemsByOrgOrderAndItemNo(orgPlan.getOrderNo(), orgPlan.getItemNo(), orgPlan.getDispDate()).size()>0){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该日计划已经生成路单，不可以修改!");
				}
				if("30".equals(orgPlan.getStatus())){//如果已停订，跳过
					continue;
				}
				if("30".equals(plan.getStatus())){
					stopPlans.put(orgPlan, plan);
					continue;
				}
				//送达时段
				if(StringUtils.isNotBlank(plan.getReachTimeType())){
					orgPlan.setReachTimeType(plan.getReachTimeType());
				}
				//变更产品
				if(!orgPlan.getMatnr().equals(plan.getMatnr())){
					float price = priceService.getMaraPrice(orgOrder.getBranchNo(), plan.getMatnr(), orgOrder.getDeliveryType());
					if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"替换的产品价格小于0，请维护价格组!");
					if(StringUtils.isNotBlank(orgPlan.getPromotionFlag())&&orgPlan.getPrice().floatValue()!=price)throw new ServiceException(MessageCode.LOGIC_ERROR,"有促销的产品只能替换价格相同的产品!");
					plan.setPrice(new BigDecimal(String.valueOf(price)));
					changeProductPlans.put(orgPlan,plan);
				}
				//变更数量
				else if(orgPlan.getQty()!=plan.getQty()){
					changeQtyPlans.put(orgPlan,plan);
				}
			}
			//换商品或换数量要处理或停订
			changeProductOrQty(orgOrder,orgEntries,daliyPlans,changeProductPlans,changeQtyPlans,stopPlans);
			
		}
			
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: resumeDaliyPlanForRouteOrder
	* @description: 路单回执后，会关联修改日计划
	* @param orderCode 
	* @see com.nhry.service.order.dao.OrderService#resumeDaliyPlanForRouteOrder(java.lang.String) 
	*/
	@Override
	public void resumeDaliyPlanForRouteOrder(BigDecimal confirmQty, TDispOrderItem entry,TPlanOrderItem orgEntry,Date dispDate)
	{  //TODO
		if("Y".equals(entry.getGiftFlag())){
			//赠品的，只更新原日计划
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(orgEntry.getOrderNo());
			for(TOrderDaliyPlanItem p : daliyPlans){
				if(p.getDispDate().equals(dispDate) && entry.getOrgItemNo().equals(p.getItemNo()) && p.getGiftQty()!= null){
					p.setMatnr(entry.getConfirmMatnr());
					p.setQty(entry.getConfirmQty().intValue());
					p.setStatus("20");
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
					break;
				}
			}
			return;
		}
		
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(orgEntry.getOrderNo());
		
		if("10".equals(entry.getReason())){
			//换货的，只更新原日计划
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(orgEntry.getOrderNo());
			for(TOrderDaliyPlanItem p : daliyPlans){
				if(p.getDispDate().equals(dispDate) && entry.getOrgItemNo().equals(p.getItemNo()) && p.getGiftQty()==null){
					p.setMatnr(entry.getConfirmMatnr());
					p.setQty(entry.getConfirmQty().intValue());
					p.setStatus("20");
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
					break;
				}
			}
			
			if("10".equals(orgOrder.getPaymentmethod())){
				orgOrder.setCurAmt(orgOrder.getCurAmt().subtract(entry.getAmt()));
				tPreOrderMapper.updateOrderCurAmt(orgOrder);
			}
			
			return;
		}
//		record 路单详细条回执信息
//		entry 原路单行详细
//		orgEntry 原订单行
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		//后付款的不需要往后延期,重新计算订单价格
		if("10".equals(orgOrder.getPaymentmethod())){
			//更新后付款订单的订单金额和剩余金额
				BigDecimal cj = entry.getAmt().subtract(entry.getConfirmAmt());
				orgOrder.setInitAmt(orgOrder.getInitAmt().subtract(cj));
				if(entry.getConfirmAmt().floatValue() == 0){
					orgOrder.setCurAmt(orgOrder.getCurAmt().subtract(entry.getAmt()));
				}else{
					orgOrder.setCurAmt(orgOrder.getCurAmt().subtract(cj).subtract(entry.getConfirmAmt()));
				}
				tPreOrderMapper.updateOrderCurAmtAndInitAmt(orgOrder);
			
			BigDecimal initAmt = orgOrder.getInitAmt();
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(orgEntry.getOrderNo());
			for(TOrderDaliyPlanItem p : daliyPlans){
				if(!"30".equals(p.getStatus())){
					if(p.getDispDate().equals(dispDate) && entry.getOrgItemNo().equals(p.getItemNo()) && p.getGiftQty()==null ){
						p.setMatnr(entry.getConfirmMatnr());
						p.setQty(entry.getConfirmQty().intValue());
						p.setAmt(entry.getConfirmAmt());
						p.setStatus("20");
						initAmt = initAmt.subtract(p.getAmt());
					}else{
						initAmt = initAmt.subtract(p.getAmt());
					}
				}
				p.setRemainAmt(initAmt);
				
				tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
			}
			return;
		}
		
		ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(orgEntry.getOrderNo());
		int daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(orgEntry.getOrderNo()) + 1;
		
		int lackQty = entry.getQty().intValue() - confirmQty.intValue();
		
		//生成该新的一条每日计划
		TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
		plan.setOrderNo(orgEntry.getOrderNo());//订单编号
		plan.setOrderDate(orgEntry.getOrderDate());//订单日期
		plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
		plan.setItemNo(orgEntry.getItemNo());//预订单日计划行
//		plan.setDispDate(today);//配送日期
		plan.setReachTimeType(orgEntry.getReachTimeType());//送达时段类型
		plan.setMatnr(entry.getMatnr());//产品编号
//		plan.setUnit(orgEntry.getUnit());//配送单位
		plan.setQty(lackQty);//产品数量
		plan.setPrice(entry.getPrice());//产品价格
		//日计划行金额和
		BigDecimal qty = new BigDecimal(String.valueOf(lackQty));
		plan.setAmt(plan.getPrice().multiply(qty));//金额小计
//		plan.setRemainAmt();//订单余额
		plan.setStatus("10");//状态
		plan.setCreateAt(new Date());//创建时间
		plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
		plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
		
		//判断是按周期送还是按星期送
		Date lastDate = dispDate;
		for(TOrderDaliyPlanItem item :daliyPlans){
			if(item.getItemNo().equals(orgEntry.getItemNo())){
				lastDate = lastDate.after(item.getDispDate())?lastDate : item.getDispDate();
				//同时更新今天的日计划
				if(format.format(item.getDispDate()).equals(format.format(dispDate)) && item.getGiftQty()==null){
					item.setQty(confirmQty.intValue());
					item.setAmt(item.getPrice().multiply(confirmQty) );
				}
			}
		}
		
		//判断哪天送
		if("10".equals(orgEntry.getRuleType())){
			int gapDays = orgEntry.getGapDays() + 1;//间隔天数
			for(int i=1;i<365;i++){
				Date today = afterDate(lastDate,i);
				if(i%gapDays !=0){
					if(orgEntry.getRuleTxt()!=null){
						List<String> deliverDays = Arrays.asList(orgEntry.getRuleTxt().split(","));
						if(!deliverDays.contains(getWeek(today))){
							continue;
						}
					}
				}
				plan.setDispDate(today);
				break;
			}
		}
		else if("20".equals(orgEntry.getRuleType())){
			List<String> deliverDays = Arrays.asList(orgEntry.getRuleTxt().split(","));
			for(int i=1;i<365;i++){
				Date today = afterDate(lastDate,i);
				String weekday = getWeek(today);
				if(deliverDays.contains(weekday)){
					plan.setDispDate(today);
					break;
				}
			}
		}
		
		tOrderDaliyPlanItemMapper.insert(plan);
		daliyPlans.add(plan);
		
   	//日期排序完
		daliyPlans.sort(new Comparator<TOrderDaliyPlanItem>(){
			@Override
			public int compare(TOrderDaliyPlanItem o1, TOrderDaliyPlanItem o2)
			{
				if(o1.getDispDate().before(o2.getDispDate()))
				{
					return -1;
				}else{
					return  1;
				}
			}
   	});
		
		//订单截止日期修改
   	orgOrder.setEndDate(daliyPlans.get(daliyPlans.size()-1).getDispDate());
		tPreOrderMapper.updateOrderEndDate(orgOrder);
		
		calculateDaliyPlanRemainAmtAfterUptRoute(daliyPlans,orgOrder,dispDate,orgEntry);
		
	}
	
	/* (non-Javadoc) 
	* @title: searchOrderRemainData
	* @description: 查询该订户有多少未送的产品和已经消费的金额
	* @param memberNo
	* @return 
	* @see com.nhry.service.order.dao.OrderService#searchOrderRemainData(java.lang.String) 
	*/
	@Override
	public OrderRemainData searchOrderRemainData(String phone)
	{
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("phone", phone);
		List<TVipCustInfo> custs = tVipCustInfoService.findCompanyCustByPhone(attrs);
		if(custs != null && custs.size() == 1){
			return tPreOrderMapper.searchOrderRemainData(custs.get(0).getVipCustNo());
		}
		return null;
	}

	@Override
	public CollectOrderModel queryCollectByOrderNo(String orderCode) {
		TSysUser user = userSessionService.getCurrentUser();
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderCode);
		if(order == null){
			throw  new ServiceException(MessageCode.LOGIC_ERROR,"该订单已不存在");
		}
		TMdBranch branch = branchMapper.selectBranchByNo(order.getBranchNo());

		CollectOrderModel model = new CollectOrderModel();
		model.setOrder(order);
		model.setBranch(branch);
		TMstRecvBill customerBill = customerBillMapper.getRecBillByOrderNo(orderCode);
		if(customerBill!=null){
			//如果收款单已存在  获取当时录入的订户余额(因为当时已经将余额扣除了)
			model.setCustAccAmt(customerBill.getCustAccAmt());
		}else{
			BigDecimal acLeftAmt = new BigDecimal("0.00");
			TVipAcct eac = tVipCustInfoService.findVipAcctByCustNo(order.getMilkmemberNo());
			if(eac!=null){
				acLeftAmt = eac.getAcctAmt();
			}else{
				TVipAcct ac = new TVipAcct();
				ac.setVipCustNo(order.getMilkmemberNo());
				ac.setAcctAmt(acLeftAmt);
				tVipCustInfoService.addVipAcct(ac);
			}
			model.setCustAccAmt(acLeftAmt);
		}

		BigDecimal totalPrices = new BigDecimal(0);
		List<ProductItem> entries = new ArrayList<ProductItem>();
		if("20".equals(order.getPaymentmethod())){
			List<TPlanOrderItem> items = tPlanOrderItemMapper.selectByOrderCode(order.getOrderNo());
			if(items!=null && items.size()>0){
				for(TPlanOrderItem item : items ){
					BigDecimal price = item.getSalesPrice() == null? new BigDecimal(0): item.getSalesPrice();
					ProductItem entry = new ProductItem();
					entry.setMatnr(item.getMatnr());
					entry.setMatnrTxt(item.getMatnrTxt());
					entry.setMatnr(item.getMatnr());
					entry.setQty(item.getDispTotal() == null ?0 :item.getDispTotal());
					entry.setUnit(item.getUnit());
					entry.setBasePrice(price);
					entry.setTotalPrice(price.multiply(new BigDecimal(entry.getQty())));
					totalPrices = totalPrices.add(entry.getTotalPrice());
					entries.add(entry);
				}
			}
		}else{
			List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.getProductItemsByOrderNo(orderCode,user.getSalesOrg());

			if(items!=null && items.size()>0){
				for(TOrderDaliyPlanItem item : items ){
					BigDecimal price = item.getPrice() == null? new BigDecimal(0): item.getPrice();
					ProductItem entry = new ProductItem();
					entry.setMatnr(item.getMatnr());
					entry.setMatnrTxt(item.getMatnrTxt());
					entry.setMatnr(item.getMatnr());
					entry.setQty(item.getQty());
					entry.setUnit(item.getUnit());
					entry.setBasePrice(price);
					entry.setTotalPrice(price.multiply(new BigDecimal(item.getQty())));
					totalPrices = totalPrices.add(entry.getTotalPrice());
					entries.add(entry);
				}
			}
		}




		model.setAddress(tVipCustInfoService.findAddressDetailById(order.getAdressNo()));
		model.setTotalPrice(totalPrices);
		model.setEntries(entries);
		return model;
	}


	/* (non-Javadoc) 
	* @title: calculateAmtAndEndDateForFront
	* @description: 页面计算行项目截止日期和总计数量,总计金额，先付款用
	* @param item
	* @return 
	* @see com.nhry.service.order.dao.OrderService#calculateAmtAndEndDateForFront(com.nhry.data.order.domain.TPlanOrderItem) 
	*/
	@Override
	public TPlanOrderItem calculateAmtAndEndDateForFront(TPlanOrderItem item)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			item.setStartDispDate(format.parse(item.getStartDispDateStr()));
		}
		catch (Exception e)
		{
			return item;
		}
		//计算
		resolveEntryEndDispDateForFront(item);
		
		return item;
	}
	
	/* (non-Javadoc) 
	* @title: calculateAmtAndEndDateForFront
	* @description: 页面计算行项目和总计数量,总计金额，后付款用
	* @param item
	* @return 
	* @see com.nhry.service.order.dao.OrderService#calculateAmtAndEndDateForFront(com.nhry.data.order.domain.TPlanOrderItem) 
	*/
	@Override
	public TPlanOrderItem calculateTotalQtyForFront(TPlanOrderItem item)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			item.setStartDispDate(format.parse(item.getStartDispDateStr()));
			item.setEndDispDate(format.parse(item.getEndDispDateStr()));
		}
		catch (Exception e)
		{
			return item;
		}
		//计算
		resolveEntryTotalQtyForFront(item);
		
		return item;
	}
	
	/* (non-Javadoc) 
	* @title: updateOrderAndEntriesDispStartDate
	* @description: 奶箱修改状态，修改订单行项目起始日期
	* @param orderNo
	* @return 
	* @see com.nhry.service.order.dao.OrderService#updateOrderAndEntriesDispStartDate(java.lang.String) 
	*/
	@Override
	public int updateOrderAndEntriesDispStartDate(String orderNo,List<TPlanOrderItem> entries)
	{
		TPreOrder order =tPreOrderMapper.selectByPrimaryKey(orderNo);
		List<TPlanOrderItem> orgEntries = tPlanOrderItemMapper.selectByOrderCode(orderNo);
		for(TPlanOrderItem org :orgEntries){
			for(TPlanOrderItem cur:entries){
				if(org.getItemNo().equals(cur.getItemNo())){
					if(!org.getStartDispDate().equals(cur.getStartDispDate())){
						org.setStartDispDate(cur.getStartDispDate());
						recalculateEntryEndDate(org);
						tPlanOrderItemMapper.updateEntryByItemNo(org);
					}
					break;
				}
			}
		}
		
		//订单截止日期
		order.setEndDate(calculateFinalDate(orgEntries));
		tPreOrderMapper.updateOrderEndDate(order);
		
		return 1;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//计算间隔天数
	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;
	}

	//日期往前后加n天
	private Date afterDate(Date date, int days) {

		Calendar aCalendar =  Calendar.getInstance();
		aCalendar.setTime(date);
		aCalendar.add(aCalendar.DATE, days);//把日期往后增加一天.整数往后推,负数往前移动
		date=aCalendar.getTime();   //这个时间就是日期往后推一天的结果

		return date;
	}
	
   //根据日期取得星期几  
   private String getWeek(Date date){  
       String[] weeks = {"7","1","2","3","4","5","6"};  
       Calendar aCalendar = Calendar.getInstance();  
       aCalendar.setTime(date);  
       int week_index = aCalendar.get(Calendar.DAY_OF_WEEK) - 1;  
       if(week_index<0){  
           week_index = 0;  
       }   
       return weeks[week_index];  
   }
   
   //计算续订的订单的开始日期
   private void calculateEntryStartDate(TPlanOrderItem entry){
   	
   	int gapDays = entry.getGapDays() + 1;//间隔天数
   	List<String> deliverDays = null;
   	if(entry.getRuleTxt()!=null){
   		deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
   	}
   	
   	for(int afterDays = 1; afterDays < 365; afterDays++){
   		
   		Date today = afterDate(entry.getEndDispDate(),afterDays);
   		if("10".equals(entry.getRuleType())){
   			if(afterDays%gapDays != 0){
   				continue;
   			}
   		}else if("20".equals(entry.getRuleType())){
   			String weekday = getWeek(today);
   			if(!deliverDays.contains(weekday)){
   				continue;
   			}
   		}
   		
   		entry.setStartDispDate(today);
   		break;
   	}
   	
   }
   
   //计算订单行的总价格
   private BigDecimal calculateEntryAmount(TPlanOrderItem entry){
   	
   	int totalqty = 0;
   	int afterDays = 0;//经过的天数
   	int allDays = daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate())+1;//总共需要配送的天数
   	
   	int goDays = 0;
   	BigDecimal qty = new BigDecimal(entry.getQty().toString());
   	BigDecimal amount = new BigDecimal(entry.getSalesPrice().toString());
   	
   	for(int i = 0;i < allDays; i++,afterDays++){
   		Date today = afterDate(entry.getStartDispDate(),afterDays);
   		if("10".equals(entry.getRuleType())){
   			int gapDays = entry.getGapDays() + 1;//间隔天数
   			if(afterDays%gapDays != 0){
   				if(entry.getRuleTxt()!=null){
      				List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
         			if(deliverDays.size() > 0){//判断周6，7是否配送
         				String weekday = getWeek(today);
         				if(!deliverDays.contains(weekday)){
         					continue;
         				}
         			}
   				}else{
   					continue;
   				}
   			}
   		}else if("20".equals(entry.getRuleType())){
   			String weekday = getWeek(today);
   			List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
   			if(!deliverDays.contains(weekday)){
   				continue;
   			}
   		}
   		totalqty += entry.getQty();
   		goDays++;
   		entry.setEndDispDate(today);
   	}
   	//end
   	
   	//配送总数和真正截止日期
   	entry.setDispTotal(totalqty);
   	
   	//如果有促销，增加促销购买数量字段
   	if(StringUtils.isNotBlank(entry.getPromotion()))entry.setBuyQty(qty.multiply(new BigDecimal(String.valueOf(goDays))).intValue());
   		
   	return amount.multiply(qty).multiply(new BigDecimal(String.valueOf(goDays)));
   }
   
   //变更产品或数量
   private void changeProductOrQty(TPreOrder orgOrder, ArrayList<TPlanOrderItem> orgEntries,List<TOrderDaliyPlanItem> daliyPlans, Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> changeProducts,Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> changeQtys,Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> stopPlans){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");//该商品该日期原日计划送多少个
		BigDecimal curAmt = orgOrder.getCurAmt();//当前订单的剩余金额
   	
   	Map<String,Integer> needNewDaliyPlans = new HashMap<String,Integer>();//所有需要新增或减少的日计划行

   	Map<String,TPlanOrderItem> relatedEntryMap = new HashMap<String,TPlanOrderItem>();//key为订单行itemNo,value为entry
   	for(TPlanOrderItem entry : orgEntries){
   		if(!relatedEntryMap.containsKey(entry.getItemNo())){
   			relatedEntryMap.put(entry.getItemNo(), entry);
   		}
   	}
   	
   	//针对于原订单行要减少或者增加多少件商品
   	Map<String,BigDecimal> productMap = new HashMap<String,BigDecimal>();
   	for (TOrderDaliyPlanItem org : changeProducts.keySet()) {
   		TOrderDaliyPlanItem cur = changeProducts.get(org);
   		BigDecimal planAmt = cur.getPrice().multiply(new BigDecimal(cur.getQty().toString()));
   		BigDecimal rate = planAmt.subtract(org.getAmt()).divide(relatedEntryMap.get(cur.getItemNo()).getSalesPrice(),2).setScale(2, BigDecimal.ROUND_FLOOR);
   		String itemNo = org.getItemNo();//原日计划的商品号
   		if(!productMap.containsKey(itemNo)){
         	productMap.put(itemNo, new BigDecimal("0.00").subtract(rate));
         }else{
         	productMap.replace(itemNo, new BigDecimal("0.00").subtract(rate).add(productMap.get(itemNo)));
         }
   	}
   	for(String key : productMap.keySet()){
   		int needDays = productMap.get(key).setScale(0, BigDecimal.ROUND_FLOOR).intValue();//需要减少或增加的原商品数量
   		if(needDays!=0){
   			needNewDaliyPlans.put(key, needDays);
   		}
   	}
   	
   	//数量，当value为负说明该产品行日计划要增加延后value件商品，正说明要提前
   	for (TOrderDaliyPlanItem org : changeQtys.keySet()) {
   		TOrderDaliyPlanItem cur = changeQtys.get(org);
   		int c = cur.getQty() - org.getQty();
   		if(needNewDaliyPlans.containsKey(org.getItemNo())){
   			needNewDaliyPlans.replace(org.getItemNo(), needNewDaliyPlans.get(org.getItemNo()) - c);
   		}else{
   			needNewDaliyPlans.put(org.getItemNo(), -c);
   		}
   	}
   	
   	//停定的，往后延期
   	for (TOrderDaliyPlanItem org : stopPlans.keySet()) {
   		int c = org.getQty();
   		if(needNewDaliyPlans.containsKey(org.getItemNo())){
   			needNewDaliyPlans.replace(org.getItemNo(), c + needNewDaliyPlans.get(org.getItemNo()));
   		}else{
   			needNewDaliyPlans.put(org.getItemNo(), c);
   		}
   	}
   	
   	
   	List<TOrderDaliyPlanItem> newPlans = new ArrayList<TOrderDaliyPlanItem>();
   	for(TOrderDaliyPlanItem plan : daliyPlans){
   		if(plan.getGiftQty()!=null)continue;
   		//停订的
   		if(stopPlans.containsKey(plan)){
   			TOrderDaliyPlanItem np = new TOrderDaliyPlanItem();
				int needQty = needNewDaliyPlans.get(plan.getItemNo());//
				np.setQty(needQty);
				needNewDaliyPlans.replace(plan.getItemNo(), 0);
				convertDaliyPlan(plan, np);
				newPlans.add(np);
   			break;
   		}
   		if(needNewDaliyPlans.containsKey(plan.getItemNo())){
   			//修改，需要的数量，从最后一个日期往前拿(从后往前扣商品数量)
   			if(needNewDaliyPlans.get(plan.getItemNo()) < 0){
      				if(changeProducts.containsKey(plan) || changeQtys.containsKey(plan) )continue;
  				      if("20".equals(plan.getStatus()) || "30".equals(plan.getStatus()) || !plan.getMatnr().equals(relatedEntryMap.get(plan.getItemNo()).getMatnr() ))continue;
      				if(plan.getQty() > 0 && (plan.getQty() + needNewDaliyPlans.get(plan.getItemNo()) >= 0) ){
      					plan.setQty(plan.getQty() + needNewDaliyPlans.get(plan.getItemNo()));
      					needNewDaliyPlans.replace(plan.getItemNo(), 0);
      				}
      				else if(plan.getQty() > 0 && (plan.getQty() + needNewDaliyPlans.get(plan.getItemNo()) < 0)){
      					plan.setQty(plan.getQty());
      					needNewDaliyPlans.replace(plan.getItemNo(), needNewDaliyPlans.get(plan.getItemNo()) + plan.getQty() );
      				}
      				else{
      					continue;
      				}
   			}
   			//修改，需要新增日计划行
   			else if(needNewDaliyPlans.get(plan.getItemNo()) > 0){
   				if(stopPlans.size()>0)continue;
   				while(needNewDaliyPlans.get(plan.getItemNo()) > 0){
   					TOrderDaliyPlanItem np = new TOrderDaliyPlanItem();
   					int needQty = needNewDaliyPlans.get(plan.getItemNo());
   					int entryQty = relatedEntryMap.get(plan.getItemNo()).getQty();
   					if(entryQty>=needQty){
   						np.setQty(needQty);
   						needNewDaliyPlans.replace(plan.getItemNo(), 0);
   					}else{
   						np.setQty(entryQty);
   						needNewDaliyPlans.replace(plan.getItemNo(), needQty-entryQty);
   					}
   					convertDaliyPlanByEntry(plan, np,relatedEntryMap.get(plan.getItemNo()));
   					newPlans.add(np);
   				}
   				break;
   			}
   		}
   	}
   	
   	for(String key :needNewDaliyPlans.keySet()){
   		if(needNewDaliyPlans.get(key) < 0)throw new ServiceException(MessageCode.LOGIC_ERROR,"该产品行日计划金额不足以更换此商品!");;
   	}
   	
   	//删除数量为0的日计划,并重新生成新加的日计划
   	Map<String,Date> dateMap = new HashMap<String,Date>();//每个订单行的最后配送时间
   	for(TOrderDaliyPlanItem plan : daliyPlans){
   		if(plan.getQty() > 0 || "20".equals(plan.getStatus())){
   			if(dateMap.containsKey(plan.getItemNo())){
   				Date lastDate = dateMap.get(plan.getItemNo()).before(plan.getDispDate())?plan.getDispDate():dateMap.get(plan.getItemNo());
   				dateMap.replace(plan.getItemNo(), lastDate);
   			}else{
   				dateMap.put(plan.getItemNo(), plan.getDispDate());
   			}
   			//停订的
				if(stopPlans.containsKey(plan)){
					plan.setStatus("30");
//					continue;
				}
				//变更产品
				if(changeProducts.containsKey(plan)){
					plan.setMatnr(changeProducts.get(plan).getMatnr());
					plan.setQty(changeProducts.get(plan).getQty());
					plan.setUnit(changeProducts.get(plan).getUnit());
					plan.setPrice(changeProducts.get(plan).getPrice());
					plan.setAmt(changeProducts.get(plan).getPrice().multiply(new BigDecimal(changeProducts.get(plan).getQty().toString())));
				}
				//变更数量
				else if(changeQtys.containsKey(plan)){
					plan.setQty(changeQtys.get(plan).getQty());
					plan.setAmt(changeQtys.get(plan).getPrice().multiply(new BigDecimal(changeQtys.get(plan).getQty().toString())));
				}
				newPlans.add(0,plan);
   		}
   	}
   	TOrderDaliyPlanItem record = new TOrderDaliyPlanItem();
   	record.setOrderNo(newPlans.get(0).getOrderNo());
   	record.setDispDateStr(format.format(newPlans.get(0).getDispDate()));//从什么时候删除日单
   	tOrderDaliyPlanItemMapper.deleteFromDateToDate(record);
   	
   	//保存所有新的日计划
   	int index = 0;
   	List<TOrderDaliyPlanItem> dateList = new ArrayList<TOrderDaliyPlanItem>(); 
   	for(TOrderDaliyPlanItem plan:newPlans){
   		
   		//停订的和确认的，直接保存
   		plan.setPlanItemNo(String.valueOf(index));
   		if(stopPlans.containsKey(plan)||"20".equals(plan.getStatus())||"30".equals(plan.getStatus())){
//   			tOrderDaliyPlanItemMapper.insert(plan);
   			index++;
   			continue;
   		}
   		
   		//跳过赠品，赠品日期需要放到最后几天
   		if(plan.getGiftQty()!=null)continue;
   		
//   		//其他的重新计算剩余金额等信息
//   		plan.setAmt(plan.getPrice().multiply(new BigDecimal(plan.getQty().toString())));//重新计算小记金额
//   		curAmt = curAmt.subtract(plan.getAmt());//计算日计划的剩余金额
//   		plan.setRemainAmt(curAmt);
//   		
//   		if(plan.getRemainAmt().floatValue() < 0)break;
   		
   		if(plan.getDispDate()==null){
   			
   			TPlanOrderItem entry = relatedEntryMap.get(plan.getItemNo());
   			Date lastDate = dateMap.get(plan.getItemNo());
   			//判断是按周期送还是按星期送
				if("10".equals(entry.getRuleType())){
					int gapDays = entry.getGapDays() + 1;//间隔天数
					for(int i=1;i<365;i++){
						Date today = afterDate(lastDate,i);
						if(daysOfTwo(dateMap.get(plan.getItemNo()),today)%gapDays !=0){
//							if(entry.getRuleTxt()!=null){
//								List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
//								if(!deliverDays.contains(getWeek(today))){
//									continue;
//								}
//							}else{
								continue;
//							}
						}
						dateMap.replace(plan.getItemNo(), today); 
						plan.setDispDate(today);
						break;
					}
				}
				else if("20".equals(entry.getRuleType())){
					List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
					for(int i=1;i<365;i++){
						Date today = afterDate(lastDate,i);
						String weekday = getWeek(today);
						if(deliverDays.contains(weekday)){
							dateMap.replace(plan.getItemNo(), today);
							plan.setDispDate(today);
							break;
						}
					}
				}
   		}
//   		tOrderDaliyPlanItemMapper.insert(plan);
   		dateList.add(0,plan);
   		
   		index++;
   	}
   	
   	//日期排序完，全保存
   	newPlans.sort(new Comparator<TOrderDaliyPlanItem>(){
			@Override
			public int compare(TOrderDaliyPlanItem o1, TOrderDaliyPlanItem o2)
			{
				if(o1.getDispDate().before(o2.getDispDate()))
				{
					return -1;
				}else{
					return  1;
				}
			}
   	});
   	
   	//设置金额
   	for(TOrderDaliyPlanItem plan:newPlans){
   		//停订的和确认的，直接保存
   		if(stopPlans.containsKey(plan)||"20".equals(plan.getStatus())||"30".equals(plan.getStatus())){
   			tOrderDaliyPlanItemMapper.insert(plan);
   			continue;
   		}
   		//跳过赠品，赠品日期需要放到最后几天
   		if(plan.getGiftQty()!=null)continue;
   		//其他的重新计算剩余金额等信息
   		plan.setAmt(plan.getPrice().multiply(new BigDecimal(plan.getQty().toString())));//重新计算小记金额
   		curAmt = curAmt.subtract(plan.getAmt());//计算日计划的剩余金额
   		plan.setRemainAmt(curAmt);
   		
   		if(plan.getRemainAmt().floatValue() < 0)break;
   		
   		tOrderDaliyPlanItemMapper.insert(plan);
   	}
   	
   	//订单截止日期修改
   	orgOrder.setEndDate(newPlans.get(newPlans.size()-1).getDispDate());
		tPreOrderMapper.updateOrderEndDate(orgOrder);
   	
   	int i=0;
   	for(TOrderDaliyPlanItem plan:newPlans){
   		if(plan.getGiftQty()==null)continue;
   		if(!dateList.get(i).getItemNo().equals(plan.getItemNo())){
   			i++;
   			continue;
   		}
			plan.setDispDate(dateList.get(i).getDispDate());
			plan.setPlanItemNo(String.valueOf(index));
			index++;
			i++;
			tOrderDaliyPlanItemMapper.insert(plan);
   	}
   	
   }
   
   //复制日计划
   private void convertDaliyPlan(TOrderDaliyPlanItem org,TOrderDaliyPlanItem plan){
   	plan.setOrderNo(org.getOrderNo());//订单编号
		plan.setOrderDate(org.getOrderDate());//订单日期
		plan.setItemNo(org.getItemNo());//预订单日计划行
		plan.setReachTimeType(org.getReachTimeType());//送达时段类型
		plan.setMatnr(org.getMatnr());//产品编号
		plan.setUnit(org.getUnit());//配送单位
		plan.setPrice(org.getPrice());//产品价格
		plan.setStatus("10");//状态
		plan.setCreateAt(new Date());//创建时间
		plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
		plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
   }
   
   private void convertDaliyPlanByEntry(TOrderDaliyPlanItem org,TOrderDaliyPlanItem plan,TPlanOrderItem entry){
   	plan.setOrderNo(org.getOrderNo());//订单编号
		plan.setOrderDate(org.getOrderDate());//订单日期
		plan.setItemNo(org.getItemNo());//预订单日计划行
		plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
		plan.setMatnr(entry.getMatnr());//产品编号
		plan.setUnit(entry.getUnit());//配送单位
		plan.setPrice(entry.getSalesPrice());//产品价格
		plan.setStatus("10");//状态
		plan.setCreateAt(new Date());//创建时间
		plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
		plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
   }
   
   //计算订单的截止日期
   private Date calculateFinalDate(List<TPlanOrderItem> entries){
   	Date finalDate = new Date();
   	for(TPlanOrderItem entry : entries){
   		if("30".equals(entry.getStatus()) )continue;
   		finalDate = finalDate.after(entry.getEndDispDate())?finalDate:entry.getEndDispDate();
   	}
   	return finalDate;
   }
   
   //计算生成的日单的剩余金额,日订单必须要按日期从小到大排序,后付款专用
   private void calculateDaliyPlanRemainAmt(TPreOrder order,List<TOrderDaliyPlanItem> daliyPlans){
   	if(daliyPlans.size() == 0 || daliyPlans == null) return;

   	BigDecimal initAmt = order.getInitAmt();

   	for(int i = daliyPlans.size() - 1 ; i >= 0 ;i--){
   		TOrderDaliyPlanItem plan = daliyPlans.get(i);
   		if("30".equals(plan.getStatus())){
   			plan.setRemainAmt(initAmt);
   			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
   			continue;
   		}
   		plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
   		initAmt = initAmt.subtract(plan.getAmt());
   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
   	}
   	
   }
   
   //交验生成订单的输入信息
   private void validateOrderInfo(OrderCreateModel record){
   	TPreOrder order = record.getOrder();
   	if(StringUtils.isBlank(order.getPaymentStat())){
   		throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择付款方式!");
		}
   	if(StringUtils.isBlank(order.getMilkboxStat())){
   		throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择奶箱状态!");
		}
   	if(StringUtils.isBlank(order.getEmpNo())){
   		if(!"10".equals(order.getPreorderSource())&&!"20".equals(order.getPreorderSource())){
   			throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择送奶员!");
   		}
		}
   	if(record.getEntries()==null || record.getEntries().size() == 0){
   		throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择商品行!");
		}
   	if(StringUtils.isBlank(order.getMilkmemberNo())){
   		if(!"10".equals(order.getPreorderSource())&&!"20".equals(order.getPreorderSource())){//电商不交验订户
   			throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择订户!");
   		}
		}
   	if(StringUtils.isBlank(order.getAdressNo())){
   		if(record.getAddress() == null){
   			throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择或输入地址!");
   		}
		}
   	for(TPlanOrderItem e:record.getEntries()){
   		if(StringUtils.isBlank(e.getRuleType())){
   			throw new ServiceException(MessageCode.LOGIC_ERROR,"商品行必须要有配送规律!");
   		}
   	}
   }

   //重新计算当天更新日单后，日计划的剩余金额
   private void calculateDaliyPlanRemainAmtAfterUptRoute(List<TOrderDaliyPlanItem> daliyPlans , TPreOrder order,Date dispDate,TPlanOrderItem orgEntry){
//   	BigDecimal curAmt = order.getCurAmt();
   	BigDecimal initAmt = order.getInitAmt();
   	
   	List<TOrderDaliyPlanItem> needUpt = new ArrayList<TOrderDaliyPlanItem>();
   	
   	for(TOrderDaliyPlanItem plan : daliyPlans){
   		if("30".equals(plan.getStatus()))continue;
   		if(plan.getGiftQty()!=null)continue;
   		
   		initAmt = initAmt.subtract(plan.getAmt());
   		
   		if(plan.getDispDate().compareTo(dispDate) == 0 && orgEntry.getItemNo().equals(plan.getItemNo())){
   			plan.setRemainAmt(initAmt);
   			plan.setStatus("20");//已确认送达,当天的确认
   			needUpt.add(plan);
   			continue;
   		}
//   		curAmt = curAmt.subtract(plan.getAmt());
   		
   		plan.setRemainAmt(initAmt);
   		needUpt.add(plan);
   	}
   
   	needUpt.stream().forEach((e)->tOrderDaliyPlanItemMapper.updateDaliyPlanItem(e));
   
   }
   
   //根据订单行生成每日计划,针对复订的订单
 	private List<TOrderDaliyPlanItem> createDaliyPlanForResumeOrder(TPreOrder order ,List<TPlanOrderItem> entries,BigDecimal amt, Date startDate, Map<String,Integer> qtyMap){

 		List<TOrderDaliyPlanItem> list = new ArrayList<TOrderDaliyPlanItem>();
 		BigDecimal curAmt = amt;

 		//计算每个行项目总共需要送多少天
 		Map<String,TPlanOrderItem> entryMap = new HashMap<String,TPlanOrderItem>();
 		int maxEntryDay = 365;
 		for(TPlanOrderItem entry: entries){
 			entryMap.put(entry.getItemNo(), entry);
 		}

 		//行号唯一，需要判断以前最大的行号
 		int daliyEntryNo = 0;//日计划行号
 		try{
 			daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(order.getOrderNo()) + 1;
 		}catch(Exception e){
 			//如果找不到最大值
 		}
 		
 		Date stopEndDate = order.getStopDateEnd();
 		outer:for(int afterDays= 0 ; afterDays < maxEntryDay; afterDays++){
 			for(TPlanOrderItem entry:entries){
 				
 				  //判断是按周期送还是按星期送
 				  Date today = afterDate(startDate,afterDays);
 				
 				  if(entry.getStartDispDate().after(today) || entry.getEndDispDate().before(order.getStopDateStart()))continue;
 				  
 				  if("10".equals(entry.getRuleType())){
 						int gapDays = entry.getGapDays() + 1;//间隔天数
 						if((!stopEndDate.after(entry.getStartDispDate())?daysOfTwo(entry.getStartDispDate(),today):afterDays)%gapDays != 0){
 							if(entry.getRuleTxt()!=null){
 								List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
 								if(deliverDays.size() > 0){//判断周6，7是否配送
 									String weekday = getWeek(today);
 									if(!deliverDays.contains(weekday)){
 										continue;
 									}
 								}
 							}else{
 								continue;
 							}
 						}
 					}
 					else if("20".equals(entry.getRuleType())){
 						String weekday = getWeek(today);
 						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
 						if(!deliverDays.contains(weekday)){
 							continue;//如果选择的星期几不送，则跳过今天生成日计划
 						}
 					}
 					
 				   //生成该订单行的每日计划
 				   if(qtyMap.containsKey(entry.getItemNo())){
 					  if(qtyMap.get(entry.getItemNo()) <= 0)continue;
 					  qtyMap.replace(entry.getItemNo(), qtyMap.get(entry.getItemNo()) - 1 );
 				   }
 				  
					TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
					plan.setOrderNo(entry.getOrderNo());//订单编号
					plan.setOrderDate(entry.getOrderDate());//订单日期
					plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
					plan.setItemNo(entry.getItemNo());//预订单日计划行
					plan.setDispDate(today);//配送日期
					plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
					plan.setMatnr(entry.getMatnr());//产品编号
					plan.setUnit(entry.getUnit());//配送单位
					plan.setQty(entry.getQty());//产品数量
					
					plan.setPrice(entry.getSalesPrice());//产品价格
					plan.setPromotionFlag(entry.getPromotion());//促销号
					//日计划行金额和
					BigDecimal qty = new BigDecimal(entry.getQty().toString());
					plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
					curAmt = curAmt.subtract(plan.getAmt());
					
					//当订单余额小于0时停止
					if(curAmt.floatValue() < 0)break outer;
					
					plan.setRemainAmt(curAmt);//订单余额
					plan.setStatus("10");//状态
					plan.setCreateAt(new Date());//创建时间
					plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
					plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
					
					tOrderDaliyPlanItemMapper.insert(plan);
					daliyEntryNo++;
					list.add(0,plan);
 			}
 		}

 		return list;
 	}
 	
   //长期修改的订单,要根据订单的余额生成每日计划
 	private List<TOrderDaliyPlanItem> createDaliyPlanForLongEdit(TPreOrder order ,List<TPlanOrderItem> entries, List<TPlanOrderItem> orgEntries){

 	   //预付款的要付款+装箱才生成日计划
		if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getPaymentStat())){
			for(TPlanOrderItem e :entries){
				resolveEntryEndDispDate(e);
			}
			return null;
		}
 			
 		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
 		if("20".equals(order.getMilkboxStat())){
 			if("20".equals(order.getPaymentmethod())){
 				for(TPlanOrderItem e :entries){
 					resolveEntryEndDispDate(e);
 				}
 			}
 			return null;
 		}
 		
		ArrayList<TOrderDaliyPlanItem> orgDaliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());
 		BigDecimal curAmt = order.getInitAmt();//订单余额总，金额减去所有未修改的金额
 		BigDecimal initAmt = order.getInitAmt();
 		for(TOrderDaliyPlanItem p :orgDaliyPlans){
 			if("30".equals(p.getStatus()))continue;
 			curAmt = curAmt.subtract(p.getAmt());
 		}
 		
 		List<TOrderDaliyPlanItem> daliyPlans = new ArrayList<TOrderDaliyPlanItem>();
 		Date firstDeliveryDate = null;
 		for(TPlanOrderItem entry: entries){
 			if(firstDeliveryDate==null){
				firstDeliveryDate = entry.getStartDispDate();
			}else{
				firstDeliveryDate = firstDeliveryDate.before(entry.getStartDispDate())?firstDeliveryDate:entry.getStartDispDate();
			}
 		}

 		//行号唯一，需要判断以前最大的行号
 		int daliyEntryNo = 0;//日计划行号
 		try{
 			daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(order.getOrderNo()) + 1;
 		}catch(Exception e){
 			//如果找不到最大值
 		}
 		
 		outer:for(int afterDays = 0; afterDays < 365 ; afterDays ++){
 			for(TPlanOrderItem entry : entries){
 				
 			//判断是按周期送还是按星期送
				Date today = afterDate(firstDeliveryDate,afterDays);
				
				if(entry.getStartDispDate().after(today))continue;
				
				if("10".equals(entry.getRuleType())){
					int gapDays = entry.getGapDays() + 1;//间隔天数
					if(daysOfTwo(entry.getStartDispDate(),today)%gapDays != 0){
						if(entry.getRuleTxt()!=null){
							List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
							if(deliverDays.size() > 0){//判断周6，7是否配送
								String weekday = getWeek(today);
								if(!deliverDays.contains(weekday)){
									continue;
								}
							}
						}else{
							continue;
						}
					}
				}
				else if("20".equals(entry.getRuleType())){
					String weekday = getWeek(today);
					List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
					if(!deliverDays.contains(weekday)){
						continue;//如果选择的星期几不送，则跳过今天生成日计划
					}
				}
				
				//行项目生成到这天
				entry.setEndDispDate(today);
				
				//生成该订单行的每日计划
				TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
				plan.setOrderNo(entry.getOrderNo());//订单编号
				plan.setOrderDate(entry.getOrderDate());//订单日期
				plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
				plan.setItemNo(entry.getItemNo());//预订单日计划行
				plan.setDispDate(today);//配送日期
				plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
				plan.setMatnr(entry.getMatnr());//产品编号
				plan.setUnit(entry.getUnit());//配送单位
				plan.setQty(entry.getQty());//产品数量
				plan.setPrice(entry.getSalesPrice());//产品价格
				//日计划行金额和
				BigDecimal qty = new BigDecimal(entry.getQty().toString());
				plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
				curAmt = curAmt.subtract(plan.getAmt());
				
				//当订单余额小于0时停止
				if(curAmt.floatValue() < 0)break outer;
				
				plan.setRemainAmt(curAmt);//订单余额
				plan.setStatus("10");//状态
				plan.setCreateAt(new Date());//创建时间
				plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
				plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
				
				tOrderDaliyPlanItemMapper.insert(plan);
				daliyEntryNo++;
				
				daliyPlans.add(0,plan);
				orgDaliyPlans.add(plan);
 			}
 		}
 		
 		//更新原日计划的金额 TODO
 		//日期排序完
 		orgDaliyPlans.sort(new Comparator<TOrderDaliyPlanItem>(){
			@Override
			public int compare(TOrderDaliyPlanItem o1, TOrderDaliyPlanItem o2)
			{
				if(o1.getDispDate().before(o2.getDispDate()))
				{
					return -1;
				}else{
					return  1;
				}
			}
   	});
   	
 		//重新计算金额
   	for(TOrderDaliyPlanItem p:orgDaliyPlans){
   		if(p.getGiftQty()!=null || "30".equals(p.getStatus()))continue;
   		initAmt = initAmt.subtract(p.getAmt());
   		p.setRemainAmt(initAmt);
   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
   	}
 		
 		//更新订单行enddispdate
 		for(TPlanOrderItem entry: entries){
 			for(TOrderDaliyPlanItem p :daliyPlans){
 				if(p.getItemNo().equals(entry.getItemNo())){
 					entry.setEndDispDate(p.getDispDate());
 					break;
 				}
 			}
 		}

 		return daliyPlans;
 	}
 	
 	//当订单是预付款时，订单行有配送总数和起始日期，需要计算结束日期
 	private void resolveEntryEndDispDate(TPlanOrderItem entry){
 		int total = entry.getDispTotal();
 		if(total<=0 || total%entry.getQty()!=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"行总共配送无法平均分配到每一天!请修改总数或每日配送数");
 		
 		int afterDays = 0;
 		
 		//判断是按周期送还是按星期送
		for(int i=0;i<365;i++){
			Date today = afterDate(entry.getStartDispDate(),afterDays);
			
			if("10".equals(entry.getRuleType())){
				int gapDays = entry.getGapDays() + 1;//间隔天数
				if(afterDays%gapDays != 0){
					if(entry.getRuleTxt()!=null){
						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
						if(deliverDays.size() > 0){//判断周6，7是否配送
							String weekday = getWeek(today);
							if(!deliverDays.contains(weekday)){
								afterDays++;
								continue;
							}
						}
					}else{
						afterDays++;
						continue;
					}
				}
			}
			else if("20".equals(entry.getRuleType())){
				String weekday = getWeek(today);
				List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
				if(!deliverDays.contains(weekday)){
					afterDays++;
					continue;//如果选择的星期几不送，则跳过今天
				}
			}
			
			total = total - entry.getQty();
			afterDays++;
			
			if(total==0){
				entry.setEndDispDate(today);
				break;
			}
			
		}
		
 	}
 	
   //当订单是预付款时，订单行有配送总数和起始日期，需要计算结束日期,页面用，顺便计算金额
  	private void resolveEntryEndDispDateForFront(TPlanOrderItem entry){
  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  		
  		try{
     		int total = entry.getDispTotal();
     		if(total<=0 || total%entry.getQty()!=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"行总共配送无法平均分配到每一天!请修改总数或每日配送数");
     		
     		int afterDays = 0;
     		BigDecimal amt = new BigDecimal("0.00");//行金额总计
     		
     		//判断是按周期送还是按星期送
    		for(int i=0;i<365;i++){
    			Date today = afterDate(entry.getStartDispDate(),afterDays);
    			
    			if("10".equals(entry.getRuleType())){
    				int gapDays = entry.getGapDays() + 1;//间隔天数
    				if(afterDays%gapDays != 0){
    					if(entry.getRuleTxt()!=null){
    						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
    						if(deliverDays.size() > 0){//判断周6，7是否配送
    							String weekday = getWeek(today);
    							if(!deliverDays.contains(weekday)){
    								afterDays++;
    								continue;
    							}
    						}
    					}else{
    						afterDays++;
    						continue;
    					}
    				}
    			}
    			else if("20".equals(entry.getRuleType())){
    				String weekday = getWeek(today);
    				List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
    				if(!deliverDays.contains(weekday)){
    					afterDays++;
    					continue;//如果选择的星期几不送，则跳过今天
    				}
    			}
    			else{
    				return;
    			}
    			
    			total = total - entry.getQty();
    			amt = amt.add(entry.getSalesPrice().multiply(new BigDecimal(String.valueOf(entry.getQty()))));
    			afterDays++;
    			
    			if(total==0){
    				entry.setEndDispDateStr(format.format(today));
    				entry.setEntryTotal(amt);
    				break;
    			}
    			
    		}
  		}catch(Exception e)
  		{
  			entry.setEndDispDateStr(null);
  			entry.setEntryTotal(null);
  			return;
  		}
  		
  	}
	
   //当订单是后付款时，订单行有结束日期和起始日期，需要计算总配送数,页面用，顺便计算金额
  	private void resolveEntryTotalQtyForFront(TPlanOrderItem entry){
  		
  		try{
     		Date edate = entry.getEndDispDate();
     		Date sdate = entry.getStartDispDate();
     		
     		int total = 0;
     		
     		int afterDays = 0;
     		BigDecimal amt = new BigDecimal("0.00");//行金额总计
     		
     		//判断是按周期送还是按星期送
    		for(int i=0;i<365;i++){
    			Date today = afterDate(sdate,afterDays);
    			
    			if(today.after(edate)){
    				entry.setDispTotal(total);
    				entry.setEntryTotal(amt);
    				break;
    			}
    			
    			if("10".equals(entry.getRuleType())){
    				int gapDays = entry.getGapDays() + 1;//间隔天数
    				if(afterDays%gapDays != 0){
   					afterDays++;
   					continue;
    				}
    			}
    			else if("20".equals(entry.getRuleType())){
    				String weekday = getWeek(today);
    				List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
    				if(!deliverDays.contains(weekday)){
    					afterDays++;
    					continue;//如果选择的星期几不送，则跳过今天
    				}
    			}
    			else{
    				return;
    			}
    			
    			total += entry.getQty();
    			amt = amt.add(entry.getSalesPrice().multiply(new BigDecimal(String.valueOf(entry.getQty()))));
    			afterDays++;
    			
    		}
  		}catch(Exception e)
  		{
  			entry.setDispTotal(null);
  			entry.setEntryTotal(null);
  			return;
  		}
  		
  	}

  	//装箱工单修改时，如果更改起始日期，重新计算行项目
  	private void recalculateEntryEndDate(TPlanOrderItem entry){
     		int total = entry.getDispTotal();
     		
     		int afterDays = 0;
     		
     		//判断是按周期送还是按星期送
    		for(int i=0;i<365;i++){
    			Date today = afterDate(entry.getStartDispDate(),afterDays);
    			
    			if("10".equals(entry.getRuleType())){
    				int gapDays = entry.getGapDays() + 1;//间隔天数
    				if(afterDays%gapDays != 0){
    					if(entry.getRuleTxt()!=null){
    						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
    						if(deliverDays.size() > 0){//判断周6，7是否配送
    							String weekday = getWeek(today);
    							if(!deliverDays.contains(weekday)){
    								afterDays++;
    								continue;
    							}
    						}
    					}else{
    						afterDays++;
    						continue;
    					}
    				}
    			}
    			else if("20".equals(entry.getRuleType())){
    				String weekday = getWeek(today);
    				List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
    				if(!deliverDays.contains(weekday)){
    					afterDays++;
    					continue;//如果选择的星期几不送，则跳过今天
    				}
    			}
    			else{
    				return;
    			}
    			
    			total = total - entry.getQty();
    			afterDays++;
    			
    			if(total==0){
    				entry.setEndDispDate(today);
    				break;
    			}
    		}
  	}

	/* (non-Javadoc) 
	* @title: createDaliyPlansForIniOrders
	* @description: 为期初订单生成日计划
	* @return 
	* @see com.nhry.service.order.dao.OrderService#createDaliyPlansForIniOrders() 
	*/
	@Override
	public int createDaliyPlansForIniOrders()
	{
		List<TPreOrder> orders = tPreOrderMapper.selectIniOrders();
		
		orders.stream().forEach((order)->{
			ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(order.getOrderNo());
			
			/////////////////////////////
			//预付款的要付款+装箱才生成日计划
			if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getPaymentStat())){
				return;
			}
			//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
			if("20".equals(order.getMilkboxStat())){
				return;
			}

			BigDecimal allAmt = new BigDecimal("0.00");
			//重新计算每个行项目的数量，金额,截止日期等
			BigDecimal curAmt = order.getCurAmt();

			//计算每个行项目总共需要送多少天
			Map<TPlanOrderItem,Integer> entryMap = new HashMap<TPlanOrderItem,Integer>();
			int maxEntryDay = 365;
			Date firstDeliveryDate = null;
			for(TPlanOrderItem entry: entries){
				if(firstDeliveryDate==null){
					firstDeliveryDate = entry.getStartDispDate();
				}else{
					firstDeliveryDate = firstDeliveryDate.before(entry.getStartDispDate())?firstDeliveryDate:entry.getStartDispDate();
				}
				int entryDays = (daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate())) + 1;
				entryMap.put(entry,entryDays);
//				maxEntryDay = maxEntryDay > entryDays ? maxEntryDay : entryDays;
			}

			//根据最大配送天数的行
			int afterDays = 0;//经过的天数
			//行号唯一，需要判断以前最大的行号
			int daliyEntryNo = 0;//日计划行号
			
			outer:for(int i = 0; i < maxEntryDay; i++){
				for (TPlanOrderItem entry : entryMap.keySet()) {
					int days = entryMap.get(entry);
					if(days - 1 >= 0){
						//判断是按周期送还是按星期送
						Date today = afterDate(firstDeliveryDate,afterDays);
						
						if(entry.getStartDispDate().after(today))continue;
						
						entryMap.replace(entry, days-1);//剩余天数减1天
						
						if("10".equals(entry.getRuleType())){
							int gapDays = entry.getGapDays() + 1;//间隔天数
							if(daysOfTwo(entry.getStartDispDate(),today)%gapDays != 0){
								if(entry.getRuleTxt()!=null){
									List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
									if(deliverDays.size() > 0){//判断周6，7是否配送
										String weekday = getWeek(today);
										if(!deliverDays.contains(weekday)){
											continue;
										}
									}
								}else{
									continue;
								}
							}
						}
						else if("20".equals(entry.getRuleType())){
							String weekday = getWeek(today);
							List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
							if(!deliverDays.contains(weekday)){
								continue;//如果选择的星期几不送，则跳过今天生成日计划
							}
						}

						//生成该订单行的每日计划
						TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
						plan.setOrderNo(entry.getOrderNo());//订单编号
						plan.setOrderDate(order.getOrderDate());//订单日期
						plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
						plan.setItemNo(entry.getItemNo());//预订单日计划行
						plan.setDispDate(today);//配送日期
//						plan.setReachTime(entry.getReachTime());//送达时段
						plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
						plan.setMatnr(entry.getMatnr());//产品编号
						plan.setUnit(entry.getUnit());//配送单位
						plan.setQty(entry.getQty());//产品数量
						plan.setPrice(entry.getSalesPrice());//产品价格
						plan.setPromotionFlag(entry.getPromotion());//促销号
						//日计划行金额和
						BigDecimal qty = new BigDecimal(entry.getQty().toString());
						plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
						curAmt = curAmt.subtract(plan.getAmt());
						
						//当订单余额小于0时停止
						if(curAmt.floatValue() < 0)break outer;
						
						allAmt = allAmt.add(plan.getAmt());
						plan.setRemainAmt(curAmt);//订单余额
						plan.setStatus("10");//状态
						plan.setCreateAt(new Date());//创建时间
						plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
						plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
						
						tOrderDaliyPlanItemMapper.insert(plan);
						daliyEntryNo++;
						
					}else{
						continue;
					}
				}
				afterDays++;
			}
			/////////////////////////////更新订单的总金额 用allAmt
//			order.setInitAmt(allAmt);
//			order.setCurAmt(allAmt);
			
		});
		
		return 1;
	}

	/* (non-Javadoc) 
	* @title: replaceOrdersDispmember
	* @description: 更换a送奶员的订单为b
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#replaceOrdersDispmember(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int replaceOrdersDispmember(OrderSearchModel record)
	{
		return tPreOrderMapper.replaceOrdersDispmember(record);
	}
	
	/* (non-Javadoc) 
	* @title: replaceOrdersDispmember
	* @description: 预览日计划
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#replaceOrdersDispmember(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public List<TOrderDaliyPlanItem> viewDaliyPlans(OrderCreateModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = record.getOrder();
		List<TPlanOrderItem> entriesList = new ArrayList<TPlanOrderItem>();
		
		//信息交验
		validateOrderInfo(record);
		//暂时生成订单号
		order.setOrderNo(CodeGeneratorUtil.getCode());
		//其他订单信息 1
		
		//生成每个订单行 2
		int index = 0;
		BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
		for(TPlanOrderItem entry: record.getEntries()){
			entry.setOrderNo(order.getOrderNo());
			entry.setItemNo(order.getOrderNo() + String.valueOf(index));//行项目编号
			entry.setRefItemNo(String.valueOf(index));//参考行项目编号
			try
			{
				entry.setStartDispDate(format.parse(entry.getStartDispDateStr()));
				if("20".equals(order.getPaymentmethod())){
					resolveEntryEndDispDate(entry);
				}else{
					entry.setEndDispDate(format.parse(entry.getEndDispDateStr()));
				}
			}
			catch (ServiceException e1){
				throw e1;
			}
			catch (Exception e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式有误");
			}
			orderAmt = orderAmt.add(calculateEntryAmount(entry));
			
			//促销判断
			if(StringUtils.isNotBlank(entry.getPromotion())&&"10".equals(order.getPaymentmethod()))throw new ServiceException(MessageCode.LOGIC_ERROR,"后付款的订单不能参加促销!");
			promotionService.calculateEntryPromotion(entry);
			
			entriesList.add(entry);

			index++;
		}

		//订单价格
		order.setCurAmt(orderAmt);
		order.setInitAmt(orderAmt);
		
		//生成每日计划 3
		List<TOrderDaliyPlanItem> daliyPlans = new ArrayList<TOrderDaliyPlanItem>();
		BigDecimal curAmt = order.getCurAmt();
		//计算每个行项目总共需要送多少天
		Map<TPlanOrderItem,Integer> entryMap = new HashMap<TPlanOrderItem,Integer>();
		int maxEntryDay = 365;
		Date firstDeliveryDate = null;
		for(TPlanOrderItem entry: entriesList){
			if(firstDeliveryDate==null){
				firstDeliveryDate = entry.getStartDispDate();
			}else{
				firstDeliveryDate = firstDeliveryDate.before(entry.getStartDispDate())?firstDeliveryDate:entry.getStartDispDate();
			}
			int entryDays = (daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate())) + 1;
			entryMap.put(entry,entryDays);
		}
		//根据最大配送天数的行
		int afterDays = 0;//经过的天数
		//行号唯一，需要判断以前最大的行号
		int daliyEntryNo = 0;//日计划行号
		
		outer:for(int i = 0; i < maxEntryDay; i++){
			for (TPlanOrderItem entry : entryMap.keySet()) {
				int days = entryMap.get(entry);
				if(days - 1 >= 0){
					//判断是按周期送还是按星期送
					Date today = afterDate(firstDeliveryDate,afterDays);
					
					if(entry.getStartDispDate().after(today))continue;
					
					entryMap.replace(entry, days-1);//剩余天数减1天
					
					if("10".equals(entry.getRuleType())){
						int gapDays = entry.getGapDays() + 1;//间隔天数
						if(daysOfTwo(entry.getStartDispDate(),today)%gapDays != 0){
							if(entry.getRuleTxt()!=null){
								List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
								if(deliverDays.size() > 0){//判断周6，7是否配送
									String weekday = getWeek(today);
									if(!deliverDays.contains(weekday)){
										continue;
									}
								}
							}else{
								continue;
							}
						}
					}
					else if("20".equals(entry.getRuleType())){
						String weekday = getWeek(today);
						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
						if(!deliverDays.contains(weekday)){
							continue;//如果选择的星期几不送，则跳过今天生成日计划
						}
					}

					//生成该订单行的每日计划
					TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
					plan.setOrderNo(entry.getOrderNo());//订单编号
					plan.setOrderDate(entry.getOrderDate());//订单日期
					plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
					plan.setItemNo(entry.getItemNo());//预订单日计划行
					plan.setDispDate(today);//配送日期
//					plan.setReachTime(entry.getReachTime());//送达时段
					plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
					plan.setMatnr(entry.getMatnr());//产品编号
					plan.setMatnrTxt(entry.getMatnrTxt());//名称
					plan.setUnit(entry.getUnit());//配送单位
					plan.setQty(entry.getQty());//产品数量
					plan.setPrice(entry.getSalesPrice());//产品价格
					plan.setPromotionFlag(entry.getPromotion());//促销号
					//日计划行金额和
					BigDecimal qty = new BigDecimal(entry.getQty().toString());
					plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
					curAmt = curAmt.subtract(plan.getAmt());
					
					//当订单余额小于0时停止
					if(curAmt.floatValue() < 0)break outer;
					
					plan.setRemainAmt(curAmt);//订单余额
					plan.setStatus("10");//状态
					
					daliyEntryNo++;
					
					daliyPlans.add(0,plan);
				}else{
					continue;
				}
			}
			afterDays++;
		}
		
		//如果有赠品，生成赠品的日计划  4
		Map<String,TPlanOrderItem> entryMap2 = new HashMap<String,TPlanOrderItem>();
		//需要生成促销日计划的订单行项目
		entriesList.stream().filter((entry)->StringUtils.isNotBlank(entry.getPromotion()))
								  .forEach((e)->{
									  entryMap2.put(e.getItemNo(), e);
		});
		
		if(entryMap2.size() > 0){
			for(TOrderDaliyPlanItem plan : daliyPlans){
				if(entryMap2.containsKey(plan.getItemNo())){
					TPlanOrderItem orgEntry = entryMap2.get(plan.getItemNo());
					
					int totalGift = orgEntry.getGiftQty();
					if(totalGift<=0)break;
					
					//复制日计划
					TOrderDaliyPlanItem giftPlan = new TOrderDaliyPlanItem();
					giftPlan.setOrderNo(plan.getOrderNo());//订单编号
					giftPlan.setOrderDate(plan.getOrderDate());//订单日期
					giftPlan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
					giftPlan.setItemNo(plan.getItemNo());//预订单日计划行
					giftPlan.setDispDate(plan.getDispDate());//配送日期
					giftPlan.setReachTimeType(plan.getReachTimeType());//送达时段类型
					giftPlan.setMatnr(orgEntry.getGiftMatnr());//产品编号
					giftPlan.setMatnrTxt(orgEntry.getGiftMatnr());//TODO
					giftPlan.setUnit(orgEntry.getGiftUnit());//配送单位
					giftPlan.setPromotionFlag(orgEntry.getPromotion());//促销号
					
					//产品数量
					if(totalGift>=plan.getQty()){
						giftPlan.setQty(plan.getQty());
					}else{
						giftPlan.setQty(totalGift);
					}
					giftPlan.setGiftQty(giftPlan.getQty());//赠品数量
					giftPlan.setStatus("10");//状态
					
					//赠品数量减少
					orgEntry.setGiftQty(totalGift-giftPlan.getGiftQty());
					daliyPlans.add(0,giftPlan);
					
					if(orgEntry.getGiftQty()<=0)break;//赠完为止
					
					
					daliyEntryNo++;
				}
			}
		}
		
		daliyPlans.sort(new Comparator<TOrderDaliyPlanItem>(){
			@Override
			public int compare(TOrderDaliyPlanItem o1, TOrderDaliyPlanItem o2)
			{
				if(o1.getDispDate().before(o2.getDispDate()))
				{
					return -1;
				}else{
					if(o1.getDispDate().equals(o2.getDispDate())){
						if(o1.getRemainAmt().floatValue() > o2.getRemainAmt().floatValue()){
							return -1;
						}else{
							return 1;
						}
					}
				}
				return 1;
			}
			
   	});
		
		//后付款显示负数金额
		if("10".equals(order.getPaymentStat())){
			for(TOrderDaliyPlanItem p : daliyPlans){
				p.setRemainAmt(p.getRemainAmt().subtract(order.getInitAmt()));
			}
		}
		
		return daliyPlans;
	}

	/* (non-Javadoc) 
	* @title: recoverStopDaliyDaliyPlan
	* @description: 恢复停订的日计划
	* @param item
	* @return 
	* @see com.nhry.service.order.dao.OrderService#recoverStopDaliyDaliyPlan(com.nhry.data.order.domain.TOrderDaliyPlanItem) 
	*/
	@Override
	public int recoverStopDaliyDaliyPlan(TOrderDaliyPlanItem item)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(item.getOrderNo());
		
		//validate
		if("30".equals(orgOrder.getSign()) || !"10".equals(orgOrder.getPreorderStat()) )throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单的日计划不能恢复!");
		if(!new Date().before(item.getDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"今日与之前的日计划无法恢复!");
		if("20".equals(orgOrder.getSign()))throw new ServiceException(MessageCode.LOGIC_ERROR,"已停订的订单不能单日恢复日计划!");
		
		//预付款恢复
		if("20".equals(orgOrder.getPaymentmethod())){
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(item.getOrderNo());
			
			//原订单行单价
			BigDecimal orgPrice = null;
			TPlanOrderItem orgEntry = null;
			ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(item.getOrderNo());
			for(TPlanOrderItem entry :orgEntries){
				if(entry.getItemNo().equals(item.getItemNo())){
					orgPrice = entry.getSalesPrice();
					orgEntry = entry;
					break;
				}
			}
   		int remainQty = 0;
   		if(!orgEntry.getMatnr().equals(item.getMatnr())){
   			for(TOrderDaliyPlanItem p : daliyPlans){
   				if(p.getItemNo().equals(item.getItemNo()) && p.getMatnr().equals(item.getMatnr())){
   					remainQty = item.getQty()- p.getQty();
   					p.setQty(p.getQty() - item.getQty());
   					if(remainQty > 0){
   						//需要恢复的数量不够
   						p.setRemainAmt(new BigDecimal("-1"));
   					}else if(remainQty == 0){
   						//刚好够
   						remainQty = -1;
   						p.setRemainAmt(new BigDecimal("-1"));
   					}else{
   						//还有多
   						remainQty = -1;
   						p.setAmt(p.getPrice().multiply(new BigDecimal(p.getQty())));
   					}
   					break;
   				}
   			}
   		}
   		
   		int rate = 0 ;
   		if(remainQty > 0){
   			rate = new BigDecimal(remainQty).multiply(item.getPrice()).divide(orgPrice,2).setScale(2, BigDecimal.ROUND_CEILING).intValue();//恢复此行需要消耗多少个原商品
   		}else if(remainQty == 0){
   			rate = item.getAmt().divide(orgPrice,2).setScale(2, BigDecimal.ROUND_CEILING).intValue();//恢复此行需要消耗多少个原商品
   		}
   		
   		if(rate > 0){
   			for(TOrderDaliyPlanItem p : daliyPlans){//换商品停订后，延后的日计划如果再换商品会发生问题
   				if(p.getGiftQty() != null)continue;
   				if(p.getDispDate().equals(item.getDispDate()) && p.getItemNo().equals(item.getItemNo()))continue;
   				if("10".equals(p.getStatus()) && p.getItemNo().equals(item.getItemNo()) && p.getMatnr().equals(orgEntry.getMatnr()) ){
   					int qty = p.getQty();
   					if(rate - qty >= 0){
   						rate = rate - qty;
   						p.setQty(0);
   						p.setRemainAmt(new BigDecimal("-1"));
   					}else{
   						rate = 0;
   						p.setQty(qty - rate);
   						p.setAmt(p.getPrice().multiply(new BigDecimal(qty - rate)));
   					}
   					if(rate == 0)break;
   				}
   			}
   		}
   		if(rate > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,"原商品不足，无法恢复此日计划!");
   		
   		//日期排序完 asc
   		daliyPlans.sort(new Comparator<TOrderDaliyPlanItem>(){
   			@Override
   			public int compare(TOrderDaliyPlanItem o1, TOrderDaliyPlanItem o2)
   			{
   				if(o1.getDispDate().before(o2.getDispDate()))
   				{
   					return -1;
   				}else{
   					return  1;
   				}
   			}
      	});
   		
			Date endDate = null;
			BigDecimal initAmt = orgOrder.getInitAmt();
			
			List<TOrderDaliyPlanItem> dateList = new ArrayList<TOrderDaliyPlanItem>();
			for(TOrderDaliyPlanItem plan :daliyPlans){
				if(plan.getDispDate().equals(item.getDispDate()) && plan.getItemNo().equals(item.getItemNo()))plan.setStatus("10");//恢复
				if("30".equals(plan.getStatus()) || plan.getGiftQty()!=null)continue;
				if(plan.getRemainAmt().floatValue() < 0){
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
					continue;
				}
				initAmt = initAmt.subtract(plan.getAmt());
				plan.setRemainAmt(initAmt);
				tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
				dateList.add(0,plan);
				
				//截止日期更新
				if(endDate == null){
					endDate = plan.getDispDate();
				}else{
					if(plan.getRemainAmt().floatValue() >= 0){
						endDate = endDate.after(plan.getDispDate())?endDate:plan.getDispDate();
					}
				}
			}
			
			//赠品
			int idx = 0;
			Collections.reverse(daliyPlans);
			for(TOrderDaliyPlanItem plan :daliyPlans){
				if(plan.getGiftQty()==null || !plan.getItemNo().equals(item.getItemNo()))continue;
				
				TOrderDaliyPlanItem dp = dateList.get(idx);
				idx++;
				if(dp.getItemNo().equals(plan.getItemNo())){
					plan.setDispDate(dp.getDispDate());
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
				}
			}
			
			tOrderDaliyPlanItemMapper.deletePlansByAmt(orgOrder.getOrderNo());
			
			//更新订单
			orgOrder.setEndDate(endDate);
			tPreOrderMapper.updateOrderEndDate(orgOrder);
			
		}else{
		//后付款恢复，重新计算订单金额等信息
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(item.getOrderNo());
			
			orgOrder.setInitAmt(orgOrder.getInitAmt().add(item.getAmt()));
			orgOrder.setCurAmt(orgOrder.getCurAmt().add(item.getAmt()));
			BigDecimal initAmt = orgOrder.getInitAmt();
			Date endDate = null;
			
			for(TOrderDaliyPlanItem plan :daliyPlans){
				if(plan.getDispDate().equals(item.getDispDate()) && plan.getItemNo().equals(item.getItemNo()))plan.setStatus("10");//恢复
				if(!"30".equals(plan.getStatus())){
					initAmt = initAmt.subtract(plan.getAmt());
					//截止日期更新
					if(endDate == null){
						endDate = plan.getDispDate();
					}else{
						endDate = endDate.after(plan.getDispDate())?endDate:plan.getDispDate();
					}
				}
				plan.setRemainAmt(initAmt);
				tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
			}
			
			//更新订单
			orgOrder.setEndDate(endDate);
			tPreOrderMapper.updateOrderEndDate(orgOrder);
		}
		
		return 1;
	}

	/* (non-Javadoc) 
	* @title: returnOrderRemainAmtToAcct
	* @description:  退回所有完结订单的剩余金额到顾客帐户
	* @see com.nhry.service.order.dao.OrderService#returnOrderRemainAmtToAcct() 
	*/
	@Override
	public void returnOrderRemainAmtToAcct(String orderNo,Date dispDate)
	{
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
		
		//完结日期不是配送那天，非预付款单，剩余金额不大于0, return
		if(order==null)return;
		if(!order.getEndDate().equals(dispDate) || !"20".equals(order.getPaymentmethod()) || order.getCurAmt().floatValue() <= 0)return;
		
		//退回剩余金额
		TVipAcct ac = new TVipAcct();
	   ac.setVipCustNo(order.getMilkmemberNo());
	   ac.setAcctAmt(order.getCurAmt());
		tVipCustInfoService.addVipAcct(ac);
	}
	
	/* (non-Javadoc) 
	* @title: returnOrderRemainAmtToAcct
	* @description:  再修改路单时，先恢复原日计划和原订单金额,在修改
	* @see com.nhry.service.order.dao.OrderService#returnOrderRemainAmtToAcct() 
	*/
	@Override
	public void reEditDaliyPlansByRouteDetail(RouteDetailUpdateModel newItem , TDispOrderItem orgItem ,Date dispDate){
		
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orgItem.getOrgOrderNo());
		TPlanOrderItem planEntry = tPlanOrderItemMapper.selectEntryByEntryNo(orgItem.getOrgItemNo());
		ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgItem.getOrgOrderNo());
		
		//赠品可以直接修改
		if("Y".equals(orgItem.getGiftFlag())){
			//赠品的，只更新原日计划
			for(TOrderDaliyPlanItem p : daliyPlans){
				if(p.getDispDate().equals(dispDate) && orgItem.getOrgItemNo().equals(p.getItemNo()) && p.getGiftQty()!= null){
					p.setMatnr(orgItem.getConfirmMatnr());
					p.setQty(orgItem.getConfirmQty().intValue());
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
					break;
				}
			}
			return;
		}
		
		//其他需要先恢复再修改
		//没有变化的
		if(StringUtils.isBlank(orgItem.getReason()) && orgItem.getConfirmQty().intValue() == orgItem.getQty().intValue()){
			BigDecimal usedAmt = orgItem.getConfirmAmt();
			order.setCurAmt(order.getCurAmt().add(usedAmt));
			tPreOrderMapper.updateOrderCurAmt(order);
			
			return;
		}
		
		//原来是换货的
		if("10".equals(orgItem.getReason())){
			BigDecimal usedAmt = orgItem.getAmt();
			
			for(TOrderDaliyPlanItem p : daliyPlans){
				if(p.getDispDate().equals(dispDate) && orgItem.getOrgItemNo().equals(p.getItemNo()) && p.getGiftQty()==null){
					p.setMatnr(orgItem.getMatnr());
					p.setQty(orgItem.getQty().intValue());
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
					break;
				}
			}
			
			order.setCurAmt(order.getCurAmt().add(usedAmt));
			tPreOrderMapper.updateOrderCurAmt(order);
			
		}else{
		//原来不是换货的
			if("20".equals(order.getPaymentmethod())){
			//预付款的恢复
				int qty = orgItem.getQty().intValue() - orgItem.getConfirmQty().intValue();
				for(TOrderDaliyPlanItem p : daliyPlans){//找到原日计划恢复
					if("30".equals(p.getStatus())||p.getGiftQty()!=null)continue;
					if(p.getItemNo().equals(orgItem.getOrgItemNo()) && p.getDispDate().equals(dispDate) ){
						p.setQty(orgItem.getQty().intValue());
						p.setAmt(orgItem.getQty().multiply(p.getPrice()));
						break;
					}
				}
				for(TOrderDaliyPlanItem p : daliyPlans){//取消延后的日计划
					if("20".equals(p.getStatus())||"30".equals(p.getStatus())||p.getGiftQty()!=null)continue;
					if(p.getItemNo().equals(orgItem.getOrgItemNo()) && orgItem.getConfirmMatnr().equals(p.getMatnr())){
						qty = qty - p.getQty();
						if(qty >= 0 ){
							p.setQty(0);
							p.setRemainAmt(new BigDecimal("-1"));
						}else{
							p.setQty(p.getQty() - qty);
							p.setAmt(orgItem.getQty().multiply(p.getPrice()));
							break;
						}
						if(qty <= 0 )break;
					}
				}
				if(qty > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,"找不到延后的日计划或原商品不足！");
				
				order.setCurAmt(order.getCurAmt().add(orgItem.getConfirmAmt()));
				tPreOrderMapper.updateOrderCurAmt(order);
				
				//重新计算剩余金额
				Collections.reverse(daliyPlans);
				BigDecimal initAmt = order.getInitAmt();
				for(TOrderDaliyPlanItem p : daliyPlans){
					if(p.getRemainAmt().floatValue() < 0){
						tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
						continue;
					}
					if(!"30".equals(p.getStatus())){
						initAmt = initAmt.subtract(p.getAmt());
					}
					p.setRemainAmt(initAmt);
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
				}
				
				tOrderDaliyPlanItemMapper.deletePlansByAmt(order.getOrderNo());
				
			}else{
			//后付款的恢复	
				BigDecimal cj = orgItem.getAmt().subtract(orgItem.getConfirmAmt());
				
				for(TOrderDaliyPlanItem p : daliyPlans){
					if(p.getDispDate().equals(dispDate) && orgItem.getOrgItemNo().equals(p.getItemNo()) && p.getGiftQty()==null){
						p.setMatnr(orgItem.getMatnr());
						p.setQty(orgItem.getQty().intValue());
						p.setAmt(orgItem.getQty().multiply(orgItem.getPrice()));
						break;
					}
				}
				
				order.setInitAmt(order.getInitAmt().add(cj));
				order.setCurAmt(order.getCurAmt().add(orgItem.getAmt()));
				tPreOrderMapper.updateOrderCurAmtAndInitAmt(order);
				
				//重新计算剩余金额
				Collections.reverse(daliyPlans);
				BigDecimal initAmt = order.getInitAmt();
				for(TOrderDaliyPlanItem p : daliyPlans){
					if(!"30".equals(p.getStatus())){
						initAmt = initAmt.subtract(p.getAmt());
					}
					p.setRemainAmt(initAmt);
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
				}
				
			}
			
		}
	
	}

}
