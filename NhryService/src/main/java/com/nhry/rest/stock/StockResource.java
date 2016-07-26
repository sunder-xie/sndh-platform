package com.nhry.rest.stock;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.stock.domain.TSsmGiOrderItem;
import com.nhry.model.basic.MessageModel;
import com.nhry.model.stock.GiOrderModel;
import com.nhry.model.stock.StockModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.pi.dao.PIProductService;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.service.stock.dao.TSsmGiOrderItemService;
import com.nhry.service.stock.dao.TSsmGiOrderService;
import com.nhry.service.stock.dao.TSsmStockService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.rmi.RemoteException;

import static org.springframework.http.MediaType.*;

/**
 * Created by cbz on 7/20/2016.
 */
@Path("/stock")
@Controller
@Singleton
@Scope("request")
@Api(value = "/stock",description = "库存管理")
public class StockResource extends BaseResource {
    @Autowired
    private TSsmStockService ssmStockService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private PIRequireOrderService requireOrderService;
    @Autowired
    private TSsmGiOrderItemService giOrderItemService;
    @Autowired
    private TSsmGiOrderService giOrderService;
    @POST
    @Path("/findStock")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findStock", response = ResponseModel.class, notes = "奶站库存列表")

    public Response findStock(@ApiParam(name = "model",value = "model")StockModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }
        return convertToRespModel(MessageCode.NORMAL,null,ssmStockService.findStock(model));
    }
    @POST
    @Path("/findStockinsidesal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findStockinsidesal", response = ResponseModel.class, notes = "查询-库存中剩余的产品数量生成销售订单")

    public Response findStockinsidesal(@ApiParam(name = "model",value = "model")StockModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }
        return convertToRespModel(MessageCode.NORMAL,null,ssmStockService.findStockinsidesal(model));
    }
    @POST
    @Path("/generateJHD")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/generateJHD", response = ResponseModel.class, notes = "获取交货单数据")
    public Response generateJHD(@ApiParam(name = "model",value = "model")StockModel model) throws RemoteException {
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }
        return convertToRespModel(MessageCode.NORMAL,requireOrderService.execDelivery(model.getBranchNo()), null);
    }
    @POST
    @Path("/findGiOrder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findGiOrder", response = ResponseModel.class, notes = "奶站交货单列表")
    public Response findGiOrder(@ApiParam(name = "model",value = "model")GiOrderModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isEmpty(model.getBranchNo()) && StringUtils.isNotEmpty(user.getBranchNo())){
            model.setBranchNo(user.getBranchNo());
        }
        return convertToRespModel(MessageCode.NORMAL,null,giOrderService.findGiOrder(model));
    }
    @POST
    @Path("/findGiOrderItem/{orderNo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findGiOrderItem/{orderNo}", response = ResponseModel.class, notes = "奶站交货单明细列表")
    public Response findStock(@ApiParam(name = "orderNo",value = "orderNo")@PathParam("orderNo") String orderNo){
        return convertToRespModel(MessageCode.NORMAL,null,giOrderItemService.findGiOrderItem(orderNo));
    }
    @POST
    @Path("/updateGiOrderItems")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/updateGiOrderItems}", response = ResponseModel.class, notes = "更新奶站交货单明细列表")
    public Response updateGiOrderItems(@ApiParam(name = "GiOrderItmes",value = "GiOrderItmes")List<TSsmGiOrderItem> giOrderItems){
        return convertToRespModel(MessageCode.NORMAL,null,giOrderItemService.updateGiOrderItems(giOrderItems));
    }
}
