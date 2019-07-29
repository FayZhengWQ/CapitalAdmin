package com.heitian.ssm.service.payment.impl;

import com.heitian.ssm.dao.payment.PurposeDao;
import com.heitian.ssm.model.payment.PurposeModel;
import com.heitian.ssm.service.payment.PurposeService;
import com.heitian.ssm.utils.excel.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:PurposeServiceImpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/4/3012:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurposeServiceImpl implements PurposeService {
    @Autowired
    private PurposeDao purposeDao;

    @Override
    public boolean importExcel(InputStream in, String fileName, String path,String CBMUNITID,String C_YEAR,String OPERATOR,String POWER) {

        System.out.println("文件导入开始" + in);
        try {
            int i = 1;
            int count = 0;
            List<PurposeModel> purposeModelList = new ArrayList<>();
            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in, fileName, path);
            System.out.print("listob" + listob.size() + "\n");
            //去掉excel中的重复数据
            for (int a = 0; a < listob.size(); a++) {
                for (int j = listob.size()-1; j >a; j--) {
                    if (a != j && listob.get(a).get(0).toString().equals(listob.get(j).get(0).toString())) {
                        listob.remove(listob.get(j));
                    }
                }
            }


            System.out.println("listob2.size"+listob.size());
            for (int j = 1; j < listob.size(); j++) {
                PurposeModel purposeModel = new PurposeModel();
                String PurposeName = listob.get(j).get(0).toString();
                Map map = new HashMap();
                map.put("PURPOSENAME",listob.get(j).get(0).toString());
                map.put("CBMUNITID",CBMUNITID);
                map.put("POWER",POWER);
                if (PurposeName != ""){
                    List<PurposeModel> purposeModels = purposeDao.isExistPurpose(map);
                    if (purposeModels.size()==0){
                        purposeModel.setPURPOSENAME(listob.get(j).get(0).toString());
                        purposeModel.setCBMUNITID(CBMUNITID);
                        purposeModel.setC_YEAR(C_YEAR);
                        purposeModel.setOPERATOR(OPERATOR);
                        purposeModel.setPOWER(POWER);
                        purposeModelList.add(purposeModel);
                    }

                }

                if (i % 100 == 0) {
                    count = purposeDao.addPurpose(purposeModelList);
                    i = 1;
                    purposeModelList.clear();
                } else {
                    i++;
                }
            }
            if (i > 1) {
                if (purposeModelList.size()!=0){
                    count = purposeDao.addPurpose(purposeModelList);
                }else{
                    System.out.println("用途全部已存在");
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

    //插入单条purpose
    @Override
    public int addOnePurpose(List<Map<String,Object>> purposeList) {
        Map map = new HashMap();
        map.put("PURPOSENAME",purposeList.get(0).get("PURPOSENAME"));
        map.put("CBMUNITID",purposeList.get(0).get("CBMUNITID"));
        map.put("POWER",purposeList.get(0).get("POWER"));

        //根据PURPOSENAME 判断是否存在数据
        List<PurposeModel> purpose = purposeDao.isExistPurpose(map);
        if (purpose.size()!=0){
            return 2;
        }else{
            return purposeDao.addOnePurpose(purposeList.get(0));
        }

    }

    //查询purpose
    @Override
    public List<Map<String, Object>> getList(Map<String, Object> map) {
        return purposeDao.getList(map);
    }

    //删除数据purpose


    @Override
    public int deletePurpose(List<Map<String,Object>> purposeList) {
        return purposeDao.deletePurpose(purposeList);
    }
}
