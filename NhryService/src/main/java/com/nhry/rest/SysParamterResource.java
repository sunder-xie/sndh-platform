package com.nhry.rest;

import javax.ws.rs.Consumes;
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
import com.nhry.exception.MessageCode;
import com.nhry.service.dao.SysParamService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/sys/param")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/sys/param", description = "系统参数信息维护")
public class SysParamterResource extends BaseResource {
	@Autowired
	private SysParamService sysParamService;
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = ResponseModel.class, notes = "增加系统参数")
	public Response addSysParam(@ApiParam(required=true,name="param",value="系统参数json格式")NHSysParameter param){
		return convertToRespModel(MessageCode.NORMAL, null,  sysParamService.insert(param));
	}
	
	@POST
	@Path("/upt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt", response = String.class, notes = "修改系统参数")
	public Response uptSysParam(@ApiParam(required=true,name="param",value="系统参数json格式")NHSysParameter param){
		return convertToRespModel(MessageCode.NORMAL, null,  sysParamService.uptSysParamByCode(param));
	}
	
	@POST
	@Path("/del")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "/del", response = String.class, notes = "删除系统参数")
	public Response delSysParam(@ApiParam(required=true,name="param",value="系统参数json格式")NHSysParameter param){
		return convertToRespModel(MessageCode.NORMAL, null,  sysParamService.uptSysParamByCode(param));
	}
	
	@POST
	@Path("/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{code}", response = NHSysParameter.class, notes = "根据code查询系统参数")
	public Response findSysParamByCode(@ApiParam(required=true,name="code",value="参数编码") @PathParam("code") String code){
		return convertToRespModel(MessageCode.NORMAL, null,sysParamService.selectSysParamByCode(code));
	}
	
}
