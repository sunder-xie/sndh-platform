package com.nhry.service.pi.impl;

import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.data.milktrans.domain.TSsmRedateByTrade;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.config.dao.DictionaryService;
import com.nhry.service.milktrans.dao.RedateByTradeService;
import com.nhry.service.pi.dao.PIRedateByTradeService;
import com.nhry.service.pi.pojo.SalesOrderHeader;
import com.nhry.utils.EnvContant;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.utils.date.*;
import com.nhry.utils.date.Date;
import com.nhry.webService.OptionManager;
import com.nhry.webService.client.PISuccessMessage;
import com.nhry.webService.client.PISuccessTMessage;
import com.nhry.webService.client.VipInfoData.ZT_CRM_BuData_MaintainServiceStub;
import com.nhry.webService.client.VipInfoData.functions.*;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cbz on 12/26/2016.
 */
public class PIRedateByTradeServiceImpl implements PIRedateByTradeService{
    private static String URL = EnvContant.getSystemConst("PI.VipInfoData.URL");
    private static Logger logger = Logger.getLogger(PIRedateByTradeServiceImpl.class);
    private static String TYPE_CODE = "1017";
    private static String ITEM_CODE = "1";
    private RedateByTradeService redateByTradeService;
    private DictionaryService dictionaryService;
    private TMdBranchMapper branchMapper;

    public void setBranchMapper(TMdBranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }

    public void setRedateByTradeService(RedateByTradeService redateByTradeService) {
        this.redateByTradeService = redateByTradeService;
    }

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * 销售订单编号 35位
     *
     * @return
     */
    private String generateSal35Id(int i,String branchNo) {
        TMdBranch branch = branchMapper.getBranchByNo(branchNo);
        StringBuilder uuid = new StringBuilder();
        uuid.append("DH001");
        uuid.append("A");
        uuid.append(branch.getCompanyCode());
        uuid.append(branchNo.substring(1));
        uuid.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
        uuid.append(new Random().nextInt(80) + 10 + i);
        System.out.println("------------------------" + uuid.toString());
        return uuid.toString();
    }

    @Override
    public String sendRedateByTradeToCRM() {
        NHSysCodeItem codeItem = new NHSysCodeItem();
        codeItem.setTypeCode(TYPE_CODE);
        codeItem.setItemCode(ITEM_CODE);
        codeItem = dictionaryService.findCodeItenByCode(codeItem);
        String dxpz = codeItem.getItemName();
        String flc = codeItem.getAttr2();
        List<TSsmRedateByTrade> tradeList = redateByTradeService.findNoSendRedateByTrade();
        for(TSsmRedateByTrade trade : tradeList){
            PISuccessMessage message = new PISuccessMessage();
            String dhNo = "";
            //贷项凭证
            if(dxpz.contains(trade.getSalesOrg())){
                List<Map<String, String>> items = new ArrayList<Map<String, String>>();
                SalesOrderHeader orderHeader = new SalesOrderHeader();
                dhNo = generateSal35Id(2,trade.getBranchNo());
                orderHeader.setBSTKD(dhNo);
                orderHeader.setKUNNR(trade.getBranchNo());
                orderHeader.setActivityId(trade.getPromNo());
                orderHeader.setVKORG(trade.getSalesOrg());
                orderHeader.setAuartType(PIPropertitesUtil.getValue("PI.AUART.ZCR"));
                orderHeader.setVTWEG(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.VKORG13"));
                orderHeader.setLFDAT(new Date());
                Map<String, String> item = new HashMap<String,String>();
                item.put("MATNR",trade.getMatnr());
                item.put("KBETR",trade.getRedate().toString());
                item.put("AUGRU","Z05");
                item.put("PROM_NO",trade.getPromNo());
                items.add(item);
                message = BusinessDataConnection.SalesOrderCreate(items,orderHeader);
            }
            //返利池
            if(flc.contains(trade.getSalesOrg())){
                List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
                Map<String,Object> item = new HashMap<String,Object>();
                item.put("branchNo",trade.getBranchNo());
                item.put("redate",trade.getRedate());
                items.add(item);
                message = createRebateByTrade(trade.getPromNo(),trade.getOrderNo(),"ddddd",items);
            }
            if(message.isSuccess()){
                TSsmRedateByTrade t = new TSsmRedateByTrade();
                t.setOrderNo(trade.getOrderNo());
                t.setDhNo(dhNo);
                t.setCrmNo(message.getData().toString());
                t.setLastModified(new Date());
                redateByTradeService.update(t);
            }else{
                logger.error(message.getMessage()+trade.getOrderNo()+trade.getPromNo());
            }
        }
        return "1";
    }

    /**
     * 会员信息接口连接
     *
     * @return
     * @throws AxisFault
     */
    private ZT_CRM_BuData_MaintainServiceStub connMaintainService() throws AxisFault {
        ZT_CRM_BuData_MaintainServiceStub client = new ZT_CRM_BuData_MaintainServiceStub(URL);
        Options options = client._getServiceClient().getOptions();
        client._getServiceClient().setOptions(OptionManager.initializable(options));
        return client;
    }
    
    private PISuccessMessage createRebateByTrade(String prom, String dhNo,String des, List<Map<String,Object>> items) {
        PISuccessMessage message = new PISuccessMessage();
        try {
            Z_CRM_CREATE_REBATE_BY_TRADE params = new Z_CRM_CREATE_REBATE_BY_TRADE();
            IV_EXTERNAL_ID_type1 externalId = new IV_EXTERNAL_ID_type1();
            externalId.setIV_EXTERNAL_ID_type0(prom);
            params.setIV_EXTERNAL_ID(externalId);
            IV_DESCRIPTION_type1 description = new IV_DESCRIPTION_type1();
            description.setIV_DESCRIPTION_type0(des);
            params.setIV_DESCRIPTION(description);
            IV_PROM_ID_type1 promId = new IV_PROM_ID_type1();
            promId.setIV_PROM_ID_type0(dhNo);
            params.setIV_PROM_ID(promId);
            IT_REBATE_TAB_type0 rebateTab = new IT_REBATE_TAB_type0();
            items.forEach(e->{
                String branchNo = String.valueOf(e.get("branchNo"));
                BigDecimal redate = new BigDecimal(String.valueOf(e.get("redate")));
                ZSCRM_ZTAB0000JQ jq = new ZSCRM_ZTAB0000JQ();
                ZZFLD0000A7_type1 a7 = new ZZFLD0000A7_type1();
                a7.setZZFLD0000A7_type0(redate);
                jq.setZZFLD0000A7(a7);
                ZZFLD0000DE_type1 de = new ZZFLD0000DE_type1();
                de.setZZFLD0000DE_type0(branchNo);
                jq.setZZFLD0000DE(de);
                rebateTab.addItem(jq);
            });
            params.setIT_REBATE_TAB(rebateTab);
            Z_CRM_CREATE_REBATE_BY_TRADEResponse response = connMaintainService().createRebateByTrade(params);
            String msg = response.getEV_MESSAGE().getEV_MESSAGE_type0();
            String crmId = response.getEV_OBJECT_ID().getEV_OBJECT_ID_type0();
            if(StringUtils.isNotEmpty(crmId)){
                message.setSuccess(true);
                message.setMessage(msg);
                message.setData(crmId);
            }else{
                message.setSuccess(false);
                message.setMessage(msg);
            }
        }catch (Exception e){
            message.setSuccess(false);
            message.setMessage(e.getMessage());
        }
        return message;
    }
}
