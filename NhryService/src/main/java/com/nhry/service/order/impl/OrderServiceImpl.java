package com.nhry.service.order.impl;


import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.service.BaseService;
import com.nhry.service.order.dao.OrderService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		//暂时生成订单号		
		Date date = new Date();
		order.setOrderNo(String.valueOf(date.getTime()));
		//其他订单信息
		order.setOrderDate(date);//订单创建日期
		order.setCreaterBy(userSessionService.getCurrentUser().getLoginName());//创建人
		order.setCreaterNo(userSessionService.getCurrentUser().getGroupId());//创建人编号
//		order.setOrderType(orderType);//订单类型
//		order.setPreorderSource(preorderSource);//订单来源
//		order.setOnlineorderNo(onlineorderNo);//线上订单编号
//		order.setMilkmemberNo(milkmemberNo);//喝奶人编号
//		order.setMemberNo(memberNo);//下单会员编号
//		order.setBranchNo(branchNo);//奶站编号
//		order.setEmpNo(empNo);//送奶员编号
		order.setPaymentStat(StringUtils.isBlank(order.getPaymentStat()) == true ? "01": order.getPaymentStat());//付款状态
		order.setMilkboxStat(StringUtils.isBlank(order.getMilkboxStat()) == true ? "01": order.getMilkboxStat());//奶箱状态
		order.setPreorderStat("01");//订单状态
      //保存订单
		tPreOrderMapper.insert(record.getOrder());
		
		//保存每个订单行
		int index = 0;
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
//			entry.setSalesPrice(salesPrice);//销售单价
			if("01".equals(entry.getRuleType())){//产品是按间隔还是按星期送
				
			}
			
		   tPlanOrderItemMapper.insert(entry);
		   index++;
		}
		
		//生成每日计划
		return createDaliyPlan(record.getEntries());
	}
	
	//根据订单行生成每日计划
	private int createDaliyPlan(List<TPlanOrderItem> entries){
		
		//计算每个行项目总共需要送多少天
		Map<TPlanOrderItem,Integer> entryMap = new HashMap<TPlanOrderItem,Integer>();
		int maxEntryDay = 0;
		for(TPlanOrderItem entry: entries){
			int entryDays = (daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate())) + 1;
			entryMap.put(entry,entryDays);
			maxEntryDay = maxEntryDay > entryDays ? maxEntryDay : entryDays;
		}

		//根据最大配送天数的行
		int afterDays = 0;
		int daliyEntryNo = 0;
		for(int i = 0; i < maxEntryDay; i++){
			for (TPlanOrderItem entry : entryMap.keySet()) {
				int days = entryMap.get(entry);
				if(days - 1 > 0){
					entryMap.replace(entry, days-1);//剩余天数减1天
					
					//判断是按周期送还是按星期送
					Date today = afterDate(entry.getStartDispDate(),afterDays);
					if("01".equals(entry.getRuleType())){
						int gapDays = entry.getGapDays() + 1;//间隔天数
						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
						if(deliverDays.size() > 0){//判断周6，7是否配送
							String weekday = getWeek(today);
							if(!deliverDays.contains(weekday)){
								if(afterDays%gapDays != 0){
									continue;
								}
							}
						}
					}
					else if("02".equals(entry.getRuleType())){
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
//					plan.setRemainAmt(remainAmt);//订单余额
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
	* @title: editOrder
	* @description: 修改订单
	* @param record
	* @return 
	* @see com.nhry.service.order.dao.OrderService#editOrder(com.nhry.model.order.OrderCreateModel) 
	*/
	@Override
	public int editOrder(OrderCreateModel record)
	{
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
		
		return 0;
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

}
