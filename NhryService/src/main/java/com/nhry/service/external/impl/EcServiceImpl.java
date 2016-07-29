package com.nhry.service.external.impl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.service.external.EcBaseService;
import com.nhry.service.external.dao.EcService;
import com.nhry.utils.EnvContant;

public class EcServiceImpl extends EcBaseService implements EcService{

	@Override
	public void pushBranch2EcForUpt(TMdBranch branch) {
		// TODO Auto-generated method stub
		try {
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
			branchJson.put("province",branch.getProvince());
			branchJson.put("city", branch.getCity());
			branchJson.put("county", branch.getCounty());
			branchJson.put("address",branch.getAddress());
			branchJson.put("tel", branch.getTel());
			branchJson.put("werks",branch.getWerks());
			branchJson.put("lgort", branch.getLgort());
			branchJson.put("emp_no",branch.getEmpNo());
			branchJson.put("mp",branch.getMp());
			branchJson.put("dealer_no",branch.getDealerNo());
			branchJson.put("emp_no",branch.getEmpNo());
			branchJson.put("emp_no",branch.getEmpNo());
			branchJson.put("emp_no",branch.getEmpNo());
			branchJson.put("emp_no",branch.getEmpNo());
			data.put(branchJson);
			ssbi.put("data", data);
			body.put("SVCSENDBRANCHINFO", ssbi);
			json.put("body", body);
			arr.put(json);
			System.out.println("----------"+arr.toString());
			pushMessage2Ec(EnvContant.getSystemConst("ec_base_url")+EnvContant.getSystemConst("ec_upt_branch"), arr.toString(), true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				orderSearchJson.put("shopId", "");
				orderSearchJson.put("ecOrderNo", "");
				orderSearchJson.put("orderPlatNo", "");
				orderSearchJson.put("dhOrderNo", "");
				orderSearchJson.put("vipNo", "");
				orderSearchJson.put("mobile", "");
				orderSearchJson.put("buyerName", "");
				orderSearchJson.put("empMobile", "");
				orderSearchJson.put("empName", "");
				orderSearchJson.put("branchName", "");
				orderSearchJson.put("createDateStart", "");
				orderSearchJson.put("createDateEnd", "");
				orderSearchJson.put("dhFlag", "");
				orderSearchJson.put("salesOrg", "");
				
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
			orderSearchJson.put("ecOrderNo", "");
			
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
			orderStopReJson.put("pauseFlag", "Y");
			orderStopReJson.put("pauseStartDate", model.getOrderDateStart());
			orderStopReJson.put("pauseEndDate", model.getOrderDateEnd());
			orderStopReJson.put("reason", "");
			
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
			orderStatusReJson.put("ecOrderNo", "");
			orderStatusReJson.put("status", "");
			orderStatusReJson.put("paymentType", "");
			orderStatusReJson.put("reFee", "");
			orderStatusReJson.put("editDate", "");
			orderStatusReJson.put("empMobile", "");
			orderStatusReJson.put("empName", "");
			
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
			orderCommentsReJson.put("ecOrderNo", "");
			
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
			orderCommentsReJson.put("ecOrderNo", "");
			orderCommentsReJson.put("type", "");
			orderCommentsReJson.put("editPerson", "");
			orderCommentsReJson.put("editDate", "");
			orderCommentsReJson.put("commentsTitle", "");
			orderCommentsReJson.put("commentsTxt", "");
			
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
