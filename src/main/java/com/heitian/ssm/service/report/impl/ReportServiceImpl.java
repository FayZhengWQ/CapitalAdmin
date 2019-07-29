package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.dao.report.GlobalDao;
import com.heitian.ssm.dao.report.ReportDao;
import com.heitian.ssm.model.*;
import com.heitian.ssm.model.report.*;
import com.heitian.ssm.service.report.ReportService;
import com.heitian.ssm.utils.excel.ImportExcelUtil;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-10 14:58
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportDao reportDao;
    @Autowired
    private GlobalDao globalDao;


    //生成帐套
    public int insertReportId(InfoModel infoModel){
        return reportDao.insertReportId(infoModel);
    }
    

    //查看帐套是否生成
    public List<InfoModel> selectAccountId(InfoModel infoModel){
        return reportDao.selectAccountId(infoModel);
    }

    public List<DebtModel> selectDebt(DebtModel debtModel){
        return reportDao.selectDebt(debtModel);
    }

    //导入资金负债表
    public int addDebt(List<DebtModel> list){
        return  reportDao.addDebt(list);
    }

    //导入财政支出月报表
    public int addCapitalMonth(List<CapitalMonthModel> list){
        return reportDao.addCapitalMonth(list);
    }

    //导入预算外支出月报表
    public int addCapitalEMonth(List<CapitalEMonthModel> list){
        return reportDao.addCapitalEMonth(list);
    }


    //查询财政支出月报表
    public List<CapitalMonthModel> selectMonth(CapitalMonthModel capitalMonthModel){
        return reportDao.selectMonth(capitalMonthModel);
    }
    //查询月算外支出月报表
    public List<CapitalEMonthModel> selectEMonth(CapitalEMonthModel capitalEMonthModel){
        return reportDao.selectEMonth(capitalEMonthModel);
    }


    //删除表信息
    public int deleteReportId(String CBMUNITID,String CBMUNITNAME,String table,String PNO){
        return reportDao.deleteReportId(CBMUNITID,CBMUNITNAME,table,PNO);
    }



    //用友生成临时表
    @Override
    public void createTempDept(Map map) {
        reportDao.createTempDept(map);
    }

    @Override
    public String isExitBalanceSheet(String co_code, String fiscal, String fis_perd) {
        Map map = new HashMap<>();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return reportDao.isExitBalanceSheet(map);
    }

    @Override
    public int deleteBalanceSheet(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return reportDao.deleteBalanceSheet(map);
    }

    @Override
    public int addBalanceSheetData(BalanceSheetModel balanceSheetModel) {
        return reportDao.addBalanceSheetData(balanceSheetModel);
    }


    @Override
    public int isExistExcute(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.isExistExcute(map);
    }

    //删除本地库数据
    @Override
    public int deleteExcute(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.deleteExcute(map);
    }

    //调用存储过程
    @Override
    public void doExcute(Map map) {
        reportDao.doExcute(map);
    }

    //插入数据到本地库
    @Override
    public int addExceute(ExceuteModel exceuteModel) {
        return reportDao.addExceute(exceuteModel);
    }

    @Override
    public int updateExceute(List<Map<String,Object>> exceuteList) {
        int count = 0;
        for(int i = 0; i<exceuteList.size();i++){
            Map<String,Object> exceute = exceuteList.get(i);
           count = reportDao.updateExceute(exceute);
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getExceuteList(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getExceuteList(map);
    }

    @Override
    public List<Map<String, Object>> getExceuteList2(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getExceuteList2(map);
    }

    //判断本地库是否存在预算支出数据
    @Override
    public int isExit(String co_code, String fiscal, String fis_perd) {
        Map map = new HashMap<>();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return reportDao.isExit(map);
    }

    //删除本地库的预算支出数据
    @Override
    public int deleteBudget(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return reportDao.deleteBudget(map);
    }

    //调存储过程
    @Override
    public void doPayMonth(Map map) {
        reportDao.doPayMonth(map);
    }


    //把数据插入到本地数据库
    @Override
    public int addData(List<BudgetModel> budgetList) {
        return reportDao.addData(budgetList);
    }
/*
    @Override
    public int updatePayMonth(List<Map<String,Object>> budgetList) {
        int count = 0;
        for(int i = 0; i<budgetList.size();i++){
//            BudgetModel budget = budgetList.get(i);
//            count = reportDao.updatePayMonth(budget);G
        }
        return count;
    }*/
    @Override
    public void updatePayMonth(List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String sql = "BEGIN ";
        Integer i = 1;
        StringBuffer stringBuffer = new StringBuffer(sql);
        for (final Map map : list) {
            stringBuffer.append(new SQL() {{
                UPDATE("C_REPORT_OUTGOINGS");
                SET(String.format("MONEY2='%s'", map.get("MONEY2").toString()));
                SET(String.format("MONEY4='%s'", map.get("MONEY4").toString()));
                SET(String.format("PLAN='%s'", map.get("PLAN").toString()));
                WHERE(String.format("FISCAL='%s' AND FIS_PERD='%s' AND CO_CODE='%s' AND B_ACC_CODE='%s'",
                        map.get("FISCAL").toString(),
                        map.get("FIS_PERD").toString(),
                        map.get("CO_CODE").toString(),
                        map.get("B_ACC_CODE").toString()));
            }}.toString() + ";");
            if (i % 100 == 0) {
                stringBuffer.append("END;");
                globalDao.updateObject(stringBuffer.toString());
                stringBuffer = new StringBuffer(sql);
                i = 1;
            } else {
                i++;
            }
        }
        if (i > 1) {
            stringBuffer.append("END;");
            globalDao.updateObject(stringBuffer.toString());
        }
    }


    //获取数据给前端
    @Override
    public List<Map<String, Object>> getData(String fiscal, String fis_perd, String co_code) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> budgetList = null;
        Map map = new HashMap();
        map.put("FISCAL",fiscal);
        map.put("FIS_PERD",fis_perd);
        map.put("CO_CODE",co_code);
        budgetList = reportDao.getBudget(map);

        return budgetList;
    }
    //获取数据给前端（含千分位）
    @Override
    public List<Map<String, Object>> getData2(String fiscal, String fis_perd, String co_code) {
        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> budgetList = null;
        Map map = new HashMap();
        map.put("FISCAL",fiscal);
        map.put("FIS_PERD",fis_perd);
        map.put("CO_CODE",co_code);
        budgetList = reportDao.getBudget2(map);

        return budgetList;
    }

    @Override
    public int isExitEdu(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.isExitEdu(map);
    }

    @Override
    public int deleteEdu(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.deleteEdu(map);
    }

    @Override
    public void doEdu(Map map) {
        reportDao.doEdu(map);
    }

    @Override
    public int addEdu(EducationModel education) {
        return reportDao.addEdu(education);
    }

    @Override
    public int updateEdu(List<Map<String,Object>> educationList) {
        int count = 0;
        for(int i = 0; i<educationList.size();i++){
            Map<String,Object> education = educationList.get(i);
            count = reportDao.updateEdu(education);
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getEdu(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getEdu(map);
    }


    @Override
    public List<Map<String, Object>> getEdu2(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getEdu2(map);
    }

    //[专户表]判断本地库是否存在数据
   @Override
    public int isExitSpe(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.isExitSpe(map);
    }

    @Override
    public int deleteSpe(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.deleteSpe(map);
    }

    @Override
    public void doSpe(Map map) {
        reportDao.doSpe(map);
    }

    @Override
    public int addSpe(SpecialrModel specialr) {
        return reportDao.addSpe(specialr);
    }

    @Override
    public List<Map<String,Object>> getSpe( String fis_perd , String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getSpe(map);
    }

    @Override
    public List<SpecialrModel> getSpe2(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getSpe2(map);
    }

    @Override
    public int isExistIncome(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fis_perd",fis_perd);
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.isExistIncome(map);
    }

    @Override
    public int addIncome(List<IncomeModel> incomeList) {
        return reportDao.addIncome(incomeList);
    }

    @Override
    public int updateIncome(List<IncomeModel> incomeList) {
        int count = 0;
        for (IncomeModel income : incomeList){
            count = reportDao.updateIncome(income);
        }
        return count;
    }

    @Override
    public int send(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportDao.send(map);
    }

    @Override
    public int back(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportDao.back(map);
    }

    @Override
    public List<Map<String, Object>> getIncome(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportDao.getIncome(map);
    }

    @Override
    public List<Map<String, Object>> getIncome2(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportDao.getIncome2(map);
    }

    @Override
    public int isExistEYear(String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.isExistEYear(map);
    }

    @Override
    public List<Map<String, Object>> getEYearForm18(String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getEYearForm18(map);
    }

    @Override
    public int addEyear(List<EarlyYearModel> eYearList) {
        return reportDao.addEyear(eYearList);
    }

    @Override
    public List<Map<String, Object>> getEYear(String fiscal, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("co_code",co_code);
        return reportDao.getEYear(map);
    }

    @Override
    public int updateEarlYear(List<Map<String,Object>> earlyYearList) {
        int count = 0;
        for (Map<String,Object> earlyYearModel:earlyYearList){
            count = reportDao.updateEarlYear(earlyYearModel);
        }
        return count;
    }


    @Override
    public int updateExcuEYear(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportDao.updateExcuEYear(map);
    }

    @Override
    public int updateOUTEYear(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportDao.updateOUTEYear(map);
    }

    @Override
    public boolean importExcel(InputStream in, String fileName,String path,String FISCAL,String FIS_PERD,String CO_CODE) {
        String[] gl_item_code = new String[]{"101", "103", "106", "107", "108", "109", "110", "111", "112", "113", "114", "11401", "11402",
                "115", "11502", "11501", "116", "117", "118", "119", "120",""};
        System.out.println("文件导入开始"+in);
        try{
            int i = 1;
            int count = 0;
            List<IncomeModel> incomeModels = new ArrayList<>();
            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in,fileName,path);
            System.out.print("listob"+listob.size()+"\n");


            int beginNum = 0;
            int endNum = 0;
            for (int k = 0; k < listob.size(); k++) {
                String value = listob.get(k).get(0).toString();
                if (value.equals("一、增值税")) {
                    beginNum = k ;
                    System.out.println("beginnum"+beginNum);
                } else if (value.equals("收入合计")) {
                    endNum = k ;
                    System.out.println("endnum"+endNum);
                }
            }

            for (int j = beginNum;j<=endNum;j++){
                IncomeModel incomeModel = new IncomeModel();
                incomeModel.setGL_ITEM_CODE(gl_item_code[j-beginNum]);
                incomeModel.setGL_ITEM_NAME(listob.get(j).get(0)==null?"":listob.get(j).get(0).toString());
                incomeModel.setMONEY1(listob.get(j).get(1)==null?"":listob.get(j).get(1).toString());
                incomeModel.setMONEY2(listob.get(j).get(2)==null?"":listob.get(j).get(2).toString());
                incomeModel.setMONEY3(listob.get(j).get(3)==null?"":listob.get(j).get(3).toString());
                incomeModel.setPROPORTION1(listob.get(j).get(4)==null?"":listob.get(j).get(4).toString());
                incomeModel.setMONEY4(listob.get(j).get(5)==null?"":listob.get(j).get(5).toString());
                incomeModel.setMONEY5(listob.get(j).get(6)==null?"":listob.get(j).get(6).toString());
                incomeModel.setMONEY6(listob.get(j).get(7)==null?"":listob.get(j).get(7).toString());
                incomeModel.setPROPORTION2(listob.get(j).get(8)==null?"":listob.get(j).get(8).toString());
                incomeModel.setFISCAL(FISCAL);
                incomeModel.setFIS_PERD(FIS_PERD);
                incomeModel.setCO_CODE(CO_CODE);
                incomeModels.add(incomeModel);
                if (i % 100==0){
                    count = reportDao.addIncome(incomeModels);
                    i = 1;
                    incomeModels.clear();
                }else{
                    i++;
                }
            }
            if (i > 1){
                count = reportDao.addIncome(incomeModels);
            }
            System.out.println("文件导入成功"+count);
            return true ;
        }catch (Exception e){
            System.out.println("文件导入失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int deleteIncome(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportDao.deleteIncome(map);
    }

    @Override
    public List<Map<String, Object>> getRelation(String co_code, String fiscal) {
        Map map = new HashMap();
        map.put("co_code",co_code);
        map.put("fiscal",fiscal);
        return reportDao.getRelation(map);
    }
}
