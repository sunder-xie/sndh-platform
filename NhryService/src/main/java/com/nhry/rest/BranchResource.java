package com.nhry.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.nhry.domain.NHSysParameter;
import com.nhry.exception.MessageCode;
import com.nhry.service.dao.BranchService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/branch")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/branch", description = "网点客户信息信息维护")
public class BranchResource extends BaseResource {
	@Autowired
    private BranchService branchService;
    
	@GET
	@Path("/search/{salesOrg}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{salesOrg}", response = NHSysParameter.class, notes = "根据销售组织查询网点客户信息列表")
	public Response findBranchListByOrg(@ApiParam(required=true,name="salesOrg",value="销售组织")  @PathParam("salesOrg") String salesOrg){
		return convertToRespModel(MessageCode.NORMAL, null,branchService.findBranchListByOrg(salesOrg));
	}  
}
