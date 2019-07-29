package com.heitian.ssm.service.report;

import com.heitian.ssm.model.report.GnkmReportModel;

import java.util.List;
import java.util.Map;

/**
 * ClassName:GnkmReportService2
 * Package:com.heitian.ssm.service.report
 * Description:
 * author:@Fay
 * Date:2019/7/814:28
 */
public interface GnkmReportService2 {
    String isExitGnkm(String co_code, String fiscal, String fis_perd);

    int deleteGnkm(String fis_perd, String fiscal, String co_code);

    void getDataFrom18(Map map);

    int addData(List list);

    List<Map<String, Object>> getData(Map<String, Object> args);

    List<Map<String, Object>> getData2(Map<String, Object> args);

    int updateXZMon(List<Map<String, Object>> gnkmList);

    int report(List<Map<String, Object>> gnkmList);

    int clearQCZ(List<Map<String,Object>> gnkmList);

    int qczReport(List<GnkmReportModel> gnkmList);

    int editGnkm(List<GnkmReportModel> gnkmList);

    int cancleConfirm(List<Map<String,Object>> gnkmList);

    int getMaxFis(String co_code, String fiscal);

//    int back(List<GnkmReportModel> gnkmList);

    List<Map<String, Object>> getAllMoney(String fiscal, String fis_perd);

    List<Map<String, Object>> getAllMoney2(String fiscal, String fis_perd);

    void update(List<Map<String, Object>> list, String status);

    void updateSetId(Map<String, Object> map);

}
