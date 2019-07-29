//package com.heitian.ssm.controller.report;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.heitian.ssm.common.JsonData;
//import com.heitian.ssm.model.report.GnkmTreeNode;
//import com.heitian.ssm.service.report.GnkmReportService;
//import com.heitian.ssm.utils.FormatHelper;
//import com.heitian.ssm.utils.excel.Excel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 支出科目功能月报
// */
//@RestController
//@CrossOrigin
//@RequestMapping("/GNKM")
//public class GnkmController {
//
//    @Autowired
//    private GnkmReportService gnkmReportService;
//
//
//    /**
//     * 判断已上报至几月
//     */
//    @RequestMapping(value = "/month", method = RequestMethod.GET)
//    public Map<String, Object> getMonth(HttpServletRequest request) {
//        Map<String, Object> args = new HashMap<>();
//        args.put("CO_CODE", request.getParameter("CO_CODE"));
//        args.put("FISCAL", request.getParameter("FISCAL"));
//        Map<String, Object> result = new HashMap<>();
//        String FIS_PERD = gnkmReportService.getMonth("dataSource1", args);
//        if (FIS_PERD == null) {
//            result.put("msg", "未生成月报");
//        } else {
//            result.put("msg", "已确认月报于" + FIS_PERD + "月");
//        }
//        return result;
//    }
//
//
//    /**
//     * 乡镇生成报表
//     * <p>
//     * 根据年月在本地数据库判断是否已经生成月报
//     * <p>
//     * 1.如果已经生成月报判断setid如果是3,5 则不能重新生成
//     * <p>
//     * 2.如果未生成月报或已生成那个月报setid1 则能重新生成
//     *
//     * @return
//     */
//    @RequestMapping(value = "/getlist", method = RequestMethod.GET)
//    public Map<String, Object> getListRemote(HttpServletRequest request) {
//        Map<String, Object> args = new HashMap<>();
//        args.put("CO_CODE", request.getParameter("CO_CODE"));
//        args.put("FISCAL", request.getParameter("FISCAL"));
//        args.put("FIS_PERD", request.getParameter("FIS_PERD"));
//        Map<String, Object> result = new HashMap<>();
//
//        String setid = gnkmReportService.IsSetid("dataSource1", args);
//        if (setid == null || setid.equals("1")) {
//            List<Map<String, Object>> list = gnkmReportService.getList("dataSource18", args);
//            gnkmReportService.saveList(list, args);
//            List<Map<String, Object>> list1 = gnkmReportService.xzGetList("dataSource1", args);
//            List<GnkmTreeNode> gnkmTreeNodes = FormatHelper.GnkmListToTreeByIsbnCode(list1);
//            System.out.println(JSON.toJSONString(gnkmTreeNodes));
//            result.put("code", true);
//            result.put("list", gnkmTreeNodes);
//            result.put("msg", "报表已生成");
//
//        } else {
//            result.put("code", false);
//            if (setid.equals("3")) {
//                result.put("msg", "该报表已上报,无法重新生成");
//            } else if (setid.equals("5")) {
//                result.put("msg", "该报表已确认");
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * 对报表中的数据进行调整，XZ_STAD_AMT字段和QCZ_STAD_AMT字段使用同一接口
//     *
//     * @return
//     */
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public Map<String, Object> set(@RequestBody String PARAM, HttpServletRequest request) {
//        Map<String,Object> args = JSON.parseObject(PARAM,new TypeReference<Map<String, Object>>(){});
//        List<Map<String,Object>> list = (List<Map<String, Object>>) args.get("list");
//        String STATUS =  args.get("STATUS").toString();
////        Map<String, Object> args = new HashMap<>();
////        args.put("STATUS", request.getParameter("STATUS"));
//        System.out.print("STATUS" +STATUS);
//        List<Map<String,Object>> gnkmList = FormatHelper.findChildrenToList2(list);
//        gnkmReportService.update(gnkmList,args);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", true);
//        result.put("msg", "数据更新成功");
//        return result;
//    }
//
//    /**
//     * 报表上报 状态1->3
//     *
//     * @return
//     */
//    @RequestMapping(value = "/setid", method = RequestMethod.POST)
//    public Map<String, Object> report(@RequestBody Map<String,Object> args, HttpServletRequest request) {
//        List<Map<String,Object>> list = (List<Map<String, Object>>) args.get("list");
//        List<Map<String,Object>> gnkmList = FormatHelper.findChildrenToList2(list);
//        gnkmReportService.update(gnkmList, args);
//        gnkmReportService.updateSETID(3, args);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", true);
//        result.put("msg", "上报成功");
//        return result;
//    }
//
//
//    /**
//     * 乡镇/李晓伟查看支出功能科目报表
//     * <p>
//     * setid 状态为1：乡镇的获取XZ_STAD_AMT
//     * <p>
//     * 乡镇：
//     * setid1:取数为xz_stad_amt
//     * setid3,5 取数为 qcz_stad_amt
//     * <p>
//     * 区财政：
//     * setid1:获取不到
//     * setid3,5 取数为 qcz_stad_amt
//     *
//     * @return xz/getlist?CO_CODE=606002&&FISCAL=2019&FIS_PERD=1
//     */
//
//    @RequestMapping(value = "xz/getlist", method = RequestMethod.POST)
//    public Map<String, Object> xzGetList(@RequestBody String PARAM,HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> args   = JSON.parseObject(PARAM, new TypeReference<HashMap<String,Object>>() {
//        });
//
//        Map<String, Object> result = new HashMap<>();
//        if (args.get("STATUS").equals("1")) {
//            List<Map<String, Object>> list = gnkmReportService.xzGetList("dataSource1", args);
//
//            if (list.size() == 0) {
//                result.put("code", false);
//                result.put("msg", "未生成报表");
//            } else {
//                List<GnkmTreeNode> gnkmTreeNodes = FormatHelper.GnkmListToTreeByIsbnCode(list);
//                result.put("code", true);
//                result.put("list", gnkmTreeNodes);
//                result.put("msg", "获取数据成功");
//            }
//
//        } else {
//            List<Map<String, Object>> list = gnkmReportService.xzGetList1("dataSource1", args);
//            if (list.size() == 0) {
//                result.put("code", false);
//                result.put("msg", "未生成报表");
//            } else {
//                String XZName = "金额";
//                String sheetName = args.get("SHORTNAME").toString() +args.get("FISCAL")+ "年" + args.get("FIS_PERD") + "月一般公共预算支出功能科目月报";
//                String fileno = args.get("SHORTNAME").toString() +args.get("FISCAL")+ "年" + args.get("FIS_PERD") +  "月一般公共预算支出功能科目月报";
//                String[] head1 = {"B_ACC_CODE", "B_ACC_NAME", "ID", "MONEY"};
//                Excel.exportOne(request, response, list, sheetName, fileno, head1, XZName);
//            }
//        }
//
//        return result;
//    }
//
//    @RequestMapping(value = "/qcz/getlist", method = RequestMethod.POST)
//    public Map<String, Object> getList(HttpServletRequest request) {
//
//        Map<String, Object> args = new HashMap<>();
//        args.put("CO_CODE", request.getParameter("CO_CODE"));
//        args.put("FISCAL", request.getParameter("FISCAL"));
//        args.put("FIS_PERD", request.getParameter("FIS_PERD"));
//        args.put("CPOSTGUID", request.getParameter("CPOSTGUID"));
//        List<Map<String, Object>> list = gnkmReportService.getList("dataSource1", args);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", true);
//        result.put("list", list);
//        return result;
//    }
//
//
//    /**
//     * 李晓伟确认支出功能科目月报 3-5
//     *
//     * @return
//     */
//    @RequestMapping(value = "/qcz/enter", method = RequestMethod.POST)
//    public Map<String, Object> reportEnter(@RequestBody List<Map<String,Object>> list, HttpServletRequest request) {
//        Map<String, Object> args = new HashMap<>();
//        args.put("CO_CODE", request.getParameter("CO_CODE"));
//        args.put("FISCAL", request.getParameter("FISCAL"));
//        args.put("FIS_PERD", request.getParameter("FIS_PERD"));
//        args.put("STATUS", request.getParameter("STATUS"));
//        List<Map<String,Object>> gnkmList= FormatHelper.findChildrenToList2(list);
//        gnkmReportService.update(gnkmList, args);
//        gnkmReportService.updateSETID(5, args);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", true);
//        result.put("msg", "已确认该报表");
//        return result;
//    }
//
//    /**
//     * 李晓伟退回支出功能科目月报
//     *
//     * @return
//     */
//    @RequestMapping(value = "/qcz/goback", method = RequestMethod.POST)
//    public Map<String, Object> reportGoBack(HttpServletRequest request) {
//        Map<String, Object> args = new HashMap<>();
//        args.put("CO_CODE", request.getParameter("CO_CODE"));
//        args.put("FISCAL", request.getParameter("FISCAL"));
//        args.put("FIS_PERD", request.getParameter("FIS_PERD"));
//        gnkmReportService.clearAMT("QCZ_STAD_AMT", "1", args);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", true);
//        return result;
//    }
//
//    /**
//     * 李晓伟编辑支出功能科目月报
//     *
//     * @return
//     */
//    @RequestMapping(value = "/qcz/edit", method = RequestMethod.POST)
//    public Map<String, Object> reportEdit(@RequestBody List<Map<String,Object>> list,HttpServletRequest request) {
//        Map<String, Object> args = new HashMap<>();
//        args.put("STATUS", request.getParameter("STATUS"));
//        List<Map<String,Object>> gnkmList = FormatHelper.findChildrenToList2(list);
//        gnkmReportService.update(gnkmList, args);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", true);
//        return result;
//    }
//
//    /**
//     * 李晓伟取消确认支出功能科目月报
//     *
//     * @return
//     */
//    @RequestMapping("/qcz/cancel")
//    public Map<String, Object> reportCancel(HttpServletRequest request) {
//        Map<String, Object> args = new HashMap<>();
//        args.put("CO_CODE", request.getParameter("CO_CODE"));
//        args.put("FISCAL", request.getParameter("FISCAL"));
//        args.put("FIS_PERD", request.getParameter("FIS_PERD"));
//        gnkmReportService.updateSETID(3, args);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", true);
//        return result;
//    }
//
//    /**
//     * 取16个乡镇的钱
//     */
//    @RequestMapping(value = "/getAllMoney", method = RequestMethod.POST)
//    public JsonData getAllMoney(HttpServletRequest request, HttpServletResponse response) {
//        String STATUS = request.getParameter("STATUS");
//        Map<String, Object> map = new HashMap();
//        map.put("FISCAL", request.getParameter("FISCAL"));
//        map.put("FIS_PERD", request.getParameter("FIS_PERD"));
//        map.put("STATUS", request.getParameter("STATUS"));
//
//        JsonData jsonData = new JsonData();
//        List<Map<String, Object>> list = gnkmReportService.getAllMoney(map);
//
//        if (STATUS.equals("1")) {
//            if (list.size() == 0) {
//                jsonData = jsonData.fail("未生成报表");
//            } else {
//                jsonData = jsonData.success(list, "成功", list.size());
//            }
//        } else {
//            //STATUS  2下载
//            String[] head1 = new String[]{"B_ACC_CODE", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
//                    "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
//            String sheetName = request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月柯桥区一般公共预算支出功能科目月报";
//            String fileno = request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月柯桥区一般公共预算支出功能科目月报";
//            Excel.exportJJFL(request, response, list, sheetName, fileno, head1);
//        }
//        return jsonData;
//    }
//}
