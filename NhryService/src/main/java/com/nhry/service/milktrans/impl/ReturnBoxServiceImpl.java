package com.nhry.service.milktrans.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchEmpMapper;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milktrans.dao.TRecBotDetailMapper;
import com.nhry.data.milktrans.dao.TRecBotMapper;
import com.nhry.data.milktrans.domain.TRecBot;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.model.milktrans.BoxSearch;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;
import com.nhry.model.milktrans.CreateReturnBoxModel;
import com.nhry.model.milktrans.ReturnboxSerarch;
import com.nhry.service.milktrans.dao.ReturnBoxService;
import com.nhry.utils.SerialUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public PageInfo searchBoxPage(BoxSearch bsearch) {
        return tRecBotMapper.searchBoxPage(bsearch);
    }

    @Override
    public int uptBoxRetrun(CreateReturnBoxModel boxModel) {
        try{
            TSysUser user = userSessionService.getCurrentUser();
            TRecBot bot = boxModel.getRecBot();
            Date date = new Date();
            List<TRecBotDetail> entries = boxModel.getEntries();
            bot.setLastModified(date);
            bot.setLastModifiedBy(user.getLoginName());
            bot.setLastModifiedByTxt(user.getDisplayName());
            tRecBotMapper.uptTrecBot(bot);

            if(entries !=null && entries.size()>0){
                for(TRecBotDetail entry : entries){
                    entry.setLastModified(date);
                    entry.setLastModifiedBy(user.getLoginName());
                    entry.setLastModifiedByTxt(user.getDisplayName());
                    tRecBotDetailMapper.uptRecBotDetail(entry);
                }
            }
            return 1;
        }catch (Exception e){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"添加失败");
        }

    }

    /**
     * 路单确认时创建该员工的回瓶管理单
     * @param cModel
     * @return
     */
    @Override
    public CreateReturnBoxModel createDayRetBox(CreateEmpReturnboxModel cModel) {
        Date today = new Date();
        TRecBot tRecBot = tRecBotMapper.searchBoxByEmpAndBranch(cModel);
        CreateReturnBoxModel boxModel = new CreateReturnBoxModel();
        if(tRecBot== null){
            //创建
            //首先根据前日录单配送信息获取应回瓶数
            TSysUser user = userSessionService.getCurrentUser();
            TMdBranchEmp emp = empMapper.selectBranchEmpByNo(cModel.getEmpNo());
            BigDecimal receiveNum = tDispOrderMapper.creatRecBot(cModel);
            TRecBot bot = new TRecBot();
            bot.setRecDate(cModel.getRetDate());
            bot.setEmpNo(cModel.getEmpNo());
            bot.setEmpName(emp.getEmpName());
            bot.setBranchNo(cModel.getBranchNo());
            bot.setReceiveNum(receiveNum.intValue());
            String retLsh = SerialUtil.creatSeria();
            bot.setRetLsh(retLsh);
            bot.setCreateAt(today);
            bot.setCreateBy(user.getLoginName());
            bot.setCreateByTxt(user.getDisplayName());
            bot.setStatus("10");
            tRecBotMapper.addTrecBot(bot);

            //生成回瓶详情列表
            List<TRecBotDetail> entries = tDispOrderItemMapper.selectItemsByReturnBox(cModel);
            for(TRecBotDetail entry :entries){
                String detLsh = SerialUtil.creatSeria();
                entry.setRetLsh(retLsh);
                entry.setDetLsh(detLsh);
                entry.setCreateBy(user.getLoginName());
                entry.setCreateByTxt(user.getDisplayName());
                tRecBotDetailMapper.addRecBotItem(entry);
            }
            boxModel.setRecBot(bot);
            boxModel.setEntries(entries);
            return boxModel;
        }else{
            List<TRecBotDetail> entries = tRecBotDetailMapper.selectBotDetailByRetLsh(tRecBot.getRetLsh());
            boxModel.setEntries(entries);
            boxModel.setRecBot(tRecBot);
            return boxModel;
        }
    }

    @Override
    public PageInfo searchRetBoxPage(ReturnboxSerarch rSearch) {
        TSysUser user = userSessionService.getCurrentUser();
        //返回列表
        PageInfo result = tRecBotDetailMapper.searchRetBoxPage(rSearch);
        return result;
    }

    @Override
    public CreateReturnBoxModel getRetBoxDetail(String retLsh) {
        CreateReturnBoxModel model = new CreateReturnBoxModel();
        TRecBot bot = tRecBotMapper.getReturnBoxByNo(retLsh);
        List<TRecBotDetail> items = tRecBotDetailMapper.selectBotDetailByRetLsh(retLsh);
        List<TRecBotDetail> entries = new ArrayList<TRecBotDetail>();
        if(items!=null && items.size()>0){
            entries.addAll(items);
        }
        model.setRecBot(bot);
        model.setEntries(entries);
        return model;
    }
}
