package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.UplodeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-01-13 13:04
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UplodeServiceImpl {

    @Resource
    UplodeDao uplodeDao;
}
