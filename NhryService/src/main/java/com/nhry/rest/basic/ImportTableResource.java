package com.nhry.rest.basic;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.domain.*;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.basic.OrderModel;
import com.nhry.model.bill.CustomerPayMentModel;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.sys.ResponseModel;
import com.nhry.rest.BaseResource;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.basic.dao.PriceService;
import com.nhry.service.basic.dao.ResidentialAreaService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.basic.pojo.BranchScopeModel;
import com.nhry.service.bill.dao.CustomerBillService;
import com.nhry.service.order.dao.OrderService;
import com.nhry.utils.EnvContant;
import com.nhry.utils.ExcelUtil;
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
import java.text.DecimalFormat;
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
    @Autowired
    private CustomerBillService customerBillService;

    @POST
    @Path("/importResidentialArea")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importResidentialArea", response = ResponseModel.class, notes = "导入小区主数据")
    public Response importResidentialArea(FormDataMultiPart form, @Context HttpServletRequest request) throws IOException {
        FormDataBodyPart filePart = form.getField("file");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        String source = formDataContentDisposition.getFileName();
        if (!source.endsWith("xlsx")) {
            return convertToRespModel(MessageCode.LOGIC_ERROR, "文件类型错误，请使用正规模板！", "");
        }
        List<TMdResidentialArea> areas = new ArrayList<TMdResidentialArea>();//配送区域
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            for (int i = 2; i <= rowNum; i++) {
                TMdResidentialArea area = new TMdResidentialArea();
                int j = 1;
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j++);
                //start 导入小区信息
                area.setId(ExcelUtil.getCellValue(cell, row));//主键编号
                cell = row.getCell(j++);
                area.setResidentialAreaTxt(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                area.setProvince(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                area.setCity(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                area.setCounty(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                area.setAddressTxt(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                area.setSalesOrg(ExcelUtil.getCellValue(cell, row));
                areas.add(area);
            }
        } catch (RuntimeException e) {
            return convertToRespModel(MessageCode.LOGIC_ERROR, e.getMessage(), "");
        }
        return convertToRespModel(MessageCode.NORMAL, "保存成功！", residentialAreaService.addResidentialAreas(areas));
    }

    @POST
    @Path("/importVipcustInfo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importVipcustInfo", response = ResponseModel.class, notes = "导入订户主数据")
    public Response importVipcustInfo(FormDataMultiPart form, @Context HttpServletRequest request) {
        List<TVipCustInfo> vipcusts = new ArrayList<TVipCustInfo>();//订户
        try {
            FormDataBodyPart filePart = form.getField("file");
            InputStream fileInputStream = filePart.getValueAs(InputStream.class);
            FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
            String source = formDataContentDisposition.getFileName();
            if (!source.endsWith("xlsx")) {
                return convertToRespModel(MessageCode.LOGIC_ERROR, "文件类型错误，请使用正规模板！", "");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            for (int i = 2; i <= rowNum; i++) {
                TVipCustInfo vipcust = new TVipCustInfo();
                int j = 1;
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j++);
                vipcust.setVipCustNo(ExcelUtil.getCellValue(cell, row));
                //start 导入小区信息
//                vipcust.setVipCustNo(cell.toString());//主键编号
                cell = row.getCell(j++);
                vipcust.setVipName(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                vipcust.setAddressTxt(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                vipcust.setSubdist(ExcelUtil.getCellValue(cell, row));
                //需要通过小区编号查询出小区信息并写入到订户地址中
                TMdResidentialArea area = residentialAreaService.selectById(vipcust.getSubdist());
                if (area == null) {
                    return convertToRespModel(MessageCode.LOGIC_ERROR, "第" + (row.getRowNum() + 1) + "行，第" + (cell.getColumnIndex() + 1) + "列：小区编号" + vipcust.getSubdist() + "的小区信息不存在！请从导入模板中删除该订户，并重新校验同类问题", "");
                }
                vipcust.setProvince(area.getProvince());
                vipcust.setCity(area.getCity());
                vipcust.setCounty(area.getCounty());
                cell = row.getCell(j++);
                vipcust.setMp(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                    vipcust.setZip(ExcelUtil.getCellValue(cell, row));
                }
                cell = row.getCell(j++);
                if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                    vipcust.setSex(ExcelUtil.getCellValue(cell, row));
                }
                cell = row.getCell(j++);
                vipcust.setVipSrc(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                vipcust.setVipType(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                vipcust.setStatus(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                vipcust.setSalesOrg(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                    vipcust.setDealerNo(ExcelUtil.getCellValue(cell, row));
                }
                cell = row.getCell(j++);
                vipcust.setBranchNo(ExcelUtil.getCellValue(cell, row));
                vipcusts.add(vipcust);
            }

            //校验手机号是否重复
            for (int k = 0; k < vipcusts.size() - 1; k++) {
                TVipCustInfo tmp1 = vipcusts.get(k);
                for (int l = vipcusts.size() - 1; l > k; l--) {
                    TVipCustInfo tmp2 = vipcusts.get(l);
                    if (tmp1.getVipCustNo().equals(tmp2.getVipCustNo())) {
                        return convertToRespModel(MessageCode.NORMAL, "模板中" + tmp1.getVipCustNo() + "的订户号重复，请重新校验数据！", "");
                    }
                    if (tmp1.getMp().equals(tmp2.getMp())) {
                        return convertToRespModel(MessageCode.NORMAL, "模板中" + tmp1.getMp() + "的手机号重复，请重新校验数据！", "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return convertToRespModel(MessageCode.LOGIC_ERROR, e.getMessage(), "");
        }
        return convertToRespModel(MessageCode.NORMAL, "保存成功！", tVipCustInfoService.addVipCusts(vipcusts));
    }

    @POST
    @Path("/importPreorder")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importPreorder", response = ResponseModel.class, notes = "导入订单、行项目数据")
    public Response importPreorder(FormDataMultiPart form, @Context HttpServletRequest request) throws IOException {
        List<OrderCreateModel> OrderModels = new ArrayList<OrderCreateModel>();//订单
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        FormDataBodyPart filePart = form.getField("file");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        String source = formDataContentDisposition.getFileName();
        if (!source.endsWith("xlsx")) {
            return convertToRespModel(MessageCode.LOGIC_ERROR, "文件类型错误，请使用正规模板！", "");
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
            XSSFSheet sheet = workbook.getSheetAt(0);//模版上sheet1页面
            XSSFSheet sheet1 = workbook.getSheetAt(1);//模版上sheet2页面
            int rowNum = sheet.getLastRowNum();
            int rowNum1 = sheet1.getLastRowNum();

            //循环sheet1获取页面值
            for (int i = 3; i <= rowNum; i++) {
                OrderCreateModel OrderModel = new OrderCreateModel();
                TPreOrder order = new TPreOrder();
                int j = 1;
                XSSFRow row = sheet.getRow(i);
                if (row == null) {
                    break;
                }
                XSSFCell cell = row.getCell(j++);
                order.setOrderNo(cell.toString());
                cell = row.getCell(j++);
                order.setBranchNo(cell.toString());
                TMdBranch branch = branchService.selectBranchByNo(order.getBranchNo());
                if (branch == null) {
                    throw new RuntimeException("第" + (row.getRowNum() + 1) + "行,第" + (cell.getColumnIndex() + 1) + "列奶站编码为" + cell.toString() + "奶站不存在！请重新校验数据！");
                }
                String salesOrg = branch.getSalesOrg();
                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setDeliveryType(ExcelUtil.getCellValue(cell, row));
                try {
                    cell = row.getCell(j++);
                    order.setOrderDate(format.parse(cell.toString()));
                    cell = row.getCell(j++);
                    order.setEndDate(format.parse(cell.toString()));
                } catch (Exception e) {
                    throw new RuntimeException("第" + (row.getRowNum() + 1) + "行,第" + (cell.getColumnIndex() + 1) + "列" + cell.toString() + "日期格式有误,正确日期格式：2016-09-09 ！");
                }

                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setPaymentmethod(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                    order.setOnlineorderNo(cell.toString());
                }
                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setMilkmemberNo(ExcelUtil.getCellValue(cell, row));
                //通过订户查询到地址信息，并写入到订单里
                TMdAddress tMdAddress = tVipCustInfoService.findAddressByCustNoISDefault(order.getMilkmemberNo());
                if (tMdAddress == null) {
                    throw new RuntimeException("第" + (row.getRowNum() + 1) + "行,第" + (cell.getColumnIndex() + 1) + "列" + cell.toString() + "的订户编码没有对应的地址信息，请校验数据是否正确！");
                }
                order.setAdressNo(tMdAddress.getAddressId());
                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setPaymentStat(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setMilkboxStat(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                order.setEmpNo(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setPreorderStat(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                    order.setMemoTxt(cell.toString());
                }
                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setPayMethod(ExcelUtil.getCellValue(cell, row));
                cell = row.getCell(j++);
                ExcelUtil.isNullCell(cell, row, j);
                order.setSign(ExcelUtil.getCellValue(cell, row));
                order.setOrderType("20");
                order.setPreorderSource("30");
                OrderModel.setOrder(order);

                ArrayList<TPlanOrderItem> entries = new ArrayList<TPlanOrderItem>();//订单行项目
                for (int s = 3; s <= rowNum1; s++) {
                    TPlanOrderItem entrie = new TPlanOrderItem();
                    int t = 1;
                    XSSFRow row1 = sheet1.getRow(s);
                    XSSFCell cell1 = row1.getCell(t++);
                    //判断订单号码是否一致，如果一致放到一个事物里
                    if (cell1.toString().equals(order.getOrderNo())) {
                        entrie.setOrderNo(cell1.toString());
                        cell1 = row1.getCell(t++);
                        entrie.setMatnr("0000000000".concat(ExcelUtil.getCellValue(cell1, row1)));//补齐产品编码
                        cell1 = row1.getCell(t++);
                        ExcelUtil.isNullCell(cell1, row, t);
                        entrie.setRuleType(ExcelUtil.getCellValue(cell1, row));
                        cell1 = row1.getCell(t++);
                        entrie.setQty(Integer.valueOf(cell1.toString()));
                        //算出配送天数
                        // entrie.setDispDays();
                        cell1 = row1.getCell(t++);
                        if (cell1 != null && StringUtils.isNotBlank(cell1.toString())) {
                            entrie.setGapDays(Integer.valueOf(ExcelUtil.getCellValue(cell1, row1)));
                        }
                        cell1 = row1.getCell(t++);
                        if (cell1 != null && StringUtils.isNotEmpty(cell1.toString())) {
                            entrie.setRuleTxt(cell1.toString());
                        }
                        cell1 = row1.getCell(t++);
                        ExcelUtil.isNullCell(cell1, row, t);
                        entrie.setReachTimeType(ExcelUtil.getCellValue(cell1, row1));
                        cell1 = row1.getCell(t++);
                        try {
                            format.parse(cell1.toString());
                        } catch (Exception e) {
                            throw new RuntimeException("行项目：第" + (row1.getRowNum() + 1) + "行，第" + cell1.getColumnIndex() + "列,日期格式有误");
                        }
                        entrie.setStartDispDateStr(cell1.toString());
                        cell1 = row1.getCell(t++);
                        try {
                            format.parse(cell1.toString());
                        } catch (Exception e) {
                            throw new RuntimeException("行项目：第" + (row1.getRowNum() + 1) + "行，第" + cell1.getColumnIndex() + "列,日期格式有误");
                        }
                        entrie.setEndDispDateStr(cell1.toString());
                        cell1 = row1.getCell(t++);
                        if ("20".equals(order.getPaymentmethod()) && (cell1 == null || StringUtils.isEmpty(cell1.toString()))) {
                            throw new RuntimeException("行项目：第" + (row1.getRowNum() + 1) + "行，第" + cell1.getColumnIndex() + "列，预付款项目必须填写配送数量！请重新校验同类问题！");
                        }
                        if (cell1 != null && StringUtils.isNotBlank(cell1.toString())) {
                            entrie.setDispTotal(Integer.valueOf(cell1.getStringCellValue()));
                        }
                        float price = priceService.getMaraPriceForCreateOrder(order.getBranchNo(), entrie.getMatnr(), order.getDeliveryType(), salesOrg);
                        if (price <= 0)
                            throw new RuntimeException("产品价格小于0,请检查传入的商品号，奶站和配送方式!信息：" + "奶站：" + order.getBranchNo() + "商品号：" + entrie.getMatnr() + "配送方式：" + order.getDeliveryType() + "销售组织：" + salesOrg);
                        entrie.setSalesPrice(new BigDecimal(String.valueOf(price)));
                        entries.add(entrie);
                    }
                }
                OrderModel.setEntries(entries);
                OrderModels.add(OrderModel);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return convertToRespModel(MessageCode.LOGIC_ERROR, e.getMessage(), "");
        }
        if(OrderModels.size()>0) {
            for (OrderCreateModel orderModel : OrderModels) {
                TPreOrder order = orderModel.getOrder();
                if (org.apache.commons.lang3.StringUtils.isBlank(order.getPaymentStat())) {
                    throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号" + order.getOrderNo() + "请选择付款方式!");
                }
                if (org.apache.commons.lang3.StringUtils.isBlank(order.getMilkboxStat())) {
                    throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号" + order.getOrderNo() + "请选择奶箱状态!");
                }
                if (org.apache.commons.lang3.StringUtils.isBlank(order.getEmpNo())) {
                    if (!"10".equals(order.getPreorderSource()) && !"20".equals(order.getPreorderSource())) {
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号" + order.getOrderNo() + "请选择送奶员!");
                    }
                }
                if (orderModel.getEntries() == null || orderModel.getEntries().size() == 0) {
                    throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号" + order.getOrderNo() + "请选择商品行!");
                }
                if (org.apache.commons.lang3.StringUtils.isBlank(order.getMilkmemberNo())) {
                    if (!"10".equals(order.getPreorderSource()) && !"20".equals(order.getPreorderSource())) {//电商不交验订户
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号" + order.getOrderNo() + "请选择订户!");
                    }
                }
                if (org.apache.commons.lang3.StringUtils.isBlank(order.getAdressNo())) {
                    if (orderModel.getAddress() == null) {
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号" + order.getOrderNo() + "请选择或输入地址!");
                    }
                }
                for (TPlanOrderItem e : orderModel.getEntries()) {
                    if (org.apache.commons.lang3.StringUtils.isBlank(e.getRuleType())) {
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号" + order.getOrderNo() + "商品行必须要有配送规律!");
                    }
                }
            }

            orderService.createOrders(OrderModels);
            for (int om = 0; om < OrderModels.size(); om++) {
                OrderCreateModel ocm = OrderModels.get(om);
                if ("20".equals(ocm.getOrder().getPaymentmethod())) {
                    customerBillService.createRecBillByOrderNo(ocm.getOrder().getOrderNo());
                    CustomerPayMentModel cModel = new CustomerPayMentModel();
                    cModel.setOrderNo(ocm.getOrder().getOrderNo());
                    cModel.setAmt(ocm.getOrder().getCurAmt().toString());
                    cModel.setPaymentType(ocm.getOrder().getPayMethod());
                    cModel.setEmpNo(ocm.getOrder().getEmpNo());
                    customerBillService.customerPayment(cModel);
                }
            }
        }
        return convertToRespModel(MessageCode.NORMAL, "保存成功！", null);
    }

    @POST
    @Path("/importLinks")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "/importLinks", response = ResponseModel.class, notes = "导入奶站关联小区信息")
    public Response importLinks(FormDataMultiPart form, @Context HttpServletRequest request) throws IOException {
        FormDataBodyPart filePart = form.getField("file");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
        XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fileInputStream));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        BranchScopeModel model = new BranchScopeModel();
        model.setBranchNo(sheet.getRow(0).getCell(0).toString());
        List<String> areaids = new ArrayList<String>();
        for (int i = 2; i <= rowNum; i++) {
            int j = 1;
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(j++);
            //start 导入小区信息
            areaids.add(cell.toString());//小区编号
        }
        model.setResidentialAreaIds(areaids);
        return convertToRespModel(MessageCode.NORMAL, null, residentialAreaService.areaRelBranch(model));
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
