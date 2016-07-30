package com.nhry.service.external.impl;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.core.task.TaskExecutor;

import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.config.dao.NHSysCodeItemMapper;
import com.nhry.data.config.domain.NHSysCodeItem;
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
}
