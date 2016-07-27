package com.nhry.rest.auth;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.auth.UserQueryModel;
import com.nhry.model.auth.UserQueryModel2;
import com.nhry.model.sys.AccessKey;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.auth.dao.UserService;
import com.nhry.utils.CookieUtil;
import com.nhry.utils.SysContant;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

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
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/login", response = ResponseModel.class, notes = "用户登录")
	public Response login(@ApiParam(required = true, name = "user", value = "用户名、密码") TSysUser user) {
		TSysUser loginuser = userService.login(user);
		String accesskey = userSessionService.generateKey();
		CookieUtil.setCookie(request, response, UserSessionService.accessKey, accesskey);
		CookieUtil.setCookie(request, response, UserSessionService.uname, loginuser.getLoginName());
		userSessionService.cacheUserSession(user.getLoginName(), accesskey, loginuser,request);
		return convertToRespModel(MessageCode.NORMAL,null, loginuser);
	}
	
	@POST
	@Path("/find/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/{token}", response = ResponseModel.class, notes = "根据token获取用户信息")
	public Response login(@ApiParam(required = true, name = "token", value = "token") @PathParam("token") String token) {
		return convertToRespModel(MessageCode.NORMAL,null, userService.findUserBytoken(token));
	}
	
	@POST
	@Path("/delete/{loginName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/delete/{loginName}", response = ResponseModel.class, notes = "失效指定用户")
	public Response deleteUserByLoginName(@ApiParam(required = true, name = "loginName", value = "用户登录名") @PathParam("loginName")String loginName) {
		return convertToRespModel(MessageCode.NORMAL,null,userService.deleteUserByLoginName(loginName));
	}

	@POST
	@Path("/findPageByRoleId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findPageByRoleId", response = PageInfo.class, notes = "根据角色 用户名(或者中文名)查询人员列表")
	public Response findPageByRoleId(	@ApiParam(required = true, name = "um", value = "角色 用户登录名、中文名") UserQueryModel um) {
		return convertToRespModel(MessageCode.NORMAL, null, userService.findUserPageByRoleId(um));
	}
	
	@POST
	@Path("/findByRoleId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findByRoleId", response = List.class, notes = "根据角色 用户名(或者中文名)查询人员列表")
	public Response findByRoleId(	@ApiParam(required = true, name = "um", value = "角色 用户登录名、中文名") UserQueryModel2 um) {
		return convertToRespModel(MessageCode.NORMAL, null, userService.findUserByRoleId(um));
	}
	
	
}
