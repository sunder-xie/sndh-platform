package com.nhry.rest.basic;

import java.util.List;

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
import com.nhry.data.basic.domain.TMaraPriceRel;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.data.basic.domain.TMdPriceBranch;
import com.nhry.data.config.domain.NHSysParameter;
import com.nhry.model.basic.PriceQueryModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.PriceService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/price")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/price", description = "价格信息维护")
public class PriceResource extends BaseResource {
	@Autowired
	private PriceService priceService;
	
	@POST
	@Path("/add/price")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add/price", response = ResponseModel.class, notes = "增加价格组")
	public Response addPriceGroup(@ApiParam(required=true,name="record",value="系统参数json格式")TMdPrice record){
		return convertToRespModel(MessageCode.NORMAL, null,  priceService.addNewPriceGroup(record));
	}
	
	@POST
	@Path("/{priceGroupCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{priceGroupCode}", response = TMdPrice.class, notes = "根据价格组编号查询信息(priceType：10 区域价;20 销售渠道价;30 奶站价)")
	public Response selectPriceGroupByCode(@ApiParam(required=true,name="priceGroupCode",value="价格组编号") @PathParam("priceGroupCode") String priceGroupCode){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.selectPriceGroupByCode(priceGroupCode));
	}
	
	@POST
	@Path("/upt/price")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt/price", response = String.class, notes = "编辑价格组信息")
	public Response uptPriceGroup(@ApiParam(required=true,name="record",value="价格组对象")TMdPrice record){
		return convertToRespModel(MessageCode.NORMAL, null,  priceService.updatePriceGroup(record));
	}
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search", response = TMdPrice.class, notes = "查询价格组信息列表")
	public Response findPriceGroups(@ApiParam(required=true,name="smodel",value="SearchModel") PriceQueryModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.searchPriceGroups(smodel));
	}
	
	@POST
	@Path("/disable/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/disable/{id}", response = ResponseModel.class, notes = "停用价格组")
	public Response pubProductByCode(@ApiParam(required=true,name="id",value="价格组编号")@PathParam("id")String id){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.disablePriceGroup(id));
	}
	
	@POST
	@Path("/add/price/branch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add/price/branch", response = ResponseModel.class, notes = "添加价格组与奶站关系")
	public Response pubProductByCode(@ApiParam(required=true,name="record",value="价格组与奶站关系对象")TMdPriceBranch record){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.addPriceBranch(record));
	}
	
	@POST
	@Path("/del/price/branch/{branchNo}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/del/price/branch/{branch}/{id}", response = ResponseModel.class, notes = "根据奶站编号(branchNo)和价格组编号(id)删除价格组与奶站关系")
	public Response pubProductByCode(@ApiParam(required=true,name="branchNo",value="奶站编号")
	@PathParam("branchNo")String branchNo,@ApiParam(required=true,name="id",value="价格组编号")@PathParam("id")String id){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.delPriceBranch(branchNo,id));
	}
}
