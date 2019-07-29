package com.heitian.ssm.dao.report;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: web-ssm
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-03-31 16:39
 **/
@Repository
public interface ReportYykjhsDao {




    List<Map<String,Object>> getGnkmAllMoney(Map map);


    List<Map<String,Object>> getJjkmAllMoney(Map map);

    List<Map<String,Object>> getJbzcAllMoney(Map map);


}
