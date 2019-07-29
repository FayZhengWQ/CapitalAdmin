package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.dao.report.JjkmReportDao;
import com.heitian.ssm.model.report.JjkmReportModel;
import com.heitian.ssm.service.report.JjkmReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:JjkmReportServiceImpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/3/19 0019下午 2:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JjkmReportServiceImpl implements JjkmReportService {
    @Resource
    JjkmReportDao jjkmReportDao;

    @Override
    public String isExitJjkm(String CO_CODE, String FISCAL, String FIS_PERD) {
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        return jjkmReportDao.isExitJjkm(map);
    }

    @Override
    public int deleteJjkm(String FIS_PERD, String FISCAL, String CO_CODE) {
        Map map = new HashMap();
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("CO_CODE", CO_CODE);
        return jjkmReportDao.deleteJjkm(map);
    }

    @Override
    public List<JjkmReportModel> getDataFrom18(Map map) {
        return jjkmReportDao.getDataFrom18(map);
    }

    @Override
    public int addData(List<JjkmReportModel> list) {
        return jjkmReportDao.addData(list);
    }

    @Override
    public List<Map<String, Object>> getData(Map<String,Object> map) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> jjkmList = null;
        //李晓伟获取数据列表
        if (map.get("CPOSTGUID").equals("04")) {
            jjkmList = jjkmReportDao.getData(map);

        } else if (map.get("CPOSTGUID").equals("01")) {
            //乡镇获取数据列表
            jjkmList = jjkmReportDao.getJjkm(map);
        }
        return jjkmList;
    }

    /*下载获取数据*/
    @Override
    public List<Map<String, Object>> getData2(Map<String,Object> map) {
       /* Map map = new HashMap();
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("CO_CODE", CO_CODE);*/
        return jjkmReportDao.getData2(map);
    }

    @Override
    public int updateXZMon(List<Map<String, Object>> jjkmList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (Map<String, Object> jjkmTreeNode : jjkmList) {
            String MONEY = jjkmTreeNode.get("MONEY") == null ? "" : jjkmTreeNode.get("MONEY").toString();
            String FISCAL = jjkmTreeNode.get("FISCAL").toString();
            String FIS_PERD = jjkmTreeNode.get("FIS_PERD").toString();
            String OUTLAY_CODE = jjkmTreeNode.get("OUTLAY_CODE").toString();
            String CO_CODE = jjkmTreeNode.get("CO_CODE").toString();
            count = jjkmReportDao.updateXZMon(MONEY, FIS_PERD, FISCAL, CO_CODE, OUTLAY_CODE);
        }
        return count;
    }

    @Override
    public int report(List<Map<String, Object>> jjkmList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (Map<String, Object> jjkmTreeNode : jjkmList) {
            String MONEY = jjkmTreeNode.get("MONEY") == null ? "" : jjkmTreeNode.get("MONEY").toString();
            String FISCAL = jjkmTreeNode.get("FISCAL").toString();
            String FIS_PERD = jjkmTreeNode.get("FIS_PERD").toString();
            String OUTLAY_CODE = jjkmTreeNode.get("OUTLAY_CODE").toString();
            String CO_CODE = jjkmTreeNode.get("CO_CODE").toString();
            count = jjkmReportDao.report(MONEY, FIS_PERD, FISCAL, CO_CODE, OUTLAY_CODE);
        }

        return count;
    }

    @Override
    public int clearQCZ(List<Map<String,Object>> jjkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < jjkmList.size(); i++) {
            count = jjkmReportDao.clearQCZ(jjkmList.get(i));
        }
        return count;
    }

    @Override
    public int qczReport(List<Map<String,Object>> jjkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < jjkmList.size(); i++) {
            count = jjkmReportDao.qczReport(jjkmList.get(i));
        }
        return count;
    }

    @Override
    public int cancleConfirm(List<Map<String,Object>> jjkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < jjkmList.size(); i++) {
            count = jjkmReportDao.cancleConfirm(jjkmList.get(i));
        }
        return count;
    }

    @Override
    public int editJjkm(List<Map<String,Object>> jjkmList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < jjkmList.size(); i++) {
            count = jjkmReportDao.updateQCZ(jjkmList.get(i));
        }
        return count;
    }

    @Override
    public int getMaxFis(String CO_CODE, String FISCAL) {
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FISCAL", FISCAL);
        return jjkmReportDao.getMaxFis(map);
    }

    @Override
    public int back(List<Map<String,Object>> jjkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < jjkmList.size(); i++) {
            count = jjkmReportDao.clearQCZ(jjkmList.get(i));
        }
        return count;
    }


    @Override
    public List<Map<String, Object>> getAllMoney(String fiscal, String fis_perd) {
        Map map = new HashMap();
        map.put("FISCAL", fiscal);
        map.put("FIS_PERD", fis_perd);
        List<Map<String, Object>> list = jjkmReportDao.getAllMoney(map);
        return list;

    }

    @Override
    public List<Map<String, Object>> getAllMoney2(String fiscal, String fis_perd) {
        Map map = new HashMap();
        map.put("FISCAL", fiscal);
        map.put("FIS_PERD", fis_perd);
        List<Map<String, Object>> list = jjkmReportDao.getAllMoney2(map);
        return list;
    }
}
