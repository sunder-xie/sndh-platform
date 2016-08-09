package com.nhry.rest.statistics;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.statistics.BranchInfoModel;
import com.nhry.model.statistics.DistInfoModel;
import com.nhry.model.statistics.ExtendBranchInfoModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.statistics.dao.BranchInfoService;
import com.nhry.service.statistics.dao.DistributionInfoService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by cbz on 7/18/2016.
 */
@Path("/statistics")
@Controller
@Singleton
@Scope("request")
@Api(value = "/statistics",description = "统计")
public class StatisticsResource extends BaseResource {
    @Autowired
    private BranchInfoService branchInfoService;
    @Autowired
    private DistributionInfoService distributionInfoService;
    @POST
    @Path("/branchDayInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branchDayInfo}", response = ResponseModel.class, notes = "奶站日报表")
    public Response branchDayInfo(@ApiParam(name = "model",value = "奶站日报") BranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.branchDayInfo(model));
    }
    @POST
    @Path("/findDifferInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findDifferInfo}", response = ResponseModel.class, notes = "路单配送差异明细")
    public Response findDifferInfo(@ApiParam(name = "model",value = "路单配送") DistInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, distributionInfoService.findDistDifferInfo(model));
    }

    @POST
    @Path("/findOrderRatio")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findOrderRatio}", response = ResponseModel.class, notes = "奶站订单转化率(T+3)报表")
    public Response findOrderRatio(@ApiParam(name = "model",value = "订单转化率") BranchInfoModel model){

        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.findOrderRatio(model));
    }
    @POST
    @Path("/findMonthReport")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findMonthReport}", response = ResponseModel.class, notes = "奶站月任务报表")
    public Response findMonthReport(@ApiParam(name = "model",value = "月任务") BranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.findBranchMonthReport(model));
    }
    @POST
    @Path("/findReqOrder")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findReqOrder}", response = ResponseModel.class, notes = "要货计划查询")
    public Response findReqOrder(@ApiParam(name = "model",value = "要货计划") BranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.findReqOrder(model));
    }
    @POST
    @Path("/findChangeplanStatReport")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findChangeplanStatReport}", response = ResponseModel.class, notes = "换货差明细")
    public Response findChangeplanStatReport(@ApiParam(name = "model",value = "换货差异") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.findChangeplanStatReport(model));
    }
    @POST
    @Path("/returnBoxStatReport")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/returnBoxStatReport}", response = ResponseModel.class, notes = "回瓶汇总")
    public Response returnBoxStatReport(@ApiParam(name = "model",value = "回瓶汇总") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.returnBoxStatReport(model));
    }
}
