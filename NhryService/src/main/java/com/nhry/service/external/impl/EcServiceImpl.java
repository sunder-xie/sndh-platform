package com.nhry.service.external.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.core.task.TaskExecutor;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.config.dao.NHSysCodeItemMapper;
import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.service.external.EcBaseService;
import com.nhry.service.external.dao.EcService;
import com.nhry.utils.EnvContant;
import com.nhry.utils.HttpUtils;
import com.nhry.utils.SysContant;

public class EcServiceImpl extends EcBaseService implements EcService{
    private NHSysCodeItemMapper itemMapper;
    
	@Override
	public void pushBranch2EcForUpt(TMdBranch branch) {
		// TODO Auto-generated method stub
		try {
			NHSysCodeItem item = new NHSysCodeItem();
			item.setTypeCode(SysContant.getSystemConst("branch_province"));
			item.setItemCode(branch.getProvince());
			NHSysCodeItem ct = itemMapper.findCodeItenByCode(item);
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("businessno", "BUSSSENDBRANCHINFO");
			JSONObject body = new JSONObject();
			JSONObject ssbi = new JSONObject();
			ssbi.put("serviceName", "SVCSENDBRANCHINFO");
			JSONArray data = new JSONArray();
			JSONObject branchJson = new JSONObject();
			branchJson.put("customerId", "DH");
			branchJson.put("branch_no", branch.getBranchNo());
			branchJson.put("sales_org", branch.getSalesOrg());
			branchJson.put("branch_name", branch.getBranchName());
			branchJson.put("branch_group",branch.getBranchGroup());
			branchJson.put("sales_cha",branch.getSalesCha());
			branchJson.put("branch_level",branch.getBranchLevel());
			branchJson.put("province",ct != null ? ct.getAttr1() : null);
			branchJson.put("city", branch.getCity());
			branchJson.put("county", branch.getCounty());
			branchJson.put("address",branch.getAddress());
			branchJson.put("tel", branch.getTel());
			branchJson.put("werks",branch.getWerks());
			branchJson.put("lgort", branch.getLgort());
			branchJson.put("emp_no",branch.getEmpNo());
			branchJson.put("mp",branch.getMp());
			branchJson.put("dealer_no",branch.getDealerNo());
			data.put(branchJson);
			ssbi.put("data", data);
			body.put("SVCSENDBRANCHINFO", ssbi);
			json.put("body", body);
			arr.put(json);
			pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setItemMapper(NHSysCodeItemMapper itemMapper) {
		this.itemMapper = itemMapper;
	}

	@Override
	public void getOrderList(OrderSearchModel model)
	{
			try {
				JSONArray arr = new JSONArray();
				JSONObject json = new JSONObject();
				json.put("businessno", "BUSSGETORDERLIST");
				JSONObject body = new JSONObject();
				JSONObject ssbi = new JSONObject();
				ssbi.put("serviceName", "SVCGETORDERLIST");
				JSONArray data = new JSONArray();
				JSONObject orderSearchJson = new JSONObject();
				
				orderSearchJson.put("customerId", "DH");
				orderSearchJson.put("shopId", model.getShopId());
				orderSearchJson.put("ecOrderNo", model.getEcOrderNo());
				orderSearchJson.put("orderPlatNo", model.getOrderPlatNo());
				orderSearchJson.put("dhOrderNo", model.getDhOrderNo());
				orderSearchJson.put("vipNo", model.getMilkmemberNo());
				orderSearchJson.put("mobile", model.getContent());
				orderSearchJson.put("buyerName", model.getBuyerName());
				orderSearchJson.put("empMobile", model.getEmpMobile());
				orderSearchJson.put("empName", model.getEmpName());
				orderSearchJson.put("branchName", model.getBranchNo());
				orderSearchJson.put("createDateStart", model.getOrderDateStart());
				orderSearchJson.put("createDateEnd", model.getOrderDateEnd());
				orderSearchJson.put("dhFlag", model.getDhFlag());
				orderSearchJson.put("salesOrg", model.getSalesOrg());
				
				data.put(orderSearchJson);
				ssbi.put("data", data);
				body.put("SVCGETORDERLIST", ssbi);
				json.put("body", body);
				arr.put(json);
				System.out.println("----------"+arr.toString());
				pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void getOrderdtlinfo(OrderSearchModel model)
	{
		try {
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("businessno", "BUSSGETORDERDETAIL");
			JSONObject body = new JSONObject();
			JSONObject ssbi = new JSONObject();
			ssbi.put("serviceName", "SVCGETORDERDETAIL");
			JSONArray data = new JSONArray();
			JSONObject orderSearchJson = new JSONObject();
			
			orderSearchJson.put("customerId", "DH");
			orderSearchJson.put("ecOrderNo", model.getOrderNo());
			
			data.put(orderSearchJson);
			ssbi.put("data", data);
			body.put("SVCGETORDERDETAIL", ssbi);
			json.put("body", body);
			arr.put(json);
			System.out.println("----------"+arr.toString());
			pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendOrderStopRe(OrderSearchModel model)
	{
		try {
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("businessno", "BUSSSENDORDERSTOPRE");
			JSONObject body = new JSONObject();
			JSONObject ssbi = new JSONObject();
			ssbi.put("serviceName", "SVCSENDORDERSTOPRE");
			JSONArray data = new JSONArray();
			JSONObject orderStopReJson = new JSONObject();
			
			orderStopReJson.put("customerId", "DH");
			orderStopReJson.put("ecOrderNo", model.getOrderNo());
			orderStopReJson.put("pauseFlag", model.getContent());//停奶标志Y/N
			orderStopReJson.put("pauseStartDate", model.getOrderDateStart());//停奶开始日期yyyy-mm-dd
			orderStopReJson.put("pauseEndDate", model.getOrderDateEnd());//停奶截止日期
			orderStopReJson.put("reason", model.getReason());//原因
			
			data.put(orderStopReJson);
			ssbi.put("data", data);
			body.put("SVCSENDORDERSTOPRE", ssbi);
			json.put("body", body);
			arr.put(json);
			System.out.println("----------"+arr.toString());
			pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendOrderStatus(TPreOrder order)
	{
		SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		try {
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("businessno", "BUSSSENDORDERSTATUS");
			JSONObject body = new JSONObject();
			JSONObject ssbi = new JSONObject();
			ssbi.put("serviceName", "SVCSENDORDERSTATUS");
			JSONArray data = new JSONArray();
			JSONObject orderStatusReJson = new JSONObject();
			
			orderStatusReJson.put("customerId", "DH");
			orderStatusReJson.put("ecOrderNo", order.getOrderNo());
			orderStatusReJson.put("status", order.getPreorderStat());//已支付：101发货：200交易完成：220退订：300
			orderStatusReJson.put("paymentType", order.getPaymentmethod());//02微信04现金09卡支付
			orderStatusReJson.put("reFee", order.getCurAmt());//退款金额
			orderStatusReJson.put("editDate", f.format(new Date()));
			orderStatusReJson.put("empMobile", order.getBranchMp());//送奶员手机
			orderStatusReJson.put("empName", order.getBranchEmpName());//送奶员姓名
			
			data.put(orderStatusReJson);
			ssbi.put("data", data);
			body.put("SVCSENDORDERSTATUS", ssbi);
			json.put("body", body);
			arr.put(json);
			System.out.println("----------"+arr.toString());
			pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getOrderComments(TPreOrder order)
	{
		try {
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("businessno", "BUSSGETORDERCOMMENTS");
			JSONObject body = new JSONObject();
			JSONObject ssbi = new JSONObject();
			ssbi.put("serviceName", "SVCGETORDERCOMMENTS");
			JSONArray data = new JSONArray();
			JSONObject orderCommentsReJson = new JSONObject();
			
			orderCommentsReJson.put("customerId", "DH");
			orderCommentsReJson.put("ecOrderNo", order.getOrderNo());
			
			data.put(orderCommentsReJson);
			ssbi.put("data", data);
			body.put("SVCGETORDERCOMMENTS", ssbi);
			json.put("body", body);
			arr.put(json);
			System.out.println("----------"+arr.toString());
			pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendOrderComments(TPreOrder order)
	{
		SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		try {
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("businessno", "BUSSSENDORDERCOMMENTS");
			JSONObject body = new JSONObject();
			JSONObject ssbi = new JSONObject();
			ssbi.put("serviceName", "SVCSENDORDERCOMMENTS");
			JSONArray data = new JSONArray();
			JSONObject orderCommentsReJson = new JSONObject();
			
			orderCommentsReJson.put("customerId", "DH");
			orderCommentsReJson.put("ecOrderNo", order.getOrderNo());
			orderCommentsReJson.put("type", order.getOrderType());//备注类别10-地址20-奶站30-其他
			orderCommentsReJson.put("editPerson", order.getCreaterBy());
			orderCommentsReJson.put("editDate", f.format(new Date()));
			orderCommentsReJson.put("commentsTitle", order.getSign());
			orderCommentsReJson.put("commentsTxt", order.getMemoTxt());
			
			data.put(orderCommentsReJson);
			ssbi.put("data", data);
			body.put("SVCSENDORDERCOMMENTS", ssbi);
			json.put("body", body);
			arr.put(json);
			System.out.println("----------"+arr.toString());
			pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
