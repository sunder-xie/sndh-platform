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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.nhry.data.dao.UserMapper;
import com.nhry.domain.User;
import com.nhry.service.dao.UserService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;

@Path("/resource")  
@Component  
@Scope("request")  
@Singleton
@Controller
public class UserResource {
	@Autowired
	private UserService userService;
	
	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
    public JSONObject say() {
        return JsonUtil.toJson(userService.selectByUserName("张",0,2));
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
   		 							 ,@QueryParam("pageSize") @DefaultValue("10") int pageSize) {
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
