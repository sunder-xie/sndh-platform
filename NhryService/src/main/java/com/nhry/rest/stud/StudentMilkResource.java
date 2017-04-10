package com.nhry.rest.stud;

import java.util.List;

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
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.stud.domain.TMdClass;
import com.nhry.data.stud.domain.TMdSchool;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.model.stud.ClassQueryModel;
import com.nhry.model.stud.SchoolBoundClassModel;
import com.nhry.model.stud.SchoolQueryModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.stud.dao.ClassService;
import com.nhry.service.stud.dao.SchoolService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
*
*@author dai
*/
@Path("/studentMilk")
@Controller
@Singleton
@Scope("request")
@Api(value = "/studentMilk",description = "学生奶")
public class StudentMilkResource  extends BaseResource {
	@Autowired
	SchoolService schoolService;
	
	
	@Autowired
	private ClassService classService;
	
	@POST
	@Path("/school/findSchoolPage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/school/findSchoolPage", response = PageInfo.class, notes = "获取学校信息列表")
	public Response findSchoolPage(@ApiParam(required=true,name="smodel",value="SearchModel") SchoolQueryModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, schoolService.serchSchoolList(smodel));
	}
	
	@POST
	@Path("/school/upt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/school/upt", response = ResponseModel.class, notes = "更新商品信息")
	public Response uptProduct(@ApiParam(required=true,name="record",value="系统参数json格式")TMdSchool tMdSchool){
		return convertToRespModel(MessageCode.NORMAL, null,  schoolService.updateSchool(tMdSchool));
	}
	
	
	
	@GET
	@Path("/studentClass/findClassByClassCode")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/studentClass/findClassByClassCode", response = TMdClass.class, notes = "查询班级信息")
	public Response findClassByClassCode(@ApiParam(required=true,name="classCode") @QueryParam("classCode") String classCode){
		return convertToRespModel(MessageCode.NORMAL, null, classService.findClassByClassCode(classCode));
	}
	
	@POST
	@Path("/studentClass/findClassListBySalesOrg")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/studentClass/findClassListBySalesOrg", response = ResponseModel.class, notes = "查询班级列表")
	public Response findClassListBySalesOrg(@ApiParam(required=false,name="salesOrg") @QueryParam("salesOrg")String salesOrg){
		return convertToRespModel(MessageCode.NORMAL, null,  classService.findClassListBySalesOrg(salesOrg));
	}
	
	@POST
	@Path("/studentClass/findClassPage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/studentClass/findClassPage", response = PageInfo.class, notes = "分页查询班级信息")
	public Response findClassPage(@ApiParam(required=true, name="queryModel") ClassQueryModel queryModel){
		return convertToRespModel(MessageCode.NORMAL, null, classService.findClassPage(queryModel));
	}
	
	@POST
	@Path("/studentClass/addClass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/studentClass/addClass", response = PageInfo.class, notes = "新增班级")
	public Response addClass(@ApiParam(required=true, name="mdClass") TMdClass mdClass){
		return convertToRespModel(MessageCode.NORMAL, null, classService.addClass(mdClass));
	}
	
	@POST
	@Path("/studentClass/updClass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/studentClass/updClass", response = PageInfo.class, notes = "更新班级")
	public Response updClass(@ApiParam(required=true, name="mdClass") TMdClass mdClass){
		return convertToRespModel(MessageCode.NORMAL, null, classService.updClass(mdClass));
	}
	
	@POST
	@Path("/studentClass/delClass/{classCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/studentClass/delClass/{classCode}", response = PageInfo.class, notes = "更新班级")
	public Response delClass(@ApiParam(required=true, name="classCode") @PathParam("classCode") String classCode){
		return convertToRespModel(MessageCode.NORMAL, null, classService.delClass(classCode));
	}
	
	
	
	
	
/*	
	@POST
	@Path("/getClassBySchoolId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getClassBySchoolId", response = List.class, notes = "根据学校获取学校的班级")
	public Response getClassBySchoolId(@ApiParam(required=true,name="tMdSchool",value="tMdSchool") TMdSchool tMdSchool){
		return convertToRespModel(MessageCode.NORMAL, null, schoolService.serchSchoolList(tMdSchool));
		return null;
	}
	
	@POST
	@Path("/findSchoolPage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findSchoolPolicyPage", response = PageInfo.class, notes = "获取学校订奶政策")
	public Response findSchoolPolicyPage(@ApiParam(required=true,name="smodel",value="SearchModel") SchoolQueryModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, schoolService.serchSchoolList(smodel));
	}
	
	
	
	
	
	
	@POST
	@Path("/getClassBySchoolIdNot")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getClassBySchoolId", response = List.class, notes = "根据学校获取学校不包含的班级")
	public Response getClassBySchoolIdNot(@ApiParam(required=true,name="tMdSchool",value="tMdSchool") TMdSchool tMdSchool){
		return convertToRespModel(MessageCode.NORMAL, null, schoolService.serchSchoolList(tMdSchool));
		return null;
	}
	
	
	@POST
	@Path("/schoolBoundClass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/schoolBoundClass", response = List.class, notes = "根据学校绑定班级")
	public Response schoolBoundClass(@ApiParam(required=true,name="tMdSchool",value="tMdSchool") SchoolBoundClassModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, schoolService.serchSchoolList(tMdSchool));
		return null;
	}*/
	
}
