package com.nhry.rest.order;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.basic.OrderModel;
import com.nhry.model.order.*;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.TSysMessageService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.service.order.pojo.OrderRemainData;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/order")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/order", description = "订单信息维护")
public class OrderResource extends BaseResource {
	@Autowired
	private OrderService orderService;
	@Autowired
	private TSysMessageService messService;
	
	@GET
	@Path("/{orderCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{orderCode}", response = OrderCreateModel.class, notes = "根据订单编号查询订单信息")
	public Response selectOrderByCode(@ApiParam(required=true,name="orderCode",value="订单编号") @PathParam("orderCode") String orderCode){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.selectOrderByCode(orderCode));
	}
	
	@GET
	@Path("/createDaliyPlansForIniOrders/{str}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{str}", response = OrderCreateModel.class, notes = "为导入的订单生成日计划")
	public Response createDaliyPlansForIniOrders(@ApiParam(required=true,name="str",value="模糊字符串,订单号前5位") @PathParam("str") String str){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.createDaliyPlansForIniOrders(str));
	}
	
	@GET
	@Path("/selectLatestOrder/{vipNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/selectLatestOrder/{vipNo}", response = OrderCreateModel.class, notes = "查询该用户上一张订单的送奶员和订单号")
	public Response selectLatestOrder(@ApiParam(required=true,name="vipNo",value="订户编号") @PathParam("vipNo") String vipNo){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.selectLatestOrder(vipNo));
	}
	
	@GET
	@Path("/selectRequiredOrderNum")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/selectRequiredOrderNum", response = OrderCreateModel.class, notes = "该组织下待确认的订单")
	public Response selectRequiredOrderNum(){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.selectRequiredOrderNum());
	}

	@GET
	@Path("/searchReturnOrdersNum")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/searchReturnOrdersNum", response = OrderCreateModel.class, notes = "该组织下人工分单")
	public Response searchReturnOrdersNum(){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchReturnOrdersNum());
	}

	@GET
	@Path("/selectStopOrderNum")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/selectStopOrderNum", response = OrderCreateModel.class, notes = "该组织下还有7天就到期没续订的订单")
	public Response selectStopOrderNum(){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.selectStopOrderNum());
	}
	
	@POST
	@Path("/searchNeedResumeOrders")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/searchNeedResumeOrders", response = PageInfo.class, notes = "查询需要续订的订单信息列表")
	public Response searchNeedResumeOrders(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchNeedResumeOrders(smodel));
	}

	@GET
	@Path("/queryCollectByOrderNo")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/queryCollectByOrderNo", response = CollectOrderModel.class, notes = "根据订单编号查询收款信息")
	public Response queryCollectByOrderNo(@ApiParam(required=true,name="orderCode",value="订单编号") @QueryParam("orderCode") String orderCode){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.queryCollectByOrderNo(orderCode));
	}


	@GET
	@Path("/daliyPlans/{orderCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/daliyPlans/{orderCode}", response = ArrayList.class, notes = "根据订单编号查询订单日计划信息")
	public Response selectDaliyPlansByOrderNo(@ApiParam(required=true,name="orderCode",value="订单编号") @PathParam("orderCode") String orderCode){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.selectDaliyPlansByOrderNo(orderCode));
	}
	
	@GET
	@Path("/findDaliyPlansByStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findDaliyPlansByStatus", response = ArrayList.class, notes = "根据订单编号,日单状态查询订单日计划信息")
	public Response findDaliyPlansByStatus(@ApiParam(required=true,name="orderNo",value="订单号") @QueryParam("orderNo") String orderNo,
			@ApiParam(required=false,name="status1",value="日单生成状态10") @QueryParam("status1") String status1,
			@ApiParam(required=false,name="status2",value="日单确认状态20") @QueryParam("status2") String status2,
			@ApiParam(required=false,name="status3",value="日单停订状态30") @QueryParam("status3") String status3){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchDaliyPlansByStatus(orderNo,status1,status2,status3));
	}
	
	@POST
	@Path("/daliyPlansByPage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/daliyPlansByPage", response = PageInfo.class, notes = "查询日计划信息列表")
	public Response daliyPlansByPage(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchDaliyOrders(smodel));
	}
	
	@GET
	@Path("/searchOrderRemain/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/searchOrderRemain/{phone}", response = OrderRemainData.class, notes = "根据电话号码查询为送达数量和总共消费金额")
	public Response searchOrderRemain(@ApiParam(required=true,name="phone",value="电话号码") @PathParam("phone") String phone){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchOrderRemainData(phone));
	}
	
	@POST
	@Path("/resumeFromStop")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/resumeFromStop", response = Integer.class, notes = "将停订的订单复订")
	public Response resumeFromStop(@ApiParam(required=true,name="record",value="系统参数json格式") OrderSearchModel record){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.continueOrdeAfterStop(record));
	}
	
	@POST
	@Path("/uptlong")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptlong", response = Integer.class, notes = "更新订单信息(长期修改)")
	public Response uptOrder(@ApiParam(required=true,name="record",value="系统参数json格式") OrderEditModel record){
		return convertToRespModel(MessageCode.NORMAL, null,  orderService.uptOrderlong(record));
	}


	@POST
	@Path("/uptOrderlong")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptOrderlong", response = Integer.class, notes = "更新订单信息(长期修改)")
	public Response uptOrderlong(@ApiParam(required=true,name="record",value="系统参数json格式") OrderEditModel record){
		return convertToRespModel(MessageCode.NORMAL, null,  orderService.uptOrderlong(record));
	}

	@POST
	@Path("/editOrderForLongForViewPlans")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/editOrderForLongForViewPlans", response = List.class, notes = "更新订单信息(长期修改),看日计划")
	public Response editOrderForLongForViewPlans(@ApiParam(required=true,name="record",value="系统参数json格式") OrderEditModel record){
		return convertToRespModel(MessageCode.NORMAL, null,  orderService.editOrderForLongForViewPlans(record));
	}
	
	@POST
	@Path("/uptshort")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptshort", response = Integer.class, notes = "更新订单信息(短期修改)")
	public Response uptOrder(@ApiParam(required=true,name="record",value="系统参数json格式") DaliyPlanEditModel record){
		return convertToRespModel(MessageCode.NORMAL, null,  orderService.editOrderForShort(record));
	}
	
	@POST
	@Path("/recoverDaliyPlan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/recoverDaliyPlan", response = Integer.class, notes = "日计划恢复")
	public Response recoverDaliyPlan(@ApiParam(required=true,name="record",value="系统参数json格式") TOrderDaliyPlanItem plan){
		return convertToRespModel(MessageCode.NORMAL, null,  orderService.recoverStopDaliyDaliyPlan(plan));
	}
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search", response = PageInfo.class, notes = "查询订单信息列表")
	public Response findOrders(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchOrders(smodel));
	}
	
	@POST
	@Path("/batchStopOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/batchStopOrder", response = Integer.class, notes = "订单批量停订")
	public Response batchStopOrder(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.batchStopOrderForTime(smodel));
	}
	
	@POST
	@Path("/batchContinueOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/batchContinueOrder", response = Integer.class, notes = "订单批量续订")
	public Response batchContinueOrder(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.batchContinueOrder(smodel));
	}
	
//	@POST
//	@Path("/batchResumeFromStop")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "/batchResumeFromStop", response = Integer.class, notes = "订单批量复订")
//	public Response batchResumeFromStop(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
//		return convertToRespModel(MessageCode.NORMAL, null, orderService.batchContinueOrdeAfterStop(smodel));
//	}
	
	@POST
	@Path("/stopOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/stopOrder", response = Integer.class, notes = "订单停订(直接停订，没有结束日期)")
	public Response stopOrder(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.stopOrderForTime(smodel));
	}
	
	@POST
	@Path("/stopOrderInTime")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/stopOrderInTime", response = Integer.class, notes = "订单停订(区间暂停，有开始和结束日期)")
	public Response stopOrderInTime(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.stopOrderInTime(smodel));
	}
	
	@POST
	@Path("/backOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/backOrder", response = Integer.class, notes = "订单退订，reason退订原因")
	public Response backOrder(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.backOrder(smodel));
	}
	
	@POST
	@Path("/continueOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/continueOrder", response = Integer.class, notes = "订单续订")
	public Response continueOrder(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.continueOrder(smodel));
	}
	
	@POST
	@Path("/calculateContinueOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/calculateContinueOrder", response = OrderSearchModel.class, notes = "订单续订计算截止和续费")
	public Response calculateContinueOrder(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.calculateContinueOrder(smodel));
	}

	@POST
	@Path("/manHandSearch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/manHandSearch", response = PageInfo.class, notes = "人工分单获取退单订单信息列表")
	public Response searchReturnOrders(@ApiParam(required=true,name="manHandModel",value="ManHandOrderSearchModel") ManHandOrderSearchModel manHandModel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchReturnOrders(manHandModel));
	}

	@POST
	@Path("/searchPendingConfirmUnOnline")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/searchPendingConfirmUnOnline", response = PageInfo.class, notes = "人工分单获取退单订单信息列表")
	public Response searchPendingConfirmUnOnline(@ApiParam(required=true,name="manHandModel",value="ManHandOrderSearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchPendingConfirmUnOnline(smodel));
	}

	@POST
	@Path("/searchPendingConfirmOnline")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/searchPendingConfirmOnline", response = PageInfo.class, notes = "查询订单信息列表")
	public Response searchPendingConfirmOnline(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchPendingConfirmOnline(smodel));
	}


	@POST
	@Path("/returnOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/returnOrder", response = PageInfo.class, notes = "待确认订单退回")
	public Response returnOrder(@ApiParam(required=true,name="manHandModel",value="uptManHandModel") UpdateManHandOrderModel uptManHandModel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.returnOrder(uptManHandModel));
	}


	@POST
	@Path("/orderConfirm")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/orderConfirm", response = PageInfo.class, notes = "待确认订单确认")
	public Response orderConfirm(@ApiParam(required=true,name="uptManHandModel",value="uptManHandModel") UpdateManHandOrderModel uptManHandModel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.orderConfirm(uptManHandModel));
	}


	@POST
	@Path("/orderConfirmUnOnline")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/orderConfirmUnOnline", response = PageInfo.class, notes = "待确认订单确认")
	public Response orderConfirmUnOnline(@ApiParam(required=true,name="uptManHandModel",value="uptManHandModel") UpdateManHandOrderModel uptManHandModel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.orderConfirmUnOnline(uptManHandModel));
	}



	@POST
	@Path("/manHandOrderDetail/{orderCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/manHandSearch", response = PageInfo.class, notes = "根据订单号查看退单信息")
	public Response manHandOrderDetail(@ApiParam(required=true,name="orderCode",value="订单号") @PathParam("orderCode")String orderCode){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.manHandOrderDetail(orderCode));
	}

	@POST
	@Path("/uptManHandOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptManHandOrder", response = Response.class, notes = "人工分单(修改订单的所属奶站)")
	public Response uptManHandOrder(@ApiParam(required=true,name="uptManHandModel",value="奶站号和订单号对象") UpdateManHandOrderModel uptManHandModel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.uptManHandOrder(uptManHandModel));
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/create", response = Integer.class, notes = "生成预订单")
	public Response createOrder(@ApiParam(required=true,name="smodel",value="OrderCreateModel") OrderCreateModel record){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.createOrder(record));
	}
	
	@POST
	@Path("/uptOrderStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptOrderStatus", response = Integer.class, notes = "修改订单,付款，奶箱状态")
	public Response uptOrderStatus(@ApiParam(required=true,name="smodel",value="OrderCreateModel") TPreOrder record){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.modifyOrderStatus(record));
	}

	@POST
	@Path("/canOrderUnsubscribe")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/canOrderUnsubscribe", response = Integer.class, notes = "判断当前订单 是否可以退订")
	public Response orderUnsubscribe(@ApiParam(required=true,name="orderNo",value="订单编号") String  orderNo){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.canOrderUnsubscribe(orderNo));
	}

	@POST
	@Path("/calculateAmtAndEndDateForFront")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/calculateAmtAndEndDateForFront", response = TPlanOrderItem.class, notes = "计算预付款订单行的行金额，截止日期")
	public Response calculateAmtAndEndDateForFront(@ApiParam(required=true,name="item",value="订单行json") TPlanOrderItem  item){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.calculateAmtAndEndDateForFront(item));
	}
	
	@POST
	@Path("/calculateQtyAndAmtForFront")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/calculateQtyAndAmtForFront", response = TPlanOrderItem.class, notes = "计算后付款订单行的行金额，数量")
	public Response calculateQtyAndAmtForFront(@ApiParam(required=true,name="item",value="订单行json") TPlanOrderItem  item){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.calculateTotalQtyForFront(item));
	}
	
	@POST
	@Path("/memo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/memo", response = ResponseModel.class, notes = "更新订单备注信息")
	public Response uptOrderComments(@ApiParam(required=true,name="OrderModel",value="订单备注对象") OrderModel  orderModel){
		return convertToRespModel(MessageCode.NORMAL, null, messService.sendOrderMemo(orderModel));
	}
	
	@POST
	@Path("/updateOrderEmp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/updateOrderEmp", response = Integer.class, notes = "订单换送奶员,empNo:被替换的员工编号，content:替换员工")
	public Response updateOrderEmp(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.replaceOrdersDispmember(smodel));
	}
	
	@POST
	@Path("/viewDaliyPlans")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/viewDaliyPlans", response = Integer.class, notes = "预览日计划")
	public Response viewDaliyPlans(@ApiParam(required=true,name="smodel",value="OrderCreateModel") OrderCreateModel record){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.viewDaliyPlans(record));
	}

	@GET
	@Path("/selectUnfinishOrderNum")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/selectUnfinishOrderNum", response = Integer.class, notes = "判断该订户是否有未完成的订单")
	public Response selectUnfinishOrderNum(@ApiParam(required=true,name="vipCustNo",value="订户号") @QueryParam("vipCustNo") String vipCustNo){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.selectUnfinishOrderNum(vipCustNo));
	}
	@POST
	@Path("/advanceBackOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/advanceBackOrder", response = Integer.class, notes = "提前退订订单")
	public Response advanceBackOrder(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL,null,orderService.advanceBackOrder(smodel));
	}

	@POST
	@Path("/updateBackState")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/advanceBackOrder", response = Integer.class, notes = "执行更新提前退订订单状态")
	public Response updateBackState(){
		return convertToRespModel(MessageCode.NORMAL,null,orderService.updateBackState());
	}
}
