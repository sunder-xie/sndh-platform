package com.nhry.rest.bill;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.model.bill.*;
import com.nhry.rest.BaseResource;
import com.nhry.service.bill.dao.BranchBillService;
import com.nhry.service.bill.dao.CustomerBillService;
import com.nhry.service.bill.dao.EmpBillService;
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

@Path("/bill")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/bill", description = "结算管理")
public class BillResource extends BaseResource {
    @Autowired
    private CustomerBillService customerBillService;
    @Autowired
    private BranchBillService branchBillService;
    @Autowired
    private EmpBillService empBillService;



    @POST
    @Path("/cust/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/cust/search", response = PageInfo.class, notes = "查询订户订单列表")
    public Response searchCustomerOrder(@ApiParam(required=true,name="cModel",value="cModel") CustBillQueryModel cModel){
        return convertToRespModel(MessageCode.NORMAL, null, customerBillService.searchCustomerOrder(cModel));
    }

    @GET
    @Path("/cust/getCustomerOrderDetialByCode")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/cust/getCustomerOrderByCode", response = Response.class, notes = "查询订户收款表详情")
    public Response getCustomerOrderByCode(@ApiParam(required=true,name="orderNo",value="订单号") @QueryParam("orderNo") String orderNo){
         return convertToRespModel(MessageCode.NORMAL, null, customerBillService.getCustomerOrderDetailByCode(orderNo));
    }

    @GET
    @Path("/cust/getRecBillByOrderNo")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/cust/getRecBillByOrderNo", response = Response.class, notes = "根据订单号获取收款单")
    public Response getRecBillByOrderNo(@ApiParam(required=true,name="orderNo",value="订单号") @QueryParam("orderNo") String orderNo){
        return convertToRespModel(MessageCode.NORMAL, null, customerBillService.getRecBillByOrderNo(orderNo));
    }

    @POST
    @Path("/cust/customerPayment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/cust/customerPayment", response = int.class, notes = "订户收款")
    public Response customerPayment(@ApiParam(required=true,name="cModel",value="收款信息") CustomerPayMentModel cModel){
        return convertToRespModel(MessageCode.NORMAL, null, customerBillService.customerPayment(cModel));
    }


    @POST
    @Path("/emp/empDispDetialInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/emp/empDispDetialInfo", response = int.class, notes = "获取送奶工配送数量明细结算表")
    public Response empDispDetialInfo(@ApiParam(required=true,name="eSearch",value="收款信息") EmpDispDetialInfoSearch eSearch){
        return convertToRespModel(MessageCode.NORMAL, null, empBillService.empDispDetialInfo(eSearch));
    }

    @POST
    @Path("/emp/empAccountReceAmount")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/emp/empAccountReceAmount", response = int.class, notes = "送奶员收款金额核算")
    public Response empAccountReceAmount(@ApiParam(required=true,name="eSearch",value="收款信息") EmpDispDetialInfoSearch eSearch){
        return convertToRespModel(MessageCode.NORMAL, null, empBillService.empAccountReceAmount(eSearch));
    }



    @POST
    @Path("/emp/empSalaryRep")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/emp/empSalaryRep", response = PageInfo.class, notes = "送奶员工资报表")
    public Response empSalaryRep(@ApiParam(required=true,name="eSearch",value="查询条件") EmpDispDetialInfoSearch eSearch){
        return convertToRespModel(MessageCode.NORMAL, null, empBillService.empSalaryRep(eSearch));
    }

    @POST
    @Path("/emp/getSalesOrgDispRate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/emp/getSalesOrgDispRate", response = Response.class, notes = "获取当前登录人所在的 销售组织下的配送率")
    public Response getSalesOrgDispRate(){
        return convertToRespModel(MessageCode.NORMAL, null, empBillService.getSalesOrgDispRate());
    }


    @POST
    @Path("/emp/uptEmpDispRate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/emp/uptEmpDispRate", response = int.class, notes = "更新销售组织 配送率")
    public Response uptEmpDispRate(@ApiParam(required=true,name="sModel",value="JSON 格式") SaleOrgDispRateModel sModel){
        return convertToRespModel(MessageCode.NORMAL, null, empBillService.uptEmpDispRate(sModel));
    }


    @POST
    @Path("/branch/branchBill")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branch/branchBill", response = PageInfo.class, notes = "奶站结算")
    public Response branchBill(@ApiParam(required=true,name="customerBill",value="奶站结算") BranchBillSearch bsearch){
        return convertToRespModel(MessageCode.NORMAL, null, branchBillService.branchBill(bsearch));
    }
}
