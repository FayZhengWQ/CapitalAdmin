package com.heitian.ssm.service.payment.impl;

import com.heitian.ssm.dao.payment.PayBankDao;
import com.heitian.ssm.model.payment.PayBankModel;
import com.heitian.ssm.service.payment.PayBankService;
import com.heitian.ssm.utils.excel.ImportExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:PayBankServiceImpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/3/14 0014上午 9:15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayBankServiceImpl implements PayBankService {

    @Resource
    PayBankDao payBankDao;

    @Override
    public List<Map> queryPayBankByCondition(String cbmunitid, String c_year,String POWER) {
        Map<String,String> map = new HashMap<>();
        map.put("cbmunitid",cbmunitid);
        map.put("c_year",c_year);
        map.put("POWER",POWER);
        List<Map> payBankList = payBankDao.queryPayBankByCondition(map);
        return payBankList;
    }


    @Override
    public int modifierPayBankByCondition(List<Map<String,Object>> list) {
        int count = 0;
        for (Map<String,Object> map :list) {
            count = payBankDao.modifierPayBankByCondition(map);
        }
        return count;
    }

    @Override
    public int addPayBank(List<PayBankModel> payBanklist) {
        int count = payBankDao.addPayBank(payBanklist);
        return count;
    }

    @Override
    public boolean importExcel(InputStream in, String fileName, String path,String CBMUNITID,String OPERATOR,String C_YEAR,String POWER)  {

        System.out.println("文件导入开始" + in);
        try {
            int i = 1;
            int count = 0;
            List<PayBankModel> payBankModelList = new ArrayList<>();
            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in, fileName, path);
            System.out.print("listob" + listob.toString());
            //去掉excel中的重复数据
            for (int a = 0; a < listob.size(); a++) {
                for (int j = listob.size()-1; j >a; j--) {
                    if (listob.get(a).get(0).toString().equals(listob.get(j).get(0).toString())
                            &&listob.get(a).get(1).toString().equals(listob.get(j).get(1).toString())&&
                            listob.get(a).get(2).toString().equals(listob.get(j).get(2).toString())) {
                        listob.remove(listob.get(j));
                    }
                }
            }

            for (int j = 1; j < listob.size(); j++) {
                String PAYBANKNO = listob.get(j).get(0).toString();
                String ENAME = listob.get(j).get(1).toString();
                String PAYBANKNAME = listob.get(j).get(2).toString();

                if (PAYBANKNO!= ""&&ENAME!=""&&PAYBANKNAME!=""){
                    Map map = new HashMap();
                    map.put("PAYBANKNO",PAYBANKNO);
                    map.put("ENAME",ENAME);
                    map.put("PAYBANKNAME",PAYBANKNAME);
                    map.put("CBMUNITID",CBMUNITID);
                    map.put("POWER",POWER);
                    List<PayBankModel> payBanklist = payBankDao.isExistENAME(map);
                    if (payBanklist.size()==0){
                        PayBankModel payBankModel = new PayBankModel();
                        payBankModel.setPAYBANKNO(listob.get(j).get(0) == null ? "" : listob.get(j).get(0).toString());
                        payBankModel.setENAME(listob.get(j).get(1) == null ? "" : listob.get(j).get(1).toString());
                        payBankModel.setPAYBANKNAME(listob.get(j).get(2) == null ? "" : listob.get(j).get(2).toString());
                        payBankModel.setLINENUM(listob.get(j).get(3) == null ? "" : listob.get(j).get(3).toString());
                        payBankModel.setTAXID(listob.get(j).get(4) == null ? "" : listob.get(j).get(4).toString());
                        payBankModel.setOPERATOR(OPERATOR);
                        payBankModel.setC_YEAR(C_YEAR);
                        payBankModel.setCBMUNITID(CBMUNITID);
                        payBankModel.setPOWER(POWER);
                        payBankModelList.add(payBankModel);
                    }

                }

                if (i % 100 == 0) {
                    count = payBankDao.addPayBank(payBankModelList);
                    i = 1;
                    payBankModelList.clear();
                } else {
                    i++;
                }
            }
            if (i > 1) {
                if (payBankModelList.size()!=0){
                    count = payBankDao.addPayBank(payBankModelList);
                }else{
                    System.out.println("付款单位已全部存在");
                }

            }
            System.out.println("文件导入成功" + count);
            return true;
        } catch (Exception e) {
            System.out.println("文件导入失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int isExistPay(String year, String month, String cbmunitid) {
        Map map = new HashMap();
        map.put("year", year);
        map.put("month", month);
        map.put("cbmunitid", cbmunitid);
        return payBankDao.isExistPay(map);
    }

    @Override
    public int deletePay(String year, String month, String cbmunitid) {
        Map map = new HashMap();
        map.put("year", year);
        map.put("month", month);
        map.put("cbmunitid", cbmunitid);
        return payBankDao.deletePay(map);
    }

    //批量删除PayBank
    @Override
    public int delete(List<Map<String,Object>> payBnakList) {
        return payBankDao.delete(payBnakList);
    }

    @Override
    public int addOnePayBank(List<Map<String,Object>> payBnakList) {
//        String ENAME = payBnakList.get(0).getENAME();
        Map map = new HashMap();
        map.put("PAYBANKNO",payBnakList.get(0).get("PAYBANKNO"));
        map.put("ENAME",payBnakList.get(0).get("ENAME"));
        map.put("PAYBANKNAME",payBnakList.get(0).get("PAYBANKNAME"));
        map.put("CBMUNITID",payBnakList.get(0).get("CBMUNITID"));
        map.put("POWER",payBnakList.get(0).get("POWER"));
        //查询是否存在数据
        List<PayBankModel> payBankModel = payBankDao.isExistENAME(map);
        if (payBankModel.size()!=0){
            return 2;
        }else{
            return payBankDao.addOnePayBank(payBnakList.get(0));
        }

    }
}
