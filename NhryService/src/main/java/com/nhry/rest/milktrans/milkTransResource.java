package com.nhry.rest.milktrans;

import com.nhry.common.exception.MessageCode;
import com.nhry.model.milktrans.RequireOrderModel;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.rest.BaseResource;
import com.nhry.service.milktrans.dao.RequireOrderService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

	@POST
	@Path("/creatRequireOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/creatRequireOrder", response = RequireOrderModel.class, notes = "生成要货计划")
	public Response creatRequireOrder(@ApiParam(required=true,name="rModel",value="要货计划") RequireOrderModel  rModel){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.creatRequireOrder(rModel));
	}

	@POST
	@Path("/queryRequireOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/creatRequireOrder", response = RequireOrderModel.class, notes = "查询要货计划")
	public Response searchRequireOrder(@ApiParam(required=true,name="rModel",value="要货计划") RequireOrderSearch rModel){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.searchRequireOrder(rModel));
	}

	@POST
	@Path("/uptRequireOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/uptRequireOrder", response = RequireOrderModel.class, notes = "更新生成要货计划")
	public Response uptRequireOrder(@ApiParam(required=true,name="rModel",value="要货计划") RequireOrderModel  rModel){
		return convertToRespModel(MessageCode.NORMAL, null, requireOrderService.uptRequireOrder(rModel));
	}

}
