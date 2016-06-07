package com.nhry.rest.basic;

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

import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.data.config.domain.NHSysParameter;
import com.nhry.exception.MessageCode;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.ProductService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/product")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/product", description = "商品信息维护")
public class ProductResource extends BaseResource {
	@Autowired
	private ProductService productService;
	
	@GET
	@Path("/{productCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{productCode}", response = NHSysParameter.class, notes = "根据商品编号查询商品信息")
	public Response selectProductByCode(@ApiParam(required=true,name="productCode",value="商品编号") @PathParam("productCode") String productCode){
		return convertToRespModel(MessageCode.NORMAL, null, productService.selectProductAndExByCode(productCode));
	} 
	
	@POST
	@Path("/upt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt", response = String.class, notes = "更新商品信息")
	public Response uptProduct(@ApiParam(required=true,name="record",value="系统参数json格式")TMdMaraEx record){
		return convertToRespModel(MessageCode.NORMAL, null,  productService.uptProductExByCode(record));
	}	
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search", response = NHSysParameter.class, notes = "查询商品信息列表")
	public Response findProducts(@ApiParam(required=true,name="smodel",value="SearchModel") ProductQueryModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, productService.searchProducts(smodel));
	} 
	
	@GET
	@Path("/publish/{productCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/publish/{productCode}", response = NHSysParameter.class, notes = "发布商品")
	public Response pubProductByCode(@ApiParam(required=true,name="productCode",value="商品编号") @PathParam("productCode") String productCode){
		return convertToRespModel(MessageCode.NORMAL, null, productService.pubProductByCode(productCode));
	} 
	
}
