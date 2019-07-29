package com.heitian.ssm.dao.report;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ApproveDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/4/2615:03
 */
@Repository
public interface ApproveDao {

    String getSetId(Map args);

    void doJJKM(Map map);

    /*int addJJKM(List<Map<String, Object>> dataList);*/

    int addJJKM(Map<String, Object> map);

    int updateColumn(Map args);

    int updateAllMoney(Map<String, Object> map1);

    void doJBZC(Map map);

    int addJBZC(Map<String, Object> map1);

    int updatejbzcAllMoney(Map<String, Object> map1);

    void doGNKM(Map map);

    /*int addGNKM(List<Map<String, Object>> dataList);*/

    int addGNKM(Map<String, Object> map1);

    int updateGNKMAllMoney(Map<String, Object> map1);

    List<Map<String, Object>> getXZJJKM(Map<String, Object> args);

    List<Map<String, Object>> getXZJJKM2(Map<String, Object> args);

    List<Map<String, Object>> getQCZJJKM(Map<String, Object> args);

    List<Map<String, Object>> getQCZJJKM2(Map<String, Object> args);

    List<Map<String, Object>> getXZJBZC(Map<String, Object> args);

    List<Map<String, Object>> getXZJBZC2(Map<String, Object> args);

    List<Map<String, Object>> getQCZJBZC(Map<String, Object> args);

    List<Map<String, Object>> getQCZJBZC2(Map<String, Object> args);

    List<Map<String, Object>> getXZGNKM(Map<String, Object> args);

    List<Map<String, Object>> getXZGNKM2(Map<String, Object> args);

    List<Map<String, Object>> getQCZGNKM(Map<String, Object> args);

    List<Map<String, Object>> getQCZGNKM2(Map<String, Object> args);

    int updateJJKM(Map<String, Object> map);

    int updateJBZC(Map<String, Object> map);

    int updateGNKM(Map<String, Object> map);

    int reportJJKM(Map<String, Object> map);

    int reportJBZC(Map<String, Object> map);

    int reportGNKM(Map<String, Object> map);

    int confirmJJKM(Map<String, Object> map);

    int confirmJBZC(Map<String, Object> map);

    int confirmGNKM(Map<String, Object> map);

    int cancelConfirmJJKM(Map<String, Object> map);

    int cancelConfirmJBZC(Map<String, Object> map);

    int cancelConfirmGNKM(Map<String, Object> map);

    int backJJKM(Map<String, Object> map);

    int backJBZC(Map<String, Object> map);

    int backGNKM(Map<String, Object> map);


}
