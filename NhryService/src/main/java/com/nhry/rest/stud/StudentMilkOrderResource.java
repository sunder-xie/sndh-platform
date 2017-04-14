package com.nhry.rest.stud;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.model.stud.OrderBatchBuildModel;
import com.nhry.model.stud.OrderStudQueryModel;
import com.nhry.model.stud.SchoolQueryModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.stud.dao.ClassService;
import com.nhry.service.stud.dao.MaraStudService;
import com.nhry.service.stud.dao.OrderStudService;
import com.nhry.service.stud.dao.SchoolClassService;
import com.nhry.service.stud.dao.SchoolRuleService;
import com.nhry.service.stud.dao.SchoolService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
@Path("/studentMilk/order")
@Controller
@Singleton
@Scope("request")
@Api(value = "/studentMilk/order",description = "学生奶_订奶管理")
public class StudentMilkOrderResource  extends BaseResource {
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private SchoolClassService schoolClassService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private SchoolRuleService schoolRuleService;
	
	@Autowired
	private OrderStudService orderStudService;
	
	@Autowired
	private MaraStudService maraStudService;
	
	@POST
	@Path("/findAllSchool")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findAllSchool", response = List.class, notes = "获取全部学校信息列表")
	public Response findAllSchool(@ApiParam(required=true,name="smodel",value="SearchModel") SchoolQueryModel smodel){
		smodel.setVisiable("10");
		return convertToRespModel(MessageCode.NORMAL, null, schoolService.findSchoolPage(smodel));
	}
	
	@GET
	@Path("/findByOrderId")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findByOrderId", response = TMstOrderStud.class, notes = "根据订单号查询订单")
	public Response findClassByClassCode(@ApiParam(required=true,name="orderId") @QueryParam("orderId") String orderId){
		return convertToRespModel(MessageCode.NORMAL, null, orderStudService.selectByOrderId(orderId));
	}
	
	@POST
	@Path("/findOrderPage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findOrderPage", response = PageInfo.class, notes = "分页查询订单")
	public Response findClassListBySalesOrg(@ApiParam(required=true,name="queryModel")OrderStudQueryModel queryModel){
		return convertToRespModel(MessageCode.NORMAL, null,  orderStudService.findOrderPage(queryModel));
	}
	
	@POST
	@Path("/createOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/createOrder", response = int.class, notes = "创建订单")
	public Response createOrder(@ApiParam(required=true,name="mstOrderStud")TMstOrderStud mstOrderStud) throws Exception{
		return convertToRespModel(MessageCode.NORMAL, null,  orderStudService.createOrder(mstOrderStud));
	}
	
	@POST
	@Path("/updateOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/updateOrder", response = int.class, notes = "更新订单")
	public Response updateOrder(@ApiParam(required=true,name="mstOrderStud")TMstOrderStud mstOrderStud){
		return convertToRespModel(MessageCode.NORMAL, null,  orderStudService.updateByOrder(mstOrderStud));
	}
	
	
	@GET
	@Path("/findMaraStudAllList")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findMaraStudAllList", response = List.class, notes = "查询奶品列表")
	public Response findMaraStudAllList(){
		return convertToRespModel(MessageCode.NORMAL, null,  maraStudService.findAllListBySalesOrg());
	}
	
	@POST
	@Path("/findOrderInfoBySchoolCodeAndDate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/findOrderInfoBySchoolCodeAndDate", response = Map.class, notes = "根据学校，时间查询订单详情列表")
	public Response findOrderInfoBySchoolCodeAndDate(@ApiParam(required=true,name="mstOrderStud")TMstOrderStud mstOrderStud){
		return convertToRespModel(MessageCode.NORMAL, null,  orderStudService.findOrderInfoBySchoolCodeAndDate(mstOrderStud));
	}
	
	@POST
	@Path("/buildBatchInfo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/buildBatchInfo", response = Map.class, notes = "批量生成订单数据准备")
	public Response buildBatchInfo(@ApiParam(required=true,name="orderBatchBuildModel")OrderBatchBuildModel orderBatchBuildModel)  throws Exception{
		return convertToRespModel(MessageCode.NORMAL, null,  orderStudService.buildBatchInfo(orderBatchBuildModel));
	}
	
	@POST
	@Path("/createOrderWithBatch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/createOrderWithBatch", response = int.class, notes = "批量生成订单")
	public Response createOrderWithBatch(@ApiParam(required=true,name="orderBatchBuildModel")OrderBatchBuildModel orderBatchBuildModel) throws Exception{
		return convertToRespModel(MessageCode.NORMAL, null,  orderStudService.createOrderWithBatch(orderBatchBuildModel));
	}
	
	@POST
	@Path("/deleteOrderWithBatch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "/deleteOrderWithBatch", response = int.class, notes = "批量删除指定日期的订单")
	public Response deleteOrderWithBatch(@ApiParam(required=true,name="orderBatchBuildModel")OrderBatchBuildModel orderBatchBuildModel) throws Exception{
		return convertToRespModel(MessageCode.NORMAL, null,  orderStudService.updateOrderWithBatch(orderBatchBuildModel));
	}
	
}
