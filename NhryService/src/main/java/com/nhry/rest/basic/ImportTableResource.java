package com.nhry.rest.basic;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.data.basic.domain.TaskYearMonthPlan;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.basic.dao.PriceService;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.basic.pojo.BranchScopeModel;
import com.nhry.service.order.dao.OrderService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;
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
import java.text.SimpleDateFormat;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private BranchService branchService;

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
            TMdResidentialArea area = residentialAreaService.selectById(vipcust.getSubdist());
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
    @POST
    @Path("/importPreorder")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importPreorder", response = ResponseModel.class, notes = "导入订单、行项目数据")
    public Response importPreorder(FormDataMultiPart form, @Context HttpServletRequest request) throws IOException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        FormDataBodyPart filePart = form.getField("file");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
        XSSFSheet sheet = workbook.getSheetAt(0);//模版上sheet1页面
        XSSFSheet sheet1 = workbook.getSheetAt(1);//模版上sheet2页面
        int rowNum = sheet.getLastRowNum();
        int rowNum1 = sheet1.getLastRowNum();
        List<OrderCreateModel> OrderModels = new ArrayList<OrderCreateModel>();//订单
        //循环sheet1获取页面值
        for (int i = 3;i <= rowNum; i++){
            OrderCreateModel OrderModel = new OrderCreateModel();
            TPreOrder order = new TPreOrder();
            int j = 1;
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(j++);
            order.setOrderNo(cell.toString());
            cell = row.getCell(j++);
            order.setBranchNo(cell.toString());
            String salesOrg = branchService.selectBranchByNo(order.getBranchNo()).getSalesOrg();
            cell = row.getCell(j++);
            order.setDeliveryType(cell.toString());
            ArrayList<TPlanOrderItem> entries = new ArrayList<TPlanOrderItem>();//订单行项目
            for(int s = 3;s <= rowNum1;s++){
                TPlanOrderItem entrie = new TPlanOrderItem();
                int t= 1;
                XSSFRow row1 = sheet1.getRow(s);
                XSSFCell cell1 = row1.getCell(t++);
                //判断订单号码是否一致，如果一致放到一个事物里
                if(cell1.toString().equals(order.getOrderNo())){
                    entrie.setOrderNo(cell1.toString());
                    cell1 = row1.getCell(t++);
                    entrie.setMatnr("0000000000".concat(cell1.toString()));//补齐产品编码
                    cell1 = row1.getCell(t++);
                    entrie.setRuleType(cell1.toString());
                    cell1 = row1.getCell(t++);
                    entrie.setQty(Integer.valueOf(cell1.toString()));
                    //算出配送天数
                   // entrie.setDispDays();
                    cell1 = row1.getCell(t++);
                    if (cell1 != null && StringUtils.isNotEmpty(cell1.toString())) {
                        entrie.setGapDays(cell1.getCellType());
                    }
                    cell1 = row1.getCell(t++);
                    entrie.setRuleTxt(cell1.toString());
                    cell1 = row1.getCell(t++);
                    entrie.setReachTimeType(cell1.toString());
                        cell1 = row1.getCell(t++);
                        entrie.setStartDispDateStr(cell1.toString());
                        cell1 = row1.getCell(t++);
                        entrie.setEndDispDateStr(cell1.toString());
                    cell1 = row1.getCell(t++);
                    entrie.setDispTotal(cell1.getCellType());
                    float price = priceService.getMaraPriceForCreateOrder(order.getBranchNo(), entrie.getMatnr(), order.getDeliveryType(), salesOrg);
                    if(price<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,"产品价格小于0,请检查传入的商品号，奶站和配送方式!信息："+"奶站："+order.getBranchNo()+"商品号："+entrie.getMatnr()+"配送方式："+order.getDeliveryType()+"销售组织："+salesOrg);
                    entrie.setSalesPrice(new BigDecimal(String.valueOf(price)));
                    entries.add(entrie);
                }
            }
            OrderModel.setEntries(entries);
            try{
                cell = row.getCell(j++);
                order.setOrderDate(format.parse(cell.toString()));
                cell = row.getCell(j++);
                order.setEndDate(format.parse(cell.toString()));
            }catch (Exception e){
                throw new ServiceException(MessageCode.LOGIC_ERROR,"日期格式有误");
            }

            cell = row.getCell(j++);
            order.setPaymentmethod(cell.toString());
            cell = row.getCell(j++);
            if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                order.setOnlineorderNo(cell.toString());
            }
            cell = row.getCell(j++);
            order.setMilkmemberNo(cell.toString());
            cell = row.getCell(j++);
            order.setPaymentStat(cell.toString());
            cell = row.getCell(j++);
            order.setMilkboxStat(cell.toString());
            cell = row.getCell(j++);
            order.setEmpNo(cell.toString());
            cell = row.getCell(j++);
            order.setPreorderStat(cell.toString());
            cell = row.getCell(j++);
            if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                order.setMemoTxt(cell.toString());
            }
            cell = row.getCell(j++);
            order.setPayMethod(cell.toString());
            cell = row.getCell(j++);
            order.setSign(cell.toString());
            order.setOrderType("20");
            order.setPreorderSource("30");
            //通过订户查询到地址信息，并写入到订单里
            TVipCustInfo custinfo = tVipCustInfoService.findVipCustByNo(order.getMilkmemberNo());
            order.setAdressNo(custinfo.getSubdist());
            OrderModel.setOrder(order);
            OrderModels.add(OrderModel);

        }
        return convertToRespModel(MessageCode.NORMAL, null,orderService.createOrders(OrderModels));
    }
    @POST
    @Path("/importLinks")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importLinks", response = ResponseModel.class, notes = "导入奶站关联小区信息")
    public Response importLinks(FormDataMultiPart form, @Context HttpServletRequest request) throws IOException{
        FormDataBodyPart filePart = form.getField("file");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        BranchScopeModel model = new BranchScopeModel();
        model.setBranchNo(sheet.getRow(0).getCell(0).toString());
        List<String> areaids = new ArrayList<String>();
        for (int i = 2; i <= rowNum; i++){
            int j = 1;
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(j++);
            //start 导入小区信息
            areaids.add(cell.toString());//小区编号
        }
        model.setResidentialAreaIds(areaids);
        return convertToRespModel(MessageCode.NORMAL, null,residentialAreaService.areaRelBranch(model));
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
