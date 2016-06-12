package com.nhry.rest.basic;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.exception.MessageCode;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.pojo.ResidentialAreaModel;
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

@Path("/residentialArea")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/residentialArea", description = "小区信息（配送区域）维护")
public class ResidentialAreaResource extends BaseResource {
	@Autowired
	private ResidentialAreaService residentialAreaService;

	@GET
	@Path("/getAreaByBranchNo/{branchNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getAreaByBranchNo/{branchNo}", response = TMdResidentialArea.class, notes = "根据网点客户信息号获取配送区域(小区)列表")
	public Response getAreaByBranchNo(
			@ApiParam(required = true, name = "branchNo", value = "网店客户信息号(奶站编号)") @PathParam("branchNo") String branchNo) {
		return convertToRespModel(MessageCode.NORMAL, null,
				residentialAreaService.getAreaByBranchNo(branchNo));
	}

	@GET
	@Path("/getAreaById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getAreaById/{id}", response = TMdResidentialArea.class, notes = "小区编号获取配送区域(小区)列表")
	public Response selectById(
			@ApiParam(required = true, name = "id", value = "小区编号") @PathParam("id") String id) {
		return convertToRespModel(MessageCode.NORMAL, null,
				residentialAreaService.selectById(id));
	}


	@POST
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/list", response = TMdResidentialArea.class, notes = "根据销售组织、奶站性质、奶站等级查询网点客户(奶站)信息列表")
	public Response findAreaListByPage(
			@ApiParam(required=true,name="branchModel",value="SearchModel") ResidentialAreaModel residentialAreaModel){
		PageInfo data = residentialAreaService.findAreaListByPage(residentialAreaModel);
		return convertToRespModel(MessageCode.NORMAL, null,data);
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = String.class, notes = "更新商品信息")
	public Response addResidentialArea(@ApiParam(required=true,name="residentialAreaModel",value="系统参数json格式")ResidentialAreaModel residentialAreaModel){
		return convertToRespModel(MessageCode.NORMAL, null,  residentialAreaService.addResidentialArea(residentialAreaModel));
	}



}
