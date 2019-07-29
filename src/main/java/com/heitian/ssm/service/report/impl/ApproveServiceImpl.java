package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.dao.report.ApproveDao;
import com.heitian.ssm.service.report.ApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ApproveServiceImpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/4/2615:02
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class ApproveServiceImpl implements ApproveService {
    @Autowired
    private ApproveDao approveDao;

    //获取setid
    @Override
    public String getSetId(String dataSource, Map args) {
        DynamicDataSourceHolder.setDataSource(dataSource);
        return approveDao.getSetId(args);
    }


  /*  @Override
    public Map<String, Object> doJJKM(String STATE, Map args) {
        Map<String, Object> result = new HashMap();
        //连接用友数据库，调用存储过程
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("FIS_YEAR", args.get("FISCAL"));
        map.put("FIS_MONTH", args.get("FIS_PERD"));
        map.put("CO_CODE", args.get("CO_CODE"));
        map.put("cur", new ArrayList<>());
        approveDao.doJJKM(map);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        //连接本地数据库将数据插入本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        //第一次直接生成报表，返回数据给前端
        if (STATE.equals("1")) {
            int i = 1;
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (Map<String, Object> map1 : mapList) {
                map1.put("FISCAL", args.get("FISCAL"));
                map1.put("FIS_PERD", args.get("FIS_PERD"));
                map1.put("CO_CODE", args.get("CO_CODE"));
                map1.put("SETID", 1);
                dataList.add(map1);
                if (i % 100 == 0) {
                    count = approveDao.addJJKM(dataList);
                    i = 1;
                    dataList.clear();
                } else {
                    i++;
                }
            }
            if (i > 1) {
                count = approveDao.addJJKM(dataList);
            }
        } else if (STATE.equals("2")) {
            //setid=1 点击同意 接收到state=2 第2+次生成报表
            count = approveDao.updateColumn(args);
            if (count > 0) {
                for (Map<String, Object> map1 : mapList) {
                    map1.put("FISCAL", args.get("FISCAL"));
                    map1.put("FIS_PERD", args.get("FIS_PERD"));
                    map1.put("CO_CODE", args.get("CO_CODE"));
                    map1.put("SETID", 1);
                    if (map1.get("STAD_AMT").toString() != null || map1.get("XZ_STAD_AMT").toString() != null) {
                        count = approveDao.updateAllMoney(map1);
                    }
                }
            }
        }
        if (count > 0) {
            result.put("msg", "报表生成成功");
            result.put("data", mapList);
            result.put("code", true);
        } else {
            result.put("msg", "报表生成失败");
            result.put("code", false);
        }

        return result;
    }
*/

    @Override
    public void doJJKM(Map map) {
        approveDao.doJJKM(map);
    }

    @Override
    public int addJJKM(List<Map<String, Object>> dataList) {
        int count = 0;
        for (Map<String, Object> map:dataList){
            count = approveDao.addJJKM(map);
        }
        return count;
    }

    @Override
    public int updateColumn(Map args) {
        return approveDao.updateColumn(args);
    }

    @Override
    public int updateAllMoney(Map<String, Object> map1) {
        return approveDao.updateAllMoney(map1);
    }

   /* @Override
    public Map<String, Object> doJBZC(String STATE, Map args) {
        Map<String, Object> result = new HashMap();
        //连接用友数据库，调用存储过程
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("FIS_YEAR", args.get("FISCAL"));
        map.put("FIS_MONTH", args.get("FIS_PERD"));
        map.put("CO_CODE", args.get("CO_CODE"));
        map.put("cur", new ArrayList<>());
        approveDao.doJBZC(map);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        //连接本地数据库将数据插入本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        //第一次直接生成报表，返回数据给前端
        if (STATE.equals("1")) {
            int i = 1;
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (Map<String, Object> map1 : mapList) {
                map1.put("FISCAL", args.get("FISCAL"));
                map1.put("FIS_PERD", args.get("FIS_PERD"));
                map1.put("CO_CODE", args.get("CO_CODE"));
                map1.put("SETID", 1);
                dataList.add(map1);
                if (i % 100 == 0) {
                    count = approveDao.addJBZC(dataList);
                    i = 1;
                    dataList.clear();
                } else {
                    i++;
                }
            }
            if (i > 1) {
                count = approveDao.addJBZC(dataList);
            }
        } else if (STATE.equals("2")) {
            //setid=1 点击同意 接收到state=2 第2+次生成报表
            count = approveDao.updateColumn(args);
            if (count > 0) {
                for (Map<String, Object> map1 : mapList) {
                    map1.put("FISCAL", args.get("FISCAL"));
                    map1.put("FIS_PERD", args.get("FIS_PERD"));
                    map1.put("CO_CODE", args.get("CO_CODE"));
                    map1.put("SETID", 1);
                    if (map1.get("STAD_AMT").toString() != null || map1.get("XZ_STAD_AMT").toString() != null) {
                        count = approveDao.updatejbzcAllMoney(map1);
                    }
                }
            }
        }
        if (count > 0) {
            result.put("msg", "报表生成成功");
            result.put("data", mapList);
            result.put("code", true);
        } else {
            result.put("msg", "报表生成失败");
            result.put("code", false);
        }

        return result;
    }
*/

    @Override
    public void doJBZC(Map map) {
        approveDao.doJBZC(map);
    }

    @Override
    public int addJBZC(Map<String, Object> map1) {
        return approveDao.addJBZC(map1);
    }

    @Override
    public int updatejbzcAllMoney(Map<String, Object> map1) {
        return approveDao.updatejbzcAllMoney(map1);
    }

   /* @Override
    public Map<String, Object> doGNKM(String STATE, Map args) {
        Map<String, Object> result = new HashMap();
        //连接用友数据库，调用存储过程
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("FIS_YEAR", args.get("FISCAL"));
        map.put("FIS_MONTH", args.get("FIS_PERD"));
        map.put("CO_CODE", args.get("CO_CODE"));
        map.put("cur", new ArrayList<>());
        approveDao.doGNKM(map);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        //连接本地数据库将数据插入本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        //第一次直接生成报表，返回数据给前端
        if (STATE.equals("1")) {
            int i = 1;
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (Map<String, Object> map1 : mapList) {
                map1.put("FISCAL", args.get("FISCAL"));
                map1.put("FIS_PERD", args.get("FIS_PERD"));
                map1.put("CO_CODE", args.get("CO_CODE"));
                map1.put("SETID", 1);
                dataList.add(map1);
                if (i % 100 == 0) {
                    count = approveDao.addGNKM(dataList);
                    i = 1;
                    dataList.clear();
                } else {
                    i++;
                }
            }
            if (i > 1) {
                count = approveDao.addGNKM(dataList);
            }
        } else if (STATE.equals("2")) {
            //setid=1 点击同意 接收到state=2 第2+次生成报表
            count = approveDao.updateColumn(args);
            if (count > 0) {
                for (Map<String, Object> map1 : mapList) {
                    map1.put("FISCAL", args.get("FISCAL"));
                    map1.put("FIS_PERD", args.get("FIS_PERD"));
                    map1.put("CO_CODE", args.get("CO_CODE"));
                    map1.put("SETID", 1);
                    if (map1.get("STAD_AMT").toString() != null || map1.get("XZ_STAD_AMT").toString() != null) {
                        count = approveDao.updateGNKMAllMoney(map1);
                    }
                }
            }
        }
        if (count > 0) {
            result.put("msg", "报表生成成功");
            result.put("data", mapList);
            result.put("code", true);
        } else {
            result.put("msg", "报表生成失败");
            result.put("code", false);
        }

        return result;
    }*/

    @Override
    public void doGNKM(Map map) {
        approveDao.doGNKM(map);
    }

   /* @Override
    public int addGNKM(List<Map<String, Object>> dataList) {
        return approveDao.addGNKM(dataList);
    }*/

    @Override
    public int addGNKM(Map<String, Object> map1) {
        return approveDao.addGNKM(map1);
    }

    @Override
    public int updateGNKMAllMoney(Map<String, Object> map1) {
        return approveDao.updateGNKMAllMoney(map1);
    }

    @Override
    public Map<String, Object> getJjkmData(Map<String, Object> args) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> jjkmList = null;
        String cpostguid = args.get("cpostguid").toString();
        if (cpostguid.equals("01")) {
            //乡镇查看数据
            if (args.get("STATUS").toString().equals("1")) {
                jjkmList = approveDao.getXZJJKM(args);
            } else if (args.get("STATUS").toString().equals("2")) {
                jjkmList = approveDao.getXZJJKM2(args);
            }
        } else if (cpostguid.equals("04")) {
            //区财政查看数据
            if (args.get("STATUS").toString().equals("1")) {
                jjkmList = approveDao.getQCZJJKM(args);
            } else if (args.get("STATUS").toString().equals("2")) {
                jjkmList = approveDao.getQCZJJKM2(args);
            }
        }
        if (jjkmList.size() == 0) {
            result.put("msg", "未生成报表");
            result.put("code", false);
        } else {
            result.put("msg", "成功");
            result.put("code", true);
            result.put("data", jjkmList);
        }

        return result;
    }

    @Override
    public Map<String, Object> getJbzcData(Map<String, Object> args) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> jbzcList = null;
        String cpostguid = args.get("cpostguid").toString();
        if (cpostguid.equals("01")) {
            //乡镇查看数据
            if (args.get("STATUS").toString().equals("1")) {
                jbzcList = approveDao.getXZJBZC(args);
            } else if (args.get("STATUS").toString().equals("2")) {
                jbzcList = approveDao.getXZJBZC2(args);
            }
        } else if (cpostguid.equals("04")) {
            //区财政查看数据
            if (args.get("STATUS").toString().equals("1")) {
                jbzcList = approveDao.getQCZJBZC(args);
            }else if (args.get("STATUS").toString().equals("2")) {
                jbzcList = approveDao.getQCZJBZC2(args);
            }
        }
        if (jbzcList.size() == 0) {
            result.put("msg", "未生成报表");
            result.put("code", false);
        } else {
            result.put("msg", "成功");
            result.put("code", true);
            result.put("data", jbzcList);
        }

        return result;
    }

    @Override
    public Map<String, Object> getGnkmData(Map<String, Object> args) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> gnkmList = null;
        String cpostguid = args.get("cpostguid").toString();
        if (cpostguid.equals("01")) {
            //乡镇查看数据
            if (args.get("STATUS").toString().equals("1")) {
                gnkmList = approveDao.getXZGNKM(args);
            }else  if (args.get("STATUS").toString().equals("2")) {
                gnkmList = approveDao.getXZGNKM2(args);
            }
        } else if (cpostguid.equals("04")) {
            //区财政查看数据
            if (args.get("STATUS").toString().equals("1")) {
                gnkmList = approveDao.getQCZGNKM(args);
            }else if (args.get("STATUS").toString().equals("2")) {
                    gnkmList = approveDao.getQCZGNKM2(args);
            }
        }
        if (gnkmList.size() == 0) {
            result.put("msg", "未生成报表");
            result.put("code", false);
        } else {
            result.put("msg", "成功");
            result.put("code", true);
            result.put("data", gnkmList);
        }

        return result;
    }

    @Override
    public Map<String, Object> XZEdit(Map<String, Object> args,List<Map<String,Object>> dataList ) {
        Map<String, Object> result = new HashMap<>();
        int count = 0;
        for (int i = 0;i<dataList.size();i++){
            Map<String, Object> map = dataList.get(i);
            map.put("SETID","1");
            if (args.get("TABLEID").toString().equals("1")){
                count = approveDao.updateJJKM(map);
            }else if (args.get("TABLEID").toString().equals("2")){
                count = approveDao.updateJBZC(map);
            }else if (args.get("TABLEID").toString().equals("3")){
                count = approveDao.updateGNKM(map);
            }
        }
        if (count > 0){
            result.put("msg","更新成功");
            result.put("code",true);
        }else{
            result.put("msg","更新失败");
            result.put("code",false);
        }

        return result;
    }

    @Override
    public Map<String, Object> QCZEdit(Map<String, Object> args,List<Map<String,Object>> dataList ) {
        Map<String, Object> result = new HashMap<>();
        int count = 0;
        for (int i = 0;i<dataList.size();i++){
            Map<String, Object> map = dataList.get(i);
            map.put("SETID","3");
            if (args.get("TABLEID").toString().equals("1")){
                count = approveDao.updateJJKM(map);
            }else if (args.get("TABLEID").toString().equals("2")){
                count = approveDao.updateJBZC(map);
            }else if (args.get("TABLEID").toString().equals("3")){
                count = approveDao.updateGNKM(map);
            }
        }
        if (count > 0){
            result.put("msg","更新成功");
            result.put("code",true);
        }else{
            result.put("msg","更新失败");
            result.put("code",false);
        }

        return result;
    }

    //乡镇上报
    @Override
    public Map<String, Object> report(String tableid, List<Map<String, Object>> dataList) {
        Map<String, Object> result = new HashMap();
        int count = 0;
        if(tableid.equals("1")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.reportJJKM(map);
            }
        }else if (tableid.equals("2")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.reportJBZC(map);
            }
        }else if (tableid.equals("3")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.reportGNKM(map);
            }
        }
        if (count>0){
            result.put("msg","更新成功");
            result.put("code",true);
        }else{
            result.put("msg","更新失败");
            result.put("code",false);
        }
        return result;
    }

    @Override
    public Map<String, Object> confirm(String tableid, List<Map<String, Object>> dataList) {
        Map<String, Object> result = new HashMap();
        int count = 0;
        if(tableid.equals("1")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.confirmJJKM(map);
            }
        }else if (tableid.equals("2")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.confirmJBZC(map);
            }
        }else if (tableid.equals("3")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.confirmGNKM(map);
            }
        }
        if (count>0){
            result.put("msg","更新成功");
            result.put("code",true);
        }else{
            result.put("msg","更新失败");
            result.put("code",false);
        }
        return result;
    }

    @Override
    public Map<String, Object> cancelConfirm(String tableid, List<Map<String, Object>> dataList) {
        Map<String, Object> result = new HashMap();
        int count = 0;
        if(tableid.equals("1")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.cancelConfirmJJKM(map);
            }
        }else if (tableid.equals("2")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.cancelConfirmJBZC(map);
            }
        }else if (tableid.equals("3")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.cancelConfirmGNKM(map);
            }
        }
        if (count>0){
            result.put("msg","更新成功");
            result.put("code",true);
        }else{
            result.put("msg","更新失败");
            result.put("code",false);
        }
        return result;
    }

    @Override
    public Map<String, Object> back(String tableid, List<Map<String, Object>> dataList) {
        Map<String, Object> result = new HashMap();
        int count = 0;
        if(tableid.equals("1")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.backJJKM(map);
            }
        }else if (tableid.equals("2")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.backJBZC(map);
            }
        }else if (tableid.equals("3")){
            for (int i =0;i<dataList.size();i++){
                Map<String, Object> map = dataList.get(i);
                count = approveDao.backGNKM(map);
            }
        }
        if (count>0){
            result.put("msg","更新成功");
            result.put("code",true);
        }else{
            result.put("msg","更新失败");
            result.put("code",false);
        }
        return result;
    }
}
