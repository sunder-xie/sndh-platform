package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.dao.TMdOrgPriceMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdOrgPrice;
import com.nhry.model.basic.OrgPriceModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.TMdOrgPriceService;
import com.nhry.utils.CodeGeneratorUtil;
import com.nhry.utils.date.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by huaguan on 2017/4/24.
 */
public class TMdOrgPriceServiceImpl extends BaseService implements TMdOrgPriceService{
    private TMdOrgPriceMapper tMdOrgPriceMapper;
    private TMdMaraMapper tMdMaraExMapper;

    public void settMdOrgPriceMapper(TMdOrgPriceMapper tMdOrgPriceMapper) {
        this.tMdOrgPriceMapper = tMdOrgPriceMapper;
    }

    public void settMdMaraExMapper(TMdMaraMapper tMdMaraExMapper) {
        this.tMdMaraExMapper = tMdMaraExMapper;
    }

    @Override
    public int insertOrgPriceSelective(TMdOrgPrice record) {
        TSysUser user = userSessionService.getCurrentUser();
        record.setCreateAt(new Date());
        record.setCreateBy(user.getLoginName());
        record.setCreateByTxt(user.getDisplayName());
        return tMdOrgPriceMapper.insertOrgPriceSelective(record);
    }

    @Override
    public int updateOrgPriceSelective(List<TMdOrgPrice> record) {
        if(record!=null){
            for(TMdOrgPrice priceItems:record){
                updateOrgPrice(priceItems);
            }
        }
        return 0;
    }

    @Override
    public int updateOrgPrice(TMdOrgPrice record) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        if(record!=null){
            try {
                Date endDate = sdf.parse("9999-01-01");
                String startDate = sdf.format(record.getStartDate());
                //如果更新的价格数据的有效期开始时间与今日相等，那么直接更新，否则进行插入数据，原价格信息成为旧价格
                if(startDate.equals(sdf.format(today))){
                    tMdOrgPriceMapper.updateOrgPriceSelective(record);
                }else{
                    TMdOrgPrice oldPrice = tMdOrgPriceMapper.selectOrgPriceById(record.getId());
                    oldPrice.setStatus("N");
                    oldPrice.setIsShow("N");
                    oldPrice.setEndDate(today);
                    tMdOrgPriceMapper.updateOrgPriceSelective(oldPrice);
                    record.setId(CodeGeneratorUtil.getCode());
                    record.setStartDate(new Date());
                    record.setEndDate(endDate);
                    tMdOrgPriceMapper.insertOrgPriceSelective(record);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return  0;
    }

    @Override
    public int inserListOrgMatnr(String orgId) {
        TSysUser user = userSessionService.getCurrentUser();
        TMdOrgPrice orgPrice = new TMdOrgPrice();
        orgPrice.setSalesOrg(user.getSalesOrg());
        orgPrice.setOrgId(orgId);
        int isEmp = tMdOrgPriceMapper.isOrgPriceEmp(orgPrice);
        //默认一个结束日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse("9999-01-01");
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("salesOrg", this.userSessionService.getCurrentUser()
                    .getSalesOrg());
            List<TMdMara> tmaras = tMdMaraExMapper.findMarasBySalesCodeAndOrg(attrs);
            //如果isEmp==0，所属机构的价格表中并没有产品，需要初始化
            if(isEmp==0){
                if(tmaras.size()>0){
                    for (TMdMara items:tmaras){
                        TMdOrgPrice ops = new TMdOrgPrice();
                        ops.setId(CodeGeneratorUtil.getCode());
                        ops.setOrgId(orgId);
                        ops.setSalesOrg(items.getSalesOrg());
                        ops.setStartDate(new Date());
                        ops.setEndDate(date);
                        ops.setMatnr(items.getMatnr());
                        tMdOrgPriceMapper.insertOrgPriceSelective(ops);
                    }
                }
            }else if(isEmp>0){//如果大于0,判断商品主数据与该机构价格数据是否一致，并加入新的商品数据
                List<TMdOrgPrice> orgPriceList = tMdOrgPriceMapper.selectListByOrgIdAndSaleOrg(orgPrice);
                List <String> newMaraList = new ArrayList<String>();
                List <String> newPriceList = new ArrayList<String>();
                for(TMdMara maraItems:tmaras){
                    newMaraList.add(maraItems.getMatnr());
                }
                for(TMdOrgPrice priceList:orgPriceList){
                    newPriceList.add(priceList.getMatnr());
                }
                List<String> diff = getDiffrent(newMaraList,newPriceList);
                if(diff.size()>0){
                    for(String maras:diff){
                        TMdOrgPrice ops = new TMdOrgPrice();
                        ops.setId(CodeGeneratorUtil.getCode());
                        ops.setOrgId(orgId);
                        ops.setSalesOrg(user.getSalesOrg());
                        ops.setStartDate(new Date());
                        ops.setEndDate(date);
                        ops.setMatnr(maras);
                        tMdOrgPriceMapper.insertOrgPriceSelective(ops);
                    }
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return  0;
    }

    /**
     * 两个LIST比较取不同的值
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent(List<String>list1,List<String>list2){
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if(list2.size()>list1.size()){
            maxList = list2;
            minList = list1;
        }
        Map<String,Integer> map = new HashMap<String,Integer>(maxList.size());
        for(String string:maxList){
            map.put(string,1);
        }
        for(String string:minList){
            if(map.get(string)!=null){
                map.put(string,2);
                continue;
            }
            diff.add(string);
        }
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            if(entry.getValue()==1)
            {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    @Override
    public List<TMdOrgPrice> selectOrgPriceList(OrgPriceModel record) {
        TSysUser user = userSessionService.getCurrentUser();
        record.setSalesOrg(user.getSalesOrg());
       return tMdOrgPriceMapper.selectOrgPriceList(record);
    }

    @Override
    public PageInfo selectOrgPricePage(OrgPriceModel record) {
        TSysUser user = userSessionService.getCurrentUser();
        record.setSalesOrg(user.getSalesOrg());
        return tMdOrgPriceMapper.selectOrgPricePage(record);
    }

    @Override
    public TMdOrgPrice selectOrgPriceByMatnr(OrgPriceModel record) {
        TSysUser user = userSessionService.getCurrentUser();
        record.setSalesOrg(user.getSalesOrg());
        return tMdOrgPriceMapper.selectOrgPriceByMatnr(record);
    }
}
