package com.nhry.service.webService.impl;

import com.nhry.model.webService.CustInfoModel;
import com.nhry.service.webService.dao.GetOrderBranchService;
import com.nhry.utils.APIHttpClient;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongjk on 2016/7/27.
 */
public class GetOrderBranchServiceImpl implements GetOrderBranchService {
    @Override
    public String  getOrderBranch(CustInfoModel custInfoModel) {

        JSONObject obj = this.dataToJson(custInfoModel);
        APIHttpClient api = new APIHttpClient("http://ec2-54-222-230-211.cn-north-1.compute.amazonaws.com.cn/FY_MOBILE_SVR/WFY_UNI_SERVICE.json?method=callProcService");
        String strObje = "["+obj.toString()+"]";
        String str =  api.post(strObje);
        JSONObject resultJson;
        try {
            resultJson  = new JSONObject(str);
            return resultJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    public  JSONObject dataToJson(CustInfoModel custInfoModel){
        Map<String,String> data = new HashMap<String,String>();
        data.put( "customerId","CallCenter");
        data.put("vipGuid",custInfoModel.getVipGuid());
        data.put("province",custInfoModel.getProvince());
        data.put("city",custInfoModel.getCity());
        data.put("district",custInfoModel.getDistrict());
        data.put("street",custInfoModel.getStreet());
        data.put("townName",custInfoModel.getTownName());
        data.put("address",custInfoModel.getAddress());
        data.put("longitude",custInfoModel.getLongitude());
        data.put("latitude",custInfoModel.getLatitude());

        JSONObject obj4 = new JSONObject(data);

        Map<String,Object> map3 = new HashMap<String,Object>();
        map3.put("serviceName","SVCGETORDERBRANCH");
        map3.put("data",obj4);
        JSONObject obj3 = new JSONObject(map3);

        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("BUSSGETORDERBRANCH",obj3);
        JSONObject obj2 = new JSONObject(map2);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("businessno","BUSSGETORDERBRANCH");
        map.put("body",obj2);


        JSONObject obj = new JSONObject(map);
        return obj;
    }
}
