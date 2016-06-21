package com.nhry.rest.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysResource;
import com.nhry.data.auth.domain.TSysRole;
import com.nhry.data.auth.domain.TSysRoleResource;
import com.nhry.model.auth.UserRoleModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.auth.dao.ResourceService;
import com.nhry.service.auth.dao.RoleService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/role")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/role", description = "角色信息维护")
public class RoleResource extends BaseResource{
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resService;
	
	@GET
	@Path("/search/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search/{id}", response = ResponseModel.class, notes = "根据角色id查找角色信息")
    public Response findRoleByRid(@ApiParam(required = true, name = "id", value = "角色id")@PathParam("id")String id) {
        return this.convertToRespModel(MessageCode.NORMAL, null, roleService.findRoleByRid(id));
    }
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = ResponseModel.class, notes = "添加角色信息")
    public Response addRole(@ApiParam(required = true, name = "role", value = "角色对象")TSysRole role) {
		return this.convertToRespModel(MessageCode.NORMAL, null, roleService.addRole(role));
    }
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/update", response = ResponseModel.class, notes = "修改角色")
    public Response updateRole(@ApiParam(required = true, name = "role", value = "角色对象")TSysRole role) {
        return this.convertToRespModel(MessageCode.NORMAL, null, roleService.updateRoleByRid(role));
    }
	
	@POST
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/delete/{id}", response = ResponseModel.class, notes = "根据角色id删除角色")
    public Response deleteRole(@ApiParam(required = true, name = "id", value = "角色id")@PathParam("id") String id) {
		return this.convertToRespModel(MessageCode.NORMAL, null, roleService.deleteRoleByRid(id));
    }
	
	@POST
	@Path("/assign/role")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/assign/role", response = ResponseModel.class, notes = "给用户(批量)指定角色")
	public Response grantRoleToUsers(@ApiParam(required = true, name = "urmodel", value = "用户角色关系对象(loginNames、roleId、isDefault)")UserRoleModel urmodel) {
		return convertToRespModel(MessageCode.NORMAL, null, roleService.assignRoleToUsers(urmodel));
	}
	
	@POST
	@Path("/delete/user/roles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/delte/user/roles", response = ResponseModel.class, notes = "删除指定的用户角色(支持批量)")
	public Response deleteUserRoles(@ApiParam(required = true, name = "urmodel", value = "用户角色关系对象(loginName、roleIds)")UserRoleModel urmodel) {
		return convertToRespModel(MessageCode.NORMAL, null, roleService.deleteUserRoles(urmodel));
	}
	
	@POST
	@Path("/add/res")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add/res", response = ResponseModel.class, notes = "添加资源")
	public Response addRes(@ApiParam(required = true, name = "resource", value = "资源对象")TSysResource resource) {
		return convertToRespModel(MessageCode.NORMAL, null, resService.addRes(resource));
	}
	
	@POST
	@Path("/find/res/{resCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/find/res/{resCode}", response = ResponseModel.class, notes = "查找资源")
	public Response addRes(@ApiParam(required = true, name = "resCode", value = "资源编号")@PathParam("resCode")String resCode) {
		return convertToRespModel(MessageCode.NORMAL, null, resService.selectResByCode(resCode));
	}
	
	@POST
	@Path("/update/res")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/update/res", response = ResponseModel.class, notes = "修改资源")
	public Response updateResByCode(@ApiParam(required = true, name = "resource", value = "资源对象")TSysResource resource) {
		return convertToRespModel(MessageCode.NORMAL, null, resService.updateResByCode(resource));
	}
	
	@POST
	@Path("/delete/res/{resCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/update/res/{resCode}", response = ResponseModel.class, notes = "删除资源")
	public Response deleteRes(@ApiParam(required = true, name = "resCode", value = "资源编码")@PathParam("resCode")String resCode) {
		return convertToRespModel(MessageCode.NORMAL, null, resService.deleteResByCode(resCode));
	}
	
	@POST
	@Path("/add/roleRes")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add/role/res", response = ResponseModel.class, notes = "添加角色资源关系")
	public Response addRoleRes(@ApiParam(required = true, name = "record", value = "角色资源关系对象")TSysRoleResource record) {
		return convertToRespModel(MessageCode.NORMAL, null,resService.addRoleRes(record));
	}
	
	@POST
	@Path("/delete/roleRes")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add/role/res", response = ResponseModel.class, notes = "删除角色资源关系")
	public Response deleteRoleRes(@ApiParam(required = true, name = "record", value = "角色资源关系对象")TSysRoleResource record) {
		return convertToRespModel(MessageCode.NORMAL, null,resService.deleteRoleRes(record));
	}
}