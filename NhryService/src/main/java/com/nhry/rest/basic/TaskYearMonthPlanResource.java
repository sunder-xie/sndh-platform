package com.nhry.rest.basic;

import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TaskYearMonthPlan;
import com.nhry.model.basic.MonthPlanModel;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.TaskYearMonthPlanService;
import com.nhry.utils.CodeGeneratorUtil;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by cbz on 8/12/2016.
 */
@Path("/plan")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/plan", description = "月任务")
public class TaskYearMonthPlanResource extends BaseResource {
    @Autowired
    private TaskYearMonthPlanService taskYearMonthPlanService;
    @POST
    @Path("/findPlan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/findPlan",notes = "查询公司奶站月任务计划",response = ResponseModel.class)
    public Response findPlanByYear(@ApiParam(name = "model",value = "model",required = false) MonthPlanModel model){
        return convertToRespModel(MessageCode.NORMAL,null,taskYearMonthPlanService.findPlanByYear(model));
    }

    @POST
    @Path("/savePlans")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/savePlans",notes = "批量保存奶站月任务计划",response = ResponseModel.class)
    public Response savePlans(@ApiParam(name="plans",value = "plans",required = true)List<TaskYearMonthPlan> plans){
        return convertToRespModel(MessageCode.NORMAL,null,taskYearMonthPlanService.savePlans(plans));
    }

    @POST
    @Path("/savePlan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/savePlan",notes = "保存奶站月任务计划",response = ResponseModel.class)
    public Response savePlans(@ApiParam(name="plan",value = "plan",required = true)TaskYearMonthPlan plan){
        return convertToRespModel(MessageCode.NORMAL,null,taskYearMonthPlanService.savePlan(plan));
    }

    @POST
    @Path("/exportTemplate")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/exportTemplate", response = OrderCreateModel.class, notes = "导出月任务模板")
    public Response exportTemplate(@ApiParam(name = "model",value = "model",required = false) MonthPlanModel model, @Context HttpServletRequest request, @Context HttpServletResponse response){
        List<TaskYearMonthPlan> plans = taskYearMonthPlanService.findPlanByYear(model);
        String url = request.getServletContext().getRealPath("/");
        String outUrl = "";
        String fileName = "MonthTaskTemplate.xlsx";
        try {
            File file = new File(url + File.separator + "report" + File.separator + "template" + File.separator + fileName);    //审批单
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(input));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(1);
            XSSFCell cell ;
            for(TaskYearMonthPlan plan : plans){
                cell = row.getCell(1);
                cell.setCellValue(plan.getBranckNo());
                cell = row.getCell(2);
                cell.setCellValue(plan.getBranchName());
                cell = row.getCell(3);
                cell.setCellValue(plan.getTaskJan().toString());
                cell = row.getCell(4);
                cell.setCellValue(plan.getTaskFeb().toString());
                cell = row.getCell(5);
                cell.setCellValue(plan.getTaskMar().toString());
                cell = row.getCell(6);
                cell.setCellValue(plan.getTaskApr().toString());
                cell = row.getCell(7);
                cell.setCellValue(plan.getTaskMay().toString());
                cell = row.getCell(8);
                cell.setCellValue(plan.getTaskJun().toString());
                cell = row.getCell(9);
                cell.setCellValue(plan.getTaskJul().toString());
                cell = row.getCell(10);
                cell.setCellValue(plan.getTaskAug().toString());
                cell = row.getCell(11);
                cell.setCellValue(plan.getTaskSep().toString());
                cell = row.getCell(12);
                cell.setCellValue(plan.getTaskOct().toString());
                cell = row.getCell(13);
                cell.setCellValue(plan.getTaskNov().toString());
                cell = row.getCell(14);
                cell.setCellValue(plan.getTaskDev().toString());
            }
            String fname = CodeGeneratorUtil.getCode();
            File export = new File(url +  File.separator + "report"+ File.separator + "export" + File.separator + fname + fileName);
            FileOutputStream stream = new FileOutputStream(export);
            workbook.write(stream);
            stream.flush();
            stream.close();
            
            outUrl = "/report/export/" + fname + fileName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertToRespModel(MessageCode.NORMAL,null,outUrl);
    }
}
