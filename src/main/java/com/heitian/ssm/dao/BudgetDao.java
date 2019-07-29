package com.heitian.ssm.dao;

import com.heitian.ssm.model.report.BudgetModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BudgetDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/4/39:40
 */
@Repository
public interface BudgetDao {
    String isExit(Map map);

    int deleteBudget(Map map);

    List<BudgetModel> getDataFrom18(Map map);

    int addData(List list);

    List<BudgetModel> getBudget(Map map);

    int addData1(BudgetModel budgetModel);
}
