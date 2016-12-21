package com.nhry.service.order.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.*;
import com.nhry.data.basic.domain.*;
import com.nhry.data.bill.dao.CustomerBillMapper;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.config.dao.NHSysCodeItemMapper;
import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.dao.*;
import com.nhry.data.order.domain.*;
import com.nhry.data.stock.dao.TSsmGiOrderItemMapper;
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
import com.nhry.service.pi.dao.PIVipInfoDataService;
import com.nhry.service.pi.dao.PIVipPointCreateBatService;
import com.nhry.service.pi.dao.SmsSendService;
import com.nhry.service.pi.pojo.MemberActivities;
import com.nhry.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
	private PIVipInfoDataService piVipInfoDataService;
	private PIVipPointCreateBatService piVipPointCreateBatService;
	private TSsmGiOrderItemMapper tSsmGiOrderItemMapper;
	private SmsSendService smsSendService;
	private TDispOrderMapper tDispOrderMapper;
	private TMdAddressMapper addressMapper;
	private TMdOperationLogMapper operationLogMapper;
	private TMdMaraExMapper maraExMapper;
	private TMdBranchEmpMapper branchEmpMapper;
	private NHSysCodeItemMapper codeItemMapper;
	private TOrderDaliyPlanItemBackMapper tOrderDaliyPlanItemBackMapper;
   private TYearCardCompOrderMapper tYearCardCompOrderMapper;

	public void settYearCardCompOrderMapper(TYearCardCompOrderMapper tYearCardCompOrderMapper) {
		this.tYearCardCompOrderMapper = tYearCardCompOrderMapper;
	}

	public void settOrderDaliyPlanItemBackMapper(TOrderDaliyPlanItemBackMapper tOrderDaliyPlanItemBackMapper) {
		this.tOrderDaliyPlanItemBackMapper = tOrderDaliyPlanItemBackMapper;
	}

	public void setPiVipPointCreateBatService(PIVipPointCreateBatService piVipPointCreateBatService) {
		this.piVipPointCreateBatService = piVipPointCreateBatService;
	}

	public void setCodeItemMapper(NHSysCodeItemMapper codeItemMapper) {
		this.codeItemMapper = codeItemMapper;
	}

	public void setBranchEmpMapper(TMdBranchEmpMapper branchEmpMapper) {
		this.branchEmpMapper = branchEmpMapper;
	}

	public void setMaraExMapper(TMdMaraExMapper maraExMapper) {
		this.maraExMapper = maraExMapper;
	}

	public void setOperationLogMapper(TMdOperationLogMapper operationLogMapper) {
		this.operationLogMapper = operationLogMapper;
	}

	public void setAddressMapper(TMdAddressMapper addressMapper) {
		this.addressMapper = addressMapper;
	}

	public void settDispOrderMapper(TDispOrderMapper tDispOrderMapper)
	{
		this.tDispOrderMapper = tDispOrderMapper;
	}
	public void settSsmGiOrderItemMapper(TSsmGiOrderItemMapper tSsmGiOrderItemMapper) {
		this.tSsmGiOrderItemMapper = tSsmGiOrderItemMapper;
	}
	public SmsSendService getSmsSendService()
	{
		return smsSendService;
	}

	public void setSmsSendService(SmsSendService smsSendService)
	{
		this.smsSendService = smsSendService;
	}

	public void setPiVipInfoDataService(PIVipInfoDataService piVipInfoDataService) {
      this.piVipInfoDataService = piVipInfoDataService;
   }
	
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
	@Override
	public int searchReturnOrdersNum(){
		OrderSearchModel smodel = new OrderSearchModel();
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());
		return tPreOrderMapper.searchReturnOrdersNum(smodel);
	}

	@Override
	public BigDecimal calPreOrderTotalFactoryPrice(String orderNo) {
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
		BigDecimal result = new BigDecimal(0);
		if("20".equals(order.getPaymentmethod())){
			List<TPlanOrderItem> items = tPlanOrderItemMapper.selectByOrderCode(orderNo);
			if(items!=null && items.size()>0){
				for(TPlanOrderItem item : items){
					BigDecimal factoryPrice = tSsmGiOrderItemMapper.selectProximalFactoryPrice(item.getMatnr(),order.getBranchNo());
				}
			}
		}
		return result;
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
		smodel.setOrderDateStart(format.format(afterDate(new Date(),0)));
		smodel.setOrderDateEnd(format.format(afterDate(new Date(),7)));
		return tPreOrderMapper.selectStopOrderNum(smodel);
	}

	/* (non-Javadoc) 
	* @title: searchNeedResumeOrders
	* @description: 5天内需要续订的订单列表 (0-7 天)
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
			smodel.setOrderDateStart(format.format(afterDate(today,0)));
			smodel.setOrderDateEnd(format.format(afterDate(today,7)));
		}
		
		//权限
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());
		
		final long startTime = System.currentTimeMillis();
		PageInfo pageinfo = tPreOrderMapper.selectNeedResumeOrders(smodel);
		System.out.println("查询待续订列表 消耗时间："+(System.currentTimeMillis()-startTime)+"毫秒");
		
		return pageinfo;
	}
	/**
	 * @description: 根据电话查询5天内需要续订的订单列表 (0-7 天)
	 * */
	@Override
	public PageInfo searchReNeedOrdersByMp(OrderSearchModel smodel)
	{
		if(StringUtils.isBlank(smodel.getOrderDateStart()) || StringUtils.isBlank(smodel.getOrderDateEnd())){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			smodel.setOrderDateStart(format.format(afterDate(today,0)));
			smodel.setOrderDateEnd(format.format(afterDate(today,7)));
		}

		//权限
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());

		if(smodel.getMps()!=null && smodel.getMps().size()>1000){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"查询的电话号码不能大于1000条");
		}
		final long startTime = System.currentTimeMillis();
		PageInfo pageinfo = tPreOrderMapper.searchReNeedOrdersByMp(smodel);
		System.out.println("查询待续订列表 消耗时间："+(System.currentTimeMillis()-startTime)+"毫秒");

		return pageinfo;
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
		if(StringUtils.isNotBlank(order.getPromotion()) && StringUtils.isNotBlank(order.getPromItemNo())){
			TPromotion promotion = promotionService.selectPromotionByPromNoAndItemNo(order.getPromotion(),order.getPromItemNo());
			if(promotion!=null){
				if("Z016".equals(promotion.getPromSubType())){
					order.setPromType("整单满减");
				}else if("Z017".equals(promotion.getPromSubType())){
					order.setPromotion("年卡");
				}

			}
		}
		ArrayList<TPlanOrderItem> entries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectDetailByOrderCode(orderCode);
		orderModel.setEntries(entries);
		orderModel.setOrder(order);
//		//帐户
		orderModel.setAccount(tVipCustInfoService.findVipAcctByCustNo(orderModel.getOrder().getMilkmemberNo()));
//		//地址信息
		orderModel.setAddress(tVipCustInfoService.findAddressDetailById(orderModel.getOrder().getAdressNo()));
		
		//是否有日计划，是否有路单
		ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orderCode);
		if(daliyPlans!=null&&daliyPlans.size()>0)orderModel.setHasPlans("Y");
		if(tDispOrderItemMapper.selectCountByOrgOrder(orderCode)>0)orderModel.setHasRoute("Y");
		
		return orderModel;
	}
	
	/* (non-Javadoc)
    * @title: selectLatestOrder
    * @description: 查询该用户上一张订单的送奶员和订单号
    * @param orderCode
    * @return
    * @see com.nhry.service.order.dao.OrderService#selectLatestOrder(java.lang.String)
    */
   @Override
   public TPreOrder selectLatestOrder(String vipNo)
   {
   	return tPreOrderMapper.selectLastOrder(vipNo);
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

	@Override
	public PageInfo searchPendingConfirmUnOnline(OrderSearchModel smodel) {
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		return tPreOrderMapper.searchPendingConfirmUnOnline(smodel);
	}

	@Override
	public PageInfo searchPendingConfirmOnline(OrderSearchModel smodel) {
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		return tPreOrderMapper.searchPendingConfirmOnline(smodel);
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
		String orderNo = uptManHandModel.getOrderNo();
		String branchNo =  uptManHandModel.getBranchNo();
		if(org.apache.commons.lang.StringUtils.isBlank(branchNo) || org.apache.commons.lang.StringUtils.isBlank(orderNo)){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站号和订单号不能为空！");
		}
		TMdBranch branch = branchMapper.selectBranchByNo(branchNo);
		//如果更换奶站，可能会更换商品价格，并把用户挂到该奶站下
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
		if(order == null){throw new ServiceException(MessageCode.LOGIC_ERROR,"订单不存在");}
	/*	TVipCustInfo orderCust = tVipCustInfoService.findVipCustByNo(order.getMilkmemberNo());
		//地址一定是一个
		TMdAddress orderAddress = addressMapper.findAddressById(order.getAdressNo());
		Map<String,String> custMap = new HashMap<String,String>();
		custMap.put("branchNo",branchNo);
		custMap.put("phone",orderCust.getMp());
		TVipCustInfo branchCust = tVipCustInfoService.findStaCustByPhone(custMap);
		//保存原来的  订户no
		uptManHandModel.setRetReason(order.getMilkmemberNo());
		if("02".equals(branch.getBranchGroup())){
			uptManHandModel.setDealerNo(branch.getDealerNo());
		}
		if(branchCust!=null){
			//如果已经该奶站有这个订户电话  则将原订单订户ID保存在退订原因中，将分配的奶站订户ID 赋值到订单订户
			uptManHandModel.setMilkmemberNo(branchCust.getVipCustNo());
			//将改地址分给奶站订户
			orderAddress.setVipCustNo(branchCust.getVipCustNo());
			addressMapper.uptCustAddress(orderAddress);
		}else{
			//如果该奶站  没有这个订户电话  则将原订单订户ID保存在退订原因中，将改订户分配奶站
			//订户挂奶站,订单的奶站和销售组织变更
			String salesOrg = tVipCustInfoService.uptCustBranchNo(order.getMilkmemberNo(),branchNo);
			uptManHandModel.setSalesOrg(salesOrg);
		}
//		}*/
		/*//查看该奶站是否已经上线
		if("10".equals(branch.getIsValid())  &&  new Date().after(branch.getOnlineDate())){
			//该奶站已上线
			uptManHandModel.setIsValid("Y");
		}else{
			uptManHandModel.setIsValid("N");
		}*/
		uptManHandModel.setDealerNo(branch.getDealerNo());
		uptManHandModel.setIsValid("N");
		tPreOrderMapper.uptManHandOrder(uptManHandModel);
		
		//当是电商的订单时，更新EC对应订单的奶站
			if(!"30".equals(order.getPreorderSource()) && !"20".equals(order.getPreorderSource())){
				order.setBranchNo(uptManHandModel.getBranchNo());
				if("01".equals(branch.getBranchGroup())){
					order.setDealerNo(branch.getBranchNo());
				}else{
					order.setDealerNo(branch.getDealerNo());
				}

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
	public int returnOrder(UpdateManHandOrderModel r) {
		if( StringUtils.isBlank(r.getRetReason()) || StringUtils.isBlank(r.getOrderNo())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "信息不完整！");
		}
		TPreOrder  order = tPreOrderMapper.selectByPrimaryKey(r.getOrderNo());
		TSysUser user = userSessionService.getCurrentUser();
		//待确认订单退单日志
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER, OrderLogEnum.RETURN_ORDER,null,null,order.getBranchName(),
				null,null,null,user,operationLogMapper);
		/*if(StringUtils.isNotBlank(order.getRetReason())){
			if(order.getRetReason().equals(order.getMilkmemberNo())){
				//如果 分配奶站当时 没有该电话的订户  将订户退回，将订单退回
				//订户设为 无奶站订户
				TVipCustInfo orderCust = tVipCustInfoService.findVipCustByNo(order.getRetReason());
				orderCust.setBranchNo(null);
				tVipCustInfoService.updateVipCustByNo(orderCust);
			}else{
				//将订户地址 恢复到原来创建订户下面
				TMdAddress  address = addressMapper.findAddressById(order.getAdressNo());
				address.setVipCustNo(order.getRetReason());
				addressMapper.uptCustAddress(address);
				//将订单订户恢复到创建时状态
				r.setMilkmemberNo(order.getRetReason());
			}
		}*/
		r.setBranchNo("");
		r.setDealerNo("");
		r.setIsValid("N");
		r.setRetDate(new Date());
		r.setRetReason(r.getRetReason().trim());
		r.setMemoTxt(r.getMemoTxt());
		return tPreOrderMapper.returnOrder(r);
	}
	/* (non-Javadoc)
	* @title: orderConfirm
	* @description: 待确认订单确认
	* @param record
	* @return
	* @see com.nhry.service.order.dao.OrderService#orderConfirm(com.nhry.model.order.UpdateManHandOrderModel)
	*/

	@Override
	public int orderConfirm(UpdateManHandOrderModel uptManHandModel) {
		if( StringUtils.isBlank(uptManHandModel.getEmpNo()) || StringUtils.isBlank(uptManHandModel.getOrderNo())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "信息不完整！");
		}
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(uptManHandModel.getOrderNo());
		//处理订户  和 地址信息
		TVipCustInfo ordercust = tVipCustInfoService.findVipCustByNoForUpt(order.getMilkmemberNo());
		TMdAddress orderAddress = addressMapper.findAddressById(order.getAdressNo());
		if(StringUtils.isBlank(ordercust.getBranchNo())){
			Map<String,String> custMap = new HashMap<String,String>();
			custMap.put("branchNo",order.getBranchNo());
			custMap.put("phone",ordercust.getMp());
			TVipCustInfo branchCust = tVipCustInfoService.findStaCustByPhone(custMap);
			if(branchCust==null){
				ordercust.setBranchNo(order.getBranchNo());
				tVipCustInfoService.updateVipCustByNo(ordercust);
			}else{
				if(!ordercust.getVipCustNo().equals(branchCust.getVipCustNo())){
					order.setMilkmemberNo(branchCust.getVipCustNo());
					uptManHandModel.setMilkmemberNo(branchCust.getVipCustNo());
					List<TMdAddress> addlist= addressMapper.findCnAddressByCustNo(branchCust.getVipCustNo());
					if(addlist!=null && addlist.size()>0){
						boolean hasDefault = false;
						for(TMdAddress address : addlist){
							if("Y".equals(address.getIsDafault())){
								hasDefault = true;
								orderAddress.setIsDafault("N");
								break;
							}
						}
					}
					orderAddress.setVipCustNo(branchCust.getVipCustNo());
					addressMapper.uptCustAddress(orderAddress);
					tVipCustInfoService.deleteCustByCustNo(ordercust.getVipCustNo());
				}

			}

		}
//		if(StringUtils.isNoneBlank(order.getRetReason()) && tVipCustInfoService.findVipCustByNo(order.getRetReason())!=null){
//			if(!order.getRetReason().equals(order.getMilkmemberNo())){
//				uptManHandModel.setRetReason(null);
//				tVipCustInfoService.deleteCustByCustNo(order.getRetReason());
//			}
//		}

		TMdBranch branch = branchMapper.selectBranchByNo(order.getBranchNo());
		//如果该奶站已上线()
		if(StringUtils.isBlank(branch.getIsValid())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站是否上线字段未维护");
		}
		if("10".equals(branch.getIsValid()) && branch.getOnlineDate() ==null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站上线时间未维护");
		}
		if("10".equals(branch.getIsValid()) &&  DateUtil.dateAfter(new Date(),branch.getOnlineDate())){
			uptManHandModel.setPreorderStat("10");
			uptManHandModel.setIsValid("Y");
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站未上线，不能确认订单,请联系部门内勤进行维护");
		}
		tPreOrderMapper.orderConfirm(uptManHandModel);
		// 更新订单  金额
		BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
		//非奶站订单要重新计算金额
		if(!"30".equals(order.getPreorderSource())) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("orderNo",order.getOrderNo());
			map.put("salesOrg",order.getSalesOrg());
			List<TPlanOrderItem> entries = tPlanOrderItemMapper.selectEntriesByOrderNo(map);

			for (TPlanOrderItem entry : entries) {
				float price = priceService.getMaraPriceForCreateOrder(order.getBranchNo(), entry.getMatnr(), order.getDeliveryType(), branch.getSalesOrg());
				if (price <= 0){
					throw new ServiceException(MessageCode.LOGIC_ERROR, "重新计算产品价格失败，"+entry.getMatnr()+" 产品价格小于0,建议退回");
				}
				entry.setSalesPrice(new BigDecimal(String.valueOf(price)));
				BigDecimal entryAmount = BigDecimal.ZERO;
				orderAmt = orderAmt.add(calculateEntryAmount(entry));
				entryAmount = entryAmount.add(orderAmt);
				//促销判断
				if (StringUtils.isNotBlank(entry.getPromotion()) && "10".equals(order.getPaymentmethod()))
					throw new ServiceException(MessageCode.LOGIC_ERROR, "后付款的订单不能参加促销!");
				if(StringUtils.isNotBlank(order.getBranchNo()) && "20".equals(order.getPaymentmethod())){
					promotionService.calculateOrderEntryPromotion(entry,entryAmount,order);
				}
				tPlanOrderItemMapper.updateEntryByItemNo(entry);
			}
			//订单价格
			order.setCurAmt(orderAmt);
			order.setInitAmt(orderAmt);
			//保存订单金额 和 状态
			tPreOrderMapper.updateOrderCurAmtAndInitAmt(order);

			if(StringUtils.isNotBlank(order.getBranchNo())){
				//订户状态更改
				tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",new com.nhry.utils.date.Date(),new com.nhry.utils.date.Date());
			}


			//生成装箱工单
			if("20".equals(order.getMilkboxStat())){
				MilkboxCreateModel model = new MilkboxCreateModel();
				model.setCode(order.getOrderNo());
				milkBoxService.addNewMilkboxPlan(model);
			}

			//生成每日计划
			List<TOrderDaliyPlanItem> list = createDaliyPlan(order,entries);

			//如果有赠品，生成赠品的日计划
			promotionService.createDaliyPlanByPromotion(order,entries,list);

			//创建订单发送EC，发送系统消息(以线程方式),只有奶站的发，摆台的确认时发，电商不发

			if("01".equals(branch.getBranchGroup())){
				order.setDealerNo(EnvContant.getSystemConst("online_code"));
			}else{
				order.setDealerNo(branch.getDealerNo());
			}

			order.setPreorderStat("10");
			order.setIsValid("Y");
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("updateOrderBranchNo");
					messLogService.sendOrderBranch(order);
					if(!"10".equals(order.getPreorderSource()) && !"40".equals(order.getPreorderSource())){
						this.setName("sendOrderToEc");
						messLogService.sendOrderInfo(order, entries);
					}
					if(list!=null){
						TPreOrder sendOrder = new TPreOrder();
						sendOrder.setOrderNo(order.getOrderNo());
						sendOrder.setPreorderStat("200");
						sendOrder.setEmpNo(order.getEmpNo());
						messLogService.sendOrderStatus(sendOrder);
					}
				}
			});
		}
		return  1;
	}

	@Override
	public int batchorderConfirm(UpdateManHandOrderModel uptManHandModel){
		if(StringUtils.isNoneBlank(uptManHandModel.getOrderNo())){
			List<String> orderList = Arrays.asList(uptManHandModel.getOrderNo().split(","));
			orderList.stream().forEach((e)->{
				uptManHandModel.setOrderNo(e);
				orderConfirm(uptManHandModel);
			});
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"参数订单编号为空");
		}
		return 1;
	}

	@Override
	public int batchOrderConfirmUnOnline(UpdateManHandOrderModel uptManHandModel){
		if(StringUtils.isNoneBlank(uptManHandModel.getOrderNo())){
			List<String> orderList = Arrays.asList(uptManHandModel.getOrderNo().split(","));
			orderList.stream().forEach((e)->{
				uptManHandModel.setOrderNo(e);
				orderConfirmUnOnline(uptManHandModel);
			});
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"参数订单编号为空");
		}
		return 1;
	}


	@Override
	public int orderConfirmUnOnline(UpdateManHandOrderModel uptManHandModel) {
		if( StringUtils.isBlank(uptManHandModel.getOrderNo())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订单号不能为空！");
		}

		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(uptManHandModel.getOrderNo());
		TMdBranch branch = branchMapper.selectBranchByNo(order.getBranchNo());
		uptManHandModel.setIsValid("Y");
		tPreOrderMapper.orderConfirm(uptManHandModel);
		if(!"30".equals(order.getPreorderSource())){
			Map<String,String> map = new HashMap<String,String>();
			map.put("orderNo",order.getOrderNo());
			map.put("salesOrg",order.getSalesOrg());
			List<TPlanOrderItem> entries = tPlanOrderItemMapper.selectEntriesByOrderNo(map);
			if("01".equals(branch.getBranchGroup())){
				order.setDealerNo(branch.getBranchNo());
			}else{
				order.setDealerNo(branch.getDealerNo());
			}
			order.setPreorderStat("10");
			order.setIsValid("Y");
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("updateOrderBranchNo");
					messLogService.sendOrderBranch(order);
					if(!"10".equals(order.getPreorderSource()) && !"40".equals(order.getPreorderSource())){
						this.setName("sendOrderToEc");
						messLogService.sendOrderInfo(order, entries);
					}
					TPreOrder sendOrder = new TPreOrder();
					sendOrder.setOrderNo(order.getOrderNo());
					sendOrder.setPreorderStat("200");
					sendOrder.setEmpNo(order.getEmpNo());
					messLogService.sendOrderStatus(sendOrder);
				}
			});
		}
		return 0;
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
		if("20".equals(order.getPreorderStat())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服");
		}
		if("30".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,order.getOrderNo()+"该订单已退订不能做停订");
		}
		if("40".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,order.getOrderNo()+"该订单已完结不能做停订");
		}
		if("10".equals(order.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(order.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"该后付款已经有收款单了，请不要修改订单，或者去删除收款单!");
		}
		if("20".equals(order.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+order.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
			if(!"20".equals(order.getPaymentStat())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单"+order.getOrderNo()+" 还没有收款，不能做长停");
			}
		}

		if("10".equals(order.getPreorderSource())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服");
		}
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
		/*	if(tOrderDaliyPlanItemMapper.searchDaliyOrdersByOrderNoAndFinalStop(record).size()>0){
				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"长期停订前订单日订单已经停订，请往后修改日期或修改日计划!");
			}*/
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
				tOrderDaliyPlanItemMapper.updateDaliyPlansToStop(order);
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());

				//更新订单金额等
				BigDecimal initAmt = new BigDecimal("0.00");
				BigDecimal usedAmt = new BigDecimal("0.00");
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if("30".equals(plan.getStatus()))continue;
					initAmt = initAmt.add(plan.getAmt());
					if("20".equals(plan.getStatus())){
						usedAmt = usedAmt.add(plan.getAmt());
					}
				}

				order.setInitAmt(initAmt);
				order.setCurAmt(initAmt.subtract(usedAmt));

				//订单截止日期修改
//				for(TOrderDaliyPlanItem plan : daliyPlans){
//					if(!"30".equals(plan.getStatus())){
//						orgOrder.setEndDate(plan.getDispDate());
//						break;
//					}
//				}

				Collections.reverse(daliyPlans);
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if("30".equals(plan.getStatus())){
						plan.setRemainAmt(initAmt);
						tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
						continue;
					}
					plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
					initAmt = initAmt.subtract(plan.getAmt());
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
				}

				tPreOrderMapper.updateOrderEndDate(order);

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
		//停订日志
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.LONG_STOP_ORDER,null,null,null,
				record.getOrderDateStart(),null,null,userSessionService.getCurrentUser(),operationLogMapper);

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
		
		if("10".equals(order.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(order.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"该后付款已经有收款单了，请不要修改订单，或者去删除收款单!");
		}
		if("20".equals(order.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+order.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
			if(!"20".equals(order.getPaymentStat())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单"+order.getOrderNo()+" 还没有收款，不能做停订");
			}
		}
		if("20".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,order.getOrderNo()+"该订单已停订不能做停订");
		}
		if("30".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,order.getOrderNo()+"该订单已退订不能做停订");
		}
		if("40".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,order.getOrderNo()+"该订单已完结不能做停订");
		}
		
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
			/*if(tOrderDaliyPlanItemMapper.searchDaliyOrdersByOrderNoAndFinalStop(record).size()>0){
				throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"停订区间内 有日订单已经停订，请往后修改日期或修改日计划!");
			}*/
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
				//把后期日订单置回原样,重新计算金额
				TOrderDaliyPlanItem uptKey= new TOrderDaliyPlanItem(); 
				uptKey.setOrderNo(order.getOrderNo());
				uptKey.setDispDateStr(startDateStr);
				tOrderDaliyPlanItemMapper.updateFromDateToDate(uptKey);
				
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(record.getOrderNo());
				BigDecimal curInitAmt =BigDecimal.ZERO;
				BigDecimal curCurAmt =BigDecimal.ZERO;
				for(TOrderDaliyPlanItem p :daliyPlans){
					if("10".equals(p.getStatus())){
						curCurAmt = curCurAmt.add(p.getAmt());
					}
					if(!"30".equals(p.getStatus())){
						curInitAmt = curInitAmt.add(p.getAmt());
					}
				}
				orderAmt = curInitAmt;
				order.setCurAmt(curCurAmt);
				order.setInitAmt(curInitAmt);
				
				for(TOrderDaliyPlanItem p :daliyPlans){
					if(!"30".equals(p.getStatus())){
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
				//判断该订单是否是整单促销（整单满减或者年卡）
				boolean orderPromFlag = (StringUtils.isNotBlank(order.getPromotion()) && StringUtils.isNotBlank(order.getPromItemNo()))?true:false;
				//判断该订单是否是行项目促销（单品满减或者单品满赠）
				boolean itemPromFlag =false;
				TPromotion prom = null;
				if(orderPromFlag){
					prom = promotionService.selectPromotionByPromNoAndItemNo(order.getPromotion(),order.getPromItemNo());
					if(prom==null){
							throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单上记录的促销编号"+order.getPromotion()+"和行号"+order.getPromItemNo()+"，在订户系统中没有找到对应促销信息");
					}
				}else{
					// itemProms 保存有参加促销的行项目
					List<TPlanOrderItem> itemProms  = orgEntries.stream().filter(item->(StringUtils.isNotBlank(item.getPromotion()) && StringUtils.isNotBlank(item.getPromItemNo()))).collect(Collectors.toList());
					if(itemProms!=null && itemProms.size()>0){
						if(itemProms.size()>1)throw new ServiceException(MessageCode.LOGIC_ERROR,"一张订单不能有两个行项目同时参加促销");
						itemPromFlag = true;
						prom = promotionService.selectPromotionByPromNoAndItemNo(itemProms.get(0).getPromotion(),itemProms.get(0).getPromItemNo());
						if(prom==null){
							throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单行上记录的促销编号"+order.getPromotion()+"和行号"+order.getPromItemNo()+"，在订户系统中没有找到对应促销信息");
						}
					}
				}
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
				Map<String,Date> endDateMap = new HashMap<String,Date>();
				if(list!=null && list.size()>0){
					list.stream().forEach(e->{
						if(endDateMap.containsKey(e.getItemNo())){
							if(DateUtil.dateAfter(e.getDispDate(),endDateMap.get(e.getItemNo()))){
								endDateMap.replace(e.getItemNo(),e.getDispDate());
							}
						}else{
							endDateMap.put(e.getItemNo(),e.getDispDate());
						}
					});
					//回改订单行项目,更新最后配送日期
					for( TPlanOrderItem entry :orgEntries){
						if(endDateMap.containsKey(entry.getItemNo())){
							entry.setEndDispDate(endDateMap.get(entry.getItemNo()));
							if(orderPromFlag && prom!=null){
								if("Z016".equals(prom.getPromSubType())){
									if(DateUtil.dateAfter(entry.getEndDispDate(),prom.getPlanStopTime())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了整单满减促销，但是停订后"+entry.getMatnr()+"产品的截止日期"+format.format(entry.getEndDispDate())+"超过了促销截止日期"+format.format(prom.getPlanStopTime()));
									}
								}
							}else if(itemPromFlag && prom!=null){
								if("Z008".equals(prom.getPromSubType())){
									if(DateUtil.dateAfter(entry.getEndDispDate(),prom.getPlanStopTime())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满赠促销，但是停订后参加促销的"+entry.getMatnr()+"产品的截止日期"+format.format(entry.getEndDispDate())+"超过了促销截止日期"+format.format(prom.getPlanStopTime()));
									}
								}
								if("Z015".equals(prom.getPromSubType())){
									if(DateUtil.dateAfter(entry.getEndDispDate(),prom.getPlanStopTime())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满减促销，但是停订后参加促销的"+entry.getMatnr()+"产品的截止日期"+format.format(entry.getEndDispDate())+"超过了促销截止日期"+format.format(prom.getPlanStopTime()));
									}
								}
							}
							tPlanOrderItemMapper.updateEntryByItemNo(entry);
						}
						promotionService.calculateEntryPromotionForStop(entry);

					}
				}

			/*
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
	   			}*/
	   			
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
					record.setContent("Y");
					messLogService.sendOrderStopRe(record);
				}
			});
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"当前订单不存在");
		}

		//停订日志
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.STOP_ORDER,null,null,null,
				record.getOrderDateStart()+"至"+record.getOrderDateEnd(),null,null,userSessionService.getCurrentUser(),operationLogMapper);
		return 1;
	}

	/**
	 * 年卡退订 退款金额 前台输入
	 * 将退款金额退回到订户余额
	 * @param smodel
	 * @return
     */
	@Override
	public int yearCardBackOrder(YearCardBackModel smodel) {
		if(StringUtils.isBlank(smodel.getOrderNo()) || smodel.getBackAmt()==null||smodel.getShRefund()==null || smodel.getRealDiscount()==null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"年卡退订，订单号、应退金额、实际退款金额、实际折扣不能为空");
		}
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(smodel.getOrderNo());
		if("20".equals(order.getPreorderStat())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服");
		}
		TSysUser user = userSessionService.getCurrentUser();
		if(order!= null){
			if(StringUtils.isNotBlank(order.getPromotion())&&StringUtils.isNotBlank(order.getPromItemNo())){
				TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(order.getPromotion(),order.getPromItemNo());

				if(prom!=null){
					//年卡订单
					if("Z017".equals(prom.getPromSubType())){

						TMstYearCardCompOrder yOrder = tOrderDaliyPlanItemMapper.selectYearCardBackOrder(order.getOrderNo(),smodel.getBackDate());
						if(yOrder!=null){
							if(yOrder.getInitAmt()!=null){
								if(yOrder.getInitAmt().compareTo(smodel.getShRefund())==-1){
									throw new ServiceException(MessageCode.LOGIC_ERROR,"应退金额不能大于订单的总金额");
								}
							}else{
								throw new ServiceException(MessageCode.LOGIC_ERROR,"获取不到该订单的总金额");
							}
							order.setMemoTxt(smodel.getMemoTxt());
							order.setBackDate(smodel.getBackDate());
							order.setBackReason(smodel.getBackReason());
							//将日订单状态更新为停订
							tOrderDaliyPlanItemMapper.updateDaliyPlansToBack(order);

							//创建年卡折扣补偿单据
							yOrder.setCreateAt(new Date());
							yOrder.setCreateBy(user.getLoginName());
							yOrder.setCreateByTxt(user.getDisplayName());
							yOrder.setShRefund(smodel.getShRefund());
							yOrder.setRealRefund(smodel.getBackAmt());
							yOrder.setRealDiscount(smodel.getRealDiscount());
							yOrder.setBackDate(smodel.getBackDate());
							yOrder.setDifference(yOrder.getShRefund().subtract(yOrder.getRealRefund()));
							tYearCardCompOrderMapper.addYearCardCompOrder(yOrder);
							//订单置为退订 更新订单总共金额和余额
							BigDecimal initAmt = order.getInitAmt();
							BigDecimal useAmt = tOrderDaliyPlanItemMapper.getOrderOrderDailyFinishAmtByOrderNo(order.getOrderNo());

							order.setPreorderStat("30");//失效的订单
							order.setSign("30");//标示退订
							order.setInitAmt(useAmt);
							order.setCurAmt(BigDecimal.ZERO);
							tPreOrderMapper.updateOrderEndDate(order);

							TVipAcct ac = new TVipAcct();
							ac.setVipCustNo(order.getMilkmemberNo());
							ac.setAcctAmt(smodel.getBackAmt());
							tVipCustInfoService.addVipAcct(ac);

							//年卡退订日志
							OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.YEAR_CARD_BACK_ORDER,null,null,
							null,smodel.getBackAmt().toString(),null,null,user,operationLogMapper);

							//发送EC,更新订单状态
							TPreOrder sendOrder = new TPreOrder();
							sendOrder.setOrderNo(order.getOrderNo());
							sendOrder.setPreorderStat("300");
							taskExecutor.execute(new Thread(){
								@Override
								public void run() {
									super.run();
									this.setName("updateOrderStatus");
									if("20".equals(order.getPaymentmethod()) && "20".equals(order.getPaymentStat())){
										sendOrder.setCurAmt(smodel.getBackAmt());
									}else{
										sendOrder.setCurAmt(new BigDecimal("0.00"));
									}
									messLogService.sendOrderStatus(sendOrder);
								}
							});

							//积分扣减
							if("20".equals(order.getPaymentmethod())&&"20".equals(order.getPaymentStat())&&"Y".equals(order.getIsIntegration())){
								taskExecutor.execute(new Thread(){
									@Override
									public void run() {
										super.run();
										this.setName("minusVipPoint"+new Date());
										BigDecimal fRate = smodel.getBackAmt().divide(yOrder.getInitAmt(),2).multiply(new BigDecimal(order.getyFresh()==null?0:order.getyFresh()));//鲜峰
										MemberActivities item = new MemberActivities();
										Date date = new Date();
										item.setActivitydate(date);
										item.setSalesorg(order.getSalesOrg());
										item.setCategory("YRETURN");
										item.setProcesstype("YSUB_RETURN");
										item.setOrderid(order.getOrderNo());
										item.setMembershipguid(order.getMemberNo());
										//第2遍传先锋
										item.setPointtype("YFRESH");
										item.setPoints(fRate);
										item.setAmount(smodel.getBackAmt());
										item.setProcess("X");
										List<MemberActivities> list1 = new ArrayList<MemberActivities>();
										list1.add(item);
										piVipPointCreateBatService.createMemberActivitiesBat(list1);
									}
								});
							}

						}
					}else{
						throw new ServiceException(MessageCode.LOGIC_ERROR,"该折扣不属于年卡");
					}
				}else{
					throw new ServiceException(MessageCode.LOGIC_ERROR,"不存在该年卡信息");
				}
			}
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前订单不存在");
		}
		return 0;
	}


	/**
	 * 订单提前退订
	 * @param record
	 * @return
	 */
	@Override
	public int advanceYearCardBackOrder(YearCardBackModel record){
		System.out.println(record.getOrderNo()+"订单提前退订开始");
		if(StringUtils.isBlank(record.getOrderNo()) || record.getBackAmt()==null||record.getShRefund()==null || record.getRealDiscount()==null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"年卡提前退订，订单号、应退金额、实际退款金额、实际折扣不能为空");
		}
		Date backDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			backDate = format.parse(format.format(record.getBackDate())) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(record.getBackDate() == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"提前退订日期不能为空！");
		}
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());

		if(order.getInitAmt()!=null && order.getInitAmt().compareTo(record.getBackAmt())==-1){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"实退金额不能大于订单总金额，请查看！！！");
		}
		if("20".equals(order.getPreorderStat())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"待确认的订单暂无法进行此操作，请联系在线客服");
		}
		if(order.getEndDate().getTime() < backDate.getTime()){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"提前退订日期不能超出订单配送结束日期！");
		}
		if(order.getBackDate() != null && "30".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"已经退订的订单，不能提前退订");
		}
		if(order.getBackDate() != null && order.getBackDate().getTime()<backDate.getTime()){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"提前退订日期不能超出已退订的日期！");
		}
		if("20".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"长停订单不能提前退订！");
		}

		if("10".equals(order.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(order.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"已经有收款单了，请不要操作，或者去删除收款单!");
		}
		if("20".equals(order.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+order.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
		}

		if("10".equals(order.getPreorderSource()))throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服!");
		if(tDispOrderItemMapper.selectCountOfTodayByOrgOrder(order.getOrderNo(), backDate)>0)throw new ServiceException(MessageCode.LOGIC_ERROR,"此订单，有退订后未确认的路单!请删除路单后再操作!");
		//判断赠品是否产生路单  或者 从今天开始到退订日期之间是否有赠品日订单
		backOrderOfProm(order,backDate);
		TSysUser user = userSessionService.getCurrentUser();
		BigDecimal initAmt = order.getInitAmt();
		OrderSearchModel smodel = new OrderSearchModel();
		smodel.setBackDate(backDate);
		smodel.setOrderNo(order.getOrderNo());
		//第一步 计算退款金额（用于更新订单总金额和余额）
		BigDecimal backAmt = tOrderDaliyPlanItemMapper.getSumDailyBackAmtByBackDate(smodel);
		//第二步 删除日计划
		tOrderDaliyPlanItemMapper.deleteDailyByStopDate(smodel);
		//第三步  创建年卡折扣补偿单据
		TMstYearCardCompOrder yOrder = tOrderDaliyPlanItemMapper.selectYearCardBackOrder(order.getOrderNo(),smodel.getBackDate());
		if(yOrder!=null){
			yOrder.setCreateAt(new Date());
			yOrder.setCreateBy(user.getLoginName());
			yOrder.setCreateByTxt(user.getDisplayName());
			yOrder.setShRefund(record.getShRefund());
			yOrder.setRealRefund(record.getBackAmt());
			yOrder.setRealDiscount(record.getRealDiscount());
			yOrder.setBackDate(record.getBackDate());
			yOrder.setDifference(yOrder.getShRefund().subtract(yOrder.getRealRefund()));
			tYearCardCompOrderMapper.addYearCardCompOrder(yOrder);
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"年卡提前退订时，创建年卡补偿单据时获取信息失败，请查看！！！");
		}
		// 第四步  订单退订日期、和退订原因录入，更新订单的截止日期，总金额和 剩余金额
		order.setBackDate(backDate);
		order.setBackReason(record.getBackReason());
		order.setEndDate(afterDate(backDate,-1));
		order.setInitAmt(order.getInitAmt().subtract(backAmt));
		order.setCurAmt(order.getCurAmt().subtract(backAmt));
		if(StringUtils.isNotEmpty(record.getMemoTxt())) {
			order.setMemoTxt(record.getMemoTxt().concat("提前退订"));
		}else{
			order.setMemoTxt("提前退订");
		}
		tPreOrderMapper.updateOrderEndDate(order);
		//第五步    金额退回订户个人账户
		String state = order.getPaymentmethod();
		if("20".equals(state)){//先付款
			if(order.getInitAmt()!=null && "20".equals(order.getPaymentStat())){//已经收款的
					TVipAcct ac = new TVipAcct();
					ac.setVipCustNo(order.getMilkmemberNo());
					ac.setAcctAmt(record.getBackAmt());
					tVipCustInfoService.addVipAcct(ac);
			}else if("10".equals(order.getPaymentStat())){
				TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
				if(bill!= null && (bill.getAccAmt().compareTo(BigDecimal.ZERO)==1)){
					TVipAcct ac = new TVipAcct();
					ac.setVipCustNo(order.getMilkmemberNo());
					ac.setAcctAmt(bill.getAccAmt());
					tVipCustInfoService.addVipAcct(ac);
				}
			}
		}
		//更新日订单剩余金额
		tOrderDaliyPlanItemMapper.updateDaliyRemainAmtAfterAdvanceBack(order.getOrderNo(),backAmt);
		//第六步      添加年卡提前退订日志
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.YEAR_CARD_BACK_ORDER_ADVANCE,null,null,
		"提前退订",record.getBackAmt().toString(),null,null,user,operationLogMapper);


		//第七步   更新订单行截止日期
		List<TPlanOrderItem> items = tPlanOrderItemMapper.selectPlanOrderItemByOrderNo(order.getOrderNo());
		for(TPlanOrderItem item : items){
			Date date = item.getEndDispDate();
			if(date.getTime() > order.getEndDate().getTime()){
				item.setEndDispDate(order.getEndDate());
				tPlanOrderItemMapper.updateEntryByItemNo(item);
			}
		}
		//第八步  发送EC,更新订单状态
		TPreOrder sendOrder = new TPreOrder();
		sendOrder.setOrderNo(order.getOrderNo());
		sendOrder.setPreorderStat("300");
		taskExecutor.execute(new Thread(){
			@Override
			public void run() {
				super.run();
				this.setName("updateOrderStatus");
				if("20".equals(state) && "20".equals(order.getPaymentStat())){
					sendOrder.setCurAmt(order.getCurAmt());
				}else{
					sendOrder.setCurAmt(new BigDecimal("0.00"));
				}
				messLogService.sendOrderStatus(sendOrder);
			}
		});

		//第九步  积分扣减
		if("20".equals(order.getPaymentmethod())&&"20".equals(order.getPaymentStat())&&"Y".equals(order.getIsIntegration())){
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("minusVipPoint"+new Date());
					BigDecimal fRate = record.getBackAmt().divide(initAmt,2).multiply(new BigDecimal(order.getyFresh()==null?0:order.getyFresh()));//鲜峰
					MemberActivities item = new MemberActivities();
					Date date = new Date();
					item.setActivitydate(date);
					item.setSalesorg(order.getSalesOrg());
					item.setCategory("YRETURN");
					item.setProcesstype("YSUB_RETURN");
					item.setOrderid(order.getOrderNo());
					item.setMembershipguid(order.getMemberNo());
					item.setPointtype("YFRESH");
					item.setPoints(fRate);
					item.setAmount(record.getBackAmt());
					item.setProcess("X");
					List<MemberActivities> list1 = new ArrayList<MemberActivities>();
					list1.add(item);
					piVipPointCreateBatService.createMemberActivitiesBat(list1);
				}
			});
		}
		return 0;
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
		System.out.println(record.getOrderNo()+"  订单开始退订");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isBlank(record.getOrderNo())) throw new ServiceException(MessageCode.LOGIC_ERROR,"订单退订，订单号不能为空！！");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		if(order!= null){
			if(StringUtils.isNotBlank(order.getPreorderStat()) && "20".equals(order.getPreorderStat())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服");
			}
			//判断赠品是否已经配送 或者产生路单
			backOrderOfProm(order,null);
			System.out.println(record.getOrderNo()+" 赠品配送判断结束");
			if("10".equals(order.getPaymentmethod())){
				if(customerBillMapper.getRecBillByOrderNo(order.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"已经有收款单了，请不要操作，或者去删除收款单!");
			}
			if("20".equals(order.getPaymentmethod())){
				TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
				if(bill!=null && "10".equals(bill.getStatus())){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+order.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
				}
			}
			System.out.println(record.getOrderNo()+"退订， 收款单判断结束没问题");
			//if("10".equals(order.getPreorderSource()))throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服!");
			if(tDispOrderItemMapper.selectCountOfTodayByOrgOrder(order.getOrderNo(), null)>0)throw new ServiceException(MessageCode.LOGIC_ERROR,"此订单，有未确认的路单!请等路单确认后再操作!");
			
			order.setBackDate(afterDate(new Date(),0));
			order.setBackReason(record.getReason());
			order.setMemoTxt(record.getMemoTxt());
			
			String state = order.getPaymentmethod();

			// BigDecimal leftAmt = order.getCurAmt();
			if(order.getInitAmt()==null) throw new ServiceException(MessageCode.LOGIC_ERROR,"订单总金额为空，请维护数据");
			BigDecimal initAmt = order.getInitAmt();
			BigDecimal useAmt = tOrderDaliyPlanItemMapper.getOrderOrderDailyFinishAmtByOrderNo(order.getOrderNo());
			BigDecimal leftAmt = initAmt.subtract(useAmt);
			if("20".equals(state)){//先付款

				tOrderDaliyPlanItemMapper.updateDaliyPlansToBack(order);
				System.out.println(record.getOrderNo()+"退订，日订单更新状态完成");
				BigDecimal backAmt = BigDecimal.ZERO;
				//此为多余的钱，如果是预付款，将存入订户账户
				if(order.getInitAmt()!=null && "20".equals(order.getPaymentStat())){
					//订单来源为 电商或者牛奶钱包
					if("10".equals(order.getPreorderSource()) || "40".equals(order.getPreorderSource())){
						if(order.getOnlineInitAmt()!=null){
							backAmt = backAmt.add(leftAmt.multiply(order.getOnlineInitAmt()).divide(order.getInitAmt(),2));
							//电商 退订日志
							OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.DH_BACK_ORDER,null,null,
									null,backAmt.toString(),null,null,userSessionService.getCurrentUser(),operationLogMapper);
						}
					}else if("70".equals(order.getPreorderSource())){
						OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.JG_BACK_ORDER,null,null,
								null,backAmt.toString(),null,null,userSessionService.getCurrentUser(),operationLogMapper);
					}else{
						// 如果有促销
						if(StringUtils.isNotBlank(order.getPromotion())&&StringUtils.isNotBlank(order.getPromItemNo())){
							TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(order.getPromotion(),order.getPromItemNo());
							if(prom!=null){
								//年卡订单
								if("Z017".equals(prom.getPromSubType())){
									throw new ServiceException(MessageCode.LOGIC_ERROR,"年卡退订不从这里退订，请查看维护");
								}
							}
						}
						//如果是满减促销，退款等于  订单收款金额-用去的金额（结果相当于没有参加促销）
						if(order.getDiscountAmt()!=null && !"".equals(order.getDiscountAmt())  && order.getDiscountAmt().compareTo(BigDecimal.ZERO)==1){
								backAmt = backAmt.add(leftAmt).subtract(order.getDiscountAmt());
						}else{
							backAmt = backAmt.add(leftAmt);
						}
						System.out.println(record.getOrderNo()+"退订，退款金额为"+backAmt);
						//退款金额大于0
						if(backAmt.compareTo(BigDecimal.ZERO)==1){
							TVipAcct ac = new TVipAcct();
							ac.setVipCustNo(order.getMilkmemberNo());
							ac.setAcctAmt(backAmt);
							tVipCustInfoService.addVipAcct(ac);
						}else if(backAmt.compareTo(BigDecimal.ZERO)==-1){
							throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单需退金额为"+backAmt+",小于零有问题，请查看");
						}
					}

				}else if("10".equals(order.getPaymentStat())){
					//此处看是否打印过收款单，里面有没有用帐户余额支付的金额，退回
					TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
					if(bill!= null && (bill.getAccAmt().compareTo(BigDecimal.ZERO)==1)){
						TVipAcct ac = new TVipAcct();
						   ac.setVipCustNo(order.getMilkmemberNo());
						   ac.setAcctAmt(bill.getAccAmt());
							tVipCustInfoService.addVipAcct(ac);
					}
				}
				
				//用掉的钱
				//BigDecimal remain = order.getInitAmt().subtract(order.getCurAmt());
				order.setInitAmt(useAmt);
				order.setCurAmt(new BigDecimal("0.00"));
				
			}else{//后付款
				tOrderDaliyPlanItemMapper.updateDaliyPlansToBack(order);

				//用掉的钱
				//BigDecimal remain = order.getInitAmt().subtract(order.getCurAmt());
				//BigDecimal remain = tOrderDaliyPlanItemMapper.getOrderOrderDailyFinishAmtByOrderNo(order.getOrderNo());
				order.setInitAmt(useAmt);
				order.setCurAmt(new BigDecimal("0.00"));
			}
			//退订日志
			if(!"10".equals(order.getPreorderSource()) &&!"40".equals(order.getPreorderSource())){
				OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.BACK_ORDER,null,null,
						("10".equals(order.getSign())?"在订":("20".equals(order.getSign())?"停订":"完结")),
						"退订",null,null,userSessionService.getCurrentUser(),operationLogMapper);
			}

			//更新订单状态为退订
			order.setEndDate(new Date());
			order.setPreorderStat("30");//失效的订单
			order.setSign("30");//标示退订
			tPreOrderMapper.updateOrderEndDate(order);
			System.out.println("订单退订，更新订单状态成功--------------");

			//订户状态更改???
			List<TPreOrder> list = tPreOrderMapper.selectByMilkmemberNoRetOrder(order.getMilkmemberNo());
			if(list==null||list.size()<=0){
				tVipCustInfoService.discontinue(order.getMilkmemberNo(), "40",null,null);
			}

			//删除装箱单
			milkBoxService.deleteMilkBoxByOrderNo(order.getOrderNo());
			System.out.println("订单退订，删除装箱表成功，下面开始发送EC");

			//发送EC,更新订单状态
			TPreOrder sendOrder = new TPreOrder();
			sendOrder.setOrderNo(order.getOrderNo());
			sendOrder.setPreorderStat("300");
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("updateOrderStatus");
					if("20".equals(state) && "20".equals(order.getPaymentStat())){
						sendOrder.setCurAmt(leftAmt);
					}else{
						sendOrder.setCurAmt(new BigDecimal("0.00"));
					}
					messLogService.sendOrderStatus(sendOrder);
				}
			});
			
			//积分扣减
			if("20".equals(order.getPaymentmethod())&&"20".equals(order.getPaymentStat())&&"Y".equals(order.getIsIntegration())){
				taskExecutor.execute(new Thread(){
					@Override
					public void run() {
						super.run();
						this.setName("minusVipPoint"+new Date());
//						BigDecimal gRate = leftAmt.divide(initAmt,2).multiply(new BigDecimal(order.getyGrowth()==null?0:order.getyGrowth()));//成长
						BigDecimal fRate = leftAmt.divide(initAmt,2).multiply(new BigDecimal(order.getyFresh()==null?0:order.getyFresh()));//鲜峰
						MemberActivities item = new MemberActivities();
						Date date = new Date();
						item.setActivitydate(date);
						item.setSalesorg(order.getSalesOrg());
						item.setCategory("YRETURN");
						item.setProcesstype("YSUB_RETURN");
						item.setOrderid(order.getOrderNo());
						item.setMembershipguid(order.getMemberNo());
//						item.setPointtype("YGROWTH");
//						item.setPoints(gRate);
//						//第1遍传成长
//						piVipInfoDataService.createMemberActivities(item);
						//第2遍传先锋
						item.setPointtype("YFRESH");
						item.setPoints(fRate);
						item.setAmount(leftAmt);
						item.setProcess("X");
//						piVipInfoDataService.createMemberActivities(item);
						List<MemberActivities> list1 = new ArrayList<MemberActivities>();
						list1.add(item);
						piVipPointCreateBatService.createMemberActivitiesBat(list1);
					}
				});
			}
			
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前订单不存在");
		}

		return 1;
	}

	/**
	 *   如果date为空说明是 订单退订 只需要判断目前赠品是否已产生路单或者已确认 如果有抛出异常
	 *   如果 date不为空说明为订单提前退订，不仅要判断目前赠品是否已产生路单或者已确认
	 *      还要判断从今天开始到退订当天之间是否有赠品，如果有提示（将赠品更改配送日期到退订日期之后再做退订）
	 * @param order 订单
	 * @param date  提前退订日期 或者 日订单行停订前一天日期
     */
	public void backOrderOfProm(TPreOrder order,Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//首先查看目前路单中是否已经有赠品配送
		List<TDispOrderItem> dispItems = tDispOrderItemMapper.selectItemsByOrgOrderByProm(order.getOrderNo());
		if(dispItems!=null && dispItems.size()>0){
			StringBuffer noConfirm = new StringBuffer();
			StringBuffer confirm = new StringBuffer();
			dispItems.stream().forEach(item->{
				if("10".equals(item.getStatus())){
					noConfirm.append(item.getMatnr()+"赠品"+format.format(item.getOrderDate()));
				}
				if("20".equals(item.getStatus())){
					confirm.append(item.getMatnr()+"赠品"+format.format(item.getOrderDate()));
				}
			});
			if(StringUtils.isNotBlank(confirm.toString().trim())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,confirm+"已经发放，不能退订");
			}
			if(StringUtils.isNotBlank(noConfirm.toString().trim())){
				throw new ServiceException(MessageCode.LOGIC_ERROR, noConfirm +"  已经产生了路单，请先删除路单再退订");
			}
		}

		if(date!=null){
			//如果date 不为空表示为提前退订或者行项目 停订  需要查看从今天开始 到date期间是否有赠品
			List<TOrderDaliyPlanItem> daliys = tOrderDaliyPlanItemMapper.selectPromDaliyBetweenDaysAndNo(order.getOrderNo(),null,new Date(),date);
			if(daliys!=null && daliys.size()>0){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"从今天到"+format.format(date)+"那天之间有赠品，不能进行此操作");
			}
		}
	}
	/**
	 * 订单提前退订
	 * @param record
	 * @return
     */
	@Override
	public int advanceBackOrder(OrderSearchModel record){
		System.out.println(record.getOrderNo()+"订单提前退订开始");
		Date backDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			backDate = format.parse(format.format(record.getBackDate())) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(record.getBackDate() == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"提前退订日期不能为空！");
		}
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		if("20".equals(order.getPreorderStat())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"待确认的订单暂无法进行此操作，请联系在线客服");
		}
		if(order.getEndDate().getTime() < backDate.getTime()){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"提前退订日期不能超出订单配送结束日期！");
		}
		if(order.getBackDate() != null && order.getBackDate().getTime()<backDate.getTime()){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"提前退订日期不能超出已退订的日期！");
		}
		if("20".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"长停订单不能提前退订！");
		}

		if("10".equals(order.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(order.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"已经有收款单了，请不要操作，或者去删除收款单!");
		}
		if("20".equals(order.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+order.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
		}

		if("10".equals(order.getPreorderSource()))throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服!");
		if(tDispOrderItemMapper.selectCountOfTodayByOrgOrder(order.getOrderNo(), backDate)>0)throw new ServiceException(MessageCode.LOGIC_ERROR,"此订单，有退订后未确认的路单!请删除路单后再操作!");
		//判断赠品是否产生路单  或者 从今天开始到退订日期之间是否有赠品日订单
		backOrderOfProm(order,backDate);

		//第一步 计算退订的金额

		BigDecimal backAmt = tOrderDaliyPlanItemMapper.getSumDailyBackAmtByBackDate(record);

				//第二步 删除日计划
		tOrderDaliyPlanItemMapper.deleteDailyByStopDate(record);

		order.setBackDate(backDate);
		order.setBackReason(record.getReason());
		order.setEndDate(afterDate(backDate,-1));
		if(StringUtils.isNotEmpty(record.getMemoTxt())) {
			order.setMemoTxt(record.getMemoTxt().concat("提前退订"));
		}else{
			order.setMemoTxt("提前退订");
		}
		BigDecimal initAmt = order.getInitAmt();
		order.setInitAmt(order.getInitAmt().subtract(backAmt));
		order.setCurAmt(order.getCurAmt().subtract(backAmt));

		String state = order.getPaymentmethod();
		if("20".equals(state)) {//先付款
			//退余额
//			TVipAcct ac = new TVipAcct();
//			ac.setVipCustNo(order.getMilkmemberNo());
//			ac.setAcctAmt(backAmt);
//			tVipCustInfoService.addVipAcct(ac);
			if(order.getInitAmt()!=null && "20".equals(order.getPaymentStat())){//已经收款的
				BigDecimal dsBackAmt = BigDecimal.ZERO;
				if("10".equals(order.getPreorderSource()) || "40".equals(order.getPreorderSource())){
					if(order.getOnlineInitAmt()!=null){
						dsBackAmt = dsBackAmt.add(backAmt.multiply(order.getOnlineInitAmt()).divide(initAmt,2));
						//电商 退订日志
						OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.DH_BACK_ORDER,null,null,
								null,dsBackAmt.toString(),null,null,userSessionService.getCurrentUser(),operationLogMapper);
					}
				}else if("70".equals(order.getPreorderSource())){
					OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.JG_BACK_ORDER,null,null,
							null,null,null,null,userSessionService.getCurrentUser(),operationLogMapper);
				}else{
					TVipAcct ac = new TVipAcct();
					ac.setVipCustNo(order.getMilkmemberNo());
					ac.setAcctAmt(backAmt);
					tVipCustInfoService.addVipAcct(ac);
				}
			}else if("10".equals(order.getPaymentStat())){
				//此处看是否打印过收款单，里面有没有用帐户余额支付的金额，退回
				TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
				if(bill!= null && (bill.getAccAmt().compareTo(BigDecimal.ZERO)==1)){
					TVipAcct ac = new TVipAcct();
					ac.setVipCustNo(order.getMilkmemberNo());
					ac.setAcctAmt(bill.getAccAmt());
					tVipCustInfoService.addVipAcct(ac);
				}
			}
		}
		//退订日志
		if(!"10".equals(order.getPreorderSource()) &&!"40".equals(order.getPreorderSource())){
			OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.BACK_ORDER,null,null,
					"提前退订",backAmt.toString(),null,null,userSessionService.getCurrentUser(),operationLogMapper);
		}
		tPreOrderMapper.updateOrderEndDate(order);

		List<TPlanOrderItem> items = tPlanOrderItemMapper.selectPlanOrderItemByOrderNo(order.getOrderNo());
		for(TPlanOrderItem item : items){
			Date date = item.getEndDispDate();
			if(date.getTime() > order.getEndDate().getTime()){
				item.setEndDispDate(order.getEndDate());
				tPlanOrderItemMapper.updateEntryByItemNo(item);
			}
		}
		//发送EC,更新订单状态
		TPreOrder sendOrder = new TPreOrder();
		sendOrder.setOrderNo(order.getOrderNo());
		sendOrder.setPreorderStat("300");
		taskExecutor.execute(new Thread(){
			@Override
			public void run() {
				super.run();
				this.setName("updateOrderStatus");
				if("20".equals(state) && "20".equals(order.getPaymentStat())){
					sendOrder.setCurAmt(order.getCurAmt());
				}else{
					sendOrder.setCurAmt(new BigDecimal("0.00"));
				}
				messLogService.sendOrderStatus(sendOrder);
			}
		});

		//积分扣减
		if("20".equals(order.getPaymentmethod())&&"20".equals(order.getPaymentStat())&&"Y".equals(order.getIsIntegration())){
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("minusVipPoint"+new Date());
//						BigDecimal gRate = leftAmt.divide(initAmt,2).multiply(new BigDecimal(order.getyGrowth()==null?0:order.getyGrowth()));//成长
					BigDecimal fRate = backAmt.divide(initAmt,2).multiply(new BigDecimal(order.getyFresh()==null?0:order.getyFresh()));//鲜峰
					MemberActivities item = new MemberActivities();
					Date date = new Date();
					item.setActivitydate(date);
					item.setSalesorg(order.getSalesOrg());
					item.setCategory("YRETURN");
					item.setProcesstype("YSUB_RETURN");
					item.setOrderid(order.getOrderNo());
					item.setMembershipguid(order.getMemberNo());
					//第2遍传先锋
					item.setPointtype("YFRESH");
					item.setPoints(fRate);
					item.setAmount(backAmt);
					item.setProcess("X");
					List<MemberActivities> list1 = new ArrayList<MemberActivities>();
					list1.add(item);
					piVipPointCreateBatService.createMemberActivitiesBat(list1);
				}
			});
		}

		return 0;
	}

	@Override
	public int updateBackState() {
		TSysUser user = userSessionService.getCurrentUser();
		TPreOrder tmpOrder = new TPreOrder();
		tmpOrder.setBackDate(new Date());
		tmpOrder.setBranchNo(user.getBranchNo());
		List<TPreOrder> orders =  tPreOrderMapper.selectBackOrderByBackDate(tmpOrder);
		if(orders != null){
			for(TPreOrder order : orders){
				//更新订单状态为退订
				order.setEndDate(new Date());
				order.setPreorderStat("30");//失效的订单
				order.setSign("30");//标示提前退订
				tPreOrderMapper.updateOrderEndDate(order);
				//订户状态更改???
				List<TPreOrder> list = tPreOrderMapper.selectByMilkmemberNoRetOrder(order.getMilkmemberNo());
				if(list==null||list.size()<=0){
					tVipCustInfoService.discontinue(order.getMilkmemberNo(), "40",null,null);
				}
				//删除装箱单
				milkBoxService.deleteMilkBoxByOrderNo(order.getOrderNo());
			}
		}
		return 0;
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
					continueOrderAuto(e,record.getMemoTxt());
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
	public int continueOrderAuto(String orderNo,String memoTxt)
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
			
			//可能有日计划单独延后的，要新算开始日期
			Map<String,Date> entryMap = new HashMap<String,Date>();
//			if("20".equals(order.getPaymentmethod())){
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());
				daliyPlans.stream().forEach((e)->{
					if(!entryMap.containsKey(e.getItemNo())){
						entryMap.put(e.getItemNo(), e.getDispDate());
					}
				});
//			}
			
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
				if("Y".equals(entry.getIsStop()))continue;//续订标示
				entry.setOrderNo(order.getOrderNo());
				//设置配送开始时间
				int days = daysOfTwo(entry.getStartDispDate(),entry.getEndDispDate());
				
				//重新算的开始日期
				Date newDate = entryMap.get(entry.getItemNo());
				if(newDate!=null)entry.setEndDispDate(entryMap.get(entry.getItemNo()));
				
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
			
			if(entriesList.size()==0)throw new ServiceException(MessageCode.LOGIC_ERROR,"在日期内"+ orderNo +"无法续订，没有订单行项目!");

			//会员号
			if(order.getMemberNo()==null){
				TVipCustInfo vip = tVipCustInfoService.findVipCustByNoForUpt(order.getMilkmemberNo());
				if(vip!=null)order.setMemberNo(vip.getVipCustNoSap());
			}
			
			//保存订单，订单行
			order.setPreorderSource("30");
			order.setCurAmt(orderAmt);//订单价格
			order.setInitAmt(orderAmt);
			order.setEndDate(calculateFinalDate(entriesList));//订单截止日期
			//将订单状态置为 在订状态（10）
			order.setSign("10");
			order.setResumeFlag("N");
			//备注
			order.setMemoTxt(memoTxt);
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
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(), LogType.ORDER, OrderLogEnum.CTN_ORDER,null,null,"续订单号"+orderNo,"新单号"+order.getOrderNo(),null,null,userSessionService.getCurrentUser(),operationLogMapper);

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
		
		record.setOrderDateEnd(format.format(afterDate(startDate,goDays-1)));
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
//		在批量续订时，预付款的订单自动续订[已不适用]// || ("batch".equals(record.getStatus()) && "20".equals(order.getPaymentmethod()))
		if("true".equals(record.getContent())){
			continueOrderAuto(order.getOrderNo(),record.getMemoTxt());
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
				if(StringUtils.isBlank(record.getOrderDateStart())){
					sdate = afterDate(order.getEndDate(),1);
				}else{
					sdate = format.parse(record.getOrderDateStart());
				}
				edate = format.parse(record.getOrderDateEnd());
			}
			catch (ParseException e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式不正确!");
			}
			
			if(edate.before(sdate))throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"续订截止日期不能小于原订单截止日期!");
			if(sdate.before(order.getEndDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"续订日期不能小于原订单截止日期!"+order.getEndDate());
//			String state = order.getPaymentmethod();
			
			//可能有日计划单独延后的，要新算开始日期
			Map<String,Date> entryMap = new HashMap<String,Date>();
//			if("20".equals(order.getPaymentmethod())){
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());
				daliyPlans.stream().forEach((e)->{
					if(!entryMap.containsKey(e.getItemNo())){
						entryMap.put(e.getItemNo(), e.getDispDate());
					}
				});
//			}
			
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
				if("Y".equals(entry.getIsStop()))continue;//续订标示
				entry.setOrderNo(order.getOrderNo());
				
				//设置配送开始时间
				if(StringUtils.isBlank(record.getOrderDateStart())){
					
					//重新算的开始日期
					Date newDate = entryMap.get(entry.getItemNo());
					if(newDate!=null)entry.setEndDispDate(entryMap.get(entry.getItemNo()));
					
					calculateEntryStartDate(entry);
					if(edate.before(entry.getStartDispDate())){
						continue;//如果需要续订天数不足某一行，这行不需要续订
					}
					entry.setEndDispDate(edate);
				}else{
					Date startDate = afterDate(sdate,entryDateMap.get(entry.getItemNo()));
					if(edate.before(startDate)){
						continue;//如果需要续订天数不足某一行，这行不需要续订
					}
					entry.setEndDispDate(edate);
					entry.setStartDispDate(startDate);
				}
				
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
			
			if(entriesList.size()==0)throw new ServiceException(MessageCode.LOGIC_ERROR,"在日期内"+record.getOrderNo()+"无法续订，没有订单行项目!");
			
			//会员号
			if(order.getMemberNo()==null){
				TVipCustInfo vip = tVipCustInfoService.findVipCustByNoForUpt(order.getMilkmemberNo());
				if(vip!=null)order.setMemberNo(vip.getVipCustNoSap());
			}
			
			//保存订单，订单行
			order.setPreorderSource("30");
			order.setCurAmt(orderAmt);//订单价格
			order.setInitAmt(orderAmt);
			order.setEndDate(calculateFinalDate(entriesList));//订单截止日期
			//将订单状态置为 在订状态（10）
			order.setSign("10");
			order.setResumeFlag("N");
			//备注
			order.setMemoTxt(record.getMemoTxt());
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
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(), LogType.ORDER, OrderLogEnum.CTN_ORDER,null,null,"续订单号"+record.getOrderNo(),"新单号"+order.getOrderNo(),null,null,userSessionService.getCurrentUser(),operationLogMapper);

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
		if("20".equals(order.getPreorderStat())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服");
		}
		if("10".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"在订的订单，不能修改复订!");
		}
		
		if("10".equals(order.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(order.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"该后付款订单已经有收款单了，请不要复订订单，或者去删除收款单!");
		}

		if("20".equals(order.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+order.getOrderNo()+"  已经有收款单但是还没完成收款，请不要复订订单，或者去删除收款单!");
			}
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
			uptKey.setDispDateStr(format.format(order.getStopDateStart()));
			tOrderDaliyPlanItemMapper.updateFromDateToDate(uptKey);
			
			order.setSign("10");//标示在订
			tPreOrderMapper.updateOrderEndDate(order);
			
			//订户状态更改
			tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",null,null);
			
			//后付款重新计算
			if("10".equals(order.getPaymentmethod())){
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());

				//更新订单金额等
				BigDecimal initAmt = new BigDecimal("0.00");
				BigDecimal usedAmt = new BigDecimal("0.00");
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if("30".equals(plan.getStatus()))continue;
					initAmt = initAmt.add(plan.getAmt());
					if("20".equals(plan.getStatus())){
						usedAmt.add(plan.getAmt());
					}
				}
				
				order.setInitAmt(initAmt);
				order.setCurAmt(initAmt.subtract(usedAmt));

//				订单截止日期修改
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if(!"30".equals(plan.getStatus())){
						order.setEndDate(plan.getDispDate());
						break;
					}
				}
				
				Collections.reverse(daliyPlans);
				for(TOrderDaliyPlanItem plan : daliyPlans){
		   		if("30".equals(plan.getStatus())){
		   			plan.setRemainAmt(initAmt);
		   			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
		   			continue;
		   		}
		   		plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
		   		initAmt = initAmt.subtract(plan.getAmt());
		   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
		   	}
				
				tPreOrderMapper.updateOrderEndDate(order);
			}
			
			//发送EC
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("resumeOrder");
					record.setOrderDateStart(startDateStr);
					record.setContent("N");
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

//			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(record.getOrderNo());
//
//			for(TOrderDaliyPlanItem p :daliyPlans){
//				if("30".equals(p.getStatus()) && !p.getDispDate().before(order.getStopDateStart())){
//					BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
//					orderAmt = orderAmt.subtract(planAmt);
//				}
//			}
//			
//			order.setCurAmt(orderAmt.subtract(order.getInitAmt().subtract(order.getCurAmt())));
//			order.setInitAmt(orderAmt);
//			
//			for(TOrderDaliyPlanItem p :daliyPlans){
//				if(!"30".equals(p.getStatus())){
//					BigDecimal planAmt = p.getPrice().multiply(new BigDecimal(p.getQty().toString()));
//					orderAmt = orderAmt.subtract(planAmt);
//					p.setRemainAmt(orderAmt);
//					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
//				}else{
//					p.setRemainAmt(orderAmt);
//					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
//				}
//			}
			
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());

			//更新订单金额等
			BigDecimal initAmt = new BigDecimal("0.00");
			BigDecimal usedAmt = new BigDecimal("0.00");
			for(TOrderDaliyPlanItem plan : daliyPlans){
				if("30".equals(plan.getStatus()))continue;
				initAmt = initAmt.add(plan.getAmt());
				if("20".equals(plan.getStatus())){
					usedAmt.add(plan.getAmt());
				}
			}
			
			order.setInitAmt(initAmt);
			order.setCurAmt(initAmt.subtract(usedAmt));

//			订单截止日期修改
			for(TOrderDaliyPlanItem plan : daliyPlans){
				if(!"30".equals(plan.getStatus())){
					order.setEndDate(plan.getDispDate());
					break;
				}
			}
			
			Collections.reverse(daliyPlans);
			for(TOrderDaliyPlanItem plan : daliyPlans){
	   		if("30".equals(plan.getStatus())){
	   			plan.setRemainAmt(initAmt);
	   			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
	   			continue;
	   		}
	   		plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
	   		initAmt = initAmt.subtract(plan.getAmt());
	   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
	   	}
			
			tPreOrderMapper.updateOrderEndDate(order);
			
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
				record.setContent("N");
				messLogService.sendOrderStopRe(record);
			}
		});
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(), LogType.ORDER, OrderLogEnum.RESUME_ORDER,null,null,"停订","在订",null,null,userSessionService.getCurrentUser(),operationLogMapper);
		return 1;
	}

	@Override
	public int continueOrdeAfterStop2(OrderSearchModel record)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(record.getOrderNo());
		if("20".equals(order.getPreorderStat())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"暂无法进行此操作，请联系在线客服");
		}
		if("10".equals(order.getSign())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,record.getOrderNo()+"在订的订单，不能修改复订!");
		}

		if("10".equals(order.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(order.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"该后付款订单已经有收款单了，请不要复订订单，或者去删除收款单!");
		}

		if("20".equals(order.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(order.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+order.getOrderNo()+"  已经有收款单但是还没完成收款，请不要复订订单，或者去删除收款单!");
			}
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
		//如果预付款
		if("20".equals(order.getPaymentmethod())){
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(record.getOrderNo());
			//删除从复订时间开始的日计划
			TOrderDaliyPlanItem deleteKey= new TOrderDaliyPlanItem();

			if(!startDate.after(order.getStopDateStart())){
				deleteKey.setDispDateStr(format.format(order.getStopDateStart()));
			}else{
				deleteKey.setDispDateStr(startDateStr);
			}
			deleteKey.setOrderNo(order.getOrderNo());

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
		}else{

			TOrderDaliyPlanItem uptKey= new TOrderDaliyPlanItem();
			uptKey.setOrderNo(order.getOrderNo());
			if(!startDate.after(order.getStopDateStart())) {
				uptKey.setDispDateStr(format.format(order.getStopDateStart()));
			}else{
				uptKey.setDispDateStr(startDateStr);
			}
			tOrderDaliyPlanItemMapper.updateFromDateToDate(uptKey);

			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());
			//更新订单金额等
			BigDecimal initAmt = new BigDecimal("0.00");
			BigDecimal usedAmt = new BigDecimal("0.00");
			for(TOrderDaliyPlanItem plan : daliyPlans){
				if("30".equals(plan.getStatus()))continue;
				initAmt = initAmt.add(plan.getAmt());
				if("20".equals(plan.getStatus())){
					usedAmt.add(plan.getAmt());
				}
			}
			order.setInitAmt(initAmt);
			order.setCurAmt(initAmt.subtract(usedAmt));
//				订单截止日期修改
			for(TOrderDaliyPlanItem plan : daliyPlans){
				if(!"30".equals(plan.getStatus())){
					order.setEndDate(plan.getDispDate());
					break;
				}
			}
			Collections.reverse(daliyPlans);
			for(TOrderDaliyPlanItem plan : daliyPlans){
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
				record.setContent("N");
				messLogService.sendOrderStopRe(record);
			}
		});
		OperationLogUtil.saveHistoryOperation(order.getOrderNo(), LogType.ORDER, OrderLogEnum.RESUME_ORDER,null,null,"停订","在订",null,null,userSessionService.getCurrentUser(),operationLogMapper);
		return 1;
	}

	/**
	 * 订奶计划中 赠品修改配送日期
	 * 配送日期  不能在所有正品配送日期之前
	 * 不能超出 促销产品配送日期范围
	 * 修改的配送日期当天 不能产生路单
	 * @param record
	 * @return
     */
	@Override
	public int uptDispDateProm(DaliyPlanEditModel record) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<TOrderDaliyPlanItem> entries = record.getEntries();
		if(entries==null || entries.size()==0){throw  new ServiceException(MessageCode.LOGIC_ERROR,"没有日计划行");}
			Date firstDate = tOrderDaliyPlanItemMapper.selectFirstDispDateByOrder(record.getOrderCode());
			for(TOrderDaliyPlanItem plan : entries){
				TOrderDaliyPlanItem oldDay = tOrderDaliyPlanItemMapper.selectByDateAndItemNoAndNo(plan);
				TPlanOrderItem item = tPlanOrderItemMapper.selectEntryByEntryNo(oldDay.getItemNo());
				if(StringUtils.isBlank(oldDay.getPromotionFlag()) && oldDay.getGiftQty()==null) {
					throw new ServiceException(MessageCode.LOGIC_ERROR, "该日计划不是满赠产品");
				}
				TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(oldDay.getPromotionFlag(),item.getPromItemNo());
				if(StringUtils.isBlank(plan.getDispDateStr())) throw new ServiceException(MessageCode.LOGIC_ERROR,"配送日期不能为空");
				try {
					plan.setDispDate(format.parse(plan.getDispDateStr()));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new ServiceException(MessageCode.LOGIC_ERROR, "配送日期格式错误");
				}
				if(prom == null) throw new ServiceException(MessageCode.LOGIC_ERROR,oldDay.getPromotionFlag()+" 该促销号不存在，请维护！");
				if(DateUtil.dateAfter(plan.getDispDate(),prom.getPlanStopTime())) throw new ServiceException(MessageCode.LOGIC_ERROR,"赠品的配送日期超出促销的截止配送日期");
				if(DateUtil.dateAfter(prom.getPlanStartTime(),plan.getDispDate())) throw new ServiceException(MessageCode.LOGIC_ERROR,"赠品的配送日期超出促销的开始配送日期");
				if(DateUtil.dateAfter(firstDate,plan.getDispDate())) throw new ServiceException(MessageCode.LOGIC_ERROR,"订单没配送正品之前不能配送赠品");
				//判断这天有没有产生路单
				if(tDispOrderItemMapper.selectItemsByOrgOrderAndItemNo(plan.getOrderNo(), plan.getItemNo(), oldDay.getDispDate()).size()>0){
					throw new ServiceException(MessageCode.LOGIC_ERROR,format.format(oldDay.getDispDate())+"的日计划已经生成路单，不可以修改!");
				}

				//判断修改后那天有没有产生路单
				if(tDispOrderItemMapper.selectItemsByOrgOrderAndItemNo(plan.getOrderNo(), plan.getItemNo(), plan.getDispDate()).size()>0){
					throw new ServiceException(MessageCode.LOGIC_ERROR,format.format(plan.getDispDate())+"的日计划已经生成路单，不可以修改到这一天!");
				}
				//判断这天是否已经有赠品配送
				List<TOrderDaliyPlanItem> itemPromDays = tOrderDaliyPlanItemMapper.selectPromDaliyBetweenDaysAndNo(plan.getOrderNo(),plan.getItemNo(),plan.getDispDate(),plan.getDispDate());
				if(itemPromDays!=null && itemPromDays.size()>0) throw new ServiceException(MessageCode.LOGIC_ERROR,"不能同一天赠送两份赠品！！！");
				oldDay.setDispDate(plan.getDispDate());
				oldDay.setReachTimeType(plan.getReachTimeType());
				tOrderDaliyPlanItemMapper.updateDaliyPlanItem(oldDay);
			}
		return 1;
	}

	/**
	 * 日订单 退款
	 * @param record
	 * @return
     */
	@Override
	public int daliyBackAmt(DaliyPlanEditModel record){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TSysUser user = userSessionService.getCurrentUser();
		List<TOrderDaliyPlanItem> entries = record.getEntries();
		if(entries==null || entries.size()==0){throw  new ServiceException(MessageCode.LOGIC_ERROR,"没有日计划行");}
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(record.getOrderCode());
		if(orgOrder==null) throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单号不存在");
		if(orgOrder.getDiscountAmt()!=null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单为满减订单，不能进行日订单退款!");
		}
		if("10".equals(orgOrder.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"后付款订单已经有收款单了，请不要修改订单，或者去删除收款单!");
		}
		if("20".equals(orgOrder.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+orgOrder.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
		}
		//判断是否是年卡订单
		boolean yearCard = false;
		if(StringUtils.isNotBlank(orgOrder.getPromotion()) && StringUtils.isNotBlank(orgOrder.getPromotion())){
			TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(orgOrder.getPromotion(),orgOrder.getPromItemNo());
			if(prom!=null){
				if("Z017".equals(prom.getPromSubType())){
					yearCard = true;
				}
			}
		}
		BigDecimal totalAmt = BigDecimal.ZERO;
		for(TOrderDaliyPlanItem entry : entries){
			TOrderDaliyPlanItem oldDay = tOrderDaliyPlanItemMapper.selectByDateAndItemNoAndNo(entry);
			if(!"10".equals(oldDay.getStatus())){ throw  new ServiceException(MessageCode.LOGIC_ERROR,format.format(entry.getDispDate())+"的日订单不是在订状态，不能退款");}
			if(StringUtils.isNotBlank(oldDay.getPromotionFlag()) && oldDay.getGiftQty()!=null){ throw  new ServiceException(MessageCode.LOGIC_ERROR,format.format(entry.getDispDate())+"的日订单为赠品，不能退款");}
			//判断这天有没有产生路单
			if(tDispOrderItemMapper.selectItemsByOrgOrderAndItemNo(oldDay.getOrderNo(), oldDay.getItemNo(), oldDay.getDispDate()).size()>0){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"该日计划已经生成路单，不可以退款!");
			}

			//记录到单日订单退款表中
			TOrderDaliyPlanItemBack back = new TOrderDaliyPlanItemBack();
			back.setOrderNo(orgOrder.getOrderNo());
			back.setBranchNo(orgOrder.getBranchNo());
			back.setDispDate(oldDay.getDispDate());
			back.setUnit(oldDay.getUnit());
			back.setSalesPrice(oldDay.getPrice());
			back.setMatnr(oldDay.getMatnr());
			back.setAmt(oldDay.getAmt());
			back.setQty(oldDay.getQty());
			back.setCreateAt(new Date());
			back.setCreateBy(user.getLoginName());
			back.setCreateByTxt(user.getDisplayName());
			if(yearCard){
				back.setOrderType("20");
			}else{
				back.setOrderType("10");
			}
			tOrderDaliyPlanItemBackMapper.insertNewItem(back);
			//计算退款金额
			totalAmt = totalAmt.add(oldDay.getAmt());
			//将日订单设为停订
			oldDay.setStatus("30");
			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(oldDay);
		}
		//年卡日计划退款不改变订单金额，日计划每天所剩金额也不重新计算。普通订单更新
		if(!yearCard) {
			orgOrder.setInitAmt(orgOrder.getInitAmt().subtract(totalAmt));
			orgOrder.setCurAmt(orgOrder.getCurAmt().subtract(totalAmt));
			List<TOrderDaliyPlanItem> daliys = tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
			calculateDaliyPlanRemainAmt(orgOrder, daliys);
		}
		tPreOrderMapper.updateBySelective(orgOrder);
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
        * @title: 查询订单列表
        * @description:根据订户电话查询订单列表
        */
	@Override
	public PageInfo searchOrderByMp(OrderSearchModel smodel)
	{
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setDealerNo(userSessionService.getCurrentUser().getDealerId());
		return tPreOrderMapper.searchOrderByMp(smodel);
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
		if(pages.getList()!=null && pages.getList().size() > 0){
			if("10".equals(orgOrder.getPaymentmethod())){
				for(Object e : pages.getList()){
					TOrderDaliyPlanItem p = ((TOrderDaliyPlanItem)e);
					p.setRemainAmt(p.getRemainAmt().subtract(orgOrder.getInitAmt()));
				}
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
		//判断如果新增订单时订单编号不为空，则代表是订户数据导入
		if(StringUtils.isBlank(order.getOrderNo())){
			order.setOrderNo(CodeGeneratorUtil.getCode());
		}
		if(order.getInitAmt()!=null){
			order.setOnlineInitAmt(order.getInitAmt());
		}

		//其他订单信息
		order.setOrderDate(date);//订单创建日期

		order.setCreaterBy(userSessionService.getCurrentUser().getLoginName());//创建人
		order.setCreaterNo(userSessionService.getCurrentUser().getGroupId());//创建人编号
		if(StringUtils.isBlank(order.getOrderType())){
			order.setOrderType("20");//订单类型 页面都是线下
		}
		if(StringUtils.isBlank(order.getPreorderSource())){
			order.setPreorderSource("30");//订单来源  页面中来源都是30（奶站） 10电商20征订40牛奶钱包50送奶工APP 60 电话 70 机构
		}
		if("70".equals(order.getPreorderSource())){
			if(StringUtils.isBlank(order.getOnlineSourceType())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"机构订单，机构编码不能为空");
			}
		}
		if(StringUtils.isBlank(order.getIsIntegration())){
			order.setIsIntegration("Y");//默认是积分订单
		}
		//		order.setMilkmemberNo(milkmemberNo);//喝奶人编号
		//		order.setEmpNo(empNo);//送奶员编号
		//		order.setInitAmt(initAmt);//页面输入的初始订单金额
		order.setPaymentmethod(order.getPaymentStat());//10 后款 20 先款( 30 殿付款)
		if("Y".equals(order.getIsPaid())){
			if(StringUtils.isBlank(order.getPayDateStr())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"已付款订单，支付时间payDateStr字段不能为空!");
			}
			order.setPaymentStat("20");//付款状态,生成时已经付款
		}else{
			order.setPaymentStat("10");//付款状态,生成时未付款
		}
		order.setMilkboxStat(StringUtils.isBlank(order.getMilkboxStat()) == true ? "20": order.getMilkboxStat());//奶箱状态
		if(StringUtils.isBlank(order.getPreorderStat())){
			order.setPreorderStat("10");//订单状态,初始确认
		}
		if("20".equals(order.getPreorderStat())){
			order.setIsValid("N");
		}else{
			order.setIsValid("Y");
		}
		order.setSign("10");//在订状态
		//根据传的奶站获取经销商和销售组织
		// 如果不是奶站过来的  可以是无奶站
		TMdBranch branch = branchMapper.selectBranchByNo(order.getBranchNo());
		//如果无奶站订单
		if(StringUtils.isBlank(order.getBranchNo())){
			if(!"20".equals(order.getPreorderStat())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"无奶站订单，订单状态 preorderStat只能是 未确认状态 20！！！");
			}
			//无奶站订单，销售组织编号不能为空
			if(StringUtils.isBlank(order.getSalesOrg())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"无奶站订单，销售组织编号不能为空!");
			}

			order.setSalesOrg(order.getSalesOrg());
		}else{
			if(StringUtils.isBlank(order.getBranchNo())) throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单奶站编号不能为空!");
			if(branch==null)throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站号为"+order.getBranchNo()+" 的奶站在订户系统中不存在!");

			order.setDealerNo(branch.getDealerNo());//进销商
			order.setSalesOrg(branch.getSalesOrg());
		}
		//order.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());//销售组织
		if(StringUtils.isNotBlank(order.getPayDateStr())){
			try
			{
				order.setPayDate(format.parse(order.getPayDateStr()));
			}
			catch (Exception e)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式有误!");
			}
		}

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
				// 无奶站订单  处理订户信息
				if(StringUtils.isBlank(order.getBranchNo())){
					if(StringUtils.isBlank(order.getSalesOrg())) throw new ServiceException(MessageCode.LOGIC_ERROR,"无奶站订单，不能没有销售组织");
					String addressId = record.getAddress().getAddressId();
					if(StringUtils.isNotBlank(addressId) ){
						TMdAddress cAddress = addressMapper.findAddressById(addressId);
						if(cAddress!=null){
							if(!cAddress.getProvince().equals(record.getAddress().getProvince())
									||!cAddress.getCity().equals(record.getAddress().getCity())
									||!cAddress.getAddressTxt().trim().equals(record.getAddress().getAddressTxt().trim())
									||!cAddress.getRecvName().trim().equals(record.getAddress().getRecvName().trim())
								)
							{
								throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址ID在订户系统已存在，但是对应的地址信息不同，请核对信息");

							}
							String custNo = order.getMilkmemberNo();
							if(StringUtils.isNotBlank(custNo)){
								TVipCustInfo cst = tVipCustInfoService.findVipCustByNo(custNo);
								if(cst!=null){
									if(!custNo.equals(order.getMilkmemberNo())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址ID在订户系统已分配给"+custNo+" "+cst.getVipName()+"订户了，而您的订奶编号为"+order.getMilkmemberNo()+"请核对信息");
									}
									if(StringUtils.isBlank(cst.getBranchNo())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址ID在订户系统已分配给"+custNo+cst.getVipName()+"订户了，但是该订户没有分配奶站，请核对信息");
									}
									record.getAddress().setVipCustNo(custNo);
									String addressAndMilkmember = tVipCustInfoService.addAddressNoBrnachForCust(record.getAddress(),order.getSalesOrg(),null);
									order.setBranchNo(cst.getBranchNo());
								}else{
									throw new ServiceException(MessageCode.LOGIC_ERROR, "订单中的订户ID "+custNo+"在订户系统中不存在!");
								}

							}else{
								//throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址ID在订户系统存在，但是订户地址详细信息对应的订户编号(vipCustNo)为空!，请核对信息");
								Map<String,String> map = new HashMap<String,String>();
								map.put("activityNo",order.getSolicitNo());
								map.put("vipType",order.getDeliveryType());
								map.put("vipSrc",order.getPreorderSource());
								String addressAndMilkmember = tVipCustInfoService.addAddressNoBrnachForCust(record.getAddress(),order.getSalesOrg(),map);
								order.setMilkmemberNo(addressAndMilkmember.split(",")[0]);
								order.setAdressNo(addressAndMilkmember.split(",")[1]);
							}
						}else{
							Map<String,String> map = new HashMap<String,String>();
							map.put("activityNo",order.getSolicitNo());
							map.put("vipType",order.getDeliveryType());
							map.put("vipSrc",order.getPreorderSource());
							String addressAndMilkmember = tVipCustInfoService.addAddressNoBrnachForCust(record.getAddress(),order.getSalesOrg(),map);
							order.setMilkmemberNo(addressAndMilkmember.split(",")[0]);
							order.setAdressNo(addressAndMilkmember.split(",")[1]);
						}
					}else{
						Map<String,String> map = new HashMap<String,String>();
						map.put("activityNo",order.getSolicitNo());
						map.put("vipType",order.getDeliveryType());
						map.put("vipSrc",order.getPreorderSource());
						String addressAndMilkmember = tVipCustInfoService.addAddressNoBrnachForCust(record.getAddress(),order.getSalesOrg(),map);
						order.setMilkmemberNo(addressAndMilkmember.split(",")[0]);
						order.setAdressNo(addressAndMilkmember.split(",")[1]);
					}

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
		}
		if(StringUtils.isBlank(order.getMemberNo())){
			//会员号
			TVipCustInfo vip = tVipCustInfoService.findVipCustByNoForUpt(order.getMilkmemberNo());
			if(vip!=null)order.setMemberNo(vip.getVipCustNoSap());
		}


		//生成每个订单行
		int index = 0;
		BigDecimal orderAmt = new BigDecimal("0.00");//订单总价


		for(TPlanOrderItem entry: record.getEntries()){
			
			//非奶站订单要重新计算金额
			if(!"30".equals(order.getPreorderSource())){
				//如果是  没有奶站的订单  不重新计算金额
				if(StringUtils.isNotBlank(order.getBranchNo())){
					float price = priceService.getMaraPriceForCreateOrder(order.getBranchNo(), entry.getMatnr(), order.getDeliveryType(),branch.getSalesOrg());
					if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"产品价格小于0,请检查传入的商品号，奶站和配送方式!信息："+"奶站："+order.getBranchNo()+"商品号："+entry.getMatnr()+"配送方式："+order.getDeliveryType()+"销售组织："+branch.getSalesOrg());
					entry.setSalesPrice(new BigDecimal(String.valueOf(price)));
				}
			}
			
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
					if(StringUtils.isNotBlank(entry.getEndDispDateStr())){
						entry.setEndDispDate(format.parse(entry.getEndDispDateStr()));
					}else{
						resolveEntryEndDispDate(entry);
					}
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
			BigDecimal entryAmount = BigDecimal.ZERO;
			if(StringUtils.isNotBlank(order.getBranchNo())){
				entryAmount = entryAmount.add(calculateEntryAmount(entry));
				orderAmt = orderAmt.add(entryAmount);
			}else{
			//TODO 无奶站订单如果订单金额没传 是否设为0  因为这个金额会在分奶站后重新算
				orderAmt =  orderAmt.add(order.getInitAmt() == null ? BigDecimal.ZERO : order.getInitAmt());
			}

			
			//促销判断
			if(StringUtils.isNotBlank(entry.getPromotion())&&"10".equals(order.getPaymentmethod()))throw new ServiceException(MessageCode.LOGIC_ERROR,"后付款的订单不能参加促销!");
			//promotionService.calculateEntryPromotion(entry);
			if(StringUtils.isNotBlank(order.getBranchNo()) && "20".equals(order.getPaymentmethod())){
				promotionService.calculateOrderEntryPromotion(entry,entryAmount,order);
			}

			entriesList.add(entry);

			index++;
		}

		//订单剩余金额
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
		order.setResumeFlag("N");
		
		promotionService.calculateOrderPromotion(order);
		//保存订单和行项目
		tPreOrderMapper.insert(record.getOrder());
		entriesList.forEach(entry->{
			tPlanOrderItemMapper.insert(entry);
		});
		
		//非确认的订单直接返回
		if(!"10".equals(order.getPreorderStat())){
			return order.getOrderNo();
		}

		if(StringUtils.isNotBlank(order.getBranchNo())){
			//订户状态更改
			tVipCustInfoService.discontinue(order.getMilkmemberNo(), "10",new com.nhry.utils.date.Date(),new com.nhry.utils.date.Date());
		}

			
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
				if(!"10".equals(order.getPreorderSource()) && !"40".equals(order.getPreorderSource())){
					this.setName("sendOrderToEc");
					messLogService.sendOrderInfo(order, entriesList);
				}

				if(list!=null){
					TPreOrder sendOrder = new TPreOrder();
					sendOrder.setOrderNo(order.getOrderNo());
					sendOrder.setPreorderStat("200");
					sendOrder.setEmpNo(order.getEmpNo());
					messLogService.sendOrderStatus(sendOrder);
				}

				//积分
				if("Y".equals(order.getIsIntegration()) && "20".equals(order.getPaymentStat()) && ("30".equals(order.getPreorderSource())||"70".equals(order.getPreorderSource()))){
					this.setName("updateVip");
					Map<String,String> planOrderMap = new HashMap<String,String>();
					planOrderMap.put("salesOrg",order.getSalesOrg());
					planOrderMap.put("orderNo",order.getOrderNo());
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
						}
						piVipPointCreateBatService.createMemberActivitiesBat(items);
					}
				}

			}
		});
		
		return order.getOrderNo();
	}
	//批量订单导入
	@Override
	public String createOrders(List<OrderCreateModel> records) {
		String s = "";
		if(records != null){
			for(OrderCreateModel record : records){
				s.concat(createOrder(record));
			}
		}
		return s;
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
		int maxEntryDay = 3650;
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
	
	   //根据订单行生成每日计划,修改后付款订单
		private void createDaliyPlanForAfterPay(TPreOrder order ,TPlanOrderItem entry, Date startDate, Date endDate){
   
			//预付款的要付款+装箱才生成日计划
			if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getPaymentStat())){
				return;
			}
			//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
			if("20".equals(order.getMilkboxStat())){
				return;
			}

			//计算每个行项目总共需要送多少天
			int maxEntryDay = 3650;

			//根据最大配送天数的行
			int afterDays = 0;//经过的天数
			//行号唯一，需要判断以前最大的行号
			int daliyEntryNo = 0;//日计划行号
			try{
				daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(order.getOrderNo()) + 1;
			}catch(Exception e){
				//如果找不到最大值
				System.out.println("=============查询日计划最大数发生错误!==========订单号："+order.getOrderNo());
			}
			
			for(int i = 0; i < maxEntryDay; i++,afterDays++){
				//判断是按周期送还是按星期送
				Date today = afterDate(startDate,afterDays);
				
				if(today.after(endDate))break;
				
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
				plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
				plan.setMatnr(entry.getMatnr());//产品编号
				plan.setUnit(entry.getUnit());//配送单位
				plan.setQty(entry.getQty());//产品数量
				plan.setPrice(entry.getSalesPrice());//产品价格
				plan.setPromotionFlag(entry.getPromotion());//促销号
				//日计划行金额和
				BigDecimal qty = new BigDecimal(entry.getQty().toString());
				plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
				
//						plan.setRemainAmt();//订单余额,统一设置
				plan.setStatus("10");//状态
				plan.setCreateAt(new Date());//创建时间
				plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
				plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
				
				if(!endDate.before(entry.getEndDispDate())){
					entry.setEndDispDate(today);
				}
				
				tOrderDaliyPlanItemMapper.insert(plan);
				daliyEntryNo++;
				
			}

			return;
		}
		
		//预付款订单，换部分商品，修改订单用 TODO
		private void createDaliyPlanForPrePay(TPreOrder order ,List<TPlanOrderItem> orgEntries,List<TPlanOrderItem> curEntries,List<TOrderDaliyPlanItem> orgDaliyPlans,List<TPlanOrderItem> unsavedOrgEntries){
			//预付款的要付款+装箱才生成日计划
			if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getPaymentStat())){
				return;
			}
			//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
			if("20".equals(order.getMilkboxStat())){
				return;
			}
			
			//1.新旧对应   
			Date firstDeliveryDate = null;
			Map<TPlanOrderItem,TPlanOrderItem> entriesMap = new HashMap<TPlanOrderItem,TPlanOrderItem>();
			Map<TPlanOrderItem,TPlanOrderItem> entriesMap2 = new HashMap<TPlanOrderItem,TPlanOrderItem>();
			for(TPlanOrderItem org : orgEntries){
				for(TPlanOrderItem cur :curEntries){
					if(cur.getItemNo().equals(org.getItemNo())){
						if(org.isModified()||"Y".equals(org.getNewFlag())){
							if(firstDeliveryDate == null){
								firstDeliveryDate = cur.getStartDispDate();
							}else{
								firstDeliveryDate = firstDeliveryDate.before(cur.getStartDispDate())?firstDeliveryDate:cur.getStartDispDate();
							}
						}
						entriesMap.put(org, cur);
						break;
					}
				}
				for(TPlanOrderItem unsave :unsavedOrgEntries){
					if(unsave.getItemNo().equals(org.getItemNo())){
						entriesMap2.put(org, unsave);
						break;
					}
				}
			}
			
			//当只有删除行项目时下面逻辑成立：
			if(firstDeliveryDate==null){
				for(TPlanOrderItem org : orgEntries){
					if(firstDeliveryDate == null){
						firstDeliveryDate = org.getStartDispDate();
					}else{
						firstDeliveryDate = firstDeliveryDate.before(org.getStartDispDate())?firstDeliveryDate:org.getStartDispDate();
					}
				}
			}
			//end
			
			if(firstDeliveryDate==null)return;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			TOrderDaliyPlanItem record = new TOrderDaliyPlanItem();
			record.setOrderNo(order.getOrderNo());
			record.setDispDateStr(format.format(firstDeliveryDate));
			record.setStatus("10");
			tOrderDaliyPlanItemMapper.deleteFromDateToDate(record);
			
			
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());
			BigDecimal initAmt = order.getInitAmt();
			for(TOrderDaliyPlanItem p :daliyPlans){
				if(!"30".equals(p.getStatus())){
					//修改后开始日期前的，未停定的所有日计划金额
					initAmt = initAmt.subtract(p.getAmt());
				}
			}
			
			int maxEntryDay = 3650;

			//根据最大配送天数的行
			int afterDays = 0;//经过的天数
			//行号唯一，需要判断以前最大的行号
			int daliyEntryNo = 0;//日计划行号
			try{
				daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(order.getOrderNo()) + 1;
			}catch(Exception e){
				//如果找不到最大值
				System.out.println("=============查询日计划最大数发生错误!==========订单号："+order.getOrderNo());
			}
			
			outer:for(int i = 0; i < maxEntryDay; i++,afterDays++){
				for(TPlanOrderItem entry : orgEntries){
					
					if(!entry.isModified()){//没有修改的行项目和新加的行项目
						
						//判断是按周期送还是按星期送
						Date today = afterDate(firstDeliveryDate,afterDays);
						if(today.before(entry.getStartDispDate()))continue;
						if(daliyPlans.stream().anyMatch((e)->e.getItemNo().equals(entry.getItemNo()) &&e.getDispDate().equals(today)))continue;//有此行，此日期的，就不生成日计划
						
						if("10".equals(entry.getRuleType())){
							int gapDays = entry.getGapDays() + 1;//间隔天数
							if(daysOfTwo(entry.getStartDispDate(),today)%gapDays != 0){
								if(entry.getRuleTxt()!=null){
									//
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
						plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
						plan.setMatnr(entry.getMatnr());//产品编号
						plan.setUnit(entry.getUnit());//配送单位
						plan.setQty(entry.getQty());//产品数量
						plan.setPrice(entry.getSalesPrice());//产品价格
						plan.setPromotionFlag(entry.getPromotion());//促销号
						//日计划行金额和
						BigDecimal qty = new BigDecimal(entry.getQty().toString());
						plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
						
						initAmt = initAmt.subtract(plan.getAmt());//TODO
						
						if(initAmt.floatValue() < 0)break outer;
						
						plan.setStatus("10");//状态
						plan.setCreateAt(new Date());//创建时间
						plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
						plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
						
						entry.setEndDispDate(today);
						
						tOrderDaliyPlanItemMapper.insert(plan);
						daliyEntryNo++;
						
					}else{//修改的行项目
						
						//判断是按周期送还是按星期送
						Date today = afterDate(firstDeliveryDate,afterDays);
						
						TPlanOrderItem orgEntry = entriesMap2.get(entry);
						TPlanOrderItem curEntry = entriesMap.get(entry);
						if(today.before(orgEntry.getStartDispDate()))continue;
						if(curEntry.getStopStartDate()!=null){
							if(!today.before(curEntry.getStopStartDate()))continue;
						}
						if(daliyPlans.stream().anyMatch((e)->e.getItemNo().equals(entry.getItemNo()) &&e.getDispDate().equals(today)))continue;//有此行，此日期的，就不生成日计划
						
						if(today.before(entriesMap.get(entry).getStartDispDate())){
							//中间部分按原来的行项目生成日日计划
							
							if("10".equals(orgEntry.getRuleType())){
								int gapDays = orgEntry.getGapDays() + 1;//间隔天数
								if(daysOfTwo(orgEntry.getStartDispDate(),today)%gapDays != 0){
									if(orgEntry.getRuleTxt()!=null){
										
									}else{
										continue;
									}
								}
							}
							else if("20".equals(orgEntry.getRuleType())){
								String weekday = getWeek(today);
								List<String> deliverDays = Arrays.asList(orgEntry.getRuleTxt().split(","));
								if(!deliverDays.contains(weekday)){
									continue;//如果选择的星期几不送，则跳过今天生成日计划
								}
							}

							//生成该订单行的每日计划
							TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
							plan.setOrderNo(orgEntry.getOrderNo());//订单编号
							plan.setOrderDate(orgEntry.getOrderDate());//订单日期
							plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
							plan.setItemNo(orgEntry.getItemNo());//预订单日计划行
							plan.setDispDate(today);//配送日期
							plan.setReachTimeType(orgEntry.getReachTimeType());//送达时段类型
							plan.setMatnr(orgEntry.getMatnr());//产品编号
							plan.setUnit(orgEntry.getUnit());//配送单位
							plan.setQty(orgEntry.getQty());//产品数量
							plan.setPrice(orgEntry.getSalesPrice());//产品价格
							plan.setPromotionFlag(orgEntry.getPromotion());//促销号
							//日计划行金额和
							BigDecimal qty = new BigDecimal(orgEntry.getQty().toString());
							plan.setAmt(orgEntry.getSalesPrice().multiply(qty));//金额小计
							
							initAmt = initAmt.subtract(plan.getAmt());//TODO
							
							if(initAmt.floatValue() < 0)break outer;
							
							plan.setStatus("10");//状态
							plan.setCreateAt(new Date());//创建时间
							plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
							plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
							
							orgEntry.setEndDispDate(today);
							
							tOrderDaliyPlanItemMapper.insert(plan);
							daliyEntryNo++;
							
						}else{
							
							//后面按有效开始日期改
							if("10".equals(entry.getRuleType())){
								int gapDays = entry.getGapDays() + 1;//间隔天数
								if(afterDays%gapDays != 0){
									if(entry.getRuleTxt()!=null){
										
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
							plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
							plan.setMatnr(entry.getMatnr());//产品编号
							plan.setUnit(entry.getUnit());//配送单位
							plan.setQty(entry.getQty());//产品数量
							plan.setPrice(entry.getSalesPrice());//产品价格
							plan.setPromotionFlag(entry.getPromotion());//促销号
							//日计划行金额和
							BigDecimal qty = new BigDecimal(entry.getQty().toString());
							plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
							
							initAmt = initAmt.subtract(plan.getAmt());//TODO
							
							if(initAmt.floatValue() < 0)break outer;
							
							plan.setStatus("10");//状态
							plan.setCreateAt(new Date());//创建时间
							plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
							plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
							
							entry.setEndDispDate(today);
							
							tOrderDaliyPlanItemMapper.insert(plan);
							daliyEntryNo++;
						}
					}
				}
			}
			
			return;
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
		//已生效10、未生效20、无效30,作废40, preorderstat
		//先款10、后付款20  paymentmethod
		//在订10、停订20、退订30  完结40 sign
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
			}
			
			//生成每日计划
			List<TOrderDaliyPlanItem> list = createDaliyPlan(order,orgEntries);
			//如果有赠品，生成赠品的日计划
			promotionService.createDaliyPlanByPromotion(order,orgEntries,list);
			
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
	* @title: editOrderForLongForViewPlans
	* @description: 长期修改订单(预览日计划)
	* @param record
	* @return
	* @see com.nhry.service.order.dao.OrderService#editOrder(com.nhry.model.order.OrderCreateModel)
	*/
	@Override
	public List editOrderForLongForViewPlans(OrderEditModel record)
	{
			List<TOrderDaliyPlanItem> backData = null;
			
			if(record.getEntries()==null||record.getEntries().size()<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"不能删除所有的行项目，请退订订单!");
			convertEntryDate(record.getEntries());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(record.getOrder().getOrderNo());
			if("10".equals(orgOrder.getPaymentmethod())){
				if(customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"已经有收款单了，请不要修改订单，或者去删除收款单!");
			}
			if("20".equals(orgOrder.getPaymentmethod())){
				TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo());
				if(bill!=null && "10".equals(bill.getStatus())){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
				}
			}
			ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrder().getOrderNo());
			ArrayList<TPlanOrderItem> curEntries = record.getEntries();
			ArrayList<TPlanOrderItem> modifiedEntries = new ArrayList<TPlanOrderItem>();
			ArrayList<TPlanOrderItem> removedEntries = new ArrayList<TPlanOrderItem>();
			
			String state = orgOrder.getPaymentmethod();
			if("10".equals(state)){
				//后付款
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
				//有路单一定作为有效期修改，*日期提前或者没有日计划，不作为有效期修改
				if((daliyPlans!=null&&daliyPlans.size() > 0&&StringUtils.isBlank(record.getEditDate()))||tDispOrderItemMapper.selectCountByOrgOrder(orgOrder.getOrderNo()) > 0 ){
					//作为有效期修改
					//修改订单根据行项目编号来确定行是否修改，换商品或改数量
					for(TPlanOrderItem orgEntry : orgEntries){
						boolean delFlag = true;
						boolean modiFlag = false;
						for(TPlanOrderItem curEntry : curEntries){
							if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
								delFlag = false;
//								if(StringUtils.isNotBlank(curEntry.getDeletePlansFlag())||StringUtils.isNotBlank(curEntry.getIsDeletedFlag()))break;
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
								String curStartstr = format.format(curEntry.getStartDispDate());
								String curEndstr = format.format(curEntry.getEndDispDate());
								if(!startstr.equals(curStartstr) || !endstr.equals(curEndstr) ){
									modiFlag = true;
								}
								
								if(!modiFlag){
									break;
								}
//								if(StringUtils.isNotBlank(orgEntry.getPromotion()))throw new ServiceException(MessageCode.LOGIC_ERROR,"促销商品行不能更改!");
								if(curEntry.getStartDispDate().before(orgEntry.getStartDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,curEntry.getStartDispDate()+"有效期不能在配送日期之外!");
								daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(orgEntry.getItemNo()))
							   	.forEach((e)->{
							   	if(!e.getDispDate().before(curEntry.getStartDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
							   });
								
								//删除不需要的日单
								TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
								newPlan.setOrderNo(orgOrder.getOrderNo());
								newPlan.setItemNo(orgEntry.getItemNo());
								newPlan.setDispDateStr(curStartstr);
								newPlan.setUnit(curEndstr);
								tOrderDaliyPlanItemMapper.deletePlansForLongEdit(newPlan);
								
								createDaliyPlanForAfterPay(orgOrder,orgEntry,curEntry.getStartDispDate(),curEntry.getEndDispDate());
								//保存修改后的该行
								tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
								
								//行修改完毕
								break;
							}
						}
						if(delFlag){
							if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
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
					
					//行项目停订的,日期内的日计划停订
					TOrderDaliyPlanItem dRecord = new TOrderDaliyPlanItem();
					dRecord.setOrderNo(orgOrder.getOrderNo());
					for(TPlanOrderItem curEntry : curEntries){
						for(TPlanOrderItem orgEntry : orgEntries){
							if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
								if(curEntry.getStopStartDate()!=null&&curEntry.getStopEndDate()!=null){
									if(curEntry.getStopStartDate().before(orgEntry.getStartDispDate())||curEntry.getStopEndDate().after(orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"停订的日期不能在配送日期之外!");
									daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(curEntry.getItemNo()))
									.forEach((e)->{
										if(!e.getDispDate().before(curEntry.getStopStartDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
									});
									dRecord.setItemNo(curEntry.getItemNo());
									dRecord.setDispDateStr(format.format(curEntry.getStopStartDate()));
									dRecord.setReachTime(format.format(curEntry.getStopEndDate()));
									tOrderDaliyPlanItemMapper.updateDaliyPlansToStopDateToDate(dRecord);
								}
								if(!(orgEntry.getIsStop()==null?"":orgEntry.getIsStop()).equals(curEntry.getIsStop()==null?"":curEntry.getIsStop())){
									orgEntry.setIsStop(curEntry.getIsStop());
									tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
								}
								break;
							}
						}
					}
					
					//新增的行项目
					int index =  tPlanOrderItemMapper.selectEntriesQtyByOrderCode(record.getOrder().getOrderNo());
					for(TPlanOrderItem entry : curEntries){
						if("Y".equals(entry.getNewFlag())){
//							if(entry.getStartDate()==null||entry.getStartDate().before(entry.getStartDispDate())||entry.getStartDate().after(entry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR, "开始配送日期填写有误，请检查");
							entry.setOrderNo(orgOrder.getOrderNo());
							entry.setItemNo(orgOrder.getOrderNo() + String.valueOf(index));//行项目编号
							entry.setRefItemNo(String.valueOf(index));//参考行项目编号
							entry.setOrderDate(orgOrder.getOrderDate());//订单日期
							entry.setCreateAt(new Date());//创建日期
							entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
							entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
							calculateEntryAmount(entry);
							Date tmp = entry.getEndDispDate();
							createDaliyPlanForAfterPay(orgOrder,entry,entry.getStartDispDate(),entry.getEndDispDate());
							entry.setEndDispDate(tmp);
							tPlanOrderItemMapper.insert(entry);
							index++;
						}
					}
		   		
					//生成新的每日订单
					daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
					
					//更新订单金额等
					BigDecimal initAmt = new BigDecimal("0.00");
					BigDecimal usedAmt = new BigDecimal("0.00");
					for(TOrderDaliyPlanItem plan : daliyPlans){
						if("30".equals(plan.getStatus()))continue;
						initAmt = initAmt.add(plan.getAmt());
						if("20".equals(plan.getStatus())){
							usedAmt.add(plan.getAmt());
						}
					}
					orgOrder.setInitAmt(initAmt);
					orgOrder.setCurAmt(initAmt.subtract(usedAmt));

					//订单截止日期修改
					for(TOrderDaliyPlanItem plan : daliyPlans){
						if(!"30".equals(plan.getStatus())){
							orgOrder.setEndDate(plan.getDispDate());
							break;
						}
					}
					
					Collections.reverse(daliyPlans);
					for(TOrderDaliyPlanItem plan : daliyPlans){
			   		if("30".equals(plan.getStatus())){
			   			plan.setRemainAmt(initAmt);
			   			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
			   			continue;
			   		}
			   		plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
			   		initAmt = initAmt.subtract(plan.getAmt());
			   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
			   	}
					
				}else{
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
							if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
							removedEntries.add(orgEntry);
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
					
					orgEntries.removeAll(removedEntries);
					//更新订单
		   		orgOrder.setInitAmt(orderAmt);
		   		orgOrder.setCurAmt(orderAmt.subtract(orderUsedAmt));
		   		
					//生成新的每日订单
					createDaliyPlan(orgOrder , modifiedEntries);
					daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
					calculateDaliyPlanRemainAmt(orgOrder,daliyPlans);
					
				}
				
			}else{
				
				ArrayList<TPlanOrderItem> unsavedOrgEntries = new ArrayList<TPlanOrderItem>();
				orgEntries.stream().forEach((orgEntry)->{
					TPlanOrderItem entry = new TPlanOrderItem();
					cloneOrderEntry(entry,orgEntry);
					unsavedOrgEntries.add(entry);
				});
				
				ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
				if((daliyPlans!=null&&daliyPlans.size() > 0&&StringUtils.isBlank(record.getEditDate()))||tDispOrderItemMapper.selectCountByOrgOrder(orgOrder.getOrderNo()) > 0 ){
					//有效期修改
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
								String curStartstr = format.format(curEntry.getStartDispDate());
								String curEndstr = format.format(curEntry.getEndDispDate());
								if(!startstr.equals(curStartstr) || !endstr.equals(curEndstr) ){
									modiFlag = true;
								}
								
								if(!modiFlag){
									break;
								}
								
								if(curEntry.getStartDispDate().before(orgEntry.getStartDispDate())||curEntry.getEndDispDate().after(orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,curEntry.getStartDispDate()+"到"+curEntry.getEndDispDate()+"[有效期不能在配送日期之外!]");
								daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(orgEntry.getItemNo()))
							   	.forEach((e)->{
							   	if(!e.getDispDate().before(curEntry.getStartDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
							   });
								
								orgEntry.setModified(true);
								//删除不需要的日单
								TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
								newPlan.setOrderNo(orgOrder.getOrderNo());
								newPlan.setItemNo(orgEntry.getItemNo());
								newPlan.setDispDateStr(curStartstr);
								newPlan.setUnit(curEndstr);
								tOrderDaliyPlanItemMapper.deletePlansForLongEdit(newPlan);
								
								//保存修改后的该行
								tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
								
								//行修改完毕
								break;
							}
						}
						if(delFlag){
							if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
							//此行删除了，删除所有剩余的日单
							removedEntries.add(orgEntry);
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
					
					orgEntries.removeAll(removedEntries);
					
					//新增的行项目
					int index =  tPlanOrderItemMapper.selectEntriesQtyByOrderCode(record.getOrder().getOrderNo());
					for(TPlanOrderItem entry : curEntries){
						if("Y".equals(entry.getNewFlag())){
							entry.setOrderNo(orgOrder.getOrderNo());
							entry.setItemNo(orgOrder.getOrderNo() + String.valueOf(index));//行项目编号
							entry.setRefItemNo(String.valueOf(index));//参考行项目编号
							entry.setOrderDate(orgOrder.getOrderDate());//订单日期
							entry.setCreateAt(new Date());//创建日期
							entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
							entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
							calculateEntryAmount(entry);
							orgEntries.add(entry);
							tPlanOrderItemMapper.insert(entry);
							index++;
						}
					}
					
					//行项目停订的,日期内的日计划停订
					TOrderDaliyPlanItem dRecord = new TOrderDaliyPlanItem();
					dRecord.setOrderNo(orgOrder.getOrderNo());
					for(TPlanOrderItem curEntry : curEntries){
						for(TPlanOrderItem orgEntry : orgEntries){
							if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
								if(curEntry.getStopStartDate()!=null&&curEntry.getStopEndDate()!=null){
									if(curEntry.getStopStartDate().before(orgEntry.getStartDispDate())||curEntry.getStopEndDate().after(orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"停订的日期不能在配送日期之外!");
									daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(curEntry.getItemNo()))
									.forEach((e)->{
										if(!e.getDispDate().before(curEntry.getStopStartDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间，无法停订!");
									});
									orgEntry.setToStop("Y");
									orgEntry.setModified(true);
									dRecord.setItemNo(curEntry.getItemNo());
									dRecord.setDispDateStr(format.format(curEntry.getStopStartDate()));
//									dRecord.setReachTime(format.format(curEntry.getStopEndDate()));
									tOrderDaliyPlanItemMapper.updateDaliyPlansToStopDateToDate(dRecord);
								}
								if(!(orgEntry.getIsStop()==null?"":orgEntry.getIsStop()).equals(curEntry.getIsStop()==null?"":curEntry.getIsStop())){
									orgEntry.setIsStop(curEntry.getIsStop());
									tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
								}
								break;
							}
						}
					}
					
					createDaliyPlanForPrePay(orgOrder,orgEntries,curEntries,daliyPlans,unsavedOrgEntries);
					
					
					//生成新的每日订单
					daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
					
					//更新订单金额等
					BigDecimal initAmt = orgOrder.getInitAmt();

					//订单截止日期修改
					for(TOrderDaliyPlanItem plan : daliyPlans){
						if(!"30".equals(plan.getStatus())){
							orgOrder.setEndDate(plan.getDispDate());
							break;
						}
					}
					
					//回改订单行项目,更新最后配送日期
//	   			for( TPlanOrderItem entry :orgEntries){
//	   				for(TOrderDaliyPlanItem p:daliyPlans){
//	   					if(entry.getItemNo().equals(p.getItemNo())){
//	   						entry.setEndDispDate(p.getDispDate());
//	   						//保存修改后的该行
//	   						tPlanOrderItemMapper.updateEntryByItemNo(entry);
//	   						break;
//	   					}
//	   				}
//	   			}
					
					Collections.reverse(daliyPlans);
					for(TOrderDaliyPlanItem plan : daliyPlans){
						if(plan.getGiftQty()!=null)continue;
			   		if("30".equals(plan.getStatus())){
			   			plan.setRemainAmt(initAmt);
			   			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
			   			continue;
			   		}
			   		plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
			   		initAmt = initAmt.subtract(plan.getAmt());
			   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
			   	}
					
				}else{
					//先付款,订单总金额不变,配送到金额为0为止
					//修改订单根据行项目编号来确定行是否修改，换商品或改数量
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
//								tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
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
							if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
							//此行删除了，删除所有剩余的日单
							removedEntries.add(orgEntry);
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
					
					orgEntries.removeAll(removedEntries);
					//生成新的每日订单
		   		createDaliyPlanForLongEdit(orgOrder , modifiedEntries ,orgEntries);
		   		
				}
				
			}
			
			OrderSearchModel mo = new OrderSearchModel();
			mo.setOrderNo(orgOrder.getOrderNo());
			backData = tOrderDaliyPlanItemMapper.selectDaliyOrdersAll(mo);
			
			throw new ServiceException(MessageCode.LOGIC_ERROR, backData);
			
	}

	public int uptOrderLongLog(OrderEditModel record,List<TPlanOrderItem> orgEntrys,ArrayList<TOrderDaliyPlanItem> daliyPlans){
		List<TPlanOrderItem>  curEntrys = record.getEntries();
		if(curEntrys==null||curEntrys.size()<=0) return 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String orderNo = record.getOrder().getOrderNo();
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(orderNo);
		TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orderNo);
		if(bill!=null){
			if("10".equals(orgOrder.getPaymentmethod())){
				return 0;
			}else{
				if("10".equals(bill.getStatus())){
					return 0;
				}
			}
		}

		TSysUser user = userSessionService.getCurrentUser();
		//备注日志
		if(ContentDiffrentUtil.isDiffrent(orgOrder.getMemoTxt(),record.getOrder().getMemoTxt())){
			OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.ORDER, OrderLogEnum.MEMO_TXT,null,null,orgOrder.getMemoTxt(),
					record.getOrder().getMemoTxt(),null,null,user,operationLogMapper);
		}
		if(daliyPlans==null || daliyPlans.size()<=0){
			//没有日订单，直接修改行项目
			this.noRouteOrderEditLog(orgOrder,orgEntrys,curEntrys,user);
		}else{
			int dispNums = tDispOrderItemMapper.selectCountByOrgOrder(orderNo);
			if(dispNums<=0){
				this.noRouteOrderEditLog(orgOrder,orgEntrys,curEntrys,user);
			}else{

			}

		}
		return 1;
	}

	private int noRouteOrderEditLog(TPreOrder orgOrder, List<TPlanOrderItem> orgEntrys, List<TPlanOrderItem> curEntrys,TSysUser user) {
		//没有产生路单的日订单修改日志
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<TPlanOrderItem> cloneCurEntrys = new ArrayList<TPlanOrderItem>();
		cloneCurEntrys.addAll(curEntrys);
		for(TPlanOrderItem orgEntry:orgEntrys){
			boolean delFlag = true;
			for(TPlanOrderItem curEntry:curEntrys){
				if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
					cloneCurEntrys.remove(curEntry);
					delFlag = false;
					if (curEntry.getMatnr().equals(orgEntry.getMatnr()) && curEntry.getQty().equals(orgEntry.getQty()) &&
							format.format(orgEntry.getStartDispDate()).equals(format.format(curEntry.getStartDispDate())) &&
							format.format(orgEntry.getEndDispDate()).equals(format.format(curEntry.getEndDispDate())) &&
							!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTime(), curEntry.getReachTime()) &&
							!ContentDiffrentUtil.isDiffrent(orgEntry.getGapDays(), curEntry.getGapDays()) &&
							!ContentDiffrentUtil.isDiffrent(orgEntry.getRuleTxt(), curEntry.getRuleTxt())&&
							curEntry.getStopStartDate()==null
						)
					{
						continue;
					} else {
						this.editOrderDispTypeForLongLog(orgEntry,curEntry,user);
					}
				}
			}
			if(delFlag){
				//删除日志
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.DEL_ITEM,null,null,
						orgEntry.getMatnr()+","+orgEntry.getShortTxt()+","+orgEntry.getQty(),null,null,null,user,operationLogMapper);
			}
		}
		if(cloneCurEntrys!=null && cloneCurEntrys.size()>0) {
			//添加的行项目 日志
			for (TPlanOrderItem entry : cloneCurEntrys) {
					OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(), LogType.ORDER, OrderLogEnum.ADD_PRODUCT, null, null,
							null, entry.getMatnr()+","+ entry.getShortTxt()+","+ format.format(entry.getStartDispDate()) + "至" + format.format(entry.getEndDispDate()), null, null, user, operationLogMapper);
			}
		}
		return 1;
	}

	private int haveRouteOrderEditLog(TPreOrder orgOrder, List<TPlanOrderItem> orgEntrys, List<TPlanOrderItem> curEntrys,TSysUser user) {
		//产生路单的日订单修改日志
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<TPlanOrderItem> cloneCurEntrys = new ArrayList<TPlanOrderItem>();
		cloneCurEntrys.addAll(curEntrys);
		for(TPlanOrderItem orgEntry:orgEntrys){
			boolean delFlag = true;
			for(TPlanOrderItem curEntry:curEntrys){
				if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
					cloneCurEntrys.remove(curEntry);
					delFlag = false;
					if (curEntry.getMatnr().equals(orgEntry.getMatnr()) && curEntry.getQty().equals(orgEntry.getQty()) &&
							format.format(orgEntry.getStartDispDate()).equals(format.format(curEntry.getStartDispDate())) &&
							format.format(orgEntry.getEndDispDate()).equals(format.format(curEntry.getEndDispDate())) &&
							!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTime(), curEntry.getReachTime()) &&
							!ContentDiffrentUtil.isDiffrent(orgEntry.getGapDays(), curEntry.getGapDays()) &&
							!ContentDiffrentUtil.isDiffrent(orgEntry.getRuleTxt(), curEntry.getRuleTxt())&&
							curEntry.getStopStartDate()==null
							)
					{
						continue;
					} else {
						confirmAllDispDateAfterDate(curEntry,orgEntry, orgOrder.getOrderNo());
						this.editOrderDispTypeForLongLog(orgEntry,curEntry,user);
					}
				}
			}
			if(delFlag){
				//删除日志
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.DEL_ITEM,null,null,
						orgEntry.getMatnr()+","+orgEntry.getShortTxt()+","+orgEntry.getQty(),null,null,null,user,operationLogMapper);
			}
		}
		if(cloneCurEntrys!=null && cloneCurEntrys.size()>0) {
			//添加的行项目 日志
			for (TPlanOrderItem entry : cloneCurEntrys) {
				confirmAllDispDateAfterDate(entry, null,orgOrder.getOrderNo());
				OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(), LogType.ORDER, OrderLogEnum.ADD_PRODUCT, null, null,
						null, entry.getMatnr()+","+ entry.getShortTxt() + "," + format.format(entry.getStartDispDate()) + "至" + format.format(entry.getEndDispDate()), null, null, user, operationLogMapper);
			}
		}
		return 1;
	}

	/**
	 * 订单编辑方法
	 * @param record
	 * （1）没有日订单的订单编辑（预付款没付款，后付款没有装箱） 直接删除订单行项目，依照前台传来的行项目为准重生，
	 * 如果行项目有停订 则更新订单行截止日期，订单更新金额和截止日期
	 * （2）有日订单但是没有路单的订单编辑（区分预付款和后付款）
	 *    1）如果是后付款，首先删除所有的行项目，日订单。会直接根据前台来的行项目为准，重新生成日订单，和订单行，更新订单
	 *    2）如果是预付款，首先删除所有的行项目，日订单。会根据前台传来的行项目为准，重新生成订单行并按照金额重新生成日订单，
	 *       根据生成后的日订单统计，更新行项目的配送数量和截止日期，并更新订单截止日期
	 * （3）产生路单的订单编辑
	 * 	  1）如果是后付款，获取修改前所有的行项目，循环和前台传来的新的行项目比较
	 * 	     ①如果没有任何变化，抛出异常，没有变化。
	 * 	     ②如果行项目有更新，有参与促销的订单行不参与修改
	 * 	         修改订单行：会根据变化的开始和截止日期 去查看路单，如果该日期范围内有路单产生或者有路单确认
	 * 	         			则抛出异常
	 *				如果修改数量、产品，配送日期、配送类型 将最新行项目的开始日期之后的日订单全部删除
	 *		 		如果配送期间有完结订单抛出异常）
	 *			 	如果该行项目被停订，则将订单行截止日期更新为停订开始日期，并将停订开始日期及后的日订单全部删除
	*			 	并更新行项目
	 *		③如果有新添加的行项目，添加行项目
	 *		④如果有删除的行项目 ，抛出异常，产生路单的订单不允许删除行项目
	 *	    此时，如果订单有被修改，则根据订单和最新的行项目 生成日订单。更新日订单行的行号(plan_item_no),剩余金额（remain_amt）
	 *	    生成日订单不根据金钱生
	 *	    ⑤生成或更新日订单时
	 *	        .首先根据最新的行项目找出最早的配送日期（firstDeliveryDay），和最晚的配送日期(lastDeliveryDay),
	 *	        算出循环总天数（lastDeliveryDay-firstDeliveryDay+1），
	 *	          并记录每个行项目的配送天数差（截止日期-开始日期+1）
	 *	        .查询出还没被删除的日订单（排序和查看日计划一直，disp_date asc,remain_amt desc）
	 *	        .循环天数  和 行项目，如果（disp_date,item_no ）对应的日订单存在 则说明该日订单没被删除，则更新plan_item_no
	 *	          如果不存在生成（此时不关心remain_amt）
 *	           .最后取出所有日订单（排序和查看日计划一致），并更新remain_amt 字段
 *	       ⑥ 更新订单行的配送总量，更新订单的截止日期
	 *
	 * @return
     */
	@Override
	public int uptOrderlong(OrderEditModel record)
	{
		List<TPlanOrderItem>  curEntrys = record.getEntries();
		if(curEntrys==null||curEntrys.size()<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"不能删除所有的行项目，请退订订单!");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String orderNo = record.getOrder().getOrderNo();
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(orderNo);
		TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orderNo);
		if(bill!=null)
		{
			if("10".equals(orgOrder.getPaymentmethod())){
				if("10".equals(bill.getStatus()))throw new ServiceException(MessageCode.LOGIC_ERROR,"后付款订单"+orderNo+"  已经有收款单了，请不要修改订单，或者去删除收款单!");
				if("20".equals(bill.getStatus()))throw new ServiceException(MessageCode.LOGIC_ERROR,"后付款订单"+orderNo+"  已经收过款了，请不要修改订单!");
			}else{
				if("10".equals(bill.getStatus())){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+orderNo+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
				}
			}
		}
		TPromotion promModel = null;
		boolean orderPromFlag = false;

		if(StringUtils.isNotBlank(orgOrder.getPromotion()) && StringUtils.isNotBlank(orgOrder.getPromItemNo())){
			//订单 整单参加促销  整单满减
			orderPromFlag = true;
			promModel = promotionService.selectPromotionByPromNoAndItemNo(orgOrder.getPromotion(),orgOrder.getPromItemNo());
		}
		TSysUser user = userSessionService.getCurrentUser();
		List<TPlanOrderItem> orgEntrys = tPlanOrderItemMapper.selectByOrderCode(orderNo);
		//日志提前处理
		//获取出现在所有的日订单，用来在做修改配送日期时 是否含有完结的日订单
		ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
		//如果日订单还没产生，可以任意修改，相当于重做订单
		if(daliyPlans==null || daliyPlans.size()<=0){
			//日志
			/**（1） 没有日计划的订单（未收款的预付款和未装箱的后付款）
			 * 删除原来的订单行，重新生成并更新订单的总金额和截止日期
			 */
			noRouteOrderEditLog(orgOrder,orgEntrys,curEntrys,user);
			uptOrderNoDailyOrder(orgEntrys,record,orgOrder);
		}else{
			//查看该订单是否有生成路单
			int dispNums = tDispOrderItemMapper.selectCountByOrgOrder(orderNo);
			if(dispNums<=0){
				//没有产生路单的订单编辑日志
				noRouteOrderEditLog(orgOrder,orgEntrys,curEntrys,user);
				//没有产生路单
				if("10".equals(orgOrder.getPaymentmethod())){
					//如果是后付款  可以任意修改，相当于重做订单
					tOrderDaliyPlanItemMapper.deletePlansByOrder(orderNo);
					uptOrderNoDailyOrder(orgEntrys,record,orgOrder);
					createDaliyPlan(orgOrder,tPlanOrderItemMapper.selectByOrderCode(orderNo));

				}else if("20".equals(orgOrder.getPaymentmethod())){
					//如果是预付款  以订单金额为基础修改
					uptBeforeOrderByAmt(orgEntrys,record,orgOrder);
				}
			}else {
				//有路单的订单编辑日志
				haveRouteOrderEditLog(orgOrder,orgEntrys,curEntrys,user);
				List<TPlanOrderItem> curAllEntry = new ArrayList<TPlanOrderItem>();
				int daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(orgOrder.getOrderNo()) + 1;
				//预付款订单修改
				if ("20".equals(orgOrder.getPaymentmethod())){
					boolean modiFlag = false;
					boolean onlyReachType = true;
					//原行项目是否参加了促销标识
					boolean planItemPromFlag = orgEntrys.stream().anyMatch(item->(StringUtils.isNotBlank(item.getPromotion())&&StringUtils.isNotBlank(item.getPromItemNo())));
					//用来判断行项目是否是新加行项目
					List<TPlanOrderItem> cloneCurEntrys = new ArrayList<TPlanOrderItem>();
					cloneCurEntrys.addAll(curEntrys);
					//orgEntrys 原来的行项目
					for (TPlanOrderItem orgEntry : orgEntrys) {
						boolean delFlag = true;
						for (TPlanOrderItem curEntry : curEntrys) {
							if (curEntry.getItemNo().equals(orgEntry.getItemNo())) {
								cloneCurEntrys.remove(curEntry);
								delFlag = false;
								if (curEntry.getMatnr().equals(orgEntry.getMatnr()) && curEntry.getQty().equals(orgEntry.getQty()) &&
										format.format(orgEntry.getStartDispDate()).equals(format.format(curEntry.getStartDispDate())) &&
										format.format(orgEntry.getEndDispDate()).equals(format.format(curEntry.getEndDispDate())) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTime(), curEntry.getReachTime()) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTimeType(), curEntry.getReachTimeType()) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getGapDays(), curEntry.getGapDays()) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getRuleTxt(), curEntry.getRuleTxt())&&
										curEntry.getStopStartDate()==null
									)
								{
									curAllEntry.add(orgEntry);
									continue;
								} else {
								/*	if (StringUtils.isNotBlank(orgEntry.getPromotion()))
										throw new ServiceException(MessageCode.LOGIC_ERROR, "促销商品行不能更改!");*/
									//修改前的  行项目是否参加促销标记位 默认没有参加促销
									boolean itemPromFlag = false;
									if(StringUtils.isNotBlank(orgEntry.getPromotion()) && StringUtils.isNotBlank(orgEntry.getPromItemNo())){
										//修改前的  行项目参加了促销，促销标记为改为true
										if(itemPromFlag || promModel!=null) throw new ServiceException(MessageCode.LOGIC_ERROR,"一个订单只能参加一个促销");
										itemPromFlag = true;
										promModel = promotionService.selectPromotionByPromNoAndItemNo(orgEntry.getPromotion(),orgEntry.getPromItemNo());
									}

									modiFlag = true;
									boolean flag = false;
									//产生路单的订单行 如果有变化  验证信息
									confirmAllDispDateAfterDate(curEntry,orgEntry, orderNo);
									//可以修改行项目
									if (!orgEntry.getMatnr().equals(curEntry.getMatnr())) {//换商品
										// 如果订单有参加促销
										if(orderPromFlag || planItemPromFlag){
											/*
												如果是该订单行参加了促销
												1.单品满赠  促销订单不能修改产品
												2.单品满减 促销订单不能修改产品
											* */
											if(itemPromFlag){
												//单品满赠
												if("Z008".equals(promModel.getPromSubType())){
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该产品行参加了单品满赠促销，不能修改产品");
												} else	if("Z015".equals(promModel.getPromSubType())){
													//单品满减
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该产品行参加了单品满减促销，不能修改产品");
												}else{
													throw new ServiceException(MessageCode.LOGIC_ERROR,"产品行参加了促销，不能更换产品");
												}
											}else if(planItemPromFlag){
												throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单有参加参加了促销，不能修改产品");
											}else if (orderPromFlag){
											/*	if("Z016".equals(promModel.getPromSubType())){
													//整单满减  促销订单可以修改同价产品
													if(orgEntry.getSalesPrice().compareTo(curEntry.getSalesPrice())!=0){
														throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了整单满减促销，只能更换同价产品");
													}
												}*/
												if("Z017".equals(promModel.getPromSubType())){
													throw new ServiceException(MessageCode.LOGIC_ERROR,"年卡订单不能更换产品，更换产品需要先退订，然后重新做年卡订单！！");
												}

											}
										}
										flag = true;
										onlyReachType =false;
										orgEntry.setMatnr(curEntry.getMatnr());
										orgEntry.setSalesPrice(curEntry.getSalesPrice());
										orgEntry.setUnit(curEntry.getUnit());
									}
									if (orgEntry.getQty() != curEntry.getQty()) {//改数量
										if(orderPromFlag || planItemPromFlag){
											;if(itemPromFlag){
												/*
													如果是该订单有行项目参加了促销
													1.单品满赠  促销订单不能修改配送数量
													2.单品满减 促销订单不能修改配送数量
												* */
												if("Z008".equals(promModel.getPromSubType())){
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该产品行参加了单品满赠促销，不能修改数量");
												} else	if("Z015".equals(promModel.getPromSubType())){
													//单品满减
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该产品行参加了单品满减促销，不能修改数量");
												}else{
													throw new ServiceException(MessageCode.LOGIC_ERROR,"产品行参加了促销，不能修改数量");
												}
											}else if(planItemPromFlag){
												throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单有行项目参加参加了促销，不能修改数量");
											}
										/*	else if(orderPromFlag){
												//整单满减  促销订单不可以修改配送数量
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了整单满减促销，不能修改数量");
											}*/
										}
										flag = true;
										onlyReachType =false;
										orgEntry.setQty(curEntry.getQty());
									}


									boolean sendRuleFlag = false;
									if (!orgEntry.getRuleType().equals(curEntry.getRuleType())) {//周期变更
										flag = true;
										sendRuleFlag = true;
										onlyReachType =false;
										orgEntry.setRuleType(curEntry.getRuleType());
										orgEntry.setGapDays(curEntry.getGapDays());
										orgEntry.setRuleTxt(curEntry.getRuleTxt());
									} else {
										//相同时判断是周期送还是星期送
										if ("10".equals(orgEntry.getRuleType()) && (!curEntry.getGapDays().equals(orgEntry.getGapDays()))) {
											onlyReachType =false;
											sendRuleFlag = true;
											flag = true;
											orgEntry.setGapDays(curEntry.getGapDays());
											orgEntry.setRuleTxt(curEntry.getRuleTxt());
										} else if ("20".equals(orgEntry.getRuleType()) && !curEntry.getRuleTxt().equals(orgEntry.getRuleTxt())) {
											onlyReachType =false;
											sendRuleFlag = true;
											flag = true;
											orgEntry.setRuleTxt(curEntry.getRuleTxt());
										}
									}
									if (!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())) {//送奶时段变更
										orgEntry.setReachTimeType(curEntry.getReachTimeType());
									}

									//比较配送日期是否修改
									String startstr = format.format(curEntry.getStartDispDate());
									String endstr = format.format(curEntry.getEndDispDate());

									if (!startstr.equals(format.format(orgEntry.getStartDispDate())) || !endstr.equals(format.format(orgEntry.getEndDispDate()))) {
										sendRuleFlag = true;
										orgEntry.setStartDispDate(curEntry.getStartDispDate());
										orgEntry.setEndDispDate(curEntry.getEndDispDate());
										daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(orgEntry.getItemNo()))
												.forEach((e)->{
													if(!e.getDispDate().before(curEntry.getStartDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
										});
										onlyReachType =false;
										flag = true;

									}
										/*
										* 	如果修改了配送规律  修改了日期范围或周期变更
										*    TODO 参加促销的订单可以修改配送规律(目前不能变更)
										* */
									if(sendRuleFlag){
										if(orderPromFlag || planItemPromFlag){
											//如果参加的是整单促销
											/*if(orderPromFlag) {
												//整单满减
												if ("Z016".equals(promModel.getPromSubType())) {
													throw new ServiceException(MessageCode.LOGIC_ERROR, "该订单参加了整单满减促销，不能修改配送日期");
												}
											}*/
											if(itemPromFlag){
												//单品满赠
												if("Z008".equals(promModel.getPromSubType())){
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满增促销，不能修改配送日期");
												}
												//单品满减
												if("Z015".equals(promModel.getPromSubType())){
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满减促销，不能修改配送日期");
												}
											}
											if(planItemPromFlag) {
												throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单有行项目参加了促销，不能修改配送日期");
											}
										}
									}


									if(flag){
										//删除 订单状态不为20（完结）的日订单-赠品不删
										TOrderDaliyPlanItem newplan = new TOrderDaliyPlanItem();
										newplan.setOrderNo(orgOrder.getOrderNo());
										//newplan.setItemNo(orgEntry.getItemNo());
										newplan.setStatus("10");
										newplan.setDispDateStr(startstr);
										tOrderDaliyPlanItemMapper.deleteFromDateByStatus(newplan);
									}
									//订单行做停订 删除不需要的日单
									if(ContentDiffrentUtil.isDiffrent(orgEntry.getIsStop(),curEntry.getIsStop())){
										if("Y".equals(curEntry.getIsStop()) && curEntry.getStopStartDate()!=null){
											//订单行的开始停订日期在 配送日期之前 抛出异常
											if(DateUtil.dateBefore(curEntry.getStopStartDate(),orgEntry.getStartDispDate())|| DateUtil.dateAfter(curEntry.getStopStartDate(),orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"停订的日期不能在配送日期之外!");
											daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(orgEntry.getItemNo()))
													.forEach((e)->{
														if(!e.getDispDate().before(curEntry.getStopStartDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
											});

											if(planItemPromFlag){
												//行项目参加满赠  判断赠品是否已经配送或者 在停订日期之前有赠品日订单
												// 如果没有可以停订，但是该产品行不再参加满赠
												if(itemPromFlag){
													if("Z008".equals(promModel.getPromSubType())){
														if(StringUtils.isNotBlank(orgEntry.getPromotion()) && StringUtils.isNotBlank(orgEntry.getPromItemNo())){
															//赠品是否在停订之前已经配送 或者有赠品日订单
															backOrderOfProm(orgOrder,DateUtil.getYestoday(curEntry.getStopStartDate()));
															//将行项目的促销去除
															orgEntry.setPromotion("");
															orgEntry.setPromItemNo("");
														}
													}else if("Z015".equals(promModel.getPromSubType())){
														//如果参加的是行项目满减，类似订单满减，将该行项目停订  还是按照订单总额顺延
													}
												}else{
														throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单有产品行项目参加了促销，非促销行项目不能停订");
												}


											}


											orgEntry.setStopStartDate(curEntry.getStopStartDate());
											orgEntry.setIsStop(curEntry.getIsStop());
											orgEntry.setEndDispDate(DateUtil.getYestoday(curEntry.getStopStartDate()));
											//删除大于等于停订日期不需要的日单 包括赠品
											TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
											newPlan.setOrderNo(orgOrder.getOrderNo());
											newPlan.setItemNo(orgEntry.getItemNo());
											newPlan.setStatus("10");
											newPlan.setDispDateStr(format.format(curEntry.getStopStartDate()));
											tOrderDaliyPlanItemMapper.deleteFromDateByStatusAndProm(newPlan);
										}
									}
									orgEntry.setNewRowFlag("N");
									onlyReachType =false;
									tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
									curAllEntry.add(orgEntry);
									break;
								}
							}
						}
						//删除了
						if (delFlag) {
							onlyReachType =false;
							throw new ServiceException(MessageCode.LOGIC_ERROR, "已生成路单的订单不能删除行项目");
						}

					}
					//有添加的行项目
					if (cloneCurEntrys != null && cloneCurEntrys.size() > 0) {
						//行项目参加了促销（单品满赠，单品满减，不能添加新产品）
						if(planItemPromFlag){
							if(promModel!=null){
								if("Z008".equals(promModel.getPromSubType())){
									throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满赠促销不能添加新的行项目");
								}
								if("Z015".equals(promModel.getPromSubType())){
									throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满减促销不能添加新的行项目");
								}
							}
						}
						onlyReachType =false;
						int index = tPlanOrderItemMapper.selectEntriesQtyByOrderCode(record.getOrder().getOrderNo());
						for (TPlanOrderItem entry : cloneCurEntrys) {
							confirmAllDispDateAfterDate(entry, null,orderNo);
							modiFlag = true;
							entry.setNewRowFlag("Y");
							entry.setOrderNo(orgOrder.getOrderNo());
							entry.setItemNo(orgOrder.getOrderNo() + String.valueOf(index));//行项目编号
							entry.setRefItemNo(String.valueOf(index));//参考行项目编号
							entry.setOrderDate(orgOrder.getOrderDate());//订单日期
							entry.setCreateAt(new Date());//创建日期
							entry.setCreateBy(user.getLoginName());//创建人
							entry.setCreateByTxt(user.getDisplayName());//创建人姓名
							calculateEntryAmount(entry);
							tPlanOrderItemMapper.insert(entry);
							//删除从这个行项目开始日期后的所有未完结日订单
							TOrderDaliyPlanItem item = new TOrderDaliyPlanItem();
							item.setOrderNo(orderNo);
							item.setStatus("10");
							item.setDispDateStr(format.format(entry.getStartDispDate()));
							tOrderDaliyPlanItemMapper.deleteFromDateByStatus(item);
							curAllEntry.add(entry);
							index++;
						}
					}
					if (modiFlag) {
						//List<TPlanOrderItem> curAllEntry = tPlanOrderItemMapper.selectByOrderCode(orderNo);
						if(onlyReachType && curAllEntry!=null){
							for(TPlanOrderItem entry : curAllEntry){
								TOrderDaliyPlanItem item = new TOrderDaliyPlanItem();
								item.setOrderNo(orderNo);
								item.setReachTimeType(entry.getReachTimeType());
								item.setItemNo(entry.getItemNo());
								item.setDispDate(entry.getStartDispDate());
								tOrderDaliyPlanItemMapper.updateDaliyReachTimeTypeItemNo(item);
							}
						}else{
							//行号唯一，需要判断以前最大的行号 如果没有日订单则返回 0

							List<TOrderDaliyPlanItem> list = this.createDaliyByAmt(orgOrder, curAllEntry,daliyEntryNo,true);
							Map<String, TPlanOrderItem> planMap = new HashMap<String, TPlanOrderItem>();
							Date lastDay = null;
							list.stream().forEach(e -> {
								if (planMap.containsKey(e.getItemNo())) {
									TPlanOrderItem item = planMap.get(e.getItemNo());
									item.setDispTotal(item.getDispTotal() + e.getQty());
									if (item.getEndDispDate().before(e.getDispDate())) {
										item.setEndDispDate(e.getDispDate());
									}
								/*	if (item.getStartDispDate().after(e.getDispDate())) {
										item.setStartDispDate(e.getDispDate());
									}*/
								} else {
									TPlanOrderItem item = new TPlanOrderItem();
									item.setItemNo(e.getItemNo());
									item.setNewRowFlag("N");
									item.setDispTotal(e.getQty());
									item.setEndDispDate(e.getDispDate());
									//item.setStartDispDate(e.getDispDate());
									planMap.put(e.getItemNo(), item);
								}
							});
							curEntrys.stream().forEach(plan -> {
								if(planMap.containsKey(plan.getItemNo())){

									TPlanOrderItem item = planMap.get(plan.getItemNo());
									tPlanOrderItemMapper.updateEntryByItemNo(item);
									plan.setEndDispDate(item.getEndDispDate());
								}else{
									TPlanOrderItem item = new TPlanOrderItem();
									item.setItemNo(plan.getItemNo());
									item.setDispTotal(0);
									tPlanOrderItemMapper.updateEntryByItemNo(item);
								}

							});
							if(planMap!=null){
								for(String key : planMap.keySet()){
									TPlanOrderItem item = planMap.get(key);
									if(lastDay==null){
										lastDay = item.getEndDispDate();
									}else{
										if(lastDay.before(item.getEndDispDate())){
											lastDay =item.getEndDispDate();
										}
									}
								}
								if(ContentDiffrentUtil.isDiffrent(lastDay,orgOrder.getEndDate())){
									orgOrder.setEndDate(lastDay);
									tPreOrderMapper.updateBySelective(orgOrder);
								}
							}

							//判断日期是否超过促销范围
							if((orderPromFlag || planItemPromFlag)&&promModel!=null){
								if(orderPromFlag){
									if("Z015".equals(promModel.getPromSubType())){
										if(promModel.getPlanStopTime()==null) throw new ServiceException(MessageCode.LOGIC_ERROR,"获取整单促销的截止配送日期失败！！");
										if(DateUtil.dateAfter(orgOrder.getEndDate(),promModel.getPlanStopTime())){
											throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了整单促销，修改后订单的截止日期"+format.format(orgOrder.getEndDate())+"超过促销的截止配送日期"+format.format(promModel.getPlanStopTime())+"！！");
										}
									}
								}
								if(planItemPromFlag){
									for(TPlanOrderItem item  : curEntrys){
										if(StringUtils.isNotBlank(item.getPromotion()) && StringUtils.isNotBlank(item.getPromItemNo())){
											if(DateUtil.dateAfter(item.getEndDispDate(),promModel.getPlanStopTime())){
												if("Z008".equals(promModel.getPromSubType())){
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满赠促销，修改后该订单行的截止日期"+format.format(item.getEndDispDate())+"超过促销的截止配送日期"+format.format(promModel.getPlanStopTime())+"！！");
												}
												if("Z015".equals(promModel.getPromSubType())){
													throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满减促销，修改后该订单行的截止日期"+format.format(item.getEndDispDate())+"超过促销的截止配送日期"+format.format(promModel.getPlanStopTime())+"！！");
												}
											}
										}
									}
								}
							}
						}
					} else {
						throw new ServiceException(MessageCode.LOGIC_ERROR, "没做修改");
					}

				} else {
					//后付款的
					boolean modiFlag = false;
					//用来判断新增的行项目
					List<TPlanOrderItem> cloneCurEntrys = new ArrayList<TPlanOrderItem>();
					cloneCurEntrys.addAll(curEntrys);
					//orgEntrys 原来的所有行项目  curEntrys :现在的行项目
					for (TPlanOrderItem orgEntry : orgEntrys) {
						boolean delFlag = true;
						for (TPlanOrderItem curEntry : curEntrys) {
							if (curEntry.getItemNo().equals(orgEntry.getItemNo())) {
								cloneCurEntrys.remove(curEntry);
								delFlag = false;
								if (curEntry.getMatnr().equals(orgEntry.getMatnr()) && curEntry.getQty().equals(orgEntry.getQty()) &&
										format.format(orgEntry.getStartDispDate()).equals(format.format(curEntry.getStartDispDate())) &&
										format.format(orgEntry.getEndDispDate()).equals(format.format(curEntry.getEndDispDate())) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTime(), curEntry.getReachTime()) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTimeType(), curEntry.getReachTimeType()) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getGapDays(), curEntry.getGapDays()) &&
										!ContentDiffrentUtil.isDiffrent(orgEntry.getRuleTxt(), curEntry.getRuleTxt())&&
										curEntry.getStopStartDate()==null) {
									curAllEntry.add(orgEntry);
									continue;
								} else {
									//BigDecimal orgEntryAmt = calculateEntryAmount(orgEntry);
									//产生路单的订单行 如果有变化  验证信息
								/*	if (StringUtils.isNotBlank(orgEntry.getPromotion()))
										throw new ServiceException(MessageCode.LOGIC_ERROR, "促销商品行不能更改!");*/
									List<TDispOrderItem> dispItems = confirmAllDispDateAfterDate(curEntry,orgEntry,orderNo);
									//获取路单中最大日期的配送日期 为了删除这个日期之后的日订单
									Date maxDispDate = orgEntry.getStartDispDate();
									if(dispItems!=null && dispItems.size()>0){
										for(TDispOrderItem item : dispItems){
											if(item.getOrderDate().after(maxDispDate)){
												maxDispDate = item.getOrderDate();
											}
										}
									}
									boolean deleteFlag = false;
									//可以修改行项目
									if (!orgEntry.getMatnr().equals(curEntry.getMatnr())) {//换商品
										deleteFlag = true;
										modiFlag = true;
										orgEntry.setMatnr(curEntry.getMatnr());
										orgEntry.setSalesPrice(curEntry.getSalesPrice());
										orgEntry.setUnit(curEntry.getUnit());
									}
									if (orgEntry.getQty() != curEntry.getQty()) {//改数量
										deleteFlag = true;
										modiFlag = true;
										orgEntry.setQty(curEntry.getQty());
									}
									if (!orgEntry.getRuleType().equals(curEntry.getRuleType())) {//周期变更
										deleteFlag = true;
										modiFlag = true;
										orgEntry.setRuleType(curEntry.getRuleType());
										orgEntry.setGapDays(curEntry.getGapDays());
										orgEntry.setRuleTxt(curEntry.getRuleTxt());
									} else {
										//相同时判断是周期送还是星期送
										if ("10".equals(orgEntry.getRuleType()) && (!curEntry.getGapDays().equals(orgEntry.getGapDays()))) {
											modiFlag = true;
											deleteFlag = true;
											orgEntry.setGapDays(curEntry.getGapDays());
											orgEntry.setRuleTxt(curEntry.getRuleTxt());
										} else if ("20".equals(orgEntry.getRuleType()) && !curEntry.getRuleTxt().equals(orgEntry.getRuleTxt())) {
											modiFlag = true;
											deleteFlag = true;
											orgEntry.setRuleTxt(curEntry.getRuleTxt());
										}
									}
									if (!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())) {//送奶时段变更
										modiFlag = true;

										orgEntry.setReachTimeType(curEntry.getReachTimeType());
									}
									//比较配送日期是否修改
									String startstr = format.format(curEntry.getStartDispDate());
									String endstr = format.format(curEntry.getEndDispDate());
									if (!startstr.equals(format.format(orgEntry.getStartDispDate())) || !endstr.equals(format.format(orgEntry.getEndDispDate()))) {
										modiFlag = true;
										deleteFlag = true;
										orgEntry.setStartDispDate(curEntry.getStartDispDate());
										orgEntry.setEndDispDate(curEntry.getEndDispDate());
										orgEntry.setIsStop("N");

									}
									if(deleteFlag){
										TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
										plan.setOrderNo(orgOrder.getOrderNo());
										plan.setItemNo(orgEntry.getItemNo());
										plan.setStatus("10");
										plan.setDispDateStr(startstr);
										//将 先开始日期之后的日订单删除
										tOrderDaliyPlanItemMapper.deleteFromDateByStatus(plan);
									}



									if("Y".equals(curEntry.getIsStop()) && curEntry.getStopStartDate()!=null){
										if(DateUtil.dateBefore(curEntry.getStopStartDate(),orgEntry.getStartDispDate())|| DateUtil.dateAfter(curEntry.getStopStartDate(),orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"停订的日期不能在配送日期之外!");
											daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(orgEntry.getItemNo()))
											.forEach((e)->{
													if(!e.getDispDate().before(curEntry.getStopStartDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划,不能停订，请修改时间!");
											});
											modiFlag = true;
											orgEntry.setStopStartDate(curEntry.getStopStartDate());
											orgEntry.setIsStop(curEntry.getIsStop());
											orgEntry.setEndDispDate(DateUtil.getYestoday(curEntry.getStopStartDate()));

											TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
											newPlan.setOrderNo(orgOrder.getOrderNo());
											newPlan.setItemNo(orgEntry.getItemNo());
											newPlan.setStatus("10");
											newPlan.setDispDateStr(format.format(curEntry.getStopStartDate()));
											//将 先开始日期之后的日订单删除
											tOrderDaliyPlanItemMapper.deleteFromDateByStatus(newPlan);

										}

										tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
										curAllEntry.add(orgEntry);
								}
							}
						}
						//删除了
						if (delFlag) {
							throw new ServiceException(MessageCode.LOGIC_ERROR, "已生成路单的订单不能删除行项目");
						}
					}

					if (cloneCurEntrys != null && cloneCurEntrys.size() > 0) {
						//有添加的行项目
						int index = tPlanOrderItemMapper.selectEntriesQtyByOrderCode(record.getOrder().getOrderNo());
						for (TPlanOrderItem entry : cloneCurEntrys) {
							confirmAllDispDateAfterDate(entry, null,orderNo);
							modiFlag = true;
							entry.setOrderNo(orgOrder.getOrderNo());
							entry.setItemNo(orgOrder.getOrderNo() + String.valueOf(index));//行项目编号
							entry.setRefItemNo(String.valueOf(index));//参考行项目编号
							entry.setOrderDate(orgOrder.getOrderDate());//订单日期
							entry.setCreateAt(new Date());//创建日期
							entry.setCreateBy(user.getLoginName());//创建人
							entry.setCreateByTxt(user.getDisplayName());//创建人姓名
							promotionService.calculateEntryPromotion(entry);
							tPlanOrderItemMapper.insert(entry);
							curAllEntry.add(entry);
							if(orgOrder.getEndDate().before(entry.getEndDispDate())){
								orgOrder.setEndDate(entry.getEndDispDate());
							}
							index++;
						}
					}

					if (modiFlag) {
						//List<TPlanOrderItem> curAllEntry = tPlanOrderItemMapper.selectByOrderCode(orderNo);
						List<TOrderDaliyPlanItem> list = this.createDaliyPlanAfterPay(orgOrder,curAllEntry,daliyEntryNo);
						//更新订单行总配送数量
						Map<String, TPlanOrderItem> planMap = new HashMap<String, TPlanOrderItem>();
						Date lastDay = null;
						list.stream().forEach(e -> {
							if (planMap.containsKey(e.getItemNo())) {
								TPlanOrderItem item = planMap.get(e.getItemNo());
								item.setDispTotal(item.getDispTotal() + e.getQty());
							} else {
								TPlanOrderItem item = new TPlanOrderItem();
								item.setItemNo(e.getItemNo());
								item.setNewRowFlag("N");
								item.setDispTotal(e.getQty());
								planMap.put(e.getItemNo(), item);
							}
						});
						curEntrys.stream().forEach(plan -> {
							if(planMap.containsKey(plan.getItemNo())){
								TPlanOrderItem item = planMap.get(plan.getItemNo());
								tPlanOrderItemMapper.updateEntryByItemNo(item);
							}else{
								TPlanOrderItem newPlan = new TPlanOrderItem();
								newPlan.setDispTotal(0);
								newPlan.setItemNo(plan.getItemNo());
								tPlanOrderItemMapper.updateEntryByItemNo(newPlan);
							}

						});
						tPreOrderMapper.updateBySelective(orgOrder);
					}else{
						throw  new ServiceException(MessageCode.LOGIC_ERROR,"没做修改");
					}

				}

			}
		}
		return 1;
	}

	public List<TOrderDaliyPlanItem> uptOrderlongForViewPlans(OrderEditModel record){
		try{
			int i = this.uptOrderlong(record);
		}catch(Exception e){
			/*if(((ServiceException) e).getValue().equals("没做修改")){
				OrderSearchModel mo = new OrderSearchModel();
				mo.setOrderNo(record.getOrder().getOrderNo());
				List<TOrderDaliyPlanItem> backData = tOrderDaliyPlanItemMapper.selectDaliyOrdersAll(mo);
				throw new ServiceException(MessageCode.LOGIC_ERROR, backData);
			}else{*/
				throw new ServiceException(MessageCode.SERVER_ERROR,((ServiceException) e).getValue());
			//}
		}
		OrderSearchModel mo = new OrderSearchModel();
		mo.setOrderNo(record.getOrder().getOrderNo());
		List<TOrderDaliyPlanItem> backData = tOrderDaliyPlanItemMapper.selectDaliyOrdersAll(mo);
		if(backData!=null){
			//System.out.println("更新成功，获取日订单,长度为:"+backData.size());
			TPreOrder order= tPreOrderMapper.selectByPrimaryKey(record.getOrder().getOrderNo());
			if("10".equals(record.getOrder().getPaymentmethod()))
			backData.stream().filter(e -> !"30".equals(e.getStatus())).forEach(day -> {
				//System.out.println(day.getRemainAmt()==null?"取不到日订单的剩余金额":day.getRemainAmt());
				//System.out.println(order.getInitAmt()==null?"取不到订单的总金额":order.getInitAmt());
					day.setRemainAmt(day.getRemainAmt().subtract(order.getInitAmt()));
			});
		}
		throw new ServiceException(MessageCode.LOGIC_ERROR, backData);
	}
	private List<TOrderDaliyPlanItem> createDaliyPlanAfterPay(TPreOrder order, List<TPlanOrderItem> entries,int daliyEntryNo) {
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
		BigDecimal initAmt = order.getInitAmt();
		Map<String,BigDecimal> initMap = new HashMap<String,BigDecimal>();
		initMap.put("initAmt",initAmt);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		//Map<String,TPlanOrderItem> entriesMap = new HashMap<String,TPlanOrderItem>();
		//计算每个行项目总共需要循环多少天
		Map<TPlanOrderItem,Integer> entryMap = new HashMap<TPlanOrderItem,Integer>();
		int maxEntryDay = 3650;
		Date firstDeliveryDate = null;
		Date lastDeliveryDate = null;
		for(TPlanOrderItem entry: entries){
			if(firstDeliveryDate==null){
				firstDeliveryDate = entry.getStartDispDate();
			}else{
				firstDeliveryDate = firstDeliveryDate.before(entry.getStartDispDate())?firstDeliveryDate:entry.getStartDispDate();
			}

			if(lastDeliveryDate==null){
				lastDeliveryDate = entry.getEndDispDate();
			}else{
				lastDeliveryDate =entry.getEndDispDate().after(lastDeliveryDate)?entry.getEndDispDate():lastDeliveryDate;
			}
			int entryDays = (dayOfTwoDay(entry.getStartDispDate(),entry.getEndDispDate())) + 1;
			entryMap.put(entry,entryDays);
			//entriesMap.put(entry.getItemNo(),entry);
		}

		int totalDays = (dayOfTwoDay(firstDeliveryDate,lastDeliveryDate)) + 1;

		//根据最大配送天数的行
		int afterDays = 0;//经过的天数
		//行号唯一，需要判断以前最大的行号
		//int daliyEntryNo = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(order.getOrderNo()) + 1;

		List<TOrderDaliyPlanItem> allDay = tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo2(order.getOrderNo());

		Map<String,TOrderDaliyPlanItem> dayMap = new HashMap<String,TOrderDaliyPlanItem>();
		if(allDay!=null && allDay.size()>0){
			for(TOrderDaliyPlanItem day : allDay){

					if(day.getDispDate().before(firstDeliveryDate)){
						continue;
					}else{
						String key = day.getItemNo()+format.format(day.getDispDate());
						if(!dayMap.containsKey(key)){
							dayMap.put(key,day);
						}
					}
			};
		}
		Date lastDay = lastDeliveryDate;

		outer:for(int i = 0; i < totalDays; i++){
			for (TPlanOrderItem entry : entryMap.keySet()) {
				int days = entryMap.get(entry);
				if(days - 1 >= 0){
					//判断是按周期送还是按星期送
					Date today = afterDate(firstDeliveryDate,afterDays);
					if(DateUtil.dateAfter(entry.getStartDispDate(),today))continue;
					entryMap.replace(entry, days-1);//剩余天数减1天
					if("10".equals(entry.getRuleType())){
						int gapDays = entry.getGapDays() + 1;//间隔天数
						if(dayOfTwoDay(entry.getStartDispDate(),today)%gapDays != 0){
								continue;
						}
					}
					else if("20".equals(entry.getRuleType())){
						String weekday = getWeek(today);
						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
						if(!deliverDays.contains(weekday)){
							continue;//如果选择的星期几不送，则跳过今天生成日计划
						}
					}
					TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
					plan.setOrderNo(entry.getOrderNo());//订单编号
					plan.setItemNo(entry.getItemNo());//预订单日计划行
					plan.setDispDate(today);//配送日期
					plan.setDispDateStr(format.format(today));
					String setKey = plan.getItemNo()+plan.getDispDateStr();
					if(dayMap.containsKey(setKey)){
						TOrderDaliyPlanItem item = dayMap.get(setKey);
						plan.setPlanItemNo(String.valueOf(daliyEntryNo));
						if("10".equals(item.getStatus())){
							plan.setQty(entry.getQty());
							plan.setMatnr(entry.getMatnr());
							plan.setUnit(entry.getUnit());//配送单位
							plan.setPrice(entry.getSalesPrice());
							plan.setPlanItemNo(String.valueOf(daliyEntryNo));
							plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
							plan.setAmt(entry.getSalesPrice().multiply(new BigDecimal(entry.getQty())));
							plan.setPromotionFlag(entry.getPromotion());//促销号
							tOrderDaliyPlanItemMapper.updateDaliyPlanItemStatus(plan);
						}else if("20".equals(item.getStatus())){
							plan.setPlanItemNo(String.valueOf(daliyEntryNo));
							tOrderDaliyPlanItemMapper.updateDaliyPlanItemStatus(plan);
						}else{
							plan.setPlanItemNo(String.valueOf(daliyEntryNo));
							tOrderDaliyPlanItemMapper.updateDaliyPlanItemStatus(plan);
						}
						daliyEntryNo++;
						continue ;
					}else{
						//生成该订单行的每日计划
						plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
						plan.setOrderDate(entry.getOrderDate());//订单日期
						plan.setReachTimeType(entry.getReachTimeType());//送达时段类型
						plan.setMatnr(entry.getMatnr());//产品编号
						plan.setUnit(entry.getUnit());//配送单位
						plan.setQty(entry.getQty());//产品数量
						plan.setPrice(entry.getSalesPrice());//产品价格
						plan.setPromotionFlag(entry.getPromotion());//促销号
						//日计划行金额和
						BigDecimal qty = new BigDecimal(entry.getQty().toString());
						plan.setAmt(entry.getSalesPrice().multiply(qty));//金额小计
						plan.setStatus("10");//状态
						plan.setCreateAt(new Date());//创建时间
						plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
						plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
						tOrderDaliyPlanItemMapper.insert(plan);
						daliyEntryNo++;
						//daliyPlans.add(plan);
					}
				}else{
					continue;
				}
			}
			afterDays++;
		}
		//重新更新所有日订单的RemainAmt字段
		List<TOrderDaliyPlanItem> allDayItems = tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo2(order.getOrderNo());
		BigDecimal  newInitAmt = tOrderDaliyPlanItemMapper.selectInitAmtDaysByNo(order.getOrderNo());
		BigDecimal  newCurAmt = tOrderDaliyPlanItemMapper.selectCurAmtDaysByNo(order.getOrderNo());
		order.setInitAmt(newInitAmt);
		order.setCurAmt(newCurAmt);
		initMap.replace("initAmt",newInitAmt);
		//System.out.println("开始执行 更新日订单剩余金额，取出的日订单数量为");
		/*	Map<String,Integer> dayEntrMap = new HashMap<String,Integer>();
		//dayEntrMap.put("planItemNo",0);*/
		//allDayItems.stream().filter(e->!"30".equals(e.getStatus())).count();
		//System.out.println(order.getOrderNo()+"开始执行更新日订单剩余金额，取出所有的日订单数量为"+allDayItems.size());
		if(allDayItems!=null && allDayItems.size()>0){
			allDayItems.stream().filter(e->!"30".equals(e.getStatus())).forEach(item->{
				initMap.replace("initAmt",initMap.get("initAmt").subtract(item.getAmt()));
					TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
					plan.setDispDate(item.getDispDate());//配送日期
					plan.setOrderNo(item.getOrderNo());//订单编号
					plan.setItemNo(item.getItemNo());//预订单日计划行
					plan.setDispDateStr(format.format(item.getDispDate()));
					//plan.setPlanItemNo(String.valueOf(dayEntrMap.get("planItemNo")));
					plan.setRemainAmt(initMap.get("initAmt"));

					tOrderDaliyPlanItemMapper.updateDaliyPlanItemByItemNo(plan);
					//System.out.println("更新金额"+plan.getRemainAmt());
					//dayEntrMap.replace("planItemNo",dayEntrMap.get("planItemNo").intValue()+1);
			});
		}
		//System.out.println("更新日订单剩余金额执行完毕");
		if(lastDay!=null && ContentDiffrentUtil.isDiffrent(lastDay,order.getEndDate())){
			order.setEndDate(lastDay);
		}

		return tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());
	}

	public List<TDispOrderItem> confirmAllDispDateAfterDate(TPlanOrderItem curEntry,TPlanOrderItem orgEntry,String orderNo){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date orderDate = curEntry.getStartDispDate();
		if(orgEntry!=null){
			if( "Y".equals(curEntry.getIsStop()) ){
				if(curEntry.getStopStartDate()!=null){
					//如果订单行被停订，判断路单日期会取 订单的开始停订日期
					if(curEntry.getStopStartDate().after(curEntry.getStartDispDate())){
						orderDate =curEntry.getStopStartDate();
					}
				}

			}
		}

		TDispOrderItem dispItem = new TDispOrderItem();
		dispItem.setOrgOrderNo(orderNo);
		dispItem.setOrderDate(orderDate);
		//dispItem.setOrgItemNo(curEntry.getItemNo());

		List<TDispOrderItem> dispItems = tDispOrderItemMapper.selectItemsByOrgOrderAndItemNoAndBeforeDate(dispItem);
		if(dispItems != null && dispItems.size() > 0) {
			//confirmlist 过滤只保留已确认的路单
			List<TDispOrderItem> confirmlist = dispItems.stream().filter(e -> ("20".equals(e.getStatus()))).collect(Collectors.toList());
			//unConist 过滤只保留还未确认的路单
			List<TDispOrderItem> unConist = dispItems.stream().filter(e -> ("10".equals(e.getStatus()))).collect(Collectors.toList());
			//在开始日期之前产生了路单
			if (confirmlist != null && confirmlist.size() > 0) {
				//confirmlist 不为空说明有已确认的路单
				StringBuffer mess = new StringBuffer("");
				//将所有确认的路单日期取出
				confirmlist.stream().forEach(e -> {
					mess.append(e.getMatnr()+"产品"+format.format(e.getOrderDate()) + "\n");
				});
				throw new ServiceException(MessageCode.LOGIC_ERROR, mess + "的路单已经确认请重新选择开始日期");
			}else{
				if (unConist != null && unConist.size() > 0) {
					StringBuffer mess = new StringBuffer("");
					unConist.stream().forEach(e -> {
						mess.append(e.getMatnr()+"产品"+format.format(e.getOrderDate()) + "\n");
					});
					throw new ServiceException(MessageCode.LOGIC_ERROR, mess + "的路单已经生成，请先删除路单，再修改日期");
				}
			}
		}
		return dispItems;
	}
	//预付款没产生路单  以订单金额为基础修改 如果有行项目参加促销：不能添加产品，不能修改非
	public void uptBeforeOrderByAmt(List<TPlanOrderItem> orgEntrys,OrderEditModel record,TPreOrder orgOrder){
		String orderNo = record.getOrder().getOrderNo();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<TPlanOrderItem>  curEntrys = record.getEntries();
		//判断是否有行项目参加促销
		boolean PlanitemPromFlag = curEntrys.stream().anyMatch(item->(StringUtils.isNotBlank(item.getPromotion())&&StringUtils.isNotBlank(item.getPromItemNo())));
		boolean orderPromFlag =(StringUtils.isNotBlank(orgOrder.getPromotion())&&StringUtils.isNotBlank(orgOrder.getPromItemNo()))?true:false;
		TPromotion promModel = null;
		if(orderPromFlag){
			promModel = promotionService.selectPromotionByPromNoAndItemNo(orgOrder.getPromotion(),orgOrder.getPromItemNo());
		}
		if(PlanitemPromFlag){
			List<TPlanOrderItem> promItem =  curEntrys.stream().filter(item->StringUtils.isNotBlank(item.getPromotion())&&StringUtils.isNotBlank(item.getPromItemNo())).collect(Collectors.toList());
			if(promItem!=null && promItem.size()>0){
				promModel = promotionService.selectPromotionByPromNoAndItemNo(promItem.get(0).getPromotion(),promItem.get(0).getPromItemNo());
			}
		}

		//如果有行项目参加促销，修改配送日期
		if(StringUtils.isNotBlank(record.getEditDate())){
			// 行项目 促销
			if(PlanitemPromFlag && promModel!=null){
				curEntrys.stream().filter(item->StringUtils.isNotBlank(item.getPromotion())&&StringUtils.isNotBlank(item.getPromItemNo())).forEach(item->{
					TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(item.getPromotion(),item.getPromItemNo());
					if(prom!=null && "Z008".equals(prom.getPromSubType())){
						if(DateUtil.dateAfter(prom.getPlanStartTime(),item.getStartDispDate())){
							throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品行参加了单品满赠促销，开始日期"+format.format(item.getStartDispDate())+"不能在促销的开始配送日期"+format.format(prom.getPlanStartTime())+"之前");
						}
						if(DateUtil.dateAfter(item.getEndDispDate(),prom.getPlanStopTime())){
							throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品行参加了单品满赠促销，截止日期"+format.format(item.getEndDispDate())+"不能在促销的截止配送日期"+format.format(prom.getPlanStopTime())+"之后");
						}
					}

					if(prom!=null && "Z015".equals(prom.getPromSubType())){
						if(DateUtil.dateAfter(prom.getPlanStartTime(),item.getStartDispDate())){
							throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品行参加了单品满减促销，开始日期"+format.format(item.getStartDispDate())+"不能在促销的开始配送日期"+format.format(prom.getPlanStartTime())+"之前");
						}
						if(DateUtil.dateAfter(item.getEndDispDate(),prom.getPlanStopTime())){
							throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品行参加了单品满减促销，截止日期"+format.format(item.getEndDispDate())+"不能在促销的截止配送日期"+format.format(prom.getPlanStopTime())+"之后");
						}
					}
				});
			}
			if(orderPromFlag){
				if(orderPromFlag && promModel!=null){
					//整单满减
					if("Z016".equals(promModel.getPromSubType())){
						curEntrys.stream().forEach(item->{
							TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(item.getPromotion(),item.getPromItemNo());
							if(prom.getPlanStartTime()==null || prom.getPlanStopTime()==null){
								throw new ServiceException(MessageCode.LOGIC_ERROR,"促销信息没有获取到该整单满减的配送日期范围,请查看维护！");
							}
							if(DateUtil.dateAfter(prom.getPlanStartTime(),item.getStartDispDate())){
								throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品行参加了整单满减促销，开始日期"+format.format(item.getStartDispDate())+"不能在促销的开始配送日期"+format.format(prom.getPlanStartTime())+"之前");
							}
							if(DateUtil.dateAfter(item.getEndDispDate(),prom.getPlanStopTime())){
								throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品行参加了整单满减促销，截止日期"+format.format(item.getEndDispDate())+"不能在促销的截止配送日期"+format.format(prom.getPlanStopTime())+"之后");
							}
						});
					}
				}
			}
		}else{
			if(PlanitemPromFlag || orderPromFlag) {
				curEntrys.stream().forEach(e -> {
					if ("Y".equals(e.getNewFlag())) {
						throw new ServiceException(MessageCode.LOGIC_ERROR, "参加促销的订单不能添加产品");
					}
				});

				for (TPlanOrderItem orgEntry : orgEntrys) {
					boolean delFlag = false;
					for (TPlanOrderItem curEntry : curEntrys) {
						if (curEntry.getItemNo().equals(orgEntry.getItemNo())) {
							delFlag = false;
							if (curEntry.getMatnr().equals(orgEntry.getMatnr()) && curEntry.getQty().equals(orgEntry.getQty()) &&
									format.format(orgEntry.getStartDispDate()).equals(format.format(curEntry.getStartDispDate())) &&
									format.format(orgEntry.getEndDispDate()).equals(format.format(curEntry.getEndDispDate())) &&
									!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTime(), curEntry.getReachTime()) &&
									!ContentDiffrentUtil.isDiffrent(orgEntry.getReachTimeType(), curEntry.getReachTimeType()) &&
									!ContentDiffrentUtil.isDiffrent(orgEntry.getGapDays(), curEntry.getGapDays()) &&
									!ContentDiffrentUtil.isDiffrent(orgEntry.getRuleTxt(), curEntry.getRuleTxt()) &&
									curEntry.getStopStartDate() == null
									) {
								continue;
							} else {

								boolean itemFlag = (StringUtils.isNotBlank(orgEntry.getPromotion()) && StringUtils.isNotBlank(orgEntry.getPromItemNo()))?true:false;
								//不能修改行项目
								if (!orgEntry.getMatnr().equals(curEntry.getMatnr())) {//换商品
									if(PlanitemPromFlag) throw new ServiceException(MessageCode.LOGIC_ERROR, "参加促销的订单，不能修改产品");
								}
								if (orgEntry.getQty() != curEntry.getQty()) {//改数量
									if(PlanitemPromFlag) throw new ServiceException(MessageCode.LOGIC_ERROR, "参加促销的订单，不能修改数量");
								}
								//修改配送日期
								boolean sendRuleFlag = false;
								if (!orgEntry.getRuleType().equals(curEntry.getRuleType())) {//周期变更
									sendRuleFlag = true;
								} else {
									//相同时判断是周期送还是星期送
									if ("10".equals(orgEntry.getRuleType()) && (!curEntry.getGapDays().equals(orgEntry.getGapDays()))) {
										sendRuleFlag = true;
									} else if ("20".equals(orgEntry.getRuleType()) && !curEntry.getRuleTxt().equals(orgEntry.getRuleTxt())) {
										sendRuleFlag = true;
									}
								}
								if (!orgEntry.getReachTimeType().equals(curEntry.getReachTimeType())) {//送奶时段变更
									sendRuleFlag = true;
								}

								//比较配送日期是否修改
								String startstr = format.format(curEntry.getStartDispDate());
								String endstr = format.format(curEntry.getEndDispDate());

								if (!startstr.equals(format.format(orgEntry.getStartDispDate())) || !endstr.equals(format.format(orgEntry.getEndDispDate()))) {
									sendRuleFlag = true;
								}
							/*
							* 	如果修改了配送规律  修改了日期范围或周期变更
							*    TODO 参加促销的订单可以修改配送规律(目前不能变更)
							* */
								if(sendRuleFlag){
									//如果参加的是整单促销
									if(orderPromFlag){
										//整单满减
										if("Z016".equals(promModel.getPromSubType())){
											throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了整单满减促销，不能修改配送日期");
										}
									}
									if(PlanitemPromFlag){
										//整单满减
										if("Z008".equals(promModel.getPromSubType())){
											throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满增促销，不能修改配送日期");
										}
										//整单满减
										if("Z015".equals(promModel.getPromSubType())){
											throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满减促销，不能修改配送日期");
										}
									}
								}
							}

						}
					}
					if(delFlag){
						throw new ServiceException(MessageCode.LOGIC_ERROR, "参加促销的订单不能删除产品");
					}
				}
			}

		}
		//首先删除原本日计划
		int index = 0;
		tOrderDaliyPlanItemMapper.deletePlansByOrder(orderNo);
		tPlanOrderItemMapper.deleteByOrderNo(orderNo);
		for(TPlanOrderItem entry: curEntrys){
			if("Y".equals(entry.getIsStop()) && entry.getStopStartDate()!=null){
				//判断是否是满赠，如果是满赠 将赠品信息删除
				if(StringUtils.isNotBlank(entry.getPromItemNo()) && StringUtils.isNotBlank(entry.getPromotion())){
					entry.setPromotion("");
					entry.setPromItemNo("");
				}
				entry.setEndDispDate(DateUtil.getYestoday(entry.getStopStartDate()));
			}
			entry.setItemNo(orderNo + String.valueOf(index));//行项目编号
			entry.setRefItemNo(String.valueOf(index));//参考行项目编号
			entry.setRefItemNo(String.valueOf(index));//参考行项目编号
			entry.setOrderNo(orderNo);
			entry.setOrderDate(new Date());//订单日期
			entry.setCreateAt(new Date());//创建日期
			entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
			entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
			//促销判断
			promotionService.calculateEntryPromotion(entry);
			index++;
		}
		curEntrys.forEach(entry->{
			tPlanOrderItemMapper.insert(entry);
		});

		//行号唯一，需要判断以前最大的行号
		int daliyEntryNo = 0;//日计划行号

		//生成每日计划
		List<TOrderDaliyPlanItem> list = createDaliyByAmt(orgOrder,record.getEntries(),daliyEntryNo,false);
		//planMap存放 订单行的 总配送量，最后一天配送日期
		Map<String,TPlanOrderItem> planMap = new HashMap<String,TPlanOrderItem>();
		 Date lastDay = null;
		list.stream().forEach(e->{
			if(planMap.containsKey(e.getItemNo())){
				TPlanOrderItem item = planMap.get(e.getItemNo());
				item.setDispTotal(item.getDispTotal()+e.getQty());
				if(item.getEndDispDate().before(e.getDispDate())){
					item.setEndDispDate(e.getDispDate());
				}
			}else{
				TPlanOrderItem item = new TPlanOrderItem();
				item.setItemNo(e.getItemNo());
				item.setDispTotal(e.getQty());
				item.setEndDispDate(e.getDispDate());
				planMap.put(e.getItemNo(),item);
			}
		});
	/*	long result = list.stream().mapToLong(e->e.getLongAmt()).sum();
		orgOrder.setCurAmt(new BigDecimal(result));
		tPreOrderMapper.updateBySelective(orgOrder);*/
		curEntrys.stream().forEach(plan->{
			TPlanOrderItem item = planMap.get(plan.getItemNo());
			tPlanOrderItemMapper.updateEntryByItemNo(item);
		});
		if(planMap!=null){
			for(String key : planMap.keySet()){
				TPlanOrderItem item = planMap.get(key);
				if(lastDay==null){
					lastDay = item.getEndDispDate();
				}else{
					if(lastDay.before(item.getEndDispDate())){
						lastDay =item.getEndDispDate();
					}
				}

			}
 			if(ContentDiffrentUtil.isDiffrent(lastDay,orgOrder.getEndDate())){
				orgOrder.setEndDate(lastDay);
				tPreOrderMapper.updateBySelective(orgOrder);
			}
		}
	}
	/**
	 *  预付款已收款订单编辑使用
	 *  orgOrder  订单详情
	 *  entries  新的订单行项目
	 *  daliyEntryNo  目前日订单中最大的行号加1  如果所有的日订单都被删除，则为0
	 */

	private List<TOrderDaliyPlanItem> createDaliyByAmt(TPreOrder orgOrder, List<TPlanOrderItem> entries,int daliyEntryNo,boolean haveRoute) {
		//预付款的要付款+装箱才生成日计划
		if("20".equals(orgOrder.getPaymentmethod()) && !"20".equals(orgOrder.getPaymentStat())){
			return null;
		}
		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
		if("20".equals(orgOrder.getMilkboxStat())){
			return null;
		}

		//List<TOrderDaliyPlanItem> daliyPlans = new ArrayList<TOrderDaliyPlanItem>();
		 Date firstDeliveryDate = null;
		Map<String,TPlanOrderItem> entriesMap = new HashMap<String,TPlanOrderItem>();
		for(TPlanOrderItem entry: entries){
			if(!entriesMap.containsKey(entry.getItemNo())){
				entriesMap.put(entry.getItemNo(),entry);
			}
			if(firstDeliveryDate==null){
				firstDeliveryDate = entry.getStartDispDate();
			}else{
				if(entry.getStartDispDate().before(firstDeliveryDate)){
					firstDeliveryDate = entry.getStartDispDate();
				}
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//按一次 订单行项目每天的钱数从小到大排序
		entries = entries.stream().sorted((p1, p2) ->
				(p1.getSalesPrice().multiply(new BigDecimal(p1.getQty())).compareTo(p2.getSalesPrice().multiply(new BigDecimal(p2.getQty())))))
				.collect(Collectors.toList());
		BigDecimal curAmt = orgOrder.getCurAmt();
		BigDecimal initAmt = orgOrder.getInitAmt();
		//final BigDecimal cloneInitAmt = orgOrder.getInitAmt();
		Map<String,BigDecimal> initMap = new HashMap<String,BigDecimal>();
		initMap.put("initAmt",initAmt);

		Map<String,TOrderDaliyPlanItem> dayMap = new HashMap<String,TOrderDaliyPlanItem>();
		if(haveRoute){
			//按 配送日期从小到大，剩余金额从大到小（未删除的部分日订单）
			List<TOrderDaliyPlanItem> oldPlans = tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo2(orgOrder.getOrderNo());
			if(oldPlans!=null && oldPlans.size()>0){
				for(TOrderDaliyPlanItem item : oldPlans){
					String key = item.getItemNo()+format.format(item.getDispDate());
					//先将 更新后的行项目最开始的配送日期（firstDeliveryDate） 之前的 未删除的日订单(oldPlans)的钱减掉
					if(item.getDispDate().before(firstDeliveryDate)){
						if(!"30".equals(item.getStatus())){
							initAmt = initAmt.subtract(item.getAmt());
						}
						//daliyEntryNo++;
					}else{
						TPlanOrderItem planItem = entriesMap.get(item.getItemNo());
						if(planItem.getStartDispDate().after(item.getDispDate())){
							if(!"30".equals(item.getStatus())){
								initAmt = initAmt.subtract(item.getAmt());
							}
							//daliyEntryNo++;
							if(!dayMap.containsKey(key)){
								dayMap.put(key,item);
							}
						}else{
							if(!"30".equals(item.getStatus())){
								initAmt = initAmt.subtract(item.getAmt());
							}
							if(!dayMap.containsKey(key)){
								dayMap.put(key,item);
							}
						}
					}

				}
			}
		}
		int maxEntryDay = 3650;
		Date lastDay = firstDeliveryDate;
		//outer:for(int i = 0; i < maxEntryDay; i++) {

		int afterdays = 0;
		//如果只有一个行项目并且参与了停订
		if(entries.size()==1 && "Y".equals(entries.get(0).getIsStop())){
		for(int i = 0; i < maxEntryDay; i++){
			for(TPlanOrderItem  entry :entries){
				Date today = afterDate(firstDeliveryDate,afterdays);
				if(initAmt.floatValue() < 0)break;
				if(DateUtil.dateAfter(entry.getStartDispDate(),today))continue;
				if("Y".equals(entry.getIsStop()) && entry.getEndDispDate().before(today)){
					break;
				}
				if("10".equals(entry.getRuleType())){
					int gapDays = entry.getGapDays() + 1;//间隔天数
					if(dayOfTwoDay(entry.getStartDispDate(),today)%gapDays != 0){
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
						continue;//如果选择的星期几不送，则跳过今天生成日计划
					}
				}

				TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
				plan.setDispDate(today);//配送日期
				plan.setOrderNo(entry.getOrderNo());//订单编号
				plan.setItemNo(entry.getItemNo());//预订单日计划行
				plan.setDispDateStr(format.format(today));
				String mapKey = plan.getItemNo()+plan.getDispDateStr();
				//TOrderDaliyPlanItem oldPlan = tOrderDaliyPlanItemMapper.selectByDateAndItemNoAndNo(plan);
				if(dayMap.containsKey(mapKey)){
					TOrderDaliyPlanItem item  = dayMap.get(mapKey);
					if("10".equals(item.getStatus())){
						//curAmt = curAmt.subtract(item.getAmt());
						//initAmt = initAmt.subtract(item.getAmt());
						//plan.setRemainAmt(initAmt);
						plan.setStatus("10");
					}else if("20".equals(item.getStatus())){
						plan.setStatus("20");
						//initAmt = initAmt.subtract(item.getAmt());
						//plan.setRemainAmt(initAmt);
					}else{
						plan.setPlanItemNo(String.valueOf(daliyEntryNo));
						plan.setStatus("30");
						tOrderDaliyPlanItemMapper.updateDaliyPlanItemByItemNo(plan);
						daliyEntryNo++;
						continue;
					}
					if(initAmt.floatValue() < 0)break;
					plan.setPlanItemNo(String.valueOf(daliyEntryNo));
					plan.setReachTimeType(entry.getReachTimeType());
					tOrderDaliyPlanItemMapper.updateDaliyPlanItemByItemNo(plan);
					daliyEntryNo++;
					continue ;
				}
				//生成该订单行的每日计划
				plan.setOrderDate(entry.getOrderDate());//订单日期
				plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
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
				//curAmt = curAmt.subtract(plan.getAmt());
				initAmt = initAmt.subtract(plan.getAmt());
				//当订单余额小于0时停止
				if(initAmt.floatValue() < 0)break ;
				plan.setRemainAmt(initAmt);//订单余额
				plan.setStatus("10");//状态
				plan.setCreateAt(new Date());//创建时间
				plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
				plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
				tOrderDaliyPlanItemMapper.insert(plan);
				//daliyPlans.add(plan);
				daliyEntryNo++;
				}
				afterdays++;
			}

		}else{
			do {
				Date today = afterDate(firstDeliveryDate,afterdays);
				lastDay = today;
				for(TPlanOrderItem entry : entries){
					if(initAmt.floatValue() < 0)break;
					if(DateUtil.dateAfter(entry.getStartDispDate(),today))continue;
					//新增的行项目 最多生到截止日期
					/*if("Y".equals(entry.getNewRowFlag())){
						if(entry.getEndDispDate().before(today)) continue;
					}*/
					//停订的行项目 最多生到停订开始日期 不参与顺延
					if("Y".equals(entry.getIsStop()) && DateUtil.dateBefore(entry.getEndDispDate(),today)){
						continue;
					}
					//只有一个行项目，并且被停订 生到截止日期就停止
					if("Y".equals(entry.getIsStop())&&entries.size()==1){
						if(entry.getEndDispDate().before(today)){
							break;
						}
					}
					//判断today是否符合配送规律  如果不符合continue
					if("10".equals(entry.getRuleType())){
						int gapDays = entry.getGapDays() + 1;//间隔天数
						if(dayOfTwoDay(entry.getStartDispDate(),today)%gapDays != 0){
							/*if(entry.getRuleTxt()!=null){
								List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
								if(deliverDays.size() > 0){//判断周6，7是否配送
									String weekday = getWeek(today);
									if(!deliverDays.contains(weekday)){
										continue;
									}
								}
							}else{*/
								continue;
							//}
						}
					}else if("20".equals(entry.getRuleType())){
						String weekday = getWeek(today);
						List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
						if(!deliverDays.contains(weekday)){
							continue;//如果选择的星期几不送，则跳过今天生成日计划
						}
					}
					TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
					plan.setDispDate(today);//配送日期
					plan.setOrderNo(entry.getOrderNo());//订单编号
					plan.setItemNo(entry.getItemNo());//预订单日计划行
					plan.setDispDateStr(format.format(today));
					String mapKey = plan.getItemNo()+plan.getDispDateStr();
					//TOrderDaliyPlanItem oldPlan = tOrderDaliyPlanItemMapper.selectByDateAndItemNoAndNo(plan);
					if(dayMap.containsKey(mapKey)){
						TOrderDaliyPlanItem item  = dayMap.get(mapKey);
						if("10".equals(item.getStatus())){
							//curAmt = curAmt.subtract(item.getAmt());
							//initAmt = initAmt.subtract(item.getAmt());
							plan.setReachTimeType(entry.getReachTimeType());
							//plan.setRemainAmt(initAmt);
						}else if("20".equals(item.getStatus())){
							//initAmt = initAmt.subtract(item.getAmt());
							plan.setRemainAmt(initAmt);
						}else{
							plan.setPlanItemNo(String.valueOf(daliyEntryNo));
							tOrderDaliyPlanItemMapper.updateDaliyPlanItemByItemNo(plan);
							daliyEntryNo++;
							continue;
						}
						if(initAmt.floatValue() < 0)break;
						plan.setPlanItemNo(String.valueOf(daliyEntryNo));
						tOrderDaliyPlanItemMapper.updateDaliyPlanItemByItemNo(plan);
						//daliyPlans.add(plan);

						daliyEntryNo++;
						continue ;
					}
					//生成该订单行的每日计划
					plan.setOrderDate(entry.getOrderDate());//订单日期
					plan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
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
					//curAmt = curAmt.subtract(plan.getAmt());
					initAmt = initAmt.subtract(plan.getAmt());
					//当订单余额小于0时停止
					if(initAmt.floatValue() < 0)break ;
					plan.setRemainAmt(initAmt);//订单余额
					plan.setStatus("10");//状态
					plan.setCreateAt(new Date());//创建时间
					plan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
					plan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
					tOrderDaliyPlanItemMapper.insert(plan);
					//daliyPlans.add(plan);
					daliyEntryNo++;

				}
				afterdays++;
			} while (initAmt.floatValue()>0);
		}

	if(haveRoute){
		List<TOrderDaliyPlanItem> allDayItems = tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo2(orgOrder.getOrderNo());
		//完结总金额
		BigDecimal cloneUseAmt = BigDecimal.ZERO;
		if(allDayItems!=null && allDayItems.size()>0){
			for(TOrderDaliyPlanItem item : allDayItems){
				if("30".equals(item.getStatus()))continue;
				if("20".equals(item.getStatus())){
					cloneUseAmt = cloneUseAmt.add(item.getAmt());
				}
				initMap.replace("initAmt",initMap.get("initAmt").subtract(item.getAmt()));
				if(item.getRemainAmt().compareTo(initMap.get("initAmt"))!=0){
					TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
					plan.setDispDate(item.getDispDate());//配送日期
					plan.setOrderNo(item.getOrderNo());//订单编号
					plan.setItemNo(item.getItemNo());//预订单日计划行
					plan.setDispDateStr(format.format(item.getDispDate()));
					plan.setRemainAmt(initMap.get("initAmt"));
					tOrderDaliyPlanItemMapper.updateDaliyPlanItemByItemNo(plan);
				}
			}
		}
		orgOrder.setCurAmt(orgOrder.getInitAmt().subtract(cloneUseAmt));
	}
		//}


		//判断新生成的日订单中 最后一天是否所有满足的行项目全部都有
		/*final Date finalDay = lastDay;
		boolean allHave = true;
		List<TPlanOrderItem> finEntry = entries.stream().filter(e->!finalDay.before(e.getEndDispDate())).collect(Collectors.toList());
		for(TPlanOrderItem entry : finEntry){
			if("10".equals(entry.getRuleType())){
				int gapDays = entry.getGapDays() + 1;//间隔天数
				if(daysOfTwo(entry.getStartDispDate(),lastDay)%gapDays != 0){
						continue;
				}
			}else if("20".equals(entry.getRuleType())){
				String weekday = getWeek(lastDay);
				List<String> deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
				if(!deliverDays.contains(weekday)){
					continue;//如果选择的星期几不送，则跳过今天生成日计划
				}
			}

			//否则满足条件  这个行项目在这天是要生成的
			TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
			plan.setItemNo(entry.getItemNo());
			plan.setOrderNo(entry.getOrderNo());
			plan.setDispDate(lastDay);
			TOrderDaliyPlanItem dayItem = tOrderDaliyPlanItemMapper.selectByDateAndItemNoAndNo(plan);
			if(dayItem==null){
				allHave = false;
				break;
			}
		}*/

		/*if(!allHave){
			//删除这天所有的日订单
			TOrderDaliyPlanItem plan = new TOrderDaliyPlanItem();
			plan.setOrderNo(orgOrder.getOrderNo());
			plan.setDispDate(lastDay);
			plan.setStatus("10");
			List<TOrderDaliyPlanItem> delItems = tOrderDaliyPlanItemMapper.selectByDayAndNo(plan);
			if(delItems!=null && delItems.size()>0){
				BigDecimal delTotal = BigDecimal.ZERO;
				for(TOrderDaliyPlanItem item : delItems){
					delTotal = delTotal.add(item.getAmt());
				}
				tOrderDaliyPlanItemMapper.deleteOneDayItem(plan);
				if("10".equals(orgOrder.getPaymentStat())){
					orgOrder.setInitAmt(orgOrder.getInitAmt().subtract(delTotal));
					orgOrder.setCurAmt(orgOrder.getCurAmt().subtract(delTotal));
					tPreOrderMapper.updateBySelective(orgOrder);
				}
			}

		}*/
		List<TOrderDaliyPlanItem> curAllDayItems = tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNoAsc(orgOrder.getOrderNo());
		return curAllDayItems;

	}


	public void uptOrderNoDailyOrder(List<TPlanOrderItem> orgEntrys,OrderEditModel record,TPreOrder orgOrder){
		String orderNo = record.getOrder().getOrderNo();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<TPlanOrderItem>  curEntrys = record.getEntries();
		if("20".equals(orgOrder.getPaymentmethod())){
			if(StringUtils.isNotBlank(orgOrder.getPromotion()) && StringUtils.isNotBlank(orgOrder.getPromItemNo())){
				TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(orgOrder.getPromotion(),orgOrder.getPromItemNo());
				if(prom!=null){
					if("Z016".equals(prom.getPromSubType())){
						//整单满减，判断
						curEntrys.stream().forEach(item->{
							if(DateUtil.dateAfter(prom.getPlanStartTime(),item.getStartDispDate())){
								throw new ServiceException(MessageCode.LOGIC_ERROR,"行项目的开始日期"+format.format(item.getStartDispDate())+"不能比整单满减促销的开始配送日期"+format.format(prom.getPlanStartTime())+"之前");
							}
							if(DateUtil.dateAfter(item.getEndDispDate(),prom.getPlanStopTime())){
								throw new ServiceException(MessageCode.LOGIC_ERROR,"行项目的截止日期"+format.format(item.getEndDispDate())+"不能比整单满减促销的截止配送日期"+format.format(prom.getPlanStopTime())+"之后");
							}
						});
					}
				}
			}
			boolean itemPromFlag = 	orgEntrys.stream().anyMatch(entry->StringUtils.isNotBlank(entry.getPromotion()) && StringUtils.isNotBlank(entry.getPromItemNo()));
			if(itemPromFlag){
				List<TPlanOrderItem> entry = orgEntrys.stream().filter(e->(StringUtils.isNotBlank(e.getPromotion()) && StringUtils.isNotBlank(e.getPromItemNo()))).collect(Collectors.toList());
				if(entry!=null && entry.size()>0){
					//因为促销最多有一个行项目
					TPlanOrderItem promEntry = entry.get(0);
					TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(promEntry.getPromotion(),promEntry.getPromItemNo());
					if(prom!=null){
						curEntrys.stream().filter(e->e.getItemNo().equals(promEntry.getItemNo())).forEach(item->{
							if(item.getItemNo().equals(promEntry.getItemNo())){
								if(DateUtil.dateAfter(prom.getPlanStartTime(),item.getStartDispDate())){
									if("Z008".equals(prom.getPromSubType())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品有促销活动,开始日期"+format.format(item.getStartDispDate())+"不能比单品满赠促销的开始配送日期"+format.format(prom.getPlanStartTime())+"之前");
									}
									if("Z015".equals(prom.getPromSubType())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品有促销活动,开始日期"+format.format(item.getStartDispDate())+"不能比单品满减促销的开始配送日期"+format.format(prom.getPlanStartTime())+"之前");
									}
								}
								if(DateUtil.dateAfter(item.getEndDispDate(),prom.getPlanStopTime())){
									if("Z008".equals(prom.getPromSubType())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品有促销活动,截止日期"+format.format(item.getEndDispDate())+"不能比单品满赠促销的截止配送日期"+format.format(prom.getPlanStopTime())+"之后");
									}
									if("Z015".equals(prom.getPromSubType())){
										throw new ServiceException(MessageCode.LOGIC_ERROR,item.getMatnr()+"产品有促销活动,截止日期"+format.format(item.getEndDispDate())+"不能比单品满减促销的截止配送日期"+format.format(prom.getPlanStopTime())+"之后");
									}
								}
							}
						});
					}
				}
			}
		}

		//删除原来的行项目
		tPlanOrderItemMapper.deleteByOrderNo(orderNo);
		//生成每个订单行
		int index = 0;
		BigDecimal orderAmt = new BigDecimal("0.00");//订单总价
		for(TPlanOrderItem entry: curEntrys){
			if("Y".equals(entry.getIsStop()) && entry.getStopStartDate()!=null){
				entry.setEndDispDate(DateUtil.getYestoday(entry.getStopStartDate()));
				if (StringUtils.isNotBlank(entry.getPromotion())&&StringUtils.isNotBlank(entry.getPromItemNo())){
					TPromotion prom = promotionService.selectPromotionByPromNoAndItemNo(entry.getPromotion(),entry.getPromItemNo());
						if("Z008".equals(prom.getPromSubType())){
							entry.setPromItemNo("");
							entry.setPromotion("");
						}
					}
			}

			entry.setItemNo(orderNo + String.valueOf(index));//行项目编号
			entry.setRefItemNo(String.valueOf(index));//参考行项目编号
			orderAmt = orderAmt.add(calculateEntryAmount(entry));
			entry.setRefItemNo(String.valueOf(index));//参考行项目编号
			entry.setOrderNo(orderNo);
			entry.setOrderDate(new Date());//订单日期
			entry.setCreateAt(new Date());//创建日期
			entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
			entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
			//促销判断
			promotionService.calculateEntryPromotion(entry);
			index++;
		}
		//订单价格
		orgOrder.setMemoTxt(record.getOrder().getMemoTxt());
		orgOrder.setCurAmt(orderAmt);
		orgOrder.setEndDate(calculateFinalDate(curEntrys));//订单截止日期
		orgOrder.setInitAmt(orderAmt);
		//orgOrder.setResumeFlag("N");
		//更新订单和 保存行项目
		tPreOrderMapper.updateBySelective(orgOrder);
		curEntrys.forEach(entry->{
			tPlanOrderItemMapper.insert(entry);
		});
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
		if(record.getEntries()==null||record.getEntries().size()<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"不能删除所有的行项目，请退订订单!");
		convertEntryDate(record.getEntries());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(record.getOrder().getOrderNo());

		if("10".equals(orgOrder.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"已经有收款单了，请不要修改订单，或者去删除收款单!");
		}
		if("20".equals(orgOrder.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+orgOrder.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
		}
		ArrayList<TPlanOrderItem> orgEntries = (ArrayList<TPlanOrderItem>) tPlanOrderItemMapper.selectByOrderCode(record.getOrder().getOrderNo());
		ArrayList<TPlanOrderItem> curEntries = record.getEntries();
		ArrayList<TPlanOrderItem> modifiedEntries = new ArrayList<TPlanOrderItem>();
		ArrayList<TPlanOrderItem> removedEntries = new ArrayList<TPlanOrderItem>();
		TSysUser user = userSessionService.getCurrentUser();

		boolean orderPromotionFlag = record.getEntries().stream().anyMatch((e)->StringUtils.isNotBlank(e.getPromotion()));

		if(ContentDiffrentUtil.isDiffrent(record.getOrder().getMemoTxt(),orgOrder.getMemoTxt())){
			//备注日志
			OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.ORDER, OrderLogEnum.MEMO_TXT,null,null,orgOrder.getMemoTxt(),
					record.getOrder().getMemoTxt(),null,null,user,operationLogMapper);
		}
		orgOrder.setMemoTxt(record.getOrder().getMemoTxt());
		String state = orgOrder.getPaymentmethod();
		if("10".equals(state)){
			//后付款
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
			//有路单一定作为有效期修改，*日期提前或者没有日计划，不作为有效期修改
			if((daliyPlans!=null&&daliyPlans.size() > 0&&StringUtils.isBlank(record.getEditDate()))||tDispOrderItemMapper.selectCountByOrgOrder(orgOrder.getOrderNo()) > 0 ){
				//作为有效期修改
				//修改订单根据行项目编号来确定行是否修改，换商品或改数量
				for(TPlanOrderItem orgEntry : orgEntries){
					boolean delFlag = true;
					boolean modiFlag = false;
					for(TPlanOrderItem curEntry : curEntries){
						if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
							this.editOrderDispTypeForLongLog(orgEntry,curEntry,user);
							delFlag = false;
//							if(StringUtils.isNotBlank(curEntry.getDeletePlansFlag())||StringUtils.isNotBlank(curEntry.getIsDeletedFlag()))break;
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
							String curStartstr = format.format(curEntry.getStartDispDate());
							String curEndstr = format.format(curEntry.getEndDispDate());
							if(!startstr.equals(curStartstr) || !endstr.equals(curEndstr) ){
								modiFlag = true;
							}
							
							if(!modiFlag){
								break;
							}
//							if(StringUtils.isNotBlank(orgEntry.getPromotion()))throw new ServiceException(MessageCode.LOGIC_ERROR,"促销商品行不能更改!");
							if(curEntry.getStartDispDate().before(orgEntry.getStartDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,curEntry.getStartDispDate()+"有效期不能在配送日期之外!");
							daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(orgEntry.getItemNo()))
						   	.forEach((e)->{
						   	if(!e.getDispDate().before(curEntry.getStartDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
						   });
							
							//删除不需要的日单
							TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
							newPlan.setOrderNo(orgOrder.getOrderNo());
							newPlan.setItemNo(orgEntry.getItemNo());
							newPlan.setDispDateStr(curStartstr);
							newPlan.setUnit(curEndstr);
							tOrderDaliyPlanItemMapper.deletePlansForLongEdit(newPlan);
							
							createDaliyPlanForAfterPay(orgOrder,orgEntry,curEntry.getStartDispDate(),curEntry.getEndDispDate());
							//保存修改后的该行
							tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
							
							//行修改完毕
							break;
						}
					}
					if(delFlag){
						if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
						//此行删除了，删除所有剩余的日单
						OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.DEL_ITEM,null,null,
								orgEntry.getMatnr()+" "+orgEntry.getShortTxt()+" "+orgEntry.getQty(),null,null,null,user,operationLogMapper);

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
				
				//行项目停订的,日期内的日计划停订
				TOrderDaliyPlanItem dRecord = new TOrderDaliyPlanItem();
				dRecord.setOrderNo(orgOrder.getOrderNo());
				for(TPlanOrderItem curEntry : curEntries){
					for(TPlanOrderItem orgEntry : orgEntries){
						if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
							if(curEntry.getStopStartDate()!=null&&curEntry.getStopEndDate()!=null){
								if(curEntry.getStopStartDate().before(orgEntry.getStartDispDate())||curEntry.getStopEndDate().after(orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"停订的日期不能在配送日期之外!");
								daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(curEntry.getItemNo()))
								.forEach((e)->{
									if(!e.getDispDate().before(curEntry.getStopStartDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
								});

								OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.STOP_ORDER,null,null,
										null,format.format(curEntry.getStopStartDate())+"-"+format.format(curEntry.getStopEndDate()) +("Y".equals(curEntry.getIsStop())?"  停订":"  "),null,null,user,operationLogMapper);

								dRecord.setItemNo(curEntry.getItemNo());
								dRecord.setDispDateStr(format.format(curEntry.getStopStartDate()));
								dRecord.setReachTime(format.format(curEntry.getStopEndDate()));
								tOrderDaliyPlanItemMapper.updateDaliyPlansToStopDateToDate(dRecord);
							}
							if(!(orgEntry.getIsStop()==null?"":orgEntry.getIsStop()).equals(curEntry.getIsStop()==null?"":curEntry.getIsStop())){
								orgEntry.setIsStop(curEntry.getIsStop());
								tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
							}
							break;
						}
					}
				}
				
				//新增的行项目
				int index =  tPlanOrderItemMapper.selectEntriesQtyByOrderCode(record.getOrder().getOrderNo());
				for(TPlanOrderItem entry : curEntries){
					if("Y".equals(entry.getNewFlag())){
//						if(entry.getStartDate()==null||entry.getStartDate().before(entry.getStartDispDate())||entry.getStartDate().after(entry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR, "开始配送日期填写有误，请检查");
						OperationLogUtil.saveHistoryOperation(record.getOrder().getOrderNo(),LogType.ORDER,OrderLogEnum.ADD_PRODUCT,null,null,
								null,entry.getMatnr()+entry.getShortTxt()+"  "+format.format(entry.getStartDispDate())+"-"+format.format(entry.getEndDispDate()),null,null,user,operationLogMapper);

						entry.setOrderNo(orgOrder.getOrderNo());
						entry.setItemNo(orgOrder.getOrderNo() + String.valueOf(index));//行项目编号
						entry.setRefItemNo(String.valueOf(index));//参考行项目编号
						entry.setOrderDate(orgOrder.getOrderDate());//订单日期
						entry.setCreateAt(new Date());//创建日期
						entry.setCreateBy(user.getLoginName());//创建人
						entry.setCreateByTxt(user.getDisplayName());//创建人姓名
						calculateEntryAmount(entry);
						Date tmp = entry.getEndDispDate();
						createDaliyPlanForAfterPay(orgOrder,entry,entry.getStartDispDate(),entry.getEndDispDate());
						entry.setEndDispDate(tmp);
						tPlanOrderItemMapper.insert(entry);
						index++;
					}
				}
	   		
				//生成新的每日订单
				daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
				
				//更新订单金额等
				BigDecimal initAmt = new BigDecimal("0.00");
				BigDecimal usedAmt = new BigDecimal("0.00");
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if("30".equals(plan.getStatus()))continue;
					initAmt = initAmt.add(plan.getAmt());
					if("20".equals(plan.getStatus())){
						usedAmt.add(plan.getAmt());
					}
				}
				orgOrder.setInitAmt(initAmt);
				orgOrder.setCurAmt(initAmt.subtract(usedAmt));

				//订单截止日期修改
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if(!"30".equals(plan.getStatus())){
						orgOrder.setEndDate(plan.getDispDate());
						break;
					}
				}
				
				Collections.reverse(daliyPlans);
				for(TOrderDaliyPlanItem plan : daliyPlans){
		   		if("30".equals(plan.getStatus())){
		   			plan.setRemainAmt(initAmt);
		   			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
		   			continue;
		   		}
		   		plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
		   		initAmt = initAmt.subtract(plan.getAmt());
		   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
		   	}
				
				tPreOrderMapper.updateOrderEndDate(orgOrder);
				
				
			}else{
				//修改订单根据行项目编号来确定行是否修改，换商品或改数量
				BigDecimal orderUsedAmt = orgOrder.getInitAmt().subtract(orgOrder.getCurAmt());
				BigDecimal orderAmt = new BigDecimal("0.00").add(orderUsedAmt);
				for(TPlanOrderItem orgEntry : orgEntries){
					boolean delFlag = true;
					boolean modiFlag = false;
					for(TPlanOrderItem curEntry : curEntries){
						if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
							this.editOrderDispTypeForLongLog(orgEntry,curEntry,user);
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
						if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
						OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.DEL_ITEM,null,null,
								orgEntry.getMatnr()+" "+orgEntry.getShortTxt()+" "+orgEntry.getQty(),null,null,null,user,operationLogMapper);
						removedEntries.add(orgEntry);
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
				
				orgEntries.removeAll(removedEntries);
				//更新订单
	   		orgOrder.setInitAmt(orderAmt);
	   		orgOrder.setCurAmt(orderAmt.subtract(orderUsedAmt));
	   		
				//生成新的每日订单
				createDaliyPlan(orgOrder , modifiedEntries);
				daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
				calculateDaliyPlanRemainAmt(orgOrder,daliyPlans);
				
				//订单截止日期修改
				orgOrder.setEndDate(calculateFinalDate(orgEntries));
				tPreOrderMapper.updateOrderEndDate(orgOrder);
			}
			
		}else{
			
			ArrayList<TPlanOrderItem> unsavedOrgEntries = new ArrayList<TPlanOrderItem>();
			orgEntries.stream().forEach((orgEntry)->{
				TPlanOrderItem entry = new TPlanOrderItem();
				cloneOrderEntry(entry,orgEntry);
				unsavedOrgEntries.add(entry);
			});
			
			ArrayList<TOrderDaliyPlanItem> daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
			if((daliyPlans!=null&&daliyPlans.size() > 0&&StringUtils.isBlank(record.getEditDate()))||tDispOrderItemMapper.selectCountByOrgOrder(orgOrder.getOrderNo()) > 0 ){
				//有效期修改
				if(orderPromotionFlag)throw new ServiceException(MessageCode.LOGIC_ERROR,"促销订单不能有修改!");
				for(TPlanOrderItem orgEntry : orgEntries){
					boolean delFlag = true;
					boolean modiFlag = false;
					for(TPlanOrderItem curEntry : curEntries){
						if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
							this.editOrderDispTypeForLongLog(orgEntry,curEntry,user);
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
							String curStartstr = format.format(curEntry.getStartDispDate());
							String curEndstr = format.format(curEntry.getEndDispDate());
							if(!startstr.equals(curStartstr) || !endstr.equals(curEndstr) ){
								modiFlag = true;
							}
							
							if(!modiFlag){
								break;
							}
							
							if(curEntry.getStartDispDate().before(orgEntry.getStartDispDate())||curEntry.getEndDispDate().after(orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,curEntry.getStartDispDate()+"到"+curEntry.getEndDispDate()+"[有效期不能在配送日期之外!]");
							daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(orgEntry.getItemNo()))
						   	.forEach((e)->{
						   	if(!e.getDispDate().before(curEntry.getStartDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间!");
						   });
							
							orgEntry.setModified(true);
							//删除不需要的日单
							TOrderDaliyPlanItem newPlan = new TOrderDaliyPlanItem();
							newPlan.setOrderNo(orgOrder.getOrderNo());
							newPlan.setItemNo(orgEntry.getItemNo());
							newPlan.setDispDateStr(curStartstr);
							newPlan.setUnit(curEndstr);
							tOrderDaliyPlanItemMapper.deletePlansForLongEdit(newPlan);
							
							//保存修改后的该行
							tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
							
							//行修改完毕
							break;
						}
					}
					if(delFlag){
						if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
						//此行删除了，删除所有剩余的日单
						OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.DEL_ITEM,null,null,
								orgEntry.getMatnr()+" "+orgEntry.getShortTxt()+" "+orgEntry.getQty(),null,null,null,user,operationLogMapper);

						removedEntries.add(orgEntry);
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
				
				orgEntries.removeAll(removedEntries);
				
				//新增的行项目
				int index =  tPlanOrderItemMapper.selectEntriesQtyByOrderCode(record.getOrder().getOrderNo());
				for(TPlanOrderItem entry : curEntries){
					if("Y".equals(entry.getNewFlag())){

						OperationLogUtil.saveHistoryOperation(record.getOrder().getOrderNo(),LogType.ORDER,OrderLogEnum.ADD_PRODUCT,null,null,
								null,entry.getMatnr()+entry.getShortTxt()+"  "+format.format(entry.getStartDispDate())+"-"+format.format(entry.getEndDispDate()),null,null,user,operationLogMapper);
						entry.setOrderNo(orgOrder.getOrderNo());
						entry.setItemNo(orgOrder.getOrderNo() + String.valueOf(index));//行项目编号
						entry.setRefItemNo(String.valueOf(index));//参考行项目编号
						entry.setOrderDate(orgOrder.getOrderDate());//订单日期
						entry.setCreateAt(new Date());//创建日期
						entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
						entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
						calculateEntryAmount(entry);
						orgEntries.add(entry);
						tPlanOrderItemMapper.insert(entry);
						index++;
					}
				}
				
				//行项目停订的,日期内的日计划停订
				TOrderDaliyPlanItem dRecord = new TOrderDaliyPlanItem();
				dRecord.setOrderNo(orgOrder.getOrderNo());
				for(TPlanOrderItem curEntry : curEntries){
					for(TPlanOrderItem orgEntry : orgEntries){
						if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
							if(curEntry.getStopStartDate()!=null&&curEntry.getStopEndDate()!=null){
								if(curEntry.getStopStartDate().before(orgEntry.getStartDispDate())||curEntry.getStopEndDate().after(orgEntry.getEndDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"停订的日期不能在配送日期之外!");
								daliyPlans.stream().filter((e)->"20".equals(e.getStatus())&&e.getItemNo().equals(curEntry.getItemNo()))
								.forEach((e)->{
									if(!e.getDispDate().before(curEntry.getStopStartDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"该日期内已经有完结的日计划，请修改时间，无法停订!");
								});
								orgEntry.setToStop("Y");
								orgEntry.setModified(true);
								dRecord.setItemNo(curEntry.getItemNo());
								dRecord.setDispDateStr(format.format(curEntry.getStopStartDate()));
//								dRecord.setReachTime(format.format(curEntry.getStopEndDate()));
								tOrderDaliyPlanItemMapper.updateDaliyPlansToStopDateToDate(dRecord);
							}
							if(!(orgEntry.getIsStop()==null?"":orgEntry.getIsStop()).equals(curEntry.getIsStop()==null?"":curEntry.getIsStop())){
								orgEntry.setIsStop(curEntry.getIsStop());
								tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
							}
							break;
						}
					}
				}
				
				createDaliyPlanForPrePay(orgOrder,orgEntries,curEntries,daliyPlans,unsavedOrgEntries);
				
				//生成新的每日订单
				daliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(orgOrder.getOrderNo());
				
				//更新订单金额等
				BigDecimal initAmt = orgOrder.getInitAmt();

				//订单截止日期修改
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if(!"30".equals(plan.getStatus())){
						orgOrder.setEndDate(plan.getDispDate());
						break;
					}
				}
				
				//回改订单行项目,更新最后配送日期
   			for( TPlanOrderItem entry :orgEntries){
   				for(TOrderDaliyPlanItem p:daliyPlans){
   					if(entry.getItemNo().equals(p.getItemNo())){
   						entry.setEndDispDate(p.getDispDate());
   						//保存修改后的该行
   						tPlanOrderItemMapper.updateEntryByItemNo(entry);
   						break;
   					}
   				}
   			}
				
				Collections.reverse(daliyPlans);
				for(TOrderDaliyPlanItem plan : daliyPlans){
					if(plan.getGiftQty()!=null)continue;
		   		if("30".equals(plan.getStatus())){
		   			plan.setRemainAmt(initAmt);
		   			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
		   			continue;
		   		}
		   		plan.setRemainAmt(initAmt.subtract(plan.getAmt()));
		   		initAmt = initAmt.subtract(plan.getAmt());
		   		tOrderDaliyPlanItemMapper.updateDaliyPlanItem(plan);
		   	}
				
				tPreOrderMapper.updateOrderEndDate(orgOrder);
				
			}else{
				if("20".equals(orgOrder.getPaymentStat())){//已经付过钱
					//先付款,订单总金额不变,配送到金额为0为止
					//修改订单根据行项目编号来确定行是否修改，换商品或改数量
					if(orderPromotionFlag)throw new ServiceException(MessageCode.LOGIC_ERROR,"促销订单不能有修改!");
					for(TPlanOrderItem orgEntry : orgEntries){
						boolean delFlag = true;
						boolean modiFlag = false;
						for(TPlanOrderItem curEntry : curEntries){
							if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
								this.editOrderDispTypeForLongLog(orgEntry,curEntry,user);
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
//								tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
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
							if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
							OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.DEL_ITEM,null,null,
									null,orgEntry.getMatnr()+" "+orgEntry.getShortTxt()+" "+orgEntry.getQty(),null,null,user,operationLogMapper);
							//此行删除了，删除所有剩余的日单
							removedEntries.add(orgEntry);
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
					
					orgEntries.removeAll(removedEntries);
					//生成新的每日订单
		   		createDaliyPlanForLongEdit(orgOrder , modifiedEntries ,orgEntries);
		   		
		   		//保存修改后的该行
		   		orgEntries.stream().forEach((e)->{tPlanOrderItemMapper.updateEntryByItemNo(e);});
					
					//订单截止日期修改
					orgOrder.setEndDate(calculateFinalDate(orgEntries));
					tPreOrderMapper.updateOrderEndDate(orgOrder);
					
				}else{
						//没付钱时，可以重新算金额，增加促销产品
						//修改订单根据行项目编号来确定行是否修改，换商品或改数量
						for(TPlanOrderItem orgEntry : orgEntries){
							boolean delFlag = true;
							boolean modiFlag = false;
							for(TPlanOrderItem curEntry : curEntries){
								if(orgEntry.getItemNo().equals(curEntry.getItemNo())){
									//修改 产品，数量，配送规律日志
									this.editOrderDispTypeForLongLog(orgEntry,curEntry,user);
									delFlag = false;
									BigDecimal orgEntryAmt = calculateEntryAmount(orgEntry);
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
									orgOrder.setInitAmt(orgOrder.getInitAmt().subtract(orgEntryAmt));
									orgOrder.setCurAmt(orgOrder.getCurAmt().subtract(orgEntryAmt));
									BigDecimal entryTotal = calculateEntryAmount(orgEntry);
									orgOrder.setInitAmt(orgOrder.getInitAmt().add(entryTotal));
									orgOrder.setCurAmt(orgOrder.getCurAmt().add(entryTotal));
//									tPlanOrderItemMapper.updateEntryByItemNo(orgEntry);
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
								if(tDispOrderItemMapper.selectCountByOrgOrderAndOrgItemNo(orgEntry.getOrderNo(),orgEntry.getItemNo(),null) > 0)throw new ServiceException(MessageCode.LOGIC_ERROR,orgEntry.getItemNo() + "[已经生成了路单，不可以删除此行!]");
								//删除订单行项目日志
								OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.DEL_ITEM,null,null,
										null,orgEntry.getMatnr()+" "+orgEntry.getShortTxt()+" "+orgEntry.getQty(),null,null,user,operationLogMapper);
								//此行删除了，删除所有剩余的日单
								BigDecimal entryTotal = calculateEntryAmount(orgEntry);
								orgOrder.setInitAmt(orgOrder.getInitAmt().subtract(entryTotal));
								orgOrder.setCurAmt(orgOrder.getCurAmt().subtract(entryTotal));
								removedEntries.add(orgEntry);
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
						
						orgEntries.removeAll(removedEntries);
						
						int index =  tPlanOrderItemMapper.selectEntriesQtyByOrderCode(record.getOrder().getOrderNo());
						for(TPlanOrderItem entry : curEntries){
							if("Y".equals(entry.getNewFlag())){
								OperationLogUtil.saveHistoryOperation(entry.getOrderNo(),LogType.ORDER,OrderLogEnum.ADD_PRODUCT,null,null,
										null,entry.getMatnr()+entry.getShortTxt()+"  "+format.format(entry.getStartDispDate())+"-"+format.format(entry.getEndDispDate()),null,null,user,operationLogMapper);

								entry.setOrderNo(orgOrder.getOrderNo());
								entry.setItemNo(orgOrder.getOrderNo() + String.valueOf(index));//行项目编号
								entry.setRefItemNo(String.valueOf(index));//参考行项目编号
								entry.setOrderDate(orgOrder.getOrderDate());//订单日期
								entry.setCreateAt(new Date());//创建日期
								entry.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
								entry.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
								orgEntries.add(entry);
								BigDecimal entryTotal = calculateEntryAmount(entry);
								orgOrder.setInitAmt(orgOrder.getInitAmt().add(entryTotal));
								orgOrder.setCurAmt(orgOrder.getCurAmt().add(entryTotal));
								promotionService.calculateEntryPromotion(entry);
								tPlanOrderItemMapper.insert(entry);
								index++;
							}
						}
						
			   		//保存修改后的该行
			   		orgEntries.stream().forEach((e)->{tPlanOrderItemMapper.updateEntryByItemNo(e);});
						
						//订单截止日期修改
						orgOrder.setEndDate(calculateFinalDate(orgEntries));
						tPreOrderMapper.updateOrderEndDate(orgOrder);
				}
				
			}
			
		}
		
		return 1;
	}

	private void editOrderDispTypeForLongLog(TPlanOrderItem orgEntry,TPlanOrderItem curEntry,TSysUser user) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String orgRuleType = orgEntry.getRuleType();
		Integer orgGapDays = orgEntry.getGapDays();
		String orgRuleTxt = orgEntry.getRuleTxt();
		String orgDispRate = format.format(orgEntry.getStartDispDate())+"至"+format.format(orgEntry.getEndDispDate());
		String orgReachTimeType = orgEntry.getReachTimeType();
		String curRuleType = curEntry.getRuleType();
		Integer curGapDays = curEntry.getGapDays();
		String curRuleTxt = curEntry.getRuleTxt();
		String curDispRate = format.format(curEntry.getStartDispDate())+"至"+format.format(curEntry.getEndDispDate());
		String curReachTimeType = curEntry.getReachTimeType();
		String orgString = "";
		String curString = "";

		if(ContentDiffrentUtil.isDiffrent(orgRuleType,curRuleType) || ContentDiffrentUtil.isDiffrent(curGapDays,orgGapDays)|| ContentDiffrentUtil.isDiffrent(orgRuleTxt,curRuleTxt)) {
			orgString = orgString + ("10".equals(orgRuleType) ? "按周期送间隔" + orgGapDays + "天 " : "按星期送" + orgRuleTxt+" ");
			curString = curString + ("10".equals(curRuleType) ? "按周期送间隔" + curGapDays + "天 " : "按星期送" + curRuleTxt+" ");
		}
		if(!orgReachTimeType.equals(curReachTimeType)){
			orgString = orgString + ("10".equals(orgReachTimeType)?"上午配送 ":"下午配送 ");
			curString = curString + ("10".equals(curReachTimeType)?"上午配送 ":"下午配送 ");
		}
		if(!orgDispRate.equals(curDispRate)){
			orgString = orgString + orgDispRate+" ";
			curString = curString + curDispRate+" ";
		}


		//变更产品
		if(!orgEntry.getMatnr().equals(curEntry.getMatnr())){
			TMdMaraEx ex = maraExMapper.getProductTransRateByCode(curEntry.getMatnr(),user.getSalesOrg());
			if(ex!=null){
				curEntry.setMatnrTxt(ex.getShortTxt());
			}
			if(!orgEntry.getQty().equals(curEntry.getQty()) && (StringUtils.isNotBlank(orgString) && StringUtils.isNotBlank(curString))){
				//变更产品 同时变更数量 和 配送日期
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.CHANGE_PRODUCT_QTY_RULE,null,null,
						orgEntry.getMatnr()+orgEntry.getShortTxt()+"，"+orgEntry.getQty()+"，"+orgString,curEntry.getMatnr()+curEntry.getShortTxt()+"，"+curEntry.getQty()+"，"+curString,
						null,null,user,operationLogMapper);

			}else if(!orgEntry.getQty().equals(curEntry.getQty()) && (StringUtils.isBlank(orgString) && StringUtils.isBlank(curString))){
				//变更产品 同时变更数量
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.CHANGE_PRODUCT_QTY,null,null,
						orgEntry.getMatnr()+orgEntry.getShortTxt()+"，"+orgEntry.getQty(),curEntry.getMatnr()+curEntry.getShortTxt()+"，"+curEntry.getQty(),
						null,null,user,operationLogMapper);

			}else if(orgEntry.getQty().equals(curEntry.getQty()) && (StringUtils.isNotBlank(orgString) && StringUtils.isNotBlank(curString))){
				//变更产品 同时变更配送日期
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.CHANGE_PRODUCT_RULE,null,null,
						orgEntry.getMatnr()+orgEntry.getShortTxt()+"，"+orgString,curEntry.getMatnr()+curEntry.getShortTxt()+"，"+curString,
						null,null,user,operationLogMapper);
			}else if(orgEntry.getQty().equals(curEntry.getQty())  && (StringUtils.isBlank(orgString) && StringUtils.isBlank(curString))){
				//只变更产品
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.CHANGE_PRODUCT,null,null,
						orgEntry.getMatnr()+orgEntry.getShortTxt(),curEntry.getMatnr()+curEntry.getShortTxt(),null,null,user,operationLogMapper);
			}
		//变更数量
		}else if(!orgEntry.getQty().equals(curEntry.getQty())){
			//变更数量  同时变更配送日期
			if(StringUtils.isNotBlank(orgString) && StringUtils.isNotBlank(curString)){
				//只变更数量
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.CHANGE_QTY_RULE,null,null,
						orgEntry.getQty().toString()+"，"+orgString,curEntry.getQty().toString()+"，"+curString,orgEntry.getMatnr()+orgEntry.getShortTxt(),null,
						user,operationLogMapper);
			}else{
				//只变更数量
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.CHANGE_QTY,null,null,
						orgEntry.getQty().toString(),curEntry.getQty().toString(),orgEntry.getMatnr()+orgEntry.getShortTxt(),null,
						user,operationLogMapper);
			}
		//变更配送日期
		}else if(StringUtils.isNotBlank(orgString) && StringUtils.isNotBlank(curString)){
			//只变配送日期
			OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.CHANGE_RULE_TYPE,null,null,
					orgString,curString,orgEntry.getMatnr()+orgEntry.getShortTxt(),null,user,operationLogMapper);
		}

		//行项目停订日志
		if(ContentDiffrentUtil.isDiffrent(orgEntry.getIsStop(),curEntry.getIsStop())){
			if("Y".equals(curEntry.getIsStop()) && curEntry.getStopStartDate()!=null){
				OperationLogUtil.saveHistoryOperation(orgEntry.getOrderNo(),LogType.ORDER,OrderLogEnum.ORDER_ITEM_STOP,null,null,
						null,format.format(curEntry.getStopStartDate()),orgEntry.getMatnr()+orgEntry.getShortTxt(),null,user,operationLogMapper);
			}
		}


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
		TSysUser user = userSessionService.getCurrentUser();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(record.getOrderCode());
		if("10".equals(orgOrder.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"后付款订单已经有收款单了，请不要修改订单，或者去删除收款单!");
		}
		if("20".equals(orgOrder.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+orgOrder.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
		}
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
				String logName = "";
				TMdMaraEx ex = maraExMapper.getProductTransRateByCode(plan.getMatnr(),user.getSalesOrg());
				if(ex!=null){
					plan.setMatnrTxt(ex.getShortTxt());
				}
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
					OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.DAIL_ORDER,DailOrderLogEnum.STATUS,null,null,
							"在订", "停订",
							orgPlan.getMatnr(),orgPlan.getDispDate(),user,operationLogMapper);
					orgPlan.setStatus(plan.getStatus());
					cj = cj.add(orgPlan.getAmt());
				}
				//送达时段
				if(StringUtils.isNotBlank(plan.getReachTimeType())){
					if(!plan.getReachTimeType().equals(orgPlan.getReachTimeType())){
						OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.DAIL_ORDER,DailOrderLogEnum.REACH_TIME_TYPE,null,null,
								"10".equals(orgPlan.getReachTimeType())?"上午配送":"下午配送","10".equals(plan.getReachTimeType())?"上午配送":"下午配送",
								orgPlan.getMatnr()+orgPlan.getMatnrTxt(),orgPlan.getDispDate(),user,operationLogMapper);
					}
					orgPlan.setReachTimeType(plan.getReachTimeType());
				}
				if(!"30".equals(plan.getStatus())){
					//更换产品和数量
					if(!orgPlan.getMatnr().equals(plan.getMatnr()) && !orgPlan.getQty().equals(plan.getQty())){
						float price = priceService.getMaraPrice(orgOrder.getBranchNo(), plan.getMatnr(), orgOrder.getDeliveryType());
						if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"替换的产品价格小于0，请维护价格组!");
						cj = orgPlan.getAmt().subtract(new BigDecimal(String.valueOf(price)).multiply(new BigDecimal(plan.getQty().toString())));
						orgPlan.setPrice(new BigDecimal(String.valueOf(price)));
					}else if(!orgPlan.getMatnr().equals(plan.getMatnr())){
						//只换产品
						float price = priceService.getMaraPrice(orgOrder.getBranchNo(), plan.getMatnr(), orgOrder.getDeliveryType());
						if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"替换的产品价格小于0，请维护价格组!");
						cj = orgPlan.getAmt().subtract(new BigDecimal(String.valueOf(price)).multiply(new BigDecimal(orgPlan.getQty().toString())));
						orgPlan.setPrice(new BigDecimal(String.valueOf(price)));
					}else if(!orgPlan.getQty().equals(plan.getQty())){
						//只换数量
						float price = orgPlan.getPrice().floatValue();
						if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"替换的产品价格小于0，请维护价格组!");
						cj = orgPlan.getAmt().subtract(new BigDecimal(String.valueOf(price)).multiply(new BigDecimal(plan.getQty().toString())));
						OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.DAIL_ORDER,DailOrderLogEnum.UPT_QTY,null,null,
								orgPlan.getStatus(),plan.getStatus(),orgPlan.getMatnr(),orgPlan.getDispDate(),user,operationLogMapper);
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
				BigDecimal newInitAmt = tOrderDaliyPlanItemMapper.selectInitAmtDaysByNo(orgOrder.getOrderNo());
				BigDecimal newCurAmt = tOrderDaliyPlanItemMapper.selectCurAmtDaysByNo(orgOrder.getOrderNo());

				//重新计算日计划的剩余金额,更新订单金额
				orgOrder.setInitAmt(newInitAmt.subtract(cj));
				orgOrder.setCurAmt(newCurAmt.subtract(cj));
//				tPreOrderMapper.updateOrderInitAmtAndCurAmt(orgOrder);
				
				calculateDaliyPlanRemainAmt(orgOrder,daliyPlans);
				
				for(TOrderDaliyPlanItem p : daliyPlans){
					if(!"30".equals(p.getStatus())){
						orgOrder.setEndDate(p.getDispDate());
						break;
					}
				}
				tPreOrderMapper.updateOrderEndDate(orgOrder);
				
			}
			
		//先付款的	,日订单往后延
		}else if("20".equals(orgOrder.getPaymentmethod()) ){
			for(TOrderDaliyPlanItem plan : record.getEntries()){
				TMdMaraEx ex = maraExMapper.getProductTransRateByCode(plan.getMatnr(),user.getSalesOrg());
				if(ex!=null){
					plan.setMatnrTxt(ex.getShortTxt());
				}
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
					if(!plan.getReachTimeType().equals(orgPlan.getReachTimeType())){
						OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.DAIL_ORDER,DailOrderLogEnum.REACH_TIME_TYPE,null,null,
								"10".equals(orgPlan.getReachTimeType())?"上午配送":"下午配送","10".equals(plan.getReachTimeType())?"上午配送":"下午配送",
								orgPlan.getMatnr()+orgPlan.getMatnrTxt(),orgPlan.getDispDate(),user,operationLogMapper);
					}
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

	@Override
	public void resumeDaliyPlanForRouteOrder2(BigDecimal confirmQty, TDispOrderItem entry, TPlanOrderItem orgEntry, Date dispDate){
		if("Y".equals(entry.getGiftFlag())){
			//赠品的，只更新原日计划
			TOrderDaliyPlanItem promDaliy = tOrderDaliyPlanItemMapper.selectDaliyByDispItem(entry);
			promDaliy.setMatnr(entry.getConfirmMatnr());
			promDaliy.setQty(entry.getConfirmQty().intValue());
			promDaliy.setStatus("20");
			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(promDaliy);
			return;
		}

		TPreOrder orgOrder = tPreOrderMapper.selectByPrimaryKey(orgEntry.getOrderNo());

		if("10".equals(entry.getReason())){
			//换货的，只更新原日计划
			TOrderDaliyPlanItem daliy = tOrderDaliyPlanItemMapper.selectDaliyByDispItem(entry);
			daliy.setMatnr(entry.getConfirmMatnr());
			daliy.setQty(entry.getConfirmQty().intValue());
			daliy.setStatus("20");
			tOrderDaliyPlanItemMapper.updateDaliyPlanItem(daliy);
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
		//预付款的订单
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
			for(int i=1;i<3650;i++){
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
			for(int i=1;i<3650;i++){
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
			for(int i=1;i<3650;i++){
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
			for(int i=1;i<3650;i++){
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
			model.setSuppAmt(customerBill.getSuppAmt());
			model.setDiscountAmt(customerBill.getDiscountAmt());
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
			model.setDiscountAmt(order.getDiscountAmt());
			BigDecimal remainAmt = order.getInitAmt().subtract(order.getDiscountAmt());
			if(acLeftAmt.compareTo(remainAmt) == 1){
				model.setSuppAmt(BigDecimal.ZERO);
			}else{
				model.setSuppAmt(remainAmt.subtract(acLeftAmt));
			}

		}

		BigDecimal totalPrices = new BigDecimal(0);
		List<ProductItem> entries = new ArrayList<ProductItem>();
		//如果是预付款订单（ 查询订单行项目）
		//如果是后付款订单(统计所有有效的的日订单)
		if("20".equals(order.getPaymentmethod())){
				List<TPlanOrderItem> items = tPlanOrderItemMapper.selectByOrderCode(order.getOrderNo());
				if(items!=null && items.size()>0){
					for(TPlanOrderItem item : items ){
						BigDecimal price = item.getSalesPrice();
						if(price == null ){
							throw  new ServiceException(MessageCode.LOGIC_ERROR,"该预付款订单中的  "+item.getMatnr()+"   "+item.getMatnrTxt()+"   产品价格不存在请查看");
						}
						ProductItem entry = new ProductItem();
						entry.setMatnr(item.getMatnr());
						entry.setMatnrTxt(item.getMatnrTxt());
						entry.setMatnr(item.getMatnr());
						if(item.getDispTotal() == null){
							throw  new ServiceException(MessageCode.LOGIC_ERROR,"该预付款订单中的  "+item.getMatnr()+"   "+item.getMatnrTxt()+"   产品的总配送数量不存在 请查看");
						}else{
							entry.setQty(item.getDispTotal());
						}
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
					BigDecimal price = item.getPrice();
					if(price == null ){
						throw  new ServiceException(MessageCode.LOGIC_ERROR,"该后付款订单  "+ orderCode+"  中的  "+item.getMatnr()+"   "+item.getMatnrTxt()+"   产品价格不存在请查看");
					}
					ProductItem entry = new ProductItem();
					entry.setMatnr(item.getMatnr());
					entry.setMatnrTxt(item.getMatnrTxt());
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
public static int dayOfTwoDay(Date day1,Date day2) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date d1 = null;
	Date d2 = null;
	try {
		d1 = sdf.parse(sdf.format(day1));
		d2 = sdf.parse(sdf.format(day2));
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return daysOfTwo(d1,d2);
}


	//计算间隔天数
	public static int daysOfTwo(Date fDate, Date oDate) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
      Calendar cal = Calendar.getInstance();    
      cal.setTime(fDate);    
      long time1 = cal.getTimeInMillis();                 
      cal.setTime(oDate);    
      long time2 = cal.getTimeInMillis();         
      long between_days=(time2-time1)/(1000*3600*24);  
          
      return Integer.parseInt(String.valueOf(between_days));
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
   	
   	int gapDays = 0;
   	if(entry.getGapDays()!=null){
   		gapDays = entry.getGapDays() + 1;//间隔天数
   	}
   	
   	List<String> deliverDays = null;
   	if(entry.getRuleTxt()!=null){
   		deliverDays = Arrays.asList(entry.getRuleTxt().split(","));
   	}
   	
   	for(int afterDays = 1; afterDays < 3650; afterDays++){
   		
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
   public BigDecimal calculateEntryAmount(TPlanOrderItem entry){
   	
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
	  TSysUser user = userSessionService.getCurrentUser();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");//该商品该日期原日计划送多少个
		//BigDecimal curAmt = orgOrder.getCurAmt();//当前订单的剩余金额
	   //获取当前日计划状态为在订的 总金额 = 订单总金额-完结的总金额
	   BigDecimal curAmt =  orgOrder.getInitAmt().subtract(tOrderDaliyPlanItemMapper.getOrderOrderDailyFinishAmtByOrderNo(orgOrder.getOrderNo()));
	  if(orgOrder.getCurAmt().compareTo(curAmt)!=0){
		  orgOrder.setCurAmt(curAmt);
		  tPreOrderMapper.updateBySelective(orgOrder);
	  }
	   Map<String,Integer> needNewDaliyPlans = new HashMap<String,Integer>();//所有需要新增或减少的日计划行

   	Map<String,TPlanOrderItem> relatedEntryMap = new HashMap<String,TPlanOrderItem>();//key为订单行itemNo,value为entry
	   boolean orderPromFlag = StringUtils.isNotBlank(orgOrder.getPromotion())&&StringUtils.isNotBlank(orgOrder.getPromItemNo())?true:false;
	   boolean itemPromFlag = false;
	   TPromotion promModel = null;
	   if(orderPromFlag){
		   promModel = promotionService.selectPromotionByPromNoAndItemNo(orgOrder.getPromotion(),orgOrder.getPromItemNo());
		   if(promModel==null){
			   throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了整单促销，但是获取促销信息失败，请查看！！！");
		   }
	   }
   for(TPlanOrderItem entry : orgEntries){
   		if(!relatedEntryMap.containsKey(entry.getItemNo())){
			if(StringUtils.isNotBlank(entry.getPromotion())&&StringUtils.isNotBlank(entry.getPromItemNo())){
				promModel = promotionService.selectPromotionByPromNoAndItemNo(entry.getPromotion(),entry.getPromItemNo());
				if(itemPromFlag)throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单既参加了不止一个行项目促销，请查看！！！");
				if(orderPromFlag) throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单既参加了整单促销，又参加了单品促销，请查看！！！");
				itemPromFlag = true;
				if(promModel==null){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单的订单参加了单品促销，但是获取促销信息失败，请查看！！！");
				}
			}
   			relatedEntryMap.put(entry.getItemNo(), entry);
   		}
   	}
   	
   	//针对于原订单行要减少或者增加多少件商品(该产品 也会改数量)
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
   	
   	//数量，当value为负说明该产品行日计划要增加延后value件商品，正说明要提前（产品不变）
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
   	
   	//newPlans 需要向后追加的日订单
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
      					//TODO 是否应该set为0
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
					//订单行需要追加的数量
   					int needQty = needNewDaliyPlans.get(plan.getItemNo());
					//订单行每天配送的数量
   					int entryQty = relatedEntryMap.get(plan.getItemNo()).getQty();
					//如果订单行每天配送的数量>需要追加的数量说明  还只够生一天
   					if(entryQty>=needQty){
   						np.setQty(needQty);
   						needNewDaliyPlans.replace(plan.getItemNo(), 0);
   					}else{
						//如果订单行每天配送的数量 <需要追加的数量说明
						// 这天能生并且 将需要追加数量更新
   						np.setQty(entryQty);
   						needNewDaliyPlans.replace(plan.getItemNo(), needQty-entryQty);
   					}
					//np 克隆该日订单
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
   	Map<String,Date> dateMap = new HashMap<String,Date>();//存放每个订单行的最后配送时间（截止日期）
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
					OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.DAIL_ORDER,DailOrderLogEnum.STATUS,null,null,
							"在订",
							"停订",
							plan.getMatnr()+plan.getMatnrTxt(),plan.getDispDate(),user,operationLogMapper);
					plan.setStatus("30");
//					continue;
				}
				//变更产品
				if(changeProducts.containsKey(plan)){
					if(plan.getQty().compareTo(changeProducts.get(plan).getQty())==0){
						//只换产品
						OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(), LogType.DAIL_ORDER,DailOrderLogEnum.UPT_MATNR,null,null,
								plan.getMatnr()+plan.getMatnrTxt(),changeProducts.get(plan).getMatnr()+changeProducts.get(plan).getMatnrTxt(),null
								,plan.getDispDate(),user,operationLogMapper);
					}else{
						//换产品 同时换数量
						OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(), LogType.DAIL_ORDER,DailOrderLogEnum.UPT_MATNR_QTY,null,null,
								plan.getMatnr()+plan.getMatnrTxt()+"—"+plan.getQty(),changeProducts.get(plan).getMatnr()+changeProducts.get(plan).getMatnrTxt()+"—"+changeProducts.get(plan).getQty(),null
								,plan.getDispDate(),user,operationLogMapper);
					}
					plan.setMatnr(changeProducts.get(plan).getMatnr());
					plan.setQty(changeProducts.get(plan).getQty());
					plan.setUnit(changeProducts.get(plan).getUnit());
					plan.setPrice(changeProducts.get(plan).getPrice());
					plan.setAmt(changeProducts.get(plan).getPrice().multiply(new BigDecimal(changeProducts.get(plan).getQty().toString())));
				}
				//变更数量
				else if(changeQtys.containsKey(plan)){
					//日订单更改数量日志
				OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(), LogType.DAIL_ORDER,DailOrderLogEnum.UPT_QTY,null,null,
					plan.getQty().toString(),changeQtys.get(plan).getQty().toString(),plan.getMatnr()+plan.getMatnrTxt(),plan.getDispDate(),userSessionService.getCurrentUser(),operationLogMapper);
					plan.setQty(changeQtys.get(plan).getQty());
					plan.setAmt(changeQtys.get(plan).getPrice().multiply(new BigDecimal(changeQtys.get(plan).getQty().toString())));
				}
				newPlans.add(0,plan);
   		}
   	}
	   //TODO 重新查取最新的plan_item_no  不删除赠品
   	TOrderDaliyPlanItem record = new TOrderDaliyPlanItem();
   	record.setOrderNo(newPlans.get(0).getOrderNo());
   	record.setDispDateStr(format.format(newPlans.get(0).getDispDate()));//从什么时候删除日单
   	tOrderDaliyPlanItemMapper.deleteFromDateToDateExceptProm(record);
   	
   	//保存所有新的日计划
   	int index = 0;
   try{
	   index = tOrderDaliyPlanItemMapper.selectMaxDaliyPlansNoByOrderNo(orgOrder.getOrderNo()) + 1;
   }catch(Exception e){
	   //如果找不到最大值
   }
   	List<TOrderDaliyPlanItem> dateList = new ArrayList<TOrderDaliyPlanItem>(); 
   	for(TOrderDaliyPlanItem plan:newPlans){
   		
   		//停订的和确认的，直接保存
   		plan.setPlanItemNo(String.valueOf(index));
   		if(stopPlans.containsKey(plan)||"20".equals(plan.getStatus())||"30".equals(plan.getStatus())){
//   			tOrderDaliyPlanItemMapper.insert(plan);
   			index++;
   			continue;
   		}
   		
   		//赠品不动
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
					for(int i=1;i<3650;i++){
						Date today = afterDate(lastDate,i);
						if(dayOfTwoDay(dateMap.get(plan.getItemNo()),today)%gapDays !=0){
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
					for(int i=1;i<3650;i++){
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
		if((orderPromFlag || itemPromFlag) && promModel!=null){
			if(orderPromFlag){
				if(DateUtil.dateAfter(plan.getDispDate(), promModel.getPlanStopTime())){
					if("Z015".equals(promModel.getPromSubType())){
						throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了整单满减促销，修改后有日订单的配送日期超过了促销的截止日期"+format.format(promModel.getPlanStopTime()));
					}
				}
			}
			if(itemPromFlag){
				if(StringUtils.isNotBlank(relatedEntryMap.get(plan.getItemNo()).getPromotion())){
					if(DateUtil.dateAfter(plan.getDispDate(), promModel.getPlanStopTime())){
						if("Z008".equals(promModel.getPromSubType())){
							throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满赠促销，修改后有日订单的配送日期"+format.format(plan.getDispDate())+"超过了促销的截止日期"+format.format(promModel.getPlanStopTime()));
						}
						if("Z015".equals(promModel.getPromSubType())){
							throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单参加了单品满减促销，修改后有日订单的配送日期"+format.format(plan.getDispDate())+"超过了促销的截止日期"+format.format(promModel.getPlanStopTime()));
						}
					}
				}
			}
		}
   		tOrderDaliyPlanItemMapper.insert(plan);
   	}
   	
   	//订单截止日期修改
   	orgOrder.setEndDate(newPlans.get(newPlans.size()-1).getDispDate());
		tPreOrderMapper.updateOrderEndDate(orgOrder);
   /*
   	for(TOrderDaliyPlanItem plan:newPlans){
   		if(plan.getGiftQty()==null)continue;
   		for(TOrderDaliyPlanItem datePlan:dateList){
   			if(!datePlan.getItemNo().equals(plan.getItemNo())){
   				continue;
   			}
   			plan.setDispDate(datePlan.getDispDate());
   			datePlan.setItemNo("-1");//占用过,下次不占用
   			plan.setPlanItemNo(String.valueOf(index));
   			index++;
   			tOrderDaliyPlanItemMapper.insert(plan);
   			break;
   		}
   	}*/
   	
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
		//TODO  赠品  跳过
		if(plan.getGiftQty()!=null && plan.getPrice()==null){ continue;}
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
   		//if(!"10".equals(order.getPreorderSource())&&!"20".equals(order.getPreorderSource())&&!"40".equals(order.getPreorderSource())){
		if("30".equals(order.getPreorderSource()) || StringUtils.isBlank(order.getPreorderSource())){
   				throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择送奶员!");
   		}
	}
   if(StringUtils.isNotBlank(order.getPromotion())){
		record.getEntries().stream().forEach(e->{
			if(StringUtils.isNotBlank(e.getPromotion()) && StringUtils.isNotBlank(e.getPromItemNo())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"一个订单只能参加一个促销");
			}
		});
   }else{
	   int num = 0;
	   for(TPlanOrderItem item :record.getEntries()){
		   if(StringUtils.isNotBlank(item.getPromotion())){
			   num = num+1;
		   }
	   }
	   if(num>1){
		   throw new ServiceException(MessageCode.LOGIC_ERROR,"一个订单只能参加一个促销");
	   }
   }
   	if(record.getEntries()==null || record.getEntries().size() == 0){
   		throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择商品行!");
	}
   	if(StringUtils.isBlank(order.getMilkmemberNo())){
   		//if(!"10".equals(order.getPreorderSource())&&!"20".equals(order.getPreorderSource())){//电商不交验订户
		if("30".equals(order.getPreorderSource())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择订户!");
   		}
	}
   	if(StringUtils.isBlank(order.getAdressNo())){
   		if(record.getAddress() == null){
   			throw new ServiceException(MessageCode.LOGIC_ERROR,"请选择或输入地址!");
   		}else{
			if(!"20".equals(order.getPreorderSource())&& !"30".equals(order.getPreorderSource())){
				if(StringUtils.isNotBlank(record.getAddress().getProvince())){
					NHSysCodeItem item = new NHSysCodeItem();
					item.setTypeCode(SysContant.getSystemConst("Province_City_County"));
					item.setItemCode(record.getAddress().getProvince());
					NHSysCodeItem result = codeItemMapper.findCodeItenByCode(item);
					if(result==null){
						throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址的省在订户系统中不存在，请维护!");
					}
				}else{
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址的省份Province不存在，请维护!");
				}
				if(StringUtils.isNotBlank(record.getAddress().getCity())){
					NHSysCodeItem item = new NHSysCodeItem();
					item.setTypeCode(SysContant.getSystemConst("Province_City_County"));
					item.setItemCode(record.getAddress().getCity());
					NHSysCodeItem result = codeItemMapper.findCodeItenByCode(item);
					if(result==null){
						throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址的市City在订户系统中不存在，请维护!");
					}
				}else{
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址的市区City字段不存在，请维护!");
				}

				if(StringUtils.isNotBlank(record.getAddress().getCounty())){
					NHSysCodeItem item = new NHSysCodeItem();
					item.setTypeCode(SysContant.getSystemConst("Province_City_County"));
					item.setItemCode(record.getAddress().getCounty());
					NHSysCodeItem result = codeItemMapper.findCodeItenByCode(item);
					if(result==null){
						throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址的区县County在订户系统中不存在，请维护!");
					}
				}else{
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该地址的区县County字段不存在，请维护!");
				}
			}

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
 		int maxEntryDay = 3650;
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
 						if((!stopEndDate.after(entry.getStartDispDate())?afterDays:daysOfTwo(entry.getStartDispDate(),today))%gapDays != 0){
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
		/*if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getPaymentStat())){
			entries.stream().forEach((e)->{resolveEntryEndDispDateByEntryAmt(e);});
			return null;
		}*/
 			
 		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
 		if("20".equals(order.getMilkboxStat())){
 			if("20".equals(order.getPaymentmethod())){
 				entries.stream().forEach((e)->{resolveEntryEndDispDateByEntryAmt(e);});
 			}
 			return null;
 		}
 		
 		entries.stream().forEach((e)->{resolveEntryEndDispDateByEntryAmt(e);});
		ArrayList<TOrderDaliyPlanItem> orgDaliyPlans = (ArrayList<TOrderDaliyPlanItem>) tOrderDaliyPlanItemMapper.selectDaliyPlansByOrderNo(order.getOrderNo());
 		BigDecimal curAmt = order.getInitAmt();//订单余额总，金额减去所有未修改的金额
 		BigDecimal initAmt = order.getInitAmt();
// 		for(TOrderDaliyPlanItem p :orgDaliyPlans){
// 			if("30".equals(p.getStatus()))continue;
// 			curAmt = curAmt.subtract(p.getAmt());
// 		}
 		
 		List<TOrderDaliyPlanItem> daliyPlans = new ArrayList<TOrderDaliyPlanItem>();
 		Date firstDeliveryDate = null;
 		Map<TPlanOrderItem,Date> dateMap = new HashMap<TPlanOrderItem,Date>();
 		for(TPlanOrderItem entry: entries){
 			dateMap.put(entry, entry.getEndDispDate());
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
 		outer:for(int afterDays = 0; afterDays < 3650 ; afterDays ++){
 			for(TPlanOrderItem entry : entries){
 				
 			//判断是按周期送还是按星期送
				Date today = afterDate(firstDeliveryDate,afterDays);
				
				if(entry.getStartDispDate().after(today) || dateMap.get(entry).before(today))continue;
				
//				if(orgDaliyPlans.stream().anyMatch((e)->e.getItemNo().equals(entry.getItemNo()) &&e.getDispDate().equals(today)) )continue;
				
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
				
				//行项目生成到这天
				entry.setEndDispDate(today);
				
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
 	public void resolveEntryEndDispDate(TPlanOrderItem entry){
 		int total = entry.getDispTotal();
 		if(total<=0 || total%entry.getQty()!=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"行总共配送无法平均分配到每一天!请修改总数或每日配送数");
 		
 		int afterDays = 0;
 		//判断是按周期送还是按星期送
		Date today = entry.getStartDispDate();
		while(total> 0){
			 today = afterDate(entry.getStartDispDate(),afterDays);
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
		}
		entry.setEndDispDate(today);

 	}
 	
   //当订单是预付款时，订单行有配送总数和单价和起始日期，需要计算结束日期
 	private void resolveEntryEndDispDateByEntryAmt(TPlanOrderItem entry){
 		BigDecimal entryTotal = new BigDecimal(entry.getDispTotal().toString()).multiply(entry.getSalesPrice());
 		int total = 0;
 		
 		int afterDays = 0;
 		
 		//判断是按周期送还是按星期送
		for(int i=0;i<3650;i++){
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
			
			entryTotal = entryTotal.subtract(new BigDecimal(entry.getQty()).multiply(entry.getSalesPrice()));
			afterDays++;
			
			if(entryTotal.floatValue() < 0){
				break;
			}
			
			total = total + entry.getQty();
			entry.setEndDispDate(today);
			entry.setDispTotal(total);
			
		}
 	};
 	
   //当订单是预付款时，订单行有配送总数和起始日期，需要计算结束日期,页面用，顺便计算金额
  	private void resolveEntryEndDispDateForFront(TPlanOrderItem entry){
  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  		
  		try{
     		int total = entry.getDispTotal();
     		if(total<=0 || total%entry.getQty()!=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"行总共配送无法平均分配到每一天!请修改总数或每日配送数");
     		
     		int afterDays = 0;
     		BigDecimal amt = new BigDecimal("0.00");//行金额总计
     		
     		//判断是按周期送还是按星期送
    		for(int i=0;i<3650;i++){
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
    		for(int i=0;i<3650;i++){
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
    		for(int i=0;i<3650;i++){
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
	public int createDaliyPlansForIniOrders(String str)
	{
		if(StringUtils.isBlank(str) || str.length()<5)throw new ServiceException(MessageCode.LOGIC_ERROR,"请输入5位或以上字符串");
		
		TPreOrder model = new TPreOrder();
		model.setOrderNo(str);
		List<TPreOrder> orders = tPreOrderMapper.selectIniOrders(model);
		
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
			int maxEntryDay = 3650;
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
			System.out.println(order.getOrderNo()+"完毕！");
			
		});
		
		System.out.println("========================生成导入订单的日计划已经全部完毕！==============================");
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
		/*
			if(record.getOrders()!=null){
				List<TPreOrder> orders = tPreOrderMapper.selectOrdersByOrderNos(record.getOrders());
				if(orders!=null){
					TSysUser user = userSessionService.getCurrentUser();
					TMdBranchEmp emp = branchEmpMapper.selectBranchEmpByNo(record.getEmpNo());
					orders.stream().forEach(order->{
						OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER, OrderLogEnum.CHANGE_EMP,null,null,order.getEmpNo()+order.getEmpName(),emp.getEmpNo()+emp.getEmpName()
						,null,null,user,operationLogMapper);
					});
				}
			}
		*/
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
		int maxEntryDay = 3650;
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
		
		for(String itemNo : entryMap2.keySet()){
			for(TOrderDaliyPlanItem plan : daliyPlans){
				if(plan.getItemNo().equals(itemNo)){
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
						if(o1.getGiftQty()!=null||o2.getGiftQty()!=null)return -1;
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
		TSysUser user = userSessionService.getCurrentUser();
		if("10".equals(orgOrder.getPaymentmethod())){
			if(customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo())!=null)throw new ServiceException(MessageCode.LOGIC_ERROR,"已经有收款单了，请不要修改订单，或者去删除收款单!");
		}
		if("20".equals(orgOrder.getPaymentmethod())){
			TMstRecvBill bill = customerBillMapper.getRecBillByOrderNo(orgOrder.getOrderNo());
			if(bill!=null && "10".equals(bill.getStatus())){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"预付款订单  "+orgOrder.getOrderNo()+"  已经有收款单但是还没完成收款，请不要修改订单，或者去删除收款单!");
			}
		}
		
		//validate
		if(tDispOrderMapper.selectTodayDispOrderByBranchNo(orgOrder.getBranchNo(),item.getDispDate()).size()>0)throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单的日计划不能恢复!此日已经生成路单!");
		if("30".equals(orgOrder.getSign()) || !"10".equals(orgOrder.getPreorderStat()) )throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单的日计划不能恢复!");
		if(DateUtil.dateAfter(new Date(),item.getDispDate()))throw new ServiceException(MessageCode.LOGIC_ERROR,"今日之前的日计划无法恢复!");
		if("20".equals(orgOrder.getSign()))throw new ServiceException(MessageCode.LOGIC_ERROR,"已停订的订单不能单日恢复日计划!");
		TMdMaraEx ex = maraExMapper.getProductTransRateByCode(item.getMatnr(),user.getSalesOrg());
		if(ex!=null){
			item.setMatnrTxt(ex.getShortTxt());
		}
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
		OperationLogUtil.saveHistoryOperation(orgOrder.getOrderNo(),LogType.DAIL_ORDER,DailOrderLogEnum.STATUS,null,null,"停订","在订",
				item.getMatnr()+item.getMatnrTxt(),item.getDispDate(),user,operationLogMapper);
		
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
	    System.out.println(order.getOrderNo()+" 退回账户 "+order.getCurAmt());
		tVipCustInfoService.addVipAcct(ac);
	}

	@Override
	public void returnOrderRemainAmtToAcct2(TPreOrder order,Date dispDate)
	{
		//完结日期不是配送那天，非预付款单，剩余金额不大于0, return
		if(order==null)return;
		if(!order.getEndDate().equals(dispDate) || !"20".equals(order.getPaymentmethod()) || order.getCurAmt().floatValue() <= 0)return;

		//退回剩余金额
		TVipAcct ac = new TVipAcct();
		ac.setVipCustNo(order.getMilkmemberNo());
		ac.setAcctAmt(order.getCurAmt());
		System.out.println(order.getOrderNo()+" 退回账户 "+order.getCurAmt());
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
				
				List<TOrderDaliyPlanItem> dateList = new ArrayList<TOrderDaliyPlanItem>();
				daliyPlans.stream()
				.filter((e)->e.getGiftQty()==null)
				.filter((e)->e.getItemNo().equals(orgItem.getOrgItemNo()) )
				.filter((e)->e.getRemainAmt().floatValue()>=0)
				.forEach((e)->{dateList.add(e);});
				//赠品日期调整
				int index = 0;
				for(TOrderDaliyPlanItem p : daliyPlans){
					if(p.getGiftQty()==null || !p.getItemNo().equals(orgItem.getOrgItemNo()) )continue;
					p.setDispDate(dateList.get(index).getDispDate());
					tOrderDaliyPlanItemMapper.updateDaliyPlanItem(p);
					index++;
				}
				
				//重新计算剩余金额
				Collections.reverse(daliyPlans);
				BigDecimal initAmt = order.getInitAmt();
				for(TOrderDaliyPlanItem p : daliyPlans){
					if(p.getGiftQty()!=null)continue;
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

	/* (non-Javadoc) 
	* @title: setOrderToFinish
	* @description: 查看订单是否完结，完结
	* @param orderNo
	* @param dispDate 
	* @see com.nhry.service.order.dao.OrderService#setOrderToFinish(java.lang.String, java.util.Date) 
	*/
	@Override
	public void setOrderToFinish(String orderNo, Date dispDate)
	{
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(orderNo);
		if(order==null)return;
		//如果订单时在订状态 ，并且没有在订的日订单
		if(!"10".equals(order.getSign())) {
			return;
		}else{
			List<TOrderDaliyPlanItem> result =  tOrderDaliyPlanItemMapper.searchDaliyPlansByStatus(order.getOrderNo(),"10",null,null);
			if(result == null || result.size() ==0){
				//订单成完结
				OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.STATUS,null,null,"10".equals(order.getSign())?"在订":"","完结",
						null,null,userSessionService.getCurrentUser(),operationLogMapper);
				tPreOrderMapper.updateOrderToFinish(order.getOrderNo());
				if(tPreOrderMapper.selectNumOfdeletedByMilkmemberNo(order.getMilkmemberNo())<=0){
					//订户状态更改
					tVipCustInfoService.discontinue(order.getMilkmemberNo(), "20",null,null);
				}
			}
		}

	}

	public void setOrderToFinish2(TPreOrder order, Date dispDate)
	{
		if(order==null)return;
		//如果订单时在订状态 ，并且没有在订的日订单
		if(!"10".equals(order.getSign())) {
			return;
		}else{
			List<TOrderDaliyPlanItem> result =  tOrderDaliyPlanItemMapper.searchDaliyPlansByStatus(order.getOrderNo(),"10",null,null);
			if(result == null || result.size() ==0){
				//订单成完结
				OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER,OrderLogEnum.STATUS,null,null,"10".equals(order.getSign())?"在订":"","完结",
						null,null,userSessionService.getCurrentUser(),operationLogMapper);
				tPreOrderMapper.updateOrderToFinish(order.getOrderNo());
				if("20".equals(order.getPaymentmethod()) && order.getCurAmt().floatValue()> 0){
					//退回剩余金额
					TVipAcct ac = new TVipAcct();
					ac.setVipCustNo(order.getMilkmemberNo());
					ac.setAcctAmt(order.getCurAmt());
					System.out.println(order.getOrderNo()+" 退回账户 "+order.getCurAmt());
					tVipCustInfoService.addVipAcct(ac);
				}
				if(tPreOrderMapper.selectNumOfdeletedByMilkmemberNo(order.getMilkmemberNo())==0){
					//订户状态更改
					tVipCustInfoService.discontinue(order.getMilkmemberNo(), "20",null,null);
				}
			}
		}

	}
	//获取订户下未完成的订单数
	@Override
	public int selectUnfinishOrderNum(String vipCustNo) {
		return  tPreOrderMapper.selectUnfinishOrderNum(vipCustNo);
	}
	
	//转换时间,不考虑小时数
	private void convertEntryDate(List<TPlanOrderItem> curEntries){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		curEntries.stream().forEach((e)->{
			try
			{
				if(e.getStartDispDate()!=null)e.setStartDispDate(format.parse(format.format(e.getStartDispDate())));
				if(e.getEndDispDate()!=null)e.setEndDispDate(format.parse(format.format(e.getEndDispDate())));
				if(e.getStopStartDate()!=null)e.setStopStartDate(format.parse(format.format(e.getStopStartDate())));
				if(e.getStopEndDate()!=null)e.setStopEndDate(format.parse(format.format(e.getStopEndDate())));
			}
			catch (Exception e1)
			{
				throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式有问题！");
			}
		});
	}
	//订单行项目克隆
	private void cloneOrderEntry(TPlanOrderItem newEntry, TPlanOrderItem orgEntry){
		newEntry.setGapDays(orgEntry.getGapDays());//间隔天数
		newEntry.setRuleTxt(orgEntry.getRuleTxt());
		newEntry.setRuleType(orgEntry.getRuleType());
		newEntry.setOrderNo(orgEntry.getOrderNo());//订单编号
		newEntry.setOrderDate(orgEntry.getOrderDate());//订单日期
		newEntry.setItemNo(orgEntry.getItemNo());//预订单日计划行
		newEntry.setReachTimeType(orgEntry.getReachTimeType());//送达时段类型
		newEntry.setMatnr(orgEntry.getMatnr());//产品编号
		newEntry.setUnit(orgEntry.getUnit());//配送单位
		newEntry.setQty(orgEntry.getQty());//产品数量
		newEntry.setSalesPrice(orgEntry.getSalesPrice());//产品价格
		newEntry.setPromotion(orgEntry.getPromotion());//促销号
		newEntry.setStartDispDate(orgEntry.getStartDispDate());
		newEntry.setEndDispDate(orgEntry.getEndDispDate());
	}
	
	
	@Override
	public void selectOrdersAndSendMessage() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		Date endDate = afterDate(today,7);
		String todayStr = format.format(today);
		String endStr = format.format(endDate);
		System.out.println("===========执行发送短信接口================");
		if("true".equals(EnvContant.getSystemConst("send_message_flag"))){
//			预付款：
//			尊敬的XX 客户：
//			您本期订奶预计将于7天后到期，我们将于7日内上门收取下期奶款，感谢您的支持！奶站电话：
//			自动触发条件：订单截止日期前7天。
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("sendMessagePrePayOrder");
					List<TPreOrder> list = tPreOrderMapper.searchPrePayOrdersForSendMessage(endStr);
					System.out.println("===========执行发送短信接口==订单数量=============="+list.size());
					list.stream().forEach((e)->{
						String str = "尊敬的" + e.getMilkmemberName() + "客户:您本期订奶预计将于7天后到期，我们将于7日内上门收取下期奶款，感谢您的支持！奶站电话：" + e.getBranchNo();
						smsSendService.sendMessage(str, e.getCustomerTel());
						System.out.println("===========发送短信====pre============"+e.getMilkmemberName()+" == "+e.getCustomerTel());
					});
				}
			});
			
//			后付款：
//			尊敬的XX 客户：
//			您本期订奶共XX瓶，总计XX元，我们将于5日内上门收取本期奶款，感谢您的支持！
//			自动触发条件：订单截止日触发。
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("sendMessageAfPayOrder");
					List<TPreOrder> list = tPreOrderMapper.searchAfPayOrdersForSendMessage(todayStr);
					list.stream().forEach((e)->{
						String str = "尊敬的" + e.getMilkmemberName() + "客户:您本期订奶共" + e.getyGrowth() + "瓶，总计" + e.getInitAmt() + "元，我们将于7日内上门收取本期奶款，感谢您的支持！";
						smsSendService.sendMessage(str, e.getCustomerTel());
						System.out.println("===========发送短信===af============="+e.getMilkmemberName()+" == "+e.getCustomerTel());
					});
				}
			});
			
//			电商订购：
//			尊敬的XX 客户：
//			您本期订奶预计将于5天后到期，请及时续费，感谢您的支持！公司电话：400—xxxxxxxx
//			自动触发条件：订单截止日期前7天。
			taskExecutor.execute(new Thread(){
				@Override
				public void run() {
					super.run();
					this.setName("sendMessageEcOrder");
					List<TPreOrder> list = tPreOrderMapper.searchECOrdersForSendMessage(endStr);
					list.stream().forEach((e)->{
						String str = "尊敬的" + e.getMilkmemberName() + "客户:您本期订奶预计将于5天后到期，请及时续费，感谢您的支持！公司电话：400—88888888";
						smsSendService.sendMessage(str, e.getCustomerTel());
						System.out.println("===========发送短信================"+e.getMilkmemberName()+" == "+e.getCustomerTel());
					});
				}
			});
		}
		
		return;
	}

	/**
	 * 退回非奶站的订单到人工分单列表
	 * 1、判断该订单是否是奶站订单，如果不是，可以进行下一步确认
	 * 2、判断该订单是否有完结的日计划，如果没有，可以进行下一步确认
	 * 3、判断该订单是否有未确认的路单，如果有给出提醒，无确认过的路单需要删除路单，在进行退回操作
	 * 4、退回流程开始
	 * --1）删除该订单日计划
	 * --2）删除该订单收款单（如果有则进行删除）
	 * --3）删除该订单装箱单，（如果有则进行删除）
	 * --4）更新订单状态为人工人分单状态，同时删除该订单的奶站和送奶员信息
	 * end
	 * */
	@Override
	public int backUnBranchOrder(UpdateManHandOrderModel smodel){

		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(smodel.getOrderNo());
		//1、判断该订单是否是奶站订单，如果不是，可以进行下一步确认
		if(order!=null){
			if(order.getPreorderSource().equals("30")){
				throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单下为奶站订单，不能进行退回操作！");
			}else{
				int dairyPlansCounts = tOrderDaliyPlanItemMapper.selectStatusDailyPlansCounts(smodel.getOrderNo());
				int dispOrderCoounts = tDispOrderItemMapper.selectCountsByOrderNo(smodel.getOrderNo());
				//2、判断该订单是否有完结的日计划，如果没有，可以进行下一步确认
				if(dairyPlansCounts>0){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单下有完结的日单，无法进行退回操作！");
				}
				//3、判断该订单是否有未确认的路单，如果有给出提醒，无确认过的路单需要删除路单，在进行退回操作
				else if(dispOrderCoounts>0){
					throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单下未确认的路单，请删除对应的路单，在执行退回操作！");
				}
				//4、退回流程开始
				else{
					//--1）删除该订单日计划
					tOrderDaliyPlanItemMapper.deletePlansByOrder(smodel.getOrderNo());
					//--2）删除该订单收款单（如果有则进行删除）
					TMstRecvBill customerBill = customerBillMapper.getRecBillByOrderNo(smodel.getOrderNo());
					if(customerBill!=null){
						customerBillMapper.delReceipt(customerBill.getReceiptNo());
					}
					//--3）删除该订单装箱单，（如果有则进行删除）
					milkBoxService.deleteMilkBoxByOrderNo(smodel.getOrderNo());
					//--4）更新订单状态为人工人分单状态，同时删除该订单的奶站和送奶员信息
					TSysUser user = userSessionService.getCurrentUser();
					//确认订单退回日志
					OperationLogUtil.saveHistoryOperation(order.getOrderNo(),LogType.ORDER, OrderLogEnum.RETURN_BACK_ORDER,null,null,order.getBranchName(),
							null,null,null,user,operationLogMapper);
					order.setBranchNo("");
					order.setDealerNo("");
					order.setIsValid("N");
					order.setEmpNo("");
					order.setPreorderStat("20");
					order.setMilkboxStat("20");
					order.setRetDate(new Date());
					order.setRetReason(smodel.getRetReason().trim());
					order.setMemoTxt(smodel.getMemoTxt());
					tPreOrderMapper.updateBackOrder(order);
				}
			}
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单不存在，请核实");
		}
		return 1;
	}
}
