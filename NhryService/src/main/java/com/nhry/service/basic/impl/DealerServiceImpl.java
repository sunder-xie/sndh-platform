package com.nhry.service.basic.impl;

import com.nhry.common.auth.UserSessionService;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdDealerMapper;
import com.nhry.data.basic.domain.TMdDealer;
import com.nhry.service.basic.DealerService;

import java.util.List;

/**
 * Created by gongjk on 2016/7/14.
 */
public class DealerServiceImpl implements DealerService {
    private TMdDealerMapper dealerMapper;
    private UserSessionService userSessionService;

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    public void setDealerMapper(TMdDealerMapper dealerMapper) {
        this.dealerMapper = dealerMapper;
    }

    @Override
    public List<TMdDealer> getDealerBySalesOrg() {
        TSysUser user = userSessionService.getCurrentUser();
        return dealerMapper.findDealersBySalesOrg(user.getSalesOrg());
    }
}
