package com.heitian.ssm.service.project.impl;

import com.heitian.ssm.dao.project.CheckDao;
import com.heitian.ssm.model.project.CheckModel;
import com.heitian.ssm.service.project.CheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-12 23:20
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class CheckServiceImpl implements CheckService {

    @Resource
    CheckDao checkDao;

    public int insertCheck(CheckModel checkModel){
        return checkDao.insertCheck( checkModel);
    }

    //获取单抽查单项目
    public List<CheckModel> SelectCheckId(CheckModel checkModel){
        return checkDao.SelectCheckId(checkModel);
    }

    //获取项目的抽查单列表
    public List<CheckModel> selectCheck(CheckModel checkModel){
        return  checkDao.selectCheck(checkModel);
    }

    //添加单抽查单文本
    public int UpdateCheckDoc(CheckModel checkModel){
        return checkDao.UpdateCheckDoc(checkModel);
    }


    public  int falsh(List<Map<String,Object>> list){
        return checkDao.falsh(list);
    }

    public  int selectCount(String YEAR,String CBMUNITID){
        return  checkDao.selectCount(YEAR, CBMUNITID);
    }

}
