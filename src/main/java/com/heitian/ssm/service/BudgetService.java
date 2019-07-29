package com.heitian.ssm.service;

import com.heitian.ssm.model.report.BudgetModel;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BudgetService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/4/39:38
 */
public interface BudgetService {
    String isExit(String co_code, String fiscal, String fis_perd);

    int deleteBudget(String fis_perd, String fiscal, String co_code);

    List<BudgetModel> getDataFrom18(Map map);

    int addData(List list);

    List<BudgetModel> getData(String fiscal, String fis_perd, String co_code);

    int addData1(BudgetModel budgetModel);
}
