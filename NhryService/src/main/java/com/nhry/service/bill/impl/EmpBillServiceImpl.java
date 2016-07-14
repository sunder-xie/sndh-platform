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
import com.nhry.data.bill.dao.TMdEmpSalMapper;
import com.nhry.data.bill.domain.TMdDispRate;
import com.nhry.data.bill.domain.TMdDispRateItem;
import com.nhry.data.bill.domain.TMdEmpSal;
import com.nhry.model.bill.*;
import com.nhry.service.bill.dao.EmpBillService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.YearLastMonthUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by gongjk on 2016/7/1.
 */
public class EmpBillServiceImpl implements EmpBillService {
    private EmpBillMapper empBillMapper;
    private TMdDispRateMapper tMdDispRateMapper;
    private TMdDispRateItemMapper tMdDispRateItemMapper;
    private TMdBranchEmpMapper branchEmpMapper;
    private UserSessionService userSessionService;
    private TMdEmpSalMapper tMdEmpSalMapper;

    private TMdMaraExMapper tMdMaraExMapper;
    public void settMdEmpSalMapper(TMdEmpSalMapper tMdEmpSalMapper) {
        this.tMdEmpSalMapper = tMdEmpSalMapper;
    }
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
        TSysUser user = userSessionService.getCurrentUser();
        eSearch.setSalesOrg(user.getSalesOrg());
        return  empBillMapper.empDispDetialInfo(eSearch);
    }

    @Override
    public PageInfo empAccountReceAmount(EmpDispDetialInfoSearch eSearch) {
        TSysUser user = userSessionService.getCurrentUser();
        if(StringUtils.isBlank(eSearch.getPageNum()) || StringUtils.isBlank(eSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        eSearch.setSalesOrg(user.getSalesOrg());
        return  empBillMapper.empAccountReceAmount(eSearch);
    }

    /**
     * 根据  数量  获取按 数量结算  的配送费率
     * @param empNo
     * @param dispNum  配送数量
     * @return  配送数量所在范围的配送费率
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
     * 获取按  产品 结算的运送费率
     * @param matnr 产品编码
     * @param salesOrg  销售组织
     * @return  产品的配送费
     */

    public BigDecimal CalculateEmpTransRateByProduct(String matnr,String salesOrg){
        TMdMaraEx product = tMdMaraExMapper.getProductTransRateByCode(matnr,salesOrg);
        if(product == null){
           return new BigDecimal(0);
        }
        return product.getRate();
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
                       item.setRate(entry.getRate());
                       item.setItemValue(entry.getEndValue());
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
        model.setSalesOrgName(rate.getSalesOrgName());
        //如果是数量
        if("10".equals(rate.getSalaryMet())){
            List<TMdDispRateItem> items = tMdDispRateItemMapper.getDispRateNumBySalOrg(salesOrg);
            List<DispNumEntry> entries = new ArrayList<DispNumEntry>();
            String frontValue="0";
            if(items!=null && items.size()>0){
                for(TMdDispRateItem item : items){
                    DispNumEntry entry  = new DispNumEntry();
                    entry.setStartValue(frontValue);
                    frontValue = item.getItemValue();
                    entry.setEndValue(item.getItemValue());
                    entry.setRate(item.getRate());
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

    @Override
    public PageInfo searchEmpSalaryRep(EmpDispDetialInfoSearch eSearch) {
        TSysUser user = userSessionService.getCurrentUser();
        eSearch.setSalesOrg(user.getSalesOrg());
        if(StringUtils.isBlank(eSearch.getPageNum()) || StringUtils.isBlank(eSearch.getPageSize())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
        }
        return tMdEmpSalMapper.searchEmpSalaryRep(eSearch);
    }

    /**
     * 结算本月工资（登录人所在奶站下的所有送奶工）
     * @return
     */
    @Override
    public int setBranchEmpSalary() {
        TSysUser user = userSessionService.getCurrentUser();
        String branchNo = user.getBranchNo();
        EmpDispDetialInfoSearch search = new EmpDispDetialInfoSearch();
        search.setBranchNo(branchNo);
        //获取上个月的第一天
        search.setStartDate(YearLastMonthUtil.getLastMonthFirstDay());
        //获取上个月的最后一天
        search.setEndDate(YearLastMonthUtil.getLastMonthLastDay());
        //获取上个月  例如今天是2016-07-02  结果是201606
        String setYearMonth = YearLastMonthUtil.getYearLastMonth();

        List<TMdBranchEmp> empList =  branchEmpMapper.getAllEmpByBranchNo(user.getBranchNo(),user.getSalesOrg());
        if(empList!=null && empList.size()>0){
            for(TMdBranchEmp emp : empList){
                search.setEmpNo(emp.getEmpNo());
                this.setEmpSalary(emp,search,setYearMonth,user);
            }
        }
        return 1;
    }

    /**
     * 结算送奶员本月工资,基本工资 + 产品配送费 + 赠品配送费+ 内部销售订单配送费
     * @return
     */
    public int setEmpSalary(TMdBranchEmp emp,EmpDispDetialInfoSearch search,String setYearMonth,TSysUser user){
        String empNo = emp.getEmpNo();
        Map<String,String> map =new HashMap<String,String>();
        map.put("empNo",empNo);
        map.put("setYearMonth",setYearMonth);
        TMdEmpSal empSal = tMdEmpSalMapper.getEmpSalByEmpNoAndDate(map);
        if(empSal == null){
            empSal = new TMdEmpSal();
            empSal.setEmpSalLsh(PrimaryKeyUtils.generateUuidKey());
            empSal.setEmpNo(empNo);
            //三种配送费
            if("20".equals(emp.getSalaryMet())){
                //按产品结算  //产品配送费
                List<EmpAccoDispFeeByProduct> pro = empBillMapper.empDisByProduct(search);
                BigDecimal dispFee = this.getEmpDispFee(pro,emp.getSalesOrg());
                empSal.setDispSal(dispFee);


                //按产品结算  //内部销售配送费
                List<EmpAccoDispFeeByProduct> proIn = empBillMapper.empInDispByProduct(search);
                BigDecimal inDispFee = this.getEmpDispFee(proIn,emp.getSalesOrg());
                empSal.setInDispSal(inDispFee);

                //按产品结算  //赠品配送费


            }else{
                //按数量结算  //产品配送费
                int dispNum = empBillMapper.empDispFeeNum(search);
                if(dispNum == 0){
                    empSal.setDispSal(new BigDecimal(0));
                }else{
                    BigDecimal dispRate = this.CalculateEmpTransRateByNum(empNo,dispNum);
                    empSal.setDispSal(dispRate.multiply(new BigDecimal(dispNum)));
                }

                //按数量结算  //内部销售配送费
                int inDispNum = empBillMapper.empInDispFeeNum(search);
                if(inDispNum == 0){
                    empSal.setInDispSal(new BigDecimal(0));
                }else{
                    BigDecimal inDispRate = this.CalculateEmpTransRateByNum(empNo,inDispNum);
                    empSal.setInDispSal(inDispRate.multiply(new BigDecimal(inDispNum)));
                }

                empSal.setDispNum(dispNum+inDispNum);

                //按数量结算  //赠品配送费
              /*  int sendDispNum = empBillMapper.empFreeDispFeeNum(search);
                BigDecimal sendDispRate = this.CalculateEmpTransRateByNum(empNo,sendDispNum);
                empSal.setSendDispSal(sendDispRate.multiply(new BigDecimal(sendDispNum)));*/


            }

            empSal.setTotalSal(empSal.getInDispSal().add(empSal.getDispSal()).add(new BigDecimal(emp.getBaseSalary())));
            empSal.setCreateAt(new Date());
            empSal.setSetDate(new Date());
            empSal.setSetYearMonth(setYearMonth);
            empSal.setCreateBy(user.getLoginName());
            empSal.setCreateByTxt(user.getDisplayName());
            tMdEmpSalMapper.addEmpSal(empSal);

        }
        return 1;
    }

    //计算配送费（按产品结算）
    public BigDecimal getEmpDispFee( List<EmpAccoDispFeeByProduct> detail,String salesOrg){
             BigDecimal result = new BigDecimal(0);
            if(detail!=null && detail.size()>0){
                for(EmpAccoDispFeeByProduct pro: detail){
                    BigDecimal dispRate = this.CalculateEmpTransRateByProduct(pro.getMatnr(),salesOrg);
                    BigDecimal dispFee = dispRate.multiply(new BigDecimal(pro.getQty()));
                    result =  result.add(dispFee);
                }
            }
             return result;
     }

}
