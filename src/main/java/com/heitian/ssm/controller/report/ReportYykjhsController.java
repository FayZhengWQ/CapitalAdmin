package com.heitian.ssm.controller.report;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.report.GnkmAllMoneyTreeNode;
import com.heitian.ssm.model.report.JbzcAllMoneyTreeNode;
import com.heitian.ssm.model.report.JjkmAllMoneyTreeNode;
import com.heitian.ssm.service.report.ReportYykjhsService;
import com.heitian.ssm.utils.FormatHelper;
import com.heitian.ssm.utils.excel.Excel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: web-ssm
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-03-31 16:38
 **/
@Controller
@CrossOrigin
public class ReportYykjhsController {


    @Resource
    private ReportYykjhsService reportYykjhsService;


    /**
     * 从用友获取支出功能科目
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/yykjhs/getGnkmAllMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getAllMoney(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource18");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();

        Map map = new HashMap();
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("return_cursor", new ArrayList<Map<String, Object>>());
        JsonData jsonData = new JsonData();
        reportYykjhsService.getGnkmAllMoney(map);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("return_cursor");
        if (STATUS.equals("1")) {
            if (list.size() == 1) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                list.remove(0);
                List<GnkmAllMoneyTreeNode> gnkmAllMoneyTreeNodes = FormatHelper.GnkmAllMoneyListToTreeByIsbnCode(list);
                jsonData = jsonData.success(gnkmAllMoneyTreeNodes, "成功", list.size());
            }
        } else {
            if (list.size() == 1) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                String[] head1 = new String[]{"B_ACC_CODE", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                        "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                String sheetName = FISCAL + "年" + FIS_PERD + "月柯桥区一般公共预算支出功能科目月报";
                String fileno = FISCAL + "年" + FIS_PERD + "月柯桥区一般公共预算支出功能科目月报";
                String[] head0 = new String[]{"编码", "支出科目", "合计数", "马鞍镇",
                        "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                        "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                Excel.exportJJFL(request, response, list, sheetName, fileno,head0, head1);
            }
        }

        return jsonData;
    }


    /**
     * 从用友获取经济科目
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/yykjhs/getJjkmAllMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getJjkmAllMoney(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource18");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();

        Map map = new HashMap();
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("return_cursor", new ArrayList<Map<String, Object>>());
        JsonData jsonData = new JsonData();
        reportYykjhsService.getJjkmAllMoney(map);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("return_cursor");
        if (STATUS.equals("1")) {
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                list.remove(0);
                List<JjkmAllMoneyTreeNode> jjkmAllMoneyTreeNodes = FormatHelper.JjkmAllMoneyListToTreeByIsbnCode(list);
                jsonData = jsonData.success(jjkmAllMoneyTreeNodes, "成功", list.size());
            }

        } else {
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                String[] head1 = new String[]{"OUTLAY_CODE", "OUTLAY_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                        "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                String sheetName = FISCAL + "年" + FIS_PERD + "月柯桥区政府经济科目支出月报";
                String fileno = FISCAL + "年" + FIS_PERD + "月柯桥区政府经济科目支出月报";
                String[] head0 = new String[]{"编码", "支出科目", "合计数", "马鞍镇",
                        "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                        "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                Excel.exportJJFL(request, response, list, sheetName, fileno,head0, head1);
            }
        }

        return jsonData;
    }


    /**
     * 从用友获取基本支出
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/yykjhs/getJbzcAllMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getJbzcAllMoney(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource18");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();

        Map map = new HashMap();
        map.put("FISCAL", FISCAL);
        map.put("FIS_PERD", FIS_PERD);
        map.put("return_cursor", new ArrayList<Map<String, Object>>());
        JsonData jsonData = new JsonData();
        reportYykjhsService.getJbzcAllMoney(map);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("return_cursor");
        if (STATUS.equals("1")) {
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                list.remove(0);
                List<JbzcAllMoneyTreeNode> jbzcAllMoneyTreeNodes = FormatHelper.JbzcAllMoneyListToTreeByIsbnCode(list);
                jsonData = jsonData.success(jbzcAllMoneyTreeNodes, "成功", list.size());
            }
        } else {
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                String[] head1 = new String[]{"GOV_OUTLAY_CODE", "GOV_OUTLAY_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                        "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                String sheetName =FISCAL + "年" + FIS_PERD + "月柯桥区一般公共预算基本支出科目月报";
                String fileno = FISCAL + "年" + FIS_PERD + "月柯桥区一般公共预算基本支出科目月报";
                String[] head0 = new String[]{"编码", "支出科目", "合计数", "马鞍镇",
                        "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                        "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                Excel.exportJJFL(request, response, list, sheetName, fileno,head0, head1);
            }

        }

        return jsonData;
    }


}
