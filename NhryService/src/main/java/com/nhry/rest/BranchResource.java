package com.nhry.rest;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.NHSysParameter;
import com.nhry.exception.MessageCode;
import com.nhry.pojo.query.BranchQueryModel;
import com.nhry.service.dao.BranchService;
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

@Path("/branch")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/branch", description = "网点客户信息(奶站)信息维护")
public class BranchResource extends BaseResource {
	@Autowired
    private BranchService branchService;
    
	@GET
	@Path("/search/{salesOrg}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{salesOrg}", response = NHSysParameter.class, notes = "根据销售组织查询网点客户信息列表")
	public Response findBranchListByOrg(@ApiParam(required=true,name="salesOrg",value="销售组织")  @PathParam("salesOrg") String salesOrg){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.findBranchListByOrg(salesOrg));
	}

	@GET
	@Path("/getBranchInfo/{branchNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{branchNo}", response = NHSysParameter.class, notes = "根据网点编号查询网点客户信息列表")
	public Response getBranchByNo(@ApiParam(required=true,name="branchNo",value="网点客户编号")  @PathParam("branchNo") String branchNo){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.selectBranchByNo(branchNo));
	}


	@POST
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/", response = NHSysParameter.class, notes = "根据销售组织、奶站性质、奶站等级查询网点客户(奶站)信息列表")
	public Response findBranchListByPage(
			@ApiParam(required=true,name="branchModel",value="SearchModel") BranchQueryModel branchModel){
		PageInfo data = branchService.findBranchListByPage(branchModel);
		return convertToRespModel(MessageCode.NORMAL, null,data);
	}
}
