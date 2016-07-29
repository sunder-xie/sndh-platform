package com.nhry.service.webService.dao;

import com.nhry.model.webService.CustInfoModel;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by gongjk on 2016/7/27.
 */
public interface GetOrderBranchService {
    public JSONObject getOrderBranch(CustInfoModel custInfoModel);
}
