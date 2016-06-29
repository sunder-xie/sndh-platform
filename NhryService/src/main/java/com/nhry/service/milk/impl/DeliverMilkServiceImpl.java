package com.nhry.service.milk.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.data.milk.domain.TDispOrderKey;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milk.RouteOrderModel;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milk.RouteUpdateModel;
import com.nhry.service.BaseService;
import com.nhry.service.milk.dao.DeliverMilkService;

public class DeliverMilkServiceImpl extends BaseService implements DeliverMilkService
{
	private TDispOrderItemMapper tDispOrderItemMapper;
	private TDispOrderMapper tDispOrderMapper;
	private TPreOrderMapper tPreOrderMapper;
	private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
	
	public void settDispOrderItemMapper(TDispOrderItemMapper tDispOrderItemMapper)
	{
		this.tDispOrderItemMapper = tDispOrderItemMapper;
	}

	public void settDispOrderMapper(TDispOrderMapper tDispOrderMapper)
	{
		this.tDispOrderMapper = tDispOrderMapper;
	}
	
	public void settOrderDaliyPlanItemMapper(TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper)
	{
		this.tOrderDaliyPlanItemMapper = tOrderDaliyPlanItemMapper;
	}

	public void settPreOrderMapper(TPreOrderMapper tPreOrderMapper)
	{
		this.tPreOrderMapper = tPreOrderMapper;
	}

	/* (non-Javadoc) 
	* @title: searchOrders
	* @description: 查询路单列表
	* @param smodel
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchOrders(com.nhry.model.milk.RouteOrderSearchModel) 
	*/
	@Override
	public PageInfo searchRouteOrders(RouteOrderSearchModel smodel)
	{
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tDispOrderMapper.selectMilkboxsByPage(smodel);
	}

	/* (non-Javadoc) 
	* @title: searchRouteDetails
	* @description: 搜索路单详情
	* @param smodel
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchRouteDetails(com.nhry.model.milk.RouteOrderSearchModel) 
	*/
	@Override
	public RouteOrderModel searchRouteDetails(String orderNo)
	{
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(orderNo);
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		List<TDispOrderItem> entries = null;
		RouteOrderModel routeModel = null;
		if(dispOrder!=null){
			TDispOrderItemKey record = new TDispOrderItemKey();
			record.setOrderNo(orderNo);
			entries = tDispOrderItemMapper.selectItemsByKeys(record);
			
			//返回信息
			routeModel = new RouteOrderModel();
			routeModel.setOrder(dispOrder);
			routeModel.setEntries((ArrayList<TDispOrderItem>) entries);
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
		}
		
		return routeModel;
	}

	/* (non-Javadoc) 
	* @title: updateRouteOrder
	* @description: 更新路单状态
	* @param record
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#updateRouteOrder(com.nhry.model.milk.RouteUpdateModel) 
	*/
	@Override
	public int updateRouteOrder(RouteUpdateModel record)
	{
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(record.getOrderNo());
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		if(dispOrder!=null){
			tDispOrderMapper.updateDispOrderStatus(record.getOrderNo(),record.getStatus());
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
		}
		
		return 1;
	}

	/* (non-Javadoc) 
	* @title: updateRouteOrderItems
	* @description: 更新路单行项目
	* @param record
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#updateRouteOrderItems(com.nhry.model.milk.RouteUpdateModel) 
	*/
	@Override
	public int updateRouteOrderItems(RouteDetailUpdateModel record)
	{
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(record.getOrderNo());
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		if(dispOrder!=null){
			tDispOrderItemMapper.updateDispOrderItem(record);
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
		}
		return 1;
	}

	/* (non-Javadoc) 
	* @title: createDayRouteOder
	* @description: 生成每日路单
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#createDayRouteOder() 
	*/
	@Override
	public int createDayRouteOder()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<TPreOrder> dispLineNos = tPreOrderMapper.selectDispNoByGroup();
		TDispOrder dispOrder = null;
		List<TDispOrderItem> dispEntries = null;
		Date date = null;
		for(TPreOrder order : dispLineNos){
			date = new Date();
			dispOrder = new TDispOrder();
			dispEntries = new ArrayList<TDispOrderItem>();
			int totalQty = 0;
			BigDecimal totalAmt = new BigDecimal("0.00");
			//生成一条路线，一个配送时段的路单
			List<TOrderDaliyPlanItem> daliyPlans = tOrderDaliyPlanItemMapper.selectbyDispLineNo(order.getDispLineNo(),format.format(new Date()),order.getOrderType());
			
			//对每日计划的统计
			int index = 0;
			for(TOrderDaliyPlanItem plan : daliyPlans){
				TDispOrderItem item = new TDispOrderItem();
				totalQty += plan.getQty();
				totalAmt = totalAmt.add(plan.getAmt());
				
				//路单详细,一个日计划对应一行
				item.setOrderNo(String.valueOf(date.getTime()));
				item.setOrderDate(date);
				item.setItemNo(String.valueOf(index));
				item.setMatnr(plan.getMatnr());
				item.setUnit(plan.getUnit());
				item.setPrice(plan.getPrice());
				item.setAmt(plan.getAmt());
				item.setQty(new BigDecimal(plan.getQty()));
				item.setAddressNo(plan.getCreateByTxt());//配送地址用创建人字段临时读取,不需要再增加字段
				item.setStatus("20");//取消发货，待发货，已回执
				item.setOrgItemNo(plan.getItemNo());//对应原订单，订单行编号
				item.setOrgOrderNo(plan.getOrderNo());//对应原订单，订单编号
				
				dispEntries.add(item);
				index++;
			}
			
			//生成路单号
			dispOrder.setOrderNo(String.valueOf(date.getTime()));
			dispOrder.setAmt(totalAmt);
			dispOrder.setTotalQty(totalQty);
			dispOrder.setDispLineNo(order.getDispLineNo());
			dispOrder.setOrderDate(date);
			dispOrder.setDispDate(date);
			dispOrder.setStatus("10");//未确认
			dispOrder.setReachTimeType(order.getOrderType());//查询时用此字段代替了reachtimetype
//			dispOrder.setBranchNo(order.getBranchNo());
//			dispOrder.setDispEmpNo(order.getEmpNo());
			
			//保存日单与日单行
			tDispOrderMapper.insert(dispOrder);
			if(dispEntries.size() == 0)continue;
			tDispOrderItemMapper.batchinsert(dispEntries);
		}
		
		return 1;
	}
	
	
	
}
