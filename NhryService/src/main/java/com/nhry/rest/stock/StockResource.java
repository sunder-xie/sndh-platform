package com.nhry.rest.stock;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.basic.MessageModel;
import com.nhry.model.stock.StockModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.stock.dao.TSsmStockService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
