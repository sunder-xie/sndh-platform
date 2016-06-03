package com.nhry.rest.config;

import javax.ws.rs.Consumes;
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

import com.nhry.data.config.domain.NHSysParameter;
import com.nhry.exception.MessageCode;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.config.dao.DictionaryService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/dic")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/dic", description = "字典信息维护")
public class DictionaryResource extends BaseResource {
	@Autowired
	private DictionaryService dicService;
	@POST
	@Path("/items/{typecode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/items/{typecode}", response = ResponseModel.class, notes = "根据类型编码查找字典代码")
	public Response getCodeItems(@ApiParam(required=true,name="typecode",value="类型编码")@PathParam("typecode")String typecode){
		return convertToRespModel(MessageCode.NORMAL, null,  dicService.getCodeItemsByTypeCode(typecode));
	}
	
	
}
