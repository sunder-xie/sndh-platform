package com.nhry.service.order.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.*;
import com.nhry.service.BaseService;
import com.nhry.service.order.dao.OrderService;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderServiceImpl extends BaseService implements OrderService {
	
	private TPreOrderMapper tPreOrderMapper;
	private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
	private TPlanOrderItemMapper tPlanOrderItemMapper;
	
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
		
		return orderModel;
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
		return tPreOrderMapper.returnOrder(r);
	}

	/* (non-Javadoc) 
	* @title: 查询订单列表
	* @description: 
	* @return 
	* @see com.nhry.service.order.dao.OrderService#createOrder() 
	*/
	@Override
	public PageInfo searchOrders(OrderSearchModel smodel)
	{
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tPreOrderMapper.selectOrdersByPages(smodel);
	}

	/* (non-Javadoc) 
	* @title: 生成订单
	* @description: 生成订单与订单行项目
	* @return 
	* @see com.nhry.service.order.dao.OrderService#createOrder() 
	*/
	@Override
	public int createOrder(OrderCreateModel record)
	{	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = record.getOrder();
		List<TPlanOrderItem> entriesList = new ArrayList<TPlanOrderItem>();
		//暂时生成订单号		
		Date date = new Date();
		order.setOrderNo(String.valueOf(date.getTime()));
		//其他订单信息
		order.setOrderDate(date);//订单创建日期
		order.setCreaterBy(userSessionService.getCurrentUser().getLoginName());//创建人
		order.setCreaterNo(userSessionService.getCurrentUser().getGroupId());//创建人编号
		order.setOrderType("20");//订单类型 页面都是线下
		order.setPreorderSource("30");//订单来源  页面中来源都是30（奶站）
//		order.setMilkmemberNo(milkmemberNo);//喝奶人编号
//		order.setMemberNo(memberNo);//下单会员编号
//		order.setEmpNo(empNo);//送奶员编号
//		order.setInitAmt(initAmt);//页面输入的初始订单金额
		order.setPaymentStat(StringUtils.isBlank(order.getPaymentStat()) == true ? "20": order.getPaymentStat());//付款状态
		order.setMilkboxStat(StringUtils.isBlank(order.getMilkboxStat()) == true ? "20": order.getMilkboxStat());//奶箱状态
		order.setPreorderStat("20");//订单状态
//		order.setBranchNo(branchNo);//奶站编号 --人工分单或自动??
		
		//生成每个订单行
		int index = 0;
		BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
		for(TPlanOrderItem entry: record.getEntries()){
			entry.setOrderNo(order.getOrderNo());
			entry.setItemNo(order.getOrderNo().substring(9) + String.valueOf(index));//行项目编号
			entry.setRefItemNo(String.valueOf(index));//参考行项目编号
			entry.setOrderDate(date);//订单日期
			entry.setCreateAt(date);//创建日期
			entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
			entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
			try
			{
				entry.setStartDispDate(format.parse(entry.getStartDispDateStr()));
				entry.setEndDispDate(format.parse(entry.getEndDispDateStr()));
			}
			catch (ParseException e)
			{
				throw new ServiceException("日期格式有误!");
			}
			orderAmt = orderAmt.add(calculateEntryAmount(entry));
			entriesList.add(entry);
			
		   index++;
		}
		
		//保存订单，订单行
		order.setCurAmt(orderAmt);//订单价格
//		BigDecimal remain = order.getInitAmt().subtract(order.getCurAmt());//此为多余的钱，如果是预付款，将存入订户账户
//		if("20".equals(order.getPaymentStat()) && remain.floatValue() > 0){
//			
//		}
		tPreOrderMapper.insert(record.getOrder());
		for(TPlanOrderItem entry: entriesList){
			tPlanOrderItemMapper.insert(entry);
		}
		
		return createDaliyPlan(order,record.getEntries());//生成每日计划
	}
	
	//根据订单行生成每日计划
	private int createDaliyPlan(TPreOrder order ,List<TPlanOrderItem> entries){
		
		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
		if("20".equals(order.getMilkboxStat())){
			return 0;
		}
		
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
		int daliyEntryNo = 0;//日计划行号
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
					plan.setReachTime(entry.getReachTime());//送达时段
					plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
					plan.setMatnr(entry.getMatnr());//产品编号
					plan.setUnit(entry.getUnit());//配送单位
					plan.setQty(entry.getQty());//产品数量
					plan.setPrice(entry.getSalesPrice());//产品价格
					//日计划行金额和
					BigDecimal qty = new BigDecimal(entry.getQty().toString());
					plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
					curAmt = curAmt.subtract(plan.getAmt());
					plan.setRemainAmt(curAmt);//订单余额
					plan.setStatus("10");//状态
					plan.setCreateAt(entry.getCreateAt());//创建时间
					plan.setCreateBy(entry.getCreateBy());//创建人
					plan.setCreateByTxt(entry.getCreateByTxt());//创建人姓名
					
					tOrderDaliyPlanItemMapper.insert(plan);
					daliyEntryNo++;
				}else{
					continue;
				}
			}
			afterDays++;
		}
		
		return 1;
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
		//已生效10、未生效20、无效30
		if(StringUtils.isBlank(record.getPaymentStat()) && StringUtils.isBlank(record.getMilkboxStat())
				&& StringUtils.isBlank(record.getPreorderStat())){
			throw new ServiceException("更新信息与状态为空!");
		}
		if(tPreOrderMapper.selectByPrimaryKey(record.getOrderNo()) == null){
			throw new ServiceException("该订单号不存在!");
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
		TPreOrder curOrder = record.getOrder();
		ArrayList<TPlanOrderItem> curEntries = record.getEntries();
		
		//长期变更(10)需对订单进行统一更改，关联更改日订单 
		//修改订单根据行项目编号来确定行是否修改，换商品或改数量
		for(TPlanOrderItem orgEntry : orgEntries){
			for(TPlanOrderItem curEntry : curEntries){
				if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
					if(!orgEntry.getMatnr().equals(curEntry.getMatnr())){//换商品
						orgEntry.setMatnr(curEntry.getMatnr());
					}
					if(orgEntry.getQty() != curEntry.getQty()){//改数量
						orgEntry.setQty(curEntry.getQty());
					}
					if(!orgEntry.getRuleType().equals(curEntry.getRuleType())){//周期变更
						orgEntry.setRuleType(curEntry.getRuleType());
						orgEntry.setGapDays(curEntry.getGapDays());
						orgEntry.setRuleTxt(curEntry.getRuleTxt());
					}else{//相同时判断是周期送还是星期送
						if("10".equals(orgEntry.getRuleType())){
							orgEntry.setGapDays(curEntry.getGapDays());
							orgEntry.setRuleTxt(curEntry.getRuleTxt());
						}else if("20".equals(orgEntry.getRuleType())){
							orgEntry.setRuleTxt(curEntry.getRuleTxt());
						}
					}
					if(!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())){//送奶时段变更
						orgEntry.setReachTimeType(curEntry.getReachTimeType());
					}
					//比较配送日期是否修改
					String startstr = format.format(curEntry.getStartDispDateStr());
					String endstr = format.format(curEntry.getEndDispDateStr());
					if(!startstr.equals(orgEntry.getStartDispDate()) || !endstr.equals(orgEntry.getEndDispDate()) ){
						try
						{
							orgEntry.setStartDispDate(format.parse(startstr));
							orgEntry.setEndDispDate(format.parse(endstr));
						}
						catch (ParseException e)
						{
							throw new ServiceException("日期格式有误!");
						}
					}
					//保存修改后的该行
					tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
					//删除不需要的日单
					TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
					newPlan.setOrderNo(orgOrder.getOrderNo());
					newPlan.setItemNo(orgEntry.getItemNo());
					newPlan.setDispDate(orgEntry.getStartDispDate());
					tOrderDaliyPlanItemMapper.deleteFromDateToDate(newPlan);
					//修改完毕
					break;
				}
			}
		}
			
		//生成新的每日订单
		createDaliyPlan(orgOrder , orgEntries);
		
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
		if("20".equals(orgOrder.getPaymentmethod()) ){
			for(TOrderDaliyPlanItem plan : record.getEntries()){
//				plan.getMatnr();//校验商品
				TOrderDaliyPlanItem orgPlan = daliyPlanMap.get(plan.getItemNo()+"/"+plan.getPlanItemNo());
				if("30".equals(orgPlan.getStatus())){//如果已停订，跳过
					continue;
				}
				if("30".equals(plan.getStatus())){
					orgPlan.setStatus(plan.getStatus());
					continue;
				}
				orgPlan.setMatnr(plan.getMatnr());
				orgPlan.setQty(plan.getQty());
				orgPlan.setUnit(plan.getUnit());
				orgPlan.setPrice(plan.getPrice());
				orgPlan.setAmt(orgPlan.getPrice().multiply(new BigDecimal(orgPlan.getQty().toString())));
				orgPlan.setLastModified(new Date());
				orgPlan.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
				orgPlan.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
				tOrderDaliyPlanItemMapper.updateDaliyPlanItem(orgPlan);
			}
			
		//先付款的	,日订单往后延
		}else if("10".equals(orgOrder.getPaymentmethod()) ){
			for(TOrderDaliyPlanItem plan : record.getEntries()){
//				plan.getMatnr();//校验商品
				TOrderDaliyPlanItem orgPlan = daliyPlanMap.get(plan.getItemNo()+"/"+plan.getPlanItemNo());
				if("30".equals(orgPlan.getStatus())){//如果已停订，跳过
					continue;
				}
				if("30".equals(plan.getStatus())){
//					orgPlan.setStatus(plan.getStatus());
					stopPlans.put(orgPlan, plan);
					continue;
				}
				//变更产品
				if(!orgPlan.getMatnr().equals(plan.getMatnr())){
					changeProductPlans.put(orgPlan,plan);
//					orgPlan.setMatnr(plan.getMatnr());
//					orgPlan.setQty(plan.getQty());
				}
				//变更数量
				else if(orgPlan.getQty()!=plan.getQty()){
					changeQtyPlans.put(orgPlan,plan);
//					orgPlan.setQty(plan.getQty());
				}
//				orgPlan.setUnit(plan.getUnit());
//				orgPlan.setPrice(plan.getPrice());
//				orgPlan.setAmt(orgPlan.getPrice().multiply(new BigDecimal(orgPlan.getQty().toString())));
//				orgPlan.setLastModified(new Date());
//				orgPlan.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
//				orgPlan.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
//				tOrderDaliyPlanItemMapper.updateDaliyPlanItem(orgPlan);
			}
			//换商品或换数量要处理或停订
			changeProductOrQty(orgOrder,orgEntries,daliyPlans,changeProductPlans,changeQtyPlans,stopPlans);
			
		}
			
		return 1;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	//计算间隔天数
	private int daysOfTwo(Date fDate, Date oDate) {

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
   	int allDays = daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate());//总共需要配送的天数
   	
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
   	
   	return amount.multiply(qty).multiply(new BigDecimal(String.valueOf(goDays)));
   }
   
   //变更产品或数量
   private void changeProductOrQty(TPreOrder orgOrder, ArrayList<TPlanOrderItem> orgEntries,List<TOrderDaliyPlanItem> daliyPlans, Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> changeProducts,Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> changeQtys,Map<TOrderDaliyPlanItem,TOrderDaliyPlanItem> stopPlans){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");//该商品该日期原日计划送多少个
//   	Map<String,Integer> daliyPlanQty = new LinkedHashMap<String,Integer>();
//   	List<TOrderDaliyPlanItem> zdaliyPlans =new ArrayList<TOrderDaliyPlanItem>();//以日期正向排序的日计划表
//   	for(TOrderDaliyPlanItem p: daliyPlans){
//   		daliyPlanQty.put(p.getItemNo()+format.format(p.getDispDate()), p.getQty());
//   		zdaliyPlans.add(0, p);
//   	}
   	
   	Map<String,Integer> needNewDaliyPlans = new HashMap<String,Integer>();//所有需要新增或减少的日计划行

   	Map<String,TPlanOrderItem> relatedEntryMap = new HashMap<String,TPlanOrderItem>();//key为订单行itemNo,value为entry
   	for(TPlanOrderItem entry : orgEntries){
   		if(!relatedEntryMap.containsKey(entry.getItemNo())){
   			relatedEntryMap.put(entry.getItemNo(), entry);
   		}
   	}
//   	int lastPlanNo = 0;//最大行号
//   	for(TOrderDaliyPlanItem plan: daliyPlans){
//   		if(lastPlanNo < Integer.parseInt(plan.getPlanItemNo())){
//   			lastPlanNo = Integer.parseInt(plan.getPlanItemNo());
//   		}
//   	}
   	
   	Map<String,BigDecimal> productMap = new HashMap<String,BigDecimal>();
   	for (TOrderDaliyPlanItem org : changeProducts.keySet()) {
   		TOrderDaliyPlanItem cur = changeProducts.get(org);
   		BigDecimal planAmt = cur.getPrice().multiply(new BigDecimal(cur.getQty().toString()));
   		BigDecimal rate = planAmt.subtract(org.getAmt()).divide(new BigDecimal(org.getQty()).setScale(2, BigDecimal.ROUND_FLOOR));
   		String itemNo = org.getItemNo();//原日计划的商品号
   		if(!productMap.containsKey(itemNo)){
         	productMap.put(itemNo, rate);
         }else{
         	productMap.replace(itemNo, rate.add(productMap.get(itemNo)));
         }
   		
   	}
   	
   	//针对于原订单行要减少或者增加多少件商品
   	for(String key : productMap.keySet()){
   		int needDays = productMap.get(key).setScale(0, BigDecimal.ROUND_FLOOR).intValue();//需要减少或增加的原商品数量
   		if(needDays>=1){
   			int passDays=0;
   			needNewDaliyPlans.put(key, needDays);
   		}else if(needDays<=-1){
   			needNewDaliyPlans.put(key, needDays);
   		}
   	}
   	
   	//数量，当value为负说明该产品行日计划要增加延后value件商品，正说明要提前
   	for (TOrderDaliyPlanItem org : changeQtys.keySet()) {
   		TOrderDaliyPlanItem cur = changeQtys.get(org);
   		int c = cur.getQty() - org.getQty();
   		if(needNewDaliyPlans.containsKey(org.getItemNo())){
   			needNewDaliyPlans.replace(org.getItemNo(), (-c) + needNewDaliyPlans.get(org.getItemNo()));
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
   			needNewDaliyPlans.get(plan.getItemNo());
   			if(needNewDaliyPlans.get(plan.getItemNo()) < 0){
      				if(plan.getQty() > 0 && (plan.getQty() - needNewDaliyPlans.get(plan.getItemNo()) > 0) ){
      					plan.setQty(plan.getQty() - needNewDaliyPlans.get(plan.getItemNo()));
      					needNewDaliyPlans.replace(plan.getItemNo(), 0);
      				}
      				else if(plan.getQty() > 0 && (plan.getQty() - needNewDaliyPlans.get(plan.getItemNo()) < 0)){
      					plan.setQty(needNewDaliyPlans.get(plan.getItemNo()) - plan.getQty());
      					needNewDaliyPlans.replace(plan.getItemNo(), needNewDaliyPlans.get(plan.getItemNo()) - plan.getQty() );
      				}
      				else{
      					continue;
      				}
   			}
   			//修改，需要新增日计划行
   			else if(needNewDaliyPlans.get(plan.getItemNo()) > 0){
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
					plan.setStatus(stopPlans.get(plan).getStatus());
					continue;
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
   	record.setDispDateStr(format.format(newPlans.get(0).getDispDate()));
   	tOrderDaliyPlanItemMapper.deleteFromDateToDate(record);
   	
   	//保存所有新的日计划
   	int index = 0;
   	for(TOrderDaliyPlanItem plan:newPlans){
   		plan.setPlanItemNo(String.valueOf(index));
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
						}
					}
				}
   		}
   		tOrderDaliyPlanItemMapper.insert(plan);
   		index++;
   	}
   	
   }
   
   //延续日单
   private void continueDaliyPlan(TPreOrder orgOrder, ArrayList<TPlanOrderItem> orgEntries, Map<String,TOrderDaliyPlanItem> stopPlans){
   	
   	for (String key : stopPlans.keySet()) {
   		
   		
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
   
}
