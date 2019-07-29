package com.heitian.ssm.dao.report;

import com.heitian.ssm.model.report.GnkmReportModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:GnkmReportDao
 * Package:com.heitian.ssm.dao.report
 * Description:
 * author:@Fay
 * Date:2019/7/814:34
 */
@Repository
public interface GnkmReportDao {
    String isExitGnkm(Map map);

    int deleteGnkm(Map map);

    void getDataFrom18(Map map);

    int addData(List list);

    List<Map<String, Object>> getData(Map<String, Object> args);

    List<Map<String, Object>> getGnkm(Map<String, Object> args);

    List<Map<String, Object>> getData2(Map<String, Object> args);

    int updateXZMon(@Param("MONEY") String MONEY,@Param("FIS_PERD") String FIS_PERD, @Param("FISCAL")String FISCAL,@Param("CO_CODE") String CO_CODE,@Param("B_ACC_CODE") String B_ACC_CODE);

    int report(@Param("MONEY") String MONEY,@Param("FIS_PERD") String FIS_PERD, @Param("FISCAL")String FISCAL,@Param("CO_CODE") String CO_CODE,@Param("B_ACC_CODE") String B_ACC_CODE);

    int clearQCZ(Map<String,Object> gnkmReportModel);

    int qczReport(GnkmReportModel gnkmReportModel);

    int updateQCZ(GnkmReportModel gnkmReportModel);

    int cancleConfirm(Map<String,Object> gnkmReportModel);

    int getMaxFis(Map map);

    List<Map<String, Object>> getAllMoney(Map map);

    List<Map<String, Object>> getAllMoney2(Map map);

    void updateSetid(Map<String, Object> map);

}
