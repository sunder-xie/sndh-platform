package com.nhry.service.milk.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.milk.dao.TDispOrderChangeMapper;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milk.domain.*;
import com.nhry.data.milktrans.dao.TMstInsideSalOrderItemMapper;
import com.nhry.data.milktrans.dao.TMstInsideSalOrderMapper;
import com.nhry.data.milktrans.domain.TMstInsideSalOrder;
import com.nhry.data.milktrans.domain.TMstInsideSalOrderItem;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPlanOrderItemMapper;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.basic.MatnrAndQtyModel;
import com.nhry.model.milk.*;
import com.nhry.model.milktrans.CreateInSalOrderModel;
import com.nhry.model.milktrans.InSideSalOrderDetailSearchModel;
import com.nhry.model.milktrans.InSideSalOrderSearchModel;
import com.nhry.model.stock.StockModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.ProductService;
import com.nhry.service.milk.dao.DeliverMilkService;
import com.nhry.service.milk.pojo.TDispOrderChangeItem;
import com.nhry.service.milktrans.dao.ReturnBoxService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.service.stock.dao.TSsmStockService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.SerialUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeliverMilkServiceImpl extends BaseService implements DeliverMilkService
{
	private TDispOrderItemMapper tDispOrderItemMapper;
	private TDispOrderMapper tDispOrderMapper;
	private TPreOrderMapper tPreOrderMapper;
	private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
	private TDispOrderChangeMapper tDispOrderChangeMapper;
	private TPlanOrderItemMapper tPlanOrderItemMapper;
	private OrderService orderService;
	private ProductService productService;
	private ReturnBoxService returnBoxService;
	private TSsmStockService tSsmStockService;

	public void settSsmStockService(TSsmStockService tSsmStockService) {
		this.tSsmStockService = tSsmStockService;
	}

	public void setReturnBoxService(ReturnBoxService returnBoxService)
	{
		this.returnBoxService = returnBoxService;
	}
	
	public void setProductService(ProductService productService)
	{
		this.productService = productService;
	}
	
	public void setOrderService(OrderService orderService)
	{
		this.orderService = orderService;
	}
	
	private TMstInsideSalOrderMapper tMstInsideSalOrderMapper;
	private TMstInsideSalOrderItemMapper tMstInsideSalOrderItemMapper;

	public void settMstInsideSalOrderMapper(TMstInsideSalOrderMapper tMstInsideSalOrderMapper) {
		this.tMstInsideSalOrderMapper = tMstInsideSalOrderMapper;
	}

	public void settMstInsideSalOrderItemMapper(TMstInsideSalOrderItemMapper tMstInsideSalOrderItemMapper) {
		this.tMstInsideSalOrderItemMapper = tMstInsideSalOrderItemMapper;
	}

	public void settPlanOrderItemMapper(TPlanOrderItemMapper tPlanOrderItemMapper)
	{
		this.tPlanOrderItemMapper = tPlanOrderItemMapper;
	}
	
	public void settDispOrderChangeMapper(TDispOrderChangeMapper tDispOrderChangeMapper)
	{
		this.tDispOrderChangeMapper = tDispOrderChangeMapper;
	}

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

	/**
	 * 根据  路单号 生成 对应的内部销售订单
	 * @param dispOrderNo
	 * @return
     */
	@Override
	public int createInsideSalOrder(String dispOrderNo) {
			TSysUser user = userSessionService.getCurrentUser();
			TMstInsideSalOrder sOrder = tMstInsideSalOrderMapper.getInSalOrderByDispOrderNo(dispOrderNo);
			TDispOrder order = tDispOrderMapper.getDispOrderByNo(dispOrderNo);
			List<TDispOrderItem> entries = tDispOrderItemMapper.selectItemsByOrderNo(dispOrderNo);
			if(sOrder!=null){
				tMstInsideSalOrderItemMapper.delInSalOrderItemByOrderNo(sOrder.getInsOrderNo());
			}
			String insOrderNo = null;
			if(entries!=null && entries.size()>0){
				for(TDispOrderItem entry : entries){
					//更新库存
					if( !"20".equals(entry.getReason()) && !"10".equals(entry.getReason())) {
						tSsmStockService.updateStock(order.getBranchNo(), entry.getConfirmMatnr(), entry.getQty(), user.getSalesOrg());
					}else{
						tSsmStockService.updateStock(order.getBranchNo(), entry.getConfirmMatnr(), entry.getConfirmQty(), user.getSalesOrg());
					}
					if(entry.getReason()!=null && !"10".equals(entry.getReason()) && !"20".equals(entry.getReason()) && !"30".equals(entry.getReason())){
						if(insOrderNo==null){
							insOrderNo = SerialUtil.creatSeria();
						}
						TMstInsideSalOrderItem item = new TMstInsideSalOrderItem();
						item.setInsOrderNo(insOrderNo);
						item.setItemNo(entry.getItemNo());
						item.setOrgOrderNo(entry.getOrgOrderNo());
						item.setMatnr(entry.getMatnr());
						item.setOrderDate(order.getOrderDate());
						item.setPrice(entry.getPrice());
						item.setQty(entry.getQty().subtract(entry.getConfirmQty()));
						item.setReason(entry.getReason());
						tMstInsideSalOrderItemMapper.insertOrderItem(item);
					}
				}
				if(insOrderNo!=null){

					sOrder = new TMstInsideSalOrder();
					sOrder.setInsOrderNo(insOrderNo);
					sOrder.setOrderDate(order.getOrderDate());
					sOrder.setDispOrderNo(order.getOrderNo());
					sOrder.setBranchNo(order.getBranchNo());
					sOrder.setSalEmpNo(order.getDispEmpNo());
					tMstInsideSalOrderMapper.insertInsideSalOrder(sOrder);
				}

			}

			return 1;



	}

	@Override
	public PageInfo getInsideSalOrder(InSideSalOrderSearchModel sModel) {
		TSysUser user = userSessionService.getCurrentUser();
		sModel.setBranchNo(user.getBranchNo());
		return tMstInsideSalOrderMapper.getAuthAllInsideSalOrder(sModel);
	}

	@Override
	public PageInfo getInsideSalOrderDetail(InSideSalOrderDetailSearchModel sModel) {
		TSysUser user = userSessionService.getCurrentUser();
		sModel.setSalesOrg(user.getSalesOrg());
		return tMstInsideSalOrderItemMapper.getInsideSalOrderDetail(sModel);
	}

	@Override
	public int createInsideSalOrderByStock(CreateInSalOrderModel cModel) {
		List<MatnrAndQtyModel> items = cModel.getMatnrs();
		if(items!=null && items.size()>0){
			Date date = new Date();
			TSysUser user = userSessionService.getCurrentUser();
			TMstInsideSalOrder order = new TMstInsideSalOrder();
			String insOrderNo = PrimaryKeyUtils.generateUuidKey();
			order.setSalEmpNo(cModel.getEmpNo());
			order.setInsOrderNo(insOrderNo);
			order.setOrderDate(date);
			order.setBranchNo(user.getBranchNo());
			for(int i=0 ;i <items.size();i++){
				MatnrAndQtyModel model = items.get(i);
				TMstInsideSalOrderItem item = new TMstInsideSalOrderItem();
				item.setOrderDate(date);
				item.setInsOrderNo(insOrderNo);
				//item.setReason("");
				item.setMatnr(model.getMatnr());
				item.setQty(new BigDecimal(model.getQty()));
				item.setItemNo(SerialUtil.creatSeria());
				tMstInsideSalOrderItemMapper.insertOrderItem(item);
				//更新库存
				tSsmStockService.updateStock(order.getBranchNo(),model.getMatnr(),new BigDecimal(model.getQty()),user.getSalesOrg());
			}
			tMstInsideSalOrderMapper.insertInsideSalOrder(order);

			//创建回瓶
			StockModel smodel = new StockModel();
			smodel.setBranchNo(user.getBranchNo());
			smodel.setEmpNo(cModel.getEmpNo());
			returnBoxService.craeteRetBotByStock(smodel);
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有选择产品！！！！");
		}

 		return 0;
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
		smodel.setBranchNo1(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		return tDispOrderMapper.searchRoutePlansByPage(smodel);
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(orderNo);
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		List<TDispOrderItem> entries = null;
		RouteOrderModel routeModel = new RouteOrderModel();
		if(dispOrder!=null){
			TDispOrderItemKey record = new TDispOrderItemKey();
			record.setOrderNo(orderNo);
			entries = tDispOrderItemMapper.selectItemsByKeys(record);
			StringBuffer sb = new StringBuffer();
			
			try{
				//昨天的回瓶
				TDispOrder record2 = new TDispOrder();
				record2.setDispEmpNo(dispOrder.getDispEmpNo());
				record2.setBranchName(format.format(afterDate(dispOrder.getDispDate(),-1)));//日期
				record2.setReachTimeType(dispOrder.getReachTimeType());
				TDispOrder tmpDispOrder = tDispOrderMapper.selectYestodayDispOrderByEmp(record2);
				if(tmpDispOrder!=null){
					String yestodayOrderNo = tmpDispOrder.getOrderNo();
					//查昨日的回瓶管理
					routeModel.setRetAmt(returnBoxService.getLastDayRets(yestodayOrderNo));
				}
				
				//处理每个产品应该送多少个
				Map<String,Integer> productMap = new HashMap<String,Integer>();
				Map<String,String> productNameMap = new HashMap<String,String>();
				entries.stream().forEach((e)->{
					if(!productNameMap.containsKey(e.getMatnr())){
						productNameMap.put(e.getMatnr(), e.getMatnrTxt());
					}
					if(productMap.containsKey(e.getMatnr())){
						productMap.replace(e.getMatnr(), productMap.get(e.getMatnr()) + e.getQty().intValue());
					}else{
						productMap.put(e.getMatnr(), e.getQty().intValue());
					}
				});
				productMap.keySet().stream().forEach((e)->{
					sb.append(productNameMap.get(e) + "(" + productMap.get(e) + "),");
				});
			}catch(Exception e){
				//暂时不处理
			}
			
			//返回信息
			routeModel.setOrder(dispOrder);
			routeModel.setProducts(sb.toString());
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
		}
		
		return routeModel;
	}

	/* (non-Javadoc) 
	* @title: searchRouteOrderDetail
	* @description: 搜索路单详情内详细表分页
	* @param smodel
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchRouteOrderDetail(com.nhry.model.milk.RouteOrderSearchModel) 
	*/
	@Override
	public PageInfo searchRouteOrderDetail(RouteOrderSearchModel smodel)
	{
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		//查询
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(smodel.getOrderNo());
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		if(dispOrder!=null){
			return tDispOrderItemMapper.selectRouteDetailsByPage(smodel);
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
		}
	}
	
	/* (non-Javadoc) 
	* @title: searchRouteOrderDetail
	* @description: 搜索路单详情内详细表不分页
	* @param smodel
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchRouteOrderDetail(com.nhry.model.milk.RouteOrderSearchModel) 
	*/
	@Override
	public List searchRouteOrderDetailAll(String code)
	{
		//查询
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(code);
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		if(dispOrder!=null){
			return tDispOrderItemMapper.selectRouteDetails(code);
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
		}
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
	* @description: 更新路单行项目  路单如果变更产品，需要更改库存 
	* @param record
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#updateRouteOrderItems(com.nhry.model.milk.RouteUpdateModel) 
	*/
	@Override
	public int updateRouteOrderAllItems(RouteDetailUpdateListModel record)
	{
		//如果更新了送奶员，更新该路单的送奶员
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(record.getRouteNo());
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		if(dispOrder==null)throw new ServiceException(MessageCode.LOGIC_ERROR,record.getRouteNo()+"[没有此路单号!]");
		
		dispOrder.setDispEmpNo(record.getDispEmpNo());
		tDispOrderMapper.updateDispOrderEmp(dispOrder);
		tDispOrderItemMapper.updateDispOrderItemEmp(dispOrder);
		
		//更新详细项目行
		Map<String,String> productMap = productService.getMataBotTypes();
		record.getList().stream().forEach((e)->{
			updateRouteOrderItems(e,productMap);
		});
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: updateRouteOrderItems
	* @description: 更新路单行项目  路单如果变更产品，需要更改库存 
	* @param record
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#updateRouteOrderItems(com.nhry.model.milk.RouteUpdateModel) 
	*/
	@Override
	public int updateRouteOrderItems(RouteDetailUpdateModel record,Map<String,String> productMap)
	{
//		final long startTime = System.currentTimeMillis();
		
//		TDispOrderKey key = new TDispOrderKey();
//		key.setOrderNo(record.getOrderNo());
//		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
//		
//		if(dispOrder!=null){
//			Date dispDate = dispOrder.getDispDate();
			TDispOrderItemKey itemKey = new TDispOrderItemKey();
			itemKey.setOrderNo(record.getOrderNo());
			itemKey.setItemNo(record.getItemNo());
			List<TDispOrderItem> entryList = tDispOrderItemMapper.selectItemsByKeys(itemKey);
			
			if(entryList.size() > 0){
				if(!"10".equals(record.getReason()) && Integer.parseInt(record.getConfirmQty()) > entryList.get(0).getQty().intValue())throw new ServiceException(MessageCode.LOGIC_ERROR,"非换货时实际数量不能大于应送数量！");
				if("10".equals(record.getReason()) && Integer.parseInt(record.getConfirmQty()) == 0)throw new ServiceException(MessageCode.LOGIC_ERROR,"换货时数量不能是0！");
   			TPlanOrderItem entry = tPlanOrderItemMapper.selectEntryByEntryNo(entryList.get(0).getOrgItemNo());
   			record.setOrgOrderNo(entryList.get(0).getOrgOrderNo());
   			record.setOrgItemNo(entryList.get(0).getOrgItemNo());
   			tDispOrderItemMapper.updateDispOrderItem(record , entry,productMap);
//   			//更新原订单剩余金额
//   			updatePreOrderCurAmt(entry.getOrderNo(),entry.getSalesPrice().multiply(new BigDecimal(record.getQty())));
//   			//更改路单,少送的，需要往后延期,并重新计算此后日计划的剩余金额
//   			orderService.resumeDaliyPlanForRouteOrder(record,entryList.get(0),entry,dispDate);
			}else{
				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"/"+record.getItemNo()+"[没有此路单详细号!]");
			}
			
//		}else{
//			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
//		}
		
//		System.out.println("修改路单 消耗时间："+(System.currentTimeMillis()-startTime)+"毫秒");
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: updateDaliyPlanByRouteOrder
	* @description: 由更新的路单，确认后，更新日计划
	* @param record
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#updateDaliyPlanByRouteOrder(com.nhry.model.milk.RouteDetailUpdateModel) 
	*/
	@Override
	public int updateDaliyPlanByRouteOrder(String routeCode)
	{
		final long startTime = System.currentTimeMillis();
		
		TDispOrderKey key = new TDispOrderKey();
		key.setOrderNo(routeCode);
		TDispOrder dispOrder = tDispOrderMapper.selectByPrimaryKey(key);
		
		if(dispOrder!=null){
			
			Date dispDate = dispOrder.getDispDate();
			List<TDispOrderItem> entryList = tDispOrderItemMapper.selectNotDeliveryItemsByKeys(routeCode);
			TOrderDaliyPlanItem record = new TOrderDaliyPlanItem();
			
			for(TDispOrderItem e : entryList){
				//变化的也更改日计划状态
				if( (StringUtils.isNotBlank(e.getReason()) && e.getConfirmQty().intValue() < e.getQty().intValue()) || !e.getMatnr().equals(e.getConfirmMatnr())  ){
					TPlanOrderItem entry = tPlanOrderItemMapper.selectEntryByEntryNo(e.getOrgItemNo());
					//更新原订单剩余金额
					if(e.getConfirmAmt()!=null){
						updatePreOrderCurAmt(entry.getOrderNo(),e.getConfirmAmt());
					}
					//更改路单,少送的，需要往后延期,并重新计算此后日计划的剩余金额
					orderService.resumeDaliyPlanForRouteOrder(e.getConfirmQty(), e, entry, dispDate);
				}else{
					//没有变化的路单更新日计划状态
					//更新原订单剩余金额
					TPlanOrderItem entry = tPlanOrderItemMapper.selectEntryByEntryNo(e.getOrgItemNo());

					if(e.getGiftFlag()==null){
						TPreOrder order = tPreOrderMapper.selectByPrimaryKey(entry.getOrderNo());
						order.setCurAmt(order.getCurAmt().subtract(e.getConfirmAmt()));
						tPreOrderMapper.updateOrderCurAmt(order);
					}
					
					//更新日计划为确认
					record.setOrderNo(e.getOrgOrderNo());
					record.setDispDate(dispDate);
					record.setItemNo(e.getOrgItemNo());
					record.setStatus("20");
					tOrderDaliyPlanItemMapper.updateDaliyPlanItemStatus(record);
					
				}
			}
			
			//路单更新为已经确认
			tDispOrderMapper.updateDispOrderStatus(routeCode,"20");
			
			//生成变化路单
//			createRouteChanges(routeCode,dispDate);
			
			//创建回瓶管理，调用
			returnBoxService.createDayRetBox(routeCode);
			
			//生成内部销售订单，调用
			createInsideSalOrder(routeCode);
			
			//找出今日会完结的预付款订单，如果有退款的，将退回订户帐户
			List<String> list = new ArrayList<String>();
			entryList.stream()
			.filter((e)->!list.contains(e.getOrgOrderNo()))
			.forEach((e)->{
				list.add(e.getOrgOrderNo());
				orderService.returnOrderRemainAmtToAcct(e.getOrgOrderNo(),dispDate);
			});
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此路单号!");
		}
		
		System.out.println("根据路单修改日计划 消耗时间："+(System.currentTimeMillis()-startTime)+"毫秒");
		
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
		if(userSessionService.getCurrentUser().getBranchNo()==null)throw new ServiceException(MessageCode.LOGIC_ERROR,"登陆人没有奶站，非奶站人员无法创建路单!");
//		if(tDispOrderMapper.selectTodayDispOrderByBranchNo(userSessionService.getCurrentUser().getBranchNo()).size()>0)throw new ServiceException(MessageCode.LOGIC_ERROR,"本日该奶站已经创建过路单!");
		List<TPreOrder> empNos = tPreOrderMapper.selectDispNoByGroup(userSessionService.getCurrentUser().getBranchNo());
		TDispOrder dispOrder = null;
		List<TDispOrderItem> dispEntries = null;
		Date date = afterDate(new Date(),1);
		Map<String,String> productMap = productService.getMataBotTypes();
		for(TPreOrder order : empNos){
			dispOrder = new TDispOrder();
			dispEntries = new ArrayList<TDispOrderItem>();
			int totalQty = 0;
			BigDecimal totalAmt = new BigDecimal("0.00");
			//生成一条路线，一个配送时段的路单
			List<TOrderDaliyPlanItem> daliyPlans = tOrderDaliyPlanItemMapper.selectbyDispLineNo(order.getEmpNo(),format.format(date),order.getOrderType());
			
			if(daliyPlans == null || daliyPlans.size() <= 0)continue;
				
			dispOrder.setOrderNo(PrimaryKeyUtils.generateUuidKey());
			//对每日计划的统计
			int index = 0;
			String empNo = order.getEmpNo();
			for(TOrderDaliyPlanItem plan : daliyPlans){
				TDispOrderItem item = new TDispOrderItem();
				totalQty += plan.getQty();
				totalAmt = totalAmt.add(plan.getAmt()==null?new BigDecimal("0.00"):plan.getAmt());
				
				//如果该行是赠品行，标记
				if(plan.getGiftQty()!=null)item.setGiftFlag("Y");
				//路单详细,一个日计划对应一行
//				if(empNo == null)empNo = plan.getLastModifiedByTxt();//配送人员id,字段临时读取,不需要再增加字段
				item.setOrderNo(dispOrder.getOrderNo());
				item.setOrderDate(date);
				item.setItemNo(String.valueOf(index));
				item.setMatnr(plan.getMatnr());
				item.setConfirmMatnr(plan.getMatnr());
				item.setUnit(plan.getUnit());
				item.setPrice(plan.getPrice());
				item.setAmt(plan.getAmt());
				item.setConfirmAmt(plan.getAmt());
				item.setQty(new BigDecimal(plan.getQty()));
				item.setConfirmQty(new BigDecimal(plan.getQty()));
				item.setAddressNo(plan.getCreateByTxt());//配送地址用创建人字段临时读取,不需要再增加字段
				item.setStatus("20");//取消发货，待发货，已回执
				item.setReachTimeType(plan.getReachTimeType());
				item.setOrgItemNo(plan.getItemNo());//对应原订单，订单行编号
				item.setOrgOrderNo(plan.getOrderNo());//对应原订单，订单编号
				item.setDispEmpNo(empNo);
				
				//回瓶规格
				if(productMap.containsKey(item.getMatnr())){
					if(productMap.get(item.getMatnr()).equals("10"))item.setRetQtyS(item.getQty().intValue());
					if(productMap.get(item.getMatnr()).equals("20"))item.setRetQtyM(item.getQty().intValue());
					if(productMap.get(item.getMatnr()).equals("30"))item.setRetQtyB(item.getQty().intValue());
				}
				
				dispEntries.add(item);
				index++;
			}
			
			//生成路单号
			dispOrder.setAmt(totalAmt);
			dispOrder.setTotalQty(totalQty);
//			dispOrder.setDispLineNo(order.getDispLineNo());
			dispOrder.setOrderDate(date);
			dispOrder.setDispDate(date);
			dispOrder.setStatus("10");//未确认
			dispOrder.setReachTimeType(order.getOrderType());
			dispOrder.setBranchNo(order.getBranchNo());
			dispOrder.setDispEmpNo(empNo);
			
			//保存日单与日单行
			tDispOrderMapper.insert(dispOrder);
			if(dispEntries.size() == 0)continue;
			tDispOrderItemMapper.batchinsert(dispEntries);
			
			createRouteChanges(dispOrder.getOrderNo(),date,empNo,dispOrder.getReachTimeType());
		}
		
		return 1;
	}

	/* (non-Javadoc) 
	* @title: createRouteChanges
	* @description: 生成变化路单  原因代码：10 变更产品 20 变更数量 30 增加订户 40 减少订户 50 变更配送时间
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#createRouteChanges() 
	*/
	@Override
	public int createRouteChanges()//此方法暂时不用
	{
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		List<TDispOrderChangeItem> list = tDispOrderItemMapper.selectDispItemsChange("2016-06-27","2016-06-26",null);
//		
////    测试用上面的，正式用下面
////		List<TDispOrderChangeItem> list = tDispOrderItemMapper.selectDispItemsChange( format.format(new Date()), format.format(afterDate(new Date(),-1)) );
//		
//		//对比每个不一样的前今日订单生成变化单，批量保存
//		List<TDispOrderChange> saveList = new ArrayList<TDispOrderChange>();
//		for(TDispOrderChangeItem item : list){
//			TDispOrderChange change = new TDispOrderChange();
//			if(StringUtils.isBlank(item.getOrderNo1()) && StringUtils.isNotBlank(item.getOrderNo2()) ){
//				//减少订户
//				change.setReason("40");
//				change.setYestodayMatnr(item.getMatnr2());
//				change.setYestodayQty(item.getQty2());
//			}else if(StringUtils.isBlank(item.getOrderNo2()) && StringUtils.isNotBlank(item.getOrderNo1())){
//				//新增订户
//				change.setReason("30");
//				change.setTodayMatnr(item.getMatnr1());
//				change.setTodayQty(item.getQty1());
//			}else if(!item.getItemNo1().equals(item.getItemNo2())){
//				//变更产品
//				change.setReason("10");
//				change.setYestodayMatnr(item.getMatnr2());
//				change.setTodayMatnr(item.getMatnr1());
//				change.setTodayQty(item.getQty1());
//				change.setYestodayQty(item.getQty2());
//			}else if(item.getQty1().intValue() != item.getQty2().intValue()){
//				//变更数量
//				change.setReason("20");
//				change.setTodayQty(item.getQty1());
//				change.setYestodayQty(item.getQty2());
//			}else if(!item.getReachTimeType1().equals(item.getReachTimeType2())){
//				//变更配送时间
//				change.setReason("50");
//			}else{
//				continue;
//			}
//			
//			change.setYestodayReachTimeType(item.getReachTimeType2());
//			change.setTodayReachTimeType(item.getReachTimeType1());
//			change.setOrderNo(StringUtils.isNotBlank(item.getOrderNo1())?item.getOrderNo1():item.getOrderNo2());
//			change.setOrderDate(item.getOrderDate1()!=null?item.getOrderDate1():item.getOrderDate2());
//			change.setOrgOrderNo(StringUtils.isNotBlank(item.getOrgOrderNo1())?item.getOrgOrderNo1():item.getOrgOrderNo2());
//			change.setOrgItemNo(StringUtils.isNotBlank(item.getOrgItemNo1())?item.getOrgItemNo1():item.getOrgItemNo2());
//			change.setAddressNo(StringUtils.isNotBlank(item.getAddressNo1())?item.getAddressNo1():item.getAddressNo2());
//			change.setEmpNo(StringUtils.isNotBlank(item.getDispEmpNo1())?item.getDispEmpNo1():item.getDispEmpNo2());
//			
//			saveList.add(change);
//		}
//		
//		if(saveList.size()==0)return 0;
//		tDispOrderChangeMapper.batchAddNewDispOrderChanges(saveList);
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: updatePreOrderCurAmt
	* @description: 回执后更新订单的剩余金额
	* @param orderCode
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#updatePreOrderCurAmt(java.lang.String) 
	*/
	@Override
	public int updatePreOrderCurAmt(String orderNo , BigDecimal amt)
	{
//		List<TDispOrderItem> list = tDispOrderItemMapper.selectItemsByConfirmed();
//		
//		for(TDispOrderItem item : list){
			TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
			if("10".equals(order.getPaymentmethod()))return 1;//后付款的暂时不处理
			order.setCurAmt(order.getCurAmt().subtract(amt));
			tPreOrderMapper.updateOrderCurAmt(order);
//		}
		
		return 1;
	}
	
	/* (non-Javadoc) 
	* @title: searchRouteChangeOrder
	* @description: 获取此路单的变化路单
	* @param code
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchRouteChangeOrder(java.lang.String) 
	*/
	@Override
	public List searchRouteChangeOrder(String code)
	{
		return tDispOrderChangeMapper.searchRouteChangeOrder(code);
	}
	
	/* (non-Javadoc) 
	* @title: searchRouteChangeOrder
	* @description: 路单重新修改
	* @param code
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchRouteChangeOrder(java.lang.String) 
	*/
	@Override
	public int reEditRouteDetail(TDispOrderItem item)
	{
		
		
		
		return 1;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//日期往前后加n天
	private Date afterDate(Date date, int days) {

		Calendar aCalendar =  Calendar.getInstance();
		aCalendar.setTime(date);
		aCalendar.add(aCalendar.DATE, days);//把日期往后增加一天.整数往后推,负数往前移动
		date=aCalendar.getTime();   //这个时间就是日期往后推一天的结果

		return date;
	}
	
	//一张路单，确认后，生成变化路单
	private int createRouteChanges(String orderNo,Date dispDate,String dispEmp,String reachTimeType)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<TDispOrderChangeItem> list = tDispOrderItemMapper.selectDispItemsChange(format.format(afterDate(dispDate,-1)),format.format(dispDate),orderNo,dispEmp,reachTimeType);
		
		//对比每个不一样的前今日订单生成变化单，批量保存
		List<TDispOrderChange> saveList = new ArrayList<TDispOrderChange>();
		for(TDispOrderChangeItem item : list){
			TDispOrderChange change = new TDispOrderChange();
			if(StringUtils.isBlank(item.getOrderNo1()) && StringUtils.isNotBlank(item.getOrderNo2()) ){
				//减少订户
				change.setReason("40");
			}else if(StringUtils.isBlank(item.getOrderNo2()) && StringUtils.isNotBlank(item.getOrderNo1())){
				//新增订户
				change.setReason("30");
			}else if(!item.getReachTimeType1().equals(item.getReachTimeType2())){
				//变更配送时间
				change.setReason("50");
			}else if(!item.getMatnr1().equals(item.getMatnr2())){
				//变更产品
				change.setReason("10");
			}else if(item.getQty1().intValue() != item.getQty2().intValue()){
				//变更数量
				change.setReason("20");
			}else{
				continue;
			}
			
			change.setYestodayMatnr(item.getMatnr2());
			change.setTodayMatnr(item.getMatnr1());
			change.setTodayQty(item.getQty1());
			change.setYestodayQty(item.getQty2());
			change.setYestodayReachTimeType(item.getReachTimeType2());
			change.setTodayReachTimeType(item.getReachTimeType1());
			//
			change.setOrderNo(orderNo);
			change.setOrderDate(dispDate);
			change.setOrgOrderNo(StringUtils.isNotBlank(item.getOrgOrderNo1())?item.getOrgOrderNo1():item.getOrgOrderNo2());
			change.setOrgItemNo(StringUtils.isNotBlank(item.getOrgItemNo1())?item.getOrgItemNo1():item.getOrgItemNo2());
			change.setAddressNo(StringUtils.isNotBlank(item.getAddressNo1())?item.getAddressNo1():item.getAddressNo2());
			change.setEmpNo(StringUtils.isNotBlank(item.getDispEmpNo1())?item.getDispEmpNo1():item.getDispEmpNo2());
			
			saveList.add(change);
		}
		
		if(saveList.size()==0)return 0;
		tDispOrderChangeMapper.batchAddNewDispOrderChanges(saveList);
		
		return 1;
	}

	/* (non-Javadoc) 
	* @title: createDispOrderdayliy
	* @description: 定时生成路单
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#createDispOrderdayliy() 
	*/
	@Override
	public int createDispOrderdayliy()
	{
		System.out.print("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq-----------------------------------------");
		return 0;
	}


}
