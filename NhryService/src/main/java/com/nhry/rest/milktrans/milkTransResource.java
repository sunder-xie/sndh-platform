package com.nhry.rest.milktrans;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrderItem;
import com.nhry.model.milktrans.*;
import com.nhry.rest.BaseResource;
import com.nhry.service.milk.dao.DeliverMilkService;
import com.nhry.service.milktrans.dao.RequireOrderService;
import com.nhry.service.milktrans.dao.ReturnBoxService;
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

@Path("/milkTrans")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/milkTrans", description = "送奶管理")
public class milkTransResource extends BaseResource {
	@Autowired
	private RequireOrderService requireOrderService;
	@Autowired
	private ReturnBoxService returnBoxService;
	@Autowired
	private DeliverMilkService deliverMilkService;

	@POST
	@Path("/creatRequireOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/creatRequireOrder", response = RequireOrderModel.class, notes = "生成要货计划")
	public Response creatRequireOrder(){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.creatRequireOrder());
	}

	@POST
	@Path("/queryRequireOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/queryRequireOrder", response = RequireOrderModel.class, notes = "查询奶站下某一天的要货计划")
	public Response searchRequireOrder(@ApiParam(required=true,name="eSearch",value="要货计划日期") ReqGoodsOrderSearch eSearch) {

		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.searchRequireOrder(eSearch.getRequiredDate()));
	}

	@POST
	@Path("/uptRequireOrderItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptRequireOrderItem", response = Response.class, notes = "更新生成的要货计划行")
	public Response uptRequireOrderItem(@ApiParam(required=true,name="rModel",value="要货计划") UpdateRequiredModel  uModel){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.uptRequireOrder(uModel));
	}



	@POST
	@Path("/uptNewRequireOrderItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptNewRequireOrderItem", response = Response.class, notes = "更新新添加的要货计划行")
	public Response uptNewRequireOrderItem(@ApiParam(required=true,name="rModel",value="要货计划") UpdateNewRequiredModel  uModel){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.uptNewRequireOrderItem(uModel));
	}

	@POST
	@Path("/addRequireOrderItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/addRequireOrderItem", response = Response.class, notes = "添加新的生成要货计划行")
	public Response addRequireOrderItem(@ApiParam(required=true,name="item",value="要货计划") TSsmReqGoodsOrderItem item){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.addRequireOrderItem(item));
	}



	@POST
	@Path("/delRequireOrderItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/delRequireOrderItem", response = Response.class, notes = "删除新添加的生成要货计划行")
	public Response delRequireOrderItem(@ApiParam(required=true,name="dModel",value="要货计划") ReqGoodsOrderItemSearch  item){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.delRequireOrderItem(item));
	}


	@POST
	@Path("/box/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/box/search", response = PageInfo.class, notes = "查询回瓶管理列表")
	public Response searchBoxPage(@ApiParam(required=true,name="cModel",value="cModel") BoxSearch bsearch){
		return convertToRespModel(MessageCode.NORMAL, null, returnBoxService.searchBoxPage(bsearch));
	}

	@POST
	@Path("/box/upt")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/box/upt", response = Response.class, notes = "录入回瓶管理")
	public Response uptBoxRetrun(@ApiParam(required=true,name="cModel",value="cModel") CreateReturnBoxModel boxModel){
		return convertToRespModel(MessageCode.NORMAL, null, returnBoxService.uptBoxRetrun(boxModel));
	}

	@POST
	@Path("/box/createDayRetBox")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/box/createDayRetBox", response = CreateReturnBoxModel.class, notes = "生成当天(员工)需要回瓶的数据")
	public Response createDayRetBox(@ApiParam(required=true,name="cModel",value="cModel") CreateEmpReturnboxModel cModel){
		return convertToRespModel(MessageCode.NORMAL, null, returnBoxService.createDayRetBox(cModel));
	}






	@GET
	@Path("/createInsideSalOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/createInsideSalOrder", response = RequireOrderModel.class, notes = "创建内部销售订单")
	public Response createInsideSalOrder(@ApiParam(required=true,name="dispOrderNo",value="配送单号")@QueryParam("dispOrderNo") String  dispOrderNo){
		return convertToRespModel(MessageCode.NORMAL, null, deliverMilkService.createInsideSalOrder(dispOrderNo));
	}






}
