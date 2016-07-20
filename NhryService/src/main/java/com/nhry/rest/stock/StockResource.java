package com.nhry.rest.stock;

import com.nhry.common.exception.MessageCode;
import com.nhry.model.basic.MessageModel;
import com.nhry.model.stock.StockModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.stock.dao.TSsmStockService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by cbz on 7/20/2016.
 */
@Path("/stock")
@Controller
@Singleton
@Scope
@Api(value = "/stock",description = "库存管理")
public class StockResource extends BaseResource {
    @Autowired
    private TSsmStockService ssmStockService;

    public Response findStock(@ApiParam(name = "model",value = "model")StockModel model){
        return convertToRespModel(MessageCode.NORMAL,null,ssmStockService.findStock(model));
    }
}
