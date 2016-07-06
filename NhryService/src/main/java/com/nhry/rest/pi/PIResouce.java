package com.nhry.rest.pi;

import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysResource;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.pi.dao.PIProductService;
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
import java.rmi.RemoteException;

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
}
