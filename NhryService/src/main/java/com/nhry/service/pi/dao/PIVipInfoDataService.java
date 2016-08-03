package com.nhry.service.pi.dao;

import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.service.pi.pojo.MemberActivities;
import com.nhry.service.pi.pojo.PIReturnMessage;
import com.nhry.webService.client.EvMemb;
import com.nhry.webService.client.EvMembPoint;
import com.nhry.webService.client.PISuccessMessage;
import com.nhry.webService.client.PISuccessTMessage;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by cbz on 7/30/2016.
 */
public interface PIVipInfoDataService {
    /**
     * 会员创建更新
     * @param vipCustInfo
     * @param address
     * @return
     */
    PISuccessTMessage<EvMemb> generateVipInfoData(TVipCustInfo vipCustInfo, TMdAddress address);

    /**
     * 会员积分查询
     * @param tel
     * @param membGuid
     * @param membId
     * @return
     */
    PISuccessTMessage<EvMembPoint> queryVipPointData(String tel, String membGuid, String membId);

    /**
     * 会员积分明细查询
     * @param tel
     * @param membGuid
     * @param membId
     * @return
     */
    PISuccessTMessage queryVipDetailData(String tel,String membGuid,String membId);

    /**
     * 会员积分活动创建
     * @param memberActivities
     * @return
     */
    PISuccessTMessage createMemberActivities(MemberActivities memberActivities);

    /**
     * 订户信息更新
     * @param vipCustInfo
     * @return
     */
    PISuccessTMessage sendSubscriber(TVipCustInfo vipCustInfo);

    /**
     * 订户地址更新
     * @param address
     * @param sapGuid
     * @return
     */
    PISuccessTMessage sendAddress(TMdAddress address,String sapGuid);

}
