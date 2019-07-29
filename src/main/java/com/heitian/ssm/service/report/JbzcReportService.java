package com.heitian.ssm.service.report;

import com.heitian.ssm.model.report.JbzcReportModel;

import java.util.List;
import java.util.Map;

/**
 * ClassName:JbzcReportService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/3/22 0022下午 4:32
 */
public interface JbzcReportService {

    String isExitJbzc(String co_code, String fiscal, String fis_perd);

    int deleteJbzc(String fis_perd, String fiscal, String co_code);

    List<JbzcReportModel> getDataFrom18(Map map);

    int addData(List list);

    List<Map<String,Object>> getData(Map<String,Object> map);

    int updateXZMon(List<Map<String,Object>> jbzcList);

    int report(List<Map<String,Object>> jbzcList);

    int clearQCZ(List<Map<String,Object>> jbzcList);

    int qczReport(List<Map<String,Object>> jbzcList);

    int editJbzc(List<Map<String,Object>> jbzcList);

    int cancleConfirm(List<Map<String,Object>> jbzcList);

    int getMaxFis(String co_code, String fiscal);

    int back(List<Map<String,Object>> jbzcList);

    List<Map<String, Object>> getAllMoney(String fis_perd, String fiscal);

    List<Map<String, Object>> huizong(String tableName);

    List<Map<String, Object>> getData2(Map<String,Object> map);

    List<Map<String, Object>> getAllMoney2(String fis_perd, String fiscal);
}
