package com.heitian.ssm.service.project.impl;

import com.heitian.ssm.dao.project.CapitalDao;
import com.heitian.ssm.model.project.CapitalModel;
import com.heitian.ssm.service.project.Capitalservice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-15 23:52
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class CapitalServiceImpl  implements Capitalservice {

    @Resource
    CapitalDao capitalDao;

    public List<CapitalModel> selectCapital(CapitalModel capitalModel){
        return  capitalDao.selectCapital(capitalModel);
    }

}
