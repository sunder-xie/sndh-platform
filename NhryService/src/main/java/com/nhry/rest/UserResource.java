package com.nhry.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.User;
import com.nhry.service.dao.UserService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.*;
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
@Api(value = "/user", description = "有关用户的CURD")
@Produces({"application/json", "application/xml","text/plain"})
public class UserResource extends BaseResource{
	@Autowired
	private UserService userService;

	@GET
	@Path("/search/{uname}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "get user", response = String.class , position=1)
	@ApiResponses(value = {
	        @ApiResponse(code = 400, message = "Invalid username supplied"),
	        @ApiResponse(code = 404, message = "User not found") })
    public Response searchByUname2(@ApiParam(required = true, name = "uname", value = "用户名") @PathParam("uname")String name)throws Exception{
		User user = new User();
		user.setId(1);
		user.setUserName("asdsad");
		return Response.ok().entity(user).build();
    }
	
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
