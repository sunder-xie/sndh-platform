package com.nhry.service.order.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.dao.TRequireOrderMapper;
import com.nhry.data.order.domain.*;
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
	private TRequireOrderMapper tRequireOrderMapper;

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

	public void settRequireOrderMapper(TRequireOrderMapper tRequireOrderMapper) {
		this.tRequireOrderMapper = tRequireOrderMapper;
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

	@Override
	public RequireOrderModel creatRequireOrder(RequireOrderModel rModel) {
		List<TMstRequireOrder> orderlist = this.tRequireOrderMapper.searchRequireOrder(rModel);
		if(orderlist!=null && orderlist.size()>0){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当天要货计划已存在");
		}
		List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(rModel);
		//将日预定单生成的要货计划存放数据库
		for(TOrderDaliyPlanItem entry :items ){
			TMstRequireOrder torder = new TMstRequireOrder();
			torder.setRequireDate(rModel.getRequireDate());
			torder.setBranchNo(rModel.getBranchNo());
			torder.setMatnr(entry.getMatnr());
			torder.setCreateAt(new Date());
			torder.setQty(entry.getQty());
			torder.setIncreQty(0);
			this.tRequireOrderMapper.insertRequireOrder(torder);
		}
		return this.searchRequireOrder(rModel);

	}

	@Override
	public int insertRequireOrder(TMstRequireOrder order) {
		return tRequireOrderMapper.insertRequireOrder(order);
	}

	/**
	 * 根据 奶站编号 和 日期 获取当前日期的要货计划
	 * @param rModel
	 * @return
	 */
	@Override
	public RequireOrderModel searchRequireOrder(RequireOrderModel rModel) {
		List<TMstRequireOrder> orderlist = this.tRequireOrderMapper.searchRequireOrder(rModel);
		RequireOrderModel orderModel = new RequireOrderModel();
		if(orderlist!= null && orderlist.size()>0){
			TMstRequireOrder rorder = orderlist.get(0);
			orderModel.setBranchNo(rorder.getBranchNo());
			orderModel.setRequireDate(rorder.getRequireDate());
			List<OrderRequireItem> entry = new ArrayList<OrderRequireItem>();
			for(TMstRequireOrder order :orderlist){
				OrderRequireItem item = new OrderRequireItem();
				item.setMatnr(order.getMatnr());
				item.setMatnrTxt(order.getMatnrTxt());
				item.setQty(order.getQty());
				item.setIncreQty(order.getIncreQty());
				entry.add(item);
			}
			orderModel.setEntries(entry);
		}
		return orderModel;
	}

	/**
	 * 修改要货计划
	 * @param rModel
	 * @return
     */
	@Override
	public int uptRequireOrder(RequireOrderModel rModel) {
		RequireOrderModel orderModel = this.searchRequireOrder(rModel);
		/*if(orderModel == orderModel){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"要货计划未发生变化");
		}*/
		int add = 0;
		int upt = -1;
		List<OrderRequireItem> items = rModel.getEntries();
		for(OrderRequireItem item : items){
			TMstRequireOrder tMstRequireOrder = new TMstRequireOrder();
			tMstRequireOrder.setBranchNo(rModel.getBranchNo());
			tMstRequireOrder.setRequireDate(rModel.getRequireDate());
			tMstRequireOrder.setMatnr(item.getMatnr());
			tMstRequireOrder.setQty(item.getQty());
			tMstRequireOrder.setIncreQty(item.getIncreQty());
			TMstRequireOrder oldItem = tRequireOrderMapper.selectRequireOrderItemByitem(tMstRequireOrder);
			if(oldItem == null ){
				//新增加的产品 : @TODO :是否需要判断信息的可靠性
				   add = tRequireOrderMapper.insertRequireOrder(tMstRequireOrder);
			}else{
				//该产品已存在
				if(oldItem.getIncreQty() != item.getIncreQty() || oldItem.getQty() != item.getQty()){
					//该产品做了修改
					upt = tRequireOrderMapper.uptRequireOrder(tMstRequireOrder);
				}
			}
		}
		return add + upt;
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
					plan.setStatus("N");//状态
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
	* @title: editOrder
	* @description: 修改订单
	* @param record
	* @return
	* @see com.nhry.service.order.dao.OrderService#editOrder(com.nhry.model.order.OrderCreateModel)
	*/
	@Override
	public int editOrder(OrderEditModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(record.getOrder().getOrderNo());
		ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrder().getOrderNo());
		TPreOrder curOrder = record.getOrder();
		ArrayList<TPlanOrderItem> curEntries = record.getEntries();
		//长期变更(10)需对订单进行统一更改，关联更改日订单  和 短期变更(20)短期变更对订户日订单进行修改
		if( "20".equals(record.getEditType()) ){
			//后付款的,日订单不往后延
			if("20".equals(orgOrder.getPaymentmethod()) ){
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
							//修改每日订单
							editDaliyPlanAfterPay(orgEntry);
							//修改完毕
							break;
						}
					}
				}

				//先付款的
			}else if("10".equals(orgOrder.getPaymentmethod()) ){
				for(TPlanOrderItem orgEntry : orgEntries){
					for(TPlanOrderItem curEntry : curEntries){
						if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
							if(!orgEntry.getMatnr().equals(curEntry.getMatnr())){//换商品

							}
							if(orgEntry.getQty() != curEntry.getQty()){//改数量

							}
							if(!orgEntry.getRuleType().equals(curEntry.getRuleType())){//周期变更

							}else{//相同时判断是周期送还是星期送
								if("10".equals(orgEntry.getRuleType())){

								}else if("20".equals(orgEntry.getRuleType())){

								}
							}
							if(!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())){//送奶时段变更
								orgEntry.setReachTimeType(curEntry.getReachTimeType());
							}
							//修改每日订单

							//保存修改后的该行

							//修改完毕
							break;
						}
					}
				}
			}

		}else if( "10".equals(record.getEditType()) ){
			//修改订单根据行项目编号来确定行是否修改，换商品或改数量
			for(TPlanOrderItem orgEntry : orgEntries){
				for(TPlanOrderItem curEntry : curEntries){
					if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
						if(!orgEntry.getMatnr().equals(curEntry.getMatnr())){//换商品

						}
						if(orgEntry.getQty() != curEntry.getQty()){//改数量

						}
						if(!orgEntry.getRuleType().equals(curEntry.getRuleType())){//周期变更

						}else{//相同时判断是周期送还是星期送
							if("10".equals(orgEntry.getRuleType())){

							}else if("20".equals(orgEntry.getRuleType())){

							}
						}
						if(!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())){//送奶时段变更
							orgEntry.setReachTimeType(curEntry.getReachTimeType());
						}
						//修改每日订单

						//保存修改后的该行

						//修改完毕
						break;
					}
				}
			}
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

		return amount.multiply(qty).multiply(new BigDecimal(String.valueOf(goDays)));
	}

	//修改每日计划单,后付款的,不延日单
	private void editDaliyPlanAfterPay(TPlanOrderItem entry){

		//计算变更订单行后的配送天

		afterDate(entry.getStartDispDate(),1);
		entry.getStartDispDate();
		entry.getEndDispDate();

		ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByEntryNo(entry.getItemNo());
		for(TOrderDaliyPlanItem daliyPlan : daliyPlans){
			Date dispDate = daliyPlan.getDispDate();
		}

		TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();


	}
}
