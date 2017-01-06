package com.nhry.rest.basic;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdDealer;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.data.basic.domain.TMdPriceBranch;
import com.nhry.model.basic.PriceQueryModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.PriceService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	@Path("/{code}/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{code}/edit", response = TMdPrice.class, notes = "根据价格组编号查询信息(priceType：10 区域价;20 销售渠道价;30 奶站价),用于点击编辑按钮查询")
	public Response selectPGByCode4Edit(@ApiParam(required=true,name="code",value="价格组编号") @PathParam("code") String code){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.selectPGByCode4Edit(code));
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
	
	@POST
	@Path("/dealers")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/dealers", response = TMdDealer.class, notes = "获取当前登录人所在公司下面的所有经销商")
	public Response getDealers(){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.getDealers());
	}
	
	@POST
	@Path("/dealers/{salesOrg}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/dealers/{salesOrg}", response = TMdDealer.class, notes = "根据销售组织获取该销售组织下的经销商")
	public Response getDealers(@ApiParam(required=true,name="salesOrg",value="销售组织编号")@PathParam("salesOrg")String salesOrg){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.getDealers(salesOrg));
	}
	
	@POST
	@Path("/lists/{branchNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/lists/{branchNo}", response = TMdPrice.class, notes = "根据奶站编号获取当前销售组织下该奶站已经选择的价格组列表")
	public Response getPricesGroupByBn(@ApiParam(required=true,name="branchNo",value="奶站编号")@PathParam("branchNo")String branchNo){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.getPricesGroupByBn(branchNo));
	}

	
	@POST
	@Path("/scope/lists/{branchNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/scope/lists/{branchNo}", response = TMdPrice.class, notes = "根据奶站编号获取当前销售组织下该奶站适用范围内的价格组列表")
	public Response getScopePricesGroupByBn(@ApiParam(required=true,name="branchNo",value="奶站编号")@PathParam("branchNo")String branchNo){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.getScopePricesGroupByBn(branchNo));
	}
	
	@POST
	@Path("/{branchNo}/{matnr}/{deliveryType}")   
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{branchNo}/{matnr}/{deliveryType}", response = Float.class, notes = "根据奶站编号、产品编号、配送类型 获取产品价格(配送类型(10:自取；20：送奶到户)")
	public Response getMaraPrice(@ApiParam(required=true,name="branchNo",value="奶站编号")@PathParam("branchNo")String branchNo,
			@ApiParam(required=true,name="matnr",value="产品编号")@PathParam("matnr")String matnr,
			@ApiParam(required=true,name="deliveryType",value="配送类型")@PathParam("deliveryType")String deliveryType){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.getMaraPrice(branchNo,matnr,deliveryType));
	}
	
	@POST
	@Path("/maras/{id}/{pageNum}/{pageSize}")   
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/maras/{id}/{pageNum}/{pageSize}", response = PageInfo.class, notes = "根据价格组编号获取价格组关联的商品列表(带分页)")
	public Response findMaraPricesById(@ApiParam(required=true,name="id",value="价格组编号")@PathParam("id")String id,
			@ApiParam(required=true,name="pageNum",value="当前页码")@PathParam("pageNum")int pageNum,
			@ApiParam(required=true,name="pageSize",value="每页显示条数")@PathParam("pageSize")int pageSize){
		return convertToRespModel(MessageCode.NORMAL, null, priceService.findMaraPricesById(id,pageNum,pageSize));
	}
}
