package com.nhry.rest.milk;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.milk.dao.DeliverMilkService;
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

@Path("/deliverMilk")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/deliverMilk", description = "送奶信息维护")
public class DeliverMilkResource extends BaseResource {
	@Autowired
	private DeliverMilkService deliverMilkService;
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search", response = PageInfo.class, notes = "查询路单信息列表")
	public Response findRouteOrders(@ApiParam(required=true,name="smodel",value="SearchModel") RouteOrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, deliverMilkService.searchRouteOrders(smodel));
	}
	
}
