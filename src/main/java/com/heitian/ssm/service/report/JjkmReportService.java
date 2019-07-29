package com.heitian.ssm.service.report;

import com.heitian.ssm.model.report.JjkmReportModel;

import java.util.List;
import java.util.Map;

/**
 * ClassName:JjkmReportService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/3/19 0019下午 2:23
 */
public interface JjkmReportService {

    List<JjkmReportModel> getDataFrom18(Map map);

    int addData(List<JjkmReportModel> list);

    List<Map<String, Object>> getData(Map<String,Object> map);

    List<Map<String, Object>> getData2(Map<String,Object> map);


    int updateXZMon(List<Map<String,Object>> jjkmList);

    int report(List<Map<String,Object>> jjkmList);

    int clearQCZ(List<Map<String,Object>> jjkmList);

    int qczReport(List<Map<String,Object>> jjkmList);

    int cancleConfirm(List<Map<String,Object>> jjkmList);

    int editJjkm(List<Map<String,Object>> jjkmList);

    int getMaxFis(String co_code, String fiscal);

    String isExitJjkm(String CO_CODE, String fiscal, String fis_perd);

    int deleteJjkm(String fis_perd, String fiscal, String CO_CODE);

    int back(List<Map<String,Object>> jjkmList);

    List<Map<String, Object>> getAllMoney(String fiscal, String fis_perd);

    //查看
    List<Map<String, Object>> getAllMoney2(String fiscal, String fis_perd);
}

