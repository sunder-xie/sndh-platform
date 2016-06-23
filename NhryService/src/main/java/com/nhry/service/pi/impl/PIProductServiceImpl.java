package com.nhry.service.pi.impl;

import com.nhry.data.basic.dao.PIProductMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.service.pi.dao.PIProductService;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.OptionManager;
import com.nhry.webService.client.masterData.ZT_MasterDataQueryServiceStub;
import com.nhry.webService.client.masterData.model.ET_MATNR;
import org.apache.axis2.client.Options;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by cbz on 6/21/2016.
 */
public class PIProductServiceImpl implements PIProductService {

    public PIProductMapper piProductMapper;
    public TMdMaraMapper maraMapper;

    public void setMaraMapper(TMdMaraMapper maraMapper) {
        this.maraMapper = maraMapper;
    }

    public void setPiProductMapper(PIProductMapper piProductMapper) {
        this.piProductMapper = piProductMapper;
    }
    @Override
    public int matHandler()  {
        try{
//            List<TMdMara> utMdMaras = new ArrayList<TMdMara>();
            ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse response = getData();
            List<ET_MATNR> etMatnrs = getET_MATNR(response);
            Map<String ,String> etMap = getET_MAKTX(response);
//            Map<String,TMdMara> tmp = findAll(); //存在的数据
//            List<TMdMara> itMdMaras = new ArrayList<TMdMara>();
            for (ET_MATNR etMatnr:etMatnrs) {
                TMdMara tMdMara = new TMdMara();
                tMdMara.setMatnr(etMatnr.getMATNR());
                tMdMara.setBaseUnit(etMatnr.getMEINS());
                tMdMara.setWeight(etMatnr.getNTGEW());
                tMdMara.setWeightUnit(etMatnr.getGEWEI());
                tMdMara.setMatnrTxt(etMap.get(etMatnr.getMATNR()));
//                int f = maraMapper.isProduct(etMatnr.getMATNR());
                TMdMara tmp = maraMapper.selectProductByCode(etMatnr.getMATNR());

                if(tmp!=null){
                    tMdMara.setLastModified(new Date());
                    maraMapper.updateProduct(tMdMara);
                }else{
                    tMdMara.setCreateAt(new Date());
                    maraMapper.insertProduct(tMdMara);
                }
            }
//            piProductMapper.addProductFromTo(itMdMaras);
//            piProductMapper.updateProductFromTo(utMdMaras);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    private ZT_MasterDataQueryServiceStub.ZSD_MATERAIL_DATA_RFCResponse getData() throws RemoteException {
        String url = PIPropertitesUtil.getValue("PI.MasterData.URL");
        ZT_MasterDataQueryServiceStub client = new ZT_MasterDataQueryServiceStub();
        Options options = client._getServiceClient().getOptions();
        client._getServiceClient().setOptions(OptionManager.initializable(options));
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
