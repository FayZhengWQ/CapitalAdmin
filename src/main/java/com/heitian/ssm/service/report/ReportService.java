package com.heitian.ssm.service.report;

import com.heitian.ssm.model.*;
import com.heitian.ssm.model.report.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-10 14:56
 **/
public interface ReportService {


    //查看帐套是否生成
    List<InfoModel> selectAccountId(InfoModel infoModel);
    //生成帐套
    int insertReportId(InfoModel infoModel);

    //查询资金负债表
    List<DebtModel> selectDebt(DebtModel debtModel);

    //导入资产负债表
    int addDebt(List<DebtModel> list);
    //导入财政支出月报表
    int addCapitalMonth(List<CapitalMonthModel> list);
    //导入预算外支出负债表
    int addCapitalEMonth(List<CapitalEMonthModel> list);


    //查询财政支出月报表
    List<CapitalMonthModel> selectMonth(CapitalMonthModel capitalMonthModel);
    //查询预算外支出负债表
    List<CapitalEMonthModel> selectEMonth(CapitalEMonthModel capitalEMonthModel);





    //判断本地库是否存在资金负债数据
    String isExitBalanceSheet(String co_code, String fiscal, String fis_perd);

    //删除本地库的资金负债数据
    int deleteBalanceSheet(String fis_perd, String fiscal, String co_code);

    //将资金负债数据插入到本地库
    int addBalanceSheetData(BalanceSheetModel balanceSheetModel);

    //[执行表]创建临时表
    void createTempDept(Map map);

    int isExistExcute(String fis_perd, String fiscal, String co_code);

    int deleteExcute(String fis_perd, String fiscal, String co_code);

    void doExcute(Map map);

    int addExceute(ExceuteModel exceuteModel);

    int updateExceute(List<Map<String,Object>> exceuteList);

    List<Map<String, Object>> getExceuteList(String fiscal, String fis_perd, String co_code);

    List<Map<String, Object>> getExceuteList2(String fiscal, String fis_perd, String co_code);

    //删除表信息
    int deleteReportId(String CBMUNITID, String CBMUNITNAME, String table, String PNO);

    //【支出表】判断本地库是否存在预算支出数据
    int isExit(String co_code, String fiscal, String fis_perd);

    //【支出表】调用存储过程
    void doPayMonth(Map map);

    //【支出表】删除本地库的预算支出数据
    int deleteBudget(String fis_perd, String fiscal, String co_code);

    //【支出表】将预算支出数据插入到本地库
    int addData(List<BudgetModel> budgetList);

    //【支出表】编辑支出表
    void updatePayMonth(List<Map<String,Object>> budgetList);

    //【支出表】获取数据
    List<Map<String, Object>> getData(String fiscal, String fis_perd, String co_code);

    //【支出表】导出获取数据【含千分位】
    List<Map<String, Object>> getData2(String fiscal, String fis_perd, String co_code);

    //【教育表】是否存在数据
    int isExitEdu(String fis_perd, String fiscal, String co_code);
    //【教育表】删除本地库数据
    int deleteEdu(String fis_perd, String fiscal, String co_code);
    //【教育表】调用存储过程
    void doEdu(Map map);
    //【教育表】插入数据
    int addEdu(EducationModel education);
    //【教育表】查看时获取数据
    List<Map<String, Object>> getEdu(String fiscal, String fis_perd, String co_code);
    //【教育表】编辑更新
    int updateEdu(List<Map<String,Object>> educationList);
    //【教育表】下载时获取数据
    List<Map<String, Object>> getEdu2(String fiscal, String fis_perd, String co_code);

    int isExitSpe(String fis_perd, String fiscal, String co_code);

    int deleteSpe(String fis_perd, String fiscal, String co_code);

    void doSpe(Map map);

    int addSpe(SpecialrModel specialr);

    List<Map<String,Object>> getSpe(String fis_perd, String fiscal, String co_code);

    List<SpecialrModel> getSpe2(String fiscal, String fis_perd, String co_code);

    int isExistIncome(String fiscal, String fis_perd, String co_code);

    int addIncome(List<IncomeModel> incomeList);

    int updateIncome(List<IncomeModel> incomeList);

    int send(String fiscal, String fis_perd, String co_code);

    int back(String fiscal, String fis_perd, String co_code);

    List<Map<String, Object>> getIncome(String fiscal, String fis_perd, String co_code);

    List<Map<String, Object>> getIncome2(String fiscal, String fis_perd, String co_code);

    int isExistEYear(String fiscal, String co_code);

    List<Map<String, Object>> getEYearForm18(String fiscal, String co_code);

    int addEyear(List<EarlyYearModel> eYearList);

    List<Map<String, Object>> getEYear(String fiscal, String co_code);

    int updateEarlYear(List<Map<String,Object>> earlyYearList);

    int updateExcuEYear(String fiscal, String fis_perd, String co_code);

    int updateOUTEYear(String fiscal, String fis_perd, String co_code);

    boolean importExcel(InputStream in, String fileName, String path, String FISCAL, String FIS_PERD, String CO_CODE);

    int deleteIncome(String fiscal, String fis_perd, String co_code);

    List<Map<String, Object>> getRelation(String co_code, String fiscal);
}
