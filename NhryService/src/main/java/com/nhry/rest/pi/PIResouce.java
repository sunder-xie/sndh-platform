package com.nhry.rest.pi;

import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.data.config.impl.NHSysCodeItemMapperImpl;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.dao.TVipCrmInfoService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.config.dao.DictionaryService;
import com.nhry.service.pi.dao.PIProductService;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.service.pi.dao.PIVipInfoDataService;
import com.nhry.service.pi.dao.SmsSendService;
import com.nhry.service.pi.pojo.MemberActivities;
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
import java.math.BigDecimal;
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
    @Autowired
    public TVipCrmInfoService tVipCrmInfoService;

    @Autowired
    public TVipCustInfoService tVipCustInfoService;

    @Autowired
    public PIVipInfoDataService piVipInfoDataService;

    @Autowired
    public DictionaryService dictionaryService;

    @Autowired
    public ResidentialAreaService residentialAreaService;

    @Autowired
    public SmsSendService smsSendService;


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
        return convertToRespModel(MessageCode.NORMAL, null, null);
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
    @GET
    @Path("/generateVipInfoData/{custId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/generateVipInfoData/{custId}", response = ResponseModel.class, notes = "获取订户信息数据")
    public Response generateVipInfoData(@PathParam("custId") String custId) throws RemoteException, ParseException {
        TVipCustInfo vipCustInfo = tVipCustInfoService.findVipCustByNoForUpt(custId);
        TMdBranch branch = branchService.selectBranchByNo(vipCustInfo.getBranchNo());
        vipCustInfo.setBranchName(branch.getBranchName());
        NHSysCodeItem codeItem = new NHSysCodeItem();
        codeItem.setTypeCode("1001");
//        codeItem.setItemCode(vipCustInfo.getProvince());
//        vipCustInfo.setProvince(dictionaryService.findCodeItenByCode(codeItem).getItemName());
        TMdResidentialArea area = residentialAreaService.selectById(vipCustInfo.getSubdist());
        vipCustInfo.setSubdist(area.getResidentialAreaTxt());

        codeItem.setItemCode(vipCustInfo.getCity());
        vipCustInfo.setCity(dictionaryService.findCodeItenByCode(codeItem).getItemName());

        codeItem.setItemCode(vipCustInfo.getCounty());
        vipCustInfo.setCounty(dictionaryService.findCodeItenByCode(codeItem).getItemName());

        java.util.List<TMdAddress> addresses = tVipCustInfoService.findCnAddressByCustNo(custId);
        TMdAddress tMdAddress = null;
        for(TMdAddress address : addresses){
           if("Y".equals(address.getIsDafault())) {
               tMdAddress = address;
           }
        }
        return convertToRespModel(MessageCode.NORMAL,piVipInfoDataService.generateVipInfoData(vipCustInfo,tMdAddress), null);
    }
    @POST
    @Path("/createMemberActivities")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/createMemberActivities", response = ResponseModel.class, notes = "会员积分活动")
    public Response createMemberActivities() throws RemoteException, ParseException {
        MemberActivities ac = new MemberActivities();
        ac.setActivitydate(new Date());
        ac.setAmount(new BigDecimal(110));
//        ac.setCardid("0000000323");
        ac.setCategory("ACCRUAL");
        ac.setCommit("X");
        ac.setItemnum("10");
//        ac.setCurrency("");
//        ac.setPoints(new BigDecimal(110));
        ac.setProcesstype("YPOS_ACCRUAL");
        ac.setMembershipguid("0050568ADF8B1EE696C390EAA540A600");
        ac.setOrderid("10000000022323232");
        ac.setProductid("000000023");
        ac.setProductquantity(new BigDecimal(100));
        ac.setRetailstoreid("0300005935");
        ac.setSalesorg("4111");
        return convertToRespModel(MessageCode.NORMAL, piVipInfoDataService.createMemberActivities(ac), null);
    }


    @POST
    @Path("/sendSubscriber/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/sendSubscriber/{code}", response = ResponseModel.class, notes = "发送订户数据")
    public Response sendSubscriber(@PathParam("code") String code){
        TVipCustInfo vipCustInfo = tVipCustInfoService.findVipCustByNoForUpt(code);
        return convertToRespModel(MessageCode.NORMAL, piVipInfoDataService.sendSubscriber(vipCustInfo), null);
    }
    @POST
    @Path("/sendSms/{tel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/sendSms/{tel}", response = ResponseModel.class, notes = "发送短信")
    public Response sendSms(@PathParam("tel") String tel){
        return convertToRespModel(MessageCode.NORMAL, smsSendService.sendMessage("",tel), null);
    }
}
