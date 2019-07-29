package com.heitian.ssm.service;

import com.heitian.ssm.model.JczlBean;

import java.util.List;

/**
 * @program: CapitalAdmin
 * @description: 基础资料库接口
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-08 13:39
 **/
public interface JczlService {


    //单位
    List<JczlBean> getAllUnit(String year, String CUNITNAME);

    //功能科目
    List<JczlBean> getAllFun(String year, String CFUNCTIONNAME);

    //部门经济科目
    List<JczlBean> getAllEcon(String year, String CECONOMYSECTIONNAME);

    //政府经济科目
    List<JczlBean> getAllEconGov(String year, String CECONOMYSECTIONGOVNAME);

    //资金来源
    List<JczlBean> getAllResoure(String year, String CRESOURCENAME);

    //指标类型
    List<JczlBean> getAllBudget(String year, String BUDGETCATEGORYNAME);

    //项目类型
    List<JczlBean> getAllOptions(String year, String CPROGRAMTYPENAME);

    //科室
    List<JczlBean> getAlldivision(String year, String DGKKS);
}
