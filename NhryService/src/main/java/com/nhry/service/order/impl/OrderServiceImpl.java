package com.nhry.service.order.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.domain.TVipAcct;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.order.*;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.PriceService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.order.dao.MilkBoxService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.service.order.dao.PromotionService;
import com.nhry.service.order.pojo.OrderRemainData;
import com.nhry.utils.CodeGeneratorUtil;

import org.apache.commons.lang3.StringUtils;

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
		ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(orderCode);
		orderModel.setEntries(entries);
		orderModel.setOrder(tPreOrderMapper.selectByPrimaryKey(orderCode));
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
		return tPreOrderMapper.uptManHandOrder(uptManHandModel);
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
		r.setRetDate(new Date());
		r.setRetReason(r.getRetReason().trim());
		return tPreOrderMapper.returnOrder(r);
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
			String status = null;
			try
			{
				status = tOrderDaliyPlanItemMapper.getDayOrderStat(record.getOrderNo(), new Date());
			}
			catch (Exception e)
			{
				//没有当天已确认的日单
			}
			if("20".equals(status)){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"订单日订单已经确认，不能修改订单!");
			}
			if(tOrderDaliyPlanItemMapper.searchDaliyOrdersByOrderNoAndFinalStop(record).size()>0){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"长期停订前订单日订单已经停订，请往后修改日期或修改日计划!");
			}
			//停订逻辑
			try
			{
				Date sdate = format.parse(record.getOrderDateStart());
				order.setStopDateStart(sdate);
				if(StringUtils.isNotBlank(record.getOrderDateEnd())){
					Date edate = format.parse(record.getOrderDateEnd());
					order.setStopDateEnd(edate);
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
				BigDecimal remain = order.getCurAmt();
				TVipAcct ac = new TVipAcct();
				ac.setAcctAmt(remain);
				ac.setVipCustNo(order.getMilkmemberNo());
				tVipCustInfoService.addVipAcct(ac);
				
				tOrderDaliyPlanItemMapper.updateDaliyPlansToStop(order);

			}else{//后付款
				//用掉的钱，将存入订户账户
				BigDecimal remain = order.getCurAmt().subtract(order.getInitAmt());
				TVipAcct ac = new TVipAcct();
				ac.setAcctAmt(remain);
				ac.setVipCustNo(order.getMilkmemberNo());
				tVipCustInfoService.addVipAcct(ac);
				
				tOrderDaliyPlanItemMapper.updateDaliyPlansToStop(order);
				
			}
			//更新订单标示为停订,综合可能有很多订单
			if(order.getStopDateEnd()==null){
   			order.setSign("20");//停订中
			}
			modifyOrderStatus(order);
			
			//订奶收款通知???
			//todo
			//订户状态更改???
			List<TPreOrder> list = tPreOrderMapper.selectByMilkmemberNo(order.getMilkmemberNo());
			if(list==null||list.size()<=0){
				tVipCustInfoService.discontinue(order.getMilkmemberNo(), "30",null,null);
			}
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前订单不存在");
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
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		
		if(order!= null){
			String status = tOrderDaliyPlanItemMapper.getDayOrderStat(record.getOrderNo(), new Date());
			if("20".equals(status)){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"订单日订单已经确认，不能修改订单!");
			}
			//退订逻辑
//			try
//			{
//				Date sdate = format.parse(record.getOrderDate());
				order.setBackDate(new Date());
//			}
//			catch (ParseException e)
//			{
//				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式不正确!");
//			}
			order.setBackReason(record.getReason());
			String state = order.getPaymentmethod();
			
			if("20".equals(state)){//先付款
				tOrderDaliyPlanItemMapper.updateDaliyPlansToBack(order);
				
				//此为多余的钱，如果是预付款，将存入订户账户
				if(order.getInitAmt()!=null){
				   TVipAcct ac = new TVipAcct();
				   ac.setVipCustNo(order.getMilkmemberNo());
				   ac.setAcctAmt(order.getCurAmt());
					tVipCustInfoService.addVipAcct(ac);
				}
				
			}else{//后付款
				tOrderDaliyPlanItemMapper.updateDaliyPlansToBack(order);
				
				//用掉的钱，将存入订户账户
				BigDecimal remain = order.getCurAmt().subtract(order.getInitAmt());
				TVipAcct ac = new TVipAcct();
				ac.setAcctAmt(remain);
				ac.setVipCustNo(order.getMilkmemberNo());
				tVipCustInfoService.addVipAcct(ac);
					
			}
			//更新订单状态为退订
			order.setPreorderStat("30");//失效的订单
			order.setSign("30");//标示退订
			modifyOrderStatus(order);
			//订奶收款通知???
			//todo
			//订户状态更改???
			List<TPreOrder> list = tPreOrderMapper.selectByMilkmemberNo(order.getMilkmemberNo());
			if(list==null||list.size()<=0){
				tVipCustInfoService.discontinue(order.getMilkmemberNo(), "40",null,null);
			}
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前订单不存在");
		}
		
		return 1;
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
		ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrderNo());
		if(order!= null){
			Date edate = null;
			try
			{
				edate = format.parse(record.getOrderDateEnd());
			}
			catch (ParseException e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式不正确!");
			}
			
			if(edate.before(order.getEndDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"续订日期不能小于原订单截止日期!");
			String state = order.getPaymentmethod();
			
			//基本参考原单
			//新的订单号
			Date date = new Date();
			order.setOrderNo(CodeGeneratorUtil.getCode());
			//
			order.setOrderDate(date);
         if("20".equals(state)){
         	order.setPaymentStat("10");
         }
			//生成每个订单行
			List<TPlanOrderItem> entriesList = new ArrayList<TPlanOrderItem>();
			int index = 0;
			BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
			for(TPlanOrderItem entry: entries){
				entry.setOrderNo(order.getOrderNo());
				entry.setItemNo(order.getOrderNo() + String.valueOf(index));//行项目编号
				entry.setRefItemNo(String.valueOf(index));//参考行项目编号
				entry.setOrderDate(date);//订单日期
				entry.setCreateAt(date);//创建日期
				entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
				entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
				entry.setStartDispDate(afterDate(order.getEndDate(),1));
				entry.setEndDispDate(edate);
				orderAmt = orderAmt.add(calculateEntryAmount(entry));
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
			
			//续费的余额存入帐户
			if(StringUtils.isNotBlank(record.getGoAmt())){
				BigDecimal remain = new BigDecimal(record.getGoAmt()).subtract(orderAmt);
				TVipAcct account = new TVipAcct();
				account.setBranchNo(order.getBranchNo());
				account.setVipCustNo(order.getMilkmemberNo());
				account.setAcctAmt(remain);
				tVipCustInfoService.addVipAcct(account);
			}
			
			//订户状态更改
			tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",null,new com.nhry.utils.date.Date());
			
			//生成每日计划
			createDaliyPlan(order,entriesList);
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"原订单不存在");
		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: continueOrdeAfterStop
	* @description: 订单停订后续订
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#continueOrdeAfterStop(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public int continueOrdeAfterStop(OrderSearchModel record)
	{
		BigDecimal orderAmt = new BigDecimal("0.00");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrderNo());
		String startDateStr = record.getOrderDateStart().substring(0, 10);//续订开始日期
		Date startDate = null;
		try
		{
			startDate = format.parse(startDateStr);
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
			
			return 1;
		}
		//比较配送日期是否修改
//		int betweenDays = daysOfTwo(order.getStopDateStart(),order.getStopDateEnd());//订单停订到复订中间间隔了多少天
		List<TPlanOrderItem> needUpdate = new ArrayList<TPlanOrderItem>();
		for(TPlanOrderItem orgEntry :orgEntries){
			//计算复订后送到哪一天
			if(orgEntry.getStartDispDate().after(startDate)){
				//行项目配送开始日期在订单停订开始日期之后,更新行订单
				Date entryEndDate = afterDate(startDate,daysOfTwo(orgEntry.getStartDispDate(),orgEntry.getEndDispDate()));
				orgEntry.setStartDispDate(startDate);
				orgEntry.setEndDispDate(entryEndDate);
				
				orderAmt = orderAmt.add(calculateEntryAmount(orgEntry));
				//促销判断
				promotionService.calculateEntryPromotionForStop(orgEntry);
			}else{
				//更新行订单
				if(order.getStopDateEnd()==null){
					int godays = daysOfTwo(orgEntry.getStartDispDate(), order.getStopDateStart());
					int leftDays = daysOfTwo(orgEntry.getStartDispDate(),orgEntry.getEndDispDate()) - godays;
					if(leftDays<=0)continue;
   				Date entryEndDate = afterDate(startDate,leftDays);
   				orgEntry.setStartDispDate(startDate);
   				orgEntry.setEndDispDate(entryEndDate);
   				
   				orderAmt = orderAmt.add(calculateEntryAmount(orgEntry));
   				//促销判断
   				promotionService.calculateEntryPromotionForStop(orgEntry);
				}
			}
			
			//保存修改后的该行
			tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
			needUpdate.add(orgEntry);
		}
		
		order.setInitAmt(orderAmt);
		order.setCurAmt(orderAmt);
		
		//查询复订后的日计划余额
//		TOrderDaliyPlanItemKey key = new TOrderDaliyPlanItemKey();
//		key.setOrderNo(order.getOrderNo());
//		key.setItemNo(startDateStr);
//		BigDecimal amt = tOrderDaliyPlanItemMapper.selectDaliyPlansRemainAmt(key);
		
		//删除从复订时间开始的日计划
		TOrderDaliyPlanItem deleteKey= new TOrderDaliyPlanItem(); 
		deleteKey.setOrderNo(order.getOrderNo());
		deleteKey.setDispDateStr(startDateStr);
		tOrderDaliyPlanItemMapper.deleteFromDateToDate(deleteKey);
		
		//生成新的每日订单
		List<TOrderDaliyPlanItem> list = createDaliyPlanForResumeOrder(order , needUpdate , orderAmt);
		
//		如果有赠品，生成赠品的日计划
		promotionService.createDaliyPlanByPromotion(order,orgEntries,list);
		
		//更新订单
		
		order.setStopDateEnd(startDate);
//		order.setPreorderStat("10");//在订
		order.setSign("10");//标示在订
		order.setEndDate(calculateFinalDate(orgEntries));//订单截止日期修改
		tPreOrderMapper.updateOrderEndDate(order);
		
		//订户状态更改
		tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",null,null);
		
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
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tOrderDaliyPlanItemMapper.selectDaliyOrdersByPages(smodel);
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
//		order.setMemberNo(memberNo);//下单会员编号
//		order.setEmpNo(empNo);//送奶员编号
//		order.setInitAmt(initAmt);//页面输入的初始订单金额
		order.setPaymentStat(StringUtils.isBlank(order.getPaymentStat()) == true ? "10": order.getPaymentStat());//付款状态
		order.setPaymentmethod(order.getPaymentStat());//10 后款 20 先款 30 殿付款
		order.setMilkboxStat(StringUtils.isBlank(order.getMilkboxStat()) == true ? "20": order.getMilkboxStat());//奶箱状态
		if(StringUtils.isBlank(order.getPreorderStat())){
			order.setPreorderStat("10");//订单状态,初始确认
		}
		order.setSign("10");//在订状态
		order.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());//销售组织
		order.setDealerNo(userSessionService.getCurrentUser().getDealerId());//进销商
		//受款日期
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
//		order.setBranchNo(branchNo);//奶站编号 --人工分单或自动??
		//如果地址信息不为空，为订户创建新的地址
		if(record.getAddress() != null && "1".equals(record.getAddress().getAddressMode())){
			record.getAddress().setVipCustNo(order.getMilkmemberNo());
			order.setAdressNo(tVipCustInfoService.addAddressForCust(record.getAddress()));
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
				throw new ServiceException(MessageCode.LOGIC_ERROR,e.getMessage());
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
		if(order.getInitAmt()!=null){
			if("20".equals(order.getPaymentStat()) && order.getInitAmt().subtract(orderAmt).floatValue()<0)throw new ServiceException(MessageCode.LOGIC_ERROR,"付款方式为预付款!您支付的金额不足!");
			BigDecimal remain = order.getInitAmt().subtract(order.getCurAmt());
			if(record.getAccount() != null && "20".equals(order.getPaymentStat())){
				if(StringUtils.isBlank(record.getAccount().getBranchNo()))record.getAccount().setBranchNo(order.getBranchNo());
				if(StringUtils.isBlank(record.getAccount().getVipCustNo()))record.getAccount().setVipCustNo(order.getMilkmemberNo());
				record.getAccount().setAcctAmt(remain);
				tVipCustInfoService.addVipAcct(record.getAccount());
			}
		}
		
		order.setEndDate(calculateFinalDate(entriesList));//订单截止日期
		order.setInitAmt(orderAmt);
		
		//保存订单和行项目
		tPreOrderMapper.insert(record.getOrder());
		entriesList.forEach(entry->{
			tPlanOrderItemMapper.insert(entry);
		});
		
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
		
		return order.getOrderNo();
	}

	//根据订单行生成每日计划
	@Override
	public List<TOrderDaliyPlanItem> createDaliyPlan(TPreOrder order ,List<TPlanOrderItem> entries){

		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
		if("20".equals(order.getMilkboxStat())){
			return null;
		}

		List<TOrderDaliyPlanItem> daliyPlans = new ArrayList<TOrderDaliyPlanItem>();
		BigDecimal curAmt = order.getCurAmt();

		//计算每个行项目总共需要送多少天
		Map<TPlanOrderItem,Integer> entryMap = new HashMap<TPlanOrderItem,Integer>();
		int maxEntryDay = 0;
		for(TPlanOrderItem entry: entries){
			int entryDays = (daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate())) + 1;
			entryMap.put(entry,entryDays);
			maxEntryDay = maxEntryDay > entryDays ? maxEntryDay : entryDays;
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
		for(int i = 0; i < maxEntryDay; i++){
			for (TPlanOrderItem entry : entryMap.keySet()) {
				int days = entryMap.get(entry);
				if(days - 1 >= 0){
					entryMap.replace(entry, days-1);//剩余天数减1天

					//判断是按周期送还是按星期送
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
					if(curAmt.floatValue() < 0)break;
					
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
		//未收款10、已收款20、垫付款30
		//已装箱10、未装箱20、无需装箱30
		//已生效10、未生效20、无效30,作废40
		//先款10、后付款20
		//在订10、停订20、退订30
		if(record.getMilkmemberNo()!=null){
			//更改订户
			tPreOrderMapper.updateOrderStatus(record);
			//订户状态更改
			tVipCustInfoService.discontinue(record.getMilkmemberNo(), "10",new com.nhry.utils.date.Date(),new com.nhry.utils.date.Date());
			
			return 1;
		}
		if("10".equals(record.getPreorderStat())){
			TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
			if(StringUtils.isBlank(order.getMilkmemberNo()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单没有订户，请选择订户或新建订户!");
			//订户状态更改
			tVipCustInfoService.discontinue(record.getMilkmemberNo(), "10",new com.nhry.utils.date.Date(),new com.nhry.utils.date.Date());
		}
		if(StringUtils.isBlank(record.getPaymentStat()) && StringUtils.isBlank(record.getMilkboxStat())
				&& StringUtils.isBlank(record.getPreorderStat()) && StringUtils.isBlank(record.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"更新信息与状态为空!");
		}
		if(tPreOrderMapper.selectByPrimaryKey(record.getOrderNo()) == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单号不存在!");
		}

		tPreOrderMapper.updateOrderStatus(record);

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
						
						orderAmt = orderUsedAmt.add(calculateEntryAmount(orgEntry));//订单总金额需要重新计算
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
//						String endstr = format.format(orgEntry.getEndDispDate());
						if(!startstr.equals(format.format(curEntry.getStartDispDate())) ){
							modiFlag = true;
							orgEntry.setStartDispDate(curEntry.getStartDispDate());
//							orgEntry.setEndDispDate(curEntry.getEndDispDate());
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
			
			//生成新的每日订单
   		createDaliyPlanForLongEdit(orgOrder , modifiedEntries);
   		
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
   				orgPlan.setMatnr(plan.getMatnr());
   				orgPlan.setQty(plan.getQty());
   				orgPlan.setUnit(plan.getUnit());
   				if(!orgPlan.getMatnr().equals(plan.getMatnr())){
   					float price = priceService.getMaraPrice(orgOrder.getBranchNo(), plan.getMatnr(), orgOrder.getDeliveryType());
   					cj = orgPlan.getAmt().subtract(new BigDecimal(String.valueOf(price)).multiply(new BigDecimal(orgPlan.getQty().toString())));
   					orgPlan.setPrice(new BigDecimal(String.valueOf(price)));
   				}
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
	{
//		record 路单详细条回执信息
//		entry 原路单行详细
//		orgEntry 原订单行
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(orgEntry.getOrderNo());
		
		if("10".equals(orgOrder.getPaymentmethod()))return;//后付款的不需要往后延期
		
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
		plan.setMatnr(orgEntry.getMatnr());//产品编号
		plan.setUnit(orgEntry.getUnit());//配送单位
		plan.setQty(lackQty);//产品数量
		plan.setPrice(orgEntry.getSalesPrice());//产品价格
		//日计划行金额和
		BigDecimal qty = new BigDecimal(String.valueOf(lackQty));
		plan.setAmt(orgEntry.getSalesPrice().multiply(qty));//金额小计
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
				if(format.format(item.getDispDate()).equals(format.format(dispDate))){
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
		
		calculateDaliyPlanRemainAmtAfterUptRoute(daliyPlans,orgOrder,dispDate);
		
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
   
   //计算订单行的总价格
   private BigDecimal calculateEntryAmount(TPlanOrderItem entry){
   	
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
   		goDays++;
   	}
   	//end
   	
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
   		BigDecimal rate = planAmt.subtract(org.getAmt()).divide(new BigDecimal(org.getQty()).setScale(2, BigDecimal.ROUND_FLOOR));
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
   		if(changeProducts.containsKey(plan) || changeQtys.containsKey(plan) || stopPlans.containsKey(plan)){
   			continue;
   		}
   		if(needNewDaliyPlans.containsKey(plan.getItemNo())){
   			//修改，需要的数量，从最后一个日期往前拿(从后往前扣商品数量)
   			if(needNewDaliyPlans.get(plan.getItemNo()) < 0){
   				   if(plan.getGiftQty()!=null)continue;//跳过促销产品
      				if(plan.getQty() > 0 && (plan.getQty() + needNewDaliyPlans.get(plan.getItemNo()) >= 0) ){
      					plan.setQty(plan.getQty() + needNewDaliyPlans.get(plan.getItemNo()));
      					needNewDaliyPlans.replace(plan.getItemNo(), 0);
      				}
      				else if(plan.getQty() > 0 && (plan.getQty() + needNewDaliyPlans.get(plan.getItemNo()) < 0)){
      					plan.setQty(needNewDaliyPlans.get(plan.getItemNo()) - plan.getQty());
      					needNewDaliyPlans.replace(plan.getItemNo(), needNewDaliyPlans.get(plan.getItemNo()) + plan.getQty() );
      				}
      				else{
      					continue;
      				}
   			}
   			//修改，需要新增日计划行
   			else if(needNewDaliyPlans.get(plan.getItemNo()) > 0){
   				TOrderDaliyPlanItem np = new TOrderDaliyPlanItem();
   				int needQty = needNewDaliyPlans.get(plan.getItemNo());//
   				int entryQty = relatedEntryMap.get(plan.getItemNo()).getQty();
   				if(entryQty>=needQty){
   					np.setQty(needQty);
   					needNewDaliyPlans.replace(plan.getItemNo(), 0);
   				}else{
   					np.setQty(entryQty);
   					needNewDaliyPlans.replace(plan.getItemNo(), needQty-entryQty);
   				}
   				convertDaliyPlan(plan, np);
   				newPlans.add(np);
   			}
   		}
   	}
   	
   	//删除数量为0的日计划,并重新生成新加的日计划
   	Map<String,Date> dateMap = new HashMap<String,Date>();//每个订单行的最后配送时间
   	for(TOrderDaliyPlanItem plan : daliyPlans){
   		if(plan.getQty() > 0){
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
   	List<Date> dateList = new ArrayList<Date>(); 
   	for(TOrderDaliyPlanItem plan:newPlans){
   		
   		//停订的和确认的，直接保存
   		plan.setPlanItemNo(String.valueOf(index));
   		if(stopPlans.containsKey(plan)||"20".equals(plan.getStatus())||"30".equals(plan.getStatus())){
   			tOrderDaliyPlanItemMapper.insert(plan);
   			index++;
   			continue;
   		}
   		
   		//跳过赠品，赠品日期需要放到最后几天
   		if(plan.getGiftQty()!=null)continue;
   		
   		//其他的重新计算剩余金额等信息
   		plan.setAmt(plan.getPrice().multiply(new BigDecimal(plan.getQty().toString())));//重新计算小记金额
   		curAmt = curAmt.subtract(plan.getAmt());//计算日计划的剩余金额
   		plan.setRemainAmt(curAmt);
   		
   		if(plan.getRemainAmt().floatValue() < 0)break;
   		
   		if(plan.getDispDate()==null){
   			
   			TPlanOrderItem entry = relatedEntryMap.get(plan.getItemNo());
   			Date lastDate = dateMap.get(plan.getItemNo());
   			//判断是按周期送还是按星期送
				if("10".equals(entry.getRuleType())){
					int gapDays = entry.getGapDays() + 1;//间隔天数
					for(int i=1;i<365;i++){
						Date today = afterDate(lastDate,i);
						if(i%gapDays !=0){
							if(entry.getRuleTxt()!=null){
								List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
								if(!deliverDays.contains(getWeek(today))){
									continue;
								}
							}
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
   		tOrderDaliyPlanItemMapper.insert(plan);
   		dateList.add(0,plan.getDispDate());
   		
   		index++;
   	}
   	
   	//订单截止日期修改
   	orgOrder.setEndDate(dateList.get(0));
		tPreOrderMapper.updateOrderEndDate(orgOrder);
   	
   	int i=0;
   	for(TOrderDaliyPlanItem plan:newPlans){
   		if(plan.getGiftQty()==null)continue;
   			plan.setDispDate(dateList.get(i));
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
   
   //计算订单的截止日期
   private Date calculateFinalDate(List<TPlanOrderItem> entries){
   	Date finalDate = new Date();
   	for(TPlanOrderItem entry : entries){
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
   		throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择送奶员!");
		}
   	if(record.getEntries()==null || record.getEntries().size() == 0){
   		throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择商品行!");
		}
   	if(StringUtils.isBlank(order.getMilkmemberNo())){
   		throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择订户!");
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
   private void calculateDaliyPlanRemainAmtAfterUptRoute(List<TOrderDaliyPlanItem> daliyPlans , TPreOrder order,Date dispDate){
   	BigDecimal curAmt = order.getCurAmt();
   	
   	List<TOrderDaliyPlanItem> needUpt = new ArrayList<TOrderDaliyPlanItem>();
   	
   	for(TOrderDaliyPlanItem plan : daliyPlans){
   		if(plan.getDispDate().compareTo(dispDate) == 0){
   			plan.setRemainAmt(curAmt);
   			plan.setStatus("20");//已确认送达,当天的确认
   			needUpt.add(plan);
   			continue;
   		}
   		if(!plan.getDispDate().after(dispDate))continue;
   		curAmt = curAmt.subtract(plan.getAmt());
   		plan.setRemainAmt(curAmt);
   		needUpt.add(plan);
   	}
   
   	needUpt.stream().forEach((e)->tOrderDaliyPlanItemMapper.updateDaliyPlanItem(e));
   
   }
   
   //根据订单行生成每日计划,针对复订的订单
 	private List<TOrderDaliyPlanItem> createDaliyPlanForResumeOrder(TPreOrder order ,List<TPlanOrderItem> entries,BigDecimal amt){

 		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
 		if("20".equals(order.getMilkboxStat())){
 			return null;
 		}
 		
 		List<TOrderDaliyPlanItem> list = new ArrayList<TOrderDaliyPlanItem>();
 		BigDecimal curAmt = amt;

 		//计算每个行项目总共需要送多少天
 		Map<TPlanOrderItem,Integer> entryMap = new HashMap<TPlanOrderItem,Integer>();
 		int maxEntryDay = 0;
 		for(TPlanOrderItem entry: entries){
 			int entryDays = (daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate())) + 1;
 			entryMap.put(entry,entryDays);
 			maxEntryDay = maxEntryDay > entryDays ? maxEntryDay : entryDays;
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
 		for(int i = 0; i < maxEntryDay; i++){
 			for (TPlanOrderItem entry : entryMap.keySet()) {
 				int days = entryMap.get(entry);
 				if(days - 1 > 0){
 					entryMap.replace(entry, days-1);//剩余天数减1天

 					//判断是按周期送还是按星期送
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
// 					plan.setReachTime(entry.getReachTime());//送达时段
 					plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
 					plan.setMatnr(entry.getMatnr());//产品编号
 					plan.setUnit(entry.getUnit());//配送单位
 					plan.setQty(entry.getQty());//产品数量
 					plan.setPrice(entry.getSalesPrice());//产品价格
 					//日计划行金额和
 					BigDecimal qty = new BigDecimal(entry.getQty().toString());
 					plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
 					
 					//当订单余额小于0时停止
 					if(curAmt.floatValue() < 0)break;
 					
 					plan.setRemainAmt(curAmt);//订单余额
 					curAmt = curAmt.subtract(plan.getAmt());
 					plan.setStatus("10");//状态
 					plan.setCreateAt(new Date());//创建时间
 					plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
 					plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
 					
 					tOrderDaliyPlanItemMapper.insert(plan);
 					daliyEntryNo++;
 					
 					list.add(0,plan);
 					
 				}else{
 					continue;
 				}
 			}
 			afterDays++;
 		}

 		return list;
 	}
 	
   //长期修改的订单,要根据订单的余额生成每日计划
 	private List<TOrderDaliyPlanItem> createDaliyPlanForLongEdit(TPreOrder order ,List<TPlanOrderItem> entries){

 		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
 		if("20".equals(order.getMilkboxStat())){
 			return null;
 		}

 		List<TOrderDaliyPlanItem> daliyPlans = new ArrayList<TOrderDaliyPlanItem>();
 		BigDecimal curAmt = order.getCurAmt();//订单余额

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
 	
 	
 	
 	
 	
}
