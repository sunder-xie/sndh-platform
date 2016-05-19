package com.nhry.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
