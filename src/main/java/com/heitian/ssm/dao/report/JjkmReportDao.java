package com.heitian.ssm.dao.report;

import com.heitian.ssm.model.report.JjkmReportModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:JjkmReportDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/3/19 0019下午 2:25
 */
@Repository
public interface JjkmReportDao {
    List<JjkmReportModel> getDataFrom18(Map map);

    int addData(List<JjkmReportModel> list);

    List<Map<String,Object>> getData(Map map);

    List<Map<String,Object>> getJjkm(Map map);

    int updateXZMon(@Param("MONEY") String MONEY,@Param("FIS_PERD") String FIS_PERD,@Param("FISCAL") String FISCAL,@Param("CO_CODE") String CO_CODE,@Param("OUTLAY_CODE") String OUTLAY_CODE);

    int report(@Param("MONEY") String MONEY,@Param("FIS_PERD") String FIS_PERD,@Param("FISCAL") String FISCAL,@Param("CO_CODE") String CO_CODE,@Param("OUTLAY_CODE") String OUTLAY_CODE);

    int clearQCZ(Map<String,Object> jjkm);

    int qczReport(Map<String,Object> jjkm);

    int cancleConfirm(Map<String,Object> jjkm);

    int updateQCZ(Map<String,Object> jjkm);

    int getMaxFis(Map map);

    String isExitJjkm(Map map);

    int deleteJjkm(Map map);

    List<Map<String,Object>> getAllMoney(Map map);

    List<Map<String, Object>> getData2(Map map);

    List<Map<String, Object>> getAllMoney2(Map map);


    //gnkm
//    void selectCall(Map<String, Object> map);
}
