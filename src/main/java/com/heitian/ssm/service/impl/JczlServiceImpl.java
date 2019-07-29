package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.JczlDao;
import com.heitian.ssm.model.JczlBean;
import com.heitian.ssm.service.JczlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: CapitalAdmin
 * @description: 基础资料库接口
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-08 13:39
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class JczlServiceImpl implements JczlService {

    @Resource
    private JczlDao jczlDao;

    //基础资料
    public List<JczlBean> getAllUnit(String year , String CUNITNAME) {
        return jczlDao.SelcetAllUnit(year ,CUNITNAME);
    }

    //功能科目
    public List<JczlBean> getAllFun(String year, String CFUNCTIONNAME) {
        return jczlDao.SelectAllFun(year,CFUNCTIONNAME);
    }

    //部门经济科目
    public List<JczlBean> getAllEcon(String year, String CECONOMYSECTIONNAME) {
        return jczlDao.SelectAllEcon(year,CECONOMYSECTIONNAME);
    }

    //政府经济科目
    public List<JczlBean> getAllEconGov(String year, String CECONOMYSECTIONGOVNAME) {
        return jczlDao.SelectAllEconGov(year,CECONOMYSECTIONGOVNAME);
    }

    //资金来源
    public List<JczlBean> getAllResoure(String year, String CRESOURCENAME) {
        return jczlDao.SelectAllResoure(year,CRESOURCENAME);
    }

    //指标类型
    public  List<JczlBean> getAllBudget(String year, String BUDGETCATEGORYNAME){
        return jczlDao.SelectAllBudget(year,BUDGETCATEGORYNAME);}

    //项目类型
    public List<JczlBean> getAllOptions(String year, String CPROGRAMTYPENAME){return  jczlDao.SelectAllOptions(year,CPROGRAMTYPENAME);}

    //科室
    public List<JczlBean> getAlldivision(String year, String DGKKS){return jczlDao.SelectAllDivision(year ,DGKKS);}
}
