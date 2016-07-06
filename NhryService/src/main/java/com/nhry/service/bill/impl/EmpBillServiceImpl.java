package com.nhry.service.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchEmpMapper;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.data.bill.dao.EmpBillMapper;
import com.nhry.data.bill.dao.TMdDispRateItemMapper;
import com.nhry.data.bill.dao.TMdDispRateMapper;
import com.nhry.data.bill.domain.TMdDispRate;
import com.nhry.data.bill.domain.TMdDispRateItem;
import com.nhry.model.bill.*;
import com.nhry.service.bill.dao.EmpBillService;
import com.nhry.utils.PrimaryKeyUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gongjk on 2016/7/1.
 */
public class EmpBillServiceImpl implements EmpBillService {
    private EmpBillMapper empBillMapper;
    private TMdDispRateMapper tMdDispRateMapper;
    private TMdDispRateItemMapper tMdDispRateItemMapper;
    private TMdBranchEmpMapper branchEmpMapper;
    private UserSessionService userSessionService;

    private TMdMaraExMapper tMdMaraExMapper;

    public void settMdMaraExMapper(TMdMaraExMapper tMdMaraExMapper) {
        this.tMdMaraExMapper = tMdMaraExMapper;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    public void setBranchEmpMapper(TMdBranchEmpMapper branchEmpMapper) {
        this.branchEmpMapper = branchEmpMapper;
    }

    public void setEmpBillMapper(EmpBillMapper empBillMapper) {
        this.empBillMapper = empBillMapper;
    }

    public void settMdDispRateMapper(TMdDispRateMapper tMdDispRateMapper) {
        this.tMdDispRateMapper = tMdDispRateMapper;
    }

    public void settMdDispRateItemMapper(TMdDispRateItemMapper tMdDispRateItemMapper) {
        this.tMdDispRateItemMapper = tMdDispRateItemMapper;
    }

    @Override
    public PageInfo empDispDetialInfo(EmpDispDetialInfoSearch eSearch) {
        if(StringUtils.isBlank(eSearch.getPageNum()) || StringUtils.isBlank(eSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return  empBillMapper.empDispDetialInfo(eSearch);
    }

    //获取指定日期内（一个月）送奶工配送详细
    @Override
    public List<EmpAccoDispFeeByProduct> empAccoDispFeeByProduct(EmpDispDetialInfoSearch eSearch) {
        return  empBillMapper.empAccoDispFeeByProduct(eSearch);
}

    @Override
    public PageInfo empAccountReceAmount(EmpDispDetialInfoSearch eSearch) {
        if(StringUtils.isBlank(eSearch.getPageNum()) || StringUtils.isBlank(eSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return  empBillMapper.empAccountReceAmount(eSearch);
    }

    /**
     * 获取按数量结算的运送费率
     * @param empNo
     * @param dispNum
     * @return
     */
    @Override
    public BigDecimal CalculateEmpTransRateByNum(String empNo,int dispNum) {
        TMdBranchEmp emp =branchEmpMapper.selectBranchEmpByNo(empNo);
        if(emp == null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该员工不存在！");
        }
        String salaryMet = emp.getSalaryMet();
        String salesOrg = emp.getSalesOrg();
        BigDecimal result = new BigDecimal(0);
        //如果是数量
        if("10".equals(salaryMet)){
            List<TMdDispRateItem> oldList = tMdDispRateItemMapper.getDispRateNumBySalOrg(salesOrg);
            if(oldList!=null && oldList.size() >0){
              for(TMdDispRateItem  dispRateNum : oldList){
                  String num = dispRateNum.getItemValue();
                  boolean flag = num.endsWith("+");
                  if(flag){
                      if(dispNum > Integer.valueOf( num.substring(0,num.length()-1))){
                          result = dispRateNum.getRate();
                          break;
                      }
                  }else{
                      if(dispNum <= Integer.valueOf(num)){
                          result = dispRateNum.getRate();
                          break;
                      }
                  }

              }
                return result;
            }
        }
        throw new ServiceException(MessageCode.LOGIC_ERROR,"配送数 不在输入的阶梯范围内!!! 请审查");
    }

    /**
     * 获取按产品结算的运送费率
     * @param matnr 产品编码
     * @param salesOrg  销售组织
     * @return
     */

    public BigDecimal CalculateEmpTransRateByProduct(String matnr,String salesOrg){
        TMdMaraEx product = tMdMaraExMapper.getProductTransRateByCode(matnr,salesOrg);
        if(product == null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"产品"+matnr+"在分公司"+salesOrg+"下存在");
        }
        return product.getRate();
    }

    /**
     * 计算送奶工的配送费
     * @param eSearch
     * @param empNo
     * @return
     */
    @Override
    public BigDecimal CalculateEmpTransFee(EmpDispDetialInfoSearch eSearch,String empNo) {
        TMdBranchEmp emp = branchEmpMapper.selectBranchEmpByEmpNo(empNo);
        String type = emp.getSalaryMet();
        String salesOrg = emp.getSalesOrg();
        if(eSearch.getEmpNo() == null){
            eSearch.setEmpNo(empNo);
        }
        BigDecimal result = new BigDecimal(0);
        //按产品计算配送费
        if("20".equals(type)){
            List<EmpAccoDispFeeByProduct> detail = empBillMapper.empAccoDispFeeByProduct(eSearch);
            if(detail!=null && detail.size()>0){
                for(EmpAccoDispFeeByProduct pro: detail){
                    BigDecimal dispRate = this.CalculateEmpTransRateByProduct(pro.getMatnr(),salesOrg);
                    BigDecimal dispFee = dispRate.multiply(new BigDecimal(pro.getQty()));
                    result =  result.add(dispFee);
                }
            }

        }
        //按 数量计算配送费
        else{
            int dispNum = empBillMapper.empAccoDispFeeByNum(eSearch);
            BigDecimal dispFee = this.CalculateEmpTransRateByNum(empNo,dispNum).multiply(new BigDecimal(dispNum));
             result =  result.add(dispFee);
        }
        return result;
    }



    @Override
    public PageInfo empSalaryRep(EmpDispDetialInfoSearch eSearch) {
        if(StringUtils.isBlank(eSearch.getPageNum()) || StringUtils.isBlank(eSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        PageInfo info = empBillMapper.empSalaryRep(eSearch);
        List<EmpSalaryBillModel> empSalList = info.getList();
        if(empSalList !=null && empSalList.size()>0){
            for (EmpSalaryBillModel  empSal : empSalList){
                //配送费
               BigDecimal dispFee =  this.CalculateEmpTransFee(eSearch,empSal.getEmpNo());
                empSal.setDispFee(dispFee);
                //奶瓶回收率
            }
        }

        return  info;
    }

    @Override
    public int uptEmpDispRate(SaleOrgDispRateModel sModel) {
       try{
           TMdDispRate dispRate =  null;
           String salesOrg = sModel.getSalesOrg();
           TSysUser user =  userSessionService.getCurrentUser();
           dispRate = tMdDispRateMapper.getDispRateBySaleOrg(salesOrg);
           //原来的结算方式已有 则更新
           if(dispRate!=null){
               dispRate.setSalaryMet(sModel.getSalaryMet());
               dispRate.setLastModified(new Date());
               dispRate.setLastModifiedBy(user.getLoginName());
               dispRate.setLastModifiedByTxt(user.getDisplayName());
               tMdDispRateMapper.uptDispRateTypeBySaleOrg(dispRate);
           }else{
               dispRate = new  TMdDispRate();
               dispRate.setSalesOrg(salesOrg);
               dispRate.setSalaryMet(sModel.getSalaryMet());
               dispRate.setCreateBy(user.getLoginName());
               dispRate.setCreateByTxt(user.getDisplayName());
               dispRate.setLastModified(new Date());
               dispRate.setLastModifiedBy(user.getLoginName());
               dispRate.setLastModifiedByTxt(user.getDisplayName());
               tMdDispRateMapper.addDispRateSalOrg(dispRate);
           }

           //如果前台传来的是数量计算
           if("10".equals(sModel.getSalaryMet())){
               //然后判断原来的以数量结算方式是否存在
               List<TMdDispRateItem> oldList = tMdDispRateItemMapper.getDispRateNumBySalOrg(salesOrg);
               if(oldList != null){
                   //先删除
                   tMdDispRateItemMapper.delDispRateNumBySaleOrg(salesOrg);
               }
               //再添加新的以数量结算方式
               List<DispNumEntry> entries = sModel.getDispNumEntries();
               if(entries!=null && entries.size()>0){
                   for (int i =1;i<=entries.size() ;i++ ){
                       DispNumEntry entry = entries.get(i-1);
                       TMdDispRateItem item = new TMdDispRateItem();
                       item.setSalesOrg(salesOrg);
                       item.setItemNo(PrimaryKeyUtils.generateUuidKey());
                       item.setItemIndex(i);
                       item.setRate(entry.getDispRate());
                       item.setItemValue(entry.getNum());
                       item.setCreateBy(user.getLoginName());
                       item.setCreateByTxt(user.getDisplayName());
                       item.setLastModified(new Date());
                       item.setLastModifiedBy(user.getLoginName());
                       item.setLastModifiedByTxt(user.getDisplayName());
                       tMdDispRateItemMapper.addDispRateItem(item);
                   }
               }
           }else{
               List<DispProductEntry> entries = sModel.getDispProductEntries();
               if(entries!=null && entries.size()>0){
                   for (DispProductEntry entry :entries ){
                       TMdMaraEx ex = new TMdMaraEx();
                       ex.setSalesOrg(salesOrg);
                       ex.setMatnr(entry.getMatnr());
                       ex.setRate(entry.getDispRate());
                       tMdMaraExMapper.uptProductExByCodeAndSalesOrg(ex);
                   }
               }
           }
           return 1;
      }catch (Exception e){
           throw new ServiceException(MessageCode.LOGIC_ERROR,"更新或创建失败！");
       }
    }

    /**
     * 获取当前登录人所在销售组织下的配送费率
     * @return
     */
    @Override
    public SaleOrgDispRateModel getSalesOrgDispRate() {
        SaleOrgDispRateModel model = new SaleOrgDispRateModel();
        TSysUser user = userSessionService.getCurrentUser();
        String salesOrg = user.getSalesOrg();
        if(StringUtils.isBlank(salesOrg)){
            throw  new ServiceException(MessageCode.LOGIC_ERROR,"当前登录人没分配销售组织，数据有问题！");
        }
        TMdDispRate rate = tMdDispRateMapper.getDispRateBySaleOrg(salesOrg);
        model.setSalesOrg(salesOrg);
        model.setSalaryMet(rate.getSalaryMet());
        //如果是数量
        if("10".equals(rate.getSalaryMet())){
            List<TMdDispRateItem> items = tMdDispRateItemMapper.getDispRateNumBySalOrg(salesOrg);
            List<DispNumEntry> entries = new ArrayList<DispNumEntry>();
            if(items!=null && items.size()>0){
                for(TMdDispRateItem item : items){
                    DispNumEntry entry  = new DispNumEntry();
                    entry.setNum(item.getItemValue());
                    entry.setDispRate(item.getRate());
                    entries.add(entry);
                }
            }
            model.setDispNumEntries(entries);

        }else{
            List<TMdMaraEx> items = tMdMaraExMapper.getProductsBySalesOrg(salesOrg);
            List<DispProductEntry> entries = new ArrayList<DispProductEntry>();
            if(items!=null && items.size()>0){
                for(TMdMaraEx item: items){
                    DispProductEntry entry = new DispProductEntry();
                    entry.setDispRate(item.getRate());
                    entry.setMatnr(item.getMatnr());
                    entries.add(entry);
                }
            }
            model.setDispProductEntries(entries);
        }

        return model;
    }
}
