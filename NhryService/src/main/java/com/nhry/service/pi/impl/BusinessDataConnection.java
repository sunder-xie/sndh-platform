package com.nhry.service.pi.impl;

import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdBranchEx;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.OptionManager;
import com.nhry.webService.client.PISuccessMessage;
import com.nhry.webService.client.businessData.ZT_BusinessData_MaintainServiceCallbackHandler;
import com.nhry.webService.client.businessData.ZT_BusinessData_MaintainServiceStub;
import com.nhry.webService.client.businessData.functions.*;
import com.nhry.webService.client.businessData.functions.Date;
import com.nhry.webService.client.businessData.model.Delivery;
import com.nhry.webService.client.masterData.ZT_MasterDataQueryServiceStub;
import com.nhry.webService.client.masterData.functions.ZSD_SALES_ORGANIZATION_RFC;
import com.nhry.webService.client.masterData.functions.ZSSD00007;
import com.sun.xml.bind.v2.TODO;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cbz on 7/4/2016.
 */
public class BusinessDataConnection {

    private static String URL = PIPropertitesUtil.getValue("PI.BusinessData.URL");
    private static String SIGN = PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.SIGN");
    private static String EQ = PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.OPTION.EQ");
    private static String I_DELIVERY_D = "D";
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd");

    public static ZT_BusinessData_MaintainServiceStub getConn() throws AxisFault {
        ZT_BusinessData_MaintainServiceStub stub = new ZT_BusinessData_MaintainServiceStub(URL);
        Options options = stub._getServiceClient().getOptions();
        OptionManager.initializable(options);
        return stub;
    }

    public static PISuccessMessage RequisitionCreate(TMdBranchEx branchEx, java.util.Date reqDate, List<Map<String, String>> items){
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
//            String unit = item.get("UNIT");
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
        PISuccessMessage successMessage = new PISuccessMessage();
        try {
            response = BusinessDataConnection.getConn().requisitionCreate(rfc);
            ET_MESSAGE_type1 message = response.getET_MESSAGE();
            BAPIRET2[] bap = message.getItem();

            if (bap.length > 0) {
                String mes = bap[0].getTYPE().getTYPE_type0();
                if ("1".equals(mes)) {
                    successMessage.setSuccess(true);
                    successMessage.setData(response.getET_EBELN().getET_EBELN_type0());
                    successMessage.setMessage("");
                } else {
                    successMessage.setSuccess(false);
                    successMessage.setData("");
                    successMessage.setMessage("bap[0].getMESSAGE().getMESSAGE_type0()");
                }
            } else{
                successMessage.setSuccess(true);
                successMessage.setData(response.getET_EBELN().getET_EBELN_type0());
                successMessage.setMessage("");
            }
        }catch (Exception e){
            successMessage.setSuccess(false);
            successMessage.setData("");
            successMessage.setMessage("bap[0].getMESSAGE().getMESSAGE_type0()");
        }
        return successMessage;
    }

    public static PISuccessMessage SalesOrderCreate(String KUNNR, String KUNWE, String VKORG, String BSTKD, java.util.Date LFDAT, List<Map<String,String>> items ) {

        IT_ZSSD00011_type0 it_zssd00011_type1 = new IT_ZSSD00011_type0();
        for (Map<String,String> map:items) {
            ZSSD00011 zssd00011 = new ZSSD00011();
            MATNR_type1 matnr_type1 = new MATNR_type1();
            matnr_type1.setMATNR_type0(map.get("MATNR"));
            zssd00011.setMATNR(matnr_type1);
            KWMENG_type1 kwmeng_type1 = new KWMENG_type1();
            BigDecimal kw = new BigDecimal(map.get("MWMENG"));
            kwmeng_type1.setKWMENG_type0(kw);
            zssd00011.setKWMENG(kwmeng_type1);
            VRKME_type1 vrkme_type1 = new VRKME_type1();
            vrkme_type1.setVRKME_type0(map.get("VRKME"));
            zssd00011.setVRKME(vrkme_type1);
            WERKS_type1 werks_type1 = new WERKS_type1();
            werks_type1.setWERKS_type0(map.get("WERKS"));
            zssd00011.setWERKS(werks_type1);
            LGORT_type1 lgort_type1 = new LGORT_type1();
            lgort_type1.setLGORT_type0(map.get("LGORT"));
            zssd00011.setLGORT(lgort_type1);
            POSEX_type1 posex_type1 = new POSEX_type1();
            posex_type1.setPOSEX_type0(map.get("POSEX"));
            zssd00011.setPOSEX(posex_type1);
            it_zssd00011_type1.addItem(zssd00011);
        }

        ZSD_SALESORDER_DATA_RFC_2 rfc = new ZSD_SALESORDER_DATA_RFC_2();
        rfc.setIT_ZSSD00011(it_zssd00011_type1);
        ZSSD00010 zssd00010 = new ZSSD00010();
        KUNNR_type1 kunnr_type1 = new KUNNR_type1();
        kunnr_type1.setKUNNR_type0(KUNNR);
        zssd00010.setKUNNR(kunnr_type1);
        KUNWE_type1 kunwe_type1 = new KUNWE_type1();
        kunwe_type1.setKUNWE_type0(KUNWE);
        zssd00010.setKUNWE(kunwe_type1);
        VKORG_type1 vkorg_type1 = new VKORG_type1();
        vkorg_type1.setVKORG_type0(VKORG);
        zssd00010.setVKORG(vkorg_type1);
        VTWEG_type1 vtweg_type1 = new VTWEG_type1();
        vtweg_type1.setVTWEG_type0(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.VKORG"));
        zssd00010.setVTWEG(vtweg_type1);
        SPART_type1 spart_type1 =  new SPART_type1();
        spart_type1.setSPART_type0(PIPropertitesUtil.getValue("PI.SPART"));
        zssd00010.setSPART(spart_type1);
        AUART_type1 auart_type1 = new AUART_type1();
        auart_type1.setAUART_type0(PIPropertitesUtil.getValue("PI.AUART"));
        zssd00010.setAUART(auart_type1);
        ParsePosition pos = new ParsePosition(8);
        String dateString = formatter.format(LFDAT);
        com.nhry.webService.client.businessData.functions.Date date = new com.nhry.webService.client.businessData.functions.Date();
        date.setObject(formatter.parse(dateString, pos));
        zssd00010.setLFDAT(date);
        BSTKD_type1 bstkd_type1 = new BSTKD_type1();
        bstkd_type1.setBSTKD_type0(BSTKD);
        zssd00010.setBSTKD(bstkd_type1);
        rfc.setIT_ZSSD00010(zssd00010);
        PISuccessMessage successMessage = new PISuccessMessage();
        try {
            ZSD_SALESORDER_DATA_RFC_2Response response = BusinessDataConnection.getConn().salesOrderCreate(rfc);
            ET_BAPIRETURN1_type1 et_bapirequrn1_type1 = response.getET_BAPIRETURN1();
            BAPIRET2[] bap = et_bapirequrn1_type1.getItem();
            if (bap.length > 0) {
                String mes = bap[0].getTYPE().getTYPE_type0();
                if ("1".equals(mes)) {
                    successMessage.setSuccess(true);
                    successMessage.setData(response.getET_VBELN().getET_VBELN_type0());
                    successMessage.setMessage("");
                } else {
                    successMessage.setSuccess(false);
                    successMessage.setData("");
                    successMessage.setMessage("bap[0].getMESSAGE().getMESSAGE_type0()");
                }
            } else {
                successMessage.setSuccess(true);
                successMessage.setData(response.getET_VBELN().getET_VBELN_type0());
                successMessage.setMessage("");
            }
        }catch (Exception e){
            successMessage.setSuccess(false);
            successMessage.setData("");
            successMessage.setMessage("bap[0].getMESSAGE().getMESSAGE_type0()");
        }
        return successMessage;
    }

    public static List<Delivery>  DeliveryQuery(String orderNo,boolean deliveryType) throws RemoteException {
        ZSD_DELIVERY_DATA zsd_delivery_data = new ZSD_DELIVERY_DATA();
        if(deliveryType) {
            I_DELIVERY_type1 i_delivery_type = new I_DELIVERY_type1();
            i_delivery_type.setI_DELIVERY_type0(I_DELIVERY_D);
            zsd_delivery_data.setI_DELIVERY(i_delivery_type);
        }
        //销售订单/要货单号
        IT_SO_type1 it_so_type1 = new IT_SO_type1();
        ZSSD00068 zssd00068 = new ZSSD00068();
        SIGN_type5 sign_type5 = new SIGN_type5();
        sign_type5.setSIGN_type4(SIGN);
        zssd00068.setSIGN(sign_type5);
        OPTION_type5 option_type5 = new OPTION_type5();
        option_type5.setOPTION_type4(EQ);
        zssd00068.setOPTION(option_type5);
        LOW_type3 low_type3 = new LOW_type3();
        low_type3.setLOW_type2(orderNo);
        zssd00068.setLOW(low_type3);
        it_so_type1.addItem(zssd00068);
//        if(datum!=null) {
//            IT_DATUM_type1 it_DATUM_type1 = new IT_DATUM_type1();
//            ZSSD00070 zssd00070 = new ZSSD00070();
//            SIGN_type1 sign_type1 = new SIGN_type1();
//            sign_type1.setSIGN_type0(SIGN);
//            zssd00070.setSIGN(sign_type1);
//            ParsePosition pos = new ParsePosition(8);
//            String dateString = formatter.format(datum);
//            com.nhry.webService.client.businessData.functions.Date date = new com.nhry.webService.client.businessData.functions.Date();
//            date.setObject(formatter.parse(dateString, pos));
//            zssd00070.setLOW(date);
//            OPTION_type1 option_type1 = new OPTION_type1();
//            option_type1.setOPTION_type0(EQ);
//            zssd00070.setOPTION(option_type1);
//            it_DATUM_type1.addItem(zssd00070);
//            zsd_delivery_data.setIT_DATUM(it_DATUM_type1);
//        }
        ZSD_DELIVERY_DATAResponse response = BusinessDataConnection.getConn().deliveryQuery(zsd_delivery_data);
        ET_DATA_type0 et_data_type0 = response.getET_DATA();
        ZSSD00069[] zssd00069s = et_data_type0.getItem();
        List<Delivery> deliveries = new ArrayList<Delivery>();
        if(zssd00069s.length > 0) {
            for(ZSSD00069 zssd00069 :zssd00069s ) {
                Delivery delivery = new Delivery();
                delivery.setKUNNR(zssd00069.getKUNNR().getKUNNR_type2());
                delivery.setBSTKD(zssd00069.getBSTKD().getBSTKD_type2());
                delivery.setVBELN(zssd00069.getVBELN().getVBELN_type0());
                delivery.setPOSNR(zssd00069.getPOSNR().getPOSNR_type0());
                delivery.setLFIMG(zssd00069.getLFIMG().getLFIMG_type0());
                delivery.setMEINS(zssd00069.getMEINS().getMEINS_type0());
                delivery.setKUNAG(zssd00069.getKUNAG().getKUNAG_type0());
                Object o =zssd00069.getLFDAT().getObject();
                String dateString = formatter.format(o);
                delivery.setLFDAT(formatter.parse(dateString,new ParsePosition(8)));
                delivery.setVBELV(zssd00069.getVBELV().getVBELV_type0());
                delivery.setPOSNV(zssd00069.getPOSNV().getPOSNV_type0());
                delivery.setLGORT(zssd00069.getLGORT().getLGORT_type2());
                delivery.setRESLO(zssd00069.getRESLO().getRESLO_type0());
                deliveries.add(delivery);
            }
        }
        return deliveries;
    }


}
