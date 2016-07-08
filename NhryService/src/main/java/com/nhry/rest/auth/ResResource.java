package com.nhry.rest.auth;

import com.alibaba.druid.filter.AutoLoad;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysResource;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.auth.dao.ResourceService;
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
import java.util.List;

/**
 * Created by cbz on 6/13/2016.
 */
@Path("/res")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/res", description = "资源信息维护")
public class ResResource extends BaseResource {
    @Autowired
    private ResourceService resService;

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/add", response = ResponseModel.class, notes = "添加资源")
    public Response addRes(@ApiParam(required = true, name = "resource", value = "资源对象")TSysResource resource) {
        return convertToRespModel(MessageCode.NORMAL, null, resService.addRes(resource));
    }
    
    @POST
    @Path("/lists")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/lists", response = ResponseModel.class, notes = "获取所有的资源列表")
    public Response getAllResources() {
        return convertToRespModel(MessageCode.NORMAL, null, resService.getAllResources());
    }

    @POST
    @Path("/get/{resCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/get/{resCode}", response = ResponseModel.class, notes = "查找资源")
    public Response addRes(@ApiParam(required = true, name = "resCode", value = "资源编号")@PathParam("resCode")String resCode) {
        return convertToRespModel(MessageCode.NORMAL, null, resService.selectResByCode(resCode));
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/update", response = ResponseModel.class, notes = "修改资源")
    public Response updateResByCode(@ApiParam(required = true, name = "resource", value = "资源对象")TSysResource resource) {
        return convertToRespModel(MessageCode.NORMAL, null, resService.updateResByCode(resource));
    }

    @POST
    @Path("/delete/{resCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/update/{resCode}", response = ResponseModel.class, notes = "删除资源")
    public Response deleteRes(@ApiParam(required = true, name = "resCode", value = "资源编码")@PathParam("resCode")String resCode) {
        return convertToRespModel(MessageCode.NORMAL, null, resService.deleteResByCode(resCode));
    }
    
    @POST
    @Path("/find/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/find/{userId}", response = ResponseModel.class, notes = "根据用户编码查询资源信息")
    public Response findResourceByUserId(@ApiParam(required = true, name = "userId", value = "用户编码")@PathParam("userId")String userId){
        return convertToRespModel(MessageCode.NORMAL, null, resService.findRecoureByUserId(userId));
    }
}
