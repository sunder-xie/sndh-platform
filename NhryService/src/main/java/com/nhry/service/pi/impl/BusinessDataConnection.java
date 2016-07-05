package com.nhry.service.pi.impl;

import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.OptionManager;
import com.nhry.webService.client.businessData.ZT_BusinessData_MaintainServiceCallbackHandler;
import com.nhry.webService.client.businessData.ZT_BusinessData_MaintainServiceStub;
import com.nhry.webService.client.businessData.functions.*;
import com.nhry.webService.client.masterData.ZT_MasterDataQueryServiceStub;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String RequisitionCreate(){

        ZSD_REQUISITION_CREATE_RFC rfc = new ZSD_REQUISITION_CREATE_RFC();
        ZSD_REQ_EKKO ekko = new ZSD_REQ_EKKO();
        COMP_CODE_type1 comp_code_type1 = new COMP_CODE_type1();
        comp_code_type1.setCOMP_CODE_type0("");
        ekko.setCOMP_CODE(comp_code_type1);

        DOC_TYPE_type1 doc_type_type1 = new DOC_TYPE_type1();
        doc_type_type1.setDOC_TYPE_type0("");
        ekko.setDOC_TYPE(doc_type_type1);
        ParsePosition pos = new ParsePosition(8);
        String dateString = formatter.format(new Date());
        com.nhry.webService.client.businessData.functions.Date date = new com.nhry.webService.client.businessData.functions.Date();
        date.setObject(formatter.parse(dateString,pos));
        ekko.setI_DATUM(date);

        PUR_GROUP_type1 pur_group_type1 = new PUR_GROUP_type1();
        pur_group_type1.setPUR_GROUP_type0("");
        ekko.setPUR_GROUP(pur_group_type1);

        SUPPL_PLNT_type1 suppl_plnt_type1 = new SUPPL_PLNT_type1();
        suppl_plnt_type1.setSUPPL_PLNT_type0("");
        ekko.setSUPPL_PLNT(suppl_plnt_type1);

        PURCH_ORG_type1 purch_org_type1 = new PURCH_ORG_type1();
        purch_org_type1.setPURCH_ORG_type0("");
        ekko.setPURCH_ORG(purch_org_type1);

        rfc.setI_EKKO(ekko);

        IT_ITEM_type0 it_item_type0 = new IT_ITEM_type0();



        ZSSD00019 zssd00019 = new ZSSD00019();
//        zssd00019.setEXT_RFX_ITEM();
//        zssd00019.setEXT_RFX_NUMBER();
//        zssd00019.setLGORT();
//        zssd00019.setMATNR();
//        zssd00019.setMEINS();
//        zssd00019.setMENGE();
//        zssd00019.setPO_ITEM();
//        zssd00019.setRESLO();
//        zssd00019.setWERKS();
//        it_item_type0.addItem();


        rfc.setIT_ITEM(it_item_type0);
        return "";
    }


}
