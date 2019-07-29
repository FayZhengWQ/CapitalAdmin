package com.heitian.ssm.service.report;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ApproveService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/4/2615:02
 */
public interface ApproveService {

    String getSetId(String dataSource, Map args);

    Map<String, Object> getJjkmData(Map<String, Object> args);

    Map<String, Object> getJbzcData(Map<String, Object> args);

    Map<String, Object> getGnkmData(Map<String, Object> args);

    Map<String, Object> XZEdit(Map<String, Object> args, List<Map<String, Object>> dataList);

    Map<String, Object> QCZEdit(Map<String, Object> args, List<Map<String, Object>> dataList);

    Map<String, Object> report(String tableid, List<Map<String, Object>> dataList);

    Map<String, Object> confirm(String tableid, List<Map<String, Object>> dataList);

    Map<String, Object> cancelConfirm(String tableid, List<Map<String, Object>> dataList);

    Map<String, Object> back(String tableid, List<Map<String, Object>> dataList);

    void doJJKM(Map map);

    int addJJKM(List<Map<String, Object>> dataList);

    int updateColumn(Map args);

    int updateAllMoney(Map<String, Object> map1);

    void doJBZC(Map map);

    /*int addJBZC(List<Map<String, Object>> dataList);*/
    int addJBZC(Map<String, Object> map1);

    int updatejbzcAllMoney(Map<String, Object> map1);

    void doGNKM(Map map);

    /*int addGNKM(List<Map<String, Object>> dataList);*/

    int addGNKM(Map<String, Object> map1);

    int updateGNKMAllMoney(Map<String, Object> map1);



}
