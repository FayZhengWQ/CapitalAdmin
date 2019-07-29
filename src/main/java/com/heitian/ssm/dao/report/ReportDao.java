package com.heitian.ssm.dao.report;

import com.heitian.ssm.model.*;
import com.heitian.ssm.model.report.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-10 14:56
 **/

@Repository
public interface ReportDao {


    //生成帐套
    int insertReportId(InfoModel infoModel);
    //查询帐套是否生成
    List<InfoModel> selectAccountId(InfoModel infoModel);


    //查询资产负债表
    List<DebtModel> selectDebt(DebtModel debtModel);
    //导入资金负债表
    int addDebt(List<DebtModel> debtModels);
    //导入财政支出月报表
    int addCapitalMonth(List<CapitalMonthModel> list);
    //
    int addCapitalEMonth(List<CapitalEMonthModel> list);


    //查询财政支出月报表
    List<CapitalMonthModel> selectMonth(CapitalMonthModel capitalMonthModel);
    //查询预算外财政月报表
    List<CapitalEMonthModel> selectEMonth(CapitalEMonthModel capitalEMonthModel);


    //删除信息
    int deleteReportId(@Param("CBMUNITID") String CBMUNITID,
                       @Param("CBMUNITNAME") String CBMUNITNAME,
                       @Param("table") String table,
                       @Param("PNO") String PNO);



    void createTempDept(Map map);

    String isExitBalanceSheet(Map map);

    int deleteBalanceSheet(Map map);


    int addBalanceSheetData(BalanceSheetModel balanceSheetModel);

    int isExistExcute(Map map);

    int deleteExcute(Map map);

    void doExcute(Map map);

    int addExceute(ExceuteModel exceuteModel);

    int updateExceute(Map<String,Object> exceute);

    List<Map<String, Object>> getExceuteList(Map map);

    List<Map<String, Object>> getExceuteList2(Map map);

    int isExit(Map map);

    int deleteBudget(Map map);

    void doPayMonth(Map map);

    int addData(List<BudgetModel> budgetList);

    int updatePayMonth(BudgetModel budget);

    List<Map<String, Object>> getBudget(Map map);

    int isExitEdu(Map map);

    int deleteEdu(Map map);

    void doEdu(Map map);

    int addEdu(EducationModel education);

    List<Map<String, Object>> getEdu(Map map);

    int updateEdu(Map<String,Object> education);

    List<Map<String, Object>> getEdu2(Map map);

    int isExitSpe(Map map);

    int deleteSpe(Map map);

    void doSpe(Map map);

    List<Map<String, Object>> getBudget2(Map map);

    int addSpe(SpecialrModel specialr);

    List<Map<String,Object>> getSpe(Map map);

    List<SpecialrModel> getSpe2(Map map);

    int isExistIncome(Map map);

    int addIncome(List<IncomeModel> incomeList);

    int updateIncome(IncomeModel income);

    int send(Map map);

    int back(Map map);

    List<Map<String, Object>> getIncome(Map map);

    List<Map<String, Object>> getIncome2(Map map);

    int isExistEYear(Map map);

    List<Map<String, Object>> getEYearForm18(Map map);

    int addEyear(List<EarlyYearModel> eYearList);

    List<Map<String, Object>> getEYear(Map map);

    int updateEarlYear(Map<String,Object> earlyYearModel);

    int addEarlyYear(List<BudgetModel> earlyList);

    int updateOutEYear(Map<String, Object> map);

    int updateExcuEYear(Map<String, Object> map);

    int updateOUTEYear(Map map);

    List<Map<String, Object>> getEarlyYear(String fiscal, String co_code);

    int deleteIncome(Map map);

    List<Map<String, Object>> getRelation(Map map);
}
