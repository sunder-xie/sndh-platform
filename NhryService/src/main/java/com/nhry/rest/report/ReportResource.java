package com.nhry.rest.report;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.bill.domain.TMstRecvBill;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.domain.TMilkboxPlan;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.bill.CollectOrderBillModel;
import com.nhry.model.milk.RouteOrderModel;
import com.nhry.model.order.CollectOrderModel;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.order.ProductItem;
import com.nhry.model.statistics.BranchInfoModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.BranchEmpService;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.basic.pojo.BranchEmpModel;
import com.nhry.service.bill.dao.CustomerBillService;
import com.nhry.service.milk.dao.DeliverMilkService;
import com.nhry.service.order.dao.MilkBoxService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.service.statistics.dao.BranchInfoService;
import com.nhry.utils.CodeGeneratorUtil;
import com.nhry.utils.ExcelUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.PageOrder;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/27/2016.
 */
@Path("/report")
@Controller
@Singleton
@Scope("request")
@Api(value = "/report", description = "导出")
public class ReportResource extends BaseResource{
    private static Logger logger = Logger.getLogger(ReportResource.class);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private DeliverMilkService deliverMilkService;
    @Autowired
    private MilkBoxService milkBoxService;
    @Autowired
    private BranchEmpService branchEmpService;
    @Autowired
    private BranchInfoService branchInfoService;
    @Autowired
    private CustomerBillService customerBillService;
    @GET
    @Path("/reportCollect")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/reportCollect", response = OrderCreateModel.class, notes = "根据订单编号导出收款信息")
    public Response reportCollect(@ApiParam(required = true, name = "orderCode", value = "订单编号") @QueryParam("orderCode") String orderCode, @Context HttpServletRequest request, @Context HttpServletResponse response) {

        CollectOrderModel collect = orderService.queryCollectByOrderNo(orderCode);
        TSysUser user = userSessionService.getCurrentUser();
        String url = request.getServletContext().getRealPath("/");
        logger.info("realPath："+url);
        TMdAddress address = collect.getAddress();
        if(address == null) {
            return convertToRespModel(MessageCode.LOGIC_ERROR, "配送地址为空，请检查！", null);
        }
        TPreOrder order = collect.getOrder();
        TMstRecvBill bill = customerBillService.getRecBillByOrderNo(orderCode);
        if(bill == null ){
            bill =  customerBillService.createRecBillByOrderNo(orderCode);
        }

        TMdBranch branch = branchService.selectBranchByNo(order.getBranchNo());
        List<ProductItem> productItems =collect.getEntries();
        String outUrl = "";//request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        try {
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "CollectOrderTemplate.xlsx");    //审批单
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(1);
            XSSFCell cell = row.getCell(2);
            cell.setCellValue(order.getSalesOrgName());
            row = sheet.getRow(3);
            cell = row.getCell(1);

            cell.setCellValue("配送奶站："+order.getBranchName());
            cell = row.getCell(4);
           // 订奶起止日期：2016-7-21—  2016-8-23
            StringBuilder orderDate = new StringBuilder("订奶起止日期：");
            orderDate.append(format.format(order.getOrderDate()));
            orderDate.append("-");
            orderDate.append(format.format(order.getEndDate()));
            cell.setCellValue(orderDate.toString());
            cell = row.getCell(10);
            cell.setCellValue("送奶员："+order.getEmpName());
            row = sheet.getRow(4);
            cell = row.getCell(10);
            cell.setCellValue("送奶员电话："+order.getEmpTel());
            row = sheet.getRow(5);
            cell = row.getCell(1);
            cell.setCellValue("客户姓名："+order.getMilkmemberName());
            cell = row.getCell(3);
            cell.setCellValue("配送地址："+address.getResidentialAreaName()+" "+address.getAddressTxt());
            cell = row.getCell(12);
            cell.setCellValue("客户电话："+order.getCustomerTel());
            sheet.setForceFormulaRecalculation(true);
            BigDecimal sum = new BigDecimal(0);
            int num = 7;
            if(productItems != null){
                for(ProductItem item : productItems){
                    row = sheet.getRow(num);
                    cell = row.getCell(1);
                    cell.setCellValue(item.getMatnrTxt());
                    cell = row.getCell(5);
                    cell.setCellValue(item.getUnit());
                    cell = row.getCell(6);
                    cell.setCellValue(item.getBasePrice().toString());
                    cell = row.getCell(8);
                    cell.setCellValue(item.getQty());
                    cell = row.getCell(10);
                    cell.setCellValue(item.getTotalPrice().toString());
                    if(item.getTotalPrice()!=null)
                        sum = sum.add(item.getTotalPrice());
                    num++;
                }
            }
            row = sheet.getRow(13);
            cell=row.getCell(10);
            cell.setCellValue(bill.getAccAmt()==null ? "0" :String.valueOf(bill.getAccAmt()));
           // cell.setCellValue(order.getAcctAmt()==null ? "0" :String.valueOf(order.getAcctAmt()));
            row = sheet.getRow(14);
            cell = row.getCell(10);
            //首先账户合计减去账户余额 大于0后剩余金额为应收款,为负数时应收款应为0;
            if(order.getAcctAmt()!=null){
                sum = sum.subtract(order.getAcctAmt());//合计减去账户金额
                if(sum.compareTo(new BigDecimal("0"))==-1){
                    sum = new BigDecimal("0");
                }
            }
            cell.setCellValue(sum.toString());

            row = sheet.getRow(15);
            cell = row.getCell(1);
            cell.setCellValue("奶站地址："+ branch.getAddress());
            cell = row.getCell(7);
            cell.setCellValue("奶站电话："+ branch.getTel());

            row = sheet.getRow(18);
            cell = row.getCell(1);
            cell.setCellValue("打印日期："+ format.format(new Date()));
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "CollectOrder.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();

//            String mt = new MimetypesFileTypeMap().getContentType(targetFilePath);
//            return Response
//                    .ok(targetFilePath, mt)
//                    .header("Content-disposition","attachment;filename=" + targetFilePath.getName())
//                    .header("ragma", "No-cache").header("Cache-Control", "no-cache").build();
            outUrl = "/report/export/" + fname + "CollectOrder.xlsx";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }
    @GET
    @Path("/reportDeliver")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/reportDeliver", response = OrderCreateModel.class, notes = "根据路单编号导出路单信息")
    public Response reportDeliver(@ApiParam(required = true, name = "orderCode", value = "订单编号") @QueryParam("orderCode") String orderCode, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        List<TDispOrderItem> details = deliverMilkService.searchRouteOrderDetailAll(orderCode);
        RouteOrderModel model = deliverMilkService.searchRouteDetails(orderCode);
        String outUrl = "";
        String url = request.getServletContext().getRealPath("/");
        try{
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "DeliverMilkTemplate.xlsx");    //审批单
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            XSSFCell cell = row.getCell(1);
            TDispOrder order = model.getOrder();

            cell.setCellValue("配送奶站：" + order.getBranchName());
            cell = row.getCell(7);
            cell.setCellValue("");
            cell = row.getCell(9);
            cell.setCellValue("送奶员："+order.getDispEmpName());
            row = sheet.getRow(4);
            cell = row.getCell(7);
            cell.setCellValue(format.format(order.getOrderDate()));
            row = sheet.getRow(5);
            cell = row.getCell(7);
            cell.setCellValue(order.getReachTimeType()=="10"?"上午配送":"下午配送");
            row = sheet.getRow(6);
            cell = row.getCell(7);
            cell.setCellValue(model.getProducts());
            XSSFCellStyle styleBold = workbook.createCellStyle();
            styleBold.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            styleBold.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            styleBold.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            styleBold.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框

            int r = 10;
            if(details!=null){
                for(TDispOrderItem item : details) {
                    row = sheet.createRow(r);
                    cell = row.createCell(1);
                    cell.setCellStyle(styleBold);
                    cell.setCellValue(item.getAddressTxt());
                    cell = row.createCell(2);
                    cell.setCellValue(" ");
                    cell.setCellStyle(styleBold);
                    cell = row.createCell(3);
                    cell.setCellValue(" ");
                    cell.setCellStyle(styleBold);
                    cell = row.createCell(4);
                    cell.setCellValue(" ");//设置第五列为空字符串
                    cell.setCellStyle(styleBold);
                    sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 1, 4));

                    cell = row.createCell(5);
                    cell.setCellStyle(styleBold);
                    if (StringUtils.isNotEmpty(item.getMatnrTxt())){
                        cell.setCellValue(item.getMatnrTxt().concat("--").concat(item.getConfirmQty()==null?"" : item.getConfirmQty().toBigInteger().toString()));
                    }
                    cell = row.createCell(6);
                    cell.setCellStyle(styleBold);
                    cell.setCellValue(item.getCustTel());
                    cell = row.createCell(7);
                    cell.setCellStyle(styleBold);
                    cell.setCellValue(item.getCustName());
                    cell = row.createCell(8);
                    cell.setCellStyle(styleBold);
                    cell = row.createCell(9);
                    cell.setCellStyle(styleBold);
                    cell = row.createCell(10);
                    cell.setCellStyle(styleBold);
                    r++;
                }

                sheet.setForceFormulaRecalculation(true);
            }
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "DeliverMilk.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            outUrl = "/report/export/" + fname + "DeliverMilk.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }
    @GET
    @Path("/reportMilkBox/{empNo}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/reportMilkBox/{empNo}", response = OrderCreateModel.class, notes = "根据送奶员导出装箱列表信息")
    public Response reportMilkBox(@ApiParam(required = true, name = "empNo", value = "送奶员编号") @PathParam("empNo") String empNo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        List<TMilkboxPlan> details = milkBoxService.findMilkBox(empNo);
        BranchEmpModel empdata =  branchEmpService.empDetailInfo(empNo);
        String outUrl = "";
        String url = request.getServletContext().getRealPath("/");
        try {
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "MilkBoxTemplate.xlsx");
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(1);
            XSSFCell cell = row.getCell(1);
            cell.setCellValue(empdata.getEmp().getBranchName());
            cell = row.getCell(4);
            cell.setCellValue(empdata.getEmp().getEmpName());
            cell = row.getCell(7);
            cell.setCellValue(format.format(new Date()));
            int r = 3;
            if(details!=null){
                for(TMilkboxPlan item : details){
                    row = sheet.getRow(r);
                    cell = row.getCell(0);
                    cell.setCellValue(item.getMemberName());
                    cell= row.getCell(1);
                    cell.setCellValue(item.getMemberTel());
                    cell = row.getCell(2);
                    cell.setCellValue(item.getAdressNo());
                    cell = row.getCell(5);
                    cell.setCellValue(item.getProNum());
                    cell = row.getCell(6);
                    cell.setCellValue(item.getInitAmt().toString());
                    cell = row.getCell(7);
                    String paymentStatName="";
                    if(item.getPaymentmethod().equals("10")){
                        paymentStatName="后付款";
                    }else if(item.getPaymentmethod().equals("20")){
                        paymentStatName="预付款";
                    }else if(item.getPaymentmethod().equals("30")){
                        paymentStatName="垫付费";
                    }
                    cell.setCellValue(paymentStatName);
                    r++;
                }
            }
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "MilkBoxTemplate.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            outUrl = "/report/export/" + fname + "MilkBoxTemplate.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }
    @POST
    @Path("/findReqOrderOutput")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findReqOrderOutput}", response = ResponseModel.class, notes = "根据日期,经销商,奶站编号生成要货计划导出")
    public Response findReqOrderOutput(@ApiParam(name = "model",value = "output要货计划") BranchInfoModel model){
        List<Map<String,String>>  details = branchInfoService.findReqOrderOutput(model);
        String outUrl = "";
        String url = request.getServletContext().getRealPath("/");
        try {
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "ReqOrderTemplate.xlsx");
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell ;
            int r = 2;
            if(details!=null){
                for(Map<String,String> map : details){
                    row = sheet.createRow(r);r++;
                    cell=row.createCell(1);
                    cell.setCellValue(map.get("dealerName"));
                    cell=row.createCell(2);
                    cell.setCellValue(map.get("branchName"));
                    cell=row.createCell(3);
                    cell.setCellValue(map.get("matnrTxt"));

                    if(map.get("qty")!=null){
                        cell=row.createCell(4);
                        cell.setCellValue(String.valueOf(map.get("qty")));
                    }
                    if(map.get("increQty")!=null){
                        cell=row.createCell(5);
                        cell.setCellValue(String.valueOf(map.get("increQty")));
                    }
                    if(map.get("sumQty")!=null){
                        cell=row.createCell(6);
                        cell.setCellValue(String.valueOf(map.get("sumQty")));
                    }
                    if(map.get("bl")!=null){
                        cell=row.createCell(7);
                        cell.setCellValue(String.valueOf(map.get("bl")));
                    }
                    cell=row.createCell(8);
                    cell.setCellValue(map.get("flag"));
                }
            }
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "ReqOrderTemplate.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            outUrl = "/report/export/" + fname + "ReqOrderTemplate.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }
    @POST
    @Path("/branchDayOutput")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/branchDayOutput}", response = ResponseModel.class, notes = "奶站日报表")
    public Response branchDayOutput(@ApiParam(name = "model",value = "奶站日报") BranchInfoModel model){
        List<Map<String,String>>  details = branchInfoService.branchDayOutput(model);
        String outUrl = "";
        String url = request.getServletContext().getRealPath("/");
        try{
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "DayReportTemplate.xlsx");
            FileInputStream input = new FileInputStream(file);
            com.nhry.utils.date.Date date1 = new com.nhry.utils.date.Date(model.getTheDate());
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row=sheet.getRow(1);
            XSSFCell cell = row.getCell(4);
            cell.setCellValue(date1.addDays(-4).toShortDate());
            cell = row.getCell(5);
            cell.setCellValue(date1.addDays(-3).toShortDate());
            cell = row.getCell(6);
            cell.setCellValue(date1.addDays(-2).toShortDate());
            cell = row.getCell(7);
            cell.setCellValue(date1.addDays(-1).toShortDate());
            cell = row.getCell(8);
            cell.setCellValue(format.format(model.getTheDate()));
            int r = 2;
            if(details!=null){
                for(Map<String,String> map : details){
                    row = sheet.createRow(r);r++;
/*                    cell=row.createCell(1);
                    cell.setCellValue(map.get("branchNo"));*/
                    cell=row.createCell(2);
                    cell.setCellValue(map.get("branchName"));
                    cell=row.createCell(3);
                    String groupName="";
                    if(map.get("branchGroup").equals("01")){
                        groupName = "自营奶站";
                    }else if(map.get("branchGroup").equals("02")){
                        groupName = "经销商奶站";
                    }
                    cell.setCellValue(groupName);
                    cell = row.createCell(4);
                    cell.setCellValue(map.get("date1")==null ? "0" :String.valueOf(map.get("date1")));
                    cell = row.createCell(5);
                    cell.setCellValue(map.get("date2")==null ? "0" :String.valueOf(map.get("date2")));
                    cell = row.createCell(6);
                    cell.setCellValue(map.get("date3")==null ? "0" :String.valueOf(map.get("date3")));
                    cell = row.createCell(7);
                    cell.setCellValue(map.get("date4")==null ? "0" :String.valueOf(map.get("date4")));
                    cell = row.createCell(8);
                    cell.setCellValue(map.get("date5")==null ? "0" :String.valueOf(map.get("date5")));
                    cell = row.createCell(9);
                    cell.setCellValue(map.get("hb")==null ? "0" :String.valueOf(map.get("hb")));

                }
            }
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "DayReportTemplate.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            outUrl = "/report/export/" + fname + "DayReportTemplate.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }

    @POST
    @Path("/findMonthReportOutput")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findMonthReportOutput}", response = ResponseModel.class, notes = "奶站月任务报表导出")
    public Response findMonthReportOutput(@ApiParam(name = "model",value = "月任务") BranchInfoModel model){
        List<Map<String,String>>  details = branchInfoService.findBranchMonthReportOutput(model);
        String outUrl = "";
        String url = request.getServletContext().getRealPath("/");
        try {
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "MonthReportTemplate.xlsx");
            FileInputStream input = new FileInputStream(file);
            com.nhry.utils.date.Date date1 = new com.nhry.utils.date.Date(model.getTheDate());
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            int r = 2;
            if(details!=null){
                for(Map<String,String> map : details){
                    row = sheet.createRow(r);r++;
                    cell=row.createCell(1);
                    cell.setCellValue(map.get("branchName"));
                    cell=row.createCell(2);
                    String groupName="";
                    if(map.get("branchGroup").equals("01")){
                        groupName = "自营奶站";
                    }else if(map.get("branchGroup").equals("02")){
                        groupName = "经销商奶站";
                    }
                    cell.setCellValue(groupName);
                    cell=row.createCell(4);
                    cell.setCellValue(map.get("dayQty")==null ? "0" :String.valueOf(map.get("dayQty")));
                    cell=row.createCell(5);
                    cell.setCellValue(map.get("monthQty")==null ? "0" :String.valueOf(map.get("monthQty")));
                    cell=row.createCell(6);
                    cell.setCellValue(map.get("hb")==null ? "0" :String.valueOf(map.get("hb")));
                    cell=row.createCell(7);
                    cell.setCellValue(map.get("newQty")==null ? "0" :String.valueOf(map.get("newQty")));
                    cell=row.createCell(8);
                    cell.setCellValue(map.get("dayWeigth")==null ? "0" :String.valueOf(map.get("dayWeigth")));
                    cell=row.createCell(9);
                    cell.setCellValue(map.get("dayPrice")==null ? "0" :String.valueOf(map.get("dayPrice")));
                    cell=row.createCell(10);
                    cell.setCellValue(map.get("montyPrice")==null ? "0" :String.valueOf(map.get("montyPrice")));
                }
            }
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "MonthReportTemplate.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            outUrl = "/report/export/" + fname + "MonthReportTemplate.xlsx";

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }

    @POST
    @Path("/findOrderRatioOupput")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findOrderRatioOupput}", response = ResponseModel.class, notes = "奶站订单转化率(T+3)报表导出")
    public Response findOrderRatioOupput(@ApiParam(name = "model",value = "订单转化率") BranchInfoModel model){
        List<Map<String,String>>  details = branchInfoService.findOrderRatioOutput(model);
        String outUrl = "";
        String url = request.getServletContext().getRealPath("/");
        try {
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "OrderRatioTemplate.xlsx");
            FileInputStream input = new FileInputStream(file);
            com.nhry.utils.date.Date date1 = new com.nhry.utils.date.Date(model.getTheDate());
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row=sheet.getRow(1);
            XSSFCell cell = row.getCell(3);
            cell.setCellValue(format.format(model.getTheDate()));
            cell = row.getCell(4);
            cell.setCellValue(date1.addDays(-3).toShortDate());
            int r=2;
            if(details!=null){
                for(Map<String,String> map : details){
                    row = sheet.createRow(r);r++;
                    cell=row.createCell(1);
                    cell.setCellValue(map.get("branchName"));
                    cell=row.createCell(2);
                    String groupName="";
                    if(map.get("branchGroup").equals("01")){
                        groupName = "自营奶站";
                    }else if(map.get("branchGroup").equals("02")){
                        groupName = "经销商奶站";
                    }
                    cell.setCellValue(groupName);
                    cell = row.createCell(3);
                    cell.setCellValue(map.get("validQty")==null ? "0" :String.valueOf(map.get("validQty")));
                    cell = row.createCell(4);
                    cell.setCellValue(map.get("initQty")==null ? "0" :String.valueOf(map.get("initQty")));
                    cell = row.createCell(5);
                    cell.setCellValue(map.get("infoErrQty")==null ? "0" :String.valueOf(map.get("infoErrQty")));
                    cell = row.createCell(6);
                    cell.setCellValue(map.get("custQty")==null ? "0" :String.valueOf(map.get("custQty")));
                    cell = row.createCell(7);
                    cell.setCellValue(map.get("superQty")==null ? "0" :String.valueOf(map.get("superQty")));
                    cell = row.createCell(8);
                    cell.setCellValue(map.get("otherQty")==null ? "0" :String.valueOf(map.get("otherQty")));
                    cell = row.createCell(9);
                    cell.setCellValue(map.get("ratio")==null ? "0" :String.valueOf(map.get("ratio")));
                }
            }
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "OrderRatioTemplate.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            outUrl = "/report/export/" + fname + "OrderRatioTemplate.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }

    @GET
    @Path("/reportCollectByEmp")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/reportCollectByEmp", response = OrderCreateModel.class, notes = "根据订单编号导出收款信息")
    public Response reportCollectByEmp(@ApiParam(required = true, name = "empNo", value = "送奶员编号") @QueryParam("empNo") String empNo, @Context HttpServletRequest request, @Context HttpServletResponse response) {
//        CollectOrderModel collect = orderService.queryCollectByOrderNo(empNo);
//        TSysUser user = userSessionService.getCurrentUser();
        CollectOrderBillModel orderBillModel = customerBillService.queryCollectByOrderNo(empNo);
        TSysUser user = userSessionService.getCurrentUser();
        List<ProductItem> productItems = orderBillModel.getEntries();
        String url = request.getServletContext().getRealPath("/");
//        logger.info("realPath："+url);
//        TMdAddress address = collect.getAddress();
//        if(address == null) {
//            return convertToRespModel(MessageCode.LOGIC_ERROR, "配送地址为空，请检查！", null);
//        }
//        TPreOrder order = collect.getOrder();
//        TMstRecvBill bill = customerBillService.getRecBillByOrderNo(empNo);
//
//        TMdBranch branch = branchService.selectBranchByNo(order.getBranchNo());
//        List<ProductItem> productItems =collect.getEntries();
        String outUrl = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet");
//            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "CollectOrderTemplate.xlsx");    //审批单
//            FileInputStream input = new FileInputStream(file);
//            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
//            XSSFSheet sheet = workbook.getSheetAt(0);
            sheet.setDefaultColumnWidth((short)8);
            sheet.setColumnWidth(0,(short)3 * 100);
            sheet.setColumnWidth(14,(short)3 * 100);

            int rowNum = 0;
            for(int i=0;i<3;i++) {
                XSSFRow rownull = sheet.createRow(rowNum++);
                rownull.setHeightInPoints((float) 8);

                XSSFRow row = sheet.createRow(rowNum++);
                XSSFCell cell = row.createCell(1);
                cell.setCellValue("四川新华西乳业有限公司销售部");
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();
                font.setFontName("微软雅黑");
                font.setFontHeightInPoints((short) 12);
                cellStyle.setFont(font);
                cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell.setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 1, 13));

                XSSFRow row1 = sheet.createRow(rowNum++);
                XSSFCell cell1 = row1.createCell(1);
                cell1.setCellValue("订奶专用收据");
                XSSFCellStyle cellStyle1 = workbook.createCellStyle();
                XSSFFont font1 = workbook.createFont();
                font1.setFontName("微软雅黑");
                font1.setFontHeightInPoints((short) 10);
                cellStyle1.setFont(font1);
                cellStyle1.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell1.setCellStyle(cellStyle1);
                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 1, 13));

                XSSFRow row2 = sheet.createRow(rowNum++);
                XSSFRow row3 = sheet.createRow(rowNum++);
                XSSFCell cell2_1 = row2.createCell(1);
                cell2_1.setCellValue("配送奶站："+orderBillModel.getBranchName());
                XSSFCellStyle cellStyle2 = workbook.createCellStyle();
                XSSFFont font2 = workbook.createFont();
                font2.setFontName("微软雅黑");
                font2.setFontHeightInPoints((short) 10);
                cellStyle2.setFont(font2);
                cellStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell2_1.setCellStyle(cellStyle2);
                sheet.addMergedRegion(new CellRangeAddress(row2.getRowNum(), row2.getRowNum() + 1, 1, 4));

                XSSFCell cell2_4 = row2.createCell(4);
                cell2_4.setCellValue("订奶起止日期：2016-08-12--2016-08-31");
                XSSFCellStyle cellStyle2_4 = workbook.createCellStyle();
                XSSFFont font2_4 = workbook.createFont();
                font2_4.setFontName("微软雅黑");
                font2_4.setFontHeightInPoints((short) 10);
                cellStyle2_4.setFont(font2_4);
                cellStyle2_4.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle2_4.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell2_4.setCellStyle(cellStyle2_4);
                sheet.addMergedRegion(new CellRangeAddress(row2.getRowNum(), row2.getRowNum() + 1, 5, 9));

                XSSFCell cell2_10 = row2.createCell(10);
                cell2_10.setCellValue("送奶员："+orderBillModel.getEmpName());
                sheet.addMergedRegion(new CellRangeAddress(row2.getRowNum(), row2.getRowNum(), 10, 13));

                XSSFCell cell3_10 = row3.createCell(10);
                cell3_10.setCellValue("送奶员电话："+orderBillModel.getEmpTel());
                sheet.addMergedRegion(new CellRangeAddress(row3.getRowNum(), row3.getRowNum(), 10, 13));

                XSSFRow row4 = sheet.createRow(rowNum++);
                XSSFCell cell4_1 = row4.createCell(1);
                sheet.addMergedRegion(new CellRangeAddress(row4.getRowNum(), row4.getRowNum(), 1, 2));
                cell4_1.setCellValue("客户姓名："+orderBillModel.getVipName());

                XSSFCell cell4_3 = row4.createCell(3);
                cell4_3.setCellValue("配送地址："+orderBillModel.getCustAddress());
                sheet.addMergedRegion(new CellRangeAddress(row4.getRowNum(), row4.getRowNum(), 3, 10));

                XSSFCell cell4_12 = row4.createCell(11);
                cell4_12.setCellValue("客户电话："+orderBillModel.getEmpTel());
                sheet.addMergedRegion(new CellRangeAddress(row4.getRowNum(), row4.getRowNum(), 11, 13));

                XSSFRow row5 = sheet.createRow(rowNum++);
                XSSFCell cell5_1 = row5.createCell(1);
                cell5_1.setCellValue("产品");
                XSSFCellStyle cellStyle5_1 = workbook.createCellStyle();
//            XSSFFont font2 = workbook.createFont();
//            font2.setFontName("微软雅黑");
//            font2.setFontHeightInPoints((short)10);
//            cellStyle5_1.setFont(font2);
                cellStyle5_1.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle5_1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell5_1.setCellStyle(cellStyle5_1);
                sheet.addMergedRegion(new CellRangeAddress(row5.getRowNum(), row5.getRowNum(), 1, 4));

                XSSFCell cell5_5 = row5.createCell(5);
                cell5_5.setCellValue("单位");
                XSSFCellStyle cellStyle5_5 = workbook.createCellStyle();
                cellStyle5_5.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle5_5.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell5_5.setCellStyle(cellStyle5_5);
//            sheet.addMergedRegion(new CellRangeAddress(row5.getRowNum(), row5.getRowNum(), 1, 4));
                XSSFCell cell5_6 = row5.createCell(6);
                cell5_6.setCellValue("单价");
                XSSFCellStyle cellStyle5_6 = workbook.createCellStyle();
                cellStyle5_6.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle5_6.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell5_6.setCellStyle(cellStyle5_6);

                XSSFCell cell5_7 = row5.createCell(7);
                cell5_7.setCellValue("数量");
                XSSFCellStyle cellStyle5_7 = workbook.createCellStyle();
                cellStyle5_7.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle5_7.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell5_7.setCellStyle(cellStyle5_7);
                sheet.addMergedRegion(new CellRangeAddress(row5.getRowNum(), row5.getRowNum(), 7, 8));

                XSSFCell cell5_9 = row5.createCell(9);
                cell5_9.setCellValue("金额");
                XSSFCellStyle cellStyle5_9 = workbook.createCellStyle();
                cellStyle5_9.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle5_9.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell5_9.setCellStyle(cellStyle5_9);
                sheet.addMergedRegion(new CellRangeAddress(row5.getRowNum(), row5.getRowNum(), 9, 10));

                XSSFCell cell5_11 = row5.createCell(11);
                cell5_11.setCellValue("产品备注");
                XSSFCellStyle cellStyle5_11 = workbook.createCellStyle();
                cellStyle5_11.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle5_11.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell5_11.setCellStyle(cellStyle5_11);
                sheet.addMergedRegion(new CellRangeAddress(row5.getRowNum(), row5.getRowNum(), 11, 13));

                for(ProductItem item : productItems){
                    XSSFRow rows = ExcelUtil.createRow(sheet,rowNum++);
                    ExcelUtil.createCell(rows,1,item.getMatnrTxt(),ExcelUtil.getCellStyle3(workbook));
                    ExcelUtil.addMergedRegion(sheet,rows.getRowNum(),rows.getRowNum(),1,4);
                    ExcelUtil.createCell(rows,5,item.getUnit(),ExcelUtil.getCellStyle2(workbook));
                    ExcelUtil.createCell(rows,6,item.getBasePrice().toString(),ExcelUtil.getCellStyle2(workbook));
                    ExcelUtil.createCell(rows,7,String.valueOf(item.getQty()),ExcelUtil.getCellStyle2(workbook));
                    ExcelUtil.addMergedRegion(sheet,rows.getRowNum(),rows.getRowNum(),7,8);
                    ExcelUtil.createCell(rows,9,String.valueOf(item.getTotalPrice()),ExcelUtil.getCellStyle2(workbook));
                    ExcelUtil.addMergedRegion(sheet,rows.getRowNum(),rows.getRowNum(),9,10);
                    ExcelUtil.createCell(rows,11,"",ExcelUtil.getCellStyle2(workbook));
                    ExcelUtil.addMergedRegion(sheet,rows.getRowNum(),rows.getRowNum(),11,13);
                }
                if(productItems.size() < 8){
                    rowNum=rowNum + 8 - productItems.size();
                }
                XSSFRow row14 = sheet.createRow(rowNum++);
                XSSFCell cell14_1 = row14.createCell(1);
                cell14_1.setCellValue("账号余额");
                XSSFCellStyle cellStyle14_1 = workbook.createCellStyle();
                cellStyle14_1.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle14_1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell14_1.setCellStyle(cellStyle14_1);
                sheet.addMergedRegion(new CellRangeAddress(row14.getRowNum(), row14.getRowNum(), 1, 9));

                XSSFCell cell14_10 = row14.createCell(10);
                cell14_10.setCellValue("100");

                XSSFRow row15 = sheet.createRow(rowNum++);
                XSSFCell cell15_1 = row15.createCell(1);
                cell15_1.setCellValue("应收款");
                XSSFCellStyle cellStyle15_1 = workbook.createCellStyle();
                cellStyle15_1.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                cellStyle15_1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                cell15_1.setCellStyle(cellStyle15_1);
                sheet.addMergedRegion(new CellRangeAddress(row15.getRowNum(), row15.getRowNum(), 1, 9));

                XSSFCell cell15_10 = row15.createCell(10);
                cell15_10.setCellValue("10");


                XSSFRow row16 = sheet.createRow(rowNum++);

                XSSFCell cell16_1 = row16.createCell(1);
                cell16_1.setCellValue("奶站地址："+orderBillModel.getBranchAddress());
                sheet.addMergedRegion(new CellRangeAddress(row16.getRowNum(), row16.getRowNum(), 1, 6));

                XSSFCell cell16_7 = row16.createCell(7);
                cell16_7.setCellValue("奶站电话："+orderBillModel.getBranchTel());
                sheet.addMergedRegion(new CellRangeAddress(row16.getRowNum(), row16.getRowNum(), 7, 10));

                XSSFCell cell16_11 = row16.createCell(11);
                cell16_11.setCellValue("公司电话：4008850555");
                sheet.addMergedRegion(new CellRangeAddress(row16.getRowNum(), row16.getRowNum(), 11, 13));

                XSSFRow row17 = sheet.createRow(rowNum++);
                XSSFCell cell17_1 = row17.createCell(1);
                cell17_1.setCellValue("备注：");
                sheet.addMergedRegion(new CellRangeAddress(row17.getRowNum(), row17.getRowNum() + 1, 1, 13));
                rowNum++;
                XSSFRow row19 = sheet.createRow(rowNum++);
                XSSFCell cell19_1 = row19.createCell(1);

                cell19_1.setCellValue("打印日期："+format.format(new Date()));
                sheet.addMergedRegion(new CellRangeAddress(row19.getRowNum(), row19.getRowNum(), 1, 9));

                XSSFCell cell19_10 = row19.createCell(10);
                cell19_10.setCellValue("客户签字：");
                sheet.addMergedRegion(new CellRangeAddress(row19.getRowNum(), row19.getRowNum(), 10, 13));

                XSSFRow rowend = sheet.createRow(rowNum++);
                rowend.setHeightInPoints((float) 15);
            }
            workbook.setPrintArea(
                    0, //sheet index
                    0, //start column
                    14, //end column
                    0, //start row
                    rowNum //end row
            );
            XSSFPrintSetup ps = sheet.getPrintSetup();
            ps.setPaperSize((short)0);
            sheet.setPrintGridlines(true);
            sheet.setDisplayGridlines(true);
            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url +  File.separator + "report"+ File.separator + "export";
            File delFiles = new File(filePath);
            if(delFiles.isDirectory()){
                for(File del : delFiles.listFiles()){
                    if(del.getName().contains(rq)){
                        del.delete();
                    }
                }
            }
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + "CollectOrder.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();

//            String mt = new MimetypesFileTypeMap().getContentType(targetFilePath);
//            return Response
//                    .ok(targetFilePath, mt)
//                    .header("Content-disposition","attachment;filename=" + targetFilePath.getName())
//                    .header("ragma", "No-cache").header("Cache-Control", "no-cache").build();
            outUrl = "/report/export/" + fname + "CollectOrder.xlsx";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }
    @POST
    @Path("/exportOrderByModel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/exportOrderByModel}", response = ResponseModel.class, notes = "导出奶站分奶表")
    public Response exportOrderByModel(@ApiParam(name = "model",value = "路单") BranchInfoModel model){
        String url = request.getServletContext().getRealPath("/");
        String outUrl = "";
        try {
            if(StringUtils.isEmpty(model.getBranchNo())){
                TSysUser user = userSessionService.getCurrentUser();
                model.setBranchNo(user.getBranchNo());
            }
            List<Map<String, String>> orders = branchInfoService.exportOrderByModel(model);
            File file = new File(url +  File.separator + "report"+ File.separator + "template" + File.separator + "FnbTemplate.xlsx");    //审批单
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            XSSFSheet sheet = workbook.createSheet("sheet");
            int rowNum = 0;
            Map<String, String> projectMap = new HashMap<String, String>();
            Map<String, String> empMap = new HashMap<String, String>();
            for (Map<String, String> map : orders) {
                projectMap.put(map.get("CONFIRM_MATNR"), map.get("MATNR_TXT"));
                empMap.put(map.get("DISP_EMP_NO"), map.get("EMP_NAME"));
            }
            int columnNum = 1;
            XSSFRow row1 = sheet.getRow(0);

            for (Map.Entry<String, String> entry : projectMap.entrySet()) {
                XSSFCell cell = row1.createCell(columnNum++);
                cell.setCellValue(entry.getValue());
                cell.setCellStyle(ExcelUtil.setBorderStyle(workbook));
            }
            rowNum++;
            for (Map.Entry<String, String> entry : empMap.entrySet()) {
                int cNum = 0;
                XSSFRow row = sheet.createRow(rowNum++);
                XSSFCell cell = row.createCell(cNum++);
                String empNo = entry.getKey();
                cell.setCellValue(entry.getValue());
                cell.setCellStyle(ExcelUtil.setBorderStyle(workbook));
                for (Map.Entry<String, String> entry1 : projectMap.entrySet()) {
                    XSSFCell cell1 = row.createCell(cNum++);
                    cell1.setCellStyle(ExcelUtil.setBorderStyle(workbook));
                    String matnr = entry1.getKey();
//                    cell1.setCellValue(matnr);
                    for (Map<String, String> map : orders) {
                        String matnrT =map.get("CONFIRM_MATNR");
                        String empNoT = map.get("DISP_EMP_NO");
                        if(empNo.equals(empNoT) && matnr.equals(matnrT)){
                            if(map.get("CONFIRM_QTY")!=null) {
                                cell1.setCellValue(new BigDecimal(String.valueOf(map.get("CONFIRM_QTY"))).intValue());
                            }
                        }
                    }
                }
            }

            String fname = CodeGeneratorUtil.getCode();
            String rq = format1.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
            String filePath = url + File.separator + "report" + File.separator + "export";
            File delFiles = new File(filePath);
            if (delFiles.isDirectory()) {
                for (File del : delFiles.listFiles()) {
                    if (del.getName().contains(rq)) {
                        del.delete();
                    }
                }
            }
            File export = new File(url + File.separator + "report" + File.separator + "export" + File.separator + fname + "fnb.xlsx");
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            outUrl = "/report/export/" + fname + "fnb.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }

}
