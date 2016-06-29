package com.nhry.rest.bill;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.model.bill.BranchBillSearch;
import com.nhry.model.bill.CustBillQueryModel;
import com.nhry.model.bill.CustomerPayMentModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.bill.dao.BranchBillService;
import com.nhry.service.bill.dao.CustomerBillService;
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
    @ApiOperation(value = "/cust/getCustomerOrderByCode", response = Response.class, notes = "查询订户收款表")
    public Response getCustomerOrderByCode(@ApiParam(required=true,name="orderNo",value="订单号") @QueryParam("orderNo") String orderNo){
         return convertToRespModel(MessageCode.NORMAL, null, customerBillService.getCustomerOrderDetailByCode(orderNo));
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
    @Path("/branch/branchBill")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branch/branchBill", response = PageInfo.class, notes = "奶站结算")
    public Response branchBill(@ApiParam(required=true,name="customerBill",value="奶站结算")BranchBillSearch bsearch){
        return convertToRespModel(MessageCode.NORMAL, null, branchBillService.branchBill(bsearch));
    }
}
