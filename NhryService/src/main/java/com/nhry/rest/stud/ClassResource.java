package com.nhry.rest.stud;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.stud.domain.TMdClass;
import com.nhry.model.stud.ClassQueryModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.stud.dao.ClassService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
*@author zhaoxijun
*@date 2017-04-10
*/

@Path("/studentClass")
@Controller
@Singleton
@Scope("request")
@Api(value = "/studentClass",description = "学生奶_班级基础信息")
public class ClassResource  extends BaseResource {
	
	@Autowired
	private ClassService classService;
	
	@GET
	@Path("/findClassByClassCode")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findClassByClassCode", response = TMdClass.class, notes = "查询班级信息")
	public Response findClassByClassCode(@ApiParam(required=true,name="classCode") @QueryParam("classCode") String classCode){
		return convertToRespModel(MessageCode.NORMAL, null, classService.findClassByClassCode(classCode));
	}
	
	@POST
	@Path("/findClassListBySalesOrg")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findClassListBySalesOrg", response = ResponseModel.class, notes = "查询班级列表")
	public Response findClassListBySalesOrg(@ApiParam(required=false,name="salesOrg") @QueryParam("salesOrg")String salesOrg){
		return convertToRespModel(MessageCode.NORMAL, null,  classService.findClassListBySalesOrg(salesOrg));
	}
	
	@POST
	@Path("/findClassPage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findClassPage", response = PageInfo.class, notes = "分页查询班级信息")
	public Response findClassPage(@ApiParam(required=true, name="queryModel") ClassQueryModel queryModel){
		return convertToRespModel(MessageCode.NORMAL, null, classService.findClassPage(queryModel));
	}
	
	@POST
	@Path("/addClass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/addClass", response = PageInfo.class, notes = "新增班级")
	public Response addClass(@ApiParam(required=true, name="mdClass") TMdClass mdClass){
		return convertToRespModel(MessageCode.NORMAL, null, classService.addClass(mdClass));
	}
	
	@POST
	@Path("/updClass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/updClass", response = PageInfo.class, notes = "更新班级")
	public Response updClass(@ApiParam(required=true, name="mdClass") TMdClass mdClass){
		return convertToRespModel(MessageCode.NORMAL, null, classService.updClass(mdClass));
	}
	
	@POST
	@Path("/delClass/{classCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/delClass/{classCode}", response = PageInfo.class, notes = "更新班级")
	public Response delClass(@ApiParam(required=true, name="classCode") @PathParam("classCode") String classCode){
		return convertToRespModel(MessageCode.NORMAL, null, classService.delClass(classCode));
	}
	
}
