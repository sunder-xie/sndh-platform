package com.nhry.rest;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.User;
import com.nhry.service.dao.UserService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "user-api", description = "有关于用户的CURD操作", position = 5)
public class UserResource extends BaseResource{
	@Autowired
	private UserService userService;

	@GET
	@Path("/search/{uname}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search/{uname}", httpMethod = "GET", response = PageInfo.class, notes = "根据用户名模糊查询")
	public JSONObject searchByUname(@ApiParam(required = true, name = "uname", value = "用户名") @PathParam("uname")String name){
		return JsonUtil.toJson(userService.selectByUserName(name,0,2));
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray all() {
		return JsonUtil.toJsonArr(userService.all());
	}

	@GET
	@Path("/hello{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object sayHello(@PathParam("name")String name) {
		return userService.selectByUserName(name,0,2);
	}

	@GET
	@Path("/allByPage")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject allByPage(@QueryParam("pageNo") @DefaultValue("0") int pageNo
			, @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
		return JsonUtil.toJson(userService.selectByPage(pageNo, pageSize));
	}

	@POST
	@Path("/postForm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object sayTo(User user) {
		System.out.println("==============POST==========="+user.getId()+user.getUserName());
		return user;
	}
}