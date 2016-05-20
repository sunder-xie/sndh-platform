package com.nhry.rest;

import java.io.IOException;
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

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.UserMapper;
import com.nhry.domain.Role;
import com.nhry.domain.User;
import com.nhry.service.dao.RoleService;
import com.nhry.service.dao.UserService;
import com.nhry.utils.JsonUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/role")  
@Component  
@Scope("request")  
@Singleton
@Controller
@Api(value = "role-api", description = "有关于角色的CURD操作", position = 5)
public class RoleResource extends BaseResource{
	@Autowired
	private RoleService roleService;
	
	@GET
	@Path("/search/{roleName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search/{roleName}", httpMethod = "GET", response = PageInfo.class, notes = "根据角色名模糊查询")
    public JSONObject searchByUname(@ApiParam(required = true, name = "roleName", value = "角色名") @PathParam("roleName")String name){
		System.out.println("search by page "+name);
		return JsonUtil.toJson(roleService.selectByRoleName(name,0,2));
    }
	
	@GET
	@Path("/hello{name}")
	@Produces(MediaType.APPLICATION_JSON)
    public Object sayHello(@PathParam("name")String name) {
		  System.out.println("hello!"+name);
        return roleService.selectOneRole(name);
    }
	
	@GET
	@Path("/allByPage")
	@Produces(MediaType.APPLICATION_JSON)
    public JSONObject allByPage(@QueryParam("pageNo") @DefaultValue("0") int pageNo
   		 							 ,@QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return JsonUtil.toJson(roleService.selectByPage(pageNo, pageSize));
    }
	
	@POST
	@Path("/postForm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Object sayTo(Role role) {
		  System.out.println("==============POST==========="+role.getId()+role.getRoleName());
        return role;
    }
	
}
