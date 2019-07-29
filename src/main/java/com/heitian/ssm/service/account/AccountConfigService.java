package com.heitian.ssm.service.account;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-06-19 00:05
 **/
public interface AccountConfigService {

    List<Map<String,Object>>  getAccountList(String year, String power, String cbmunitid)  ;

    int addAccount(String cbmunitid, String name, String power);

    int deleteAccount(String guid);

    int updateAccount(String name, String guid);

}
