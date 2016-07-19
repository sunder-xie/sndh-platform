package com.nhry.rest.statistics;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.model.statistics.BranchInfoModel;
import com.nhry.model.statistics.DistInfoModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.statistics.dao.BranchInfoService;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
@Path("/branchInfo")
@Controller
@Singleton
@Scope("request")
@Api(value = "/branchInfo",description = "奶站信息统计")
public class BranchInfoResource extends BaseResource {
    @Autowired
    private BranchInfoService branchInfoService;
    @Autowired
    private UserSessionService userSessionService;
    @POST
    @Path("/branchDayInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branchDayInfo}", response = ResponseModel.class, notes = "奶站日报表")
    public Response getYHD(@ApiParam(name = "model",value = "奶站日报") BranchInfoModel model){
        TSysUser user = userSessionService.getCurrentUser();
        if(user.getBranchNo()!=null){
            model.setBranchNo(user.getBranchNo());
        }else if(user.getDealerId() != null){
            model.setDealerId(user.getDealerId());
        }
        if(user.getSalesOrg() != null){
            model.setSalesOrg(user.getSalesOrg());
        }
        return convertToRespModel(MessageCode.NORMAL, null, branchInfoService.branchDayInfo(model));
    }

}
