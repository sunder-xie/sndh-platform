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
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/add", response = ResponseModel.class, notes = "增加价格组")
	public Response addPriceGroup(@ApiParam(required=true,name="record",value="系统参数json格式")TMdPrice record){
		return convertToRespModel(MessageCode.NORMAL, null,  priceService.addNewPriceGroup(record));
	}
	
	@GET
	@Path("/{priceGroupCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{priceGroupCode}", response = TMdPrice.class, notes = "根据价格组编号查询信息")
	public Response selectPriceGroupByCode(@ApiParam(required=true,name="priceGroupCode",value="价格组编号") @PathParam("priceGroupCode") String priceGroupCode){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.selectPriceGroupByCode(priceGroupCode));
	}
	
	@POST
	@Path("/upt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt", response = String.class, notes = "编辑价格组信息")
	public Response uptPriceGroup(@ApiParam(required=true,name="record",value="系统参数json格式")TMdPrice record){
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
	@Path("/disable")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/disable", response = ResponseModel.class, notes = "停用价格组")
	public Response pubProductByCode(@ApiParam(required=true,name="productCode",value="商品编号")TMdPrice record){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.disablePriceGroup(record));
	}
	
	@POST
	@Path("/upt/mprice/rel")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt/mprice/rel", response = ResponseModel.class, notes = "维护价格组与产品关系")
	public Response mergeMaraPriceRel(@ApiParam(required=true,name="records",value="价格组与产品关系对象")List<TMaraPriceRel> records){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.mergeMaraPriceRel(records));
	}
}
