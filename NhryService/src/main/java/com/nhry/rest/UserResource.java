package com.nhry.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
import com.nhry.exception.ExceptionMapperSupport;
import com.nhry.rest.BaseResource.ErrorType;
import com.nhry.service.dao.UserService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

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
        if(4%0 == 0){
        	
        }
		return JsonUtil.toJson(userService.selectByUserName(name,0,2));
    }
}
