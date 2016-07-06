package com.nhry.rest.basic;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.data.config.domain.NHSysParameter;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.ProductService;
import com.nhry.service.basic.pojo.ProductInfoExModel;
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

@Path("/product")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/product", description = "商品信息维护")
public class ProductResource extends BaseResource {
	@Autowired
	private ProductService productService;
	
	@POST
	@Path("/{productCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{productCode}", response = TMdMara.class, notes = "根据商品编号查询商品信息")
	public Response selectProductByCode(@ApiParam(required=true,name="productCode",value="商品编号") @PathParam("productCode") String productCode){
		return convertToRespModel(MessageCode.NORMAL, null, productService.selectProductAndExByCode(productCode));
	} 
	
	@POST
	@Path("/upt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/upt", response = ResponseModel.class, notes = "更新商品信息")
	public Response uptProduct(@ApiParam(required=true,name="record",value="系统参数json格式")TMdMara record){
		return convertToRespModel(MessageCode.NORMAL, null,  productService.uptProductByCode(record));
	}	
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/search", response = PageInfo.class, notes = "查询商品信息列表")
	public Response findProducts(@ApiParam(required=true,name="smodel",value="SearchModel") ProductQueryModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, productService.searchProducts(smodel));
	}
	
	@POST
	@Path("/publish/{productCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/publish/{productCode}", response = ResponseModel.class, notes = "发布商品")
	public Response pubProductByCode(@ApiParam(required=true,name="productCode",value="商品编号") @PathParam("productCode") String productCode){
		return convertToRespModel(MessageCode.NORMAL, null, productService.pubProductByCode(productCode));
	} 
	
	@POST
	@Path("/lists/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/lists/{id}", response = TMdMara.class, notes = "根据价格编号查询当前组织下未被选择的商品列表")
	public Response selectProductAndExListByCode(@ApiParam(required=true,name="id",value="价格组编号(如果还没有价格组编号，直接写-1,-1表示获取当前组织下所有的产品列表)") @PathParam("id") String id){
		return convertToRespModel(MessageCode.NORMAL, null, productService.findMarasBySalesCodeAndOrg(id));
	}
	
	@POST
	@Path("/sell/lists/{branchNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/sell/lists/{branchNo}", response = TMdMara.class, notes = "获取奶站可销售的产品清单")
	public Response getBranchSaleMaras(@ApiParam(required=true,name="branchNo",value="奶站编号") @PathParam("branchNo") String branchNo){
		return convertToRespModel(MessageCode.NORMAL, null, productService.getBranchSaleMaras(branchNo));
	}
}
