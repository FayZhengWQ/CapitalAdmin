package com.heitian.ssm.service.impl;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.dao.BudgetDao;
import com.heitian.ssm.model.report.BudgetModel;
import com.heitian.ssm.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:BudgetServiceimpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/4/39:39
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BudgetServiceimpl implements BudgetService {
    @Autowired
    private BudgetDao budgetDao;

    @Override
    public String isExit(String co_code, String fiscal, String fis_perd) {
        Map map = new HashMap<>();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return budgetDao.isExit(map);
    }

    @Override
    public int deleteBudget(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return budgetDao.deleteBudget(map);
    }

    @Override
    public List<BudgetModel> getDataFrom18(Map map) {
        return budgetDao.getDataFrom18(map);
    }

    @Override
    public int addData(List list) {
        return budgetDao.addData(list);
    }

    @Override
    public int addData1(BudgetModel budgetModel) {
        return budgetDao.addData1(budgetModel);
    }

    @Override
    public List<BudgetModel> getData(String fiscal, String fis_perd, String co_code) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<BudgetModel> budgetList = null;
        Map map = new HashMap();
        map.put("FISCAL",fiscal);
        map.put("FIS_PERD",fis_perd);
        map.put("CO_CODE",co_code);
        budgetList = budgetDao.getBudget(map);

        return budgetList;
    }
}
