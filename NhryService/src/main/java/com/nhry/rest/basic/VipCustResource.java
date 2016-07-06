package com.nhry.rest.basic;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.model.basic.CustQueryModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.TVipCustInfoService;
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
	private TVipCustInfoService custService;
	@Autowired
	private UserSessionService userSessionService;

	@POST
	@Path("/{vipCustNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{vipCustNo}",response = TVipCustInfo.class, notes = "根据订户编号获取会员信息")
	public Response findVipCust(@ApiParam(required=true,name="vipCustNo",value="会员编号") @PathParam("vipCustNo") String vipCustNo) {
		return convertToRespModel(MessageCode.NORMAL, null,custService.findVipCustByNo(vipCustNo));
	}
	
	@POST
	@Path("/upt/cust")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt/cust",response = ResponseModel.class, notes = "修改订户信息")
	public Response findVipCustForUpt(@ApiParam(required=true,name="vipCustNo",value="订户信息对象") TVipCustInfo cust) {
		return convertToRespModel(MessageCode.NORMAL, null,custService.updateVipCustByNo(cust));
	}

	@POST
	@Path("/add/cust")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add/cust", response = ResponseModel.class, notes = "添加订户信息")
	public Response addVipCust(@ApiParam(required=true,name="cust",value="订户信息对象")TVipCustInfo cust) {
		return convertToRespModel(MessageCode.NORMAL, null,custService.addVipCust(cust));
	}
	
	@POST
	@Path("/find/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/{phone}", response = TVipCustInfo.class, notes = "根据电话号码查询订户信息")
	public Response findCustByPhone(@ApiParam(required=true,name="phone",value="电话号码") @PathParam("phone")String phone) {
		if(!StringUtils.isEmpty(userSessionService.getCurrentUser().getBranchNo())){
			//奶站用户
			Map<String,String> attrs = new HashMap<String,String>();
			attrs.put("branchNo", this.userSessionService.getCurrentUser().getBranchNo());
			attrs.put("phone",phone);
			return convertToRespModel(MessageCode.NORMAL, null,custService.findStaCustByPhone(attrs));
		}
		//公司用户
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("branchNo", this.userSessionService.getCurrentUser().getSalesOrg());
		attrs.put("phone",phone);
		return convertToRespModel(MessageCode.NORMAL, null,custService.findCompanyCustByPhone(attrs));
	}
	
	@POST
	@Path("/add/address")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add/address", response = ResponseModel.class, notes = "为订户信息添加地址信息")
	public Response addAdress(@ApiParam(required=true,name="address",value="地址信息对象") TMdAddress address) {
	  return convertToRespModel(MessageCode.NORMAL, null,custService.addAddressForCust(address));
	}
	
	@POST
	@Path("/upt/address")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt/address", response = ResponseModel.class, notes = "修改订户详细地址信息")
	public Response addAddressForCust(@ApiParam(required=true,name="address",value="地址信息对象") TMdAddress address) {
	  return convertToRespModel(MessageCode.NORMAL, null,custService.uptCustAddress(address));
	}
	
	@POST
	@Path("/find/cust")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/cust", response = PageInfo.class, notes = "根据奶站编号、征订时间等内容查询订户信息")
	public Response findcustMixedTerms(@ApiParam(required=true,name="cust",value="订户查询对象") CustQueryModel cust) {
	  return convertToRespModel(MessageCode.NORMAL, null,custService.findcustMixedTerms(cust));
	}
	
	@POST
	@Path("/find/address/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/address/{id}", response = PageInfo.class, notes = "根据地址编号获取地址详细信息")
	public Response findAddressById(@ApiParam(required=true,name="id",value="地址编号id")@PathParam("id")String id) {
	  return convertToRespModel(MessageCode.NORMAL, null,custService.findAddressDetailById(id));
	}
	
	@POST
	@Path("/find/cust/address/{custNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/cust/address/{custNo}", response = PageInfo.class, notes = "根据订户编号获取地址列表信息")
	public Response findCnAddressByCustNo(@ApiParam(required=true,name="custNo",value="订户编号")@PathParam("custNo")String custNo) {
	  return convertToRespModel(MessageCode.NORMAL, null,custService.findCnAddressByCustNo(custNo));
	}
	
	@POST
	@Path("/find/cust/acct/{custNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/cust/address/{custNo}", response = PageInfo.class, notes = "根据订户编号查询订户的资金订户信息")
	public Response findVipAcctByCustNo(@ApiParam(required=true,name="custNo",value="订户编号")@PathParam("custNo")String custNo) {
	  return convertToRespModel(MessageCode.NORMAL, null,custService.findVipAcctByCustNo(custNo));
	}
}
