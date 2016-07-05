package com.nhry.service.pi.impl;

import com.nhry.data.basic.dao.*;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdDealer;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.service.pi.dao.PIProductService;
import com.nhry.utils.PIPropertitesUtil;
import com.nhry.webService.OptionManager;
import com.nhry.webService.client.masterData.ZT_MasterDataQueryServiceStub;
import com.nhry.webService.client.masterData.functions.*;
import com.nhry.webService.client.masterData.model.*;
import org.apache.axis2.client.Options;

import java.rmi.RemoteException;
import java.util.*;
import java.util.Date;

/**
 * Created by cbz on 6/21/2016.
 */
public class PIProductServiceImpl implements PIProductService {

    public static String URL = PIPropertitesUtil.getValue("PI.MasterData.URL");
    public static String VKORG = PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.VKORG");
    public static String BRANDCHTYPE_ZY = "01";
    public static String BRANDCHTYPE_WB = "02";
    public PIProductMapper piProductMapper;
    public TMdMaraMapper maraMapper;
    public TMaraSalesMapper maraSalesMapper;
    public TMdBranchMapper branchMapper;
    public TMdDealerMapper dealerMapper;

    public void setMaraMapper(TMdMaraMapper maraMapper) {
        this.maraMapper = maraMapper;
    }

    public void setPiProductMapper(PIProductMapper piProductMapper) {
        this.piProductMapper = piProductMapper;
    }

    public void setMaraSalesMapper(TMaraSalesMapper maraSalesMapper) {
        this.maraSalesMapper = maraSalesMapper;
    }

    public void setBranchMapper(TMdBranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }

    public void setDealerMapper(TMdDealerMapper dealerMapper) {
        this.dealerMapper = dealerMapper;
    }

    /**
     * 物料查询
     *
     * @return
     */
    @Override
    public int matHandler() {
        try {
            ZSD_MATERAIL_DATA_RFCResponse response = getMaterailData();
            List<ET_MATNR> etMatnrs = getET_MATNR(response);
            Map<String, String> etMap = getET_MAKTX(response);
            for (ET_MATNR etMatnr : etMatnrs) {
                TMdMara tMdMara = new TMdMara();
                tMdMara.setMatnr(etMatnr.getMATNR());
                tMdMara.setBaseUnit(etMatnr.getMEINS());
                tMdMara.setWeight(etMatnr.getNTGEW() == null ? null : etMatnr.getNTGEW().floatValue());
                tMdMara.setWeightUnit(etMatnr.getGEWEI());
                tMdMara.setMatnrTxt(etMap.get(etMatnr.getMATNR()));
                tMdMara.setSalesOrg(etMatnr.getVKORG());
                TMdMara tmp = maraMapper.selectProductByCode(tMdMara);
                if (tmp != null) {
                    tMdMara.setLastModified(new Date());
                    maraMapper.updateProduct(tMdMara);
                } else {
                    tMdMara.setCreateAt(new Date());
                    maraMapper.addProduct(tMdMara);
                }
                System.out.println("-----" + tMdMara.getMatnr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 自营奶站和供应商及供应商奶站
     * @return
     */
    @Override
    public int customerDataHandle() {
        try {
            ZSD_CUSTOMER_DATA_SYN_RFCResponse response = getCustomerData();
            Map<String, ET_KUNNR> et_kunnrs = getET_KUNNR(response);
            Map<String, List<ET_VKORG>> et_vkorgs = getET_VKORG(response);
            List<ET_PARTNER> et_partner = getET_PARTNER(response);
            Map<String, List<ET_PARTNER>> partners = new HashMap<String, List<ET_PARTNER>>();

            List<ET_VKORG> zys = et_vkorgs.get("01");
            for (ET_VKORG et_vkorg : zys) {
                saveBranch(et_kunnrs, BRANDCHTYPE_ZY, et_vkorg.getVKORG(), et_vkorg.getKUNNR(), et_vkorg.getVTWEG(), "");
            }
            List<ET_VKORG> wbs = et_vkorgs.get("02");
            Map<String, ET_VKORG> jxs = new HashMap<String, ET_VKORG>();
            for (ET_VKORG et_vkorg : wbs) {
                String kunner = et_vkorg.getKUNNR();
                for (ET_PARTNER et_partner1 : et_partner) {
                    if (kunner.equals(et_partner1.getKUNNR())) {
                        jxs.put(kunner, et_vkorg);
                    }
                }
            }
            /**
             * 保存经销商信息
             */
            for (Map.Entry<String, ET_VKORG> entry : jxs.entrySet()) {
                saveDealer(et_kunnrs, entry.getKey(), entry.getValue().getVKORG());
            }

//            for(ET_PARTNER partner : et_partner){
            for (Map.Entry<String, ET_VKORG> entry : jxs.entrySet()) {
                String kunnr = entry.getKey();
                List<ET_PARTNER> l = new ArrayList<>();
                for (ET_PARTNER p : et_partner) {
                    if (kunnr.equals(p.getKUNNR()) && !kunnr.equals(p.getKUNWE())) {

                        l.add(p);
                    }
                }
                if (l.size() > 0)
                    partners.put(kunnr, l);
            }

            for (Map.Entry<String, List<ET_PARTNER>> entry : partners.entrySet()) {
                String key = entry.getKey();
                String vkorg = "";
                List<ET_PARTNER> lists = entry.getValue();
                for (ET_PARTNER partner : lists) {
                    vkorg = partner.getVKORG();
                    saveBranch(et_kunnrs, BRANDCHTYPE_WB, partner.getVKORG(), partner.getKUNWE(), partner.getVTWEG(), key);
                }
//                saveDealer(et_kunnrs, key, vkorg);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    private void saveDealer(Map<String, ET_KUNNR> et_kunnrs, String key, String vkorg) {
        ET_KUNNR et_kunnr = et_kunnrs.get(key);
        TMdDealer dealer = dealerMapper.selectDealerByNo(key);
        if (dealer == null) {
            dealer = new TMdDealer();
            dealer.setDealerNo(key);
            dealer.setDealerName(et_kunnr.getNAME1());
            dealer.setCity(et_kunnr.getORT01());
            dealer.setProvince(et_kunnr.getREGIO());
            dealer.setCreateAt(new Date());
            dealer.setCreateBy("ECC");
            dealer.setCreateByTxt("ECC");
            dealer.setSalesOrg(vkorg);
            dealer.setAddress(et_kunnr.getSTRAS());
            dealer.setTel(et_kunnr.getTELF1());
            dealer.setDelFlag("N");
            dealerMapper.insertDealer(dealer);
        } else {
            dealer.setDealerNo(key);
            dealer.setDealerName(et_kunnr.getNAME1());
            dealer.setCity(et_kunnr.getORT01());
            dealer.setProvince(et_kunnr.getREGIO());
            dealer.setCreateAt(new Date());
            dealer.setCreateBy("ECC");
            dealer.setCreateByTxt("ECC");
            dealer.setSalesOrg(vkorg);
            dealer.setAddress(et_kunnr.getSTRAS());
            dealer.setTel(et_kunnr.getTELF1());
            dealer.setDelFlag("N");
            dealerMapper.updateDealer(dealer);
        }
    }

    private void saveBranch(Map<String, ET_KUNNR> et_kunnrs, String branchGroup, String vkorg, String kunnr, String vtweg, String dealerNo) {
        ET_KUNNR et_kunnr = et_kunnrs.get(kunnr);
        if (et_kunnr == null) {
            System.out.println(kunnr + "@@@@@@@@@@@@@@@");
        } else {
            TMdBranch branch = branchMapper.selectBranchByNo(et_kunnr.getKUNNR());
            if (branch == null) {
                branch = new TMdBranch();
                branch.setBranchNo(et_kunnr.getKUNNR());
                branch.setBranchName(et_kunnr.getNAME1());
                branch.setTel(et_kunnr.getTELF1());
                branch.setAddress(et_kunnr.getSTRAS());
                branch.setCity(et_kunnr.getORT01());
                branch.setBranchGroup(branchGroup);
                branch.setProvince(et_kunnr.getREGIO());
                branch.setSalesCha(vtweg);
                branch.setSalesOrg(vkorg);
                branch.setCreateAt(new Date());
                branch.setCreateBy("ECC");
                branch.setCreateByTxt("ECC");
                branch.setDealerNo(dealerNo);
                branchMapper.addBranch(branch);
            } else {
                branch.setBranchNo(et_kunnr.getKUNNR());
                branch.setBranchName(et_kunnr.getNAME1());
                branch.setTel(et_kunnr.getTELF1());
                branch.setAddress(et_kunnr.getSTRAS());
                branch.setCity(et_kunnr.getORT01());
                branch.setBranchGroup(branchGroup);
                branch.setProvince(et_kunnr.getREGIO());
                branch.setSalesCha(vtweg);
                branch.setSalesOrg(vkorg);
                branch.setDealerNo(dealerNo);
                branchMapper.updateBranch(branch);
            }
        }
    }

    /**
     * 工厂
     * @return
     * @throws RemoteException
     */
    @Override
    public int matWHWHandler() throws RemoteException {
        Map<String, String> etMap = getET_DATA();
        Map<String, ET_LGORT> lgMap = getET_LGORT();
        for (Map.Entry<String, String> entry : etMap.entrySet()) {
            TMdBranch branch = branchMapper.selectBranchByNo(entry.getKey());
            ET_LGORT et = lgMap.get(entry.getValue());
            if (et != null && branch != null) {
                branch.setWerks(et.getWERKS());
                branch.setLgort(entry.getValue());
                branchMapper.updateBranch(branch);
            }
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        return 1;
    }

    protected ZSD_CUSTOMER_DATA_SYN_RFCResponse getCustomerData() throws RemoteException {
        ZT_MasterDataQueryServiceStub client = getZt_masterDataQueryServiceStub();
        ZSD_CUSTOMER_DATA_SYN_RFC zsdCustomerDataSynRfc = new ZSD_CUSTOMER_DATA_SYN_RFC();
        ZSD_CUSTOMER_DATA_SYN_RFCResponse response = new ZSD_CUSTOMER_DATA_SYN_RFCResponse();
        IT_VTWEG_type1 it_vtweg_type1 = new IT_VTWEG_type1();
        ZSSD00071 zssd00071 = new ZSSD00071();
        SIGN_type5 sign_type5 = new SIGN_type5();
        sign_type5.setSIGN_type4("I");
        zssd00071.setSIGN(sign_type5);
        OPTION_type5 option_type5 = new OPTION_type5();
        option_type5.setOPTION_type4("EQ");
        zssd00071.setOPTION(option_type5);
        LOW_type5 low_type5 = new LOW_type5();
        low_type5.setLOW_type4(VKORG);
        zssd00071.setLOW(low_type5);
        it_vtweg_type1.addItem(zssd00071);
        zsdCustomerDataSynRfc.setIT_VTWEG(it_vtweg_type1);
        response = client.customerQuery(zsdCustomerDataSynRfc);
        return response;
    }


    private ZSD_MATERAIL_DATA_RFCResponse getMaterailData() throws RemoteException {
        ZT_MasterDataQueryServiceStub client = getZt_masterDataQueryServiceStub();
        IT_MTART_type0 itMtartType0 = new IT_MTART_type0();
        ZSSD00006 zssd00006 = new ZSSD00006();
        SIGN_type3 sign_type3 = new SIGN_type3();
        sign_type3.setSIGN_type2(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.SIGN"));
        zssd00006.setSIGN(sign_type3);
        OPTION_type3 option_type3 = new OPTION_type3();
        option_type3.setOPTION_type2(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.OPTION.EQ"));
        zssd00006.setOPTION(option_type3);
        LOW_type3 low_type3 = new LOW_type3();
        low_type3.setLOW_type2(PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.LOW"));
        zssd00006.setLOW(low_type3);
        itMtartType0.addItem(zssd00006);
        ZSD_MATERAIL_DATA_RFCResponse response;
        ZSD_MATERAIL_DATA_RFC zsdMaterailDataRfc = new ZSD_MATERAIL_DATA_RFC();
        zsdMaterailDataRfc.setIT_MTART(itMtartType0);
        return client.mATQUERY(zsdMaterailDataRfc);

    }

    /**
     * 客户对应的库存地点
     *
     * @return
     */
    private Map<String, String> getET_DATA() throws RemoteException {
        ZMM_POS_24DATAResponse response = getMatWHQuery();
        ET_DATA_type0 et_data_type0 = response.getET_DATA();
        ZTSD00024[] ztsd00024s = et_data_type0.getItem();
        Map<String, String> map = new HashMap<String, String>();
        for (ZTSD00024 zt : ztsd00024s) {
            String kunner = zt.getKUNNR1().getKUNNR1_type0();
            String lgort = zt.getLGORT().getLGORT_type0();
            if (org.apache.commons.lang.StringUtils.isNotEmpty(kunner)) {
                map.put(kunner, lgort);
            }
        }
        return map;
    }

    private Map<String, ET_LGORT> getET_LGORT() throws RemoteException {
        ZMM_POS_24DATAResponse response = getMatWHQuery();
        ET_LGORT_type0 et_lgort_type0 = response.getET_LGORT();
        T001L[] t001Ls = et_lgort_type0.getItem();
        Map<String, ET_LGORT> result = new HashMap<String, ET_LGORT>();
        for (T001L t001L : t001Ls) {
            ET_LGORT et = new ET_LGORT();
            et.setWERKS(t001L.getWERKS().getWERKS_type6());
            et.setLGOBE(t001L.getLGOBE().getLGOBE_type0());
            et.setLGORT(t001L.getLGORT().getLGORT_type2());
            result.put(t001L.getLGORT().getLGORT_type2(), et);
        }
        return result;
    }


    private ZMM_POS_24DATAResponse getMatWHQuery() throws RemoteException {
        ZT_MasterDataQueryServiceStub client = getZt_masterDataQueryServiceStub();
        ZMM_POS_24DATA zmm_pos_24DATA = new ZMM_POS_24DATA();
        return client.matWHWQuery(zmm_pos_24DATA);
    }

    private Map<String, ET_KUNNR> getET_KUNNR(ZSD_CUSTOMER_DATA_SYN_RFCResponse response) {
        ET_KUNNR_type1 et_kunnr_type1 = response.getET_KUNNR();

        ZSSD00002[] zssd00002s = et_kunnr_type1.getItem();
        List<ET_KUNNR> records = new ArrayList<ET_KUNNR>();
        Map<String, ET_KUNNR> map = new HashMap<String, ET_KUNNR>();
        if (zssd00002s.length > 0) {
            for (ZSSD00002 zssd00002 : zssd00002s) {
                ET_KUNNR et = new ET_KUNNR();
                et.setBUKRS(zssd00002.getBUKRS() == null ? null : zssd00002.getBUKRS().getBUKRS_type0());
                et.setKUNNR(zssd00002.getKUNNR() == null ? null : zssd00002.getKUNNR().getKUNNR_type0());
                et.setNAME1(zssd00002.getNAME1() == null ? null : zssd00002.getNAME1().getNAME1_type0());
                et.setNAME2(zssd00002.getNAME2() == null ? null : zssd00002.getNAME2().getNAME2_type0());
                et.setNAME3(zssd00002.getNAME3() == null ? null : zssd00002.getNAME3().getNAME3_type0());
                et.setNAMEV(zssd00002.getNAMEV() == null ? null : zssd00002.getNAMEV().getNAMEV_type0());
                et.setORT01(zssd00002.getORT01() == null ? null : zssd00002.getORT01().getORT01_type0());
                et.setREGIO(zssd00002.getREGIO() == null ? null : zssd00002.getREGIO().getREGIO_type0());
                et.setSTRAS(zssd00002.getSTRAS() == null ? null : zssd00002.getSTRAS().getSTRAS_type0());
                et.setTELF1(zssd00002.getTELF1() == null ? null : zssd00002.getTELF1().getTELF1_type0());
                map.put(zssd00002.getKUNNR().getKUNNR_type0(), et);
            }
        }
        return map;
    }

    private List<ET_PARTNER> getET_PARTNER(ZSD_CUSTOMER_DATA_SYN_RFCResponse response) {
        ET_PARTNER_type1 et_partner_type1 = response.getET_PARTNER();

        ZSSD00030[] zssd00030s = et_partner_type1.getItem();
        List<ET_PARTNER> records = new ArrayList<ET_PARTNER>();
        if (zssd00030s.length > 0) {
            for (ZSSD00030 zssd00030 : zssd00030s) {
                String vtweg = zssd00030.getVTWEG() == null ? null : zssd00030.getVTWEG().getVTWEG_type4();
//                if(vtweg!=null && VKORG.equals(vtweg)) {
                ET_PARTNER et = new ET_PARTNER();
                et.setKUNNR(zssd00030.getKUNNR() == null ? null : zssd00030.getKUNNR().getKUNNR_type2());
                et.setKUNWE(zssd00030.getKUNWE() == null ? null : zssd00030.getKUNWE().getKUNWE_type0());
                et.setSPART(zssd00030.getSPART() == null ? null : zssd00030.getSPART().getSPART_type2());
                et.setVKORG(zssd00030.getVKORG() == null ? null : zssd00030.getVKORG().getVKORG_type4());
                et.setVTWEG(zssd00030.getVTWEG() == null ? null : zssd00030.getVTWEG().getVTWEG_type4());
                records.add(et);
            }
//            }
        }
        return records;
    }

    private Map<String, List<ET_VKORG>> getET_VKORG(ZSD_CUSTOMER_DATA_SYN_RFCResponse response) {
        List<ET_VKORG> records = new ArrayList<ET_VKORG>();
        ZSSD00003[] zssd00003s = response.getET_VKORG().getItem();

        Map<String, List<ET_VKORG>> map = new HashMap<String, List<ET_VKORG>>();
        List<ET_VKORG> zy = new ArrayList<>();
        List<ET_VKORG> wb = new ArrayList<>();
        if (zssd00003s.length > 0) {
            for (ZSSD00003 zssd00003 : zssd00003s) {
                ET_VKORG et = new ET_VKORG();
                String kvgr2 = zssd00003.getKVGR2() == null ? null : zssd00003.getKVGR2().getKVGR2_type0();
                et.setKUNNR(zssd00003.getKUNNR() == null ? null : zssd00003.getKUNNR().getKUNNR_type4());
                et.setVTWEG(zssd00003.getVTWEG() == null ? null : zssd00003.getVTWEG().getVTWEG_type6());
                et.setVKORG(zssd00003.getVKORG() == null ? null : zssd00003.getVKORG().getVKORG_type6());
                et.setSPART(zssd00003.getSPART() == null ? null : zssd00003.getSPART().getSPART_type4());
                et.setKDGRP(zssd00003.getKDGRP() == null ? null : zssd00003.getKDGRP().getKDGRP_type0());
                et.setKUNWE(zssd00003.getKUNWE() == null ? null : zssd00003.getKUNWE().getKUNWE_type2());
                et.setKVGR1(zssd00003.getKVGR1() == null ? null : zssd00003.getKVGR1().getKVGR1_type0());
                et.setKVGR2(zssd00003.getKVGR2() == null ? null : zssd00003.getKVGR2().getKVGR2_type0());
                if (org.apache.commons.lang.StringUtils.isNotEmpty(kvgr2) && "01".equals(kvgr2)) {
                    zy.add(et);
                } else if (org.apache.commons.lang.StringUtils.isNotEmpty(kvgr2) && "02".equals(kvgr2)) {
                    wb.add(et);
                }
            }
            map.put("01", zy);
            map.put("02", wb);
        }
        return map;
    }

    private ZT_MasterDataQueryServiceStub getZt_masterDataQueryServiceStub() throws org.apache.axis2.AxisFault {
        ZT_MasterDataQueryServiceStub client = new ZT_MasterDataQueryServiceStub(URL);
        Options options = client._getServiceClient().getOptions();
        client._getServiceClient().setOptions(OptionManager.initializable(options));
        return client;
    }

    /**
     * 获取产品数据
     *
     * @param response
     * @return
     */
    private List<ET_MATNR> getET_MATNR(ZSD_MATERAIL_DATA_RFCResponse response) {

        String ZORM = PIPropertitesUtil.getValue("PI.MasterData.mATQUERY.ZORM");
        ET_MATNR_type1 et_matnr = response.getET_MATNR();
        ZSSD00005[] zssd00005s = et_matnr.getItem();
        List<ET_MATNR> records = new ArrayList<ET_MATNR>();
        if (zssd00005s.length > 0) {
            for (ZSSD00005 zssd00005 : zssd00005s) {
                VTWEG_type3 vtweg_type3 = zssd00005.getVTWEG();
                if (vtweg_type3 != null && VKORG.equals(vtweg_type3.getVTWEG_type2()) && zssd00005.getMATNR() != null && zssd00005.getMTPOS_MARA()!=null && ZORM.equals(zssd00005.getMTPOS_MARA().getMTPOS_MARA_type0())) {
                    ET_MATNR etMatnr = new ET_MATNR();
                    etMatnr.setMATNR(zssd00005.getMATNR().getMATNR_type6());//    "matnr",
                    etMatnr.setBOMX(zssd00005.getBOMX() == null ? null : zssd00005.getBOMX().getBOMX_type0());   //    "bomx",
                    etMatnr.setGEWEI(zssd00005.getGEWEI() == null ? null : zssd00005.getGEWEI().getGEWEI_type2());//    "gewei"
                    etMatnr.setMEINS(zssd00005.getMEINS() == null ? null : zssd00005.getMEINS().getMEINS_type2());//    "meins",
                    etMatnr.setMTPOSMARA(zssd00005.getMTPOS_MARA() == null ? null : zssd00005.getMTPOS_MARA().getMTPOS_MARA_type0());//    "mtposmara",
                    etMatnr.setNTGEW(zssd00005.getNTGEW() == null ? null : zssd00005.getNTGEW().getNTGEW_type0());//    "ntgew",
                    etMatnr.setSPART(zssd00005.getSPART() == null ? null : zssd00005.getSPART().getSPART_type0());//    "spart",
                    etMatnr.setVKORG(zssd00005.getVKORG() == null ? null : zssd00005.getVKORG().getVKORG_type2());//    "vkorg",
                    etMatnr.setVTWEG(zssd00005.getVTWEG() == null ? null : zssd00005.getVTWEG().getVTWEG_type2());//    "vtweg",
                    etMatnr.setWERKS(zssd00005.getWERKS() == null ? null : zssd00005.getWERKS().getWERKS_type2());
                    etMatnr.setMTPOSMARA(zssd00005.getMTPOS_MARA() == null ? null : zssd00005.getMTPOS_MARA().getMTPOS_MARA_type0());
                    etMatnr.setMTART(zssd00005.getMTART() == null ? null : zssd00005.getMTART().getMTART_type0());
                    records.add(etMatnr);
                }
            }
        }
        return records;
    }

    private Map getET_MAKTX(ZSD_MATERAIL_DATA_RFCResponse response) {
        ET_MAKTX_type1 et_maktx = response.getET_MAKTX();
        ZSSD00008[] zssd00008s = et_maktx.getItem();
        Map<String, String> map = new HashMap<String, String>();
        if (zssd00008s.length > 0)
            for (ZSSD00008 zssd00008 : zssd00008s) {
                map.put(zssd00008.getMATNR().getMATNR_type2(), zssd00008.getMAKTX().getMAKTX_type0());
            }
        return map;
    }

    public Map<String, TMdMara> findAll() {
        Map<String, TMdMara> results = new HashMap<String, TMdMara>();
        List<TMdMara> lists = piProductMapper.findAll();
        for (TMdMara tMdMara : lists) {
            results.put(tMdMara.getMatnr(), tMdMara);
        }
        return results;
    }
}
