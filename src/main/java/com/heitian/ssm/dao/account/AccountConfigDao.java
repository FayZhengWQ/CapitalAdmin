package com.heitian.ssm.dao.account;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-06-19 00:04
 **/
@Repository
public interface  AccountConfigDao {

    List<Map<String,Object>> getAccountList(Map map);

    int addAccount(Map map);

    int deteleAccount(Map map);

    int updateAccount(Map map);

}
