package com.nhry.service.milktrans.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchEmpMapper;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milktrans.dao.TRecBotDetailMapper;
import com.nhry.data.milktrans.dao.TRecBotMapper;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.model.milktrans.ReturnboxSerarch;
import com.nhry.model.milktrans.UpdateReturnBoxModel;
import com.nhry.service.milktrans.dao.ReturnBoxService;
import com.nhry.utils.PrimaryKeyUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongjk on 2016/6/27.
 */
public class ReturnBoxServiceImpl implements ReturnBoxService {
    private TRecBotMapper tRecBotMapper;
    private TRecBotDetailMapper tRecBotDetailMapper;
    private TDispOrderMapper tDispOrderMapper;
    private TDispOrderItemMapper tDispOrderItemMapper;
    private TMdBranchEmpMapper empMapper;
    private UserSessionService userSessionService;
    public void settRecBotMapper(TRecBotMapper tRecBotMapper) {
        this.tRecBotMapper = tRecBotMapper;
    }
    public void setEmpMapper(TMdBranchEmpMapper empMapper) {
        this.empMapper = empMapper;
    }
    public void settRecBotDetailMapper(TRecBotDetailMapper tRecBotDetailMapper) {
        this.tRecBotDetailMapper = tRecBotDetailMapper;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    public void settDispOrderMapper(TDispOrderMapper tDispOrderMapper) {
        this.tDispOrderMapper = tDispOrderMapper;
    }

    public void settDispOrderItemMapper(TDispOrderItemMapper tDispOrderItemMapper) {
        this.tDispOrderItemMapper = tDispOrderItemMapper;
    }

    @Override
    public int uptBoxRetrun(UpdateReturnBoxModel boxModel) {
        try{
            TSysUser user = userSessionService.getCurrentUser();
            Date date = new Date();
            Map<String,String> map = new HashMap<String,String>();
            map.put("detLsh",boxModel.getDetLsh());
            map.put("spec",boxModel.getSpec());
            TRecBotDetail rotDetail = tRecBotDetailMapper.selectBotDetailByDetLsh(map);
            rotDetail.setRealNum(boxModel.getRealNum());
            rotDetail.setRecDate(date);
            rotDetail.setStatus("20");
            rotDetail.setLastModified(date);
            rotDetail.setLastModifiedBy(user.getLoginName());
            rotDetail.setLastModifiedByTxt(user.getDisplayName());
            tRecBotDetailMapper.uptRecBotDetail(rotDetail);
            return 1;
        }catch (Exception e){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"录入失败");
        }

    }

    /**
     * 路单确认时根据路单号创建该员工的回瓶管理单
     * @param dispOrderNo
     * @return
     */
    @Override
    public int createDayRetBox(String dispOrderNo) {
        Date today = new Date();
        TSysUser user = userSessionService.getCurrentUser();
        TDispOrder dispOrder = tDispOrderMapper.getDispOrderByNo(dispOrderNo);
        TRecBotDetail tRecBot = tRecBotDetailMapper.selectRetByDispOrderNo(dispOrderNo);
        if (tRecBot == null) {
            //生成回瓶详情列表
            List<TRecBotDetail> entries = tDispOrderItemMapper.createRecBotByDispOrder(dispOrderNo);
            if (entries != null && entries.size() > 0) {
                for (TRecBotDetail bot : entries) {
               	  bot.setDispOrderNo(dispOrderNo);
                    bot.setCreateDate(today);
                    bot.setCreateBy(user.getLoginName());
                    bot.setCreateByTxt(user.getDisplayName());
                    bot.setStatus("10");
                    bot.setDetLsh(PrimaryKeyUtils.generateUuidKey());
                    tRecBotDetailMapper.addRecBotItem(bot);
                }
            }
        }
         return 1;
    }

    @Override
    public PageInfo searchRetBoxPage(ReturnboxSerarch rSearch) {
        TSysUser user = userSessionService.getCurrentUser();
        //返回列表
        PageInfo result = tRecBotDetailMapper.searchRetBoxPage(rSearch);
        return result;
    }

}
