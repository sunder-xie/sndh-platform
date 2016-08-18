package com.nhry.rest.order;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.order.domain.TMilkboxPlan;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.order.*;
import com.nhry.rest.BaseResource;
import com.nhry.service.order.dao.MilkBoxService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.service.order.dao.PromotionService;
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

import java.util.ArrayList;
import java.util.List;

@Path("/promotion")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/promotion", description = "促销信息维护")
public class PromotionResource extends BaseResource {
	@Autowired
	private PromotionService promotionService;
	
	@GET
	@Path("/product/{productCode}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{productCode}", response = List.class, notes = "根据商品号，得到促销信息")
	public Response selectPromotionByMatnr(@ApiParam(required=true,name="productCode",value="商品编号") @PathParam("productCode") String productCode){
		return convertToRespModel(MessageCode.NORMAL, null, promotionService.getPromotionByMatnr(productCode));
	}
	
	@GET
	@Path("/promNo/{promNo}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/{promNo}", response = TPromotion.class, notes = "根据促销号，得到促销信息")
	public Response selectPromotionByPromNo(@ApiParam(required=true,name="promNo",value="商品编号") @PathParam("promNo") String promNo){
		return convertToRespModel(MessageCode.NORMAL, null, promotionService.selectPromotionByPromNo(promNo));
	}
	
	@POST
	@Path("/searchPromotionsByPage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/searchPromotionsByPage", response = Integer.class, notes = "查询所有促销规则")
	public Response searchPromotionsByPage(@ApiParam(required=true,name="smodel",value="SearchModel") OrderSearchModel smodel){
		return convertToRespModel(MessageCode.NORMAL, null, promotionService.selectPromotionsrsByPage(smodel));
	}
	
}
