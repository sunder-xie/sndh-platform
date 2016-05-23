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
import com.nhry.data.dao.UserMapper;
import com.nhry.domain.User;
import com.nhry.service.dao.UserService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.*;

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
    public Response searchByUname(@ApiParam(required = true, name = "uname", value = "用户名") @PathParam("uname")String name)throws Exception{
		User user = new User();
		user.setId(1);
		user.setUserName("asdsad");
		return Response.ok().entity(user).build();
    }
	
}
