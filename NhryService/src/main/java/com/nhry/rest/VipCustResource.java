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

import com.github.pagehelper.PageInfo;
import com.nhry.domain.TMdVipCustInfo;
import com.nhry.exception.MessageCode;
import com.nhry.service.dao.TMdVipCustInfoService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/vipcust")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/vipcust", description = "订户信息维护")
public class VipCustResource extends BaseResource {
	@Autowired
	private TMdVipCustInfoService custService;

	@GET
	@Path("/{vipCustNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{vipCustNo}",response = TMdVipCustInfo.class, notes = "根据会员编号获取会员信息")
	public Response findVipCust(@ApiParam(required=true,name="vipCustNo",value="会员编号") @PathParam("vipCustNo") String vipCustNo) {
		return formatData(MessageCode.NORMAL, null,custService.selectByPrimaryKey(vipCustNo));
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = String.class, notes = "添加会员信息")
	public Response addVipCust(@ApiParam(required=true,name="cust",value="会员信息json格式")TMdVipCustInfo cust) {
		return formatData(MessageCode.NORMAL, null,custService.insert(cust));
	}
	
	@GET
	@Path("/find/phone/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/phone/{phone}", response = String.class, notes = "根据电话号码查询会员信息")
	public Response findCustByPhone(@ApiParam(required=true,name="phone",value="电话号码") @PathParam("phone")String phone) {
		return formatData(MessageCode.NORMAL, null,custService.findCustByPhone(phone));
	}
	
	@POST
	@Path("/delete/{vipCustNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/delete/{vipCustNo}", response = String.class, notes = "删除会员信息")
	public Response delVipCust(@ApiParam(required=true,name="vipCustNo",value="会员编号") @PathParam("vipCustNo") String vipCustNo) {
		return formatData(MessageCode.NORMAL, null,custService.deleteByPrimaryKey(vipCustNo));
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/all", response = String.class, notes = "获取所有的会员信息")
	public Response all() {
		return formatData(MessageCode.NORMAL, null,custService.allCust());
	} 
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/update", response = String.class, notes = "修改会员信息")
	public Response uptCust(@ApiParam(required=true,name="cust",value="会员信息json格式")TMdVipCustInfo cust) {
		return formatData(MessageCode.NORMAL, null,custService.updateByPrimaryKey(cust));
	}

}
