package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.dao.report.GlobalDao;
import com.heitian.ssm.dao.report.GnkmReportDao;
import com.heitian.ssm.model.report.GnkmReportModel;
import com.heitian.ssm.service.report.GnkmReportService2;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:GnkmReportService2
 * Package:com.heitian.ssm.service.report.impl
 * Description:
 * author:@Fay
 * Date:2019/7/814:31
 */
@Service
public class GnkmReportService2impl implements GnkmReportService2 {

    @Autowired
    private GnkmReportDao gnkmReportDao;

    @Autowired
    private GlobalDao globalDao;

    @Override
    public String isExitGnkm(String CO_CODE, String FISCAL, String FIS_PERD) {
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        return gnkmReportDao.isExitGnkm(map);
    }

    @Override
    public int deleteGnkm(String FIS_PERD, String FISCAL, String CO_CODE) {
        Map map = new HashMap();
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("CO_CODE", CO_CODE);
        return gnkmReportDao.deleteGnkm(map);
    }

    @Override
    public void getDataFrom18(Map map) {
        gnkmReportDao.getDataFrom18(map);
    }

    @Override
    public int addData(List list) {
        return gnkmReportDao.addData(list);
    }

    @Override
    public List<Map<String, Object>> getData(Map<String, Object> args) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> gnkmList = null;
        //李晓伟获取数据列表
        if (args.get("CPOSTGUID").equals("04")) {
            gnkmList = gnkmReportDao.getData(args);

        } else if (args.get("CPOSTGUID").equals("01")) {
            //乡镇获取数据列表
            gnkmList = gnkmReportDao.getGnkm(args);
        }
        return gnkmList;
    }

    @Override
    public List<Map<String, Object>> getData2(Map<String, Object> args) {
        return gnkmReportDao.getData2(args);
    }

    @Override
    public int updateXZMon(List<Map<String, Object>> gnkmList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (Map<String, Object> gnkmTreeNode : gnkmList) {
            String MONEY = gnkmTreeNode.get("MONEY") == null ? "" : gnkmTreeNode.get("MONEY").toString();
            String FISCAL = gnkmTreeNode.get("FISCAL").toString();
            String FIS_PERD = gnkmTreeNode.get("FIS_PERD").toString();
            String B_ACC_CODE = gnkmTreeNode.get("B_ACC_CODE").toString();
            String CO_CODE = gnkmTreeNode.get("CO_CODE").toString();
            count = gnkmReportDao.updateXZMon(MONEY, FIS_PERD, FISCAL, CO_CODE, B_ACC_CODE);
        }
        return count;
    }

    @Override
    public int report(List<Map<String, Object>> gnkmList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (Map<String, Object> gnkmTreeNode : gnkmList) {
            String MONEY = gnkmTreeNode.get("MONEY") == null ? "" : gnkmTreeNode.get("MONEY").toString();
            String FISCAL = gnkmTreeNode.get("FISCAL").toString();
            String FIS_PERD = gnkmTreeNode.get("FIS_PERD").toString();
            String B_ACC_CODE = gnkmTreeNode.get("B_ACC_CODE").toString();
            String CO_CODE = gnkmTreeNode.get("CO_CODE").toString();
            count = gnkmReportDao.report(MONEY, FIS_PERD, FISCAL, CO_CODE, B_ACC_CODE);
        }

        return count;
    }

    @Override
    public int clearQCZ(List<Map<String,Object>> gnkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < gnkmList.size(); i++) {
            count = gnkmReportDao.clearQCZ(gnkmList.get(i));
        }
        return count;
    }

    @Override
    public int qczReport(List<GnkmReportModel> gnkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < gnkmList.size(); i++) {
            count = gnkmReportDao.qczReport(gnkmList.get(i));
        }
        return count;
    }

    @Override
    public int editGnkm(List<GnkmReportModel> gnkmList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < gnkmList.size(); i++) {
            count = gnkmReportDao.updateQCZ(gnkmList.get(i));
        }
        return count;
    }

    @Override
    public int cancleConfirm(List<Map<String,Object>> gnkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < gnkmList.size(); i++) {
            count = gnkmReportDao.cancleConfirm(gnkmList.get(i));
        }
        return count;
    }

    @Override
    public int getMaxFis(String CO_CODE, String FISCAL) {
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FISCAL", FISCAL);
        return gnkmReportDao.getMaxFis(map);
    }

  /*  @Override
    public int back(List<GnkmReportModel> gnkmList) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (int i = 0; i < gnkmList.size(); i++) {
            count = gnkmReportDao.clearQCZ(gnkmList.get(i));
        }
        return count;
    }*/

    @Override
    public List<Map<String, Object>> getAllMoney(String fiscal, String fis_perd) {
        Map map = new HashMap();
        map.put("FISCAL", fiscal);
        map.put("FIS_PERD", fis_perd);
        List<Map<String, Object>> list = gnkmReportDao.getAllMoney(map);
        return list;
    }

    @Override
    public List<Map<String, Object>> getAllMoney2(String fiscal, String fis_perd) {
        Map map = new HashMap();
        map.put("FISCAL", fiscal);
        map.put("FIS_PERD", fis_perd);
        List<Map<String, Object>> list = gnkmReportDao.getAllMoney2(map);
        return list;
    }

    @Override
    public void update(List<Map<String,Object>> list, final String STATUS) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String sql = "BEGIN ";
        Integer i = 1;
        StringBuffer stringBuffer = new StringBuffer(sql);
        for (final Map map : list) {
            stringBuffer.append(new SQL() {{
                UPDATE("C_REPORT_GNKM");
                SET(String.format("B_ACC_CODE='%s'", map.get("B_ACC_CODE").toString()));
                if (STATUS.equals("1") && map.get("MONEY") != null) {         //乡镇编辑    STATUS为1
                    SET(String.format("XZ_STAD_AMT='%s'", map.get("MONEY").toString()));
                }
                if (STATUS.equals("3") && map.get("QCZ_STAD_AMT") != null)       //区财政编辑   status为3  QCZ_STAD_AMT
                    SET(String.format("QCZ_STAD_AMT='%s'", map.get("QCZ_STAD_AMT").toString()));
                else if (STATUS.equals("3") && map.get("MONEY") != null)    //上报      status为3
                    SET(String.format("QCZ_STAD_AMT='%s'", map.get("MONEY").toString()));
                WHERE(String.format("FISCAL='%s' AND FIS_PERD='%s' AND CO_CODE='%s' AND B_ACC_CODE='%s'",
                        map.get("FISCAL").toString(),
                        map.get("FIS_PERD").toString(),
                        map.get("CO_CODE").toString(),
                        map.get("B_ACC_CODE").toString()));
            }}.toString() + ";");
            if (i % 100 == 0) {
                stringBuffer.append("END;");
                globalDao.updateObject(stringBuffer.toString());
                stringBuffer = new StringBuffer(sql);
                i = 1;
            } else {
                i++;
            }
        }
        if (i > 1) {
            stringBuffer.append("END;");
            globalDao.updateObject(stringBuffer.toString());
        }
    }

    @Override
    public void updateSetId( Map<String, Object> map) {
        gnkmReportDao.updateSetid(map);
    }
}
