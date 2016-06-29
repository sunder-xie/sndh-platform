package com.nhry.service.pi.impl;

import com.nhry.data.basic.dao.PIProductMapper;
import com.nhry.data.basic.dao.TMaraSalesMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMaraSalesKey;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.service.pi.dao.PIProductService;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.OptionManager;
import com.nhry.webService.client.masterData.ZT_MasterDataQueryServiceStub;
import com.nhry.webService.client.masterData.model.ET_KUNNR;
import com.nhry.webService.client.masterData.model.ET_MATNR;
import com.nhry.webService.client.masterData.model.ET_PARTNER;
import com.nhry.webService.client.masterData.model.ET_VKORG;
import org.apache.axis2.client.Options;

import javax.ws.rs.core.Response;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by cbz on 6/21/2016.
 */
public class PIProductServiceImpl implements PIProductService {

    public static String URL = PIPropertitesUtil.getValue("PI.MasterData.URL");
    public PIProductMapper piProductMapper;
    public TMdMaraMapper maraMapper;
    public TMaraSalesMapper maraSalesMapper;

    public void setMaraMapper(TMdMaraMapper maraMapper) {
        this.maraMapper = maraMapper;
    }

    public void setPiProductMapper(PIProductMapper piProductMapper) {
        this.piProductMapper = piProductMapper;
    }

    public void setMaraSalesMapper(TMaraSalesMapper maraSalesMapper) {
        this.maraSalesMapper = maraSalesMapper;
    }

    @Override
    public int matHandler()  {
        try{
            ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse response = getMaterailData();
            List<ET_MATNR> etMatnrs = getET_MATNR(response);
            Map<String ,String> etMap = getET_MAKTX(response);
            for (ET_MATNR etMatnr:etMatnrs) {
                TMdMara tMdMara = new TMdMara();
                tMdMara.setMatnr(etMatnr.getMATNR());
                tMdMara.setBaseUnit(etMatnr.getMEINS());
                tMdMara.setWeight(etMatnr.getNTGEW() == null ? null : etMatnr.getNTGEW().floatValue());
                tMdMara.setWeightUnit(etMatnr.getGEWEI());
                tMdMara.setMatnrTxt(etMap.get(etMatnr.getMATNR()));
                TMdMara tmp = maraMapper.selectProductByCode(etMatnr.getMATNR());
                if(tmp!=null){
                    tMdMara.setLastModified(new Date());
                    maraMapper.updateProduct(tMdMara);
                }else{
                    tMdMara.setCreateAt(new Date());
                    maraMapper.addProduct(tMdMara);
                }
                TMaraSalesKey maraSalesKey = new TMaraSalesKey();
                maraSalesKey.setMaraId(tMdMara.getMatnr());
                maraSalesKey.setSalesOrg(etMatnr.getVKORG());
                int num =maraSalesMapper.isMaraSales(maraSalesKey);
                if(num == 0){
                    maraSalesMapper.insertMaraSales(maraSalesKey);
                }
                System.out.println("-----" + tMdMara.getMatnr());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public int customerDataHandle() throws RemoteException{
        ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse response = getCustomerData();
        List<ET_KUNNR> et_kunnrs = getET_KUNNR(response);
        List<ET_PARTNER> et_partner = getET_PARTNER(response);
        List<ET_VKORG> et_vkorg = getET_VKORG(response);

        return 1;
    }

    private ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse getCustomerData() throws RemoteException{
        ZT_MasterDataQueryServiceStub client = getZt_masterDataQueryServiceStub();
        ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFC zsdCustomerDataSynRfc = new ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFC();
        ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse response = new ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse();
        response = client.customerQuery(zsdCustomerDataSynRfc);
        return response;
    }


    private ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse getMaterailData() throws RemoteException {
        ZT_MasterDataQueryServiceStub client = getZt_masterDataQueryServiceStub();
        ZT_MasterDataQueryServiceStub.IT_MTART_type0 itMtartType0 = new ZT_MasterDataQueryServiceStub.IT_MTART_type0();
        ZT_MasterDataQueryServiceStub.ZSSD00006 zssd00006 = new ZT_MasterDataQueryServiceStub.ZSSD00006();
        ZT_MasterDataQueryServiceStub.SIGN_type3 sign_type3 = new ZT_MasterDataQueryServiceStub.SIGN_type3();
        sign_type3.setSIGN_type2(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.SIGN"));
        zssd00006.setSIGN(sign_type3);
        ZT_MasterDataQueryServiceStub.OPTION_type3 option_type3 = new ZT_MasterDataQueryServiceStub.OPTION_type3();
        option_type3.setOPTION_type2(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.OPTION"));
        zssd00006.setOPTION(option_type3);
        ZT_MasterDataQueryServiceStub.LOW_type3 low_type3 = new ZT_MasterDataQueryServiceStub.LOW_type3();
        low_type3.setLOW_type2(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.LOW"));
        zssd00006.setLOW(low_type3);
        itMtartType0.addItem(zssd00006);
        ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse response ;
        ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFC zsdMaterailDataRfc = new ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFC();
        zsdMaterailDataRfc.setIT_MTART(itMtartType0);
        return client.mATQUERY(zsdMaterailDataRfc);

    }

    private List<ET_KUNNR> getET_KUNNR(ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse response){
        ZT_MasterDataQueryServiceStub.ET_KUNNR_type1 et_kunnr_type1 = response.getET_KUNNR();

        ZT_MasterDataQueryServiceStub.ZSSD00002[] zssd00002s = et_kunnr_type1.getItem();
        List<ET_KUNNR> records = new ArrayList<ET_KUNNR>();
        if(zssd00002s.length>0){
            for (ZT_MasterDataQueryServiceStub.ZSSD00002 zssd00002:zssd00002s) {
                ET_KUNNR et = new ET_KUNNR();
                et.setBUKRS(zssd00002.getBUKRS()==null?null:zssd00002.getBUKRS().getBUKRS_type0());
                et.setKUNNR(zssd00002.getKUNNR()==null?null:zssd00002.getKUNNR().getKUNNR_type0());
                et.setNAME1(zssd00002.getNAME1()==null?null:zssd00002.getNAME1().getNAME1_type0());
                et.setNAME2(zssd00002.getNAME2()==null?null:zssd00002.getNAME2().getNAME2_type0());
                et.setNAME3(zssd00002.getNAME3()==null?null:zssd00002.getNAME3().getNAME3_type0());
                et.setNAMEV(zssd00002.getNAMEV()==null?null:zssd00002.getNAMEV().getNAMEV_type0());
                et.setORT01(zssd00002.getORT01()==null?null:zssd00002.getORT01().getORT01_type0());
                et.setREGIO(zssd00002.getREGIO()==null?null:zssd00002.getREGIO().getREGIO_type0());
                et.setSTRAS(zssd00002.getSTRAS()==null?null:zssd00002.getSTRAS().getSTRAS_type0());
                et.setTELF1(zssd00002.getTELF1()==null?null:zssd00002.getTELF1().getTELF1_type0());
                records.add(et);
            }
        }
        return records;
    }

    private List<ET_PARTNER> getET_PARTNER(ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse response){
        ZT_MasterDataQueryServiceStub.ET_PARTNER_type1 et_partner_type1 = response.getET_PARTNER();

        ZT_MasterDataQueryServiceStub.ZSSD00030[] zssd00030s = et_partner_type1.getItem();
        List<ET_PARTNER> records = new ArrayList<ET_PARTNER>();
        if(zssd00030s.length>0){
            for (ZT_MasterDataQueryServiceStub.ZSSD00030 zssd00030: zssd00030s) {
                ET_PARTNER et = new ET_PARTNER();
                et.setKUNNR(zssd00030.getKUNNR()==null?null:zssd00030.getKUNNR().getKUNNR_type2());
                et.setKUNWE(zssd00030.getKUNWE()==null?null:zssd00030.getKUNWE().getKUNWE_type0());
                et.setSPART(zssd00030.getSPART()==null?null:zssd00030.getSPART().getSPART_type2());
                et.setVKORG(zssd00030.getVTWEG()==null?null:zssd00030.getVTWEG().getVTWEG_type4());
                et.setVTWEG(zssd00030.getVTWEG()==null?null:zssd00030.getVTWEG().getVTWEG_type4());
                records.add(et);
            }
        }
        return records;
    }

    private List<ET_VKORG> getET_VKORG(ZT_MasterDataQueryServiceStub.ZSD_CUSTOMER_DATA_SYN_RFCResponse response){
        List<ET_VKORG> records = new ArrayList<ET_VKORG>();
        ZT_MasterDataQueryServiceStub.ZSSD00003[] zssd00003s = response.getET_VKORG().getItem();
        if(zssd00003s.length>0){
            for (ZT_MasterDataQueryServiceStub.ZSSD00003 zssd00003:zssd00003s) {
                ET_VKORG et = new ET_VKORG();
                et.setKUNNR(zssd00003.getKUNNR()==null?null:zssd00003.getKUNNR().getKUNNR_type4());
                et.setVTWEG(zssd00003.getVTWEG()==null?null:zssd00003.getVTWEG().getVTWEG_type6());
                et.setVKORG(zssd00003.getVKORG()==null?null:zssd00003.getVKORG().getVKORG_type6());
                et.setSPART(zssd00003.getSPART()==null?null:zssd00003.getSPART().getSPART_type4());
                et.setKDGRP(zssd00003.getKDGRP()==null?null:zssd00003.getKDGRP().getKDGRP_type0());
                et.setKUNWE(zssd00003.getKUNWE()==null?null:zssd00003.getKUNWE().getKUNWE_type2());
                et.setKVGR1(zssd00003.getKVGR1()==null?null:zssd00003.getKVGR1().getKVGR1_type0());
                et.setKVGR2(zssd00003.getKVGR2()==null?null:zssd00003.getKVGR2().getKVGR2_type0());
                records.add(et);
            }
        }
        return records;
    }

    private ZT_MasterDataQueryServiceStub getZt_masterDataQueryServiceStub() throws org.apache.axis2.AxisFault {
        ZT_MasterDataQueryServiceStub client = new ZT_MasterDataQueryServiceStub(URL);
        Options options = client._getServiceClient().getOptions();
        client._getServiceClient().setOptions(OptionManager.initializable(options));
        return client;
    }

    private List<ET_MATNR>  getET_MATNR(ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse response){
        ZT_MasterDataQueryServiceStub.ET_MATNR_type1 et_matnr = response.getET_MATNR();
        ZT_MasterDataQueryServiceStub.ZSSD00005[] zssd00005s = et_matnr.getItem();
        List<ET_MATNR> records = new ArrayList<ET_MATNR>();
        if(zssd00005s.length>0){
            Map<String,String> map = new HashMap<String,String>();
            map.put("-1","");
            for (ZT_MasterDataQueryServiceStub.ZSSD00005 zssd00005 : zssd00005s) {
                ZT_MasterDataQueryServiceStub.VTWEG_type3 vtweg_type3 = zssd00005.getVTWEG();
                if(vtweg_type3 != null && "13".equals(vtweg_type3.getVTWEG_type2()) && zssd00005.getMATNR()!=null) {
                    String matrn = zssd00005.getMATNR().getMATNR_type4();
                    if ( map.get(matrn) == null) {
                        ET_MATNR etMatnr = new ET_MATNR();
                        etMatnr.setMATNR(zssd00005.getMATNR().getMATNR_type4());//    "matnr",
                        etMatnr.setBOMX(zssd00005.getBOMX() == null ? null : zssd00005.getBOMX().getBOMX_type0());   //    "bomx",
                        etMatnr.setGEWEI(zssd00005.getGEWEI() == null ? null : zssd00005.getGEWEI().getGEWEI_type0());//    "gewei"
                        etMatnr.setMEINS(zssd00005.getMEINS() == null ? null : zssd00005.getMEINS().getMEINS_type2());//    "meins",
                        etMatnr.setMTPOSMARA(zssd00005.getMTPOS_MARA() == null ? null : zssd00005.getMTPOS_MARA().getMTPOS_MARA_type0());//    "mtposmara",
                        etMatnr.setNTGEW(zssd00005.getNTGEW() == null ? null : zssd00005.getNTGEW().getNTGEW_type0());//    "ntgew",
                        etMatnr.setSPART(zssd00005.getSPART() == null ? null : zssd00005.getSPART().getSPART_type0());//    "spart",
                        etMatnr.setVKORG(zssd00005.getVKORG() == null ? null : zssd00005.getVKORG().getVKORG_type2());//    "vkorg",
                        etMatnr.setVTWEG(zssd00005.getVTWEG() == null ? null : zssd00005.getVTWEG().getVTWEG_type2());//    "vtweg",
                        etMatnr.setWERKS(zssd00005.getWERKS() == null ? null : zssd00005.getWERKS().getWERKS_type2());
                        etMatnr.setMTPOSMARA(zssd00005.getMTPOS_MARA() == null ? null : zssd00005.getMTPOS_MARA().getMTPOS_MARA_type0());
                        etMatnr.setMTART(zssd00005.getMTART() == null ? null : zssd00005.getMTART().getMTART_type0());
                        map.put(matrn,matrn);
                        records.add(etMatnr);
                    }
                }
            }
        }
        return records;
    }



    private Map getET_MAKTX(ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse response){
        ZT_MasterDataQueryServiceStub.ET_MAKTX_type1 et_maktx = response.getET_MAKTX();
        ZT_MasterDataQueryServiceStub.ZSSD00008[] zssd00008s = et_maktx.getItem();
        Map<String,String> map = new HashMap<String,String>();
        if(zssd00008s.length>0)
            for(ZT_MasterDataQueryServiceStub.ZSSD00008 zssd00008 : zssd00008s){
                map.put(zssd00008.getMATNR().getMATNR_type2(),zssd00008.getMAKTX().getMAKTX_type0());
            }
        return map;
    }

    public Map<String,TMdMara> findAll(){
        Map<String,TMdMara> results = new HashMap<String,TMdMara>();
        List<TMdMara> lists = piProductMapper.findAll();
        for(TMdMara tMdMara : lists){
            results.put(tMdMara.getMatnr(),tMdMara);
        }
        return results;
    }
}
