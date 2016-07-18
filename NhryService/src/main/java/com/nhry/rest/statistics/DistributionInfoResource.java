package com.nhry.rest.statistics;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.statistics.DistInfoModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.statistics.dao.DistributionInfoService;
import com.sun.jersey.spi.resource.Singleton;
import com.sun.xml.internal.rngom.parse.host.Base;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by cbz on 7/16/2016.
 */
@Path("/dist")
@Controller
@Singleton
@Scope("request")
@Component
@Api(value = "/dist",description = "配送信息统计")
public class DistributionInfoResource extends BaseResource{
    @Autowired
    private DistributionInfoService distributionInfoService;
    @Autowired
    private UserSessionService userSessionService;
    @POST
    @Path("/findDifferInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findDifferInfo}", response = ResponseModel.class, notes = "路单配送差异明细")
    public Response getYHD(@ApiParam(name = "model",value = "路单配送") DistInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(user.getBranchNo()!=null){
            model.setBranchNo(user.getBranchNo());
        }else if(user.getDealerId() != null){
            model.setDealerId(user.getDealerId());
        }
        if(user.getSalesOrg() != null){
            model.setSalesOrg(user.getSalesOrg());
        }
        return convertToRespModel(MessageCode.NORMAL, null, distributionInfoService.findDistDifferInfo(model));
    }




}
