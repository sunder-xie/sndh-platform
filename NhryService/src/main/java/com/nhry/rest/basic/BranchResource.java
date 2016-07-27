package com.nhry.rest.basic;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.model.basic.BranchQueryModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.BranchService;
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
	@Path("/searchBySalesOrg")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/searchBySalesOrg", response = TMdBranch.class, notes = "根据销售组织查询网点客户信息列表")
	public Response findBranchListByOrg(){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.findBranchListByOrg());
	}

	@GET
	@Path("/getBranchByCodeOrName")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getBranchByCodeOrName", response = TMdBranch.class, notes = "根据奶站名称或者名称获取销售组织下奶站列表")
	public Response getBranchByCodeOrName(@ApiParam(required=true,name="branch",value="奶站编号或奶站名称")  @QueryParam("branch") String branch){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.getBranchByCodeOrName(branch));
	}

	@GET
	@Path("/getBranchInfo/{branchNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getBranchInfo/{branchNo}", response = TMdBranch.class, notes = "根据网点编号查询网点客户信息列表")
	public Response getBranchByNo(@ApiParam(required=true,name="branchNo",value="网点客户编号")  @PathParam("branchNo") String branchNo){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.selectBranchByNo(branchNo));
	}

	@GET
	@Path("/getCustBranchInfo")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getCustBranchInfo", response = TMdBranch.class, notes = "获取当前登录人所属奶站")
	public Response getCustBranchInfo(){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.getCustBranchInfo());
	}


	@POST
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/list", response = TMdBranch.class, notes = "奶站编号、奶站性质查询网点客户(奶站)信息列表")
	public Response findBranchListByPage(
			@ApiParam(required=true,name="branchModel",value="SearchModel") BranchQueryModel branchModel){
		PageInfo data = branchService.findBranchListByPage(branchModel);
		return convertToRespModel(MessageCode.NORMAL, null,data);
	}

	@POST
	@Path("/upt")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt", response = String.class, notes = "更新网点客户(奶站)信息列表")
	public Response uptBranch(@ApiParam(required=true,name="branchModel",value="SearchModel") TMdBranch tMdBranch){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.updateBranch(tMdBranch));
	}

	@GET
	@Path("/getInfoByType")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getInfoByType", response = Response.class, notes = "自营下返回可选奶站，外包下返回可选经销商")
	public Response getInfoByType(
			@ApiParam(required=true,name="type",value="01（代表选择自营） 02（代表选择外包）")@QueryParam("type") String type){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.findResultByType(type));
	}
	
	@POST
	@Path("/find/{dealerNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/{deanerNo}", response = String.class, notes = "根据经销商编号获取奶站列表信息")
	public Response uptBranch(@ApiParam(required=true,name="dealerNo",value="经销商编号(自有奶站时：-1)")@PathParam("dealerNo") String dealerNo){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.findBranchByDno(dealerNo));
	}




}
