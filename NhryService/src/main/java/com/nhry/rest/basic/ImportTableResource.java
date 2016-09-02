package com.nhry.rest.basic;

import com.nhry.common.exception.MessageCode;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.data.basic.domain.TaskYearMonthPlan;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.utils.PrimaryKeyUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huaguan on 2016/8/30.
 */
@Path("/importTable")
@Component
@Scope("request")
@Singleton
@Controller
@Api(value = "/importTable", description = "导入历史数据")
public class ImportTableResource extends BaseResource {

    @Autowired
    private ResidentialAreaService residentialAreaService;
    @Autowired
    private TVipCustInfoService tVipCustInfoService;

    @POST
    @Path("/importResidentialArea")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importResidentialArea", response = ResponseModel.class, notes = "导入小区主数据")
    public Response importResidentialArea(FormDataMultiPart form, @Context HttpServletRequest request) throws IOException {
        String url = request.getServletContext().getRealPath("/");
        FormDataBodyPart filePart = form.getField("file");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        String source = formDataContentDisposition.getFileName();
        if (!source.endsWith("xlsx")) {
            return convertToRespModel(MessageCode.LOGIC_ERROR, "文件类型错误！", "");
        }
        String result = new String(source.getBytes("ISO8859-1"), "UTF-8");
        String filePath = url + File.separator + "report" + File.separator + "template" + File.separator + result;
        File file = new File(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        List<TMdResidentialArea> areas = new ArrayList<TMdResidentialArea>();//配送区域
        for (int i = 2; i <= rowNum; i++){
            TMdResidentialArea area = new TMdResidentialArea();

            int j = 1;
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(j++);
            //start 导入小区信息
            area.setId(cell.toString());//主键编号
            cell = row.getCell(j++);
            area.setResidentialAreaTxt(cell.toString());
            cell = row.getCell(j++);
            area.setProvince(cell.toString());
            cell = row.getCell(j++);
            area.setCity(cell.toString());
            cell = row.getCell(j++);
            area.setCounty(cell.toString());
            cell = row.getCell(j++);
            area.setAddressTxt(cell.toString());
            cell = row.getCell(j++);
            area.setSalesOrg(cell.toString());

            areas.add(area);
        }
        return convertToRespModel(MessageCode.NORMAL, null,residentialAreaService.addResidentialAreas(areas));
    }
    @POST
    @Path("/importVipcustInfo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importVipcustInfo", response = ResponseModel.class, notes = "导入订户主数据")
    public Response importVipcustInfo(FormDataMultiPart form, @Context HttpServletRequest request) throws IOException {
        FormDataBodyPart filePart = form.getField("file");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        List<TVipCustInfo> vipcusts = new ArrayList<TVipCustInfo>();//订户
        for (int i = 2; i <= rowNum; i++){
            TVipCustInfo vipcust = new TVipCustInfo();
            int j = 1;
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(j++);
            //start 导入小区信息
            vipcust.setVipCustNo(cell.toString());//主键编号
            cell = row.getCell(j++);
            vipcust.setVipName(cell.toString());
            cell = row.getCell(j++);
            vipcust.setAddressTxt(cell.toString());
            cell = row.getCell(j++);
            vipcust.setSubdist(cell.toString());
            //需要通过小区编号查询出小区信息并写入到订户地址中
            TMdResidentialArea area = new TMdResidentialArea();
            area = residentialAreaService.selectById(vipcust.getSubdist());
            vipcust.setProvince(area.getProvince());
            vipcust.setCity(area.getCity());
            vipcust.setCounty(area.getCounty());
            cell = row.getCell(j++);
            vipcust.setMp(cell.toString());
            cell = row.getCell(j++);
            if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                vipcust.setZip(cell.toString());
            }
            cell = row.getCell(j++);
            if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                vipcust.setSex(cell.toString());
            }
            cell = row.getCell(j++);
            vipcust.setVipSrc(cell.toString());
            cell = row.getCell(j++);
            vipcust.setVipType(cell.toString());
            cell = row.getCell(j++);
            vipcust.setStatus(cell.toString());
            cell = row.getCell(j++);
            vipcust.setSalesOrg(cell.toString());
            cell = row.getCell(j++);
            if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                vipcust.setDealerNo(cell.toString());
            }
            cell = row.getCell(j++);
            vipcust.setBranchNo(cell.toString());
            vipcusts.add(vipcust);
        }
        return convertToRespModel(MessageCode.NORMAL, null,tVipCustInfoService.addVipCusts(vipcusts));
    }

    private BigDecimal getCellValue(XSSFCell cell) {
        BigDecimal value = new BigDecimal(0);
        if (null != cell) {
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                    value = new BigDecimal(cell.getNumericCellValue());
                    break;
                case XSSFCell.CELL_TYPE_STRING: // 字符串
                    value = new BigDecimal(cell.getStringCellValue());
                    break;
                case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    break;
                case XSSFCell.CELL_TYPE_FORMULA: // 公式
                    break;
                case XSSFCell.CELL_TYPE_BLANK: // 空值
                    break;
                case XSSFCell.CELL_TYPE_ERROR: // 故障
                    break;
                default:
                    break;
            }
        }
        return value;
    }
}
