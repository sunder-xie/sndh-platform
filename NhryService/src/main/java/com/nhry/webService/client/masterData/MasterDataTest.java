package com.nhry.webService.client.masterData;

import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl;
import org.apache.axis2.util.URL;

/**
 * Created by cbz on 6/17/2016.
 */
public class MasterDataTest {
    public static void main(String [] args){
        try{
            String url = "http://pidev.newhope.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=ZT_Q&receiverParty=&receiverService=&interface=ZT_MasterDataQuery&interfaceNamespace=urn%3Anewhopedairy.cn%3AZT%3AMasterData";
            ZT_MasterDataQueryServiceStub client = new ZT_MasterDataQueryServiceStub(url);
            Options options = client._getServiceClient().getOptions();
            HttpTransportPropertiesImpl.Authenticator authenticator1 = new HttpTransportPropertiesImpl.Authenticator();
            authenticator1.setPassword("CANDYTOZT");
            authenticator1.setUsername("ZT_CONN");
            options.setProperty(HTTPConstants.AUTHENTICATE, authenticator1);
            options.setProperty(HTTPConstants.SO_TIMEOUT,new Integer(300000));
            client._getServiceClient().setOptions(options);
            ZT_MasterDataQueryServiceStub.ZMM_POS_24DATA zmm_pos_24DATA = new ZT_MasterDataQueryServiceStub.ZMM_POS_24DATA();
            ZT_MasterDataQueryServiceStub.ZMMRP022_S_RFC_WERKS item = new ZT_MasterDataQueryServiceStub.ZMMRP022_S_RFC_WERKS();
            ZT_MasterDataQueryServiceStub.SIGN_type9 sign = new ZT_MasterDataQueryServiceStub.SIGN_type9();
            sign.setSIGN_type8("I");
            ZT_MasterDataQueryServiceStub.OPTION_type9 optionType7 = new ZT_MasterDataQueryServiceStub.OPTION_type9();
            optionType7.setOPTION_type8("EQ");
            ZT_MasterDataQueryServiceStub.LOW_type9 lowType7 = new ZT_MasterDataQueryServiceStub.LOW_type9();
            lowType7.setLOW_type8("4030");
//            item.setSIGN(sign);
//            item.setOPTION(optionType7);
//            item.setLOW(lowType7);
            ZT_MasterDataQueryServiceStub.IT_WERKS_type1 itWerksType1 = new ZT_MasterDataQueryServiceStub.IT_WERKS_type1();
            itWerksType1.addItem(item);
            zmm_pos_24DATA.setIT_WERKS(itWerksType1);
            ZT_MasterDataQueryServiceStub.ZMM_POS_24DATAResponse response = new ZT_MasterDataQueryServiceStub.ZMM_POS_24DATAResponse();
            response = client.matWHWQuery(zmm_pos_24DATA);
            ZT_MasterDataQueryServiceStub.ET_LGORT_type0 et_lgort_type0 = response.getET_LGORT();
            ZT_MasterDataQueryServiceStub.T001L[] t001L = et_lgort_type0.getItem();

            for(ZT_MasterDataQueryServiceStub.T001L t001L1 : t001L){
                System.out.println("--------"+t001L1.getLGORT());
            }

            ZT_MasterDataQueryServiceStub.ET_DATA_type0 dataType0 = response.getET_DATA();
            ZT_MasterDataQueryServiceStub.ZTSD00024[] results = dataType0.getItem();
            for(ZT_MasterDataQueryServiceStub.ZTSD00024 re : results){
                System.out.println("##################"+re.getLGORT());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
