package com.nhry.rest.auth;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.auth.UserQueryModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.auth.dao.UserService;
import com.nhry.utils.CookieUtil;
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

@Path("/user")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/user", description = "用户rest api")
public class UserResource extends BaseResource {
	@Autowired
	private UserService userService;
	@Autowired
	private UserSessionService userSessionService;

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search", response = PageInfo.class, notes = "根据用户名(或者中文名)、用户组名查询人员列表")
	public Response searchByUname(	@ApiParam(required = true, name = "um", value = "用户登录名、中文名") UserQueryModel um) {
		return convertToRespModel(MessageCode.NORMAL, null, userService.findUser(um));
	}

	@GET
	@Path("/getUserByLoginName/{loginName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/getUserByLoginName/{loginName}", response = TSysUser.class, notes = "根据用户ID查询用户信息")
	public Response getUserByLoginName(	@ApiParam(required = true, name = "loginName", value = "用户登录名")  @PathParam("loginName")String loginName) {
		return convertToRespModel(MessageCode.NORMAL, null, userService.findUserByLoginName(loginName));
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = ResponseModel.class, notes = "增加用户")
	public Response addUser(@ApiParam(required = true, name = "user", value = "用户对象") TSysUser user) {
		return convertToRespModel(MessageCode.NORMAL, null,userService.addUser(user));
	}
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/update", response = ResponseModel.class, notes = "修改用户")
	public Response updateUser(@ApiParam(required = true, name = "user", value = "用户对象") TSysUser user) {
		return convertToRespModel(MessageCode.NORMAL,null,userService.updateUser(user));
	}
	
//	@POST
//	@Path("/update/password")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "/update/password", response = ResponseModel.class, notes = "修改用户密码")
//	public Response updateUserPw(@ApiParam(required = true, name = "user", value = "用户对象(只需要：loginName、pwd属性值)") TSysUser user) {
//		return convertToRespModel(MessageCode.NORMAL, null,userService.updateUserPw(user));
//	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/login", response = ResponseModel.class, notes = "用户登录")
	public Response login(@ApiParam(required = true, name = "user", value = "用户名、密码") TSysUser user) {
		TSysUser loginuser = userService.login(user);
		String accesskey = userSessionService.generateKey();
		CookieUtil.setCookie(request, response, UserSessionService.accessKey, accesskey);
		CookieUtil.setCookie(request, response, UserSessionService.uname, loginuser.getLoginName());
		userSessionService.cacheUserSession(user.getLoginName(), accesskey, user,request);
		return convertToRespModel(MessageCode.NORMAL,null, loginuser);
	}
	
	@POST
	@Path("/delete/{loginName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/delete/{loginName}", response = ResponseModel.class, notes = "失效指定用户")
	public Response deleteUserByLoginName(@ApiParam(required = true, name = "loginName", value = "用户登录名") @PathParam("loginName")String loginName) {
		return convertToRespModel(MessageCode.NORMAL,null,userService.deleteUserByLoginName(loginName));
	}
	
	
}
