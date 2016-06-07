package com.nhry.rest.auth;

import com.github.pagehelper.PageInfo;
import com.nhry.auth.UserSessionService;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.exception.MessageCode;
import com.nhry.rest.BaseResource;
import com.nhry.service.auth.dao.UserService;
import com.nhry.utils.CookieUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.codehaus.jettison.json.JSONObject;
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
	@Path("/search/{uname}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search", response = PageInfo.class, notes = "根据用户名查询用户列表")
	public Response searchByUname(
			@ApiParam(required = true, name = "uname", value = "用户名") @PathParam("uname") String name,
			@ApiParam(required = true, name = "json", value = "分页信息") PageInfo page) {
		PageInfo data = userService.selectByUserName(name,page.getPageNum(),page.getPageSize());
		return convertToRespModel(MessageCode.NORMAL, null, data);
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = String.class, notes = "增加用户")
	public Response addUser(
			@ApiParam(required = true, name = "json", value = "分页信息") JSONObject json) {
		return convertToRespModel(MessageCode.NORMAL, userService.addUser(json), null);
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/login", response = String.class, notes = "用户登录")
	public Response login(@ApiParam(required = true, name = "user", value = "用户名、密码") TSysUser user) {
		TSysUser loginuser = userService.login(user);
		String accesskey = userSessionService.generateKey();
		CookieUtil.setCookie(request, response, UserSessionService.accessKey, accesskey);
		CookieUtil.setCookie(request, response, UserSessionService.uname, loginuser.getLoginName());
		userSessionService.cacheUserSession(user.getLoginName(), accesskey, user,request);
		return convertToRespModel(MessageCode.NORMAL,null, loginuser);
	}

	@GET
	@Path("/currentUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/currentUser", response = String.class, notes = "获取当前登录用户")
	public Response currentUser() {
		TSysUser user = userSessionService.getCurrentUser();
		return convertToRespModel(MessageCode.NORMAL,null, user);
	}

}
