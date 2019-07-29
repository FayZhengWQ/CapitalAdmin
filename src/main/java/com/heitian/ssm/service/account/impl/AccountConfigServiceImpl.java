package com.heitian.ssm.service.account.impl;

import com.heitian.ssm.dao.account.AccountConfigDao;
import com.heitian.ssm.service.account.AccountConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-06-19 00:06
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountConfigServiceImpl implements AccountConfigService {

    @Autowired
    private AccountConfigDao accountConfigDao;


    @Override
    public List<Map<String,Object>> getAccountList(String year,String power,String cbmunitid){
        Map map = new HashMap();
        map.put("year",year);
        map.put("power",power);
        map.put("cbmunitid",cbmunitid);
        List<Map<String,Object>> list= accountConfigDao.getAccountList(map);
        return list;
    }

    @Override
    public int addAccount(String cbmunitid,String name, String power){
        Map map=new HashMap();
        map.put("cbmunitid",cbmunitid);
        map.put("name",name);
        map.put("power",power);
        int count=accountConfigDao.addAccount(map);
        return  count;
    }

    @Override
    public int deleteAccount(String guid){
        Map map=new HashMap();
        map.put("guid",guid);
        int count=accountConfigDao.addAccount(map);
        return  count;
    }

    @Override
    public int updateAccount(String name,String guid){
        Map map=new HashMap();
        map.put("name",name);
        map.put("guid",guid);
        int count=accountConfigDao.addAccount(map);
        return  count;
    }




}
