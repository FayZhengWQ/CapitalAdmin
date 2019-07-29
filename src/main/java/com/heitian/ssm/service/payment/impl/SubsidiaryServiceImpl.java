package com.heitian.ssm.service.payment.impl;

import com.heitian.ssm.dao.payment.CashDao;
import com.heitian.ssm.dao.payment.SubsidiaryDao;
import com.heitian.ssm.model.payment.SubsidiaryModel;
import com.heitian.ssm.service.payment.SubsidiaryService;
import com.heitian.ssm.utils.excel.ImportExcelUtil;
import org.apache.poi.hpsf.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:SubsidiaryServiceImpl
 * Package:com.heitian.ssm.service.payment.impl
 * Description:
 * author:@Fay
 * Date:2019/5/2810:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubsidiaryServiceImpl implements SubsidiaryService {

    @Autowired
    private SubsidiaryDao subsidiaryDao;
    @Autowired
    private CashDao cashDao;


    @Override
    public int isExistLastBanlance(Map<String, Object> args,String IS_CASH) {
        int count = 0;
        if (IS_CASH.equals("0")) {
            count = subsidiaryDao.isExistLastBanlance(args);
        }else{
            count = cashDao.isExistLastCash(args);
        }
        return count;
    }

    @Override
    public int addOne(List<Map<String, Object>> subsidiaryList, String IS_CASH, Map<String, Object> args) {
        int count = 0;
        if (IS_CASH.equals("0")) {
            //判断当天该账簿是否已经生成数据
            int isExist = subsidiaryDao.isExistSubsidiary(args);
            if (isExist == 0) {
                for (Map<String, Object> subsidiaryModel : subsidiaryList) {
                    count = subsidiaryDao.addOne(subsidiaryModel);
                }
                subsidiaryDao.updateFirstSub(args);
            } else {
                for (Map<String, Object> subsidiaryModel : subsidiaryList) {
                    count = subsidiaryDao.addOne(subsidiaryModel);
                }
            }

        } else {
            int isExist = cashDao.isExistCash(args);
            if (isExist == 0) {
                for (Map<String, Object> cashModel : subsidiaryList) {
                    count = cashDao.addOneCash(cashModel);
                }
                cashDao.upadteOneCash(args);
            } else {
                for (Map<String, Object> cashModel : subsidiaryList) {
                    count = cashDao.addOneCash(cashModel);
                }
            }
        }
        return count;
    }

    @Override
    public int editSubsidiary(List<Map<String, Object>> subsidiaryList, String IS_CASH) {
        int count = 0;
        if (IS_CASH.equals("0")) {
            for (Map<String, Object> subsidiary : subsidiaryList) {
                count = subsidiaryDao.editSubsidiary(subsidiary);
            }
        } else {
            for (Map<String, Object> subsidiary : subsidiaryList) {
                count = cashDao.editCash(subsidiary);
            }
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getList(Map<String, Object> args) {
        String IS_CASH = args.get("IS_CASH").toString();
        if (IS_CASH.equals("0")) {
            return subsidiaryDao.getList(args);
        } else {
            return cashDao.getCashList(args);
        }
    }

    @Override
    public boolean importExcel(InputStream in, String fileName, String path, String CBMUNITNAME, String CBMUNITID, String OPERATOR, String ACCOUNTGUID, String POWER, String IS_CASH) {
        System.out.println("文件导入开始" + in);
        try {
            int i = 1;
            int count = 0;
//            List<Map<String,Object>> subsidiaryModelList = new ArrayList<>();
            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in, fileName, path);
            System.out.print("listob" + listob.size() + "\n");


            for (int j = 1; j < listob.size(); j++) {
                Map<String, Object> map = new HashMap<>();
                map.put("CBMUNITID", CBMUNITID);
                map.put("OPERATOR", OPERATOR);
                map.put("POWER", POWER);
                map.put("YEAR", listob.get(j).get(0) == "" ? "" : listob.get(j).get(0).toString());
                map.put("MONTH", listob.get(j).get(1) == "" ? "" : listob.get(j).get(1).toString());
                map.put("DAY", listob.get(j).get(2) == "" ? "" : listob.get(j).get(2).toString());
                map.put("ACCOUNTGUID", ACCOUNTGUID);
                map.put("CBMUNITNAME", CBMUNITNAME);
                if (IS_CASH.equals("0")) {
                    count = subsidiaryDao.isExistSubsidiary(map);
                    map.put("BANKCODE", listob.get(j).get(3) == "" ? "" : listob.get(j).get(3).toString());
                    map.put("VOUCHERCODE", listob.get(j).get(4) == "" ? "" : listob.get(j).get(4).toString());
                    map.put("PAYBANKNAME", listob.get(j).get(5) == "" ? "" : listob.get(j).get(5).toString());
                    map.put("OPPOCBMUNIT", listob.get(j).get(6) == "" ? "" : listob.get(j).get(6).toString());
                    map.put("REMARKS", listob.get(j).get(7) == "" ? "" : listob.get(j).get(7).toString());
                    map.put("DEBITMONEY", listob.get(j).get(8) == "" ? "" : listob.get(j).get(8).toString());
                    map.put("CREDITMONEY", listob.get(j).get(9) == "" ? "" : listob.get(j).get(9).toString());

                    if (count == 0) {
                        count = subsidiaryDao.addOne(map);
                        subsidiaryDao.updateFirstSub(map);
                    } else {
                        count = subsidiaryDao.addOne(map);
                    }
                } else {
                        /*"年","月","日","记帐凭证编号",
                        "对方单位","摘要","借方金额","贷方金额"*/
                    count = cashDao.isExistCash(map);
                    map.put("VOUCHERCODE", listob.get(j).get(3) == "" ? "" : listob.get(j).get(3).toString());
                    map.put("OPPOCBMUNIT", listob.get(j).get(4) == "" ? "" : listob.get(j).get(4).toString());
                    map.put("REMARKS", listob.get(j).get(5) == "" ? "" : listob.get(j).get(5).toString());
                    map.put("DEBITMONEY", listob.get(j).get(6) == "" ? "" : listob.get(j).get(6).toString());
                    map.put("CREDITMONEY", listob.get(j).get(7) == "" ? "" : listob.get(j).get(7).toString());
                    if (count == 0) {
                        count = cashDao.addOneCash(map);
                        cashDao.upadteOneCash(map);
                    } else {
                        count = cashDao.addOneCash(map);
                    }
                }
            }


            System.out.println("文件导入成功" + count);
            return true;
        } catch (Exception e) {
            System.out.println("文件导入失败");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int deleteSubsidiary(List<Map<String, Object>> subsidiaryList, String IS_CASH) {
        int count = 0;
        if (IS_CASH.equals("0")) {
            count = subsidiaryDao.deleteSubsidiary(subsidiaryList);

        } else {
            count = cashDao.deleteCash(subsidiaryList);
        }

        return count;
    }

    @Override
    public void getSubsidiary(Map<String, Object> args) {
        subsidiaryDao.getSubsidiary(args);
    }

    /* @Override
    public List<Map<String, Object>> getBnakList(Map args) {
        return subsidiaryDao.getBankList(args);
    }

    @Override
    public int addBank(List<Map<String, Object>> mapList) {
        return subsidiaryDao.addBank(mapList.get(0));
    }

    @Override
    public int editBank(List<Map<String, Object>> mapList) {
        int count = 0;
        for (int i = 0; i < mapList.size(); i++) {
            count = subsidiaryDao.editBank(mapList.get(i));
        }
        return count;
    }


    @Override
    public int deleteBank(List<Map<String, Object>> mapList) {
        return subsidiaryDao.deleteBank(mapList);
    }*/
}
