package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.HomeDao;
import com.heitian.ssm.service.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-23 10:13
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class HomeServiceImpl implements HomeService {

    @Resource
    HomeDao homeDao;

}
