package com.heitian.ssm.utils;

import com.heitian.ssm.model.CapitalEMonthModel;
import com.heitian.ssm.model.CapitalMonthModel;
import com.heitian.ssm.model.DebtModel;
import com.heitian.ssm.model.MapResult;
import com.heitian.ssm.utils.config.*;
import com.heitian.ssm.utils.excel.UplodeExcel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-18 14:31
 **/
public class ReportUtil {


     //财政资产负债表
     public static List<DebtModel> Report1(File localFile, String CBMUNITID, String CBMUNITNAME, String DDATE, String IYEAR, String IMONTH){

         List<DebtModel> list=new ArrayList<>();

         //从临时文件 读取数据
         ConfigParser configParser = null;
         Configuration configuration = null;
         try {
             configParser = ConfigurationParserFactory.getConfigParser(Configuration.ParserType.XML);
             configuration = configParser.getConfig(UplodeExcel.class.getResourceAsStream("/import/debtconfig.xml"));
             MapResult mapResult = (MapResult) FileImportExecutor.importFile(configuration, localFile, localFile.getName());
             List<Map> maps = new ArrayList<>();
             maps = mapResult.getResult();   //读取得数据

             for (Map<String,Object> map:  maps) {
                 DebtModel debtModel = new DebtModel();
                            String C_ACC_NAME, C_START_MONEY, C_END_MONEY, D_ACC_NAME, D_START_MONEY, D_END_MONEY;

                            if ((String) map.get("C_ACC_NAME") != null && ((String) map.get("C_ACC_NAME")).length() != 0) {
                                C_ACC_NAME = ReplaceStr((String) map.get("C_ACC_NAME"));
                            } else {
                                C_ACC_NAME = "";
                            }

                            if (((String) map.get("C_START_MONEY")) != null && ((String) map.get("C_START_MONEY")).length() != 0) {
                                C_START_MONEY = ReplaceStr((String) map.get("C_START_MONEY"));
                            } else {
                                C_START_MONEY = "";
                            }
                            if ((String) map.get("C_END_MONEY") != null && ((String) map.get("C_END_MONEY")).length() != 0) {
                                C_END_MONEY = ReplaceStr((String) map.get("C_END_MONEY"));
                            } else {
                                C_END_MONEY = "";
                            }
                            if ((String) map.get("D_ACC_NAME") != null && ((String) map.get("D_ACC_NAME")).length() != 0) {
                                D_ACC_NAME = ReplaceStr((String) map.get("D_ACC_NAME"));
                            } else {
                                D_ACC_NAME = "";
                            }
                            if ((String) map.get("D_START_MONEY") != null && ((String) map.get("D_START_MONEY")).length() != 0) {
                                D_START_MONEY = ReplaceStr((String) map.get("D_START_MONEY"));
                            } else {
                                D_START_MONEY = "";

                            }
                            if ((String) map.get("D_END_MONEY") != null && ((String) map.get("D_END_MONEY")).length() != 0) {
                                D_END_MONEY = ReplaceStr((String) map.get("D_END_MONEY")) ;
                            } else {
                                D_END_MONEY = "";
                            }

                            debtModel.setCBMUNITID(CBMUNITID);
                            debtModel.setCBMUNITNAME(CBMUNITNAME);
                            debtModel.setC_ACC_NAME(C_ACC_NAME);
                            debtModel.setC_START_MONEY(C_START_MONEY);
                            debtModel.setC_END_MONEY(C_END_MONEY);
                            debtModel.setD_ACC_NAME(D_ACC_NAME);
                            debtModel.setD_START_MONEY(D_START_MONEY);
                            debtModel.setD_END_MONEY(D_END_MONEY);
                            debtModel.setIYEAR(IYEAR);
                            debtModel.setIMONTH(IMONTH);
                            debtModel.setDDATE(DDATE);
                            list.add(debtModel);
             }

         } catch (FileImportException e) {
             e.printStackTrace();
         }




         return list;

     }




      //财政支出月报表

    public static List<CapitalMonthModel> Report2(File localFile, String CBMUNITID, String CBMUNITNAME, String DDATE, String IYEAR, String IMONTH){
        //从临时文件 读取数据
        ConfigParser configParser = null;
        Configuration configuration = null;
        List<CapitalMonthModel> list=new ArrayList<>();
        try {
            configParser = ConfigurationParserFactory.getConfigParser(Configuration.ParserType.XML);
            configuration = configParser.getConfig(UplodeExcel.class.getResourceAsStream("/import/monthconfig.xml"));
            MapResult mapResult = (MapResult) FileImportExecutor.importFile(configuration, localFile, localFile.getName());
            List<Map> maps = new ArrayList<>();
            maps = mapResult.getResult();   //读取得数据

            for (Map<String,Object> map:  maps) {

                String C_ACC_CODE,C_ACC_NAME,PNO,P_EARLY_BUDGET,P_ADJUSTMENT,P_SUBSID,P_SUBTOTAL,
                        P_PAY_MONTH,P_PAY_CUMULATIVE,O_DPAY_MONTH,P_ACCOUNTING,O_DPAY_CUMULATIVE,O_LPAY_MONTH,O_LPAY_CUMULATIVE;

                //编码
                if ((String) map.get("C_ACC_CODE") != null && ((String) map.get("C_ACC_CODE")).length() != 0) {
                    C_ACC_CODE =ReplaceStr((String) map.get("C_ACC_CODE")) ;
                            } else {
                    C_ACC_CODE = "";
                            }
               //支出科目
                if ((String) map.get("C_ACC_NAME") != null && ((String) map.get("C_ACC_NAME")).length() != 0) {
                    C_ACC_NAME = ReplaceStr((String) map.get("C_ACC_NAME"));
                } else {
                    C_ACC_NAME = "";
                }
                //栏号
                if ((String) map.get("PNO") != null && ((String) map.get("PNO")).length() != 0) {
                    PNO = ReplaceStr((String) map.get("PNO"));
                } else {
                    PNO = "";
                }
                //公共财政预算支出|支出预算计划|年初预算
                if ((String) map.get("P_EARLY_BUDGET") != null && ((String) map.get("P_EARLY_BUDGET")).length() != 0) {
                    P_EARLY_BUDGET = ReplaceStr((String) map.get("P_EARLY_BUDGET")).replaceAll(",","");
                } else {
                    P_EARLY_BUDGET = "";
                }
                //公共财政预算支出|支出预算计划|科目调整
                if ((String) map.get("P_ADJUSTMENT") != null && ((String) map.get("P_ADJUSTMENT")).length() != 0) {
                    P_ADJUSTMENT = ReplaceStr((String) map.get("P_ADJUSTMENT")).replaceAll(",","");
                } else {
                    P_ADJUSTMENT = "";
                }
                //公共财政预算支出|支出预算计划|上级补助
                if ((String) map.get("P_SUBSID") != null && ((String) map.get("P_SUBSID")).length() != 0) {
                    P_SUBSID = ReplaceStr((String) map.get("P_SUBSID")).replaceAll(",","");
                } else {
                    P_SUBSID = "";
                }
                //公共财政预算支出||小计
                if ((String) map.get("P_SUBTOTAL") != null && ((String) map.get("P_SUBTOTAL")).length() != 0) {
                    P_SUBTOTAL = ReplaceStr((String) map.get("P_SUBTOTAL")).replaceAll(",","");
                } else {
                    P_SUBTOTAL = "";
                }
                //公共财政预算支出|本月支出|
                if ((String) map.get("P_PAY_MONTH") != null && ((String) map.get("P_PAY_MONTH")).length() != 0) {
                    P_PAY_MONTH = ReplaceStr((String) map.get("P_PAY_MONTH")).replaceAll(",","");
                } else {
                    P_PAY_MONTH = "";
                }
                //-公共财政预算支出|累计支出|
                if ((String) map.get("P_PAY_CUMULATIVE") != null && ((String) map.get("P_PAY_CUMULATIVE")).length() != 0) {
                    P_PAY_CUMULATIVE = ReplaceStr((String) map.get("P_PAY_CUMULATIVE")).replaceAll(",","");
                } else {
                    P_PAY_CUMULATIVE = "";
                }
                //公共财政预算支出|占计划%
                if ((String) map.get("P_ACCOUNTING") != null && ((String) map.get("P_ACCOUNTING")).length() != 0) {
                    P_ACCOUNTING = ReplaceStr((String) map.get("P_ACCOUNTING")).replaceAll(",","");
                } else {
                    P_ACCOUNTING = "";
                }
                //其他预算支出|预算外支出|本月支出
                if ((String) map.get("O_DPAY_MONTH") != null && ((String) map.get("O_DPAY_MONTH")).length() != 0) {
                    O_DPAY_MONTH = ReplaceStr((String) map.get("O_DPAY_MONTH")).replaceAll(",","");
                } else {
                    O_DPAY_MONTH = "";
                }
                //其他预算支出|预算外支出|累计支出
                if ((String) map.get("O_DPAY_CUMULATIVE") != null && ((String) map.get("O_DPAY_CUMULATIVE")).length() != 0) {
                    O_DPAY_CUMULATIVE = ReplaceStr((String) map.get("O_DPAY_CUMULATIVE")).replaceAll(",","");
                } else {
                    O_DPAY_CUMULATIVE = "";
                }
                // 其他预算支出|地方教育附加支出|本月支出
                if ((String) map.get("O_LPAY_MONTH") != null && ((String) map.get("O_LPAY_MONTH")).length() != 0) {
                    O_LPAY_MONTH = ReplaceStr((String) map.get("O_LPAY_MONTH")).replaceAll(",","");
                } else {
                    O_LPAY_MONTH = "";
                }

                //其他预算支出|地方教育附加支出|累计支出
                if ((String) map.get("O_LPAY_CUMULATIVE") != null && ((String) map.get("O_LPAY_CUMULATIVE")).length() != 0) {
                    O_LPAY_CUMULATIVE = ReplaceStr((String) map.get("O_LPAY_CUMULATIVE")).replaceAll(",","");
                } else {
                    O_LPAY_CUMULATIVE = "";
                }

                CapitalMonthModel capitalMonthModel=new CapitalMonthModel();
                capitalMonthModel.setCBMUNITID(CBMUNITID);
                capitalMonthModel.setCBMUNITNAME(CBMUNITNAME);
                capitalMonthModel.setC_ACC_CODE(C_ACC_CODE);
                capitalMonthModel.setC_ACC_NAME(C_ACC_NAME);
                capitalMonthModel.setPNO(PNO);
                capitalMonthModel.setP_EARLY_BUDGET(P_EARLY_BUDGET);
                capitalMonthModel.setP_ADJUSTMENT(P_ADJUSTMENT);
                capitalMonthModel.setP_SUBSID(P_SUBSID);
                capitalMonthModel.setP_SUBTOTAL(P_SUBTOTAL);
                capitalMonthModel.setP_PAY_MONTH(P_PAY_MONTH);
                capitalMonthModel.setP_PAY_CUMULATIVE(P_PAY_CUMULATIVE);
                capitalMonthModel.setP_ACCOUNTING(P_ACCOUNTING);
                capitalMonthModel.setO_DPAY_MONTH(O_DPAY_MONTH);
                capitalMonthModel.setO_DPAY_CUMULATIVE(O_DPAY_CUMULATIVE);
                capitalMonthModel.setO_LPAY_MONTH(O_LPAY_MONTH);
                capitalMonthModel.setO_LPAY_CUMULATIVE(O_LPAY_CUMULATIVE);
                capitalMonthModel.setDDATE(DDATE);
                capitalMonthModel.setIYEAR(IYEAR) ;
                capitalMonthModel.setIMONTH(IMONTH);
                list.add(capitalMonthModel);
            }


        } catch (FileImportException e) {
            e.printStackTrace();
        }




        return list;
    }

     //预算外支出月报表
    public static List<CapitalEMonthModel> Report3(File localFile, String CBMUNITID, String CBMUNITNAME, String DDATE, String IYEAR, String IMONTH){

        //从临时文件 读取数据
        ConfigParser configParser = null;
        Configuration configuration = null;
        List<CapitalEMonthModel> list=new ArrayList<>();
        try {
            configParser = ConfigurationParserFactory.getConfigParser(Configuration.ParserType.XML);
            configuration = configParser.getConfig(UplodeExcel.class.getResourceAsStream("/import/emonthconfig.xml"));
            MapResult mapResult = (MapResult) FileImportExecutor.importFile(configuration, localFile, localFile.getName());
            List<Map> maps = new ArrayList<>();
            maps = mapResult.getResult();   //读取得数据

            for (Map<String,Object> map:  maps) {

                String C_ACC_NAME,C_MONEY ;
                //项目名称
                if ((String) map.get("C_ACC_NAME") != null && ((String) map.get("C_ACC_NAME")).length() != 0) {
                    C_ACC_NAME = ReplaceStr((String) map.get("C_ACC_NAME"));
                } else {
                    C_ACC_NAME = "";
                }
                //金额
                if ((String) map.get("C_MONEY") != null && ((String) map.get("C_MONEY")).length() != 0) {
                    C_MONEY = ReplaceStr((String) map.get("C_MONEY"));
                } else {
                    C_MONEY = "";
                }
                CapitalEMonthModel capitalEMonthModel=new CapitalEMonthModel();
                capitalEMonthModel.setCBMUNITID(CBMUNITID);
                capitalEMonthModel.setCBMUNITNAME(CBMUNITNAME);
                capitalEMonthModel.setC_ACC_NAME(C_ACC_NAME);
                capitalEMonthModel.setC_MONEY(C_MONEY);

                capitalEMonthModel.setDDATE(DDATE);
                capitalEMonthModel.setIYEAR(IYEAR) ;
                capitalEMonthModel.setIMONTH(IMONTH);
                list.add(capitalEMonthModel);
            }


        } catch (FileImportException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static String ReplaceStr(String str){

         String value=str.replaceAll("[\\s\\u00A0]+","").trim();
         return value;
    }

}
