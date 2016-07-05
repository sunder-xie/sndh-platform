package com.nhry.service.pi.impl;

import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdBranchEx;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.OptionManager;
import com.nhry.webService.client.businessData.ZT_BusinessData_MaintainServiceCallbackHandler;
import com.nhry.webService.client.businessData.ZT_BusinessData_MaintainServiceStub;
import com.nhry.webService.client.businessData.functions.*;
import com.nhry.webService.client.masterData.ZT_MasterDataQueryServiceStub;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/4/2016.
 */
public class BusinessDataConnection {

    public static String URL = PIPropertitesUtil.getValue("PI.BusinessData.URL");

    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd");

    public static ZT_BusinessData_MaintainServiceStub getConn() throws AxisFault {
        ZT_BusinessData_MaintainServiceStub stub = new ZT_BusinessData_MaintainServiceStub(URL);
        Options options = stub._getServiceClient().getOptions();
        OptionManager.initializable(options);
        return stub;
    }

    public static String RequisitionCreate(TMdBranchEx branchEx, Date reqDate, List<Map<String, String>> items) throws RemoteException {
        ZSD_REQUISITION_CREATE_RFC rfc = new ZSD_REQUISITION_CREATE_RFC();
        ZSD_REQ_EKKO ekko = new ZSD_REQ_EKKO();
        COMP_CODE_type1 comp_code_type1 = new COMP_CODE_type1();
        comp_code_type1.setCOMP_CODE_type0(branchEx.getCompCode());
        ekko.setCOMP_CODE(comp_code_type1);

        DOC_TYPE_type1 doc_type_type1 = new DOC_TYPE_type1();
        doc_type_type1.setDOC_TYPE_type0(branchEx.getDocType());
        ekko.setDOC_TYPE(doc_type_type1);
        ParsePosition pos = new ParsePosition(8);
        String dateString = formatter.format(reqDate);
        com.nhry.webService.client.businessData.functions.Date date = new com.nhry.webService.client.businessData.functions.Date();
        date.setObject(formatter.parse(dateString, pos));
        ekko.setI_DATUM(date);

        PUR_GROUP_type1 pur_group_type1 = new PUR_GROUP_type1();
        pur_group_type1.setPUR_GROUP_type0(branchEx.getPurchGroup());
        ekko.setPUR_GROUP(pur_group_type1);

        SUPPL_PLNT_type1 suppl_plnt_type1 = new SUPPL_PLNT_type1();
        suppl_plnt_type1.setSUPPL_PLNT_type0(branchEx.getSupplPlnt());
        ekko.setSUPPL_PLNT(suppl_plnt_type1);

        PURCH_ORG_type1 purch_org_type1 = new PURCH_ORG_type1();
        purch_org_type1.setPURCH_ORG_type0(branchEx.getPurchOrg());
        ekko.setPURCH_ORG(purch_org_type1);
        rfc.setI_EKKO(ekko);
        IT_ITEM_type0 it_item_type0 = new IT_ITEM_type0();
        for (Map<String, String> item :items) {
            String item_no = item.get("ITEM_NO");
            String order_no = item.get("ORDER_NO");
            String matnr = item.get("MATNR");
            String unit = item.get("UNIT");
            String sum_qty = item.get("SUM_QTY");
            String werks = item.get("WERKS");
            String reslo = item.get("RESLO");
            String base_unit = item.get("BASE_UNIT");
            ZSSD00019 zssd00019 = new ZSSD00019();
            EXT_RFX_ITEM_type1 ext_rfx_item_type1 = new EXT_RFX_ITEM_type1();
            ext_rfx_item_type1.setEXT_RFX_ITEM_type0(item_no);
            zssd00019.setEXT_RFX_ITEM(ext_rfx_item_type1);
            EXT_RFX_NUMBER_type1 ext_rfx_number_type1 = new EXT_RFX_NUMBER_type1();
            ext_rfx_item_type1.setEXT_RFX_ITEM_type0(order_no);
            zssd00019.setEXT_RFX_NUMBER(ext_rfx_number_type1);
            LGORT_type5 lgort_type5 = new LGORT_type5();
            lgort_type5.setLGORT_type4(branchEx.getBranchNo());
            zssd00019.setLGORT(lgort_type5);
            MATNR_type5 matnr_type5 = new MATNR_type5();
            matnr_type5.setMATNR_type4(matnr);
            zssd00019.setMATNR(matnr_type5);
            MEINS_type3 meins_type3 = new MEINS_type3();
            meins_type3.setMEINS_type2(base_unit);
            zssd00019.setMEINS(meins_type3);
            MENGE_type1 menge_type1 = new MENGE_type1();
            BigDecimal qty = new BigDecimal(sum_qty);
            menge_type1.setMENGE_type0(qty);
            zssd00019.setMENGE(menge_type1);
            PO_ITEM_type1 po_item_type1 = new PO_ITEM_type1();
            po_item_type1.setPO_ITEM_type0(item_no);
            zssd00019.setPO_ITEM(po_item_type1);
            RESLO_type3 reslo_type3 = new RESLO_type3();
            reslo_type3.setRESLO_type2(reslo);
            zssd00019.setRESLO(reslo_type3);
            WERKS_type3 werks_type3 = new WERKS_type3();
            werks_type3.setWERKS_type2(werks);
            zssd00019.setWERKS(werks_type3);
            it_item_type0.addItem(zssd00019);
        }
        rfc.setIT_ITEM(it_item_type0);
        ZSD_REQUISITION_CREATE_RFCResponse response;
        response = BusinessDataConnection.getConn().requisitionCreate(rfc);
        ET_MESSAGE_type1 message = response.getET_MESSAGE();
        BAPIRET2[] bap = message.getItem();
        String mes = bap[0].getTYPE().getTYPE_type0();
        if("1".equals(mes)){
            return response.getET_EBELN().getET_EBELN_type0();
        }else{
            throw new RuntimeException(bap[0].getMESSAGE().getMESSAGE_type0());
        }
    }


}
