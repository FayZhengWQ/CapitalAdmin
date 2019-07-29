package com.heitian.ssm.service.payment.impl;

import com.heitian.ssm.dao.payment.RecBankDao;
import com.heitian.ssm.model.payment.RecBankModel;
import com.heitian.ssm.service.payment.RecBankService;
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
 * ClassName:RecBAnk
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/3/14 0014上午 9:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RecBankServiceImpl implements RecBankService {

    @Resource
    RecBankDao recBankDao;

    @Override
    public List<Map> queryRecBankByCndition(String cbmunitid, String c_year,String POWER) {
        Map<String,String> map = new HashMap<>();
        map.put("cbmunitid",cbmunitid);
        map.put("c_year",c_year);
        map.put("POWER",POWER);
        List<Map> recBankList = recBankDao.queryRecBankByCndition(map);
        return recBankList;
    }

    @Override
    public int modifierRecBankByCondition(List<Map<String,Object>> list) {
        int count =0;
        for (Map<String,Object> map: list) {
            count = recBankDao.modifierRecBankByCondition(map);
        }
        return count;
    }

    @Override
    public int addRecBank(List<RecBankModel> recBnakList) {
        int count = recBankDao.addRecBank(recBnakList);
        return count;
    }

    @Override
    public boolean importExcel(InputStream in, String fileName, String path,String CBMUNITID,String C_YEAR,String OPERATOR,String POWER) {
        System.out.println("文件导入开始"+in);
        try{
            int i = 1;
            int count = 0;
            List<RecBankModel> recBankModelList = new ArrayList<>();
            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in,fileName,path);
            System.out.print("listob"+listob.size()+"\n");

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

            for (int j = 1;j<listob.size();j++){
                String RECBANKNO = listob.get(j).get(0).toString();
                String RECNAME = listob.get(j).get(1).toString();
                String RECBANKNAME = listob.get(j).get(2).toString();

                if (RECBANKNO!=""&&RECNAME!=""&&RECBANKNAME!=""){
                    Map map = new HashMap();
                    map.put("RECNAME",RECNAME);
                    map.put("RECBANKNO",RECBANKNO);
                    map.put("RECBANKNAME",RECBANKNAME);
                    map.put("CBMUNITID",CBMUNITID);
                    map.put("POWER",POWER);
                    List<RecBankModel> recList = recBankDao.isExistRecName(map);
                    if (recList.size()==0){
                        RecBankModel recBankModel = new RecBankModel();
                        recBankModel.setRECBANKNO(RECBANKNO);
                        recBankModel.setRECNAME(RECNAME);
                        recBankModel.setRECBANKNAME(RECBANKNAME);
                        recBankModel.setLINENUM(listob.get(j).get(3)==null?"":listob.get(j).get(3).toString());
                        recBankModel.setTAXID(listob.get(j).get(4)==null?"":listob.get(j).get(4).toString());
                        recBankModel.setAREAID(listob.get(j).get(5)==null?"":listob.get(j).get(5).toString());
                        recBankModel.setAREANAME(listob.get(j).get(6)==null?"":listob.get(j).get(6).toString());
                        recBankModel.setLOCATION(listob.get(j).get(7)==null?"":listob.get(j).get(7).toString());
                        recBankModel.setPOSTCODE(listob.get(j).get(8)==null?"":listob.get(j).get(8).toString());
                        recBankModel.setPHONE(listob.get(j).get(9)==null?"":listob.get(j).get(9).toString());
                        recBankModel.setFAX(listob.get(j).get(10)==null?"":listob.get(j).get(10).toString());
                        recBankModel.setLINKMAN(listob.get(j).get(11)==null?"":listob.get(j).get(11).toString());
                        recBankModel.setUSERNAME(listob.get(j).get(12)==null?"":listob.get(j).get(12).toString());
                        recBankModel.setCBMUNITID(CBMUNITID);
                        recBankModel.setC_YEAR(C_YEAR);
                        recBankModel.setOPERATOR(OPERATOR);
                        recBankModel.setPOWER(POWER);
                        recBankModelList.add(recBankModel);
                    }

                }
                if (i % 100==0){
                    count = recBankDao.addRecBank(recBankModelList);
                    i = 1;
                    recBankModelList.clear();
                }else{
                    i++;
                }
            }
            if (i > 1){
                if (recBankModelList.size()!=0){
                    count = recBankDao.addRecBank(recBankModelList);
                }else{
                    System.out.println("收款单位名称全部已存在");
                }

            }
            System.out.println("文件导入成功"+count);
            return true ;
        }catch (Exception e){
            System.out.println("文件导入失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int isExistRec(String year, String month, String cbmunitid) {
        Map map= new HashMap();
        map.put("year",year);
        map.put("month",month);
        map.put("cbmunitid",cbmunitid);
        return recBankDao.isExistRec(map);
    }

    @Override
    public int deleteRec(String year, String month, String cbmunitid) {
        Map map= new HashMap();
        map.put("year",year);
        map.put("month",month);
        map.put("cbmunitid",cbmunitid);
        return recBankDao.deleteRec(map);
    }

    //批量删除recbank
    @Override
    public int delete(List<Map<String,Object>> recBnakList) {
        return recBankDao.delete(recBnakList);
    }

    @Override
    public int addOneRecBank(List<Map<String,Object>> recBnakList) {
//        String RECNAME = recBnakList.get(0).getRECNAME();
        Map map = new HashMap();
        map.put("RECNAME",recBnakList.get(0).get("RECNAME"));
        map.put("RECBANKNAME",recBnakList.get(0).get("RECBANKNAME"));
        map.put("RECBANKNO",recBnakList.get(0).get("RECBANKNO"));
        map.put("CBMUNITID",recBnakList.get(0).get("CBMUNITID"));
        map.put("POWER",recBnakList.get(0).get("POWER"));
        //查询是否存在该值得数据
        List<RecBankModel> recbank = recBankDao.isExistRecName(map);
        if (recbank.size()!=0){
            return 2;
        }else{
            return recBankDao.addOneRecBank(recBnakList.get(0));
        }

    }
}
