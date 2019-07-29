package com.heitian.ssm.service.report;

import java.util.List;
import java.util.Map;

/**
 * @program: web-ssm
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-03-31 16:40
 **/
public interface ReportYykjhsService {


    List<Map<String,Object>> getGnkmAllMoney(Map map);

    List<Map<String,Object>> getJjkmAllMoney(Map map);

    List<Map<String,Object>> getJbzcAllMoney(Map map);
}
