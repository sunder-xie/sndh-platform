package com.nhry.rest.order;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.*;
import com.nhry.rest.BaseResource;
import com.nhry.service.order.dao.OrderService;
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

@Path("/order")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/order", description = "订单信息维护")
public class OrderResource extends BaseResource {
	@Autowired
	private OrderService orderService;
	
	@GET
	@Path("/{orderCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{orderCode}", response = TPreOrder.class, notes = "根据订单编号查询订单信息")
	public Response selectOrderByCode(@ApiParam(required=true,name="orderCode",value="订单编号") @PathParam("orderCode") String orderCode){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.selectOrderByCode(orderCode));
	} 
	
	@POST
	@Path("/upt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt", response = String.class, notes = "更新订单信息")
	public Response uptOrder(@ApiParam(required=true,name="record",value="系统参数json格式")TMdMaraEx record){
		return convertToRespModel(MessageCode.NORMAL, null,  null);
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
	@Path("/manHandSearch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/manHandSearch", response = PageInfo.class, notes = "人工分单获取退单订单信息列表")
	public Response searchReturnOrders(@ApiParam(required=true,name="manHandModel",value="ManHandOrderSearchModel") ManHandOrderSearchModel manHandModel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.searchReturnOrders(manHandModel));
	}


	@POST
	@Path("/returnOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/returnOrder", response = PageInfo.class, notes = "订单退回")
	public Response returnOrder(@ApiParam(required=true,name="manHandModel",value="ReturnOrderModel") ReturnOrderModel returnOrderModel){
		return convertToRespModel(MessageCode.NORMAL, null, orderService.returnOrder(returnOrderModel));
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
	@ApiOperation(value = "/manHandSearch", response = Response.class, notes = "人工分单(修改订单的所属奶站)")
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
	

}
