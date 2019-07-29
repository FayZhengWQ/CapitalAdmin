package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.dao.report.JbzcReportDao;
import com.heitian.ssm.model.report.JbzcReportModel;
import com.heitian.ssm.service.report.JbzcReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:JbzcReportServiceImpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/3/22 0022下午 4:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JbzcReportServiceImpl implements JbzcReportService {
    @Autowired
    private JbzcReportDao jbzcReportDao;

    @Override
    public String isExitJbzc(String co_code,String fiscal, String fis_perd) {
        Map map = new HashMap();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return jbzcReportDao.isExitJbzc(map);
    }

    @Override
    public int deleteJbzc(String fis_perd, String fiscal,String co_code) {
        Map map = new HashMap();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return jbzcReportDao.deleteJbzc(map);
    }

    @Override
    public List<JbzcReportModel> getDataFrom18(Map map) {
        return jbzcReportDao.getDataFrom18(map);
    }

    @Override
    public int addData(List list) {
        return jbzcReportDao.addData(list);
    }

    @Override
    public List<Map<String,Object>> getData(Map<String,Object> map) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String,Object>> jbzcList = null;
        /*Map map = new HashMap();
        map.put("FISCAL",FISCAL);
        map.put("FIS_PERD",FIS_PERD);
        map.put("CO_CODE",CO_CODE);*/
        //李晓伟获取数据列表
        if (map.get("CPOSTGUID").equals("04")){
            jbzcList = jbzcReportDao.getData(map);

        }else if (map.get("CPOSTGUID").equals("01")){
            //乡镇获取数据列表
            jbzcList = jbzcReportDao.getJbzc(map);
        }
        return jbzcList;
    }

    @Override
    public List<Map<String, Object>> getData2(Map<String,Object> map) {
        return jbzcReportDao.getData2(map);
    }

    @Override
    public int updateXZMon(List<Map<String,Object>> jbzcList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0 ;i < jbzcList.size();i++) {
            Map<String,Object> jbzcTreeNode =  jbzcList.get(i);
            String MONEY = jbzcTreeNode.get("MONEY") == null ? "" : jbzcTreeNode.get("MONEY").toString();
            String FISCAL = jbzcTreeNode.get("FISCAL").toString();
            String FIS_PERD = jbzcTreeNode.get("FIS_PERD").toString();
            String GOV_OUTLAY_CODE = jbzcTreeNode.get("GOV_OUTLAY_CODE").toString();
            String CO_CODE = jbzcTreeNode.get("CO_CODE").toString();
            count = jbzcReportDao.updateXZMon(MONEY, FISCAL,FIS_PERD,  CO_CODE, GOV_OUTLAY_CODE);
            System.out.println(i);
        }
        return count;
    }

    @Override
    public int report(List<Map<String,Object>> jbzcList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;

        for (Map<String, Object> jbzcTreeNode : jbzcList) {
            String MONEY = jbzcTreeNode.get("MONEY") == null ? "" : jbzcTreeNode.get("MONEY").toString();
            String FISCAL = jbzcTreeNode.get("FISCAL").toString();
            String FIS_PERD = jbzcTreeNode.get("FIS_PERD").toString();
            String GOV_OUTLAY_CODE = jbzcTreeNode.get("GOV_OUTLAY_CODE").toString();
            String CO_CODE = jbzcTreeNode.get("CO_CODE").toString();
            count = jbzcReportDao.report(MONEY, FISCAL,FIS_PERD, CO_CODE, GOV_OUTLAY_CODE);
        }
        return count;
    }

    @Override
    public int clearQCZ(List<Map<String,Object>> jbzcList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0;i<jbzcList.size();i++) {
            count = jbzcReportDao.clearQCZ(jbzcList.get(i));
        }
        return count;
    }

    @Override
    public int qczReport(List<Map<String,Object>> jbzcList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0;i<jbzcList.size();i++){
            count = jbzcReportDao.qczReport(jbzcList.get(i));
        }
        return count;
    }



    @Override
    public int editJbzc(List<Map<String,Object>> jbzcList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0;i<jbzcList.size();i++){
            count = jbzcReportDao.updateQCZ(jbzcList.get(i));
        }
        return count;
    }

    @Override
    public int cancleConfirm(List<Map<String,Object>> jbzcList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0;i<jbzcList.size();i++) {
            count = jbzcReportDao.cancleConfirm(jbzcList.get(i));
        }
        return count;
    }

    @Override
    public int getMaxFis(String co_code, String fiscal) {
        Map map = new HashMap();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        return jbzcReportDao.getMaxFis(map);
    }

    @Override
    public int back(List<Map<String,Object>> jbzcList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0;i<jbzcList.size();i++) {
            count = jbzcReportDao.clearQCZ(jbzcList.get(i));
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getAllMoney(String FIS_PERD, String FISCAL) {
        Map map = new HashMap();
        map.put("FIS_PERD",FIS_PERD);
        map.put("FISCAL",FISCAL);
        return jbzcReportDao.getAllMoney(map);
    }

    @Override
    public List<Map<String, Object>> getAllMoney2(String FIS_PERD, String FISCAL) {
        Map map = new HashMap();
        map.put("FIS_PERD",FIS_PERD);
        map.put("FISCAL",FISCAL);
        return jbzcReportDao.getAllMoney2(map);
    }

    @Override
    public List<Map<String, Object>> huizong(String tableName) {
        Map map = new HashMap();
        map.put("tableName",tableName);
        return jbzcReportDao.collect(map);
    }

}
