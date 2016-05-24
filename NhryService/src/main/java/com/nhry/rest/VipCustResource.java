package com.nhry.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.nhry.exception.MessageCode;
import com.nhry.service.dao.TMdVipCustInfoService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;

@Path("/vipcust")
@Component
@Scope("request")
@Singleton
@Controller
public class VipCustResource extends BaseResource {
	@Autowired
	private TMdVipCustInfoService custService;

	@GET
	@Path("/{vipCustNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findVipCust(@PathParam("vipCustNo") String vipCustNo) {
		return formatData(MessageCode.NORMAL, null,custService.selectByPrimaryKey(vipCustNo));
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addVipCust(JSONObject json) {
		return formatData(MessageCode.NORMAL, null,custService.insert(json));
	}
	
	@POST
	@Path("/delete/{vipCustNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delVipCust(@PathParam("vipCustNo") String vipCustNo) {
		return formatData(MessageCode.NORMAL, null,custService.deleteByPrimaryKey(vipCustNo));
	}

}
