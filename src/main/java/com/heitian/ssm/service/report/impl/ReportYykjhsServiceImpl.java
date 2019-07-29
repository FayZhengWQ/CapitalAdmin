package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.dao.report.ReportYykjhsDao;
import com.heitian.ssm.service.report.ReportYykjhsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: web-ssm
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-03-31 16:41
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportYykjhsServiceImpl implements ReportYykjhsService{


    @Resource
    ReportYykjhsDao reportYykjhsDao;

    @Override
    public List<Map<String,Object>> getGnkmAllMoney(Map map) {

        return reportYykjhsDao.getGnkmAllMoney(map);

    }


    @Override
    public List<Map<String,Object>> getJjkmAllMoney(Map map) {

        return reportYykjhsDao.getJjkmAllMoney(map);

    }


    @Override
    public List<Map<String,Object>> getJbzcAllMoney(Map map) {

        return reportYykjhsDao.getJbzcAllMoney(map);

    }



}
