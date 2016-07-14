package com.nhry.rest.pi;

import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.pi.dao.PIProductService;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cbz on 6/22/2016.
 */
@Path("/pi")
@Controller
@Component
@Scope("request")
@Singleton
@Api(value = "/pi", description = "PI测试使用")
public class PIResouce extends BaseResource{
    @Autowired
    public PIProductService piProductService;
    @Autowired
    public PIRequireOrderService requireOrderService;

    @Autowired
    public BranchService branchService;

    @GET
    @Path("/getProducts")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/getProducts", response = ResponseModel.class, notes = "获取产品数据")
    public Response getProducts() throws RemoteException {
        return convertToRespModel(MessageCode.NORMAL, piProductService.matHandler(), null);
    }

    @GET
    @Path("/getCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/getCustomer", response = ResponseModel.class, notes = "获取奶站经销商数据")
    public Response getCustomer() throws RemoteException {
        return convertToRespModel(MessageCode.NORMAL, piProductService.customerDataHandle(), null);
    }

    @GET
    @Path("/getZD")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/getZD", response = ResponseModel.class, notes = "获取字典数据")
    public Response getZD() throws RemoteException {
        return convertToRespModel(MessageCode.NORMAL, piProductService.salesQueryHandler(), null);
    }

    @GET
    @Path("/getJHD/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/getJHD/{id}", response = ResponseModel.class, notes = "获取交货单数据")
    public Response gegetJHDZD(@PathParam("id") String id) throws RemoteException {
        return convertToRespModel(MessageCode.NORMAL, requireOrderService.getDelivery(id,true), null);
    }

    @POST
    @Path("/getYHD/{date}/{branchNo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/getYHD/{date}/{branchNo}", response = ResponseModel.class, notes = "获取交货单数据")
    public Response getYHD(@PathParam("date") String date,@PathParam("branchNo") String branchNo) throws RemoteException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return convertToRespModel(MessageCode.NORMAL, requireOrderService.execRequieOrder(format.parse(date),branchNo), null);
    }
    @POST
    @Path("/getSalesOrder/{date}/{branchNo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/getSalesOrder/{date}/{branchNo}", response = ResponseModel.class, notes = "获取交货单数据")
    public Response getSalesOrder(@PathParam("date") String date,@PathParam("branchNo") String branchNo) throws RemoteException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        TMdBranch branch = branchService.selectBranchByNo(branchNo);
        return convertToRespModel(MessageCode.NORMAL, requireOrderService.execSalesOrder(format.parse(date),branch), null);
    }

}
