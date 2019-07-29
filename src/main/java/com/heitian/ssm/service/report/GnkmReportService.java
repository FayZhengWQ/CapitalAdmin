package com.heitian.ssm.service.report;

import java.util.List;
import java.util.Map;

public interface GnkmReportService {

    String getMonth(String datasource, Map<String, Object> args);

    String IsSetid(String datasource, Map<String, Object> args);

    List<Map<String, Object>> xzGetList(String datasource, Map<String, Object> args);

    List<Map<String, Object>> getList(String datasource, Map<String, Object> args);

    List<Map<String, Object>> saveList(List<Map<String, Object>> list, Map<String, Object> args);

    void update(List<Map<String,Object>> list, Map<String, Object> args);

    void updateSETID(Integer setId, Map<String, Object> args);

    void clearAMT(String column, String setid, Map<String, Object> args);

    List<Map<String, Object>> getAllMoney(Map<String, Object> map);

    List<Map<String, Object>> xzGetList1(String dataSource1, Map<String, Object> args);

}
