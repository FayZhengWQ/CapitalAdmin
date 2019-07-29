package com.heitian.ssm.controller.report;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.report.*;
import com.heitian.ssm.service.report.ReportGovService;
import com.heitian.ssm.utils.Date.MaxDateUtil;
import com.heitian.ssm.utils.excel.Excel;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ReportGovController
 * Package:com.heitian.ssm.controller
 * Description:
 * author:@Fay
 * Date:2019/4/98:51
 */
@Controller
@CrossOrigin
public class ReportGovController {

    @Autowired
    private ReportGovService reportGovService;

    /**
     * 对个人和家庭的补助明细表
     */
    @RequestMapping(value = "/report/gov/subsidy", method = RequestMethod.POST)
    @ResponseBody
    public JsonData allowance(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        int count = 0;
        int i = 1;
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();
        JsonData jsonData = new JsonData();
        //判断本地库是否有数据
        int isExitAllow = reportGovService.isExitAllow(FISCAL, FIS_PERD, CO_CODE);
        System.out.println("isExitAllow:" + isExitAllow);

        //删除本地数据库当月的数据
        if (isExitAllow > 0) {
            int deleteCount = reportGovService.deleteAllow(FISCAL, FIS_PERD, CO_CODE);
            System.out.println("deleteCount:" + deleteCount);
        }


        //连接到用友取数
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("fiscal", FISCAL);
        map.put("fis_perd", FIS_PERD);
        map.put("co_code", CO_CODE);
        map.put("cur", new ArrayList<Map<String, Object>>());
        reportGovService.doAllow(map);

        //插入到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        //调用存储过程
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        List<AllowanceModel> allowList = new ArrayList();
        for (Map<String, Object> map1 : mapList) {


            AllowanceModel allow = new AllowanceModel();
            allow.setB_ACC_CODE(map1.get("B_ACC_CODE") == null ? "" : map1.get("B_ACC_CODE").toString());
            allow.setB_ACC_NAME(map1.get("B_ACC_NAME") == null ? "" : map1.get("B_ACC_NAME").toString());
            allow.setACC_CODE(map1.get("ACC_CODE") == null ? "" : map1.get("ACC_CODE").toString());
            allow.setMONEY1(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString());
            allow.setMONEY2(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString());
            allow.setMONEY3(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString());
            allow.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
            allow.setMONEY5(map1.get("MONEY5") == null ? "" : map1.get("MONEY5").toString());
            allow.setMONEY6(map1.get("MONEY6") == null ? "" : map1.get("MONEY6").toString());
            allow.setMONEY7(map1.get("MONEY7") == null ? "" : map1.get("MONEY7").toString());
            allow.setMONEY8(map1.get("MONEY8") == null ? "" : map1.get("MONEY8").toString());
            allow.setMONEY9(map1.get("MONEY9") == null ? "" : map1.get("MONEY9").toString());
            allow.setMONEY10(map1.get("MONEY10") == null ? "" : map1.get("MONEY10").toString());
            allow.setMONEY11(map1.get("MONEY11") == null ? "" : map1.get("MONEY11").toString());
            allow.setMONEY12(map1.get("MONEY12") == null ? "" : map1.get("MONEY12").toString());
            allow.setMONEY13(map1.get("MONEY13") == null ? "" : map1.get("MONEY13").toString());
            allow.setFISCAL(FISCAL);
            allow.setCO_CODE(CO_CODE);
            allow.setFIS_PERD(FIS_PERD);
            allowList.add(allow);


            if (i % 100 == 0) {
                count = reportGovService.addAllow(allowList);
                i = 1;
            } else {
                i++;
            }
        }
        if (i > 1) {
            count = reportGovService.addAllow(allowList);
        }
        if (count > 0) {

            if (STATUS.equals("1")) {
                //查看
                List<Map<String, Object>> dataList = reportGovService.getAllow(FISCAL, FIS_PERD, CO_CODE);
                if (dataList.size() == 0) {
                    jsonData = jsonData.fail("未生成报表");
                } else {
                    jsonData = jsonData.success(dataList, "成功", dataList.size());
                }
            } else {
                //下载
                List<Map<String, Object>> dataList = reportGovService.getAllow2(FISCAL, FIS_PERD, CO_CODE);
                String[] ids = new String[]{"","","01","02","03","04","05","06","07","08","09","10","11","","01","02","03","04","05","06","07","08","09","10","11"};
                for (int j= 0;j<dataList.size();j++){
                    dataList.get(j).put("ID",ids[j]);
                }
                String dateStr = MaxDateUtil.getLastDayOfMonth(FISCAL+"-"+FIS_PERD);
                String sheetName = "对   个   人   和   家   庭   的   补   助   明   细   表";
                String fileno = "对个人和家庭的补助明细表";
                String[] head0 = new String[]{"单位名称：" + PARAM.get("SHORTNAME"), "单位名称：柯桥区" + PARAM.get("SHORTNAME"),
                        "单位名称：" + PARAM.get("SHORTNAME") , dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, "单位:元（以下至角分）", "单位:元（以下至角分）", "单位:元（以下至角分）"};
                String[] headnum0 = new String[]{"1,1,0,2", "1,1,3,12", "1,1,13,15"};
                String[] head1 = new String[]{"功能分类科目", "", "合计", "离休费", "退休费", "退职（役）费", "抚恤金", "生活补助", "救济费", "医疗补助费", "助学金","奖励金","个人农业生产补助","住房公积金","购房补贴", "其它对个人和家庭的补助"};
                String[] headnum1 = new String[]{"2,2,0,1", "2,3,2,2", "2,3,3,3", "2,3,4,4", "2,3,5,5", "2,3,6,6", "2,3,7,7", "2,3,8,8", "2,3,9,9", "2,3,10,10", "2,3,11,11", "2,3,12,12", "2,3,13,13", "2,3,14,14", "2,3,15,15"};
                String[] head2 = new String[]{"编码", "名称","","","","","","","","","","","","","",""};
                String[] headnum2 = new String[]{"3,4,0,0", "3,4,1,1"};
                String[] head3 = new String[]{"", "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13","14"};
                String[] detail = new String[]{"ID", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6", "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13"};

                Excel.exportGov(request, response, dataList, sheetName, head0, headnum0, head1, headnum1, head2, headnum2, head3, detail, fileno);

            }
        } else {
            jsonData = jsonData.fail("报表生成失败");
        }

        return jsonData;
    }

    /**
     * 工资福利支出明细表
     */
    @RequestMapping(value = "/report/gov/salary", method = RequestMethod.POST)
    @ResponseBody
    public JsonData Salary(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();

        //判断本地库是否存在数据
        int isExistFee = reportGovService.isExistSalary(FISCAL, FIS_PERD, CO_CODE);
        System.out.println("isExistFee:" + isExistFee);

        if (isExistFee > 0) {
            int deleteSalary = reportGovService.deleteSalary(FISCAL, FIS_PERD, CO_CODE);
            System.out.println(deleteSalary);
        }

        //连接到用友数据库，调用存储过程
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("fiscal", FISCAL);
        map.put("fis_perd", FIS_PERD);
        map.put("co_code", CO_CODE);
        map.put("cur", new ArrayList<>());
        reportGovService.doSalary(map);

        //连接到本地数据库
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        List<SalaryModel> salaryList = new ArrayList<>();
        //插入到本地数据库
        int i = 1;
        int count = 0;
        for (Map<String, Object> map1 : mapList) {

            SalaryModel salary = new SalaryModel();
            salary.setFISCAL(FISCAL);
            salary.setCO_CODE(CO_CODE);
            salary.setFIS_PERD(FIS_PERD);
            salary.setB_ACC_CODE(map1.get("B_ACC_CODE") == null ? "" : map1.get("B_ACC_CODE").toString());
            salary.setB_ACC_NAME(map1.get("B_ACC_NAME") == null ? "" : map1.get("B_ACC_NAME").toString());
            salary.setACC_CODE(map1.get("ACC_CODE") == null ? "" : map1.get("ACC_CODE").toString());
            salary.setMONEY1(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString());
            salary.setMONEY2(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString());
            salary.setMONEY3(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString());
            salary.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
            salary.setMONEY5(map1.get("MONEY5") == null ? "" : map1.get("MONEY5").toString());
            salary.setMONEY6(map1.get("MONEY6") == null ? "" : map1.get("MONEY6").toString());
            salary.setMONEY7(map1.get("MONEY7") == null ? "" : map1.get("MONEY7").toString());
            salary.setMONEY8(map1.get("MONEY8") == null ? "" : map1.get("MONEY8").toString());
            salary.setMONEY9(map1.get("MONEY9") == null ? "" : map1.get("MONEY9").toString());
            salary.setMONEY10(map1.get("MONEY10") == null ? "" : map1.get("MONEY10").toString());
            salary.setMONEY11(map1.get("MONEY11") == null ? "" : map1.get("MONEY11").toString());
            salary.setMONEY12(map1.get("MONEY12") == null ? "" : map1.get("MONEY12").toString());
            salary.setMONEY13(map1.get("MONEY13") == null ? "" : map1.get("MONEY13").toString());
            salary.setMONEY14(map1.get("MONEY14") == null ? "" : map1.get("MONEY14").toString());
            salaryList.add(salary);
            if (i % 100 == 0) {
                count = reportGovService.addSalary(salaryList);
                i = 1;
                salaryList.clear();
            } else {
                i++;
            }

        }
        if (i > 1) {
            count = reportGovService.addSalary(salaryList);
        }
        if (count > 0) {
            if (STATUS.equals("1")) {
                //查看
                List<Map<String, Object>> dataList = reportGovService.getSalary(FIS_PERD, FISCAL, CO_CODE);
                if (dataList.size() == 1) {
                    jsonData = jsonData.fail("未生成报表");
                } else {
                    jsonData = jsonData.success(dataList, "成功", dataList.size());
                }
            } else {
                //下载
                List<Map<String, Object>> dataList = reportGovService.getSalary2(FIS_PERD, FISCAL, CO_CODE);
                String[] ids = new String[]{"","","01","02","03","04","05","06","07","08","09","10","11","","01","02","03","04","05","06","07","08","09","10","11"};
                for (int k=0;k<dataList.size();k++){
                    dataList.get(k).put("ID",ids[k]);
                }
                String dateStr = MaxDateUtil.getLastDayOfMonth(FISCAL+"-"+FIS_PERD);
                String sheetName = "工    资    福    利    支    出    明    细    表";
                String fileno = "工资福利支出明细表";
                String[] head0 = new String[]{"单位名称：" + PARAM.get("SHORTNAME") , "单位名称：柯桥区" + PARAM.get("SHORTNAME") ,
                        "单位名称：" + PARAM.get("SHORTNAME") , dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr, dateStr,"", "单位:元（以下至角分）", "单位:元（以下至角分）", "单位:元（以下至角分）"};
                String[] headnum0 = new String[]{"1,1,0,2", "1,1,3,13", "1,1,14,16"};
                String[] head1 = new String[]{"功能分类科目", "", "合计", "基本工资", "津贴补贴", "奖金", "其他社会保障费", "伙食补助费", "绩效工资",
                        "机关事业单位基本养老保险缴费", "职业年金缴费", "城镇职工基本医疗保险缴费", "公务员医疗补助缴费", "其他社会保障缴费", "住房公积金", "医疗费", "其他工资福利支出"};
                String[] headnum1 = new String[]{"2,2,0,1", "2,3,2,2", "2,3,3,3", "2,3,4,4", "2,3,5,5", "2,3,6,6", "2,3,7,7", "2,3,8,8", "2,3,9,9", "2,3,10,10", "2,3,11,11"
                        , "2,3,12,12", "2,3,13,13", "2,3,14,14", "2,3,15,15","2,3,16,16"};
                String[] head2 = new String[]{"编码", "名称","","","","","","","","","","","","","","",""};
                String[] headnum2 = new String[]{"3,4,0,0", "3,4,1,1"};
                String[] head3 = new String[]{"", "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
                String[] detail = new String[]{"ID", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6", "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14"};

                Excel.exportGov(request, response, dataList, sheetName, head0, headnum0, head1, headnum1, head2, headnum2, head3, detail, fileno);
            }

        }
        return jsonData;
    }

    /**
     * 其他资本性支出明细表
     */
    @RequestMapping(value = "/report/gov/otherpay", method = RequestMethod.POST)
    @ResponseBody
    public JsonData otherPay(@RequestBody Map<String,Object> PARAM,HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();

        //判断本地数据库是否有数据
        int isExistOtherPay = reportGovService.isExistOtherPay(FIS_PERD, FISCAL, CO_CODE);

        if (isExistOtherPay > 0) {
            //删除本地数据库数据
            int deleteCount = reportGovService.deleteOtherPay(FIS_PERD, FISCAL, CO_CODE);
        }

        //调用存储过程
        Map map = new HashMap();
        map.put("fiscal", FISCAL);
        map.put("fis_perd", FIS_PERD);
        map.put("co_code", CO_CODE);
        map.put("cur", new ArrayList<>());

        //连接到用友数据库
        DynamicDataSourceHolder.setDataSource("dataSource18");
        reportGovService.doOtherPay(map);

        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        List<OtherPayModel> otherPayList = new ArrayList<>();

        int i = 1;
        int count = 0;
        for (Map<String, Object> map1 : mapList) {

            OtherPayModel otherPay = new OtherPayModel();
            otherPay.setFISCAL(FISCAL);
            otherPay.setCO_CODE(CO_CODE);
            otherPay.setFIS_PERD(FIS_PERD);
            otherPay.setB_ACC_CODE(map1.get("B_ACC_CODE") == null ? "" : map1.get("B_ACC_CODE").toString());
            otherPay.setB_ACC_NAME(map1.get("B_ACC_NAME") == null ? "" : map1.get("B_ACC_NAME").toString());
            otherPay.setACC_CODE(map1.get("ACC_CODE") == null ? "" : map1.get("ACC_CODE").toString());
            otherPay.setACC_CODE(map1.get("ACC_CODE") == null ? "" : map1.get("ACC_CODE").toString());
            otherPay.setMONEY1(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString());
            otherPay.setMONEY2(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString());
            otherPay.setMONEY3(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString());
            otherPay.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
            otherPay.setMONEY5(map1.get("MONEY5") == null ? "" : map1.get("MONEY5").toString());
            otherPay.setMONEY6(map1.get("MONEY6") == null ? "" : map1.get("MONEY6").toString());
            otherPay.setMONEY7(map1.get("MONEY7") == null ? "" : map1.get("MONEY7").toString());
            otherPay.setMONEY8(map1.get("MONEY8") == null ? "" : map1.get("MONEY8").toString());
            otherPay.setMONEY9(map1.get("MONEY9") == null ? "" : map1.get("MONEY9").toString());
            otherPay.setMONEY10(map1.get("MONEY10") == null ? "" : map1.get("MONEY10").toString());
            otherPay.setMONEY11(map1.get("MONEY11") == null ? "" : map1.get("MONEY11").toString());
            otherPay.setMONEY12(map1.get("MONEY12") == null ? "" : map1.get("MONEY12").toString());
            otherPay.setMONEY13(map1.get("MONEY13") == null ? "" : map1.get("MONEY13").toString());
            otherPay.setMONEY14(map1.get("MONEY14") == null ? "" : map1.get("MONEY14").toString());
            otherPay.setMONEY15(map1.get("MONEY15") == null ? "" : map1.get("MONEY15").toString());
            otherPay.setMONEY16(map1.get("MONEY16") == null ? "" : map1.get("MONEY16").toString());
            otherPayList.add(otherPay);
            if (i % 100 == 0) {
                count = reportGovService.addOtherPay(otherPayList);
                i = 1;
                otherPayList.clear();
            } else {
                i++;
            }

        }
        if (i > 1) {
            count = reportGovService.addOtherPay(otherPayList);
        }

        if (count > 0) {
            if (STATUS.equals("1")) {
                //查看
                List<Map<String, Object>> dataList = reportGovService.getOtherPay(FIS_PERD, FISCAL, CO_CODE);
                if (dataList.size() == 1) {
                    jsonData = jsonData.fail("未生成报表");
                } else {
                    jsonData = jsonData.success(dataList, "成功", dataList.size());
                }
            } else {
                //下载
                List<Map<String, Object>> dataList = reportGovService.getOtherPay2(FIS_PERD, FISCAL, CO_CODE);
                String[] ids = new String[]{"","","01","02","03","04","05","06","07","08","09","10","11","","01","02","03","04","05","06","07","08","09","10","11"};
                for (int j= 0;j<dataList.size();j++){
                    dataList.get(j).put("ID",ids[j]);
                }
                String dateStr = MaxDateUtil.getLastDayOfMonth(FISCAL+"-"+FIS_PERD);
                String sheetName = "其    他    资    本    性    支    出    明    细    表";
                String fileno = "其他资本性支出明细表";
                String[] head0 = new String[]{"单位名称：" + PARAM.get("SHORTNAME"), "单位名称：" + PARAM.get("SHORTNAME"),
                        "单位名称：" + PARAM.get("SHORTNAME"), dateStr, "", "", "", "", "", "", "", "", "", "", "",  "单位:元（以下至角分）", "单位:元（以下至角分）", "单位:元（以下至角分）","单位:元（以下至角分）"};
                String[] headnum0 = new String[]{"1,1,0,2", "1,1,3,14", "1,1,15,18"};
                String[] head1 = new String[]{"功能分类科目", "", "合计", "房屋建筑物购建", "办公设备购置", "专用设备购置", "基础设施建设", "大型修缮", "信息网络及软件购置更新",
                        "物资储备", "土地补偿", "安置补助", "地上附着物和青苗补偿", "拆迁补偿", "公务用车购置", "其他交通工具购置", "文物和陈列品购置", "无形资产购置", "其他资本性支出"};
                String[] headnum1 = new String[]{"2,2,0,1", "2,3,2,2", "2,3,3,3", "2,3,4,4", "2,3,5,5", "2,3,6,6", "2,3,7,7", "2,3,8,8", "2,3,9,9", "2,3,10,10", "2,3,11,11"
                        , "2,3,12,12", "2,3,13,13", "2,3,14,14", "2,3,15,15", "2,3,16,16", "2,3,17,17", "2,3,18,18"};
                String[] head2 = new String[]{"编码", "名称","","","","","","","","","","","","","","","","",""};
                String[] headnum2 = new String[]{"3,4,0,0", "3,4,1,1"};
                String[] head3 = new String[]{"", "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};
                String[] detail = new String[]{"ID", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6", "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                Excel.exportGov(request, response, dataList, sheetName, head0, headnum0, head1, headnum1, head2, headnum2, head3, detail, fileno);

            }
        }

        return jsonData;
    }

    /**
     * 商品和服务支出明细表
     */
    @RequestMapping(value = "/report/gov/goods", method = RequestMethod.POST)
    @ResponseBody
    public JsonData Goods(@RequestBody Map<String,Object> PARAM,HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();

        //判断本地数据库是否有数据
        int isExistGoods = reportGovService.isExistGoods(FIS_PERD, FISCAL, CO_CODE);

        if (isExistGoods > 0) {
            //删除本地数据库数据
            int deleteCount = reportGovService.deleteGoods(FIS_PERD, FISCAL, CO_CODE);
            System.out.println(deleteCount);
        }

        //调用存储过程
        Map map = new HashMap();
        map.put("fiscal", FISCAL);
        map.put("fis_perd", FIS_PERD);
        map.put("co_code", CO_CODE);
        map.put("cur", new ArrayList<>());

        //连接到用友数据库
        DynamicDataSourceHolder.setDataSource("dataSource18");
        reportGovService.doGoods(map);

        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
        List<GoodsModel> goodsList = new ArrayList<>();

        int i = 1;
        int count = 0;
        for (Map<String, Object> map1 : mapList) {

            GoodsModel goods = new GoodsModel();
            goods.setFISCAL(FISCAL);
            goods.setCO_CODE(CO_CODE);
            goods.setFIS_PERD(FIS_PERD);
            goods.setB_ACC_CODE(map1.get("B_ACC_CODE") == null ? "" : map1.get("B_ACC_CODE").toString());
            goods.setB_ACC_NAME(map1.get("B_ACC_NAME") == null ? "" : map1.get("B_ACC_NAME").toString());
            goods.setACC_CODE(map1.get("ACC_CODE") == null ? "" : map1.get("ACC_CODE").toString());
            goods.setMONEY1(map1.get("MONEY1") == null ? "" : map1.get("MONEY1").toString());
            goods.setMONEY2(map1.get("MONEY2") == null ? "" : map1.get("MONEY2").toString());
            goods.setMONEY3(map1.get("MONEY3") == null ? "" : map1.get("MONEY3").toString());
            goods.setMONEY4(map1.get("MONEY4") == null ? "" : map1.get("MONEY4").toString());
            goods.setMONEY5(map1.get("MONEY5") == null ? "" : map1.get("MONEY5").toString());
            goods.setMONEY6(map1.get("MONEY6") == null ? "" : map1.get("MONEY6").toString());
            goods.setMONEY7(map1.get("MONEY7") == null ? "" : map1.get("MONEY7").toString());
            goods.setMONEY8(map1.get("MONEY8") == null ? "" : map1.get("MONEY8").toString());
            goods.setMONEY9(map1.get("MONEY9") == null ? "" : map1.get("MONEY9").toString());
            goods.setMONEY10(map1.get("MONEY10") == null ? "" : map1.get("MONEY10").toString());
            goods.setMONEY11(map1.get("MONEY11") == null ? "" : map1.get("MONEY11").toString());
            goods.setMONEY12(map1.get("MONEY12") == null ? "" : map1.get("MONEY12").toString());
            goods.setMONEY13(map1.get("MONEY13") == null ? "" : map1.get("MONEY13").toString());
            goods.setMONEY14(map1.get("MONEY14") == null ? "" : map1.get("MONEY14").toString());
            goods.setMONEY15(map1.get("MONEY15") == null ? "" : map1.get("MONEY15").toString());
            goods.setMONEY16(map1.get("MONEY16") == null ? "" : map1.get("MONEY16").toString());
            goods.setMONEY17(map1.get("MONEY17") == null ? "" : map1.get("MONEY17").toString());
            goods.setMONEY18(map1.get("MONEY18") == null ? "" : map1.get("MONEY18").toString());
            goods.setMONEY19(map1.get("MONEY19") == null ? "" : map1.get("MONEY19").toString());
            goods.setMONEY20(map1.get("MONEY20") == null ? "" : map1.get("MONEY20").toString());
            goods.setMONEY21(map1.get("MONEY21") == null ? "" : map1.get("MONEY21").toString());
            goods.setMONEY22(map1.get("MONEY22") == null ? "" : map1.get("MONEY22").toString());
            goods.setMONEY23(map1.get("MONEY23") == null ? "" : map1.get("MONEY23").toString());
            goods.setMONEY24(map1.get("MONEY24") == null ? "" : map1.get("MONEY24").toString());
            goods.setMONEY25(map1.get("MONEY25") == null ? "" : map1.get("MONEY25").toString());
            goods.setMONEY26(map1.get("MONEY26") == null ? "" : map1.get("MONEY26").toString());
            goods.setMONEY27(map1.get("MONEY27") == null ? "" : map1.get("MONEY27").toString());
            goodsList.add(goods);
            if (i % 100 == 0) {
                count = reportGovService.addGoods(goodsList);
                i = 1;
                goodsList.clear();
            } else {
                i++;
            }

        }
        if (i > 1) {
            count = reportGovService.addGoods(goodsList);
        }
        if (count > 0) {
            if (STATUS.equals("1")) {
                //查看
                List<Map<String, Object>> dataList = reportGovService.getGoods(FIS_PERD, FISCAL, CO_CODE);
                if (dataList.size() == 1) {
                    jsonData = jsonData.fail("未生成报表");
                } else {
                    jsonData = jsonData.success(dataList, "成功", dataList.size());
                }
            } else {
                //下载
                List<Map<String, Object>> dataList = reportGovService.getGoods2(FIS_PERD, FISCAL, CO_CODE);
                String[] ids = new String[]{"","","01","02","03","04","05","06","07","08","09","10","11","","01","02","03","04","05","06","07","08","09","10","11"};
                for (int j= 0;j<dataList.size();j++){
                    dataList.get(j).put("ID",ids[j]);
                }
                String dateStr = MaxDateUtil.getLastDayOfMonth(FISCAL+"-"+FIS_PERD);
                String sheetName = "商    品    和    服    务    支    出    明    细    表";
                String fileno = "商品和服务支出明细表";
                String[] head0 = new String[]{"单位名称：" + PARAM.get("SHORTNAME") , "单位名称：柯桥区" + PARAM.get("SHORTNAME") ,
                        "单位名称：" + PARAM.get("SHORTNAME"), dateStr, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "单位:元（以下至角分）", "单位:元（以下至角分）", "单位:元（以下至角分）"};
                String[] headnum0 = new String[]{"1,1,0,2", "1,1,3,26", "1,1,27,29"};
                String[] head1 = new String[]{"功能分类科目", "", "合计", "办公费", "印刷费", "咨询费", "手续费", "水费", "电费", "邮电费",
                        "取暖费", "物业管理费", "差旅费", "因公出国费用", "维修费", "租赁费", "会议费", "培训费", "公务接待费", "专用材料费",
                        "被装购置费", "专用燃料费", "劳务费", "委托业务费", "工会经费", "福利费", "公务用车运行维护费", "其他交通费用", "税金及附加费用", "其他商品和服务支出"};
                String[] headnum1 = new String[]{"2,2,0,1", "2,3,2,2", "2,3,3,3", "2,3,4,4", "2,3,5,5", "2,3,6,6", "2,3,7,7", "2,3,8,8", "2,3,9,9", "2,3,10,10", "2,3,11,11"
                        , "2,3,12,12", "2,3,13,13", "2,3,14,14", "2,3,15,15", "2,3,16,16", "2,3,17,17", "2,3,18,18", "2,3,19,19", "2,3,20,20", "2,3,21,21", "2,3,22,22"
                        , "2,3,23,23", "2,3,24,24", "2,3,25,25", "2,3,26,26", "2,3,27,27", "2,3,28,28", "2,3,29,29"};
                String[] head2 = new String[]{"编码", "名称","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
                String[] headnum2 = new String[]{"3,4,0,0", "3,4,1,1"};
                String[] head3 = new String[]{"", "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
                String[] detail = new String[]{"ID", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6", "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11",
                        "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16", "MONEY17", "MONEY18", "MONEY19", "MONEY20", "MONEY21", "MONEY22", "MONEY23", "MONEY24", "MONEY25", "MONEY26", "MONEY27"};

                Excel.exportGov(request, response, dataList, sheetName, head0, headnum0, head1, headnum1, head2, headnum2, head3, detail, fileno);

            }
        }

        return jsonData;
    }

    /**
     * 经费支出表
     */
    @RequestMapping(value="/report/gov/jfzc",method = RequestMethod.POST)
    @ResponseBody
    public JsonData FeePay(@RequestBody Map<String,Object> PARAM,HttpServletRequest request,HttpServletResponse response){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int i = 1;
        int count = 0;
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String STATUS = PARAM.get("STATUS").toString();
        JsonData jsonData = new JsonData();
        //判断本地库是否存在数据
        int isExistFee = reportGovService.isExistFee(FISCAL,FIS_PERD,CO_CODE);
        System.out.println("isExistFee:"+isExistFee);

        if(isExistFee > 0){
            int deleteFee = reportGovService.deleteFee(FISCAL,FIS_PERD,CO_CODE);
            System.out.println(deleteFee);
        }


        //连接到用友数据库，调用存储过程
        DynamicDataSourceHolder.setDataSource("dataSource18");
        Map map = new HashMap();
        map.put("fiscal",FISCAL);
        map.put("fis_perd",FIS_PERD);
        map.put("co_code",CO_CODE);
        map.put("cur",new ArrayList<>());
        reportGovService.doFee(map);

        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("cur");
        List<FeePayModel> feeList = new ArrayList<>();
        for (Map<String,Object> map1:mapList){

            FeePayModel feePay = new FeePayModel();
            feePay.setFIS_PERD(FIS_PERD);
            feePay.setFISCAL(FISCAL);
            feePay.setCO_CODE(CO_CODE);
            feePay.setB_ACC_NAME(map1.get("B_ACC_NAME")==null?"":map1.get("B_ACC_NAME").toString());
            feePay.setB_ACC_CODE(map1.get("B_ACC_CODE")==null?"":map1.get("B_ACC_CODE").toString());
            feePay.setACC_CODE(map1.get("ACC_CODE")==null?"":map1.get("ACC_CODE").toString());
            feePay.setMONEY1(map1.get("MONEY1")==null?"":map1.get("MONEY1").toString());
            feePay.setMONEY2(map1.get("MONEY2")==null?"":map1.get("MONEY2").toString());
            feePay.setMONEY3(map1.get("MONEY3")==null?"":map1.get("MONEY3").toString());
            feePay.setMONEY4(map1.get("MONEY4")==null?"":map1.get("MONEY4").toString());
            feePay.setMONEY5(map1.get("MONEY5")==null?"":map1.get("MONEY5").toString());
            feePay.setMONEY6(map1.get("MONEY6")==null?"":map1.get("MONEY6").toString());
            feePay.setMONEY7(map1.get("MONEY7")==null?"":map1.get("MONEY7").toString());
            feePay.setMONEY8(map1.get("MONEY8")==null?"":map1.get("MONEY8").toString());
            feePay.setMONEY9(map1.get("MONEY9")==null?"":map1.get("MONEY9").toString());
            feePay.setMONEY10(map1.get("MONEY10")==null?"":map1.get("MONEY10").toString());
            feeList.add(feePay);
            if (i % 100 == 0) {
                count = reportGovService.addFee(feeList);
                i = 1;
                feeList.clear();
            } else {
                i++;
            }
        }

        if (i>1){
            count = reportGovService.addFee(feeList);
        }

        if (count > 0){
            if (STATUS.equals("1")){
                //查看
                List<Map<String,Object>> dataList = reportGovService.getFeeList(FISCAL,FIS_PERD,CO_CODE);
                System.out.println("--------------"+dataList.size());
                if (dataList.size()==1){
                    jsonData = jsonData.fail("未生成报表");
                }else{
                    jsonData = jsonData.success(dataList,"生成报表成功",dataList.size());
                }

            }else{
                //下载
                List<Map<String,Object>> dataList = reportGovService.getFeeList2(FISCAL,FIS_PERD,CO_CODE);
                String[] ids = new String[]{"","","01","02","03","04","05","06","07","08","09","10","11","","01","02","03","04","05","06","07","08","09","10","11"};
                for (int j= 0;j<dataList.size();j++){
                    dataList.get(j).put("ID",ids[j]);
                }
                String dateStr = MaxDateUtil.getLastDayOfMonth(FISCAL+"-"+FIS_PERD);
                String sheetName = "经  费  支  出  表";
                String fileno = "经费支出表";
                String[] head0 = new String[]{"单位名称："+PARAM.get("SHORTNAME"), "单位名称："+PARAM.get("SHORTNAME"),
                        dateStr, "","","","","","","","单位:元（以下至角分）","单位:元（以下至角分）",""};
                String[] headnum0 = new String[]{"1,1,0,1","1,1,2,9","1,1,10,12"};
                String[] head1 = new String[]{"功能科目分类","","基本支出及项目支出","","","","","","","","","",""};
                String[] headnum1 = new String[]{"2,2,0,1","2,2,2,12"};
                String[] head2 = new String[]{"编码","名称","合计","工资福利支出","商品和服务支出","对个人和家庭补助支出","债务利息及费用支出","资本性支出（基础建设）",
                        "资本性支出","对企业补助（基础建设）","对企业补助","对社会保障基金补助","其他支出"};
                String[] headnum2 = new String[]{"3,4,0,0","3,4,1,1"};
                String[] head3 = new String[]{"", "", "1", "2","3","4","5","6","7","8","9","10","11"};
                String[] detail = new String[]{"ID","B_ACC_NAME","ALLMONEY","MONEY1","MONEY2","MONEY3","MONEY4","MONEY5","MONEY6","MONEY7","MONEY8","MONEY9","MONEY10"};
                Excel.exportGov(request,response,dataList,sheetName,head0,headnum0,head1,headnum1,head2,headnum2,head3,detail,fileno);
            }
        }else{
            jsonData = jsonData.fail("生成报表失败");
        }

        return jsonData;
    }



    /**
     * 收入支出表
     */
   @RequestMapping(value="/report/gov/incomePay",method = RequestMethod.POST)
   @ResponseBody
   public JsonData incomePay(@RequestBody Map<String,Object> param,HttpServletRequest request,HttpServletResponse response){
       DynamicDataSourceHolder.setDataSource("dataSource1");

       String FISCAL = param.get("FISCAL").toString();
       String CO_CODE = param.get("CO_CODE").toString();
       String STATUS = param.get("STATUS").toString();
       String FIS_PERD = param.get("FIS_PERD").toString();
       Map map = new HashMap();
       map.put("FISCAL",FISCAL);
       map.put("FIS_PERD",FIS_PERD);
       map.put("CO_CODE",CO_CODE);
       JsonData jsonData = new JsonData();
       //判断本地数据库是否有数据
       int isExist = reportGovService.isExistIncomePay(map);
       System.out.println("isExist："+ isExist);

       if (isExist > 0 ){
           int deleteCount = reportGovService.deleteIncomePay(map);
           System.out.println("deleteCount:"+deleteCount);
       }

       map.put("cur",new ArrayList<>());
       DynamicDataSourceHolder.setDataSource("dataSource18");
       reportGovService.doIncomePay(map);

       DynamicDataSourceHolder.setDataSource("dataSource1");
       List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("cur");
       for (Map<String,Object> args:mapList) {
           args.put("FIS_PERD",FIS_PERD);
           args.put("FISCAL",FISCAL);
           args.put("CO_CODE",CO_CODE);
       }
       int count = reportGovService.addIncomePay(mapList);

       if (count > 0){

           if (STATUS.equals("1")){
               List<Map<String,Object>> datalist = reportGovService.getIncomePay(map);
               if (datalist.size()==0){
                   jsonData = jsonData.fail("未生成该报表");
               }else{
                   //查看
                   jsonData = jsonData.success(datalist,"报表生成成功",datalist.size());
               }

           }else{
               //下载
               List<Map<String,Object>> datalist = reportGovService.getIncomePay2(map);
               if (datalist.size()==0){
                   jsonData =jsonData.fail("未生成该报表");
               }else{
                   String sheetName = param.get("SHORTNAME")+FISCAL+"年"+FIS_PERD+"月"+"收入支出表";
                   String fileNo = param.get("SHORTNAME")+FISCAL+"年"+FIS_PERD+"月"+"收入支出表";
                   String[] head1 = {"B_ACC_CODE","B_ACC_NAME","ID","MONEY1","MONEY2","MONEY3"};
                   String[] head0 = {"收入金额","支出金额","年终金额"};
                   Excel.exportOne(request, response, datalist, sheetName, fileNo, head1, head0);
               }
           }
       }else{
           jsonData = jsonData.fail("报表生成失败");
       }

       return jsonData;
   }


}
