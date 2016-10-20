package com.nhry.rest.statistics;

import com.nhry.common.exception.MessageCode;
import com.nhry.model.statistics.BranchInfoModel;
import com.nhry.model.statistics.DistInfoModel;
import com.nhry.model.statistics.ExtendBranchInfoModel;
import com.nhry.model.statistics.RefuseResendDetailModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.statistics.dao.BranchInfoService;
import com.nhry.service.statistics.dao.DistributionInfoService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
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
    @POST
    @Path("/mstDispNumStat")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/mstDispNumStat}", response = ResponseModel.class, notes = "配送数量汇总-送奶员维度")
    public Response mstDispNumStat(@ApiParam(name = "model",value = "配送数量汇总-送奶员维度") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.mstDispNumStat(model));
    }
    @POST
    @Path("/branchMstDispNumStat")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branchMstDispNumStat}", response = ResponseModel.class, notes = "配送数量汇总-奶站维度")
    public Response branchMstDispNumStat(@ApiParam(name = "model",value = "配送数量汇总-奶站维度") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.branchMstDispNumStat(model));
    }
    @POST
    @Path("/dayMstDispNumStat")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/dayMstDispNumStat}", response = ResponseModel.class, notes = "奶站日配送数量汇总")
    public Response dayMstDispNumStat(@ApiParam(name = "model",value = "奶站日配送数量汇总") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.dayMstDispNumStat(model));
    }
    @POST
    @Path("/branchDayRepo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branchDayRepo}", response = ResponseModel.class, notes = "公司部门、经销商日统计送奶份数")
    public Response branchDayRepo(@ApiParam(name = "model",value = "公司部门、经销商日统计送奶份数") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.branchDayRepo(model));
    }
    @POST
    @Path("/branchDayQty")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branchDayQty}", response = ResponseModel.class, notes = "公司部门、经销商当日送奶份数 ")
    public Response branchDayQty(@ApiParam(name = "model",value = "公司部门、经销商当日送奶份数") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.branchDayQty(model));
    }

    @POST
    @Path("/Refuse2receiveResend")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/Refuse2receiveResend}", response = ResponseModel.class, notes = "拒收复送报表")
    public Response Refuse2receive(@ApiParam(name = "model",value = "公司部门、经销商当日送奶份数") ExtendBranchInfoModel model){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.Refuse2receiveResend(model));
    }

    @POST
    @Path("/Refuse2receiveResendDetail/{resendOrderNo}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/Refuse2receiveResendDetail/{resendOrderNo}", response = ResponseModel.class, notes = "拒收复送详情")
    public Response Refuse2receiveResendDetail(@ApiParam(name = "resendOrderNo",value = "单号") @PathParam("resendOrderNo") String resendOrderNo){
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.Refuse2receiveResendDetail(resendOrderNo));
    }
}
