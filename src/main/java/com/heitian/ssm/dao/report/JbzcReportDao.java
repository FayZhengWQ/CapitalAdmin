package com.heitian.ssm.dao.report;

import com.heitian.ssm.model.report.JbzcReportModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:JbzcReportDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/3/22 0022下午 4:35
 */
@Repository
public interface JbzcReportDao {
    String isExitJbzc(Map map);

    int deleteJbzc(Map map);

    List<JbzcReportModel> getDataFrom18(Map map);

    int addData(List list);

    List<Map<String,Object>> getData(Map map);

    List<Map<String, Object>> getData2(Map map);

    List<Map<String,Object>> getJbzc(Map map);

    int updateXZMon(@Param("MONEY") String MONEY,@Param("FISCAL") String FISCAL,@Param("FIS_PERD") String FIS_PERD,@Param("CO_CODE") String CO_CODE,@Param("GOV_OUTLAY_CODE") String GOV_OUTLAY_CODE);

    int report(@Param("MONEY") String MONEY,@Param("FISCAL") String FISCAL,@Param("FIS_PERD") String FIS_PERD,@Param("CO_CODE") String CO_CODE,@Param("GOV_OUTLAY_CODE") String GOV_OUTLAY_CODE);

    int clearQCZ(Map<String,Object> jbzcReportModel);

    int qczReport(Map<String,Object> jbzcReportModel);

    int updateQCZ(Map<String,Object> jbzcReportModel);

    int cancleConfirm(Map<String,Object> jbzcReportModel);

    int getMaxFis(Map map);

    List<Map<String, Object>> getAllMoney(Map map);

    List<Map<String, Object>> getAllMoney2(Map map);

    List<Map<String, Object>> collect(Map map);
}
