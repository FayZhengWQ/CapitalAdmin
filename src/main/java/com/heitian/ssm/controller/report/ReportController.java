package com.heitian.ssm.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.report.*;
import com.heitian.ssm.service.report.ReportService;
import com.heitian.ssm.utils.Date.MaxDateUtil;
import com.heitian.ssm.utils.FormatHelper;
import com.heitian.ssm.utils.MoneyUtils;
import com.heitian.ssm.utils.excel.Excel;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表 资产负债表 预算支出表 执行表 专户表
 *
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-10 13:39
 **/
@Controller
@CrossOrigin
public class ReportController {


    @Resource
    private ReportService reportService;


    //创建一个Map的封装方法
    public static Map<String, Object> createMap(List<Map<String, Object>> funtList, List<Map<String, Object>> debtList, int i) {
        Map<String, Object> map = new HashMap();
        map.put("FACC_CODE", funtList.get(i).get("FACC_CODE"));
        map.put("FACC_NAME", funtList.get(i).get("FACC_NAME"));
        map.put("FMONEY1", funtList.get(i).get("FMONEY1"));
        map.put("FMONEY2", funtList.get(i).get("FMONEY2"));
        map.put("FMONEY3", funtList.get(i).get("FMONEY3"));
        map.put("DACC_CODE", debtList.get(i).get("DACC_CODE"));
        map.put("DACC_NAME", debtList.get(i).get("DACC_NAME"));
        map.put("DMONEY1", debtList.get(i).get("DMONEY1"));
        map.put("DMONEY2", debtList.get(i).get("DMONEY2"));
        map.put("DMONEY3", debtList.get(i).get("DMONEY3"));
        return map;
    }

    /**
     * 根据年份月份查询资产负债
     * STATUS = 1查询
     * STATUS = 2下载
     * 需要参数FISCAL、FIS_PERD、STATUS、CO_CODE、SHORTNAME
     * co_code=606002：财政帐
     */

    @RequestMapping(value = "/report/debt/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData debtlist(@RequestBody Map<String,Object> PARAM,HttpServletRequest request,HttpServletResponse response) throws Exception {
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        JsonData jsonData = new JsonData();
        String isExitlist = reportService.isExitBalanceSheet(CO_CODE, FISCAL, FIS_PERD);
        System.out.print(isExitlist);

        //删除本地数据库这个月的数据
        int deletecount = reportService.deleteBalanceSheet(FIS_PERD, FISCAL, CO_CODE);
        System.out.print("deletecount" + deletecount);
        //连接到用友数据库
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("cur", new ArrayList<Map<String, Object>>());
        //存储过程生成临时表
        reportService.createTempDept(map);

        //从用友数据库获取数据
        List<Map<String, Object>> balanceSheetList = (List<Map<String, Object>>) map.get("cur");

        if (!balanceSheetList.isEmpty()) {
            //连接到本地数据库
            DynamicDataSourceHolder.setDataSource("dataSource1");
            //把查出的数据插入到数据库
            for (Map<String, Object> balanceSheet : balanceSheetList) {
                if (!balanceSheet.get("ACC_TYPE").equals("6") && !balanceSheet.get("ACC_TYPE").equals("7") && !balanceSheet.get("ACC_TYPE").equals("8")) {
                    BalanceSheetModel balanceSheetModel = new BalanceSheetModel();
                    balanceSheetModel.setACC_CODE(balanceSheet.get("ACC_CODE") == null ? "" : balanceSheet.get("ACC_CODE").toString());
                    balanceSheetModel.setACC_NAME(balanceSheet.get("ACC_NAME").toString());
                    balanceSheetModel.setMONEY1(balanceSheet.get("MONEY1") == null ? "" : balanceSheet.get("MONEY1").toString()); //当月金额
                    balanceSheetModel.setMONEY2(balanceSheet.get("MONEY2") == null ? "" : balanceSheet.get("MONEY2").toString()); //累计金额
                    balanceSheetModel.setMONEY3(balanceSheet.get("MONEY3") == null ? "" : balanceSheet.get("MONEY3").toString()); //期末金额
                    balanceSheetModel.setFISCAL(FISCAL);
                    balanceSheetModel.setFIS_PERD(FIS_PERD);
                    balanceSheetModel.setCO_CODE(CO_CODE);
                    count = reportService.addBalanceSheetData(balanceSheetModel);
                }
            }

            if (count > 0) {
                jsonData = jsonData.success("生成报表成功");
            } else {
                jsonData = jsonData.fail("生成报表失败");
            }
        }

        int num = 0;
        List<Map<String, Object>> funtList = new ArrayList<>();//资产
        List<Map<String, Object>> debtList = new ArrayList<>();//负债
        List<Map<String, Object>> dataList = new ArrayList<>();


        //ACC_CODE第一个数字是1,5是资产部类
        //ACC_CODE第一个数字是2,3,4是负债部类
        for (Map<String, Object> map1 : balanceSheetList) {
            String ACC_TYPE = (String) map1.get("ACC_TYPE");
            if (!ACC_TYPE.equals("7") && !ACC_TYPE.equals("8")) {

                String ACC_CODE = (String) map1.get("ACC_CODE");
                int length = ACC_CODE.length();
                Map map2 = new HashMap();
                if (ACC_TYPE.equals("1") || ACC_TYPE.equals("5")) {
                    if (length == 1) {
                        map2.put("FACC_CODE", true);
                    } else {
                        map2.put("FACC_CODE", map1.get("ACC_CODE"));
                    }
                    if (ACC_TYPE.equals("5")) {
                        if (length == 4 || length == 1) {
                            map2.put("FACC_NAME", map1.get("ACC_NAME"));
                            map2.put("FMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                            map2.put("FMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                            map2.put("FMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                            funtList.add(map2);
                        }
                    } else {
                        //ACC_TYPE=1
                        //把1904插入190401/190402过滤
                        if (ACC_CODE.contains("1904")) {
                            if (length == 4) {
                                map2.put("FACC_NAME", map1.get("ACC_NAME"));
                                map2.put("FMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                                map2.put("FMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                                map2.put("FMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                                funtList.add(map2);
                            }
                        } else {
                            //把ACC_TYPE=1 的其他放进去
                            if (length == 4 || length == 1) {
                                map2.put("FACC_NAME", map1.get("ACC_NAME"));
                            } else if (length == 6) {
                                map2.put("FACC_NAME", " " + map1.get("ACC_NAME"));
                            } else if (length == 8) {
                                map2.put("FACC_NAME", "   " + map1.get("ACC_NAME"));
                            } else if (length == 10) {
                                map2.put("FACC_NAME", "     " + map1.get("ACC_NAME"));
                            }
                            map2.put("FMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                            map2.put("FMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                            map2.put("FMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                            funtList.add(map2);

                        }
                    }
                } else if (ACC_TYPE.equals("2") || ACC_TYPE.equals("3") || ACC_TYPE.equals("4")) {
                    if (length == 1) {
                        map2.put("DACC_CODE", true);
                    } else {
                        map2.put("DACC_CODE", map1.get("ACC_CODE"));
                    }
                    //ACC_TYPE=4
                    if (ACC_TYPE.equals("4")) {
                        if (length == 4 || length == 1) {
                            map2.put("DACC_NAME", map1.get("ACC_NAME"));
                            map2.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                            map2.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                            map2.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                            debtList.add(map2);
                        }
                    } else if (ACC_TYPE.equals("2")) {
                        if (length == 4 || length == 1) {
                            map2.put("DACC_NAME", map1.get("ACC_NAME"));
                        } else if (length == 6) {
                            map2.put("DACC_NAME", " " + map1.get("ACC_NAME"));
                        } else if (length == 8) {
                            map2.put("DACC_NAME", "   " + map1.get("ACC_NAME"));
                        } else if (length == 10) {
                            map2.put("DACC_NAME", "     " + map1.get("ACC_NAME"));
                        }
                        if (ACC_CODE.contains("2902")) {
                            if (length == 4) {
                                map2.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                                map2.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                                map2.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                                debtList.add(map2);
                            }
                        } else {
                            map2.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                            map2.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                            map2.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                            debtList.add(map2);
                        }
                    } else {
                        //ACC_TYPE=3
                        map2.put("DACC_NAME", map1.get("ACC_NAME"));
                        map2.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                        map2.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                        map2.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                        debtList.add(map2);
                    }
                }
            }

        }

        if (funtList.size() > debtList.size()) {
            for (int i = 0; i < debtList.size(); i++) {
                Map<String, Object> map1 = createMap(funtList, debtList, i);
                dataList.add(map1);
                num = debtList.size();
            }
            //把没循环funtlist的放进去
            for (int i = num; i < funtList.size(); i++) {
                Map map3 = new HashMap();
                map3.put("FACC_CODE", funtList.get(i).get("FACC_CODE"));
                map3.put("FACC_NAME", funtList.get(i).get("FACC_NAME"));
                map3.put("FMONEY1", funtList.get(i).get("FMONEY1").toString());
                map3.put("FMONEY2", funtList.get(i).get("FMONEY2").toString());
                map3.put("FMONEY3", funtList.get(i).get("FMONEY3").toString());
                map3.put("DACC_CODE", "");
                map3.put("DACC_NAME", "");
                map3.put("DMONEY1", "");
                map3.put("DMONEY2", "");
                map3.put("DMONEY3", "");
                dataList.add(map3);
            }
        } else {
            for (int i = 0; i < funtList.size(); i++) {
                Map<String, Object> map1 = createMap(funtList, debtList, i);
                dataList.add(map1);
                num = funtList.size();
            }
            //把没循环debtlist的放进去
            for (int i = num; i < debtList.size(); i++) {
                Map map3 = new HashMap();
                map3.put("FACC_CODE", "");
                map3.put("FACC_NAME", "");
                map3.put("FMONEY1", "");
                map3.put("FMONEY2", "");
                map3.put("FMONEY3", "");
                map3.put("DACC_CODE", debtList.get(i).get("DACC_CODE"));
                map3.put("DACC_NAME", debtList.get(i).get("DACC_NAME"));
                map3.put("DMONEY1", debtList.get(i).get("DMONEY1").toString());
                map3.put("DMONEY2", debtList.get(i).get("DMONEY2").toString());
                map3.put("DMONEY3", debtList.get(i).get("DMONEY3").toString());
                dataList.add(map3);
            }
        }
        Map map4 = new HashMap();

        for (Map<String, Object> map1 : balanceSheetList) {
            if (map1.get("ACC_TYPE").equals("7") && map1.get("ACC_CODE") == null) {
                map4.put("FACC_NAME", map1.get("ACC_NAME").toString());
                map4.put("FMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                map4.put("FMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                map4.put("FMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
            } else if (map1.get("ACC_TYPE").equals("8") && map1.get("ACC_CODE") == null) {
                map4.put("DACC_NAME", map1.get("ACC_NAME").toString());
                map4.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                map4.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                map4.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
            }
        }

        Map map5 = new HashMap();
        dataList.add(map5);//导出excel时,在合计前空一行
        dataList.add(map4);

        if (STATUS.equals("1")) {
            if (dataList.size() == 2) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                jsonData = jsonData.success(dataList, "报表生成成功", dataList.size());
            }

        } else {
            if (dataList.size() == 2) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                String sheetName = FISCAL + "  年  " + FIS_PERD + "  月  资  产  负  债  表";
                String fileno = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月资产负债表";
                String[] head = new String[]{"编制单位:" + PARAM.get("SHORTNAME"), "编制单位:" + PARAM.get("SHORTNAME"),
                        "", "", "单位：元角分", "单位：元角分"};
                String[] headnum = new String[]{"1,1,0,1", "1,1,2,3", "1,1,4,5"};
                String[] head0 = new String[]{"资产部类", "资产部类", "资产部类", "负债部类", "负债部类", "负债部类"};
                String[] head1 = new String[]{"科目名称", "年初数", "期末数", "科目名称", "年初数", "期末数",};
                String[] headnum0 = new String[]{"2,2,0,2", "2,2,3,5"};
                //从资产类里面取map值
                String[] detail = new String[]{"FACC_NAME", "FMONEY1", "FMONEY3", "DACC_NAME", "DMONEY1", "DMONEY3"};
                Excel.exportDebt(request, response, dataList, sheetName, head0, headnum0, head1, detail, head, headnum, fileno);
            }
        }
        return jsonData;
    }

    /**
     * 政府账资产负债表导出
     * co_code = 606001
     */

    @RequestMapping(value = "/report/debtgov/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData debtgovlist(@RequestBody Map<String,Object> PARAM,HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        JsonData jsonData = new JsonData();
        String isExitlist = reportService.isExitBalanceSheet(CO_CODE, FISCAL, FIS_PERD);
        System.out.print(isExitlist);

        //删除本地数据库这个月的数据
        int deletecount = reportService.deleteBalanceSheet(FIS_PERD, FISCAL, CO_CODE);
        System.out.print("deletecount" + deletecount);
        //连接到用友数据库
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("cur", new ArrayList<Map<String, Object>>());
        //存储过程生成临时表
        reportService.createTempDept(map);

        //从用友数据库获取数据
        List<Map<String, Object>> balanceSheetList = (List<Map<String, Object>>) map.get("cur");

        if (!balanceSheetList.isEmpty()) {
            //连接到本地数据库
            DynamicDataSourceHolder.setDataSource("dataSource1");
            //把查出的数据插入到数据库
            for (Map<String, Object> balanceSheet : balanceSheetList) {
                if (!balanceSheet.get("ACC_TYPE").equals("6") && !balanceSheet.get("ACC_TYPE").equals("7") && !balanceSheet.get("ACC_TYPE").equals("8")) {
                    BalanceSheetModel balanceSheetModel = new BalanceSheetModel();
                    balanceSheetModel.setACC_CODE(balanceSheet.get("ACC_CODE") == null ? "" : balanceSheet.get("ACC_CODE").toString());
                    balanceSheetModel.setACC_NAME(balanceSheet.get("ACC_NAME") == null ? "" : balanceSheet.get("ACC_NAME").toString());
                    balanceSheetModel.setMONEY1(balanceSheet.get("MONEY1") == null ? "" : balanceSheet.get("MONEY1").toString()); //当月金额
                    balanceSheetModel.setMONEY2(balanceSheet.get("MONEY2") == null ? "" : balanceSheet.get("MONEY2").toString()); //累计金额
                    balanceSheetModel.setMONEY3(balanceSheet.get("MONEY3") == null ? "" : balanceSheet.get("MONEY3").toString()); //期末金额
                    balanceSheetModel.setFISCAL(FISCAL);
                    balanceSheetModel.setFIS_PERD(FIS_PERD);
                    balanceSheetModel.setCO_CODE(CO_CODE);
                    count = reportService.addBalanceSheetData(balanceSheetModel);
                }
            }

            if (count > 0) {
                jsonData = jsonData.success("报表生成成功");
            } else {
                jsonData = jsonData.fail("报表生成失败");
            }
        }

        int num = 0;
        List<Map<String, Object>> funtList = new ArrayList<>();//资产
        List<Map<String, Object>> debtList = new ArrayList<>();//负债
        List<Map<String, Object>> dataList = new ArrayList<>();


        //ACC_CODE第一个数字是1,5是资产部类
        //ACC_CODE第一个数字是2,3,4是负债部类
        for (Map<String, Object> map1 : balanceSheetList) {
            String ACC_TYPE = (String) map1.get("ACC_TYPE");
            if (!ACC_TYPE.equals("7") && !ACC_TYPE.equals("8") && !ACC_TYPE.equals("6")) {
                Map map2 = new HashMap();
                if (ACC_TYPE.equals("1") || ACC_TYPE.equals("5")) {
                    String ACC_CODE = (String) map1.get("ACC_CODE");
                    int length = ACC_CODE.length();
                    if (length == 1) {
                        map2.put("FACC_CODE", true);
                    } else {
                        map2.put("FACC_CODE", map1.get("ACC_CODE"));
                    }
                    if (length == 1 || length == 4) {
                        map2.put("FACC_NAME", map1.get("ACC_NAME"));
                    } else if (length == 6) {
                        map2.put("FACC_NAME", " " + map1.get("ACC_NAME"));
                    } else if (length == 8) {
                        map2.put("FACC_NAME", "   " + map1.get("ACC_NAME"));
                    } else if (length == 10) {
                        map2.put("FACC_NAME", "     " + map1.get("ACC_NAME"));
                    }
                    if (ACC_TYPE.equals("5")) {
                        if (length == 4 || length == 1) {
                            map2.put("FMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                            map2.put("FMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                            map2.put("FMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                            funtList.add(map2);
                        }
                    } else {
                        map2.put("FMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                        map2.put("FMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                        map2.put("FMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                        funtList.add(map2);
                    }
                } else if (ACC_TYPE.equals("2") || ACC_TYPE.equals("3") || ACC_TYPE.equals("4")) {

                    String ACC_CODE1 = (String) map1.get("ACC_CODE");
                    int length = ACC_CODE1.length();
                    if (length == 1 || length == 4) {
                        map2.put("DACC_NAME", map1.get("ACC_NAME"));
                    } else if (length == 6) {
                        map2.put("DACC_NAME", " " + map1.get("ACC_NAME"));
                    } else if (length == 8) {
                        map2.put("DACC_NAME", "   " + map1.get("ACC_NAME"));
                    } else if (length == 10) {
                        map2.put("DACC_NAME", "     " + map1.get("ACC_NAME"));
                    }
                    if (length == 1) {
                        map2.put("DACC_CODE", true);
                    } else {
                        map2.put("DACC_CODE", map1.get("ACC_CODE"));
                    }
                    if (ACC_TYPE.equals("4") || ACC_TYPE.equals("2")) {
                        if (length == 4 || length == 1) {
                            map2.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                            map2.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                            map2.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                            debtList.add(map2);
                        }
                    } else {
                        map2.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                        map2.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                        map2.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
                        debtList.add(map2);
                    }
                }
            }
        }

        if (funtList.size() > debtList.size()) {
            for (int i = 0; i < debtList.size(); i++) {
                Map<String, Object> map1 = createMap(funtList, debtList, i);
                dataList.add(map1);
                num = debtList.size();
            }
            //把没循环funtlist的放进去
            for (int i = num; i < funtList.size(); i++) {
                Map map3 = new HashMap();
                map3.put("FACC_CODE", funtList.get(i).get("FACC_CODE"));
                map3.put("FACC_NAME", funtList.get(i).get("FACC_NAME"));
                map3.put("FMONEY1", funtList.get(i).get("FMONEY1").toString());
                map3.put("FMONEY2", funtList.get(i).get("FMONEY2").toString());
                map3.put("FMONEY3", funtList.get(i).get("FMONEY3").toString());
                dataList.add(map3);
            }
        } else {
            for (int i = 0; i < funtList.size(); i++) {
                Map<String, Object> map1 = createMap(funtList, debtList, i);
                dataList.add(map1);
                num = funtList.size();
            }
            //把没循环debtlist的放进去
            for (int i = num; i < debtList.size(); i++) {
                Map map3 = new HashMap();
                map3.put("DACC_CODE", debtList.get(i).get("DACC_CODE"));
                map3.put("DACC_NAME", debtList.get(i).get("DACC_NAME"));
                map3.put("DMONEY1", debtList.get(i).get("DMONEY1").toString());
                map3.put("DMONEY2", debtList.get(i).get("DMONEY2").toString());
                map3.put("DMONEY3", debtList.get(i).get("DMONEY3").toString());
                dataList.add(map3);
            }
        }
        Map map4 = new HashMap();

        for (Map<String, Object> map1 : balanceSheetList) {
            if (map1.get("ACC_TYPE").equals("7") && map1.get("ACC_CODE") == null) {
                map4.put("FACC_CODE", map1.get("ACC_CODE") == null ? "" : map1.get("ACC_CODE").toString());
                map4.put("FACC_NAME", map1.get("ACC_NAME").toString());
                map4.put("FMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                map4.put("FMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                map4.put("FMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
            } else if (map1.get("ACC_TYPE").equals("8") && map1.get("ACC_CODE") == null) {
                map4.put("DACC_CODE", map1.get("ACC_CODE") == null ? "" : map1.get("ACC_CODE").toString());
                map4.put("DACC_NAME", map1.get("ACC_NAME").toString());
                map4.put("DMONEY1", MoneyUtils.fmtMicrometer(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString()));
                map4.put("DMONEY2", MoneyUtils.fmtMicrometer(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString()));
                map4.put("DMONEY3", MoneyUtils.fmtMicrometer(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString()));
            }
        }

        Map map5 = new HashMap();
        dataList.add(map5);//导出excel时,在合计前空一行
        dataList.add(map4);

        if (STATUS.equals("1")) {
            if (dataList.size() == 0) {
                jsonData = jsonData.fail("未生成报表");
            } else {
                jsonData = jsonData.success(dataList, "成功", dataList.size());
            }

        } else {
            if (dataList.size() == 0) {
                jsonData = jsonData.fail("未生成报表");
            } else {
                String dateStr = MaxDateUtil.getLastDayOfMonth(FISCAL + "-" + FIS_PERD);
                String sheetName = FISCAL + "  年  " + FIS_PERD + "  月  资  产  负  债  表";
                String fileno = FISCAL + "年" + FIS_PERD + "月资产负债表";
                String[] head0 = new String[]{"编制单位:" + PARAM.get("SHORTNAME"), "编制单位:" + PARAM.get("SHORTNAME"), "编制单位:" + PARAM.get("SHORTNAME"),
                        dateStr, dateStr, dateStr, "单位：元(以下至角分)", "单位：元(以下至角分)"};
                String[] head1 = new String[]{"科目", "资产部类", "年初数", "期末数", "科目", "负债部类", "年初数", "期末数",};
                String[] headnum0 = new String[]{"1,1,0,2", "1,1,3,5", "1,1,6,7"};
                //从资产类里面取map值
                String[] detail = new String[]{"FACC_CODE", "FACC_NAME", "FMONEY1", "FMONEY3", "DACC_CODE", "DACC_NAME", "DMONEY1", "DMONEY3"};
                Excel.exportDebtGov(request, response, dataList, sheetName, head0, headnum0, head1, detail, fileno);
            }
        }
        return jsonData;
    }


    /**
     * 执行表
     */
    //判断本地库是都存在数据
    @RequestMapping(value = "/report/exceute/isExist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData isExistExcute(@RequestBody Map<String, Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();

        JsonData jsonData = new JsonData();
        int count = reportService.isExistExcute(FIS_PERD, FISCAL, CO_CODE);
        if (count > 0) {
            jsonData = jsonData.fail("数据已存在");
        } else {
            jsonData = jsonData.success("数据不存在");
        }
        return jsonData;
    }

    //从用友数据库取数；刷新本地数据库再插入数据
    @RequestMapping(value = "/report/exceute/getData", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getExcuteData(@RequestBody Map<String, Object> PARAM) {

        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        JsonData jsonData = new JsonData();

        //判断是否生成年初数报表
        int isExistEYear = reportService.isExistEYear(FISCAL, CO_CODE);
        if (isExistEYear == 0) {
            jsonData = jsonData.fail("未生成年初数报表");
        } else {
            int deleteCount = reportService.deleteExcute(FIS_PERD, FISCAL, CO_CODE);
            System.out.println(deleteCount);
            //连接到用友数据库
            DynamicDataSourceHolder.setDataSource("dataSource18");
            //取数
            Map map = new HashMap();
            map.put("CO_CODE", CO_CODE);
            map.put("FIS_MONTH", FIS_PERD);
            map.put("FIS_YEAR", FISCAL);
            map.put("cur", new ArrayList<Map<String, Object>>());

            //调用存储过程
            reportService.doExcute(map);

            List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");

            //插入本地数据库
            DynamicDataSourceHolder.setDataSource("dataSource1");
            int count = 0;
            for (Map<String, Object> map1 : mapList) {
                if (!map1.get("B_ACC_NAME").equals("总计")) {
                    ExceuteModel exceuteModel = new ExceuteModel();
                    exceuteModel.setB_ACC_CODE(map1.get("B_ACC_CODE").toString());
                    exceuteModel.setB_ACC_NAME(map1.get("B_ACC_NAME").toString());
                    exceuteModel.setCO_CODE(CO_CODE);
                    exceuteModel.setFIS_PERD(FIS_PERD);
                    exceuteModel.setFISCAL(FISCAL);
                    exceuteModel.setMONEY3(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString());
                    exceuteModel.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
//                    exceuteModel.setMONEY5(map1.get("MONEY5") == null ? "" : map1.get("MONEY5").toString());
                    exceuteModel.setMONEY6(map1.get("MONEY6") == null ? "" : map1.get("MONEY6").toString());
//                    exceuteModel.setMONEY7(map1.get("MONEY7") == null ? "" : map1.get("MONEY7").toString());
                    exceuteModel.setMONEY8(map1.get("MONEY8") == null ? "" : map1.get("MONEY8").toString());
                    exceuteModel.setPLAN(map1.get("PLAN") == null ? "" : map1.get("PLAN").toString());
//                    exceuteModel.setINDECREASE1(map1.get("INDECREASE1") == null ? "" : map1.get("INDECREASE1").toString());
                    exceuteModel.setINDECREASE2(map1.get("INDECREASE2") == null ? "" : map1.get("INDECREASE2").toString());
                    count = reportService.addExceute(exceuteModel);
                }
            }
            if (count > 0) {
                count = reportService.updateExcuEYear(FISCAL, FIS_PERD, CO_CODE);
                if (count > 0) {
                    List<Map<String, Object>> dataList = reportService.getExceuteList(FISCAL, FIS_PERD, CO_CODE);
                    jsonData = jsonData.success(dataList, "报表生成成功", dataList.size());
                }
            } else {
                jsonData = jsonData.fail("报表生成失败");
            }
        }
        return jsonData;
    }

    //编辑执行表
    @RequestMapping(value = "/report/exceute/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonData editExcute(@RequestBody List<Map<String,Object>> exceuteList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = reportService.updateExceute(exceuteList);
        if (count > 0) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }


    @RequestMapping(value = "/report/exceute/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getExceuteList(@RequestBody Map<String, Object> PARAM, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();

        Integer PREFISCAL = Integer.valueOf(FISCAL) - 1;

        JsonData jsonData = new JsonData();
        //更新年初数MONEY1
        int count = reportService.updateExcuEYear(FISCAL, FIS_PERD, CO_CODE);

        if (count > 0) {

            if (STATUS.equals("1")) {
                List<Map<String, Object>> datalist = reportService.getExceuteList(FISCAL, FIS_PERD, CO_CODE);
                if (datalist.size() == 0) {
                    jsonData = jsonData.fail("未生成报表");
                } else {
                    jsonData = jsonData.success(datalist, "成功", datalist.size());
                }

            } else {
                //下载
                List<Map<String, Object>> datalist2 = reportService.getExceuteList2(FISCAL, FIS_PERD, CO_CODE);
                if (datalist2.size() == 0) {
                    jsonData = jsonData.fail("未生成该报表");
                } else {

                    String SHORTNAME = PARAM.get("SHORTNAME").toString();
                    Map map1 = new HashMap();
                    datalist2.add(map1);
                    Map map = new HashMap();
                    map.put("B_ACC_NAME", "财务负责人:" + PARAM.get("FINANCELEADER"));
                    map.put("MONEY5", "制表人:" + PARAM.get("NICKNAME"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                    Date date = new Date(System.currentTimeMillis());
                    map.put("MONEY8", "制表时间：" + dateFormat.format(date));
                    datalist2.add(map);

                    String sheetName = SHORTNAME + FISCAL + "年" + FIS_PERD + "月预算执行表";
                    String fileno = SHORTNAME + FISCAL + "年" + FIS_PERD + "月预算执行表";
                    String head = "单位：元(保留至角分)";
                    /*String[] headnum = new String[]{"1,1,0,8", "1,1,9,10"};*/
                    String[] head0 = new String[]{"编码", "支出科目", "栏号", FISCAL + "年" + FIS_PERD + "月公共财政预算支出", FISCAL + "年" + FIS_PERD + "月公共财政预算支出", FISCAL + "年" + FIS_PERD + "月公共财政预算支出", FISCAL + "年" + FIS_PERD + "月公共财政预算支出"
                            , FISCAL + "年" + FIS_PERD + "月公共财政预算支出", FISCAL + "年" + FIS_PERD + "月公共财政预算支出", PREFISCAL + "年支出", "同比增减（%）"};
                    String[] headnum0 = new String[]{"2,4,0,0", "2,4,1,1", "2,4,2,2", "2,2,3,8"};
                    String[] head1 = new String[]{"", "", "", "支出预算计划", "支出预算计划", "支出预算计划", "支出预算计划", "累计支出", "占计划(%)", "累计支出", "累计增减"};
                    String[] headnum1 = new String[]{"3,3,3,6", "3,4,7,7", "3,4,8,8", "3,4,9,9", "3,4,10,10"};
                    String[] head2 = new String[]{"年初预算", "科目调整", "上级补助", "小计"};
                    String[] detail = new String[]{"B_ACC_CODE", "B_ACC_NAME", "ID", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY6", "PLAN", "MONEY8", "INDECREASE2"};
                    Excel.exportExecution(request, response, datalist2, sheetName, head, head0, headnum0, head1, headnum1, head2, detail, fileno);
                }
            }
        }else{
            jsonData = jsonData.fail("未生成该报表");
        }
        return jsonData;
    }

    /**
     * 支出表
     */
    //判断本地库是都存在数据
    @RequestMapping(value = "/report/outgoings/isExist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData isExistPay(@RequestBody Map<String, Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();

        JsonData jsonData = new JsonData();
        int count = reportService.isExit(CO_CODE, FISCAL, FIS_PERD);
        if (count > 0) {
            jsonData = jsonData.fail("数据已存在");
        } else {
            jsonData = jsonData.success("数据不存在");
        }
        return jsonData;
    }

    //判断年初数是否生成，未生成，提示去生成年初数
    //生成，将年初数插进去
    // 从用友数据库取数；刷新本地数据库再插入数据
    @RequestMapping(value = "/report/outgoings/getData", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getPayData(@RequestBody Map<String, Object> PARAM) {

        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");

        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();

        JsonData jsonData = new JsonData();

        //判断是否生成年初数
        int isExistEYear = reportService.isExistEYear(FISCAL, CO_CODE);
        if (isExistEYear == 0) {
            jsonData = jsonData.fail("未生成年初数报表");
        } else {

            int deleteCount = reportService.deleteBudget(FIS_PERD, FISCAL, CO_CODE);
            System.out.println(deleteCount);

            //连接到用友数据库
            DynamicDataSourceHolder.setDataSource("dataSource18");
            //取数
            Map map = new HashMap();
            map.put("CO_CODE", CO_CODE);
            map.put("FIS_PERD", FIS_PERD);
            map.put("FISCAL", FISCAL);
            map.put("cur", new ArrayList<Map<String, Object>>());

            //调用存储过程
            reportService.doPayMonth(map);

            List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");

            //插入本地数据库
            DynamicDataSourceHolder.setDataSource("dataSource1");

            int i = 1;
            int count = 0;
            List<BudgetModel> bugetList = new ArrayList<>();
            for (Map<String, Object> map1 : mapList) {
                BudgetModel budgetModel = new BudgetModel();
                if (!map1.get("B_ACC_NAME").equals("合计数")) {
                    budgetModel.setB_ACC_CODE(map1.get("B_ACC_CODE") == null ? " " : map1.get("B_ACC_CODE").toString());
                    budgetModel.setB_ACC_NAME(map1.get("B_ACC_NAME").toString());
                    budgetModel.setCO_CODE(CO_CODE);
                    budgetModel.setFIS_PERD(FIS_PERD);
                    budgetModel.setFISCAL(FISCAL);
                    budgetModel.setMONEY3(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString());
                    budgetModel.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
                    budgetModel.setMONEY5(map1.get("MONEY5") == null ? "" : map1.get("MONEY5").toString());
                    budgetModel.setMONEY6(map1.get("MONEY6") == null ? "" : map1.get("MONEY6").toString());
                    budgetModel.setMONEY7(map1.get("MONEY7") == null ? "" : map1.get("MONEY7").toString());
                    budgetModel.setMONEY8(map1.get("MONEY8") == null ? "" : map1.get("MONEY8").toString());
                    budgetModel.setIS_LOWEST(map1.get("IS_LOWEST") == null ? "" : map1.get("IS_LOWEST").toString());
                    bugetList.add(budgetModel);
                }
                if (i % 100 == 0) {
                    count = reportService.addData(bugetList);
                    bugetList.clear();
                    i = 1;
                } else {
                    i++;
                }
            }
            if (i > 1) {
                count = reportService.addData(bugetList);
            }

            if (count > 0) {
                count = reportService.updateOUTEYear(FISCAL, FIS_PERD, CO_CODE);
                if (count > 0) {
                    List<Map<String, Object>> datalist = reportService.getData(FISCAL, FIS_PERD, CO_CODE);
                    List<BudgetTreeNode> budgetTreeNodes = FormatHelper.OutgoingListToTreeByIsbnCode(datalist);
                    jsonData = jsonData.success(budgetTreeNodes, "生成报表成功", datalist.size());
                }

            } else {
                jsonData = jsonData.fail("生成报表失败");
            }
        }


        return jsonData;
    }

    //编辑支出表
    @RequestMapping(value = "/report/outgoings/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonData editPayMonth(@RequestBody List<Map<String, Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String, Object>> budgetList = FormatHelper.findChildrenToList2(list);
        reportService.updatePayMonth(budgetList);
        jsonData = jsonData.success("更新成功");
        return jsonData;
    }

    //下载查看
    @RequestMapping(value = "/report/outgoings/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getPayList(@RequestBody Map<String, Object> PARAM, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();

        //更新支出表的年初数MONEY1
        int count = reportService.updateOUTEYear(FISCAL, FIS_PERD, CO_CODE);
        JsonData jsonData = new JsonData();

        if (count > 0) {
            List<Map<String, Object>> datalist = reportService.getData(FISCAL, FIS_PERD, CO_CODE);

            if (STATUS.equals("1")) {
                if (datalist.size() == 0) {
                    jsonData = jsonData.fail("未生成报表");
                } else {
                    List<BudgetTreeNode> budgetTreeNodes = FormatHelper.OutgoingListToTreeByIsbnCode(datalist);
                    jsonData = jsonData.success(budgetTreeNodes, "获取数据成功", datalist.size());
                }
            } else {
                if (datalist.size() == 0) {
                    jsonData = jsonData.fail("未生成该报表");
                } else {
                    List<Map<String, Object>> datalist2 = reportService.getData2(FISCAL, FIS_PERD, CO_CODE);
                    Map map = new HashMap();
                    Map map1 = new HashMap();
                    datalist2.add(map);
                    datalist2.add(map1);
                    String sheetName = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月预算支出表";
                    String fileno = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月预算支出表";
                    String head = "单位：元(保留至角分)";
                    String[] head0 = new String[]{"编码", "支出科目", "栏号", "公共财政预算支出", "公共财政预算支出", "公共财政预算支出", "公共财政预算支出"
                            , "公共财政预算支出", "公共财政预算支出", "公共财政预算支出", "其他预算支出", "其他预算支出"};
                    String[] headnum0 = new String[]{"2,4,0,0", "2,4,1,1", "2,4,2,2", "2,2,3,9", "2,2,10,11"};
                    String[] head1 = new String[]{"", "", "", "支出预算计划", "支出预算计划", "支出预算计划", "", "本月支出", "累计支出", "占计划%", "预算外支出", "预算外支出"};
                    String[] headnum1 = new String[]{"3,3,3,5", "3,4,7,7", "3,4,8,8", "3,4,9,9", "3,3,10,11"};
                    String[] head2 = new String[]{"年初预算", "科目调整", "上级补助", "小计", "", "", "", "本月支出", "累计支出"};
                    String[] detail = new String[]{"B_ACC_CODE", "B_ACC_NAME", "ID", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6", "PLAN", "MONEY7", "MONEY8"};
                    Excel.exportExecution(request, response, datalist2, sheetName, head, head0, headnum0, head1, headnum1, head2, detail, fileno);
                }
            }
        }
        return jsonData;
    }

    //新增对应关系接口 做分页
    @RequestMapping(value = "/report/outgoings/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonData page(@RequestBody Map<String, Object> PARAM) {
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String FISCAL = PARAM.get("FISCAL").toString();
        JsonData jsonData = new JsonData();
        DynamicDataSourceHolder.setDataSource("dataSource18");
//        Integer PageSize = Integer.valueOf(request.getParameter("PageSize"));//每页条数
//        Integer PageIndex = Integer.valueOf(request.getParameter("PageIndex"));//页码
//        PageHelper.startPage(PageIndex, PageSize);

        //获取数据
        List<Map<String, Object>> mapList = reportService.getRelation(CO_CODE, FISCAL);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(mapList);
        jsonData = jsonData.success(mapList, "获取成功", Integer.valueOf(String.valueOf(pageInfo.getTotal())));

        return jsonData;
    }

    /**
     * 教育表
     */
    //判断本地库是否存在数据
    @RequestMapping(value = "/report/education/isExist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData isExitEdu(@RequestBody Map<String,Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();

        JsonData jsonData = new JsonData();
        int count = reportService.isExitEdu(FIS_PERD, FISCAL, CO_CODE);
        if (count > 0) {
            jsonData = jsonData.fail("数据已存在");
        } else {
            jsonData = jsonData.success("数据不存在");
        }
        return jsonData;
    }

    //从用友取数，刷新本地数据库，向前端展示数据
    //从用友数据库取数；刷新本地数据库再插入数据
    @RequestMapping(value = "/report/education/getData", method = RequestMethod.POST)
    @ResponseBody
    public JsonData addEduData(@RequestBody Map<String,Object> PARAM) {

        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        JsonData jsonData = new JsonData();
        int deleteCount = reportService.deleteEdu(FIS_PERD, FISCAL, CO_CODE);
        System.out.println(deleteCount);
        //连接到用友数据库
        DynamicDataSourceHolder.setDataSource("dataSource18");
        //取数
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FIS_MONTH", FIS_PERD);
        map.put("FIS_YEAR", FISCAL);
        map.put("cur", new ArrayList<Map<String, Object>>());

        //调用存储过程
        reportService.doEdu(map);

        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        String[] proName = new String[]{"彩票发行机构和彩票销售机构的业务费用", "事业单位对外投资收益", "事业单位国有资产出租收入"};

        //插入本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        for (Map<String, Object> map1 : mapList) {
            //插入用友里查出来的，在新建3条收入的和3条支出的
            String ACC_TYPE = map1.get("ACC_TYPE").toString();
            if (ACC_TYPE.equals("4")) {
                String[] codeArray = new String[]{"461202", "461203", "461204"};
                for (int i = 0; i < proName.length; i++) {
                    EducationModel education = new EducationModel();
                    education.setFISCAL(FISCAL);
                    education.setFIS_PERD(FIS_PERD);
                    education.setCO_CODE(CO_CODE);
                    education.setACC_NAME(proName[i]);
                    education.setACC_TYPE(ACC_TYPE);
                    education.setACC_CODE(codeArray[i]);
                    education.setMONEY3("0");
                    count = reportService.addEdu(education);
                }
            } else if (ACC_TYPE.equals("5")) {
                String[] codeArray = new String[]{"590402", "590403", "590404"};
                for (int i = 0; i < proName.length; i++) {
                    EducationModel education = new EducationModel();
                    education.setFISCAL(FISCAL);
                    education.setFIS_PERD(FIS_PERD);
                    education.setCO_CODE(CO_CODE);
                    education.setACC_NAME(proName[i]);
                    education.setACC_TYPE(ACC_TYPE);
                    education.setACC_CODE(codeArray[i]);
                    education.setMONEY3("0");
                    count = reportService.addEdu(education);
                }
            }
            EducationModel education = new EducationModel();
            education.setACC_CODE(map1.get("ACC_CODE").toString());
            education.setACC_NAME(map1.get("ACC_NAME").toString());
            education.setACC_TYPE(map1.get("ACC_TYPE").toString());
            education.setMONEY1(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString());
            education.setMONEY2(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString());
            education.setMONEY3(map1.get("MONEY3") == null ? "0" : map1.get("MONEY3").toString());
            education.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
            education.setCO_CODE(CO_CODE);
            education.setFIS_PERD(FIS_PERD);
            education.setFISCAL(FISCAL);
            count = reportService.addEdu(education);
        }

        List<Map<String, Object>> datalist = reportService.getEdu(FISCAL, FIS_PERD, CO_CODE);
        if (count > 0) {
            jsonData = jsonData.success(datalist, "报表生成成功", datalist.size());
        } else {
            jsonData = jsonData.fail("报表生成失败");
        }
        return jsonData;
    }


    //编辑教育表
    @RequestMapping(value = "/report/education/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonData editEdu(@RequestBody List<Map<String,Object>> educationList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = reportService.updateEdu(educationList);
        if (count > 0) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    //下载查看
    @RequestMapping(value = "/report/education/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getEdu(@RequestBody Map<String,Object> PARAM,HttpServletResponse response,HttpServletRequest request) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();

        JsonData jsonData = new JsonData();

        List<Map<String, Object>> datalist = reportService.getEdu(FISCAL, FIS_PERD, CO_CODE);
        if (STATUS.equals("1")) {
            if (datalist.size() == 0) {
                jsonData = jsonData.fail("未生成报表");
            } else {
                jsonData = jsonData.success(datalist, "获取数据成功", datalist.size());
            }
        } else {
            if (datalist.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {

                List<Map<String, Object>> datalist2 = reportService.getEdu2(FISCAL, FIS_PERD, CO_CODE);//下载总计含千分位
                String sheetName = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月" + "教育收支表";
                String fileno = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月" + "教育收支表";
                String head = "单位：见下";
                String[] head0 = new String[]{"项目", "本年累计发生额(万元)", "本年累计发生额合计(元)", "县级(元)", "镇级(元)"};
                String[] detail = new String[]{"ACC_NAME", "MONEY1", "MONEY2", "MONEY3", "MONEY4"};

                Excel.exportEducation(request, response, datalist2, head0, head, sheetName, fileno, detail);

            }
        }
        return jsonData;
    }

    /**
     * 专户表
     */
    //判断本地数据库是否有数据,刷新,从用友取数，插入到本地数据库（除合计），向前端展示数据
    @RequestMapping(value = "/report/specialr/getData", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getSpecialr(@RequestBody Map<String,Object> PARAM) {
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        JsonData jsonData = new JsonData();
        DynamicDataSourceHolder.setDataSource("dataSource1");
        //判断本地库是否存在数据
        int count = reportService.isExitSpe(FIS_PERD, FISCAL, CO_CODE);

        if (count > 0) {
            //删除本地数据库数据
            count = reportService.deleteSpe(FIS_PERD, FISCAL, CO_CODE);
        }

        //从用友数据库调用存储过程
        Map map = new HashMap();
        map.put("CO_CODE", CO_CODE);
        map.put("FIS_PERD", FIS_PERD);
        map.put("FISCAL", FISCAL);
        map.put("cur", new ArrayList<Map<String, Object>>());

        DynamicDataSourceHolder.setDataSource("dataSource18");
        reportService.doSpe(map);

        DynamicDataSourceHolder.setDataSource("dataSource1");
        //从存储过程获取数据插入到本地数据库
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        for (Map<String, Object> map1 : mapList) {
            SpecialrModel specialr = new SpecialrModel();
            specialr.setACC_CODE(map1.get("ACC_CODE").toString());
            specialr.setACC_NAME(map1.get("ACC_NAME").toString());
            specialr.setMONEY1(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString());
            specialr.setMONEY2(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString());
            specialr.setMONEY3(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString());
            specialr.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
            specialr.setFIS_PERD(FIS_PERD);
            specialr.setFISCAL(FISCAL);
            specialr.setCO_CODE(CO_CODE);
            count = reportService.addSpe(specialr);
        }
        if (count > 0) {
            List<Map<String, Object>> speList = reportService.getSpe(FIS_PERD, FISCAL, CO_CODE);
            jsonData = jsonData.success(speList, "报表生成成功", speList.size());
        } else {
            jsonData = jsonData.fail("报表生成失败");
        }
        return jsonData;
    }

    //[专户表]查看下载
    @RequestMapping(value = "/report/specialr/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getSpelist(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();

        JsonData jsonData = new JsonData();

        if (STATUS.equals("1")) {
            List<Map<String, Object>> speList = reportService.getSpe(FIS_PERD, FISCAL, CO_CODE);
            if (speList.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                jsonData = jsonData.success(speList, "生成报表成功", speList.size());
            }
        } else {
            List<SpecialrModel> dataList = reportService.getSpe2(FISCAL, FIS_PERD, CO_CODE);
            String sheetName = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月专户收支表";
            String fileno = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月专户收支表";
            String danwei = "单位：元角分";
            String[] head0 = new String[]{"项目", "专项收入", "专项收入", "专项支出", "专项支出", "备注"};
            String[] head1 = new String[]{"本月收入", "累计收入", "本月支出", "累计支出"};
            String[] headnum0 = new String[]{"2,3,0,0", "2,2,1,2", "2,2,3,4", "2,3,5,5"};

            Excel.exportAccount(request, response, dataList, sheetName, head0, headnum0, head1, danwei, fileno);

        }

        return jsonData;
    }


    /**
     * 预算收入表
     */
    //[区财政]判断是否有该报表
    @RequestMapping(value = "/report/income/isExist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData isExistIncome(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        String CO_CODE = request.getParameter("CO_CODE");
        JsonData jsonData = new JsonData();
        int isExist = reportService.isExistIncome(FISCAL, FIS_PERD, CO_CODE);
        if (isExist > 0) {
            jsonData = jsonData.fail("报表已存在");
        } else {
            //生成报表
            //1.获取用友的GL_ITEM_CODE
            String[] gl_item_code = new String[]{"101", "103", "106", "107", "108", "109", "110", "111", "112", "113", "114", "11401", "11402",
                    "115", "11502", "11501", "116", "117", "118", "119", "120"};
            String[] gl_item_name = new String[]{"一、增值税", "三、消费税", "六、土地增值税", "七、城市维护建设税", "八、车船使用税",
                    "九、房产税", "十、土地使用税", "十一、印花税", "十二、资源税", "十三、环境保护税", "十四、所得税小计", "  个人所得税", "  企业所得税",
                    "十五、契税", "中心契税", "分局契税", "十六、耕地占用税", "十七、教育费附加（90%）", "十八、地方教育费附加（90%）",
                    "十九、残疾人保障金（90%）", "二十、水利建设专项资金（85%）"};
            List<IncomeModel> incomeList = new ArrayList<>();
            for (int i = 0; i < gl_item_code.length; i++) {
                IncomeModel income = new IncomeModel();
                income.setGL_ITEM_CODE(gl_item_code[i]);
                income.setGL_ITEM_NAME(gl_item_name[i]);
                income.setCO_CODE(CO_CODE);
                income.setFIS_PERD(FIS_PERD);
                income.setFISCAL(FISCAL);
                incomeList.add(income);
            }
            int count1 = reportService.addIncome(incomeList);
            List<Map<String, Object>> dataList = reportService.getIncome(FISCAL, FIS_PERD, CO_CODE);
            if (count1 > 0) {
                jsonData = jsonData.success(dataList, "报表生成成功", dataList.size());
            } else {
                jsonData = jsonData.fail("报表生成失败");
            }
        }
        return jsonData;
    }

    //[区财政][乡镇]编辑
    @RequestMapping(value = "/report/income/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonData updateIncome(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<IncomeModel> incomeList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<IncomeModel>>() {
        });
        JsonData jsonData = new JsonData();
        int count = reportService.updateIncome(incomeList);
        if (count > 0) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    //[区财政]下发
    @RequestMapping(value = "/report/income/send", method = RequestMethod.POST)
    @ResponseBody
    public JsonData updateSend(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        String CO_CODE = request.getParameter("CO_CODE");

        JsonData jsonData = new JsonData();
        int count = reportService.send(FISCAL, FIS_PERD, CO_CODE);
        if (count > 0) {
            jsonData = jsonData.success("下发成功");
        } else {
            jsonData = jsonData.fail("下发失败");
        }
        return jsonData;
    }

    //[乡镇]退回
    @RequestMapping(value = "/report/income/back", method = RequestMethod.POST)
    @ResponseBody
    public JsonData updateback(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        String CO_CODE = request.getParameter("CO_CODE");

        JsonData jsonData = new JsonData();
        int count = reportService.back(FISCAL, FIS_PERD, CO_CODE);
        if (count > 0) {
            jsonData = jsonData.success("退回成功");
        } else {
            jsonData = jsonData.fail("退回失败");
        }
        return jsonData;
    }

    //[乡镇][区财政]查看下载
    @RequestMapping(value = "/report/income/getList", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getIncome(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();
        JsonData jsonData = new JsonData();


        if (STATUS.equals("1")) {
            //查看
            List<Map<String, Object>> incomeList = reportService.getIncome(FISCAL, FIS_PERD, CO_CODE);
            if (incomeList.size() == 0) {
                jsonData = jsonData.fail("未生成报表");
            } else {
                jsonData = jsonData.success(incomeList, "报表生成成功", incomeList.size());
            }
        } else {
            List<Map<String, Object>> incomeList = reportService.getIncome2(FISCAL, FIS_PERD, CO_CODE);
            if (incomeList.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                String date = MaxDateUtil.getLastDayOfMonth(FISCAL + "-" + FIS_PERD);
                String dateStr = "所属日期:" + date;
                String sheetName = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月财政收入表";
                String fileno = PARAM.get("SHORTNAME") + FISCAL + "年" + FIS_PERD + "月财政收入表";
                String[] head0 = new String[]{"收入科目", "财政总收入", "财政总收入", "财政总收入", "财政总收入", "一般公共预算收入", "一般公共预算收入", "一般公共预算收入", "一般公共预算收入"};
                String[] headnum0 = new String[]{"2,3,0,0", "2,2,1,4", "2,2,5,8"};
                String[] head1 = new String[]{"", "本月收入", "累计收入", "上年同期数", "与上年同期比（%）", "本月收入", "累计收入", "上年同期数", "与上年同期比（%）"};
                String[] detail = new String[]{"GL_ITEM_NAME", "MONEY1", "MONEY2", "MONEY3", "PROPORTION1", "MONEY4", "MONEY5", "MONEY6", "PROPORTION2"};
                Excel.exportIncome(request, response, incomeList, sheetName, head0, headnum0, head1, detail, dateStr, fileno);
            }
        }
        return jsonData;
    }


    @RequestMapping(value = "/report/income/open", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView uploadExcel(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    //收入表2003excel导入
    @RequestMapping(value = "/report/income/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonData importIncome(HttpServletRequest request) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        String CO_CODE = request.getParameter("CO_CODE");
        /*String FISCAL = "2018";
        String FIS_PERD = "2";
        String CO_CODE="123";*/


        //判断[收入表]数据库中是否有数据
        int count = reportService.isExistIncome(FISCAL, FIS_PERD, CO_CODE);
        //删除当月的本地数据库数据
        if (count > 0) {
            int deletecount = reportService.deleteIncome(FISCAL, FIS_PERD, CO_CODE);
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        System.out.println("excel文件导入开始" + multipartRequest.getFile("upfile"));


        MultipartFile file = multipartRequest.getFile("upfile");
        String Path = request.getServletContext().getRealPath("/upload") + "/" + file.getOriginalFilename();
        File localFile = new File(Path);
        if (!localFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            localFile.getParentFile().mkdirs();
            file.transferTo(localFile);
        }

        InputStream in = file.getInputStream();
        String fileName = file.getOriginalFilename();

        boolean flag = reportService.importExcel(in, fileName, Path, FISCAL, FIS_PERD, CO_CODE);
        JsonData jsonData = new JsonData();
        in.close();
        if (flag) {
            FileUtils.deleteQuietly(localFile);
            jsonData = jsonData.success("导入成功");
        } else {
            FileUtils.deleteQuietly(localFile);
            jsonData = jsonData.fail("导入失败");
        }

        return jsonData;
    }


    /**
     * 年初数报表
     */
    //生成年初数接口报表
    @RequestMapping(value = "/report/earlyYear", method = RequestMethod.POST)
    @ResponseBody
    public JsonData addearlyYear(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int i = 1;
        int count = 0;
        String FISCAL = request.getParameter("FISCAL");
        String CO_CODE = request.getParameter("CO_CODE");
        JsonData jsonData = new JsonData();
        //判断本地库是否存在数据
        int isExistEYear = reportService.isExistEYear(FISCAL, CO_CODE);
        if (isExistEYear > 0) {
            jsonData = jsonData.fail("年初数已生成");
        } else {
            //连接用友获取数据
            DynamicDataSourceHolder.setDataSource("dataSource18");
            List<Map<String, Object>> mapList = reportService.getEYearForm18(FISCAL, CO_CODE);
            DynamicDataSourceHolder.setDataSource("dataSource1");
            List<EarlyYearModel> eYearList = new ArrayList<>();
            for (Map<String, Object> map : mapList) {
                EarlyYearModel eyear = new EarlyYearModel();
                eyear.setB_ACC_CODE(map.get("B_ACC_CODE").toString());
                eyear.setB_ACC_NAME(map.get("B_ACC_NAME").toString());
                eyear.setCO_CODE(CO_CODE);
                eyear.setFISCAL(FISCAL);
                eYearList.add(eyear);
                if (i % 100 == 0) {
                    count = reportService.addEyear(eYearList);
                    eYearList.clear();
                    i = 1;
                } else {
                    i++;
                }
            }
            if (i > 1) {
                count = reportService.addEyear(eYearList);
            }
            if (count > 0) {
                List<Map<String, Object>> eyearLists = reportService.getEYear(FISCAL, CO_CODE);
                jsonData = jsonData.success(eyearLists, "年初数生成成功", eyearLists.size());
            } else {
                jsonData = jsonData.fail("年初数生成失败");
            }

        }
        return jsonData;
    }

    //更新年初数
    @RequestMapping(value = "/report/earlyYear/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonData update(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> earlyYearList = FormatHelper.findChildrenToList2(list);

        JsonData jsonData = new JsonData();
        int count = reportService.updateEarlYear(earlyYearList);
        if (count > 0) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }

        return jsonData;
    }

    //查看接口
    @RequestMapping(value = "/report/earlyYear/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getEYearList(@RequestBody Map<String,Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        JsonData jsonData = new JsonData();
        List<Map<String, Object>> eyearLists = reportService.getEYear(FISCAL, CO_CODE);
        List<EarlyYearTreeNode> earlyYearTreeNodes = FormatHelper.EarlyYearListToTreeByIsbnCode(eyearLists);
        if (eyearLists.size() == 0) {
            jsonData = jsonData.fail("未生成年初数");
        } else {
            jsonData = jsonData.success(earlyYearTreeNodes, "数据生成成功", eyearLists.size());
        }
        return jsonData;
    }

}