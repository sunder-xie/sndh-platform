package com.nhry.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.nhry.common.model.ResponseModel;
import com.nhry.domain.NHSysParameter;
import com.nhry.domain.TMdBranchEmp;
import com.nhry.domain.model.SearchModel;
import com.nhry.exception.MessageCode;
import com.nhry.service.dao.BranchEmpService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/emp")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/emp", description = "网点员工信息(奶站)维护")
public class BranchEmpResource extends BaseResource {
	@Autowired
	private BranchEmpService branchEmpService;
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = ResponseModel.class, notes = "增加网点员工")
	public Response addBranchEmp(@ApiParam(required=true,name="record",value="系统参数json格式")TMdBranchEmp record){
		return convertToRespModel(MessageCode.NORMAL, null,  branchEmpService.addBranchEmp(record));
	}
	
	@POST
	@Path("/upt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt", response = String.class, notes = "修改网点员工信息")
	public Response uptBranchEmp(@ApiParam(required=true,name="record",value="系统参数json格式")TMdBranchEmp record){
		return convertToRespModel(MessageCode.NORMAL, null,  branchEmpService.uptBranchEmpByNo(record));
	}
	
	@POST
	@Path("/del")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/del", response = String.class, notes = "删除员工信息")
	public Response delBranchEmp(@ApiParam(required=true,name="empNo",value="员工编号")String empNo){
		return convertToRespModel(MessageCode.NORMAL, null,  branchEmpService.deleteBranchEmpByNo(empNo));
	}
	
	@GET
	@Path("/{empNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{empNo}", response = NHSysParameter.class, notes = "根据员工编号查询员工信息")
	public Response findBranchEmpByNo(@ApiParam(required=true,name="empNo",value="员工编号") @PathParam("empNo") String empNo){
		return convertToRespModel(MessageCode.NORMAL, null,branchEmpService.selectBranchEmpByNo(empNo));
	} 
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{search}", response = NHSysParameter.class, notes = "查询员工信息")
	public Response findBranchEmpByNo(@ApiParam(required=true,name="smodel",value="SearchModel") SearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null,branchEmpService.searchBranchEmp(smodel));
	} 
	
}
